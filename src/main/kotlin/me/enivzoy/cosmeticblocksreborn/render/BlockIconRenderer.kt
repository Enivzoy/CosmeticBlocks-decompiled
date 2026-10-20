package me.enivzoy.cosmeticblocksreborn.render

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import org.joml.Matrix3x2f

object BlockIconRenderer {
   private val cache = HashMap<String, TextureAtlasSprite?>()
   private const val TOP_SHADE = 1.0f
   private const val LEFT_SHADE = 0.6f
   private const val RIGHT_SHADE = 0.8f

   fun clear() {
      cache.clear()
      BlockIconStore.clear()
   }

   fun draw(blockId: String, cx: Float, cy: Float, size: Float, alpha: Float) {
      val icon = BlockIconStore.identifierFor(blockId)
      if (icon != null) {
         CascadeUi.iconQuad(icon, cx - size / 2f, cy - size / 2f, cx + size / 2f, cy + size / 2f, argb(alpha))
         return
      }
      drawFallback(blockId, cx, cy, size, alpha)
   }

   private fun drawFallback(blockId: String, cx: Float, cy: Float, size: Float, alpha: Float) {
      val ex = CascadeUi.extractorOrNull() ?: return
      val sprite = spriteFor(blockId) ?: run {
         CascadeUi.rect(cx - size / 2f, cy - size / 2f, size, size, Theme.withAlpha(-9605779, alpha * 0.85f), size * 0.12f)
         return
      }

      val w = size
      val hh = size * 0.25f
      val left = cx - w / 2f
      val top = cy - size * 0.5f

      ex.pose().pushMatrix()
      ex.pose().translate(left, top)
      ex.pose().mul(topMatrix())
      CascadeUi.spriteQuad(sprite, 0f, 0f, w, w, argb(alpha * TOP_SHADE))
      ex.pose().popMatrix()

      ex.pose().pushMatrix()
      ex.pose().translate(left, top)
      ex.pose().translate(0f, hh)
      ex.pose().mul(leftMatrix())
      CascadeUi.spriteQuad(sprite, 0f, 0f, w, w, argb(alpha * LEFT_SHADE))
      ex.pose().popMatrix()

      ex.pose().pushMatrix()
      ex.pose().translate(left, top)
      ex.pose().translate(w / 2f, 2f * hh)
      ex.pose().mul(rightMatrix())
      CascadeUi.spriteQuad(sprite, 0f, 0f, w, w, argb(alpha * RIGHT_SHADE))
      ex.pose().popMatrix()
   }

   private fun spriteFor(blockId: String): TextureAtlasSprite? {
      if (cache.containsKey(blockId)) {
         return cache[blockId]
      }

      val result = try {
         val loc = Identifier.parse(if (blockId.contains(":")) blockId else "minecraft:$blockId")
         val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null)
         if (block == null) {
            null
         } else {
            val modelSet = Minecraft.getInstance().modelManager.blockStateModelSet
            modelSet.get(block.defaultBlockState()).particleMaterial().sprite()
         }
      } catch (_: Throwable) {
         null
      }

      cache[blockId] = result
      return result
   }

   private fun topMatrix(): Matrix3x2f {
      val m = Matrix3x2f()
      m.m00 = 0.5f
      m.m01 = 0.5f
      m.m10 = -0.25f
      m.m11 = 0.25f
      return m
   }

   private fun leftMatrix(): Matrix3x2f {
      val m = Matrix3x2f()
      m.m00 = 0.5f
      m.m01 = 0f
      m.m10 = 0.25f
      m.m11 = 0.5f
      return m
   }

   private fun rightMatrix(): Matrix3x2f {
      val m = Matrix3x2f()
      m.m00 = 0.5f
      m.m01 = 0f
      m.m10 = -0.25f
      m.m11 = 0.5f
      return m
   }

   private fun argb(alpha: Float): Int {
      val a = (alpha.coerceIn(0f, 1f) * 255f).toInt().coerceIn(0, 255)
      return a shl 24 or 0xFFFFFF
   }
}
