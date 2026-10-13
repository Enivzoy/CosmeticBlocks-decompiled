package com.cb.gui.hud;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\f\b&\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0004\b\t\u0010\nJ \u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H&J\u0018\u0010*\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J\u0016\u0010+\u001a\u00020\u00182\u0006\u0010,\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u0005J\u0016\u0010.\u001a\u00020#2\u0006\u0010,\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u0005J&\u0010/\u001a\u00020#2\u0006\u0010,\u001a\u00020\u00052\u0006\u0010-\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u00052\u0006\u00101\u001a\u00020\u0005J\u0006\u00102\u001a\u00020#J\u000e\u00103\u001a\u00020#2\u0006\u00104\u001a\u00020\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000e\"\u0004\b\u001e\u0010\u0010R\u001a\u0010\u001f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u000e\"\u0004\b!\u0010\u0010¨\u00065",
   d2 = {
         "Lcom/cb/gui/hud/HudElement;",
         "",
         "id",
         "",
         "x",
         "",
         "y",
         "width",
         "height",
         "<init>",
         "(Ljava/lang/String;IIII)V",
         "getId",
         "()Ljava/lang/String;",
         "getX",
         "()I",
         "setX",
         "(I)V",
         "getY",
         "setY",
         "getWidth",
         "setWidth",
         "getHeight",
         "setHeight",
         "isDragging",
         "",
         "()Z",
         "setDragging",
         "(Z)V",
         "dragOffsetX",
         "getDragOffsetX",
         "setDragOffsetX",
         "dragOffsetY",
         "getDragOffsetY",
         "setDragOffsetY",
         "render",
         "",
         "graphics",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "partialTicks",
         "",
         "renderInEditMode",
         "contains",
         "mouseX",
         "mouseY",
         "startDrag",
         "updateDrag",
         "screenWidth",
         "screenHeight",
         "stopDrag",
         "snapToGrid",
         "gridSize",
         "cb_client"
   }
)
public abstract class HudElement {
   @NotNull
   private final String id;
   private int x;
   private int y;
   private int width;
   private int height;
   private boolean isDragging;
   private int dragOffsetX;
   private int dragOffsetY;

   public HudElement(@NotNull String id, int x, int y, int width, int height) {
      Intrinsics.checkNotNullParameter(id, "id");
      super();
      this.id = id;
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   @NotNull
   public final String getId() {
      return this.id;
   }

   public final int getX() {
      return this.x;
   }

   public final void setX(int var1) {
      this.x = var1/* $VF was: <set-?> */;
   }

   public final int getY() {
      return this.y;
   }

   public final void setY(int var1) {
      this.y = var1/* $VF was: <set-?> */;
   }

   public final int getWidth() {
      return this.width;
   }

   public final void setWidth(int var1) {
      this.width = var1/* $VF was: <set-?> */;
   }

   public final int getHeight() {
      return this.height;
   }

   public final void setHeight(int var1) {
      this.height = var1/* $VF was: <set-?> */;
   }

   public final boolean isDragging() {
      return this.isDragging;
   }

   public final void setDragging(boolean var1) {
      this.isDragging = var1/* $VF was: <set-?> */;
   }

   public final int getDragOffsetX() {
      return this.dragOffsetX;
   }

   public final void setDragOffsetX(int var1) {
      this.dragOffsetX = var1/* $VF was: <set-?> */;
   }

   public final int getDragOffsetY() {
      return this.dragOffsetY;
   }

   public final void setDragOffsetY(int var1) {
      this.dragOffsetY = var1/* $VF was: <set-?> */;
   }

   public abstract void render(@NotNull GuiGraphicsExtractor var1, @NotNull Minecraft var2, float var3);

   public void renderInEditMode(@NotNull GuiGraphicsExtractor graphics, @NotNull Minecraft mc) {
      Intrinsics.checkNotNullParameter(graphics, "graphics");
      Intrinsics.checkNotNullParameter(mc, "mc");
      graphics.fill(this.x, this.y, this.x + this.width, this.y + this.height, Integer.MIN_VALUE);
      graphics.fill(this.x, this.y, this.x + this.width, this.y + 1, -1);
      graphics.fill(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, -1);
      graphics.fill(this.x, this.y, this.x + 1, this.y + this.height, -1);
      graphics.fill(this.x + this.width - 1, this.y, this.x + this.width, this.y + this.height, -1);
      this.render(graphics, mc, 0.0F);
   }

   public final boolean contains(int mouseX, int mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
   }

   public final void startDrag(int mouseX, int mouseY) {
      this.isDragging = true;
      this.dragOffsetX = mouseX - this.x;
      this.dragOffsetY = mouseY - this.y;
   }

   public final void updateDrag(int mouseX, int mouseY, int screenWidth, int screenHeight) {
      if (this.isDragging) {
         this.x = RangesKt.coerceIn(mouseX - this.dragOffsetX, 0, screenWidth - this.width);
         this.y = RangesKt.coerceIn(mouseY - this.dragOffsetY, 0, screenHeight - this.height);
      }
   }

   public final void stopDrag() {
      this.isDragging = false;
   }

   public final void snapToGrid(int gridSize) {
      this.x = this.x / gridSize * gridSize;
      this.y = this.y / gridSize * gridSize;
   }
}
