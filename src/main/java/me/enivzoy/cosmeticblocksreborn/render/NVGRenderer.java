package me.enivzoy.cosmeticblocksreborn.render;

import java.util.ArrayDeque;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import renderkit.PulsivePipelines;
import renderkit.RenderUtil;
import renderkit.TextRenderUtil;
import renderkit.font.PulsiveFont;

/**
 * Immediate-style GUI drawing facade. Geometry is submitted as vanilla
 * {@code GuiElementRenderState}s (via blaze3d-renderkit) into the current
 * {@link GuiGraphicsExtractor}, which makes it backend agnostic (OpenGL/Vulkan).
 *
 * <p>The public API mirrors the previous NanoVG based renderer so the existing
 * screens stay untouched. Coordinates are expressed in the mod's custom
 * &quot;ui space&quot;; the active extractor is supplied once per frame by the
 * {@code Screen.extractRenderState} mixin.
 */
public final class NVGRenderer {
   @NotNull
   public static final NVGRenderer INSTANCE = new NVGRenderer();
   private static final Logger logger = LoggerFactory.getLogger("cb/render");

   private static GuiGraphicsExtractor extractor;
   private static float uiScale = 1.0F;
   private static float guiScale = 1.0F;
   private static boolean inFrame;
   private static float alpha = 1.0F;

   private static boolean hasScissor;
   private static int scissorLeft;
   private static int scissorTop;
   private static int scissorRight;
   private static int scissorBottom;

   private static final ArrayDeque<float[]> SAVE_STACK = new ArrayDeque<>(16);

   private static PulsiveFont fontRegular;
   private static PulsiveFont fontMedium;
   private static PulsiveFont fontBold;
   private static boolean fontsFailed;

   private NVGRenderer() {
   }

   public static void setExtractor(GuiGraphicsExtractor extractor) {
      NVGRenderer.extractor = extractor;
   }

   public static void clearExtractor() {
      NVGRenderer.extractor = null;
   }

   public final boolean getAvailable() {
      return PulsivePipelines.ready();
   }

   private static boolean ready() {
      return extractor != null && PulsivePipelines.ready();
   }

   public final boolean beginFrame(int framebufferWidth, int framebufferHeight, float uiScale) {
      if (extractor == null || !PulsivePipelines.ready() || framebufferWidth <= 0 || framebufferHeight <= 0) {
         return false;
      }

      NVGRenderer.uiScale = uiScale;
      float gs = 1.0F;
      try {
         gs = (float)Minecraft.getInstance().getWindow().getGuiScale();
      } catch (Throwable ignored) {
      }
      if (gs <= 0.0F) {
         gs = 1.0F;
      }
      NVGRenderer.guiScale = gs;

      alpha = 1.0F;
      hasScissor = false;
      SAVE_STACK.clear();

      Matrix3x2fStack pose = extractor.pose();
      pose.pushMatrix();
      pose.scale(NVGRenderer.uiScale / NVGRenderer.guiScale);

      inFrame = true;
      return true;
   }

   public final void endFrame() {
      if (!inFrame) {
         return;
      }
      inFrame = false;
      hasScissor = false;
      SAVE_STACK.clear();
      if (extractor != null) {
         extractor.pose().popMatrix();
      }
   }

   private static int color(int argb) {
      int a = argb >>> 24 & 0xFF;
      a = Math.round(a * alpha);
      if (a < 0) {
         a = 0;
      } else if (a > 255) {
         a = 255;
      }
      return a << 24 | argb & 0xFFFFFF;
   }

   private static void transformPoint(Matrix3x2fStack pose, float x, float y, float[] out) {
      out[0] = pose.m00() * x + pose.m10() * y + pose.m20();
      out[1] = pose.m01() * x + pose.m11() * y + pose.m21();
   }

   /**
    * Transforms a local rectangle through the current pose and returns the
    * resulting axis-aligned screen-space bounds {@code [left, top, right, bottom]}.
    * Evaluated once, when the clip is defined, so later pose changes (scrolling
    * translations) do not move an already-established clip.
    */
   private static int[] absoluteBounds(float x, float y, float w, float h) {
      Matrix3x2fStack pose = extractor.pose();
      float[] p = new float[2];
      float minX = Float.MAX_VALUE;
      float minY = Float.MAX_VALUE;
      float maxX = -Float.MAX_VALUE;
      float maxY = -Float.MAX_VALUE;

      for (int i = 0; i < 4; i++) {
         float cx = i == 1 || i == 3 ? x + w : x;
         float cy = i >= 2 ? y + h : y;
         transformPoint(pose, cx, cy, p);
         minX = Math.min(minX, p[0]);
         minY = Math.min(minY, p[1]);
         maxX = Math.max(maxX, p[0]);
         maxY = Math.max(maxY, p[1]);
      }

      return new int[]{(int)Math.floor(minX), (int)Math.floor(minY), (int)Math.ceil(maxX), (int)Math.ceil(maxY)};
   }

