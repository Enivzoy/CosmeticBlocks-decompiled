package com.cb.dungeon;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public enum RoomType {
   BLOOD,
   CHAMPION,
   ENTRANCE,
   FAIRY,
   NORMAL,
   PUZZLE,
   RARE,
   TRAP;

   @NotNull
   public static final RoomType.Companion Companion = new RoomType.Companion();

   @NotNull
   public static EnumEntries<RoomType> getEntries() {
      return kotlin.enums.EnumEntriesKt.enumEntries(values());
   }

   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final RoomType fromString(@NotNull String s) {
         Intrinsics.checkNotNullParameter(s, "s");

         for (RoomType type : RoomType.getEntries()) {
            if (StringsKt.equals(type.name(), s, true)) {
               return type;
            }
         }

         return RoomType.NORMAL;
      }
   }
}
