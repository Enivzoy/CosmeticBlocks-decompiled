package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil

object RoomOverrideManager {
   private val overrideFile = File("config/cb/cb_roomoverrides.json")

   @Volatile
   private var overrides: Map<String, String> = emptyMap()

   @Volatile
   private var lastRoomName: String? = null

   @Volatile
   private var configBeforeOverride: String? = null

   private var initialized = false

   fun init() {
      this.load()
      initialized = true
   }

   fun load() {
      if (!overrideFile.exists()) {
         overrides = emptyMap()
         return
      }

      try {
         val content = overrideFile.readText(Charsets.UTF_8)
         if (content.isBlank()) {
            overrides = emptyMap()
            return
         }

         val root = JsonParser.parseString(content).asJsonObject
         val map = LinkedHashMap<String, String>()
         for ((room, value) in root.entrySet()) {
            map[room] = value.asString
         }

         overrides = map
      } catch (e: Exception) {
         System.err.println("[CB] Failed to load room overrides: " + e.message)
         overrides = emptyMap()
      }
   }

   private fun save() {
      overrideFile.parentFile?.mkdirs()
      val tmpFile = File(overrideFile.parentFile, overrideFile.name + ".tmp")
      val snapshot = overrides

      SaveScheduler.saveExecutor.execute {
         try {
            BufferedWriter(FileWriter(tmpFile), 65536).use { writer ->
               JsonWriter(writer).use { jw ->
                  jw.setIndent("  ")
                  jw.beginObject()
                  for ((room, config) in snapshot) {
                     jw.name(room).value(config)
                  }

                  jw.endObject()
               }
            }

            if (overrideFile.exists()) {
               overrideFile.delete()
            }

            tmpFile.renameTo(overrideFile)
         } catch (e: Exception) {
            System.err.println("[CB] Failed to save room overrides: " + e.message)
         }
      }
   }

   fun setOverride(roomName: String, configName: String): Boolean {
      if (!ReplacementStore.getConfigFile(configName).exists()) {
         return false
      }

      val updated = LinkedHashMap(overrides)
      updated[roomName] = configName
      overrides = updated
      this.save()

      if (RoomScanner.currentRoom?.data?.name == roomName) {
         configBeforeOverride = DungeonConfigTab.INSTANCE.activeConfig.value
         ConfigSwitcher.INSTANCE.loadConfigByName(configName)
      }

      return true
   }

   fun removeOverride(roomName: String): Boolean {
      if (!overrides.containsKey(roomName)) {
         return false
      }

      val updated = LinkedHashMap(overrides)
      updated.remove(roomName)
      overrides = updated
      this.save()
      return true
   }

   fun clearOverrides(): Int {
      val count = overrides.size
      overrides = emptyMap()
      this.save()
      return count
   }

   fun getOverride(roomName: String): String? = overrides[roomName]

   fun listOverrides(): Map<String, String> = LinkedHashMap(overrides)

   fun checkRoomOverride() {
      if (!initialized) {
         return
      }

      val roomName = RoomScanner.currentRoom?.data?.name
      if (roomName == lastRoomName) {
         return
      }

      lastRoomName = roomName
      val override = if (roomName != null) overrides[roomName] else null

      if (override != null) {
         val active = DungeonConfigTab.INSTANCE.activeConfig.value
         if (active != override && ReplacementStore.getConfigFile(override).exists()) {
            configBeforeOverride = active
            ConfigSwitcher.INSTANCE.loadConfigByName(override)
            ChatUtil.info("Room override §b$roomName§f - loading config §b$override")
         }

         return
      }

      val previous = configBeforeOverride
      if (previous != null) {
         configBeforeOverride = null
         if (previous != DungeonConfigTab.INSTANCE.activeConfig.value && ReplacementStore.getConfigFile(previous).exists()) {
            ConfigSwitcher.INSTANCE.loadConfigByName(previous)
         }
      }
   }
}