   private static boolean clipped() {
      return hasScissor && (scissorRight <= scissorLeft || scissorBottom <= scissorTop);
   }

   private static ScreenRectangle scissorArea() {
      if (!hasScissor || scissorRight <= scissorLeft || scissorBottom <= scissorTop) {
         return null;
      }
      return new ScreenRectangle(scissorLeft, scissorTop, scissorRight - scissorLeft, scissorBottom - scissorTop);
   }

   public final void rect(float x, float y, float w, float h, int argb, float radius) {
      if (!ready() || clipped()) {
         return;
      }
      RenderUtil.roundedRect(extractor, x, y, w, h, radius > 0.0F ? radius : 0.01F, color(argb), scissorArea());
   }

   public final void rectVarying(float x, float y, float w, float h, float radTl, float radTr, float radBr, float radBl, int argb) {
      if (!ready() || clipped()) {
         return;
      }
      ScreenRectangle sc = scissorArea();
      int c = color(argb);
      boolean top = radTl > 0.0F || radTr > 0.0F;
      boolean bottom = radBl > 0.0F || radBr > 0.0F;

      if (top && bottom) {
         RenderUtil.roundedRect(extractor, x, y, w, h, Math.min(Math.min(radTl, radTr), Math.min(radBr, radBl)), c, sc);
      } else if (top) {
         RenderUtil.roundedRectAsym(extractor, x, y, w, h, Math.max(radTl, radTr), false, c, sc);
      } else if (bottom) {
         RenderUtil.roundedRectAsym(extractor, x, y, w, h, Math.max(radBl, radBr), true, c, sc);
      } else {
         RenderUtil.roundedRect(extractor, x, y, w, h, 0.01F, c, sc);
      }
   }

   public final void rectOutline(float x, float y, float w, float h, int argb, float radius, float strokeWidth) {
      if (!ready() || clipped()) {
         return;
      }
      RenderUtil.roundedOutline(extractor, x, y, w, h, Math.max(radius, 0.01F), strokeWidth, color(argb), scissorArea());
   }

   public final void circle(float cx, float cy, float radius, int argb) {
      if (!ready() || clipped() || radius <= 0.0F) {
         return;
      }
      RenderUtil.roundedRect(extractor, cx - radius, cy - radius, radius * 2.0F, radius * 2.0F, radius, color(argb), scissorArea());
   }

   public final void line(float x1, float y1, float x2, float y2, int argb, float strokeWidth) {
      if (!ready() || clipped()) {
         return;
      }
      float dx = x2 - x1;
      float dy = y2 - y1;
      float len = (float)Math.sqrt(dx * dx + dy * dy);
      if (len <= 0.0F) {
         return;
      }

      ScreenRectangle sc = scissorArea();
      int c = color(argb);
      Matrix3x2fStack pose = extractor.pose();
      pose.pushMatrix();
      pose.translate(x1, y1);
      pose.rotate((float)Math.atan2(dy, dx));
      RenderUtil.flatRect(extractor, 0.0F, -strokeWidth / 2.0F, len, strokeWidth, c, sc);
      pose.popMatrix();
   }

   public final void gradientRect(float x, float y, float w, float h, float radius, int startArgb, int endArgb, boolean horizontal) {
      if (!ready() || clipped()) {
         return;
      }
      RenderUtil.roundedRectGradient(
              extractor, x, y, w, h, Math.max(radius, 0.01F), color(startArgb), color(endArgb), !horizontal, scissorArea());
   }

   public final void shadow(float x, float y, float w, float h, float radius, float blur, int argb) {
      if (!ready() || clipped()) {
         return;
      }

      ScreenRectangle sc = scissorArea();
      int baseAlpha = Math.round((argb >>> 24 & 0xFF) * alpha);
      if (baseAlpha <= 0) {
         return;
      }

      int rgb = argb & 0xFFFFFF;
      int steps = Math.max(1, Math.round(blur));
      for (int i = steps; i >= 1; i--) {
         float f = i * 0.5F;
         int a = Math.round(baseAlpha * (1.0F - (float)i / (steps + 1.0F)));
         if (a <= 0) {
            continue;
         }
         RenderUtil.roundedRect(extractor, x - f, y - f + 2.0F, w + f * 2.0F, h + f * 2.0F, radius + f, a << 24 | rgb, sc);
      }
   }

