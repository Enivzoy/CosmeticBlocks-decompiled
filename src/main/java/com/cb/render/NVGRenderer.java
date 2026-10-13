package com.cb.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL13C;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NVGRenderer {
   @NotNull
   public static final NVGRenderer INSTANCE = new NVGRenderer();
   private static final Logger logger = LoggerFactory.getLogger("fable/nvg");
   private static long ctx;
   private static boolean broken;
   @NotNull
   private static final ArrayList<ByteBuffer> fontBuffers = new ArrayList<>(3);
   private static int sProgram;
   private static int sVao;
   private static int sArrayBuffer;
   private static int sUniformBuffer;
   private static int sActiveTexture;
   private static int sTexture2D;
   private static int sSampler0;
   private static int sDrawFbo;
   private static int sReadFbo;
   private static boolean sBlend;
   private static int sBlendSrcRgb;
   private static int sBlendDstRgb;
   private static int sBlendSrcAlpha;
   private static int sBlendDstAlpha;
   private static boolean sCull;
   private static int sCullMode;
   private static int sFrontFace;
   private static boolean sDepth;
   private static boolean sDepthMask;
   private static boolean sScissor;
   private static boolean sStencil;
   private static int sStencilWriteMask;
   private static int sStencilFunc;
   private static int sStencilRef;
   private static int sStencilValueMask;
   private static int sStencilFail;
   private static int sStencilZFail;
   private static int sStencilZPass;
   @NotNull
   private static final byte[] sColorMask = new byte[4];
   @NotNull
   private static final int[] sViewport = new int[4];
   @NotNull
   private static final int[] sScissorBox = new int[4];
   @NotNull
   private static final float[] fAscender = new float[1];
   @NotNull
   private static final float[] fDescender = new float[1];
   @NotNull
   private static final float[] fLineH = new float[1];

   private NVGRenderer() {
   }

   public final boolean getAvailable() {
      return ctx != 0L && !broken;
   }

   private final boolean ensureInitialized() {
      if (broken) {
         return false;
      }

      if (ctx != 0L) {
         return true;
      }

      try {
         ctx = NanoVGGL3.nvgCreate(1);
         if (ctx == 0L) {
            throw new IllegalStateException("nvgCreate returned 0");
         }

         for (NVGRenderer.Font font : NVGRenderer.Font.getEntries()) {
            ByteBuffer data = this.loadResource(font.getResource());
            fontBuffers.add(data);
            int handle = NanoVG.nvgCreateFontMem(ctx, font.getId(), data, false);
            if (handle == -1) {
               throw new IllegalStateException("failed to register font " + font.getId());
            }
         }

         logger.info("NanoVG context created, {} fonts loaded", NVGRenderer.Font.getEntries().size());
         return true;
      } catch (Throwable t) {
         broken = true;
         ctx = 0L;
         logger.error("Failed to initialise NanoVG — GUI rendering disabled", t);
         return false;
      }
   }

   private final ByteBuffer loadResource(String path) throws IOException {
      InputStream input = NVGRenderer.class.getResourceAsStream(path);
      if (input != null) {
         byte[] bytes;
         try (InputStream in = input) {
            bytes = ByteStreamsKt.readBytes(in);
         }

         ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length).put(bytes).flip();
         Intrinsics.checkNotNullExpressionValue(buffer, "flip(...)");
         return buffer;
      }

      throw new IllegalStateException("missing resource " + path);
   }

   private final void saveGlState() {
      sProgram = GL11C.glGetInteger(35725);
      sVao = GL11C.glGetInteger(34229);
      sArrayBuffer = GL11C.glGetInteger(34964);
      sUniformBuffer = GL11C.glGetInteger(35368);
      sActiveTexture = GL11C.glGetInteger(34016);
      GL13C.glActiveTexture(33984);
      sTexture2D = GL11C.glGetInteger(32873);
      sSampler0 = GL11C.glGetInteger(35097);
      GL33C.glBindSampler(0, 0);
      sDrawFbo = GL11C.glGetInteger(36006);
      sReadFbo = GL11C.glGetInteger(36010);
      sBlend = GL11C.glIsEnabled(3042);
      sBlendSrcRgb = GL11C.glGetInteger(32969);
      sBlendDstRgb = GL11C.glGetInteger(32968);
      sBlendSrcAlpha = GL11C.glGetInteger(32971);
      sBlendDstAlpha = GL11C.glGetInteger(32970);
      sCull = GL11C.glIsEnabled(2884);
      sCullMode = GL11C.glGetInteger(2885);
      sFrontFace = GL11C.glGetInteger(2886);
      sDepth = GL11C.glIsEnabled(2929);
      sDepthMask = GL11C.glGetBoolean(2930);
      sScissor = GL11C.glIsEnabled(3089);
      sStencil = GL11C.glIsEnabled(2960);
      sStencilWriteMask = GL11C.glGetInteger(2968);
      sStencilFunc = GL11C.glGetInteger(2962);
      sStencilRef = GL11C.glGetInteger(2967);
      sStencilValueMask = GL11C.glGetInteger(2963);
      sStencilFail = GL11C.glGetInteger(2964);
      sStencilZFail = GL11C.glGetInteger(2965);
      sStencilZPass = GL11C.glGetInteger(2966);

      try (MemoryStack stack = MemoryStack.stackPush()) {
         ByteBuffer mask = stack.malloc(4);
         Intrinsics.checkNotNullExpressionValue(mask, "malloc(...)");
         GL11C.glGetBooleanv(3107, mask);

         for (int i = 0; i < 4; i++) {
            sColorMask[i] = mask.get(i);
         }
      }

      GL11C.glGetIntegerv(2978, sViewport);
      GL11C.glGetIntegerv(3088, sScissorBox);
   }

   private final void restoreGlState() {
      GL20C.glUseProgram(sProgram);
      GL30C.glBindVertexArray(sVao);
      GL15C.glBindBuffer(34962, sArrayBuffer);
      GL15C.glBindBuffer(35345, sUniformBuffer);
      GL13C.glActiveTexture(33984);
      GL11C.glBindTexture(3553, sTexture2D);
      GL33C.glBindSampler(0, sSampler0);
      GL13C.glActiveTexture(sActiveTexture);
      GL30C.glBindFramebuffer(36009, sDrawFbo);
      GL30C.glBindFramebuffer(36008, sReadFbo);
      if (sBlend) {
         GL11C.glEnable(3042);
      } else {
         GL11C.glDisable(3042);
      }

      GL14C.glBlendFuncSeparate(sBlendSrcRgb, sBlendDstRgb, sBlendSrcAlpha, sBlendDstAlpha);
      if (sCull) {
         GL11C.glEnable(2884);
      } else {
         GL11C.glDisable(2884);
      }

      GL11C.glCullFace(sCullMode);
      GL11C.glFrontFace(sFrontFace);
      if (sDepth) {
         GL11C.glEnable(2929);
      } else {
         GL11C.glDisable(2929);
      }

      GL11C.glDepthMask(sDepthMask);
      if (sScissor) {
         GL11C.glEnable(3089);
      } else {
         GL11C.glDisable(3089);
      }

      if (sStencil) {
         GL11C.glEnable(2960);
      } else {
         GL11C.glDisable(2960);
      }

      GL11C.glStencilMask(sStencilWriteMask);
      GL11C.glStencilFunc(sStencilFunc, sStencilRef, sStencilValueMask);
      GL11C.glStencilOp(sStencilFail, sStencilZFail, sStencilZPass);
      GL11C.glColorMask(sColorMask[0] != 0, sColorMask[1] != 0, sColorMask[2] != 0, sColorMask[3] != 0);
      GL11C.glViewport(sViewport[0], sViewport[1], sViewport[2], sViewport[3]);
      GL11C.glScissor(sScissorBox[0], sScissorBox[1], sScissorBox[2], sScissorBox[3]);
   }

   public final boolean beginFrame(int framebufferWidth, int framebufferHeight, float uiScale) {
      if (!this.ensureInitialized()) {
         return false;
      } else if (framebufferWidth > 0 && framebufferHeight > 0) {
         this.saveGlState();
         GL30C.glBindFramebuffer(36160, 0);
         GL11C.glViewport(0, 0, framebufferWidth, framebufferHeight);
         GL11C.glDisable(3089);
         NanoVG.nvgBeginFrame(ctx, framebufferWidth, framebufferHeight, 1.0F);
         NanoVG.nvgScale(ctx, uiScale, uiScale);
         return true;
      } else {
         return false;
      }
   }

   public final void endFrame() {
      NanoVG.nvgEndFrame(ctx);
      this.restoreGlState();
   }

   private final NVGColor color(int argb, NVGColor out) {
      NanoVG.nvgRGBA((byte)(argb >> 16 & 0xFF), (byte)(argb >> 8 & 0xFF), (byte)(argb & 0xFF), (byte)(argb >>> 24 & 0xFF), out);
      return out;
   }

   public final void rect(float x, float y, float w, float h, int argb, float radius) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgBeginPath(ctx);
         if (radius > 0.0F) {
            NanoVG.nvgRoundedRect(ctx, x, y, w, h, radius);
         } else {
            NanoVG.nvgRect(ctx, x, y, w, h);
         }

         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         NanoVG.nvgFill(ctx);
      }
   }

   public final void rectVarying(float x, float y, float w, float h, float radTl, float radTr, float radBr, float radBl, int argb) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgRoundedRectVarying(ctx, x, y, w, h, radTl, radTr, radBr, radBl);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         NanoVG.nvgFill(ctx);
      }
   }

   public final void rectOutline(float x, float y, float w, float h, int argb, float radius, float strokeWidth) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         float inset = strokeWidth / 2.0F;
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgRoundedRect(ctx, x + inset, y + inset, w - strokeWidth, h - strokeWidth, RangesKt.coerceAtLeast(radius - inset, 0.0F));
         NanoVG.nvgStrokeWidth(ctx, strokeWidth);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgStrokeColor(ctx, this.color(argb, color));
         NanoVG.nvgStroke(ctx);
      }
   }

   public final void circle(float cx, float cy, float radius, int argb) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgCircle(ctx, cx, cy, radius);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         NanoVG.nvgFill(ctx);
      }
   }

   public final void line(float x1, float y1, float x2, float y2, int argb, float strokeWidth) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgMoveTo(ctx, x1, y1);
         NanoVG.nvgLineTo(ctx, x2, y2);
         NanoVG.nvgStrokeWidth(ctx, strokeWidth);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgStrokeColor(ctx, this.color(argb, color));
         NanoVG.nvgStroke(ctx);
      }
   }

   public final void gradientRect(float x, float y, float w, float h, float radius, int startArgb, int endArgb, boolean horizontal) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NVGPaint paint = NVGPaint.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(paint, "malloc(...)");
         float endX = horizontal ? x + w : x;
         float endY = horizontal ? y : y + h;
         NVGColor startColor = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(startColor, "malloc(...)");
         NVGColor start = this.color(startArgb, startColor);
         NVGColor endColor = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(endColor, "malloc(...)");
         NanoVG.nvgLinearGradient(ctx, x, y, endX, endY, start, this.color(endArgb, endColor), paint);
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgRoundedRect(ctx, x, y, w, h, radius);
         NanoVG.nvgFillPaint(ctx, paint);
         NanoVG.nvgFill(ctx);
      }
   }

   public final void shadow(float x, float y, float w, float h, float radius, float blur, int argb) {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NVGPaint paint = NVGPaint.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(paint, "malloc(...)");
         float shadowY = y + 2.0F;
         NVGColor innerColor = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(innerColor, "malloc(...)");
         NVGColor inner = this.color(argb, innerColor);
         int transparentArgb = argb & 16777215;
         NVGColor outerColor = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(outerColor, "malloc(...)");
         NanoVG.nvgBoxGradient(ctx, x, shadowY, w, h, radius, blur, inner, this.color(transparentArgb, outerColor), paint);
         NanoVG.nvgBeginPath(ctx);
         NanoVG.nvgRect(ctx, x - blur, y - blur, w + blur * 2.0F, h + blur * 2.0F + 2.0F);
         NanoVG.nvgRoundedRect(ctx, x, y, w, h, radius);
         NanoVG.nvgPathWinding(ctx, 2);
         NanoVG.nvgFillPaint(ctx, paint);
         NanoVG.nvgFill(ctx);
      }
   }

   private final float opticalBaseline(float yCenter) {
      NanoVG.nvgTextMetrics(ctx, fAscender, fDescender, fLineH);
      float capHeight = fAscender[0] * 0.7F;
      return yCenter + capHeight / 2.0F;
   }

   public final float text(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      Intrinsics.checkNotNullParameter(string, "string");
      Intrinsics.checkNotNullParameter(font, "font");
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgFontFace(ctx, font.getId());
         NanoVG.nvgFontSize(ctx, size);
         NanoVG.nvgTextAlign(ctx, 65);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         return NanoVG.nvgText(ctx, x, this.opticalBaseline(y), string);
      }
   }

   public final void textCentered(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      Intrinsics.checkNotNullParameter(string, "string");
      Intrinsics.checkNotNullParameter(font, "font");
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgFontFace(ctx, font.getId());
         NanoVG.nvgFontSize(ctx, size);
         NanoVG.nvgTextAlign(ctx, 66);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         NanoVG.nvgText(ctx, x, this.opticalBaseline(y), string);
      }
   }

   public final void textRight(@NotNull String string, float x, float y, float size, int argb, @NotNull NVGRenderer.Font font) {
      Intrinsics.checkNotNullParameter(string, "string");
      Intrinsics.checkNotNullParameter(font, "font");
      try (MemoryStack stack = MemoryStack.stackPush()) {
         NanoVG.nvgFontFace(ctx, font.getId());
         NanoVG.nvgFontSize(ctx, size);
         NanoVG.nvgTextAlign(ctx, 68);
         NVGColor color = NVGColor.malloc(stack);
         Intrinsics.checkNotNullExpressionValue(color, "malloc(...)");
         NanoVG.nvgFillColor(ctx, this.color(argb, color));
         NanoVG.nvgText(ctx, x, this.opticalBaseline(y), string);
      }
   }

   public final float textWidth(@NotNull String string, float size, @NotNull NVGRenderer.Font font) {
      Intrinsics.checkNotNullParameter(string, "string");
      Intrinsics.checkNotNullParameter(font, "font");
      NanoVG.nvgFontFace(ctx, font.getId());
      NanoVG.nvgFontSize(ctx, size);
      NanoVG.nvgTextAlign(ctx, 17);
      return NanoVG.nvgTextBounds(ctx, 0.0F, 0.0F, string, (float[])null);
   }

   public final void scissor(float x, float y, float w, float h, @NotNull Function0<Unit> block) {
      Intrinsics.checkNotNullParameter(block, "block");
      this.save();
      this.intersectScissor(x, y, w, h);

      try {
         block.invoke();
      } finally {
         this.restore();
      }
   }

   public final void save() {
      NanoVG.nvgSave(ctx);
   }

   public final void restore() {
      NanoVG.nvgRestore(ctx);
   }

   public final void translate(float x, float y) {
      NanoVG.nvgTranslate(ctx, x, y);
   }

   public final void rotate(float angle) {
      NanoVG.nvgRotate(ctx, angle);
   }

   public final void intersectScissor(float x, float y, float w, float h) {
      NanoVG.nvgIntersectScissor(ctx, x, y, w, h);
   }

   public final void globalAlpha(float alpha) {
      NanoVG.nvgGlobalAlpha(ctx, RangesKt.coerceIn(alpha, 0.0F, 1.0F));
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
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }
}
