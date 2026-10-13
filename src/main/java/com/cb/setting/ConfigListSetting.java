/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.setting;

import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\b0\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lcom/cb/setting/ConfigListSetting;", "Lcom/cb/setting/Setting;", "", "name", "description", "defaultValue", "optionsProvider", "Lkotlin/Function0;", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "options", "writeJson", "", "json", "Lcom/google/gson/JsonObject;", "readJson", "cb_client"})
public final class ConfigListSetting
extends Setting<String> {
    @NotNull
    private final Function0<List<String>> optionsProvider;

    public ConfigListSetting(@NotNull String name, @NotNull String description, @NotNull String defaultValue, @NotNull Function0<? extends List<String>> optionsProvider) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)defaultValue, (String)"defaultValue");
        Intrinsics.checkNotNullParameter(optionsProvider, (String)"optionsProvider");
        super(name, description, defaultValue);
        this.optionsProvider = optionsProvider;
    }

    @NotNull
    public final List<String> options() {
        return (List)this.optionsProvider.invoke();
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


