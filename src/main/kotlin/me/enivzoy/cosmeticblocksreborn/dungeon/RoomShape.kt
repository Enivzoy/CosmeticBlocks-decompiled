package me.enivzoy.cosmeticblocksreborn.dungeon

enum class RoomShape {
   S1x1,
   S1x2,
   S1x3,
   S1x4,
   L,
   S2x2,
   UNKNOWN;

   companion object {
      fun fromString(s: String): RoomShape = when (s) {
         "1x1" -> S1x1
         "1x2" -> S1x2
         "1x3" -> S1x3
         "1x4" -> S1x4
         "L" -> L
         "2x2" -> S2x2
         else -> UNKNOWN
      }
   }
}
