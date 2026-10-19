package me.enivzoy.cosmeticblocksreborn.gui.component;

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.render.Anim;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.setting.ModeSetting;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ModeComponent extends SettingComponent<ModeSetting<?>> {
   @NotNull
   private static final ModeComponent.Companion Companion = new ModeComponent.Companion();
   private boolean open;
   @NotNull
   private final Anim openAnim;
   @Deprecated
   public static final float OPTION_HEIGHT = 22.0F;

   public ModeComponent(@NotNull ClickGuiScreen gui, @NotNull ModeSetting<?> setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.openAnim = new Anim(0.0F, 16.0F);
   }

   private final float getExpandedExtra() {
      return this.getSetting().getOptions().size() * 22.0F + 4.0F;
   }

   @Override
   public float getHeight() {
      return 26.0F + this.openAnim.getValue() * this.getExpandedExtra();
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      this.openAnim.setTarget(this.open ? 1.0F : 0.0F);
      this.openAnim.update(delta);
      float midY = this.getY() + 13.0F;
      NVGRenderer.INSTANCE.textRight(this.getSetting().currentLabel(), this.getPadRight() - 14.0F, midY, 12.0F, -6513508, NVGRenderer.Font.MEDIUM);
      this.chevron(this.getPadRight() - 5.0F, midY, this.openAnim.getValue());
      if (!(this.openAnim.getValue() <= 0.01F)) {
         float listTop = this.getY() + 26.0F;
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.intersectScissor(this.getX(), listTop, this.getWidth(), this.openAnim.getValue() * this.getExpandedExtra());

         try {
            int index = 0;

            for (Enum option : this.getSetting().getOptions()) {
               int currentIndex = index++;
               float rowY = listTop + currentIndex * 22.0F;
               boolean selected = Intrinsics.areEqual(option, this.getSetting().getValue());
               boolean hovered = mouseX >= this.getPadLeft() && mouseX <= this.getPadRight() && mouseY >= rowY && mouseY < rowY + 22.0F;
               if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.getX() + 10.0F, rowY + 1.0F, this.getWidth() - 20.0F, 20.0F, 318767103, 4.0F);
               }

               if (selected) {
                  NVGRenderer.INSTANCE.rect(this.getPadLeft(), rowY + 6.0F, 2.5F, 10.0F, -1750963, 1.25F);
               }

               NVGRenderer.INSTANCE
                  .text(
                     this.getSetting().label(option), this.getPadLeft() + 10.0F, rowY + 11.0F, 12.0F, selected ? -1184275 : -6513508, NVGRenderer.Font.REGULAR
                  );
            }
         } finally {
            NVGRenderer.INSTANCE.restore();
         }
      }
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
         int index = (int)((mouseY - (this.getY() + 26.0F)) / 22.0F);
         if (0 <= index ? index < this.getSetting().getOptions().size() : false) {
            this.getSetting().select((Enum<?>)this.getSetting().getOptions().get(index));
            this.open = false;
         }

         return true;
      } else {
         return false;
      }
   }

   private final void chevron(float cx, float cy, float t) {
      float dir = 1.0F - 2.0F * t;
      float w = 3.5F;
      float h = 2.0F * dir;
      NVGRenderer.INSTANCE.line(cx - w, cy - h / 2.0F, cx, cy + h / 2.0F, -6513508, 1.5F);
      NVGRenderer.INSTANCE.line(cx, cy + h / 2.0F, cx + w, cy - h / 2.0F, -6513508, 1.5F);
   }

   private static final class Companion {
      private Companion() {
      }
   }
}
