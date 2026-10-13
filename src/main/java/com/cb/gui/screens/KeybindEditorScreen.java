package com.cb.gui.screens;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.gui.ClickGuiScreen;
import com.cb.gui.NvgScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.cb.setting.KeybindSetting;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
   d1 = "\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002:\u0001HB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0016J \u0010&\u001a\u00020$2\u0006\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0002JH\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\t2\u0006\u0010%\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u0016H\u0002J\u0010\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u000201H\u0002J\u0018\u00102\u001a\u00020\u00162\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u0016H\u0016J(\u00106\u001a\u00020\u00162\u0006\u0010'\u001a\u0002012\u0006\u0010(\u001a\u0002012\u0006\u00107\u001a\u0002012\u0006\u00108\u001a\u000201H\u0016J\u0010\u00109\u001a\u00020\u00162\u0006\u00103\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00162\u0006\u00103\u001a\u00020<H\u0016J8\u0010=\u001a\u00020\u00162\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010@\u001a\u00020\tH\u0002J(\u0010A\u001a\u00020$2\u0006\u0010B\u001a\u00020C2\u0006\u0010'\u001a\u00020D2\u0006\u0010(\u001a\u00020D2\u0006\u0010E\u001a\u00020\tH\u0016J\b\u0010F\u001a\u00020\u0016H\u0016J\b\u0010G\u001a\u00020$H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u001e\u001a\u001e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020 0\u001fj\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020 `!X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006I",
   d2 = {
         "Lcom/cb/gui/screens/KeybindEditorScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "allRows",
         "",
         "Lcom/cb/gui/screens/KeybindEditorScreen$Row;",
         "uiScale",
         "",
         "uiWidth",
         "uiHeight",
         "panelWidth",
         "panelHeight",
         "panelX",
         "panelY",
         "headerHeight",
         "searchRowHeight",
         "rowHeight",
         "search",
         "",
         "searchFocused",
         "",
         "capturing",
         "Lcom/cb/setting/KeybindSetting;",
         "scroll",
         "maxScroll",
         "caretBlink",
         "listTop",
         "listBottom",
         "rowHover",
         "Ljava/util/HashMap;",
         "Lcom/cb/render/Anim;",
         "Lkotlin/collections/HashMap;",
         "filtered",
         "renderOverlay",
         "",
         "delta",
         "draw",
         "mouseX",
         "mouseY",
         "drawRow",
         "row",
         "x",
         "y",
         "w",
         "sectionStart",
         "toUi",
         "scaled",
         "",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "mouseScrolled",
         "scrollX",
         "scrollY",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "charTyped",
         "Lnet/minecraft/client/input/CharacterEvent;",
         "inside",
         "mx",
         "my",
         "h",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "",
         "partialTick",
         "isPauseScreen",
         "onClose",
         "Row",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nKeybindEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KeybindEditorScreen.kt\ncom/cb/gui/screens/KeybindEditorScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,325:1\n1391#2:326\n1480#2,2:327\n812#2,12:329\n1586#2:341\n1661#2,3:342\n1482#2,3:345\n777#2:348\n873#2,2:349\n445#3,8:351\n410#4,7:359\n*S KotlinDebug\n*F\n+ 1 KeybindEditorScreen.kt\ncom/cb/gui/screens/KeybindEditorScreen\n*L\n32#1:326\n32#1:327,2\n33#1:329,12\n33#1:341\n33#1:342,3\n32#1:345,3\n65#1:348\n65#1:349,2\n152#1:351,8\n172#1:359,7\n*E\n"
)
public final class KeybindEditorScreen extends Screen implements NvgScreen {
   @NotNull
   private final List<KeybindEditorScreen.Row> allRows;
   private float uiScale;
   private float uiWidth;
   private float uiHeight;
   private final float panelWidth;
   private final float panelHeight;
   private float panelX;
   private float panelY;
   private final float headerHeight;
   private final float searchRowHeight;
   private final float rowHeight;
   @NotNull
   private String search;
   private boolean searchFocused;
   @Nullable
   private KeybindSetting capturing;
   private float scroll;
   private float maxScroll;
   private float caretBlink;
   private float listTop;
   private float listBottom;
   @NotNull
   private final HashMap<String, Anim> rowHover;

