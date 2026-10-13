/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.stream.JsonWriter
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.EditMode;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.SaveScheduler;
import com.cb.dungeon.RoomDataKt;
import com.cb.dungeon.RoomInfo;
import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001DB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\nH\u0002J\r\u0010\u001d\u001a\u00020\u0019H\u0000\u00a2\u0006\u0002\b\u001eJ\u0006\u0010\u001f\u001a\u00020\u0019J \u0010 \u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060!0!H\u0002J(\u0010\"\u001a\u00020\u00192\u001e\u0010#\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060!0!H\u0002J\u0017\u0010$\u001a\u00020\u00192\b\u0010%\u001a\u0004\u0018\u00010&H\u0000\u00a2\u0006\u0002\b'J\u0015\u0010(\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b*J\u0006\u0010+\u001a\u00020\u0019J-\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b2J-\u00103\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b4J\u001f\u00105\u001a\u0004\u0018\u00010\u00062\u0006\u0010-\u001a\u00020.2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b6J+\u00107\u001a\u00020\u00192\u0014\u0010#\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060!2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b8J-\u00109\u001a\u00020\u00192\u0006\u0010-\u001a\u00020.2\u0006\u0010:\u001a\u00020;2\u0006\u00100\u001a\u00020\u001b2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b<J\u001d\u0010=\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020.2\u0006\u00101\u001a\u00020&H\u0000\u00a2\u0006\u0002\b>J\u000e\u0010?\u001a\u00020@2\u0006\u0010)\u001a\u00020\u0006J\u0006\u0010A\u001a\u00020@J\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00060\u0016J\u000e\u0010C\u001a\u00020@2\u0006\u0010)\u001a\u00020\u0006R,\u0010\u0004\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0006X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00160\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006E"}, d2={"Lcom/cb/cosmetic/RoomStore;", "", "<init>", "()V", "roomBlocks", "", "", "getRoomBlocks$cb_client", "()Ljava/util/Map;", "roomBlocksFile", "Ljava/io/File;", "roomCacheKeys", "", "", "getRoomCacheKeys$cb_client", "()Ljava/util/Set;", "lastScannedRoom", "getLastScannedRoom$cb_client", "()Ljava/lang/String;", "setLastScannedRoom$cb_client", "(Ljava/lang/String;)V", "resolvedRoomCache", "", "Lcom/cb/cosmetic/RoomStore$ResolvedRoomBlock;", "loadRoomBlocks", "", "tryLoadRoomFile", "", "file", "saveRoomBlocksAsync", "saveRoomBlocksAsync$cb_client", "forceRoomSave", "snapshotRoomBlocks", "", "writeRoomConfig", "snapshot", "onRoomChanged", "newRoom", "Lcom/cb/dungeon/ScannedRoom;", "onRoomChanged$cb_client", "resolveRoomBlocks", "roomName", "resolveRoomBlocks$cb_client", "resolveAllRoomBlocks", "setRoomReplacement", "pos", "Lnet/minecraft/core/BlockPos;", "blockId", "phantom", "room", "setRoomReplacement$cb_client", "setRoomReplacementRaw", "setRoomReplacementRaw$cb_client", "rawAtWorld", "rawAtWorld$cb_client", "restoreRoomFill", "restoreRoomFill$cb_client", "setRoomReplacementState", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "setRoomReplacementState$cb_client", "removeRoomReplacement", "removeRoomReplacement$cb_client", "clearRoom", "", "clearAllRooms", "listRoomNames", "getRoomBlockCount", "ResolvedRoomBlock", "cb_client"})
@SourceDebugExtension(value={"SMAP\nRoomStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomStore.kt\ncom/cb/cosmetic/RoomStore\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,361:1\n410#2,7:362\n410#2,7:369\n410#2,7:376\n*S KotlinDebug\n*F\n+ 1 RoomStore.kt\ncom/cb/cosmetic/RoomStore\n*L\n237#1:362,7\n276#1:369,7\n291#1:376,7\n*E\n"})
public final class RoomStore {
    @NotNull
    public static final RoomStore INSTANCE = new RoomStore();
    @NotNull
    private static final Map<String, Map<String, String>> roomBlocks = new LinkedHashMap();
    @NotNull
    private static final File roomBlocksFile = new File("config/cb/cb_rooms.json");
    @NotNull
    private static final Set<Long> roomCacheKeys;
    @Nullable
    private static String lastScannedRoom;
    @NotNull
    private static final Map<String, List<ResolvedRoomBlock>> resolvedRoomCache;

