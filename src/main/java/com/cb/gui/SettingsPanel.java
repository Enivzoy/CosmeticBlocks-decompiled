package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.Setting;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class SettingsPanel {
   @NotNull
   private static final SettingsPanel.Companion Companion = new SettingsPanel.Companion();
   @NotNull
   private final ClickGuiScreen gui;
   @NotNull
   private final SettingsTab tab;
   private float x;
   private float y;
   private boolean collapsed;
   @NotNull
   private final List<SettingComponent<?>> components;
   @NotNull
   private final Anim collapseAnim;
   private float scroll;
   @NotNull
   private final Anim scrollAnim;
   private boolean dragging;
   private float dragOffsetX;
   private float dragOffsetY;
   private float tiltAngle;
   private float tiltVelocity;
   private float smoothedVx;
   private float prevX;
   private float shownContent;
   @Deprecated
   public static final float TILT_PER_SPEED = 9.0E-5F;
   @Deprecated
   public static final float TILT_MAX = 0.14F;
   @Deprecated
   public static final float TILT_STIFFNESS = 170.0F;
   @Deprecated
   public static final float TILT_DAMPING = 10.5F;
   @Deprecated
   public static final float VELOCITY_SMOOTHING = 14.0F;
   @Deprecated
   public static final float SUBHEADER_HEIGHT = 18.0F;

   public SettingsPanel(@NotNull ClickGuiScreen gui, @NotNull SettingsTab tab, float defaultX, float defaultY) {
      Intrinsics.checkNotNullParameter(gui, "gui");
      Intrinsics.checkNotNullParameter(tab, "tab");
      super();
      this.gui = gui;
      this.tab = tab;
      Iterable saved = this.tab.getSettings();
      List<SettingComponent<?>> destination = new ArrayList<>();

      for (Object element : saved) {
         Setting setting = (Setting)element;
         SettingComponent component = SettingComponent.Companion.create(this.gui, setting);
         if (component != null) {
            destination.add(component);
         }
      }

      this.components = destination;
      Config.PanelState savedx = Config.INSTANCE.getPanelState().get(this.tab.getName());
      this.x = savedx != null ? savedx.getX() : defaultX;
      this.y = savedx != null ? savedx.getY() : defaultY;
      this.collapsed = savedx != null ? savedx.getCollapsed() : false;
      this.collapseAnim = new Anim(this.collapsed ? 0.0F : 1.0F, 14.0F);
      this.scrollAnim = new Anim(0.0F, 20.0F);
   }

   @NotNull
   public final SettingsTab getTab() {
      return this.tab;
   }

   public final float getX() {
      return this.x;
   }

   public final void setX(float value) {
      this.x = value;
   }

   public final float getY() {
      return this.y;
   }

   public final void setY(float value) {
      this.y = value;
   }

   public final float getWidth() {
      return Intrinsics.areEqual(this.tab.getName(), "Cosmetic Blocks") ? 280.0F : 240.0F;
   }

   private final float contentHeight() {
      float h = 6.0F;
      String lastSub = "";
      for (SettingComponent c : this.components) {
         if (c.getSetting().getVisible()) {
            String sub = c.getSetting().getSubcategory();
            if (sub.length() > 0 && !Intrinsics.areEqual(sub, lastSub)) {
               h += 18.0F;
               lastSub = sub;
            }

            h += c.getHeight();
         }
      }

      return h;
   }

   private final float maxContentHeight() {
      return RangesKt.coerceAtLeast(this.gui.getUiHeight() - this.y - 36.0F - 16.0F, 60.0F);
   }

   public final float totalHeight() {
      return 36.0F + this.shownContent;
   }

   public final boolean contains(float mouseX, float mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.getWidth() && mouseY >= this.y && mouseY <= this.y + this.totalHeight();
   }

   private final boolean headerHovered(float mouseX, float mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.getWidth() && mouseY >= this.y && mouseY <= this.y + 36.0F;
   }

   public final void render(float mouseX, float mouseY, float delta) {
      if (this.dragging) {
         this.x = mouseX - this.dragOffsetX;
         this.y = mouseY - this.dragOffsetY;
      }

      this.x = RangesKt.coerceIn(this.x, 4.0F - this.getWidth() + 40.0F, this.gui.getUiWidth() - 40.0F);
      this.y = RangesKt.coerceIn(this.y, 4.0F, this.gui.getUiHeight() - 36.0F - 4.0F);
      this.updateTilt(delta);
      this.collapseAnim.setTarget(this.collapsed ? 0.0F : 1.0F);
      this.collapseAnim.update(delta);
      float rawContent = RangesKt.coerceAtMost(this.contentHeight(), this.maxContentHeight());
      this.shownContent = rawContent * this.collapseAnim.getValue();
      float maxScroll = RangesKt.coerceAtLeast(this.contentHeight() - this.maxContentHeight(), 0.0F);
      this.scroll = RangesKt.coerceIn(this.scroll, 0.0F, maxScroll);
      this.scrollAnim.setTarget(this.scroll);
      this.scrollAnim.update(delta);
      float panelHeight = this.totalHeight();
      boolean tilting = this.tiltAngle != 0.0F || this.tiltVelocity != 0.0F;
      if (tilting) {
         float pivotX = this.x + this.dragOffsetX;
         float pivotY = this.y + this.dragOffsetY;
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.translate(pivotX, pivotY);
         NVGRenderer.INSTANCE.rotate(this.tiltAngle);
         NVGRenderer.INSTANCE.translate(-pivotX, -pivotY);
      }

      NVGRenderer.INSTANCE.shadow(this.x, this.y, this.getWidth(), panelHeight, 10.0F, 14.0F, 1493172224);
      NVGRenderer.INSTANCE.rect(this.x, this.y, this.getWidth(), panelHeight, -183233516, 10.0F);
      this.renderHeader(mouseX, mouseY);
      if (this.shownContent > 0.5F) {
         float clipTop = this.y + 36.0F;
         float clipX = this.x;
         float clipY = clipTop;
         float clipW = this.getWidth();
         float clipH = this.shownContent;
         NVGRenderer.INSTANCE.save();
         NVGRenderer.INSTANCE.intersectScissor(clipX, clipY, clipW, clipH);

         try {
            float offset = clipTop + 3.0F - this.scrollAnim.getValue();
            String lastSub = "";
            for (SettingComponent component : this.components) {
               if (component.getSetting().getVisible()) {
                  String sub = component.getSetting().getSubcategory();
                  if (sub.length() > 0 && !Intrinsics.areEqual(sub, lastSub)) {
                     lastSub = sub;
                     if (offset + 18.0F >= clipTop && offset <= clipTop + this.shownContent) {
                        String upper = sub.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(upper, "toUpperCase(...)");
                        NVGRenderer.INSTANCE.text(upper, this.x + 14.0F, offset + 9.0F, 11.0F, -10724260, NVGRenderer.Font.MEDIUM);
                     }

                     offset += 18.0F;
                  }

                  component.setX(this.x);
                  component.setY(offset);
                  component.setWidth(this.getWidth());
                  if (offset + component.getHeight() >= clipTop && offset <= clipTop + this.shownContent) {
                     component.render(mouseX, mouseY, delta);
                  }

                  offset += component.getHeight();
               }
            }
         } finally {
            NVGRenderer.INSTANCE.restore();
         }
      }

      NVGRenderer.INSTANCE.rectOutline(this.x, this.y, this.getWidth(), panelHeight, 352321535, 10.0F, 1.0F);
      if (tilting) {
         NVGRenderer.INSTANCE.restore();
      }
   }

   private final void updateTilt(float delta) {
      if (!(delta <= 0.0F) && !(delta > 0.1F)) {
         float vx = (this.x - this.prevX) / delta;
         this.prevX = this.x;
         this.smoothedVx = this.smoothedVx + (vx - this.smoothedVx) * RangesKt.coerceAtMost(delta * 14.0F, 1.0F);
         float target = this.dragging ? RangesKt.coerceIn(-this.smoothedVx * 9.0E-5F, -0.14F, 0.14F) : 0.0F;
         this.tiltVelocity = this.tiltVelocity + (target - this.tiltAngle) * 170.0F * delta;
         this.tiltVelocity = this.tiltVelocity * (float)Math.exp(-10.5F * delta);
         this.tiltAngle = this.tiltAngle + this.tiltVelocity * delta;
         if (!this.dragging && Math.abs(this.tiltAngle) < 4.0E-4F && Math.abs(this.tiltVelocity) < 4.0E-4F) {
            this.tiltAngle = 0.0F;
            this.tiltVelocity = 0.0F;
         }
      } else {
         this.prevX = this.x;
      }
   }

   private final void renderHeader(float mouseX, float mouseY) {
      float radius = 10.0F;
      float bottomRadius = this.shownContent > 0.5F ? 0.0F : radius;
      NVGRenderer.INSTANCE.rectVarying(this.x, this.y, this.getWidth(), 36.0F, radius, radius, bottomRadius, bottomRadius, -14935012);
      if (this.shownContent > 0.5F) {
         NVGRenderer.INSTANCE.line(this.x, this.y + 36.0F, this.x + this.getWidth(), this.y + 36.0F, 352321535, 1.0F);
      }

      NVGRenderer.INSTANCE.text(this.tab.getName(), this.x + 14.0F, this.y + 18.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      int dotColor = this.collapsed ? -10724260 : -1750963;
      NVGRenderer.INSTANCE.circle(this.x + this.getWidth() - 16.0F, this.y + 18.0F, 3.0F, dotColor);
   }

   public final boolean mouseClicked(float mouseX, float mouseY, int button) {
      if (this.headerHovered(mouseX, mouseY)) {
         switch (button) {
            case 0:
               this.dragging = true;
               this.dragOffsetX = mouseX - this.x;
               this.dragOffsetY = mouseY - this.y;
               this.smoothedVx = 0.0F;
               this.prevX = this.x;
               break;
            case 1:
               this.collapsed = !this.collapsed;
         }

         return true;
      } else {
         if (!this.contains(mouseX, mouseY)) {
            return false;
         }

         for (SettingComponent component : this.components) {
            if (component.getSetting().getVisible() && component.mouseClicked(mouseX, mouseY, button)) {
               return true;
            }
         }

         return true;
      }
   }

   public final void mouseReleased(int button) {
      if (button == 0) {
         this.dragging = false;
      }

      for (SettingComponent component : this.components) {
         component.mouseReleased(button);
      }
   }

   public final void mouseDragged(float mouseX, float mouseY) {
      for (SettingComponent component : this.components) {
         component.mouseDragged(mouseX, mouseY);
      }
   }

   public final boolean mouseScrolled(float mouseX, float mouseY, float amount) {
      if (this.contains(mouseX, mouseY) && !this.collapsed) {
         this.scroll -= amount * 24.0F;
         return true;
      } else {
         return false;
      }
   }

   public final boolean keyPressed(int key, int modifiers) {
      for (SettingComponent component : this.components) {
         if (component.keyPressed(key, modifiers)) {
            return true;
         }
      }

      return false;
   }

   public final boolean charTyped(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      for (SettingComponent component : this.components) {
         if (component.charTyped(text)) {
            return true;
         }
      }

      return false;
   }

   public final void persistState() {
      Config.INSTANCE.getPanelState().put(this.tab.getName(), new Config.PanelState(this.x, this.y, this.collapsed));
   }

   private static final class Companion {
      private Companion() {
      }
   }
}
