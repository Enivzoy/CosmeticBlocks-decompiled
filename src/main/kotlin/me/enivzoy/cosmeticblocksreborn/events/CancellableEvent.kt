package me.enivzoy.cosmeticblocksreborn.events

abstract class CancellableEvent : Event {
   var isCancelled = false
      private set

   fun cancel() {
      this.isCancelled = true
   }

   override fun postAndCatch(): Boolean {
      try {
         EventBus.post(this)
      } catch (throwable: Throwable) {
         throwable.printStackTrace()
      }

      return this.isCancelled
   }
}
