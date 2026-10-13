/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.render;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0012X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lcom/cb/render/Theme;", "", "<init>", "()V", "SCREEN_DIM", "", "SURFACE", "SURFACE_RAISED", "SURFACE_HOVER", "SURFACE_SUNKEN", "OUTLINE", "ACCENT", "ACCENT_SOFT", "TEXT", "TEXT_MUTED", "TEXT_FAINT", "TRACK", "PANEL_WIDTH", "", "PANEL_RADIUS", "HEADER_HEIGHT", "MODULE_HEIGHT", "SETTING_HEIGHT", "PADDING", "FONT_SIZE", "FONT_SIZE_SMALL", "FONT_SIZE_HEADER", "withAlpha", "color", "alpha", "cb_client"})
public final class Theme {
    @NotNull
    public static final Theme INSTANCE = new Theme();
    public static final int SCREEN_DIM = 0x66000000;
    public static final int SURFACE = -183233516;
    public static final int SURFACE_RAISED = -14935012;
    public static final int SURFACE_HOVER = 0x12FFFFFF;
    public static final int SURFACE_SUNKEN = 0x66000000;
    public static final int OUTLINE = 0x14FFFFFF;
    public static final int ACCENT = -1750963;
    public static final int ACCENT_SOFT = 1088768077;
    public static final int TEXT = -1184275;
    public static final int TEXT_MUTED = -6513508;
    public static final int TEXT_FAINT = -10724260;
    public static final int TRACK = -14013910;
    public static final float PANEL_WIDTH = 240.0f;
    public static final float PANEL_RADIUS = 10.0f;
    public static final float HEADER_HEIGHT = 36.0f;
    public static final float MODULE_HEIGHT = 30.0f;
    public static final float SETTING_HEIGHT = 26.0f;
    public static final float PADDING = 10.0f;
    public static final float FONT_SIZE = 13.0f;
    public static final float FONT_SIZE_SMALL = 12.0f;
    public static final float FONT_SIZE_HEADER = 14.0f;

    private Theme() {
    }

    public final int withAlpha(int color, float alpha) {
        int a = RangesKt.coerceIn((int)((int)((float)(color >>> 24) * RangesKt.coerceIn((float)alpha, (float)0.0f, (float)1.0f))), (int)0, (int)255);
        return a << 24 | color & 0xFFFFFF;
    }
}


