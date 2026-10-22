package me.enivzoy.cosmeticblocksreborn.gui.screens

import com.mojang.blaze3d.platform.InputConstants
import java.util.HashMap
import java.util.Locale
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.Tabs
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class KeybindEditorScreen : Screen(Component.literal("Keybinds")), NvgScreen {
   private val allRows: List<Row> = Tabs.all.flatMap { tab ->
      tab.settings.filterIsInstance<KeybindSetting>().map { Row(tab.name, it) }
   }
   private var uiScale: Float = 0f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private val panelWidth: Float = 440f
   private val panelHeight: Float = 400f
   private var panelX: Float = 0f
   private var panelY: Float = 0f
   private val headerHeight: Float = 44f
   private val searchRowHeight: Float = 34f
   private val rowHeight: Float = 28f
   private var search: String = ""
   private var searchFocused: Boolean = false
   private var capturing: KeybindSetting? = null
   private var scroll: Float = 0f
   private var maxScroll: Float = 0f
   private var caretBlink: Float = 0f
   private var listTop: Float = 0f
   private var listBottom: Float = 0f
   private val rowHover: HashMap<String, Anim> = HashMap()

   private fun filtered(): List<Row> {
      val q = search.trim().lowercase(Locale.ROOT)
      if (q.isEmpty()) {
         return allRows
      }

      return allRows.filter { row ->
         row.tabName.lowercase(Locale.ROOT).contains(q, false) || row.setting.name.lowercase(Locale.ROOT).contains(q, false)
      }
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
         caretBlink += delta
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               draw(mouseX, mouseY, delta)
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun draw(mouseX: Float, mouseY: Float, delta: Float) {
      panelX = (uiWidth - panelWidth) / 2f
      panelY = (uiHeight - panelHeight) / 2f
      CascadeUi.rect(0f, 0f, uiWidth, uiHeight, 1711276032, 0f)
      CascadeUi.shadow(panelX, panelY, panelWidth, panelHeight, 10f, 18f, Int.MIN_VALUE)
      CascadeUi.rect(panelX, panelY, panelWidth, panelHeight, -183233516, 10f)
      CascadeUi.rectOutline(panelX, panelY, panelWidth, panelHeight, 352321535, 10f, 1f)
      CascadeUi.rectVarying(panelX, panelY, panelWidth, headerHeight, 10f, 10f, 0f, 0f, -14935012)
      CascadeUi.text("Keybinds", panelX + 10f + 4f, panelY + 16f, 14f, -1184275, CascadeUi.Font.BOLD)
      CascadeUi.text("Click a row to rebind \u00c2\u00b7 ESC/DEL clears", panelX + 10f + 4f, panelY + 33f, 12f, -10724260, CascadeUi.Font.REGULAR)
      val closeSize = 18f
      val closeX = panelX + panelWidth - closeSize - 10f
      val closeY = panelY + (headerHeight - closeSize) / 2f
      val closeHover = inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize)
      CascadeUi.textCentered(
         "x", closeX + closeSize / 2f, closeY + closeSize / 2f, 14f, if (closeHover) -1750963 else -6513508, CascadeUi.Font.BOLD
      )
      val searchY = panelY + headerHeight
      val fieldX = panelX + 10f
      val fieldY = searchY + (searchRowHeight - 24f) / 2f
      val fieldWidth = panelWidth - 20f
      val fieldHeight = 24f
      CascadeUi.rect(fieldX, fieldY, fieldWidth, fieldHeight, 1711276032, 5f)
      CascadeUi.rectOutline(fieldX, fieldY, fieldWidth, fieldHeight, if (searchFocused) -1750963 else 352321535, 5f, 1f)
      val showCaret = searchFocused && caretBlink % 1f < 0.5f
      val display = if (search.isEmpty() && !searchFocused) "filter keybinds\u00e2\u20ac\u00a6" else search + (if (showCaret) "|" else "")
      val tc = if (search.isEmpty() && !searchFocused) -10724260 else -1184275
      CascadeUi.text(display, fieldX + 8f, fieldY + fieldHeight / 2f, 13f, tc, CascadeUi.Font.REGULAR)
      listTop = searchY + searchRowHeight + 2f
      listBottom = panelY + panelHeight - 10f
      val listX = panelX + 10f
      val listWidth = panelWidth - 20f
      val listHeight = listBottom - listTop
      val rows = filtered()
      val contentHeight = rows.size * rowHeight
      maxScroll = (contentHeight - listHeight).coerceAtLeast(0f)
      scroll = scroll.coerceIn(0f, maxScroll)
      CascadeUi.save()
      CascadeUi.intersectScissor(listX, listTop, listWidth, listHeight)

      try {
         if (rows.isEmpty()) {
            CascadeUi.textCentered("No matches.", panelX + panelWidth / 2f, listTop + 24f, 13f, -10724260, CascadeUi.Font.REGULAR)
         } else {
            var rowY = listTop - scroll
            var currentTab = ""
            for (row in rows) {
               if (rowY + rowHeight >= listTop && rowY <= listBottom) {
                  drawRow(row, listX, rowY, listWidth, mouseX, mouseY, delta, row.tabName != currentTab)
               }

               currentTab = row.tabName
               rowY += rowHeight
            }
         }
      } finally {
         CascadeUi.restore()
      }
   }

   private fun drawRow(row: Row, x: Float, y: Float, w: Float, mouseX: Float, mouseY: Float, delta: Float, sectionStart: Boolean) {
      val key = "${row.tabName}/${row.setting.name}"
      val hovered = inside(mouseX, mouseY, x, y, w, rowHeight) && mouseY >= listTop && mouseY <= listBottom
      val anim = rowHover.getOrPut(key) { Anim(0f, 14f) }
      anim.target = if (hovered) 1f else 0f
      anim.update(delta)
      if (anim.value > 0.001f) {
         CascadeUi.rect(x, y, w, rowHeight, Theme.withAlpha(-1, anim.value * 0.07f), 5f)
      }

      val label = "${row.tabName} \u00c2\u00b7 ${row.setting.name}"
      CascadeUi.text(label, x + 8f, y + rowHeight / 2f, 13f, -1184275, CascadeUi.Font.REGULAR)
      val btnW = 110f
      val btnH = 20f
      val btnX = x + w - btnW - 6f
      val btnY = y + (rowHeight - btnH) / 2f
      val isCapturing = capturing === row.setting
      val text = if (isCapturing) "press a key\u00e2\u20ac\u00a6" else row.setting.displayName()
      val bg = if (isCapturing) 1088768077 else (if (hovered) -14013910 else 1711276032)
      val fg = if (isCapturing) -1750963 else (if (!row.setting.bound) -10724260 else -1184275)
      CascadeUi.rect(btnX, btnY, btnW, btnH, bg, 5f)
      CascadeUi.textCentered(text, btnX + btnW / 2f, btnY + btnH / 2f, 12f, fg, CascadeUi.Font.MEDIUM)
   }

   private fun toUi(scaled: Double): Float = (scaled * Minecraft.getInstance().window.guiScale / uiScale).toFloat()

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      if (event.button() != InputConstants.MOUSE_BUTTON_LEFT) {
         return false
      }

      val mx = toUi(event.x())
      val my = toUi(event.y())
      val closeSize = 18f
      val closeX = panelX + panelWidth - closeSize - 10f
      val closeY = panelY + (headerHeight - closeSize) / 2f
      if (inside(mx, my, closeX, closeY, closeSize, closeSize)) {
         onClose()
         return true
      }

      val wasCapturing = capturing
      capturing = null
      val searchY = panelY + headerHeight
      val fieldX = panelX + 10f
      val fieldY = searchY + (searchRowHeight - 24f) / 2f
      val fieldWidth = panelWidth - 20f
      val fieldHeight = 24f
      searchFocused = inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight)
      if (searchFocused) {
         return true
      }

      if (my >= listTop && my <= listBottom) {
         val listX = panelX + 10f
         val listWidth = panelWidth - 20f
         var rowY = listTop - scroll
         for (row in filtered()) {
            if (inside(mx, my, listX, rowY, listWidth, rowHeight)) {
               capturing = if (wasCapturing === row.setting) null else row.setting
               return true
            }

            rowY += rowHeight
         }
      }

      return true
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      scroll = (scroll - scrollY.toFloat() * 28f).coerceIn(0f, maxScroll)
      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      val key = event.key()
      val target = capturing
      if (target != null) {
         when (key) {
            InputConstants.KEY_ESCAPE, InputConstants.KEY_BACKSPACE, InputConstants.KEY_DELETE -> target.clear()
            else -> target.value = key
         }

         capturing = null
         Config.INSTANCE.save()
         return true
      } else {
         if (searchFocused) {
            when (key) {
               InputConstants.KEY_ESCAPE -> {
                  searchFocused = false
                  return true
               }

               InputConstants.KEY_BACKSPACE -> {
                  if (search.isNotEmpty()) {
                     search = search.dropLast(1)
                  }

                  return true
               }
            }
         }

         return if (key == InputConstants.KEY_ESCAPE) {
            onClose()
            true
         } else {
            super.keyPressed(event)
         }
      }
   }

   override fun charTyped(event: CharacterEvent): Boolean {
      if (!searchFocused) {
         return false
      }

      search = search + event.codepointAsString()
      scroll = 0f
      return true
   }

   private fun inside(mx: Float, my: Float, x: Float, y: Float, w: Float, h: Float): Boolean =
      mx >= x && mx <= x + w && my >= y && my <= y + h

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun onClose() {
      Config.INSTANCE.save()
      if (minecraft != null) {
         minecraft.setScreenAndShow(ClickGuiScreen())
      }
   }

   private data class Row(val tabName: String, val setting: KeybindSetting)
}
