package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class ColorSetting extends Setting<Integer> {
   private float hue;
   private float saturation;
   private float brightness;
   private float alpha;
   private boolean syncingFromHsba;

   public ColorSetting(@NotNull String name, @NotNull String description, int defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      super(name, description, defaultValue);
      this.alpha = 1.0F;
      this.decompose(defaultValue);
   }

   public final float getHue() {
      return this.hue;
   }

   public final float getSaturation() {
      return this.saturation;
   }

   public final float getBrightness() {
      return this.brightness;
   }

   public final float getAlpha() {
      return this.alpha;
   }

   @NotNull
   protected Integer coerce(int newValue) {
      if (!this.syncingFromHsba) {
         this.decompose(newValue);
      }

      return newValue;
   }

   public final void setHsba(float hue, float saturation, float brightness, float alpha) {
      this.hue = RangesKt.coerceIn(hue, 0.0F, 1.0F);
      this.saturation = RangesKt.coerceIn(saturation, 0.0F, 1.0F);
      this.brightness = RangesKt.coerceIn(brightness, 0.0F, 1.0F);
      this.alpha = RangesKt.coerceIn(alpha, 0.0F, 1.0F);
      int rgb = Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 16777215;
      int a = RangesKt.coerceIn((int)(this.alpha * 255.0F), 0, 255);
      int packed = a << 24 | rgb;
      if (packed != this.getValue().intValue()) {
         Function1 listener = this.getOnChanged();
         this.setOnChanged(null);
         this.setValue(packed);
         this.setOnChanged(listener);
      }
   }

   public final int getOpaqueRgb() {
      return this.getValue().intValue() | 0xFF000000;
   }

   private final void decompose(int argb) {
      float[] hsb = Color.RGBtoHSB(argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF, null);
      if (hsb[1] > 0.0F) {
         this.hue = hsb[0];
      }

      this.saturation = hsb[1];
      this.brightness = hsb[2];
      this.alpha = (argb >>> 24 & 0xFF) / 255.0F;
   }

   @Override
   public void writeJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      json.addProperty(this.getName(), this.getValue());
   }

   @Override
   public void readJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      JsonElement element = json.get(this.getName());
      if (element != null) {
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.setValue(element.getAsInt());
            this.decompose(this.getValue().intValue());
         }
      }
   }
}
