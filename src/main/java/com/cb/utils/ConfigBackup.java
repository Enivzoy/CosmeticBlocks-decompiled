package com.cb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.io.ByteStreamsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

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
