package me.enivzoy.cosmeticblocksreborn.cosmetic

import it.unimi.dsi.fastutil.longs.LongArrayList
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos

internal object ReplacementCompactor {
   private const val INVALID_POS = Long.MIN_VALUE

   internal fun compress(dimMap: Map<String, String>): LinkedHashMap<String, MutableList<IntArray>> {
      val groups = LinkedHashMap<String, LongArrayList>()
      for ((pos, raw) in dimMap) {
         val packed = parsePacked(pos)
         if (packed == INVALID_POS) {
            continue
         }

         groups.getOrPut(raw) { LongArrayList() }.add(packed)
      }

      if (groups.isEmpty()) {
         return LinkedHashMap()
      }

      val compressed = LinkedHashMap<String, MutableList<IntArray>>(groups.size * 2)
      for ((raw, positions) in groups) {
         val boxes = greedyBoxes(positions)
         if (boxes.isNotEmpty()) {
            compressed[raw] = boxes
         }
      }

      return compressed
   }

   internal fun expand(raw: String, ranges: Iterable<IntArray>, target: MutableMap<String, String>) {
      for (range in ranges) {
         if (range.size < 6) {
            continue
         }

         val x1 = minOf(range[0], range[3])
         val x2 = maxOf(range[0], range[3])
         val y1 = minOf(range[1], range[4])
         val y2 = maxOf(range[1], range[4])
         val z1 = minOf(range[2], range[5])
         val z2 = maxOf(range[2], range[5])
         for (x in x1..x2) {
            for (y in y1..y2) {
               for (z in z1..z2) {
                  target[x.toString() + "," + y + "," + z] = raw
               }
            }
         }
      }
   }

   private fun greedyBoxes(positions: LongArrayList): MutableList<IntArray> {
      val remaining = LongOpenHashSet(positions.size)
      for (i in 0 until positions.size) {
         remaining.add(positions.getLong(i))
      }

      val boxes = ArrayList<IntArray>()
      for (i in 0 until positions.size) {
         val packed = positions.getLong(i)
         if (!remaining.contains(packed)) {
            continue
         }

         val x = BlockPos.getX(packed)
         val y = BlockPos.getY(packed)
         val z = BlockPos.getZ(packed)

         var sizeX = 1
         while (remaining.contains(BlockPos.asLong(x + sizeX, y, z))) {
            sizeX++
         }

         var sizeZ = 1
         while (xLineFilled(remaining, x, y, z + sizeZ, sizeX)) {
            sizeZ++
         }

         var sizeY = 1
         while (xzPlaneFilled(remaining, x, y + sizeY, z, sizeX, sizeZ)) {
            sizeY++
         }

         for (dy in 0 until sizeY) {
            for (dz in 0 until sizeZ) {
               for (dx in 0 until sizeX) {
                  remaining.remove(BlockPos.asLong(x + dx, y + dy, z + dz))
               }
            }
         }

         boxes.add(intArrayOf(x, y, z, x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1))
      }

      return boxes
   }

   private fun xLineFilled(remaining: LongOpenHashSet, x: Int, y: Int, z: Int, sizeX: Int): Boolean {
      for (dx in 0 until sizeX) {
         if (!remaining.contains(BlockPos.asLong(x + dx, y, z))) {
            return false
         }
      }

      return true
   }

   private fun xzPlaneFilled(remaining: LongOpenHashSet, x: Int, y: Int, z: Int, sizeX: Int, sizeZ: Int): Boolean {
      for (dz in 0 until sizeZ) {
         for (dx in 0 until sizeX) {
            if (!remaining.contains(BlockPos.asLong(x + dx, y, z + dz))) {
               return false
            }
         }
      }

      return true
   }

   private fun parsePacked(pos: String): Long {
      val length = pos.length
      var i = 0
      var sign = 1
      if (i < length && pos[i] == '-') {
         sign = -1
         i++
      }

      var digits = 0
      var value = 0L
      while (i < length && pos[i] in '0'..'9') {
         value = value * 10 + (pos[i] - '0')
         i++
         digits++
      }

      if (digits == 0) {
         return INVALID_POS
      }

      val x = (value * sign).toInt()
      if (i >= length || pos[i] != ',') {
         return INVALID_POS
      }

      i++
      sign = 1
      if (i < length && pos[i] == '-') {
         sign = -1
         i++
      }

      digits = 0
      value = 0L
      while (i < length && pos[i] in '0'..'9') {
         value = value * 10 + (pos[i] - '0')
         i++
         digits++
      }

      if (digits == 0) {
         return INVALID_POS
      }

      val y = (value * sign).toInt()
      if (i >= length || pos[i] != ',') {
         return INVALID_POS
      }

      i++
      sign = 1
      if (i < length && pos[i] == '-') {
         sign = -1
         i++
      }

      digits = 0
      value = 0L
      while (i < length && pos[i] in '0'..'9') {
         value = value * 10 + (pos[i] - '0')
         i++
         digits++
      }

      if (digits == 0 || i != length) {
         return INVALID_POS
      }

      val z = (value * sign).toInt()
      return BlockPos.asLong(x, y, z)
   }
}
