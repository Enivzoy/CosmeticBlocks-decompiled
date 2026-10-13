package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.NumberSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\tH\u0016J \u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0016J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0002R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u000e\u0010\u0012\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000¨\u0006\u001e",
   d2 = {
         "Lcom/cb/gui/component/SliderComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/NumberSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/NumberSetting;)V",
         "height",
         "",
         "getHeight",
         "()F",
         "fillAnim",
         "Lcom/cb/render/Anim;",
         "dragging",
         "",
         "trackY",
         "getTrackY",
         "trackHeight",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "mouseClicked",
         "button",
         "",
         "mouseDragged",
         "mouseReleased",
         "updateFromMouse",
         "cb_client"
   }
)
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
      } else {
         float var4 = this.getX();
         if ((mouseX <= this.getX() + this.getWidth() ? var4 <= mouseX : false) && mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight()) {
            this.dragging = true;
            this.updateFromMouse(mouseX);
            return true;
         } else {
            return false;
         }
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
