package com.cb.mixin.client;

import com.cb.cosmetic.Freecam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftCompatMixin {
    public Screen screen;

    @Shadow
    public HitResult hitResult;

    @Shadow
    public Entity crosshairPickEntity;

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

    @Inject(method = "pick", at = @At("TAIL"))
    private void cb$freecamPick(float partialTicks, CallbackInfo ci) {
        if (!Freecam.INSTANCE.isActive()) {
            return;
        }
        HitResult result = Freecam.INSTANCE.raycast();
        if (result != null) {
            this.hitResult = result;
            this.crosshairPickEntity = result instanceof EntityHitResult entityHit ? entityHit.getEntity() : null;
        }
    }
}
