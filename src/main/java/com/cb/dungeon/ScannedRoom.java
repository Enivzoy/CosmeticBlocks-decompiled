package com.cb.dungeon;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ScannedRoom {
   @NotNull
   private final RoomInfo data;
   @NotNull
   private Rotation rotation;
   @NotNull
   private BlockPos clayPos;
   @NotNull
   private final Set<Vec2i> components;

   public ScannedRoom(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      Intrinsics.checkNotNullParameter(clayPos, "clayPos");
      Intrinsics.checkNotNullParameter(components, "components");
      super();
      this.data = data;
      this.rotation = rotation;
      this.clayPos = clayPos;
      this.components = components;
   }

   @NotNull
   public final RoomInfo getData() {
      return this.data;
   }

   @NotNull
   public final Rotation getRotation() {
      return this.rotation;
   }

   public final void setRotation(@NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(rotation, "<set-?>");
      this.rotation = rotation;
   }

   @NotNull
   public final BlockPos getClayPos() {
      return this.clayPos;
   }

   public final void setClayPos(@NotNull BlockPos clayPos) {
      Intrinsics.checkNotNullParameter(clayPos, "<set-?>");
      this.clayPos = clayPos;
   }

   @NotNull
   public final Set<Vec2i> getComponents() {
      return this.components;
   }

   @NotNull
   public final RoomInfo component1() {
      return this.data;
   }

   @NotNull
   public final Rotation component2() {
      return this.rotation;
   }

   @NotNull
   public final BlockPos component3() {
      return this.clayPos;
   }

   @NotNull
   public final Set<Vec2i> component4() {
      return this.components;
   }

   @NotNull
   public final ScannedRoom copy(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
      Intrinsics.checkNotNullParameter(data, "data");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      Intrinsics.checkNotNullParameter(clayPos, "clayPos");
      Intrinsics.checkNotNullParameter(components, "components");
      return new ScannedRoom(data, rotation, clayPos, components);
   }

   @NotNull
   @Override
   public String toString() {
      return "ScannedRoom(data=" + this.data + ", rotation=" + this.rotation + ", clayPos=" + this.clayPos + ", components=" + this.components + ")";
   }

   @Override
   public int hashCode() {
      int result = this.data.hashCode();
      result = result * 31 + this.rotation.hashCode();
      result = result * 31 + this.clayPos.hashCode();
      return result * 31 + this.components.hashCode();
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ScannedRoom that)) {
         return false;
      } else if (!Intrinsics.areEqual(this.data, that.data)) {
         return false;
      } else if (this.rotation != that.rotation) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.clayPos, that.clayPos) ? false : Intrinsics.areEqual(this.components, that.components);
      }
   }
}
