/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.dungeon;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\f"}, d2={"Lcom/cb/dungeon/RoomShape;", "", "<init>", "(Ljava/lang/String;I)V", "S1x1", "S1x2", "S1x3", "S1x4", "L", "S2x2", "UNKNOWN", "Companion", "cb_client"})
public final class RoomShape
extends Enum<RoomShape> {
    @NotNull
    public static final Companion Companion;
    public static final /* enum */ RoomShape S1x1;
    public static final /* enum */ RoomShape S1x2;
    public static final /* enum */ RoomShape S1x3;
    public static final /* enum */ RoomShape S1x4;
    public static final /* enum */ RoomShape L;
    public static final /* enum */ RoomShape S2x2;
    public static final /* enum */ RoomShape UNKNOWN;
    private static final /* synthetic */ RoomShape[] $VALUES;
    private static final /* synthetic */ EnumEntries $ENTRIES;

    public static RoomShape[] values() {
        return (RoomShape[])$VALUES.clone();
    }

    public static RoomShape valueOf(String value) {
        return Enum.valueOf(RoomShape.class, value);
    }

    @NotNull
    public static EnumEntries<RoomShape> getEntries() {
        return $ENTRIES;
    }

    static {
        S1x1 = new RoomShape();
        S1x2 = new RoomShape();
        S1x3 = new RoomShape();
        S1x4 = new RoomShape();
        L = new RoomShape();
        S2x2 = new RoomShape();
        UNKNOWN = new RoomShape();
        $VALUES = roomShapeArray = new RoomShape[]{RoomShape.S1x1, RoomShape.S1x2, RoomShape.S1x3, RoomShape.S1x4, RoomShape.L, RoomShape.S2x2, RoomShape.UNKNOWN};
        $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        Companion = new Companion(null);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u00a8\u0006\b"}, d2={"Lcom/cb/dungeon/RoomShape$Companion;", "", "<init>", "()V", "fromString", "Lcom/cb/dungeon/RoomShape;", "s", "", "cb_client"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final RoomShape fromString(@NotNull String s) {
            Intrinsics.checkNotNullParameter((Object)s, (String)"s");
            return switch (s) {
                case "1x1" -> S1x1;
                case "1x2" -> S1x2;
                case "1x3" -> S1x3;
                case "1x4" -> S1x4;
                case "L" -> L;
                case "2x2" -> S2x2;
                default -> UNKNOWN;
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


