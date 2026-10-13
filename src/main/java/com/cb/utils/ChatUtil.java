package com.cb.utils;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;

public final class ChatUtil {
   @NotNull
   public static final ChatUtil INSTANCE = new ChatUtil();
   private static final int PREFIX_COLOR = 7231183;

   private ChatUtil() {
   }

   private final Minecraft getMc() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      return mc;
   }

   private final String translateColorCodes(String message) {
      Regex regex = new Regex("&([0-9a-fk-or])");
      return regex.replace(message, "§$1");
   }

   private final Component buildMessage(String message, int color) {
      MutableComponent result = Component.empty();
      Intrinsics.checkNotNullExpressionValue(result, "empty(...)");
      result.append(Component.literal("CB ").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(7231183))));
      Regex regex = new Regex("&([0-9a-fk-or])");
      result.append(Component.literal(regex.replace(message, "§$1")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(color))));
      return result;
   }

   private final void sendRaw(Component component) {
      LocalPlayer player = this.getMc().player;
      if (player != null) {
         player.sendSystemMessage(component);
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
