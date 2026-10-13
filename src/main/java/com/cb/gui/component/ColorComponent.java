package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ColorSetting;
import java.awt.Color;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class ColorComponent extends SettingComponent<ColorSetting> {
   @NotNull
   private static final ColorComponent.Companion Companion = new ColorComponent.Companion();
   private boolean open;
   @NotNull
   private ColorComponent.Drag drag;
   @NotNull
   private final Anim openAnim;
   @Deprecated
   public static final float SV_HEIGHT = 86.0F;
   @Deprecated
   public static final float BAR_HEIGHT = 10.0F;
   @Deprecated
   public static final float GAP = 7.0F;
   @Deprecated
   public static final float EXTRA = 134.0F;

   public ColorComponent(@NotNull ClickGuiScreen gui, @NotNull ColorSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.drag = ColorComponent.Drag.NONE;
      this.openAnim = new Anim(0.0F, 16.0F);
   }

   @Override
   public float getHeight() {
      return 26.0F + this.openAnim.getValue() * 134.0F;
   }

   private final float getSvY() {
      return this.getY() + 26.0F + 7.0F;
   }

   private final float getHueY() {
      return this.getSvY() + 86.0F + 7.0F;
   }

   private final float getAlphaY() {
      return this.getHueY() + 10.0F + 7.0F;
   }

   private final float getInnerWidth() {
      return this.getPadRight() - this.getPadLeft();
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      this.openAnim.setTarget(this.open ? 1.0F : 0.0F);
      this.openAnim.update(delta);
      float swatchWidth = 26.0F;
      float swatchHeight = 14.0F;
      float swatchX = this.getPadRight() - swatchWidth;
      float swatchY = this.getY() + (26.0F - swatchHeight) / 2.0F;
      NVGRenderer.INSTANCE.rect(swatchX, swatchY, swatchWidth, swatchHeight, this.getSetting().getValue().intValue(), 4.0F);
      NVGRenderer.INSTANCE.rectOutline(swatchX, swatchY, swatchWidth, swatchHeight, 352321535, 4.0F, 1.0F);
      if (!(this.openAnim.getValue() <= 0.01F)) {
         if (this.drag != ColorComponent.Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
         }

         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.intersectScissor(this.getX(), this.getY() + 26.0F, this.getWidth(), this.openAnim.getValue() * 134.0F);

         try {
            this.renderSvBox();
            this.renderHueBar();
            this.renderAlphaBar();
         } finally {
            NVGRenderer.INSTANCE.restore();
         }
      }
   }

   private final void renderSvBox() {
      int hueRgb = Color.HSBtoRGB(this.getSetting().getHue(), 1.0F, 1.0F) & 16777215 | 0xFF000000;
      NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 5.0F, -1, hueRgb, true);
      NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 5.0F, 0, -16777216, false);
      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 352321535, 5.0F, 1.0F);
      float selX = this.getPadLeft() + this.getSetting().getSaturation() * this.getInnerWidth();
      float selY = this.getSvY() + (1.0F - this.getSetting().getBrightness()) * 86.0F;
      NVGRenderer.INSTANCE.circle(selX, selY, 5.0F, -16777216);
      NVGRenderer.INSTANCE.circle(selX, selY, 4.0F, -1);
      NVGRenderer.INSTANCE.circle(selX, selY, 2.5F, this.getSetting().getOpaqueRgb());
   }

   private final void renderHueBar() {
      int segments = 6;
      float segmentWidth = this.getInnerWidth() / segments;

      for (int i = 0; i < segments; i++) {
         int from = Color.HSBtoRGB((float)i / segments, 1.0F, 1.0F) & 16777215 | 0xFF000000;
         int to = Color.HSBtoRGB((float)(i + 1) / segments, 1.0F, 1.0F) & 16777215 | 0xFF000000;
         NVGRenderer.INSTANCE.gradientRect(this.getPadLeft() + i * segmentWidth, this.getHueY(), segmentWidth + 0.5F, 10.0F, 0.0F, from, to, true);
      }

      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getHueY(), this.getInnerWidth(), 10.0F, 352321535, 3.0F, 1.0F);
      this.selectorLine(this.getPadLeft() + this.getSetting().getHue() * this.getInnerWidth(), this.getHueY());
   }

   private final void renderAlphaBar() {
      NVGRenderer.INSTANCE.rect(this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0F, -14013910, 3.0F);
      NVGRenderer.INSTANCE
         .gradientRect(
            this.getPadLeft(),
            this.getAlphaY(),
            this.getInnerWidth(),
            10.0F,
            3.0F,
            this.getSetting().getOpaqueRgb() & 16777215,
            this.getSetting().getOpaqueRgb(),
            true
         );
      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0F, 352321535, 3.0F, 1.0F);
      this.selectorLine(this.getPadLeft() + this.getSetting().getAlpha() * this.getInnerWidth(), this.getAlphaY());
   }

   private final void selectorLine(float selX, float barY) {
      float clamped = RangesKt.coerceIn(selX, this.getPadLeft() + 1.0F, this.getPadRight() - 1.0F);
      NVGRenderer.INSTANCE.rect(clamped - 1.5F, barY - 1.5F, 3.0F, 13.0F, -16777216, 1.5F);
      NVGRenderer.INSTANCE.rect(clamped - 0.75F, barY - 0.75F, 1.5F, 11.5F, -1, 0.75F);
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (button != 0) {
         return false;
      }

      if (this.rowHovered(mouseX, mouseY)) {
         this.open = !this.open;
         return true;
      }

      if (this.open && this.contains(mouseX, mouseY)) {
         this.drag = mouseY >= this.getSvY() && mouseY <= this.getSvY() + 86.0F
            ? ColorComponent.Drag.SV
            : (
               mouseY >= this.getHueY() && mouseY <= this.getHueY() + 10.0F
                  ? ColorComponent.Drag.HUE
                  : (mouseY >= this.getAlphaY() && mouseY <= this.getAlphaY() + 10.0F ? ColorComponent.Drag.ALPHA : ColorComponent.Drag.NONE)
            );
         if (this.drag != ColorComponent.Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseDragged(float mouseX, float mouseY) {
      if (this.drag != ColorComponent.Drag.NONE) {
         this.updateDrag(mouseX, mouseY);
      }
   }

   @Override
   public void mouseReleased(int button) {
      if (button == 0) {
         this.drag = ColorComponent.Drag.NONE;
      }
   }

   private final void updateDrag(float mouseX, float mouseY) {
      if (!(this.getInnerWidth() <= 0.0F)) {
         float fx = RangesKt.coerceIn((mouseX - this.getPadLeft()) / this.getInnerWidth(), 0.0F, 1.0F);
         switch (this.drag) {
            case SV:
               this.getSetting().setHsba(this.getSetting().getHue(), fx, 1.0F - RangesKt.coerceIn((mouseY - this.getSvY()) / 86.0F, 0.0F, 1.0F), this.getSetting().getAlpha());
               break;
            case HUE:
               this.getSetting().setHsba(fx, this.getSetting().getSaturation(), this.getSetting().getBrightness(), this.getSetting().getAlpha());
               break;
            case ALPHA:
               this.getSetting().setHsba(this.getSetting().getHue(), this.getSetting().getSaturation(), this.getSetting().getBrightness(), fx);
            case NONE:
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }
   }

   private static final class Companion {
      private Companion() {
      }
   }

   private enum Drag {
      NONE,
      SV,
      HUE,
      ALPHA;

      @NotNull
      public static EnumEntries<ColorComponent.Drag> getEntries() {
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }
}
