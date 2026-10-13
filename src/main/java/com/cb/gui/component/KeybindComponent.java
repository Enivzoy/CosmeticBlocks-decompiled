package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.NVGRenderer;
import com.cb.setting.KeybindSetting;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class KeybindComponent extends SettingComponent<KeybindSetting> {
   private boolean listening;

   public KeybindComponent(@NotNull ClickGuiScreen gui, @NotNull KeybindSetting setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super(gui, setting);
   }

   public final boolean getListening() {
      return this.listening;
   }

   @Override
   public void render(float mouseX, float mouseY, float delta) {
      this.renderRowBase(mouseX, mouseY, delta);
      String label = this.listening ? "…" : this.getSetting().displayName();
      float chipWidth = NVGRenderer.INSTANCE.textWidth(label, 12.0F, NVGRenderer.Font.MEDIUM) + 14.0F;
      float chipHeight = 16.0F;
      float chipX = this.getPadRight() - chipWidth;
      float chipY = this.getY() + (26.0F - chipHeight) / 2.0F;
      NVGRenderer.INSTANCE.rect(chipX, chipY, chipWidth, chipHeight, -14013910, 4.0F);
      if (this.listening) {
         NVGRenderer.INSTANCE.rectOutline(chipX, chipY, chipWidth, chipHeight, -1750963, 4.0F, 0.0F);
      }

      NVGRenderer.INSTANCE
         .textCentered(
            label,
            chipX + chipWidth / 2.0F,
            chipY + chipHeight / 2.0F,
            12.0F,
            this.listening ? -1750963 : (this.getSetting().getBound() ? -1184275 : -10724260),
            NVGRenderer.Font.MEDIUM
         );
   }

   @Override
   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (this.listening) {
         this.listening = false;
         return this.rowHovered(mouseX, mouseY);
      } else if (button == 0 && this.rowHovered(mouseX, mouseY)) {
         this.listening = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyPressed(int key, int modifiers) {
      if (!this.listening) {
         return false;
      }

      switch (key) {
         case 256:
         case 261:
            this.getSetting().clear();
            break;
         default:
            this.getSetting().setValue(key);
      }

      this.listening = false;
      return true;
   }
}
