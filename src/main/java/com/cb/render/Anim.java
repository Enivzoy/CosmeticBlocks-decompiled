package com.cb.render;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\r¨\u0006\u0013",
   d2 = {
         "Lcom/cb/render/Anim;",
         "",
         "initial",
         "",
         "speed",
         "<init>",
         "(FF)V",
         "value",
         "getValue",
         "()F",
         "target",
         "getTarget",
         "setTarget",
         "(F)V",
         "update",
         "delta",
         "snap",
         "",
         "v",
         "cb_client"
   }
)
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
