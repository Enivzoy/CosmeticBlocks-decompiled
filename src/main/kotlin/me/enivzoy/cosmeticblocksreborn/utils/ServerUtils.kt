package me.enivzoy.cosmeticblocksreborn.utils

import net.minecraft.client.Minecraft

object ServerUtils {
   private var prevTime = 0L
   private var tps = 20.0F

   val averageTps: Float
      get() = tps

   val averagePing: Int
      get() {
         val mc = Minecraft.getInstance()
         val pingLogger = mc.debugOverlay.pingLogger
         val sampleSize = minOf(pingLogger.size(), 20)
         if (sampleSize == 0) {
            val player = mc.player ?: return 0
            val connection = mc.connection ?: return 0
            val info = connection.getPlayerInfo(player.uuid) ?: return 0
            return info.latency
         }

         var total = 0L
         for (i in 0 until sampleSize) {
            total += pingLogger.get(i)
         }

         return (total / sampleSize).toInt()
      }

   fun onTimePacket() {
      if (prevTime != 0L) {
         tps = (20000.0F / (System.currentTimeMillis() - prevTime + 1L).toFloat()).coerceIn(0.0F, 20.0F)
      }

      prevTime = System.currentTimeMillis()
   }

   fun reset() {
      prevTime = 0L
      tps = 20.0F
   }
}
