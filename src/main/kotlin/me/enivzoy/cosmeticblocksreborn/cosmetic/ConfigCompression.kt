package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.util.Base64
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Optional extra compression stages for stored configs. Each stage is a flag so the
 * loader can tell exactly how a file was written. `flags == 0` produces the plain
 * (readable, uncompressed) format 2 used by regular saves.
 */
internal object ConfigCompression {
   const val MINIFY = 1
   const val GZIP = 2
   const val DICTIONARY = 4
   const val PALETTE = 8
   const val RLE = 16

   const val MINI_STAGES = GZIP or MINIFY or DICTIONARY

   internal class Document(
      val floor: String?,
      val water: WaterConfig.Fields,
      val dims: LinkedHashMap<String, LinkedHashMap<String, String>>,
      val isLegacy: Boolean = false,
      val rawSize: Int = 0,
      val flags: Int = 0
   )

   fun describe(flags: Int): String {
      if (flags == 0) {
         return "compact ranges"
      }

      val parts = ArrayList<String>()
      if (flags and GZIP != 0) parts.add("gzip")
      if (flags and MINIFY != 0) parts.add("minified")
      if (flags and PALETTE != 0) {
         parts.add("blockstate palette")
      } else if (flags and DICTIONARY != 0) {
         parts.add("block dictionary")
      }
      if (flags and RLE != 0) parts.add("rle")
      return parts.joinToString(", ")
   }

   fun encode(floor: String?, water: WaterConfig.Fields, dims: Map<String, Map<String, String>>, flags: Int): ByteArray {
      val sink = ByteArrayOutputStream(1 shl 16)
      val out: OutputStream = if (flags and GZIP != 0) GZIPOutputStream(sink, 1 shl 16) else sink
      val minify = flags != 0 && flags and MINIFY != 0
      val indent = if (minify) "" else "  "
      val palette = flags != 0 && flags and PALETTE != 0
      val dictionary = palette || (flags != 0 && flags and DICTIONARY != 0)
      val rle = flags != 0 && flags and RLE != 0

      val dimList = dims.entries.toList()
      val compressed = ArrayList<LinkedHashMap<String, MutableList<IntArray>>>(dimList.size)
      for (entry in dimList) {
         compressed.add(ReplacementCompactor.compress(entry.value))
      }

      val blockIndex = LinkedHashMap<String, Int>()
      val stateIndex = LinkedHashMap<String, Int>()
      if (dictionary) {
         for (comp in compressed) {
            for (raw in comp.keys) {
               if (palette) {
                  blockIndex.getOrPut(baseOf(raw)) { blockIndex.size }
                  stateOf(raw)?.let { stateIndex.getOrPut(it) { stateIndex.size } }
               } else {
                  blockIndex.getOrPut(raw) { blockIndex.size }
               }
            }
         }
      }

      OutputStreamWriter(out, Charsets.UTF_8).let { osw ->
         BufferedWriter(osw, 1 shl 16).let { bw ->
            JsonWriter(bw).use { jw ->
               jw.setIndent(indent)
               jw.beginObject()

               if (floor != null && floor.isNotEmpty()) {
                  jw.name("floor").value(floor)
               }

               if (flags != 0) {
                  jw.name("format").value(3L)
                  jw.name("flags").value(flags.toLong())
               } else {
                  jw.name("format").value(2L)
               }

               water.allow?.let { jw.name("allowWaterColor").value(it) }
               water.rgb?.let { jw.name("rgbWaterColor").value(it) }
               water.lava?.let { jw.name("lavaAsWater").value(it) }

               if (dictionary) {
                  jw.name("blocks")
                  jw.beginArray()
                  for (name in blockIndex.keys) {
                     jw.value(name)
                  }
                  jw.endArray()

                  if (palette) {
                     jw.name("states")
                     jw.beginArray()
                     for (name in stateIndex.keys) {
                        jw.value(name)
                     }
                     jw.endArray()
                  }
               }

               for (i in dimList.indices) {
                  val dim = dimList[i].key
                  val dimMap = compressed[i]
                  jw.name(dim)
                  if (palette) {
                     jw.beginArray()
                     for ((raw, boxes) in dimMap) {
                        val base = baseOf(raw)
                        val state = stateOf(raw)
                        jw.beginArray()
                        jw.value(blockIndex[base]!!.toLong())
                        jw.value((if (state == null) -1 else stateIndex[state]!!).toLong())
                        writeBoxes(jw, boxes, rle, minify, indent)
                        jw.endArray()
                     }
                     jw.endArray()
                  } else {
                     jw.beginObject()
                     for ((raw, boxes) in dimMap) {
                        jw.name(if (dictionary) blockIndex[raw]!!.toString() else raw)
                        writeBoxes(jw, boxes, rle, minify, indent)
                     }
                     jw.endObject()
                  }
               }

               jw.endObject()
            }
         }
      }

      return sink.toByteArray()
   }

