package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J,\u0010\u0014\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u001c\u001a\u00020\u001dJ \u0010\u001e\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016J \u0010\u001f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016J-\u0010 \u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0000¢\u0006\u0002\b$J%\u0010%\u001a\u00020!2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016H\u0000¢\u0006\u0002\b&J\r\u0010'\u001a\u00020!H\u0000¢\u0006\u0002\b(J\u0006\u0010)\u001a\u00020\u001aJ\u001e\u0010*\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016R \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r¨\u0006+",
   d2 = {
         "Lcom/cb/cosmetic/ReplacementCache;",
         "",
         "<init>",
         "()V",
         "activeCache",
         "Ljava/util/concurrent/ConcurrentHashMap;",
         "",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "getActiveCache$cb_client",
         "()Ljava/util/concurrent/ConcurrentHashMap;",
         "phantomPositions",
         "",
         "getPhantomPositions$cb_client",
         "()Ljava/util/Set;",
         "retextureCache",
         "getRetextureCache$cb_client",
         "originalShapeCache",
         "getOriginalShapeCache$cb_client",
         "shapedPositions",
         "getShapedPositions$cb_client",
         "getReplacementAt",
         "x",
         "",
         "y",
         "z",
         "originalAir",
         "",
         "getReplacement",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "getRetextureAt",
         "getOriginalShapeAt",
         "applyCacheForEntry",
         "",
         "raw",
         "",
         "applyCacheForEntry$cb_client",
         "clearCacheForPos",
         "clearCacheForPos$cb_client",
         "rebuildActiveCache",
         "rebuildActiveCache$cb_client",
         "hasActiveReplacements",
         "hasAdjacentReplacement",
         "cb_client"
   }
)
public final class ReplacementCache {
   @NotNull
   public static final ReplacementCache INSTANCE = new ReplacementCache();
   @NotNull
   private static final ConcurrentHashMap<Long, BlockState> activeCache = new ConcurrentHashMap<>();
   @NotNull
   private static final Set<Long> phantomPositions;
   @NotNull
   private static final ConcurrentHashMap<Long, BlockState> retextureCache = new ConcurrentHashMap<>();
   @NotNull
   private static final ConcurrentHashMap<Long, BlockState> originalShapeCache = new ConcurrentHashMap<>();
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
      if (!CosmeticBlocksTab.INSTANCE.getEnabled().getValue()) {
         return null;
      }

      if (CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() && !LocationTracker.INSTANCE.getInDungeon()) {
         return null;
      }

      if (activeCache.isEmpty()) {
         return null;
      }

