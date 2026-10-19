package me.enivzoy.cosmeticblocksreborn.render

import kotlin.math.abs

class Anim(initial: Float, private val speed: Float) {
   var value: Float = initial
      private set

   var target: Float = initial

   fun update(delta: Float): Float {
      val t = (delta * speed).coerceIn(0.0f, 1.0f)
      value += (target - value) * t
      if (abs(target - value) < 5.0E-4f) {
         value = target
      }

      return value
   }

   fun snap(v: Float) {
      value = v
      target = v
   }
}
