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
package me.enivzoy.cosmeticblocksreborn.render;

import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen;
import kotlin.jvm.JvmStatic;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

