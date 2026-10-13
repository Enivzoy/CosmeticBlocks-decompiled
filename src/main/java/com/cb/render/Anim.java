/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.ranges.RangesKt
 */
package com.cb.render;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0003R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\t\"\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, d2={"Lcom/cb/render/Anim;", "", "initial", "", "speed", "<init>", "(FF)V", "value", "getValue", "()F", "target", "getTarget", "setTarget", "(F)V", "update", "delta", "snap", "", "v", "cb_client"})
public final class Anim {
    private final float speed;
    private float value;
    private float target;

    public Anim(float initial, float speed) {
        this.speed = speed;
        this.value = initial;
        this.target = initial;
    }

    public /* synthetic */ Anim(float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f2 = 14.0f;
        }
        this(f, f2);
    }

    public final float getValue() {
        return this.value;
    }

    public final float getTarget() {
        return this.target;
    }

    public final void setTarget(float f) {
        this.target = f;
    }

    public final float update(float delta) {
        float t = RangesKt.coerceIn((float)(delta * this.speed), (float)0.0f, (float)1.0f);
        this.value += (this.target - this.value) * t;
        if (Math.abs(this.target - this.value) < 5.0E-4f) {
            this.value = this.target;
        }
        return this.value;
    }

    public final void snap(float v) {
        this.value = v;
        this.target = v;
    }
}


