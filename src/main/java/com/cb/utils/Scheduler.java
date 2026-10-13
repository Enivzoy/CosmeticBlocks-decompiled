package com.cb.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
