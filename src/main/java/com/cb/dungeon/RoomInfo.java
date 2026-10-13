/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.dungeon;

import com.cb.dungeon.RoomShape;
import com.cb.dungeon.RoomType;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u000bH\u00c6\u0003JA\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010!\u001a\u00020\bH\u00d6\u0081\u0004J\n\u0010\"\u001a\u00020\u0003H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006#"}, d2={"Lcom/cb/dungeon/RoomInfo;", "", "name", "", "type", "Lcom/cb/dungeon/RoomType;", "cores", "", "", "secrets", "shape", "Lcom/cb/dungeon/RoomShape;", "<init>", "(Ljava/lang/String;Lcom/cb/dungeon/RoomType;Ljava/util/List;ILcom/cb/dungeon/RoomShape;)V", "getName", "()Ljava/lang/String;", "getType", "()Lcom/cb/dungeon/RoomType;", "getCores", "()Ljava/util/List;", "getSecrets", "()I", "getShape", "()Lcom/cb/dungeon/RoomShape;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "cb_client"})
public final class RoomInfo {
    @NotNull
    private final String name;
    @NotNull
    private final RoomType type;
    @NotNull
    private final List<Integer> cores;
    private final int secrets;
    @NotNull
    private final RoomShape shape;

    public RoomInfo(@NotNull String name, @NotNull RoomType type2, @NotNull List<Integer> cores, int secrets, @NotNull RoomShape shape) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)((Object)type2), (String)"type");
        Intrinsics.checkNotNullParameter(cores, (String)"cores");
        Intrinsics.checkNotNullParameter((Object)((Object)shape), (String)"shape");
        this.name = name;
        this.type = type2;
        this.cores = cores;
        this.secrets = secrets;
        this.shape = shape;
    }

    public /* synthetic */ RoomInfo(String string, RoomType roomType, List list, int n, RoomShape roomShape, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            n = 0;
        }
        if ((n2 & 0x10) != 0) {
            roomShape = RoomShape.S1x1;
        }
        this(string, roomType, list, n, roomShape);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final RoomType getType() {
        return this.type;
    }

    @NotNull
    public final List<Integer> getCores() {
        return this.cores;
    }

    public final int getSecrets() {
        return this.secrets;
    }

    @NotNull
    public final RoomShape getShape() {
        return this.shape;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final RoomType component2() {
        return this.type;
    }

    @NotNull
    public final List<Integer> component3() {
        return this.cores;
    }

    public final int component4() {
        return this.secrets;
    }

    @NotNull
    public final RoomShape component5() {
        return this.shape;
    }

    @NotNull
    public final RoomInfo copy(@NotNull String name, @NotNull RoomType type2, @NotNull List<Integer> cores, int secrets, @NotNull RoomShape shape) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)((Object)type2), (String)"type");
        Intrinsics.checkNotNullParameter(cores, (String)"cores");
        Intrinsics.checkNotNullParameter((Object)((Object)shape), (String)"shape");
        return new RoomInfo(name, type2, cores, secrets, shape);
    }

    public static /* synthetic */ RoomInfo copy$default(RoomInfo roomInfo, String string, RoomType roomType, List list, int n, RoomShape roomShape, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = roomInfo.name;
        }
        if ((n2 & 2) != 0) {
            roomType = roomInfo.type;
        }
        if ((n2 & 4) != 0) {
            list = roomInfo.cores;
        }
        if ((n2 & 8) != 0) {
            n = roomInfo.secrets;
        }
        if ((n2 & 0x10) != 0) {
            roomShape = roomInfo.shape;
        }
        return roomInfo.copy(string, roomType, list, n, roomShape);
    }

    @NotNull
    public String toString() {
        return "RoomInfo(name=" + this.name + ", type=" + this.type + ", cores=" + this.cores + ", secrets=" + this.secrets + ", shape=" + this.shape + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.type.hashCode();
        result = result * 31 + ((Object)this.cores).hashCode();
        result = result * 31 + Integer.hashCode(this.secrets);
        result = result * 31 + this.shape.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RoomInfo)) {
            return false;
        }
        RoomInfo roomInfo = (RoomInfo)other;
        if (!Intrinsics.areEqual((Object)this.name, (Object)roomInfo.name)) {
            return false;
        }
        if (this.type != roomInfo.type) {
            return false;
        }
        if (!Intrinsics.areEqual(this.cores, roomInfo.cores)) {
            return false;
        }
        if (this.secrets != roomInfo.secrets) {
            return false;
        }
        return this.shape == roomInfo.shape;
    }
}