   fun decode(bytes: ByteArray): Document? {
      val data = if (bytes.size >= 2 && bytes[0] == 0x1F.toByte() && bytes[1] == 0x8B.toByte()) {
         GZIPInputStream(ByteArrayInputStream(bytes)).use { it.readBytes() }
      } else {
         bytes
      }

      val root = JsonParser.parseString(String(data, Charsets.UTF_8)).asJsonObject
      val format = root.get("format")?.asInt ?: 0
      return if (format >= 3) {
         decodeV3(root, bytes.size)
      } else {
         decodeLegacy(root, bytes.size, format < 2)
      }
   }

   private fun decodeLegacy(root: JsonObject, rawSize: Int, declaredLegacy: Boolean): Document {
      var floor: String? = null
      var allow: Boolean? = null
      var rgb: Int? = null
      var lava: Boolean? = null
      var flatEntries = false
      val dims = LinkedHashMap<String, LinkedHashMap<String, String>>()

      for ((key, element) in root.entrySet()) {
         if (element.isJsonPrimitive) {
            val primitive = element.asJsonPrimitive
            when {
               key == "floor" && primitive.isString -> floor = element.asString
               key == "allowWaterColor" && primitive.isBoolean -> allow = element.asBoolean
               key == "rgbWaterColor" && primitive.isNumber -> rgb = element.asInt
               key == "lavaAsWater" && primitive.isBoolean -> lava = element.asBoolean
            }

            continue
         }

         if (!element.isJsonObject) {
            continue
         }

         val dimMap = LinkedHashMap<String, String>()
         for ((raw, value) in element.asJsonObject.entrySet()) {
            if (value.isJsonArray) {
               ReplacementCompactor.expand(raw, toRanges(value.asJsonArray), dimMap)
            } else if (value.isJsonPrimitive) {
               dimMap[raw] = value.asString
               flatEntries = true
            }
         }

         if (dimMap.isNotEmpty()) {
            dims[key] = dimMap
         }
      }

      return Document(floor, WaterConfig.Fields(allow, rgb, lava), dims, declaredLegacy || flatEntries, rawSize)
   }

   private fun decodeV3(root: JsonObject, rawSize: Int): Document {
      val flags = root.get("flags")?.asInt ?: 0
      val palette = flags and PALETTE != 0
      val dictionary = palette || (flags and DICTIONARY != 0)
      val rle = flags and RLE != 0
      val blocks = root.getAsJsonArray("blocks")?.map { it.asString } ?: emptyList()
      val states = root.getAsJsonArray("states")?.map { it.asString } ?: emptyList()

      var floor: String? = null
      var allow: Boolean? = null
      var rgb: Int? = null
      var lava: Boolean? = null
      val dims = LinkedHashMap<String, LinkedHashMap<String, String>>()

      for ((key, element) in root.entrySet()) {
         if (key == "format" || key == "flags" || key == "blocks" || key == "states") {
            continue
         }

         if (element.isJsonPrimitive) {
            val primitive = element.asJsonPrimitive
            when {
               key == "floor" && primitive.isString -> floor = element.asString
               key == "allowWaterColor" && primitive.isBoolean -> allow = element.asBoolean
               key == "rgbWaterColor" && primitive.isNumber -> rgb = element.asInt
               key == "lavaAsWater" && primitive.isBoolean -> lava = element.asBoolean
            }

            continue
         }

         val dimMap = LinkedHashMap<String, String>()
         if (palette && element.isJsonArray) {
            for (entry in element.asJsonArray) {
               if (!entry.isJsonArray) {
                  continue
               }

               val array = entry.asJsonArray
               if (array.size() < 3) {
                  continue
               }

               val blockIndex = array.get(0).asInt
               val stateIndex = array.get(1).asInt
               if (blockIndex < 0 || blockIndex >= blocks.size) {
                  continue
               }

               val raw = if (stateIndex >= 0 && stateIndex < states.size) {
                  blocks[blockIndex] + "|" + states[stateIndex]
               } else {
                  blocks[blockIndex]
               }

               ReplacementCompactor.expand(raw, readBoxes(array.get(2), rle), dimMap)
            }
         } else if (element.isJsonObject) {
            for ((rawKey, value) in element.asJsonObject.entrySet()) {
               val raw = if (dictionary) {
                  val index = rawKey.toIntOrNull() ?: continue
                  if (index < 0 || index >= blocks.size) {
                     continue
                  }

                  blocks[index]
               } else {
                  rawKey
               }

               ReplacementCompactor.expand(raw, readBoxes(value, rle), dimMap)
            }
         }

         if (dimMap.isNotEmpty()) {
            dims[key] = dimMap
         }
      }

      return Document(floor, WaterConfig.Fields(allow, rgb, lava), dims, false, rawSize, flags)
   }

