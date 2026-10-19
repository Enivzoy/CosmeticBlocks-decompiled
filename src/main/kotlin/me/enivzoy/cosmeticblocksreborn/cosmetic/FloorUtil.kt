package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.io.File
import java.nio.file.Files
import java.util.Locale
import java.util.regex.Pattern

object FloorUtil {
   const val NONE = "none"
   private val FLOOR_LINE = Pattern.compile("\"floor\"\\s*:\\s*\"([^\"]*)\"")
   private val VALID = setOf(
      "none", "e",
      "f1", "f2", "f3", "f4", "f5", "f6", "f7",
      "m1", "m2", "m3", "m4", "m5", "m6", "m7"
   )
   private val DISPLAY_OPTIONS = buildDisplayOptions()
   private const val COLOR_NONE = -0xE7E5D6
   private const val COLOR_ENTRANCE = -0xD4E2C6
   private val FLOOR_COLORS = intArrayOf(-0xC1E3B5, -0xA7E4A2, -0x8BE692, -0x71E98C, -0x55F28C, -0x37FF92, -0x1AFFA0)
   private val FLOOR_CACHE = HashMap<String, String?>()
   private val FLOOR_CACHE_TIME = HashMap<String, Long>()

   private fun buildDisplayOptions(): MutableList<String> {
      val options = mutableListOf<String>()
      options.add("None")
      options.add("Entrance")
      for (i in 1..7) {
         options.add("f$i/m$i")
      }

      return options
   }

   @JvmStatic
   fun listFloorOptions(): MutableList<String> = DISPLAY_OPTIONS

   @JvmStatic
   fun normalize(raw: String?): String? {
      if (raw == null) {
         return null
      }

      var value = raw.trim().lowercase(Locale.ROOT)
      if (value.isEmpty()) {
         return null
      }

      if (value == NONE) {
         return NONE
      }

      if (value == "e" || value == "entrance") {
         return "e"
      }

      val slash = value.indexOf('/')
      if (slash >= 0) {
         value = value.substring(0, slash)
      }

      return if (VALID.contains(value)) value else null
   }

   @JvmStatic
   fun display(canonical: String?): String {
      if (canonical == null) {
         return "None"
      }

      val value = canonical.trim().lowercase(Locale.ROOT)
      if (value.isEmpty() || value == NONE) {
         return "None"
      }

      if (value == "e") {
         return "Entrance"
      }

      val number = floorNumber(value)
      if (number >= 1) {
         return "f$number/m$number"
      }

      return value
   }

   @JvmStatic
   fun displayOrNone(canonical: String?): String {
      val normalized = normalize(canonical) ?: return "None"
      return display(normalized)
   }

   @JvmStatic
   fun colorForFloor(raw: String?): Int {
      val floor = normalize(raw)
      if (floor == null || floor == NONE) {
         return COLOR_NONE
      }

      if (floor == "e") {
         return COLOR_ENTRANCE
      }

      val number = floorNumber(floor)
      return if (number >= 1 && number <= FLOOR_COLORS.size) {
         FLOOR_COLORS[number - 1]
      } else {
         COLOR_NONE
      }
   }

   @JvmStatic
   fun floorOfConfig(name: String): String? = readFloor(ReplacementStore.getConfigFile(name))

   @JvmStatic
   fun configLabel(name: String): String = name + " [" + display(floorOfConfig(name)) + "]"

   @JvmStatic
   fun floorNumber(canonical: String?): Int {
      if (canonical == null) {
         return -1
      }

      val value = canonical.trim().lowercase(Locale.ROOT)
      if (value == "e") {
         return 0
      }

      if (value.length >= 2 && (value[0] == 'f' || value[0] == 'm')) {
         try {
            return value.substring(1).toInt()
         } catch (_: NumberFormatException) {
         }
      }

      return -1
   }

   @JvmStatic
   fun matches(configFloor: String?, detected: String?): Boolean {
      val a = normalize(configFloor)
      val b = normalize(detected)
      if (a == null || b == null || a == NONE || b == NONE) {
         return false
      }

      if (a == b) {
         return true
      }

      val numberA = floorNumber(a)
      val numberB = floorNumber(b)
      return numberA >= 1 && numberA == numberB
   }

   @JvmStatic
   fun findConfigForFloor(detected: String?): String? {
      val target = normalize(detected)
      if (target == null || target == NONE) {
         return null
      }

      var exact: String? = null
      var sameNumber: String? = null
      for (name in ConfigSwitcher.INSTANCE.listConfigs()) {
         val floor = normalize(readFloor(ReplacementStore.getConfigFile(name)))
         if (floor == null || floor == NONE) {
            continue
         }

         if (floor == target) {
            exact = name
            break
         }

         if (sameNumber == null && floorNumber(floor) >= 1 && floorNumber(floor) == floorNumber(target)) {
            sameNumber = name
         }
      }

      return exact ?: sameNumber
   }

   @JvmStatic
   fun readFloor(file: File?): String? {
      if (file == null || !file.exists()) {
         return null
      }

      val key = file.path
      val modified = file.lastModified()
      synchronized(FLOOR_CACHE) {
         val cachedTime = FLOOR_CACHE_TIME[key]
         if (cachedTime != null && cachedTime == modified && FLOOR_CACHE.containsKey(key)) {
            return FLOOR_CACHE[key]
         }
      }

      val floor = readFloorUncached(file)
      synchronized(FLOOR_CACHE) {
         FLOOR_CACHE[key] = floor
         FLOOR_CACHE_TIME[key] = modified
      }

      return floor
   }

   private fun readFloorUncached(file: File): String? {
      try {
         Files.newBufferedReader(file.toPath(), Charsets.UTF_8).use { reader ->
            var line: String? = reader.readLine()
            var scanned = 0
            while (line != null && scanned < 256) {
               scanned++
               val matcher = FLOOR_LINE.matcher(line)
               if (matcher.find()) {
                  return matcher.group(1)
               }

               line = reader.readLine()
            }
         }
      } catch (_: Exception) {
      }

      return null
   }
}
