/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.dungeon;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0014\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0010\u001a\u00020\u0003H\u00d6\u0081\u0004J\n\u0010\u0011\u001a\u00020\u0012H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b\u00a8\u0006\u0013"}, d2={"Lcom/cb/dungeon/Vec2i;", "", "x", "", "z", "<init>", "(II)V", "getX", "()I", "getZ", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "cb_client"})
public final class Vec2i {
    private final int x;
    private final int z;

    public Vec2i(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public final int getX() {
        return this.x;
    }

    public final int getZ() {
        return this.z;
    }

    public final int component1() {
        return this.x;
    }

    public final int component2() {
        return this.z;
    }

    @NotNull
    public final Vec2i copy(int x, int z) {
        return new Vec2i(x, z);
    }

    public static /* synthetic */ Vec2i copy$default(Vec2i vec2i, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = vec2i.x;
        }
        if ((n3 & 2) != 0) {
            n2 = vec2i.z;
        }
        return vec2i.copy(n, n2);
    }

    @NotNull
    public String toString() {
        return "Vec2i(x=" + this.x + ", z=" + this.z + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.x);
        result = result * 31 + Integer.hashCode(this.z);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vec2i)) {
            return false;
        }
        Vec2i vec2i = (Vec2i)other;
        if (this.x != vec2i.x) {
            return false;
        }
        return this.z == vec2i.z;
    }
}


