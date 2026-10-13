/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.util.debugchart.LocalSampleLogger
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.debugchart.LocalSampleLogger;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lcom/cb/utils/ServerUtils;", "", "<init>", "()V", "prevTime", "", "value", "", "averageTps", "getAverageTps", "()F", "averagePing", "", "getAveragePing", "()I", "onTimePacket", "", "reset", "cb_client"})
public final class ServerUtils {
    @NotNull
    public static final ServerUtils INSTANCE = new ServerUtils();
    private static long prevTime;
    private static float averageTps;

    private ServerUtils() {
    }

    public final float getAverageTps() {
        return averageTps;
    }

    public final int getAveragePing() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        LocalSampleLogger localSampleLogger = mc.getDebugOverlay().getPingLogger();
        Intrinsics.checkNotNullExpressionValue((Object)localSampleLogger, (String)"getPingLogger(...)");
        LocalSampleLogger pingLogger = localSampleLogger;
        int sampleSize = Math.min(pingLogger.size(), 20);
        if (sampleSize == 0) {
            LocalPlayer localPlayer = mc.player;
            if (localPlayer == null) {
                return 0;
            }
            LocalPlayer player = localPlayer;
            ClientPacketListener clientPacketListener = mc.getConnection();
            return clientPacketListener != null && (clientPacketListener = clientPacketListener.getPlayerInfo(player.getUUID())) != null ? clientPacketListener.getLatency() : 0;
        }
        long total = 0L;
        for (int i = 0; i < sampleSize; ++i) {
            total += pingLogger.get(i);
        }
        return (int)(total / (long)sampleSize);
    }

    public final void onTimePacket() {
        if (prevTime != 0L) {
            averageTps = RangesKt.coerceIn((float)(20000.0f / (float)(System.currentTimeMillis() - prevTime + 1L)), (float)0.0f, (float)20.0f);
        }
        prevTime = System.currentTimeMillis();
    }

    public final void reset() {
        prevTime = 0L;
        averageTps = 20.0f;
    }

    static {
        averageTps = 20.0f;
    }
}


