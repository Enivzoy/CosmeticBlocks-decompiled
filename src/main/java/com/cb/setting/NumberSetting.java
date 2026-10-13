/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.math.MathKt
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.setting;

import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BC\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\b\b\u0002\u0010\t\u001a\u00020\u0002\u0012\b\b\u0002\u0010\n\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u0017J\u000e\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0019J\u0006\u0010\u001f\u001a\u00020\u0004J\u0010\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\n\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u00198F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006$"}, d2={"Lcom/cb/setting/NumberSetting;", "Lcom/cb/setting/Setting;", "", "name", "", "description", "defaultValue", "min", "max", "step", "unit", "<init>", "(Ljava/lang/String;Ljava/lang/String;DDDDLjava/lang/String;)V", "getMin", "()D", "getMax", "getStep", "getUnit", "()Ljava/lang/String;", "decimals", "", "coerce", "new", "(D)Ljava/lang/Double;", "fraction", "", "getFraction", "()F", "setFromFraction", "", "f", "displayValue", "writeJson", "json", "Lcom/google/gson/JsonObject;", "readJson", "cb_client"})
@SourceDebugExtension(value={"SMAP\nNumberSetting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NumberSetting.kt\ncom/cb/setting/NumberSetting\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"})
public final class NumberSetting
extends Setting<Double> {
    private final double min;
    private final double max;
    private final double step;
    @NotNull
    private final String unit;
    private final int decimals;

    public NumberSetting(@NotNull String name, @NotNull String description, double defaultValue, double min, double max, double step, @NotNull String unit) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)unit, (String)"unit");
        super(name, description, defaultValue);
        this.min = min;
        this.max = max;
        this.step = step;
        this.unit = unit;
        if (!(this.min < this.max)) {
            boolean $i$a$-require-NumberSetting$32 = false;
            String $i$a$-require-NumberSetting$32 = name + ": min (" + this.min + ") must be < max (" + this.max + ")";
            throw new IllegalArgumentException($i$a$-require-NumberSetting$32.toString());
        }
        if (!(this.step > 0.0)) {
            boolean bl = false;
            String string = name + ": step must be positive";
            throw new IllegalArgumentException(string.toString());
        }
        this.decimals = this.step >= 1.0 ? 0 : RangesKt.coerceIn((int)((int)Math.ceil(-Math.log10(this.step))), (int)1, (int)6);
    }

    public /* synthetic */ NumberSetting(String string, String string2, double d, double d2, double d3, double d4, String string3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x20) != 0) {
            d4 = 1.0;
        }
        if ((n & 0x40) != 0) {
            string3 = "";
        }
        this(string, string2, d, d2, d3, d4, string3);
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

    @Override
    @NotNull
    protected Double coerce(double d) {
        double snapped = (double)MathKt.roundToLong((double)(d / this.step)) * this.step;
        return RangesKt.coerceIn((double)snapped, (double)this.min, (double)this.max);
    }

    public final float getFraction() {
        return (float)((((Number)this.getValue()).doubleValue() - this.min) / (this.max - this.min));
    }

    public final void setFromFraction(float f) {
        this.setValue(this.min + (this.max - this.min) * (double)RangesKt.coerceIn((float)f, (float)0.0f, (float)1.0f));
    }

    @NotNull
    public final String displayValue() {
        String string;
        if (this.decimals == 0) {
            string = String.valueOf(MathKt.roundToLong((double)((Number)this.getValue()).doubleValue()));
        } else {
            Locale locale = Locale.ROOT;
            String string2 = "%." + this.decimals + "f";
            Object[] objectArray = new Object[]{this.getValue()};
            String string3 = String.format(locale, string2, Arrays.copyOf(objectArray, objectArray.length));
            string = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"format(...)");
        }
        String number = string;
        return ((CharSequence)this.unit).length() == 0 ? number : number + this.unit;
    }

    @Override
    public void writeJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty(this.getName(), (Number)this.getValue());
    }

    @Override
    public void readJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get(this.getName());
        if (jsonElement == null) {
            return;
        }
        JsonElement element = jsonElement;
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.setValue(element.getAsDouble());
        }
    }
}


