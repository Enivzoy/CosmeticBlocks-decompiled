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
      EventBus.ListenerEntry it = (EventBus.ListenerEntry)b;
      int var4/* $VF was: $i$a$-sortedByDescending-EventBus$registerListener$newArray$1 */ = 0;
      Comparable var10000 = it.getListener().getPriority();
      it = (EventBus.ListenerEntry)a;
      Comparable var5 = var10000;
      var4/* $VF was: $i$a$-sortedByDescending-EventBus$registerListener$newArray$1 */ = 0;
      return ComparisonsKt.compareValues(var5, it.getListener().getPriority());
   }
}