   private fun readBoxes(element: JsonElement, rle: Boolean): List<IntArray> {
      if (rle && element.isJsonPrimitive) {
         return try {
            unpackRanges(element.asString)
         } catch (ignored: Exception) {
            emptyList()
         }
      }

      return if (element.isJsonArray) toRanges(element.asJsonArray) else emptyList()
   }

   private fun writeBoxes(jw: JsonWriter, boxes: List<IntArray>, rle: Boolean, minify: Boolean, indent: String) {
      if (rle) {
         jw.value(packRanges(boxes))
         return
      }

      jw.beginArray()
      for (box in boxes) {
         jw.beginArray()
         if (!minify) {
            jw.setIndent("")
         }

         for (value in box) {
            jw.value(value.toLong())
         }

         jw.endArray()
         if (!minify) {
            jw.setIndent(indent)
         }
      }

      jw.endArray()
   }

   private fun toRanges(array: JsonArray): List<IntArray> {
      val ranges = ArrayList<IntArray>(array.size())
      for (rangeElement in array) {
         if (!rangeElement.isJsonArray) {
            continue
         }

         val values = rangeElement.asJsonArray
         if (values.size() < 6) {
            continue
         }

         val range = IntArray(6)
         var valid = true
         for (i in 0 until 6) {
            val value = values.get(i)
            if (!value.isJsonPrimitive || !value.asJsonPrimitive.isNumber) {
               valid = false
               break
            }

            range[i] = value.asInt
         }

         if (valid) {
            ranges.add(range)
         }
      }

      return ranges
   }

   private fun baseOf(raw: String): String {
      val index = raw.indexOf('|')
      return if (index < 0) raw else raw.substring(0, index)
   }

   private fun stateOf(raw: String): String? {
      val index = raw.indexOf('|')
      return if (index < 0) null else raw.substring(index + 1)
   }

   private fun packRanges(boxes: List<IntArray>): String {
      val buffer = ByteArrayOutputStream(boxes.size * 20 + 4)
      writeVarInt(buffer, boxes.size)
      for (box in boxes) {
         for (value in box) {
            writeVarInt(buffer, (value shl 1) xor (value shr 31))
         }
      }

      return Base64.getEncoder().encodeToString(buffer.toByteArray())
   }

   private fun unpackRanges(encoded: String): List<IntArray> {
      val bytes = Base64.getDecoder().decode(encoded)
      val input = ByteArrayInputStream(bytes)
      val count = readVarInt(input)
      val boxes = ArrayList<IntArray>(count.coerceAtLeast(0))
      for (i in 0 until count) {
         val box = IntArray(6)
         for (j in 0 until 6) {
            val zig = readVarInt(input)
            box[j] = (zig ushr 1) xor -(zig and 1)
         }

         boxes.add(box)
      }

      return boxes
   }

   private fun writeVarInt(out: OutputStream, value: Int) {
      var remaining = value
      while (true) {
         if (remaining and -128 == 0) {
            out.write(remaining)
            return
         }

         out.write((remaining and 0x7F) or 0x80)
         remaining = remaining ushr 7
      }
   }

   private fun readVarInt(input: InputStream): Int {
      var result = 0
      var shift = 0
      while (true) {
         val byte = input.read()
         if (byte < 0) {
            return result
         }

         result = result or ((byte and 0x7F) shl shift)
         if (byte and 0x80 == 0) {
            return result
         }

         shift += 7
      }
   }
}
