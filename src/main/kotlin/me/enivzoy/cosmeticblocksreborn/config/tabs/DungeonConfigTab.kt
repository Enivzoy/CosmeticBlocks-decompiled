package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.ConfigRemap
import me.enivzoy.cosmeticblocksreborn.cosmetic.ConfigSwitcher
import me.enivzoy.cosmeticblocksreborn.cosmetic.FloorUtil
import me.enivzoy.cosmeticblocksreborn.cosmetic.ReplacementIO
import me.enivzoy.cosmeticblocksreborn.cosmetic.ReplacementStore
import me.enivzoy.cosmeticblocksreborn.cosmetic.SaveScheduler
import me.enivzoy.cosmeticblocksreborn.cosmetic.WaterConfig
import me.enivzoy.cosmeticblocksreborn.gui.screens.GlobalRemapEditorScreen
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting
import me.enivzoy.cosmeticblocksreborn.setting.onChange
import me.enivzoy.cosmeticblocksreborn.setting.optionColor
import me.enivzoy.cosmeticblocksreborn.setting.optionLabel
import me.enivzoy.cosmeticblocksreborn.setting.visibleWhen
import net.minecraft.client.Minecraft

class DungeonConfigTab private constructor() : SettingsTab("Dungeon Config") {
   val activeConfig: ConfigListSetting = this.configList(
      "Active Config", "Switch between saved block layouts.", "default"
   ) { listConfigOptions() }
      .optionColor { activeConfigColor(it) }
      .optionLabel { activeConfigLabel(it) }

   val configFloor: ConfigListSetting = this.configList(
      "Config Floor", "Which dungeon floor this config is for (auto-loaded on entry).", "None"
   ) { listFloorOptions() }
      .optionColor { floorOptionColor(it) }
      .onChange { onFloorChanged(it) }

   val allowWaterColor: BooleanSetting = this.bool(
      "Allow Water Color", "Tints water with a custom colour for this config.", false
   ).onChange { onPerConfigWaterChanged(it) }

   val rgbWaterColor: ColorSetting = this.color(
      "RGB Water Color", "Custom water tint for this config.", -12618012
   ).onChange { onPerConfigWaterColorChanged(it) }.visibleWhen { allowWaterColorVisible() }

   val lavaAsWater: BooleanSetting = this.bool(
      "Lava as Water", "Makes lava look like water for this config.", false
   ).onChange { onPerConfigWaterChanged(it) }

   val remapEnabled: BooleanSetting = this.bool(
      "Block Remaps", "Render all blocks of a source type as their mapped target for this config.", false
   ).onChange { onRemapEnabledChanged(it) }

   val remapKeepShape: BooleanSetting = this.bool(
      "Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false
   ).onChange { onRemapKeepShapeChanged(it) }.visibleWhen { remapEnabledVisible() }

   val openEditor: ButtonSetting = this.button(
      "Remap Editor", "Add or remove block remaps with a searchable picker.", "Open"
   ) { openEditorAction() }.visibleWhen { remapEnabledVisible() }

   init {
      this.subcategory("Config")
      this.subcategory("Water")
      this.subcategory("Block Remaps")
   }

   companion object {
      private var floorReady = false
      private var floorSyncing = false
      private var syncing = false

      @JvmField
      val INSTANCE: DungeonConfigTab = DungeonConfigTab()

      @JvmStatic
      internal fun applyWater(allow: Boolean?, rgb: Int?, lava: Boolean?) {
         syncing = true
         try {
            if (allow != null) {
               INSTANCE.allowWaterColor.value = allow
            }

            if (rgb != null) {
               INSTANCE.rgbWaterColor.value = rgb
            }

            if (lava != null) {
               INSTANCE.lavaAsWater.value = lava
            }
         } finally {
            syncing = false
         }
      }

      @JvmStatic
      internal fun applyRemap(enabled: Boolean, keepShape: Boolean) {
         syncing = true
         try {
            INSTANCE.remapEnabled.value = enabled
            INSTANCE.remapKeepShape.value = keepShape
         } finally {
            syncing = false
         }
      }

      @JvmStatic
      internal fun beginSync() {
         syncing = true
      }

      @JvmStatic
      internal fun endSync() {
         syncing = false
      }

      private fun onPerConfigWaterChanged(it: Boolean) {
         if (!syncing) {
            WaterConfig.INSTANCE.onPerConfigChanged()
         }
      }

      private fun onPerConfigWaterColorChanged(it: Int) {
         if (!syncing) {
            WaterConfig.INSTANCE.onPerConfigChanged()
         }
      }

      private fun onRemapEnabledChanged(it: Boolean) {
         if (!syncing) {
            ConfigRemap.INSTANCE.onSettingChanged(it, INSTANCE.remapKeepShape.value)
         }
      }

      private fun onRemapKeepShapeChanged(it: Boolean) {
         if (!syncing) {
            ConfigRemap.INSTANCE.onSettingChanged(INSTANCE.remapEnabled.value, it)
         }
      }

      private fun openEditorOnClientThread(mc: Minecraft) {
         mc.setScreenAndShow(GlobalRemapEditorScreen(ConfigRemap.INSTANCE, false))
      }

      private fun openEditorAction() {
         val mc = Minecraft.getInstance()
         mc.execute { openEditorOnClientThread(mc) }
      }

      private fun listConfigOptions(): List<String> {
         val sorted = ArrayList(ConfigSwitcher.INSTANCE.listConfigs())
         sorted.sortWith { a, b ->
            FloorUtil.floorNumber(FloorUtil.floorOfConfig(b)).compareTo(FloorUtil.floorNumber(FloorUtil.floorOfConfig(a)))
         }
         return sorted
      }

      private fun listFloorOptions(): List<String> = FloorUtil.listFloorOptions()

      private fun activeConfigLabel(name: String): String = FloorUtil.configLabel(name)

      private fun activeConfigColor(name: String): Int = FloorUtil.colorForFloor(FloorUtil.floorOfConfig(name))

      private fun floorOptionColor(label: String): Int = FloorUtil.colorForFloor(label)

      private fun onFloorChanged(value: String) {
         if (floorReady && !floorSyncing) {
            val normalized = FloorUtil.normalize(value)
            ReplacementStore.floor = if (normalized == null || normalized == FloorUtil.NONE) null else normalized
            SaveScheduler.awaitPendingSaves()
            ReplacementIO.INSTANCE.saveCurrentAsync(
               ReplacementStore.getConfigFile(DungeonConfigTab.INSTANCE.activeConfig.value)
            )
            Config.INSTANCE.save()
         }
      }

      @JvmStatic
      fun syncFloorFromStore() {
         floorSyncing = true
         try {
            INSTANCE.configFloor.value = FloorUtil.displayOrNone(ReplacementStore.floor)
         } finally {
            floorSyncing = false
         }
      }

      @JvmStatic
      fun markFloorReady() {
         floorReady = true
      }

      private fun allowWaterColorVisible(): Boolean = INSTANCE.allowWaterColor.value

      private fun remapEnabledVisible(): Boolean = INSTANCE.remapEnabled.value
   }
}
