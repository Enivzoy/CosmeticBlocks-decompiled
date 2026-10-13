package com.cb.events;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1\n+ 2 EventBus.kt\ncom/cb/events/EventBus\n*L\n1#1,328:1\n57#2:329\n*E\n"
)
public final class EventBus$registerListener$$inlined$sortedByDescending$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      EventBus.ListenerEntry entryA = (EventBus.ListenerEntry)a;
      EventBus.ListenerEntry entryB = (EventBus.ListenerEntry)b;
      return ComparisonsKt.compareValues(entryB.getListener().getPriority(), entryA.getListener().getPriority());
   }
}
