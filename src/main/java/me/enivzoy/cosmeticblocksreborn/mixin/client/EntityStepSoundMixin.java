/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={Entity.class})
public class EntityStepSoundMixin {
    @ModifyVariable(method={"playStepSound"}, at=@At(value="HEAD"), argsOnly=true)
    private BlockState cfme$swapStepSoundState(BlockState state, BlockPos pos, BlockState stateArg) {
        Entity self = (Entity)(Object)this;
        if (!self.level().isClientSide()) {
            return state;
        }
        BlockState replacement = CosmeticBlocks.getEffectReplacement(pos, state);
        return replacement != null ? replacement : state;
    }
}

