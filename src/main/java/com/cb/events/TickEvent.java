package com.cb.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003¨\u0006\u0004À\u0006\u0003",
   d2 = {"Lcom/cb/events/TickEvent;", "Lcom/cb/events/Event;", "Start", "End", "cb_client"}
)
public interface TickEvent extends Event {
   @Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull TickEvent $this) {
         return $this.postAndCatch();
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b",
      d2 = {
            "Lcom/cb/events/TickEvent$End;",
            "Lcom/cb/events/TickEvent;",
            "world",
            "Lnet/minecraft/client/multiplayer/ClientLevel;",
            "<init>",
            "(Lnet/minecraft/client/multiplayer/ClientLevel;)V",
            "getWorld",
            "()Lnet/minecraft/client/multiplayer/ClientLevel;",
            "cb_client"
      }
   )
   final class End implements TickEvent {
      @NotNull
      private final ClientLevel world;

      public End(@NotNull ClientLevel world) {
         Intrinsics.checkNotNullParameter(world, "world");
         super();
         this.world = world;
      }

      @NotNull
      public final ClientLevel getWorld() {
         return this.world;
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b",
      d2 = {
            "Lcom/cb/events/TickEvent$Start;",
            "Lcom/cb/events/TickEvent;",
            "world",
            "Lnet/minecraft/client/multiplayer/ClientLevel;",
            "<init>",
            "(Lnet/minecraft/client/multiplayer/ClientLevel;)V",
            "getWorld",
            "()Lnet/minecraft/client/multiplayer/ClientLevel;",
            "cb_client"
      }
   )
   final class Start implements TickEvent {
      @NotNull
      private final ClientLevel world;

      public Start(@NotNull ClientLevel world) {
         Intrinsics.checkNotNullParameter(world, "world");
         super();
         this.world = world;
      }

      @NotNull
      public final ClientLevel getWorld() {
         return this.world;
      }
   }
}
