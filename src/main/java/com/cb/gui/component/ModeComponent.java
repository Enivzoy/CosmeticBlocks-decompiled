package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ModeSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u001e2\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001:\u0001\u001eB\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0002¢\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rH\u0016J \u0010\u0017\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J \u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\rH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u001f",
   d2 = {
         "Lcom/cb/gui/component/ModeComponent;",
         "Lcom/cb/gui/component/SettingComponent;",
         "Lcom/cb/setting/ModeSetting;",
         "gui",
         "Lcom/cb/gui/ClickGuiScreen;",
         "setting",
         "<init>",
         "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ModeSetting;)V",
         "open",
         "",
         "openAnim",
         "Lcom/cb/render/Anim;",
         "expandedExtra",
         "",
         "getExpandedExtra",
         "()F",
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
         "chevron",
         "cx",
         "cy",
         "t",
         "Companion",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nModeComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModeComponent.kt\ncom/cb/gui/component/ModeComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,91:1\n445#2,4:92\n450#2,3:99\n1924#3,3:96\n*S KotlinDebug\n*F\n+ 1 ModeComponent.kt\ncom/cb/gui/component/ModeComponent\n*L\n46#1:92,4\n46#1:99,3\n47#1:96,3\n*E\n"
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0006",
      d2 = {"Lcom/cb/gui/component/ModeComponent$Companion;", "", "<init>", "()V", "OPTION_HEIGHT", "", "cb_client"}
   )
   private static final class Companion {
      private Companion() {
      }
   }
}
