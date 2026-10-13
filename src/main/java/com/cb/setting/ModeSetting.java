/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.CharsKt
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.setting;

import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00028\u0000\u00a2\u0006\u0004\b\b\u0010\tJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0012\u0010\u0010\u001a\u00020\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0002J\u0006\u0010\u0012\u001a\u00020\u0005J\u0012\u0010\u0013\u001a\u00020\u000f2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0018"}, d2={"Lcom/cb/setting/ModeSetting;", "E", "", "Lcom/cb/setting/Setting;", "name", "", "description", "defaultValue", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Enum;)V", "options", "", "getOptions", "()Ljava/util/List;", "cycle", "", "label", "option", "currentLabel", "select", "writeJson", "json", "Lcom/google/gson/JsonObject;", "readJson", "cb_client"})
@SourceDebugExtension(value={"SMAP\nModeSetting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModeSetting.kt\ncom/cb/setting/ModeSetting\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1#2:65\n296#3,2:66\n*S KotlinDebug\n*F\n+ 1 ModeSetting.kt\ncom/cb/setting/ModeSetting\n*L\n61#1:66,2\n*E\n"})
public final class ModeSetting<E extends Enum<E>>
extends Setting<E> {
    @NotNull
    private final List<E> options;

    public ModeSetting(@NotNull String name, @NotNull String description, @NotNull E defaultValue) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter(defaultValue, (String)"defaultValue");
        super(name, description, defaultValue);
        Class clazz = ((Enum)defaultValue).getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(clazz, (String)"getDeclaringClass(...)");
        Object[] objectArray = clazz.getEnumConstants();
        Intrinsics.checkNotNullExpressionValue(objectArray, (String)"getEnumConstants(...)");
        this.options = ArraysKt.toList((Object[])objectArray);
    }

    @NotNull
    public final List<E> getOptions() {
        return this.options;
    }

    public final void cycle() {
        this.setValue(this.options.get((this.options.indexOf(this.getValue()) + 1) % this.options.size()));
    }

    @NotNull
    public final String label(@NotNull Enum<?> option) {
        Intrinsics.checkNotNullParameter(option, (String)"option");
        Object object = option.name();
        Locale locale = Locale.ROOT;
        Intrinsics.checkNotNullExpressionValue((Object)locale, (String)"ROOT");
        String string = ((String)object).toLowerCase(locale);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toLowerCase(...)");
        object = new char[1];
        object[0] = 95;
        return CollectionsKt.joinToString$default((Iterable)StringsKt.split$default((CharSequence)string, (char[])object, (boolean)false, (int)0, (int)6, null), (CharSequence)" ", null, null, (int)0, null, ModeSetting::label$lambda$0, (int)30, null);
    }

    @NotNull
    public final String currentLabel() {
        return this.label((Enum)this.getValue());
    }

    public final void select(@NotNull Enum<?> option) {
        Intrinsics.checkNotNullParameter(option, (String)"option");
        if (!CollectionsKt.contains((Iterable)this.options, option)) {
            boolean bl = false;
            String string = this.getName() + ": " + option.name() + " is not a valid option";
            throw new IllegalArgumentException(string.toString());
        }
        this.setValue(option);
    }

    @Override
    public void writeJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty(this.getName(), ((Enum)this.getValue()).name());
    }

    @Override
    public void readJson(@NotNull JsonObject json) {
        block4: {
            Object v1;
            block3: {
                Intrinsics.checkNotNullParameter((Object)json, (String)"json");
                JsonElement jsonElement = json.get(this.getName());
                if (jsonElement == null) {
                    return;
                }
                JsonElement element = jsonElement;
                if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString()) {
                    return;
                }
                String stored = element.getAsString();
                Iterable $this$firstOrNull$iv = this.options;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    Enum it = (Enum)element$iv;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.name(), (Object)stored)) continue;
                    v1 = element$iv;
                    break block3;
                }
                v1 = null;
            }
            Enum enum_ = v1;
            if (enum_ == null) break block4;
            Enum it = enum_;
            boolean bl = false;
            this.setValue(it);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static final CharSequence label$lambda$0(String word) {
        String string;
        Intrinsics.checkNotNullParameter((Object)word, (String)"word");
        String string2 = word;
        if (((CharSequence)string2).length() > 0) {
            void it;
            char c = string2.charAt(0);
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = false;
            Locale locale = Locale.ROOT;
            Intrinsics.checkNotNullExpressionValue((Object)locale, (String)"ROOT");
            StringBuilder stringBuilder2 = stringBuilder.append((Object)CharsKt.uppercase((char)it, (Locale)locale));
            String string3 = string2;
            int n = 1;
            String string4 = string3.substring(n);
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"substring(...)");
            string = stringBuilder2.append(string4).toString();
        } else {
            string = string2;
        }
        return string;
    }
}


