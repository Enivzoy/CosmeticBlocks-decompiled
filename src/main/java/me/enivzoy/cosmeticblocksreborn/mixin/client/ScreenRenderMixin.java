package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.render.OverlayRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces the old {@code GpuSurface.present} hook. The GUI is now emitted as
 * part of the screen's own render-state extraction, so it works on every
 * backend (OpenGL and Vulkan).
 */
@Mixin(Screen.class)
public abstract class ScreenRenderMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void cb$renderNvg(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!((Object)this instanceof NvgScreen)) {
            return;
        }
        NVGRenderer.setExtractor(extractor);
        try {
            OverlayRenderer.onFrameEnd();
        } finally {
            NVGRenderer.clearExtractor();
        }
    }
}