    private RoomStore() {
    }

    @NotNull
    public final Map<String, Map<String, String>> getRoomBlocks$cb_client() {
        return roomBlocks;
    }

    @NotNull
    public final Set<Long> getRoomCacheKeys$cb_client() {
        return roomCacheKeys;
    }

    @Nullable
    public final String getLastScannedRoom$cb_client() {
        return lastScannedRoom;
    }

    public final void setLastScannedRoom$cb_client(@Nullable String string) {
        lastScannedRoom = string;
    }

    public final void loadRoomBlocks() {
        boolean loaded;
        roomBlocks.clear();
        resolvedRoomCache.clear();
        File tmpFile = new File(roomBlocksFile.getParentFile(), roomBlocksFile.getName() + ".tmp");
        boolean bl = loaded = this.tryLoadRoomFile(roomBlocksFile) || this.tryLoadRoomFile(tmpFile);
        if (!loaded && (roomBlocksFile.exists() || tmpFile.exists())) {
            System.err.println("[CB] Failed to load room blocks");
        }
    }

    private final boolean tryLoadRoomFile(File file) {
        boolean bl;
        if (!file.exists()) {
            return false;
        }
        try {
            String text = FilesKt.readText$default((File)file, null, (int)1, null);
            if (StringsKt.isBlank((CharSequence)text)) {
                return false;
            }
            JsonObject root = JsonParser.parseString((String)text).getAsJsonObject();
            for (Map.Entry entry : root.entrySet()) {
                Intrinsics.checkNotNull((Object)entry);
                String roomName = (String)entry.getKey();
                JsonElement element = (JsonElement)entry.getValue();
                JsonObject roomObj = element.getAsJsonObject();
                Map roomMap = new LinkedHashMap();
                for (Map.Entry entry2 : roomObj.entrySet()) {
                    Intrinsics.checkNotNull((Object)entry2);
                    String pos = (String)entry2.getKey();
                    JsonElement block = (JsonElement)entry2.getValue();
                    roomMap.put(pos, block.getAsString());
                }
                roomBlocks.put(roomName, roomMap);
            }
            bl = true;
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to parse room file " + file.getName() + ": " + e.getMessage());
            bl = false;
        }
        return bl;
    }

