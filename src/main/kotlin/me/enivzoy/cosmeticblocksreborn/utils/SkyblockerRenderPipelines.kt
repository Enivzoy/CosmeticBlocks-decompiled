package me.enivzoy.cosmeticblocksreborn.utils

import com.mojang.blaze3d.PrimitiveTopology
import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.BindGroupLayout
import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.DepthStencilState
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.CompareOp
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.Identifier

object SkyblockerRenderPipelines {
   private val NO_DEPTH = DepthStencilState(CompareOp.ALWAYS_PASS, false)
   private val TRANSLUCENT = ColorTargetState(BlendFunction.TRANSLUCENT)

   val FILLED_THROUGH_WALLS: RenderPipeline
   val LINES_THROUGH_WALLS: RenderPipeline
   val ROUND_RECT: RenderPipeline

   init {
      FILLED_THROUGH_WALLS = RenderPipelines.register(
         RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/filled_through_walls"))
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .withDepthStencilState(NO_DEPTH)
            .withCull(false)
            .withColorTargetState(TRANSLUCENT)
            .build()
      )

      LINES_THROUGH_WALLS = RenderPipelines.register(
         RenderPipeline.builder(RenderPipelines.LINES_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/lines_through_walls"))
            .withDepthStencilState(NO_DEPTH)
            .build()
      )

      ROUND_RECT = RenderPipelines.register(
         RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath("cb", "pipeline/round_rect"))
            .withVertexShader(Identifier.fromNamespaceAndPath("cb", "core/round_rect"))
            .withFragmentShader(Identifier.fromNamespaceAndPath("cb", "core/round_rect"))
            .withColorTargetState(TRANSLUCENT)
            .withBindGroupLayout(BindGroupLayout.builder().withUniform("u", UniformType.UNIFORM_BUFFER).build())
            .withVertexBinding(0, DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            .build()
      )
   }
}
