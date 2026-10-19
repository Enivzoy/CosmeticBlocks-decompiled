package me.enivzoy.cosmeticblocksreborn.gui.hud;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.NotNull;

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

   public final void setX(int x) {
      this.x = x;
   }

   public final int getY() {
      return this.y;
   }

   public final void setY(int y) {
      this.y = y;
   }

   public final int getWidth() {
      return this.width;
   }

   public final void setWidth(int width) {
      this.width = width;
   }

   public final int getHeight() {
      return this.height;
   }

   public final void setHeight(int height) {
      this.height = height;
   }

   public final boolean isDragging() {
      return this.isDragging;
   }

   public final void setDragging(boolean dragging) {
      this.isDragging = dragging;
   }

   public final int getDragOffsetX() {
      return this.dragOffsetX;
   }

   public final void setDragOffsetX(int dragOffsetX) {
      this.dragOffsetX = dragOffsetX;
   }

   public final int getDragOffsetY() {
      return this.dragOffsetY;
   }

   public final void setDragOffsetY(int dragOffsetY) {
      this.dragOffsetY = dragOffsetY;
   }

   public abstract void render(@NotNull GuiGraphicsExtractor graphics, @NotNull Minecraft mc, float partialTicks);

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
