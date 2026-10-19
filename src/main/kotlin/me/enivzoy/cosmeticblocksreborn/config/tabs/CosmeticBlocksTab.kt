package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting
import me.enivzoy.cosmeticblocksreborn.setting.visibleWhen

class CosmeticBlocksTab private constructor() : SettingsTab("Cosmetic Blocks") {
   val enabled: BooleanSetting
   val keepBlockSize: BooleanSetting
   val onlyInDungeons: BooleanSetting
   val roomScanning: BooleanSetting
   val placeMode: BooleanSetting
   val weFillOnClick: BooleanSetting
   val fillModeLimit: NumberSetting
   val placeModeKeybind: KeybindSetting
   val editModeKeybind: KeybindSetting
   val worldEditKeybind: KeybindSetting
   val blockSelectorKeybind: KeybindSetting
   val blockWheelKeybind: KeybindSetting
   val rulerKeybind: KeybindSetting
   val showReplacedBlocks: BooleanSetting
   val overlayColor: ColorSetting
   val overlayRange: NumberSetting

   init {
      this.enabled = this.bool("Enabled", "Master toggle for cosmetic block replacement.", false)
      this.keepBlockSize = this.bool("Keep Block Size", "Preserves stair/slab shape when replacing blocks.", false)
      this.onlyInDungeons = this.bool("Only in Dungeons", "Limits cosmetic blocks to dungeon areas only.", false)
      this.roomScanning = this.bool("Room Scanning", "Auto-detects rooms so blocks save per-room.", true)
      this.placeMode = this.bool("Place Mode", "Right-click places on air instead of replacing blocks.", false)
      this.weFillOnClick = this.bool("WE Fill on Click", "Auto-fills the region when you set Pos2.", false)
      this.fillModeLimit = this.number(
         "Fill Mode Limit", "Max blocks a single fill-mode flood fill can change.", 256.0, 1.0, 8192.0, 1.0, ""
      )
      this.placeModeKeybind = this.keybind("Place Mode Keybind", "Toggles Place Mode.", -1)
      this.editModeKeybind = this.keybind("Edit Mode Keybind", "Toggles interactive Edit Mode.", -1)
      this.worldEditKeybind = this.keybind("World Edit Keybind", "Toggles region (WorldEdit) selection mode.", -1)
      this.blockSelectorKeybind = this.keybind("Block Selector Keybind", "Opens the block selector.", -1)
      this.blockWheelKeybind = this.keybind("Block Wheel Keybind", "Opens the radial favourites wheel.", -1)
      this.rulerKeybind = this.keybind("Ruler Keybind", "Toggles the ruler tool.", -1)
      this.showReplacedBlocks = this.bool(
         "Show Replaced Blocks", "Renders a wireframe over replaced blocks.", false
      ).visibleWhen { this.enabled.value }
      this.overlayColor = this.color(
         "Overlay Color", "Wireframe overlay colour.", -10172161
      ).visibleWhen { this.enabled.value }
      this.overlayRange = this.number(
         "Overlay Range", "Max distance to render overlays.", 32.0, 8.0, 64.0, 1.0, ""
      ).visibleWhen { this.enabled.value }
      this.subcategory("General")
      this.subcategory("Modes")
      this.subcategory("Keybinds")
      this.subcategory("Overlay")
   }

   companion object {
      @JvmField
      val INSTANCE: CosmeticBlocksTab = CosmeticBlocksTab()
   }
}
