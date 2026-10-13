/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.EditMode;
import com.cb.cosmetic.LocationTracker;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.RoomStore;
import com.cb.cosmetic.SaveScheduler;
import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.ScannedRoom;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0017\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\nH\u0000\u00a2\u0006\u0002\b\u000bJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u0016J \u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013J\u001e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001dJ\u0006\u0010 \u001a\u00020\u001dJ\u0006\u0010!\u001a\u00020\u001dJ\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0#J\u0006\u0010$\u001a\u00020\u001dJ%\u0010%\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0&0&H\u0000\u00a2\u0006\u0002\b'R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R,\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\r0\rX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006("}, d2={"Lcom/cb/cosmetic/ReplacementStore;", "", "<init>", "()V", "configDir", "Ljava/io/File;", "getConfigDir$cb_client", "()Ljava/io/File;", "getConfigFile", "name", "", "getConfigFile$cb_client", "replacements", "", "getReplacements$cb_client", "()Ljava/util/Map;", "setReplacement", "", "pos", "Lnet/minecraft/core/BlockPos;", "blockId", "phantom", "", "setReplacementState", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "removeReplacement", "removeAt", "x", "", "y", "z", "clearDimension", "clearAll", "listReplacements", "", "getReplacementCount", "snapshotReplacements", "", "snapshotReplacements$cb_client", "cb_client"})
@SourceDebugExtension(value={"SMAP\nReplacementStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReplacementStore.kt\ncom/cb/cosmetic/ReplacementStore\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,183:1\n410#2,7:184\n410#2,7:191\n1586#3:198\n1661#3,3:199\n1586#3:202\n1661#3,3:203\n*S KotlinDebug\n*F\n+ 1 ReplacementStore.kt\ncom/cb/cosmetic/ReplacementStore\n*L\n30#1:184,7\n66#1:191,7\n154#1:198\n154#1:199,3\n160#1:202\n160#1:203,3\n*E\n"})
public final class ReplacementStore {
    @NotNull
    public static final ReplacementStore INSTANCE = new ReplacementStore();
    @NotNull
    private static final File configDir = new File("config/cb/cb");
    @NotNull
    private static final Map<String, Map<String, String>> replacements = new LinkedHashMap();

    private ReplacementStore() {
    }

    @NotNull
    public final File getConfigDir$cb_client() {
        return configDir;
    }

    @NotNull
    public final File getConfigFile$cb_client(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return new File(configDir, name + ".json");
    }

