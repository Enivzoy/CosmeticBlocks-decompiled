package com.cb.gui.screens;

import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.List;
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
import org.lwjgl.glfw.GLFW;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0006H\u0016J(\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u0006H\u0002J \u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J \u0010$\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J\u0018\u0010%\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0006H\u0002J(\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020\u0006H\u0016J\b\u0010,\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020-H\u0016J\u0010\u00102\u001a\u00020-2\u0006\u0010/\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020-2\u0006\u0010/\u001a\u000205H\u0016J\b\u00106\u001a\u00020\u001aH\u0002J\u0010\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u00020\u000fH\u0002J\b\u00109\u001a\u00020\u001aH\u0002J\u0010\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020\nH\u0002J\u0010\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020>H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006?",
   d2 = {
         "Lcom/cb/gui/screens/CliScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "uiScale",
         "",
         "uiWidth",
         "uiHeight",
         "input",
         "",
         "caret",
         "",
         "suggestions",
         "",
         "Lcom/cb/gui/screens/CliEngine$Suggestion;",
         "suggestionIdx",
         "historyIdx",
         "lastResult",
         "lastResultUntil",
         "",
         "caretBlink",
         "barWidth",
         "getBarWidth",
         "()F",
         "renderOverlay",
         "",
         "delta",
         "renderBar",
         "x",
         "y",
         "w",
         "h",
         "renderSuggestions",
         "barX",
         "barY",
         "renderFeedback",
         "renderHistoryHint",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "mouseX",
         "mouseY",
         "partialTick",
         "isPauseScreen",
         "",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "charTyped",
         "Lnet/minecraft/client/input/CharacterEvent;",
         "onInputChanged",
         "applySuggestion",
         "s",
         "cycleHistory",
         "executeAndRecord",
         "cmd",
         "toUi",
         "scaled",
         "",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nCliScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CliScreen.kt\ncom/cb/gui/screens/CliScreen\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,298:1\n1088#2,2:299\n*S KotlinDebug\n*F\n+ 1 CliScreen.kt\ncom/cb/gui/screens/CliScreen\n*L\n251#1:299,2\n*E\n"
)
public final class CliScreen extends Screen implements NvgScreen {
   private float uiScale = 1.0F;
   private float uiWidth;
   private float uiHeight;
   @NotNull
   private String input = "";
   private int caret;
   @NotNull
   private List<CliEngine.Suggestion> suggestions = CollectionsKt.emptyList();
   private int suggestionIdx;
   private int historyIdx = -1;
   @NotNull
   private String lastResult = "";
   private long lastResultUntil;
   private float caretBlink;

   public CliScreen() {
      super(Component.literal("CB Command Line"));
   }

   private final float getBarWidth() {
      return RangesKt.coerceIn(this.uiWidth * 0.6F, 280.0F, 620.0F);
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
         this.caretBlink += delta;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, 1493172224, 0.0F);
               float barH = 30.0F;
               float barX = (this.uiWidth - this.getBarWidth()) / 2.0F;
               float barY = this.uiHeight * 0.62F;
               this.suggestions = CliEngine.INSTANCE.suggestions(this.input);
               if (this.suggestionIdx >= this.suggestions.size()) {
                  this.suggestionIdx = 0;
               }

               this.renderSuggestions(barX, barY, this.getBarWidth());
               this.renderBar(barX, barY, this.getBarWidth(), barH);
               this.renderFeedback(barX, barY + barH + 6.0F, this.getBarWidth());
               this.renderHistoryHint(barX, barY);
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void renderBar(float x, float y, float w, float h) {
      NVGRenderer.INSTANCE.shadow(x, y, w, h, 10.0F, 12.0F, Integer.MIN_VALUE);
      NVGRenderer.INSTANCE.rect(x, y, w, h, -183233516, 10.0F);
      NVGRenderer.INSTANCE.rectOutline(x, y, w, h, Theme.INSTANCE.withAlpha(-1750963, 0.55F), 10.0F, 0.0F);
      float padX = 12.0F;
      float cy = y + h / 2.0F;
      NVGRenderer.INSTANCE.text(">", x + padX, cy, 13.0F, -1750963, NVGRenderer.Font.BOLD);
      float textX = x + padX + 16.0F;
      if (this.input.length() == 0) {
         NVGRenderer.INSTANCE.text("type a command, Tab to complete, Enter to run", textX, cy, 13.0F, -10724260, null);
      } else {
         NVGRenderer.INSTANCE.text(this.input, textX, cy, 13.0F, -1184275, null);
      }

      if ((int)(this.caretBlink * 2.0F) % 2 == 0) {
         String before = StringsKt.take(this.input, RangesKt.coerceIn(this.caret, 0, this.input.length()));
         float caretX = textX + NVGRenderer.INSTANCE.textWidth(before, 13.0F, null);
         NVGRenderer.INSTANCE.rect(caretX, cy - 6.5F, 1.0F, 13.0F, -1184275, 0.0F);
      }
   }

   private final void renderSuggestions(float barX, float barY, float w) {
      if (!this.suggestions.isEmpty()) {
         float rowH = 20.0F;
         float popH = this.suggestions.size() * rowH + 8.0F;
         float x = barX;
         float y = barY - popH - 6.0F;
         NVGRenderer.INSTANCE.shadow(x, y, w, popH, 8.0F, 12.0F, Integer.MIN_VALUE);
         NVGRenderer.INSTANCE.rect(x, y, w, popH, -14935012, 8.0F);
         NVGRenderer.INSTANCE.rectOutline(x, y, w, popH, Theme.INSTANCE.withAlpha(-1750963, 0.3F), 8.0F, 0.0F);
         float ry = y + 4.0F;
         int i = 0;

         for (CliEngine.Suggestion s : this.suggestions) {
            boolean highlight = i == this.suggestionIdx;
            if (highlight) {
               NVGRenderer.INSTANCE.rect(x + 3.0F, ry, w - 6.0F, rowH, Theme.INSTANCE.withAlpha(-1750963, 0.22F), 4.0F);
            }

            int labelColor = highlight ? -1750963 : -1184275;
            NVGRenderer.INSTANCE.text(s.getDisplay(), x + 12.0F, ry + rowH / 2.0F, 12.0F, labelColor, null);
            if (s.getHint().length() > 0) {
               NVGRenderer.INSTANCE.textRight(s.getHint(), x + w - 12.0F, ry + rowH / 2.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
            }

            ry += rowH;
            i++;
         }
      }
   }

   private final void renderFeedback(float x, float y, float w) {
      long now = System.currentTimeMillis();
      if (now < this.lastResultUntil && this.lastResult.length() != 0) {
         float alpha = RangesKt.coerceIn((float)(this.lastResultUntil - now) / 3500.0F, 0.0F, 1.0F);
         NVGRenderer.INSTANCE.text(this.lastResult, x + 4.0F, y + 8.0F, 12.0F, Theme.INSTANCE.withAlpha(-6513508, alpha), null);
      }
   }

   private final void renderHistoryHint(float barX, float barY) {
      if (this.input.length() <= 0) {
         List<String> hist = CliEngine.INSTANCE.history();
         if (!hist.isEmpty()) {
            List<String> recent = CollectionsKt.reversed(CollectionsKt.takeLast(hist, 3));
            float ry = barY + 36.0F;

            for (String h : recent) {
               NVGRenderer.INSTANCE.text("· " + h, barX + 4.0F, ry, 12.0F, -10724260, null);
               ry += 14.0F;
            }
         }
      }
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
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      float barH = 30.0F;
      float barX = (this.uiWidth - this.getBarWidth()) / 2.0F;
      float barY = this.uiHeight * 0.62F;
      float popH = this.suggestions.isEmpty() ? 0.0F : this.suggestions.size() * 20.0F + 8.0F;
      float mx = this.toUi(event.x());
      float my = this.toUi(event.y());
      boolean insideBar = (barX <= mx ? mx <= barX + this.getBarWidth() : false) && (barY <= my ? my <= barY + barH : false);
      boolean insidePop = (barX <= mx ? mx <= barX + this.getBarWidth() : false) && (barY - popH - 6.0F <= my ? my <= barY : false);
      if (!insideBar && !insidePop) {
         this.onClose();
         return true;
      }

      if (insidePop && !this.suggestions.isEmpty()) {
         float rowH = 20.0F;
         float popY = barY - popH - 6.0F + 4.0F;
         int idx = (int)((my - popY) / rowH);
         if (0 <= idx ? idx < this.suggestions.size() : false) {
            this.applySuggestion(this.suggestions.get(idx));
         }
      }

      return true;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      switch (event.key()) {
         case 86:
            if ((event.modifiers() & 2) != 0) {
               String clip = GLFW.glfwGetClipboardString(GLFW.glfwGetCurrentContext());
               if (clip != null) {
                  String before = this.input.substring(0, this.caret);
                  Intrinsics.checkNotNullExpressionValue(before, "substring(...)");
                  String after = this.input.substring(this.caret);
                  Intrinsics.checkNotNullExpressionValue(after, "substring(...)");
                  this.input = before + clip + after;
                  this.caret = this.caret + clip.length();
                  this.onInputChanged();
               }

               return true;
            }
         default:
            return super.keyPressed(event);
         case 256:
            this.onClose();
            return true;
         case 257:
         case 335:
            String cmd = StringsKt.trim(this.input).toString();
            if (cmd.length() > 0) {
               this.executeAndRecord(cmd);
            }

            return true;
         case 258:
            if (!this.suggestions.isEmpty()) {
               this.applySuggestion(this.suggestions.get(RangesKt.coerceIn(this.suggestionIdx, 0, this.suggestions.size() - 1)));
            }

            return true;
         case 259:
            if (this.caret > 0 && this.input.length() > 0) {
               String text = this.input;
               int deleteFrom = this.caret - 1;
               int deleteTo = this.caret;
               this.input = StringsKt.removeRange(text, deleteFrom, deleteTo).toString();
               this.caret = this.caret - 1;
               this.onInputChanged();
            }

            return true;
         case 261:
            if (this.caret < this.input.length()) {
               String text = this.input;
               int deleteFrom = this.caret;
               int deleteTo = this.caret + 1;
               this.input = StringsKt.removeRange(text, deleteFrom, deleteTo).toString();
               this.onInputChanged();
            }

            return true;
         case 262:
            this.caret = RangesKt.coerceAtMost(this.caret + 1, this.input.length());
            return true;
         case 263:
            this.caret = RangesKt.coerceAtLeast(this.caret - 1, 0);
            return true;
         case 264:
            if (!this.suggestions.isEmpty()) {
               this.suggestionIdx = RangesKt.coerceAtMost(this.suggestionIdx + 1, this.suggestions.size() - 1);
            }

            return true;
         case 265:
            if (!this.suggestions.isEmpty() && this.suggestionIdx > 0) {
               this.suggestionIdx--;
            } else {
               this.cycleHistory();
            }

            return true;
         case 268:
            this.caret = 0;
            return true;
         case 269:
            this.caret = this.input.length();
            return true;
      }
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String s = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(s, "codepointAsString(...)");
      if (s.length() > 0) {
         CharSequence chars = s;
         boolean allPrintable;

         label31: {
            for (int i = 0; i < chars.length(); i++) {
               char c = chars.charAt(i);
               if (c < ' ') {
                  allPrintable = false;
                  break label31;
               }
            }

            allPrintable = true;
         }

         if (allPrintable) {
            String before = this.input.substring(0, this.caret);
            Intrinsics.checkNotNullExpressionValue(before, "substring(...)");
            String after = this.input.substring(this.caret);
            Intrinsics.checkNotNullExpressionValue(after, "substring(...)");
            this.input = before + s + after;
            this.caret = this.caret + s.length();
            this.onInputChanged();
            return true;
         }
      }

      return false;
   }

   private final void onInputChanged() {
      this.suggestionIdx = 0;
      this.historyIdx = -1;
   }

   private final void applySuggestion(CliEngine.Suggestion s) {
      boolean needsSpace = !StringsKt.endsWith(s.getInsert(), ' ', false);
      this.input = needsSpace ? s.getInsert() + " " : s.getInsert();
      this.caret = this.input.length();
      this.suggestionIdx = 0;
   }

   private final void cycleHistory() {
      List hist = CliEngine.INSTANCE.history();
      if (!hist.isEmpty()) {
         this.historyIdx = this.historyIdx < 0 ? hist.size() - 1 : RangesKt.coerceAtLeast(this.historyIdx - 1, 0);
         this.input = (String)hist.get(this.historyIdx);
         this.caret = this.input.length();
      }
   }

   private final void executeAndRecord(String cmd) {
      this.lastResult = CliEngine.INSTANCE.execute(cmd);
      this.lastResultUntil = System.currentTimeMillis() + 3500;
      this.input = "";
      this.caret = 0;
      this.historyIdx = -1;
      this.suggestionIdx = 0;
   }

   private final float toUi(double scaled) {
      Window window = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      return (float)(scaled * window.getGuiScale() / this.uiScale);
   }
}
