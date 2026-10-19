package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

object GlobalRemap : RemapStore {
   internal val remaps = LinkedHashMap<String, MutableList<RemapTarget>>()
   private val file = File("config/cb/cb_global_remaps.json")
   private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

   @Volatile
   private var resolved: Map<Block, Resolved> = emptyMap()

   fun remapFor(block: Block, x: Int, y: Int, z: Int): BlockState? {
      if (!GlobalCosmeticsTab.INSTANCE.getGlobalRemapEnabled().value) {
         return null
      }

      if (LocationTracker.inDungeon && ConfigRemap.INSTANCE.isActiveForActiveConfig()) {
         return null
      }

      val r = resolved[block] ?: return null
      if (r.total <= 0) {
         return null
      }

      if (r.states.size == 1) {
         return r.states[0]
      }

      val h = Math.floorMod(x * 73856093 xor y * 19349663 xor z * 83492791, r.total)
      var idx = 0

      while (idx < r.cumWeights.size && h >= r.cumWeights[idx]) {
         idx++
      }

      return r.states[idx.coerceIn(0, r.states.size - 1)]
   }

   fun hasRemaps(): Boolean = resolved.isNotEmpty()

   internal fun rebuild() {
      val map = HashMap<Block, Resolved>()

      for ((sourceId, targets) in remaps) {
         val sourceState = BlockStateCodec.resolveBlockState(BlockStateCodec.normalizeBlockId(sourceId)) ?: continue
         val sourceBlock = sourceState.block ?: continue

         val stateList = ArrayList<BlockState>(targets.size)
         val cumWeights = ArrayList<Int>(targets.size)
         var running = 0

         for (t in targets) {
            if (t.weight > 0) {
               val state = BlockStateCodec.resolveBlockState(BlockStateCodec.normalizeBlockId(t.id))
               if (state != null) {
                  running += t.weight
                  stateList.add(state)
                  cumWeights.add(running)
               }
            }
         }

         if (stateList.isNotEmpty()) {
            map[sourceBlock] = Resolved(stateList.toTypedArray(), cumWeights.toIntArray(), running)
         }
      }

      resolved = map
      val mc = Minecraft.getInstance()
      val level = mc.level
      if (level != null) {
         val center = mc.player?.blockPosition() ?: BlockPos.ZERO
         val dist = mc.options.getEffectiveRenderDistance() * 16 + 16
         level.setSectionRangeDirty(
            center.x - dist, level.minY, center.z - dist, center.x + dist, level.maxY, center.z + dist
         )
      }
   }

   override fun sources(): List<String> = remaps.keys.toList()

   override fun targetsOf(source: String): List<RemapTarget> = remaps[source]?.toList() ?: emptyList()

   fun hasSource(source: String): Boolean = remaps.containsKey(BlockStateCodec.normalizeBlockId(source))

   override fun addTarget(source: String, targetId: String, weight: Int) {
      val src = BlockStateCodec.normalizeBlockId(source)
      val tgt = BlockStateCodec.normalizeBlockId(targetId)
      val list = remaps.getOrPut(src) { mutableListOf() }
      val existing = list.firstOrNull { it.id == tgt }
      if (existing != null) {
         existing.weight = existing.weight + weight
      } else {
         list.add(RemapTarget(tgt, weight.coerceAtLeast(1)))
      }

      this.rebuild()
      this.save()
   }

   override fun setWeight(source: String, index: Int, weight: Int) {
      val remapList = remaps[BlockStateCodec.normalizeBlockId(source)] ?: return
      if (index in 0 until remapList.size) {
         remapList[index].weight = weight.coerceIn(1, 999)
         this.rebuild()
         this.save()
      }
   }

   override fun removeTarget(source: String, index: Int) {
      val key = BlockStateCodec.normalizeBlockId(source)
      val remapList = remaps[key] ?: return
      if (index in 0 until remapList.size) {
         remapList.removeAt(index)
         if (remapList.isEmpty()) {
            remaps.remove(key)
         }

         this.rebuild()
         this.save()
      }
   }

   override fun removeSource(source: String) {
      if (remaps.remove(BlockStateCodec.normalizeBlockId(source)) != null) {
         this.rebuild()
         this.save()
      }
   }

   override fun clear() {
      remaps.clear()
      this.rebuild()
      this.save()
   }

   override fun exportJson(): String = gson.toJson(remaps)

   override fun importJson(json: String): Boolean {
      return try {
         val mapType = object : TypeToken<LinkedHashMap<String, List<RemapTarget>>>() {}.type
         val parsed: LinkedHashMap<String, List<RemapTarget>>? = gson.fromJson(json, mapType)
         if (parsed == null) {
            return false
         }

         remaps.clear()

         for ((src, targets) in parsed) {
            val filtered = targets.filter { !it.id.isBlank() && it.weight > 0 }
            if (filtered.isNotEmpty()) {
               remaps[BlockStateCodec.normalizeBlockId(src)] = filtered.toMutableList()
            }
         }

         this.rebuild()
         this.save()
         true
      } catch (_: Exception) {
         false
      }
   }

   fun load() {
      remaps.clear()

      try {
         if (file.exists()) {
            val text = file.readText(Charsets.UTF_8)
            if (text.isNotBlank()) {
               @Suppress("UNCHECKED_CAST")
               val type = object : TypeToken<LinkedHashMap<String, List<RemapTarget>>>() {}.type
               val parsed: LinkedHashMap<String, List<RemapTarget>>? = gson.fromJson(text, type)
               if (parsed != null) {
                  for ((key, value) in parsed) {
                     remaps[key] = value.toMutableList()
                  }
               }
            }
         }
      } catch (e: Exception) {
         System.err.println("[CB] Failed to load global remaps: ${e.message}")
      }

      this.rebuild()
   }

   fun save() {
      try {
         file.parentFile?.mkdirs()

         val tmp = File(file.parentFile, file.name + ".tmp")
         val json = gson.toJson(remaps)
         tmp.writeText(json, Charsets.UTF_8)
         if (file.exists()) {
            file.delete()
         }

         tmp.renameTo(file)
      } catch (e: Exception) {
         System.err.println("[CB] Failed to save global remaps: ${e.message}")
      }
   }

   data class RemapTarget(var id: String, var weight: Int)

   internal class Resolved(val states: Array<BlockState>, val cumWeights: IntArray, val total: Int)
}
