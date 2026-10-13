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
package com.cb.mixin.client;

import com.cb.cosmetic.Ruler;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MouseHandler.class})
public class MouseHandlerMixin {
    @Inject(method={"onScroll"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseScroll(long window, double scrollX, double scrollY, CallbackInfo ci) {
        if (Ruler.INSTANCE.getActive() && Ruler.INSTANCE.onScroll(scrollY)) {
            ci.cancel();
        }
    }
}


