/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.Regex
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\tH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bR\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2={"Lcom/cb/utils/ChatUtil;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "PREFIX_COLOR", "", "translateColorCodes", "", "message", "buildMessage", "Lnet/minecraft/network/chat/Component;", "color", "sendRaw", "", "component", "send", "info", "success", "error", "warning", "actionbar", "cb_client"})
public final class ChatUtil {
    @NotNull
    public static final ChatUtil INSTANCE = new ChatUtil();
    private static final int PREFIX_COLOR = 7231183;

    private ChatUtil() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    private final String translateColorCodes(String message) {
        CharSequence charSequence = message;
        Regex regex = new Regex("&([0-9a-fk-or])");
        String string = "\u00a7$1";
        return regex.replace(charSequence, string);
    }

    private final Component buildMessage(String message, int color) {
        MutableComponent mutableComponent = Component.empty();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"empty(...)");
        MutableComponent result = mutableComponent;
        result.append((Component)Component.literal((String)"CB ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb((int)7231183))));
        CharSequence charSequence = message;
        Regex regex = new Regex("&([0-9a-fk-or])");
        String string = "\u00a7$1";
        result.append((Component)Component.literal((String)regex.replace(charSequence, string)).withStyle(Style.EMPTY.withColor(TextColor.fromRgb((int)color))));
        return (Component)result;
    }

    static /* synthetic */ Component buildMessage$default(ChatUtil chatUtil, String string, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0xFFFFFF;
        }
        return chatUtil.buildMessage(string, n);
    }

    private final void sendRaw(Component component) {
        block0: {
            LocalPlayer localPlayer = this.getMc().player;
            if (localPlayer == null) break block0;
            localPlayer.sendSystemMessage(component);
        }
    }

    public final void send(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.sendRaw(ChatUtil.buildMessage$default(this, message, 0, 2, null));
    }

    public final void info(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.send(message);
    }

    public final void success(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.sendRaw(this.buildMessage(message, 0x55FF55));
    }

    public final void error(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.sendRaw(this.buildMessage(message, 0xFF5555));
    }

    public final void warning(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.sendRaw(this.buildMessage(message, 0xFFFF55));
    }

    public final void actionbar(@NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.getMc().gui.setOverlayMessage((Component)Component.literal((String)this.translateColorCodes(message)), false);
    }
}


