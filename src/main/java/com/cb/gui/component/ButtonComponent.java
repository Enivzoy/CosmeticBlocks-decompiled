package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.ButtonSetting;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

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
