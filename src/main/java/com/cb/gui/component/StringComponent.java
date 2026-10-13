package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.StringSetting;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public final class StringComponent extends SettingComponent<StringSetting> {
   private boolean focused;
   private int cursor;
   private float blinkTimer;

   public StringComponent(@NotNull ClickGuiScreen gui, @NotNull StringSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
   }

   public final boolean getFocused() {
      return this.focused;
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      this.blinkTimer += delta;
      String value = this.getSetting().getValue();
      boolean empty = value.length() == 0;
      String display = empty && !this.focused ? this.getSetting().getPlaceholder() : value;
      int color = this.focused ? -1184275 : (empty ? -10724260 : -6513508);
      float nameWidth = NVGRenderer.INSTANCE.textWidth(this.getSetting().getName(), 12.0F, NVGRenderer.Font.REGULAR) + 12.0F;
      float maxTextWidth = RangesKt.coerceAtLeast(this.getPadRight() - this.getPadLeft() - nameWidth, 20.0F);
      float midY = this.getY() + 13.0F;
      NVGRenderer.INSTANCE.save();
      NVGRenderer.INSTANCE.intersectScissor(this.getPadRight() - maxTextWidth, this.getY(), maxTextWidth, 26.0F);

      try {
         NVGRenderer.INSTANCE.textRight(display, this.getPadRight(), midY, 12.0F, color, NVGRenderer.Font.REGULAR);
         if (this.focused && this.blinkTimer % 1.0F < 0.55F) {
            float textWidth = NVGRenderer.INSTANCE.textWidth(value, 12.0F, NVGRenderer.Font.REGULAR);
            float textStart = this.getPadRight() - textWidth;
            String prefix = value.substring(0, this.cursor);
            Intrinsics.checkNotNullExpressionValue(prefix, "substring(...)");
            float caretX = textStart + NVGRenderer.INSTANCE.textWidth(prefix, 12.0F, NVGRenderer.Font.REGULAR);
            NVGRenderer.INSTANCE.rect(RangesKt.coerceAtMost(caretX, this.getPadRight()), midY - 6.0F, 1.2F, 12.0F, -1750963, 0.0F);
         }
      } finally {
         NVGRenderer.INSTANCE.restore();
      }

      if (this.focused) {
         NVGRenderer.INSTANCE.rect(this.getPadRight() - maxTextWidth, this.getY() + 26.0F - 4.0F, maxTextWidth, 1.5F, 1088768077, 0.75F);
      }
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      boolean inside = this.rowHovered(mouseX, mouseY);
      if (button == 0 && inside) {
         this.focused = true;
         this.cursor = this.getSetting().getValue().length();
         this.blinkTimer = 0.0F;
         return true;
      }

      if (this.focused && !inside) {
         this.focused = false;
      }

      return false;
   }

   @Override
   public boolean keyPressed(int key, int modifiers) {
      if (!this.focused) {
         return false;
      }

      this.blinkTimer = 0.0F;
      switch (key) {
          case 86:
            if ((modifiers & 2) != 0) {
               String clipboard = Minecraft.getInstance().keyboardHandler.getClipboard();
               Intrinsics.checkNotNullExpressionValue(clipboard, "getClipboard(...)");
               this.insert(clipboard);
            }
            break;
          case 256:
          case 257:
          case 335:
            this.focused = false;
            break;
          case 259:
            if (this.cursor > 0) {
               StringSetting setting = this.getSetting();
               String current = this.getSetting().getValue();
               setting.setValue(StringsKt.removeRange(current, this.cursor - 1, this.cursor).toString());
               this.cursor--;
            }
            break;
          case 261:
            if (this.cursor < this.getSetting().getValue().length()) {
               StringSetting setting = this.getSetting();
               String current = this.getSetting().getValue();
               setting.setValue(StringsKt.removeRange(current, this.cursor, this.cursor + 1).toString());
            }
            break;
         case 262:
            this.cursor = RangesKt.coerceAtMost(this.cursor + 1, this.getSetting().getValue().length());
            break;
         case 263:
            this.cursor = RangesKt.coerceAtLeast(this.cursor - 1, 0);
            break;
         case 268:
            this.cursor = 0;
            break;
         case 269:
            this.cursor = this.getSetting().getValue().length();
      }

      return true;
   }

   @Override
   public boolean charTyped(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      if (!this.focused) {
         return false;
      }

      this.insert(text);
      return true;
   }

   private final void insert(String text) {
      StringBuilder filtered = new StringBuilder();

      for (int index = 0; index < text.length(); index++) {
         char ch = text.charAt(index);
         if (ch >= ' ' && ch != 127) {
            filtered.append(ch);
         }
      }

      String clean = filtered.toString();
      if (clean.length() != 0) {
         String original = this.getSetting().getValue();
         StringSetting setting = this.getSetting();
         setting.setValue(new StringBuilder(original).insert(this.cursor, clean).toString());
         this.cursor = RangesKt.coerceIn(
            this.cursor + (this.getSetting().getValue().length() - original.length()), 0, this.getSetting().getValue().length()
         );
         this.blinkTimer = 0.0F;
      }
   }
}
