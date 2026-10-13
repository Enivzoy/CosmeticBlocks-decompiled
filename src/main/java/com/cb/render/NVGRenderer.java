package com.cb.render;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.io.ByteStreamsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001yB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u000209H\u0002J\u001e\u0010;\u001a\u00020\n2\u0006\u0010<\u001a\u00020\u00172\u0006\u0010=\u001a\u00020\u00172\u0006\u0010>\u001a\u00020?J\u0006\u0010@\u001a\u000209J\u0018\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00172\u0006\u0010D\u001a\u00020BH\u0002J8\u0010E\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010J\u001a\u00020?JN\u0010K\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010L\u001a\u00020?2\u0006\u0010M\u001a\u00020?2\u0006\u0010N\u001a\u00020?2\u0006\u0010O\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017JB\u0010P\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010J\u001a\u00020?2\b\b\u0002\u0010Q\u001a\u00020?J&\u0010R\u001a\u0002092\u0006\u0010S\u001a\u00020?2\u0006\u0010T\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017J8\u0010U\u001a\u0002092\u0006\u0010V\u001a\u00020?2\u0006\u0010W\u001a\u00020?2\u0006\u0010X\u001a\u00020?2\u0006\u0010Y\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010Q\u001a\u00020?JF\u0010Z\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010[\u001a\u00020\u00172\u0006\u0010\\\u001a\u00020\u00172\u0006\u0010]\u001a\u00020\nJ>\u0010^\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010_\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017J\u0010\u0010d\u001a\u00020?2\u0006\u0010e\u001a\u00020?H\u0002J8\u0010f\u001a\u00020?2\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ8\u0010k\u001a\u0002092\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ8\u0010l\u001a\u0002092\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ \u0010m\u001a\u00020?2\u0006\u0010g\u001a\u00020\u00152\u0006\u0010h\u001a\u00020?2\b\b\u0002\u0010i\u001a\u00020jJ:\u0010n\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\f\u0010o\u001a\b\u0012\u0004\u0012\u0002090pH\u0086\bø\u0001\u0000J\u0006\u0010q\u001a\u000209J\u0006\u0010r\u001a\u000209J\u0016\u0010s\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?J\u000e\u0010t\u001a\u0002092\u0006\u0010u\u001a\u00020?J&\u0010v\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?J\u000e\u0010w\u001a\u0002092\u0006\u0010x\u001a\u00020?R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000206X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020aX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020aX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006z",
   d2 = {
         "Lcom/cb/render/NVGRenderer;",
         "",
         "<init>",
         "()V",
         "logger",
         "Lorg/slf4j/Logger;",
         "kotlin.jvm.PlatformType",
         "ctx",
         "",
         "broken",
         "",
         "fontBuffers",
         "Ljava/util/ArrayList;",
         "Ljava/nio/ByteBuffer;",
         "Lkotlin/collections/ArrayList;",
         "available",
         "getAvailable",
         "()Z",
         "ensureInitialized",
         "loadResource",
         "path",
         "",
         "sProgram",
         "",
         "sVao",
         "sArrayBuffer",
         "sUniformBuffer",
         "sActiveTexture",
         "sTexture2D",
         "sSampler0",
         "sDrawFbo",
         "sReadFbo",
         "sBlend",
         "sBlendSrcRgb",
         "sBlendDstRgb",
         "sBlendSrcAlpha",
         "sBlendDstAlpha",
         "sCull",
         "sCullMode",
         "sFrontFace",
         "sDepth",
         "sDepthMask",
         "sScissor",
         "sStencil",
         "sStencilWriteMask",
         "sStencilFunc",
         "sStencilRef",
         "sStencilValueMask",
         "sStencilFail",
         "sStencilZFail",
         "sStencilZPass",
         "sColorMask",
         "",
         "sViewport",
         "",
         "sScissorBox",
         "saveGlState",
         "",
         "restoreGlState",
         "beginFrame",
         "framebufferWidth",
         "framebufferHeight",
         "uiScale",
         "",
         "endFrame",
         "color",
         "Lorg/lwjgl/nanovg/NVGColor;",
         "argb",
         "out",
         "rect",
         "x",
         "y",
         "w",
         "h",
         "radius",
         "rectVarying",
         "radTl",
         "radTr",
         "radBr",
         "radBl",
         "rectOutline",
         "strokeWidth",
         "circle",
         "cx",
         "cy",
         "line",
         "x1",
         "y1",
         "x2",
         "y2",
         "gradientRect",
         "startArgb",
         "endArgb",
         "horizontal",
         "shadow",
         "blur",
         "fAscender",
         "",
         "fDescender",
         "fLineH",
         "opticalBaseline",
         "yCenter",
         "text",
         "string",
         "size",
         "font",
         "Lcom/cb/render/NVGRenderer$Font;",
         "textCentered",
         "textRight",
         "textWidth",
         "scissor",
         "block",
         "Lkotlin/Function0;",
         "save",
         "restore",
         "translate",
         "rotate",
         "angle",
         "intersectScissor",
         "globalAlpha",
         "alpha",
         "Font",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nNVGRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,473:1\n1#2:474\n*E\n"
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r",
      d2 = {
            "Lcom/cb/render/NVGRenderer$Font;",
            "",
            "id",
            "",
            "resource",
            "<init>",
            "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V",
            "getId",
            "()Ljava/lang/String;",
            "getResource",
            "REGULAR",
            "MEDIUM",
            "BOLD",
            "cb_client"
      }
   )
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
