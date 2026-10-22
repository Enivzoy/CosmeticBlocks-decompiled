package me.enivzoy.cosmeticblocksreborn.gui.screens

import com.mojang.blaze3d.platform.InputConstants
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import me.enivzoy.cosmeticblocksreborn.gui.ProfileManager
import me.enivzoy.cosmeticblocksreborn.render.Anim
import me.enivzoy.cosmeticblocksreborn.render.CascadeUi
import me.enivzoy.cosmeticblocksreborn.util.InputCompat
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.CharacterEvent
import net.minecraft.client.input.KeyEvent
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component

class ProfileScreen : Screen(Component.literal("Profiles")), NvgScreen {
   private var uiScale: Float = 1f
   private var uiWidth: Float = 0f
   private var uiHeight: Float = 0f
   private val panelWidth: Float = 460f
   private val panelHeight: Float = 380f
   private var panelX: Float = 0f
   private var panelY: Float = 0f
   private val headerHeight: Float = 44f
   private val saveRowHeight: Float = 36f
   private val cardHeight: Float = 58f
   private val cardGap: Float = 8f
   private var profiles: List<ProfileManager.ProfileInfo> = ProfileManager.listProfiles()
   private var scroll: Float = 0f
   private var maxScroll: Float = 0f
   private var nameInput: String = ""
   private var nameFocused: Boolean = false
   private var caretBlink: Float = 0f
   private var pendingDelete: String? = null
   private var pendingDeleteUntil: Long = 0L
   private var toast: String = ""
   private var toastUntil: Long = 0L
   private val saveBtn: Anim = Anim(0f, 14f)
   private val cardHover: HashMap<String, Anim> = HashMap()
   private var listClipTop: Float = 0f
   private var listClipBottom: Float = 0f

   private fun refresh() {
      profiles = ProfileManager.listProfiles()
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
      CascadeUi.text("Profiles", panelX + 10f + 4f, panelY + headerHeight / 2f, 14f, -1184275, CascadeUi.Font.BOLD)
      val closeSize = 18f
      val closeX = panelX + panelWidth - closeSize - 10f
      val closeY = panelY + (headerHeight - closeSize) / 2f
      val closeHover = inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize)
      CascadeUi.textCentered(
         "x", closeX + closeSize / 2f, closeY + closeSize / 2f, 14f, if (closeHover) -1750963 else -6513508, CascadeUi.Font.BOLD
      )
      val countText = "${profiles.size} profile${if (profiles.size == 1) "" else "s"}"
      CascadeUi.textRight(
         countText, panelX + panelWidth - 10f - 4f, panelY + headerHeight + saveRowHeight / 2f, 12f, -10724260, CascadeUi.Font.REGULAR
      )
      val saveRowY = panelY + headerHeight
      val fieldX = panelX + 10f
      val fieldY = saveRowY + (saveRowHeight - 24f) / 2f
      val fieldWidth = 240f
      val fieldHeight = 24f
      CascadeUi.rect(fieldX, fieldY, fieldWidth, fieldHeight, 1711276032, 5f)
      CascadeUi.rectOutline(fieldX, fieldY, fieldWidth, fieldHeight, if (nameFocused) -1750963 else 352321535, 5f, 1f)
      val showCaret = nameFocused && caretBlink % 1f < 0.5f
      val display = if (nameInput.isEmpty() && !nameFocused) "new profile name\u00e2\u20ac\u00a6" else nameInput + (if (showCaret) "|" else "")
      val textColor = if (nameInput.isEmpty() && !nameFocused) -10724260 else -1184275
      CascadeUi.text(display, fieldX + 8f, fieldY + fieldHeight / 2f, 13f, textColor, CascadeUi.Font.REGULAR)
      val saveW = 70f
      val saveX = fieldX + fieldWidth + 8f
      val saveHover = inside(mouseX, mouseY, saveX, fieldY, saveW, fieldHeight)
      saveBtn.target = if (saveHover) 1f else 0f
      saveBtn.update(delta)
      val saveBg = lerp(1088768077, -1750963, saveBtn.value)
      CascadeUi.rect(saveX, fieldY, saveW, fieldHeight, saveBg, 5f)
      CascadeUi.textCentered("Save", saveX + saveW / 2f, fieldY + fieldHeight / 2f, 13f, -1184275, CascadeUi.Font.MEDIUM)
      val listTop = saveRowY + saveRowHeight + 4f
      val listBottom = panelY + panelHeight - 10f
      listClipTop = listTop
      listClipBottom = listBottom
      val listX = panelX + 10f
      val listWidth = panelWidth - 20f
      val listHeight = listBottom - listTop
      val contentHeight = if (profiles.isEmpty()) 0f else profiles.size * (cardHeight + cardGap)
      maxScroll = (contentHeight - listHeight).coerceAtLeast(0f)
      scroll = scroll.coerceIn(0f, maxScroll)
      CascadeUi.save()
      CascadeUi.intersectScissor(listX, listTop, listWidth, listHeight)

