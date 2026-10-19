package me.enivzoy.cosmeticblocksreborn.events

import net.minecraft.client.multiplayer.ClientLevel

sealed interface TickEvent : Event {
   class Start(val world: ClientLevel) : TickEvent
   class End(val world: ClientLevel) : TickEvent
}
