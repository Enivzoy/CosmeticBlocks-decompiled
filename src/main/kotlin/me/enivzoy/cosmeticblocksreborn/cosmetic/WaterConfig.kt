package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab

class WaterConfig private constructor() {
   private var overrideActiveState = false

   internal val overrideActive: Boolean
      get() = overrideActiveState

   internal fun onConfigLoaded(allow: Boolean?, rgb: Int?, lava: Boolean?) {
      overrideActiveState = allow != null || rgb != null || lava != null
      val effectiveAllow = allow ?: GlobalCosmeticsTab.INSTANCE.allowWaterColor.value
      val effectiveRgb = rgb ?: GlobalCosmeticsTab.INSTANCE.rgbWaterColor.value
      val effectiveLava = lava ?: GlobalCosmeticsTab.INSTANCE.lavaAsWater.value
      DungeonConfigTab.applyWater(effectiveAllow, effectiveRgb, effectiveLava)
   }

   internal fun onPerConfigChanged() {
      overrideActiveState = true
      ReplacementIO.INSTANCE.save()
   }

   internal fun onGlobalChanged() {
      if (!overrideActiveState) {
         DungeonConfigTab.applyWater(
            GlobalCosmeticsTab.INSTANCE.allowWaterColor.value,
            GlobalCosmeticsTab.INSTANCE.rgbWaterColor.value,
            GlobalCosmeticsTab.INSTANCE.lavaAsWater.value
         )
      }
   }

   fun allowWaterColor(): Boolean = if (overrideActiveState) {
      DungeonConfigTab.INSTANCE.allowWaterColor.value
   } else {
      GlobalCosmeticsTab.INSTANCE.allowWaterColor.value
   }

   fun rgbWaterColor(): Int {
      val value = if (overrideActiveState) {
         DungeonConfigTab.INSTANCE.rgbWaterColor.value
      } else {
         GlobalCosmeticsTab.INSTANCE.rgbWaterColor.value
      }

      return value and 0xFFFFFF
   }

   fun lavaAsWater(): Boolean = if (overrideActiveState) {
      DungeonConfigTab.INSTANCE.lavaAsWater.value
   } else {
      GlobalCosmeticsTab.INSTANCE.lavaAsWater.value
   }

   fun captureFields(): Fields = Fields(
      if (overrideActiveState) DungeonConfigTab.INSTANCE.allowWaterColor.value else null,
      if (overrideActiveState) DungeonConfigTab.INSTANCE.rgbWaterColor.value else null,
      if (overrideActiveState) DungeonConfigTab.INSTANCE.lavaAsWater.value else null
   )

   class Fields(val allow: Boolean?, val rgb: Int?, val lava: Boolean?) {
      fun hasAny(): Boolean = this.allow != null || this.rgb != null || this.lava != null
   }

   companion object {
      @JvmField
      val INSTANCE: WaterConfig = WaterConfig()
   }
}
