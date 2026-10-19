package me.enivzoy.cosmeticblocksreborn.gui;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

public final class ProfileManager$listProfiles$$inlined$sortedByDescending$1<T> implements Comparator {
   @Override
   public final int compare(Object a, Object b) {
      ProfileManager.ProfileInfo it = (ProfileManager.ProfileInfo)b;
      Comparable compareValue = it.getLastModified();
      it = (ProfileManager.ProfileInfo)a;
      return ComparisonsKt.compareValues(compareValue, it.getLastModified());
   }
}