      try {
         if (profiles.isEmpty()) {
            CascadeUi.textCentered(
               "No saved profiles yet.", panelX + panelWidth / 2f, listTop + 30f, 13f, -10724260, CascadeUi.Font.REGULAR
            )
         } else {
            var cardY = listTop - scroll
            for (p in profiles) {
               if (cardY + cardHeight >= listTop && cardY <= listBottom) {
                  drawCard(p, listX, cardY, listWidth, mouseX, mouseY, delta)
               }

               cardY += cardHeight + cardGap
            }
         }
      } finally {
         CascadeUi.restore()
      }

      val now = System.currentTimeMillis()
      if (now < toastUntil && toast.isNotEmpty()) {
         val alpha = ((toastUntil - now).toFloat() / 1800f).coerceIn(0f, 1f)
         val textW = CascadeUi.textWidth(toast, 13f, CascadeUi.Font.REGULAR)
         val boxW = textW + 28f
         val boxH = 26f
         val tx = panelX + (panelWidth - boxW) / 2f
         val ty = panelY + panelHeight - boxH - 12f
         CascadeUi.save()
         CascadeUi.globalAlpha(alpha)
         CascadeUi.rect(tx, ty, boxW, boxH, -14935012, 6f)
         CascadeUi.rectOutline(tx, ty, boxW, boxH, -1750963, 6f, 1f)
         CascadeUi.textCentered(toast, tx + boxW / 2f, ty + boxH / 2f, 13f, -1184275, CascadeUi.Font.REGULAR)
         CascadeUi.restore()
      }
   }

   private fun drawCard(p: ProfileManager.ProfileInfo, x: Float, y: Float, w: Float, mouseX: Float, mouseY: Float, delta: Float) {
      val hovered = inside(mouseX, mouseY, x, y, w, cardHeight) && mouseY >= listClipTop && mouseY <= listClipBottom
      val anim = cardHover.getOrPut(p.name) { Anim(0f, 14f) }
      anim.target = if (hovered) 1f else 0f
      anim.update(delta)
      val bgx = lerp(-14935012, -1, anim.value * 0.4f)
      CascadeUi.rect(x, y, w, cardHeight, bgx, 8f)
      CascadeUi.rectOutline(x, y, w, cardHeight, 352321535, 8f, 1f)
      CascadeUi.rectVarying(x, y, 3f, cardHeight, 8f, 0f, 0f, 8f, -1750963)
      CascadeUi.text(p.name, x + 14f, y + 18f, 14f, -1184275, CascadeUi.Font.MEDIUM)
      val info = "${p.settingCount} settings  \u00c2\u00b7  ${ProfileManager.formatTimestamp(p.lastModified)}"
      CascadeUi.text(info, x + 14f, y + 40f, 12f, -10724260, CascadeUi.Font.REGULAR)
      val btnW = 76f
      val btnH = 26f
      val btnY = y + (cardHeight - btnH) / 2f
      val deleteX = x + w - btnW - 10f
      val loadX = deleteX - btnW - 8f
      val loadHover = inside(mouseX, mouseY, loadX, btnY, btnW, btnH) && hovered
      CascadeUi.rect(loadX, btnY, btnW, btnH, if (loadHover) -1750963 else 1088768077, 6f)
      CascadeUi.textCentered("Load", loadX + btnW / 2f, btnY + btnH / 2f, 13f, -1184275, CascadeUi.Font.MEDIUM)
      val isPending = pendingDelete == p.name && System.currentTimeMillis() < pendingDeleteUntil
      val delHover = inside(mouseX, mouseY, deleteX, btnY, btnW, btnH) && hovered
      if (isPending) {
         CascadeUi.rect(deleteX, btnY, btnW, btnH, if (delHover) -1750963 else -5096899, 6f)
         CascadeUi.textCentered("Confirm?", deleteX + btnW / 2f, btnY + btnH / 2f, 12f, -1184275, CascadeUi.Font.MEDIUM)
      } else {
         CascadeUi.rect(deleteX, btnY, btnW, btnH, if (delHover) -14013910 else 1711276032, 6f)
         CascadeUi.textCentered(
            "Delete", deleteX + btnW / 2f, btnY + btnH / 2f, 13f, if (delHover) -1750963 else -6513508, CascadeUi.Font.REGULAR
         )
      }
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

      val saveRowY = panelY + headerHeight
      val fieldX = panelX + 10f
      val fieldY = saveRowY + (saveRowHeight - 24f) / 2f
      val fieldWidth = 240f
      val fieldHeight = 24f
      nameFocused = inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight)
      val saveW = 70f
      val saveX = fieldX + fieldWidth + 8f
      if (inside(mx, my, saveX, fieldY, saveW, fieldHeight)) {
         doSave()
         return true
      }

      val listTop = saveRowY + saveRowHeight + 4f
      if (my >= listClipTop && my <= listClipBottom) {
         val listX = panelX + 10f
         val listWidth = panelWidth - 20f
         var cardY = listTop - scroll
         for (p in profiles) {
            if (inside(mx, my, listX, cardY, listWidth, cardHeight)) {
               val btnW = 76f
               val btnH = 26f
               val btnY = cardY + (cardHeight - btnH) / 2f
               val deleteX = listX + listWidth - btnW - 10f
               val loadX = deleteX - btnW - 8f
               if (inside(mx, my, loadX, btnY, btnW, btnH)) {
                  val ok = ProfileManager.loadProfile(p.name)
                  showToast(if (ok) "Loaded \"${p.name}\"" else "Load failed")
                  return true
               }

               if (!inside(mx, my, deleteX, btnY, btnW, btnH)) {
                  return true
               }

               val isPending = pendingDelete == p.name && System.currentTimeMillis() < pendingDeleteUntil
               if (isPending) {
                  val ok = ProfileManager.deleteProfile(p.name)
                  showToast(if (ok) "Deleted \"${p.name}\"" else "Delete failed")
                  pendingDelete = null
                  refresh()
               } else {
                  pendingDelete = p.name
                  pendingDeleteUntil = System.currentTimeMillis() + 3000
               }

               return true
            }

            cardY += cardHeight + cardGap
         }
      }

      return false
   }

   private fun doSave() {
      val n = nameInput.trim()
      if (n.isEmpty()) {
         showToast("Enter a name first")
      } else {
         val ok = ProfileManager.saveProfile(n)
         showToast(if (ok) "Saved \"$n\"" else "Save failed")
         if (ok) {
            nameInput = ""
            nameFocused = false
            refresh()
         }
      }
   }

   override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
      scroll = (scroll - scrollY.toFloat() * 28f).coerceIn(0f, maxScroll)
      return true
   }

   override fun keyPressed(event: KeyEvent): Boolean {
      val key = event.key()
      if (nameFocused) {
         when (key) {
            InputConstants.KEY_ESCAPE -> {
               nameFocused = false
               return true
            }

            InputConstants.KEY_RETURN, InputCompat.KEY_KP_ENTER -> {
               doSave()
               return true
            }

            InputConstants.KEY_BACKSPACE -> {
               if (nameInput.isNotEmpty()) {
                  nameInput = nameInput.dropLast(1)
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

   override fun charTyped(event: CharacterEvent): Boolean {
      if (!nameFocused) {
         return false
      }

      val s = event.codepointAsString()
      for (ch in s) {
         if ((ch.isLetterOrDigit() || ch == '_' || ch == '-' || ch == ' ') && nameInput.length < 32) {
            nameInput += ch
         }
      }

      return true
   }

   private fun showToast(msg: String) {
      toast = msg
      toastUntil = System.currentTimeMillis() + 1800
   }

   private fun inside(mx: Float, my: Float, x: Float, y: Float, w: Float, h: Float): Boolean =
      mx >= x && mx <= x + w && my >= y && my <= y + h

   private fun lerp(a: Int, b: Int, t: Float): Int {
      val tt = t.coerceIn(0f, 1f)
      return (lerpChannel(a, b, tt, 24) shl 24) or (lerpChannel(a, b, tt, 16) shl 16) or (lerpChannel(a, b, tt, 8) shl 8) or lerpChannel(a, b, tt, 0)
   }

   override fun extractBackground(extractor: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
   }

   override fun isPauseScreen(): Boolean = false

   override fun onClose() {
      if (minecraft != null) {
         minecraft.setScreenAndShow(ClickGuiScreen())
      }
   }

   private fun lerpChannel(a: Int, b: Int, tt: Float, shift: Int): Int {
      val ca = a ushr shift and 0xFF
      val cb = b ushr shift and 0xFF
      return (ca + (cb - ca) * tt).toInt().coerceIn(0, 255)
   }
}
