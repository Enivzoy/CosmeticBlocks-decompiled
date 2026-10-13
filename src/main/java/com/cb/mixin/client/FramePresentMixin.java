package com.cb.mixin.client;

import com.cb.render.OverlayRenderer;
import com.mojang.blaze3d.systems.GpuSurface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GpuSurface.class)
public abstract class FramePresentMixin {
    @Inject(method = "present", at = @At("HEAD"))
    private void cb$renderOverlay(CallbackInfo ci) {
        OverlayRenderer.onFrameEnd();
    }
}
