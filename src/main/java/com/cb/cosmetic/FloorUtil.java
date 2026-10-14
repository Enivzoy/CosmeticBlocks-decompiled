package com.cb.cosmetic;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FloorUtil {
   public static final String NONE = "none";
   private static final Pattern FLOOR_LINE = Pattern.compile("\"floor\"\\s*:\\s*\"([^\"]*)\"");
   private static final Set<String> VALID = Set.of(
      "none", "e",
      "f1", "f2", "f3", "f4", "f5", "f6", "f7",
      "m1", "m2", "m3", "m4", "m5", "m6", "m7"
   );
   private static final List<String> DISPLAY_OPTIONS = buildDisplayOptions();

   private FloorUtil() {
   }

   private static List<String> buildDisplayOptions() {
      List<String> options = new ArrayList<>();
      options.add("Entrance");
      for (int i = 1; i <= 7; i++) {
         options.add("f" + i + "/m" + i);
      }
      return options;
   }

   @NotNull
   public static List<String> listFloorOptions() {
      return DISPLAY_OPTIONS;
   }

   @Nullable
   public static String normalize(@Nullable String raw) {
      if (raw == null) {
         return null;
      }
      String value = raw.trim().toLowerCase(Locale.ROOT);
      if (value.isEmpty()) {
         return null;
      }
      if (value.equals("none")) {
         return NONE;
      }
      if (value.equals("e") || value.equals("entrance")) {
         return "e";
      }
      int slash = value.indexOf('/');
      if (slash >= 0) {
         value = value.substring(0, slash);
      }
      return VALID.contains(value) ? value : null;
   }

   @NotNull
   public static String display(@Nullable String canonical) {
      if (canonical == null) {
         return "None";
      }
      String value = canonical.trim().toLowerCase(Locale.ROOT);
      if (value.isEmpty() || value.equals(NONE)) {
         return "None";
      }
      if (value.equals("e")) {
         return "Entrance";
      }
      int number = floorNumber(value);
      if (number >= 1) {
         return "f" + number + "/m" + number;
      }
      return value;
   }

   @NotNull
   public static String displayOrNone(@Nullable String canonical) {
      String normalized = normalize(canonical);
      return normalized == null ? "None" : display(normalized);
   }

   public static int floorNumber(@Nullable String canonical) {
      if (canonical == null) {
         return -1;
      }
      String value = canonical.trim().toLowerCase(Locale.ROOT);
      if (value.equals("e")) {
         return 0;
      }
      if (value.length() >= 2 && (value.charAt(0) == 'f' || value.charAt(0) == 'm')) {
         try {
            return Integer.parseInt(value.substring(1));
         } catch (NumberFormatException ignored) {
         }
      }
      return -1;
   }

   public static boolean matches(@Nullable String configFloor, @Nullable String detected) {
      String a = normalize(configFloor);
      String b = normalize(detected);
      if (a == null || b == null || a.equals(NONE) || b.equals(NONE)) {
         return false;
      }
      if (a.equals(b)) {
         return true;
      }
      int numberA = floorNumber(a);
      int numberB = floorNumber(b);
      return numberA >= 1 && numberA == numberB;
   }

   @Nullable
   public static String findConfigForFloor(@Nullable String detected) {
      String target = normalize(detected);
      if (target == null || target.equals(NONE)) {
         return null;
      }
      String exact = null;
      String sameNumber = null;
      for (String name : ConfigSwitcher.INSTANCE.listConfigs()) {
         String floor = normalize(readFloor(ReplacementStore.INSTANCE.getConfigFile$cb_client(name)));
         if (floor == null || floor.equals(NONE)) {
            continue;
         }
         if (floor.equals(target)) {
            exact = name;
            break;
         }
         if (sameNumber == null && floorNumber(floor) >= 1 && floorNumber(floor) == floorNumber(target)) {
            sameNumber = name;
         }
      }
      return exact != null ? exact : sameNumber;
   }

   @Nullable
   public static String readFloor(@Nullable File file) {
      if (file == null || !file.exists()) {
         return null;
      }
      try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
         String line;
         int scanned = 0;
         while ((line = reader.readLine()) != null && scanned < 256) {
            scanned++;
            Matcher matcher = FLOOR_LINE.matcher(line);
            if (matcher.find()) {
               return matcher.group(1);
            }
         }
      } catch (Exception ignored) {
      }
      return null;
   }
}
