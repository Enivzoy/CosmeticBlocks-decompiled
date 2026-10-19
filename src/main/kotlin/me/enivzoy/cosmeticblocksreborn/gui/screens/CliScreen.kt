package me.enivzoy.cosmeticblocksreborn.gui.screens

import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.render.Theme
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component
import org.lwjgl.glfw.GLFW

class CliScreen : Screen(Component.literal("CB Command Line")), NvgScreen {
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private var input: String = ""
   private var caret: Int = 0
   private var suggestions: List<CliEngine.Suggestion> = emptyList()
   private var suggestionIdx: Int = 0
   private var historyIdx: Int = -1
   private var lastResult: String = ""
   private var lastResultUntil: Long = 0L
   private var caretBlink: Float = 0f

   private val barWidth: Float
      get() = (uiWidth * 0.6f).coerceIn(280f, 620f)

   override fun renderOverlay(delta: Float) {
      val minecraft = Minecraft.getInstance()
      val window = minecraft.window
      val fbWidth = window.width
      val fbHeight = window.height
      if (fbWidth > 0 && fbHeight > 0) {
         uiScale = (window.guiScale * 0.75f).coerceIn(1f, 3f)
         uiWidth = fbWidth / uiScale
         uiHeight = fbHeight / uiScale
         caretBlink += delta
         if (CascadeUi.beginFrame(fbWidth, fbHeight, uiScale)) {
            try {
               CascadeUi.rect(0f, 0f, uiWidth, uiHeight, 1493172224, 0f)
               val barH = 30f
               val barX = (uiWidth - barWidth) / 2f
               val barY = uiHeight * 0.62f
               suggestions = CliEngine.suggestions(input)
               if (suggestionIdx >= suggestions.size) {
                  suggestionIdx = 0
               }

               renderSuggestions(barX, barY, barWidth)
               renderBar(barX, barY, barWidth, barH)
               renderFeedback(barX, barY + barH + 6f, barWidth)
               renderHistoryHint(barX, barY)
            } finally {
               CascadeUi.endFrame()
            }
         }
      }
   }

   private fun renderBar(x: Float, y: Float, w: Float, h: Float) {
      CascadeUi.shadow(x, y, w, h, 10f, 12f, Int.MIN_VALUE)
      CascadeUi.rect(x, y, w, h, -183233516, 10f)
      CascadeUi.rectOutline(x, y, w, h, Theme.withAlpha(-1750963, 0.55f), 10f, 1f)
      val padX = 12f
      val cy = y + h / 2f
      CascadeUi.text(">", x + padX, cy, 13f, -1750963, CascadeUi.Font.BOLD)
      val textX = x + padX + 16f
      if (input.isEmpty()) {
         CascadeUi.text("type a command, Tab to complete, Enter to run", textX, cy, 13f, -10724260, CascadeUi.Font.REGULAR)
      } else {
         CascadeUi.text(input, textX, cy, 13f, -1184275, CascadeUi.Font.REGULAR)
      }

      if ((caretBlink * 2f).toInt() % 2 == 0) {
         val before = input.take(caret.coerceIn(0, input.length))
         val caretX = textX + CascadeUi.textWidth(before, 13f, CascadeUi.Font.REGULAR)
         CascadeUi.rect(caretX, cy - 6.5f, 1f, 13f, -1184275, 0f)
      }
   }

   private fun renderSuggestions(barX: Float, barY: Float, w: Float) {
      if (suggestions.isNotEmpty()) {
         val rowH = 20f
         val popH = suggestions.size * rowH + 8f
         val x = barX
         val y = barY - popH - 6f
         CascadeUi.shadow(x, y, w, popH, 8f, 12f, Int.MIN_VALUE)
         CascadeUi.rect(x, y, w, popH, -14935012, 8f)
         CascadeUi.rectOutline(x, y, w, popH, Theme.withAlpha(-1750963, 0.3f), 8f, 1f)
         var ry = y + 4f
         var i = 0
         for (s in suggestions) {
            val highlight = i == suggestionIdx
            if (highlight) {
               CascadeUi.rect(x + 3f, ry, w - 6f, rowH, Theme.withAlpha(-1750963, 0.22f), 4f)
            }

            val labelColor = if (highlight) -1750963 else -1184275
            CascadeUi.text(s.display, x + 12f, ry + rowH / 2f, 12f, labelColor, CascadeUi.Font.REGULAR)
            if (s.hint.isNotEmpty()) {
               CascadeUi.textRight(s.hint, x + w - 12f, ry + rowH / 2f, 12f, -6513508, CascadeUi.Font.REGULAR)
            }

            ry += rowH
            i++
         }
      }
   }

   private fun renderFeedback(x: Float, y: Float, w: Float) {
      val now = System.currentTimeMillis()
      if (now < lastResultUntil && lastResult.isNotEmpty()) {
         val alpha = ((lastResultUntil - now).toFloat() / 3500f).coerceIn(0f, 1f)
         CascadeUi.text(lastResult, x + 4f, y + 8f, 12f, Theme.withAlpha(-6513508, alpha), CascadeUi.Font.REGULAR)
      }
   }