    public final void saveRoomBlocksAsync$cb_client() {
        SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
        SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
        Map<String, Map<String, String>> snapshot = this.snapshotRoomBlocks();
        SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> RoomStore.saveRoomBlocksAsync$lambda$0(snapshot));
    }

    public final void forceRoomSave() {
        SaveScheduler.INSTANCE.setNeedsRoomSave$cb_client(false);
        SaveScheduler.INSTANCE.setLastRoomSaveTime$cb_client(System.currentTimeMillis());
        Map<String, Map<String, String>> snapshot = this.snapshotRoomBlocks();
        SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> RoomStore.forceRoomSave$lambda$0(snapshot));
    }

    private final Map<String, Map<String, String>> snapshotRoomBlocks() {
        HashMap snapshot = new HashMap(roomBlocks.size());
        for (Map.Entry<String, Map<String, String>> entry : roomBlocks.entrySet()) {
            String room = entry.getKey();
            Map<String, String> map = entry.getValue();
            ((Map)snapshot).put(room, new HashMap<String, String>(map));
        }
        return snapshot;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void writeRoomConfig(Map<String, ? extends Map<String, String>> snapshot) {
        File file = roomBlocksFile.getParentFile();
        if (file != null) {
            file.mkdirs();
        }
        File tmpFile = new File(roomBlocksFile.getParentFile(), roomBlocksFile.getName() + ".tmp");
        Closeable closeable = new BufferedWriter(new FileWriter(tmpFile), 65536);
        Throwable throwable = null;
        try {
            JsonWriter jsonWriter;
            BufferedWriter bw = (BufferedWriter)closeable;
            boolean bl = false;
            Closeable closeable2 = (Closeable)new JsonWriter((Writer)bw);
            Throwable throwable2 = null;
            try {
                JsonWriter jw = (JsonWriter)closeable2;
                boolean bl2 = false;
                jw.setIndent("  ");
                jw.beginObject();
                for (Map.Entry<String, ? extends Map<String, String>> entry : snapshot.entrySet()) {
                    String room = entry.getKey();
                    Map<String, String> map = entry.getValue();
                    jw.name(room);
                    jw.beginObject();
                    for (Map.Entry<String, String> entry2 : map.entrySet()) {
                        String pos = entry2.getKey();
                        String block = entry2.getValue();
                        jw.name(pos).value(block);
                    }
                    jw.endObject();
                }
                jsonWriter = jw.endObject();
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable2, (Throwable)throwable2);
            }
            JsonWriter jsonWriter2 = jsonWriter;
        }
        catch (Throwable throwable4) {
            throwable = throwable4;
            throw throwable4;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
        if (roomBlocksFile.exists()) {
            roomBlocksFile.delete();
        }
        tmpFile.renameTo(roomBlocksFile);
    }

    public final void onRoomChanged$cb_client(@Nullable ScannedRoom newRoom) {
        HashSet<Long> dirtySections = new HashSet<Long>();
        Iterator<Long> iterator = roomCacheKeys.iterator();
        while (iterator.hasNext()) {
            long key = ((Number)iterator.next()).longValue();
            dirtySections.add(DirtySections.INSTANCE.sectionKey$cb_client(BlockPos.getX((long)key), BlockPos.getY((long)key), BlockPos.getZ((long)key)));
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(key);
        }
        roomCacheKeys.clear();
        if (newRoom != null && newRoom.getRotation() != Rotation.NONE) {
            List<ResolvedRoomBlock> resolved = resolvedRoomCache.get(newRoom.getData().getName());
            if (resolved != null) {
                Rotation rot = newRoom.getRotation();
                for (ResolvedRoomBlock entry : resolved) {
                    BlockPos absPos;
                    Intrinsics.checkNotNullExpressionValue((Object)RoomDataKt.rotateAroundNorth(entry.getRelPos(), rot).offset(newRoom.getClayPos().getX(), 0, newRoom.getClayPos().getZ()), (String)"offset(...)");
                    long packed = BlockPos.asLong((int)absPos.getX(), (int)absPos.getY(), (int)absPos.getZ());
                    if (entry.getShapeState() != null && entry.getTextureState() != null) {
                        ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, entry.getShapeState());
                        ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, BlockStateCodec.INSTANCE.rotateBlockStateFromNorth$cb_client(entry.getTextureState(), rot));
                        ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(packed);
                    } else {
                        ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, BlockStateCodec.INSTANCE.rotateBlockStateFromNorth$cb_client(entry.getState(), rot));
                    }
                    roomCacheKeys.add(packed);
                    if (entry.getPhantom()) {
                        ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed);
                    }
                    dirtySections.add(DirtySections.INSTANCE.sectionKey$cb_client(absPos.getX(), absPos.getY(), absPos.getZ()));
                }
            }
            lastScannedRoom = newRoom.getData().getName();
        } else {
            lastScannedRoom = null;
        }
        DirtySections.INSTANCE.queueDirtySections$cb_client((Set<Long>)dirtySections);
    }

    public final void resolveRoomBlocks$cb_client(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        Map<String, String> rawMap = roomBlocks.get(roomName);
        if (rawMap == null || rawMap.isEmpty()) {
            resolvedRoomCache.remove(roomName);
            return;
        }
        ArrayList<ResolvedRoomBlock> list = new ArrayList<ResolvedRoomBlock>(rawMap.size());
        for (Map.Entry<String, String> entry : rawMap.entrySet()) {
            String relKey = entry.getKey();
            String raw = entry.getValue();
            String[] stringArray = new String[]{","};
            List parts = StringsKt.split$default((CharSequence)relKey, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            if (parts.size() != 3) continue;
            try {
                BlockState state;
                String string;
                BlockPos relPos = new BlockPos(Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2)));
                boolean phantom = StringsKt.startsWith$default((String)raw, (String)"!", (boolean)false, (int)2, null);
                if (phantom) {
                    String string2 = raw.substring(1);
                    string = string2;
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
                } else {
                    string = raw;
                }
                String cleanRaw = string;
                String[] stringArray2 = new String[]{"|shape="};
                List shapeSplit = StringsKt.split$default((CharSequence)cleanRaw, (String[])stringArray2, (boolean)false, (int)2, (int)2, null);
                String blockStr = (String)shapeSplit.get(0);
                String shape = (String)CollectionsKt.getOrNull((List)shapeSplit, (int)1);
                if (shape != null) {
                    BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
                    BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockStr);
                    if (shapeState == null || textureState == null) continue;
                    list.add(new ResolvedRoomBlock(relPos, shapeState, phantom, shapeState, textureState));
                    continue;
                }
                if (BlockStateCodec.INSTANCE.resolveBlockState(blockStr) == null) continue;
                list.add(new ResolvedRoomBlock(relPos, state, phantom, null, null));
            }
            catch (Exception exception) {}
        }
        resolvedRoomCache.put(roomName, list);
    }

    public final void resolveAllRoomBlocks() {
        resolvedRoomCache.clear();
        for (String roomName : roomBlocks.keySet()) {
            this.resolveRoomBlocks$cb_client(roomName);
        }
    }

    public final void setRoomReplacement$cb_client(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom, @NotNull ScannedRoom room) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        this.setRoomReplacementRaw$cb_client(pos, blockId, phantom, room);
        this.resolveRoomBlocks$cb_client(room.getData().getName());
        SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
    }

    /*
     * WARNING - void declaration
     */
    public final void setRoomReplacementRaw$cb_client(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom, @NotNull ScannedRoom room) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        BlockPos blockPos = pos.subtract((Vec3i)room.getClayPos().atY(0));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"subtract(...)");
        BlockPos relPos = RoomDataKt.rotateToNorth(blockPos, room.getRotation());
        String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
        Map<String, Map<String, String>> map = roomBlocks;
        String key$iv = room.getData().getName();
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(key$iv);
        if (value$iv == null) {
            boolean bl = false;
            Map answer$iv = new LinkedHashMap();
            $this$getOrPut$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        Map roomMap = (Map)object;
        String rotatedId = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(blockId, room.getRotation());
        String storeId = EditMode.INSTANCE.getCurrentShape() != null ? rotatedId + "|shape=" + EditMode.INSTANCE.getCurrentShape() : rotatedId;
        roomMap.put(relKey, phantom ? "!" + storeId : storeId);
        long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
        if (EditMode.INSTANCE.getCurrentShape() != null) {
            String string = EditMode.INSTANCE.getCurrentShape();
            Intrinsics.checkNotNull((Object)string);
            BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(string);
            BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
            if (shapeState != null && textureState != null) {
                ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, shapeState);
                ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, textureState);
                ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(packed);
                roomCacheKeys.add(packed);
            }
        } else {
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
            if (state != null) {
                ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, state);
                roomCacheKeys.add(packed);
            }
        }
        boolean bl = phantom ? ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed) : ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
        DirtySections.INSTANCE.markDirty$cb_client(pos);
    }

    @Nullable
    public final String rawAtWorld$cb_client(@NotNull BlockPos pos, @NotNull ScannedRoom room) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        BlockPos blockPos = pos.subtract((Vec3i)room.getClayPos().atY(0));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"subtract(...)");
        BlockPos relPos = RoomDataKt.rotateToNorth(blockPos, room.getRotation());
        Map<String, String> map = roomBlocks.get(room.getData().getName());
        return map != null ? map.get(relPos.getX() + "," + relPos.getY() + "," + relPos.getZ()) : null;
    }

    /*
     * WARNING - void declaration
     */
    public final void restoreRoomFill$cb_client(@NotNull Map<String, String> snapshot, @NotNull ScannedRoom room) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter(snapshot, (String)"snapshot");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        Map<String, Map<String, String>> map = roomBlocks;
        String entry = room.getData().getName();
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(entry);
        if (value$iv == null) {
            boolean bl = false;
            Map answer$iv = new LinkedHashMap();
            $this$getOrPut$iv.put(entry, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        Map roomMap = (Map)object;
        for (Map.Entry entry2 : snapshot.entrySet()) {
            String relKey = (String)entry2.getKey();
            String prior = (String)entry2.getValue();
            if (prior == null) {
                roomMap.remove(relKey);
                continue;
            }
            roomMap.put(relKey, prior);
        }
        if (roomMap.isEmpty()) {
            roomBlocks.remove(room.getData().getName());
        }
        this.resolveRoomBlocks$cb_client(room.getData().getName());
        Object object2 = RoomScanner.INSTANCE.getCurrentRoom();
        if (Intrinsics.areEqual((Object)(object2 != null && (object2 = ((ScannedRoom)object2).getData()) != null ? ((RoomInfo)object2).getName() : null), (Object)room.getData().getName())) {
            this.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
        }
        SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
    }

    /*
     * WARNING - void declaration
     */
    public final void setRoomReplacementState$cb_client(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom, @NotNull ScannedRoom room) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        BlockPos blockPos = pos.subtract((Vec3i)room.getClayPos().atY(0));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"subtract(...)");
        BlockPos relPos = RoomDataKt.rotateToNorth(blockPos, room.getRotation());
        String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
        Map<String, Map<String, String>> map = roomBlocks;
        String key$iv = room.getData().getName();
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(key$iv);
        if (value$iv == null) {
            boolean bl = false;
            Map answer$iv = new LinkedHashMap();
            $this$getOrPut$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        Map roomMap = (Map)object;
        String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
        String rotatedSerialized = BlockStateCodec.INSTANCE.rotateStateToNorth$cb_client(serialized, room.getRotation());
        roomMap.put(relKey, phantom ? "!" + rotatedSerialized : rotatedSerialized);
        long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
        ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, state);
        roomCacheKeys.add(packed);
        boolean bl = phantom ? ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed) : ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
        DirtySections.INSTANCE.markDirty$cb_client(pos);
        this.resolveRoomBlocks$cb_client(room.getData().getName());
        SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
    }

    public final boolean removeRoomReplacement$cb_client(@NotNull BlockPos pos, @NotNull ScannedRoom room) {
        boolean removed;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)room, (String)"room");
        BlockPos blockPos = pos.subtract((Vec3i)room.getClayPos().atY(0));
        Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"subtract(...)");
        BlockPos relPos = RoomDataKt.rotateToNorth(blockPos, room.getRotation());
        String relKey = relPos.getX() + "," + relPos.getY() + "," + relPos.getZ();
        Map<String, String> map = roomBlocks.get(room.getData().getName());
        if (map == null) {
            return false;
        }
        Map<String, String> roomMap = map;
        boolean bl = removed = roomMap.remove(relKey) != null;
        if (removed) {
            if (roomMap.isEmpty()) {
                roomBlocks.remove(room.getData().getName());
            }
            long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(packed);
            roomCacheKeys.remove(packed);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
            DirtySections.INSTANCE.markDirtyImmediate$cb_client(pos);
            this.resolveRoomBlocks$cb_client(room.getData().getName());
            SaveScheduler.INSTANCE.scheduleRoomSave$cb_client();
        }
        return removed;
    }

    public final int clearRoom(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        Map<String, String> map = roomBlocks.remove(roomName);
        if (map == null) {
            return 0;
        }
        Map<String, String> roomMap = map;
        int count = roomMap.size();
        resolvedRoomCache.remove(roomName);
        Iterator iterator = CollectionsKt.toList((Iterable)roomCacheKeys).iterator();
        while (iterator.hasNext()) {
            long key = ((Number)iterator.next()).longValue();
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
        }
        roomCacheKeys.clear();
        ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
        ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
        DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        this.forceRoomSave();
        return count;
    }

    public final int clearAllRooms() {
        int count = 0;
        Iterator<Map.Entry<String, Map<String, String>>> iterator = roomBlocks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map<String, String> map = iterator.next().getValue();
            count += map.size();
        }
        roomBlocks.clear();
        resolvedRoomCache.clear();
        iterator = CollectionsKt.toList((Iterable)roomCacheKeys).iterator();
        while (iterator.hasNext()) {
            long key = ((Number)((Object)iterator.next())).longValue();
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
        }
        roomCacheKeys.clear();
        DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        this.forceRoomSave();
        return count;
    }

    @NotNull
    public final List<String> listRoomNames() {
        return CollectionsKt.sorted((Iterable)roomBlocks.keySet());
    }

    public final int getRoomBlockCount(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        Map<String, String> map = roomBlocks.get(roomName);
        return map != null ? map.size() : 0;
    }

    private static final void saveRoomBlocksAsync$lambda$0(Map $snapshot) {
        try {
            INSTANCE.writeRoomConfig($snapshot);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static final void forceRoomSave$lambda$0(Map $snapshot) {
        try {
            INSTANCE.writeRoomConfig($snapshot);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static {
        ConcurrentHashMap.KeySetView keySetView = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetView, (String)"newKeySet(...)");
        roomCacheKeys = keySetView;
        resolvedRoomCache = new LinkedHashMap();
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J?\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001J\u0014\u0010\u001a\u001a\u00020\u00072\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u001c\u001a\u00020\u001dH\u00d6\u0081\u0004J\n\u0010\u001e\u001a\u00020\u001fH\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006 "}, d2={"Lcom/cb/cosmetic/RoomStore$ResolvedRoomBlock;", "", "relPos", "Lnet/minecraft/core/BlockPos;", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "phantom", "", "shapeState", "textureState", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;ZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V", "getRelPos", "()Lnet/minecraft/core/BlockPos;", "getState", "()Lnet/minecraft/world/level/block/state/BlockState;", "getPhantom", "()Z", "getShapeState", "getTextureState", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "cb_client"})
    private static final class ResolvedRoomBlock {
        @NotNull
        private final BlockPos relPos;
        @NotNull
        private final BlockState state;
        private final boolean phantom;
        @Nullable
        private final BlockState shapeState;
        @Nullable
        private final BlockState textureState;

        public ResolvedRoomBlock(@NotNull BlockPos relPos, @NotNull BlockState state, boolean phantom, @Nullable BlockState shapeState, @Nullable BlockState textureState) {
            Intrinsics.checkNotNullParameter((Object)relPos, (String)"relPos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            this.relPos = relPos;
            this.state = state;
            this.phantom = phantom;
            this.shapeState = shapeState;
            this.textureState = textureState;
        }

        @NotNull
        public final BlockPos getRelPos() {
            return this.relPos;
        }

        @NotNull
        public final BlockState getState() {
            return this.state;
        }

        public final boolean getPhantom() {
            return this.phantom;
        }

        @Nullable
        public final BlockState getShapeState() {
            return this.shapeState;
        }

        @Nullable
        public final BlockState getTextureState() {
            return this.textureState;
        }

        @NotNull
        public final BlockPos component1() {
            return this.relPos;
        }

        @NotNull
        public final BlockState component2() {
            return this.state;
        }

        public final boolean component3() {
            return this.phantom;
        }

        @Nullable
        public final BlockState component4() {
            return this.shapeState;
        }

        @Nullable
        public final BlockState component5() {
            return this.textureState;
        }

        @NotNull
        public final ResolvedRoomBlock copy(@NotNull BlockPos relPos, @NotNull BlockState state, boolean phantom, @Nullable BlockState shapeState, @Nullable BlockState textureState) {
            Intrinsics.checkNotNullParameter((Object)relPos, (String)"relPos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            return new ResolvedRoomBlock(relPos, state, phantom, shapeState, textureState);
        }

        public static /* synthetic */ ResolvedRoomBlock copy$default(ResolvedRoomBlock resolvedRoomBlock, BlockPos blockPos, BlockState blockState, boolean bl, BlockState blockState2, BlockState blockState3, int n, Object object) {
            if ((n & 1) != 0) {
                blockPos = resolvedRoomBlock.relPos;
            }
            if ((n & 2) != 0) {
                blockState = resolvedRoomBlock.state;
            }
            if ((n & 4) != 0) {
                bl = resolvedRoomBlock.phantom;
            }
            if ((n & 8) != 0) {
                blockState2 = resolvedRoomBlock.shapeState;
            }
            if ((n & 0x10) != 0) {
                blockState3 = resolvedRoomBlock.textureState;
            }
            return resolvedRoomBlock.copy(blockPos, blockState, bl, blockState2, blockState3);
        }

        @NotNull
        public String toString() {
            return "ResolvedRoomBlock(relPos=" + this.relPos + ", state=" + this.state + ", phantom=" + this.phantom + ", shapeState=" + this.shapeState + ", textureState=" + this.textureState + ")";
        }

        public int hashCode() {
            int result = this.relPos.hashCode();
            result = result * 31 + this.state.hashCode();
            result = result * 31 + Boolean.hashCode(this.phantom);
            result = result * 31 + (this.shapeState == null ? 0 : this.shapeState.hashCode());
            result = result * 31 + (this.textureState == null ? 0 : this.textureState.hashCode());
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ResolvedRoomBlock)) {
                return false;
            }
            ResolvedRoomBlock resolvedRoomBlock = (ResolvedRoomBlock)other;
            if (!Intrinsics.areEqual((Object)this.relPos, (Object)resolvedRoomBlock.relPos)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.state, (Object)resolvedRoomBlock.state)) {
                return false;
            }
            if (this.phantom != resolvedRoomBlock.phantom) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.shapeState, (Object)resolvedRoomBlock.shapeState)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.textureState, (Object)resolvedRoomBlock.textureState);
        }
    }
}


