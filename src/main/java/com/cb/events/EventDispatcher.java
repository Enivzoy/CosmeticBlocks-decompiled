package com.cb.events;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.NotNull;

public final class EventDispatcher {
   @NotNull
   public static final EventDispatcher INSTANCE = new EventDispatcher();

   private EventDispatcher() {
   }

   public final void init() {
      ClientPlayConnectionEvents.JOIN.register(EventDispatcher::onJoin);
      ClientPlayConnectionEvents.DISCONNECT.register(EventDispatcher::onDisconnect);
      ClientTickEvents.START_LEVEL_TICK.register(EventDispatcher::onStartLevelTick);
      ClientTickEvents.END_LEVEL_TICK.register(EventDispatcher::onEndLevelTick);
   }

   private static final void onJoin(ClientPacketListener listener, PacketSender sender, Minecraft client) {
      Intrinsics.checkNotNullParameter(listener, "<unused var>");
      Intrinsics.checkNotNullParameter(sender, "<unused var>");
      Intrinsics.checkNotNullParameter(client, "<unused var>");
      WorldEvent.Load.INSTANCE.postAndCatch();
   }

   private static final void onDisconnect(ClientPacketListener listener, Minecraft client) {
      Intrinsics.checkNotNullParameter(listener, "<unused var>");
      Intrinsics.checkNotNullParameter(client, "<unused var>");
      WorldEvent.Unload.INSTANCE.postAndCatch();
   }

   private static final void onStartLevelTick(ClientLevel world) {
      Intrinsics.checkNotNullParameter(world, "world");
      new TickEvent.Start(world).postAndCatch();
   }

   private static final void onEndLevelTick(ClientLevel world) {
      Intrinsics.checkNotNullParameter(world, "world");
      new TickEvent.End(world).postAndCatch();
   }
}
