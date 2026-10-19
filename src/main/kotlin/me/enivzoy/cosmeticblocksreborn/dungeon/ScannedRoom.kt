package me.enivzoy.cosmeticblocksreborn.dungeon

import net.minecraft.core.BlockPos

class ScannedRoom(
   val data: RoomInfo,
   var rotation: Rotation,
   var clayPos: BlockPos,
   val components: Set<Vec2i>
)