   private static PulsiveFont resolveFont(NVGRenderer.Font font) {
      if (fontsFailed) {
         return null;
      }

      try {
         switch (font) {
            case REGULAR:
               if (fontRegular == null) {
                  fontRegular = PulsiveFont.createFromResource("JetBrainsMono-Regular.ttf");
               }
               return fontRegular;
            case MEDIUM:
               if (fontMedium == null) {
                  fontMedium = PulsiveFont.createFromResource("JetBrainsMono-Medium.ttf");
               }
               return fontMedium;
            default:
               if (fontBold == null) {
                  fontBold = PulsiveFont.createFromResource("JetBrainsMono-Bold.ttf");
               }
               return fontBold;
         }
      } catch (Throwable t) {
         fontsFailed = true;
         logger.error("Failed to load GUI font", t);
         return null;
      }
   }

   private static float topFor(float yCenter, float size, PulsiveFont font) {
      return yCenter - font.height(size) / 2.0F;
   }

   public final float text(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      PulsiveFont pf = resolveFont(font);
      if (!ready() || clipped() || pf == null || string.isEmpty()) {
         return 0.0F;
      }
      return TextRenderUtil.drawString(extractor, pf, string, x, topFor(y, size, pf), size, color(argb), scissorArea());
   }

   public final void textCentered(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      PulsiveFont pf = resolveFont(font);
      if (!ready() || clipped() || pf == null || string.isEmpty()) {
         return;
      }
      float w = pf.stringWidth(string, size);
      TextRenderUtil.drawString(extractor, pf, string, x - w / 2.0F, topFor(y, size, pf), size, color(argb), scissorArea());
   }

   public final void textRight(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      PulsiveFont pf = resolveFont(font);
      if (!ready() || clipped() || pf == null || string.isEmpty()) {
         return;
      }
      float w = pf.stringWidth(string, size);
      TextRenderUtil.drawString(extractor, pf, string, x - w, topFor(y, size, pf), size, color(argb), scissorArea());
   }

   public final float textWidth(@NotNull String string, float size, @NotNull NVGRenderer.Font font) {
      PulsiveFont pf = resolveFont(font);
      return pf == null ? 0.0F : pf.stringWidth(string, size);
   }

   public final void scissor(float x, float y, float w, float h, @NotNull Function0<Unit> block) {
      save();
      intersectScissor(x, y, w, h);
      try {
         block.invoke();
      } finally {
         restore();
      }
   }

   public final void save() {
      if (extractor == null) {
         return;
      }
      extractor.pose().pushMatrix();
      SAVE_STACK.push(new float[]{
              alpha, hasScissor ? 1.0F : 0.0F,
              scissorLeft, scissorTop, scissorRight, scissorBottom});
   }

   public final void restore() {
      if (extractor == null || SAVE_STACK.isEmpty()) {
         return;
      }
      float[] s = SAVE_STACK.pop();
      extractor.pose().popMatrix();
      alpha = s[0];
      hasScissor = s[1] != 0.0F;
      scissorLeft = (int)s[2];
      scissorTop = (int)s[3];
      scissorRight = (int)s[4];
      scissorBottom = (int)s[5];
   }

   public final void translate(float x, float y) {
      if (extractor != null) {
         extractor.pose().translate(x, y);
      }
   }

   public final void rotate(float angle) {
      if (extractor != null) {
         extractor.pose().rotate(angle);
      }
   }

   public final void intersectScissor(float x, float y, float w, float h) {
      if (extractor == null) {
         return;
      }

      int[] b = absoluteBounds(x, y, w, h);
      if (!hasScissor) {
         hasScissor = true;
         scissorLeft = b[0];
         scissorTop = b[1];
         scissorRight = b[2];
         scissorBottom = b[3];
         return;
      }

      scissorLeft = Math.max(scissorLeft, b[0]);
      scissorTop = Math.max(scissorTop, b[1]);
      scissorRight = Math.min(scissorRight, b[2]);
      scissorBottom = Math.min(scissorBottom, b[3]);
   }

   public final void globalAlpha(float alpha) {
      NVGRenderer.alpha = Math.max(0.0F, Math.min(1.0F, alpha));
   }

   public enum Font {
      REGULAR("jbm-regular", "/assets/cb/fonts/JetBrainsMono-Regular.ttf"),
      MEDIUM("jbm-medium", "/assets/cb/fonts/JetBrainsMono-Medium.ttf"),
      BOLD("jbm-bold", "/assets/cb/fonts/JetBrainsMono-Bold.ttf");

      @NotNull
      private final String id;
      @NotNull
      private final String resource;

      Font(String id, String resource) {
         this.id = id;
         this.resource = resource;
      }

      @NotNull
      public final String getId() {
         return this.id;
      }

      @NotNull
      public final String getResource() {
         return this.resource;
      }

      @NotNull
      public static EnumEntries<NVGRenderer.Font> getEntries() {
         return EnumEntriesKt.enumEntries(values());
      }
   }
}
