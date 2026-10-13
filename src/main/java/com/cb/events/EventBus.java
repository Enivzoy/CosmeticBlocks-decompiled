package com.cb.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EventBus {
   @NotNull
   public static final EventBus INSTANCE = new EventBus();
   @JvmField
   @NotNull
   public static final Map<Class<? extends Event>, EventBus.ListenerEntry<? extends Event>[]> listenerArrays = new LinkedHashMap<>();
   @JvmField
   @NotNull
   public static final Set<Object> activeSubscribers = new LinkedHashSet<>();
   @JvmField
   @NotNull
   public static final Map<Object, Class<?>> subscriberClasses = new LinkedHashMap<>();
   @JvmField
   @NotNull
   public static final HashMap<Class<? extends Event>, EventBus.Invoker> invokers = new HashMap<>();
   @NotNull
   private static final HashMap<Class<? extends Event>, String> profilerNameCache = new HashMap<>();

   private EventBus() {
   }

   public final void subscribe(@NotNull Object subscriber) {
      Intrinsics.checkNotNullParameter(subscriber, "subscriber");
      if (activeSubscribers.add(subscriber)) {
         subscriberClasses.put(subscriber, subscriber.getClass());
         this.rebuildAffectedCaches(subscriber.getClass());
      }
   }

   public final void unsubscribe(@NotNull Object subscriber) {
      Intrinsics.checkNotNullParameter(subscriber, "subscriber");
      if (activeSubscribers.remove(subscriber)) {
         Class<?> subscriberClass = subscriberClasses.remove(subscriber);
         if (subscriberClass != null) {
            INSTANCE.rebuildAffectedCaches(subscriberClass);
         }
      }
   }

   @JvmStatic
   public static final <T extends Event> void post(@NotNull T event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Class eventClass = event.getClass();
      EventBus.Invoker invoker = invokers.get(eventClass);
      if (invoker != null) {
         ProfilerFiller profiler = Profiler.get();
         Intrinsics.checkNotNullExpressionValue(profiler, "get(...)");
         String profilerName = profilerNameCache.get(eventClass);
         if (profilerName == null) {
            profilerName = "CB: " + eventClass.getSimpleName();
            profilerNameCache.put(eventClass, profilerName);
         }

         profiler.push(profilerName);

         try {
            invoker.invoke(event, profiler);
         } finally {
            profiler.pop();
         }
      }
   }

   public final <T extends Event> void registerListener(
      @NotNull Class<?> subscriberClass, @NotNull Class<T> eventClass, int priority, boolean ignoreCancelled, @NotNull Function1<? super T, Unit> handler
   ) {
      Intrinsics.checkNotNullParameter(subscriberClass, "subscriberClass");
      Intrinsics.checkNotNullParameter(eventClass, "eventClass");
      Intrinsics.checkNotNullParameter(handler, "handler");
      CharSequence simpleName = subscriberClass.getSimpleName();
      CharSequence resolvedName;
      if (simpleName.length() == 0) {
         resolvedName = subscriberClass.getName();
      } else {
         resolvedName = simpleName;
      }

      String name = (String)resolvedName;
      Intrinsics.checkNotNull(name);
      EventBus.ListenerEntry newEntry = new EventBus.ListenerEntry(subscriberClass, new EventBus.EventListener<>(priority, ignoreCancelled, name, handler));
      EventBus.ListenerEntry[] existingListeners = listenerArrays.get(eventClass);
      if (existingListeners == null) {
         existingListeners = new EventBus.ListenerEntry[0];
      }

      Object[] combined = ArraysKt.plus(existingListeners, newEntry);
      Collection sorted = ArraysKt.sortedWith((Object[])combined, new EventBus$registerListener$$inlined$sortedByDescending$1<>());
      EventBus.ListenerEntry[] newArray = (EventBus.ListenerEntry[])sorted.toArray(new EventBus.ListenerEntry[0]);
      listenerArrays.put(eventClass, newArray);
      this.rebuildInvoker(eventClass, newArray);
   }

   private final void rebuildAffectedCaches(Class<?> changedClass) {
      for (Entry<Class<? extends Event>, EventBus.ListenerEntry<? extends Event>[]> entry : listenerArrays.entrySet()) {
         Class<? extends Event> eventClass = entry.getKey();
         EventBus.ListenerEntry<? extends Event>[] listeners = entry.getValue();
         boolean hasSubscriber = false;

         for (EventBus.ListenerEntry<? extends Event> listener : listeners) {
            if (Intrinsics.areEqual(listener.getSubscriber(), changedClass)) {
               hasSubscriber = true;
               break;
            }
         }

         if (hasSubscriber) {
            this.rebuildInvoker(eventClass, listeners);
         }
      }
   }

   private final void rebuildInvoker(Class<? extends Event> eventClass, EventBus.ListenerEntry<?>[] allListeners) {
      if (activeSubscribers.isEmpty()) {
         invokers.put(eventClass, EventBus.EmptyInvoker.INSTANCE);
      } else {
         Collection activeClasses = subscriberClasses.values();
         List activeListeners = new ArrayList();

         for (EventBus.ListenerEntry<?> listener : allListeners) {
            if (activeClasses.contains(listener.getSubscriber())) {
               activeListeners.add(listener.getListener());
            }
         }

         EventBus.EventListener[] invokersArray = (EventBus.EventListener[])activeListeners.toArray(new EventBus.EventListener[0]);
         invokers.put(eventClass, invokersArray.length == 0 ? EventBus.EmptyInvoker.INSTANCE : EventBus.InvokerFactory.INSTANCE.build(invokersArray));
      }
   }

   private static final class EmptyInvoker implements EventBus.Invoker {
      @NotNull
      public static final EventBus.EmptyInvoker INSTANCE = new EventBus.EmptyInvoker();

      @Override
      public void invoke(@NotNull Event event, @NotNull ProfilerFiller profiler) {
         Intrinsics.checkNotNullParameter(event, "event");
         Intrinsics.checkNotNullParameter(profiler, "profiler");
      }
   }

   public static final class EventListener<T extends Event> {
      private final int priority;
      @NotNull
      private final String subscriberName;
      @NotNull
      private final Function1<T, Unit> handler;
      @JvmField
      public final boolean checkCancelled;

      public EventListener(int priority, boolean ignoreCancelled, @NotNull String subscriberName, @NotNull Function1<? super T, Unit> handler) {
         Intrinsics.checkNotNullParameter(subscriberName, "subscriberName");
         Intrinsics.checkNotNullParameter(handler, "handler");
         super();
         this.priority = priority;
         this.subscriberName = subscriberName;
         this.handler = (Function1<T, Unit>)handler;
         this.checkCancelled = ignoreCancelled;
      }

      public final int getPriority() {
         return this.priority;
      }

      @NotNull
      public final String getSubscriberName() {
         return this.subscriberName;
      }

      @NotNull
      public final Function1<T, Unit> getHandler() {
         return this.handler;
      }

      public final void invoke(@NotNull T event) {
         Intrinsics.checkNotNullParameter(event, "event");
         if (!this.checkCancelled || !(event instanceof CancellableEvent) || !((CancellableEvent)event).isCancelled()) {
            this.handler.invoke((T)event);
         }
      }
   }

   public interface Invoker {
      void invoke(@NotNull Event event, @NotNull ProfilerFiller profiler);
   }

   private static final class InvokerFactory {
      @NotNull
      public static final EventBus.InvokerFactory INSTANCE = new EventBus.InvokerFactory();

      @NotNull
      public final EventBus.Invoker build(@NotNull final EventBus.EventListener<Event>[] listeners) {
         Intrinsics.checkNotNullParameter(listeners, "listeners");
         return new EventBus.Invoker() {
            @Override
            public void invoke(Event event, ProfilerFiller profiler) {
               Intrinsics.checkNotNullParameter(event, "event");
               Intrinsics.checkNotNullParameter(profiler, "profiler");
               for (EventBus.EventListener listener : listeners) {
                  profiler.push(listener.getSubscriberName());

                  try {
                     listener.invoke(event);
                  } finally {
                     profiler.pop();
                  }
               }
            }
         };
      }
   }

   public static final class ListenerEntry<T extends Event> {
      @NotNull
      private final Class<?> subscriber;
      @NotNull
      private final EventBus.EventListener<T> listener;

      public ListenerEntry(@NotNull Class<?> subscriber, @NotNull EventBus.EventListener<T> listener) {
         Intrinsics.checkNotNullParameter(subscriber, "subscriber");
         Intrinsics.checkNotNullParameter(listener, "listener");
         super();
         this.subscriber = subscriber;
         this.listener = listener;
      }

      @NotNull
      public final Class<?> getSubscriber() {
         return this.subscriber;
      }

      @NotNull
      public final EventBus.EventListener<T> getListener() {
         return this.listener;
      }

      @NotNull
      public final Class<?> component1() {
         return this.subscriber;
      }

      @NotNull
      public final EventBus.EventListener<T> component2() {
         return this.listener;
      }

      @NotNull
      public final EventBus.ListenerEntry<T> copy(@NotNull Class<?> subscriber, @NotNull EventBus.EventListener<T> listener) {
         Intrinsics.checkNotNullParameter(subscriber, "subscriber");
         Intrinsics.checkNotNullParameter(listener, "listener");
         return new EventBus.ListenerEntry<>(subscriber, listener);
      }

      @NotNull
      @Override
      public String toString() {
         return "ListenerEntry(subscriber=" + this.subscriber + ", listener=" + this.listener + ")";
      }

      @Override
      public int hashCode() {
         int result = this.subscriber.hashCode();
         return result * 31 + this.listener.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof EventBus.ListenerEntry entry)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.subscriber, entry.subscriber) ? false : Intrinsics.areEqual(this.listener, entry.listener);
         }
      }
   }
}
