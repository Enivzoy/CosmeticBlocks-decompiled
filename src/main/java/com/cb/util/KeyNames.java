package com.cb.util;

import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

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
         return printable;
      }

      if (290 <= key ? key < 315 : false) {
         return "F" + (key - 290 + 1);
      }

      if (320 <= key ? key < 330 : false) {
         return "NUM " + (key - 320);
      }

      try {
         printable = GLFW.glfwGetKeyName(key, 0);
      } catch (Throwable ignored) {
         printable = null;
      }

      if (printable != null) {
         String upper = printable.toUpperCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(upper, "toUpperCase(...)");
         return upper;
      }

      return "KEY " + key;
   }

   static {
      Pair[] pairs = new Pair[]{
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
      SPECIAL = MapsKt.mapOf(pairs);
   }
}
