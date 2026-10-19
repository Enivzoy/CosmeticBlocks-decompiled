package me.enivzoy.cosmeticblocksreborn.gui.screens

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier

class BlockSelectorScreen : Screen(Component.literal("Block Selector")), NvgScreen {
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private var allBlocks: List<String> = emptyList()
   private var filtered: List<String> = emptyList()
   private var lastFilterKey: String = "__none__"
   private var search: String = ""
   private var highlightIdx: Int = 0
   private var lastHighlightIdx: Int = -1
   private var scroll: Float = 0f
   private var mouseX: Float = 0f
   private var mouseY: Float = 0f
   private var blink: Float = 0f
   private val rowH: Float = 18f
   private var px: Float = 0f
   private var py: Float = 0f
   private var listTop: Float = 0f
   private var listBottom: Float = 0f
   private var favStripBottom: Float = 0f
   private var favRects: List<Triple<String, Float, Float>> = emptyList()

   private val panelW: Float
      get() = (uiWidth * 0.55f).coerceIn(360f, 560f)

   private val panelH: Float
      get() = (uiHeight * 0.75f).coerceIn(280f, 540f)

   private fun ensureBlocks() {
      if (allBlocks.isEmpty()) {
         val blockKeys = BuiltInRegistries.BLOCK.keySet()
         allBlocks = blockKeys.map { it.toString() }.sorted()
         applyFilter(true)
      }
   }

   private fun applyFilter(force: Boolean) {
      val q = search.trim().lowercase()
      if (force || q != lastFilterKey) {
         lastFilterKey = q
         filtered = if (q.isEmpty()) allBlocks else allBlocks.filter { it.contains(q) }
         highlightIdx = highlightIdx.coerceIn(0, (filtered.size - 1).coerceAtLeast(0))
      }
   }

