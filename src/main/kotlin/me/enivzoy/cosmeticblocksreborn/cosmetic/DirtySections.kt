package me.enivzoy.cosmeticblocksreborn.cosmetic

import kotlin.collections.ArrayDeque
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos

object DirtySections {
   private val pendingDirtySections = ArrayDeque<Long>()
   private val pendingDirtySet = HashSet<Long>()
   private const val DIRTY_SECTIONS_PER_TICK = 4

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   internal fun hasPending(): Boolean = !pendingDirtySections.isEmpty()

   internal fun markAllCachedDirty() {
      val level = this.mc.level ?: return
      val sections = HashSet<Long>(ReplacementCache.activeCache.size / 4)

      for (packed in ReplacementCache.activeCache.keys) {
         sections.add(this.sectionKey(BlockPos.getX(packed), BlockPos.getY(packed), BlockPos.getZ(packed)))
      }

      this.queueDirtySections(sections)
   }

   internal fun sectionKey(x: Int, y: Int, z: Int): Long = BlockPos.asLong(x shr 4, y shr 4, z shr 4)

   internal fun queueDirtySections(sections: Set<Long>) {
      for (sec in sections) {
         if (pendingDirtySet.add(sec)) {
            pendingDirtySections.addLast(sec)
         }
      }
   }

   internal fun drainDirtySections() {
      val renderer = this.mc.levelRenderer ?: return
      val level = this.mc.level
      if (level == null) {
         pendingDirtySections.clear()
         pendingDirtySet.clear()
         return
      }

      try {
         var count = 0
         while (count < DIRTY_SECTIONS_PER_TICK && !pendingDirtySections.isEmpty()) {
            val sec = pendingDirtySections.removeFirst()
            pendingDirtySet.remove(sec)
            level.setSectionDirtyWithNeighbors(BlockPos.getX(sec), BlockPos.getY(sec), BlockPos.getZ(sec))
            count++
         }
      } catch (ignored: Exception) {
         pendingDirtySections.clear()
         pendingDirtySet.clear()
      }
   }

   internal fun markRegionDirtyByBounds(minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int) {
      if (this.mc.level != null) {
         val sx = (minX - 1) shr 4
         val ex = (maxX + 1) shr 4
         val sy = (minY - 1) shr 4
         val ey = (maxY + 1) shr 4
         val sz = (minZ - 1) shr 4
         val ez = (maxZ + 1) shr 4
         val sections = HashSet<Long>((ex - sx + 1) * (ey - sy + 1) * (ez - sz + 1))

         for (cx in sx..ex) {
            for (cy in sy..ey) {
               for (cz in sz..ez) {
                  sections.add(BlockPos.asLong(cx, cy, cz))
               }
            }
         }

         this.queueDirtySections(sections)
      }
   }

   internal fun markDirty(pos: BlockPos) {
      if (this.mc.level != null) {
         val sections = HashSet<Long>(8)

         for (dx in -1..1) {
            for (dy in -1..1) {
               for (dz in -1..1) {
                  sections.add(this.sectionKey(pos.x + dx, pos.y + dy, pos.z + dz))
               }
            }
         }

         this.queueDirtySections(sections)
      }
   }

   internal fun markDirtyImmediate(pos: BlockPos) {
      val renderer = this.mc.levelRenderer ?: return
      val level = this.mc.level ?: return
      val sections = HashSet<Long>(8)

      for (dx in -1..1) {
         for (dy in -1..1) {
            for (dz in -1..1) {
               sections.add(this.sectionKey(pos.x + dx, pos.y + dy, pos.z + dz))
            }
         }
      }

      try {
         for (sec in sections) {
            level.setSectionDirtyWithNeighbors(BlockPos.getX(sec), BlockPos.getY(sec), BlockPos.getZ(sec))
         }
      } catch (ignored: Exception) {
      }
   }
}
