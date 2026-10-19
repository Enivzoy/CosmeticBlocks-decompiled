package me.enivzoy.cosmeticblocksreborn.render

import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen
import net.minecraft.client.Minecraft
import org.slf4j.LoggerFactory

object OverlayRenderer {
   private val logger = LoggerFactory.getLogger("cb/overlay")
   private var lastFrameNanos = 0L
   private var crashed = false

   @JvmStatic
   fun onFrameEnd() {
      val minecraft = Minecraft.getInstance() ?: return
      val screen = minecraft.gui.screen()
      val nvgScreen = screen as? NvgScreen
      if (nvgScreen == null) {
         lastFrameNanos = 0L
         return
      }

      if (crashed) {
         return
      }

      val now = System.nanoTime()
      val delta = if (lastFrameNanos == 0L) 0.0f else (((now - lastFrameNanos).toDouble() / 1.0E9).toFloat()).coerceIn(0.0f, 0.1f)
      lastFrameNanos = now
      try {
         nvgScreen.renderOverlay(delta)
      } catch (t: Throwable) {
         crashed = true
         logger.error("GUI overlay rendering failed \u2014 disabling overlay", t)
      }
   }
}
