package me.enivzoy.cosmeticblocksreborn.gui.component;

import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.render.Anim;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.render.Theme;
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting;
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting;
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting;
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting;
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting;
import me.enivzoy.cosmeticblocksreborn.setting.LabelSetting;
import me.enivzoy.cosmeticblocksreborn.setting.ModeSetting;
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting;
import me.enivzoy.cosmeticblocksreborn.setting.Setting;
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SettingComponent<S extends Setting<?>> {
   @NotNull
   public static final SettingComponent.Companion Companion = new SettingComponent.Companion();
   @NotNull
   private final ClickGuiScreen gui;
   @NotNull
   private final S setting;
   private float x;
   private float y;
   private float width;
   @NotNull
   private final Anim hoverAnim;

   public SettingComponent(@NotNull ClickGuiScreen gui, @NotNull S setting) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(setting, "setting");
      super();
      this.gui = gui;
      this.setting = (S)setting;
      this.hoverAnim = new Anim(0.0F, 18.0F);
   }

   @NotNull
   protected final ClickGuiScreen getGui() {
      return this.gui;
   }

   @NotNull
   public final S getSetting() {
      return this.setting;
   }

   public final float getX() {
      return this.x;
   }

   public final void setX(float x) {
      this.x = x;
   }

   public final float getY() {
      return this.y;
   }

   public final void setY(float y) {
      this.y = y;
   }

   public final float getWidth() {
      return this.width;
   }

   public final void setWidth(float width) {
      this.width = width;
   }

   @NotNull
   protected final Anim getHoverAnim() {
      return this.hoverAnim;
   }

   public float getHeight() {
      return 26.0F;
   }

   protected final float getPadLeft() {
      return this.x + 16.0F;
   }

   protected final float getPadRight() {
      return this.x + this.width - 12.0F;
   }

   protected final boolean rowHovered(float mouseX, float mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + 26.0F;
   }

   public final boolean contains(float mouseX, float mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.getHeight();
   }

   public abstract void render(float mouseX, float mouseY, float delta);

   public boolean mouseClicked(float mouseX, float mouseY, int button) {
      return false;
   }

   public void mouseReleased(int button) {
   }

   public void mouseDragged(float mouseX, float mouseY) {
   }

   public boolean keyPressed(int key, int modifiers) {
      return false;
   }

   public boolean charTyped(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      return false;
   }

   protected final void renderRowBase(float mouseX, float mouseY, float delta) {
      boolean hovered = this.rowHovered(mouseX, mouseY);
      this.hoverAnim.setTarget(hovered ? 1.0F : 0.0F);
      this.hoverAnim.update(delta);
      if (this.hoverAnim.getValue() > 0.01F) {
         NVGRenderer.INSTANCE
            .rect(this.x + 4.0F, this.y + 1.0F, this.width - 8.0F, 24.0F, Theme.INSTANCE.withAlpha(318767103, this.hoverAnim.getValue()), 5.0F);
      }

      NVGRenderer.INSTANCE.text(this.setting.getName(), this.getPadLeft(), this.y + 13.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
      if (hovered && this.setting.getDescription().length() > 0) {
         this.gui.requestTooltip(this.setting.getDescription());
      }
   }

   public static final class Companion {
      private Companion() {
      }

      @Nullable
      public final SettingComponent<?> create(@NotNull ClickGuiScreen gui, @NotNull Setting<?> setting) {
         Intrinsics.checkNotNullParameter(gui, "gui");
         Intrinsics.checkNotNullParameter(setting, "setting");
         return setting instanceof BooleanSetting
            ? new BooleanComponent(gui, (BooleanSetting)setting)
            : (
               setting instanceof NumberSetting
                  ? new SliderComponent(gui, (NumberSetting)setting)
                  : (
                     setting instanceof ModeSetting
                        ? new ModeComponent(gui, (ModeSetting<?>)setting)
                        : (
                           setting instanceof ColorSetting
                              ? new ColorComponent(gui, (ColorSetting)setting)
                              : (
                                 setting instanceof KeybindSetting
                                    ? new KeybindComponent(gui, (KeybindSetting)setting)
                                    : (
                                       setting instanceof StringSetting
                                          ? new StringComponent(gui, (StringSetting)setting)
                                          : (
                                             setting instanceof ConfigListSetting
                                                ? new ConfigListComponent(gui, (ConfigListSetting)setting)
                                                : (
                                                   setting instanceof LabelSetting
                                                      ? new TextComponent(gui, (LabelSetting)setting)
                                                      : (setting instanceof ButtonSetting ? new ButtonComponent(gui, (ButtonSetting)setting) : null)
                                                )
                                          )
                                    )
                              )
                        )
                  )
            );
      }
   }
}
