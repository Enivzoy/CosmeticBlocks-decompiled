package com.cb.utils;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.debugchart.LocalSampleLogger;
import org.jetbrains.annotations.NotNull;

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
