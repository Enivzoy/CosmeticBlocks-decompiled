package me.enivzoy.cosmeticblocksreborn.utils

import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor

object ChatUtil {
   private const val PREFIX_COLOR = 7231183

   private val colorCodeRegex = Regex("&([0-9a-fk-or])")

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   private fun translateColorCodes(message: String): String =
      colorCodeRegex.replace(message, "§$1")

   private fun buildMessage(message: String, color: Int): Component {
      val result = Component.empty()
      result.append(Component.literal("CB ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(PREFIX_COLOR))))
      result.append(Component.literal(colorCodeRegex.replace(message, "§$1")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))))
      return result
   }

   private fun sendRaw(component: Component) {
      val player = this.mc.player ?: return
      player.sendSystemMessage(component)
   }

   fun send(message: String) {
      this.sendRaw(this.buildMessage(message, 16777215))
   }

   fun info(message: String) {
      this.send(message)
   }

   fun success(message: String) {
      this.sendRaw(this.buildMessage(message, 5635925))
   }

   fun error(message: String) {
      this.sendRaw(this.buildMessage(message, 16733525))
   }

   fun warning(message: String) {
      this.sendRaw(this.buildMessage(message, 16777045))
   }

   fun actionbar(message: String) {
      this.mc.gui.hud.setOverlayMessage(Component.literal(this.translateColorCodes(message)), false)
   }
}
