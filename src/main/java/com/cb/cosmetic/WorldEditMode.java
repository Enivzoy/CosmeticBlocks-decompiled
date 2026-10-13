package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.dungeon.RoomDataKt;
import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import com.cb.utils.ChatUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public final class WorldEditMode {
    public static final WorldEditMode INSTANCE = new WorldEditMode();

    private static boolean worldEditMode;
    private static boolean weLeftWasPressed;
    private static boolean weRightWasPressed;

    @Nullable
    private static BlockPos pos1;
    @Nullable
    private static BlockPos pos2;

    @Nullable
    private static Map<String, String> undoSnapshot;
    @Nullable
    private static String undoDimension;
    @Nullable
    private static Map<String, String> undoRoomSnapshot;
    @Nullable
    private static ScannedRoom undoRoom;

    private WorldEditMode() {
    }

    private Minecraft getMc() {
        return Minecraft.getInstance();
    }

    public boolean getWorldEditMode() {
        return worldEditMode;
    }

    public void setWorldEditMode$cb_client(boolean value) {
        worldEditMode = value;
    }

    @Nullable
    public BlockPos getPos1() {
        return pos1;
    }

    @Nullable
    public BlockPos getPos2() {
        return pos2;
    }

    public void setPos1(@NotNull BlockPos pos) {
        pos1 = pos;
        ChatUtil.INSTANCE.success("Pos1 set to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    public void setPos2(@NotNull BlockPos pos) {
        pos2 = pos;
        ChatUtil.INSTANCE.success("Pos2 set to " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    public void clearSelection() {
        pos1 = null;
        pos2 = null;
        ChatUtil.INSTANCE.success("Selection cleared");
    }

    @NotNull
    public String getSelectionInfo() {
        BlockPos p1 = pos1;
        BlockPos p2 = pos2;

        if (p1 == null && p2 == null) {
            return "No selection set";
        }
        if (p1 == null) {
            return "Pos1 not set (Pos2: " + p2.getX() + ", " + p2.getY() + ", " + p2.getZ() + ")";
        }
        if (p2 == null) {
            return "Pos2 not set (Pos1: " + p1.getX() + ", " + p1.getY() + ", " + p1.getZ() + ")";
        }

        int sizeX = Math.abs(p1.getX() - p2.getX()) + 1;
        int sizeY = Math.abs(p1.getY() - p2.getY()) + 1;
        int sizeZ = Math.abs(p1.getZ() - p2.getZ()) + 1;

        return sizeX + "x" + sizeY + "x" + sizeZ + " (" + (sizeX * sizeY * sizeZ) + ") | P1: "
            + p1.getX() + "," + p1.getY() + "," + p1.getZ()
            + " | P2: " + p2.getX() + "," + p2.getY() + "," + p2.getZ();
    }

    @Nullable
    public List<Pair<BlockState, Integer>> parseWeightedBlocks(@NotNull String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] tokens = trimmed.split("\\s+");
        List<Pair<BlockState, Integer>> result = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }

            int colonIdx = token.lastIndexOf(':');
            String blockId;
            int weight;

            if (colonIdx > 0 && colonIdx < token.length() - 1) {
                String weightText = token.substring(colonIdx + 1).trim();
                Integer parsedWeight = parseIntOrNull(weightText);
                if (parsedWeight != null && parsedWeight > 0) {
                    blockId = token.substring(0, colonIdx).trim();
                    weight = parsedWeight;
                } else {
                    blockId = token;
                    weight = 1;
                }
            } else {
                blockId = token;
                weight = 1;
            }

            if (blockId.isEmpty()) {
                return null;
            }

            String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
            if (state == null) {
                return null;
            }
            result.add(new Pair<>(state, weight));
        }

        return result.isEmpty() ? null : result;
    }

    private BlockState pickWeightedBlock(List<? extends Pair<? extends BlockState, Integer>> blocks) {
        if (blocks.isEmpty()) {
            throw new IllegalArgumentException("No block weights provided");
        }
        if (blocks.size() == 1) {
            return blocks.get(0).getFirst();
        }

        int totalWeight = 0;
        for (Pair<? extends BlockState, Integer> block : blocks) {
            totalWeight += block.getSecond();
        }

        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        for (Pair<? extends BlockState, Integer> block : blocks) {
            int weight = block.getSecond();
            roll -= weight;
            if (roll < 0) {
                return block.getFirst();
            }
        }

        return blocks.get(blocks.size() - 1).getFirst();
    }

    @NotNull
    public String fillRegion(@NotNull String blockSpecs) {
        BlockPos p1 = pos1;
        if (p1 == null) {
            return "§cPos1 not set";
        }
        BlockPos p2 = pos2;
        if (p2 == null) {
            return "§cPos2 not set";
        }

        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (dim == null) {
            return "§cNot in a world";
        }

        List<Pair<BlockState, Integer>> blocks = parseWeightedBlocks(blockSpecs);
        if (blocks == null) {
            return "§cInvalid block specification";
        }

        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        Map<String, Map<String, String>> replacements = ReplacementStore.INSTANCE.getReplacements$cb_client();
        Map<String, String> dimMap = replacements.computeIfAbsent(dim, key -> new LinkedHashMap<>());
        Map<String, String> snapshot = new LinkedHashMap<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    snapshot.put(key, dimMap.get(key));
                }
            }
        }

        undoSnapshot = snapshot;
        undoDimension = dim;
        undoRoomSnapshot = null;
        undoRoom = null;

        ClientLevel level = getMc().level;
        int count = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    BlockState state = pickWeightedBlock(blocks);
                    String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
                    boolean isAir = level != null && level.getBlockState(new BlockPos(x, y, z)).isAir();
                    String raw = isAir ? "!" + serialized : serialized;
                    dimMap.put(key, raw);
                    ReplacementCache.INSTANCE.applyCacheForEntry$cb_client(x, y, z, raw);
                    count++;
                }
            }
        }

        DirtySections.INSTANCE.markRegionDirtyByBounds$cb_client(minX, minY, minZ, maxX, maxY, maxZ);
        SaveScheduler.INSTANCE.scheduleSave$cb_client();

        int sizeX = maxX - minX + 1;
        int sizeY = maxY - minY + 1;
        int sizeZ = maxZ - minZ + 1;

        String blockDesc = blocks.size() == 1
            ? BuiltInRegistries.BLOCK.getKey(blocks.get(0).getFirst().getBlock()).getPath()
            : blocks.size() + " block types";

        return "§aFilled " + count + " blocks (" + sizeX + "x" + sizeY + "x" + sizeZ + ") with " + blockDesc;
    }

    @NotNull
    public String replaceRegion(@NotNull String fromBlock, @NotNull String toSpecs) {
        BlockPos p1 = pos1;
        if (p1 == null) {
            return "§cPos1 not set";
        }
        BlockPos p2 = pos2;
        if (p2 == null) {
            return "§cPos2 not set";
        }

        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (dim == null) {
            return "§cNot in a world";
        }

        ClientLevel level = getMc().level;
        if (level == null) {
            return "§cNot in a world";
        }

        String normalizedFrom = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(fromBlock);
        BlockState fromState = BlockStateCodec.INSTANCE.resolveBlockState(normalizedFrom);
        if (fromState == null) {
            return "§cUnknown block: " + fromBlock;
        }

        List<Pair<BlockState, Integer>> toBlocks = parseWeightedBlocks(toSpecs);
        if (toBlocks == null) {
            return "§cInvalid target block specification";
        }

        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        Map<String, Map<String, String>> replacements = ReplacementStore.INSTANCE.getReplacements$cb_client();
        Map<String, String> dimMap = replacements.computeIfAbsent(dim, key -> new LinkedHashMap<>());
        Map<String, String> snapshot = new LinkedHashMap<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    snapshot.put(key, dimMap.get(key));
                }
            }
        }

        Block fromBlockObj = fromState.getBlock();
        int count = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    String existing = dimMap.get(key);
                    boolean matches = false;

                    if (existing != null) {
                        String clean = existing.startsWith("!") ? existing.substring(1) : existing;
                        BlockState existingState = BlockStateCodec.INSTANCE.resolveBlockState(clean);
                        matches = existingState != null && existingState.getBlock() == fromBlockObj;
                    } else {
                        matches = level.getBlockState(new BlockPos(x, y, z)).getBlock() == fromBlockObj;
                    }

                    if (matches) {
                        BlockState state = pickWeightedBlock(toBlocks);
                        boolean wasPhantom = existing != null && existing.startsWith("!");
                        String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
                        String raw = wasPhantom ? "!" + serialized : serialized;
                        dimMap.put(key, raw);
                        ReplacementCache.INSTANCE.applyCacheForEntry$cb_client(x, y, z, raw);
                        count++;
                    }
                }
            }
        }

        if (count > 0) {
            undoSnapshot = snapshot;
            undoDimension = dim;
            undoRoomSnapshot = null;
            undoRoom = null;
            DirtySections.INSTANCE.markRegionDirtyByBounds$cb_client(minX, minY, minZ, maxX, maxY, maxZ);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
        }

        String fromName = normalizedFrom.startsWith("minecraft:") ? normalizedFrom.substring("minecraft:".length()) : normalizedFrom;
        return "§aReplaced " + count + " '" + fromName + "' blocks";
    }

    @NotNull
    public String removeInRegion(@Nullable String filterBlock) {
        BlockPos p1 = pos1;
        if (p1 == null) {
            return "§cPos1 not set";
        }
        BlockPos p2 = pos2;
        if (p2 == null) {
            return "§cPos2 not set";
        }

        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (dim == null) {
            return "§cNot in a world";
        }

        Map<String, String> dimMap = ReplacementStore.INSTANCE.getReplacements$cb_client().get(dim);
        if (dimMap == null) {
            return "§cNo replacements in this dimension";
        }

        BlockState filterState = null;
        if (filterBlock != null) {
            String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(filterBlock);
            filterState = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
            if (filterState == null) {
                return "§cUnknown block: " + filterBlock;
            }
        }

        int minX = Math.min(p1.getX(), p2.getX());
        int maxX = Math.max(p1.getX(), p2.getX());
        int minY = Math.min(p1.getY(), p2.getY());
        int maxY = Math.max(p1.getY(), p2.getY());
        int minZ = Math.min(p1.getZ(), p2.getZ());
        int maxZ = Math.max(p1.getZ(), p2.getZ());

        Map<String, String> snapshot = new LinkedHashMap<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    snapshot.put(key, dimMap.get(key));
                }
            }
        }

        int count = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    String key = x + "," + y + "," + z;
                    String existing = dimMap.get(key);
                    if (existing == null) {
                        continue;
                    }

                    if (filterState == null || matchesState(existing, filterState.getBlock())) {
                        dimMap.remove(key);
                        long packed = BlockPos.asLong(x, y, z);
                        ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(packed);
                        ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
                        ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                        ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                        ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
                        count++;
                    }
                }
            }
        }

        if (count > 0) {
            if (dimMap.isEmpty()) {
                ReplacementStore.INSTANCE.getReplacements$cb_client().remove(dim);
            }
            undoSnapshot = snapshot;
            undoDimension = dim;
            undoRoomSnapshot = null;
            undoRoom = null;
            DirtySections.INSTANCE.markRegionDirtyByBounds$cb_client(minX, minY, minZ, maxX, maxY, maxZ);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
        }

        String desc = filterBlock != null
            ? "'" + filterBlock.replace("minecraft:", "") + "' blocks"
            : "blocks";
        return "§aRemoved " + count + " cosmetic " + desc + " in selection";
    }

    @NotNull
    public String floodFill(@NotNull BlockPos start, @NotNull String blockSpecs) {
        ClientLevel level = getMc().level;
        if (level == null) {
            return "§cNot in a world";
        }

        List<Pair<BlockState, Integer>> blocks = parseWeightedBlocks(blockSpecs);
        if (blocks == null) {
            return "§cInvalid block specification";
        }

        if (level.getBlockState(start).isAir()) {
            return "§cLook at a solid block to fill";
        }

        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
            return floodFillRoom(start, blocks, room, level);
        }

        return floodFillFlat(start, blocks, level);
    }

    private String floodFillFlat(BlockPos start, List<? extends Pair<? extends BlockState, Integer>> blocks, ClientLevel level) {
        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (dim == null) {
            return "§cNot in a world";
        }

        Block matchBlock = level.getBlockState(start).getBlock();
        int limit = (int) ((Number) CosmeticBlocksTab.INSTANCE.getFillModeLimit().getValue()).doubleValue();
        Map<String, Map<String, String>> replacements = ReplacementStore.INSTANCE.getReplacements$cb_client();
        Map<String, String> dimMap = replacements.computeIfAbsent(dim, key -> new LinkedHashMap<>());
        Map<String, String> snapshot = new LinkedHashMap<>();
        HashSet<Long> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(BlockPos.asLong(start.getX(), start.getY(), start.getZ()));

        int count = 0;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        boolean hitLimit = false;

        while (!queue.isEmpty()) {
            BlockPos pos = queue.removeFirst();
            if (level.getBlockState(pos).getBlock() != matchBlock) {
                continue;
            }
            if (count >= limit) {
                hitLimit = true;
                break;
            }

            String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
            snapshot.putIfAbsent(key, dimMap.get(key));

            String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(pickWeightedBlock(blocks));
            dimMap.put(key, serialized);
            ReplacementCache.INSTANCE.applyCacheForEntry$cb_client(pos.getX(), pos.getY(), pos.getZ(), serialized);
            count++;

            minX = Math.min(minX, pos.getX());
            minY = Math.min(minY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());
            maxX = Math.max(maxX, pos.getX());
            maxY = Math.max(maxY, pos.getY());
            maxZ = Math.max(maxZ, pos.getZ());

            for (Direction dir : Direction.values()) {
                BlockPos next = pos.relative(dir);
                long nextPacked = BlockPos.asLong(next.getX(), next.getY(), next.getZ());
                if (!visited.add(nextPacked)) {
                    continue;
                }
                queue.add(next);
            }
        }

        if (count > 0) {
            undoSnapshot = snapshot;
            undoDimension = dim;
            undoRoomSnapshot = null;
            undoRoom = null;
            DirtySections.INSTANCE.markRegionDirtyByBounds$cb_client(minX, minY, minZ, maxX, maxY, maxZ);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
        }

        String name = BuiltInRegistries.BLOCK.getKey(matchBlock).getPath();
        String suffix = hitLimit ? " §7(limit " + limit + " reached)" : "";
        return "§aFilled " + count + " '" + name + "' blocks" + suffix;
    }

    private String floodFillRoom(BlockPos start, List<? extends Pair<? extends BlockState, Integer>> blocks, ScannedRoom room, ClientLevel level) {
        Block matchBlock = level.getBlockState(start).getBlock();
        int limit = (int) ((Number) CosmeticBlocksTab.INSTANCE.getFillModeLimit().getValue()).doubleValue();
        Map<String, String> snapshot = new LinkedHashMap<>();
        HashSet<Long> visited = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(BlockPos.asLong(start.getX(), start.getY(), start.getZ()));

        int count = 0;
        boolean hitLimit = false;

        while (!queue.isEmpty()) {
            BlockPos pos = queue.removeFirst();
            if (level.getBlockState(pos).getBlock() != matchBlock) {
                continue;
            }
            if (count >= limit) {
                hitLimit = true;
                break;
            }

            BlockPos relPos = RoomDataKt.rotateToNorth(pos.subtract(room.getClayPos().atY(0)), room.getRotation());
            String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
            snapshot.putIfAbsent(relKey, RoomStore.INSTANCE.rawAtWorld$cb_client(pos, room));

            String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(pickWeightedBlock(blocks));
            RoomStore.INSTANCE.setRoomReplacementRaw$cb_client(pos, serialized, false, room);
            count++;

            for (Direction dir : Direction.values()) {
                BlockPos next = pos.relative(dir);
                long nextPacked = BlockPos.asLong(next.getX(), next.getY(), next.getZ());
                if (!visited.add(nextPacked)) {
                    continue;
                }
                queue.add(next);
            }
        }

        if (count > 0) {
            RoomStore.INSTANCE.resolveRoomBlocks$cb_client(room.getData().getName());
            SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
            undoRoomSnapshot = snapshot;
            undoRoom = room;
            undoSnapshot = null;
            undoDimension = null;
        }

        String name = BuiltInRegistries.BLOCK.getKey(matchBlock).getPath();
        String suffix = hitLimit ? " §7(limit " + limit + " reached)" : "";
        return "§aFilled " + count + " '" + name + "' blocks (room-relative)" + suffix;
    }

    @NotNull
    public String undoLastOperation() {
        Map<String, String> roomSnap = undoRoomSnapshot;
        ScannedRoom undoR = undoRoom;
        if (roomSnap != null && undoR != null) {
            RoomStore.INSTANCE.restoreRoomFill$cb_client(roomSnap, undoR);
            undoRoomSnapshot = null;
            undoRoom = null;
            return "§aUndone: " + roomSnap.size() + " blocks restored";
        }

        Map<String, String> snapshot = undoSnapshot;
        if (snapshot == null || undoDimension == null) {
            return "§cNothing to undo";
        }

        String dim = undoDimension;
        Map<String, Map<String, String>> replacements = ReplacementStore.INSTANCE.getReplacements$cb_client();
        Map<String, String> dimMap = replacements.computeIfAbsent(dim, key -> new LinkedHashMap<>());

        int restored = 0;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;

        for (Map.Entry<String, String> entry : snapshot.entrySet()) {
            String key = entry.getKey();
            String previousValue = entry.getValue();
            String[] parts = key.split(",");
            if (parts.length != 3) {
                continue;
            }

            Integer x = parseIntOrNull(parts[0]);
            Integer y = parseIntOrNull(parts[1]);
            Integer z = parseIntOrNull(parts[2]);
            if (x == null || y == null || z == null) {
                continue;
            }

            if (previousValue == null) {
                dimMap.remove(key);
                ReplacementCache.INSTANCE.clearCacheForPos$cb_client(x, y, z);
            } else {
                dimMap.put(key, previousValue);
                ReplacementCache.INSTANCE.applyCacheForEntry$cb_client(x, y, z, previousValue);
            }

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
            restored++;
        }

        if (dimMap.isEmpty()) {
            replacements.remove(dim);
        }

        undoSnapshot = null;
        undoDimension = null;

        if (restored > 0) {
            DirtySections.INSTANCE.markRegionDirtyByBounds$cb_client(minX, minY, minZ, maxX, maxY, maxZ);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
        }

        return "§aUndone: " + restored + " blocks restored";
    }

    public void tickWorldEditMode$cb_client() {
        if (getMc().gui.screen() != null) {
            return;
        }

        long window = Minecraft.getInstance().getWindow().handle();
        boolean leftPressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == 1;
        boolean rightPressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == 1;

        HitResult hit;
        if (leftPressed && !weLeftWasPressed && (hit = getMc().hitResult) instanceof BlockHitResult && ((BlockHitResult) hit).getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hit).getBlockPos();
            setPos1(pos);
        }

        if (rightPressed && !weRightWasPressed && (hit = getMc().hitResult) instanceof BlockHitResult && ((BlockHitResult) hit).getType() == HitResult.Type.BLOCK) {
            BlockPos pos = ((BlockHitResult) hit).getBlockPos();
            setPos2(pos);
            if (Boolean.TRUE.equals(CosmeticBlocksTab.INSTANCE.getWeFillOnClick().getValue()) && pos1 != null) {
                String result = fillRegion(EditMode.INSTANCE.getSelectedBlock());
                ChatUtil.INSTANCE.send(result);
            }
        }

        weLeftWasPressed = leftPressed;
        weRightWasPressed = rightPressed;
    }

    private boolean matchesState(String existing, Block filterBlock) {
        String clean = existing.startsWith("!") ? existing.substring(1) : existing;
        BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(clean);
        return state != null && state.getBlock() == filterBlock;
    }

    @Nullable
    private Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
