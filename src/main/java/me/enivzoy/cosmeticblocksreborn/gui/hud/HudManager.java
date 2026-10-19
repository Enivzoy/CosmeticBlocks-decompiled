package me.enivzoy.cosmeticblocksreborn.gui.hud;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class HudManager {
   @NotNull
   public static final HudManager INSTANCE = new HudManager();
   @NotNull
   private static final List<HudElement> hudElements = new ArrayList<>();
   @NotNull
   private static final File configFile = new File("config/cb/hud_positions.json");

   private HudManager() {
   }

   public final void registerElement(@NotNull HudElement element) {
      Intrinsics.checkNotNullParameter(element, "element");
      hudElements.add(element);
   }

   @NotNull
   public final List<HudElement> getElements() {
      return hudElements;
   }

   @Nullable
   public final HudElement getElement(@NotNull String id) {
      Intrinsics.checkNotNullParameter(id, "id");

      for (HudElement element : hudElements) {
         if (Intrinsics.areEqual(element.getId(), id)) {
            return element;
         }
      }

      return null;
   }

   public final void render(@NotNull GuiGraphicsExtractor graphics, @NotNull Minecraft mc, float partialTicks) {
      Intrinsics.checkNotNullParameter(graphics, "graphics");
      Intrinsics.checkNotNullParameter(mc, "mc");

      for (HudElement element : hudElements) {
         element.render(graphics, mc, partialTicks);
      }
   }

   public final void savePositions() {
      File configDir = new File("config/cb");
      if (!configDir.exists()) {
         configDir.mkdirs();
      }

      JsonObject json = new JsonObject();

      for (HudElement element : hudElements) {
         JsonObject elementJson = new JsonObject();
         elementJson.addProperty("x", element.getX());
         elementJson.addProperty("y", element.getY());
         elementJson.addProperty("width", element.getWidth());
         elementJson.addProperty("height", element.getHeight());
         json.add(element.getId(), elementJson);
      }

      try {
         String content = new Gson().toJson(json);
         Intrinsics.checkNotNullExpressionValue(content, "toJson(...)");
         FilesKt.writeText(configFile, content, kotlin.text.Charsets.UTF_8);
      } catch (Exception e) {
         System.out.println((Object)("CB: Failed to save HUD positions: " + e.getMessage()));
      }
   }

   public final void loadPositions() {
      if (configFile.exists()) {
         try {
            JsonObject json = JsonParser.parseString(FilesKt.readText(configFile, kotlin.text.Charsets.UTF_8)).getAsJsonObject();

            for (HudElement element : hudElements) {
               JsonObject elementJson = json.getAsJsonObject(element.getId());
               if (elementJson != null) {
                  JsonElement x = elementJson.get("x");
                  element.setX(x != null ? x.getAsInt() : element.getX());
                  JsonElement y = elementJson.get("y");
                  element.setY(y != null ? y.getAsInt() : element.getY());
                  JsonElement width = elementJson.get("width");
                  element.setWidth(width != null ? width.getAsInt() : element.getWidth());
                  JsonElement height = elementJson.get("height");
                  element.setHeight(height != null ? height.getAsInt() : element.getHeight());
               }
            }
         } catch (Exception e) {
            System.out.println((Object)("CB: Failed to load HUD positions: " + e.getMessage()));
         }
      }
   }

   static {
      INSTANCE.loadPositions();
   }
}
