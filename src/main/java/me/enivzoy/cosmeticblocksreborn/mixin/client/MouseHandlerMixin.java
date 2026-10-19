/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.MouseHandler
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.Freecam;
import me.enivzoy.cosmeticblocksreborn.cosmetic.Ruler;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MouseHandler.class})
public class MouseHandlerMixin {
    @Shadow
    private double accumulatedDX;

    @Shadow
    private double accumulatedDY;

    @Inject(method={"onScroll"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseScroll(long window, double scrollX, double scrollY, CallbackInfo ci) {
        if (Ruler.INSTANCE.getActive() && Ruler.INSTANCE.onScroll(scrollY)) {
            ci.cancel();
        }
    }

    @Inject(method={"turnPlayer"}, at={@At(value="HEAD")}, cancellable=true)
    private void onTurnPlayer(double mousea, CallbackInfo ci) {
        if (Freecam.INSTANCE.isActive()) {
            Freecam.INSTANCE.addLook(this.accumulatedDX, this.accumulatedDY);
            ci.cancel();
        }
    }
}


