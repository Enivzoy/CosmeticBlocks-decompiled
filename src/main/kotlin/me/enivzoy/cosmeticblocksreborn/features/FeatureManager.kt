package me.enivzoy.cosmeticblocksreborn.features

import me.enivzoy.cosmeticblocksreborn.events.EventBus

object FeatureManager {
   private val map = LinkedHashMap<String, Feature>()

   val features: LinkedHashMap<String, Feature>
      get() = map

   fun register(vararg featureList: Feature) {
      for (feature in featureList) {
         val key = feature.name.lowercase()
         map[key] = feature
         if (feature.enabled && !feature.alwaysActive) {
            EventBus.subscribe(feature)
         }
      }
   }

   fun get(name: String): Feature? = map[name.lowercase()]

   fun enabledFeatures(): List<Feature> = map.values.filter { it.enabled }
}
