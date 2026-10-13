/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.dungeon;

import com.cb.dungeon.Rotation;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=2, xi=48, d1={"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u00a8\u0006\u0007"}, d2={"rotateToNorth", "Lnet/minecraft/core/BlockPos;", "rotation", "Lcom/cb/dungeon/Rotation;", "rotateAroundNorth", "Lnet/minecraft/core/Direction;", "Lnet/minecraft/core/Direction$Axis;", "cb_client"})
public final class RoomDataKt {
    @NotNull
    public static final BlockPos rotateToNorth(@NotNull BlockPos $this$rotateToNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateToNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        return switch (WhenMappings.$EnumSwitchMapping$0[rotation.ordinal()]) {
            case 1 -> new BlockPos(-$this$rotateToNorth.getX(), $this$rotateToNorth.getY(), -$this$rotateToNorth.getZ());
            case 2 -> new BlockPos($this$rotateToNorth.getZ(), $this$rotateToNorth.getY(), -$this$rotateToNorth.getX());
            case 3 -> new BlockPos($this$rotateToNorth.getX(), $this$rotateToNorth.getY(), $this$rotateToNorth.getZ());
            case 4 -> new BlockPos(-$this$rotateToNorth.getZ(), $this$rotateToNorth.getY(), $this$rotateToNorth.getX());
            case 5 -> $this$rotateToNorth;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public static final BlockPos rotateAroundNorth(@NotNull BlockPos $this$rotateAroundNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateAroundNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        return switch (WhenMappings.$EnumSwitchMapping$0[rotation.ordinal()]) {
            case 1 -> new BlockPos(-$this$rotateAroundNorth.getX(), $this$rotateAroundNorth.getY(), -$this$rotateAroundNorth.getZ());
            case 2 -> new BlockPos(-$this$rotateAroundNorth.getZ(), $this$rotateAroundNorth.getY(), $this$rotateAroundNorth.getX());
            case 3 -> new BlockPos($this$rotateAroundNorth.getX(), $this$rotateAroundNorth.getY(), $this$rotateAroundNorth.getZ());
            case 4 -> new BlockPos($this$rotateAroundNorth.getZ(), $this$rotateAroundNorth.getY(), -$this$rotateAroundNorth.getX());
            case 5 -> $this$rotateAroundNorth;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public static final Direction rotateToNorth(@NotNull Direction $this$rotateToNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateToNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        if ($this$rotateToNorth == Direction.UP || $this$rotateToNorth == Direction.DOWN) {
            return $this$rotateToNorth;
        }
        BlockPos rotated = RoomDataKt.rotateToNorth(new BlockPos($this$rotateToNorth.getStepX(), 0, $this$rotateToNorth.getStepZ()), rotation);
        Direction direction = Direction.getNearest((int)rotated.getX(), (int)0, (int)rotated.getZ(), (Direction)$this$rotateToNorth);
        if (direction == null) {
            direction = $this$rotateToNorth;
        }
        return direction;
    }

    @NotNull
    public static final Direction rotateAroundNorth(@NotNull Direction $this$rotateAroundNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateAroundNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        if ($this$rotateAroundNorth == Direction.UP || $this$rotateAroundNorth == Direction.DOWN) {
            return $this$rotateAroundNorth;
        }
        BlockPos rotated = RoomDataKt.rotateAroundNorth(new BlockPos($this$rotateAroundNorth.getStepX(), 0, $this$rotateAroundNorth.getStepZ()), rotation);
        Direction direction = Direction.getNearest((int)rotated.getX(), (int)0, (int)rotated.getZ(), (Direction)$this$rotateAroundNorth);
        if (direction == null) {
            direction = $this$rotateAroundNorth;
        }
        return direction;
    }

    @NotNull
    public static final Direction.Axis rotateToNorth(@NotNull Direction.Axis $this$rotateToNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateToNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        return switch (WhenMappings.$EnumSwitchMapping$0[rotation.ordinal()]) {
            case 2, 4 -> {
                switch (WhenMappings.$EnumSwitchMapping$1[$this$rotateToNorth.ordinal()]) {
                    case 1: {
                        yield Direction.Axis.Z;
                    }
                    case 2: {
                        yield Direction.Axis.X;
                    }
                }
                yield $this$rotateToNorth;
            }
            default -> $this$rotateToNorth;
        };
    }

    @NotNull
    public static final Direction.Axis rotateAroundNorth(@NotNull Direction.Axis $this$rotateAroundNorth, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)$this$rotateAroundNorth, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        return RoomDataKt.rotateToNorth($this$rotateAroundNorth, rotation);
    }

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] nArray = new int[Rotation.values().length];
            try {
                nArray[Rotation.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Rotation.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Rotation.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Rotation.EAST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Rotation.NONE.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
            nArray = new int[Direction.Axis.values().length];
            try {
                nArray[Direction.Axis.X.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.Axis.Z.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$1 = nArray;
        }
    }
}


