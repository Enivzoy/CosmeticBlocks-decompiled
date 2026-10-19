package me.enivzoy.cosmeticblocksreborn.events

sealed interface WorldEvent : Event {
   object Load : WorldEvent
   object Unload : WorldEvent
}
