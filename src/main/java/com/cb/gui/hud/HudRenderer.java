/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
 *  net.minecraft.client.DeltaTracker
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.resources.Identifier
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.hud;

import com.cb.gui.hud.HudManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cb/gui/hud/HudRenderer;", "", "<init>", "()V", "register", "", "cb_client"})
public final class HudRenderer {
    @NotNull
    public static final HudRenderer INSTANCE = new HudRenderer();

    private HudRenderer() {
    }

    public final void register() {
        HudElementRegistry.addLast((Identifier)Identifier.fromNamespaceAndPath((String)"cb", (String)"hud"), HudRenderer::register$lambda$0);
    }

    private static final void register$lambda$0(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        Intrinsics.checkNotNullParameter((Object)graphics, (String)"graphics");
        Intrinsics.checkNotNullParameter((Object)deltaTracker, (String)"deltaTracker");
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        HudManager.INSTANCE.render(graphics, mc, deltaTracker.getRealtimeDeltaTicks());
    }
}


