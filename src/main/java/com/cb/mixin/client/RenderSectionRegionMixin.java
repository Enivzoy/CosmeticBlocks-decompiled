/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.chunk.RenderSectionRegion
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={RenderSectionRegion.class})
public class RenderSectionRegionMixin {
    @Inject(method={"getBlockState"}, at={@At(value="RETURN")}, cancellable=true)
    private void cfme$swapBlockState(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState swapped;
        BlockState original = (BlockState)cir.getReturnValue();
        boolean isAir = original == null || original.isAir();
        BlockState replacement = CosmeticBlocks.getReplacementOrRemap(original, pos.getX(), pos.getY(), pos.getZ(), isAir);
        if (replacement != null) {
            BlockState result = CosmeticBlocks.INSTANCE.applyOriginalShape(original, replacement, pos.getX(), pos.getY(), pos.getZ());
            RenderSectionRegion region = (RenderSectionRegion)(Object)this;
            result = CosmeticBlocks.INSTANCE.updateConnections(result, pos.getX(), pos.getY(), pos.getZ(), (nx, ny, nz) -> region.getBlockState(new BlockPos(nx, ny, nz)));
            cir.setReturnValue(result);
            return;
        }
        if (!isAir && CosmeticBlocks.INSTANCE.hasActiveReplacements() && (original.getBlock() instanceof CrossCollisionBlock || original.getBlock() instanceof WallBlock) && CosmeticBlocks.INSTANCE.hasAdjacentReplacement(pos.getX(), pos.getY(), pos.getZ())) {
            RenderSectionRegion region = (RenderSectionRegion)(Object)this;
            BlockState updated = CosmeticBlocks.INSTANCE.updateConnections(original, pos.getX(), pos.getY(), pos.getZ(), (nx, ny, nz) -> region.getBlockState(new BlockPos(nx, ny, nz)));
            if (updated != original) {
                cir.setReturnValue(updated);
                return;
            }
        }
        if ((swapped = CosmeticBlocks.swapFluid(original)) != null) {
            cir.setReturnValue(swapped);
        }
    }
}

