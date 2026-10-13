package com.cb.config;

import com.cb.setting.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Config {
   @NotNull
   public static final Config INSTANCE = new Config();
   private static final Logger logger = LoggerFactory.getLogger("cb/config");
   private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
   @NotNull
   private static final HashMap<String, Config.PanelState> panelState = new HashMap<>();

   private Config() {
   }

   @NotNull
   public final HashMap<String, Config.PanelState> getPanelState() {
      return panelState;
   }

   private final Path getConfigFile() {
      Path path = FabricLoader.getInstance().getConfigDir().resolve("cb").resolve("cb-ui.json");
      Intrinsics.checkNotNullExpressionValue(path, "resolve(...)");
      return path;
   }

   public final void load() {
      Path file = this.getConfigFile();
      if (Files.exists(file)) {
         try {
            JsonElement root;
            try (BufferedReader reader = Files.newBufferedReader(file)) {
               root = JsonParser.parseReader(reader);
            }

            if (!root.isJsonObject()) {
               return;
            }

            JsonObject json = root.getAsJsonObject();
            JsonElement tabsElement = json.get("tabs");
            if (tabsElement instanceof JsonObject tabsJson) {
               for (SettingsTab tab : Tabs.INSTANCE.getAll()) {
                  JsonElement tabElement = tabsJson.get(tab.getName());
                  if (tabElement instanceof JsonObject tabJson) {
                     for (Setting setting : tab.getSettings()) {
                        setting.readJson(tabJson);
                     }
                  }
               }
            }

            JsonElement panelsElement = json.get("panels");
            if (panelsElement instanceof JsonObject panelsJson) {
               Set<Entry<String, JsonElement>> entries = panelsJson.entrySet();
               Intrinsics.checkNotNullExpressionValue(entries, "entrySet(...)");

               for (Entry<String, JsonElement> entry : entries) {
                  String name = entry.getKey();
                  JsonElement element = entry.getValue();
                  if (element instanceof JsonObject panel) {
                     JsonElement xElement = panel.get("x");
                     float x = xElement != null ? xElement.getAsFloat() : 0.0F;
                     JsonElement yElement = panel.get("y");
                     float y = yElement != null ? yElement.getAsFloat() : 0.0F;
                     JsonElement collapsedElement = panel.get("collapsed");
                     panelState.put(name, new Config.PanelState(x, y, collapsedElement != null ? collapsedElement.getAsBoolean() : false));
                  }
               }
            }

            logger.info("Loaded config from {}", file);
         } catch (Throwable t) {
            logger.error("Failed to load config — keeping defaults", t);
         }
      }
   }

   public final void save() {
      try {
         JsonObject json = new JsonObject();
         JsonObject tabsJson = new JsonObject();

         for (SettingsTab tab : Tabs.INSTANCE.getAll()) {
            JsonObject tabJson = new JsonObject();

            for (Setting setting : tab.getSettings()) {
               setting.writeJson(tabJson);
            }

            tabsJson.add(tab.getName(), tabJson);
         }

         json.add("tabs", tabsJson);
         JsonObject panelsJson = new JsonObject();

         for (Entry<String, Config.PanelState> entry : panelState.entrySet()) {
            String name = entry.getKey();
            Config.PanelState state = entry.getValue();
            JsonObject panel = new JsonObject();
            panel.addProperty("x", state.getX());
            panel.addProperty("y", state.getY());
            panel.addProperty("collapsed", state.getCollapsed());
            panelsJson.add(name, panel);
         }

         json.add("panels", panelsJson);
         Path file = this.getConfigFile();
         Files.createDirectories(file.getParent());
         Path temp = file.resolveSibling("cb-ui.json.tmp");

         try (BufferedWriter writer = Files.newBufferedWriter(temp)) {
            gson.toJson(json, writer);
         }

         CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE};
         Files.move(temp, file, options);
      } catch (Throwable t) {
         logger.error("Failed to save config", t);
      }
   }

   public static final class PanelState {
      private float x;
      private float y;
      private boolean collapsed;

      public PanelState(float x, float y, boolean collapsed) {
         this.x = x;
         this.y = y;
         this.collapsed = collapsed;
      }

      public final float getX() {
         return this.x;
      }

      public final void setX(float x) {
         this.x = x;
      }

      public final float getY() {
         return this.y;
      }

      public final void setY(float y) {
         this.y = y;
      }

      public final boolean getCollapsed() {
         return this.collapsed;
      }

      public final void setCollapsed(boolean collapsed) {
         this.collapsed = collapsed;
      }

      public final float component1() {
         return this.x;
      }

      public final float component2() {
         return this.y;
      }

      public final boolean component3() {
         return this.collapsed;
      }

      @NotNull
      public final Config.PanelState copy(float x, float y, boolean collapsed) {
         return new Config.PanelState(x, y, collapsed);
      }

      @NotNull
      @Override
      public String toString() {
         return "PanelState(x=" + this.x + ", y=" + this.y + ", collapsed=" + this.collapsed + ")";
      }

      @Override
      public int hashCode() {
         int result = Float.hashCode(this.x);
         result = result * 31 + Float.hashCode(this.y);
         return result * 31 + Boolean.hashCode(this.collapsed);
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
          } else if (!(other instanceof Config.PanelState state)) {
             return false;
          } else if (Float.compare(this.x, state.x) != 0) {
             return false;
          } else {
             return Float.compare(this.y, state.y) != 0 ? false : this.collapsed == state.collapsed;
          }
      }
   }
}
