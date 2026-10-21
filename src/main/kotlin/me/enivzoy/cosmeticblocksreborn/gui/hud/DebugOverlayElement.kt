package me.enivzoy.cosmeticblocksreborn.gui.hud

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor

class DebugOverlayElement : HudElement("debug_overlay", 4, 4, 220, 120) {
   override fun render(graphics: GuiGraphicsExtractor, mc: Minecraft, partialTicks: Float) {
      if (!DebugStats.enabled) {
         return
      }

      val lines = DebugStats.lines()
      val font = mc.font
      val lineH = 10
      val height = lines.size * lineH + 8
      val width = 220
      graphics.fill(x, y, x + width, y + height, 0xC0000000.toInt())
      graphics.fill(x, y, x + width, y + 1, 0xFF55FF55.toInt())
      var cy = y + 4
      for (line in lines) {
         graphics.text(font, line, x + 4, cy, 0xFFE0E0E0.toInt())
         cy += lineH
      }
   }
}
