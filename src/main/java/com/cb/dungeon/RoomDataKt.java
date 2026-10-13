package com.cb.dungeon;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0007",
   d2 = {
         "rotateToNorth",
         "Lnet/minecraft/core/BlockPos;",
         "rotation",
         "Lcom/cb/dungeon/Rotation;",
         "rotateAroundNorth",
         "Lnet/minecraft/core/Direction;",
         "Lnet/minecraft/core/Direction$Axis;",
         "cb_client"
   }
)
public final class RoomDataKt {
   @NotNull
   public static final BlockPos rotateToNorth(@NotNull BlockPos $this$rotateToNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateToNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case NORTH -> new BlockPos(-$this$rotateToNorth.getX(), $this$rotateToNorth.getY(), -$this$rotateToNorth.getZ());
         case WEST -> new BlockPos($this$rotateToNorth.getZ(), $this$rotateToNorth.getY(), -$this$rotateToNorth.getX());
         case SOUTH -> new BlockPos($this$rotateToNorth.getX(), $this$rotateToNorth.getY(), $this$rotateToNorth.getZ());
         case EAST -> new BlockPos(-$this$rotateToNorth.getZ(), $this$rotateToNorth.getY(), $this$rotateToNorth.getX());
         case NONE -> $this$rotateToNorth;
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   @NotNull
   public static final BlockPos rotateAroundNorth(@NotNull BlockPos $this$rotateAroundNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateAroundNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case NORTH -> new BlockPos(-$this$rotateAroundNorth.getX(), $this$rotateAroundNorth.getY(), -$this$rotateAroundNorth.getZ());
         case WEST -> new BlockPos(-$this$rotateAroundNorth.getZ(), $this$rotateAroundNorth.getY(), $this$rotateAroundNorth.getX());
         case SOUTH -> new BlockPos($this$rotateAroundNorth.getX(), $this$rotateAroundNorth.getY(), $this$rotateAroundNorth.getZ());
         case EAST -> new BlockPos($this$rotateAroundNorth.getZ(), $this$rotateAroundNorth.getY(), -$this$rotateAroundNorth.getX());
         case NONE -> $this$rotateAroundNorth;
         default -> throw new NoWhenBranchMatchedException();
      };
   }

   @NotNull
   public static final Direction rotateToNorth(@NotNull Direction $this$rotateToNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateToNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      if ($this$rotateToNorth != Direction.UP && $this$rotateToNorth != Direction.DOWN) {
         BlockPos rotated = rotateToNorth(new BlockPos($this$rotateToNorth.getStepX(), 0, $this$rotateToNorth.getStepZ()), rotation);
         Direction var10000 = Direction.getNearest(rotated.getX(), 0, rotated.getZ(), $this$rotateToNorth);
         if (var10000 == null) {
            var10000 = $this$rotateToNorth;
         }

         return var10000;
      } else {
         return $this$rotateToNorth;
      }
   }

   @NotNull
   public static final Direction rotateAroundNorth(@NotNull Direction $this$rotateAroundNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateAroundNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      if ($this$rotateAroundNorth != Direction.UP && $this$rotateAroundNorth != Direction.DOWN) {
         BlockPos rotated = rotateAroundNorth(new BlockPos($this$rotateAroundNorth.getStepX(), 0, $this$rotateAroundNorth.getStepZ()), rotation);
         Direction var10000 = Direction.getNearest(rotated.getX(), 0, rotated.getZ(), $this$rotateAroundNorth);
         if (var10000 == null) {
            var10000 = $this$rotateAroundNorth;
         }

         return var10000;
      } else {
         return $this$rotateAroundNorth;
      }
   }

   @NotNull
   public static final Axis rotateToNorth(@NotNull Axis $this$rotateToNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateToNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");

      return switch (rotation) {
         case WEST, EAST -> {
            switch ($this$rotateToNorth) {
               case X:
                  yield Axis.Z;
               case Z:
                  yield Axis.X;
               default:
                  yield $this$rotateToNorth;
            }
         }
         default -> $this$rotateToNorth;
      };
   }

   @NotNull
   public static final Axis rotateAroundNorth(@NotNull Axis $this$rotateAroundNorth, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter($this$rotateAroundNorth, "<this>");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return rotateToNorth($this$rotateAroundNorth, rotation);
   }
}
