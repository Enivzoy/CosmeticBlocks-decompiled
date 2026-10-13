package com.cb.gui.hud;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0010J\u001e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\nJ\u0006\u0010\u0019\u001a\u00020\nR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a",
   d2 = {
         "Lcom/cb/gui/hud/HudManager;",
         "",
         "<init>",
         "()V",
         "hudElements",
         "",
         "Lcom/cb/gui/hud/HudElement;",
         "configFile",
         "Ljava/io/File;",
         "registerElement",
         "",
         "element",
         "getElements",
         "",
         "getElement",
         "id",
         "",
         "render",
         "graphics",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "partialTicks",
         "",
         "savePositions",
         "loadPositions",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nHudManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HudManager.kt\ncom/cb/gui/hud/HudManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"
)
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
      Iterable var2 = hudElements;
      Iterator var3 = var2.iterator();

      Object var10000;
      while (true) {
         if (var3.hasNext()) {
            Object var4 = var3.next();
            HudElement it = (HudElement)var4;
            int var6/* $VF was: $i$a$-find-HudManager$getElement$1 */ = 0;
            if (!Intrinsics.areEqual(it.getId(), id)) {
               continue;
            }

            var10000 = var4;
            break;
         }

         var10000 = null;
         break;
      }

      return (HudElement)var10000;
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
         File var10000 = configFile;
         String var10001 = new Gson().toJson(json);
         Intrinsics.checkNotNullExpressionValue(var10001, "toJson(...)");
         FilesKt.writeText(var10000, var10001, kotlin.text.Charsets.UTF_8);
      } catch (Exception e) {
         System.out.println((Object)("CB: Failed to save HUD positions: " + e.getMessage()));
      }
   }

   public final void loadPositions() {
      if (configFile.exists()) {
         try {
            JsonObject json = JsonParser.parseString(FilesKt.readText(configFile, kotlin.text.Charsets.UTF_8)).getAsJsonObject();

            for (HudElement element : hudElements) {
               JsonObject var10000 = json.getAsJsonObject(element.getId());
               if (var10000 != null) {
                  JsonObject elementJson = var10000;
                  JsonElement var10001 = elementJson.get("x");
                  element.setX(var10001 != null ? var10001.getAsInt() : element.getX());
                  var10001 = elementJson.get("y");
                  element.setY(var10001 != null ? var10001.getAsInt() : element.getY());
                  var10001 = elementJson.get("width");
                  element.setWidth(var10001 != null ? var10001.getAsInt() : element.getWidth());
                  var10001 = elementJson.get("height");
                  element.setHeight(var10001 != null ? var10001.getAsInt() : element.getHeight());
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
