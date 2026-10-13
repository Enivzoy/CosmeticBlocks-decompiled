package com.cb.gui;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.SourceDebugExtension;

@Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareByDescending$1\n+ 2 ProfileManager.kt\ncom/cb/gui/ProfileManager\n*L\n1#1,328:1\n47#2:329\n*E\n"
)
public final class ProfileManager$listProfiles$$inlined$sortedByDescending$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      ProfileManager.ProfileInfo it = (ProfileManager.ProfileInfo)b;
      Comparable compareValue = it.getLastModified();
      it = (ProfileManager.ProfileInfo)a;
      return ComparisonsKt.compareValues(compareValue, it.getLastModified());
   }
}
