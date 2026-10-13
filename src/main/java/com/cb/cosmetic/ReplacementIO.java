package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ReplacementIO {
   @NotNull
   public static final ReplacementIO INSTANCE = new ReplacementIO();

   private ReplacementIO() {
   }

   public final void save() {
      if (SaveScheduler.INSTANCE.getSaveInFlight$cb_client()) {
         SaveScheduler.INSTANCE.setSaveAgain$cb_client(true);
      } else {
         SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
         SaveScheduler.INSTANCE.setLastSaveTime$cb_client(System.currentTimeMillis());
         Map snapshot = ReplacementStore.INSTANCE.snapshotReplacements$cb_client();
         File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue());
         SaveScheduler.INSTANCE.setSaveInFlight$cb_client(true);
         SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveAsync(snapshot, file));
      }
   }

   public final void saveToFileSync$cb_client(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastSaveTime$cb_client(System.currentTimeMillis());

      try {
         this.writeConfig(ReplacementStore.INSTANCE.snapshotReplacements$cb_client(), file);
      } catch (Exception e) {
         System.err.println("[CB] Failed to save config sync: " + e.getMessage());
      }
   }

   private final void writeConfig(Map<String, ? extends Map<String, String>> snapshot, File file) throws IOException {
      if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
         ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
      }

      File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
      var writer = new BufferedWriter(new FileWriter(tmpFile), 65536);
      Throwable writerCause = null;

      try {
         BufferedWriter bw = writer;
         var jsonWriter = new JsonWriter(bw);
         Throwable jsonWriterCause = null;

         try {
            JsonWriter jw = jsonWriter;
            jw.setIndent("  ");
            jw.beginObject();

            for (Entry dimEntry : snapshot.entrySet()) {
               String dim = (String)dimEntry.getKey();
               Map<String, String> dimMap = (Map<String, String>)dimEntry.getValue();
               jw.name(dim);
               jw.beginObject();

               for (Entry posEntry : dimMap.entrySet()) {
                  String pos = (String)posEntry.getKey();
                  String block = (String)posEntry.getValue();
                  jw.name(pos).value(block);
               }

               jw.endObject();
            }

            jw = jw.endObject();
         } catch (Throwable jsonWriterError) {
            jsonWriterCause = jsonWriterError;
            throw jsonWriterError;
         } finally {
            CloseableKt.closeFinally(jsonWriter, jsonWriterCause);
         }
      } catch (Throwable writerError) {
         writerCause = writerError;
         throw writerError;
      } finally {
         CloseableKt.closeFinally(writer, writerCause);
      }

      try {
         Path sourcePath = tmpFile.toPath();
         Path targetPath = file.toPath();
         CopyOption[] copyOptions = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
         Comparable moveResult = Files.move(sourcePath, targetPath, copyOptions);
      } catch (Exception ignored) {
         if (file.exists()) {
            file.delete();
         }

         tmpFile.renameTo(file);
      }
   }

   public final void load() {
      SaveScheduler.INSTANCE.flushSave$cb_client();
      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      ConfigSwitcher.INSTANCE.setLoadedConfigName$cb_client(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue());
      this.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue()));
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
   }

   public final void loadFrom$cb_client(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      ReplacementStore.INSTANCE.getReplacements$cb_client().clear();
      File tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
      boolean loaded = this.tryLoadFile(file) || this.tryLoadFile(tmpFile);
      if (!loaded && (file.exists() || tmpFile.exists())) {
         System.err.println("[CB] Failed to load config from " + file.getName());
      }

      ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
   }

   private final boolean tryLoadFile(File file) {
      if (!file.exists()) {
         return false;
      }

      boolean text;
      try {
         String content = FilesKt.readText(file, kotlin.text.Charsets.UTF_8);
         if (StringsKt.isBlank(content)) {
            return false;
         }

         JsonObject root = JsonParser.parseString(content).getAsJsonObject();

         for (Entry rootEntry : root.entrySet()) {
            Intrinsics.checkNotNull(rootEntry);
            String dim = (String)rootEntry.getKey();
            JsonElement element = (JsonElement)rootEntry.getValue();
            JsonObject dimObj = element.getAsJsonObject();
            Map dimMap = new LinkedHashMap();

            for (Entry posEntry : dimObj.entrySet()) {
               Intrinsics.checkNotNull(posEntry);
               String pos = (String)posEntry.getKey();
               JsonElement block = (JsonElement)posEntry.getValue();
               dimMap.put(pos, block.getAsString());
            }

            ReplacementStore.INSTANCE.getReplacements$cb_client().put(dim, dimMap);
         }

         text = true;
      } catch (Exception e) {
         System.err.println("[CB] Failed to parse " + file.getName() + ": " + e.getMessage());
         text = false;
      }

      return text;
   }

   public final void migrateOldConfig$cb_client() {
      File oldFile = new File("config/cb/cfme.json");
      if (oldFile.exists()) {
         if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
            ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
         }

         File target = ReplacementStore.INSTANCE.getConfigFile$cb_client("default");
         if (!target.exists()) {
            FilesKt.copyTo(oldFile, target, false, 8192);
         }

         oldFile.delete();
      }
   }

   private static final void saveAsync(Map $snapshot, File $file) {
      try {
         INSTANCE.writeConfig($snapshot, $file);
      } catch (Exception e) {
         System.err.println("[CB] Failed to save config: " + e.getMessage());
      } finally {
         SaveScheduler.INSTANCE.setSaveInFlight$cb_client(false);
         if (SaveScheduler.INSTANCE.getSaveAgain$cb_client()) {
            SaveScheduler.INSTANCE.setSaveAgain$cb_client(false);
            SaveScheduler.INSTANCE.setNeedsSave$cb_client(true);
            SaveScheduler.INSTANCE.setLastEditTime$cb_client(0L);
         }
      }
   }
}