   public KeybindEditorScreen() {
      super(Component.literal("Keybinds"));
      Iterable tabs = Tabs.INSTANCE.getAll();
      KeybindEditorScreen ignored = this;
      Iterable source = tabs;
      var result = new ArrayList();

      for (Object element : source) {
         SettingsTab tab = (SettingsTab)element;
         Iterable settings = tab.getSettings();
         Iterable instanceSource = settings;
         var buffer = new ArrayList();

         for (Object candidate : instanceSource) {
            if (candidate instanceof KeybindSetting) {
               buffer.add(candidate);
            }
         }

         settings = (Iterable & List)buffer;
         instanceSource = settings;
         buffer = new ArrayList(CollectionsKt.collectionSizeOrDefault(settings, 10));

         for (Object item : instanceSource) {
            KeybindSetting it = (KeybindSetting)item;
            Collection rows = buffer;
            rows.add(new KeybindEditorScreen.Row(tab.getName(), it));
         }

         var list = (Iterable & List)buffer;
         CollectionsKt.addAll(result, list);
      }

      this.allRows = (List<KeybindEditorScreen.Row>)result;
      this.uiScale = 1.0F;
      this.panelWidth = 440.0F;
      this.panelHeight = 400.0F;
      this.headerHeight = 44.0F;
      this.searchRowHeight = 34.0F;
      this.rowHeight = 28.0F;
      this.search = "";
      this.rowHover = new HashMap<>();
   }

   private final List<KeybindEditorScreen.Row> filtered() {
      String q = StringsKt.trim(this.search).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(q, "toLowerCase(...)");
      if (q.length() == 0) {
         return this.allRows;
      }

      Iterable allRows = this.allRows;
      Iterable source = allRows;
      var result = new ArrayList();

      for (Object element : source) {
         boolean matches;
         label28: {
            KeybindEditorScreen.Row it = (KeybindEditorScreen.Row)element;
            String lower = it.getTabName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
            if (!StringsKt.contains(lower, q, false)) {
               lower = it.getSetting().getName().toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
               if (!StringsKt.contains(lower, q, false)) {
                  matches = false;
                  break label28;
               }
            }

            matches = true;
         }

         if (matches) {
            result.add(element);
         }
      }

      return (List<KeybindEditorScreen.Row>)result;
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
      NVGRenderer.INSTANCE.rectOutline(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 352321535, 10.0F, 0.0F);
      NVGRenderer.INSTANCE.rectVarying(this.panelX, this.panelY, this.panelWidth, this.headerHeight, 10.0F, 10.0F, 0.0F, 0.0F, -14935012);
      NVGRenderer.INSTANCE.text("Keybinds", this.panelX + 10.0F + 4.0F, this.panelY + 16.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      NVGRenderer.INSTANCE.text("Click a row to rebind · ESC/DEL clears", this.panelX + 10.0F + 4.0F, this.panelY + 33.0F, 12.0F, -10724260, null);
      float closeSize = 18.0F;
      float closeX = this.panelX + this.panelWidth - closeSize - 10.0F;
      float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0F;
      boolean closeHover = this.inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize);
      NVGRenderer.INSTANCE
         .textCentered("x", closeX + closeSize / 2.0F, closeY + closeSize / 2.0F, 14.0F, closeHover ? -1750963 : -6513508, NVGRenderer.Font.BOLD);
      float searchY = this.panelY + this.headerHeight;
      float fieldX = this.panelX + 10.0F;
      float fieldY = searchY + (this.searchRowHeight - 24.0F) / 2.0F;
      float fieldWidth = this.panelWidth - 20.0F;
      float fieldHeight = 24.0F;
      NVGRenderer.INSTANCE.rect(fieldX, fieldY, fieldWidth, fieldHeight, 1711276032, 5.0F);
      NVGRenderer.INSTANCE.rectOutline(fieldX, fieldY, fieldWidth, fieldHeight, this.searchFocused ? -1750963 : 352321535, 5.0F, 0.0F);
      boolean showCaret = this.searchFocused && this.caretBlink % 1.0F < 0.5F;
      String display = this.search.length() == 0 && !this.searchFocused ? "filter keybinds…" : this.search + (showCaret ? "|" : "");
      int tc = this.search.length() == 0 && !this.searchFocused ? -10724260 : -1184275;
      NVGRenderer.INSTANCE.text(display, fieldX + 8.0F, fieldY + fieldHeight / 2.0F, 13.0F, tc, null);
      this.listTop = searchY + this.searchRowHeight + 2.0F;
      this.listBottom = this.panelY + this.panelHeight - 10.0F;
      float listX = this.panelX + 10.0F;
      float listWidth = this.panelWidth - 20.0F;
      float listHeight = this.listBottom - this.listTop;
      List<KeybindEditorScreen.Row> rows = this.filtered();
      float contentHeight = rows.size() * this.rowHeight;
      this.maxScroll = RangesKt.coerceAtLeast(contentHeight - listHeight, 0.0F);
      this.scroll = RangesKt.coerceIn(this.scroll, 0.0F, this.maxScroll);
      NVGRenderer renderer = NVGRenderer.INSTANCE;
      float scissorX = listX;
      float scissorY = this.listTop;
      float scissorW = listWidth;
      float scissorH = listHeight;
      renderer.save();
      renderer.intersectScissor(scissorX, scissorY, scissorW, scissorH);

      try {
         if (rows.isEmpty()) {
            NVGRenderer.INSTANCE.textCentered("No matches.", this.panelX + this.panelWidth / 2.0F, this.listTop + 24.0F, 13.0F, -10724260, null);
         } else {
            float rowY = this.listTop - this.scroll;
            String currentTab = "";

            for (KeybindEditorScreen.Row row : rows) {
               if (rowY + this.rowHeight >= this.listTop && rowY <= this.listBottom) {
                  this.drawRow(row, listX, rowY, listWidth, mouseX, mouseY, delta, !Intrinsics.areEqual(row.getTabName(), currentTab));
               }

               currentTab = row.getTabName();
               rowY += this.rowHeight;
            }
         }
      } finally {
         renderer.restore();
      }
   }

