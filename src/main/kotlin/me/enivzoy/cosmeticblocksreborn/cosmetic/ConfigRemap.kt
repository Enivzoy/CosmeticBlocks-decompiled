package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class ConfigRemap private constructor() : RemapStore {
   private val remaps = LinkedHashMap<String, MutableList<GlobalRemap.RemapTarget>>()
   private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
   private var loadedNameState: String? = null
   var enabled = false
   var keepShape = false

   @Volatile
   private var resolved: Map<Block, GlobalRemap.Resolved> = emptyMap()
   private var resolvedSignature = 0
   private var hasResolvedSignature = false

   internal val loadedName: String?
      get() = loadedNameState

   internal fun onSettingChanged(newEnabled: Boolean, newKeepShape: Boolean) {
      enabled = newEnabled
      keepShape = newKeepShape
      this.rebuild()
      this.save()
   }

   fun isActiveForActiveConfig(): Boolean {
      if (!enabled) {
         return false
      }

      if (!this.hasRemaps()) {
         return false
      }

      val active = DungeonConfigTab.INSTANCE.activeConfig.value
      return loadedNameState == active
   }

   fun fileFor(name: String): File = File(ReplacementStore.configDir, "$name-BlockRemap.json")

   fun remapFor(block: Block, x: Int, y: Int, z: Int): BlockState? {
      if (!enabled) {
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

   fun load(name: String?) {
      loadedNameState = name
      remaps.clear()
      enabled = false
      keepShape = false

      val file = if (name == null) null else this.fileFor(name)
      if (file != null && file.exists()) {
         try {
            val text = file.readText(Charsets.UTF_8)
            if (text.isNotBlank()) {
               val root = JsonParser.parseString(text).asJsonObject
               val enabledElement = root.get("enabled")
               if (enabledElement != null && enabledElement.isJsonPrimitive) {
                  enabled = enabledElement.asBoolean
               }

               val keepShapeElement = root.get("keepShape")
               if (keepShapeElement != null && keepShapeElement.isJsonPrimitive) {
                  keepShape = keepShapeElement.asBoolean
               }

               val remapsElement = root.get("remaps")
               if (remapsElement != null && remapsElement.isJsonObject) {
                  val type: Type = object : TypeToken<LinkedHashMap<String, List<GlobalRemap.RemapTarget>>>() {}.type
                  val parsed: LinkedHashMap<String, List<GlobalRemap.RemapTarget>>? = gson.fromJson(remapsElement, type)
                  if (parsed != null) {
                     for ((key, value) in parsed) {
                        remaps[key] = value.toMutableList()
                     }
                  }
               }
            }
         } catch (e: Exception) {
            System.err.println("[CB] Failed to load config remaps: " + e.message)
         }
      }

      this.rebuild()
      DungeonConfigTab.applyRemap(enabled, keepShape)
   }

   fun save() {
      val name = loadedNameState
      val file = if (name == null) null else this.fileFor(name)
      if (file == null) {
         return
      }

      if (remaps.isEmpty()) {
         if (file.exists()) {
            file.delete()
         }

         return
      }

      try {
         file.parentFile?.mkdirs()
         val root = JsonObject()
         root.addProperty("enabled", enabled)
         root.addProperty("keepShape", keepShape)
         root.add("remaps", gson.toJsonTree(remaps))
         val tmp = File(file.parentFile, file.name + ".tmp")
         tmp.writeText(gson.toJson(root), Charsets.UTF_8)
         if (file.exists()) {
            file.delete()
         }

         tmp.renameTo(file)
      } catch (e: Exception) {
         System.err.println("[CB] Failed to save config remaps: " + e.message)
      }
   }

   internal fun rebuild() {
      val map = HashMap<Block, GlobalRemap.Resolved>()

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
            map[sourceBlock] = GlobalRemap.Resolved(stateList.toTypedArray(), cumWeights.toIntArray(), running)
         }
      }

      var signature = (if (enabled) 1 else 0) * 31 + (if (keepShape) 1 else 0)
      for ((block, r) in map) {
         var h = block.hashCode()
         h = h * 31 + r.total
         for (s in r.states) {
            h = h * 31 + s.hashCode()
         }

         signature += h
      }

      val changed = !hasResolvedSignature || signature != resolvedSignature
      resolved = map
      resolvedSignature = signature
      hasResolvedSignature = true
      if (!changed) {
         return
      }

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

   override fun targetsOf(source: String): List<GlobalRemap.RemapTarget> = remaps[source]?.toList() ?: emptyList()

   fun hasSource(source: String): Boolean = remaps.containsKey(BlockStateCodec.normalizeBlockId(source))

   override fun addTarget(source: String, targetId: String, weight: Int) {
      val src = BlockStateCodec.normalizeBlockId(source)
      val tgt = BlockStateCodec.normalizeBlockId(targetId)
      val list = remaps.getOrPut(src) { mutableListOf() }
      val existing = list.firstOrNull { it.id == tgt }
      if (existing != null) {
         existing.weight = existing.weight + weight
      } else {
         list.add(GlobalRemap.RemapTarget(tgt, weight.coerceAtLeast(1)))
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
         val mapType: Type = object : TypeToken<LinkedHashMap<String, List<GlobalRemap.RemapTarget>>>() {}.type
         val parsed: LinkedHashMap<String, List<GlobalRemap.RemapTarget>>? = gson.fromJson(json, mapType)
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

   companion object {
      @JvmField
      val INSTANCE: ConfigRemap = ConfigRemap()
   }
}
