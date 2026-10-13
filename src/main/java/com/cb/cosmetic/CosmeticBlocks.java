/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.Intrinsics
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WallSide
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.ConfigSwitcher;
import com.cb.cosmetic.CosmeticRenderer;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.EditMode;
import com.cb.cosmetic.Favorites;
import com.cb.cosmetic.GlobalRemap;
import com.cb.cosmetic.LocationTracker;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.RoomStore;
import com.cb.cosmetic.Ruler;
import com.cb.cosmetic.SaveScheduler;
import com.cb.cosmetic.WorldEditMode;
import com.cb.dungeon.RoomScanner;
import com.cb.utils.ChatUtil;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public final class CosmeticBlocks {
    @NotNull
    public static final CosmeticBlocks INSTANCE = new CosmeticBlocks();
    private static boolean editModeKeyWasPressed;
    private static boolean placeModeKeyWasPressed;
    private static boolean worldEditKeyWasPressed;
    @Nullable
    private static String lastDimension;
    private static boolean lastEnabledState;
    private static boolean lastOnlyInDungeonsState;
    private static boolean lastKeepBlockSizeState;
    private static boolean lastLavaAsWaterState;
    @Nullable
    private static BlockPos lastLookBlockPos;
    private static final ThreadLocal<Boolean> updatingConnections;

    private CosmeticBlocks() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    @Nullable
    public final BlockPos getLastLookBlockPos() {
        return lastLookBlockPos;
    }

    public final void register() {
        BlockStateCodec.INSTANCE.buildVariantMaps$cb_client();
        ReplacementIO.INSTANCE.migrateOldConfig$cb_client();
        ReplacementIO.INSTANCE.load();
        GlobalRemap.INSTANCE.load();
        Favorites.INSTANCE.loadFavorites();
        RoomStore.INSTANCE.loadRoomBlocks();
        RoomStore.INSTANCE.resolveAllRoomBlocks();
        RoomScanner.INSTANCE.init();
        ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
        ClientTickEvents.END_CLIENT_TICK.register(CosmeticBlocks::onEndClientTick);
    }

    public final void flushAll() {
        SaveScheduler.INSTANCE.flushAll();
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z, boolean originalAir) {
        return ReplacementCache.INSTANCE.getReplacementAt(x, y, z, originalAir);
    }

    public static /* synthetic */ BlockState getReplacementAt$default(CosmeticBlocks cosmeticBlocks, int n, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 8) != 0) {
            bl = false;
        }
        return cosmeticBlocks.getReplacementAt(n, n2, n3, bl);
    }

    @JvmStatic
    @Nullable
    public static final BlockState getReplacementOrRemap(@Nullable BlockState original, int x, int y, int z, boolean originalAir) {
        BlockState perPos = ReplacementCache.INSTANCE.getReplacementAt(x, y, z, originalAir);
        if (perPos != null) {
            return perPos;
        }
        if (original == null || original.isAir()) {
            return null;
        }
        Block block = original.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        return GlobalRemap.INSTANCE.remapFor(block, x, y, z);
    }

    @JvmStatic
    @Nullable
    public static final BlockState getEffectReplacement(@NotNull BlockPos pos, @NotNull BlockState original) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        BlockState perPos = ReplacementCache.INSTANCE.getReplacement(pos);
        if (perPos != null) {
            return perPos;
        }
        if (original.isAir()) {
            return null;
        }
        Block block = original.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        return GlobalRemap.INSTANCE.remapFor(block, pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    public final BlockState getReplacement(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return ReplacementCache.INSTANCE.getReplacement(pos);
    }

    @JvmStatic
    @Nullable
    public static final BlockState swapFluid(@NotNull BlockState original) {
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue()).booleanValue()) {
            return null;
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !LocationTracker.INSTANCE.getInDungeon()) {
            return null;
        }
        if (!(original.getBlock() instanceof LiquidBlock)) {
            return null;
        }
        if (original.getFluidState().is(Fluids.LAVA) || original.getFluidState().is(Fluids.FLOWING_LAVA)) {
            Comparable comparable = original.getValue((Property)LiquidBlock.LEVEL);
            Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"getValue(...)");
            int level = ((Number)((Object)comparable)).intValue();
            return (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(level));
        }
        return null;
    }

    @Nullable
    public final BlockState getRetextureAt(int x, int y, int z) {
        return ReplacementCache.INSTANCE.getRetextureAt(x, y, z);
    }

    @Nullable
    public final BlockState getOriginalShapeAt(int x, int y, int z) {
        return ReplacementCache.INSTANCE.getOriginalShapeAt(x, y, z);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public final BlockState updateConnections(@NotNull BlockState state, int x, int y, int z, @Nullable BlockStateGetter getter) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Block block = state.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        Block block2 = block;
        if (!(block2 instanceof CrossCollisionBlock) && !(block2 instanceof WallBlock)) {
            return state;
        }
        if (updatingConnections.get().booleanValue()) {
            return state;
        }
        updatingConnections.set(true);
        try {
            BlockState result = state;
            if (block2 instanceof WallBlock) {
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                for (Direction dir2 : directions) {
                    EnumProperty<WallSide> wallProp;
                    BlockState neighborState;
                    wallProp = WallBlock.PROPERTY_BY_DIRECTION.get(dir2);
                    if (wallProp == null) continue;
                    BlockStateGetter blockStateGetter = getter;
                    BlockState blockState = neighborState = blockStateGetter != null ? blockStateGetter.get(x + dir2.getStepX(), y + dir2.getStepY(), z + dir2.getStepZ()) : null;
                    boolean connects = neighborState != null && !neighborState.isAir() ? neighborState.getBlock() instanceof WallBlock || neighborState.getBlock() instanceof CrossCollisionBlock || neighborState.getBlock() instanceof FenceGateBlock || neighborState.canOcclude() : false;
                    Object object = result.setValue((Property)wallProp, (Comparable)(connects ? WallSide.LOW : WallSide.NONE));
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                }
                Object object = result.setValue((Property)WallBlock.UP, (Comparable)Boolean.valueOf(true));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                result = (BlockState)object;
            } else {
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                for (Direction dir2 : directions) {
                    BlockState neighborState;
                    BooleanProperty prop;
                    String propName = dir2.getSerializedName();
                    Intrinsics.checkNotNullExpressionValue((Object)dir2.getSerializedName(), (String)"getSerializedName(...)");
                    Property property = result.getBlock().getStateDefinition().getProperty(propName);
                    if (!(property instanceof BooleanProperty booleanProperty)) continue;
                    prop = booleanProperty;
                    BlockStateGetter blockStateGetter = getter;
                    BlockState blockState = neighborState = blockStateGetter != null ? blockStateGetter.get(x + dir2.getStepX(), y + dir2.getStepY(), z + dir2.getStepZ()) : null;
                    boolean connects = neighborState != null && !neighborState.isAir() ? neighborState.getBlock() instanceof CrossCollisionBlock || neighborState.getBlock() instanceof FenceGateBlock || neighborState.canOcclude() || neighborState.getBlock() instanceof WallBlock : false;
                    Object object = result.setValue((Property)prop, (Comparable)Boolean.valueOf(connects));
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                }
            }
            BlockState blockState = result;
            return blockState;
        }
        finally {
            updatingConnections.set(false);
        }
    }

    @NotNull
    public final BlockState applyOriginalShape(@NotNull BlockState original, @NotNull BlockState replacement, int x, int y, int z) {
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        Intrinsics.checkNotNullParameter((Object)replacement, (String)"replacement");
        return BlockStateCodec.INSTANCE.applyOriginalShape(original, replacement, x, y, z);
    }

    public final boolean hasActiveReplacements() {
        return ReplacementCache.INSTANCE.hasActiveReplacements();
    }

    public final boolean hasAdjacentReplacement(int x, int y, int z) {
        return ReplacementCache.INSTANCE.hasAdjacentReplacement(x, y, z);
    }

    public final boolean keepBlockSizeEnabled() {
        return (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue();
    }

    public final boolean customWaterColorEnabled() {
        return (Boolean)CosmeticBlocksTab.INSTANCE.getCustomWaterColor().getValue();
    }

    public final int waterColorRgb() {
        return ((Number)CosmeticBlocksTab.INSTANCE.getWaterColor().getValue()).intValue() & 0xFFFFFF;
    }

    @JvmStatic
    public static final void onPlayerInfoUpdate(@NotNull ClientboundPlayerInfoUpdatePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        LocationTracker.onPlayerInfoUpdate(packet);
    }

    @JvmStatic
    public static final void onSetObjective(@NotNull ClientboundSetObjectivePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        LocationTracker.onSetObjective(packet);
    }

    public final void onWorldRender(@NotNull Matrix4f modelView, @NotNull Matrix4f projection, @NotNull Vec3 camPos) {
        Intrinsics.checkNotNullParameter((Object)modelView, (String)"modelView");
        Intrinsics.checkNotNullParameter((Object)projection, (String)"projection");
        Intrinsics.checkNotNullParameter((Object)camPos, (String)"camPos");
        CosmeticRenderer.INSTANCE.onWorldRender(modelView, projection, camPos);
    }

    public final boolean tryPickCosmeticAtCrosshair() {
        return EditMode.INSTANCE.tryPickCosmeticAtCrosshair();
    }

    public final boolean getEditMode() {
        return EditMode.INSTANCE.getEditMode();
    }

    @NotNull
    public final String getSelectedBlock() {
        return EditMode.INSTANCE.getSelectedBlock();
    }

    public final void setSelectedBlock(@NotNull String value) {
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        EditMode.INSTANCE.setSelectedBlock$cb_client(value);
    }

    @Nullable
    public final String getCurrentShape() {
        return EditMode.INSTANCE.getCurrentShape();
    }

    public final void setCurrentShape(@Nullable String value) {
        EditMode.INSTANCE.setCurrentShape(value);
    }

    public final void toggleEditMode() {
        EditMode.INSTANCE.toggleEditMode();
    }

    public final boolean getFillMode() {
        return EditMode.INSTANCE.getFillMode();
    }

    public final void toggleFillMode() {
        EditMode.INSTANCE.toggleFillMode();
    }

    public final void setBlock(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        EditMode.INSTANCE.setBlock(blockId);
    }

    public final void setBlockProperty(@NotNull String property, @NotNull String value) {
        Intrinsics.checkNotNullParameter((Object)property, (String)"property");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        EditMode.INSTANCE.setBlockProperty(property, value);
    }

    public final void listBlockProperties() {
        EditMode.INSTANCE.listBlockProperties();
    }

    public final boolean hasExplicitProperties() {
        return EditMode.INSTANCE.hasExplicitProperties();
    }

    public final boolean getWorldEditMode() {
        return WorldEditMode.INSTANCE.getWorldEditMode();
    }

    @Nullable
    public final BlockPos getPos1() {
        return WorldEditMode.INSTANCE.getPos1();
    }

    @Nullable
    public final BlockPos getPos2() {
        return WorldEditMode.INSTANCE.getPos2();
    }

    public final void setPos1(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        WorldEditMode.INSTANCE.setPos1(pos);
    }

    public final void setPos2(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        WorldEditMode.INSTANCE.setPos2(pos);
    }

    @NotNull
    public final String getSelectionInfo() {
        return WorldEditMode.INSTANCE.getSelectionInfo();
    }

    public final void clearSelection() {
        WorldEditMode.INSTANCE.clearSelection();
    }

    @NotNull
    public final String fillRegion(@NotNull String blockSpecs) {
        Intrinsics.checkNotNullParameter((Object)blockSpecs, (String)"blockSpecs");
        return WorldEditMode.INSTANCE.fillRegion(blockSpecs);
    }

    @NotNull
    public final String replaceRegion(@NotNull String fromBlock, @NotNull String toSpecs) {
        Intrinsics.checkNotNullParameter((Object)fromBlock, (String)"fromBlock");
        Intrinsics.checkNotNullParameter((Object)toSpecs, (String)"toSpecs");
        return WorldEditMode.INSTANCE.replaceRegion(fromBlock, toSpecs);
    }

    @NotNull
    public final String removeInRegion(@Nullable String filterBlock) {
        return WorldEditMode.INSTANCE.removeInRegion(filterBlock);
    }

    @NotNull
    public final String undoLastOperation() {
        return WorldEditMode.INSTANCE.undoLastOperation();
    }

    @Nullable
    public final List<Pair<BlockState, Integer>> parseWeightedBlocks(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        return WorldEditMode.INSTANCE.parseWeightedBlocks(input);
    }

    public final void setReplacement(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        ReplacementStore.INSTANCE.setReplacement(pos, blockId, phantom);
    }

    public static /* synthetic */ void setReplacement$default(CosmeticBlocks cosmeticBlocks, BlockPos blockPos, String string, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        cosmeticBlocks.setReplacement(blockPos, string, bl);
    }

    public final void setReplacementState(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        ReplacementStore.INSTANCE.setReplacementState(pos, state, phantom);
    }

    public static /* synthetic */ void setReplacementState$default(CosmeticBlocks cosmeticBlocks, BlockPos blockPos, BlockState blockState, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        cosmeticBlocks.setReplacementState(blockPos, blockState, bl);
    }

    public final boolean removeReplacement(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return ReplacementStore.INSTANCE.removeReplacement(pos);
    }

    public final boolean removeAt(int x, int y, int z) {
        return ReplacementStore.INSTANCE.removeAt(x, y, z);
    }

    public final int clearDimension() {
        return ReplacementStore.INSTANCE.clearDimension();
    }

    public final int clearAll() {
        return ReplacementStore.INSTANCE.clearAll();
    }

    @NotNull
    public final List<String> listReplacements() {
        return ReplacementStore.INSTANCE.listReplacements();
    }

    public final int getReplacementCount() {
        return ReplacementStore.INSTANCE.getReplacementCount();
    }

    public final void save() {
        ReplacementIO.INSTANCE.save();
    }

    public final void load() {
        ReplacementIO.INSTANCE.load();
    }

    public final boolean createConfig(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.createConfig(name);
    }

    public final boolean loadConfigByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.loadConfigByName(name);
    }

    public final boolean deleteConfig(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.deleteConfig(name);
    }

    @NotNull
    public final List<String> listConfigs() {
        return ConfigSwitcher.INSTANCE.listConfigs();
    }

    @NotNull
    public final List<String> getFavorites() {
        return Favorites.INSTANCE.getFavorites();
    }

    public final boolean addFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.addFavorite(blockId);
    }

    public final boolean removeFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.removeFavorite(blockId);
    }

    public final boolean isFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.isFavorite(blockId);
    }

    public final int clearRoom(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        return RoomStore.INSTANCE.clearRoom(roomName);
    }

    public final int clearAllRooms() {
        return RoomStore.INSTANCE.clearAllRooms();
    }

    @NotNull
    public final List<String> listRoomNames() {
        return RoomStore.INSTANCE.listRoomNames();
    }

    public final int getRoomBlockCount(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        return RoomStore.INSTANCE.getRoomBlockCount(roomName);
    }

    public final boolean isInSkyblock() {
        return LocationTracker.INSTANCE.isInSkyblock();
    }

    @NotNull
    public final String getCurrentArea() {
        return LocationTracker.INSTANCE.getCurrentArea();
    }

    public final boolean getInDungeon() {
        return LocationTracker.INSTANCE.getInDungeon();
    }

    @Nullable
    public final String getCurrentDimension() {
        return LocationTracker.INSTANCE.getCurrentDimension();
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z) {
        return this.getReplacementAt(x, y, z, false);
    }

    private static final void onEndClientTick(Minecraft minecraft) {
        boolean pressed;
        long window;
        HitResult hit;
        long now;
        long now2;
        boolean roomChanged;
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"<unused var>");
        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (!Intrinsics.areEqual((Object)dim, (Object)lastDimension)) {
            SaveScheduler.INSTANCE.flushSave$cb_client();
            if (dim != null && lastDimension != null) {
                LocationTracker.INSTANCE.resetLocationState();
            }
            lastDimension = dim;
            ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
            if (dim != null) {
                DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            }
        }
        if (DirtySections.INSTANCE.hasPending$cb_client()) {
            DirtySections.INSTANCE.drainDirtySections$cb_client();
        }
        if ((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue() != lastEnabledState || (Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() != lastOnlyInDungeonsState || (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue() != lastKeepBlockSizeState || (Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue() != lastLavaAsWaterState) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
            lastEnabledState = (Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue();
            lastOnlyInDungeonsState = (Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue();
            lastKeepBlockSizeState = (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue();
            lastLavaAsWaterState = (Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue();
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        }
        ConfigSwitcher.INSTANCE.checkConfigSwitch$cb_client();
        LocationTracker.INSTANCE.updateDungeonCheck$cb_client();
        if (LocationTracker.INSTANCE.getInDungeon() && RoomScanner.INSTANCE.isReady() && ((Boolean)CosmeticBlocksTab.INSTANCE.getRoomScanning().getValue()).booleanValue() && (roomChanged = RoomScanner.INSTANCE.tick())) {
            RoomStore.INSTANCE.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
        }
        if (SaveScheduler.INSTANCE.getNeedsSave$cb_client() && ((now2 = System.currentTimeMillis()) - SaveScheduler.INSTANCE.getLastEditTime$cb_client() >= 2000L || now2 - SaveScheduler.INSTANCE.getLastSaveTime$cb_client() >= 10000L)) {
            ReplacementIO.INSTANCE.save();
        }
        if (SaveScheduler.INSTANCE.getNeedsRoomSave$cb_client() && ((now = System.currentTimeMillis()) - SaveScheduler.INSTANCE.getLastRoomEditTime$cb_client() >= 2000L || now - SaveScheduler.INSTANCE.getLastRoomSaveTime$cb_client() >= 10000L)) {
            RoomStore.INSTANCE.saveRoomBlocksAsync$cb_client();
        }
        if ((hit = CosmeticBlocks.INSTANCE.getMc().hitResult) instanceof BlockHitResult && ((BlockHitResult)hit).getType() == HitResult.Type.BLOCK) {
            lastLookBlockPos = ((BlockHitResult)hit).getBlockPos();
        }
        if ((window = Minecraft.getInstance().getWindow().handle()) != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getEditModeKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getEditModeKeybind().getValue()).intValue()) == 1;
            if (pressed && !editModeKeyWasPressed) {
                INSTANCE.toggleEditMode();
            }
            editModeKeyWasPressed = pressed;
        } else {
            editModeKeyWasPressed = false;
        }
        if (window != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getPlaceModeKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getPlaceModeKeybind().getValue()).intValue()) == 1;
            if (pressed && !placeModeKeyWasPressed) {
                CosmeticBlocksTab.INSTANCE.getPlaceMode().setValue((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue() == false);
                ChatUtil.INSTANCE.send((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue() != false ? "Place mode \u00a7aON" : "Place mode \u00a7cOFF");
            }
            placeModeKeyWasPressed = pressed;
        } else {
            placeModeKeyWasPressed = false;
        }
        if (window != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getWorldEditKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getWorldEditKeybind().getValue()).intValue()) == 1;
            if (pressed && !worldEditKeyWasPressed) {
                WorldEditMode.INSTANCE.setWorldEditMode$cb_client(!WorldEditMode.INSTANCE.getWorldEditMode());
                ChatUtil.INSTANCE.send(WorldEditMode.INSTANCE.getWorldEditMode() ? "World edit mode \u00a7aON" : "World edit mode \u00a7cOFF");
            }
            worldEditKeyWasPressed = pressed;
        } else {
            worldEditKeyWasPressed = false;
        }
        if (WorldEditMode.INSTANCE.getWorldEditMode()) {
            WorldEditMode.INSTANCE.tickWorldEditMode$cb_client();
        }
        if (EditMode.INSTANCE.getEditMode()) {
            EditMode.INSTANCE.tickEditMode$cb_client();
        }
        Ruler.INSTANCE.tick();
        CosmeticRenderer.INSTANCE.emitGizmos();
    }

    private static final Boolean initialUpdatingConnections() {
        return false;
    }

    static {
        updatingConnections = ThreadLocal.withInitial(CosmeticBlocks::initialUpdatingConnections);
    }

    public static interface BlockStateGetter {
        @Nullable
        public BlockState get(int x, int y, int z);
    }
}

