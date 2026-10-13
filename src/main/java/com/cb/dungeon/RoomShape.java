package com.cb.dungeon;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\f",
   d2 = {"Lcom/cb/dungeon/RoomShape;", "", "<init>", "(Ljava/lang/String;I)V", "S1x1", "S1x2", "S1x3", "S1x4", "L", "S2x2", "UNKNOWN", "Companion", "cb_client"}
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b",
      d2 = {"Lcom/cb/dungeon/RoomShape$Companion;", "", "<init>", "()V", "fromString", "Lcom/cb/dungeon/RoomShape;", "s", "", "cb_client"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final RoomShape fromString(@NotNull String s) {
         Intrinsics.checkNotNullParameter(s, "s");
         String var2 = s;
         switch (var2.hashCode()) {
            case 76:
               if (var2.equals("L")) {
                  return RoomShape.L;
               }
               break;
            case 50858:
               if (var2.equals("1x1")) {
                  return RoomShape.S1x1;
               }
               break;
            case 50859:
               if (var2.equals("1x2")) {
                  return RoomShape.S1x2;
               }
               break;
            case 50860:
               if (var2.equals("1x3")) {
                  return RoomShape.S1x3;
               }
               break;
            case 50861:
               if (var2.equals("1x4")) {
                  return RoomShape.S1x4;
               }
               break;
            case 51820:
               if (var2.equals("2x2")) {
                  return RoomShape.S2x2;
               }
         }

         return RoomShape.UNKNOWN;
      }
   }
}
