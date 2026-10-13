/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.pipeline.RenderPipeline
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.math.MathKt
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.renderer.ShapeRenderer
 *  net.minecraft.client.renderer.rendertype.LayeringTransform
 *  net.minecraft.client.renderer.rendertype.OutputTarget
 *  net.minecraft.client.renderer.rendertype.RenderSetup
 *  net.minecraft.client.renderer.rendertype.RenderType
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.utils;

import com.cb.utils.SkyblockerRenderPipelines;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bJn\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u00192\u0006\u0010\n\u001a\u00020\u0019Jf\u0010\u001d\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0019J\u0098\u0001\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020\u000b2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u000b2\u0006\u0010+\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\u0006\u00100\u001a\u00020.2\u0006\u00101\u001a\u00020.H\u0002JP\u00102\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020 2\u0006\u00103\u001a\u00020\u000b2\u0006\u00104\u001a\u00020\u000b2\u0006\u00105\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\u0006\u00100\u001a\u00020.2\u0006\u00101\u001a\u00020.H\u0002J(\u00106\u001a\u00020.2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0019H\u0002J\u0010\u00107\u001a\u00020.2\u0006\u00108\u001a\u00020\u0019H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00069"}, d2={"Lcom/cb/utils/RenderLayers;", "", "<init>", "()V", "FILLED_THROUGH_WALLS", "Lnet/minecraft/client/renderer/rendertype/RenderType;", "getFILLED_THROUGH_WALLS", "()Lnet/minecraft/client/renderer/rendertype/RenderType;", "LINES_THROUGH_WALLS", "getLinesThroughWalls", "lineWidth", "", "renderLineBox", "", "matrices", "Lcom/mojang/blaze3d/vertex/PoseStack;", "buffer", "Lcom/mojang/blaze3d/vertex/VertexConsumer;", "minX", "minY", "minZ", "maxX", "maxY", "maxZ", "red", "", "green", "blue", "alpha", "addFilledBoxVertices", "face", "pose", "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "x1", "y1", "z1", "x2", "y2", "z2", "x3", "y3", "z3", "x4", "y4", "z4", "r", "", "g", "b", "a", "vertex", "x", "y", "z", "argb", "channel", "value", "cb_client"})
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

    public final void renderLineBox(@NotNull PoseStack matrices, @NotNull VertexConsumer buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha, float lineWidth) {
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ShapeRenderer.renderShape((PoseStack)matrices, (VertexConsumer)buffer, (VoxelShape)Shapes.create((AABB)new AABB(minX, minY, minZ, maxX, maxY, maxZ)), (double)0.0, (double)0.0, (double)0.0, (int)this.argb(red, green, blue, alpha), (float)lineWidth);
    }

    public final void addFilledBoxVertices(@NotNull PoseStack matrices, @NotNull VertexConsumer buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
        Intrinsics.checkNotNullParameter((Object)matrices, (String)"matrices");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        PoseStack.Pose pose = matrices.last();
        Intrinsics.checkNotNullExpressionValue((Object)pose, (String)"last(...)");
        PoseStack.Pose pose2 = pose;
        int r = this.channel(red);
        int g = this.channel(green);
        int b = this.channel(blue);
        int a = this.channel(alpha);
        this.face(buffer, pose2, minX, minY, minZ, maxX, minY, minZ, maxX, maxY, minZ, minX, maxY, minZ, r, g, b, a);
        this.face(buffer, pose2, minX, minY, maxZ, minX, maxY, maxZ, maxX, maxY, maxZ, maxX, minY, maxZ, r, g, b, a);
        this.face(buffer, pose2, minX, minY, minZ, minX, maxY, minZ, minX, maxY, maxZ, minX, minY, maxZ, r, g, b, a);
        this.face(buffer, pose2, maxX, minY, minZ, maxX, minY, maxZ, maxX, maxY, maxZ, maxX, maxY, minZ, r, g, b, a);
        this.face(buffer, pose2, minX, maxY, minZ, maxX, maxY, minZ, maxX, maxY, maxZ, minX, maxY, maxZ, r, g, b, a);
        this.face(buffer, pose2, minX, minY, minZ, minX, minY, maxZ, maxX, minY, maxZ, maxX, minY, minZ, r, g, b, a);
    }

    private final void face(VertexConsumer buffer, PoseStack.Pose pose, double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, int r, int g, int b, int a) {
        this.vertex(buffer, pose, x1, y1, z1, r, g, b, a);
        this.vertex(buffer, pose, x2, y2, z2, r, g, b, a);
        this.vertex(buffer, pose, x3, y3, z3, r, g, b, a);
        this.vertex(buffer, pose, x4, y4, z4, r, g, b, a);
    }

    private final void vertex(VertexConsumer buffer, PoseStack.Pose pose, double x, double y, double z, int r, int g, int b, int a) {
        buffer.addVertex(pose, (float)x, (float)y, (float)z).setColor(r, g, b, a);
    }

    private final int argb(float red, float green, float blue, float alpha) {
        return this.channel(alpha) << 24 | this.channel(red) << 16 | this.channel(green) << 8 | this.channel(blue);
    }

    private final int channel(float value) {
        return MathKt.roundToInt((float)(RangesKt.coerceIn((float)value, (float)0.0f, (float)1.0f) * 255.0f));
    }

    static {
        RenderType renderType = RenderType.create((String)"cb_filled_through_walls", (RenderSetup)RenderSetup.builder((RenderPipeline)SkyblockerRenderPipelines.INSTANCE.getFILLED_THROUGH_WALLS()).sortOnUpload().setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).createRenderSetup());
        Intrinsics.checkNotNullExpressionValue((Object)renderType, (String)"create(...)");
        FILLED_THROUGH_WALLS = renderType;
        RenderType renderType2 = RenderType.create((String)"cb_lines_through_walls", (RenderSetup)RenderSetup.builder((RenderPipeline)SkyblockerRenderPipelines.INSTANCE.getLINES_THROUGH_WALLS()).setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING).setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).createRenderSetup());
        Intrinsics.checkNotNullExpressionValue((Object)renderType2, (String)"create(...)");
        LINES_THROUGH_WALLS = renderType2;
    }
}


