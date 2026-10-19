package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.gizmos.GizmoStyle
import net.minecraft.gizmos.Gizmos
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f

object CosmeticRenderer {
   private val mc: Minecraft
      get() = Minecraft.getInstance()

   private val enabled: Boolean
      get() = CosmeticBlocksTab.INSTANCE.enabled.value

   fun onWorldRender(modelView: Matrix4f, projection: Matrix4f, camPos: Vec3) {
   }

   fun emitGizmos() {
      if (!this.enabled) {
         return
      }

      val p1 = WorldEditMode.pos1
      val p2 = WorldEditMode.pos2
      try {
         if (p1 != null || p2 != null) {
            if (p1 != null && p2 != null) {
               val box = AABB(
                  minOf(p1.x.toDouble(), p2.x.toDouble()), minOf(p1.y.toDouble(), p2.y.toDouble()), minOf(p1.z.toDouble(), p2.z.toDouble()),
                  maxOf(p1.x.toDouble(), p2.x.toDouble()) + 1.0, maxOf(p1.y.toDouble(), p2.y.toDouble()) + 1.0, maxOf(p1.z.toDouble(), p2.z.toDouble()) + 1.0
               )
               Gizmos.cuboid(box, GizmoStyle.strokeAndFill(0xCC4DFF4D.toInt(), 2.0f, 0x264DFF4D)).setAlwaysOnTop()
            } else {
               val p = p1 ?: p2
               if (p != null) {
                  Gizmos.cuboid(p, GizmoStyle.stroke(0xCCFFFF00.toInt(), 2.0f)).setAlwaysOnTop()
               }
            }
         }

         if (p1 != null && p2 != null && CosmeticBlocksTab.INSTANCE.showReplacedBlocks.value && ReplacementCache.activeCache.isNotEmpty()) {
            val selMinX = minOf(p1.x, p2.x)
            val selMinY = minOf(p1.y, p2.y)
            val selMinZ = minOf(p1.z, p2.z)
            val selMaxX = maxOf(p1.x, p2.x)
            val selMaxY = maxOf(p1.y, p2.y)
            val selMaxZ = maxOf(p1.z, p2.z)
            val range = CosmeticBlocksTab.INSTANCE.overlayRange.value.toDouble()
            val rangeSq = range * range
            val color = CosmeticBlocksTab.INSTANCE.overlayColor.value.toInt()
            val argb = 0x99 or (color and 0xFFFFFF)
            val cam = mc.player?.getEyePosition(1.0f) ?: Vec3.ZERO

            for (packed in ReplacementCache.activeCache.keys) {
               val bx = BlockPos.getX(packed)
               val by = BlockPos.getY(packed)
               val bz = BlockPos.getZ(packed)
               if (bx < selMinX || bx > selMaxX || by < selMinY || by > selMaxY || bz < selMinZ || bz > selMaxZ) {
                  continue
               }

               val dx = bx + 0.5 - cam.x
               val dy = by + 0.5 - cam.y
               val dz = bz + 0.5 - cam.z
               if (dx * dx + dy * dy + dz * dz > rangeSq) {
                  continue
               }

               Gizmos.cuboid(AABB(bx.toDouble(), by.toDouble(), bz.toDouble(), bx + 1.0, by + 1.0, bz + 1.0), GizmoStyle.stroke(argb, 1.0f)).setAlwaysOnTop()
            }
         }
      } catch (_: IllegalStateException) {
      }
   }
}
