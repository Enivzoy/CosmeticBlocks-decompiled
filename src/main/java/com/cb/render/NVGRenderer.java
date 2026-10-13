/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.io.ByteStreamsKt
 *  kotlin.io.CloseableKt
 *  kotlin.jdk7.AutoCloseableKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.InlineMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 *  org.lwjgl.nanovg.NVGColor
 *  org.lwjgl.nanovg.NVGPaint
 *  org.lwjgl.nanovg.NanoVG
 *  org.lwjgl.nanovg.NanoVGGL3
 *  org.lwjgl.opengl.GL11C
 *  org.lwjgl.opengl.GL13C
 *  org.lwjgl.opengl.GL14C
 *  org.lwjgl.opengl.GL15C
 *  org.lwjgl.opengl.GL20C
 *  org.lwjgl.opengl.GL30C
 *  org.lwjgl.opengl.GL33C
 *  org.lwjgl.system.MemoryStack
 *  org.lwjgl.system.MemoryUtil
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.cb.render;

import java.io.Closeable;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
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

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u001c\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u0014\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001yB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u000209H\u0002J\u001e\u0010;\u001a\u00020\n2\u0006\u0010<\u001a\u00020\u00172\u0006\u0010=\u001a\u00020\u00172\u0006\u0010>\u001a\u00020?J\u0006\u0010@\u001a\u000209J\u0018\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020\u00172\u0006\u0010D\u001a\u00020BH\u0002J8\u0010E\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010J\u001a\u00020?JN\u0010K\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010L\u001a\u00020?2\u0006\u0010M\u001a\u00020?2\u0006\u0010N\u001a\u00020?2\u0006\u0010O\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017JB\u0010P\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010J\u001a\u00020?2\b\b\u0002\u0010Q\u001a\u00020?J&\u0010R\u001a\u0002092\u0006\u0010S\u001a\u00020?2\u0006\u0010T\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017J8\u0010U\u001a\u0002092\u0006\u0010V\u001a\u00020?2\u0006\u0010W\u001a\u00020?2\u0006\u0010X\u001a\u00020?2\u0006\u0010Y\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010Q\u001a\u00020?JF\u0010Z\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010[\u001a\u00020\u00172\u0006\u0010\\\u001a\u00020\u00172\u0006\u0010]\u001a\u00020\nJ>\u0010^\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\u0006\u0010J\u001a\u00020?2\u0006\u0010_\u001a\u00020?2\u0006\u0010C\u001a\u00020\u0017J\u0010\u0010d\u001a\u00020?2\u0006\u0010e\u001a\u00020?H\u0002J8\u0010f\u001a\u00020?2\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ8\u0010k\u001a\u0002092\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ8\u0010l\u001a\u0002092\u0006\u0010g\u001a\u00020\u00152\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010h\u001a\u00020?2\u0006\u0010C\u001a\u00020\u00172\b\b\u0002\u0010i\u001a\u00020jJ \u0010m\u001a\u00020?2\u0006\u0010g\u001a\u00020\u00152\u0006\u0010h\u001a\u00020?2\b\b\u0002\u0010i\u001a\u00020jJ:\u0010n\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?2\f\u0010o\u001a\b\u0012\u0004\u0012\u0002090pH\u0086\b\u00f8\u0001\u0000J\u0006\u0010q\u001a\u000209J\u0006\u0010r\u001a\u000209J\u0016\u0010s\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?J\u000e\u0010t\u001a\u0002092\u0006\u0010u\u001a\u00020?J&\u0010v\u001a\u0002092\u0006\u0010F\u001a\u00020?2\u0006\u0010G\u001a\u00020?2\u0006\u0010H\u001a\u00020?2\u0006\u0010I\u001a\u00020?J\u000e\u0010w\u001a\u0002092\u0006\u0010x\u001a\u00020?R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00020\r`\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000206X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020aX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006z"}, d2={"Lcom/cb/render/NVGRenderer;", "", "<init>", "()V", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "ctx", "", "broken", "", "fontBuffers", "Ljava/util/ArrayList;", "Ljava/nio/ByteBuffer;", "Lkotlin/collections/ArrayList;", "available", "getAvailable", "()Z", "ensureInitialized", "loadResource", "path", "", "sProgram", "", "sVao", "sArrayBuffer", "sUniformBuffer", "sActiveTexture", "sTexture2D", "sSampler0", "sDrawFbo", "sReadFbo", "sBlend", "sBlendSrcRgb", "sBlendDstRgb", "sBlendSrcAlpha", "sBlendDstAlpha", "sCull", "sCullMode", "sFrontFace", "sDepth", "sDepthMask", "sScissor", "sStencil", "sStencilWriteMask", "sStencilFunc", "sStencilRef", "sStencilValueMask", "sStencilFail", "sStencilZFail", "sStencilZPass", "sColorMask", "", "sViewport", "", "sScissorBox", "saveGlState", "", "restoreGlState", "beginFrame", "framebufferWidth", "framebufferHeight", "uiScale", "", "endFrame", "color", "Lorg/lwjgl/nanovg/NVGColor;", "argb", "out", "rect", "x", "y", "w", "h", "radius", "rectVarying", "radTl", "radTr", "radBr", "radBl", "rectOutline", "strokeWidth", "circle", "cx", "cy", "line", "x1", "y1", "x2", "y2", "gradientRect", "startArgb", "endArgb", "horizontal", "shadow", "blur", "fAscender", "", "fDescender", "fLineH", "opticalBaseline", "yCenter", "text", "string", "size", "font", "Lcom/cb/render/NVGRenderer$Font;", "textCentered", "textRight", "textWidth", "scissor", "block", "Lkotlin/Function0;", "save", "restore", "translate", "rotate", "angle", "intersectScissor", "globalAlpha", "alpha", "Font", "cb_client"})
@SourceDebugExtension(value={"SMAP\nNVGRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,473:1\n1#2:474\n*E\n"})
public final class NVGRenderer {
    @NotNull
    public static final NVGRenderer INSTANCE = new NVGRenderer();
    private static final Logger logger = LoggerFactory.getLogger((String)"fable/nvg");
    private static long ctx;
    private static boolean broken;
    @NotNull
    private static final ArrayList<ByteBuffer> fontBuffers;
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
    private static final byte[] sColorMask;
    @NotNull
    private static final int[] sViewport;
    @NotNull
    private static final int[] sScissorBox;
    @NotNull
    private static final float[] fAscender;
    @NotNull
    private static final float[] fDescender;
    @NotNull
    private static final float[] fLineH;

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
            ctx = NanoVGGL3.nvgCreate((int)1);
            if (!(ctx != 0L)) {
                boolean $i$a$-check-NVGRenderer$ensureInitialized$32 = false;
                String $i$a$-check-NVGRenderer$ensureInitialized$32 = "nvgCreate returned 0";
                throw new IllegalStateException($i$a$-check-NVGRenderer$ensureInitialized$32.toString());
            }
            for (Font font : Font.getEntries()) {
                ByteBuffer data = this.loadResource(font.getResource());
                ((Collection)fontBuffers).add(data);
                int handle = NanoVG.nvgCreateFontMem((long)ctx, (CharSequence)font.getId(), (ByteBuffer)data, (boolean)false);
                if (handle != -1) continue;
                boolean bl = false;
                String string = "failed to register font " + font.getId();
                throw new IllegalStateException(string.toString());
            }
            logger.info("NanoVG context created, {} fonts loaded", (Object)Font.getEntries().size());
        }
        catch (Throwable t) {
            broken = true;
            ctx = 0L;
            logger.error("Failed to initialise NanoVG \u2014 GUI rendering disabled", t);
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final ByteBuffer loadResource(String path) {
        Object object;
        block9: {
            block8: {
                byte[] byArray;
                object = NVGRenderer.class.getResourceAsStream(path);
                if (object == null) break block8;
                Closeable closeable = (Closeable)object;
                Throwable throwable = null;
                try {
                    InputStream it = (InputStream)closeable;
                    boolean bl = false;
                    byArray = ByteStreamsKt.readBytes((InputStream)it);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
                }
                object = byArray;
                if (byArray != null) break block9;
            }
            throw new IllegalStateException(("missing resource " + path).toString());
        }
        Object bytes = object;
        ByteBuffer byteBuffer = MemoryUtil.memAlloc((int)((Object)bytes).length).put((byte[])bytes).flip();
        Intrinsics.checkNotNullExpressionValue((Object)byteBuffer, (String)"flip(...)");
        return byteBuffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void saveGlState() {
        sProgram = GL11C.glGetInteger((int)35725);
        sVao = GL11C.glGetInteger((int)34229);
        sArrayBuffer = GL11C.glGetInteger((int)34964);
        sUniformBuffer = GL11C.glGetInteger((int)35368);
        sActiveTexture = GL11C.glGetInteger((int)34016);
        GL13C.glActiveTexture((int)33984);
        sTexture2D = GL11C.glGetInteger((int)32873);
        sSampler0 = GL11C.glGetInteger((int)35097);
        GL33C.glBindSampler((int)0, (int)0);
        sDrawFbo = GL11C.glGetInteger((int)36006);
        sReadFbo = GL11C.glGetInteger((int)36010);
        sBlend = GL11C.glIsEnabled((int)3042);
        sBlendSrcRgb = GL11C.glGetInteger((int)32969);
        sBlendDstRgb = GL11C.glGetInteger((int)32968);
        sBlendSrcAlpha = GL11C.glGetInteger((int)32971);
        sBlendDstAlpha = GL11C.glGetInteger((int)32970);
        sCull = GL11C.glIsEnabled((int)2884);
        sCullMode = GL11C.glGetInteger((int)2885);
        sFrontFace = GL11C.glGetInteger((int)2886);
        sDepth = GL11C.glIsEnabled((int)2929);
        sDepthMask = GL11C.glGetBoolean((int)2930);
        sScissor = GL11C.glIsEnabled((int)3089);
        sStencil = GL11C.glIsEnabled((int)2960);
        sStencilWriteMask = GL11C.glGetInteger((int)2968);
        sStencilFunc = GL11C.glGetInteger((int)2962);
        sStencilRef = GL11C.glGetInteger((int)2967);
        sStencilValueMask = GL11C.glGetInteger((int)2963);
        sStencilFail = GL11C.glGetInteger((int)2964);
        sStencilZFail = GL11C.glGetInteger((int)2965);
        sStencilZPass = GL11C.glGetInteger((int)2966);
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            ByteBuffer byteBuffer = stack.malloc(4);
            Intrinsics.checkNotNullExpressionValue((Object)byteBuffer, (String)"malloc(...)");
            ByteBuffer mask = byteBuffer;
            GL11C.glGetBooleanv((int)3107, (ByteBuffer)mask);
            for (int i = 0; i < 4; ++i) {
                NVGRenderer.sColorMask[i] = mask.get(i);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
        GL11C.glGetIntegerv((int)2978, (int[])sViewport);
        GL11C.glGetIntegerv((int)3088, (int[])sScissorBox);
    }

    private final void restoreGlState() {
        GL20C.glUseProgram((int)sProgram);
        GL30C.glBindVertexArray((int)sVao);
        GL15C.glBindBuffer((int)34962, (int)sArrayBuffer);
        GL15C.glBindBuffer((int)35345, (int)sUniformBuffer);
        GL13C.glActiveTexture((int)33984);
        GL11C.glBindTexture((int)3553, (int)sTexture2D);
        GL33C.glBindSampler((int)0, (int)sSampler0);
        GL13C.glActiveTexture((int)sActiveTexture);
        GL30C.glBindFramebuffer((int)36009, (int)sDrawFbo);
        GL30C.glBindFramebuffer((int)36008, (int)sReadFbo);
        if (sBlend) {
            GL11C.glEnable((int)3042);
        } else {
            GL11C.glDisable((int)3042);
        }
        GL14C.glBlendFuncSeparate((int)sBlendSrcRgb, (int)sBlendDstRgb, (int)sBlendSrcAlpha, (int)sBlendDstAlpha);
        if (sCull) {
            GL11C.glEnable((int)2884);
        } else {
            GL11C.glDisable((int)2884);
        }
        GL11C.glCullFace((int)sCullMode);
        GL11C.glFrontFace((int)sFrontFace);
        if (sDepth) {
            GL11C.glEnable((int)2929);
        } else {
            GL11C.glDisable((int)2929);
        }
        GL11C.glDepthMask((boolean)sDepthMask);
        if (sScissor) {
            GL11C.glEnable((int)3089);
        } else {
            GL11C.glDisable((int)3089);
        }
        if (sStencil) {
            GL11C.glEnable((int)2960);
        } else {
            GL11C.glDisable((int)2960);
        }
        GL11C.glStencilMask((int)sStencilWriteMask);
        GL11C.glStencilFunc((int)sStencilFunc, (int)sStencilRef, (int)sStencilValueMask);
        GL11C.glStencilOp((int)sStencilFail, (int)sStencilZFail, (int)sStencilZPass);
        GL11C.glColorMask((sColorMask[0] != 0 ? 1 : 0) != 0, (sColorMask[1] != 0 ? 1 : 0) != 0, (sColorMask[2] != 0 ? 1 : 0) != 0, (sColorMask[3] != 0 ? 1 : 0) != 0);
        GL11C.glViewport((int)sViewport[0], (int)sViewport[1], (int)sViewport[2], (int)sViewport[3]);
        GL11C.glScissor((int)sScissorBox[0], (int)sScissorBox[1], (int)sScissorBox[2], (int)sScissorBox[3]);
    }

    public final boolean beginFrame(int framebufferWidth, int framebufferHeight, float uiScale) {
        if (!this.ensureInitialized()) {
            return false;
        }
        if (framebufferWidth <= 0 || framebufferHeight <= 0) {
            return false;
        }
        this.saveGlState();
        GL30C.glBindFramebuffer((int)36160, (int)0);
        GL11C.glViewport((int)0, (int)0, (int)framebufferWidth, (int)framebufferHeight);
        GL11C.glDisable((int)3089);
        NanoVG.nvgBeginFrame((long)ctx, (float)framebufferWidth, (float)framebufferHeight, (float)1.0f);
        NanoVG.nvgScale((long)ctx, (float)uiScale, (float)uiScale);
        return true;
    }

    public final void endFrame() {
        NanoVG.nvgEndFrame((long)ctx);
        this.restoreGlState();
    }

    private final NVGColor color(int argb, NVGColor out) {
        NanoVG.nvgRGBA((byte)((byte)(argb >> 16 & 0xFF)), (byte)((byte)(argb >> 8 & 0xFF)), (byte)((byte)(argb & 0xFF)), (byte)((byte)(argb >>> 24 & 0xFF)), (NVGColor)out);
        return out;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void rect(float x, float y, float w, float h, int argb, float radius) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgBeginPath((long)ctx);
            if (radius > 0.0f) {
                NanoVG.nvgRoundedRect((long)ctx, (float)x, (float)y, (float)w, (float)h, (float)radius);
            } else {
                NanoVG.nvgRect((long)ctx, (float)x, (float)y, (float)w, (float)h);
            }
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            NanoVG.nvgFill((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ void rect$default(NVGRenderer nVGRenderer, float f, float f2, float f3, float f4, int n, float f5, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            f5 = 0.0f;
        }
        nVGRenderer.rect(f, f2, f3, f4, n, f5);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void rectVarying(float x, float y, float w, float h, float radTl, float radTr, float radBr, float radBl, int argb) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgRoundedRectVarying((long)ctx, (float)x, (float)y, (float)w, (float)h, (float)radTl, (float)radTr, (float)radBr, (float)radBl);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            NanoVG.nvgFill((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void rectOutline(float x, float y, float w, float h, int argb, float radius, float strokeWidth) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            float inset = strokeWidth / 2.0f;
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgRoundedRect((long)ctx, (float)(x + inset), (float)(y + inset), (float)(w - strokeWidth), (float)(h - strokeWidth), (float)RangesKt.coerceAtLeast((float)(radius - inset), (float)0.0f));
            NanoVG.nvgStrokeWidth((long)ctx, (float)strokeWidth);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgStrokeColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            NanoVG.nvgStroke((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ void rectOutline$default(NVGRenderer nVGRenderer, float f, float f2, float f3, float f4, int n, float f5, float f6, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            f5 = 0.0f;
        }
        if ((n2 & 0x40) != 0) {
            f6 = 1.0f;
        }
        nVGRenderer.rectOutline(f, f2, f3, f4, n, f5, f6);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void circle(float cx, float cy, float radius, int argb) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgCircle((long)ctx, (float)cx, (float)cy, (float)radius);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            NanoVG.nvgFill((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void line(float x1, float y1, float x2, float y2, int argb, float strokeWidth) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgMoveTo((long)ctx, (float)x1, (float)y1);
            NanoVG.nvgLineTo((long)ctx, (float)x2, (float)y2);
            NanoVG.nvgStrokeWidth((long)ctx, (float)strokeWidth);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgStrokeColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            NanoVG.nvgStroke((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ void line$default(NVGRenderer nVGRenderer, float f, float f2, float f3, float f4, int n, float f5, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            f5 = 1.0f;
        }
        nVGRenderer.line(f, f2, f3, f4, n, f5);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void gradientRect(float x, float y, float w, float h, float radius, int startArgb, int endArgb, boolean horizontal) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NVGPaint nVGPaint = NVGPaint.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGPaint, (String)"malloc(...)");
            NVGPaint paint = nVGPaint;
            float endX = horizontal ? x + w : x;
            float endY = horizontal ? y : y + h;
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NVGColor nVGColor2 = INSTANCE.color(startArgb, nVGColor);
            NVGColor nVGColor3 = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor3, (String)"malloc(...)");
            NanoVG.nvgLinearGradient((long)ctx, (float)x, (float)y, (float)endX, (float)endY, (NVGColor)nVGColor2, (NVGColor)INSTANCE.color(endArgb, nVGColor3), (NVGPaint)paint);
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgRoundedRect((long)ctx, (float)x, (float)y, (float)w, (float)h, (float)radius);
            NanoVG.nvgFillPaint((long)ctx, (NVGPaint)paint);
            NanoVG.nvgFill((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void shadow(float x, float y, float w, float h, float radius, float blur, int argb) {
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NVGPaint nVGPaint = NVGPaint.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGPaint, (String)"malloc(...)");
            NVGPaint paint = nVGPaint;
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NVGColor nVGColor2 = INSTANCE.color(argb, nVGColor);
            NVGColor nVGColor3 = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor3, (String)"malloc(...)");
            NanoVG.nvgBoxGradient((long)ctx, (float)x, (float)(y + 2.0f), (float)w, (float)h, (float)radius, (float)blur, (NVGColor)nVGColor2, (NVGColor)INSTANCE.color(argb & 0xFFFFFF, nVGColor3), (NVGPaint)paint);
            NanoVG.nvgBeginPath((long)ctx);
            NanoVG.nvgRect((long)ctx, (float)(x - blur), (float)(y - blur), (float)(w + blur * 2.0f), (float)(h + blur * 2.0f + 2.0f));
            NanoVG.nvgRoundedRect((long)ctx, (float)x, (float)y, (float)w, (float)h, (float)radius);
            NanoVG.nvgPathWinding((long)ctx, (int)2);
            NanoVG.nvgFillPaint((long)ctx, (NVGPaint)paint);
            NanoVG.nvgFill((long)ctx);
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    private final float opticalBaseline(float yCenter) {
        NanoVG.nvgTextMetrics((long)ctx, (float[])fAscender, (float[])fDescender, (float[])fLineH);
        float capHeight = fAscender[0] * 0.7f;
        return yCenter + capHeight / 2.0f;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final float text(@NotNull String string, float x, float y, float size, int argb, @NotNull Font font) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        Intrinsics.checkNotNullParameter((Object)((Object)font), (String)"font");
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgFontFace((long)ctx, (CharSequence)font.getId());
            NanoVG.nvgFontSize((long)ctx, (float)size);
            NanoVG.nvgTextAlign((long)ctx, (int)65);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            float f = NanoVG.nvgText((long)ctx, (float)x, (float)INSTANCE.opticalBaseline(y), (CharSequence)string);
            return f;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ float text$default(NVGRenderer nVGRenderer, String string, float f, float f2, float f3, int n, Font font, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            font = Font.REGULAR;
        }
        return nVGRenderer.text(string, f, f2, f3, n, font);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void textCentered(@NotNull String string, float x, float y, float size, int argb, @NotNull Font font) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        Intrinsics.checkNotNullParameter((Object)((Object)font), (String)"font");
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgFontFace((long)ctx, (CharSequence)font.getId());
            NanoVG.nvgFontSize((long)ctx, (float)size);
            NanoVG.nvgTextAlign((long)ctx, (int)66);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            float f = NanoVG.nvgText((long)ctx, (float)x, (float)INSTANCE.opticalBaseline(y), (CharSequence)string);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ void textCentered$default(NVGRenderer nVGRenderer, String string, float f, float f2, float f3, int n, Font font, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            font = Font.REGULAR;
        }
        nVGRenderer.textCentered(string, f, f2, f3, n, font);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void textRight(@NotNull String string, float x, float y, float size, int argb, @NotNull Font font) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        Intrinsics.checkNotNullParameter((Object)((Object)font), (String)"font");
        AutoCloseable autoCloseable = (AutoCloseable)MemoryStack.stackPush();
        Throwable throwable = null;
        try {
            MemoryStack stack = (MemoryStack)autoCloseable;
            boolean bl = false;
            NanoVG.nvgFontFace((long)ctx, (CharSequence)font.getId());
            NanoVG.nvgFontSize((long)ctx, (float)size);
            NanoVG.nvgTextAlign((long)ctx, (int)68);
            NVGColor nVGColor = NVGColor.malloc((MemoryStack)stack);
            Intrinsics.checkNotNullExpressionValue((Object)nVGColor, (String)"malloc(...)");
            NanoVG.nvgFillColor((long)ctx, (NVGColor)INSTANCE.color(argb, nVGColor));
            float f = NanoVG.nvgText((long)ctx, (float)x, (float)INSTANCE.opticalBaseline(y), (CharSequence)string);
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            AutoCloseableKt.closeFinally((AutoCloseable)autoCloseable, (Throwable)throwable);
        }
    }

    public static /* synthetic */ void textRight$default(NVGRenderer nVGRenderer, String string, float f, float f2, float f3, int n, Font font, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            font = Font.REGULAR;
        }
        nVGRenderer.textRight(string, f, f2, f3, n, font);
    }

    public final float textWidth(@NotNull String string, float size, @NotNull Font font) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        Intrinsics.checkNotNullParameter((Object)((Object)font), (String)"font");
        NanoVG.nvgFontFace((long)ctx, (CharSequence)font.getId());
        NanoVG.nvgFontSize((long)ctx, (float)size);
        NanoVG.nvgTextAlign((long)ctx, (int)17);
        return NanoVG.nvgTextBounds((long)ctx, (float)0.0f, (float)0.0f, (CharSequence)string, null);
    }

    public static /* synthetic */ float textWidth$default(NVGRenderer nVGRenderer, String string, float f, Font font, int n, Object object) {
        if ((n & 4) != 0) {
            font = Font.REGULAR;
        }
        return nVGRenderer.textWidth(string, f, font);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void scissor(float x, float y, float w, float h, @NotNull Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(block, (String)"block");
        boolean $i$f$scissor = false;
        this.save();
        this.intersectScissor(x, y, w, h);
        try {
            block.invoke();
        }
        finally {
            InlineMarker.finallyStart((int)1);
            this.restore();
            InlineMarker.finallyEnd((int)1);
        }
    }

    public final void save() {
        NanoVG.nvgSave((long)ctx);
    }

    public final void restore() {
        NanoVG.nvgRestore((long)ctx);
    }

    public final void translate(float x, float y) {
        NanoVG.nvgTranslate((long)ctx, (float)x, (float)y);
    }

    public final void rotate(float angle) {
        NanoVG.nvgRotate((long)ctx, (float)angle);
    }

    public final void intersectScissor(float x, float y, float w, float h) {
        NanoVG.nvgIntersectScissor((long)ctx, (float)x, (float)y, (float)w, (float)h);
    }

    public final void globalAlpha(float alpha) {
        NanoVG.nvgGlobalAlpha((long)ctx, (float)RangesKt.coerceIn((float)alpha, (float)0.0f, (float)1.0f));
    }

    static {
        fontBuffers = new ArrayList(3);
        sColorMask = new byte[4];
        sViewport = new int[4];
        sScissorBox = new int[4];
        fAscender = new float[1];
        fDescender = new float[1];
        fLineH = new float[1];
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lcom/cb/render/NVGRenderer$Font;", "", "id", "", "resource", "<init>", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "getResource", "REGULAR", "MEDIUM", "BOLD", "cb_client"})
    public static final class Font
    extends Enum<Font> {
        @NotNull
        private final String id;
        @NotNull
        private final String resource;
        public static final /* enum */ Font REGULAR = new Font("jbm-regular", "/assets/cb/fonts/JetBrainsMono-Regular.ttf");
        public static final /* enum */ Font MEDIUM = new Font("jbm-medium", "/assets/cb/fonts/JetBrainsMono-Medium.ttf");
        public static final /* enum */ Font BOLD = new Font("jbm-bold", "/assets/cb/fonts/JetBrainsMono-Bold.ttf");
        private static final /* synthetic */ Font[] $VALUES;
        private static final /* synthetic */ EnumEntries $ENTRIES;

        private Font(String id, String resource) {
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

        public static Font[] values() {
            return (Font[])$VALUES.clone();
        }

        public static Font valueOf(String value) {
            return Enum.valueOf(Font.class, value);
        }

        @NotNull
        public static EnumEntries<Font> getEntries() {
            return $ENTRIES;
        }

        static {
            $VALUES = fontArray = new Font[]{Font.REGULAR, Font.MEDIUM, Font.BOLD};
            $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        }
    }
}


