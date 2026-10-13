/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.LocationTracker;
import com.cb.cosmetic.ReplacementStore;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J,\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001c\u001a\u00020\u001dJ \u0010\u001e\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016J \u0010\u001f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016J-\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0000\u00a2\u0006\u0002\b$J%\u0010%\u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016H\u0000\u00a2\u0006\u0002\b&J\r\u0010'\u001a\u00020!H\u0000\u00a2\u0006\u0002\b(J\u0006\u0010)\u001a\u00020\u001aJ\u001e\u0010*\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016R \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r\u00a8\u0006+"}, d2={"Lcom/cb/cosmetic/ReplacementCache;", "", "<init>", "()V", "activeCache", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lnet/minecraft/world/level/block/state/BlockState;", "getActiveCache$cb_client", "()Ljava/util/concurrent/ConcurrentHashMap;", "phantomPositions", "", "getPhantomPositions$cb_client", "()Ljava/util/Set;", "retextureCache", "getRetextureCache$cb_client", "originalShapeCache", "getOriginalShapeCache$cb_client", "shapedPositions", "getShapedPositions$cb_client", "getReplacementAt", "x", "", "y", "z", "originalAir", "", "getReplacement", "pos", "Lnet/minecraft/core/BlockPos;", "getRetextureAt", "getOriginalShapeAt", "applyCacheForEntry", "", "raw", "", "applyCacheForEntry$cb_client", "clearCacheForPos", "clearCacheForPos$cb_client", "rebuildActiveCache", "rebuildActiveCache$cb_client", "hasActiveReplacements", "hasAdjacentReplacement", "cb_client"})
public final class ReplacementCache {
    @NotNull
    public static final ReplacementCache INSTANCE = new ReplacementCache();
    @NotNull
    private static final ConcurrentHashMap<Long, BlockState> activeCache = new ConcurrentHashMap();
    @NotNull
    private static final Set<Long> phantomPositions;
    @NotNull
    private static final ConcurrentHashMap<Long, BlockState> retextureCache;
    @NotNull
    private static final ConcurrentHashMap<Long, BlockState> originalShapeCache;
    @NotNull
    private static final Set<Long> shapedPositions;

    private ReplacementCache() {
    }

    @NotNull
    public final ConcurrentHashMap<Long, BlockState> getActiveCache$cb_client() {
        return activeCache;
    }

    @NotNull
    public final Set<Long> getPhantomPositions$cb_client() {
        return phantomPositions;
    }

    @NotNull
    public final ConcurrentHashMap<Long, BlockState> getRetextureCache$cb_client() {
        return retextureCache;
    }

    @NotNull
    public final ConcurrentHashMap<Long, BlockState> getOriginalShapeCache$cb_client() {
        return originalShapeCache;
    }

