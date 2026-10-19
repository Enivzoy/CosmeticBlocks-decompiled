package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object SaveScheduler {
   internal val saveExecutor: ExecutorService = Executors.newSingleThreadExecutor(::newSaveThread)
   internal var needsSave = false
   internal var lastEditTime = 0L
   internal var lastSaveTime = 0L
   const val SAVE_DELAY_MS = 2000L
   const val SAVE_MAX_INTERVAL_MS = 10000L

   @Volatile
   internal var saveInFlight = false

   @Volatile
   internal var saveAgain = false

   internal var needsRoomSave = false
   internal var lastRoomEditTime = 0L
   internal var lastRoomSaveTime = 0L
   const val ROOM_SAVE_DELAY_MS = 2000L
   const val ROOM_SAVE_MAX_INTERVAL_MS = 10000L

   internal fun scheduleSave() {
      needsSave = true
      lastEditTime = System.currentTimeMillis()
   }

   internal fun scheduleRoomSave() {
      needsRoomSave = true
      lastRoomEditTime = System.currentTimeMillis()
   }

   internal fun flushSave() {
      if (needsSave) {
         ReplacementIO.INSTANCE.save()
      }

      if (needsRoomSave) {
         RoomStore.forceRoomSave()
      }
   }

   fun flushAll() {
      this.flushSave()
      this.awaitPendingSaves()
   }

   internal fun awaitPendingSaves() {
      try {
         saveExecutor.submit(Runnable { }).get()
      } catch (ignored: Exception) {
      }
   }

   private fun newSaveThread(r: Runnable): Thread {
      val thread = Thread(r, "CFME-Save")
      thread.isDaemon = true
      return thread
   }
}
