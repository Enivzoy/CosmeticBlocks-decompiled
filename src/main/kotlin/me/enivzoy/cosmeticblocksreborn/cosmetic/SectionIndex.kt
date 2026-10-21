package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.concurrent.ConcurrentHashMap
import net.minecraft.core.BlockPos

object SectionIndex {
   @Volatile
   private var sections = ConcurrentHashMap<Long, MutableSet<Long>>()

   @Volatile
   private var version = 0L

   private val lastSection = ThreadLocal<Long>()
   private val lastResult = ThreadLocal<Boolean>()
   private val lastVersion = ThreadLocal<Long>()

   private fun packSection(x: Int, y: Int, z: Int): Long = BlockPos.asLong(x shr 4, y shr 4, z shr 4)

   internal fun build(keys: Collection<Long>): ConcurrentHashMap<Long, MutableSet<Long>> {
      val map = ConcurrentHashMap<Long, MutableSet<Long>>()
      for (packed in keys) {
         val sec = packSection(BlockPos.getX(packed), BlockPos.getY(packed), BlockPos.getZ(packed))
         map.computeIfAbsent(sec) { ConcurrentHashMap.newKeySet<Long>() }.add(packed)
      }

      return map
   }

   internal fun swap(newSections: ConcurrentHashMap<Long, MutableSet<Long>>) {
      sections = newSections
      version++
   }

   internal fun add(packed: Long) {
      val sec = packSection(BlockPos.getX(packed), BlockPos.getY(packed), BlockPos.getZ(packed))
      val bucket = sections.computeIfAbsent(sec) { ConcurrentHashMap.newKeySet<Long>() }
      if (bucket.isEmpty()) {
         version++
      }

      bucket.add(packed)
   }

   internal fun remove(packed: Long) {
      val sec = packSection(BlockPos.getX(packed), BlockPos.getY(packed), BlockPos.getZ(packed))
      val bucket = sections[sec] ?: return
      bucket.remove(packed)
      if (bucket.isEmpty() && sections.remove(sec, bucket)) {
         version++
      }
   }

   internal fun clear() {
      sections = ConcurrentHashMap()
      version++
      lastSection.remove()
      lastResult.remove()
      lastVersion.remove()
   }

   fun hasAny(): Boolean = !sections.isEmpty()

   fun hasSectionAt(x: Int, y: Int, z: Int): Boolean {
      val sec = packSection(x, y, z)
      val current = version
      if (lastSection.get() == sec && lastVersion.get() == current) {
         return lastResult.get() == true
      }

      val result = sections.containsKey(sec)
      lastSection.set(sec)
      lastResult.set(result)
      lastVersion.set(current)
      return result
   }

   internal fun occupiedSections(): Set<Long> = sections.keys

   internal fun sectionCount(): Int = sections.size

   internal fun entryCount(): Int {
      var total = 0
      for (bucket in sections.values) {
         total += bucket.size
      }

      return total
   }

   internal fun currentVersion(): Long = version
}
