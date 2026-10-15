package com.cb.cosmetic;

import com.cb.config.tabs.DungeonConfigTab;
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
import org.jetbrains.annotations.Nullable;

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
         String floor = ReplacementStore.INSTANCE.getFloor$cb_client();
         File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue());
         WaterConfig.Fields water = WaterConfig.INSTANCE.captureFields();
         SaveScheduler.INSTANCE.setSaveInFlight$cb_client(true);
         SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> saveAsync(snapshot, floor, water, file));
      }
   }

   public final void saveToFileSync$cb_client(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
      SaveScheduler.INSTANCE.setLastSaveTime$cb_client(System.currentTimeMillis());

      try {
         this.writeConfig(ReplacementStore.INSTANCE.snapshotReplacements$cb_client(), ReplacementStore.INSTANCE.getFloor$cb_client(), WaterConfig.INSTANCE.captureFields(), file);
      } catch (Exception e) {
         System.err.println("[CB] Failed to save config sync: " + e.getMessage());
      }
   }

   private final void writeConfig(Map<String, ? extends Map<String, String>> snapshot, @Nullable String floor, @NotNull WaterConfig.Fields water, File file) throws IOException {
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

            if (floor != null && !floor.isEmpty()) {
               jw.name("floor").value(floor);
            }

            if (water.getAllow() != null) {
               jw.name("allowWaterColor").value(water.getAllow().booleanValue());
            }

            if (water.getRgb() != null) {
               jw.name("rgbWaterColor").value(water.getRgb().intValue());
            }

            if (water.getLava() != null) {
               jw.name("lavaAsWater").value(water.getLava().booleanValue());
            }

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
      ConfigSwitcher.INSTANCE.setLoadedConfigName$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue());
      this.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue()));
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
   }

   public final void loadFrom$cb_client(@NotNull File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      ReplacementStore.INSTANCE.getReplacements$cb_client().clear();
      ReplacementStore.INSTANCE.setFloor$cb_client(null);
      WaterConfig.INSTANCE.onConfigLoaded$cb_client(null, null, null);
      ConfigRemap.INSTANCE.load(FilesKt.getNameWithoutExtension(file));
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
         String loadedFloor = null;
         Boolean loadedAllowWater = null;
         Integer loadedRgbWater = null;
         Boolean loadedLavaAsWater = null;

         for (Entry rootEntry : root.entrySet()) {
            Intrinsics.checkNotNull(rootEntry);
            String dim = (String)rootEntry.getKey();
            JsonElement element = (JsonElement)rootEntry.getValue();
            if (element.isJsonPrimitive()) {
               if (Intrinsics.areEqual(dim, "floor") && element.getAsJsonPrimitive().isString()) {
                  loadedFloor = element.getAsString();
               } else if (Intrinsics.areEqual(dim, "allowWaterColor") && element.getAsJsonPrimitive().isBoolean()) {
                  loadedAllowWater = element.getAsBoolean();
               } else if (Intrinsics.areEqual(dim, "rgbWaterColor") && element.getAsJsonPrimitive().isNumber()) {
                  loadedRgbWater = element.getAsInt();
               } else if (Intrinsics.areEqual(dim, "lavaAsWater") && element.getAsJsonPrimitive().isBoolean()) {
                  loadedLavaAsWater = element.getAsBoolean();
               }

               continue;
            }

            if (!element.isJsonObject()) {
               continue;
            }

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

         ReplacementStore.INSTANCE.setFloor$cb_client(loadedFloor);
         WaterConfig.INSTANCE.onConfigLoaded$cb_client(loadedAllowWater, loadedRgbWater, loadedLavaAsWater);
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

   private static final void saveAsync(Map $snapshot, String $floor, WaterConfig.Fields $water, File $file) {
      try {
         INSTANCE.writeConfig($snapshot, $floor, $water, $file);
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
