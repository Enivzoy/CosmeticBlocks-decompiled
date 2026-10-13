package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.LabelSetting;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u00182\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0018B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u000eH\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0017\u001a\u00020\fH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019",
   d2 = {
         "Lcom/cb/gui/component/TextComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/LabelSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/LabelSetting;)V",
         "isHeader",
         "",
         "lines",
         "",
         "",
         "height",
         "",
         "getHeight",
         "()F",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "wrap",
         "text",
         "Companion",
         "cb_client"
   }
)
public final class TextComponent extends SettingComponent<LabelSetting> {
   @NotNull
   private static final TextComponent.Companion Companion = new TextComponent.Companion();
   private final boolean isHeader;
   @NotNull
   private final List<String> lines;
   @Deprecated
   public static final float LINE_HEIGHT = 13.0F;
   @Deprecated
   public static final float CHAR_WIDTH_RATIO = 0.62F;

   public TextComponent(@NotNull ClickGuiScreen gui, @NotNull LabelSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.isHeader = StringsKt.startsWith(setting.getText(), "---", false);
      this.lines = this.wrap(setting.getText());
   }

   @Override
   public float getHeight() {
      return (this.getSetting().getSpaceBefore() ? 8.0F : 0.0F) + this.lines.size() * 13.0F + 4.0F;
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      float top = this.getY() + (this.getSetting().getSpaceBefore() ? 8.0F : 0.0F) + 6.5F + 1.0F;
      int color = this.isHeader ? -1184275 : -6513508;
      NVGRenderer.Font font = this.isHeader ? NVGRenderer.Font.BOLD : NVGRenderer.Font.REGULAR;

      for (String line : this.lines) {
         NVGRenderer.INSTANCE.text(line, this.getX() + 16.0F, top, 12.0F, color, font);
         top += 13.0F;
      }
   }

   private final List<String> wrap(String text) {
      float avail = 212.0F;
      int maxChars = RangesKt.coerceAtLeast((int)(avail / 7.44F), 8);
      if (text.length() <= maxChars) {
         return CollectionsKt.listOf(text);
      }

      List out = new ArrayList();
      StringBuilder current = new StringBuilder();
      CharSequence var10000 = text;
      char[] word = new char[]{' '};

      for (String wordx : StringsKt.split(var10000, word, false, 0)) {
         if (current.length() == 0) {
            current.append(wordx);
         } else if (current.length() + 1 + wordx.length() <= maxChars) {
            current.append(' ').append(wordx);
         } else {
            String var10001 = current.toString();
            Intrinsics.checkNotNullExpressionValue(var10001, "toString(...)");
            out.add(var10001);
            current.setLength(0);
            current.append(wordx);
         }
      }

      if (current.length() > 0) {
         String var9 = current.toString();
         Intrinsics.checkNotNullExpressionValue(var9, "toString(...)");
         out.add(var9);
      }

      return out;
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007",
      d2 = {"Lcom/cb/gui/component/TextComponent$Companion;", "", "<init>", "()V", "LINE_HEIGHT", "", "CHAR_WIDTH_RATIO", "cb_client"}
   )
   private static final class Companion {
      private Companion() {
      }
   }
}
