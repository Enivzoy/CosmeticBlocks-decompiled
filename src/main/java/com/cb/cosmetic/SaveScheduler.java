package com.cb.cosmetic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

public final class SaveScheduler {
   @NotNull
   public static final SaveScheduler INSTANCE = new SaveScheduler();
   private static final ExecutorService saveExecutor = Executors.newSingleThreadExecutor(SaveScheduler::newSaveThread);
   private static boolean needsSave;
   private static long lastEditTime;
   private static long lastSaveTime;
   public static final long SAVE_DELAY_MS = 2000L;
   public static final long SAVE_MAX_INTERVAL_MS = 10000L;
   private static volatile boolean saveInFlight;
   private static volatile boolean saveAgain;
   private static boolean needsRoomSave;
   private static long lastRoomEditTime;
   private static long lastRoomSaveTime;
   public static final long ROOM_SAVE_DELAY_MS = 2000L;
   public static final long ROOM_SAVE_MAX_INTERVAL_MS = 10000L;

   private SaveScheduler() {
   }

   public final ExecutorService getSaveExecutor$cb_client() {
      return saveExecutor;
   }

   public final boolean getNeedsSave$cb_client() {
      return needsSave;
   }

   public final void setNeedsSave$cb_client(boolean value) {
      needsSave = value;
   }

   public final long getLastEditTime$cb_client() {
      return lastEditTime;
   }

   public final void setLastEditTime$cb_client(long value) {
      lastEditTime = value;
   }

   public final long getLastSaveTime$cb_client() {
      return lastSaveTime;
   }

   public final void setLastSaveTime$cb_client(long value) {
      lastSaveTime = value;
   }

   public final boolean getSaveInFlight$cb_client() {
      return saveInFlight;
   }

   public final void setSaveInFlight$cb_client(boolean value) {
      saveInFlight = value;
   }

   public final boolean getSaveAgain$cb_client() {
      return saveAgain;
   }

   public final void setSaveAgain$cb_client(boolean value) {
      saveAgain = value;
   }

   public final boolean getNeedsRoomSave$cb_client() {
      return needsRoomSave;
   }

   public final void setNeedsRoomSave$cb_client(boolean value) {
      needsRoomSave = value;
   }

   public final long getLastRoomEditTime$cb_client() {
      return lastRoomEditTime;
   }

   public final void setLastRoomEditTime$cb_client(long value) {
      lastRoomEditTime = value;
   }

   public final long getLastRoomSaveTime$cb_client() {
      return lastRoomSaveTime;
   }

   public final void setLastRoomSaveTime$cb_client(long value) {
      lastRoomSaveTime = value;
   }

   public final void scheduleSave$cb_client() {
      needsSave = true;
      lastEditTime = System.currentTimeMillis();
   }

   public final void scheduleRoomSave$cb_client() {
      needsRoomSave = true;
      lastRoomEditTime = System.currentTimeMillis();
   }

   public final void flushSave$cb_client() {
      if (needsSave) {
         ReplacementIO.INSTANCE.save();
      }

      if (needsRoomSave) {
         RoomStore.INSTANCE.forceRoomSave();
      }
   }

   public final void flushAll() {
      this.flushSave$cb_client();
      this.awaitPendingSaves$cb_client();
   }

   public final void awaitPendingSaves$cb_client() {
      try {
         saveExecutor.submit(SaveScheduler::noOpTask).get();
      } catch (Exception ignored) {
      }
   }

   private static final Thread newSaveThread(Runnable r) {
      Thread thread = new Thread(r, "CFME-Save");
      thread.setDaemon(true);
      return thread;
   }

   private static final void noOpTask() {
   }
}
