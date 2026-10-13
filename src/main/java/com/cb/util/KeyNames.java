/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.util;

import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lcom/cb/util/KeyNames;", "", "<init>", "()V", "SPECIAL", "", "", "", "name", "key", "cb_client"})
@SourceDebugExtension(value={"SMAP\nKeyNames.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KeyNames.kt\ncom/cb/util/KeyNames\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,56:1\n1#2:57\n*E\n"})
public final class KeyNames {
    @NotNull
    public static final KeyNames INSTANCE = new KeyNames();
    @NotNull
    private static final Map<Integer, String> SPECIAL;

    private KeyNames() {
    }

    @NotNull
    public final String name(int key) {
        Object object;
        block10: {
            block9: {
                Object object2;
                if (key == -1) {
                    return "None";
                }
                String string = SPECIAL.get(key);
                if (string != null) {
                    String it = string;
                    boolean bl = false;
                    return it;
                }
                boolean bl = 290 <= key ? key < 315 : false;
                if (bl) {
                    return "F" + (key - 290 + 1);
                }
                boolean bl2 = 320 <= key ? key < 330 : false;
                if (bl2) {
                    return "NUM " + (key - 320);
                }
                Object object3 = this;
                try {
                    KeyNames $this$name_u24lambda_u241 = object3;
                    boolean bl3 = false;
                    object2 = Result.constructor-impl((Object)GLFW.glfwGetKeyName((int)key, (int)0));
                }
                catch (Throwable throwable) {
                    object2 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
                }
                object3 = object2;
                String printable = (String)(Result.isFailure-impl((Object)object3) ? null : object3);
                object = printable;
                if (object == null) break block9;
                String string2 = ((String)object).toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"toUpperCase(...)");
                object = string2;
                if (string2 != null) break block10;
            }
            object = "KEY " + key;
        }
        return object;
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)340, (Object)"L SHIFT"), TuplesKt.to((Object)344, (Object)"R SHIFT"), TuplesKt.to((Object)341, (Object)"L CTRL"), TuplesKt.to((Object)345, (Object)"R CTRL"), TuplesKt.to((Object)342, (Object)"L ALT"), TuplesKt.to((Object)346, (Object)"R ALT"), TuplesKt.to((Object)32, (Object)"SPACE"), TuplesKt.to((Object)257, (Object)"ENTER"), TuplesKt.to((Object)258, (Object)"TAB"), TuplesKt.to((Object)259, (Object)"BACK"), TuplesKt.to((Object)261, (Object)"DELETE"), TuplesKt.to((Object)260, (Object)"INSERT"), TuplesKt.to((Object)268, (Object)"HOME"), TuplesKt.to((Object)269, (Object)"END"), TuplesKt.to((Object)266, (Object)"PG UP"), TuplesKt.to((Object)267, (Object)"PG DN"), TuplesKt.to((Object)265, (Object)"UP"), TuplesKt.to((Object)264, (Object)"DOWN"), TuplesKt.to((Object)263, (Object)"LEFT"), TuplesKt.to((Object)262, (Object)"RIGHT"), TuplesKt.to((Object)280, (Object)"CAPS"), TuplesKt.to((Object)282, (Object)"NUM LOCK"), TuplesKt.to((Object)283, (Object)"PRT SC"), TuplesKt.to((Object)284, (Object)"PAUSE"), TuplesKt.to((Object)348, (Object)"MENU"), TuplesKt.to((Object)343, (Object)"L SUPER"), TuplesKt.to((Object)347, (Object)"R SUPER")};
        SPECIAL = MapsKt.mapOf((Pair[])pairArray);
    }
}


