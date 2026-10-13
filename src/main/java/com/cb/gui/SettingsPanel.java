package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.Setting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 B2\u00020\u0001:\u0001BB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\nJ\b\u0010(\u001a\u00020\u0007H\u0002J\b\u0010)\u001a\u00020\u0007H\u0002J\u0006\u0010*\u001a\u00020\u0007J\u0016\u0010+\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007J\u0018\u0010.\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002J\u001e\u0010/\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u0007J\u0010\u00102\u001a\u0002002\u0006\u00101\u001a\u00020\u0007H\u0002J\u0018\u00103\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002J\u001e\u00104\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u00105\u001a\u000206J\u000e\u00107\u001a\u0002002\u0006\u00105\u001a\u000206J\u0016\u00108\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007J\u001e\u00109\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u0007J\u0016\u0010;\u001a\u00020\u00162\u0006\u0010<\u001a\u0002062\u0006\u0010=\u001a\u000206J\u000e\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020@J\u0006\u0010A\u001a\u000200R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b'\u0010\u000f¨\u0006C",
   d2 = {
         "Lcom/cb/gui/SettingsPanel;",
         "",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "tab",
         "Lcom/cb/config/SettingsTab;",
         "defaultX",
         "",
         "defaultY",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/config/SettingsTab;FF)V",
         "getTab",
         "()Lcom/cb/config/SettingsTab;",
         "x",
         "getX",
         "()F",
         "setX",
         "(F)V",
         "y",
         "getY",
         "setY",
         "collapsed",
         "",
         "components",
         "",
         "Lcom/cb/gui/component/SettingComponent;",
         "collapseAnim",
         "Lcom/cb/render/Anim;",
         "scroll",
         "scrollAnim",
         "dragging",
         "dragOffsetX",
         "dragOffsetY",
         "tiltAngle",
         "tiltVelocity",
         "smoothedVx",
         "prevX",
         "shownContent",
         "width",
         "getWidth",
         "contentHeight",
         "maxContentHeight",
         "totalHeight",
         "contains",
         "mouseX",
         "mouseY",
         "headerHovered",
         "render",
         "",
         "delta",
         "updateTilt",
         "renderHeader",
         "mouseClicked",
         "button",
         "",
         "mouseReleased",
         "mouseDragged",
         "mouseScrolled",
         "amount",
         "keyPressed",
         "key",
         "modifiers",
         "charTyped",
         "text",
         "",
         "persistState",
         "Companion",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nSettingsPanel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SettingsPanel.kt\ncom/cb/gui/SettingsPanel\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n*L\n1#1,251:1\n1642#2,10:252\n1915#2:262\n1916#2:264\n1652#2:265\n1915#2,2:266\n1915#2,2:272\n1915#2,2:277\n1915#2,2:279\n1915#2,2:281\n1807#2,3:283\n1807#2,3:286\n1#3:263\n445#4,4:268\n450#4,3:274\n*S KotlinDebug\n*F\n+ 1 SettingsPanel.kt\ncom/cb/gui/SettingsPanel\n*L\n28#1:252,10\n28#1:262\n28#1:264\n28#1:265\n68#1:266,2\n136#1:272,2\n222#1:277,2\n228#1:279,2\n232#1:281,2\n242#1:283,3\n245#1:286,3\n28#1:263\n133#1:268,4\n133#1:274,3\n*E\n"
)
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
      SettingsPanel var20 = this;
      int $i$f$mapNotNull = 0;
      Iterable $this$mapNotNullTo$iv$iv = saved;
      var destination$iv$iv = new ArrayList();
      int $i$f$mapNotNullTo = 0;
      Iterable $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
      int $i$f$forEach = 0;

      for (Object element$iv$iv$iv : $this$forEach$iv$iv$iv) {
         Object element$iv$iv = element$iv$iv$iv;
         int var15/* $VF was: $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv */ = 0;
         Setting it = (Setting)element$iv$iv;
         int var17/* $VF was: $i$a$-mapNotNull-SettingsPanel$components$1 */ = 0;
         SettingComponent var10000 = SettingComponent.Companion.create(this.gui, it);
         if (var10000 != null) {
            Object it$iv$iv = var10000;
            int var19/* $VF was: $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv */ = 0;
            destination$iv$iv.add(it$iv$iv);
         }
      }

      this.components = (List<SettingComponent<?>>)destination$iv$iv;
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

   public final void setX(float var1) {
      this.x = var1/* $VF was: <set-?> */;
   }

   public final float getY() {
      return this.y;
   }

   public final void setY(float var1) {
      this.y = var1/* $VF was: <set-?> */;
   }

   private final float getWidth() {
      return 240.0F;
   }

   private final float contentHeight() {
      float h = 0.0F;
      h = 6.0F;
      Object lastSub = null;
      lastSub = "";
      Iterable $this$forEach$iv = this.components;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingComponent c = (SettingComponent)element$iv;
         int var8/* $VF was: $i$a$-forEach-SettingsPanel$contentHeight$1 */ = 0;
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
         NVGRenderer var29 = NVGRenderer.INSTANCE;
         float x$iv = this.x;
         float y$iv = clipTop;
         float w$iv = this.getWidth();
         float h$iv = this.shownContent;
         int $i$f$scissor = 0;
         var29.save();
         var29.intersectScissor(x$iv, y$iv, w$iv, h$iv);

         try {
            int var15/* $VF was: $i$a$-scissor-SettingsPanel$render$1 */ = 0;
            float offset = 0.0F;
            offset = clipTop + 3.0F - this.scrollAnim.getValue();
            Object lastSub = null;
            lastSub = "";
            Iterable $this$forEach$iv = this.components;
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               SettingComponent component = (SettingComponent)element$iv;
               int var23/* $VF was: $i$a$-forEach-SettingsPanel$render$1$1 */ = 0;
               if (component.getSetting().getVisible()) {
                  String sub = component.getSetting().getSubcategory();
                  if (sub.length() > 0 && !Intrinsics.areEqual(sub, lastSub)) {
                     lastSub = sub;
                     if (offset + 18.0F >= clipTop && offset <= clipTop + this.shownContent) {
                        NVGRenderer var10000 = NVGRenderer.INSTANCE;
                        String var10001 = sub.toUpperCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue(var10001, "toUpperCase(...)");
                        var10000.text(var10001, this.x + 14.0F, offset + 9.0F, 11.0F, -10724260, NVGRenderer.Font.MEDIUM);
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
            var29.restore();
         }
      }

      NVGRenderer.INSTANCE.rectOutline(this.x, this.y, this.getWidth(), panelHeight, 352321535, 10.0F, 0.0F);
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
         NVGRenderer.INSTANCE.line(this.x, this.y + 36.0F, this.x + this.getWidth(), this.y + 36.0F, 352321535, 0.0F);
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

         Iterable $this$forEach$iv = this.components;
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$iv) {
            SettingComponent it = (SettingComponent)element$iv;
            int var9/* $VF was: $i$a$-forEach-SettingsPanel$mouseClicked$1 */ = 0;
            if (it.getSetting().getVisible() && it.mouseClicked(mouseX, mouseY, button)) {
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

      Iterable $this$forEach$iv = this.components;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingComponent it = (SettingComponent)element$iv;
         int var7/* $VF was: $i$a$-forEach-SettingsPanel$mouseReleased$1 */ = 0;
         it.mouseReleased(button);
      }
   }

   public final void mouseDragged(float mouseX, float mouseY) {
      Iterable $this$forEach$iv = this.components;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingComponent it = (SettingComponent)element$iv;
         int var8/* $VF was: $i$a$-forEach-SettingsPanel$mouseDragged$1 */ = 0;
         it.mouseDragged(mouseX, mouseY);
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
      var $this$any$iv = this.components;
      int $i$f$any = 0;
      boolean var10000;
      if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
         var10000 = false;
      } else {
         Iterator var5 = $this$any$iv.iterator();

         while (true) {
            if (!var5.hasNext()) {
               var10000 = false;
               break;
            }

            Object element$iv = var5.next();
            SettingComponent it = (SettingComponent)element$iv;
            int var8/* $VF was: $i$a$-any-SettingsPanel$keyPressed$1 */ = 0;
            if (it.keyPressed(key, modifiers)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public final boolean charTyped(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      var $this$any$iv = this.components;
      int $i$f$any = 0;
      boolean var10000;
      if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
         var10000 = false;
      } else {
         Iterator var4 = $this$any$iv.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            Object element$iv = var4.next();
            SettingComponent it = (SettingComponent)element$iv;
            int var7/* $VF was: $i$a$-any-SettingsPanel$charTyped$1 */ = 0;
            if (it.charTyped(text)) {
               var10000 = true;
               break;
            }
         }
      }

      return var10000;
   }

   public final void persistState() {
      Config.INSTANCE.getPanelState().put(this.tab.getName(), new Config.PanelState(this.x, this.y, this.collapsed));
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b",
      d2 = {
            "Lcom/cb/gui/SettingsPanel$Companion;",
            "",
            "<init>",
            "()V",
            "TILT_PER_SPEED",
            "",
            "TILT_MAX",
            "TILT_STIFFNESS",
            "TILT_DAMPING",
            "VELOCITY_SMOOTHING",
            "SUBHEADER_HEIGHT",
            "cb_client"
      }
   )
   private static final class Companion {
      private Companion() {
      }
   }
}
