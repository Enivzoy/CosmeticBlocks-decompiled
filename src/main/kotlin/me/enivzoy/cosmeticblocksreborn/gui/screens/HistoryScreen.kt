package me.enivzoy.cosmeticblocksreborn.gui.screens

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class HistoryScreen : Screen(Component.literal("Edit History")), NvgScreen {
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private var mouseX: Float = 0f
   private var mouseY: Float = 0f
   private var px: Float = 0f
   private var py: Float = 0f
   private var scroll: Float = 0f
   private var status: String = ""

   private val rowH: Float = 20f

   private val panelW: Float
      get() = (uiWidth * 0.5f).coerceIn(340f, 520f)

   private val panelH: Float
      get() = (uiHeight * 0.7f).coerceIn(260f, 480f)

   private fun historyInfo(): Pair<Int, List<String>> = try {
      CosmeticBlocks.INSTANCE.historyInfo()
   } catch (_: Throwable) {
      0 to emptyList()
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
         mouseX = minecraft.mouseHandler.xpos().toFloat() * fbScaleX / uiScale
         mouseY = minecraft.mouseHandler.ypos().toFloat() * fbScaleY / uiScale
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               CascadeUi.rect(0f, 0f, uiWidth, uiHeight, 1711276032, 0f)
               val w = panelW
               val h = panelH
               px = (uiWidth - w) / 2f
               py = (uiHeight - h) / 2f
               CascadeUi.shadow(px, py, w, h, 10f, 16f, Int.MIN_VALUE)
               CascadeUi.rect(px, py, w, h, -183233516, 10f)
               CascadeUi.rectOutline(px, py, w, h, 352321535, 10f, 1f)

               val (depth, labels) = historyInfo()
               val total = labels.size
               CascadeUi.text("Edit History", px + 12f, py + 16f, 15f, -1184275, CascadeUi.Font.BOLD)
               CascadeUi.textRight("$depth / $total", px + w - 12f, py + 16f, 12f, -6513508, CascadeUi.Font.REGULAR)

               val btnY = py + 30f
               val btnW = (w - 24f - 8f) / 2f
               val btnH = 22f
               val undoEnabled = depth > 0
               val redoEnabled = depth < total
               val undoHover = inRect(px + 12f, btnY, btnW, btnH)
               val redoHover = inRect(px + 12f + btnW + 8f, btnY, btnW, btnH)

               CascadeUi.rect(px + 12f, btnY, btnW, btnH, if (!undoEnabled) -14935012 else if (undoHover) 318767103 else -13749480, 5f)
               CascadeUi.textCentered("Undo", px + 12f + btnW / 2f, btnY + btnH / 2f, 13f, if (undoEnabled) -1184275 else -7303024, CascadeUi.Font.MEDIUM)
               CascadeUi.rect(px + 12f + btnW + 8f, btnY, btnW, btnH, if (!redoEnabled) -14935012 else if (redoHover) 318767103 else -13749480, 5f)
               CascadeUi.textCentered("Redo", px + 12f + btnW + 8f + btnW / 2f, btnY + btnH / 2f, 13f, if (redoEnabled) -1184275 else -7303024, CascadeUi.Font.MEDIUM)

               val listTop = btnY + btnH + 10f
               val listBottom = py + h - 30f
               renderList(labels, depth, listTop, listBottom)

               val footer = if (status.isEmpty()) "Click a row to jump to that state" else status
               CascadeUi.textCentered(footer, px + w / 2f, py + h - 14f, 12f, -10724260, CascadeUi.Font.REGULAR)
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun renderList(labels: List<String>, depth: Int, listTop: Float, listBottom: Float) {
      val w = panelW
      val viewH = listBottom - listTop
      val totalH = labels.size * rowH
      val maxScroll = (totalH - viewH).coerceAtLeast(0f)
      scroll = scroll.coerceIn(0f, maxScroll)
      CascadeUi.save()
      CascadeUi.intersectScissor(px, listTop, w, viewH)
      try {
         val first = (scroll / rowH).toInt().coerceAtLeast(0)
         val last = ((scroll + viewH) / rowH).toInt().coerceAtMost(labels.size - 1)
         var i = first
         if (i <= last) {
            while (true) {
               val label = labels[i]
               val ry = listTop + i * rowH - scroll
               val number = labels.size - i
               val isCurrent = number == depth
               val hovered = inRect(px + 8f, ry, w - 16f, rowH - 2f)
               val bg = if (isCurrent) 352321535 else if (hovered) 318767103 else 0
               if (bg != 0) {
                  CascadeUi.rect(px + 8f, ry, w - 16f, rowH - 2f, bg, 4f)
               }

               val color = if (number <= depth) -1184275 else -7303024
               CascadeUi.text("$number.  $label", px + 14f, ry + rowH / 2f - 1f, 12f, color, CascadeUi.Font.REGULAR)
               if (isCurrent) {
                  CascadeUi.textRight("current", px + w - 14f, ry + rowH / 2f - 1f, 11f, -1750963, CascadeUi.Font.REGULAR)
               }

               if (i == last) {
                  break
               }

               i++
            }
         }
      } finally {
         CascadeUi.restore()
      }
   }

   private fun inRect(x: Float, y: Float, w: Float, h: Float): Boolean = mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      if (event.button() != 0) {
         return false
      }

      val (depth, labels) = historyInfo()
      val total = labels.size
      val w = panelW
      val btnY = py + 30f
      val btnW = (w - 24f - 8f) / 2f
      val btnH = 22f

      if (inRect(px + 12f, btnY, btnW, btnH)) {
         status = CosmeticBlocks.INSTANCE.undoLastOperation()
         return true
      }

      if (inRect(px + 12f + btnW + 8f, btnY, btnW, btnH)) {
         status = CosmeticBlocks.INSTANCE.redoLastOperation()
         return true
      }

      val listTop = btnY + btnH + 10f
      val listBottom = py + panelH - 30f
      if (mouseY in listTop..listBottom && mouseX in px..(px + w)) {
         val index = ((mouseY - listTop + scroll) / rowH).toInt()
         if (index in labels.indices) {
            val number = total - index
            status = CosmeticBlocks.INSTANCE.jumpHistoryTo(number)
            return true
         }
      }

      return false
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      val listTop = py + 30f + 22f + 10f
      val listBottom = py + panelH - 30f
      if (this.mouseY in listTop..listBottom) {
         scroll = (scroll - scrollY.toFloat() * rowH).coerceAtLeast(0f)
         return true
      }

      return false
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      if (event.key() == 256) {
         onClose()
         return true
      }

      if (event.key() == 90 && (event.modifiers() and 2) != 0) {
         status = CosmeticBlocks.INSTANCE.undoLastOperation()
         return true
      }

      if (event.key() == 89 && (event.modifiers() and 2) != 0) {
         status = CosmeticBlocks.INSTANCE.redoLastOperation()
         return true
      }

      return super.keyPressed(event)
   }
}
