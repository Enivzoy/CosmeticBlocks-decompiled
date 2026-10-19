package me.enivzoy.cosmeticblocksreborn.dungeon

data class RoomInfo(
   val name: String,
   val type: RoomType,
   val cores: List<Int>,
   val secrets: Int,
   val shape: RoomShape
)
