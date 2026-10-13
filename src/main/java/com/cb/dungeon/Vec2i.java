package com.cb.dungeon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Vec2i {
   private final int x;
   private final int z;

   public Vec2i(int x, int z) {
      this.x = x;
      this.z = z;
   }

   public final int getX() {
      return this.x;
   }

   public final int getZ() {
      return this.z;
   }

   public final int component1() {
      return this.x;
   }

   public final int component2() {
      return this.z;
   }

   @NotNull
   public final Vec2i copy(int x, int z) {
      return new Vec2i(x, z);
   }

   @NotNull
   @Override
   public String toString() {
      return "Vec2i(x=" + this.x + ", z=" + this.z + ")";
   }

   @Override
   public int hashCode() {
      int result = Integer.hashCode(this.x);
      return result * 31 + Integer.hashCode(this.z);
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Vec2i that)) {
         return false;
      } else {
         return this.x != that.x ? false : this.z == that.z;
      }
   }
}
