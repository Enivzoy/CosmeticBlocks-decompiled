package com.cb.gui.screens;

import com.cb.gui.NvgScreen;
import com.cb.gui.hud.HudElement;
import com.cb.gui.hud.HudManager;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0016\u001a\u00020\u0006H\u0002J\b\u0010\u0017\u001a\u00020\u0006H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0013H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013H\u0002J\u0018\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013H\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\tH\u0002J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020#H\u0016J \u0010'\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010,\u001a\u00020#2\u0006\u0010$\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\tH\u0002J(\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\u0013H\u0016J\b\u00105\u001a\u00020#H\u0016J\b\u00106\u001a\u00020\u0019H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00067",
   d2 = {
         "Lcom/cb/gui/screens/HudEditorScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "snapThreshold",
         "",
         "gridSize",
         "draggingElement",
         "Lcom/cb/gui/hud/HudElement;",
         "activeSnapLinesX",
         "",
         "Lkotlin/Pair;",
         "activeSnapLinesY",
         "colorCenter",
         "colorGrid",
         "colorEdge",
         "colorElement",
         "uiScale",
         "",
         "screenW",
         "screenH",
         "scaledWidth",
         "scaledHeight",
         "renderOverlay",
         "",
         "delta",
         "draw",
         "drawGrid",
         "w",
         "h",
         "drawSnapLines",
         "drawElement",
         "element",
         "mouseClicked",
         "",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "mouseDragged",
         "dragX",
         "",
         "dragY",
         "mouseReleased",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "applySmartSnap",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "mouseX",
         "mouseY",
         "partialTick",
         "isPauseScreen",
         "onClose",
         "cb_client"
   }
)
public final class HudEditorScreen extends Screen implements NvgScreen {
   private final int snapThreshold = 6;
   private final int gridSize = 20;
   @Nullable
   private HudElement draggingElement;
   @NotNull
   private final List<Pair<Integer, Integer>> activeSnapLinesX = new ArrayList<>();
   @NotNull
   private final List<Pair<Integer, Integer>> activeSnapLinesY = new ArrayList<>();
   private final int colorCenter = -16711681;
   private final int colorGrid = -5570646;
   private final int colorEdge = -256;
   private final int colorElement = -30720;
   private float uiScale = 1.0F;
   private int screenW;
   private int screenH;

   public HudEditorScreen() {
      super(Component.literal("HUD Editor"));
   }

   private final int scaledWidth() {
      return Minecraft.getInstance().getWindow().getGuiScaledWidth();
   }

   private final int scaledHeight() {
      return Minecraft.getInstance().getWindow().getGuiScaledHeight();
   }

