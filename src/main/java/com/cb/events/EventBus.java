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
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\bÆ\u0002\u0018\u00002\u00020\u0001:\u0005)*+,-B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0001J\u000e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0001J\u001f\u0010\u0017\u001a\u00020\u0014\"\b\b\u0000\u0010\u0018*\u00020\u00072\u0006\u0010\u0019\u001a\u0002H\u0018H\u0007¢\u0006\u0002\u0010\u001aJN\u0010\u001b\u001a\u00020\u0014\"\b\b\u0000\u0010\u0018*\u00020\u00072\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u00062\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00180\u00062\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u00020\u00140#J\u0014\u0010$\u001a\u00020\u00142\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002J/\u0010&\u001a\u00020\u00142\u000e\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u00062\u0010\u0010'\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bH\u0002¢\u0006\u0002\u0010(R2\u0010\u0004\u001a$\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u0012\u0012\u0012\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t0\b0\u00058\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b8\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R \u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u0001\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u00058\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R<\u0010\r\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u000f0\u000ej\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u000f`\u00108\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R:\u0010\u0011\u001a.\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u00120\u000ej\u0016\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\u0012`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006.",
   d2 = {
         "Lcom/cb/events/EventBus;",
         "",
         "<init>",
         "()V",
         "listenerArrays",
         "",
         "Ljava/lang/Class;",
         "Lcom/cb/events/Event;",
         "",
         "Lcom/cb/events/EventBus$ListenerEntry;",
         "activeSubscribers",
         "",
         "subscriberClasses",
         "invokers",
         "Ljava/util/HashMap;",
         "Lcom/cb/events/EventBus$Invoker;",
         "Lkotlin/collections/HashMap;",
         "profilerNameCache",
         "",
         "subscribe",
         "",
         "subscriber",
         "unsubscribe",
         "post",
         "T",
         "event",
         "(Lcom/cb/events/Event;)V",
         "registerListener",
         "subscriberClass",
         "eventClass",
         "priority",
         "",
         "ignoreCancelled",
         "",
         "handler",
         "Lkotlin/Function1;",
         "rebuildAffectedCaches",
         "changedClass",
         "rebuildInvoker",
         "allListeners",
         "(Ljava/lang/Class;[Lcom/cb/events/EventBus$ListenerEntry;)V",
         "ListenerEntry",
         "EventListener",
         "Invoker",
         "EmptyInvoker",
         "InvokerFactory",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nEventBus.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EventBus.kt\ncom/cb/events/EventBus\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,144:1\n1#2:145\n410#3,7:146\n6720#4:153\n13213#4,2:156\n3938#4:158\n4474#4,2:159\n37#5,2:154\n37#5,2:165\n1586#6:161\n1661#6,3:162\n*S KotlinDebug\n*F\n+ 1 EventBus.kt\ncom/cb/events/EventBus\n*L\n37#1:146,7\n57#1:153\n64#1:156,2\n81#1:158\n81#1:159,2\n57#1:154,2\n83#1:165,2\n82#1:161\n82#1:162,3\n*E\n"
)
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
         Class var10000 = subscriberClasses.remove(subscriber);
         if (var10000 != null) {
            Class it = var10000;
            int var3/* $VF was: $i$a$-let-EventBus$unsubscribe$1 */ = 0;
            INSTANCE.rebuildAffectedCaches(it);
         }
      }
   }

   @JvmStatic
   public static final <T extends Event> void post(@NotNull T event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Class eventClass = event.getClass();
      EventBus.Invoker var10000 = invokers.get(eventClass);
      if (var10000 != null) {
         EventBus.Invoker invoker = var10000;
         ProfilerFiller var13 = Profiler.get();
         Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
         ProfilerFiller profiler = var13;
         Map $this$getOrPut$iv = profilerNameCache;
         Object key$iv = eventClass;
         int $i$f$getOrPut = 0;
         Object value$iv = $this$getOrPut$iv.get(key$iv);
         Object var14;
         if (value$iv == null) {
            int var9/* $VF was: $i$a$-getOrPut-EventBus$post$profilerName$1 */ = 0;
            Object answer$iv = "CB: " + eventClass.getSimpleName();
            $this$getOrPut$iv.put(key$iv, answer$iv);
            var14 = answer$iv;
         } else {
            var14 = value$iv;
         }

         String profilerName = (String)var14;
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
      CharSequence entry = subscriberClass.getSimpleName();
      CharSequence var10000;
      if (entry.length() == 0) {
         int var8/* $VF was: $i$a$-ifEmpty-EventBus$registerListener$name$1 */ = 0;
         var10000 = subscriberClass.getName();
      } else {
         var10000 = entry;
      }

      String name = (String)var10000;
      Intrinsics.checkNotNull(name);
      EventBus.ListenerEntry entryx = new EventBus.ListenerEntry(subscriberClass, new EventBus.EventListener<>(priority, ignoreCancelled, name, handler));
      EventBus.ListenerEntry[] var17 = listenerArrays.get(eventClass);
      if (var17 == null) {
         var17 = new EventBus.ListenerEntry[0];
      }

      EventBus.ListenerEntry[] existing = var17;
      Object[] $this$sortedByDescending$iv = ArraysKt.plus(existing, entryx);
      int $i$f$sortedByDescending = 0;
      Collection var15 = ArraysKt.sortedWith((Object[])$this$sortedByDescending$iv, new EventBus$registerListener$$inlined$sortedByDescending$1<>());
      $i$f$sortedByDescending = 0;
      Collection thisCollection$iv = var15;
      EventBus.ListenerEntry[] newArray = (EventBus.ListenerEntry[])thisCollection$iv.toArray(new EventBus.ListenerEntry[0]);
      listenerArrays.put(eventClass, newArray);
      this.rebuildInvoker(eventClass, newArray);
   }

   private final void rebuildAffectedCaches(Class<?> changedClass) {
      for (Entry var3 : listenerArrays.entrySet()) {
         Class eventClass = (Class)var3.getKey();
         EventBus.ListenerEntry[] listeners = (EventBus.ListenerEntry[])var3.getValue();
         Object[] $this$any$iv = listeners;
         int $i$f$any = 0;
         int var8 = 0;
         int var9 = $this$any$iv.length;

         boolean var10000;
         while (true) {
            if (var8 >= var9) {
               var10000 = false;
               break;
            }

            Object element$iv = $this$any$iv[var8];
            EventBus.ListenerEntry it = (EventBus.ListenerEntry)element$iv;
            int var12/* $VF was: $i$a$-any-EventBus$rebuildAffectedCaches$1 */ = 0;
            if (Intrinsics.areEqual(it.getSubscriber(), changedClass)) {
               var10000 = true;
               break;
            }

            var8++;
         }

         if (var10000) {
            this.rebuildInvoker(eventClass, listeners);
         }
      }
   }

   private final void rebuildInvoker(Class<? extends Event> eventClass, EventBus.ListenerEntry<?>[] allListeners) {
      if (activeSubscribers.isEmpty()) {
         invokers.put(eventClass, EventBus.EmptyInvoker.INSTANCE);
      } else {
         Collection activeClasses = subscriberClasses.values();
         EventBus.ListenerEntry[] $this$toTypedArray$iv = allListeners;
         int $i$f$filter = 0;
         EventBus.ListenerEntry[] thisCollection$iv = $this$toTypedArray$iv;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;
         int var10 = 0;

         for (int item$iv$iv = thisCollection$iv.length; var10 < item$iv$iv; var10++) {
            Object element$iv$iv = thisCollection$iv[var10];
            EventBus.ListenerEntry it = (EventBus.ListenerEntry)element$iv$iv;
            int var14/* $VF was: $i$a$-filter-EventBus$rebuildInvoker$activeListeners$1 */ = 0;
            if (activeClasses.contains(it.getSubscriber())) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var var16 = (Iterable & List)destination$iv$iv;
         $i$f$filter = 0;
         Iterable var20 = var16;
         destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var16, 10));
         $i$f$filterTo = 0;

         for (Object item$iv$iv : var20) {
            EventBus.ListenerEntry var26 = (EventBus.ListenerEntry)item$iv$iv;
            Collection var15 = destination$iv$iv;
            int var27/* $VF was: $i$a$-map-EventBus$rebuildInvoker$activeListeners$2 */ = 0;
            EventBus.EventListener var10000 = var26.getListener();
            Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type com.cb.events.EventBus.EventListener<com.cb.events.Event>");
            var15.add(var10000);
         }

         var var17 = (Collection & List)destination$iv$iv;
         $i$f$filter = 0;
         Collection thisCollection$ivx = var17;
         EventBus.EventListener[] activeListeners = (EventBus.EventListener[])thisCollection$ivx.toArray(new EventBus.EventListener[0]);
         invokers.put(eventClass, activeListeners.length == 0 ? EventBus.EmptyInvoker.INSTANCE : EventBus.InvokerFactory.INSTANCE.build(activeListeners));
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n",
      d2 = {
            "Lcom/cb/events/EventBus$EmptyInvoker;",
            "Lcom/cb/events/EventBus$Invoker;",
            "<init>",
            "()V",
            "invoke",
            "",
            "event",
            "Lcom/cb/events/Event;",
            "profiler",
            "Lnet/minecraft/util/profiling/ProfilerFiller;",
            "cb_client"
      }
   )
   private static final class EmptyInvoker implements EventBus.Invoker {
      @NotNull
      public static final EventBus.EmptyInvoker INSTANCE = new EventBus.EmptyInvoker();

      @Override
      public void invoke(@NotNull Event event, @NotNull ProfilerFiller profiler) {
         Intrinsics.checkNotNullParameter(event, "event");
         Intrinsics.checkNotNullParameter(profiler, "profiler");
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00028\u0000¢\u0006\u0002\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0015\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019",
      d2 = {
            "Lcom/cb/events/EventBus$EventListener;",
            "T",
            "Lcom/cb/events/Event;",
            "",
            "priority",
            "",
            "ignoreCancelled",
            "",
            "subscriberName",
            "",
            "handler",
            "Lkotlin/Function1;",
            "",
            "<init>",
            "(IZLjava/lang/String;Lkotlin/jvm/functions/Function1;)V",
            "getPriority",
            "()I",
            "getSubscriberName",
            "()Ljava/lang/String;",
            "getHandler",
            "()Lkotlin/jvm/functions/Function1;",
            "checkCancelled",
            "invoke",
            "event",
            "(Lcom/cb/events/Event;)V",
            "cb_client"
      }
   )
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\bÀ\u0006\u0003",
      d2 = {
            "Lcom/cb/events/EventBus$Invoker;",
            "",
            "invoke",
            "",
            "event",
            "Lcom/cb/events/Event;",
            "profiler",
            "Lnet/minecraft/util/profiling/ProfilerFiller;",
            "cb_client"
      }
   )
   public interface Invoker {
      void invoke(@NotNull Event var1, @NotNull ProfilerFiller var2);
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007¢\u0006\u0002\u0010\n¨\u0006\u000b",
      d2 = {
            "Lcom/cb/events/EventBus$InvokerFactory;",
            "",
            "<init>",
            "()V",
            "build",
            "Lcom/cb/events/EventBus$Invoker;",
            "listeners",
            "",
            "Lcom/cb/events/EventBus$EventListener;",
            "Lcom/cb/events/Event;",
            "([Lcom/cb/events/EventBus$EventListener;)Lcom/cb/events/EventBus$Invoker;",
            "cb_client"
      }
   )
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
               EventBus.EventListener[] var3 = listeners;
               int var4 = 0;

               for (int var5 = var3.length; var4 < var5; var4++) {
                  EventBus.EventListener listener = var3[var4];
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B!\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0004\b\b\u0010\tJ\r\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u0005HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007HÆ\u0003J-\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\b\u0002\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007HÆ\u0001J\u0014\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÖ\u0083\u0004J\n\u0010\u0014\u001a\u00020\u0015HÖ\u0081\u0004J\n\u0010\u0016\u001a\u00020\u0017HÖ\u0081\u0004R\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018",
      d2 = {
            "Lcom/cb/events/EventBus$ListenerEntry;",
            "T",
            "Lcom/cb/events/Event;",
            "",
            "subscriber",
            "Ljava/lang/Class;",
            "listener",
            "Lcom/cb/events/EventBus$EventListener;",
            "<init>",
            "(Ljava/lang/Class;Lcom/cb/events/EventBus$EventListener;)V",
            "getSubscriber",
            "()Ljava/lang/Class;",
            "getListener",
            "()Lcom/cb/events/EventBus$EventListener;",
            "component1",
            "component2",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "",
            "toString",
            "",
            "cb_client"
      }
   )
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
         } else if (!(other instanceof EventBus.ListenerEntry var2)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.subscriber, var2.subscriber) ? false : Intrinsics.areEqual(this.listener, var2.listener);
         }
      }
   }
}