    @NotNull
    public final Set<Long> getShapedPositions$cb_client() {
        return shapedPositions;
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z, boolean originalAir) {
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue()).booleanValue()) {
            return null;
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !LocationTracker.INSTANCE.getInDungeon()) {
            return null;
        }
        if (activeCache.isEmpty()) {
            return null;
        }
        long key = BlockPos.asLong((int)x, (int)y, (int)z);
        if (originalAir && !phantomPositions.contains(key)) {
            return null;
        }
        return activeCache.get(key);
    }

    public static /* synthetic */ BlockState getReplacementAt$default(ReplacementCache replacementCache, int n, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 8) != 0) {
            bl = false;
        }
        return replacementCache.getReplacementAt(n, n2, n3, bl);
    }

    @Nullable
    public final BlockState getReplacement(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return ReplacementCache.getReplacementAt$default(this, pos.getX(), pos.getY(), pos.getZ(), false, 8, null);
    }

    @Nullable
    public final BlockState getRetextureAt(int x, int y, int z) {
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue()).booleanValue()) {
            return null;
        }
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue()).booleanValue()) {
            return null;
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !LocationTracker.INSTANCE.getInDungeon()) {
            return null;
        }
        long packed = BlockPos.asLong((int)x, (int)y, (int)z);
        if (!activeCache.containsKey(packed)) {
            retextureCache.remove(packed);
            return null;
        }
        return retextureCache.get(packed);
    }

    @Nullable
    public final BlockState getOriginalShapeAt(int x, int y, int z) {
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue()).booleanValue()) {
            return null;
        }
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue()).booleanValue()) {
            return null;
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !LocationTracker.INSTANCE.getInDungeon()) {
            return null;
        }
        long packed = BlockPos.asLong((int)x, (int)y, (int)z);
        if (!activeCache.containsKey(packed)) {
            originalShapeCache.remove(packed);
            return null;
        }
        return originalShapeCache.get(packed);
    }

    public final void applyCacheForEntry$cb_client(int x, int y, int z, @NotNull String raw) {
        String string;
        Intrinsics.checkNotNullParameter((Object)raw, (String)"raw");
        long packed = BlockPos.asLong((int)x, (int)y, (int)z);
        boolean phantom = StringsKt.startsWith$default((String)raw, (String)"!", (boolean)false, (int)2, null);
        if (phantom) {
            String string2 = raw.substring(1);
            string = string2;
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
        } else {
            string = raw;
        }
        String cleanRaw = string;
        String[] stringArray = new String[]{"|shape="};
        List shapeSplit = StringsKt.split$default((CharSequence)cleanRaw, (String[])stringArray, (boolean)false, (int)2, (int)2, null);
        String blockId = (String)shapeSplit.get(0);
        String shape = (String)CollectionsKt.getOrNull((List)shapeSplit, (int)1);
        if (shape != null) {
            BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
            BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
            if (shapeState != null && textureState != null) {
                ((Map)activeCache).put(packed, shapeState);
                ((Map)retextureCache).put(packed, textureState);
                shapedPositions.add(packed);
                boolean bl = phantom ? phantomPositions.add(packed) : phantomPositions.remove(packed);
                return;
            }
        } else {
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
            if (state != null) {
                ((Map)activeCache).put(packed, state);
                shapedPositions.remove(packed);
                retextureCache.remove(packed);
                boolean bl = phantom ? phantomPositions.add(packed) : phantomPositions.remove(packed);
                return;
            }
        }
        activeCache.remove(packed);
        retextureCache.remove(packed);
        shapedPositions.remove(packed);
        phantomPositions.remove(packed);
    }

    public final void clearCacheForPos$cb_client(int x, int y, int z) {
        long packed = BlockPos.asLong((int)x, (int)y, (int)z);
        activeCache.remove(packed);
        phantomPositions.remove(packed);
        retextureCache.remove(packed);
        originalShapeCache.remove(packed);
        shapedPositions.remove(packed);
    }

    public final void rebuildActiveCache$cb_client() {
        activeCache.clear();
        phantomPositions.clear();
        retextureCache.clear();
        originalShapeCache.clear();
        shapedPositions.clear();
        String string = LocationTracker.INSTANCE.getCurrentDimension();
        if (string == null) {
            return;
        }
        String dim = string;
        Map<String, String> map = ReplacementStore.INSTANCE.getReplacements$cb_client().get(dim);
        if (map == null) {
            return;
        }
        Map<String, String> dimMap = map;
        for (Map.Entry<String, String> entry : dimMap.entrySet()) {
            String key = entry.getKey();
            String raw = entry.getValue();
            String[] stringArray = new String[]{","};
            List parts = StringsKt.split$default((CharSequence)key, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
            if (parts.size() != 3) continue;
            try {
                String string2;
                int x = Integer.parseInt((String)parts.get(0));
                int y = Integer.parseInt((String)parts.get(1));
                int z = Integer.parseInt((String)parts.get(2));
                boolean phantom = StringsKt.startsWith$default((String)raw, (String)"!", (boolean)false, (int)2, null);
                if (phantom) {
                    String string3 = raw.substring(1);
                    string2 = string3;
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
                } else {
                    string2 = raw;
                }
                String cleanRaw = string2;
                String[] stringArray2 = new String[]{"|shape="};
                List shapeSplit = StringsKt.split$default((CharSequence)cleanRaw, (String[])stringArray2, (boolean)false, (int)2, (int)2, null);
                String blockId = (String)shapeSplit.get(0);
                String shape = (String)CollectionsKt.getOrNull((List)shapeSplit, (int)1);
                long packed = BlockPos.asLong((int)x, (int)y, (int)z);
                if (shape != null) {
                    BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
                    BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
                    if (shapeState == null || textureState == null) continue;
                    ((Map)activeCache).put(packed, shapeState);
                    ((Map)retextureCache).put(packed, textureState);
                    shapedPositions.add(packed);
                    if (!phantom) continue;
                    phantomPositions.add(packed);
                    continue;
                }
                BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockId);
                if (state == null) continue;
                ((Map)activeCache).put(packed, state);
                if (!phantom) continue;
                phantomPositions.add(packed);
            }
            catch (Exception exception) {}
        }
    }

    public final boolean hasActiveReplacements() {
        return !((Map)activeCache).isEmpty();
    }

    public final boolean hasAdjacentReplacement(int x, int y, int z) {
        return activeCache.containsKey(BlockPos.asLong((int)(x - 1), (int)y, (int)z)) || activeCache.containsKey(BlockPos.asLong((int)(x + 1), (int)y, (int)z)) || activeCache.containsKey(BlockPos.asLong((int)x, (int)y, (int)(z - 1))) || activeCache.containsKey(BlockPos.asLong((int)x, (int)y, (int)(z + 1)));
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z) {
        return ReplacementCache.getReplacementAt$default(this, x, y, z, false, 8, null);
    }

    static {
        ConcurrentHashMap.KeySetView keySetView = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetView, (String)"newKeySet(...)");
        phantomPositions = keySetView;
        retextureCache = new ConcurrentHashMap();
        originalShapeCache = new ConcurrentHashMap();
        ConcurrentHashMap.KeySetView keySetView2 = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetView2, (String)"newKeySet(...)");
        shapedPositions = keySetView2;
    }
}


