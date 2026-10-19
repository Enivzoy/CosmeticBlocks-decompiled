package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.io.File
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.PreferencesTab
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor

class ConfigSwitcher private constructor() {
   private var lastActiveConfig: String? = null
   private var dropdownInitialized = false
   internal var loadedConfigName: String? = null

   @Volatile
   private var lastDetectedFloor: String? = null
   private var dungeonActive = false
   private var configBeforeDungeon: String? = null

   internal fun checkConfigSwitch() {
      if (!dropdownInitialized) {
         this.updateConfigDropdown()
         dropdownInitialized = true
         lastActiveConfig = DungeonConfigTab.INSTANCE.activeConfig.value
         if (loadedConfigName != null && loadedConfigName != DungeonConfigTab.INSTANCE.activeConfig.value) {
            ReplacementIO.INSTANCE.loadFrom(ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value))
            DirtySections.markAllCachedDirty()
         }

         DungeonConfigTab.markFloorReady()
         DungeonConfigTab.syncFloorFromStore()
      } else {
         val current = DungeonConfigTab.INSTANCE.activeConfig.value
         if (current != lastActiveConfig) {
            SaveScheduler.awaitPendingSaves()
            val previousConfigName = lastActiveConfig ?: "default"
            ReplacementIO.INSTANCE.saveToFileSync(ReplacementStore.getConfigFile(previousConfigName))
            DirtySections.markAllCachedDirty()
            ReplacementIO.INSTANCE.loadFrom(ReplacementStore.getConfigFile(current))
            DirtySections.markAllCachedDirty()
            lastActiveConfig = current
            DungeonConfigTab.syncFloorFromStore()
         }
      }
   }

   internal fun checkFloorAutoLoad() {
      if (!PreferencesTab.INSTANCE.autoLoadFloor.value) {
         lastDetectedFloor = null
         return
      }

      if (!LocationTracker.inDungeon) {
         lastDetectedFloor = null
         return
      }

      val floor = LocationTracker.currentFloor
      if (floor == null || floor.isEmpty() || floor == "e") {
         return
      }

      if (floor == lastDetectedFloor) {
         return
      }

      lastDetectedFloor = floor
      val match = FloorUtil.findConfigForFloor(floor) ?: return
      if (match == DungeonConfigTab.INSTANCE.activeConfig.value) {
         return
      }

      if (PreferencesTab.INSTANCE.showTitleAfterLoad.value) {
         this.showFloorTitles(FloorUtil.display(floor), match)
      }

      if (PreferencesTab.INSTANCE.loadChatMessage.value) {
         ChatUtil.send("Found a config for §b" + FloorUtil.display(floor) + "§f - loading §b" + match)
      }

      this.loadConfigByName(match)
   }

   internal fun checkDungeonRevert() {
      if (!PreferencesTab.INSTANCE.revertAfterCompletion.value) {
         dungeonActive = LocationTracker.inDungeon
         configBeforeDungeon = null
         return
      }

      val nowInDungeon = LocationTracker.inDungeon
      if (nowInDungeon && !dungeonActive) {
         configBeforeDungeon = DungeonConfigTab.INSTANCE.activeConfig.value
      } else if (!nowInDungeon && dungeonActive && configBeforeDungeon != null) {
         val target = configBeforeDungeon!!
         configBeforeDungeon = null
         if (target != DungeonConfigTab.INSTANCE.activeConfig.value && ReplacementStore.getConfigFile(target).exists()) {
            if (PreferencesTab.INSTANCE.loadChatMessage.value) {
               ChatUtil.send("Dungeon done §f- reverting to config §b" + target)
            }

            this.loadConfigByName(target)
         }
      }

      dungeonActive = nowInDungeon
   }

   private fun showFloorTitles(floorDisplay: String, configName: String) {
      val minecraft = Minecraft.getInstance()
      minecraft.gui.hud.setTimes(5, 60, 10)
      minecraft.gui.hud.setTitle(
         Component.literal("Found a Config for " + floorDisplay).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(5635925)))
      )
      minecraft.gui.hud.setSubtitle(
         Component.literal("Loading " + configName).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777045)))
      )
   }

   fun createConfig(name: String): Boolean {
      if (name.isBlank()) {
         return false
      }

      val file = ReplacementStore.getConfigFile(name)
      if (file.exists()) {
         return false
      }

      if (!ReplacementStore.configDir.exists()) {
         ReplacementStore.configDir.mkdirs()
      }

      file.writeText("{}", Charsets.UTF_8)
      this.updateConfigDropdown()
      return true
   }

   fun loadConfigByName(name: String): Boolean {
      if (name == DungeonConfigTab.INSTANCE.activeConfig.value) {
         return true
      }

      val file = ReplacementStore.getConfigFile(name)
      if (!file.exists()) {
         return false
      }

      SaveScheduler.awaitPendingSaves()
      ReplacementIO.INSTANCE.saveToFileSync(ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value))
      DirtySections.markAllCachedDirty()
      DungeonConfigTab.INSTANCE.activeConfig.value = name
      lastActiveConfig = name
      ReplacementIO.INSTANCE.loadFrom(file)
      DirtySections.markAllCachedDirty()
      DungeonConfigTab.syncFloorFromStore()
      Config.INSTANCE.save()
      return true
   }

   fun deleteConfig(name: String): Boolean {
      if (name == "default") {
         return false
      }

      val file = ReplacementStore.getConfigFile(name)
      if (!file.exists()) {
         return false
      }

      SaveScheduler.awaitPendingSaves()
      if (DungeonConfigTab.INSTANCE.activeConfig.value == name) {
         SaveScheduler.needsSave = false
      }

      val remapFile = ConfigRemap.INSTANCE.fileFor(name)
      val deleted = file.delete()
      if (deleted) {
         if (remapFile.exists()) {
            remapFile.delete()
         }

         if (DungeonConfigTab.INSTANCE.activeConfig.value == name) {
            DungeonConfigTab.INSTANCE.activeConfig.value = "default"
            lastActiveConfig = "default"
            DirtySections.markAllCachedDirty()
            ReplacementIO.INSTANCE.loadFrom(ReplacementStore.getConfigFile("default"))
            DirtySections.markAllCachedDirty()
            DungeonConfigTab.syncFloorFromStore()
            Config.INSTANCE.save()
         }

         this.updateConfigDropdown()
      }

      return deleted
   }

   fun listConfigs(): List<String> {
      if (!ReplacementStore.configDir.exists()) {
         return listOf("default")
      }

      val files = ReplacementStore.configDir.listFiles()
      if (files != null) {
         val configNames = ArrayList<String>()

         for (file in files) {
            if (file.extension == "json" && !file.name.endsWith("-BlockRemap.json")) {
               configNames.add(file.nameWithoutExtension)
            }
         }

         return configNames.sorted()
      }

      return listOf("default")
   }

   fun updateConfigDropdown() {
   }

   companion object {
      @JvmField
      val INSTANCE: ConfigSwitcher = ConfigSwitcher()
   }
}
