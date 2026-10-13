package com.cb.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006",
   d2 = {"Lcom/cb/events/EventDispatcher;", "", "<init>", "()V", "init", "", "cb_client"}
)
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
