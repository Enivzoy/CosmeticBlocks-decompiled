package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab

class ReplacementIO private constructor() {
   fun save() {
      if (SaveScheduler.saveInFlight) {
         SaveScheduler.saveAgain = true
      } else {
         SaveScheduler.needsSave = false
         SaveScheduler.lastSaveTime = System.currentTimeMillis()
         val snapshot = ReplacementStore.snapshotReplacements()
         val floor = ReplacementStore.floor
         val file = ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value)
         val water = WaterConfig.INSTANCE.captureFields()
         SaveScheduler.saveInFlight = true
         SaveScheduler.saveExecutor.execute { saveAsync(snapshot, floor, water, file) }
      }
   }

   internal fun saveToFileSync(file: File) {
      SaveScheduler.needsSave = false
      SaveScheduler.lastSaveTime = System.currentTimeMillis()

      try {
         this.writeConfig(
            ReplacementStore.snapshotReplacements(), ReplacementStore.floor, WaterConfig.INSTANCE.captureFields(), file
         )
      } catch (e: Exception) {
         System.err.println("[CB] Failed to save config sync: " + e.message)
      }
   }

   private fun writeConfig(snapshot: Map<String, Map<String, String>>, floor: String?, water: WaterConfig.Fields, file: File) {
      if (!ReplacementStore.configDir.exists()) {
         ReplacementStore.configDir.mkdirs()
      }

      val tmpFile = File(file.parentFile, file.name + ".tmp")
      BufferedWriter(FileWriter(tmpFile), 65536).use { bw ->
         JsonWriter(bw).use { jw ->
            jw.setIndent("  ")
            jw.beginObject()

            if (floor != null && floor.isNotEmpty()) {
               jw.name("floor").value(floor)
            }

            val allow = water.allow
            if (allow != null) {
               jw.name("allowWaterColor").value(allow)
            }

            val rgb = water.rgb
            if (rgb != null) {
               jw.name("rgbWaterColor").value(rgb)
            }

            val lava = water.lava
            if (lava != null) {
               jw.name("lavaAsWater").value(lava)
            }

            for ((dim, dimMap) in snapshot) {
               jw.name(dim)
               jw.beginObject()

               for ((pos, block) in dimMap) {
                  jw.name(pos).value(block)
               }

               jw.endObject()
            }

            jw.endObject()
         }
      }

      try {
         Files.move(tmpFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING)
      } catch (ignored: Exception) {
         if (file.exists()) {
            file.delete()
         }

         tmpFile.renameTo(file)
      }
   }

   fun load() {
      SaveScheduler.flushSave()
      SaveScheduler.awaitPendingSaves()
      ConfigSwitcher.INSTANCE.loadedConfigName = DungeonConfigTab.INSTANCE.activeConfig.value
      this.loadFrom(ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value))
      DirtySections.markAllCachedDirty()
   }

   internal fun loadFrom(file: File) {
      ReplacementStore.replacements.clear()
      ReplacementStore.floor = null
      WaterConfig.INSTANCE.onConfigLoaded(null, null, null)
      ConfigRemap.INSTANCE.load(file.nameWithoutExtension)
      val tmpFile = File(file.parentFile, file.name + ".tmp")
      val loaded = this.tryLoadFile(file) || this.tryLoadFile(tmpFile)
      if (!loaded && (file.exists() || tmpFile.exists())) {
         System.err.println("[CB] Failed to load config from " + file.name)
      }

      ReplacementCache.rebuildActiveCache()
   }

   private fun tryLoadFile(file: File): Boolean {
      if (!file.exists()) {
         return false
      }

      return try {
         val content = file.readText(Charsets.UTF_8)
         if (content.isBlank()) {
            return false
         }

         val root = JsonParser.parseString(content).asJsonObject
         var loadedFloor: String? = null
         var loadedAllowWater: Boolean? = null
         var loadedRgbWater: Int? = null
         var loadedLavaAsWater: Boolean? = null

         for ((dim, element) in root.entrySet()) {
            if (element.isJsonPrimitive) {
               val primitive = element.asJsonPrimitive
               if (dim == "floor" && primitive.isString) {
                  loadedFloor = element.asString
               } else if (dim == "allowWaterColor" && primitive.isBoolean) {
                  loadedAllowWater = element.asBoolean
               } else if (dim == "rgbWaterColor" && primitive.isNumber) {
                  loadedRgbWater = element.asInt
               } else if (dim == "lavaAsWater" && primitive.isBoolean) {
                  loadedLavaAsWater = element.asBoolean
               }

               continue
            }

            if (!element.isJsonObject) {
               continue
            }

            val dimObj = element.asJsonObject
            val dimMap = LinkedHashMap<String, String>()

            for ((pos, block) in dimObj.entrySet()) {
               dimMap[pos] = block.asString
            }

            ReplacementStore.replacements[dim] = dimMap
         }

         ReplacementStore.floor = loadedFloor
         WaterConfig.INSTANCE.onConfigLoaded(loadedAllowWater, loadedRgbWater, loadedLavaAsWater)
         true
      } catch (e: Exception) {
         System.err.println("[CB] Failed to parse " + file.name + ": " + e.message)
         false
      }
   }

   internal fun migrateOldConfig() {
      val oldFile = File("config/cb/cfme.json")
      if (oldFile.exists()) {
         if (!ReplacementStore.configDir.exists()) {
            ReplacementStore.configDir.mkdirs()
         }

         val target = ReplacementStore.getConfigFile("default")
         if (!target.exists()) {
            oldFile.copyTo(target, overwrite = false, bufferSize = 8192)
         }

         oldFile.delete()
      }
   }

   companion object {
      @JvmField
      val INSTANCE: ReplacementIO = ReplacementIO()

      private fun saveAsync(
         snapshot: Map<String, Map<String, String>>, floor: String?, water: WaterConfig.Fields, file: File
      ) {
         try {
            INSTANCE.writeConfig(snapshot, floor, water, file)
         } catch (e: Exception) {
            System.err.println("[CB] Failed to save config: " + e.message)
         } finally {
            SaveScheduler.saveInFlight = false
            if (SaveScheduler.saveAgain) {
               SaveScheduler.saveAgain = false
               SaveScheduler.needsSave = true
               SaveScheduler.lastEditTime = 0L
            }
         }
      }
   }
}
