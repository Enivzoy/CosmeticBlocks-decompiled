package me.enivzoy.cosmeticblocksreborn.utils

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.PoseStack.Pose
import com.mojang.blaze3d.vertex.VertexConsumer
import kotlin.math.roundToInt
import net.minecraft.client.renderer.rendertype.LayeringTransform
import net.minecraft.client.renderer.rendertype.OutputTarget
import net.minecraft.client.renderer.rendertype.RenderSetup
import net.minecraft.client.renderer.rendertype.RenderType

object RenderLayers {
   val FILLED_THROUGH_WALLS: RenderType
   private val LINES_THROUGH_WALLS: RenderType

   init {
      FILLED_THROUGH_WALLS = RenderType.create(
         "cb_filled_through_walls",
         RenderSetup.builder(SkyblockerRenderPipelines.FILLED_THROUGH_WALLS)
            .sortOnUpload()
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .createRenderSetup()
      )

      LINES_THROUGH_WALLS = RenderType.create(
         "cb_lines_through_walls",
         RenderSetup.builder(SkyblockerRenderPipelines.LINES_THROUGH_WALLS)
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
            .createRenderSetup()
      )
   }

   fun getLinesThroughWalls(lineWidth: Double): RenderType = LINES_THROUGH_WALLS

   fun renderLineBox(
      matrices: PoseStack,
      buffer: VertexConsumer,
      minX: Double,
      minY: Double,
      minZ: Double,
      maxX: Double,
      maxY: Double,
      maxZ: Double,
      red: Float,
      green: Float,
      blue: Float,
      alpha: Float,
      lineWidth: Float
   ) {
      val pose = matrices.last()
      val r = channel(red)
      val g = channel(green)
      val b = channel(blue)
      val a = channel(alpha)
      line(buffer, pose, minX, minY, minZ, maxX, minY, minZ, r, g, b, a)
      line(buffer, pose, maxX, minY, minZ, maxX, minY, maxZ, r, g, b, a)
      line(buffer, pose, maxX, minY, maxZ, minX, minY, maxZ, r, g, b, a)
      line(buffer, pose, minX, minY, maxZ, minX, minY, minZ, r, g, b, a)
      line(buffer, pose, minX, maxY, minZ, maxX, maxY, minZ, r, g, b, a)
      line(buffer, pose, maxX, maxY, minZ, maxX, maxY, maxZ, r, g, b, a)
      line(buffer, pose, maxX, maxY, maxZ, minX, maxY, maxZ, r, g, b, a)
      line(buffer, pose, minX, maxY, maxZ, minX, maxY, minZ, r, g, b, a)
      line(buffer, pose, minX, minY, minZ, minX, maxY, minZ, r, g, b, a)
      line(buffer, pose, maxX, minY, minZ, maxX, maxY, minZ, r, g, b, a)
      line(buffer, pose, maxX, minY, maxZ, maxX, maxY, maxZ, r, g, b, a)
      line(buffer, pose, minX, minY, maxZ, minX, maxY, maxZ, r, g, b, a)
   }

   private fun line(
      buffer: VertexConsumer,
      pose: Pose,
      x1: Double,
      y1: Double,
      z1: Double,
      x2: Double,
      y2: Double,
      z2: Double,
      r: Int,
      g: Int,
      b: Int,
      a: Int
   ) {
      vertex(buffer, pose, x1, y1, z1, r, g, b, a)
      vertex(buffer, pose, x2, y2, z2, r, g, b, a)
   }

   fun addFilledBoxVertices(
      matrices: PoseStack,
      buffer: VertexConsumer,
      minX: Double,
      minY: Double,
      minZ: Double,
      maxX: Double,
      maxY: Double,
      maxZ: Double,
      red: Float,
      green: Float,
      blue: Float,
      alpha: Float
   ) {
      val pose = matrices.last()
      val r = channel(red)
      val g = channel(green)
      val b = channel(blue)
      val a = channel(alpha)
      face(buffer, pose, minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, r, g, b, a)
      face(buffer, pose, minX, minY, maxZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, r, g, b, a)
      face(buffer, pose, minX, minY, minZ, minX, maxY, minZ, minX, maxY, maxZ, minX, minY, maxZ, r, g, b, a)
      face(buffer, pose, maxX, minY, minZ, maxX, minY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, r, g, b, a)
      face(buffer, pose, minX, maxY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, minX, maxY, maxZ, r, g, b, a)
      face(buffer, pose, minX, minY, minZ, minX, minY, maxZ, maxX, minY, maxZ, maxX, minY, minZ, r, g, b, a)
   }

   private fun face(
      buffer: VertexConsumer,
      pose: Pose,
      x1: Double,
      y1: Double,
      z1: Double,
      x2: Double,
      y2: Double,
      z2: Double,
      x3: Double,
      y3: Double,
      z3: Double,
      x4: Double,
      y4: Double,
      z4: Double,
      r: Int,
      g: Int,
      b: Int,
      a: Int
   ) {
      vertex(buffer, pose, x1, y1, z1, r, g, b, a)
      vertex(buffer, pose, x2, y2, z2, r, g, b, a)
      vertex(buffer, pose, x3, y3, z3, r, g, b, a)
      vertex(buffer, pose, x4, y4, z4, r, g, b, a)
   }

   private fun vertex(buffer: VertexConsumer, pose: Pose, x: Double, y: Double, z: Double, r: Int, g: Int, b: Int, a: Int) {
      buffer.addVertex(pose, x.toFloat(), y.toFloat(), z.toFloat()).setColor(r, g, b, a)
   }

   private fun argb(red: Float, green: Float, blue: Float, alpha: Float): Int =
      channel(alpha) shl 24 or (channel(red) shl 16) or (channel(green) shl 8) or channel(blue)

   private fun channel(value: Float): Int = (value.coerceIn(0.0F, 1.0F) * 255.0F).roundToInt()
}
