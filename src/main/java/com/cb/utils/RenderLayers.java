package com.cb.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;

public final class RenderLayers {
   @NotNull
   public static final RenderLayers INSTANCE = new RenderLayers();
   @NotNull
   private static final RenderType FILLED_THROUGH_WALLS;
   @NotNull
   private static final RenderType LINES_THROUGH_WALLS;

   private RenderLayers() {
   }

   @NotNull
   public final RenderType getFILLED_THROUGH_WALLS() {
      return FILLED_THROUGH_WALLS;
   }

   @NotNull
   public final RenderType getLinesThroughWalls(double lineWidth) {
      return LINES_THROUGH_WALLS;
   }

   public final void renderLineBox(
      @NotNull PoseStack matrices,
      @NotNull VertexConsumer buffer,
      double minX,
      double minY,
      double minZ,
      double maxX,
      double maxY,
      double maxZ,
      float red,
      float green,
      float blue,
      float alpha,
      float lineWidth
   ) {
      Intrinsics.checkNotNullParameter(matrices, "matrices");
      Intrinsics.checkNotNullParameter(buffer, "buffer");
      Pose pose = matrices.last();
      int r = this.channel(red);
      int g = this.channel(green);
      int b = this.channel(blue);
      int a = this.channel(alpha);
      this.line(buffer, pose, minX, minY, minZ, maxX, minY, minZ, r, g, b, a);
      this.line(buffer, pose, maxX, minY, minZ, maxX, minY, maxZ, r, g, b, a);
      this.line(buffer, pose, maxX, minY, maxZ, minX, minY, maxZ, r, g, b, a);
      this.line(buffer, pose, minX, minY, maxZ, minX, minY, minZ, r, g, b, a);
      this.line(buffer, pose, minX, maxY, minZ, maxX, maxY, minZ, r, g, b, a);
      this.line(buffer, pose, maxX, maxY, minZ, maxX, maxY, maxZ, r, g, b, a);
      this.line(buffer, pose, maxX, maxY, maxZ, minX, maxY, maxZ, r, g, b, a);
      this.line(buffer, pose, minX, maxY, maxZ, minX, maxY, minZ, r, g, b, a);
      this.line(buffer, pose, minX, minY, minZ, minX, maxY, minZ, r, g, b, a);
      this.line(buffer, pose, maxX, minY, minZ, maxX, maxY, minZ, r, g, b, a);
      this.line(buffer, pose, maxX, minY, maxZ, maxX, maxY, maxZ, r, g, b, a);
      this.line(buffer, pose, minX, minY, maxZ, minX, maxY, maxZ, r, g, b, a);
   }

   private final void line(
      VertexConsumer buffer,
      Pose pose,
      double x1,
      double y1,
      double z1,
      double x2,
      double y2,
      double z2,
      int r,
      int g,
      int b,
      int a
   ) {
      this.vertex(buffer, pose, x1, y1, z1, r, g, b, a);
      this.vertex(buffer, pose, x2, y2, z2, r, g, b, a);
   }

   public final void addFilledBoxVertices(
      @NotNull PoseStack matrices,
      @NotNull VertexConsumer buffer,
      double minX,
      double minY,
      double minZ,
      double maxX,
      double maxY,
      double maxZ,
      float red,
      float green,
      float blue,
      float alpha
   ) {
      Intrinsics.checkNotNullParameter(matrices, "matrices");
      Intrinsics.checkNotNullParameter(buffer, "buffer");
      Pose pose = matrices.last();
      Intrinsics.checkNotNullExpressionValue(pose, "last(...)");
      int r = this.channel(red);
      int g = this.channel(green);
      int b = this.channel(blue);
      int a = this.channel(alpha);
      this.face(buffer, pose, minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, r, g, b, a);
      this.face(buffer, pose, minX, minY, maxZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, r, g, b, a);
      this.face(buffer, pose, minX, minY, minZ, minX, maxY, minZ, minX, maxY, maxZ, minX, minY, maxZ, r, g, b, a);
      this.face(buffer, pose, maxX, minY, minZ, maxX, minY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, r, g, b, a);
      this.face(buffer, pose, minX, maxY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, minX, maxY, maxZ, r, g, b, a);
      this.face(buffer, pose, minX, minY, minZ, minX, minY, maxZ, maxX, minY, maxZ, maxX, minY, minZ, r, g, b, a);
   }

   private final void face(
      VertexConsumer buffer,
      Pose pose,
      double x1,
      double y1,
      double z1,
      double x2,
      double y2,
      double z2,
      double x3,
      double y3,
      double z3,
      double x4,
      double y4,
      double z4,
      int r,
      int g,
      int b,
      int a
   ) {
      this.vertex(buffer, pose, x1, y1, z1, r, g, b, a);
      this.vertex(buffer, pose, x2, y2, z2, r, g, b, a);
      this.vertex(buffer, pose, x3, y3, z3, r, g, b, a);
      this.vertex(buffer, pose, x4, y4, z4, r, g, b, a);
   }

   private final void vertex(VertexConsumer buffer, Pose pose, double x, double y, double z, int r, int g, int b, int a) {
      buffer.addVertex(pose, (float)x, (float)y, (float)z).setColor(r, g, b, a);
   }

   private final int argb(float red, float green, float blue, float alpha) {
      return this.channel(alpha) << 24 | this.channel(red) << 16 | this.channel(green) << 8 | this.channel(blue);
   }

   private final int channel(float value) {
      return MathKt.roundToInt(RangesKt.coerceIn(value, 0.0F, 1.0F) * 255.0F);
   }

   static {
      RenderType filled = RenderType.create(
         "cb_filled_through_walls",
         RenderSetup.builder(SkyblockerRenderPipelines.INSTANCE.getFILLED_THROUGH_WALLS())
            .sortOnUpload()
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .createRenderSetup()
      );
      Intrinsics.checkNotNullExpressionValue(filled, "create(...)");
      FILLED_THROUGH_WALLS = filled;
      RenderType lines = RenderType.create(
         "cb_lines_through_walls",
         RenderSetup.builder(SkyblockerRenderPipelines.INSTANCE.getLINES_THROUGH_WALLS())
            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
            .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
            .createRenderSetup()
      );
      Intrinsics.checkNotNullExpressionValue(lines, "create(...)");
      LINES_THROUGH_WALLS = lines;
   }
}
