/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Block.class})
public class BlockFaceCullingMixin {
    @Inject(method={"shouldRenderFace"}, at={@At(value="HEAD")}, cancellable=true)
    private static void cfme$fixStairCulling(BlockState adjacentState, BlockState thisState, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (!CosmeticBlocks.INSTANCE.hasActiveReplacements()) {
            return;
        }
        VoxelShape adjShape = thisState.getFaceOcclusionShape(direction.getOpposite());
        VoxelShape selfShape = adjacentState.getFaceOcclusionShape(direction);
        if (adjShape != Shapes.block() || selfShape != Shapes.block()) {
            cir.setReturnValue(true);
        }
    }
}

