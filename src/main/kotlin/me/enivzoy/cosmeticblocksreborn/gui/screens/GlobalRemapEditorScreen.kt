package me.enivzoy.cosmeticblocksreborn.gui.screens

import com.mojang.blaze3d.platform.InputConstants
import java.util.ArrayList
import java.util.Locale
import me.enivzoy.cosmeticblocksreborn.cosmetic.GlobalRemap
import me.enivzoy.cosmeticblocksreborn.cosmetic.RemapStore
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import me.enivzoy.cosmeticblocksreborn.util.InputCompat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component

class GlobalRemapEditorScreen(
   private val store: RemapStore,
   private val showImportExport: Boolean,
) : Screen(Component.literal("Block Remaps")), NvgScreen {
   private var view: View = View.LIST
   private var pickPhase: PickPhase = PickPhase.SOURCE
   private var pendingSource: String? = null
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private var mouseX: Float = 0f
   private var mouseY: Float = 0f
   private var px: Float = 0f
   private var py: Float = 0f
   private val rowH: Float = 20f
   private var listScroll: Float = 0f
   private var listTop: Float = 0f
   private var listBottom: Float = 0f
   private val hits: ArrayList<Hit> = ArrayList()
   private var allBlocks: List<String> = emptyList()
   private var filtered: List<String> = emptyList()
   private var search: String = ""
   private var lastFilterKey: String = "__none__"
   private var highlightIdx: Int = 0
   private var pickScroll: Float = 0f
   private var pickListTop: Float = 0f
   private var pickListBottom: Float = 0f
   private var blink: Float = 0f

   constructor() : this(GlobalRemap, true)

   private fun getPanelW(): Float = (uiWidth * 0.6f).coerceIn(420f, 640f)

   private fun getPanelH(): Float = (uiHeight * 0.78f).coerceIn(300f, 560f)

   override fun renderOverlay(delta: Float) {
      val minecraft = Minecraft.getInstance()
      val window = minecraft.window
      val fbWidth = window.width
      val fbHeight = window.height
      if (fbWidth > 0 && fbHeight > 0) {
         blink += delta
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
               val w = getPanelW()
               val h = getPanelH()
               px = (uiWidth - w) / 2f
               py = (uiHeight - h) / 2f
               CascadeUi.shadow(px, py, w, h, 10f, 16f, Int.MIN_VALUE)
               CascadeUi.rect(px, py, w, h, -183233516, 10f)
               CascadeUi.rectOutline(px, py, w, h, 352321535, 10f, 1f)
               if (view == View.LIST) {
                  renderListView()
               } else {
                  renderPickView()
               }
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun renderListView() {
      hits.clear()
      val w = getPanelW()
      val h = getPanelH()
      var cy = py + 10f
      CascadeUi.text("Block Remaps", px + 10f, cy + 7f, 14f, -1184275, CascadeUi.Font.BOLD)
      CascadeUi.textRight("${store.sources().size} sources", px + w - 10f, cy + 7f, 12f, -6513508, CascadeUi.Font.REGULAR)
      cy += 24f
      var bx = px + 10f
      bx += pillButton("+ Add Remap", bx, cy, true) { onAddRemap() } + 6f
      if (showImportExport) {
         bx += pillButton("Export", bx, cy, false) { onExport() } + 6f
         bx += pillButton("Import", bx, cy, false) { onImport() } + 6f
      }

      pillButton("Clear", bx, cy, false) { onClear() }
      cy += 24f
      listTop = cy
      listBottom = py + h - 20f
      val viewH = listBottom - listTop
      val sources = store.sources()
      var totalH = 0f

      for (src in sources) {
         totalH += rowH + store.targetsOf(src).size * rowH + rowH
      }

      val maxScroll = (totalH - viewH).coerceAtLeast(0f)
      listScroll = listScroll.coerceIn(0f, maxScroll)
      CascadeUi.save()
      CascadeUi.intersectScissor(px, listTop, w, viewH)

      try {
         var ry = listTop - listScroll
         for (src in sources) {
            ry = renderSourceBlock(src, ry)
         }
      } finally {
         CascadeUi.restore()
      }

      if (sources.isEmpty()) {
         CascadeUi.textCentered(
            "No remaps yet \u00e2\u20ac\u201d click \"+ Add Remap\".", px + w / 2f, listTop + 24f, 12f, -10724260, CascadeUi.Font.REGULAR
         )
      }

      CascadeUi.textCentered("ESC to close", px + w / 2f, py + h - 10f, 12f, -10724260, CascadeUi.Font.REGULAR)
   }

   private fun renderSourceBlock(src: String, startY: Float): Float {
      val w = getPanelW()
      var ry = startY
      if (isRowVisible(ry)) {
         CascadeUi.rect(px + 4f, ry + 1f, w - 8f, rowH - 2f, -14935012, 4f)
         CascadeUi.text(prettify(src), px + 10f, ry + rowH / 2f, 12f, -1184275, CascadeUi.Font.MEDIUM)
         val delW = iconButton("x", px + w - 10f - 16f, ry + (rowH - 14f) / 2f) { onRemoveSource(src) }
         iconButtonLabeled(
            "+ target", px + w - 10f - 16f - delW - 64f, ry + (rowH - 14f) / 2f, 60f
         ) { onAddTarget(src) }
      }

      ry += rowH
      val targets = store.targetsOf(src)
      var targetIndex = 0

      for (t in targets) {
         val i = targetIndex++
         if (isRowVisible(ry)) {
            CascadeUi.text("> ${prettify(t.id)}", px + 10f + 12f, ry + rowH / 2f, 12f, -6513508, CascadeUi.Font.REGULAR)
            var cx = px + w - 10f - 16f
            iconButton("x", cx, ry + (rowH - 14f) / 2f) { onRemoveTarget(src, i) }
            cx -= 22f
            iconButton("+", cx, ry + (rowH - 14f) / 2f) { onIncreaseWeight(src, i, t) }
            cx -= 30f
            CascadeUi.textCentered("x${t.weight}", cx + 14f, ry + rowH / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
            cx -= 18f
            iconButton("-", cx, ry + (rowH - 14f) / 2f) { onDecreaseWeight(src, i, t) }
         }

         ry += rowH
      }

      return ry
   }

   private fun renderPickView() {
      ensureBlocks()
      applyFilter(false)
      val w = getPanelW()
      val h = getPanelH()
      var cy = py + 10f
      val title: String = if (pickPhase == PickPhase.SOURCE) {
         "Pick source block"
      } else {
         "Pick target for ${prettify(pendingSource ?: "")}"
      }

      CascadeUi.text(title, px + 10f, cy + 7f, 14f, -1184275, CascadeUi.Font.BOLD)
      cy += 24f
      val searchH = 22f
      CascadeUi.rect(px + 10f, cy, w - 20f, searchH, 1711276032, 5f)
      CascadeUi.rectOutline(px + 10f, cy, w - 20f, searchH, 352321535, 5f, 1f)
      val searchText = if (search.isEmpty()) "type to filter..." else search
      val searchColorx = if (search.isEmpty()) -10724260 else -1184275
      CascadeUi.text(searchText, px + 10f + 8f, cy + searchH / 2f, 13f, searchColorx, CascadeUi.Font.REGULAR)
      if (blink % 1f < 0.55f) {
         val caretX = px + 10f + 8f + CascadeUi.textWidth(search, 13f, CascadeUi.Font.REGULAR)
         CascadeUi.rect(caretX + 1f, cy + searchH / 2f - 6f, 1.2f, 12f, -1750963, 0f)
      }

      cy += searchH + 6f
      CascadeUi.text(
         "${filtered.size} result${if (filtered.size == 1) "" else "s"}", px + 10f, cy + 6f, 12f, -10724260, CascadeUi.Font.REGULAR
      )
      cy += 16f
      pickListTop = cy
      pickListBottom = py + h - 20f
      val viewH = pickListBottom - pickListTop
      val totalH = filtered.size * rowH
      val maxScroll = (totalH - viewH).coerceAtLeast(0f)
      pickScroll = pickScroll.coerceIn(0f, maxScroll)
      CascadeUi.save()
      CascadeUi.intersectScissor(px, pickListTop, w, viewH)

      try {
         val first = (pickScroll / rowH).toInt().coerceAtLeast(0)
         val last = ((pickScroll + viewH) / rowH).toInt().coerceAtMost(filtered.size - 1)
         var i = first
         if (i <= last) {
            while (true) {
               val id = filtered[i]
               val ry = pickListTop + i * rowH - pickScroll
               val hovered = mouseX >= px && mouseX <= px + w && ry <= mouseY && mouseY <= ry + rowH
               if (i == highlightIdx) {
                  CascadeUi.rect(px + 2f, ry, w - 4f, rowH, Theme.withAlpha(-1750963, 0.28f), 4f)
               } else if (hovered) {
                  CascadeUi.rect(px + 2f, ry, w - 4f, rowH, 318767103, 4f)
               }

               CascadeUi.text(prettify(id), px + 10f + 4f, ry + rowH / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
               if (i == last) {
                  break
               }

               i++
            }
         }
      } finally {
         CascadeUi.restore()
      }

      CascadeUi.textCentered("Enter confirm   |   ESC back", px + w / 2f, py + h - 10f, 12f, -10724260, CascadeUi.Font.REGULAR)
   }

   private fun pillButton(label: String, x: Float, y: Float, accent: Boolean, act: () -> Unit): Float {
      val bw = CascadeUi.textWidth(label, 12f, CascadeUi.Font.REGULAR) + 16f
      val bh = 16f
      val hovered = mouseX >= x && mouseX <= x + bw && mouseY >= y && mouseY <= y + bh
      val bg = if (accent) -1750963 else (if (hovered) 318767103 else -14013910)
      CascadeUi.rect(x, y, bw, bh, bg, 4f)
      CascadeUi.textCentered(label, x + bw / 2f, y + bh / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
      hits.add(Hit(x, y, bw, bh, act))
      return bw
   }

   private fun iconButton(glyph: String, x: Float, y: Float, act: () -> Unit): Float {
      val s = 14f
      val hovered = mouseX >= x && mouseX <= x + s && mouseY >= y && mouseY <= y + s
      CascadeUi.rect(x, y, s, s, if (hovered) -1750963 else -14013910, 3f)
      CascadeUi.textCentered(glyph, x + s / 2f, y + s / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
      if (withinList(y)) {
         hits.add(Hit(x, y, s, s, act))
      }

      return s
   }

   private fun iconButtonLabeled(label: String, x: Float, y: Float, wdt: Float, act: () -> Unit) {
      val s = 14f
      val hovered = mouseX >= x && mouseX <= x + wdt && mouseY >= y && mouseY <= y + s
      CascadeUi.rect(x, y, wdt, s, if (hovered) 318767103 else -14013910, 3f)
      CascadeUi.textCentered(label, x + wdt / 2f, y + s / 2f, 12f, -1184275, CascadeUi.Font.REGULAR)
      if (withinList(y)) {
         hits.add(Hit(x, y, wdt, s, act))
      }
   }

   private fun withinList(y: Float): Boolean = y >= listTop - 2f && y + rowH <= listBottom + 2f

   private fun ensureBlocks() {
      if (allBlocks.isEmpty()) {
         allBlocks = BuiltInRegistries.BLOCK.keySet().map { it.toString() }.sorted()
         applyFilter(true)
      }
   }

   private fun applyFilter(force: Boolean) {
      val q = search.trim().lowercase(Locale.ROOT)
      if (force || q != lastFilterKey) {
         lastFilterKey = q
         filtered = if (q.isEmpty()) allBlocks else allBlocks.filter { it.contains(q, false) }
         highlightIdx = highlightIdx.coerceIn(0, (filtered.size - 1).coerceAtLeast(0))
      }
   }

   private fun enterPick(phase: PickPhase, source: String?) {
      view = View.PICK
      pickPhase = phase
      pendingSource = source
      search = ""
      lastFilterKey = "__none__"
      highlightIdx = 0
      pickScroll = 0f
      ensureBlocks()
      applyFilter(true)
   }

   private fun commitPick(id: String) {
      when (pickPhase) {
         PickPhase.SOURCE -> enterPick(PickPhase.TARGET, id)
         PickPhase.TARGET -> {
            val src = pendingSource
            if (src != null) {
               store.addTarget(src, id, 1)
            }

            view = View.LIST
            pendingSource = null
         }
      }
   }

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      val mx = toUi(event.x())
      val my = toUi(event.y())
      if (event.button() != InputConstants.MOUSE_BUTTON_LEFT) {
         return true
      }

      if (view == View.LIST) {
         for (hit in hits) {
            if (mx >= hit.x && mx <= hit.x + hit.w && my >= hit.y && my <= hit.y + hit.h) {
               hit.act()
               return true
            }
         }

         if (mx < px || mx > px + getPanelW() || my < py || my > py + getPanelH()) {
            onClose()
         }

         return true
      } else {
         if (mx >= px && mx <= px + getPanelW() && my >= pickListTop && my <= pickListBottom) {
            val idxx = ((my - pickListTop + pickScroll) / rowH).toInt()
            if (idxx >= 0 && idxx < filtered.size) {
               commitPick(filtered[idxx])
            }

            return true
         }

         if (mx < px || mx > px + getPanelW() || my < py || my > py + getPanelH()) {
            view = View.LIST
         }

         return true
      }
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      if (view == View.LIST) {
         listScroll -= scrollY.toFloat() * rowH * 3f
      } else {
         pickScroll -= scrollY.toFloat() * rowH * 3f
      }

      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      if (view == View.PICK) {
          when (event.key()) {
             InputConstants.KEY_ESCAPE -> {
                view = View.LIST
                return true
             }

             InputConstants.KEY_RETURN, InputCompat.KEY_KP_ENTER -> {
                if (filtered.isNotEmpty()) {
                   commitPick(filtered[highlightIdx.coerceIn(0, filtered.size - 1)])
                }

                return true
             }

             InputConstants.KEY_BACKSPACE -> {
                if (search.isNotEmpty()) {
                   search = search.dropLast(1)
                   applyFilter(false)
                }

                return true
             }

             InputConstants.KEY_DOWN -> {
                highlightIdx = (highlightIdx + 1).coerceAtMost((filtered.size - 1).coerceAtLeast(0))
                return true
             }

             InputConstants.KEY_UP -> {
                highlightIdx = (highlightIdx - 1).coerceAtLeast(0)
                return true
             }

             else -> return super.keyPressed(event)
          }
       } else if (event.key() == InputConstants.KEY_ESCAPE) {
         onClose()
         return true
      } else {
         return super.keyPressed(event)
      }
   }

   override fun charTyped(event: CharacterEvent): Boolean {
      if (view != View.PICK) {
         return false
      }

      val s = event.codepointAsString()
      if (s.isNotEmpty() && s.all { it >= ' ' }) {
         search += s
         applyFilter(false)
         return true
      }

      return false
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   private fun toUi(scaled: Double): Float = (scaled * Minecraft.getInstance().window.guiScale / uiScale).toFloat()

   private fun prettify(id: String): String = id.removePrefix("minecraft:")

   private fun onAddRemap() {
      enterPick(PickPhase.SOURCE, null)
   }

   private fun onExport() {
      Minecraft.getInstance().keyboardHandler.setClipboard(store.exportJson())
   }

   private fun onImport() {
      store.importJson(Minecraft.getInstance().keyboardHandler.clipboard)
   }

   private fun onClear() {
      store.clear()
   }

   private fun isRowVisible(y: Float): Boolean = y + rowH > listTop && y < listBottom

   private fun onRemoveSource(src: String) {
      store.removeSource(src)
   }

   private fun onAddTarget(src: String) {
      enterPick(PickPhase.TARGET, src)
   }

   private fun onRemoveTarget(src: String, index: Int) {
      store.removeTarget(src, index)
   }

   private fun onIncreaseWeight(src: String, index: Int, target: GlobalRemap.RemapTarget) {
      store.setWeight(src, index, target.weight + 1)
   }

   private fun onDecreaseWeight(src: String, index: Int, target: GlobalRemap.RemapTarget) {
      store.setWeight(src, index, target.weight - 1)
   }

   private class Hit(val x: Float, val y: Float, val w: Float, val h: Float, val act: () -> Unit)

   private enum class PickPhase {
      SOURCE,
      TARGET,
   }

   private enum class View {
      LIST,
      PICK,
   }
}
