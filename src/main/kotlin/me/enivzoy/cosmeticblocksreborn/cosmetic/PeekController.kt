package me.enivzoy.cosmeticblocksreborn.cosmetic

import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos

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
      val center = mc.player?.blockPosition() ?: return
      if (!SectionIndex.hasAny()) {
         return
      }

      val distance = ((mc.options.effectiveRenderDistance + 2) shl 4)
      val minX = center.x - distance
      val maxX = center.x + distance
      val minZ = center.z - distance
      val maxZ = center.z + distance
      val dirty = HashSet<Long>()

      for (sec in SectionIndex.occupiedSections()) {
         val x = BlockPos.getX(sec)
         val z = BlockPos.getZ(sec)
         if (x in minX..maxX && z in minZ..maxZ) {
            dirty.add(sec)
         }
      }

      DirtySections.queueDirtySections(dirty)
   }
}
