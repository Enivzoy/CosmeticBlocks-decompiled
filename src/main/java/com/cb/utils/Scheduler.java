package com.cb.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u000e\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\tJ\u0006\u0010\u0019\u001a\u00020\u0007R\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c",
   d2 = {
         "Lcom/cb/utils/Scheduler;",
         "",
         "<init>",
         "()V",
         "actions",
         "",
         "Lkotlin/Function0;",
         "",
         "nextTaskId",
         "",
         "delayedTasks",
         "",
         "Lcom/cb/utils/Scheduler$DelayedTask;",
         "repeatingTasks",
         "Lcom/cb/utils/Scheduler$RepeatingTask;",
         "scheduleTickEnd",
         "",
         "action",
         "schedule",
         "delayTicks",
         "",
         "scheduleRepeating",
         "intervalTicks",
         "cancel",
         "taskId",
         "register",
         "DelayedTask",
         "RepeatingTask",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nScheduler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Scheduler.kt\ncom/cb/utils/Scheduler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,90:1\n1915#2,2:91\n1915#2,2:93\n1915#2,2:95\n*S KotlinDebug\n*F\n+ 1 Scheduler.kt\ncom/cb/utils/Scheduler\n*L\n55#1:91,2\n71#1:93,2\n87#1:95,2\n*E\n"
)
public final class Scheduler {
   @NotNull
   public static final Scheduler INSTANCE = new Scheduler();
   @NotNull
   private static List<Function0<Unit>> actions = new ArrayList<>();
   private static int nextTaskId;
   @NotNull
   private static final Map<Integer, Scheduler.DelayedTask> delayedTasks = new LinkedHashMap<>();
   @NotNull
   private static final Map<Integer, Scheduler.RepeatingTask> repeatingTasks = new LinkedHashMap<>();

   private Scheduler() {
   }

   public final boolean scheduleTickEnd(@NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      return actions.add(action);
   }

   public final int schedule(int delayTicks, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      int id = nextTaskId++;
      delayedTasks.put(id, new Scheduler.DelayedTask(delayTicks, action));
      return id;
   }

   public final int schedule(long delayTicks, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      return this.schedule((int)delayTicks, action);
   }

   public final int scheduleRepeating(long intervalTicks, @NotNull Function0<Unit> action) {
      Intrinsics.checkNotNullParameter(action, "action");
      int id = nextTaskId++;
      repeatingTasks.put(id, new Scheduler.RepeatingTask((int)intervalTicks, (int)intervalTicks, action));
      return id;
   }

   public final void cancel(int taskId) {
      delayedTasks.remove(taskId);
      repeatingTasks.remove(taskId);
   }

   public final void register() {
      ClientTickEvents.END_CLIENT_TICK.register(Scheduler::onEndClientTick);
   }

