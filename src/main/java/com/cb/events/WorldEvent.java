package com.cb.events;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004À\u0006\u0003",
   d2 = {"Lcom/cb/events/WorldEvent;", "Lcom/cb/events/Event;", "Load", "Unload", "cb_client"}
)
public interface WorldEvent extends Event {
   @Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull WorldEvent $this) {
         return $this.postAndCatch();
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004",
      d2 = {"Lcom/cb/events/WorldEvent$Load;", "Lcom/cb/events/WorldEvent;", "<init>", "()V", "cb_client"}
   )
   final class Load implements WorldEvent {
      @NotNull
      public static final WorldEvent.Load INSTANCE = new WorldEvent.Load();

      private Load() {
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004",
      d2 = {"Lcom/cb/events/WorldEvent$Unload;", "Lcom/cb/events/WorldEvent;", "<init>", "()V", "cb_client"}
   )
   final class Unload implements WorldEvent {
      @NotNull
      public static final WorldEvent.Unload INSTANCE = new WorldEvent.Unload();

      private Unload() {
      }
   }
}
