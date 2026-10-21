package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.gui.hud.DebugStats
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft

class ReplacementIO private constructor() {
   @Volatile
   private var loadGeneration = 0

   @Volatile
   internal var loading = false

   private fun flagsForFile(file: File): Int = configFlags[file.nameWithoutExtension] ?: 0

   private fun rememberFlags(name: String, flags: Int) {
      configFlags[name] = flags
   }

   fun save() {
      if (this.loading) {
         return
      }

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
         SaveScheduler.saveExecutor.execute { saveAsync(snapshot, floor, water, file, false) }
      }
   }

   internal fun saveDeliberateClear() {
      SaveScheduler.needsSave = false
      SaveScheduler.lastSaveTime = System.currentTimeMillis()
      val floor = ReplacementStore.floor
      val file = ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value)
      val water = WaterConfig.INSTANCE.captureFields()
      SaveScheduler.saveInFlight = true
      SaveScheduler.saveExecutor.execute { saveAsync(emptyMap(), floor, water, file, true) }
   }

   internal fun saveToFileSync(file: File) {
      SaveScheduler.needsSave = false
      SaveScheduler.lastSaveTime = System.currentTimeMillis()

      try {
         this.writeConfig(
            ReplacementStore.snapshotReplacements(), ReplacementStore.floor, WaterConfig.INSTANCE.captureFields(), file,
            this.flagsForFile(file), false
         )
      } catch (e: Exception) {
         System.err.println("[CB] Failed to save config sync: " + e.message)
      }
   }

   internal fun saveCurrentAsync(file: File) {
      SaveScheduler.needsSave = false
      SaveScheduler.lastSaveTime = System.currentTimeMillis()
      val snapshot = ReplacementStore.snapshotReplacements()
      val floor = ReplacementStore.floor
      val water = WaterConfig.INSTANCE.captureFields()
      SaveScheduler.saveInFlight = true
      SaveScheduler.saveExecutor.execute { saveAsync(snapshot, floor, water, file, false) }
   }

   private fun writeConfig(
      snapshot: Map<String, Map<String, String>>,
      floor: String?,
      water: WaterConfig.Fields,
      file: File,
      flags: Int,
      force: Boolean
   ) {
      if (!ReplacementStore.configDir.exists()) {
         ReplacementStore.configDir.mkdirs()
      }

      if (!force && !this.isSafeWrite(snapshot, file)) {
         System.err.println("[CB] Refused unsafe save for " + file.name + ": snapshot is empty but store/last-saved is not")
         return
      }

      val tmpFile = File(file.parentFile, file.name + ".tmp")
      if (tmpFile.exists()) {
         tmpFile.delete()
      }

      val bytes = ConfigCompression.encode(floor, water, snapshot, flags)
      tmpFile.writeBytes(bytes)

      if (file.exists() && file.length() > 0L && file.length() > bytes.size) {
         val backup = File(file.parentFile, file.name + ".bak")
         try {
            file.copyTo(backup, overwrite = true)
         } catch (ignored: Exception) {
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

   private fun isSafeWrite(snapshot: Map<String, Map<String, String>>, file: File): Boolean {
      val snapshotCount = ReplacementStore.snapshotCount(snapshot)
      if (snapshotCount > 0) {
         return true
      }

      if (!file.exists() || file.length() <= 0L) {
         return true
      }

      val existing = this.parseFile(file)
      val existingCount = if (existing == null) 0 else ReplacementStore.snapshotCount(existing.dims)
      return existingCount <= 0
   }

   fun load() {
      SaveScheduler.flushSave()
      SaveScheduler.awaitPendingSaves()
      ConfigSwitcher.INSTANCE.loadedConfigName = DungeonConfigTab.INSTANCE.activeConfig.value
      this.loadFrom(ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value))
      DirtySections.markAllCachedDirty()
   }

   internal fun loadFrom(file: File) {
      val loadStartNanos = System.nanoTime()
      ReplacementStore.replacements.clear()
      ReplacementStore.floor = null
      WaterConfig.INSTANCE.onConfigLoaded(null, null, null)
      ConfigRemap.INSTANCE.load(file.nameWithoutExtension)
      val tmpFile = File(file.parentFile, file.name + ".tmp")
      val parsed = this.parseFileTimed(file)
      val loaded = parsed.document ?: this.parseFileTimed(tmpFile).document
      if (loaded != null) {
         for ((dim, dimMap) in loaded.dims) {
            ReplacementStore.replacements[dim] = dimMap
         }

         ReplacementStore.floor = loaded.floor
         WaterConfig.INSTANCE.onConfigLoaded(loaded.water.allow, loaded.water.rgb, loaded.water.lava)
         this.rememberFlags(file.nameWithoutExtension, loaded.flags)
      } else if (file.exists() || tmpFile.exists()) {
         System.err.println("[CB] Failed to load config from " + file.name)
      }

      ReplacementCache.rebuildActiveCache()
      WorldEditMode.resetHistory()
      if (loaded != null && loaded.isLegacy) {
         val totalMs = (System.nanoTime() - loadStartNanos) / 1_000_000L
         this.notifyLegacyConfig(file.nameWithoutExtension, loaded, totalMs, parsed.decodeMs)
      }
   }

   internal fun loadFromAsync(file: File) {
      val gen = ++this.loadGeneration
      this.loading = true
      val dim = LocationTracker.getCurrentDimension()
      val name = file.nameWithoutExtension
      val tmpFile = File(file.parentFile, file.name + ".tmp")
      val loadStartNanos = System.nanoTime()
      loadExecutor.execute {
         try {
            val parsed = this.parseFileTimed(file)
            val document: ConfigCompression.Document?
            val decodeMs: Long
            if (parsed.document != null) {
               document = parsed.document
               decodeMs = parsed.decodeMs
            } else {
               val fallback = this.parseFileTimed(tmpFile)
               document = fallback.document
               decodeMs = fallback.decodeMs
            }

            val dimMap = if (dim == null || document == null) emptyMap() else document.dims[dim] ?: emptyMap()
            val bundle = ReplacementCache.buildBundle(dimMap)
            Minecraft.getInstance().execute {
               if (gen != this.loadGeneration) {
                  return@execute
               }

               try {
                  this.applyLoaded(file, tmpFile, name, dim, document, bundle, decodeMs, loadStartNanos)
               } finally {
                  this.loading = false
               }
            }
         } catch (e: Exception) {
            System.err.println("[CB] Failed to load config " + file.name + ": " + e.message)
            Minecraft.getInstance().execute {
               if (gen == this.loadGeneration) {
                  this.loading = false
               }
            }
         }
      }
   }

   private fun applyLoaded(
      file: File,
      tmpFile: File,
      name: String,
      dim: String?,
      document: ConfigCompression.Document?,
      bundle: ReplacementCache.Bundle,
      decodeMs: Long,
      loadStartNanos: Long
   ) {
      ConfigRemap.INSTANCE.load(name)
      val newReplacements = LinkedHashMap<String, MutableMap<String, String>>()
      ReplacementStore.floor = null
      WaterConfig.INSTANCE.onConfigLoaded(null, null, null)
      if (document != null) {
         for ((loadedDim, dimMap) in document.dims) {
            newReplacements[loadedDim] = dimMap
         }

         ReplacementStore.replacements = newReplacements
         ReplacementStore.floor = document.floor
         WaterConfig.INSTANCE.onConfigLoaded(document.water.allow, document.water.rgb, document.water.lava)
         this.rememberFlags(name, document.flags)
      } else if (file.exists() || tmpFile.exists()) {
         ReplacementStore.replacements = newReplacements
         System.err.println("[CB] Failed to load config from " + file.name)
      } else {
         ReplacementStore.replacements = newReplacements
      }

      ReplacementCache.swap(bundle)
      if (LocationTracker.getCurrentDimension() != dim) {
         ReplacementCache.rebuildActiveCache()
      }

      DirtySections.markAllCachedDirty()
      DungeonConfigTab.syncFloorFromStore()
      WorldEditMode.resetHistory()
      if (document != null && document.isLegacy) {
         val totalMs = (System.nanoTime() - loadStartNanos) / 1_000_000L
         this.notifyLegacyConfig(name, document, totalMs, decodeMs)
      }
   }

   private fun parseFile(file: File): ConfigCompression.Document? = this.parseFileTimed(file).document

   private fun parseFileTimed(file: File): ParseResult {
      if (!file.exists()) {
         return ParseResult(null, 0L)
      }

      return try {
         val start = System.nanoTime()
         val bytes = file.readBytes()
         val document = ConfigCompression.decode(bytes)
         ParseResult(document, (System.nanoTime() - start) / 1_000_000L)
      } catch (e: Exception) {
         System.err.println("[CB] Failed to parse " + file.name + ": " + e.message)
         ParseResult(null, 0L)
      }
   }

   private class ParseResult(val document: ConfigCompression.Document?, val decodeMs: Long)

   internal fun migrateAsync(nameInput: String, flags: Int) {
      val name = nameInput.trim().removePrefix("\"").removeSuffix("\"").removeSuffix(".json")
      if (name.isEmpty()) {
         this.report("Usage: /cb migrate <config> [-mini] [--settings]")
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

      this.report("Migrating '$name' (" + ConfigCompression.describe(flags) + ")...")
      SaveScheduler.saveExecutor.execute {
         try {
            val before = file.length()
            val backup = File(file.parentFile, file.name + ".bak")
            file.copyTo(backup, overwrite = true)

            if (active) {
               this.writeConfig(snapshot!!, floor, water!!, file, flags, false)
            } else {
               val loaded = this.parseFile(file) ?: throw IllegalStateException("could not parse config")
               this.writeConfig(loaded.dims, loaded.floor, loaded.water, file, flags, false)
            }

            this.rememberFlags(name, flags)

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

   internal fun diffConfigs(nameA: String, nameB: String) {
      val fileA = ReplacementStore.getConfigFile(nameA.trim().removeSuffix(".json"))
      val fileB = ReplacementStore.getConfigFile(nameB.trim().removeSuffix(".json"))
      if (!fileA.exists()) {
         this.report("Config '$nameA' not found")
         return
      }

      if (!fileB.exists()) {
         this.report("Config '$nameB' not found")
         return
      }

      this.report("Diffing §b$nameA§f against §b$nameB§f...")
      SaveScheduler.saveExecutor.execute {
         try {
            val docA = this.parseFile(fileA)
            val docB = this.parseFile(fileB)
            if (docA == null || docB == null) {
               this.report("Failed to parse one of the configs")
               return@execute
            }

            val mapA = flatten(docA)
            val mapB = flatten(docB)
            val dims = LinkedHashSet<String>()
            dims.addAll(mapA.keys)
            dims.addAll(mapB.keys)

            var added = 0
            var removed = 0
            var changed = 0
            for (dim in dims) {
               val a = mapA[dim] ?: emptyMap()
               val b = mapB[dim] ?: emptyMap()
               for ((key, value) in b) {
                  val old = a[key]
                  if (old == null) {
                     added++
                  } else if (old != value) {
                     changed++
                  }
               }

               for (key in a.keys) {
                  if (!b.containsKey(key)) {
                     removed++
                  }
               }
            }

            this.report(
               "Diff $nameB vs $nameA: §a+$added§f / §c-$removed§f / §e~$changed§f (dimensions: " + dims.joinToString(", ") + ")"
            )
         } catch (e: Exception) {
            this.report("Diff failed: " + e.message)
         }
      }
   }

   private fun flatten(document: ConfigCompression.Document): Map<String, Map<String, String>> {
      val result = LinkedHashMap<String, Map<String, String>>()
      for ((dim, dimMap) in document.dims) {
         result[dim] = dimMap
      }

      return result
   }

   private fun report(message: String) {
      Minecraft.getInstance().execute { ChatUtil.send(message) }
   }

   private fun formatSize(bytes: Long): String = when {
      bytes >= 1024L * 1024L -> String.format(Locale.ROOT, "%.1f MB", bytes / (1024.0 * 1024.0))
      bytes >= 1024L -> String.format(Locale.ROOT, "%.1f KB", bytes / 1024.0)
      else -> "$bytes B"
   }

   private fun notifyLegacyConfig(name: String, document: ConfigCompression.Document, totalLoadMs: Long, decodeMs: Long) {
      if (!notifiedLegacy.add(name)) {
         return
      }

      SaveScheduler.saveExecutor.execute {
         try {
            val legacyBytes = this.readConfigBytes(name) ?: return@execute
            val miniFlags = ConfigCompression.MINI_STAGES
            val migrated = ConfigCompression.encode(document.floor, document.water, document.dims, miniFlags)

            val legacySize = legacyBytes.size.toLong()
            val spacePercent = if (legacySize > 0) (legacySize - migrated.size) * 100.0 / legacySize else 0.0
            val migratedDecodeMs = this.measureMigratedLoadMs(name, migrated)
            val savedMs = (decodeMs - migratedDecodeMs).coerceAtLeast(0L)
            val quoted = if (name.contains(" ")) "\"" + name + "\"" else name

            Minecraft.getInstance().execute {
               ChatUtil.send("§eThis config (§b$name§e) is stored in the §c§llegacy§r§e format.")
               ChatUtil.send(
                  "§7Loading it took §b" + totalLoadMs + " ms§7 (§b" + decodeMs
                     + " ms§7 parsing the legacy file, §b" + (totalLoadMs - decodeMs).coerceAtLeast(0L) + " ms§7 building the cache)."
               )
               ChatUtil.send(
                  "§7Migrating it with §b/cb migrate " + quoted + " -m§7 shrinks it from §b" + this.formatSize(legacySize)
                     + "§7 to §b" + this.formatSize(migrated.size.toLong()) + "§7 ("
                     + (if (spacePercent >= 0) "§a§l" else "§c§l")
                     + String.format(Locale.ROOT, "%.1f", Math.abs(spacePercent)) + "% smaller§r§7)."
               )
               ChatUtil.send(
                  "§7It would then load in roughly §b" + (totalLoadMs - savedMs).coerceAtLeast(0L)
                     + " ms§7 instead (§a§l" + savedMs + " ms faster§r§7)."
               )
               ChatUtil.send("§7Run §b/cb migrate " + quoted + " -m§7 to convert it. A §f.bak§7 backup is kept.")
            }
         } catch (e: Exception) {
            System.err.println("[CB] Failed to compute legacy config stats: " + e.message)
         }
      }
   }

   private fun readConfigBytes(name: String): ByteArray? {
      val file = ReplacementStore.getConfigFile(name)
      if (file.exists()) {
         return file.readBytes()
      }

      val tmpFile = File(file.parentFile, file.name + ".tmp")
      return if (tmpFile.exists()) tmpFile.readBytes() else null
   }

   private fun measureMigratedLoadMs(name: String, bytes: ByteArray): Long {
      val tmpFile = File(ReplacementStore.configDir, name + ".cbestimate.tmp")
      return try {
         if (!ReplacementStore.configDir.exists()) {
            ReplacementStore.configDir.mkdirs()
         }

         tmpFile.writeBytes(bytes)
         val start = System.nanoTime()
         val read = tmpFile.readBytes()
         ConfigCompression.decode(read)
         val elapsed = (System.nanoTime() - start) / 1_000_000L
         if (elapsed > 0L) {
            elapsed
         } else {
            this.measureDecodeMs(bytes)
         }
      } catch (_: Exception) {
         this.measureDecodeMs(bytes)
      } finally {
         try {
            tmpFile.delete()
         } catch (ignored: Exception) {
         }
      }
   }

   private fun measureDecodeMs(bytes: ByteArray): Long {
      val start = System.nanoTime()
      try {
         ConfigCompression.decode(bytes)
      } catch (_: Exception) {
      }

      return (System.nanoTime() - start) / 1_000_000L
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

      private val configFlags = java.util.concurrent.ConcurrentHashMap<String, Int>()

      private val notifiedLegacy = java.util.concurrent.ConcurrentHashMap.newKeySet<String>()

      private val loadExecutor: ExecutorService = Executors.newSingleThreadExecutor { runnable ->
         Thread(runnable, "cb-config-load").apply { isDaemon = true }
      }

      private fun saveAsync(
         snapshot: Map<String, Map<String, String>>, floor: String?, water: WaterConfig.Fields, file: File, force: Boolean
      ) {
         try {
            DebugStats.noteSaveStart()
            INSTANCE.writeConfig(snapshot, floor, water, file, INSTANCE.flagsForFile(file), force)
         } catch (e: Exception) {
            System.err.println("[CB] Failed to save config: " + e.message)
         } finally {
            DebugStats.noteSaveEnd()
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
