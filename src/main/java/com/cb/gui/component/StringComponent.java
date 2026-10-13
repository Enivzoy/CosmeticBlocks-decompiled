package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.StringSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0010H\u0016J \u0010\u0016\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u000eH\u0016J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f",
   d2 = {
         "Lcom/cb/gui/component/StringComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/StringSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/StringSetting;)V",
         "value",
         "",
         "focused",
         "getFocused",
         "()Z",
         "cursor",
         "",
         "blinkTimer",
         "",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "mouseClicked",
         "button",
         "keyPressed",
         "key",
         "modifiers",
         "charTyped",
         "text",
         "",
         "insert",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nStringComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringComponent.kt\ncom/cb/gui/component/StringComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,116:1\n445#2,8:117\n437#3:125\n513#3,5:126\n*S KotlinDebug\n*F\n+ 1 StringComponent.kt\ncom/cb/gui/component/StringComponent\n*L\n48#1:117,8\n107#1:125\n107#1:126,5\n*E\n"
)
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
      float nameWidth = NVGRenderer.INSTANCE.textWidth(this.getSetting().getName(), 12.0F, null) + 12.0F;
      float maxTextWidth = RangesKt.coerceAtLeast(this.getPadRight() - this.getPadLeft() - nameWidth, 20.0F);
      float midY = this.getY() + 13.0F;
      NVGRenderer this_$iv = NVGRenderer.INSTANCE;
      float x$iv = this.getPadRight() - maxTextWidth;
      float y$iv = this.getY();
      float w$iv = maxTextWidth;
      float h$iv = 26.0F;
      int $i$f$scissor = 0;
      this_$iv.save();
      this_$iv.intersectScissor(x$iv, y$iv, w$iv, h$iv);

      try {
         int var17/* $VF was: $i$a$-scissor-StringComponent$render$1 */ = 0;
         NVGRenderer.INSTANCE.textRight(display, this.getPadRight(), midY, 12.0F, color, NVGRenderer.Font.REGULAR);
         if (this.focused && this.blinkTimer % 1.0F < 0.55F) {
            float textWidth = NVGRenderer.INSTANCE.textWidth(value, 12.0F, null);
            float textStart = this.getPadRight() - textWidth;
            NVGRenderer var10001 = NVGRenderer.INSTANCE;
            String var10002 = value.substring(0, this.cursor);
            Intrinsics.checkNotNullExpressionValue(var10002, "substring(...)");
            float caretX = textStart + var10001.textWidth(var10002, 12.0F, null);
            NVGRenderer.INSTANCE.rect(RangesKt.coerceAtMost(caretX, this.getPadRight()), midY - 6.0F, 1.2F, 12.0F, -1750963, 0.0F);
         }
      } finally {
         this_$iv.restore();
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
               String var10001 = Minecraft.getInstance().keyboardHandler.getClipboard();
               Intrinsics.checkNotNullExpressionValue(var10001, "getClipboard(...)");
               this.insert(var10001);
            }
            break;
         case 256:
         case 257:
         case 335:
            this.focused = false;
            break;
         case 259:
            if (this.cursor > 0) {
               StringSetting var10 = this.getSetting();
               String var6 = this.getSetting().getValue();
               int var8 = this.cursor - 1;
               int var9 = this.cursor;
               var10.setValue(StringsKt.removeRange(var6, var8, var9).toString());
               int var7 = this.cursor;
               this.cursor = var7 + -1;
            }
            break;
         case 261:
            if (this.cursor < this.getSetting().getValue().length()) {
               StringSetting var10000 = this.getSetting();
               String var3 = this.getSetting().getValue();
               int var4 = this.cursor;
               int var5 = this.cursor + 1;
               var10000.setValue(StringsKt.removeRange(var3, var4, var5).toString());
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
      String $this$filter$iv = text;
      int $i$f$filter = 0;
      CharSequence $this$filterTo$iv$iv = $this$filter$iv;
      var destination$iv$iv = new StringBuilder();
      int $i$f$filterTo = 0;
      int index$iv$iv = 0;

      for (int var9 = $this$filterTo$iv$iv.length(); index$iv$iv < var9; index$iv$iv++) {
         char element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
         char it = element$iv$iv;
         int var12/* $VF was: $i$a$-filter-StringComponent$insert$clean$1 */ = 0;
         if (it >= ' ' && it != 127) {
            destination$iv$iv.append(element$iv$iv);
         }
      }

      String clean = ((StringBuilder)destination$iv$iv).toString();
      if (clean.length() != 0) {
         $this$filter$iv = this.getSetting().getValue();
         StringSetting var10000 = this.getSetting();
         String var10001 = new StringBuilder($this$filter$iv).insert(this.cursor, clean).toString();
         Intrinsics.checkNotNullExpressionValue(var10001, "toString(...)");
         var10000.setValue(var10001);
         this.cursor = RangesKt.coerceIn(
            this.cursor + (this.getSetting().getValue().length() - $this$filter$iv.length()), 0, this.getSetting().getValue().length()
         );
         this.blinkTimer = 0.0F;
      }
   }
}
