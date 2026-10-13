package com.cb.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftCompatMixin {
    public Screen screen;

    public void setScreen(Screen screen) {
        this.screen = screen;
        ((Minecraft)(Object)this).setScreenAndShow(screen);
    }

    @Inject(method = "setScreenAndShow", at = @At("HEAD"))
    private void cb$syncScreenOnSet(Screen screen, CallbackInfo ci) {
        this.screen = screen;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void cb$syncScreenOnTick(CallbackInfo ci) {
        this.screen = ((Minecraft)(Object)this).gui.screen();
    }
}
