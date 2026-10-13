package com.cb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.io.ByteStreamsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f",
   d2 = {
         "Lcom/cb/utils/ConfigBackup;",
         "",
         "<init>",
         "()V",
         "cbDir",
         "Ljava/io/File;",
         "backupDir",
         "timestampFormat",
         "Ljava/time/format/DateTimeFormatter;",
         "kotlin.jvm.PlatformType",
         "backup",
         "",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nConfigBackup.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigBackup.kt\ncom/cb/utils/ConfigBackup\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"
)
public final class ConfigBackup {
   @NotNull
   public static final ConfigBackup INSTANCE = new ConfigBackup();
   @NotNull
   private static final File cbDir = new File("config/cb/cb");
   @NotNull
   private static final File backupDir = new File("config/cb/cb_backups");
   private static final DateTimeFormatter timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

   private ConfigBackup() {
   }

   @NotNull
   public final String backup() {
      if (!cbDir.exists()) {
         return "No CB configs found";
      }

      File[] files = cbDir.listFiles(ConfigBackup::isJsonFile);
      if (files == null || files.length == 0) {
         return "No CB configs found";
      }

      String message;
      try {
         if (!backupDir.exists()) {
            backupDir.mkdirs();
         }

         String timestamp = LocalDateTime.now().format(timestampFormat);
         File zipFile = new File(backupDir, "cb_backup_" + timestamp + ".zip");

         try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file : files) {
               try {
                  zos.putNextEntry(new ZipEntry(file.getName()));
                  Intrinsics.checkNotNull(file);

                  try (FileInputStream input = new FileInputStream(file)) {
                     ByteStreamsKt.copyTo(input, zos, 8192);
                  }

                  zos.closeEntry();
               } catch (Exception ignored) {
               }
            }
         }

         message = "Backup saved: cb_backup_" + timestamp + ".zip (" + files.length + " configs)";
      } catch (Exception e) {
         message = "Backup failed: " + e.getMessage();
      }

      return message;
   }

   private static final boolean isJsonFile(File f) {
      if (f.isFile()) {
         Intrinsics.checkNotNull(f);
         if (Intrinsics.areEqual(FilesKt.getExtension(f), "json")) {
            return true;
         }
      }

      return false;
   }
}
