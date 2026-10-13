package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ConfigListSetting;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J \u0010\u001b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082D¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012¨\u0006\u001e",
   d2 = {
         "Lcom/cb/gui/component/ConfigListComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/ConfigListSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ConfigListSetting;)V",
         "open",
         "",
         "expandAnim",
         "Lcom/cb/render/Anim;",
         "options",
         "",
         "",
         "rowH",
         "",
         "getRowH",
         "()F",
         "optionH",
         "height",
         "getHeight",
         "render",
         "",
         "mouseX",
         "mouseY",
         "delta",
         "mouseClicked",
         "button",
         "",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nConfigListComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigListComponent.kt\ncom/cb/gui/component/ConfigListComponent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n1#2:73\n445#3,4:74\n450#3,3:80\n1915#4,2:78\n*S KotlinDebug\n*F\n+ 1 ConfigListComponent.kt\ncom/cb/gui/component/ConfigListComponent\n*L\n37#1:74,4\n37#1:80,3\n39#1:78,2\n*E\n"
)
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
      NVGRenderer var10000 = NVGRenderer.INSTANCE;
      CharSequence listHeight = this.getSetting().getValue();
      CharSequence var10001;
      if (listHeight.length() == 0) {
         NVGRenderer var22 = var10000;
         int var5/* $VF was: $i$a$-ifEmpty-ConfigListComponent$render$1 */ = 0;
         var10001 = "—";
         var10000 = var22;
      } else {
         var10001 = listHeight;
      }

      var10000.textRight((String)var10001, this.getPadRight(), this.getY() + this.getRowH() / 2.0F, 12.0F, -6513508, null);
      this.expandAnim.setTarget(this.open ? 1.0F : 0.0F);
      this.expandAnim.update(delta);
      if (this.expandAnim.getValue() > 0.01F && !this.options.isEmpty()) {
         float listHeightx = this.expandAnim.getValue() * (this.options.size() * this.optionH + 4.0F);
         NVGRenderer var26 = NVGRenderer.INSTANCE;
         float x$iv = this.getX();
         float y$iv = this.getY() + this.getRowH();
         float w$iv = this.getWidth();
         float h$iv = listHeightx;
         int $i$f$scissor = 0;
         var26.save();
         var26.intersectScissor(x$iv, y$iv, w$iv, h$iv);

         try {
            int var11/* $VF was: $i$a$-scissor-ConfigListComponent$render$2 */ = 0;
            float oy = 0.0F;
            oy = this.getY() + this.getRowH() + 2.0F;
            Iterable $this$forEach$iv = this.options;
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               String opt = (String)element$iv;
               int var18/* $VF was: $i$a$-forEach-ConfigListComponent$render$2$1 */ = 0;
               boolean hovered = mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH;
               if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.getX() + 6.0F, oy, this.getWidth() - 12.0F, this.optionH, 318767103, 4.0F);
               }

               int color = Intrinsics.areEqual(opt, this.getSetting().getValue()) ? -1750963 : -6513508;
               NVGRenderer.INSTANCE.text(opt, this.getX() + 18.0F, oy + this.optionH / 2.0F, 12.0F, color, null);
               oy += this.optionH;
            }
         } finally {
            var26.restore();
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
