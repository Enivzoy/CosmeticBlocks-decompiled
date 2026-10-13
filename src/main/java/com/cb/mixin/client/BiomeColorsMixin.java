/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.BiomeColors
 *  net.minecraft.client.renderer.block.BlockAndTintGetter
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.ColorResolver
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BiomeColors.class})
public class BiomeColorsMixin {
    @Inject(method={"getAverageColor"}, at={@At(value="HEAD")}, cancellable=true)
    private static void cfme$customWaterColor(BlockAndTintGetter level, BlockPos pos, ColorResolver resolver, CallbackInfoReturnable<Integer> cir) {
        if (resolver == BiomeColors.WATER_COLOR_RESOLVER && CosmeticBlocks.INSTANCE.customWaterColorEnabled()) {
            cir.setReturnValue(CosmeticBlocks.INSTANCE.waterColorRgb());
        }
    }
}

