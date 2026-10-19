package me.enivzoy.cosmeticblocksreborn.gui.component;

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.render.Anim;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class BooleanComponent extends SettingComponent<BooleanSetting> {
   @NotNull
   private final Anim knobAnim;

   public BooleanComponent(@NotNull ClickGuiScreen gui, @NotNull BooleanSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.knobAnim = new Anim(setting.getValue() ? 1.0F : 0.0F, 16.0F);
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      this.knobAnim.setTarget(this.getSetting().getValue() ? 1.0F : 0.0F);
      float t = this.knobAnim.update(delta);
      float pillWidth = 28.0F;
      float pillHeight = 15.0F;
      float pillX = this.getPadRight() - pillWidth;
      float pillY = this.getY() + (26.0F - pillHeight) / 2.0F;
      int track = this.blend(-14013910, -1750963, t);
      NVGRenderer.INSTANCE.rect(pillX, pillY, pillWidth, pillHeight, track, pillHeight / 2.0F);
      float knobRadius = pillHeight / 2.0F - 2.5F;
      float knobX = pillX + knobRadius + 2.5F + (pillWidth - 2.0F * (knobRadius + 2.5F)) * t;
      NVGRenderer.INSTANCE.circle(knobX, pillY + pillHeight / 2.0F, knobRadius, -1184275);
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (button == 0 && this.rowHovered(mouseX, mouseY)) {
         this.getSetting().toggle();
         return true;
      } else {
         return false;
      }
   }

   private final int blend(int from, int to, float t) {
      float f = RangesKt.coerceIn(t, 0.0F, 1.0F);
      return blend$channel(from, to, f, 24) << 24 | blend$channel(from, to, f, 16) << 16 | blend$channel(from, to, f, 8) << 8 | blend$channel(from, to, f, 0);
   }

   private static final int blend$channel(int $from, int $to, float f, int shift) {
      int a = $from >> shift & 0xFF;
      int b = $to >> shift & 0xFF;
      return RangesKt.coerceIn((int)(a + (b - a) * f), 0, 255);
   }
}
