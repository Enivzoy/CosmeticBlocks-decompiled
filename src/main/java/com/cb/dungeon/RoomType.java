/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.dungeon;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \f2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\fB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\r"}, d2={"Lcom/cb/dungeon/RoomType;", "", "<init>", "(Ljava/lang/String;I)V", "BLOOD", "CHAMPION", "ENTRANCE", "FAIRY", "NORMAL", "PUZZLE", "RARE", "TRAP", "Companion", "cb_client"})
public final class RoomType
extends Enum<RoomType> {
    @NotNull
    public static final Companion Companion;
    public static final /* enum */ RoomType BLOOD;
    public static final /* enum */ RoomType CHAMPION;
    public static final /* enum */ RoomType ENTRANCE;
    public static final /* enum */ RoomType FAIRY;
    public static final /* enum */ RoomType NORMAL;
    public static final /* enum */ RoomType PUZZLE;
    public static final /* enum */ RoomType RARE;
    public static final /* enum */ RoomType TRAP;
    private static final /* synthetic */ RoomType[] $VALUES;
    private static final /* synthetic */ EnumEntries $ENTRIES;

    public static RoomType[] values() {
        return (RoomType[])$VALUES.clone();
    }

    public static RoomType valueOf(String value) {
        return Enum.valueOf(RoomType.class, value);
    }

    @NotNull
    public static EnumEntries<RoomType> getEntries() {
        return $ENTRIES;
    }

    static {
        BLOOD = new RoomType();
        CHAMPION = new RoomType();
        ENTRANCE = new RoomType();
        FAIRY = new RoomType();
        NORMAL = new RoomType();
        PUZZLE = new RoomType();
        RARE = new RoomType();
        TRAP = new RoomType();
        $VALUES = roomTypeArray = new RoomType[]{RoomType.BLOOD, RoomType.CHAMPION, RoomType.ENTRANCE, RoomType.FAIRY, RoomType.NORMAL, RoomType.PUZZLE, RoomType.RARE, RoomType.TRAP};
        $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        Companion = new Companion(null);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u00a8\u0006\b"}, d2={"Lcom/cb/dungeon/RoomType$Companion;", "", "<init>", "()V", "fromString", "Lcom/cb/dungeon/RoomType;", "s", "", "cb_client"})
    @SourceDebugExtension(value={"SMAP\nRoomData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RoomData.kt\ncom/cb/dungeon/RoomType$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n296#2,2:112\n*S KotlinDebug\n*F\n+ 1 RoomData.kt\ncom/cb/dungeon/RoomType$Companion\n*L\n38#1:112,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final RoomType fromString(@NotNull String s) {
            RoomType roomType;
            Object v0;
            block2: {
                Intrinsics.checkNotNullParameter((Object)s, (String)"s");
                Iterable $this$firstOrNull$iv = (Iterable)RoomType.getEntries();
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    RoomType it = (RoomType)((Object)element$iv);
                    boolean bl = false;
                    if (!StringsKt.equals((String)it.name(), (String)s, (boolean)true)) continue;
                    v0 = element$iv;
                    break block2;
                }
                v0 = null;
            }
            if ((roomType = (RoomType)v0) == null) {
                roomType = NORMAL;
            }
            return roomType;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