      long key = BlockPos.asLong(x, y, z);
      return originalAir && !phantomPositions.contains(key) ? null : activeCache.get(key);
   }

   @Nullable
   public final BlockState getReplacement(@NotNull BlockPos pos) {
      Intrinsics.checkNotNullParameter(pos, "pos");
      return this.getReplacementAt(pos.getX(), pos.getY(), pos.getZ(), false);
   }

   @Nullable
   public final BlockState getRetextureAt(int x, int y, int z) {
      if (!CosmeticBlocksTab.INSTANCE.getEnabled().getValue()) {
         return null;
      } else if (!CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue()) {
         return null;
      } else if (CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() && !LocationTracker.INSTANCE.getInDungeon()) {
         return null;
      } else {
         long packed = BlockPos.asLong(x, y, z);
         if (!activeCache.containsKey(packed)) {
            retextureCache.remove(packed);
            return null;
         } else {
            return retextureCache.get(packed);
         }
      }
   }

   @Nullable
   public final BlockState getOriginalShapeAt(int x, int y, int z) {
      if (!CosmeticBlocksTab.INSTANCE.getEnabled().getValue()) {
         return null;
      } else if (!CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue()) {
         return null;
      } else if (CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() && !LocationTracker.INSTANCE.getInDungeon()) {
         return null;
      } else {
         long packed = BlockPos.asLong(x, y, z);
         if (!activeCache.containsKey(packed)) {
            originalShapeCache.remove(packed);
            return null;
         } else {
            return originalShapeCache.get(packed);
         }
      }
   }

   public final void applyCacheForEntry$cb_client(int x, int y, int z, @NotNull String raw) {
      Intrinsics.checkNotNullParameter(raw, "raw");
      long packed = BlockPos.asLong(x, y, z);
      boolean phantom = StringsKt.startsWith(raw, "!", false);
      String var10000;
      if (phantom) {
         var10000 = raw.substring(1);
         Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
      } else {
         var10000 = raw;
      }

      String cleanRaw = var10000;
      var10000 = cleanRaw;
      String[] blockId = new String[]{"|shape="};
      List<String> shapeSplit = StringsKt.split(var10000, blockId, false, 2);
      String blockIdx = (String)shapeSplit.get(0);
      String shape = CollectionsKt.getOrNull(shapeSplit, 1);
      if (shape != null) {
         BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
         BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockIdx);
         if (shapeState != null && textureState != null) {
            activeCache.put(packed, shapeState);
            retextureCache.put(packed, textureState);
            shapedPositions.add(packed);
            if (phantom) {
               phantomPositions.add(packed);
            } else {
               phantomPositions.remove(packed);
            }

            return;
         }
      } else {
         BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockIdx);
         if (state != null) {
            activeCache.put(packed, state);
            shapedPositions.remove(packed);
            retextureCache.remove(packed);
            if (phantom) {
               phantomPositions.add(packed);
            } else {
               phantomPositions.remove(packed);
            }

            return;
         }
      }

      activeCache.remove(packed);
      retextureCache.remove(packed);
      shapedPositions.remove(packed);
      phantomPositions.remove(packed);
   }

   public final void clearCacheForPos$cb_client(int x, int y, int z) {
      long packed = BlockPos.asLong(x, y, z);
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
      String var10000 = LocationTracker.INSTANCE.getCurrentDimension();
      if (var10000 != null) {
         String dim = var10000;
         Map<String, String> var24 = ReplacementStore.INSTANCE.getReplacements$cb_client().get(dim);
         if (var24 != null) {
            Map<String, String> dimMap = var24;

            for (Entry var4 : dimMap.entrySet()) {
               String key = (String)var4.getKey();
               String raw = (String)var4.getValue();
               CharSequence var25 = key;
               String[] x = new String[]{","};
               List parts = StringsKt.split(var25, x, false, 0);
               if (parts.size() == 3) {
                  try {
                     int xx = Integer.parseInt((String)parts.get(0));
                     int y = Integer.parseInt((String)parts.get(1));
                     int z = Integer.parseInt((String)parts.get(2));
                     boolean phantom = StringsKt.startsWith(raw, "!", false);
                     String var26;
                     if (phantom) {
                        var26 = raw.substring(1);
                        Intrinsics.checkNotNullExpressionValue(var26, "substring(...)");
                     } else {
                        var26 = raw;
                     }

                     String cleanRaw = var26;
                     var26 = cleanRaw;
                     String[] blockId = new String[]{"|shape="};
                     List<String> shapeSplit = StringsKt.split(var26, blockId, false, 2);
                     String blockIdx = (String)shapeSplit.get(0);
                     String shape = CollectionsKt.getOrNull(shapeSplit, 1);
                     long packed = BlockPos.asLong(xx, y, z);
                     if (shape != null) {
                        BlockState shapeState = BlockStateCodec.INSTANCE.resolveShapeState$cb_client(shape);
                        BlockState textureState = BlockStateCodec.INSTANCE.resolveBlockState(blockIdx);
                        if (shapeState != null && textureState != null) {
                           activeCache.put(packed, shapeState);
                           retextureCache.put(packed, textureState);
                           shapedPositions.add(packed);
                           if (phantom) {
                              phantomPositions.add(packed);
                           }
                        }
                     } else {
                        BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(blockIdx);
                        if (state != null) {
                           activeCache.put(packed, state);
                           if (phantom) {
                              phantomPositions.add(packed);
                           }
                        }
                     }
                  } catch (Exception var20) {
                  }
               }
            }
         }
      }
   }

   public final boolean hasActiveReplacements() {
      return !activeCache.isEmpty();
   }

   public final boolean hasAdjacentReplacement(int x, int y, int z) {
      return activeCache.containsKey(BlockPos.asLong(x - 1, y, z))
         || activeCache.containsKey(BlockPos.asLong(x + 1, y, z))
         || activeCache.containsKey(BlockPos.asLong(x, y, z - 1))
         || activeCache.containsKey(BlockPos.asLong(x, y, z + 1));
   }

   @JvmOverloads
   @Nullable
   public final BlockState getReplacementAt(int x, int y, int z) {
      return this.getReplacementAt(x, y, z, false);
   }

   static {
      KeySetView var10000 = ConcurrentHashMap.newKeySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "newKeySet(...)");
      phantomPositions = var10000;
      var10000 = ConcurrentHashMap.newKeySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "newKeySet(...)");
      shapedPositions = var10000;
   }
}
