/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.block.BlockStateModelSet
 *  net.minecraft.client.renderer.block.dispatch.BlockStateModel
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CFMEModelWrapper;
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={BlockStateModelSet.class})
public class BlockModelShaperMixin {
    @Inject(method={"get"}, at={@At(value="RETURN")}, cancellable=true)
    private void cfme$wrapModel(BlockState state, CallbackInfoReturnable<BlockStateModel> cir) {
        if (!CosmeticBlocks.INSTANCE.keepBlockSizeEnabled()) {
            return;
        }
        if (!CosmeticBlocks.INSTANCE.hasActiveReplacements()) {
            return;
        }
        BlockStateModel original = (BlockStateModel)cir.getReturnValue();
        if (original instanceof CFMEModelWrapper) {
            return;
        }
        cir.setReturnValue(new CFMEModelWrapper(original));
    }
}

