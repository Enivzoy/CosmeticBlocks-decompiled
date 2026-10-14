package com.cb.mixin.client;

import com.cb.cosmetic.Freecam;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientInput.class)
public abstract class ClientInputMixin {
    @Inject(method = "getMoveVector", at = @At("HEAD"), cancellable = true)
    private void cb$freecamFreezeMove(CallbackInfoReturnable<Vec2> cir) {
        if (Freecam.INSTANCE.isActive()) {
            cir.setReturnValue(Vec2.ZERO);
        }
    }
}
