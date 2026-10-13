/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.google.gson.stream.JsonWriter
 *  kotlin.Metadata
 *  kotlin.io.CloseableKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.ConfigSwitcher;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.SaveScheduler;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0000\u00a2\u0006\u0002\b\tJ0\u0010\n\u001a\u00020\u00052\u001e\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f0\f2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0006\u0010\u000e\u001a\u00020\u0005J\u0015\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0000\u00a2\u0006\u0002\b\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0002J\r\u0010\u0013\u001a\u00020\u0005H\u0000\u00a2\u0006\u0002\b\u0014\u00a8\u0006\u0015"}, d2={"Lcom/cb/cosmetic/ReplacementIO;", "", "<init>", "()V", "save", "", "saveToFileSync", "file", "Ljava/io/File;", "saveToFileSync$cb_client", "writeConfig", "snapshot", "", "", "load", "loadFrom", "loadFrom$cb_client", "tryLoadFile", "", "migrateOldConfig", "migrateOldConfig$cb_client", "cb_client"})
public final class ReplacementIO {
    @NotNull
    public static final ReplacementIO INSTANCE = new ReplacementIO();

    private ReplacementIO() {
    }

    public final void save() {
        if (SaveScheduler.INSTANCE.getSaveInFlight$cb_client()) {
            SaveScheduler.INSTANCE.setSaveAgain$cb_client(true);
            return;
        }
        SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
        SaveScheduler.INSTANCE.setLastSaveTime$cb_client(System.currentTimeMillis());
        Map<String, Map<String, String>> snapshot = ReplacementStore.INSTANCE.snapshotReplacements$cb_client();
        File file = ReplacementStore.getConfigFile$cb_client$default(ReplacementStore.INSTANCE, null, 1, null);
        SaveScheduler.INSTANCE.setSaveInFlight$cb_client(true);
        SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> ReplacementIO.save$lambda$0(snapshot, file));
    }

    public final void saveToFileSync$cb_client(@NotNull File file) {
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
        SaveScheduler.INSTANCE.setLastSaveTime$cb_client(System.currentTimeMillis());
        try {
            this.writeConfig(ReplacementStore.INSTANCE.snapshotReplacements$cb_client(), file);
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to save config sync: " + e.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void writeConfig(Map<String, ? extends Map<String, String>> snapshot, File file) {
        if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
            ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
        }
        File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
        CopyOption[] copyOptionArray = (CopyOption[])new BufferedWriter(new FileWriter(tmpFile), 65536);
        Throwable throwable = null;
        try {
            JsonWriter jsonWriter;
            BufferedWriter bw = (BufferedWriter)copyOptionArray;
            boolean bl = false;
            Closeable closeable = (Closeable)new JsonWriter((Writer)bw);
            Throwable throwable2 = null;
            try {
                JsonWriter jw = (JsonWriter)closeable;
                boolean bl2 = false;
                jw.setIndent("  ");
                jw.beginObject();
                for (Map.Entry<String, ? extends Map<String, String>> entry : snapshot.entrySet()) {
                    String dim = entry.getKey();
                    Map<String, String> dimMap = entry.getValue();
                    jw.name(dim);
                    jw.beginObject();
                    for (Map.Entry<String, String> entry2 : dimMap.entrySet()) {
                        String pos = entry2.getKey();
                        String block = entry2.getValue();
                        jw.name(pos).value(block);
                    }
                    jw.endObject();
                }
                jsonWriter = jw.endObject();
            }
            catch (Throwable throwable3) {
                throwable2 = throwable3;
                throw throwable3;
            }
            finally {
                CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable2);
            }
            JsonWriter jsonWriter2 = jsonWriter;
        }
        catch (Throwable throwable4) {
            throwable = throwable4;
            throw throwable4;
        }
        finally {
            CloseableKt.closeFinally((Closeable)copyOptionArray, (Throwable)throwable);
        }
        try {
            copyOptionArray = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
            Comparable cfr_ignored_0 = Files.move(tmpFile.toPath(), file.toPath(), copyOptionArray);
        }
        catch (Exception exception) {
            if (file.exists()) {
                file.delete();
            }
            tmpFile.renameTo(file);
        }
    }

    public final void load() {
        SaveScheduler.INSTANCE.flushSave$cb_client();
        SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
        ConfigSwitcher.INSTANCE.setLoadedConfigName$cb_client((String)CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue());
        this.loadFrom$cb_client(ReplacementStore.getConfigFile$cb_client$default(ReplacementStore.INSTANCE, null, 1, null));
        DirtySections.INSTANCE.markAllCachedDirty$cb_client();
    }

    public final void loadFrom$cb_client(@NotNull File file) {
        boolean loaded;
        Intrinsics.checkNotNullParameter((Object)file, (String)"file");
        ReplacementStore.INSTANCE.getReplacements$cb_client().clear();
        File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
        boolean bl = loaded = this.tryLoadFile(file) || this.tryLoadFile(tmpFile);
        if (!loaded && (file.exists() || tmpFile.exists())) {
            System.err.println("[CB] Failed to load config from " + file.getName());
        }
        ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
    }

    private final boolean tryLoadFile(File file) {
        boolean bl;
        if (!file.exists()) {
            return false;
        }
        try {
            String text = FilesKt.readText$default((File)file, null, (int)1, null);
            if (StringsKt.isBlank((CharSequence)text)) {
                return false;
            }
            JsonObject root = JsonParser.parseString((String)text).getAsJsonObject();
            for (Map.Entry entry : root.entrySet()) {
                Intrinsics.checkNotNull((Object)entry);
                String dim = (String)entry.getKey();
                JsonElement element = (JsonElement)entry.getValue();
                JsonObject dimObj = element.getAsJsonObject();
                Map dimMap = new LinkedHashMap();
                for (Map.Entry entry2 : dimObj.entrySet()) {
                    Intrinsics.checkNotNull((Object)entry2);
                    String pos = (String)entry2.getKey();
                    JsonElement block = (JsonElement)entry2.getValue();
                    dimMap.put(pos, block.getAsString());
                }
                ReplacementStore.INSTANCE.getReplacements$cb_client().put(dim, dimMap);
            }
            bl = true;
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to parse " + file.getName() + ": " + e.getMessage());
            bl = false;
        }
        return bl;
    }

    public final void migrateOldConfig$cb_client() {
        File oldFile = new File("config/cb/cfme.json");
        if (oldFile.exists()) {
            File target;
            if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
                ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
            }
            if (!(target = ReplacementStore.INSTANCE.getConfigFile$cb_client("default")).exists()) {
                FilesKt.copyTo$default((File)oldFile, (File)target, (boolean)false, (int)0, (int)6, null);
            }
            oldFile.delete();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static final void save$lambda$0(Map $snapshot, File $file) {
        try {
            INSTANCE.writeConfig($snapshot, $file);
        }
        catch (Exception e) {
            System.err.println("[CB] Failed to save config: " + e.getMessage());
        }
        finally {
            SaveScheduler.INSTANCE.setSaveInFlight$cb_client(false);
            if (SaveScheduler.INSTANCE.getSaveAgain$cb_client()) {
                SaveScheduler.INSTANCE.setSaveAgain$cb_client(false);
                SaveScheduler.INSTANCE.setNeedsSave$cb_client(true);
                SaveScheduler.INSTANCE.setLastEditTime$cb_client(0L);
            }
        }
    }
}


