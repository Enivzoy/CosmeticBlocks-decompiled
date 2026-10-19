/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 *  org.lwjgl.glfw.GLFW
 */
package me.enivzoy.cosmeticblocksreborn;

import me.enivzoy.cosmeticblocksreborn.command.CbCommand;
import me.enivzoy.cosmeticblocksreborn.config.Config;
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab;
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import me.enivzoy.cosmeticblocksreborn.events.EventDispatcher;
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.gui.hud.HudRenderer;
import me.enivzoy.cosmeticblocksreborn.gui.screens.BlockSelectorScreen;
import me.enivzoy.cosmeticblocksreborn.gui.screens.CliEngine;
import me.enivzoy.cosmeticblocksreborn.gui.screens.CliScreen;
import me.enivzoy.cosmeticblocksreborn.gui.screens.RadialBlockScreen;
import me.enivzoy.cosmeticblocksreborn.render.PulsiveRenderBootstrap;
import me.enivzoy.cosmeticblocksreborn.utils.Scheduler;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public final class CbClient
implements ClientModInitializer {
    @NotNull
    public static final CbClient INSTANCE = new CbClient();
    @NotNull
    public static final String NAMESPACE = "cb";
    @NotNull
    private static final File configDir = new File("config/cb");
    @NotNull
    private static final HashMap<Integer, Boolean> keyWasDown = new HashMap();

    private CbClient() {
    }

    public void onInitializeClient() {
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        PulsiveRenderBootstrap.register();
        EventDispatcher.INSTANCE.init();
        HudRenderer.INSTANCE.register();
        CosmeticBlocks.INSTANCE.register();
        Scheduler.INSTANCE.register();
        Config.INSTANCE.load();
        ClientLifecycleEvents.CLIENT_STOPPING.register(CbClient::onClientStopping);
        ClientTickEvents.END_CLIENT_TICK.register(CbClient::onEndClientTick);
        CbCommand.INSTANCE.register();
    }

    private final void pollOpenBind(Minecraft client, int key, Function0<? extends Screen> screen) {
        if (key == -1) {
            return;
        }
        long window = Minecraft.getInstance().getWindow().handle();
        boolean down = GLFW.glfwGetKey((long)window, (int)key) == 1;
        if (down && !Intrinsics.areEqual((Object)keyWasDown.get(key), (Object)true)) {
            client.gui.setScreen((Screen)screen.invoke());
        }
        ((Map)keyWasDown).put(key, down);
    }

    private static final void onClientStopping(Minecraft minecraft) {
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"<unused var>");
        CosmeticBlocks.INSTANCE.flushAll();
    }

    private static final void onEndClientTick(Minecraft client) {
        Intrinsics.checkNotNullParameter((Object)client, (String)"client");
        if (client.gui.screen() == null) {
            INSTANCE.pollOpenBind(client, ((Number)GuiTab.INSTANCE.getOpenGuiKeybind().getValue()).intValue(), ClickGuiScreen::new);
            INSTANCE.pollOpenBind(client, ((Number)GuiTab.INSTANCE.getOpenCliKeybind().getValue()).intValue(), CliScreen::new);
            INSTANCE.pollOpenBind(client, ((Number)CosmeticBlocksTab.INSTANCE.getBlockSelectorKeybind().getValue()).intValue(), BlockSelectorScreen::new);
            INSTANCE.pollOpenBind(client, ((Number)CosmeticBlocksTab.INSTANCE.getBlockWheelKeybind().getValue()).intValue(), RadialBlockScreen::new);
        } else {
            keyWasDown.clear();
        }
        CliEngine.INSTANCE.checkReminders();
    }
}
