package com.cb.events;

public abstract class CancellableEvent implements Event {
   private boolean isCancelled;

   public final boolean isCancelled() {
      return this.isCancelled;
   }

   public final void cancel() {
      this.isCancelled = true;
   }

   @Override
   public boolean postAndCatch() {
      try {
         EventBus.post(this);
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }

      return this.isCancelled;
   }
}
