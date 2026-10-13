package com.cb.gui.hud;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006",
   d2 = {"Lcom/cb/gui/hud/HudRenderer;", "", "<init>", "()V", "register", "", "cb_client"}
)
public final class HudRenderer {
   @NotNull
   public static final HudRenderer INSTANCE = new HudRenderer();

   private HudRenderer() {
   }

   public final void register() {
      HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("cb", "hud"), HudRenderer::register$lambda$0);
   }

   private static final void register$lambda$0(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
      Intrinsics.checkNotNullParameter(graphics, "graphics");
      Intrinsics.checkNotNullParameter(deltaTracker, "deltaTracker");
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft mc = var10000;
      HudManager.INSTANCE.render(graphics, mc, deltaTracker.getRealtimeDeltaTicks());
   }
}
