package com.cb.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.debugchart.LocalSampleLogger;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0012",
   d2 = {
         "Lcom/cb/utils/ServerUtils;",
         "",
         "<init>",
         "()V",
         "prevTime",
         "",
         "value",
         "",
         "averageTps",
         "getAverageTps",
         "()F",
         "averagePing",
         "",
         "getAveragePing",
         "()I",
         "onTimePacket",
         "",
         "reset",
         "cb_client"
   }
)
public final class ServerUtils {
   @NotNull
   public static final ServerUtils INSTANCE = new ServerUtils();
   private static long prevTime;
   private static float averageTps = 20.0F;

   private ServerUtils() {
   }

   public final float getAverageTps() {
      return averageTps;
   }

   public final int getAveragePing() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      LocalSampleLogger pingLogger = mc.getDebugOverlay().getPingLogger();
      Intrinsics.checkNotNullExpressionValue(pingLogger, "getPingLogger(...)");
      int sampleSize = Math.min(pingLogger.size(), 20);
      if (sampleSize == 0) {
         if (mc.player == null) {
            return 0;
         }

         LocalPlayer player = mc.player;
         ClientPacketListener connection = mc.getConnection();
         if (connection != null) {
            PlayerInfo info = connection.getPlayerInfo(player.getUUID());
            if (info != null) {
               return info.getLatency();
            }
         }

         return 0;
      } else {
         long total = 0L;

         for (int i = 0; i < sampleSize; i++) {
            total += pingLogger.get(i);
         }

         return (int)(total / sampleSize);
      }
   }

   public final void onTimePacket() {
      if (prevTime != 0L) {
         averageTps = RangesKt.coerceIn(20000.0F / (float)(System.currentTimeMillis() - prevTime + 1L), 0.0F, 20.0F);
      }

      prevTime = System.currentTimeMillis();
   }

   public final void reset() {
      prevTime = 0L;
      averageTps = 20.0F;
   }
}
