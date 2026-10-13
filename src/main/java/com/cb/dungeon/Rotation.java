package com.cb.dungeon;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f",
   d2 = {
         "Lcom/cb/dungeon/Rotation;",
         "",
         "x",
         "",
         "z",
         "<init>",
         "(Ljava/lang/String;III)V",
         "getX",
         "()I",
         "getZ",
         "NORTH",
         "SOUTH",
         "WEST",
         "EAST",
         "NONE",
         "cb_client"
   }
)
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
