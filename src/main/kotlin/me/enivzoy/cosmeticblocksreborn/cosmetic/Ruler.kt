package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import java.util.Collections
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.core.BlockPos
import net.minecraft.gizmos.GizmoStyle
import net.minecraft.gizmos.Gizmos
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW

object Ruler {
   private var activeState = false
   private var startPos: BlockPos? = null
   private var previewPositions: List<BlockPos> = Collections.emptyList()
   private var airReach = 4.0
   private var reachTargetPos: BlockPos? = null
   private var rulerKeyWasPressed = false
   private var rightWasPressed = false
   private var leftWasPressed = false

   private fun mc(): Minecraft = Minecraft.getInstance()

   private fun enabled(): Boolean = CosmeticBlocksTab.INSTANCE.enabled.value as Boolean

   private fun rulerKeybind(): Int = (CosmeticBlocksTab.INSTANCE.rulerKeybind.value as Number).toInt()

   val active: Boolean
      get() = activeState

   fun onScroll(scrollY: Double): Boolean {
      if (!activeState || mc().gui.screen() != null) {
         return false
      }

      airReach = Math.max(1.0, Math.min(100.0, airReach + scrollY))
      ChatUtil.success("Ruler reach: ${airReach.toInt()}")
      return true
   }

   fun tick() {
      if (!enabled()) {
         if (activeState) {
            activeState = false
            startPos = null
            previewPositions = Collections.emptyList()
            reachTargetPos = null
         }

         return
      }

      val window = Minecraft.getInstance().window.handle()
      if (window == 0L) {
         return
      }

      if (mc().gui.screen() == null && rulerKeybind() != -1) {
         val pressed = GLFW.glfwGetKey(window, rulerKeybind()) == 1
         if (pressed && !rulerKeyWasPressed) {
            activeState = !activeState
            if (!activeState) {
               startPos = null
               previewPositions = Collections.emptyList()
               reachTargetPos = null
            }

            ChatUtil.send(if (activeState) "Ruler mode §aON §7(reach: ${airReach.toInt()})" else "Ruler mode §cOFF")
         }

         rulerKeyWasPressed = pressed
      } else {
         rulerKeyWasPressed = false
      }

      if (!activeState || mc().gui.screen() != null) {
         return
      }

      reachTargetPos = getAirTargetPos()
      val start = startPos
      if (start != null) {
         val endPos = getTargetPos()
         if (endPos != null) {
            previewPositions = bresenham3D(start, endPos)
         }
      }

      val leftPressed = GLFW.glfwGetMouseButton(window, 0) == 1
      val rightPressed = GLFW.glfwGetMouseButton(window, 1) == 1

      if (rightPressed && !rightWasPressed) {
         if (start == null) {
            val endPos2 = getTargetPos()
            if (endPos2 != null) {
               startPos = endPos2
               ChatUtil.success("Ruler: Start set at ${endPos2.getX()}, ${endPos2.getY()}, ${endPos2.getZ()}")
            }
         } else if (previewPositions.isNotEmpty()) {
            for (blockPos in previewPositions) {
               CosmeticBlocks.INSTANCE.setReplacement(
                  blockPos, CosmeticBlocks.INSTANCE.getSelectedBlock(), CosmeticBlocksTab.INSTANCE.placeMode.value as Boolean
               )
            }

            ChatUtil.success("Ruler: Placed ${previewPositions.size} blocks")
            startPos = null
            previewPositions = Collections.emptyList()
         }
      }

      if (leftPressed && !leftWasPressed && start != null) {
         startPos = null
         previewPositions = Collections.emptyList()
         ChatUtil.warning("Ruler: Cancelled")
      }

      leftWasPressed = leftPressed
      rightWasPressed = rightPressed
      emitGizmos()
   }

   fun onWorldRender(modelView: Matrix4f, projection: Matrix4f, camPos: Vec3) {
   }

   private fun emitGizmos() {
      if (!activeState) {
         return
      }

      try {
         val reachTarget = reachTargetPos
         if (reachTarget != null && startPos == null) {
            Gizmos.cuboid(reachTarget, GizmoStyle.stroke(0x9980FF80.toInt())).setAlwaysOnTop()
         }

         for (blockPos in previewPositions) {
            Gizmos.cuboid(blockPos, GizmoStyle.fill(0x59FFFFFF.toInt())).setAlwaysOnTop()
         }
      } catch (_: IllegalStateException) {
      }
   }

   private fun getTargetPos(): BlockPos? {
      val hit = mc().hitResult
      if (hit is BlockHitResult && hit.getType() === HitResult.Type.BLOCK) {
         return hit.getBlockPos()
      }

      return getAirTargetPos()
   }

   private fun getAirTargetPos(): BlockPos? {
      val player: LocalPlayer = mc().player ?: return null
      val eye: Vec3 = player.getEyePosition(1.0f)
      val look: Vec3 = player.getLookAngle()
      val target = eye.add(look.x * airReach, look.y * airReach, look.z * airReach)
      return BlockPos(Math.floor(target.x).toInt(), Math.floor(target.y).toInt(), Math.floor(target.z).toInt())
   }

   private fun bresenham3D(start: BlockPos, end: BlockPos): List<BlockPos> {
      val result = ArrayList<BlockPos>()
      var x = start.getX()
      var y = start.getY()
      var z = start.getZ()
      val x2 = end.getX()
      val y2 = end.getY()
      val z2 = end.getZ()
      val dx = Math.abs(x2 - x)
      val dy = Math.abs(y2 - y)
      val dz = Math.abs(z2 - z)
      val sx = if (x2 > x) 1 else -1
      val sy = if (y2 > y) 1 else -1
      val sz = if (z2 > z) 1 else -1

      if (dx >= dy && dx >= dz) {
         var ey = 2 * dy - dx
         var ez = 2 * dz - dx

         for (i in 0..dx) {
            result.add(BlockPos(x, y, z))

            if (ey >= 0) {
               y += sy
               ey -= 2 * dx
            }

            if (ez >= 0) {
               z += sz
               ez -= 2 * dx
            }

            ey += 2 * dy
            ez += 2 * dz
            x += sx
         }
      } else if (dy >= dx && dy >= dz) {
         var ex = 2 * dx - dy
         var ez = 2 * dz - dy

         for (i in 0..dy) {
            result.add(BlockPos(x, y, z))

            if (ex >= 0) {
               x += sx
               ex -= 2 * dy
            }

            if (ez >= 0) {
               z += sz
               ez -= 2 * dy
            }

            ex += 2 * dx
            ez += 2 * dz
            y += sy
         }
      } else {
         var ex = 2 * dx - dz
         var ey = 2 * dy - dz

         for (i in 0..dz) {
            result.add(BlockPos(x, y, z))

            if (ex >= 0) {
               x += sx
               ex -= 2 * dz
            }

            if (ey >= 0) {
               y += sy
               ey -= 2 * dz
            }

            ex += 2 * dx
            ey += 2 * dy
            z += sz
         }
      }

      return result
   }
}
