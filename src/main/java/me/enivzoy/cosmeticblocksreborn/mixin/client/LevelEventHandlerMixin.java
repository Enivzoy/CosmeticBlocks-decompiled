/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.LevelEventHandler
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Redirect
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value={LevelEventHandler.class})
public class LevelEventHandlerMixin {
    @Redirect(method={"levelEvent"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/level/block/state/BlockState;getSoundType()Lnet/minecraft/world/level/block/SoundType;"))
    private SoundType cfme$swapBreakSound(BlockState state, int type2, BlockPos pos, int data) {
        BlockState replacement = CosmeticBlocks.getEffectReplacement(pos, state);
        return replacement != null ? replacement.getSoundType() : state.getSoundType();
    }
}