   private final void drawRow(KeybindEditorScreen.Row row, float x, float y, float w, float mouseX, float mouseY, float delta, boolean sectionStart) {
      String key = row.getTabName() + "/" + row.getSetting().getName();
      boolean hovered = this.inside(mouseX, mouseY, x, y, w, this.rowHeight) && mouseY >= this.listTop && mouseY <= this.listBottom;
      Map label = this.rowHover;
      Object lookupKey = key;
      Object cached = label.get(lookupKey);
      Object value;
      if (cached == null) {
         Object answer = new Anim(0.0F, 14.0F);
         label.put(lookupKey, answer);
         value = answer;
      } else {
         value = cached;
      }

      Anim anim = (Anim)value;
      anim.setTarget(hovered ? 1.0F : 0.0F);
      anim.update(delta);
      if (anim.getValue() > 0.001F) {
         NVGRenderer.INSTANCE.rect(x, y, w, this.rowHeight, Theme.INSTANCE.withAlpha(-1, anim.getValue() * 0.07F), 5.0F);
      }

      String labelx = row.getTabName() + " · " + row.getSetting().getName();
      NVGRenderer.INSTANCE.text(labelx, x + 8.0F, y + this.rowHeight / 2.0F, 13.0F, -1184275, null);
      float btnW = 110.0F;
      float btnH = 20.0F;
      float btnX = x + w - btnW - 6.0F;
      float btnY = y + (this.rowHeight - btnH) / 2.0F;
      boolean isCapturing = this.capturing == row.getSetting();
      String text = isCapturing ? "press a key…" : row.getSetting().displayName();
      int bg = isCapturing ? 1088768077 : (hovered ? -14013910 : 1711276032);
      int fg = isCapturing ? -1750963 : (!row.getSetting().getBound() ? -10724260 : -1184275);
      NVGRenderer.INSTANCE.rect(btnX, btnY, btnW, btnH, bg, 5.0F);
      NVGRenderer.INSTANCE.textCentered(text, btnX + btnW / 2.0F, btnY + btnH / 2.0F, 12.0F, fg, NVGRenderer.Font.MEDIUM);
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

      KeybindSetting wasCapturing = this.capturing;
      this.capturing = null;
      float searchY = this.panelY + this.headerHeight;
      float fieldX = this.panelX + 10.0F;
      float fieldY = searchY + (this.searchRowHeight - 24.0F) / 2.0F;
      float fieldWidth = this.panelWidth - 20.0F;
      float fieldHeight = 24.0F;
      this.searchFocused = this.inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight);
      if (this.searchFocused) {
         return true;
      }

