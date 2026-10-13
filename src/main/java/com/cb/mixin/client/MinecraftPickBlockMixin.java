/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Minecraft.class})
public class MinecraftPickBlockMixin {
    @Inject(method={"pickBlockOrEntity"}, at={@At(value="HEAD")}, cancellable=true)
    private void cb$pickCosmeticBlock(CallbackInfo ci) {
        if (CosmeticBlocks.INSTANCE.tryPickCosmeticAtCrosshair()) {
            ci.cancel();
        }
    }
}


