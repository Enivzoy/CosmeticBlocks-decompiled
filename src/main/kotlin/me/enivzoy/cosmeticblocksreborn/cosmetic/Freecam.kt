package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.lwjgl.glfw.GLFW

object Freecam {
   private const val SPEED = 0.7
   private const val LOOK_SENSITIVITY = 0.15f
   private const val REACH = 5.0

   private var activeState = false
   private var xState = 0.0
   private var yState = 0.0
   private var zState = 0.0
   private var yawState = 0.0f
   private var pitchState = 0.0f
   private var keyWasPressed = false

   val isActive: Boolean
      get() = activeState

   val x: Double
      get() = xState

   val y: Double
      get() = yState

   val z: Double
      get() = zState

   val yaw: Float
      get() = yawState

   val pitch: Float
      get() = pitchState

   fun toggle() {
      setActive(!activeState)
   }

   fun setActive(value: Boolean) {
      val mc = Minecraft.getInstance()
      if (value && mc.player == null) {
         return
      }

      if (value == activeState) {
         return
      }

      activeState = value
      if (value) {
         val player: LocalPlayer = mc.player ?: return
         val eye: Vec3 = player.getEyePosition(1.0f)
         xState = eye.x
         yState = eye.y
         zState = eye.z
         yawState = player.getYRot()
         pitchState = player.getXRot()
      }

      ChatUtil.send(if (activeState) "Freecam §aON" else "Freecam §cOFF")
   }

   fun tick() {
      val mc = Minecraft.getInstance()
      val window = mc.window.handle()

      if (mc.gui.screen() == null && window != 0L) {
         val key = (GuiTab.INSTANCE.freecamKeybind.value as Number).toInt()
         if (key != -1) {
            val down = GLFW.glfwGetKey(window, key) == 1
            if (down && !keyWasPressed) {
               toggle()
            }

            keyWasPressed = down
         } else {
            keyWasPressed = false
         }
      } else {
         keyWasPressed = false
      }

      if (!activeState) {
         return
      }

      if (mc.player == null || mc.level == null) {
         setActive(false)
      }
   }

   fun getLookVector(): Vec3 {
      val yawRad = Math.toRadians(yawState.toDouble())
      val pitchRad = Math.toRadians(pitchState.toDouble())
      return Vec3(
         -Math.sin(yawRad) * Math.cos(pitchRad), -Math.sin(pitchRad), Math.cos(yawRad) * Math.cos(pitchRad)
      )
   }

   fun raycast(): HitResult? {
      val mc = Minecraft.getInstance()
      val player = mc.player ?: return null
      val level = mc.level ?: return null
      val from = Vec3(xState, yState, zState)
      val to = from.add(getLookVector().scale(REACH))
      return level.clip(ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player))
   }

   fun onFrame(realtimeDeltaTicks: Float) {
      if (!activeState) {
         return
      }

      val mc = Minecraft.getInstance()
      if (mc.player == null || mc.gui.screen() != null) {
         return
      }

      val yawRad = Math.toRadians(yawState.toDouble())
      val pitchRad = Math.toRadians(pitchState.toDouble())
      val forwardX = -Math.sin(yawRad) * Math.cos(pitchRad)
      val forwardY = -Math.sin(pitchRad)
      val forwardZ = Math.cos(yawRad) * Math.cos(pitchRad)
      val rightX = Math.cos(yawRad)
      val rightZ = Math.sin(yawRad)
      var moveX = 0.0
      var moveY = 0.0
      var moveZ = 0.0

      if (mc.options.keyUp.isDown()) {
         moveX += forwardX
         moveY += forwardY
         moveZ += forwardZ
      }

      if (mc.options.keyDown.isDown()) {
         moveX -= forwardX
         moveY -= forwardY
         moveZ -= forwardZ
      }

      if (mc.options.keyRight.isDown()) {
         moveX += rightX
         moveZ += rightZ
      }

      if (mc.options.keyLeft.isDown()) {
         moveX -= rightX
         moveZ -= rightZ
      }

      if (mc.options.keyJump.isDown()) {
         moveY += 1.0
      }

      if (mc.options.keyShift.isDown()) {
         moveY -= 1.0
      }

      val length = Math.sqrt(moveX * moveX + moveY * moveY + moveZ * moveZ)
      if (length > 0.0) {
         val speed = SPEED * realtimeDeltaTicks.toDouble()
         xState += (moveX / length) * speed
         yState += (moveY / length) * speed
         zState += (moveZ / length) * speed
      }
   }

   fun addLook(dx: Double, dy: Double) {
      yawState += (dx * LOOK_SENSITIVITY.toDouble()).toFloat()
      pitchState += (dy * LOOK_SENSITIVITY.toDouble()).toFloat()

      if (pitchState > 90.0f) {
         pitchState = 90.0f
      } else if (pitchState < -90.0f) {
         pitchState = -90.0f
      }
   }
}