   private static final void onEndClientTick(Minecraft client) {
      Intrinsics.checkNotNullParameter(client, "client");

      for (Function0<Unit> action : actions) {
         action.invoke();
      }

      actions.clear();
      List<Integer> expiredIds = new ArrayList<>();

      for (Entry<Integer, Scheduler.DelayedTask> entry : delayedTasks.entrySet()) {
         int id = entry.getKey();
         Scheduler.DelayedTask task = entry.getValue();
         task.setTicksRemaining(task.getTicksRemaining() - 1);
         if (task.getTicksRemaining() <= 0) {
            try {
               task.getAction().invoke();
            } catch (Exception e) {
               System.out.println((Object)("CB: Scheduler delayed task error: " + e.getMessage()));
            }

            expiredIds.add(id);
         }
      }

      for (int id : expiredIds) {
         delayedTasks.remove(id);
      }

      List<Integer> repeatingToRemove = new ArrayList<>();

      for (Entry<Integer, Scheduler.RepeatingTask> entry : repeatingTasks.entrySet()) {
         int id = entry.getKey();
         Scheduler.RepeatingTask task = entry.getValue();
         task.setTicksRemaining(task.getTicksRemaining() - 1);
         if (task.getTicksRemaining() <= 0) {
            try {
               task.getAction().invoke();
            } catch (Exception e) {
               System.out.println((Object)("CB: Scheduler repeating task error: " + e.getMessage()));
               repeatingToRemove.add(id);
            }

            task.setTicksRemaining(task.getInterval());
         }
      }

      for (int id : repeatingToRemove) {
         repeatingTasks.remove(id);
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0014\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0015\u001a\u00020\u0003HÖ\u0081\u0004J\n\u0010\u0016\u001a\u00020\u0017HÖ\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018",
      d2 = {
            "Lcom/cb/utils/Scheduler$DelayedTask;",
            "",
            "ticksRemaining",
            "",
            "action",
            "Lkotlin/Function0;",
            "",
            "<init>",
            "(ILkotlin/jvm/functions/Function0;)V",
            "getTicksRemaining",
            "()I",
            "setTicksRemaining",
            "(I)V",
            "getAction",
            "()Lkotlin/jvm/functions/Function0;",
            "component1",
            "component2",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "",
            "cb_client"
      }
   )
   private static final class DelayedTask {
      private int ticksRemaining;
      @NotNull
      private final Function0<Unit> action;

      public DelayedTask(int ticksRemaining, @NotNull Function0<Unit> action) {
         Intrinsics.checkNotNullParameter(action, "action");
         super();
         this.ticksRemaining = ticksRemaining;
         this.action = action;
      }

      public final int getTicksRemaining() {
         return this.ticksRemaining;
      }

      public final void setTicksRemaining(int ticksRemaining) {
         this.ticksRemaining = ticksRemaining;
      }

      @NotNull
      public final Function0<Unit> getAction() {
         return this.action;
      }

      public final int component1() {
         return this.ticksRemaining;
      }

      @NotNull
      public final Function0<Unit> component2() {
         return this.action;
      }

      @NotNull
      public final Scheduler.DelayedTask copy(int ticksRemaining, @NotNull Function0<Unit> action) {
         Intrinsics.checkNotNullParameter(action, "action");
         return new Scheduler.DelayedTask(ticksRemaining, action);
      }

      @NotNull
      @Override
      public String toString() {
         return "DelayedTask(ticksRemaining=" + this.ticksRemaining + ", action=" + this.action + ")";
      }

      @Override
      public int hashCode() {
         int result = Integer.hashCode(this.ticksRemaining);
         return result * 31 + this.action.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
          } else if (!(other instanceof Scheduler.DelayedTask task)) {
             return false;
          } else {
             return this.ticksRemaining != task.ticksRemaining ? false : Intrinsics.areEqual(this.action, task.action);
          }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0001J\u0014\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0018\u001a\u00020\u0003HÖ\u0081\u0004J\n\u0010\u0019\u001a\u00020\u001aHÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000b\"\u0004\b\r\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001b",
      d2 = {
            "Lcom/cb/utils/Scheduler$RepeatingTask;",
            "",
            "interval",
            "",
            "ticksRemaining",
            "action",
            "Lkotlin/Function0;",
            "",
            "<init>",
            "(IILkotlin/jvm/functions/Function0;)V",
            "getInterval",
            "()I",
            "getTicksRemaining",
            "setTicksRemaining",
            "(I)V",
            "getAction",
            "()Lkotlin/jvm/functions/Function0;",
            "component1",
            "component2",
            "component3",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "",
            "cb_client"
      }
   )
   private static final class RepeatingTask {
      private final int interval;
      private int ticksRemaining;
      @NotNull
      private final Function0<Unit> action;

      public RepeatingTask(int interval, int ticksRemaining, @NotNull Function0<Unit> action) {
         Intrinsics.checkNotNullParameter(action, "action");
         super();
         this.interval = interval;
         this.ticksRemaining = ticksRemaining;
         this.action = action;
      }

      public final int getInterval() {
         return this.interval;
      }

      public final int getTicksRemaining() {
         return this.ticksRemaining;
      }

      public final void setTicksRemaining(int ticksRemaining) {
         this.ticksRemaining = ticksRemaining;
      }

      @NotNull
      public final Function0<Unit> getAction() {
         return this.action;
      }

      public final int component1() {
         return this.interval;
      }

      public final int component2() {
         return this.ticksRemaining;
      }

      @NotNull
      public final Function0<Unit> component3() {
         return this.action;
      }

      @NotNull
      public final Scheduler.RepeatingTask copy(int interval, int ticksRemaining, @NotNull Function0<Unit> action) {
         Intrinsics.checkNotNullParameter(action, "action");
         return new Scheduler.RepeatingTask(interval, ticksRemaining, action);
      }

      @NotNull
      @Override
      public String toString() {
         return "RepeatingTask(interval=" + this.interval + ", ticksRemaining=" + this.ticksRemaining + ", action=" + this.action + ")";
      }

      @Override
      public int hashCode() {
         int result = Integer.hashCode(this.interval);
         result = result * 31 + Integer.hashCode(this.ticksRemaining);
         return result * 31 + this.action.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
          } else if (!(other instanceof Scheduler.RepeatingTask task)) {
             return false;
          } else if (this.interval != task.interval) {
             return false;
          } else {
             return this.ticksRemaining != task.ticksRemaining ? false : Intrinsics.areEqual(this.action, task.action);
          }
      }
   }
}
