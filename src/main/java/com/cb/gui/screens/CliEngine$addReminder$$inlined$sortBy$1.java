package com.cb.gui.screens;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 CliEngine.kt\ncom/cb/gui/screens/CliEngine\n*L\n1#1,328:1\n105#2:329\n*E\n"
)
public final class CliEngine$addReminder$$inlined$sortBy$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      CliEngine.Reminder it = (CliEngine.Reminder)a;
      Comparable compareValue = it.getTriggerTime();
      it = (CliEngine.Reminder)b;
      return ComparisonsKt.compareValues(compareValue, it.getTriggerTime());
   }
}
