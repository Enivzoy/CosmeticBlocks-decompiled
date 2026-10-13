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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\u000f\u001a\u00020\tH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0002J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bJ\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\u000bR\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0019",
   d2 = {
         "Lcom/cb/utils/ChatUtil;",
         "",
         "<init>",
         "()V",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "PREFIX_COLOR",
         "",
         "translateColorCodes",
         "",
         "message",
         "buildMessage",
         "Lnet/minecraft/network/chat/Component;",
         "color",
         "sendRaw",
         "",
         "component",
         "send",
         "info",
         "success",
         "error",
         "warning",
         "actionbar",
         "cb_client"
   }
)
public final class ChatUtil {
   @NotNull
   public static final ChatUtil INSTANCE = new ChatUtil();
   private static final int PREFIX_COLOR = 7231183;

   private ChatUtil() {
   }

   private final Minecraft getMc() {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
   }

   private final String translateColorCodes(String message) {
      CharSequence var2 = message;
      Regex var3 = new Regex("&([0-9a-fk-or])");
      String var4 = "§$1";
      return var3.replace(var2, var4);
   }

   private final Component buildMessage(String message, int color) {
      MutableComponent var10000 = Component.empty();
      Intrinsics.checkNotNullExpressionValue(var10000, "empty(...)");
      MutableComponent result = var10000;
      result.append(Component.literal("CB ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(7231183))));
      CharSequence var4 = message;
      Regex var5 = new Regex("&([0-9a-fk-or])");
      String var6 = "§$1";
      result.append(Component.literal(var5.replace(var4, var6)).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))));
      return result;
   }

   private final void sendRaw(Component component) {
      LocalPlayer var10000 = this.getMc().player;
      if (var10000 != null) {
         var10000.sendSystemMessage(component);
      }
   }

   public final void send(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.sendRaw(this.buildMessage(message, 0));
   }

   public final void info(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.send(message);
   }

   public final void success(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.sendRaw(this.buildMessage(message, 5635925));
   }

   public final void error(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.sendRaw(this.buildMessage(message, 16733525));
   }

   public final void warning(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.sendRaw(this.buildMessage(message, 16777045));
   }

   public final void actionbar(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      this.getMc().gui.hud.setOverlayMessage(Component.literal(this.translateColorCodes(message)), false);
   }
}
