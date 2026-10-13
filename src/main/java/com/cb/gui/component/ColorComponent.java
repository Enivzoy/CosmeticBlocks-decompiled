package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ColorSetting;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 +2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002+,B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000fH\u0016J\b\u0010\u001f\u001a\u00020\u001bH\u0002J\b\u0010 \u001a\u00020\u001bH\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\u0018\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000fH\u0002J \u0010%\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020'H\u0016J\u0018\u0010(\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fH\u0016J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020'H\u0016J\u0018\u0010*\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0011R\u0014\u0010\u0016\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0011R\u0014\u0010\u0018\u001a\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011¨\u0006-",
   d2 = {
         "Lcom/cb/gui/component/ColorComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/ColorSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ColorSetting;)V",
         "open",
         "",
         "drag",
         "Lcom/cb/gui/component/ColorComponent$Drag;",
         "openAnim",
         "Lcom/cb/render/Anim;",
         "height",
         "",
         "getHeight",
         "()F",
         "svY",
         "getSvY",
         "hueY",
         "getHueY",
         "alphaY",
         "getAlphaY",
         "innerWidth",
         "getInnerWidth",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "renderSvBox",
         "renderHueBar",
         "renderAlphaBar",
         "selectorLine",
         "selX",
         "barY",
         "mouseClicked",
         "button",
         "",
         "mouseDragged",
         "mouseReleased",
         "updateDrag",
         "Companion",
         "Drag",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nColorComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ColorComponent.kt\ncom/cb/gui/component/ColorComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n*L\n1#1,150:1\n445#2,8:151\n*S KotlinDebug\n*F\n+ 1 ColorComponent.kt\ncom/cb/gui/component/ColorComponent\n*L\n59#1:151,8\n*E\n"
)
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
      NVGRenderer.INSTANCE.rectOutline(swatchX, swatchY, swatchWidth, swatchHeight, 352321535, 4.0F, 0.0F);
      if (!(this.openAnim.getValue() <= 0.01F)) {
         if (this.drag != ColorComponent.Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
         }

         NVGRenderer this_$iv = NVGRenderer.INSTANCE;
         float x$iv = this.getX();
         float y$iv = this.getY() + 26.0F;
         float w$iv = this.getWidth();
         float h$iv = this.openAnim.getValue() * 134.0F;
         int $i$f$scissor = 0;
         this_$iv.save();
         this_$iv.intersectScissor(x$iv, y$iv, w$iv, h$iv);

         try {
            int var14/* $VF was: $i$a$-scissor-ColorComponent$render$1 */ = 0;
            this.renderSvBox();
            this.renderHueBar();
            this.renderAlphaBar();
         } finally {
            this_$iv.restore();
         }
      }
   }

   private final void renderSvBox() {
      int hueRgb = Color.HSBtoRGB(this.getSetting().getHue(), 1.0F, 1.0F) & 16777215 | 0xFF000000;
      NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 5.0F, -1, hueRgb, true);
      NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 5.0F, 0, -16777216, false);
      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0F, 352321535, 5.0F, 0.0F);
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

      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getHueY(), this.getInnerWidth(), 10.0F, 352321535, 3.0F, 0.0F);
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
      NVGRenderer.INSTANCE.rectOutline(this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0F, 352321535, 3.0F, 0.0F);
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
               this.getSetting().setHsba(0.0F, fx, 1.0F - RangesKt.coerceIn((mouseY - this.getSvY()) / 86.0F, 0.0F, 1.0F), 0.0F);
               break;
            case HUE:
               this.getSetting().setHsba(fx, 0.0F, 0.0F, 0.0F);
               break;
            case ALPHA:
               this.getSetting().setHsba(0.0F, 0.0F, 0.0F, fx);
            case NONE:
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\t",
      d2 = {"Lcom/cb/gui/component/ColorComponent$Companion;", "", "<init>", "()V", "SV_HEIGHT", "", "BAR_HEIGHT", "GAP", "EXTRA", "cb_client"}
   )
   private static final class Companion {
      private Companion() {
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b",
      d2 = {"Lcom/cb/gui/component/ColorComponent$Drag;", "", "<init>", "(Ljava/lang/String;I)V", "NONE", "SV", "HUE", "ALPHA", "cb_client"}
   )
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
