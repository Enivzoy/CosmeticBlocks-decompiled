package me.enivzoy.cosmeticblocksreborn.gui.screens;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class RadialBlockScreen extends Screen implements NvgScreen {
   private float uiScale = 1.0F;
   private float uiWidth;
   private float uiHeight;
   @NotNull
   private float[] hoverAnim = new float[0];
   private int hoveredIdx = -1;
   @NotNull
   private List<String> favs = CollectionsKt.emptyList();
   private final float twoPi = (float) (Math.PI * 2);

   public RadialBlockScreen() {
      super(Component.literal("Block Wheel"));
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
         this.uiScale = RangesKt.coerceIn(window.getGuiScale() * 0.75F, 1.0F, 3.0F);
         this.uiWidth = fbWidth / this.uiScale;
         this.uiHeight = fbHeight / this.uiScale;
         int screenWidth = window.getScreenWidth();
         int screenHeight = window.getScreenHeight();
         float fbScaleX = screenWidth > 0 ? (float)fbWidth / screenWidth : 1.0F;
         float fbScaleY = screenHeight > 0 ? (float)fbHeight / screenHeight : 1.0F;
         float mx = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
         float my = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, -1946157056, 0.0F);
               float cx = this.uiWidth * 0.5F;
               float cy = this.uiHeight * 0.5F;
               float rOuter = Math.min(this.uiWidth, this.uiHeight) * 0.3F;
               float rInner = rOuter * 0.42F;
               float rMid = (rOuter + rInner) * 0.5F;
               List<String> favs;
               try {
                  favs = CosmeticBlocks.INSTANCE.getFavorites();
                } catch (Throwable ignored) {
                  favs = CollectionsKt.emptyList();
               }

               this.favs = favs;
               NVGRenderer.INSTANCE.circle(cx, cy, rOuter, Theme.INSTANCE.withAlpha(-183233516, 0.92F));
               if (!this.favs.isEmpty()) {
                  float dxx = mx - cx;
                  float dy = my - cy;
                  float dist = (float)Math.sqrt(dxx * dxx + dy * dy);
                  float mouseAngle = (float)((Math.atan2(dy, dxx) + (Math.PI * 2)) % (Math.PI * 2));
                  int n = this.favs.size();
                  float sliceAngle = this.twoPi / n;
                  if (this.hoverAnim.length != n) {
                     this.hoverAnim = new float[n];
                  }

                  int hovered;
                  if (rInner <= dist ? dist <= rOuter + 20.0F : false) {
                     float shifted = (mouseAngle + sliceAngle * 0.5F) % this.twoPi;
                     hovered = (int)(shifted / sliceAngle) % n;
                  } else {
                     hovered = -1;
                  }

                  this.hoveredIdx = hovered;

                  for (int i = 0; i < n; i++) {
                     float target = i == this.hoveredIdx ? 1.0F : 0.0F;
                     float[] a1 = this.hoverAnim;
                     int t = i;
                     a1[t] += (target - this.hoverAnim[i]) * RangesKt.coerceIn(delta * 12.0F, 0.0F, 1.0F);
                  }

                  this.drawCircleOutline(cx, cy, rOuter, Theme.INSTANCE.withAlpha(-1750963, 0.9F), 2.0F);
                  this.drawCircleOutline(cx, cy, rInner, Theme.INSTANCE.withAlpha(-1750963, 0.4F), 1.5F);

                  for (int i = 0; i < n; i++) {
                     float a0 = i * sliceAngle - sliceAngle * 0.5F;
                     float a1 = a0 + sliceAngle;
                     float t = this.hoverAnim[i];
                     float rOuterE = rOuter + t * 8.0F;
                     if (t > 0.02F) {
                        this.drawWedge(cx, cy, rInner, rOuterE, a0, a1, Theme.INSTANCE.withAlpha(-1750963, 0.18F + 0.35F * t));
                     }

                     float sx1 = cx + rInner * (float)Math.cos(a0);
                     float sy1 = cy + rInner * (float)Math.sin(a0);
                     float sx2 = cx + rOuterE * (float)Math.cos(a0);
                     float sy2 = cy + rOuterE * (float)Math.sin(a0);
                     NVGRenderer.INSTANCE.line(sx1, sy1, sx2, sy2, Theme.INSTANCE.withAlpha(-1750963, 0.3F), 1.0F);
                     float midA = a0 + sliceAngle * 0.5F;
                     float lx = cx + rMid * (float)Math.cos(midA);
                     float ly = cy + rMid * (float)Math.sin(midA);
                     String name = StringsKt.removePrefix(this.favs.get(i), "minecraft:");
                     String shortName = name.length() > 12 ? StringsKt.take(name, 11) + "..." : name;
                     int textCol = i == this.hoveredIdx ? -1184275 : -6513508;
                     NVGRenderer.INSTANCE.textCentered(shortName, lx, ly, 12.0F, textCol, NVGRenderer.Font.REGULAR);
                  }

                  NVGRenderer.INSTANCE.circle(cx, cy, rInner - 4.0F, Theme.INSTANCE.withAlpha(-14935012, 0.95F));
                  this.drawCircleOutline(cx, cy, rInner - 4.0F, Theme.INSTANCE.withAlpha(-1750963, 0.6F), 1.5F);
                  if (this.hoveredIdx >= 0) {
                     String fullName = StringsKt.replace(StringsKt.removePrefix(this.favs.get(this.hoveredIdx), "minecraft:"), '_', ' ', false);
                     NVGRenderer.INSTANCE.textCentered("SELECT", cx, cy - 10.0F, 12.0F, -1750963, NVGRenderer.Font.REGULAR);
                     NVGRenderer.INSTANCE.textCentered(fullName, cx, cy + 8.0F, 13.0F, -1184275, NVGRenderer.Font.BOLD);
                  } else {
                     String current = StringsKt.replace(StringsKt.removePrefix(CosmeticBlocks.INSTANCE.getSelectedBlock(), "minecraft:"), '_', ' ', false);
                     NVGRenderer.INSTANCE.textCentered("CURRENT", cx, cy - 10.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
                     NVGRenderer.INSTANCE.textCentered(current, cx, cy + 8.0F, 13.0F, -1184275, NVGRenderer.Font.REGULAR);
                  }

                  NVGRenderer.INSTANCE.textCentered("LMB pick   |   RMB / Esc close", cx, this.uiHeight - 48.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
                  return;
               }

               NVGRenderer.INSTANCE.textCentered("no favorites", cx, cy - 12.0F, 13.0F, -6513508, NVGRenderer.Font.REGULAR);
               NVGRenderer.INSTANCE.textCentered("right-click any block in the selector to add", cx, cy + 10.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
               this.hoveredIdx = -1;
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void drawCircleOutline(float cx, float cy, float r, int argb, float stroke) {
      int segments = 96;
      float prevX = cx + r;
      float prevY = cy;
      int i = 1;

      while (true) {
         float a = this.twoPi * i / segments;
         float nx = cx + r * (float)Math.cos(a);
         float ny = cy + r * (float)Math.sin(a);
         NVGRenderer.INSTANCE.line(prevX, prevY, nx, ny, argb, stroke);
         prevX = nx;
         prevY = ny;
         if (i == segments) {
            return;
         }

         i++;
      }
   }

   private final void drawWedge(float cx, float cy, float rInner, float rOuter, float a0, float a1, int argb) {
      float span = a1 - a0;
      int steps = RangesKt.coerceIn((int)(rOuter * span / 2.0F), 8, 96);
      float stroke = rOuter * span / steps + 1.5F;
      int s = 0;
      if (s <= steps) {
         while (true) {
            float a = a0 + span * s / steps;
            float x1 = cx + rInner * (float)Math.cos(a);
            float y1 = cy + rInner * (float)Math.sin(a);
            float x2 = cx + rOuter * (float)Math.cos(a);
            float y2 = cy + rOuter * (float)Math.sin(a);
            NVGRenderer.INSTANCE.line(x1, y1, x2, y2, argb, stroke);
            if (s == steps) {
               break;
            }

            s++;
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
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      switch (event.button()) {
         case 0:
            int count = this.favs.size();
            int hovered = this.hoveredIdx;
            if (0 <= hovered ? hovered < count : false) {
               CosmeticBlocks.INSTANCE.setBlock(this.favs.get(this.hoveredIdx));
               this.onClose();
            }

            return true;
         case 1:
            this.onClose();
            return true;
         default:
            return true;
      }
   }

   @Override
   public boolean mouseReleased(@NotNull MouseButtonEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (event.button() == 0) {
         int count = this.favs.size();
         int hovered = this.hoveredIdx;
         if (0 <= hovered ? hovered < count : false) {
            CosmeticBlocks.INSTANCE.setBlock(this.favs.get(this.hoveredIdx));
            this.onClose();
         }
      }

      return true;
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
}
