/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
 *  net.fabricmc.fabric.api.networking.v1.PacketSender
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.events;

import com.cb.events.TickEvent;
import com.cb.events.WorldEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cb/events/EventDispatcher;", "", "<init>", "()V", "init", "", "cb_client"})
public final class EventDispatcher {
    @NotNull
    public static final EventDispatcher INSTANCE = new EventDispatcher();

    private EventDispatcher() {
    }

    public final void init() {
        ClientPlayConnectionEvents.JOIN.register(EventDispatcher::init$lambda$0);
        ClientPlayConnectionEvents.DISCONNECT.register(EventDispatcher::init$lambda$1);
        ClientTickEvents.START_LEVEL_TICK.register(EventDispatcher::init$lambda$2);
        ClientTickEvents.END_LEVEL_TICK.register(EventDispatcher::init$lambda$3);
    }

    private static final void init$lambda$0(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        Intrinsics.checkNotNullParameter((Object)clientPacketListener, (String)"<unused var>");
        Intrinsics.checkNotNullParameter((Object)packetSender, (String)"<unused var>");
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"<unused var>");
        WorldEvent.Load.INSTANCE.postAndCatch();
    }

    private static final void init$lambda$1(ClientPacketListener clientPacketListener, Minecraft minecraft) {
        Intrinsics.checkNotNullParameter((Object)clientPacketListener, (String)"<unused var>");
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"<unused var>");
        WorldEvent.Unload.INSTANCE.postAndCatch();
    }

    private static final void init$lambda$2(ClientLevel world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        new TickEvent.Start(world).postAndCatch();
    }

    private static final void init$lambda$3(ClientLevel world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        new TickEvent.End(world).postAndCatch();
    }
}


