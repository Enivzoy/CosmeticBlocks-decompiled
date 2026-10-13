package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.ButtonSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016",
   d2 = {
         "Lcom/cb/gui/component/ButtonComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/ButtonSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ButtonSetting;)V",
         "pillX",
         "",
         "pillY",
         "pillW",
         "pillH",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "mouseClicked",
         "",
         "button",
         "",
         "cb_client"
   }
)
public final class ButtonComponent extends SettingComponent<ButtonSetting> {
   private float pillX;
   private float pillY;
   private float pillW;
   private float pillH;

   public ButtonComponent(@NotNull ClickGuiScreen gui, @NotNull ButtonSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      this.pillW = RangesKt.coerceAtLeast(
         NVGRenderer.INSTANCE.textWidth(this.getSetting().getActionLabel(), 12.0F, null) + 18.0F, 44.0F
      );
      this.pillH = 15.0F;
      this.pillX = this.getPadRight() - this.pillW;
      this.pillY = this.getY() + (26.0F - this.pillH) / 2.0F;
      boolean hovered = this.isPillHovered(mouseX, mouseY);
      int bg = hovered ? -1750963 : -14013910;
      NVGRenderer.INSTANCE.rect(this.pillX, this.pillY, this.pillW, this.pillH, bg, this.pillH / 2.0F);
      NVGRenderer.INSTANCE.textCentered(this.getSetting().getActionLabel(), this.pillX + this.pillW / 2.0F, this.pillY + this.pillH / 2.0F, 12.0F, -1184275, null);
   }

   private boolean isPillHovered(float mouseX, float mouseY) {
      return mouseX >= this.pillX && mouseX <= this.pillX + this.pillW && mouseY >= this.pillY && mouseY <= this.pillY + this.pillH;
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (button == 0 && this.isPillHovered(mouseX, mouseY)) {
         this.getSetting().getOnClick().invoke();
         return true;
      }

      return false;
   }
}
