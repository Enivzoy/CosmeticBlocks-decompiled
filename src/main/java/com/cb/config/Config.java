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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f`\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0018",
   d2 = {
         "Lcom/cb/config/Config;",
         "",
         "<init>",
         "()V",
         "logger",
         "Lorg/slf4j/Logger;",
         "kotlin.jvm.PlatformType",
         "gson",
         "Lcom/google/gson/Gson;",
         "panelState",
         "Ljava/util/HashMap;",
         "",
         "Lcom/cb/config/Config$PanelState;",
         "Lkotlin/collections/HashMap;",
         "getPanelState",
         "()Ljava/util/HashMap;",
         "configFile",
         "Ljava/nio/file/Path;",
         "getConfigFile",
         "()Ljava/nio/file/Path;",
         "load",
         "",
         "save",
         "PanelState",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Config.kt\ncom/cb/config/Config\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,100:1\n1#2:101\n1915#3:102\n1915#3,2:103\n1916#3:105\n1915#3,2:106\n1915#3:108\n1915#3,2:109\n1916#3:111\n221#4,2:112\n*S KotlinDebug\n*F\n+ 1 Config.kt\ncom/cb/config/Config\n*L\n44#1:102\n46#1:103,2\n44#1:105\n52#1:106,2\n73#1:108\n75#1:109,2\n73#1:111\n81#1:112,2\n*E\n"
)
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0014\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0019\u001a\u00020\u001aHÖ\u0081\u0004J\n\u0010\u001b\u001a\u00020\u001cHÖ\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001d",
      d2 = {
            "Lcom/cb/config/Config$PanelState;",
            "",
            "x",
            "",
            "y",
            "collapsed",
            "",
            "<init>",
            "(FFZ)V",
            "getX",
            "()F",
            "setX",
            "(F)V",
            "getY",
            "setY",
            "getCollapsed",
            "()Z",
            "setCollapsed",
            "(Z)V",
            "component1",
            "component2",
            "component3",
            "copy",
            "equals",
            "other",
            "hashCode",
            "",
            "toString",
            "",
            "cb_client"
      }
   )
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
