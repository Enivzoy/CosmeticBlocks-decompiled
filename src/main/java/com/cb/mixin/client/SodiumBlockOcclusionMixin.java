/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.IronBarsBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets={"net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockOcclusionCache"}, remap=false)
public class SodiumBlockOcclusionMixin {
    @Inject(method={"shouldDrawSide(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;)Z"}, at={@At(value="HEAD")}, cancellable=true, require=0, remap=true)
    private void cfme$fixStairCulling(BlockState state, BlockGetter view, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!CosmeticBlocks.INSTANCE.hasActiveReplacements()) {
            return;
        }
        BlockState adjState = view.getBlockState(pos.relative(direction));
        if (state.skipRendering(adjState, direction)) {
            boolean adjIsGlassStair;
            BlockPos adjPos = pos.relative(direction);
            boolean selfIsGlassStair = CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.getX(), pos.getY(), pos.getZ()) != null;
            boolean bl = adjIsGlassStair = CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjPos.getX(), adjPos.getY(), adjPos.getZ()) != null;
            if (adjIsGlassStair && !selfIsGlassStair) {
                cir.setReturnValue(true);
                return;
            }
            cir.setReturnValue(false);
            return;
        }
        if (state.getBlock() == adjState.getBlock() && state.getBlock() instanceof IronBarsBlock) {
            BlockPos adjPos = pos.relative(direction);
            if (CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.getX(), pos.getY(), pos.getZ()) != null || CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjPos.getX(), adjPos.getY(), adjPos.getZ()) != null) {
                cir.setReturnValue(true);
                return;
            }
            cir.setReturnValue(false);
            return;
        }
        VoxelShape adjShape = adjState.getFaceOcclusionShape(direction.getOpposite());
        if (adjShape != Shapes.block()) {
            cir.setReturnValue(true);
        }
    }
}

