package me.enivzoy.cosmeticblocksreborn.render

import foo.starred.cascade.graphics.extensions.circle.circle
import foo.starred.cascade.graphics.extensions.image.image
import foo.starred.cascade.graphics.extensions.rectangle.hollow.hollowRectangle
import foo.starred.cascade.graphics.extensions.rectangle.rounded.roundedRectangle
import foo.starred.cascade.graphics.extensions.shadow.dropShadow
import foo.starred.cascade.graphics.extensions.stroke.stroke
import foo.starred.cascade.graphics.font.CascadeFonts
import foo.starred.cascade.graphics.geometry.CascadeGeometricColor
import foo.starred.cascade.graphics.geometry.CascadeGeometricOffset
import foo.starred.cascade.graphics.geometry.CascadeGeometricRadius
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.network.chat.Component
import java.util.ArrayDeque
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.max

object CascadeUi {
   private var extractor: GuiGraphicsExtractor? = null
   private var inFrame = false
   private var alpha = 1.0f

   private var scissorDepth = 0
   private var scissorEmpty = false

   private val SAVE_STACK = ArrayDeque<FloatArray>(16)

   @JvmStatic
   fun setExtractor(extractor: GuiGraphicsExtractor?) {
      CascadeUi.extractor = extractor
   }

   @JvmStatic
   fun clearExtractor() {
      extractor = null
   }

   fun getAvailable(): Boolean = true

   private fun ready(): Boolean = extractor != null

   fun extractorOrNull(): GuiGraphicsExtractor? = if (ready()) extractor else null

   fun spriteQuad(sprite: TextureAtlasSprite, x0: Float, y0: Float, x1: Float, y1: Float, argb: Int) {
      if (!ready() || clipped()) {
         return
      }
      extractor!!.image(
         sprite, x0, y0, x1, y1,
         CascadeGeometricColor(color(argb)), CascadeGeometricRadius(0.01f),
         foo.starred.cascade.graphics.states.impl.image.data.CascadeImageFilter.NEAREST, null, null, null
      )
   }

   fun iconQuad(identifier: net.minecraft.resources.Identifier, x0: Float, y0: Float, x1: Float, y1: Float, argb: Int) {
      if (!ready() || clipped()) {
         return
      }
      extractor!!.image(
         location = identifier, x = x0, y = y0, width = x1 - x0, height = y1 - y0,
         color = CascadeGeometricColor(color(argb)), radius = CascadeGeometricRadius(0.01f),
         filter = foo.starred.cascade.graphics.states.impl.image.data.CascadeImageFilter.LINEAR
      )
   }

   fun beginFrame(framebufferWidth: Int, framebufferHeight: Int, uiScale: Float): Boolean {
      if (extractor == null || framebufferWidth <= 0 || framebufferHeight <= 0) {
         return false
      }

      var gs = 1.0f
      try {
         gs = Minecraft.getInstance().window.guiScale.toFloat()
      } catch (ignored: Throwable) {
      }
      if (gs <= 0.0f) {
         gs = 1.0f
      }

      alpha = 1.0f
      scissorDepth = 0
      scissorEmpty = false
      SAVE_STACK.clear()

      val pose = extractor!!.pose()
      pose.pushMatrix()
      pose.scale(uiScale / gs)

      inFrame = true
      return true
   }

   fun endFrame() {
      if (!inFrame) {
         return
      }
      inFrame = false
      SAVE_STACK.clear()
      val ex = extractor
      if (ex != null) {
         while (scissorDepth > 0) {
            ex.disableScissor()
            scissorDepth--
         }
         ex.pose().popMatrix()
      }
      scissorDepth = 0
      scissorEmpty = false
   }

   private fun color(argb: Int): Int {
      var a = argb ushr 24 and 0xFF
      a = Math.round(a * alpha)
      if (a < 0) {
         a = 0
      } else if (a > 255) {
         a = 255
      }
      return a shl 24 or (argb and 0xFFFFFF)
   }

   private fun clipped(): Boolean = scissorEmpty

   fun rect(x: Float, y: Float, w: Float, h: Float, argb: Int, radius: Float) {
      if (!ready() || clipped() || w <= 0.0f || h <= 0.0f) {
         return
      }
      extractor!!.roundedRectangle(x, y, w, h, CascadeGeometricColor(color(argb)), CascadeGeometricRadius(max(radius, 0.01f)))
   }

   fun rectVarying(x: Float, y: Float, w: Float, h: Float, radTl: Float, radTr: Float, radBr: Float, radBl: Float, argb: Int) {
      if (!ready() || clipped() || w <= 0.0f || h <= 0.0f) {
         return
      }
      extractor!!.roundedRectangle(
         x, y, w, h,
         CascadeGeometricColor(color(argb)),
         CascadeGeometricRadius(max(radTl, 0.01f), max(radTr, 0.01f), max(radBl, 0.01f), max(radBr, 0.01f))
      )
   }

   fun rectOutline(x: Float, y: Float, w: Float, h: Float, argb: Int, radius: Float, strokeWidth: Float) {
      if (!ready() || clipped() || w <= 0.0f || h <= 0.0f) {
         return
      }
      extractor!!.hollowRectangle(
         x, y, w, h,
         max(strokeWidth, 0.01f),
         CascadeGeometricColor(color(argb)),
         CascadeGeometricRadius(max(radius, 0.01f)),
         true
      )
   }

