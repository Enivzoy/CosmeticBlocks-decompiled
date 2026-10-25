package me.enivzoy.cosmeticblocksreborn.cosmetic

import net.minecraft.client.Minecraft

object PeekController {
   @Volatile
   var isPeeking: Boolean = false
      private set

   fun setPeeking(value: Boolean) {
      if (this.isPeeking == value) {
         return
      }

      this.isPeeking = value
      this.refreshVisibleSections()
   }

   private fun refreshVisibleSections() {
      val mc = Minecraft.getInstance()
      val level = mc.level ?: return
      val center = mc.player?.blockPosition() ?: return
      val distance = mc.options.getEffectiveRenderDistance() * 16 + 16
      level.setSectionRangeDirty(
         center.x - distance,
         level.minY,
         center.z - distance,
         center.x + distance,
         level.maxY,
         center.z + distance
      )
   }
}
