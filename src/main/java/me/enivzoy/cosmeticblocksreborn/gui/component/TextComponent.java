package me.enivzoy.cosmeticblocksreborn.gui.component;

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.setting.LabelSetting;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

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

      for (String wordx : StringsKt.split(text, new char[]{' '}, false, 0)) {
         if (current.length() == 0) {
            current.append(wordx);
         } else if (current.length() + 1 + wordx.length() <= maxChars) {
            current.append(' ').append(wordx);
         } else {
            String line = current.toString();
            Intrinsics.checkNotNullExpressionValue(line, "toString(...)");
            out.add(line);
            current.setLength(0);
            current.append(wordx);
         }
      }

      if (current.length() > 0) {
         String line = current.toString();
         Intrinsics.checkNotNullExpressionValue(line, "toString(...)");
         out.add(line);
      }

      return out;
   }

   private static final class Companion {
      private Companion() {
      }
   }
}
