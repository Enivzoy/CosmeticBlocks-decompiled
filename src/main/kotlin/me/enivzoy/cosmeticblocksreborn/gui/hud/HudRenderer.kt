package me.enivzoy.cosmeticblocksreborn.gui.hud

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.minecraft.client.Minecraft
import net.minecraft.resources.Identifier

object HudRenderer {
    fun register() {
        HudManager.registerElement(DebugOverlayElement())
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("cb", "hud")) { graphics, deltaTracker ->
            val mc = Minecraft.getInstance()
            HudManager.render(graphics, mc, deltaTracker.realtimeDeltaTicks)
        }
    }
}
