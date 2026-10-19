package me.enivzoy.cosmeticblocksreborn.events

interface Event {
   fun postAndCatch(): Boolean {
      try {
         EventBus.post(this)
      } catch (throwable: Throwable) {
         throwable.printStackTrace()
      }

      return false
   }
}
