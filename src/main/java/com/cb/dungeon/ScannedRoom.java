/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.dungeon;

import com.cb.dungeon.RoomInfo;
import com.cb.dungeon.Rotation;
import com.cb.dungeon.Vec2i;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J7\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010!\u001a\u00020\"H\u00d6\u0081\u0004J\n\u0010#\u001a\u00020$H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006%"}, d2={"Lcom/cb/dungeon/ScannedRoom;", "", "data", "Lcom/cb/dungeon/RoomInfo;", "rotation", "Lcom/cb/dungeon/Rotation;", "clayPos", "Lnet/minecraft/core/BlockPos;", "components", "", "Lcom/cb/dungeon/Vec2i;", "<init>", "(Lcom/cb/dungeon/RoomInfo;Lcom/cb/dungeon/Rotation;Lnet/minecraft/core/BlockPos;Ljava/util/Set;)V", "getData", "()Lcom/cb/dungeon/RoomInfo;", "getRotation", "()Lcom/cb/dungeon/Rotation;", "setRotation", "(Lcom/cb/dungeon/Rotation;)V", "getClayPos", "()Lnet/minecraft/core/BlockPos;", "setClayPos", "(Lnet/minecraft/core/BlockPos;)V", "getComponents", "()Ljava/util/Set;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "", "cb_client"})
public final class ScannedRoom {
    @NotNull
    private final RoomInfo data;
    @NotNull
    private Rotation rotation;
    @NotNull
    private BlockPos clayPos;
    @NotNull
    private final Set<Vec2i> components;

    public ScannedRoom(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        Intrinsics.checkNotNullParameter((Object)clayPos, (String)"clayPos");
        Intrinsics.checkNotNullParameter(components, (String)"components");
        this.data = data;
        this.rotation = rotation;
        this.clayPos = clayPos;
        this.components = components;
    }

    public /* synthetic */ ScannedRoom(RoomInfo roomInfo, Rotation rotation, BlockPos blockPos, Set set, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            rotation = Rotation.NONE;
        }
        if ((n & 4) != 0) {
            BlockPos blockPos2 = BlockPos.ZERO;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"ZERO");
            blockPos = blockPos2;
        }
        if ((n & 8) != 0) {
            set = new LinkedHashSet();
        }
        this(roomInfo, rotation, blockPos, set);
    }

    @NotNull
    public final RoomInfo getData() {
        return this.data;
    }

    @NotNull
    public final Rotation getRotation() {
        return this.rotation;
    }

    public final void setRotation(@NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"<set-?>");
        this.rotation = rotation;
    }

    @NotNull
    public final BlockPos getClayPos() {
        return this.clayPos;
    }

    public final void setClayPos(@NotNull BlockPos blockPos) {
        Intrinsics.checkNotNullParameter((Object)blockPos, (String)"<set-?>");
        this.clayPos = blockPos;
    }

    @NotNull
    public final Set<Vec2i> getComponents() {
        return this.components;
    }

    @NotNull
    public final RoomInfo component1() {
        return this.data;
    }

    @NotNull
    public final Rotation component2() {
        return this.rotation;
    }

    @NotNull
    public final BlockPos component3() {
        return this.clayPos;
    }

    @NotNull
    public final Set<Vec2i> component4() {
        return this.components;
    }

    @NotNull
    public final ScannedRoom copy(@NotNull RoomInfo data, @NotNull Rotation rotation, @NotNull BlockPos clayPos, @NotNull Set<Vec2i> components) {
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        Intrinsics.checkNotNullParameter((Object)clayPos, (String)"clayPos");
        Intrinsics.checkNotNullParameter(components, (String)"components");
        return new ScannedRoom(data, rotation, clayPos, components);
    }

    public static /* synthetic */ ScannedRoom copy$default(ScannedRoom scannedRoom, RoomInfo roomInfo, Rotation rotation, BlockPos blockPos, Set set, int n, Object object) {
        if ((n & 1) != 0) {
            roomInfo = scannedRoom.data;
        }
        if ((n & 2) != 0) {
            rotation = scannedRoom.rotation;
        }
        if ((n & 4) != 0) {
            blockPos = scannedRoom.clayPos;
        }
        if ((n & 8) != 0) {
            set = scannedRoom.components;
        }
        return scannedRoom.copy(roomInfo, rotation, blockPos, set);
    }

    @NotNull
    public String toString() {
        return "ScannedRoom(data=" + this.data + ", rotation=" + this.rotation + ", clayPos=" + this.clayPos + ", components=" + this.components + ")";
    }

    public int hashCode() {
        int result = this.data.hashCode();
        result = result * 31 + this.rotation.hashCode();
        result = result * 31 + this.clayPos.hashCode();
        result = result * 31 + ((Object)this.components).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScannedRoom)) {
            return false;
        }
        ScannedRoom scannedRoom = (ScannedRoom)other;
        if (!Intrinsics.areEqual((Object)this.data, (Object)scannedRoom.data)) {
            return false;
        }
        if (this.rotation != scannedRoom.rotation) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.clayPos, (Object)scannedRoom.clayPos)) {
            return false;
        }
        return Intrinsics.areEqual(this.components, scannedRoom.components);
    }
}


