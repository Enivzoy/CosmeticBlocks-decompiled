package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ConfigListSetting;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ConfigListComponent extends SettingComponent<ConfigListSetting> {
   private boolean open;
   @NotNull
   private final Anim expandAnim;
   @NotNull
   private List<String> options;
   @NotNull
   private List<String> labels;
   @NotNull
   private List<Integer> colors;
   private final float optionH;

   public ConfigListComponent(@NotNull ClickGuiScreen gui, @NotNull ConfigListSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
      this.expandAnim = new Anim(0.0F, 16.0F);
      this.options = CollectionsKt.emptyList();
      this.labels = CollectionsKt.emptyList();
      this.colors = CollectionsKt.emptyList();
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
      String display = this.headerLabel();
      String shown = display.length() == 0 ? "—" : display;
      NVGRenderer.INSTANCE.textRight(shown, this.getPadRight(), this.getY() + this.getRowH() / 2.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
      this.expandAnim.setTarget(this.open ? 1.0F : 0.0F);
      this.expandAnim.update(delta);
      if (this.expandAnim.getValue() > 0.01F && !this.options.isEmpty()) {
         float expandedHeight = this.expandAnim.getValue() * (this.options.size() * this.optionH + 4.0F);
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.intersectScissor(this.getX(), this.getY() + this.getRowH(), this.getWidth(), expandedHeight);

         try {
            float oy = this.getY() + this.getRowH() + 2.0F;

            for (int i = 0; i < this.options.size(); i++) {
               String opt = this.options.get(i);
               String label = i < this.labels.size() ? this.labels.get(i) : opt;
               boolean hovered = mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH;
               int background = i < this.colors.size() ? this.colors.get(i) : 0;
               if ((background >>> 24) != 0) {
                  NVGRenderer.INSTANCE.rect(this.getX() + 6.0F, oy, this.getWidth() - 12.0F, this.optionH, background, 4.0F);
               }

               if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.getX() + 6.0F, oy, this.getWidth() - 12.0F, this.optionH, 318767103, 4.0F);
               }

               int color = Intrinsics.areEqual(opt, this.getSetting().getValue()) ? -1750963 : -6513508;
               if ((background >>> 24) != 0 && isBright(background)) {
                  color = -16777216;
               }

               NVGRenderer.INSTANCE.text(label, this.getX() + 18.0F, oy + this.optionH / 2.0F, 12.0F, color, NVGRenderer.Font.REGULAR);
               oy += this.optionH;
            }
         } finally {
            NVGRenderer.INSTANCE.restore();
         }
      }
   }

   private static boolean isBright(int argb) {
      int r = argb >> 16 & 255;
      int g = argb >> 8 & 255;
      int b = argb & 255;
      return r * 299 + g * 587 + b * 114 > 70000;
   }

   @NotNull
   private final String headerLabel() {
      String raw = this.getSetting().getValue();
      Function1<String, String> provider = this.getSetting().getLabelProvider();
      if (provider != null) {
         String label = provider.invoke(raw);
         if (label != null) {
            return label;
         }
      }

      return raw;
   }

   private final void buildOptionLists() {
      this.options = this.getSetting().options();
      Function1<String, String> labelProvider = this.getSetting().getLabelProvider();
      Function1<String, Integer> colorProvider = this.getSetting().getColorProvider();
      List<String> newLabels = new ArrayList<>();
      List<Integer> newColors = new ArrayList<>();

      for (String opt : this.options) {
         String label = opt;
         if (labelProvider != null) {
            String provided = labelProvider.invoke(opt);
            if (provided != null) {
               label = provided;
            }
         }

         newLabels.add(label);
         Integer color = colorProvider != null ? colorProvider.invoke(opt) : null;
         newColors.add(color != null ? color : 0);
      }

      this.labels = newLabels;
      this.colors = newColors;
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (this.rowHovered(mouseX, mouseY)) {
         this.open = !this.open;
         if (this.open) {
            this.buildOptionLists();
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