   @Override
   public void renderOverlay(float delta) {
      Minecraft minecraft = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(minecraft, "getInstance(...)");
      Window window = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      int fbWidth = window.getWidth();
      int fbHeight = window.getHeight();
      if (fbWidth > 0 && fbHeight > 0) {
         this.uiScale = RangesKt.coerceAtLeast(window.getGuiScale(), 1.0F);
         this.screenW = window.getGuiScaledWidth();
         this.screenH = window.getGuiScaledHeight();
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               this.draw();
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void draw() {
      float w = this.screenW;
      float h = this.screenH;
      NVGRenderer.INSTANCE.rect(0.0F, 0.0F, w, h, 1610612736, 0.0F);
      this.drawGrid(w, h);
      this.drawSnapLines(w, h);

      for (HudElement element : HudManager.INSTANCE.getElements()) {
         this.drawElement(element);
      }

      NVGRenderer.INSTANCE.text("HUD Editor", 10.0F, 12.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      NVGRenderer.INSTANCE.text("Cyan=Center  Green=Grid  Yellow=Edge  Orange=Element", 10.0F, 28.0F, 12.0F, -6513508, null);
      NVGRenderer.INSTANCE.text("Drag elements · ESC saves and exits", 10.0F, 42.0F, 12.0F, -10724260, null);
   }

   private final void drawGrid(float w, float h) {
      int centerX = this.screenW / 2;
      int centerY = this.screenH / 2;
      int grid = 637534207;
      int center = 1627389951;

      for (int x = this.gridSize; x < this.screenW; x += this.gridSize) {
         NVGRenderer.INSTANCE.line(x, 0.0F, x, h, x == centerX ? center : grid, 1.0F);
      }

      for (int y = this.gridSize; y < this.screenH; y += this.gridSize) {
         NVGRenderer.INSTANCE.line(0.0F, y, w, y, y == centerY ? center : grid, 1.0F);
      }

      NVGRenderer.INSTANCE.line(centerX, 0.0F, centerX, h, 1358954495, 2.0F);
      NVGRenderer.INSTANCE.line(0.0F, centerY, w, centerY, 1358954495, 2.0F);
   }

   private final void drawSnapLines(float w, float h) {
      for (Pair pair : this.activeSnapLinesX) {
         int x = ((Number)pair.component1()).intValue();
         int color = ((Number)pair.component2()).intValue();
         NVGRenderer.INSTANCE.line(x, 0.0F, x, h, color, 2.0F);
      }

      for (Pair pair : this.activeSnapLinesY) {
         int y = ((Number)pair.component1()).intValue();
         int color = ((Number)pair.component2()).intValue();
         NVGRenderer.INSTANCE.line(0.0F, y, w, y, color, 2.0F);
      }
   }

   private final void drawElement(HudElement element) {
      float ex = element.getX();
      float ey = element.getY();
      float ew = element.getWidth();
      float eh = element.getHeight();
      boolean active = element == this.draggingElement;
      NVGRenderer.INSTANCE.rect(ex, ey, ew, eh, Integer.MIN_VALUE, 0.0F);
      NVGRenderer.INSTANCE.rectOutline(ex, ey, ew, eh, active ? this.colorCenter : this.colorElement, 0.0F, 1.0F);
      NVGRenderer.INSTANCE.text(element.getId(), ex + 4.0F, ey + eh / 2.0F, 12.0F, -1184275, null);
   }

   @Override
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.button() != 0) {
         return false;
      }

      int mouseX = (int)event.x();
      int mouseY = (int)event.y();

      for (HudElement element : HudManager.INSTANCE.getElements()) {
         if (element.contains(mouseX, mouseY)) {
            this.draggingElement = element;
            element.startDrag(mouseX, mouseY);
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
      Intrinsics.checkNotNullParameter(event, "event");
      int mouseX = (int)event.x();
      int mouseY = (int)event.y();
      if (this.draggingElement != null) {
         HudElement element = this.draggingElement;
         element.updateDrag(mouseX, mouseY, this.scaledWidth(), this.scaledHeight());
         this.applySmartSnap(element);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(@NotNull MouseButtonEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.button() == 0) {
         HudElement element = this.draggingElement;
         if (element != null) {
            element.stopDrag();
            this.draggingElement = null;
            this.activeSnapLinesX.clear();
            this.activeSnapLinesY.clear();
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.key() == 256) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(event);
      }
   }

   private final void applySmartSnap(HudElement element) {
      this.activeSnapLinesX.clear();
      this.activeSnapLinesY.clear();
      int width = this.scaledWidth();
      int height = this.scaledHeight();
      int elementLeft = element.getX();
      int elementRight = element.getX() + element.getWidth();
      int elementCenterX = element.getX() + element.getWidth() / 2;
      int elementTop = element.getY();
      int elementBottom = element.getY() + element.getHeight();
      int elementCenterY = element.getY() + element.getHeight() / 2;
      int centerX = width / 2;
      int centerY = height / 2;
      List<Triple<Integer, Integer, Integer>> snapTargetsX = new ArrayList<>();
      snapTargetsX.add(new Triple<>(0, 0, this.colorEdge));
      snapTargetsX.add(new Triple<>(width, width - element.getWidth(), this.colorEdge));
      snapTargetsX.add(new Triple<>(centerX, centerX - element.getWidth() / 2, this.colorCenter));

      for (int gx = this.gridSize; gx < width; gx += this.gridSize) {
         if (gx != centerX) {
            snapTargetsX.add(new Triple<>(gx, gx - element.getWidth() / 2, this.colorGrid));
         }
      }

      for (HudElement other : HudManager.INSTANCE.getElements()) {
         if (other != element) {
            snapTargetsX.add(new Triple<>(other.getX(), other.getX(), this.colorElement));
            snapTargetsX.add(new Triple<>(other.getX() + other.getWidth(), other.getX() + other.getWidth() - element.getWidth(), this.colorElement));
            snapTargetsX.add(new Triple<>(other.getX() + other.getWidth() / 2, other.getX() + other.getWidth() / 2 - element.getWidth() / 2, this.colorElement));
            snapTargetsX.add(new Triple<>(other.getX() + other.getWidth(), other.getX() + other.getWidth(), this.colorElement));
            snapTargetsX.add(new Triple<>(other.getX(), other.getX() - element.getWidth(), this.colorElement));
         }
      }

      for (Triple triple : snapTargetsX) {
         int target = ((Number)triple.component1()).intValue();
         int snapTo = ((Number)triple.component2()).intValue();
         int color = ((Number)triple.component3()).intValue();
         if (Math.abs(elementCenterX - target) < this.snapThreshold) {
            element.setX(snapTo);
            this.activeSnapLinesX.add(TuplesKt.to(target, color));
            break;
         }

         if (Math.abs(elementLeft - target) < this.snapThreshold) {
            element.setX(target);
            this.activeSnapLinesX.add(TuplesKt.to(target, color));
            break;
         }

         if (Math.abs(elementRight - target) < this.snapThreshold) {
            element.setX(target - element.getWidth());
            this.activeSnapLinesX.add(TuplesKt.to(target, color));
            break;
         }
      }

      List<Triple<Integer, Integer, Integer>> snapTargetsY = new ArrayList<>();
      snapTargetsY.add(new Triple<>(0, 0, this.colorEdge));
      snapTargetsY.add(new Triple<>(height, height - element.getHeight(), this.colorEdge));
      snapTargetsY.add(new Triple<>(centerY, centerY - element.getHeight() / 2, this.colorCenter));

      for (int gy = this.gridSize; gy < height; gy += this.gridSize) {
         if (gy != centerY) {
            snapTargetsY.add(new Triple<>(gy, gy - element.getHeight() / 2, this.colorGrid));
         }
      }

      for (HudElement other : HudManager.INSTANCE.getElements()) {
         if (other != element) {
            snapTargetsY.add(new Triple<>(other.getY(), other.getY(), this.colorElement));
            snapTargetsY.add(new Triple<>(other.getY() + other.getHeight(), other.getY() + other.getHeight() - element.getHeight(), this.colorElement));
            snapTargetsY.add(
               new Triple<>(other.getY() + other.getHeight() / 2, other.getY() + other.getHeight() / 2 - element.getHeight() / 2, this.colorElement)
            );
            snapTargetsY.add(new Triple<>(other.getY() + other.getHeight(), other.getY() + other.getHeight(), this.colorElement));
            snapTargetsY.add(new Triple<>(other.getY(), other.getY() - element.getHeight(), this.colorElement));
         }
      }

      for (Triple triple : snapTargetsY) {
         int target = ((Number)triple.component1()).intValue();
         int snapTo = ((Number)triple.component2()).intValue();
         int color = ((Number)triple.component3()).intValue();
         if (Math.abs(elementCenterY - target) < this.snapThreshold) {
            element.setY(snapTo);
            this.activeSnapLinesY.add(TuplesKt.to(target, color));
            break;
         }

         if (Math.abs(elementTop - target) < this.snapThreshold) {
            element.setY(target);
            this.activeSnapLinesY.add(TuplesKt.to(target, color));
            break;
         }

         if (Math.abs(elementBottom - target) < this.snapThreshold) {
            element.setY(target - element.getHeight());
            this.activeSnapLinesY.add(TuplesKt.to(target, color));
            break;
         }
      }
   }

   @Override
   public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
      Intrinsics.checkNotNullParameter(extractor, "extractor");
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public void onClose() {
      HudManager.INSTANCE.savePositions();
      super.onClose();
   }
}