      if (my >= this.listTop && my <= this.listBottom) {
         float listX = this.panelX + 10.0F;
         float listWidth = this.panelWidth - 20.0F;
         float rowY = this.listTop - this.scroll;

         for (KeybindEditorScreen.Row row : this.filtered()) {
            if (this.inside(mx, my, listX, rowY, listWidth, this.rowHeight)) {
               this.capturing = wasCapturing == row.getSetting() ? null : row.getSetting();
               return true;
            }

            rowY += this.rowHeight;
         }
      }

      return true;
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
      KeybindSetting target = this.capturing;
      if (target != null) {
         switch (key) {
            case 256:
            case 259:
            case 261:
               target.clear();
               break;
            case 257:
            case 258:
            case 260:
            default:
               target.setValue(key);
         }

         this.capturing = null;
         Config.INSTANCE.save();
         return true;
      } else {
         if (this.searchFocused) {
            switch (key) {
               case 256:
                  this.searchFocused = false;
                  return true;
               case 257:
               case 258:
               default:
                  break;
               case 259:
                  if (this.search.length() > 0) {
                     this.search = StringsKt.dropLast(this.search, 1);
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
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (!this.searchFocused) {
         return false;
      }

      this.search = this.search + event.codepointAsString();
      this.scroll = 0.0F;
      return true;
   }

   private final boolean inside(float mx, float my, float x, float y, float w, float h) {
      return mx >= x && mx <= x + w && my >= y && my <= y + h;
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
      Config.INSTANCE.save();
      if (this.minecraft != null) {
         this.minecraft.setScreenAndShow(new ClickGuiScreen());
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0014\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0012\u001a\u00020\u0013HÖ\u0081\u0004J\n\u0010\u0014\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015",
      d2 = {
            "Lcom/cb/gui/screens/KeybindEditorScreen$Row;",
            "",
            "tabName",
            "",
            "setting",
            "Lcom/cb/setting/KeybindSetting;",
            "<init>",
            "(Ljava/lang/String;Lcom/cb/setting/KeybindSetting;)V",
            "getTabName",
            "()Ljava/lang/String;",
            "getSetting",
            "()Lcom/cb/setting/KeybindSetting;",
            "component1",
            "component2",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "",
            "toString",
            "cb_client"
      }
   )
   private static final class Row {
      @NotNull
      private final String tabName;
      @NotNull
      private final KeybindSetting setting;

      public Row(@NotNull String tabName, @NotNull KeybindSetting setting) {
         Intrinsics.checkNotNullParameter(tabName, "tabName");
         Intrinsics.checkNotNullParameter(setting, "setting");
         super();
         this.tabName = tabName;
         this.setting = setting;
      }

      @NotNull
      public final String getTabName() {
         return this.tabName;
      }

      @NotNull
      public final KeybindSetting getSetting() {
         return this.setting;
      }

      @NotNull
      public final String component1() {
         return this.tabName;
      }

      @NotNull
      public final KeybindSetting component2() {
         return this.setting;
      }

      @NotNull
      public final KeybindEditorScreen.Row copy(@NotNull String tabName, @NotNull KeybindSetting setting) {
         Intrinsics.checkNotNullParameter(tabName, "tabName");
         Intrinsics.checkNotNullParameter(setting, "setting");
         return new KeybindEditorScreen.Row(tabName, setting);
      }

      @NotNull
      @Override
      public String toString() {
         return "Row(tabName=" + this.tabName + ", setting=" + this.setting + ")";
      }

      @Override
      public int hashCode() {
         int result = this.tabName.hashCode();
         return result * 31 + this.setting.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof KeybindEditorScreen.Row that)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.tabName, that.tabName) ? false : Intrinsics.areEqual(this.setting, that.setting);
         }
      }
   }
}
