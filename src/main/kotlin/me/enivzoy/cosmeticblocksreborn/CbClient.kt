package me.enivzoy.cosmeticblocksreborn

import me.enivzoy.cosmeticblocksreborn.command.CbCommand
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.events.EventDispatcher
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.gui.hud.HudRenderer
import me.enivzoy.cosmeticblocksreborn.gui.screens.BlockSelectorScreen
import me.enivzoy.cosmeticblocksreborn.gui.screens.CliEngine
import me.enivzoy.cosmeticblocksreborn.gui.screens.CliScreen
import me.enivzoy.cosmeticblocksreborn.gui.screens.RadialBlockScreen
import me.enivzoy.cosmeticblocksreborn.utils.Scheduler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import org.lwjgl.glfw.GLFW
import java.io.File

object CbClient : ClientModInitializer {
   const val NAMESPACE = "cb"

   private val configDir = File("config/cb")
   private val keyWasDown = HashMap<Int, Boolean>()

   override fun onInitializeClient() {
      if (!configDir.exists()) {
         configDir.mkdirs()
      }
      EventDispatcher.init()
      HudRenderer.register()
      CosmeticBlocks.INSTANCE.register()
      Scheduler.register()
      Config.INSTANCE.load()
      ClientLifecycleEvents.CLIENT_STOPPING.register(::onClientStopping)
      ClientTickEvents.END_CLIENT_TICK.register(::onEndClientTick)
      CbCommand.INSTANCE.register()
   }

   private fun pollOpenBind(client: Minecraft, key: Int, screen: () -> Screen) {
      if (key == -1) {
         return
      }
      val window = Minecraft.getInstance().window.handle()
      val down = GLFW.glfwGetKey(window, key) == 1
      if (down && keyWasDown[key] != true) {
         client.gui.setScreen(screen())
      }
      keyWasDown[key] = down
   }

   private fun onClientStopping(minecraft: Minecraft) {
      CosmeticBlocks.INSTANCE.flushAll()
   }

   private fun onEndClientTick(client: Minecraft) {
      if (client.gui.screen() == null) {
         pollOpenBind(client, GuiTab.INSTANCE.openGuiKeybind.value, { ClickGuiScreen() })
         pollOpenBind(client, GuiTab.INSTANCE.openCliKeybind.value, { CliScreen() })
         pollOpenBind(client, CosmeticBlocksTab.INSTANCE.blockSelectorKeybind.value, { BlockSelectorScreen() })
         pollOpenBind(client, CosmeticBlocksTab.INSTANCE.blockWheelKeybind.value, { RadialBlockScreen() })
      } else {
         keyWasDown.clear()
      }
      CliEngine.checkReminders()
   }
}
