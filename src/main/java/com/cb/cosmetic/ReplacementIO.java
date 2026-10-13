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
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0004\u001a\u00020\u0005J\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tJ0\u0010\n\u001a\u00020\u00052\u001e\u0010\u000b\u001a\u001a\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\f0\f2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0006\u0010\u000e\u001a\u00020\u0005J\u0015\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0002J\r\u0010\u0013\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0014¨\u0006\u0015",
   d2 = {
         "Lcom/cb/cosmetic/ReplacementIO;",
         "",
         "<init>",
         "()V",
         "save",
         "",
         "saveToFileSync",
         "file",
         "Ljava/io/File;",
         "saveToFileSync$cb_client",
         "writeConfig",
         "snapshot",
         "",
         "",
         "load",
         "loadFrom",
         "loadFrom$cb_client",
         "tryLoadFile",
         "",
         "migrateOldConfig",
         "migrateOldConfig$cb_client",
         "cb_client"
   }
)
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
         File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(null);
         SaveScheduler.INSTANCE.setSaveInFlight$cb_client(true);
         SaveScheduler.INSTANCE.getSaveExecutor$cb_client().execute(() -> save$lambda$0(snapshot, file));
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
      var var4 = new BufferedWriter(new FileWriter(tmpFile), 65536);
      Throwable var5 = null;

      try {
         BufferedWriter bw = var4;
         int var7/* $VF was: $i$a$-use-ReplacementIO$writeConfig$1 */ = 0;
         var var8 = new JsonWriter(bw);
         Throwable var9 = null;

         try {
            JsonWriter jw = var8;
            int var11/* $VF was: $i$a$-use-ReplacementIO$writeConfig$1$1 */ = 0;
            jw.setIndent("  ");
            jw.beginObject();

            for (Entry var13 : snapshot.entrySet()) {
               String dim = (String)var13.getKey();
               Map<String, String> dimMap = (Map<String, String>)var13.getValue();
               jw.name(dim);
               jw.beginObject();

               for (Entry var17 : dimMap.entrySet()) {
                  String pos = (String)var17.getKey();
                  String block = (String)var17.getValue();
                  jw.name(pos).value(block);
               }

               jw.endObject();
            }

            jw = jw.endObject();
         } catch (Throwable var31) {
            var9 = var31;
            throw var31;
         } finally {
            CloseableKt.closeFinally(var8, var9);
         }
      } catch (Throwable var33) {
         var5 = var33;
         throw var33;
      } finally {
         CloseableKt.closeFinally(var4, var5);
      }

      try {
         Path var10000 = tmpFile.toPath();
         Path var10001 = file.toPath();
         CopyOption[] var35 = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
         Comparable var37 = Files.move(var10000, var10001, var35);
      } catch (Exception var30/* $VF was: <unused var> */) {
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
      this.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(null));
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
         String textx = FilesKt.readText(file, kotlin.text.Charsets.UTF_8);
         if (StringsKt.isBlank(textx)) {
            return false;
         }

         JsonObject root = JsonParser.parseString(textx).getAsJsonObject();

         for (Entry var5 : root.entrySet()) {
            Intrinsics.checkNotNull(var5);
            String dim = (String)var5.getKey();
            JsonElement element = (JsonElement)var5.getValue();
            JsonObject dimObj = element.getAsJsonObject();
            Map dimMap = new LinkedHashMap();

            for (Entry var11 : dimObj.entrySet()) {
               Intrinsics.checkNotNull(var11);
               String pos = (String)var11.getKey();
               JsonElement block = (JsonElement)var11.getValue();
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

   private static final void save$lambda$0(Map $snapshot, File $file) {
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