   fun circle(cx: Float, cy: Float, radius: Float, argb: Int) {
      if (!ready() || clipped() || radius <= 0.0f) {
         return
      }
      extractor!!.circle(cx, cy, radius, CascadeGeometricColor(color(argb)))
   }

   fun line(x1: Float, y1: Float, x2: Float, y2: Float, argb: Int, strokeWidth: Float) {
      if (!ready() || clipped() || strokeWidth <= 0.0f) {
         return
      }
      if (x1 == x2 && y1 == y2) {
         return
      }
      extractor!!.stroke(x1, y1, x2, y2, CascadeGeometricColor(color(argb)), strokeWidth, true)
   }

   fun gradientRect(x: Float, y: Float, w: Float, h: Float, radius: Float, startArgb: Int, endArgb: Int, horizontal: Boolean) {
      if (!ready() || clipped() || w <= 0.0f || h <= 0.0f) {
         return
      }
      val start = color(startArgb)
      val end = color(endArgb)
      val corners = if (horizontal) {
         CascadeGeometricColor(start, end, start, end)
      } else {
         CascadeGeometricColor(start, start, end, end)
      }
      extractor!!.roundedRectangle(x, y, w, h, corners, CascadeGeometricRadius(max(radius, 0.01f)))
   }

   fun shadow(x: Float, y: Float, w: Float, h: Float, radius: Float, blur: Float, argb: Int) {
      if (!ready() || clipped() || w <= 0.0f || h <= 0.0f) {
         return
      }
      val a = Math.round((argb ushr 24 and 0xFF) * alpha)
      if (a <= 0) {
         return
      }
      val shaded = (a shl 24) or (argb and 0xFFFFFF)
      extractor!!.dropShadow(
         x, y, w, h,
         CascadeGeometricOffset(0.0f, 2.0f),
         max(blur, 0.01f),
         0.0f,
         CascadeGeometricColor(shaded),
         CascadeGeometricRadius(max(radius, 0.01f))
      )
   }

   private fun component(string: String, font: Font): Component {
      val text = Component.literal(string)
      return if (font == Font.BOLD) text.withStyle(ChatFormatting.BOLD) else text
   }

   private fun topFor(yCenter: Float, size: Float): Float = yCenter - size / 2.0f

   fun text(string: String, x: Float, y: Float, size: Float, argb: Int, font: Font): Float {
      if (!ready() || clipped() || string.isEmpty()) {
         return 0.0f
      }
      val text = component(string, font)
      val width = CascadeFonts.sans.width(text, size, false)
      CascadeFonts.sans.extract(extractor!!, text, x, topFor(y, size), CascadeGeometricColor(color(argb)), false, size, false)
      return width
   }

   fun textCentered(string: String, x: Float, y: Float, size: Float, argb: Int, font: Font) {
      if (!ready() || clipped() || string.isEmpty()) {
         return
      }
      val text = component(string, font)
      val width = CascadeFonts.sans.width(text, size, false)
      CascadeFonts.sans.extract(extractor!!, text, x - width / 2.0f, topFor(y, size), CascadeGeometricColor(color(argb)), false, size, false)
   }

   fun textRight(string: String, x: Float, y: Float, size: Float, argb: Int, font: Font) {
      if (!ready() || clipped() || string.isEmpty()) {
         return
      }
      val text = component(string, font)
      val width = CascadeFonts.sans.width(text, size, false)
      CascadeFonts.sans.extract(extractor!!, text, x - width, topFor(y, size), CascadeGeometricColor(color(argb)), false, size, false)
   }

   fun textWidth(string: String, size: Float, font: Font): Float {
      if (string.isEmpty()) {
         return 0.0f
      }
      return CascadeFonts.sans.width(component(string, font), size, false)
   }

   fun scissor(x: Float, y: Float, w: Float, h: Float, block: () -> Unit) {
      save()
      intersectScissor(x, y, w, h)
      try {
         block()
      } finally {
         restore()
      }
   }

   fun save() {
      if (extractor == null) {
         return
      }
      extractor!!.pose().pushMatrix()
      SAVE_STACK.push(floatArrayOf(alpha, scissorDepth.toFloat(), if (scissorEmpty) 1.0f else 0.0f))
   }

   fun restore() {
      val ex = extractor
      if (ex == null || SAVE_STACK.isEmpty()) {
         return
      }
      val s = SAVE_STACK.pop()
      val savedDepth = s[1].toInt()
      while (scissorDepth > savedDepth) {
         ex.disableScissor()
         scissorDepth--
      }
      ex.pose().popMatrix()
      alpha = s[0]
      scissorEmpty = s[2] != 0.0f
   }

   fun translate(x: Float, y: Float) {
      extractor?.pose()?.translate(x, y)
   }

   fun rotate(angle: Float) {
      extractor?.pose()?.rotate(angle)
   }

   fun intersectScissor(x: Float, y: Float, w: Float, h: Float) {
      val ex = extractor ?: return
      if (scissorEmpty || w <= 0.0f || h <= 0.0f) {
         scissorEmpty = true
         return
      }
      val left = floor(x).toInt()
      val top = floor(y).toInt()
      val right = ceil(x + w).toInt()
      val bottom = ceil(y + h).toInt()
      if (right <= left || bottom <= top) {
         scissorEmpty = true
         return
      }
      ex.enableScissor(left, top, right, bottom)
      scissorDepth++
   }

   fun globalAlpha(alpha: Float) {
      CascadeUi.alpha = Math.max(0.0f, Math.min(1.0f, alpha))
   }

   enum class Font {
      REGULAR,
      MEDIUM,
      BOLD
   }
}
