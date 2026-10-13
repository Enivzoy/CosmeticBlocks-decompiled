package com.cb.gui.hud;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class HudRenderer {
   @NotNull
   public static final HudRenderer INSTANCE = new HudRenderer();

   private HudRenderer() {
   }

   public final void register() {
      HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("cb", "hud"), HudRenderer::onRenderHud);
   }

   private static final void onRenderHud(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
      Intrinsics.checkNotNullParameter(graphics, "graphics");
      Intrinsics.checkNotNullParameter(deltaTracker, "deltaTracker");
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      HudManager.INSTANCE.render(graphics, mc, deltaTracker.getRealtimeDeltaTicks());
   }
}
