package com.cb.dungeon;
import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

public enum Rotation {
   NORTH(15, 15),
   SOUTH(-15, -15),
   WEST(15, -15),
   EAST(-15, 15),
   NONE(0, 0);

   private final int x;
   private final int z;

   Rotation(int x, int z) {
      this.x = x;
      this.z = z;
   }

   public final int getX() {
      return this.x;
   }

   public final int getZ() {
      return this.z;
   }

   @NotNull
   public static EnumEntries<Rotation> getEntries() {
      return kotlin.enums.EnumEntriesKt.enumEntries(values());
   }
}
