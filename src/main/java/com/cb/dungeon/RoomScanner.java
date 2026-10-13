/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.TextStreamsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedRange
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.text.Charsets
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.LevelChunk
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.dungeon;

import com.cb.dungeon.RoomInfo;
import com.cb.dungeon.RoomShape;
import com.cb.dungeon.RoomType;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import com.cb.dungeon.Vec2i;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001@B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0006\u0010\u001f\u001a\u00020\u001eJ\b\u0010 \u001a\u00020\u001eH\u0002J\u0006\u0010!\u001a\u00020\u0015J\u0016\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005J\u001a\u0010%\u001a\u0004\u0018\u00010\u00172\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020(H\u0002J \u0010)\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-H\u0002J(\u0010.\u001a\u00020\u00052\u0006\u0010*\u001a\u00020\u00052\u0006\u0010+\u001a\u00020\u00052\u0006\u0010/\u001a\u00020\u00052\u0006\u0010,\u001a\u00020-H\u0002JT\u00100\u001a\b\u0012\u0004\u0012\u00020\u001c012\u0006\u0010&\u001a\u00020\u001c2\f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010/\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(2\u000e\b\u0002\u00103\u001a\b\u0012\u0004\u0012\u00020\u001c012\u000e\b\u0002\u00104\u001a\b\u0012\u0004\u0012\u00020\u001c01H\u0002J \u00105\u001a\u00020\u001e2\u0006\u00106\u001a\u00020\u00172\u0006\u0010/\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(H\u0002J\u0018\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u0002092\u0006\u0010'\u001a\u00020(H\u0002J\u0006\u0010:\u001a\u00020\u0015J\u0018\u0010;\u001a\u0004\u0018\u00010\u00172\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010'\u001a\u00020(J \u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010>\u001a\u00020\u00052\u0006\u0010?\u001a\u00020\u00052\u0006\u0010'\u001a\u00020(R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2={"Lcom/cb/dungeon/RoomScanner;", "", "<init>", "()V", "START", "", "ROOM_SIZE_SHIFT", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "horizontals", "", "Lnet/minecraft/core/Direction;", "mutableBlockPos", "Lnet/minecraft/core/BlockPos$MutableBlockPos;", "roomDatabase", "Lcom/cb/dungeon/RoomInfo;", "coreToRoom", "", "loaded", "", "value", "Lcom/cb/dungeon/ScannedRoom;", "currentRoom", "getCurrentRoom", "()Lcom/cb/dungeon/ScannedRoom;", "lastRoomCenter", "Lcom/cb/dungeon/Vec2i;", "init", "", "unload", "loadRoomDatabase", "tick", "getRoomCenter", "posX", "posZ", "scanRoom", "center", "level", "Lnet/minecraft/client/multiplayer/ClientLevel;", "getTopLayerOfRoom", "x", "z", "chunk", "Lnet/minecraft/world/level/chunk/LevelChunk;", "getCoreAtHeight", "roomHeight", "findComponentsRecursive", "", "cores", "visited", "tiles", "findRotation", "room", "validateClay", "pos", "Lnet/minecraft/core/BlockPos;", "isReady", "scanAt", "detectDoorType", "", "worldX", "worldZ", "RoomJsonEntry", "cb_client"})
@SourceDebugExtension(value={"SMAP\nRoomScanner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomScanner.kt\ncom/cb/dungeon/RoomScanner\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,320:1\n1#2:321\n1586#3:322\n1661#3,3:323\n1391#3:326\n1480#3,2:327\n1586#3:329\n1661#3,3:330\n1482#3,3:333\n*S KotlinDebug\n*F\n+ 1 RoomScanner.kt\ncom/cb/dungeon/RoomScanner\n*L\n56#1:322\n56#1:323,3\n66#1:326\n66#1:327,2\n67#1:329\n67#1:330,3\n66#1:333,3\n*E\n"})
public final class RoomScanner {
    @NotNull
    public static final RoomScanner INSTANCE = new RoomScanner();
    private static final int START = -185;
    private static final int ROOM_SIZE_SHIFT = 5;
    @NotNull
    private static final List<Direction> horizontals;
    @NotNull
    private static final BlockPos.MutableBlockPos mutableBlockPos;
    @NotNull
    private static List<RoomInfo> roomDatabase;
    @NotNull
    private static Map<Integer, RoomInfo> coreToRoom;
    private static boolean loaded;
    @Nullable
    private static ScannedRoom currentRoom;
    @Nullable
    private static Vec2i lastRoomCenter;

    private RoomScanner() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    @Nullable
    public final ScannedRoom getCurrentRoom() {
        return currentRoom;
    }

    public final void init() {
        if (!loaded) {
            this.loadRoomDatabase();
            loaded = true;
        }
    }

    public final void unload() {
        currentRoom = null;
        lastRoomCenter = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private final void loadRoomDatabase() {
        try {
            void $this$flatMapTo$iv$iv;
            Iterable $this$mapTo$iv$iv;
            Object it;
            InputStream stream;
            InputStream inputStream = RoomScanner.class.getResourceAsStream("/assets/cb/rooms.json");
            if (inputStream == null) {
                return;
            }
            Closeable closeable = stream = inputStream;
            Object object = Charsets.UTF_8;
            Reader reader = new InputStreamReader((InputStream)closeable, (Charset)object);
            int n = 8192;
            closeable = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, n);
            object = null;
            try {
                it = (BufferedReader)closeable;
                boolean bl = false;
                it = TextStreamsKt.readText((Reader)((Reader)it));
            }
            catch (Throwable bl) {
                object = bl;
                throw bl;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)object);
            }
            Object json = it;
            Type type2 = new TypeToken<List<? extends RoomJsonEntry>>(){}.getType();
            Object object2 = new Gson().fromJson((String)json, type2);
            Intrinsics.checkNotNullExpressionValue((Object)object2, (String)"fromJson(...)");
            List entries = (List)object2;
            Iterable $this$map$iv = entries;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void entry;
                RoomJsonEntry roomJsonEntry = (RoomJsonEntry)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new RoomInfo(entry.getName(), RoomType.Companion.fromString(entry.getType()), entry.getCores(), entry.getSecrets(), RoomShape.Companion.fromString(entry.getShape())));
            }
            roomDatabase = (List)destination$iv$iv;
            Iterable $this$flatMap$iv = roomDatabase;
            boolean $i$f$flatMap = false;
            $this$mapTo$iv$iv = $this$flatMap$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                void $this$mapTo$iv$iv2;
                RoomInfo room = (RoomInfo)element$iv$iv;
                boolean bl = false;
                Iterable $this$map$iv2 = room.getCores();
                boolean $i$f$map2 = false;
                Iterable iterable2 = $this$map$iv2;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
                boolean $i$f$mapTo2 = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                    void core;
                    int n2 = ((Number)item$iv$iv).intValue();
                    Collection collection = destination$iv$iv2;
                    boolean bl2 = false;
                    collection.add(TuplesKt.to((Object)((int)core), (Object)room));
                }
                Iterable list$iv$iv = (List)destination$iv$iv2;
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            coreToRoom = MapsKt.toMap((Iterable)((List)destination$iv$iv));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean tick() {
        LocalPlayer localPlayer = this.getMc().player;
        if (localPlayer == null) {
            return false;
        }
        LocalPlayer player = localPlayer;
        ClientLevel clientLevel = this.getMc().level;
        if (clientLevel == null) {
            return false;
        }
        ClientLevel level = clientLevel;
        Vec2i center = this.getRoomCenter(player.blockPosition().getX(), player.blockPosition().getZ());
        ScannedRoom current = currentRoom;
        if (current != null && current.getComponents().contains(center)) {
            return false;
        }
        if (Intrinsics.areEqual((Object)center, (Object)lastRoomCenter)) {
            return false;
        }
        lastRoomCenter = center;
        ScannedRoom room = this.scanRoom(center, level);
        if (room != null && room.getRotation() != Rotation.NONE) {
            boolean changed = current == null || !Intrinsics.areEqual((Object)current.getData().getName(), (Object)room.getData().getName()) || !current.getComponents().containsAll((Collection)room.getComponents());
            currentRoom = room;
            return changed;
        }
        return false;
    }

    @NotNull
    public final Vec2i getRoomCenter(int posX, int posZ) {
        int roomX = posX - -185 + 16 >> 5;
        int roomZ = posZ - -185 + 16 >> 5;
        return new Vec2i((roomX << 5) + -185, (roomZ << 5) + -185);
    }

    private final ScannedRoom scanRoom(Vec2i center, ClientLevel level) {
        LevelChunk levelChunk;
        try {
            levelChunk = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
        }
        catch (Exception exception) {
            return null;
        }
        LevelChunk levelChunk2 = levelChunk;
        Intrinsics.checkNotNull((Object)levelChunk2);
        LevelChunk chunk = levelChunk2;
        int roomHeight = this.getTopLayerOfRoom(center.getX(), center.getZ(), chunk);
        if (roomHeight <= 0) {
            return null;
        }
        int core = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeight, chunk);
        RoomInfo roomInfo = coreToRoom.get(core);
        if (roomInfo == null) {
            return null;
        }
        RoomInfo roomInfo2 = roomInfo;
        Set components = RoomScanner.findComponentsRecursive$default(this, center, roomInfo2.getCores(), roomHeight, level, null, null, 48, null);
        ScannedRoom room = new ScannedRoom(roomInfo2, null, null, components, 6, null);
        this.findRotation(room, roomHeight, level);
        return room;
    }

    private final int getTopLayerOfRoom(int x, int z, LevelChunk chunk) {
        for (int y = 140; 59 < y; --y) {
            BlockState state;
            mutableBlockPos.set(x, y, z);
            Intrinsics.checkNotNullExpressionValue((Object)chunk.getBlockState((BlockPos)mutableBlockPos), (String)"getBlockState(...)");
            if (state.isAir()) continue;
            return Intrinsics.areEqual((Object)state.getBlock(), (Object)Blocks.GOLD_BLOCK) ? y - 1 : y;
        }
        return 0;
    }

    private final int getCoreAtHeight(int x, int z, int roomHeight, LevelChunk chunk) {
        int n;
        StringBuilder sb = new StringBuilder(150);
        int clampedHeight = RangesKt.coerceIn((int)roomHeight, (ClosedRange)((ClosedRange)new IntRange(11, 140)));
        int n2 = 0;
        int n3 = 140 - clampedHeight;
        char[] cArray = new char[n3];
        StringBuilder stringBuilder = sb;
        while (n2 < n3) {
            n = n2++;
            cArray[n] = 48;
        }
        stringBuilder.append(cArray);
        int bedrock = 0;
        for (int y = clampedHeight; 11 < y; --y) {
            Block block;
            mutableBlockPos.set(x, y, z);
            Intrinsics.checkNotNullExpressionValue((Object)chunk.getBlockState((BlockPos)mutableBlockPos).getBlock(), (String)"getBlock(...)");
            if (Intrinsics.areEqual((Object)block, (Object)Blocks.AIR) && bedrock >= 2 && y < 69) {
                n = 0;
                int n4 = y - 11;
                char[] cArray2 = new char[n4];
                stringBuilder = sb;
                while (n < n4) {
                    int n5 = n++;
                    cArray2[n5] = 48;
                }
                stringBuilder.append(cArray2);
                break;
            }
            if (Intrinsics.areEqual((Object)block, (Object)Blocks.BEDROCK)) {
                ++bedrock;
            } else {
                bedrock = 0;
                if (Intrinsics.areEqual((Object)block, (Object)Blocks.OAK_PLANKS) || Intrinsics.areEqual((Object)block, (Object)Blocks.TRAPPED_CHEST) || Intrinsics.areEqual((Object)block, (Object)Blocks.CHEST)) continue;
            }
            sb.append(block);
        }
        return sb.toString().hashCode();
    }

    private final Set<Vec2i> findComponentsRecursive(Vec2i center, List<Integer> cores, int roomHeight, ClientLevel level, Set<Vec2i> visited, Set<Vec2i> tiles) {
        LevelChunk levelChunk;
        if (visited.contains(center)) {
            return tiles;
        }
        visited.add(center);
        try {
            levelChunk = level.getChunk(center.getX() >> 4, center.getZ() >> 4);
        }
        catch (Exception exception) {
            return tiles;
        }
        LevelChunk levelChunk2 = levelChunk;
        Intrinsics.checkNotNull((Object)levelChunk2);
        LevelChunk chunk = levelChunk2;
        int core = this.getCoreAtHeight(center.getX(), center.getZ(), roomHeight, chunk);
        if (!cores.contains(core)) {
            return tiles;
        }
        tiles.add(center);
        for (Direction dir : horizontals) {
            int nx = center.getX() + ((dir.getAxis() == Direction.Axis.X ? dir.getStepX() : 0) << 5);
            int nz = center.getZ() + ((dir.getAxis() == Direction.Axis.Z ? dir.getStepZ() : 0) << 5);
            this.findComponentsRecursive(new Vec2i(nx, nz), cores, roomHeight, level, visited, tiles);
        }
        return tiles;
    }

    static /* synthetic */ Set findComponentsRecursive$default(RoomScanner roomScanner, Vec2i vec2i, List list, int n, ClientLevel clientLevel, Set set, Set set2, int n2, Object object) {
        if ((n2 & 0x10) != 0) {
            set = new LinkedHashSet();
        }
        if ((n2 & 0x20) != 0) {
            set2 = new LinkedHashSet();
        }
        return roomScanner.findComponentsRecursive(vec2i, list, n, clientLevel, set, set2);
    }

    private final void findRotation(ScannedRoom room, int roomHeight, ClientLevel level) {
        if (Intrinsics.areEqual((Object)room.getData().getName(), (Object)"Fairy")) {
            Vec2i vec2i = (Vec2i)CollectionsKt.firstOrNull((Iterable)room.getComponents());
            if (vec2i != null) {
                Vec2i it = vec2i;
                boolean bl = false;
                room.setClayPos(new BlockPos(it.getX() - 15, roomHeight, it.getZ() - 15));
                room.setRotation(Rotation.SOUTH);
            }
            return;
        }
        for (Rotation rotation : Rotation.getEntries()) {
            if (rotation == Rotation.NONE) continue;
            for (Vec2i component : room.getComponents()) {
                BlockState blockState;
                BlockPos testPos = new BlockPos(component.getX() + rotation.getX(), roomHeight, component.getZ() + rotation.getZ());
                try {
                    blockState = level.getBlockState(testPos);
                }
                catch (Exception exception) {
                    continue;
                }
                BlockState blockState2 = blockState;
                Intrinsics.checkNotNull((Object)blockState2);
                BlockState state = blockState2;
                if (!Intrinsics.areEqual((Object)state.getBlock(), (Object)Blocks.BLUE_TERRACOTTA) || room.getComponents().size() != 1 && !this.validateClay(testPos, level)) continue;
                room.setClayPos(testPos);
                room.setRotation(rotation);
                return;
            }
        }
    }

    private final boolean validateClay(BlockPos pos, ClientLevel level) {
        for (Direction dir : horizontals) {
            Block block;
            Intrinsics.checkNotNullExpressionValue((Object)pos.offset(dir.getAxis() == Direction.Axis.X ? dir.getStepX() : 0, 0, dir.getAxis() == Direction.Axis.Z ? dir.getStepZ() : 0), (String)"offset(...)");
            try {
                BlockPos neighbor;
                block = level.getBlockState(neighbor).getBlock();
            }
            catch (Exception exception) {
                return false;
            }
            Block block2 = block;
            Intrinsics.checkNotNull((Object)block2);
            Block block3 = block2;
            if (Intrinsics.areEqual((Object)block3, (Object)Blocks.AIR) || Intrinsics.areEqual((Object)block3, (Object)Blocks.BLUE_TERRACOTTA)) continue;
            return false;
        }
        return true;
    }

    public final boolean isReady() {
        return !coreToRoom.isEmpty();
    }

    @Nullable
    public final ScannedRoom scanAt(@NotNull Vec2i center, @NotNull ClientLevel level) {
        Intrinsics.checkNotNullParameter((Object)center, (String)"center");
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        return this.scanRoom(center, level);
    }

    @Nullable
    public final String detectDoorType(int worldX, int worldZ, @NotNull ClientLevel level) {
        BlockState block;
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)level, (String)"level");
        int height = 0;
        for (int y = 140; 11 < y; --y) {
            BlockState blockState2;
            mutableBlockPos.set(worldX, y, worldZ);
            try {
                blockState2 = level.getBlockState((BlockPos)mutableBlockPos);
            }
            catch (Exception exception) {
                return null;
            }
            blockState = blockState2;
            Intrinsics.checkNotNull((Object)blockState);
            BlockState state = blockState;
            if (state.isAir()) continue;
            height = y;
            break;
        }
        if (!(73 <= height ? height < 83 : false)) {
            return null;
        }
        mutableBlockPos.set(worldX, 69, worldZ);
        try {
            blockState = level.getBlockState((BlockPos)mutableBlockPos).getBlock();
        }
        catch (Exception exception) {
            return null;
        }
        BlockState blockState3 = blockState;
        Intrinsics.checkNotNull((Object)blockState3);
        blockState3 = block = blockState3;
        return Intrinsics.areEqual((Object)blockState3, (Object)Blocks.COAL_BLOCK) ? "wither" : (Intrinsics.areEqual((Object)blockState3, (Object)Blocks.RED_TERRACOTTA) ? "blood" : (Intrinsics.areEqual((Object)blockState3, (Object)Blocks.AIR) ? "normal" : null));
    }

    static {
        Object[] objectArray = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        horizontals = CollectionsKt.listOf((Object[])objectArray);
        mutableBlockPos = new BlockPos.MutableBlockPos();
        roomDatabase = CollectionsKt.emptyList();
        coreToRoom = MapsKt.emptyMap();
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0082\b\u0018\u00002\u00020\u0001BS\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003JU\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u0003H\u00c6\u0001J\u0014\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010#\u001a\u00020\u0007H\u00d6\u0081\u0004J\n\u0010$\u001a\u00020\u0003H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006%"}, d2={"Lcom/cb/dungeon/RoomScanner$RoomJsonEntry;", "", "name", "", "type", "cores", "", "", "secrets", "crypts", "trappedChests", "shape", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;IIILjava/lang/String;)V", "getName", "()Ljava/lang/String;", "getType", "getCores", "()Ljava/util/List;", "getSecrets", "()I", "getCrypts", "getTrappedChests", "getShape", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "cb_client"})
    private static final class RoomJsonEntry {
        @NotNull
        private final String name;
        @NotNull
        private final String type;
        @NotNull
        private final List<Integer> cores;
        private final int secrets;
        private final int crypts;
        private final int trappedChests;
        @NotNull
        private final String shape;

        public RoomJsonEntry(@NotNull String name, @NotNull String type2, @NotNull List<Integer> cores, int secrets, int crypts, int trappedChests, @NotNull String shape) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter((Object)type2, (String)"type");
            Intrinsics.checkNotNullParameter(cores, (String)"cores");
            Intrinsics.checkNotNullParameter((Object)shape, (String)"shape");
            this.name = name;
            this.type = type2;
            this.cores = cores;
            this.secrets = secrets;
            this.crypts = crypts;
            this.trappedChests = trappedChests;
            this.shape = shape;
        }

        public /* synthetic */ RoomJsonEntry(String string, String string2, List list, int n, int n2, int n3, String string3, int n4, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n4 & 1) != 0) {
                string = "";
            }
            if ((n4 & 2) != 0) {
                string2 = "NORMAL";
            }
            if ((n4 & 4) != 0) {
                list = CollectionsKt.emptyList();
            }
            if ((n4 & 8) != 0) {
                n = 0;
            }
            if ((n4 & 0x10) != 0) {
                n2 = 0;
            }
            if ((n4 & 0x20) != 0) {
                n3 = 0;
            }
            if ((n4 & 0x40) != 0) {
                string3 = "1x1";
            }
            this(string, string2, list, n, n2, n3, string3);
        }

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final String getType() {
            return this.type;
        }

        @NotNull
        public final List<Integer> getCores() {
            return this.cores;
        }

        public final int getSecrets() {
            return this.secrets;
        }

        public final int getCrypts() {
            return this.crypts;
        }

        public final int getTrappedChests() {
            return this.trappedChests;
        }

        @NotNull
        public final String getShape() {
            return this.shape;
        }

        @NotNull
        public final String component1() {
            return this.name;
        }

        @NotNull
        public final String component2() {
            return this.type;
        }

        @NotNull
        public final List<Integer> component3() {
            return this.cores;
        }

        public final int component4() {
            return this.secrets;
        }

        public final int component5() {
            return this.crypts;
        }

        public final int component6() {
            return this.trappedChests;
        }

        @NotNull
        public final String component7() {
            return this.shape;
        }

        @NotNull
        public final RoomJsonEntry copy(@NotNull String name, @NotNull String type2, @NotNull List<Integer> cores, int secrets, int crypts, int trappedChests, @NotNull String shape) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter((Object)type2, (String)"type");
            Intrinsics.checkNotNullParameter(cores, (String)"cores");
            Intrinsics.checkNotNullParameter((Object)shape, (String)"shape");
            return new RoomJsonEntry(name, type2, cores, secrets, crypts, trappedChests, shape);
        }

        public static /* synthetic */ RoomJsonEntry copy$default(RoomJsonEntry roomJsonEntry, String string, String string2, List list, int n, int n2, int n3, String string3, int n4, Object object) {
            if ((n4 & 1) != 0) {
                string = roomJsonEntry.name;
            }
            if ((n4 & 2) != 0) {
                string2 = roomJsonEntry.type;
            }
            if ((n4 & 4) != 0) {
                list = roomJsonEntry.cores;
            }
            if ((n4 & 8) != 0) {
                n = roomJsonEntry.secrets;
            }
            if ((n4 & 0x10) != 0) {
                n2 = roomJsonEntry.crypts;
            }
            if ((n4 & 0x20) != 0) {
                n3 = roomJsonEntry.trappedChests;
            }
            if ((n4 & 0x40) != 0) {
                string3 = roomJsonEntry.shape;
            }
            return roomJsonEntry.copy(string, string2, list, n, n2, n3, string3);
        }

        @NotNull
        public String toString() {
            return "RoomJsonEntry(name=" + this.name + ", type=" + this.type + ", cores=" + this.cores + ", secrets=" + this.secrets + ", crypts=" + this.crypts + ", trappedChests=" + this.trappedChests + ", shape=" + this.shape + ")";
        }

        public int hashCode() {
            int result = this.name.hashCode();
            result = result * 31 + this.type.hashCode();
            result = result * 31 + ((Object)this.cores).hashCode();
            result = result * 31 + Integer.hashCode(this.secrets);
            result = result * 31 + Integer.hashCode(this.crypts);
            result = result * 31 + Integer.hashCode(this.trappedChests);
            result = result * 31 + this.shape.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RoomJsonEntry)) {
                return false;
            }
            RoomJsonEntry roomJsonEntry = (RoomJsonEntry)other;
            if (!Intrinsics.areEqual((Object)this.name, (Object)roomJsonEntry.name)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.type, (Object)roomJsonEntry.type)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.cores, roomJsonEntry.cores)) {
                return false;
            }
            if (this.secrets != roomJsonEntry.secrets) {
                return false;
            }
            if (this.crypts != roomJsonEntry.crypts) {
                return false;
            }
            if (this.trappedChests != roomJsonEntry.trappedChests) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.shape, (Object)roomJsonEntry.shape);
        }

        public RoomJsonEntry() {
            this(null, null, null, 0, 0, 0, null, 127, null);
        }
    }
}


