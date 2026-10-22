package me.enivzoy.cosmeticblocksreborn.gui

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.config.Tabs
import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class ClickGuiScreen : Screen(Component.literal("Cosmetic Blocks")), NvgScreen {
   var uiScale: Float = 1f
      private set
   var uiWidth: Float = 0f
      private set
   var uiHeight: Float = 0f
      private set
   private val panels: MutableList<SettingsPanel>
   private val openedAtNanos: Long
   private var tooltipRequest: String? = null
   private var tooltipCurrent: String? = null
   private var tooltipTimer: Float = 0f
   private var tooltipX: Float = 0f
   private var tooltipY: Float = 0f

   init {
      val built = ArrayList<SettingsPanel>()
      var nextX = 16f
      for (tab in Tabs.all) {
         val panel = SettingsPanel(this, tab, nextX, 16f)
         built.add(panel)
         nextX += panel.width + 14f
      }

      this.panels = built.toMutableList()
      this.openedAtNanos = System.nanoTime()
   }

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
         val mouseX = minecraft.mouseHandler.xpos().toFloat() * fbScaleX / uiScale
         val mouseY = minecraft.mouseHandler.ypos().toFloat() * fbScaleY / uiScale
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               tooltipRequest = null
               CascadeUi.rect(0f, 0f, uiWidth, uiHeight, 1711276032, 0f)
               for (panel in panels) {
                  panel.render(mouseX, mouseY, delta)
               }

               renderTooltip(mouseX, mouseY, delta)
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   fun requestTooltip(text: String) {
      tooltipRequest = text
   }

   private fun renderTooltip(mouseX: Float, mouseY: Float, delta: Float) {
      val request = tooltipRequest
      if (request != tooltipCurrent) {
         tooltipCurrent = request
         tooltipTimer = 0f
      }

      if (request != null) {
         tooltipTimer += delta
         if (!(tooltipTimer < 0.55f)) {
            val size = 12f
            val textWidth = CascadeUi.textWidth(request, size, CascadeUi.Font.REGULAR)
            val boxWidth = textWidth + 16f
            val boxHeight = 22f
            tooltipX = (mouseX + 12f).coerceAtMost(uiWidth - boxWidth - 4f)
            tooltipY = (mouseY + 14f).coerceAtMost(uiHeight - boxHeight - 4f)
            CascadeUi.shadow(tooltipX, tooltipY, boxWidth, boxHeight, 5f, 8f, 1711276032)
            CascadeUi.rect(tooltipX, tooltipY, boxWidth, boxHeight, -14935012, 5f)
            CascadeUi.rectOutline(tooltipX, tooltipY, boxWidth, boxHeight, 352321535, 5f, 1f)
            CascadeUi.text(request, tooltipX + 8f, tooltipY + boxHeight / 2f, size, -6513508, CascadeUi.Font.REGULAR)
         }
      }
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun onClose() {
      for (panel in panels) {
         panel.persistState()
      }

      Config.INSTANCE.save()
      super.onClose()
   }

   private fun toUi(scaled: Double): Float = (scaled * Minecraft.getInstance().window.guiScale / uiScale).toFloat()

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      val mouseX = toUi(event.x())
      val mouseY = toUi(event.y())
      for (panel in panels.asReversed()) {
         if (panel.mouseClicked(mouseX, mouseY, event.button())) {
            panels.remove(panel)
            panels.add(panel)
            return true
         }
      }

      return false
   }

   override fun mouseReleased(event: MouseButtonEvent): Boolean {
      for (panel in panels) {
         panel.mouseReleased(event.button())
      }

      return true
   }

   override fun mouseDragged(event: MouseButtonEvent, dragX: Double, dragY: Double): Boolean {
      val mouseX = toUi(event.x())
      val mouseY = toUi(event.y())
      for (panel in panels) {
         panel.mouseDragged(mouseX, mouseY)
      }

      return true
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      val uiX = toUi(mouseX)
      val uiY = toUi(mouseY)
      for (panel in panels.asReversed()) {
         if (panel.mouseScrolled(uiX, uiY, scrollY.toFloat())) {
            return true
         }
      }

      return false
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      for (panel in panels.asReversed()) {
         if (panel.keyPressed(event.key(), event.modifiers())) {
            return true
         }
      }

      if (event.key() == InputConstants.KEY_ESCAPE) {
         onClose()
         return true
      }

      val openKey = GuiTab.INSTANCE.openGuiKeybind.value
      if (openKey != -1 && event.key() == openKey) {
         if (System.nanoTime() - openedAtNanos > 600000000L) {
            onClose()
         }

         return true
      } else {
         return super.keyPressed(event)
      }
   }

   override fun charTyped(event: CharacterEvent): Boolean {
      val text = event.codepointAsString()
      for (panel in panels.asReversed()) {
         if (panel.charTyped(text)) {
            return true
         }
      }

      return false
   }
}
