package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.concurrent.ConcurrentHashMap
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

object ReplacementCache {
   internal val activeCache = ConcurrentHashMap<Long, BlockState>()
   internal val phantomPositions: MutableSet<Long> = ConcurrentHashMap.newKeySet()
   internal val retextureCache = ConcurrentHashMap<Long, BlockState>()
   internal val originalShapeCache = ConcurrentHashMap<Long, BlockState>()
   internal val shapedPositions: MutableSet<Long> = ConcurrentHashMap.newKeySet()

   fun getReplacementAt(x: Int, y: Int, z: Int, originalAir: Boolean): BlockState? {
      if (!CosmeticBlocksTab.INSTANCE.enabled.value) {
         return null
      }

      if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && !LocationTracker.inDungeon) {
         return null
      }

      if (activeCache.isEmpty()) {
         return null
      }

      val key = BlockPos.asLong(x, y, z)
      return if (originalAir && !phantomPositions.contains(key)) null else activeCache[key]
   }

   fun getReplacementAt(x: Int, y: Int, z: Int): BlockState? = this.getReplacementAt(x, y, z, false)

   fun getReplacement(pos: BlockPos): BlockState? = this.getReplacementAt(pos.x, pos.y, pos.z, false)

   fun getRetextureAt(x: Int, y: Int, z: Int): BlockState? {
      if (!CosmeticBlocksTab.INSTANCE.enabled.value) {
         return null
      } else if (!CosmeticBlocksTab.INSTANCE.keepBlockSize.value) {
         return null
      } else if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && !LocationTracker.inDungeon) {
         return null
      } else {
         val packed = BlockPos.asLong(x, y, z)
         if (!activeCache.containsKey(packed)) {
            retextureCache.remove(packed)
            return null
         } else {
            return retextureCache[packed]
         }
      }
   }

   fun getOriginalShapeAt(x: Int, y: Int, z: Int): BlockState? {
      if (!CosmeticBlocksTab.INSTANCE.enabled.value) {
         return null
      } else if (!CosmeticBlocksTab.INSTANCE.keepBlockSize.value) {
         return null
      } else if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && !LocationTracker.inDungeon) {
         return null
      } else {
         val packed = BlockPos.asLong(x, y, z)
         if (!activeCache.containsKey(packed)) {
            originalShapeCache.remove(packed)
            return null
         } else {
            return originalShapeCache[packed]
         }
      }
   }

   internal fun applyCacheForEntry(x: Int, y: Int, z: Int, raw: String) {
      val packed = BlockPos.asLong(x, y, z)
      val phantom = raw.startsWith("!")
      val strippedRaw = if (phantom) raw.substring(1) else raw
      val shapeSplit = strippedRaw.split("|shape=", limit = 2)
      val blockIdx = shapeSplit[0]
      val shape = shapeSplit.getOrNull(1)
      if (shape != null) {
         val shapeState = BlockStateCodec.resolveShapeState(shape)
         val textureState = BlockStateCodec.resolveBlockState(blockIdx)
         if (shapeState != null && textureState != null) {
            activeCache[packed] = shapeState
            retextureCache[packed] = textureState
            shapedPositions.add(packed)
            if (phantom) {
               phantomPositions.add(packed)
            } else {
               phantomPositions.remove(packed)
            }

            return
         }
      } else {
         val state = BlockStateCodec.resolveBlockState(blockIdx)
         if (state != null) {
            activeCache[packed] = state
            shapedPositions.remove(packed)
            retextureCache.remove(packed)
            if (phantom) {
               phantomPositions.add(packed)
            } else {
               phantomPositions.remove(packed)
            }

            return
         }
      }

      activeCache.remove(packed)
      retextureCache.remove(packed)
      shapedPositions.remove(packed)
      phantomPositions.remove(packed)
   }

   internal fun clearCacheForPos(x: Int, y: Int, z: Int) {
      val packed = BlockPos.asLong(x, y, z)
      activeCache.remove(packed)
      phantomPositions.remove(packed)
      retextureCache.remove(packed)
      originalShapeCache.remove(packed)
      shapedPositions.remove(packed)
   }

   internal fun rebuildActiveCache() {
      activeCache.clear()
      phantomPositions.clear()
      retextureCache.clear()
      originalShapeCache.clear()
      shapedPositions.clear()
      val dim = LocationTracker.getCurrentDimension() ?: return
      val dimMap = ReplacementStore.replacements[dim] ?: return

      for ((key, raw) in dimMap) {
         val parts = key.split(",")
         if (parts.size == 3) {
            try {
               val xx = parts[0].toInt()
               val y = parts[1].toInt()
               val z = parts[2].toInt()
               val phantom = raw.startsWith("!")
               val strippedRaw = if (phantom) raw.substring(1) else raw
               val shapeSplit = strippedRaw.split("|shape=", limit = 2)
               val blockIdx = shapeSplit[0]
               val shape = shapeSplit.getOrNull(1)
               val packed = BlockPos.asLong(xx, y, z)
               if (shape != null) {
                  val shapeState = BlockStateCodec.resolveShapeState(shape)
                  val textureState = BlockStateCodec.resolveBlockState(blockIdx)
                  if (shapeState != null && textureState != null) {
                     activeCache[packed] = shapeState
                     retextureCache[packed] = textureState
                     shapedPositions.add(packed)
                     if (phantom) {
                        phantomPositions.add(packed)
                     }
                  }
               } else {
                  val state = BlockStateCodec.resolveBlockState(blockIdx)
                  if (state != null) {
                     activeCache[packed] = state
                     if (phantom) {
                        phantomPositions.add(packed)
                     }
                  }
               }
            } catch (ignored: Exception) {
            }
         }
      }
   }

   fun hasActiveReplacements(): Boolean = !activeCache.isEmpty()

   fun hasAdjacentReplacement(x: Int, y: Int, z: Int): Boolean =
      activeCache.containsKey(BlockPos.asLong(x - 1, y, z)) ||
         activeCache.containsKey(BlockPos.asLong(x + 1, y, z)) ||
         activeCache.containsKey(BlockPos.asLong(x, y, z - 1)) ||
         activeCache.containsKey(BlockPos.asLong(x, y, z + 1))
}
