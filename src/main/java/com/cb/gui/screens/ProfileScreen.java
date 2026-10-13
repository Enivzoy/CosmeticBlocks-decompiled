package com.cb.gui.screens;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.NvgScreen;
import com.cb.gui.ProfileManager;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProfileScreen extends Screen implements NvgScreen {
   private float uiScale = 1.0F;
   private float uiWidth;
   private float uiHeight;
   private final float panelWidth = 460.0F;
   private final float panelHeight = 380.0F;
   private float panelX;
   private float panelY;
   private final float headerHeight = 44.0F;
   private final float saveRowHeight = 36.0F;
   private final float cardHeight = 58.0F;
   private final float cardGap = 8.0F;
   @NotNull
   private List<ProfileManager.ProfileInfo> profiles = ProfileManager.INSTANCE.listProfiles();
   private float scroll;
   private float maxScroll;
   @NotNull
   private String nameInput = "";
   private boolean nameFocused;
   private float caretBlink;
   @Nullable
   private String pendingDelete;
   private long pendingDeleteUntil;
   @NotNull
   private String toast = "";
   private long toastUntil;
   @NotNull
   private final Anim saveBtn = new Anim(0.0F, 14.0F);
   @NotNull
   private final HashMap<String, Anim> cardHover = new HashMap<>();
   private float listClipTop;
   private float listClipBottom;

   public ProfileScreen() {
      super(Component.literal("Profiles"));
   }

   private final void refresh() {
      this.profiles = ProfileManager.INSTANCE.listProfiles();
   }

   @Override
   public void renderOverlay(float delta) {
      Minecraft minecraft = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(minecraft, "getInstance(...)");
      Window window = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      int fbWidth = window.getWidth();
      int fbHeight = window.getHeight();
      if (fbWidth > 0 && fbHeight > 0) {
         this.uiScale = RangesKt.coerceIn(window.getGuiScale() * 0.75F, 1.0F, 3.0F);
         this.uiWidth = fbWidth / this.uiScale;
         this.uiHeight = fbHeight / this.uiScale;
         int screenWidth = window.getScreenWidth();
         int screenHeight = window.getScreenHeight();
         float fbScaleX = screenWidth > 0 ? (float)fbWidth / screenWidth : 1.0F;
         float fbScaleY = screenHeight > 0 ? (float)fbHeight / screenHeight : 1.0F;
         float mouseX = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
         float mouseY = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
         this.caretBlink += delta;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               this.draw(mouseX, mouseY, delta);
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void draw(float mouseX, float mouseY, float delta) {
      this.panelX = (this.uiWidth - this.panelWidth) / 2.0F;
      this.panelY = (this.uiHeight - this.panelHeight) / 2.0F;
      NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, 1711276032, 0.0F);
      NVGRenderer.INSTANCE.shadow(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 10.0F, 18.0F, Integer.MIN_VALUE);
      NVGRenderer.INSTANCE.rect(this.panelX, this.panelY, this.panelWidth, this.panelHeight, -183233516, 10.0F);
      NVGRenderer.INSTANCE.rectOutline(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 352321535, 10.0F, 1.0F);
      NVGRenderer.INSTANCE.rectVarying(this.panelX, this.panelY, this.panelWidth, this.headerHeight, 10.0F, 10.0F, 0.0F, 0.0F, -14935012);
      NVGRenderer.INSTANCE.text("Profiles", this.panelX + 10.0F + 4.0F, this.panelY + this.headerHeight / 2.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      float closeSize = 18.0F;
      float closeX = this.panelX + this.panelWidth - closeSize - 10.0F;
      float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0F;
      boolean closeHover = this.inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize);
      NVGRenderer.INSTANCE
         .textCentered("x", closeX + closeSize / 2.0F, closeY + closeSize / 2.0F, 14.0F, closeHover ? -1750963 : -6513508, NVGRenderer.Font.BOLD);
      String countText = this.profiles.size() + " profile" + (this.profiles.size() == 1 ? "" : "s");
      NVGRenderer.INSTANCE.textRight(countText, this.panelX + this.panelWidth - 10.0F - 4.0F, this.panelY + this.headerHeight + this.saveRowHeight / 2.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
      float saveRowY = this.panelY + this.headerHeight;
      float fieldX = this.panelX + 10.0F;
      float fieldY = saveRowY + (this.saveRowHeight - 24.0F) / 2.0F;
      float fieldWidth = 240.0F;
      float fieldHeight = 24.0F;
      NVGRenderer.INSTANCE.rect(fieldX, fieldY, fieldWidth, fieldHeight, 1711276032, 5.0F);
      NVGRenderer.INSTANCE.rectOutline(fieldX, fieldY, fieldWidth, fieldHeight, this.nameFocused ? -1750963 : 352321535, 5.0F, 1.0F);
      boolean showCaret = this.nameFocused && this.caretBlink % 1.0F < 0.5F;
      String display = this.nameInput.length() == 0 && !this.nameFocused ? "new profile name…" : this.nameInput + (showCaret ? "|" : "");
      int textColor = this.nameInput.length() == 0 && !this.nameFocused ? -10724260 : -1184275;
      NVGRenderer.INSTANCE.text(display, fieldX + 8.0F, fieldY + fieldHeight / 2.0F, 13.0F, textColor, NVGRenderer.Font.REGULAR);
      float saveW = 70.0F;
      float saveX = fieldX + fieldWidth + 8.0F;
      boolean saveHover = this.inside(mouseX, mouseY, saveX, fieldY, saveW, fieldHeight);
      this.saveBtn.setTarget(saveHover ? 1.0F : 0.0F);
      this.saveBtn.update(delta);
      int saveBg = this.lerp(1088768077, -1750963, this.saveBtn.getValue());
      NVGRenderer.INSTANCE.rect(saveX, fieldY, saveW, fieldHeight, saveBg, 5.0F);
      NVGRenderer.INSTANCE.textCentered("Save", saveX + saveW / 2.0F, fieldY + fieldHeight / 2.0F, 13.0F, -1184275, NVGRenderer.Font.MEDIUM);
      float listTop = saveRowY + this.saveRowHeight + 4.0F;
      float listBottom = this.panelY + this.panelHeight - 10.0F;
      this.listClipTop = listTop;
      this.listClipBottom = listBottom;
      float listX = this.panelX + 10.0F;
      float listWidth = this.panelWidth - 20.0F;
      float listHeight = listBottom - listTop;
      float contentHeight = this.profiles.isEmpty() ? 0.0F : this.profiles.size() * (this.cardHeight + this.cardGap);
      this.maxScroll = RangesKt.coerceAtLeast(contentHeight - listHeight, 0.0F);
      this.scroll = RangesKt.coerceIn(this.scroll, 0.0F, this.maxScroll);
      NVGRenderer now = NVGRenderer.INSTANCE;
      float x = listX;
      float alpha = listTop;
      float tw = listWidth;
      float h = listHeight;
      now.save();
      now.intersectScissor(x, alpha, tw, h);

      try {
         int scissorLambda = 0;
         if (this.profiles.isEmpty()) {
            NVGRenderer.INSTANCE.textCentered("No saved profiles yet.", this.panelX + this.panelWidth / 2.0F, listTop + 30.0F, 13.0F, -10724260, NVGRenderer.Font.REGULAR);
         } else {
            float cardY = listTop - this.scroll;

            for (ProfileManager.ProfileInfo p : this.profiles) {
               if (cardY + this.cardHeight >= listTop && cardY <= listBottom) {
                  this.drawCard(p, listX, cardY, listWidth, mouseX, mouseY, delta);
               }

               cardY += this.cardHeight + this.cardGap;
            }
         }
      } finally {
         now.restore();
      }

      long nowx = System.currentTimeMillis();
      if (nowx < this.toastUntil && this.toast.length() > 0) {
         alpha = RangesKt.coerceIn((float)(this.toastUntil - nowx) / 1800.0F, 0.0F, 1.0F);
         tw = NVGRenderer.INSTANCE.textWidth(this.toast, 13.0F, NVGRenderer.Font.REGULAR);
         h = tw + 28.0F;
         float boxH = 26.0F;
         float tx = this.panelX + (this.panelWidth - h) / 2.0F;
         float ty = this.panelY + this.panelHeight - boxH - 12.0F;
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.globalAlpha(alpha);
         NVGRenderer.INSTANCE.rect(tx, ty, h, boxH, -14935012, 6.0F);
         NVGRenderer.INSTANCE.rectOutline(tx, ty, h, boxH, -1750963, 6.0F, 1.0F);
         NVGRenderer.INSTANCE.textCentered(this.toast, tx + h / 2.0F, ty + boxH / 2.0F, 13.0F, -1184275, NVGRenderer.Font.REGULAR);
         NVGRenderer.INSTANCE.restore();
      }
   }

   private final void drawCard(ProfileManager.ProfileInfo p, float x, float y, float w, float mouseX, float mouseY, float delta) {
      boolean hovered = this.inside(mouseX, mouseY, x, y, w, this.cardHeight) && mouseY >= this.listClipTop && mouseY <= this.listClipBottom;
      Map bg = this.cardHover;
      Object key = p.getName();
      Object value = bg.get(key);
      Object result;
      if (value == null) {
         int getOrPutLambda = 0;
         Object answer = new Anim(0.0F, 14.0F);
         bg.put(key, answer);
         result = answer;
      } else {
         result = value;
      }

      Anim anim = (Anim)result;
      anim.setTarget(hovered ? 1.0F : 0.0F);
      anim.update(delta);
      int bgx = this.lerp(-14935012, -1, anim.getValue() * 0.4F);
      NVGRenderer.INSTANCE.rect(x, y, w, this.cardHeight, bgx, 8.0F);
      NVGRenderer.INSTANCE.rectOutline(x, y, w, this.cardHeight, 352321535, 8.0F, 1.0F);
      NVGRenderer.INSTANCE.rectVarying(x, y, 3.0F, this.cardHeight, 8.0F, 0.0F, 0.0F, 8.0F, -1750963);
      NVGRenderer.INSTANCE.text(p.getName(), x + 14.0F, y + 18.0F, 14.0F, -1184275, NVGRenderer.Font.MEDIUM);
      key = p.getSettingCount() + " settings  ·  " + ProfileManager.INSTANCE.formatTimestamp(p.getLastModified());
      NVGRenderer.INSTANCE.text((String)key, x + 14.0F, y + 40.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
      float btnW = 76.0F;
      float btnH = 26.0F;
      float btnY = y + (this.cardHeight - btnH) / 2.0F;
      float deleteX = x + w - btnW - 10.0F;
      float loadX = deleteX - btnW - 8.0F;
      boolean loadHover = this.inside(mouseX, mouseY, loadX, btnY, btnW, btnH) && hovered;
      NVGRenderer.INSTANCE.rect(loadX, btnY, btnW, btnH, loadHover ? -1750963 : 1088768077, 6.0F);
      NVGRenderer.INSTANCE.textCentered("Load", loadX + btnW / 2.0F, btnY + btnH / 2.0F, 13.0F, -1184275, NVGRenderer.Font.MEDIUM);
      boolean isPending = Intrinsics.areEqual(this.pendingDelete, p.getName()) && System.currentTimeMillis() < this.pendingDeleteUntil;
      boolean delHover = this.inside(mouseX, mouseY, deleteX, btnY, btnW, btnH) && hovered;
      if (isPending) {
         NVGRenderer.INSTANCE.rect(deleteX, btnY, btnW, btnH, delHover ? -1750963 : -5096899, 6.0F);
         NVGRenderer.INSTANCE.textCentered("Confirm?", deleteX + btnW / 2.0F, btnY + btnH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.MEDIUM);
      } else {
         NVGRenderer.INSTANCE.rect(deleteX, btnY, btnW, btnH, delHover ? -14013910 : 1711276032, 6.0F);
         NVGRenderer.INSTANCE.textCentered("Delete", deleteX + btnW / 2.0F, btnY + btnH / 2.0F, 13.0F, delHover ? -1750963 : -6513508, NVGRenderer.Font.REGULAR);
      }
   }

   private final float toUi(double scaled) {
      Window window = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      return (float)(scaled * window.getGuiScale() / this.uiScale);
   }

   @Override
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.button() != 0) {
         return false;
      }

      float mx = this.toUi(event.x());
      float my = this.toUi(event.y());
      float closeSize = 18.0F;
      float closeX = this.panelX + this.panelWidth - closeSize - 10.0F;
      float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0F;
      if (this.inside(mx, my, closeX, closeY, closeSize, closeSize)) {
         this.onClose();
         return true;
      }

      float saveRowY = this.panelY + this.headerHeight;
      float fieldX = this.panelX + 10.0F;
      float fieldY = saveRowY + (this.saveRowHeight - 24.0F) / 2.0F;
      float fieldWidth = 240.0F;
      float fieldHeight = 24.0F;
      this.nameFocused = this.inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight);
      float saveW = 70.0F;
      float saveX = fieldX + fieldWidth + 8.0F;
      if (this.inside(mx, my, saveX, fieldY, saveW, fieldHeight)) {
         this.doSave();
         return true;
      }

      float listTop = saveRowY + this.saveRowHeight + 4.0F;
      if (my >= this.listClipTop && my <= this.listClipBottom) {
         float listX = this.panelX + 10.0F;
         float listWidth = this.panelWidth - 20.0F;
         float cardY = listTop - this.scroll;

         for (ProfileManager.ProfileInfo p : this.profiles) {
            if (this.inside(mx, my, listX, cardY, listWidth, this.cardHeight)) {
               float btnW = 76.0F;
               float btnH = 26.0F;
               float btnY = cardY + (this.cardHeight - btnH) / 2.0F;
               float deleteX = listX + listWidth - btnW - 10.0F;
               float loadX = deleteX - btnW - 8.0F;
               if (this.inside(mx, my, loadX, btnY, btnW, btnH)) {
                  boolean ok = ProfileManager.INSTANCE.loadProfile(p.getName());
                  this.showToast(ok ? "Loaded \"" + p.getName() + "\"" : "Load failed");
                  return true;
               }

               if (!this.inside(mx, my, deleteX, btnY, btnW, btnH)) {
                  return true;
               }

               boolean isPending = Intrinsics.areEqual(this.pendingDelete, p.getName()) && System.currentTimeMillis() < this.pendingDeleteUntil;
               if (isPending) {
                  boolean ok = ProfileManager.INSTANCE.deleteProfile(p.getName());
                  this.showToast(ok ? "Deleted \"" + p.getName() + "\"" : "Delete failed");
                  this.pendingDelete = null;
                  this.refresh();
               } else {
                  this.pendingDelete = p.getName();
                  this.pendingDeleteUntil = System.currentTimeMillis() + 3000;
               }

               return true;
            }

            cardY += this.cardHeight + this.cardGap;
         }
      }

      return false;
   }

   private final void doSave() {
      String n = StringsKt.trim(this.nameInput).toString();
      if (n.length() == 0) {
         this.showToast("Enter a name first");
      } else {
         boolean ok = ProfileManager.INSTANCE.saveProfile(n);
         this.showToast(ok ? "Saved \"" + n + "\"" : "Save failed");
         if (ok) {
            this.nameInput = "";
            this.nameFocused = false;
            this.refresh();
         }
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      this.scroll = RangesKt.coerceIn(this.scroll - (float)scrollY * 28.0F, 0.0F, this.maxScroll);
      return true;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      int key = event.key();
      if (this.nameFocused) {
         switch (key) {
            case 256:
               this.nameFocused = false;
               return true;
            case 257:
            case 335:
               this.doSave();
               return true;
            case 259:
               if (this.nameInput.length() > 0) {
                  this.nameInput = StringsKt.dropLast(this.nameInput, 1);
               }

               return true;
         }
      }

      if (key == 256) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(event);
      }
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.nameFocused) {
         return false;
      }

      String s = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(s, "codepointAsString(...)");
      int i = 0;

      for (int len = s.length(); i < len; i++) {
         char ch = s.charAt(i);
         if ((Character.isLetterOrDigit(ch) || ch == '_' || ch == '-' || ch == ' ') && this.nameInput.length() < 32) {
            this.nameInput = this.nameInput + ch;
         }
      }

      return true;
   }

   private final void showToast(String msg) {
      this.toast = msg;
      this.toastUntil = System.currentTimeMillis() + 1800;
   }

   private final boolean inside(float mx, float my, float x, float y, float w, float h) {
      return mx >= x && mx <= x + w && my >= y && my <= y + h;
   }

   private final int lerp(int a, int b, float t) {
      float tt = RangesKt.coerceIn(t, 0.0F, 1.0F);
      return lerpChannel(a, b, tt, 24) << 24 | lerpChannel(a, b, tt, 16) << 16 | lerpChannel(a, b, tt, 8) << 8 | lerpChannel(a, b, tt, 0);
   }

   @Override
   public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
      Intrinsics.checkNotNullParameter(extractor, "extractor");
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public void onClose() {
      if (this.minecraft != null) {
         this.minecraft.setScreenAndShow(new ClickGuiScreen());
      }
   }

   private static final int lerpChannel(int a, int b, float tt, int shift) {
      int ca = a >>> shift & 0xFF;
      int cb = b >>> shift & 0xFF;
      return RangesKt.coerceIn((int)(ca + (cb - ca) * tt), 0, 255);
   }
}
