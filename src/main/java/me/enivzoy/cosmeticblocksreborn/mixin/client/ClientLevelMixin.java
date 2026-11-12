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
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
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

    /**
     * Swaps the block state used for the breaking (crack) particles and for the
     * hit sound of a block that is being destroyed.
     * <p>
     * Since 26.3 the vanilla method is {@code addBreakingBlockEffects(BlockPos, Direction, boolean)}
     * and it reads the state through {@code getBlockState} before spawning the particles and before
     * playing the hit sound, so redirecting that single call covers both.
     * <p>
     * The handler signature follows the mixin redirect convention: the owner of the redirected call
     * first, then the arguments of the redirected call, then the arguments of the target method.
     */
    @Redirect(method={"addBreakingBlockEffects"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/multiplayer/ClientLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState cfme$swapCrackParticleState(ClientLevel level, BlockPos posArg, BlockPos pos, Direction side, boolean playSound) {
        BlockState world = level.getBlockState(pos);
        BlockState replacement = CosmeticBlocks.getEffectReplacement(pos, world);
        return replacement != null ? replacement : world;
    }
}
