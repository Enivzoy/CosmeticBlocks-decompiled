package com.cb.dungeon;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public enum RoomShape {
   S1x1,
   S1x2,
   S1x3,
   S1x4,
   L,
   S2x2,
   UNKNOWN;

   @NotNull
   public static final RoomShape.Companion Companion = new RoomShape.Companion();

   @NotNull
   public static EnumEntries<RoomShape> getEntries() {
      return kotlin.enums.EnumEntriesKt.enumEntries(values());
   }

   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final RoomShape fromString(@NotNull String s) {
         Intrinsics.checkNotNullParameter(s, "s");
         switch (s.hashCode()) {
            case 76:
               if (s.equals("L")) {
                  return RoomShape.L;
               }
               break;
            case 50858:
               if (s.equals("1x1")) {
                  return RoomShape.S1x1;
               }
               break;
            case 50859:
               if (s.equals("1x2")) {
                  return RoomShape.S1x2;
               }
               break;
            case 50860:
               if (s.equals("1x3")) {
                  return RoomShape.S1x3;
               }
               break;
            case 50861:
               if (s.equals("1x4")) {
                  return RoomShape.S1x4;
               }
               break;
            case 51820:
               if (s.equals("2x2")) {
                  return RoomShape.S2x2;
               }
         }

         return RoomShape.UNKNOWN;
      }
   }
}
