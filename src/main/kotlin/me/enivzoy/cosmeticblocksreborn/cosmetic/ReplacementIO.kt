package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Locale
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft

class ReplacementIO private constructor() {
   private class LoadedConfig(
      val dims: LinkedHashMap<String, LinkedHashMap<String, String>>,
      val floor: String?,
      val water: WaterConfig.Fields
   )

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
      BufferedWriter(tmpFile.writer(Charsets.UTF_8), 65536).use { bw ->
         JsonWriter(bw).use { jw ->
            jw.setIndent("  ")
            jw.beginObject()

            if (floor != null && floor.isNotEmpty()) {
               jw.name("floor").value(floor)
            }

            jw.name("format").value(2L)

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

               val compressed = ReplacementCompactor.compress(dimMap)
               for ((raw, boxes) in compressed) {
                  jw.name(raw)
                  jw.beginArray()

                  for (box in boxes) {
                     jw.beginArray()
                     jw.setIndent("")
                     jw.value(box[0].toLong())
                     jw.value(box[1].toLong())
                     jw.value(box[2].toLong())
                     jw.value(box[3].toLong())
                     jw.value(box[4].toLong())
                     jw.value(box[5].toLong())
                     jw.endArray()
                     jw.setIndent("  ")
                  }

                  jw.endArray()
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
      val loaded = this.parseFile(file) ?: this.parseFile(tmpFile)
      if (loaded != null) {
         for ((dim, dimMap) in loaded.dims) {
            ReplacementStore.replacements[dim] = dimMap
         }

         ReplacementStore.floor = loaded.floor
         WaterConfig.INSTANCE.onConfigLoaded(loaded.water.allow, loaded.water.rgb, loaded.water.lava)
      } else if (file.exists() || tmpFile.exists()) {
         System.err.println("[CB] Failed to load config from " + file.name)
      }

      ReplacementCache.rebuildActiveCache()
   }

   private fun parseFile(file: File): LoadedConfig? {
      if (!file.exists()) {
         return null
      }

      return try {
         val root = FileReader(file, Charsets.UTF_8).use { JsonParser.parseReader(it).asJsonObject }
         var loadedFloor: String? = null
         var loadedAllowWater: Boolean? = null
         var loadedRgbWater: Int? = null
         var loadedLavaAsWater: Boolean? = null
         val dims = LinkedHashMap<String, LinkedHashMap<String, String>>()

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

            val dimMap = LinkedHashMap<String, String>()
            for ((raw, value) in element.asJsonObject.entrySet()) {
               if (value.isJsonArray) {
                  ReplacementCompactor.expand(raw, this.toRanges(value.asJsonArray), dimMap)
               } else if (value.isJsonPrimitive) {
                  dimMap[raw] = value.asString
               }
            }

            if (dimMap.isNotEmpty()) {
               dims[dim] = dimMap
            }
         }

         LoadedConfig(dims, loadedFloor, WaterConfig.Fields(loadedAllowWater, loadedRgbWater, loadedLavaAsWater))
      } catch (e: Exception) {
         System.err.println("[CB] Failed to parse " + file.name + ": " + e.message)
         null
      }
   }

   private fun toRanges(array: JsonArray): List<IntArray> {
      val ranges = ArrayList<IntArray>(array.size())
      for (rangeElement in array) {
         if (!rangeElement.isJsonArray) {
            continue
         }

         val values = rangeElement.asJsonArray
         if (values.size() < 6) {
            continue
         }

         val range = IntArray(6)
         var valid = true
         for (i in 0 until 6) {
            val value = values.get(i)
            if (!value.isJsonPrimitive || !value.asJsonPrimitive.isNumber) {
               valid = false
               break
            }

            range[i] = value.asInt
         }

         if (valid) {
            ranges.add(range)
         }
      }

      return ranges
   }

   internal fun migrateAsync(nameInput: String) {
      val name = nameInput.trim().removePrefix("\"").removeSuffix("\"").removeSuffix(".json")
      if (name.isEmpty()) {
         this.report("Usage: /cb migrate <config>")
         return
      }

      val file = ReplacementStore.getConfigFile(name)
      if (!file.exists()) {
         this.report("Config '$name' not found")
         return
      }

      val active = name == DungeonConfigTab.INSTANCE.activeConfig.value
      val snapshot = if (active) ReplacementStore.snapshotReplacements() else null
      val floor = if (active) ReplacementStore.floor else null
      val water = if (active) WaterConfig.INSTANCE.captureFields() else null

      this.report("Migrating '$name' to the compact format...")
      SaveScheduler.saveExecutor.execute {
         try {
            val before = file.length()
            val backup = File(file.parentFile, file.name + ".bak")
            file.copyTo(backup, overwrite = true)

            if (active) {
               this.writeConfig(snapshot!!, floor, water!!, file)
            } else {
               val loaded = this.parseFile(file) ?: throw IllegalStateException("could not parse config")
               this.writeConfig(loaded.dims, loaded.floor, loaded.water, file)
            }

            val after = file.length()
            val percent = if (before > 0L) (before - after) * 100.0 / before else 0.0
            this.report(
               "Migrated '$name': "
                  + this.formatSize(before)
                  + " -> "
                  + this.formatSize(after)
                  + " ("
                  + String.format(Locale.ROOT, "%.1f", percent)
                  + "% smaller). Backup: "
                  + backup.name
            )
         } catch (e: Exception) {
            this.report("Migration of '$name' failed: " + e.message)
            System.err.println("[CB] Migration of " + name + " failed: " + e.message)
         }
      }
   }

   private fun report(message: String) {
      Minecraft.getInstance().execute { ChatUtil.send(message) }
   }

   private fun formatSize(bytes: Long): String = when {
      bytes >= 1024L * 1024L -> String.format(Locale.ROOT, "%.1f MB", bytes / (1024.0 * 1024.0))
      bytes >= 1024L -> String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0)
      else -> "$bytes B"
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
