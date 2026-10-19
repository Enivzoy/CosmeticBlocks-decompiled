package me.enivzoy.cosmeticblocksreborn.events

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.multiplayer.ClientPacketListener

object EventDispatcher {
   fun init() {
      ClientPlayConnectionEvents.JOIN.register(::onJoin)
      ClientPlayConnectionEvents.DISCONNECT.register(::onDisconnect)
      ClientTickEvents.START_LEVEL_TICK.register(::onStartLevelTick)
      ClientTickEvents.END_LEVEL_TICK.register(::onEndLevelTick)
   }

   private fun onJoin(listener: ClientPacketListener, sender: PacketSender, client: Minecraft) {
      WorldEvent.Load.postAndCatch()
   }

   private fun onDisconnect(listener: ClientPacketListener, client: Minecraft) {
      WorldEvent.Unload.postAndCatch()
   }

   private fun onStartLevelTick(world: ClientLevel) {
      TickEvent.Start(world).postAndCatch()
   }

   private fun onEndLevelTick(world: ClientLevel) {
      TickEvent.End(world).postAndCatch()
   }
}
