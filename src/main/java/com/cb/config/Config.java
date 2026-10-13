/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.io.CloseableKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.fabricmc.loader.api.FabricLoader
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.cb.config;

import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.setting.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.Reader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0017B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0014\u001a\u00020\u0015J\u0006\u0010\u0016\u001a\u00020\u0015R\u0016\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \u0006*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R-\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f`\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0018"}, d2={"Lcom/cb/config/Config;", "", "<init>", "()V", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "panelState", "Ljava/util/HashMap;", "", "Lcom/cb/config/Config$PanelState;", "Lkotlin/collections/HashMap;", "getPanelState", "()Ljava/util/HashMap;", "configFile", "Ljava/nio/file/Path;", "getConfigFile", "()Ljava/nio/file/Path;", "load", "", "save", "PanelState", "cb_client"})
@SourceDebugExtension(value={"SMAP\nConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Config.kt\ncom/cb/config/Config\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,100:1\n1#2:101\n1915#3:102\n1915#3,2:103\n1916#3:105\n1915#3,2:106\n1915#3:108\n1915#3,2:109\n1916#3:111\n221#4,2:112\n*S KotlinDebug\n*F\n+ 1 Config.kt\ncom/cb/config/Config\n*L\n44#1:102\n46#1:103,2\n44#1:105\n52#1:106,2\n73#1:108\n75#1:109,2\n73#1:111\n81#1:112,2\n*E\n"})
public final class Config {
    @NotNull
    public static final Config INSTANCE = new Config();
    private static final Logger logger = LoggerFactory.getLogger((String)"cb/config");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @NotNull
    private static final HashMap<String, PanelState> panelState = new HashMap();

    private Config() {
    }

    @NotNull
    public final HashMap<String, PanelState> getPanelState() {
        return panelState;
    }

    private final Path getConfigFile() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("cb").resolve("cb-ui.json");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        return path;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void load() {
        Path file = this.getConfigFile();
        if (!Files.exists(file, new LinkOption[0])) {
            return;
        }
        try {
            boolean $i$f$forEach;
            Iterable $this$forEach$iv;
            JsonElement jsonElement;
            Closeable closeable = Files.newBufferedReader(file);
            Throwable throwable = null;
            try {
                BufferedReader it = (BufferedReader)closeable;
                boolean bl = false;
                jsonElement = JsonParser.parseReader((Reader)it);
            }
            catch (Throwable bl) {
                throwable = bl;
                throw bl;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
            }
            JsonElement root = jsonElement;
            if (!root.isJsonObject()) {
                return;
            }
            JsonObject json = root.getAsJsonObject();
            jsonElement = json.get("tabs");
            JsonObject jsonObject = jsonElement instanceof JsonObject ? (JsonObject)jsonElement : null;
            if (jsonObject != null) {
                JsonObject tabsJson = jsonObject;
                boolean bl = false;
                $this$forEach$iv = Tabs.INSTANCE.getAll();
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    JsonObject tabJson;
                    SettingsTab tab = (SettingsTab)element$iv;
                    boolean bl2 = false;
                    JsonElement jsonElement2 = tabsJson.get(tab.getName());
                    if ((jsonElement2 instanceof JsonObject ? (JsonObject)jsonElement2 : null) == null) continue;
                    tabJson = tabJson;
                    boolean bl3 = false;
                    Iterable $this$forEach$iv2 = tab.getSettings();
                    boolean $i$f$forEach2 = false;
                    for (Object element$iv2 : $this$forEach$iv2) {
                        Setting it = (Setting)element$iv2;
                        boolean bl4 = false;
                        it.readJson(tabJson);
                    }
                }
            }
            JsonObject jsonObject2 = (jsonElement = json.get("panels")) instanceof JsonObject ? (JsonObject)jsonElement : null;
            if (jsonObject2 != null) {
                JsonObject panelsJson = jsonObject2;
                boolean bl = false;
                Set set = panelsJson.entrySet();
                Intrinsics.checkNotNullExpressionValue((Object)set, (String)"entrySet(...)");
                $this$forEach$iv = set;
                $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    JsonObject panel;
                    Map.Entry entry = (Map.Entry)element$iv;
                    boolean bl5 = false;
                    Intrinsics.checkNotNull((Object)entry);
                    String name = (String)entry.getKey();
                    JsonElement element = (JsonElement)entry.getValue();
                    if ((element instanceof JsonObject ? (JsonObject)element : null) == null) continue;
                    panel = panel;
                    Map map = panelState;
                    JsonElement jsonElement3 = panel.get("x");
                    JsonElement jsonElement4 = panel.get("y");
                    JsonElement jsonElement5 = panel.get("collapsed");
                    PanelState panelState = new PanelState(jsonElement3 != null ? jsonElement3.getAsFloat() : 0.0f, jsonElement4 != null ? jsonElement4.getAsFloat() : 0.0f, jsonElement5 != null ? jsonElement5.getAsBoolean() : false);
                    map.put(name, panelState);
                }
            }
            logger.info("Loaded config from {}", (Object)file);
        }
        catch (Throwable t) {
            logger.error("Failed to load config \u2014 keeping defaults", t);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void save() {
        try {
            JsonObject json = new JsonObject();
            JsonObject tabsJson = new JsonObject();
            Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SettingsTab tab = (SettingsTab)element$iv;
                boolean bl = false;
                JsonObject tabJson = new JsonObject();
                Iterable $this$forEach$iv2 = tab.getSettings();
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Setting it = (Setting)element$iv2;
                    boolean bl2 = false;
                    it.writeJson(tabJson);
                }
                tabsJson.add(tab.getName(), (JsonElement)tabJson);
            }
            json.add("tabs", (JsonElement)tabsJson);
            JsonObject panelsJson = new JsonObject();
            Map $this$forEach$iv3 = panelState;
            boolean $i$f$forEach3 = false;
            CopyOption[] copyOptionArray = $this$forEach$iv3.entrySet().iterator();
            while (copyOptionArray.hasNext()) {
                Map.Entry element$iv;
                Map.Entry bl = element$iv = copyOptionArray.next();
                boolean bl3 = false;
                String name = (String)bl.getKey();
                PanelState state = (PanelState)bl.getValue();
                JsonObject panel = new JsonObject();
                panel.addProperty("x", (Number)Float.valueOf(state.getX()));
                panel.addProperty("y", (Number)Float.valueOf(state.getY()));
                panel.addProperty("collapsed", Boolean.valueOf(state.getCollapsed()));
                panelsJson.add(name, (JsonElement)panel);
            }
            json.add("panels", (JsonElement)panelsJson);
            Path file = this.getConfigFile();
            Files.createDirectories(file.getParent(), new FileAttribute[0]);
            Path temp = file.resolveSibling("cb-ui.json.tmp");
            copyOptionArray = Files.newBufferedWriter(temp, new OpenOption[0]);
            Throwable throwable = null;
            try {
                BufferedWriter it = (BufferedWriter)copyOptionArray;
                boolean bl = false;
                gson.toJson((JsonElement)json, (Appendable)it);
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally((Closeable)copyOptionArray, (Throwable)throwable);
            }
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE};
            Files.move(temp, file, copyOptionArray);
        }
        catch (Throwable t) {
            logger.error("Failed to save config", t);
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0007\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0006H\u00c6\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0014\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0019\u001a\u00020\u001aH\u00d6\u0081\u0004J\n\u0010\u001b\u001a\u00020\u001cH\u00d6\u0081\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\n\"\u0004\b\u000e\u0010\fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001d"}, d2={"Lcom/cb/config/Config$PanelState;", "", "x", "", "y", "collapsed", "", "<init>", "(FFZ)V", "getX", "()F", "setX", "(F)V", "getY", "setY", "getCollapsed", "()Z", "setCollapsed", "(Z)V", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "cb_client"})
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

        public final void setX(float f) {
            this.x = f;
        }

        public final float getY() {
            return this.y;
        }

        public final void setY(float f) {
            this.y = f;
        }

        public final boolean getCollapsed() {
            return this.collapsed;
        }

        public final void setCollapsed(boolean bl) {
            this.collapsed = bl;
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
        public final PanelState copy(float x, float y, boolean collapsed) {
            return new PanelState(x, y, collapsed);
        }

        public static /* synthetic */ PanelState copy$default(PanelState panelState, float f, float f2, boolean bl, int n, Object object) {
            if ((n & 1) != 0) {
                f = panelState.x;
            }
            if ((n & 2) != 0) {
                f2 = panelState.y;
            }
            if ((n & 4) != 0) {
                bl = panelState.collapsed;
            }
            return panelState.copy(f, f2, bl);
        }

        @NotNull
        public String toString() {
            return "PanelState(x=" + this.x + ", y=" + this.y + ", collapsed=" + this.collapsed + ")";
        }

        public int hashCode() {
            int result = Float.hashCode(this.x);
            result = result * 31 + Float.hashCode(this.y);
            result = result * 31 + Boolean.hashCode(this.collapsed);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PanelState)) {
                return false;
            }
            PanelState panelState = (PanelState)other;
            if (Float.compare(this.x, panelState.x) != 0) {
                return false;
            }
            if (Float.compare(this.y, panelState.y) != 0) {
                return false;
            }
            return this.collapsed == panelState.collapsed;
        }
    }
}


