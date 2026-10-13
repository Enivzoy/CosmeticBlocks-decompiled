package com.cb.util;

import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n",
   d2 = {"Lcom/cb/util/KeyNames;", "", "<init>", "()V", "SPECIAL", "", "", "", "name", "key", "cb_client"}
)
@SourceDebugExtension(
   "SMAP\nKeyNames.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KeyNames.kt\ncom/cb/util/KeyNames\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,56:1\n1#2:57\n*E\n"
)
public final class KeyNames {
   @NotNull
   public static final KeyNames INSTANCE = new KeyNames();
   @NotNull
   private static final Map<Integer, String> SPECIAL;

   private KeyNames() {
   }

   @NotNull
   public final String name(int key) {
      if (key == -1) {
         return "None";
      }

      String printable = SPECIAL.get(key);
      if (printable != null) {
         String it = printable;
         int var11/* $VF was: $i$a$-let-KeyNames$name$1 */ = 0;
         return it;
      }

      if (290 <= key ? key < 315 : false) {
         return "F" + (key - 290 + 1);
      }

      if (320 <= key ? key < 330 : false) {
         return "NUM " + (key - 320);
      }

      String printable2;
      try {
         printable2 = GLFW.glfwGetKeyName(key, 0);
      } catch (Throwable var6) {
         printable2 = null;
      }

      printable = printable2;
      if (printable != null) {
         String var10000 = printable.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var10000, "toUpperCase(...)");
         if (var10000 != null) {
            return var10000;
         }
      }

      return "KEY " + key;
   }

   static {
      Pair[] var0 = new Pair[]{
         TuplesKt.to(340, "L SHIFT"),
         TuplesKt.to(344, "R SHIFT"),
         TuplesKt.to(341, "L CTRL"),
         TuplesKt.to(345, "R CTRL"),
         TuplesKt.to(342, "L ALT"),
         TuplesKt.to(346, "R ALT"),
         TuplesKt.to(32, "SPACE"),
         TuplesKt.to(257, "ENTER"),
         TuplesKt.to(258, "TAB"),
         TuplesKt.to(259, "BACK"),
         TuplesKt.to(261, "DELETE"),
         TuplesKt.to(260, "INSERT"),
         TuplesKt.to(268, "HOME"),
         TuplesKt.to(269, "END"),
         TuplesKt.to(266, "PG UP"),
         TuplesKt.to(267, "PG DN"),
         TuplesKt.to(265, "UP"),
         TuplesKt.to(264, "DOWN"),
         TuplesKt.to(263, "LEFT"),
         TuplesKt.to(262, "RIGHT"),
         TuplesKt.to(280, "CAPS"),
         TuplesKt.to(282, "NUM LOCK"),
         TuplesKt.to(283, "PRT SC"),
         TuplesKt.to(284, "PAUSE"),
         TuplesKt.to(348, "MENU"),
         TuplesKt.to(343, "L SUPER"),
         TuplesKt.to(347, "R SUPER")
      };
      SPECIAL = MapsKt.mapOf(var0);
   }
}
