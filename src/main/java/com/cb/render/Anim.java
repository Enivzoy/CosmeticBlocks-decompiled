package com.cb.render;
import kotlin.ranges.RangesKt;

public final class Anim {
   private final float speed;
   private float value;
   private float target;

   public Anim(float initial, float speed) {
      this.speed = speed;
      this.value = initial;
      this.target = initial;
   }

   public final float getValue() {
      return this.value;
   }

   public final float getTarget() {
      return this.target;
   }

   public final void setTarget(float target) {
      this.target = target;
   }

   public final float update(float delta) {
      float t = RangesKt.coerceIn(delta * this.speed, 0.0F, 1.0F);
      this.value = this.value + (this.target - this.value) * t;
      if (Math.abs(this.target - this.value) < 5.0E-4F) {
         this.value = this.target;
      }

      return this.value;
   }

   public final void snap(float v) {
      this.value = v;
      this.target = v;
   }
}
