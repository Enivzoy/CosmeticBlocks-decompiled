package me.enivzoy.cosmeticblocksreborn.events

import java.util.HashMap
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic
import net.minecraft.util.profiling.Profiler
import net.minecraft.util.profiling.ProfilerFiller

object EventBus {
   @JvmField
   val listenerArrays: MutableMap<Class<out Event>, Array<ListenerEntry<out Event>>> = LinkedHashMap()

   @JvmField
   val activeSubscribers: MutableSet<Any> = LinkedHashSet()

   @JvmField
   val subscriberClasses: MutableMap<Any, Class<*>> = LinkedHashMap()

   @JvmField
   val invokers: HashMap<Class<out Event>, Invoker> = HashMap()

   private val profilerNameCache = HashMap<Class<out Event>, String>()

   fun subscribe(subscriber: Any) {
      if (activeSubscribers.add(subscriber)) {
         subscriberClasses[subscriber] = subscriber.javaClass
         this.rebuildAffectedCaches(subscriber.javaClass)
      }
   }

   fun unsubscribe(subscriber: Any) {
      if (activeSubscribers.remove(subscriber)) {
         val subscriberClass = subscriberClasses.remove(subscriber)
         if (subscriberClass != null) {
            this.rebuildAffectedCaches(subscriberClass)
         }
      }
   }

   @JvmStatic
   fun <T : Event> post(event: T) {
      val eventClass = event.javaClass
      val invoker = invokers[eventClass] ?: return
      val profiler = Profiler.get()
      var profilerName = profilerNameCache[eventClass]
      if (profilerName == null) {
         profilerName = "CB: " + eventClass.simpleName
         profilerNameCache[eventClass] = profilerName
      }

      profiler.push(profilerName)

      try {
         invoker.invoke(event, profiler)
      } finally {
         profiler.pop()
      }
   }

   fun <T : Event> registerListener(
      subscriberClass: Class<*>,
      eventClass: Class<T>,
      priority: Int,
      ignoreCancelled: Boolean,
      handler: (T) -> Unit
   ) {
      val simpleName = subscriberClass.simpleName
      val resolvedName = if (simpleName.isEmpty()) subscriberClass.name else simpleName

      val newEntry = ListenerEntry(subscriberClass, EventListener(priority, ignoreCancelled, resolvedName, handler))
      val existingListeners: MutableList<ListenerEntry<out Event>> =
         listenerArrays[eventClass]?.toMutableList() ?: mutableListOf()

      existingListeners.add(newEntry)
      existingListeners.sortByDescending { it.listener.priority }

      val newArray = existingListeners.toTypedArray()
      listenerArrays[eventClass] = newArray
      this.rebuildInvoker(eventClass, newArray)
   }

   private fun rebuildAffectedCaches(changedClass: Class<*>) {
      for ((eventClass, listeners) in listenerArrays) {
         val hasSubscriber = listeners.any { it.subscriber == changedClass }
         if (hasSubscriber) {
            this.rebuildInvoker(eventClass, listeners)
         }
      }
   }

   @Suppress("UNCHECKED_CAST")
   private fun rebuildInvoker(eventClass: Class<out Event>, allListeners: Array<ListenerEntry<out Event>>) {
      if (activeSubscribers.isEmpty()) {
         invokers[eventClass] = EmptyInvoker
      } else {
         val activeClasses = subscriberClasses.values
         val invokersArray = allListeners
            .filter { activeClasses.contains(it.subscriber) }
            .map { it.listener as EventListener<Event> }
            .toTypedArray()

         invokers[eventClass] =
            if (invokersArray.isEmpty()) EmptyInvoker else InvokerFactory.build(invokersArray)
      }
   }

   private object EmptyInvoker : Invoker {
      override fun invoke(event: Event, profiler: ProfilerFiller) {
      }
   }

   class EventListener<T : Event>(
      val priority: Int,
      val checkCancelled: Boolean,
      val subscriberName: String,
      private val handler: (T) -> Unit
   ) {
      fun invoke(event: T) {
         if (!this.checkCancelled || event !is CancellableEvent || !event.isCancelled) {
            this.handler.invoke(event)
         }
      }
   }

   fun interface Invoker {
      fun invoke(event: Event, profiler: ProfilerFiller)
   }

   private object InvokerFactory {
      fun build(listeners: Array<EventListener<Event>>): Invoker = Invoker { event, profiler ->
         for (listener in listeners) {
            profiler.push(listener.subscriberName)

            try {
               listener.invoke(event)
            } finally {
               profiler.pop()
            }
         }
      }
   }

   data class ListenerEntry<T : Event>(
      val subscriber: Class<*>,
      val listener: EventListener<T>
   )
}
