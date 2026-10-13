package com.cb.events;
import org.jetbrains.annotations.NotNull;

public interface WorldEvent extends Event {
   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull WorldEvent $this) {
         return $this.postAndCatch();
      }
   }

   final class Load implements WorldEvent {
      @NotNull
      public static final WorldEvent.Load INSTANCE = new WorldEvent.Load();

      private Load() {
      }
   }

   final class Unload implements WorldEvent {
      @NotNull
      public static final WorldEvent.Unload INSTANCE = new WorldEvent.Unload();

      private Unload() {
      }
   }
}
