package com.cb.dungeon;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\r",
   d2 = {
         "Lcom/cb/dungeon/RoomType;",
         "",
         "<init>",
         "(Ljava/lang/String;I)V",
         "BLOOD",
         "CHAMPION",
         "ENTRANCE",
         "FAIRY",
         "NORMAL",
         "PUZZLE",
         "RARE",
         "TRAP",
         "Companion",
         "cb_client"
   }
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b",
      d2 = {"Lcom/cb/dungeon/RoomType$Companion;", "", "<init>", "()V", "fromString", "Lcom/cb/dungeon/RoomType;", "s", "", "cb_client"}
   )
   @SourceDebugExtension(
      "SMAP\nRoomData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomData.kt\ncom/cb/dungeon/RoomType$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n296#2,2:112\n*S KotlinDebug\n*F\n+ 1 RoomData.kt\ncom/cb/dungeon/RoomType$Companion\n*L\n38#1:112,2\n*E\n"
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final RoomType fromString(@NotNull String s) {
         Intrinsics.checkNotNullParameter(s, "s");
         Iterable $this$firstOrNull$iv = RoomType.getEntries();
         int $i$f$firstOrNull = 0;
         Iterator var4 = $this$firstOrNull$iv.iterator();

         RoomType var10000;
         while (true) {
            if (var4.hasNext()) {
               Object element$iv = var4.next();
               RoomType it = (RoomType)element$iv;
               int var7/* $VF was: $i$a$-firstOrNull-RoomType$Companion$fromString$1 */ = 0;
               if (!StringsKt.equals(it.name(), s, true)) {
                  continue;
               }

               var10000 = (RoomType)element$iv;
               break;
            }

            var10000 = null;
            break;
         }

         var10000 = var10000;
         if (var10000 == null) {
            var10000 = RoomType.NORMAL;
         }

         return var10000;
      }
   }
}
