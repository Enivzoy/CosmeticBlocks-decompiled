package com.cb.features;

import com.cb.events.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FeatureManager {
   @NotNull
   public static final FeatureManager INSTANCE = new FeatureManager();
   @NotNull
   private static final LinkedHashMap<String, Feature> features = new LinkedHashMap<>();

   private FeatureManager() {
   }

   @NotNull
   public final LinkedHashMap<String, Feature> getFeatures() {
      return features;
   }

   public final void register(@NotNull Feature... featureList) {
      Intrinsics.checkNotNullParameter(featureList, "featureList");

      for (Feature feature : featureList) {
         String key = feature.getName().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(key, "toLowerCase(...)");
         features.put(key, feature);
         if (feature.getEnabled() && !feature.getAlwaysActive()) {
            EventBus.INSTANCE.subscribe(feature);
         }
      }
   }

   @Nullable
   public final Feature get(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String key = name.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(key, "toLowerCase(...)");
      return features.get(key);
   }

   @NotNull
   public final List<Feature> enabledFeatures() {
      Collection<Feature> allFeatures = features.values();
      Intrinsics.checkNotNullExpressionValue(allFeatures, "<get-values>(...)");
      List<Feature> result = new ArrayList<>();

      for (Feature feature : allFeatures) {
         if (feature.getEnabled()) {
            result.add(feature);
         }
      }

      return result;
   }
}
