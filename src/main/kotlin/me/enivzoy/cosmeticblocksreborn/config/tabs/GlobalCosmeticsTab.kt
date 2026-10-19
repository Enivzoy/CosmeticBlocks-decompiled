package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.GlobalRemap
import me.enivzoy.cosmeticblocksreborn.cosmetic.WaterConfig
import me.enivzoy.cosmeticblocksreborn.gui.screens.GlobalRemapEditorScreen
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting
import me.enivzoy.cosmeticblocksreborn.setting.onChange
import me.enivzoy.cosmeticblocksreborn.setting.visibleWhen
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft

class GlobalCosmeticsTab private constructor() : SettingsTab("Global Cosmetics") {
   val allowWaterColor: BooleanSetting = this.bool(
      "Allow Water Color", "Tints water with a custom colour for configs that do not set their own.", false
   ).onChange { onGlobalChanged(it) }

   val rgbWaterColor: ColorSetting = this.color(
      "RGB Water Color", "Custom water tint used when a config does not set its own.", -12618012
   ).onChange { onGlobalColorChanged(it) }.visibleWhen { allowWaterColorVisible() }

   val lavaAsWater: BooleanSetting = this.bool(
      "Lava as Water", "Makes lava look like water for configs that do not set their own.", false
   ).onChange { onGlobalChanged(it) }

   val globalRemapEnabled: BooleanSetting = this.bool(
      "Enabled", "Render all blocks of a source type as their mapped target.", false
   ).onChange { onRemapEnabledChanged(it) }

   val globalRemapKeepShape: BooleanSetting = this.bool(
      "Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false
   ).onChange { onRemapKeepShapeChanged(it) }

   val openEditor: ButtonSetting = this.button(
      "Remap Editor", "Add or remove block remaps with a searchable picker.", "Open"
   ) { openEditorAction() }

   val export: ButtonSetting = this.button(
      "Export", "Copy all remaps to the clipboard as JSON.", "Copy"
   ) { exportAction() }

   val importBtn: ButtonSetting = this.button(
      "Import", "Replace all remaps from clipboard JSON.", "Paste"
   ) { importAction() }

   init {
      this.subcategory("Water")
      this.subcategory("Block Remaps")
      this.subcategory("Editor")
      this.subcategory("Share")
   }

   companion object {
      @JvmField
      val INSTANCE: GlobalCosmeticsTab = GlobalCosmeticsTab()

      private fun allowWaterColorVisible(): Boolean = INSTANCE.allowWaterColor.value

      private fun onGlobalChanged(it: Boolean) {
         WaterConfig.INSTANCE.onGlobalChanged()
      }

      private fun onGlobalColorChanged(it: Int) {
         WaterConfig.INSTANCE.onGlobalChanged()
      }

      private fun onRemapEnabledChanged(it: Boolean) {
         GlobalRemap.rebuild()
      }

      private fun onRemapKeepShapeChanged(it: Boolean) {
         GlobalRemap.rebuild()
      }

      private fun openEditorOnClientThread(mc: Minecraft) {
         mc.setScreenAndShow(GlobalRemapEditorScreen(GlobalRemap, true))
      }

      private fun openEditorAction() {
         val mc = Minecraft.getInstance()
         mc.execute { openEditorOnClientThread(mc) }
      }

      private fun exportAction() {
         val mc = Minecraft.getInstance()
         mc.keyboardHandler.clipboard = GlobalRemap.exportJson()
         ChatUtil.success("Copied " + GlobalRemap.sources().size + " remaps to clipboard")
      }

      private fun importAction() {
         val mc = Minecraft.getInstance()
         val json = mc.keyboardHandler.clipboard
         if (GlobalRemap.importJson(json)) {
            ChatUtil.success("Imported " + GlobalRemap.sources().size + " remaps")
         } else {
            ChatUtil.error("Clipboard is not a valid remap export")
         }
      }
   }
}
