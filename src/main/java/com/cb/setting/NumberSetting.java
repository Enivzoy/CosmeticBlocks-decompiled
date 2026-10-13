package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BC\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\b\b\u0002\u0010\t\u001a\u00020\u0002\u0012\b\b\u0002\u0010\n\u001a\u00020\u0004¢\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0014¢\u0006\u0002\u0010\u0017J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0019J\u0006\u0010\u001f\u001a\u00020\u0004J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\n\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006$",
   d2 = {
         "Lcom/cb/setting/NumberSetting;",
         "Lcom/cb/setting/Setting;",
         "",
         "name",
         "",
         "description",
         "defaultValue",
         "min",
         "max",
         "step",
         "unit",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;DDDDLjava/lang/String;)V",
         "getMin",
         "()D",
         "getMax",
         "getStep",
         "getUnit",
         "()Ljava/lang/String;",
         "decimals",
         "",
         "coerce",
         "new",
         "(D)Ljava/lang/Double;",
         "fraction",
         "",
         "getFraction",
         "()F",
         "setFromFraction",
         "",
         "f",
         "displayValue",
         "writeJson",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nNumberSetting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NumberSetting.kt\ncom/cb/setting/NumberSetting\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"
)
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
         int var14/* $VF was: $i$a$-require-NumberSetting$1 */ = 0;
         String var15 = name + ": min (" + this.min + ") must be < max (" + this.max + ")";
         throw new IllegalArgumentException(var15.toString());
      }

      if (!(this.step > 0.0)) {
         int var12/* $VF was: $i$a$-require-NumberSetting$2 */ = 0;
         String var13 = name + ": step must be positive";
         throw new IllegalArgumentException(var13.toString());
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
   protected Double coerce(double var1) {
      double snapped = MathKt.roundToLong(var1 / this.step) * this.step;
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
      String var10000;
      if (this.decimals == 0) {
         var10000 = String.valueOf(MathKt.roundToLong(this.getValue().doubleValue()));
      } else {
         Locale var2 = Locale.ROOT;
         String var3 = "%." + this.decimals + "f";
         Object[] var4 = new Object[]{this.getValue()};
         var4 = var4;
         var10000 = String.format(var2, var3, Arrays.copyOf(var4, var4.length));
         Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      }

      String number = var10000;
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
      JsonElement var10000 = json.get(this.getName());
      if (var10000 != null) {
         JsonElement element = var10000;
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.setValue(element.getAsDouble());
         }
      }
   }
}
