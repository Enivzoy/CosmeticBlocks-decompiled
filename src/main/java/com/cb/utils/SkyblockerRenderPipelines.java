/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.BlendFunction
 *  com.mojang.blaze3d.pipeline.ColorTargetState
 *  com.mojang.blaze3d.pipeline.DepthStencilState
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.pipeline.RenderPipeline$Snippet
 *  com.mojang.blaze3d.platform.CompareOp
 *  com.mojang.blaze3d.shaders.UniformType
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.resources.Identifier
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.utils;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cb/utils/SkyblockerRenderPipelines;", "", "<init>", "()V", "NO_DEPTH", "Lcom/mojang/blaze3d/pipeline/DepthStencilState;", "TRANSLUCENT", "Lcom/mojang/blaze3d/pipeline/ColorTargetState;", "FILLED_THROUGH_WALLS", "Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "getFILLED_THROUGH_WALLS", "()Lcom/mojang/blaze3d/pipeline/RenderPipeline;", "LINES_THROUGH_WALLS", "getLINES_THROUGH_WALLS", "ROUND_RECT", "getROUND_RECT", "cb_client"})
public final class SkyblockerRenderPipelines {
    @NotNull
    public static final SkyblockerRenderPipelines INSTANCE = new SkyblockerRenderPipelines();
    @NotNull
    private static final DepthStencilState NO_DEPTH = new DepthStencilState(CompareOp.ALWAYS_PASS, false);
    @NotNull
    private static final ColorTargetState TRANSLUCENT = new ColorTargetState(BlendFunction.TRANSLUCENT);
    @NotNull
    private static final RenderPipeline FILLED_THROUGH_WALLS;
    @NotNull
    private static final RenderPipeline LINES_THROUGH_WALLS;
    @NotNull
    private static final RenderPipeline ROUND_RECT;

    private SkyblockerRenderPipelines() {
    }

    @NotNull
    public final RenderPipeline getFILLED_THROUGH_WALLS() {
        return FILLED_THROUGH_WALLS;
    }

    @NotNull
    public final RenderPipeline getLINES_THROUGH_WALLS() {
        return LINES_THROUGH_WALLS;
    }

    @NotNull
    public final RenderPipeline getROUND_RECT() {
        return ROUND_RECT;
    }

    static {
        RenderPipeline.Snippet[] snippetArray = new RenderPipeline.Snippet[]{RenderPipelines.DEBUG_FILLED_SNIPPET};
        RenderPipeline renderPipeline = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])snippetArray).withLocation(Identifier.fromNamespaceAndPath((String)"cb", (String)"pipeline/filled_through_walls")).withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS).withDepthStencilState(NO_DEPTH).withCull(false).withColorTargetState(TRANSLUCENT).build());
        Intrinsics.checkNotNullExpressionValue((Object)renderPipeline, (String)"register(...)");
        FILLED_THROUGH_WALLS = renderPipeline;
        snippetArray = new RenderPipeline.Snippet[]{RenderPipelines.LINES_SNIPPET};
        RenderPipeline renderPipeline2 = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])snippetArray).withLocation(Identifier.fromNamespaceAndPath((String)"cb", (String)"pipeline/lines_through_walls")).withDepthStencilState(NO_DEPTH).build());
        Intrinsics.checkNotNullExpressionValue((Object)renderPipeline2, (String)"register(...)");
        LINES_THROUGH_WALLS = renderPipeline2;
        snippetArray = new RenderPipeline.Snippet[]{RenderPipelines.DEBUG_FILLED_SNIPPET};
        RenderPipeline renderPipeline3 = RenderPipelines.register((RenderPipeline)RenderPipeline.builder((RenderPipeline.Snippet[])snippetArray).withLocation(Identifier.fromNamespaceAndPath((String)"cb", (String)"pipeline/round_rect")).withVertexShader(Identifier.fromNamespaceAndPath((String)"cb", (String)"core/round_rect")).withFragmentShader(Identifier.fromNamespaceAndPath((String)"cb", (String)"core/round_rect")).withColorTargetState(TRANSLUCENT).withUniform("u", UniformType.UNIFORM_BUFFER).withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS).build());
        Intrinsics.checkNotNullExpressionValue((Object)renderPipeline3, (String)"register(...)");
        ROUND_RECT = renderPipeline3;
    }
}