   override fun renderOverlay(delta: Float) {
      ensureBlocks()
      applyFilter(false)
      blink += delta
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
               var cy = py + 10f
               CascadeUi.text("Brush Block", px + 10f, cy + 7f, 14f, -1184275, CascadeUi.Font.BOLD)
               CascadeUi.textRight(
                  "Current: ${prettify(CosmeticBlocks.INSTANCE.getSelectedBlock())}", px + w - 10f, cy + 7f, 12f, -6513508, CascadeUi.Font.REGULAR
               )
               cy += 22f
               val searchH = 22f
               CascadeUi.rect(px + 10f, cy, w - 20f, searchH, 1711276032, 5f)
               CascadeUi.rectOutline(px + 10f, cy, w - 20f, searchH, 352321535, 5f, 1f)
               val searchText = if (search.isEmpty()) "type to filter..." else search
               val searchTextColor = if (search.isEmpty()) -10724260 else -1184275
               CascadeUi.text(searchText, px + 10f + 8f, cy + searchH / 2f, 13f, searchTextColor, CascadeUi.Font.REGULAR)
               if (blink % 1f < 0.55f) {
                  val caretX = px + 10f + 8f + CascadeUi.textWidth(search, 13f, CascadeUi.Font.REGULAR)
                  CascadeUi.rect(caretX + 1f, cy + searchH / 2f - 6f, 1.2f, 12f, -1750963, 0f)
               }

               cy += searchH + 6f
               val favs = try {
                  CosmeticBlocks.INSTANCE.getFavorites()
               } catch (_: Throwable) {
                  emptyList()
               }

               favRects = emptyList()
               if (favs.isNotEmpty()) {
                  CascadeUi.text("FAVORITES", px + 10f, cy + 6f, 12f, -10724260, CascadeUi.Font.REGULAR)
                  cy += 14f
                  val chips = ArrayList<Triple<String, Float, Float>>()
                  var chipX = px + 10f
                  val chipH = 18f
                  val maxX = px + w - 10f
                  for (id in favs) {
                     val label = "\u00e2\u02dc\u2026 ${prettify(id)}"
                     val cw = CascadeUi.textWidth(label, 12f, CascadeUi.Font.REGULAR) + 14f
                     if (chipX + cw > maxX) {
                        break
                     }

                     val hovered = (chipX <= mouseX && mouseX <= chipX + cw) && (cy <= mouseY && mouseY <= cy + chipH)
                     CascadeUi.rect(chipX, cy, cw, chipH, if (hovered) 318767103 else -14935012, 4f)
                     CascadeUi.text(label, chipX + 7f, cy + chipH / 2f, 12f, -1750963, CascadeUi.Font.REGULAR)
                     chips.add(Triple(id, chipX, cw))
                     chipX += cw + 6f
                  }

                  favRects = chips
                  cy += chipH + 8f
               }

               favStripBottom = cy
               CascadeUi.text(
                  "${filtered.size} result${if (filtered.size == 1) "" else "s"}", px + 10f, cy + 6f, 12f, -10724260, CascadeUi.Font.REGULAR
               )
               cy += 16f
               listTop = cy
               listBottom = py + h - 24f
               renderList(favs)
               CascadeUi.textCentered(
                  "Up/Down navigate   |   Enter confirm   |   Right-click toggle favorite",
                  px + w / 2f,
                  py + h - 12f,
                  12f,
                  -10724260,
                  CascadeUi.Font.REGULAR
               )
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun renderList(favs: List<String>) {
      val w = panelW
      val viewH = listBottom - listTop
      val totalH = filtered.size * rowH
      val maxScroll = (totalH - viewH).coerceAtLeast(0f)
      scroll = scroll.coerceIn(0f, maxScroll)
      if (highlightIdx != lastHighlightIdx) {
         lastHighlightIdx = highlightIdx
         val hlY = highlightIdx * rowH - scroll
         if (hlY < 0f) {
            scroll = (highlightIdx * rowH).coerceAtLeast(0f)
         } else if (hlY + rowH > viewH) {
            scroll = (highlightIdx * rowH - viewH + rowH).coerceAtLeast(0f)
         }

         scroll = scroll.coerceIn(0f, maxScroll)
      }

      val x = px
      val y = listTop
      CascadeUi.save()
      CascadeUi.intersectScissor(x, y, w, viewH)

      try {
         val first = (scroll / rowH).toInt().coerceAtLeast(0)
         val last = ((scroll + viewH) / rowH).toInt().coerceAtMost(filtered.size - 1)
         var i = first
         if (i <= last) {
            while (true) {
               val id = filtered[i]
               val ry = listTop + i * rowH - scroll
               val isHighlight = i == highlightIdx
               val hovered = (px <= mouseX && mouseX <= px + w) && (ry <= mouseY && mouseY <= ry + rowH)
               if (isHighlight) {
                  CascadeUi.rect(px + 2f, ry, w - 4f, rowH, Theme.withAlpha(-1750963, 0.28f), 4f)
               } else if (hovered) {
                  CascadeUi.rect(px + 2f, ry, w - 4f, rowH, 318767103, 4f)
               }

               CascadeUi.text(prettify(id), px + 10f + 4f, ry + rowH / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
               val isCurrent = id == CosmeticBlocks.INSTANCE.getSelectedBlock()
               val isFav = favs.contains(id)
               if (isCurrent) {
                  CascadeUi.textRight(
                     "\u00e2\u2014\u008f active", px + w - 10f, ry + rowH / 2f, 12f, -1750963, CascadeUi.Font.REGULAR
                  )
               } else if (isFav) {
                  CascadeUi.textRight("\u00e2\u02dc\u2026", px + w - 10f, ry + rowH / 2f, 12f, -15821, CascadeUi.Font.REGULAR)
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

      if (filtered.isEmpty()) {
         CascadeUi.textCentered("No blocks match.", px + w / 2f, listTop + 16f, 12f, -10724260, CascadeUi.Font.REGULAR)
      }
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      val mx = toUi(event.x())
      val my = toUi(event.y())
      val button = event.button()
      for (favRect in favRects) {
         val favId = favRect.first
         val fx = favRect.second
         val fw = favRect.third
         if (fx <= mx && mx <= fx + fw) {
            val favStripTop = favStripBottom - 26f
            if ((favStripTop <= my && my <= favStripBottom) && button == 0) {
               CosmeticBlocks.INSTANCE.setBlock(favId)
               onClose()
               return true
            }
         }
      }

      if (px <= mx && mx <= px + panelW) {
         if (listTop <= my && my <= listBottom) {
            val index = ((my - listTop + scroll) / rowH).toInt()
            if (index >= 0 && index < filtered.size) {
               val blockId = filtered[index]
               when (button) {
                  0 -> {
                     CosmeticBlocks.INSTANCE.setBlock(blockId)
                     onClose()
                     return true
                  }

                  1 -> {
                     if (CosmeticBlocks.INSTANCE.isFavorite(blockId)) {
                        CosmeticBlocks.INSTANCE.removeFavorite(blockId)
                     } else {
                        CosmeticBlocks.INSTANCE.addFavorite(blockId)
                     }

                     return true
                  }
               }
            }
         }
      }

      if (!(mx < px) && !(mx > px + panelW) && !(my < py) && !(my > py + panelH)) {
         return true
      }

      onClose()
      return true
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      scroll -= scrollY.toFloat() * rowH * 3f
      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      when (event.key()) {
         256 -> {
            onClose()
            return true
         }

         257, 335 -> {
            if (filtered.isNotEmpty()) {
               CosmeticBlocks.INSTANCE.setBlock(filtered[highlightIdx.coerceIn(0, filtered.size - 1)])
               onClose()
            }

            return true
         }

         259 -> {
            if (search.isNotEmpty()) {
               search = search.dropLast(1)
               applyFilter(false)
            }

            return true
         }

         264 -> {
            highlightIdx = (highlightIdx + 1).coerceAtMost((filtered.size - 1).coerceAtLeast(0))
            return true
         }

         265 -> {
            highlightIdx = (highlightIdx - 1).coerceAtLeast(0)
            return true
         }

         else -> return super.keyPressed(event)
      }
   }

   override fun charTyped(event: CharacterEvent): Boolean {
      val s = event.codepointAsString()
      if (s.isNotEmpty()) {
         val printable = s.none { it < ' ' }
         if (printable) {
            search += s
            applyFilter(false)
            return true
         }
      }

      return false
   }

   private fun toUi(scaled: Double): Float = (scaled * Minecraft.getInstance().window.guiScale / uiScale).toFloat()

   private fun prettify(id: String): String = id.removePrefix("minecraft:")
}
