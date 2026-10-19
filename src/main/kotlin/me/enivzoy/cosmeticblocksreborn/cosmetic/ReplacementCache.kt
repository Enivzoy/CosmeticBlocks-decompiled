package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.concurrent.ConcurrentHashMap
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

object ReplacementCache {
   internal class Data(
      val active: ConcurrentHashMap<Long, BlockState>,
      val phantom: MutableSet<Long>,
      val retexture: ConcurrentHashMap<Long, BlockState>,
      val originalShape: ConcurrentHashMap<Long, BlockState>,
      val shaped: MutableSet<Long>
   )

   internal class Bundle(val data: Data, val sections: ConcurrentHashMap<Long, MutableSet<Long>>)

   @Volatile
   private var data = Data(
      ConcurrentHashMap(),
      ConcurrentHashMap.newKeySet(),
      ConcurrentHashMap(),
      ConcurrentHashMap(),
      ConcurrentHashMap.newKeySet()
   )

   internal val activeCache: ConcurrentHashMap<Long, BlockState>
      get() = data.active

   internal val phantomPositions: MutableSet<Long>
      get() = data.phantom

   internal val retextureCache: ConcurrentHashMap<Long, BlockState>
      get() = data.retexture

   internal val originalShapeCache: ConcurrentHashMap<Long, BlockState>
      get() = data.originalShape

   internal val shapedPositions: MutableSet<Long>
      get() = data.shaped

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

      if (!SectionIndex.hasSectionAt(x, y, z)) {
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
            SectionIndex.add(packed)
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
            SectionIndex.add(packed)
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
      SectionIndex.remove(packed)
      retextureCache.remove(packed)
      shapedPositions.remove(packed)
      phantomPositions.remove(packed)
   }

   internal fun clearCacheForPos(x: Int, y: Int, z: Int) {
      val packed = BlockPos.asLong(x, y, z)
      activeCache.remove(packed)
      SectionIndex.remove(packed)
      phantomPositions.remove(packed)
      retextureCache.remove(packed)
      originalShapeCache.remove(packed)
      shapedPositions.remove(packed)
   }

   internal fun buildBundle(dimMap: Map<String, String>): Bundle {
      val active = ConcurrentHashMap<Long, BlockState>()
      val phantom = ConcurrentHashMap.newKeySet<Long>()
      val retexture = ConcurrentHashMap<Long, BlockState>()
      val shaped = ConcurrentHashMap.newKeySet<Long>()

      for ((key, raw) in dimMap) {
         val parts = key.split(",")
         if (parts.size == 3) {
            try {
               val xx = parts[0].toInt()
               val y = parts[1].toInt()
               val z = parts[2].toInt()
               val isPhantom = raw.startsWith("!")
               val strippedRaw = if (isPhantom) raw.substring(1) else raw
               val shapeSplit = strippedRaw.split("|shape=", limit = 2)
               val blockIdx = shapeSplit[0]
               val shape = shapeSplit.getOrNull(1)
               val packed = BlockPos.asLong(xx, y, z)
               if (shape != null) {
                  val shapeState = BlockStateCodec.resolveShapeState(shape)
                  val textureState = BlockStateCodec.resolveBlockState(blockIdx)
                  if (shapeState != null && textureState != null) {
                     active[packed] = shapeState
                     retexture[packed] = textureState
                     shaped.add(packed)
                     if (isPhantom) {
                        phantom.add(packed)
                     }
                  }
               } else {
                  val state = BlockStateCodec.resolveBlockState(blockIdx)
                  if (state != null) {
                     active[packed] = state
                     if (isPhantom) {
                        phantom.add(packed)
                     }
                  }
               }
            } catch (ignored: Exception) {
            }
         }
      }

      val sections = SectionIndex.build(active.keys)
      return Bundle(Data(active, phantom, retexture, ConcurrentHashMap(), shaped), sections)
   }

   internal fun swap(bundle: Bundle) {
      SectionIndex.swap(bundle.sections)
      data = bundle.data
   }

   internal fun rebuildActiveCache() {
      val dim = LocationTracker.getCurrentDimension()
      val dimMap = if (dim == null) emptyMap() else ReplacementStore.replacements[dim] ?: emptyMap()
      this.swap(this.buildBundle(dimMap))
   }

   fun hasActiveReplacements(): Boolean = !activeCache.isEmpty()

   fun hasAdjacentReplacement(x: Int, y: Int, z: Int): Boolean =
      activeCache.containsKey(BlockPos.asLong(x - 1, y, z)) ||
         activeCache.containsKey(BlockPos.asLong(x + 1, y, z)) ||
         activeCache.containsKey(BlockPos.asLong(x, y, z - 1)) ||
         activeCache.containsKey(BlockPos.asLong(x, y, z + 1))
}
