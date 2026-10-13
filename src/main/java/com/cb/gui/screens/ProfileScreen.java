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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010'\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020\u0006H\u0016J \u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J@\u0010.\u001a\u00020(2\u0006\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J\u0010\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u000205H\u0002J\u0018\u00106\u001a\u00020\u00192\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u0019H\u0016J\b\u0010:\u001a\u00020(H\u0002J(\u0010;\u001a\u00020\u00192\u0006\u0010,\u001a\u0002052\u0006\u0010-\u001a\u0002052\u0006\u0010<\u001a\u0002052\u0006\u0010=\u001a\u000205H\u0016J\u0010\u0010>\u001a\u00020\u00192\u0006\u00107\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020\u00192\u0006\u00107\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020(2\u0006\u0010C\u001a\u00020\u0017H\u0002J8\u0010D\u001a\u00020\u00192\u0006\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u00062\u0006\u0010G\u001a\u00020\u0006H\u0002J \u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020I2\u0006\u0010L\u001a\u00020\u0006H\u0002J(\u0010M\u001a\u00020(2\u0006\u0010N\u001a\u00020O2\u0006\u0010,\u001a\u00020I2\u0006\u0010-\u001a\u00020I2\u0006\u0010P\u001a\u00020\u0006H\u0016J\b\u0010Q\u001a\u00020\u0019H\u0016J\b\u0010R\u001a\u00020(H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020!0#j\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020!`$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006S",
   d2 = {
         "Lcom/cb/gui/screens/ProfileScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "uiScale",
         "",
         "uiWidth",
         "uiHeight",
         "panelWidth",
         "panelHeight",
         "panelX",
         "panelY",
         "headerHeight",
         "saveRowHeight",
         "cardHeight",
         "cardGap",
         "profiles",
         "",
         "Lcom/cb/gui/ProfileManager$ProfileInfo;",
         "scroll",
         "maxScroll",
         "nameInput",
         "",
         "nameFocused",
         "",
         "caretBlink",
         "pendingDelete",
         "pendingDeleteUntil",
         "",
         "toast",
         "toastUntil",
         "saveBtn",
         "Lcom/cb/render/Anim;",
         "cardHover",
         "Ljava/util/HashMap;",
         "Lkotlin/collections/HashMap;",
         "listClipTop",
         "listClipBottom",
         "refresh",
         "",
         "renderOverlay",
         "delta",
         "draw",
         "mouseX",
         "mouseY",
         "drawCard",
         "p",
         "x",
         "y",
         "w",
         "toUi",
         "scaled",
         "",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "doSave",
         "mouseScrolled",
         "scrollX",
         "scrollY",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "charTyped",
         "Lnet/minecraft/client/input/CharacterEvent;",
         "showToast",
         "msg",
         "inside",
         "mx",
         "my",
         "h",
         "lerp",
         "",
         "a",
         "b",
         "t",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "partialTick",
         "isPauseScreen",
         "onClose",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nProfileScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileScreen.kt\ncom/cb/gui/screens/ProfileScreen\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,394:1\n445#2,8:395\n410#3,7:403\n*S KotlinDebug\n*F\n+ 1 ProfileScreen.kt\ncom/cb/gui/screens/ProfileScreen\n*L\n163#1:395,8\n197#1:403,7\n*E\n"
)
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft minecraft = var10000;
      Window var15 = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(var15, "getWindow(...)");
      Window window = var15;
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
      NVGRenderer.INSTANCE.rectOutline(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 352321535, 10.0F, 0.0F);
      NVGRenderer.INSTANCE.rectVarying(this.panelX, this.panelY, this.panelWidth, this.headerHeight, 10.0F, 10.0F, 0.0F, 0.0F, -14935012);
      NVGRenderer.INSTANCE.text("Profiles", this.panelX + 10.0F + 4.0F, this.panelY + this.headerHeight / 2.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      float closeSize = 18.0F;
      float closeX = this.panelX + this.panelWidth - closeSize - 10.0F;
      float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0F;
      boolean closeHover = this.inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize);
      NVGRenderer.INSTANCE
         .textCentered("x", closeX + closeSize / 2.0F, closeY + closeSize / 2.0F, 14.0F, closeHover ? -1750963 : -6513508, NVGRenderer.Font.BOLD);
      String countText = this.profiles.size() + " profile" + (this.profiles.size() == 1 ? "" : "s");
      NVGRenderer.INSTANCE.textRight(countText, this.panelX + this.panelWidth - 10.0F - 4.0F, this.panelY + this.headerHeight + this.saveRowHeight / 2.0F, 12.0F, -10724260, null);
      float saveRowY = this.panelY + this.headerHeight;
      float fieldX = this.panelX + 10.0F;
      float fieldY = saveRowY + (this.saveRowHeight - 24.0F) / 2.0F;
      float fieldWidth = 240.0F;
      float fieldHeight = 24.0F;
      NVGRenderer.INSTANCE.rect(fieldX, fieldY, fieldWidth, fieldHeight, 1711276032, 5.0F);
      NVGRenderer.INSTANCE.rectOutline(fieldX, fieldY, fieldWidth, fieldHeight, this.nameFocused ? -1750963 : 352321535, 5.0F, 0.0F);
      boolean showCaret = this.nameFocused && this.caretBlink % 1.0F < 0.5F;
      String display = this.nameInput.length() == 0 && !this.nameFocused ? "new profile name…" : this.nameInput + (showCaret ? "|" : "");
      int textColor = this.nameInput.length() == 0 && !this.nameFocused ? -10724260 : -1184275;
      NVGRenderer.INSTANCE.text(display, fieldX + 8.0F, fieldY + fieldHeight / 2.0F, 13.0F, textColor, null);
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
      float x$iv = listX;
      float alpha = listTop;
      float tw = listWidth;
      float h$iv = listHeight;
      int $i$f$scissor = 0;
      now.save();
      now.intersectScissor(x$iv, alpha, tw, h$iv);

      try {
         int var33/* $VF was: $i$a$-scissor-ProfileScreen$draw$1 */ = 0;
         if (this.profiles.isEmpty()) {
            NVGRenderer.INSTANCE.textCentered("No saved profiles yet.", this.panelX + this.panelWidth / 2.0F, listTop + 30.0F, 13.0F, -10724260, null);
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
         tw = NVGRenderer.INSTANCE.textWidth(this.toast, 13.0F, null);
         h$iv = tw + 28.0F;
         float boxH = 26.0F;
         float tx = this.panelX + (this.panelWidth - h$iv) / 2.0F;
         float ty = this.panelY + this.panelHeight - boxH - 12.0F;
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.globalAlpha(alpha);
         NVGRenderer.INSTANCE.rect(tx, ty, h$iv, boxH, -14935012, 6.0F);
         NVGRenderer.INSTANCE.rectOutline(tx, ty, h$iv, boxH, -1750963, 6.0F, 0.0F);
         NVGRenderer.INSTANCE.textCentered(this.toast, tx + h$iv / 2.0F, ty + boxH / 2.0F, 13.0F, -1184275, null);
         NVGRenderer.INSTANCE.restore();
      }
   }

   private final void drawCard(ProfileManager.ProfileInfo p, float x, float y, float w, float mouseX, float mouseY, float delta) {
      boolean hovered = this.inside(mouseX, mouseY, x, y, w, this.cardHeight) && mouseY >= this.listClipTop && mouseY <= this.listClipBottom;
      Map bg = this.cardHover;
      Object key$iv = p.getName();
      int $i$f$getOrPut = 0;
      Object value$iv = bg.get(key$iv);
      Object var10000;
      if (value$iv == null) {
         int var14/* $VF was: $i$a$-getOrPut-ProfileScreen$drawCard$anim$1 */ = 0;
         Object answer$iv = new Anim(0.0F, 14.0F);
         bg.put(key$iv, answer$iv);
         var10000 = answer$iv;
      } else {
         var10000 = value$iv;
      }

      Anim anim = (Anim)var10000;
      anim.setTarget(hovered ? 1.0F : 0.0F);
      anim.update(delta);
      int bgx = this.lerp(-14935012, -1, anim.getValue() * 0.4F);
      NVGRenderer.INSTANCE.rect(x, y, w, this.cardHeight, bgx, 8.0F);
      NVGRenderer.INSTANCE.rectOutline(x, y, w, this.cardHeight, 352321535, 8.0F, 0.0F);
      NVGRenderer.INSTANCE.rectVarying(x, y, 3.0F, this.cardHeight, 8.0F, 0.0F, 0.0F, 8.0F, -1750963);
      NVGRenderer.INSTANCE.text(p.getName(), x + 14.0F, y + 18.0F, 14.0F, -1184275, NVGRenderer.Font.MEDIUM);
      key$iv = p.getSettingCount() + " settings  ·  " + ProfileManager.INSTANCE.formatTimestamp(p.getLastModified());
      NVGRenderer.INSTANCE.text((String)key$iv, x + 14.0F, y + 40.0F, 12.0F, -10724260, null);
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
         NVGRenderer.INSTANCE.textCentered("Delete", deleteX + btnW / 2.0F, btnY + btnH / 2.0F, 13.0F, delHover ? -1750963 : -6513508, null);
      }
   }

   private final float toUi(double scaled) {
      Window var10000 = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(var10000, "getWindow(...)");
      Window window = var10000;
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

      String var10000 = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(var10000, "codepointAsString(...)");
      String s = var10000;
      int var3 = 0;

      for (int var4 = s.length(); var3 < var4; var3++) {
         char ch = s.charAt(var3);
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
      return lerp$ch(a, b, tt, 24) << 24 | lerp$ch(a, b, tt, 16) << 16 | lerp$ch(a, b, tt, 8) << 8 | lerp$ch(a, b, tt, 0);
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

   private static final int lerp$ch(int $a, int $b, float tt, int shift) {
      int ca = $a >>> shift & 0xFF;
      int cb = $b >>> shift & 0xFF;
      return RangesKt.coerceIn((int)(ca + (cb - ca) * tt), 0, 255);
   }
}
