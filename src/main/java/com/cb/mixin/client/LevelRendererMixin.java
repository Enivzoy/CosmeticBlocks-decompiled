package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import com.cb.cosmetic.Ruler;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LevelRenderer.class})
public class LevelRendererMixin {
    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void onRenderLevel(GraphicsResourceAllocator graphicsResourceAllocator, DeltaTracker deltaTracker, boolean bl, CameraRenderState cameraRenderState, Matrix4fc modelView, GpuBufferSlice gpuBufferSlice, Vector4f vector4f, boolean bl2, CallbackInfo ci) {
        Matrix4f modelViewCopy = new Matrix4f(modelView);
        Ruler.INSTANCE.onWorldRender(modelViewCopy, cameraRenderState.projectionMatrix, cameraRenderState.pos);
        CosmeticBlocks.INSTANCE.onWorldRender(modelViewCopy, cameraRenderState.projectionMatrix, cameraRenderState.pos);
    }
}


