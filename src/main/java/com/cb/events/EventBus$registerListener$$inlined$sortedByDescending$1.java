package com.cb.events;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

public final class EventBus$registerListener$$inlined$sortedByDescending$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      EventBus.ListenerEntry entryA = (EventBus.ListenerEntry)a;
      EventBus.ListenerEntry entryB = (EventBus.ListenerEntry)b;
      return ComparisonsKt.compareValues(entryB.getListener().getPriority(), entryA.getListener().getPriority());
   }
}
