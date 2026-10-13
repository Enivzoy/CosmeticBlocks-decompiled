/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmStatic
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.cb.render;

import com.cb.gui.NvgScreen;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u000b\u001a\u00020\fH\u0007R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lcom/cb/render/OverlayRenderer;", "", "<init>", "()V", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "lastFrameNanos", "", "crashed", "", "onFrameEnd", "", "cb_client"})
public final class OverlayRenderer {
    @NotNull
    public static final OverlayRenderer INSTANCE = new OverlayRenderer();
    private static final Logger logger = LoggerFactory.getLogger((String)"cb/overlay");
    private static long lastFrameNanos;
    private static boolean crashed;

    private OverlayRenderer() {
    }

    @JvmStatic
    public static final void onFrameEnd() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            return;
        }
        Screen screen = minecraft.gui.screen();
        NvgScreen nvgScreen = screen instanceof NvgScreen ? (NvgScreen)screen : null;
        if (nvgScreen == null) {
            OverlayRenderer $this$onFrameEnd_u24lambda_u240 = INSTANCE;
            boolean bl = false;
            lastFrameNanos = 0L;
            return;
        }
        NvgScreen screen2 = nvgScreen;
        if (crashed) {
            return;
        }
        long now = System.nanoTime();
        float delta = lastFrameNanos == 0L ? 0.0f : RangesKt.coerceIn((float)((float)((double)(now - lastFrameNanos) / 1.0E9)), (float)0.0f, (float)0.1f);
        lastFrameNanos = now;
        try {
            screen2.renderOverlay(delta);
        }
        catch (Throwable t) {
            crashed = true;
            logger.error("GUI overlay rendering failed \u2014 disabling overlay", t);
        }
    }
}

