package me.enivzoy.cosmeticblocksreborn.dungeon

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis

fun BlockPos.rotateToNorth(rotation: Rotation): BlockPos = when (rotation) {
   Rotation.NORTH -> BlockPos(-this.x, this.y, -this.z)
   Rotation.WEST -> BlockPos(this.z, this.y, -this.x)
   Rotation.SOUTH -> BlockPos(this.x, this.y, this.z)
   Rotation.EAST -> BlockPos(-this.z, this.y, this.x)
   Rotation.NONE -> this
}

fun BlockPos.rotateAroundNorth(rotation: Rotation): BlockPos = when (rotation) {
   Rotation.NORTH -> BlockPos(-this.x, this.y, -this.z)
   Rotation.WEST -> BlockPos(-this.z, this.y, this.x)
   Rotation.SOUTH -> BlockPos(this.x, this.y, this.z)
   Rotation.EAST -> BlockPos(this.z, this.y, -this.x)
   Rotation.NONE -> this
}

fun Direction.rotateToNorth(rotation: Rotation): Direction {
   if (this == Direction.UP || this == Direction.DOWN) {
      return this
   }

   val rotated = BlockPos(this.stepX, 0, this.stepZ).rotateToNorth(rotation)
   return Direction.getNearest(rotated.x, 0, rotated.z, this) ?: this
}

fun Direction.rotateAroundNorth(rotation: Rotation): Direction {
   if (this == Direction.UP || this == Direction.DOWN) {
      return this
   }

   val rotated = BlockPos(this.stepX, 0, this.stepZ).rotateAroundNorth(rotation)
   return Direction.getNearest(rotated.x, 0, rotated.z, this) ?: this
}

fun Axis.rotateToNorth(rotation: Rotation): Axis = when (rotation) {
   Rotation.WEST, Rotation.EAST -> when (this) {
      Axis.X -> Axis.Z
      Axis.Z -> Axis.X
      else -> this
   }

   else -> this
}

fun Axis.rotateAroundNorth(rotation: Rotation): Axis = this.rotateToNorth(rotation)
