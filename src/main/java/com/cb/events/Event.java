package com.cb.events;
import org.jetbrains.annotations.NotNull;

public interface Event {
   default boolean postAndCatch() {
      try {
         EventBus.post(this);
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }

      return false;
   }

   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull Event $this) {
         return $this.postAndCatch();
      }
   }
}
