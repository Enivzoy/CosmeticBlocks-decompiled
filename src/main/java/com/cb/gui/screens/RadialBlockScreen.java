package com.cb.gui.screens;

import com.cb.cosmetic.CosmeticBlocks;
import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0016J0\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J@\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\fH\u0002J(\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u0006H\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020&H\u0016J\u0010\u0010+\u001a\u00020&2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020&2\u0006\u0010(\u001a\u00020-H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006.",
   d2 = {
         "Lcom/cb/gui/screens/RadialBlockScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "uiScale",
         "",
         "uiWidth",
         "uiHeight",
         "hoverAnim",
         "",
         "hoveredIdx",
         "",
         "favs",
         "",
         "",
         "twoPi",
         "renderOverlay",
         "",
         "delta",
         "drawCircleOutline",
         "cx",
         "cy",
         "r",
         "argb",
         "stroke",
         "drawWedge",
         "rInner",
         "rOuter",
         "a0",
         "a1",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "mouseX",
         "mouseY",
         "partialTick",
         "isPauseScreen",
         "",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "mouseReleased",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nRadialBlockScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RadialBlockScreen.kt\ncom/cb/gui/screens/RadialBlockScreen\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,229:1\n1#2:230\n*E\n"
)
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft minecraft = var10000;
      Window var56 = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(var56, "getWindow(...)");
      Window window = var56;
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
               } catch (Throwable var41) {
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

                  int var10001;
                  if (rInner <= dist ? dist <= rOuter + 20.0F : false) {
                     float shifted = (mouseAngle + sliceAngle * 0.5F) % this.twoPi;
                     var10001 = (int)(shifted / sliceAngle) % n;
                  } else {
                     var10001 = -1;
                  }

                  this.hoveredIdx = var10001;

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
                     NVGRenderer.INSTANCE.textCentered(shortName, lx, ly, 12.0F, textCol, null);
                  }

                  NVGRenderer.INSTANCE.circle(cx, cy, rInner - 4.0F, Theme.INSTANCE.withAlpha(-14935012, 0.95F));
                  this.drawCircleOutline(cx, cy, rInner - 4.0F, Theme.INSTANCE.withAlpha(-1750963, 0.6F), 1.5F);
                  if (this.hoveredIdx >= 0) {
                     String fullName = StringsKt.replace(StringsKt.removePrefix(this.favs.get(this.hoveredIdx), "minecraft:"), '_', ' ', false);
                     NVGRenderer.INSTANCE.textCentered("SELECT", cx, cy - 10.0F, 12.0F, -1750963, null);
                     NVGRenderer.INSTANCE.textCentered(fullName, cx, cy + 8.0F, 13.0F, -1184275, NVGRenderer.Font.BOLD);
                  } else {
                     String current = StringsKt.replace(StringsKt.removePrefix(CosmeticBlocks.INSTANCE.getSelectedBlock(), "minecraft:"), '_', ' ', false);
                     NVGRenderer.INSTANCE.textCentered("CURRENT", cx, cy - 10.0F, 12.0F, -6513508, null);
                     NVGRenderer.INSTANCE.textCentered(current, cx, cy + 8.0F, 13.0F, -1184275, null);
                  }

                  NVGRenderer.INSTANCE.textCentered("LMB pick   |   RMB / Esc close", cx, this.uiHeight - 48.0F, 12.0F, -10724260, null);
                  return;
               }

               NVGRenderer.INSTANCE.textCentered("no favorites", cx, cy - 12.0F, 13.0F, -6513508, null);
               NVGRenderer.INSTANCE.textCentered("right-click any block in the selector to add", cx, cy + 10.0F, 12.0F, -10724260, null);
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
            int var3 = this.favs.size();
            int var4 = this.hoveredIdx;
            if (0 <= var4 ? var4 < var3 : false) {
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
         int var2 = this.favs.size();
         int var3 = this.hoveredIdx;
         if (0 <= var3 ? var3 < var2 : false) {
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
