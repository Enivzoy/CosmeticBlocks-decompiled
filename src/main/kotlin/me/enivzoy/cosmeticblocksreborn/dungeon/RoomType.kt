package me.enivzoy.cosmeticblocksreborn.dungeon

enum class RoomType {
   BLOOD,
   CHAMPION,
   ENTRANCE,
   FAIRY,
   NORMAL,
   PUZZLE,
   RARE,
   TRAP;

   companion object {
      fun fromString(s: String): RoomType {
         for (type in entries) {
            if (type.name.equals(s, ignoreCase = true)) {
               return type
            }
         }

         return NORMAL
      }
   }
}
