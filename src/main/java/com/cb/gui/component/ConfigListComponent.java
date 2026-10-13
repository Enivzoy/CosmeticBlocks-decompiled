package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ConfigListSetting;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ConfigListComponent extends SettingComponent<ConfigListSetting> {
   private boolean open;
   @NotNull
   private final Anim expandAnim;
   @NotNull
   private List<String> options;
   private final float optionH;

   public ConfigListComponent(@NotNull ClickGuiScreen gui, @NotNull ConfigListSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.expandAnim = new Anim(0.0F, 16.0F);
      this.options = CollectionsKt.emptyList();
      this.optionH = 18.0F;
   }

   private final float getRowH() {
      return 26.0F;
   }

   @Override
   public float getHeight() {
      return this.getRowH() + this.expandAnim.getValue() * (this.options.size() * this.optionH + 4.0F);
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      String display = this.getSetting().getValue();
      String shown = display.length() == 0 ? "—" : display;
      NVGRenderer.INSTANCE.textRight(shown, this.getPadRight(), this.getY() + this.getRowH() / 2.0F, 12.0F, -6513508, null);
      this.expandAnim.setTarget(this.open ? 1.0F : 0.0F);
      this.expandAnim.update(delta);
      if (this.expandAnim.getValue() > 0.01F && !this.options.isEmpty()) {
         float expandedHeight = this.expandAnim.getValue() * (this.options.size() * this.optionH + 4.0F);
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.intersectScissor(this.getX(), this.getY() + this.getRowH(), this.getWidth(), expandedHeight);

         try {
            float oy = this.getY() + this.getRowH() + 2.0F;

            for (String opt : this.options) {
               boolean hovered = mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH;
               if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.getX() + 6.0F, oy, this.getWidth() - 12.0F, this.optionH, 318767103, 4.0F);
               }

               int color = Intrinsics.areEqual(opt, this.getSetting().getValue()) ? -1750963 : -6513508;
               NVGRenderer.INSTANCE.text(opt, this.getX() + 18.0F, oy + this.optionH / 2.0F, 12.0F, color, null);
               oy += this.optionH;
            }
         } finally {
            NVGRenderer.INSTANCE.restore();
         }
      }
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (this.rowHovered(mouseX, mouseY)) {
         this.open = !this.open;
         if (this.open) {
            this.options = this.getSetting().options();
         }

         return true;
      } else {
         if (this.open) {
            float oy = this.getY() + this.getRowH() + 2.0F;

            for (String opt : this.options) {
               if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH) {
                  this.getSetting().setValue(opt);
                  this.open = false;
                  return true;
               }

               oy += this.optionH;
            }
         }

         return false;
      }
   }
}
