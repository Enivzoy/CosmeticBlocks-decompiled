package me.enivzoy.cosmeticblocksreborn.gui.screens

import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.gui.hud.HudElement
import me.enivzoy.cosmeticblocksreborn.gui.hud.HudManager
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import kotlin.math.abs
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class HudEditorScreen : Screen(Component.literal("HUD Editor")), NvgScreen {
   private val snapThreshold: Int = 6
   private val gridSize: Int = 20
   private var draggingElement: HudElement? = null
   private val activeSnapLinesX: ArrayList<Pair<Int, Int>> = ArrayList()
   private val activeSnapLinesY: ArrayList<Pair<Int, Int>> = ArrayList()
   private val colorCenter: Int = -16711681
   private val colorGrid: Int = -5570646
   private val colorEdge: Int = -256
   private val colorElement: Int = -30720
   private var uiScale: Float = 1f
   private var screenW: Int = 0
   private var screenH: Int = 0

   private fun scaledWidth(): Int = Minecraft.getInstance().window.guiScaledWidth

   private fun scaledHeight(): Int = Minecraft.getInstance().window.guiScaledHeight

   override fun renderOverlay(delta: Float) {
      val minecraft = Minecraft.getInstance()
      val window = minecraft.window
      val fbWidth = window.width
      val fbHeight = window.height
      if (fbWidth > 0 && fbHeight > 0) {
         uiScale = window.guiScale.toFloat().coerceAtLeast(1f)
         screenW = window.guiScaledWidth
         screenH = window.guiScaledHeight
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               draw()
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun draw() {
      val w = screenW.toFloat()
      val h = screenH.toFloat()
      CascadeUi.rect(0f, 0f, w, h, 1610612736, 0f)
      drawGrid(w, h)
      drawSnapLines(w, h)

      for (element in HudManager.getElements()) {
         drawElement(element)
      }

      CascadeUi.text("HUD Editor", 10f, 12f, 14f, -1184275, CascadeUi.Font.BOLD)
      CascadeUi.text("Cyan=Center  Green=Grid  Yellow=Edge  Orange=Element", 10f, 28f, 12f, -6513508, CascadeUi.Font.REGULAR)
      CascadeUi.text("Drag elements \u00c2\u00b7 ESC saves and exits", 10f, 42f, 12f, -10724260, CascadeUi.Font.REGULAR)
   }

   private fun drawGrid(w: Float, h: Float) {
      val centerX = screenW / 2
      val centerY = screenH / 2
      val grid = 637534207
      val center = 1627389951
      var x = gridSize
      while (x < screenW) {
         CascadeUi.line(x.toFloat(), 0f, x.toFloat(), h, if (x == centerX) center else grid, 1f)
         x += gridSize
      }

      var y = gridSize
      while (y < screenH) {
         CascadeUi.line(0f, y.toFloat(), w, y.toFloat(), if (y == centerY) center else grid, 1f)
         y += gridSize
      }

      CascadeUi.line(centerX.toFloat(), 0f, centerX.toFloat(), h, 1358954495, 2f)
      CascadeUi.line(0f, centerY.toFloat(), w, centerY.toFloat(), 1358954495, 2f)
   }

   private fun drawSnapLines(w: Float, h: Float) {
      for ((x, color) in activeSnapLinesX) {
         CascadeUi.line(x.toFloat(), 0f, x.toFloat(), h, color, 2f)
      }

      for ((y, color) in activeSnapLinesY) {
         CascadeUi.line(0f, y.toFloat(), w, y.toFloat(), color, 2f)
      }
   }

   private fun drawElement(element: HudElement) {
      val ex = element.x.toFloat()
      val ey = element.y.toFloat()
      val ew = element.width.toFloat()
      val eh = element.height.toFloat()
      val active = element === draggingElement
      CascadeUi.rect(ex, ey, ew, eh, Int.MIN_VALUE, 0f)
      CascadeUi.rectOutline(ex, ey, ew, eh, if (active) colorCenter else colorElement, 0f, 1f)
      CascadeUi.text(element.id, ex + 4f, ey + eh / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
   }

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      if (event.button() != 0) {
         return false
      }

      val mouseX = event.x().toInt()
      val mouseY = event.y().toInt()

      for (element in HudManager.getElements()) {
         if (element.contains(mouseX, mouseY)) {
            draggingElement = element
            element.startDrag(mouseX, mouseY)
            return true
         }
      }

      return false
   }

   override fun mouseDragged(event: MouseButtonEvent, dragX: Double, dragY: Double): Boolean {
      val mouseX = event.x().toInt()
      val mouseY = event.y().toInt()
      val element = draggingElement
      return if (element != null) {
         element.updateDrag(mouseX, mouseY, scaledWidth(), scaledHeight())
         applySmartSnap(element)
         true
      } else {
         false
      }
   }

   override fun mouseReleased(event: MouseButtonEvent): Boolean {
      if (event.button() == 0) {
         val element = draggingElement
         if (element != null) {
            element.stopDrag()
            draggingElement = null
            activeSnapLinesX.clear()
            activeSnapLinesY.clear()
            return true
         }
      }

      return false
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      return if (event.key() == 256) {
         onClose()
         true
      } else {
         super.keyPressed(event)
      }
   }

   private fun applySmartSnap(element: HudElement) {
      activeSnapLinesX.clear()
      activeSnapLinesY.clear()
      val width = scaledWidth()
      val height = scaledHeight()
      val elementLeft = element.x
      val elementRight = element.x + element.width
      val elementCenterX = element.x + element.width / 2
      val elementTop = element.y
      val elementBottom = element.y + element.height
      val elementCenterY = element.y + element.height / 2
      val centerX = width / 2
      val centerY = height / 2
      val snapTargetsX = ArrayList<Triple<Int, Int, Int>>()
      snapTargetsX.add(Triple(0, 0, colorEdge))
      snapTargetsX.add(Triple(width, width - element.width, colorEdge))
      snapTargetsX.add(Triple(centerX, centerX - element.width / 2, colorCenter))

      var gx = gridSize
      while (gx < width) {
         if (gx != centerX) {
            snapTargetsX.add(Triple(gx, gx - element.width / 2, colorGrid))
         }

         gx += gridSize
      }

      for (other in HudManager.getElements()) {
         if (other !== element) {
            snapTargetsX.add(Triple(other.x, other.x, colorElement))
            snapTargetsX.add(Triple(other.x + other.width, other.x + other.width - element.width, colorElement))
            snapTargetsX.add(Triple(other.x + other.width / 2, other.x + other.width / 2 - element.width / 2, colorElement))
            snapTargetsX.add(Triple(other.x + other.width, other.x + other.width, colorElement))
            snapTargetsX.add(Triple(other.x, other.x - element.width, colorElement))
         }
      }

      for ((target, snapTo, color) in snapTargetsX) {
         if (abs(elementCenterX - target) < snapThreshold) {
            element.x = snapTo
            activeSnapLinesX.add(target to color)
            break
         }

         if (abs(elementLeft - target) < snapThreshold) {
            element.x = target
            activeSnapLinesX.add(target to color)
            break
         }

         if (abs(elementRight - target) < snapThreshold) {
            element.x = target - element.width
            activeSnapLinesX.add(target to color)
            break
         }
      }

      val snapTargetsY = ArrayList<Triple<Int, Int, Int>>()
      snapTargetsY.add(Triple(0, 0, colorEdge))
      snapTargetsY.add(Triple(height, height - element.height, colorEdge))
      snapTargetsY.add(Triple(centerY, centerY - element.height / 2, colorCenter))

      var gy = gridSize
      while (gy < height) {
         if (gy != centerY) {
            snapTargetsY.add(Triple(gy, gy - element.height / 2, colorGrid))
         }

         gy += gridSize
      }

      for (other in HudManager.getElements()) {
         if (other !== element) {
            snapTargetsY.add(Triple(other.y, other.y, colorElement))
            snapTargetsY.add(Triple(other.y + other.height, other.y + other.height - element.height, colorElement))
            snapTargetsY.add(Triple(other.y + other.height / 2, other.y + other.height / 2 - element.height / 2, colorElement))
            snapTargetsY.add(Triple(other.y + other.height, other.y + other.height, colorElement))
            snapTargetsY.add(Triple(other.y, other.y - element.height, colorElement))
         }
      }

      for ((target, snapTo, color) in snapTargetsY) {
         if (abs(elementCenterY - target) < snapThreshold) {
            element.y = snapTo
            activeSnapLinesY.add(target to color)
            break
         }

         if (abs(elementTop - target) < snapThreshold) {
            element.y = target
            activeSnapLinesY.add(target to color)
            break
         }

         if (abs(elementBottom - target) < snapThreshold) {
            element.y = target - element.height
            activeSnapLinesY.add(target to color)
            break
         }
      }
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun onClose() {
      HudManager.savePositions()
      super.onClose()
   }
}
