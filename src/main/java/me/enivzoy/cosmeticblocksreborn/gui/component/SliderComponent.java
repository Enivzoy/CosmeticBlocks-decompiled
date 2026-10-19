package me.enivzoy.cosmeticblocksreborn.gui.component;

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.render.Anim;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class SliderComponent extends SettingComponent<NumberSetting> {
   @NotNull
   private final Anim fillAnim;
   private boolean dragging;
   private final float trackHeight;

   public SliderComponent(@NotNull ClickGuiScreen gui, @NotNull NumberSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.fillAnim = new Anim(setting.getFraction(), 22.0F);
      this.trackHeight = 3.0F;
   }

   @Override
   public float getHeight() {
      return 32.0F;
   }

   private final float getTrackY() {
      return this.getY() + 26.0F + 1.0F;
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      NVGRenderer.INSTANCE.textRight(this.getSetting().displayValue(), this.getPadRight(), this.getY() + 13.0F, 12.0F, -6513508, NVGRenderer.Font.MEDIUM);
      if (this.dragging) {
         this.updateFromMouse(mouseX);
      }

      this.fillAnim.setTarget(this.getSetting().getFraction());
      float fill = this.fillAnim.update(delta);
      float trackWidth = this.getPadRight() - this.getPadLeft();
      NVGRenderer.INSTANCE.rect(this.getPadLeft(), this.getTrackY(), trackWidth, this.trackHeight, -14013910, this.trackHeight / 2.0F);
      if (fill > 0.001F) {
         NVGRenderer.INSTANCE.rect(this.getPadLeft(), this.getTrackY(), trackWidth * fill, this.trackHeight, -1750963, this.trackHeight / 2.0F);
      }

      float knobRadius = this.dragging ? 5.0F : 4.0F;
      NVGRenderer.INSTANCE.circle(this.getPadLeft() + trackWidth * fill, this.getTrackY() + this.trackHeight / 2.0F, knobRadius, -1184275);
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (button != 0) {
         return false;
      } else if (this.contains(mouseX, mouseY)) {
         this.dragging = true;
         this.updateFromMouse(mouseX);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseDragged(float mouseX, float mouseY) {
      if (this.dragging) {
         this.updateFromMouse(mouseX);
      }
   }

   @Override
   public void mouseReleased(int button) {
      if (button == 0) {
         this.dragging = false;
      }
   }

   private final void updateFromMouse(float mouseX) {
      float trackWidth = this.getPadRight() - this.getPadLeft();
      if (!(trackWidth <= 0.0F)) {
         this.getSetting().setFromFraction((mouseX - this.getPadLeft()) / trackWidth);
      }
   }
}
