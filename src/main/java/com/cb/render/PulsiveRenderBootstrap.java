package com.cb.render;

import net.minecraft.client.renderer.RenderPipelines;
import renderkit.PulsivePipelines;

/**
 * Registers the blaze3d-renderkit GUI pipelines/metadata that the vendored copy
 * builds but never registers on its own. Must run during client initialisation,
 * before any screen draws.
 */
public final class PulsiveRenderBootstrap {
   private PulsiveRenderBootstrap() {
   }

   public static void register() {
      RenderPipelines.register(PulsivePipelines.ROUNDED_RECT);
      RenderPipelines.register(PulsivePipelines.ROUNDED_RECT_ASYM);
      RenderPipelines.register(PulsivePipelines.ROUNDED_OUTLINE);
      RenderPipelines.register(PulsivePipelines.TEXT);
      PulsivePipelines.markReady();
   }
}
