package com.cb.features;

import com.cb.events.EventBus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
      int var2 = 0;

      for (int var3 = featureList.length; var2 < var3; var2++) {
         Feature feature = featureList[var2];
         Map var10000 = features;
         String var10001 = feature.getName().toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
         var10000.put(var10001, feature);
         if (feature.getEnabled() && !feature.getAlwaysActive()) {
            EventBus.INSTANCE.subscribe(feature);
         }
      }
   }

   @Nullable
   public final Feature get(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      LinkedHashMap var10000 = features;
      String var10001 = name.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
      return (Feature)var10000.get(var10001);
   }

   @NotNull
   public final List<Feature> enabledFeatures() {
      Collection var10000 = features.values();
      Intrinsics.checkNotNullExpressionValue(var10000, "<get-values>(...)");
      Iterable $this$filter$iv = var10000;
      int $i$f$filter = 0;
      Iterable $this$filterTo$iv$iv = $this$filter$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : $this$filterTo$iv$iv) {
         Feature it = (Feature)element$iv$iv;
         int var9/* $VF was: $i$a$-filter-FeatureManager$enabledFeatures$1 */ = 0;
         if (it.getEnabled()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List<Feature>)destination$iv$iv;
   }
}
