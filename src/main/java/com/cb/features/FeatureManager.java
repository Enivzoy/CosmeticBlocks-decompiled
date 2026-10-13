package com.cb.features;

import com.cb.events.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001f\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u000e\"\u00020\u0007¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0011\u001a\u00020\u0006J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013R-\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005j\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014",
   d2 = {
         "Lcom/cb/features/FeatureManager;",
         "",
         "<init>",
         "()V",
         "features",
         "Ljava/util/LinkedHashMap;",
         "",
         "Lcom/cb/features/Feature;",
         "Lkotlin/collections/LinkedHashMap;",
         "getFeatures",
         "()Ljava/util/LinkedHashMap;",
         "register",
         "",
         "featureList",
         "",
         "([Lcom/cb/features/Feature;)V",
         "get",
         "name",
         "enabledFeatures",
         "",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nFeatureManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FeatureManager.kt\ncom/cb/features/FeatureManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,36:1\n777#2:37\n873#2,2:38\n*S KotlinDebug\n*F\n+ 1 FeatureManager.kt\ncom/cb/features/FeatureManager\n*L\n34#1:37\n34#1:38,2\n*E\n"
)
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
