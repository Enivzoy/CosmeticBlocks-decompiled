package me.enivzoy.cosmeticblocksreborn.gui

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.gui.component.SettingComponent
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi

class SettingsPanel(
   private val gui: ClickGuiScreen,
   val tab: SettingsTab,
   defaultX: Float,
   defaultY: Float,
) {
   var x: Float
   var y: Float
   private var collapsed: Boolean
   private val components: List<SettingComponent<*>>
   private val collapseAnim: Anim
   private var scroll: Float = 0f
   private val scrollAnim: Anim
   private var dragging: Boolean = false
   private var dragOffsetX: Float = 0f
   private var dragOffsetY: Float = 0f
   private var tiltAngle: Float = 0f
   private var tiltVelocity: Float = 0f
   private var smoothedVx: Float = 0f
   private var prevX: Float = 0f
   private var shownContent: Float = 0f

   init {
      val saved = Config.INSTANCE.panelState[tab.name]
      this.components = tab.settings.mapNotNull { SettingComponent.create(gui, it) }
      this.x = saved?.x ?: defaultX
      this.y = saved?.y ?: defaultY
      this.collapsed = saved?.collapsed ?: false
      this.collapseAnim = Anim(if (this.collapsed) 0f else 1f, 14f)
      this.scrollAnim = Anim(0f, 20f)
   }

   val width: Float
      get() = if (tab.name == "Cosmetic Blocks") 280f else 240f

   private fun contentHeight(): Float {
      var h = 6f
      var lastSub = ""
      for (c in components) {
         if (c.setting.visible) {
            val sub = c.setting.subcategory
            if (sub.isNotEmpty() && sub != lastSub) {
               h += 18f
               lastSub = sub
            }

            h += c.getHeight()
         }
      }

      return h
   }

   private fun maxContentHeight(): Float = (gui.uiHeight - y - 36f - 16f).coerceAtLeast(60f)

   fun totalHeight(): Float = 36f + shownContent

   fun contains(mouseX: Float, mouseY: Float): Boolean =
      mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + totalHeight()

   private fun headerHovered(mouseX: Float, mouseY: Float): Boolean =
      mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 36f

   fun render(mouseX: Float, mouseY: Float, delta: Float) {
      if (dragging) {
         x = mouseX - dragOffsetX
         y = mouseY - dragOffsetY
      }

      x = x.coerceIn(4f - width + 40f, gui.uiWidth - 40f)
      y = y.coerceIn(4f, gui.uiHeight - 36f - 4f)
      updateTilt(delta)
      collapseAnim.target = if (collapsed) 0f else 1f
      collapseAnim.update(delta)
      val rawContent = contentHeight().coerceAtMost(maxContentHeight())
      shownContent = rawContent * collapseAnim.value
      val maxScroll = (contentHeight() - maxContentHeight()).coerceAtLeast(0f)
      scroll = scroll.coerceIn(0f, maxScroll)
      scrollAnim.target = scroll
      scrollAnim.update(delta)
      val panelHeight = totalHeight()
      val tilting = tiltAngle != 0f || tiltVelocity != 0f
      if (tilting) {
         val pivotX = x + dragOffsetX
         val pivotY = y + dragOffsetY
         CascadeUi.save()
         CascadeUi.translate(pivotX, pivotY)
         CascadeUi.rotate(tiltAngle)
         CascadeUi.translate(-pivotX, -pivotY)
      }

      CascadeUi.shadow(x, y, width, panelHeight, 10f, 14f, 1493172224)
      CascadeUi.rect(x, y, width, panelHeight, -183233516, 10f)
      renderHeader(mouseX, mouseY)
      if (shownContent > 0.5f) {
         val clipTop = y + 36f
         val clipX = x
         val clipY = clipTop
         val clipW = width
         val clipH = shownContent
         CascadeUi.save()
         CascadeUi.intersectScissor(clipX, clipY, clipW, clipH)

         try {
            var offset = clipTop + 3f - scrollAnim.value
            var lastSub = ""
            for (component in components) {
               if (component.setting.visible) {
                  val sub = component.setting.subcategory
                  if (sub.isNotEmpty() && sub != lastSub) {
                     lastSub = sub
                     if (offset + 18f >= clipTop && offset <= clipTop + shownContent) {
                        val upper = sub.uppercase()
                        CascadeUi.text(upper, x + 14f, offset + 9f, 11f, -10724260, CascadeUi.Font.MEDIUM)
                     }

                     offset += 18f
                  }

                  component.x = x
                  component.y = offset
                  component.width = width
                  if (offset + component.getHeight() >= clipTop && offset <= clipTop + shownContent) {
                     component.render(mouseX, mouseY, delta)
                  }

                  offset += component.getHeight()
               }
            }
         } finally {
            CascadeUi.restore()
         }
      }

      CascadeUi.rectOutline(x, y, width, panelHeight, 352321535, 10f, 1f)
      if (tilting) {
         CascadeUi.restore()
      }
   }

   private fun updateTilt(delta: Float) {
      if (!(delta <= 0f) && !(delta > 0.1f)) {
         val vx = (x - prevX) / delta
         prevX = x
         smoothedVx += (vx - smoothedVx) * (delta * 14f).coerceAtMost(1f)
         val target = if (dragging) (-smoothedVx * 9.0E-5f).coerceIn(-0.14f, 0.14f) else 0f
         tiltVelocity += (target - tiltAngle) * 170f * delta
         tiltVelocity *= Math.exp(-10.5f * delta.toDouble()).toFloat()
         tiltAngle += tiltVelocity * delta
         if (!dragging && Math.abs(tiltAngle) < 4.0E-4f && Math.abs(tiltVelocity) < 4.0E-4f) {
            tiltAngle = 0f
            tiltVelocity = 0f
         }
      } else {
         prevX = x
      }
   }

   private fun renderHeader(mouseX: Float, mouseY: Float) {
      val radius = 10f
      val bottomRadius = if (shownContent > 0.5f) 0f else radius
      CascadeUi.rectVarying(x, y, width, 36f, radius, radius, bottomRadius, bottomRadius, -14935012)
      if (shownContent > 0.5f) {
         CascadeUi.line(x, y + 36f, x + width, y + 36f, 352321535, 1f)
      }

      CascadeUi.text(tab.name, x + 14f, y + 18f, 14f, -1184275, CascadeUi.Font.BOLD)
      val dotColor = if (collapsed) -10724260 else -1750963
      CascadeUi.circle(x + width - 16f, y + 18f, 3f, dotColor)
   }

   fun mouseClicked(mouseX: Float, mouseY: Float, button: Int): Boolean {
      if (headerHovered(mouseX, mouseY)) {
         when (button) {
            InputConstants.MOUSE_BUTTON_LEFT -> {
               dragging = true
               dragOffsetX = mouseX - x
               dragOffsetY = mouseY - y
               smoothedVx = 0f
               prevX = x
            }

            InputConstants.MOUSE_BUTTON_RIGHT -> collapsed = !collapsed
         }

         return true
      } else {
         if (!contains(mouseX, mouseY)) {
            return false
         }

         for (component in components) {
            if (component.setting.visible && component.mouseClicked(mouseX, mouseY, button)) {
               return true
            }
         }

         return true
      }
   }

   fun mouseReleased(button: Int) {
      if (button == InputConstants.MOUSE_BUTTON_LEFT) {
         dragging = false
      }

      for (component in components) {
         component.mouseReleased(button)
      }
   }

   fun mouseDragged(mouseX: Float, mouseY: Float) {
      for (component in components) {
         component.mouseDragged(mouseX, mouseY)
      }
   }

   fun mouseScrolled(mouseX: Float, mouseY: Float, amount: Float): Boolean =
      if (contains(mouseX, mouseY) && !collapsed) {
         scroll -= amount * 24f
         true
      } else {
         false
      }

   fun keyPressed(key: Int, modifiers: Int): Boolean {
      for (component in components) {
         if (component.keyPressed(key, modifiers)) {
            return true
         }
      }

      return false
   }

   fun charTyped(text: String): Boolean {
      for (component in components) {
         if (component.charTyped(text)) {
            return true
         }
      }

      return false
   }

   fun persistState() {
      Config.INSTANCE.panelState[tab.name] = Config.PanelState(x, y, collapsed)
   }
}
