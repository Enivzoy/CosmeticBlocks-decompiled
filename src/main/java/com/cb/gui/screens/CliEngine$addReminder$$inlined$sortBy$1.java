package com.cb.gui.screens;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

public final class CliEngine$addReminder$$inlined$sortBy$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      CliEngine.Reminder it = (CliEngine.Reminder)a;
      Comparable compareValue = it.getTriggerTime();
      it = (CliEngine.Reminder)b;
      return ComparisonsKt.compareValues(compareValue, it.getTriggerTime());
   }
}
