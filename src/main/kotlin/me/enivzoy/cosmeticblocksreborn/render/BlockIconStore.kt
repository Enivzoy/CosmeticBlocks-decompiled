package me.enivzoy.cosmeticblocksreborn.render

import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import java.io.File
import java.net.HttpURLConnection
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

object BlockIconStore {
   private const val BASE_URL = "https://blockrender.dev/render/block/"
   private const val NAMESPACE = "cb_isoicons"
   private const val ICON_SIZE = 64

   private val iconsDir: File
      get() = File("config/cb/isoicons").resolve(gameVersion())

   private val pool = Executors.newFixedThreadPool(6) { r ->
      val t = Thread(r, "cb-icon-download")
      t.isDaemon = true
      t
   }

   private val state = ConcurrentHashMap<String, Identifier?>()
   private val registry = ConcurrentHashMap<String, DynamicTexture>()
   private val inFlight = ConcurrentHashMap.newKeySet<String>()

   @Volatile
   private var updating = false

   private val started = AtomicBoolean(false)

   fun gameVersion(): String = try {
      FabricLoader.getInstance().rawGameVersion ?: "unknown"
   } catch (_: Throwable) {
      "unknown"
   }

   private fun fileNameFor(blockId: String): String =
      blockId.replace(':', '_').replace('/', '_').replace('.', '_') + ".png"

   private fun textureId(blockId: String, file: File): Identifier =
      Identifier.fromNamespaceAndPath(NAMESPACE, fileNameFor(blockId))

   private fun pngFile(blockId: String): File = File(iconsDir, fileNameFor(blockId))

   private fun isValidPng(file: File): Boolean {
      if (!file.exists() || file.length() < 8L) {
         return false
      }

      return try {
         val header = ByteArray(8)
         file.inputStream().use { it.read(header) }
         header[0] == 0x89.toByte() && header[1] == 0x50.toByte() && header[2] == 0x4E.toByte() && header[3] == 0x47.toByte()
      } catch (_: Throwable) {
         false
      }
   }

   fun bootstrap() {
      if (!started.compareAndSet(false, true)) {
         return
      }
      if (!iconsDir.exists()) {
         ChatUtil.info("Downloading block icons for ${gameVersion()}...")
      }
      loadExisting()
      thread(start = true, name = "cb-icon-bootstrap", isDaemon = true) {
         downloadMissing(false)
      }
   }

   fun updateIcons() {
      if (updating) {
         ChatUtil.warning("Icon update already in progress")
         return
      }
      ChatUtil.info("Updating block icons for ${gameVersion()}...")
      loadExisting()
      thread(start = true, name = "cb-icon-update", isDaemon = true) {
         downloadMissing(true)
      }
   }

   fun validateAndRepair() {
      if (updating) {
         ChatUtil.warning("Icon update already in progress")
         return
      }
      ChatUtil.info("Validating block icons for ${gameVersion()}...")
      loadExisting()
      thread(start = true, name = "cb-icon-validate", isDaemon = true) {
         downloadMissing(true)
      }
   }

   private fun loadExisting() {
      val files = iconsDir.listFiles { f -> f.isFile && f.name.endsWith(".png") } ?: return
      Minecraft.getInstance().execute {
         for (file in files) {
            val blockId = file.nameWithoutExtension.replaceFirst('_', ':')
            registerFromFile(blockId, file)
         }
      }
   }

   private fun registerFromFile(blockId: String, file: File) {
      if (registry.containsKey(blockId)) {
         return
      }
      try {
         val image = com.mojang.blaze3d.platform.NativeImage.read(file.inputStream())
         val texture = DynamicTexture({ "cb_isoicon_$blockId" }, image)
         val id = textureId(blockId, file)
         Minecraft.getInstance().textureManager.register(id, texture)
         registry[blockId] = texture
         state[blockId] = id
      } catch (_: Throwable) {
      }
   }

   private fun downloadMissing(force: Boolean) {
      updating = true
      try {
         if (!iconsDir.exists()) {
            iconsDir.mkdirs()
         }
         val blockIds = BuiltInRegistries.BLOCK.keySet().map { it.toString() }
         val total = blockIds.size
         val done = AtomicInteger(0)
         val downloaded = AtomicInteger(0)
         val missing = blockIds.filter { id ->
            val f = pngFile(id)
            force || !isValidPng(f)
         }

         if (missing.isEmpty()) {
            if (force) {
               ChatUtil.success("All $total block icons already up to date")
            }
            return
         }

         ChatUtil.info("Repairing ${missing.size} of $total block icons...")

         for (blockId in missing) {
            pool.execute {
               try {
                  if (fetch(blockId)) {
                     downloaded.incrementAndGet()
                  }
               } finally {
                  done.incrementAndGet()
               }
            }
         }

         while (done.get() < missing.size) {
            try {
               Thread.sleep(200L)
            } catch (_: InterruptedException) {
               break
            }
         }

         val got = downloaded.get()
         if (force) {
            ChatUtil.success("Icon update complete: $got downloaded, ${missing.size - got} unavailable (of $total blocks)")
         } else {
            ChatUtil.success("Block icons ready: $got downloaded, ${missing.size - got} unavailable (of $total blocks)")
         }
      } catch (e: Throwable) {
         ChatUtil.error("Icon download failed: ${e.message}")
      } finally {
         updating = false
      }
   }

   private fun fetch(blockId: String): Boolean {
      if (inFlight.contains(blockId)) {
         return false
      }
      inFlight.add(blockId)
      try {
         val file = pngFile(blockId)
         val url = "$BASE_URL$blockId.png?view=iso&size=$ICON_SIZE&fit=block"
         val conn = (URI(url).toURL().openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 15000
            readTimeout = 20000
            setRequestProperty("User-Agent", "CosmeticBlocks-Reborn/icon-fetch")
            instanceFollowRedirects = true
         }
         conn.connect()
         val code = conn.responseCode
         if (code != 200) {
            conn.disconnect()
            return false
         }
         val bytes = conn.inputStream.use { it.readBytes() }
         conn.disconnect()
         if (bytes.size < 8) {
            return false
         }

         if (bytes[0] != 0x89.toByte() || bytes[1] != 0x50.toByte() || bytes[2] != 0x4E.toByte() || bytes[3] != 0x47.toByte()) {
            return false
         }

         file.writeBytes(bytes)
         Minecraft.getInstance().execute { registerFromFile(blockId, file) }
         return true
      } catch (_: Throwable) {
         return false
      } finally {
         inFlight.remove(blockId)
      }
   }

   fun registeredCount(): Int = registry.size

   fun identifierFor(blockId: String): Identifier? {
      state[blockId]?.let { return it }
      if (registry.containsKey(blockId)) {
         return null
      }
      val file = pngFile(blockId)
      if (file.exists() && file.length() > 0L) {
         if (Minecraft.getInstance().isSameThread) {
            registerFromFile(blockId, file)
         } else {
            Minecraft.getInstance().execute { registerFromFile(blockId, file) }
         }
      }
      return state[blockId]
   }

   fun clear() {
      state.clear()
      registry.clear()
      inFlight.clear()
      started.set(false)
   }
}
