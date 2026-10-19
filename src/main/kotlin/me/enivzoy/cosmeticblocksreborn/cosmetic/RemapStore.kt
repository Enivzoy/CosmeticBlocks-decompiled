package me.enivzoy.cosmeticblocksreborn.cosmetic

interface RemapStore {
   fun sources(): List<String>

   fun targetsOf(source: String): List<GlobalRemap.RemapTarget>

   fun addTarget(source: String, targetId: String, weight: Int)

   fun setWeight(source: String, index: Int, weight: Int)

   fun removeTarget(source: String, index: Int)

   fun removeSource(source: String)

   fun clear()

   fun exportJson(): String

   fun importJson(json: String): Boolean
}
