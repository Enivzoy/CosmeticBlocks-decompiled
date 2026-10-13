package com.cb.dungeon;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import org.jetbrains.annotations.NotNull;

public final class RoomDataKt {
   @NotNull
   public static final BlockPos rotateToNorth(@NotNull BlockPos pos, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(pos, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case NORTH -> new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
         case WEST -> new BlockPos(pos.getZ(), pos.getY(), -pos.getX());
         case SOUTH -> new BlockPos(pos.getX(), pos.getY(), pos.getZ());
         case EAST -> new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
         case NONE -> pos;
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   @NotNull
   public static final BlockPos rotateAroundNorth(@NotNull BlockPos pos, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(pos, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case NORTH -> new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
         case WEST -> new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
         case SOUTH -> new BlockPos(pos.getX(), pos.getY(), pos.getZ());
         case EAST -> new BlockPos(pos.getZ(), pos.getY(), -pos.getX());
         case NONE -> pos;
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   @NotNull
   public static final Direction rotateToNorth(@NotNull Direction direction, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(direction, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      if (direction != Direction.UP && direction != Direction.DOWN) {
         BlockPos rotated = rotateToNorth(new BlockPos(direction.getStepX(), 0, direction.getStepZ()), rotation);
         Direction result = Direction.getNearest(rotated.getX(), 0, rotated.getZ(), direction);
         if (result == null) {
            result = direction;
         }

         return result;
      } else {
         return direction;
      }
   }

   @NotNull
   public static final Direction rotateAroundNorth(@NotNull Direction direction, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(direction, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      if (direction != Direction.UP && direction != Direction.DOWN) {
         BlockPos rotated = rotateAroundNorth(new BlockPos(direction.getStepX(), 0, direction.getStepZ()), rotation);
         Direction result = Direction.getNearest(rotated.getX(), 0, rotated.getZ(), direction);
         if (result == null) {
            result = direction;
         }

         return result;
      } else {
         return direction;
      }
   }

   @NotNull
   public static final Axis rotateToNorth(@NotNull Axis axis, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(axis, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case WEST, EAST -> {
            switch (axis) {
               case X:
                  yield Axis.Z;
               case Z:
                  yield Axis.X;
               default:
                  yield axis;
            }
         }
         default -> axis;
      };
   }

   @NotNull
   public static final Axis rotateAroundNorth(@NotNull Axis axis, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(axis, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return rotateToNorth(axis, rotation);
   }
}
