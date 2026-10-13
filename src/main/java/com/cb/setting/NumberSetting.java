package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class NumberSetting extends Setting<Double> {
   private final double min;
   private final double max;
   private final double step;
   @NotNull
   private final String unit;
   private final int decimals;

   public NumberSetting(@NotNull String name, @NotNull String description, double defaultValue, double min, double max, double step, @NotNull String unit) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(unit, "unit");
      super(name, description, defaultValue);
      this.min = min;
      this.max = max;
      this.step = step;
      this.unit = unit;
      if (!(this.min < this.max)) {
         throw new IllegalArgumentException(name + ": min (" + this.min + ") must be < max (" + this.max + ")");
      }

      if (!(this.step > 0.0)) {
         throw new IllegalArgumentException(name + ": step must be positive");
      }

      this.decimals = this.step >= 1.0 ? 0 : RangesKt.coerceIn((int)Math.ceil(-Math.log10(this.step)), 1, 6);
   }

   public final double getMin() {
      return this.min;
   }

   public final double getMax() {
      return this.max;
   }

   public final double getStep() {
      return this.step;
   }

   @NotNull
   public final String getUnit() {
      return this.unit;
   }

   @NotNull
   protected Double coerce(double newValue) {
      double snapped = MathKt.roundToLong(newValue / this.step) * this.step;
      return RangesKt.coerceIn(snapped, this.min, this.max);
   }

   public final float getFraction() {
      return (float)((this.getValue().doubleValue() - this.min) / (this.max - this.min));
   }

   public final void setFromFraction(float f) {
      this.setValue(this.min + (this.max - this.min) * RangesKt.coerceIn(f, 0.0F, 1.0F));
   }

   @NotNull
   public final String displayValue() {
      String result;
      if (this.decimals == 0) {
         result = String.valueOf(MathKt.roundToLong(this.getValue().doubleValue()));
      } else {
         Locale locale = Locale.ROOT;
         String format = "%." + this.decimals + "f";
         Object[] args = new Object[]{this.getValue()};
         result = String.format(locale, format, Arrays.copyOf(args, args.length));
         Intrinsics.checkNotNullExpressionValue(result, "format(...)");
      }

      String number = result;
      return this.unit.length() == 0 ? number : number + this.unit;
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
            this.setValue(element.getAsDouble());
         }
      }
   }
}
