package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.visibleWhen

class PreferencesTab private constructor() : SettingsTab("Settings") {
   val autoLoadFloor: BooleanSetting
   val revertAfterCompletion: BooleanSetting
   val showTitleAfterLoad: BooleanSetting
   val loadChatMessage: BooleanSetting

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
   }

   companion object {
      @JvmField
      val INSTANCE: PreferencesTab = PreferencesTab()
   }
}
