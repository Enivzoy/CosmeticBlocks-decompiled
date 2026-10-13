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
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
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
      Path var10000 = FabricLoader.getInstance().getConfigDir().resolve("cb").resolve("cb-ui.json");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      return var10000;
   }

   public final void load() {
      Path file = this.getConfigFile();
      if (Files.exists(file)) {
         try {
            var json = Files.newBufferedReader(file);
            Throwable var4 = null;

            JsonElement var30;
            try {
               BufferedReader it = json;
               int var6/* $VF was: $i$a$-use-Config$load$root$1 */ = 0;
               var30 = JsonParser.parseReader(it);
            } catch (Throwable var26) {
               var4 = var26;
               throw var26;
            } finally {
               CloseableKt.closeFinally(json, var4);
            }

            JsonElement root = var30;
            if (!root.isJsonObject()) {
               return;
            }

            JsonObject jsonx = root.getAsJsonObject();
            var30 = jsonx.get("tabs");
            JsonObject var10000 = var30 instanceof JsonObject ? (JsonObject)var30 : null;
            if ((var30 instanceof JsonObject ? (JsonObject)var30 : null) != null) {
               JsonObject tabsJson = var10000;
               int var7/* $VF was: $i$a$-let-Config$load$1 */ = 0;
               Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
               int $i$f$forEach = 0;

               for (Object element$iv : $this$forEach$iv) {
                  SettingsTab tab = (SettingsTab)element$iv;
                  int var13/* $VF was: $i$a$-forEach-Config$load$1$1 */ = 0;
                  JsonElement name = tabsJson.get(tab.getName());
                  var10000 = name instanceof JsonObject ? (JsonObject)name : null;
                  if ((name instanceof JsonObject ? (JsonObject)name : null) != null) {
                     JsonObject tabJson = var10000;
                     int var16/* $VF was: $i$a$-let-Config$load$1$1$1 */ = 0;
                     Iterable $this$forEach$ivx = tab.getSettings();
                     int $i$f$forEachx = 0;

                     for (Object element$ivx : $this$forEach$ivx) {
                        Setting it = (Setting)element$ivx;
                        int var22/* $VF was: $i$a$-forEach-Config$load$1$1$1$1 */ = 0;
                        it.readJson(tabJson);
                     }
                  }
               }
            }

            var30 = jsonx.get("panels");
            var10000 = var30 instanceof JsonObject ? (JsonObject)var30 : null;
            if ((var30 instanceof JsonObject ? (JsonObject)var30 : null) != null) {
               JsonObject panelsJson = var10000;
               int var35/* $VF was: $i$a$-let-Config$load$2 */ = 0;
               Set var49 = panelsJson.entrySet();
               Intrinsics.checkNotNullExpressionValue(var49, "entrySet(...)");
               Iterable $this$forEach$iv = var49;
               int $i$f$forEach = 0;

               for (Object element$iv : $this$forEach$iv) {
                  Entry var40 = (Entry)element$iv;
                  int var41/* $VF was: $i$a$-forEach-Config$load$2$1 */ = 0;
                  Intrinsics.checkNotNull(var40);
                  String name = (String)var40.getKey();
                  JsonElement element = (JsonElement)var40.getValue();
                  var10000 = element instanceof JsonObject ? (JsonObject)element : null;
                  if ((element instanceof JsonObject ? (JsonObject)element : null) != null) {
                     JsonObject panel = var10000;
                     Map var45 = panelState;
                     JsonElement var10002 = panel.get("x");
                     float var51 = var10002 != null ? var10002.getAsFloat() : 0.0F;
                     JsonElement var10003 = panel.get("y");
                     float var52 = var10003 != null ? var10003.getAsFloat() : 0.0F;
                     JsonElement var10004 = panel.get("collapsed");
                     Config.PanelState var46 = new Config.PanelState(var51, var52, var10004 != null ? var10004.getAsBoolean() : false);
                     var45.put(name, var46);
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
         Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$iv) {
            SettingsTab tab = (SettingsTab)element$iv;
            int var8/* $VF was: $i$a$-forEach-Config$save$1 */ = 0;
            JsonObject tabJson = new JsonObject();
            Iterable $this$forEach$ivx = tab.getSettings();
            int $i$f$forEachx = 0;

            for (Object element$ivx : $this$forEach$ivx) {
               Setting it = (Setting)element$ivx;
               int var15/* $VF was: $i$a$-forEach-Config$save$1$1 */ = 0;
               it.writeJson(tabJson);
            }

            tabsJson.add(tab.getName(), tabJson);
         }

         json.add("tabs", tabsJson);
         JsonObject panelsJson = new JsonObject();
         Map $this$forEach$ivx = panelState;
         int $i$f$forEachx = 0;

         for (Object element$iv : $this$forEach$ivx.entrySet()) {
            Entry var32 = (Entry)element$iv;
            int var35/* $VF was: $i$a$-forEach-Config$save$2 */ = 0;
            String name = (String)var32.getKey();
            Config.PanelState state = (Config.PanelState)var32.getValue();
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
         var var28 = Files.newBufferedWriter(temp);
         Throwable var31 = null;

         try {
            BufferedWriter it = var28;
            int var36/* $VF was: $i$a$-use-Config$save$3 */ = 0;
            gson.toJson(json, it);
            Unit var34 = Unit.INSTANCE;
         } catch (Throwable var19) {
            var31 = var19;
            throw var19;
         } finally {
            CloseableKt.closeFinally(var28, var31);
         }

         CopyOption[] var29 = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE};
         Files.move(temp, file, var29);
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

      public final void setX(float var1) {
         this.x = var1/* $VF was: <set-?> */;
      }

      public final float getY() {
         return this.y;
      }

      public final void setY(float var1) {
         this.y = var1/* $VF was: <set-?> */;
      }

      public final boolean getCollapsed() {
         return this.collapsed;
      }

      public final void setCollapsed(boolean var1) {
         this.collapsed = var1/* $VF was: <set-?> */;
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
         } else if (!(other instanceof Config.PanelState var2)) {
            return false;
         } else if (Float.compare(this.x, var2.x) != 0) {
            return false;
         } else {
            return Float.compare(this.y, var2.y) != 0 ? false : this.collapsed == var2.collapsed;
         }
      }
   }
}
