/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.setting;

import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0002H\u0014\u00a2\u0006\u0002\u0010\u0018J.\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u0012\u001a\u00020\nJ\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u0002H\u0002J\u0010\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\"H\u0016R\u001e\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u001e\u0010\u0010\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u001e\u0010\u0012\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u00028F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006$"}, d2={"Lcom/cb/setting/ColorSetting;", "Lcom/cb/setting/Setting;", "", "name", "", "description", "defaultValue", "<init>", "(Ljava/lang/String;Ljava/lang/String;I)V", "value", "", "hue", "getHue", "()F", "saturation", "getSaturation", "brightness", "getBrightness", "alpha", "getAlpha", "syncingFromHsba", "", "coerce", "new", "(I)Ljava/lang/Integer;", "setHsba", "", "opaqueRgb", "getOpaqueRgb", "()I", "decompose", "argb", "writeJson", "json", "Lcom/google/gson/JsonObject;", "readJson", "cb_client"})
public final class ColorSetting
extends Setting<Integer> {
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    private boolean syncingFromHsba;

    public ColorSetting(@NotNull String name, @NotNull String description, int defaultValue) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        super(name, description, defaultValue);
        this.alpha = 1.0f;
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

    @Override
    @NotNull
    protected Integer coerce(int n) {
        if (!this.syncingFromHsba) {
            this.decompose(n);
        }
        return n;
    }

    public final void setHsba(float hue, float saturation, float brightness, float alpha) {
        this.hue = RangesKt.coerceIn((float)hue, (float)0.0f, (float)1.0f);
        this.saturation = RangesKt.coerceIn((float)saturation, (float)0.0f, (float)1.0f);
        this.brightness = RangesKt.coerceIn((float)brightness, (float)0.0f, (float)1.0f);
        this.alpha = RangesKt.coerceIn((float)alpha, (float)0.0f, (float)1.0f);
        int rgb = Color.HSBtoRGB(this.hue, this.saturation, this.brightness) & 0xFFFFFF;
        int a = RangesKt.coerceIn((int)((int)(this.alpha * 255.0f)), (int)0, (int)255);
        int packed = a << 24 | rgb;
        if (packed != ((Number)this.getValue()).intValue()) {
            Function1 listener = this.getOnChanged();
            this.setOnChanged(null);
            this.setValue(packed);
            this.setOnChanged(listener);
        }
    }

    public static /* synthetic */ void setHsba$default(ColorSetting colorSetting, float f, float f2, float f3, float f4, int n, Object object) {
        if ((n & 1) != 0) {
            f = colorSetting.hue;
        }
        if ((n & 2) != 0) {
            f2 = colorSetting.saturation;
        }
        if ((n & 4) != 0) {
            f3 = colorSetting.brightness;
        }
        if ((n & 8) != 0) {
            f4 = colorSetting.alpha;
        }
        colorSetting.setHsba(f, f2, f3, f4);
    }

    public final int getOpaqueRgb() {
        return ((Number)this.getValue()).intValue() | 0xFF000000;
    }

    private final void decompose(int argb) {
        float[] hsb = Color.RGBtoHSB(argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF, null);
        if (hsb[1] > 0.0f) {
            this.hue = hsb[0];
        }
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        this.alpha = (float)(argb >>> 24 & 0xFF) / 255.0f;
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
            this.setValue(element.getAsInt());
            this.decompose(((Number)this.getValue()).intValue());
        }
    }
}


