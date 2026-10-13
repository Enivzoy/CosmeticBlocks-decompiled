/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.setting;

import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0002H\u0014J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0016"}, d2={"Lcom/cb/setting/StringSetting;", "Lcom/cb/setting/Setting;", "", "name", "description", "defaultValue", "maxLength", "", "placeholder", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V", "getMaxLength", "()I", "getPlaceholder", "()Ljava/lang/String;", "coerce", "new", "writeJson", "", "json", "Lcom/google/gson/JsonObject;", "readJson", "cb_client"})
public final class StringSetting
extends Setting<String> {
    private final int maxLength;
    @NotNull
    private final String placeholder;

    public StringSetting(@NotNull String name, @NotNull String description, @NotNull String defaultValue, int maxLength, @NotNull String placeholder) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)defaultValue, (String)"defaultValue");
        Intrinsics.checkNotNullParameter((Object)placeholder, (String)"placeholder");
        super(name, description, defaultValue);
        this.maxLength = maxLength;
        this.placeholder = placeholder;
    }

    public /* synthetic */ StringSetting(String string, String string2, String string3, int n, String string4, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            n = 64;
        }
        if ((n2 & 0x10) != 0) {
            string4 = "\u2026";
        }
        this(string, string2, string3, n, string4);
    }

    public final int getMaxLength() {
        return this.maxLength;
    }

    @NotNull
    public final String getPlaceholder() {
        return this.placeholder;
    }

    @Override
    @NotNull
    protected String coerce(@NotNull String string) {
        String string2;
        Intrinsics.checkNotNullParameter((Object)string, (String)"new");
        if (string.length() > this.maxLength) {
            String string3 = string.substring(0, this.maxLength);
            string2 = string3;
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
        } else {
            string2 = string;
        }
        return string2;
    }

    @Override
    public void writeJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty(this.getName(), (String)this.getValue());
    }

    @Override
    public void readJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        JsonElement jsonElement = json.get(this.getName());
        if (jsonElement == null) {
            return;
        }
        JsonElement element = jsonElement;
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String string = element.getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getAsString(...)");
            this.setValue(string);
        }
    }
}


