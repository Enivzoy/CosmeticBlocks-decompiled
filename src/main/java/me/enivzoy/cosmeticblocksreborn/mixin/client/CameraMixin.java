package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.Freecam;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract void setPosition(double x, double y, double z);

    @Shadow
    protected abstract void setRotation(float yRot, float xRot);

    @Inject(method = "alignWithEntity", at = @At("TAIL"))
    private void cb$applyFreecam(float partialTicks, CallbackInfo ci) {
        Freecam freecam = Freecam.INSTANCE;
        if (!freecam.isActive()) {
            return;
        }
        freecam.onFrame(Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks());
        setPosition(freecam.getX(), freecam.getY(), freecam.getZ());
        setRotation(freecam.getYaw(), freecam.getPitch());
    }
}
