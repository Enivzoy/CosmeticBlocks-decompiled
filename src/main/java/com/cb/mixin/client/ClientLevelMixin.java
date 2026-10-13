/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={ClientLevel.class})
public class ClientLevelMixin {
    @ModifyVariable(method={"addDestroyBlockEffect"}, at=@At(value="HEAD"), argsOnly=true)
    private BlockState cfme$swapDestroyParticleState(BlockState state, BlockPos pos, BlockState stateArg) {
        BlockState replacement = CosmeticBlocks.getEffectReplacement(pos, state);
        return replacement != null ? replacement : state;
    }

    @Redirect(method={"addBreakingBlockEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/ClientLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState cfme$swapCrackParticleState(ClientLevel level, BlockPos pos, BlockPos posArg, Direction side) {
        BlockState world = level.getBlockState(pos);
        BlockState replacement = CosmeticBlocks.getEffectReplacement(pos, world);
        return replacement != null ? replacement : world;
    }
}


