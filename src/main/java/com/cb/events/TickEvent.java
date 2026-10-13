package com.cb.events;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.multiplayer.ClientLevel;
import org.jetbrains.annotations.NotNull;

public interface TickEvent extends Event {
   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull TickEvent $this) {
         return $this.postAndCatch();
      }
   }

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
