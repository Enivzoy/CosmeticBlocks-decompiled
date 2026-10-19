package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting

class GuiTab private constructor() : SettingsTab("GUI") {
   val openGuiKeybind: KeybindSetting
   val openCliKeybind: KeybindSetting
   val freecamKeybind: KeybindSetting

   init {
      this.openGuiKeybind = this.keybind("Open GUI Keybind", "Opens this settings screen.", 344)
      this.openCliKeybind = this.keybind("Open CLI Keybind", "Opens the command-line overlay.", -1)
      this.freecamKeybind = this.keybind("Freecam Keybind", "Toggles the freecam.", 70)
      this.subcategory("Keybinds")
   }

   companion object {
      @JvmField
      val INSTANCE: GuiTab = GuiTab()
   }
}
