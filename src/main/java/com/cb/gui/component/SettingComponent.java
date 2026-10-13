package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.ColorSetting;
import com.cb.setting.ConfigListSetting;
import com.cb.setting.KeybindSetting;
import com.cb.setting.LabelSetting;
import com.cb.setting.ModeSetting;
import com.cb.setting.NumberSetting;
import com.cb.setting.Setting;
import com.cb.setting.StringSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u0000 8*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003:\u00018B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0004J\u0016\u0010(\u001a\u00020%2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fJ \u0010)\u001a\u00020*2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u000fH&J \u0010,\u001a\u00020%2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010-\u001a\u00020.H\u0016J\u0010\u0010/\u001a\u00020*2\u0006\u0010-\u001a\u00020.H\u0016J\u0018\u00100\u001a\u00020*2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000fH\u0016J\u0018\u00101\u001a\u00020%2\u0006\u00102\u001a\u00020.2\u0006\u00103\u001a\u00020.H\u0016J\u0010\u00104\u001a\u00020%2\u0006\u00105\u001a\u000206H\u0016J \u00107\u001a\u00020*2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\u000f2\u0006\u0010+\u001a\u00020\u000fH\u0004R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R\u001a\u0010\u0017\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u0014\u0010\u001a\u001a\u00020\u001bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0011R\u0014\u0010 \u001a\u00020\u000f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u0011R\u0014\u0010\"\u001a\u00020\u000f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u0011¨\u00069",
   d2 = {
         "Lcom/cb/gui/component/SettingComponent;",
         "S",
         "Lcom/cb/setting/Setting;",
         "",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/Setting;)V",
         "getGui",
         "()Lcom/cb/gui/ClickGuiScreen;",
         "getSetting",
         "()Lcom/cb/setting/Setting;",
         "Lcom/cb/setting/Setting;",
         "x",
         "",
         "getX",
         "()F",
         "setX",
         "(F)V",
         "y",
         "getY",
         "setY",
         "width",
         "getWidth",
         "setWidth",
         "hoverAnim",
         "Lcom/cb/render/Anim;",
         "getHoverAnim",
         "()Lcom/cb/render/Anim;",
         "height",
         "getHeight",
         "padLeft",
         "getPadLeft",
         "padRight",
         "getPadRight",
         "rowHovered",
         "",
         "mouseX",
         "mouseY",
         "contains",
         "render",
         "",
         "delta",
         "mouseClicked",
         "button",
         "",
         "mouseReleased",
         "mouseDragged",
         "keyPressed",
         "key",
         "modifiers",
         "charTyped",
         "text",
         "",
         "renderRowBase",
         "Companion",
         "cb_client"
   }
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J \u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¨\u0006\n",
      d2 = {
            "Lcom/cb/gui/component/SettingComponent$Companion;",
            "",
            "<init>",
            "()V",
            "create",
            "Lcom/cb/gui/component/SettingComponent;",
            "gui",
            "Lcom/cb/gui/ClickGuiScreen;",
            "setting",
            "Lcom/cb/setting/Setting;",
            "cb_client"
      }
   )
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