   private fun renderHistoryHint(barX: Float, barY: Float) {
      if (input.isEmpty()) {
         val hist = CliEngine.history()
         if (hist.isNotEmpty()) {
            val recent = hist.takeLast(3).reversed()
            var ry = barY + 36f
            for (h in recent) {
               CascadeUi.text("\u00c2\u00b7 $h", barX + 4f, ry, 12f, -10724260, CascadeUi.Font.REGULAR)
               ry += 14f
            }
         }
      }
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun mouseClicked(event: MouseButtonEvent, doubled: Boolean): Boolean {
      val barH = 30f
      val barX = (uiWidth - barWidth) / 2f
      val barY = uiHeight * 0.62f
      val popH = if (suggestions.isEmpty()) 0f else suggestions.size * 20f + 8f
      val mx = toUi(event.x())
      val my = toUi(event.y())
      val insideBar = (barX <= mx && mx <= barX + barWidth) && (barY <= my && my <= barY + barH)
      val insidePop = (barX <= mx && mx <= barX + barWidth) && (barY - popH - 6f <= my && my <= barY)
      if (!insideBar && !insidePop) {
         onClose()
         return true
      }

      if (insidePop && suggestions.isNotEmpty()) {
         val rowH = 20f
         val popY = barY - popH - 6f + 4f
         val idx = ((my - popY) / rowH).toInt()
         if (idx >= 0 && idx < suggestions.size) {
            applySuggestion(suggestions[idx])
         }
      }

      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      when (event.key()) {
         86 -> {
            if ((event.modifiers() and 2) != 0) {
               val clip = GLFW.glfwGetClipboardString(Minecraft.getInstance().window.handle())
               if (clip != null) {
                  val before = input.substring(0, caret)
                  val after = input.substring(caret)
                  input = before + clip + after
                  caret += clip.length
                  onInputChanged()
               }

               return true
            }
            return super.keyPressed(event)
         }

         256 -> {
            onClose()
            return true
         }

         257, 335 -> {
            val cmd = input.trim()
            if (cmd.isNotEmpty()) {
               executeAndRecord(cmd)
            }

            return true
         }

         258 -> {
            if (suggestions.isNotEmpty()) {
               applySuggestion(suggestions[suggestionIdx.coerceIn(0, suggestions.size - 1)])
            }

            return true
         }

         259 -> {
            if (caret > 0 && input.isNotEmpty()) {
               input = input.removeRange(caret - 1, caret).toString()
               caret -= 1
               onInputChanged()
            }

            return true
         }

         261 -> {
            if (caret < input.length) {
               input = input.removeRange(caret, caret + 1).toString()
               onInputChanged()
            }

            return true
         }

         262 -> {
            caret = (caret + 1).coerceAtMost(input.length)
            return true
         }

         263 -> {
            caret = (caret - 1).coerceAtLeast(0)
            return true
         }

         264 -> {
            if (suggestions.isNotEmpty()) {
               suggestionIdx = (suggestionIdx + 1).coerceAtMost(suggestions.size - 1)
            }

            return true
         }

         265 -> {
            if (suggestions.isNotEmpty() && suggestionIdx > 0) {
               suggestionIdx--
            } else {
               cycleHistory()
            }

            return true
         }

         268 -> {
            caret = 0
            return true
         }

         269 -> {
            caret = input.length
            return true
         }

         else -> return super.keyPressed(event)
      }
   }

   override fun charTyped(event: CharacterEvent): Boolean {
      val s = event.codepointAsString()
      if (s.isNotEmpty()) {
         val allPrintable = s.none { it < ' ' }
         if (allPrintable) {
            val before = input.substring(0, caret)
            val after = input.substring(caret)
            input = before + s + after
            caret += s.length
            onInputChanged()
            return true
         }
      }

      return false
   }

   private fun onInputChanged() {
      suggestionIdx = 0
      historyIdx = -1
   }

   private fun applySuggestion(s: CliEngine.Suggestion) {
      val needsSpace = !s.insert.endsWith(' ')
      input = if (needsSpace) "${s.insert} " else s.insert
      caret = input.length
      suggestionIdx = 0
   }

   private fun cycleHistory() {
      val hist = CliEngine.history()
      if (hist.isNotEmpty()) {
         historyIdx = if (historyIdx < 0) hist.size - 1 else (historyIdx - 1).coerceAtLeast(0)
         input = hist[historyIdx]
         caret = input.length
      }
   }

   private fun executeAndRecord(cmd: String) {
      lastResult = CliEngine.execute(cmd)
      lastResultUntil = System.currentTimeMillis() + 3500
      input = ""
      caret = 0
      historyIdx = -1
      suggestionIdx = 0
   }

   private fun toUi(scaled: Double): Float = (scaled * Minecraft.getInstance().window.guiScale / uiScale).toFloat()
}
