/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0014\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u001c\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00142\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\u000e\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\tJ\u0006\u0010\u0019\u001a\u00020\u0007R\u001a\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lcom/cb/utils/Scheduler;", "", "<init>", "()V", "actions", "", "Lkotlin/Function0;", "", "nextTaskId", "", "delayedTasks", "", "Lcom/cb/utils/Scheduler$DelayedTask;", "repeatingTasks", "Lcom/cb/utils/Scheduler$RepeatingTask;", "scheduleTickEnd", "", "action", "schedule", "delayTicks", "", "scheduleRepeating", "intervalTicks", "cancel", "taskId", "register", "DelayedTask", "RepeatingTask", "cb_client"})
@SourceDebugExtension(value={"SMAP\nScheduler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Scheduler.kt\ncom/cb/utils/Scheduler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,90:1\n1915#2,2:91\n1915#2,2:93\n1915#2,2:95\n*S KotlinDebug\n*F\n+ 1 Scheduler.kt\ncom/cb/utils/Scheduler\n*L\n55#1:91,2\n71#1:93,2\n87#1:95,2\n*E\n"})
public final class Scheduler {
    @NotNull
    public static final Scheduler INSTANCE = new Scheduler();
    @NotNull
    private static List<Function0<Unit>> actions = new ArrayList();
    private static int nextTaskId;
    @NotNull
    private static final Map<Integer, DelayedTask> delayedTasks;
    @NotNull
    private static final Map<Integer, RepeatingTask> repeatingTasks;

    private Scheduler() {
    }

    public final boolean scheduleTickEnd(@NotNull Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(action, (String)"action");
        return actions.add(action);
    }

    public final int schedule(int delayTicks, @NotNull Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(action, (String)"action");
        int n = nextTaskId;
        nextTaskId = n + 1;
        int id = n;
        delayedTasks.put(id, new DelayedTask(delayTicks, action));
        return id;
    }

    public final int schedule(long delayTicks, @NotNull Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(action, (String)"action");
        return this.schedule((int)delayTicks, action);
    }

    public final int scheduleRepeating(long intervalTicks, @NotNull Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(action, (String)"action");
        int n = nextTaskId;
        nextTaskId = n + 1;
        int id = n;
        repeatingTasks.put(id, new RepeatingTask((int)intervalTicks, (int)intervalTicks, action));
        return id;
    }

    public final void cancel(int taskId) {
        delayedTasks.remove(taskId);
        repeatingTasks.remove(taskId);
    }

    public final void register() {
        ClientTickEvents.END_CLIENT_TICK.register(Scheduler::register$lambda$0);
    }

    private static final void register$lambda$0(Minecraft it) {
        int n;
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        Iterable $this$forEach$iv = actions;
        boolean $i$f$forEach = false;
        for (Object t : $this$forEach$iv) {
            Function0 it2 = (Function0)t;
            boolean bl = false;
            it2.invoke();
        }
        actions.clear();
        List completedDelayed = new ArrayList();
        for (Map.Entry<Integer, DelayedTask> entry : delayedTasks.entrySet()) {
            int n2 = ((Number)entry.getKey()).intValue();
            DelayedTask task = entry.getValue();
            int bl = task.getTicksRemaining();
            task.setTicksRemaining(bl + -1);
            if (task.getTicksRemaining() > 0) continue;
            try {
                task.getAction().invoke();
            }
            catch (Exception e) {
                System.out.println((Object)("CB: Scheduler delayed task error: " + e.getMessage()));
            }
            completedDelayed.add(n2);
        }
        Iterable $this$forEach$iv2 = completedDelayed;
        boolean bl = false;
        for (Object element$iv : $this$forEach$iv2) {
            int it3 = ((Number)element$iv).intValue();
            n = 0;
            delayedTasks.remove(it3);
        }
        List toRemove = new ArrayList();
        for (Map.Entry<Integer, RepeatingTask> entry : repeatingTasks.entrySet()) {
            int id2 = ((Number)entry.getKey()).intValue();
            RepeatingTask task = entry.getValue();
            n = task.getTicksRemaining();
            task.setTicksRemaining(n + -1);
            if (task.getTicksRemaining() > 0) continue;
            try {
                task.getAction().invoke();
            }
            catch (Exception e) {
                System.out.println((Object)("CB: Scheduler repeating task error: " + e.getMessage()));
                toRemove.add(id2);
            }
            task.setTicksRemaining(task.getInterval());
        }
        Iterable iterable = toRemove;
        boolean bl2 = false;
        for (Object element$iv : iterable) {
            int it4 = ((Number)element$iv).intValue();
            boolean bl3 = false;
            repeatingTasks.remove(it4);
        }
    }

