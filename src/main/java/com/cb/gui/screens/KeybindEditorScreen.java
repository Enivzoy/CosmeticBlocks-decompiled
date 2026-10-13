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
import kotlin.collections.CollectionsKt;
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
