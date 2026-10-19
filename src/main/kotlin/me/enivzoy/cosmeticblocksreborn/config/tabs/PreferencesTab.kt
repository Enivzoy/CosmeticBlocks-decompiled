package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.visibleWhen

class PreferencesTab private constructor() : SettingsTab("Settings") {
   val autoLoadFloor: BooleanSetting
   val revertAfterCompletion: BooleanSetting
   val showTitleAfterLoad: BooleanSetting
   val loadChatMessage: BooleanSetting
   val minimizeConfig: BooleanSetting
   val compressGzip: BooleanSetting
   val compressMinify: BooleanSetting
   val compressDictionary: BooleanSetting
   val compressPalette: BooleanSetting
   val compressRle: BooleanSetting

   init {
      this.autoLoadFloor = this.bool(
         "Auto-load Floor Config", "Loads the config tagged for the current dungeon floor on entry.", true
      )
      this.revertAfterCompletion = this.bool(
         "Revert After Completion", "Switch back to the config that was active before the dungeon started.", true
      ).visibleWhen { this.autoLoadFloor.value }
      this.showTitleAfterLoad = this.bool(
         "Show Title After Load", "Shows a HUD title when a floor config is found and loaded.", true
      ).visibleWhen { this.autoLoadFloor.value }
      this.loadChatMessage = this.bool(
         "Load Chat Message", "Prints a chat line when a config is loaded automatically.", false
      ).visibleWhen { this.autoLoadFloor.value }
      this.subcategory("Auto-load")

      this.subcategory("Minimize Config")
      this.minimizeConfig = this.bool(
         "Minimize Config",
         "Enables the extra compression options below. They only apply when you run /cb migrate <config> --settings.",
         false
      )
      this.compressGzip = this.bool(
         "1 - Gzip JSON", "Deflates the whole file. The strongest option and still readable after unzipping.", true
      ).visibleWhen { this.minimizeConfig.value }
      this.compressMinify = this.bool(
         "2 - No Readability", "Strips all whitespace so the file no longer looks pretty. Smaller, harder to read.", true
      ).visibleWhen { this.minimizeConfig.value }
      this.compressDictionary = this.bool(
         "3 - Block Dictionary",
         "Stores each unique block only once in a table, then refers to it by index.",
         true
      ).visibleWhen { this.minimizeConfig.value }
      this.compressPalette = this.bool(
         "4 - Blockstate Palette",
         "Splits blocks from their state suffix so identical states are shared. Needs the Block Dictionary.",
         false
      ).visibleWhen { this.minimizeConfig.value && this.compressDictionary.value }
      this.compressRle = this.bool(
         "5 - RLE Encoding", "Packs the coordinate ranges into a compact run-length stream instead of nested arrays.", false
      ).visibleWhen { this.minimizeConfig.value }
      this.label("To use these changes please run  /cb migrate \"configname\" --settings", true)
         .visibleWhen { this.minimizeConfig.value }
   }

   companion object {
      @JvmField
      val INSTANCE: PreferencesTab = PreferencesTab()
   }
}
