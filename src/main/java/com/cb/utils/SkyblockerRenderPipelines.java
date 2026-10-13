package com.cb.utils;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.BindGroupLayout;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.PrimitiveTopology;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

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
      Snippet[] filledSnippets = new Snippet[]{RenderPipelines.DEBUG_FILLED_SNIPPET};
      RenderPipeline filled = RenderPipelines.register(
         RenderPipeline.builder(filledSnippets)
             .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/filled_through_walls"))
             .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
             .withPrimitiveTopology(PrimitiveTopology.QUADS)
             .withDepthStencilState(NO_DEPTH)
            .withCull(false)
            .withColorTargetState(TRANSLUCENT)
            .build()
      );
      Intrinsics.checkNotNullExpressionValue(filled, "register(...)");
      FILLED_THROUGH_WALLS = filled;
      Snippet[] lineSnippets = new Snippet[]{RenderPipelines.LINES_SNIPPET};
      RenderPipeline lines = RenderPipelines.register(
         RenderPipeline.builder(lineSnippets)
            .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/lines_through_walls"))
            .withDepthStencilState(NO_DEPTH)
            .build()
      );
      Intrinsics.checkNotNullExpressionValue(lines, "register(...)");
      LINES_THROUGH_WALLS = lines;
      Snippet[] roundRectSnippets = new Snippet[]{RenderPipelines.DEBUG_FILLED_SNIPPET};
      RenderPipeline roundRect = RenderPipelines.register(
         RenderPipeline.builder(roundRectSnippets)
            .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/round_rect"))
            .withVertexShader(Identifier.fromNamespaceAndPath("cb", "core/round_rect"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cb", "core/round_rect"))
            .withColorTargetState(TRANSLUCENT)
            .withBindGroupLayout(BindGroupLayout.builder().withUniform("u", UniformType.UNIFORM_BUFFER).build())
            .withVertexBinding(0, DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .build()
      );
      Intrinsics.checkNotNullExpressionValue(roundRect, "register(...)");
      ROUND_RECT = roundRect;
   }
}