    static {
        delayedTasks = new LinkedHashMap();
        repeatingTasks = new LinkedHashMap();
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0014\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0015\u001a\u00020\u0003H\u00d6\u0081\u0004J\n\u0010\u0016\u001a\u00020\u0017H\u00d6\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0018"}, d2={"Lcom/cb/utils/Scheduler$DelayedTask;", "", "ticksRemaining", "", "action", "Lkotlin/Function0;", "", "<init>", "(ILkotlin/jvm/functions/Function0;)V", "getTicksRemaining", "()I", "setTicksRemaining", "(I)V", "getAction", "()Lkotlin/jvm/functions/Function0;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "cb_client"})
    private static final class DelayedTask {
        private int ticksRemaining;
        @NotNull
        private final Function0<Unit> action;

        public DelayedTask(int ticksRemaining, @NotNull Function0<Unit> action) {
            Intrinsics.checkNotNullParameter(action, (String)"action");
            this.ticksRemaining = ticksRemaining;
            this.action = action;
        }

        public final int getTicksRemaining() {
            return this.ticksRemaining;
        }

        public final void setTicksRemaining(int n) {
            this.ticksRemaining = n;
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
        public final DelayedTask copy(int ticksRemaining, @NotNull Function0<Unit> action) {
            Intrinsics.checkNotNullParameter(action, (String)"action");
            return new DelayedTask(ticksRemaining, action);
        }

        public static /* synthetic */ DelayedTask copy$default(DelayedTask delayedTask, int n, Function0 function0, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = delayedTask.ticksRemaining;
            }
            if ((n2 & 2) != 0) {
                function0 = delayedTask.action;
            }
            return delayedTask.copy(n, function0);
        }

        @NotNull
        public String toString() {
            return "DelayedTask(ticksRemaining=" + this.ticksRemaining + ", action=" + this.action + ")";
        }

        public int hashCode() {
            int result = Integer.hashCode(this.ticksRemaining);
            result = result * 31 + this.action.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DelayedTask)) {
                return false;
            }
            DelayedTask delayedTask = (DelayedTask)other;
            if (this.ticksRemaining != delayedTask.ticksRemaining) {
                return false;
            }
            return Intrinsics.areEqual(this.action, delayedTask.action);
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J-\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0001J\u0014\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0018\u001a\u00020\u0003H\u00d6\u0081\u0004J\n\u0010\u0019\u001a\u00020\u001aH\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000b\"\u0004\b\r\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001b"}, d2={"Lcom/cb/utils/Scheduler$RepeatingTask;", "", "interval", "", "ticksRemaining", "action", "Lkotlin/Function0;", "", "<init>", "(IILkotlin/jvm/functions/Function0;)V", "getInterval", "()I", "getTicksRemaining", "setTicksRemaining", "(I)V", "getAction", "()Lkotlin/jvm/functions/Function0;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "", "cb_client"})
    private static final class RepeatingTask {
        private final int interval;
        private int ticksRemaining;
        @NotNull
        private final Function0<Unit> action;

        public RepeatingTask(int interval, int ticksRemaining, @NotNull Function0<Unit> action) {
            Intrinsics.checkNotNullParameter(action, (String)"action");
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

        public final void setTicksRemaining(int n) {
            this.ticksRemaining = n;
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
        public final RepeatingTask copy(int interval, int ticksRemaining, @NotNull Function0<Unit> action) {
            Intrinsics.checkNotNullParameter(action, (String)"action");
            return new RepeatingTask(interval, ticksRemaining, action);
        }

        public static /* synthetic */ RepeatingTask copy$default(RepeatingTask repeatingTask, int n, int n2, Function0 function0, int n3, Object object) {
            if ((n3 & 1) != 0) {
                n = repeatingTask.interval;
            }
            if ((n3 & 2) != 0) {
                n2 = repeatingTask.ticksRemaining;
            }
            if ((n3 & 4) != 0) {
                function0 = repeatingTask.action;
            }
            return repeatingTask.copy(n, n2, function0);
        }

        @NotNull
        public String toString() {
            return "RepeatingTask(interval=" + this.interval + ", ticksRemaining=" + this.ticksRemaining + ", action=" + this.action + ")";
        }

        public int hashCode() {
            int result = Integer.hashCode(this.interval);
            result = result * 31 + Integer.hashCode(this.ticksRemaining);
            result = result * 31 + this.action.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RepeatingTask)) {
                return false;
            }
            RepeatingTask repeatingTask = (RepeatingTask)other;
            if (this.interval != repeatingTask.interval) {
                return false;
            }
            if (this.ticksRemaining != repeatingTask.ticksRemaining) {
                return false;
            }
            return Intrinsics.areEqual(this.action, repeatingTask.action);
        }
    }
}


