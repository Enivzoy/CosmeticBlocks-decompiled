package me.enivzoy.cosmeticblocksreborn.gui.screens

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.BlockIconRenderer
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class RadialBlockScreen : Screen(Component.literal("Block Wheel")), NvgScreen {
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private var hoverAnim: FloatArray = FloatArray(0)
   private var hoveredIdx: Int = -1
   private var favs: List<String> = emptyList()
   private val twoPi: Float = (Math.PI * 2).toFloat()

   override fun renderOverlay(delta: Float) {
      val minecraft = Minecraft.getInstance()
      val window = minecraft.window
      val fbWidth = window.width
      val fbHeight = window.height
      if (fbWidth > 0 && fbHeight > 0) {
         uiScale = (window.guiScale * 0.75f).coerceIn(1f, 3f)
         uiWidth = fbWidth / uiScale
         uiHeight = fbHeight / uiScale
         val screenWidth = window.screenWidth
         val screenHeight = window.screenHeight
         val fbScaleX = if (screenWidth > 0) fbWidth.toFloat() / screenWidth else 1f
         val fbScaleY = if (screenHeight > 0) fbHeight.toFloat() / screenHeight else 1f
         val mx = minecraft.mouseHandler.xpos().toFloat() * fbScaleX / uiScale
         val my = minecraft.mouseHandler.ypos().toFloat() * fbScaleY / uiScale
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               CascadeUi.rect(0f, 0f, uiWidth, uiHeight, -1946157056, 0f)
               val cx = uiWidth * 0.5f
               val cy = uiHeight * 0.5f
               val rOuter = Math.min(uiWidth, uiHeight) * 0.3f
               val rInner = rOuter * 0.42f
               val rMid = (rOuter + rInner) * 0.5f
               val favs = try {
                  CosmeticBlocks.INSTANCE.getFavorites()
               } catch (_: Throwable) {
                  emptyList()
               }

               this.favs = favs
               CascadeUi.circle(cx, cy, rOuter, Theme.withAlpha(-183233516, 0.92f))
               if (this.favs.isNotEmpty()) {
                  val dxx = mx - cx
                  val dy = my - cy
                  val dist = Math.sqrt((dxx * dxx + dy * dy).toDouble()).toFloat()
                  val mouseAngle = (Math.atan2(dy.toDouble(), dxx.toDouble()) + Math.PI * 2) % (Math.PI * 2)
                  val n = this.favs.size
                  val sliceAngle = twoPi / n
                  if (hoverAnim.size != n) {
                     hoverAnim = FloatArray(n)
                  }

                  val hovered = if (rInner <= dist && dist <= rOuter + 20f) {
                     val shifted = (mouseAngle + sliceAngle * 0.5f) % twoPi
                     (shifted / sliceAngle).toInt() % n
                  } else {
                     -1
                  }

                  hoveredIdx = hovered
                  for (i in 0 until n) {
                     val target = if (i == hoveredIdx) 1f else 0f
                     hoverAnim[i] = hoverAnim[i] + (target - hoverAnim[i]) * (delta * 12f).coerceIn(0f, 1f)
                  }

                  drawCircleOutline(cx, cy, rOuter, Theme.withAlpha(-1750963, 0.9f), 2f)
                  drawCircleOutline(cx, cy, rInner, Theme.withAlpha(-1750963, 0.4f), 1.5f)

                  for (i in 0 until n) {
                     val a0 = i * sliceAngle - sliceAngle * 0.5f
                     val a1 = a0 + sliceAngle
                     val t = hoverAnim[i]
                     val rOuterE = rOuter + t * 8f
                     if (t > 0.02f) {
                        drawWedge(cx, cy, rInner, rOuterE, a0, a1, Theme.withAlpha(-1750963, 0.18f + 0.35f * t))
                     }

                     val sx1 = cx + rInner * Math.cos(a0.toDouble()).toFloat()
                     val sy1 = cy + rInner * Math.sin(a0.toDouble()).toFloat()
                     val sx2 = cx + rOuterE * Math.cos(a0.toDouble()).toFloat()
                     val sy2 = cy + rOuterE * Math.sin(a0.toDouble()).toFloat()
                     CascadeUi.line(sx1, sy1, sx2, sy2, Theme.withAlpha(-1750963, 0.3f), 1f)
                     val midA = a0 + sliceAngle * 0.5f
                     val lx = cx + rMid * Math.cos(midA.toDouble()).toFloat()
                     val ly = cy + rMid * Math.sin(midA.toDouble()).toFloat()
                     val name = this.favs[i].removePrefix("minecraft:")
                     val shortName = if (name.length > 12) name.take(11) + "..." else name
                     val textCol = if (i == hoveredIdx) -1184275 else -6513508
                     val iconSize = (rOuter - rInner) * 0.5f
                     BlockIconRenderer.draw(this.favs[i], lx, ly - iconSize * 0.4f, iconSize, 1f)
                     CascadeUi.textCentered(shortName, lx, ly + iconSize * 0.5f, 11f, textCol, CascadeUi.Font.REGULAR)
                  }

                  CascadeUi.circle(cx, cy, rInner - 4f, Theme.withAlpha(-14935012, 0.95f))
                  drawCircleOutline(cx, cy, rInner - 4f, Theme.withAlpha(-1750963, 0.6f), 1.5f)
                  val previewId = if (hoveredIdx >= 0) this.favs[hoveredIdx] else CosmeticBlocks.INSTANCE.getSelectedBlock()
                  val previewSize = rInner * 0.9f
                  BlockIconRenderer.draw(previewId, cx, cy - rInner * 0.28f, previewSize, 1f)
                  if (hoveredIdx >= 0) {
                     val fullName = this.favs[hoveredIdx].removePrefix("minecraft:").replace('_', ' ')
                     CascadeUi.textCentered("SELECT", cx, cy + rInner * 0.42f, 12f, -1750963, CascadeUi.Font.REGULAR)
                     CascadeUi.textCentered(fullName, cx, cy + rInner * 0.62f, 12f, -1184275, CascadeUi.Font.BOLD)
                  } else {
                     val current = CosmeticBlocks.INSTANCE.getSelectedBlock().removePrefix("minecraft:").replace('_', ' ')
                     CascadeUi.textCentered("CURRENT", cx, cy + rInner * 0.42f, 12f, -6513508, CascadeUi.Font.REGULAR)
                     CascadeUi.textCentered(current, cx, cy + rInner * 0.62f, 12f, -1184275, CascadeUi.Font.REGULAR)
                  }

                  CascadeUi.textCentered("LMB pick   |   RMB / Esc close", cx, uiHeight - 48f, 12f, -10724260, CascadeUi.Font.REGULAR)
                  return
               }

               CascadeUi.textCentered("no favorites", cx, cy - 12f, 13f, -6513508, CascadeUi.Font.REGULAR)
               CascadeUi.textCentered("right-click any block in the selector to add", cx, cy + 10f, 12f, -10724260, CascadeUi.Font.REGULAR)
               hoveredIdx = -1
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun drawCircleOutline(cx: Float, cy: Float, r: Float, argb: Int, stroke: Float) {
      val segments = 96
      var prevX = cx + r
      var prevY = cy
      var i = 1
      while (true) {
         val a = twoPi * i / segments
         val nx = cx + r * Math.cos(a.toDouble()).toFloat()
         val ny = cy + r * Math.sin(a.toDouble()).toFloat()
         CascadeUi.line(prevX, prevY, nx, ny, argb, stroke)
         prevX = nx
         prevY = ny
         if (i == segments) {
            return
         }

         i++
      }
   }

   private fun drawWedge(cx: Float, cy: Float, rInner: Float, rOuter: Float, a0: Float, a1: Float, argb: Int) {
      val span = a1 - a0
      val steps = (rOuter * span / 2f).toInt().coerceIn(8, 96)
      val stroke = rOuter * span / steps + 1.5f
      var s = 0
      if (s <= steps) {
         while (true) {
            val a = a0 + span * s / steps
            val x1 = cx + rInner * Math.cos(a.toDouble()).toFloat()
            val y1 = cy + rInner * Math.sin(a.toDouble()).toFloat()
            val x2 = cx + rOuter * Math.cos(a.toDouble()).toFloat()
            val y2 = cy + rOuter * Math.sin(a.toDouble()).toFloat()
            CascadeUi.line(x1, y1, x2, y2, argb, stroke)
            if (s == steps) {
               break
            }

            s++
         }
      }
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      when (event.button()) {
         0 -> {
            val count = favs.size
            val hovered = hoveredIdx
            if (hovered >= 0 && hovered < count) {
               CosmeticBlocks.INSTANCE.setBlock(favs[hoveredIdx])
               onClose()
            }

            return true
         }

         1 -> {
            onClose()
            return true
         }

         else -> return true
      }
   }

   override fun mouseReleased(event: MouseButtonEvent): Boolean {
      if (event.button() == 0) {
         val count = favs.size
         val hovered = hoveredIdx
         if (hovered >= 0 && hovered < count) {
            CosmeticBlocks.INSTANCE.setBlock(favs[hoveredIdx])
            onClose()
         }
      }

      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean =
      if (event.key() == 256) {
         onClose()
         true
      } else {
         super.keyPressed(event)
      }
}
