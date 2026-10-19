package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.tabs.RoomProfilesTab
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil

object RoomProfileManager {
   private val profilesDir: File = ReplacementStore.configDir
   private const val PROFILE_EXT = ".roomprofile"
   private const val DEFAULT_PROFILE = "default"

   @Volatile
   private var lastActive: String? = null
   private var initialized = false

   fun init() {
      if (!profilesDir.exists()) {
         profilesDir.mkdirs()
      }

      if (this.profileFiles().isEmpty()) {
         this.writeProfileFile(DEFAULT_PROFILE)
      }
   }

   fun checkSwitch() {
      val current = this.currentActive()
      if (!initialized) {
         initialized = true
         lastActive = current
         return
      }

      if (current != null && current != lastActive) {
         this.switchTo(current)
      }
   }

   fun listProfiles(): MutableList<String> {
      val names = mutableListOf<String>()
      for (file in this.profileFiles()) {
         names.add(file.nameWithoutExtension)
      }

      if (names.isEmpty()) {
         return mutableListOf(DEFAULT_PROFILE)
      }

      return names.sorted().toMutableList()
   }

   fun createProfile(name: String): Boolean {
      val trimmed = this.sanitize(name) ?: return false
      if (this.profileFile(trimmed).exists()) {
         return false
      }

      this.writeProfileFile(trimmed)
      return true
   }

   fun saveAs(name: String): Boolean {
      val trimmed = this.sanitize(name)
      if (trimmed == null) {
         ChatUtil.error("Invalid name. Use letters, numbers, spaces, _ or - (max 32).")
         return false
      }

      SaveScheduler.awaitPendingSaves()
      if (!this.writeProfileFile(trimmed)) {
         ChatUtil.error("Failed to save room profile $trimmed.")
         return false
      }

      this.setActive(trimmed)
      lastActive = trimmed
      Config.INSTANCE.save()
      ChatUtil.success("Saved room profile $trimmed.")
      return true
   }

   fun overwriteActive(): Boolean {
      val name = this.currentActive()
      if (name.isNullOrEmpty()) {
         ChatUtil.error("No active room profile.")
         return false
      }

      SaveScheduler.awaitPendingSaves()
      if (!this.writeProfileFile(name)) {
         ChatUtil.error("Failed to save room profile $name.")
         return false
      }

      lastActive = name
      Config.INSTANCE.save()
      ChatUtil.success("Updated room profile $name.")
      return true
   }

   fun deleteActive(): Boolean {
      val name = this.currentActive()
      if (name.isNullOrEmpty()) {
         ChatUtil.error("No active room profile.")
         return false
      }

      if (this.profileFiles().size <= 1) {
         ChatUtil.error("Cannot delete the only room profile.")
         return false
      }

      SaveScheduler.awaitPendingSaves()
      val file = this.profileFile(name)
      if (!file.exists() || !file.delete()) {
         ChatUtil.error("Failed to delete room profile $name.")
         return false
      }

      val remaining = this.listProfiles()
      val next = if (remaining.isEmpty()) DEFAULT_PROFILE else remaining[0]
      if (!this.loadProfileInto(next)) {
         RoomStore.roomBlocks.clear()
      }

      this.reapplyCurrentRoom()
      this.setActive(next)
      lastActive = next
      Config.INSTANCE.save()
      ChatUtil.success("Deleted room profile $name. Now active: $next.")
      return true
   }

   fun loadByName(name: String): Boolean {
      val trimmed = this.sanitize(name)
      if (trimmed == null || trimmed == this.currentActive()) {
         return false
      }

      this.switchTo(trimmed)
      return true
   }

   private fun switchTo(name: String) {
      SaveScheduler.awaitPendingSaves()
      val previous = this.lastActive
      if (!previous.isNullOrEmpty() && previous != name) {
         this.writeProfileFile(previous)
      }

      if (!this.loadProfileInto(name)) {
         RoomStore.roomBlocks.clear()
      }

      this.reapplyCurrentRoom()
      lastActive = name
      Config.INSTANCE.save()
      ChatUtil.info("Loaded room profile $name.")
   }

   private fun reapplyCurrentRoom() {
      RoomStore.resolveAllRoomBlocks()
      RoomStore.onRoomChanged(RoomScanner.currentRoom)
      DirtySections.markAllCachedDirty()
      RoomStore.forceRoomSave()
   }

   private fun loadProfileInto(name: String): Boolean {
      val file = this.profileFile(name)
      if (!file.exists()) {
         return false
      }

      val target = RoomStore.roomBlocks
      target.clear()
      try {
         val content = file.readText(Charsets.UTF_8)
         if (content.isNotBlank()) {
            val root = JsonParser.parseString(content).asJsonObject
            for ((roomName, roomValue) in root.entrySet()) {
               val roomMap = LinkedHashMap<String, String>()
               val roomObj = roomValue.asJsonObject
               for ((posKey, posValue) in roomObj.entrySet()) {
                  roomMap[posKey] = posValue.asString
               }

               target[roomName] = roomMap
            }
         }

         return true
      } catch (ex: Exception) {
         System.err.println("[CB] Failed to parse room profile $name: ${ex.message}")
         return false
      }
   }

   private fun writeProfileFile(name: String): Boolean {
      val file = this.profileFile(name)
      try {
         val snapshot = LinkedHashMap<String, Map<String, String>>()
         for ((roomName, roomMap) in RoomStore.roomBlocks) {
            snapshot[roomName] = LinkedHashMap(roomMap)
         }

         this.writeJson(file, snapshot)
         return true
      } catch (ex: Exception) {
         System.err.println("[CB] Failed to write room profile $name: ${ex.message}")
         return false
      }
   }

   private fun writeJson(file: File, snapshot: Map<String, Map<String, String>>) {
      val parent = file.parentFile
      parent?.mkdirs()

      val tmpFile = File(parent, file.name + ".tmp")
      BufferedWriter(FileWriter(tmpFile), 65536).use { writer ->
         JsonWriter(writer).use { jw ->
            jw.setIndent("  ")
            jw.beginObject()

            for ((roomName, roomMap) in snapshot) {
               jw.name(roomName)
               jw.beginObject()

               for ((posKey, value) in roomMap) {
                  jw.name(posKey).value(value)
               }

               jw.endObject()
            }

            jw.endObject()
            jw.flush()
         }
      }

      if (file.exists()) {
         file.delete()
      }

      tmpFile.renameTo(file)
   }

   private fun profileFiles(): Array<File> {
      if (!profilesDir.exists()) {
         return emptyArray()
      }

      return profilesDir.listFiles { _, fileName -> fileName.endsWith(PROFILE_EXT) } ?: emptyArray()
   }

   private fun profileFile(name: String): File = File(profilesDir, name + PROFILE_EXT)

   private fun currentActive(): String? = RoomProfilesTab.INSTANCE.getActiveProfile().value

   private fun setActive(name: String) {
      RoomProfilesTab.INSTANCE.getActiveProfile().value = name
   }

   private fun sanitize(name: String): String? {
      val trimmed = name.trim()
      if (trimmed.isEmpty() || trimmed.length > 32 || !trimmed.matches(Regex("[A-Za-z0-9 _-]+"))) {
         return null
      }

      return trimmed
   }
}