    public static /* synthetic */ File getConfigFile$cb_client$default(ReplacementStore replacementStore, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = (String)CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue();
        }
        return replacementStore.getConfigFile$cb_client(string);
    }

    @NotNull
    public final Map<String, Map<String, String>> getReplacements$cb_client() {
        return replacements;
    }

    /*
     * WARNING - void declaration
     */
    public final void setReplacement(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
            RoomStore.INSTANCE.setRoomReplacement$cb_client(pos, BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId), phantom, room);
            return;
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return;
        }
        String dim = string;
        Map<String, Map<String, String>> map = replacements;
        String key$iv = dim;
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
        Map dimMap = (Map)object;
        String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
        String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
        String storeId = EditMode.INSTANCE.getCurrentShape() != null ? normalized + "|shape=" + EditMode.INSTANCE.getCurrentShape() : normalized;
        dimMap.put(key, phantom ? "!" + storeId : storeId);
        long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
        if (EditMode.INSTANCE.getCurrentShape() != null) {
            String string2 = EditMode.INSTANCE.getCurrentShape();
            Intrinsics.checkNotNull((Object)string2);
            BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(string2);
            BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
            if (shapeState != null && textureState != null) {
                ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, shapeState);
                ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, textureState);
                ReplacementCache.INSTANCE.getShapedPositions$cb_client().add(packed);
            }
        } else {
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
            if (state != null) {
                ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, state);
            }
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
        }
        boolean bl = phantom ? ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed) : ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
        DirtySections.INSTANCE.markDirty$cb_client(pos);
        SaveScheduler.INSTANCE.scheduleSave$cb_client();
    }

    public static /* synthetic */ void setReplacement$default(ReplacementStore replacementStore, BlockPos blockPos, String string, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        replacementStore.setReplacement(blockPos, string, bl);
    }

    /*
     * WARNING - void declaration
     */
    public final void setReplacementState(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
            RoomStore.INSTANCE.setRoomReplacementState$cb_client(pos, state, phantom, room);
            return;
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return;
        }
        String dim = string;
        Map<String, Map<String, String>> map = replacements;
        String key$iv = dim;
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
        Map dimMap = (Map)object;
        String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
        String serialized = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
        dimMap.put(key, phantom ? "!" + serialized : serialized);
        long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
        ((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).put(packed, state);
        boolean bl = phantom ? ReplacementCache.INSTANCE.getPhantomPositions$cb_client().add(packed) : ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
        DirtySections.INSTANCE.markDirty$cb_client(pos);
        SaveScheduler.INSTANCE.scheduleSave$cb_client();
    }

    public static /* synthetic */ void setReplacementState$default(ReplacementStore replacementStore, BlockPos blockPos, BlockState blockState, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        replacementStore.setReplacementState(blockPos, blockState, bl);
    }

    public final boolean removeReplacement(@NotNull BlockPos pos) {
        boolean removed;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
            return RoomStore.INSTANCE.removeRoomReplacement$cb_client(pos, room);
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return false;
        }
        String dim = string;
        Map<String, String> map = replacements.get(dim);
        if (map == null) {
            return false;
        }
        Map<String, String> dimMap = map;
        String key = pos.getX() + "," + pos.getY() + "," + pos.getZ();
        boolean bl = removed = dimMap.remove(key) != null;
        if (removed) {
            if (dimMap.isEmpty()) {
                replacements.remove(dim);
            }
            long packed = BlockPos.asLong((int)pos.getX(), (int)pos.getY(), (int)pos.getZ());
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(packed);
            DirtySections.INSTANCE.markDirtyImmediate$cb_client(pos);
            SaveScheduler.INSTANCE.scheduleSave$cb_client();
        }
        return removed;
    }

    public final boolean removeAt(int x, int y, int z) {
        return this.removeReplacement(new BlockPos(x, y, z));
    }

    public final int clearDimension() {
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null && room.getRotation() != Rotation.NONE) {
            return RoomStore.INSTANCE.clearRoom(room.getData().getName());
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return 0;
        }
        String dim = string;
        Map<String, String> map = replacements.remove(dim);
        if (map == null) {
            return 0;
        }
        Map<String, String> dimMap = map;
        int count = dimMap.size();
        for (String key : dimMap.keySet()) {
            String[] stringArray;
            List parts = StringsKt.split$default((CharSequence)key, (String[])(stringArray = new String[]{","}), (boolean)false, (int)0, (int)6, null);
            if (parts.size() != 3) continue;
            try {
                DirtySections.INSTANCE.markDirty$cb_client(new BlockPos(Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2))));
            }
            catch (NumberFormatException numberFormatException) {}
        }
        ReplacementCache.INSTANCE.getActiveCache$cb_client().clear();
        ReplacementCache.INSTANCE.getPhantomPositions$cb_client().clear();
        ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
        ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
        ReplacementIO.INSTANCE.save();
        return count;
    }

    public final int clearAll() {
        int count = 0;
        Iterator<Map.Entry<String, Map<String, String>>> iterator = replacements.entrySet().iterator();
        while (iterator.hasNext()) {
            Map<String, String> dimMap = iterator.next().getValue();
            count += dimMap.size();
            for (String key : dimMap.keySet()) {
                String[] stringArray = new String[]{","};
                List parts = StringsKt.split$default((CharSequence)key, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
                if (parts.size() != 3) continue;
                try {
                    DirtySections.INSTANCE.markDirty$cb_client(new BlockPos(Integer.parseInt((String)parts.get(0)), Integer.parseInt((String)parts.get(1)), Integer.parseInt((String)parts.get(2))));
                }
                catch (NumberFormatException numberFormatException) {}
            }
        }
        replacements.clear();
        ReplacementCache.INSTANCE.getActiveCache$cb_client().clear();
        ReplacementCache.INSTANCE.getPhantomPositions$cb_client().clear();
        ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
        ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
        ReplacementIO.INSTANCE.save();
        return count;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<String> listReplacements() {
        void $this$mapTo$iv$iv;
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null) {
            void $this$mapTo$iv$iv2;
            Map<String, String> map = RoomStore.INSTANCE.getRoomBlocks$cb_client().get(room.getData().getName());
            if (map == null) {
                return CollectionsKt.emptyList();
            }
            Map<String, String> roomMap = map;
            Iterable $this$map$iv = roomMap.entrySet();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                Map.Entry entry = (Map.Entry)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                String pos = (String)entry.getKey();
                String block = (String)entry.getValue();
                collection.add("[" + room.getData().getName() + "] " + pos + " -> " + StringsKt.removePrefix((String)StringsKt.removePrefix((String)block, (CharSequence)"!"), (CharSequence)"minecraft:"));
            }
            return (List)destination$iv$iv;
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return CollectionsKt.emptyList();
        }
        String dim = string;
        Map<String, String> map = replacements.get(dim);
        if (map == null) {
            return CollectionsKt.emptyList();
        }
        Map<String, String> dimMap = map;
        Iterable $this$map$iv = dimMap.entrySet();
        boolean $i$f$map = false;
        Iterable destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Map.Entry entry = (Map.Entry)item$iv$iv;
            Collection collection = destination$iv$iv2;
            boolean bl = false;
            String pos = (String)entry.getKey();
            String block = (String)entry.getValue();
            collection.add(pos + " -> " + StringsKt.removePrefix((String)StringsKt.removePrefix((String)block, (CharSequence)"!"), (CharSequence)"minecraft:"));
        }
        return (List)destination$iv$iv2;
    }

    public final int getReplacementCount() {
        ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
        if (LocationTracker.INSTANCE.getInDungeon() && room != null) {
            Map<String, String> map = RoomStore.INSTANCE.getRoomBlocks$cb_client().get(room.getData().getName());
            return map != null ? map.size() : 0;
        }
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return 0;
        }
        String dim = string;
        Map<String, String> map = replacements.get(dim);
        return map != null ? map.size() : 0;
    }

    @NotNull
    public final Map<String, Map<String, String>> snapshotReplacements$cb_client() {
        HashMap snapshot = new HashMap(replacements.size() * 2);
        for (Map.Entry<String, Map<String, String>> entry : replacements.entrySet()) {
            String dim = entry.getKey();
            Map<String, String> dimMap = entry.getValue();
            HashMap<String, String> copy = new HashMap<String, String>(dimMap.size() * 2);
            copy.putAll(dimMap);
            ((Map)snapshot).put(dim, copy);
        }
        return snapshot;
    }
}


