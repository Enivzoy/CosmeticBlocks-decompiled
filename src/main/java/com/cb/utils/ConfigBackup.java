package com.cb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
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

      File[] var10000 = cbDir.listFiles(ConfigBackup::backup$lambda$0);
      if (var10000 == null) {
         return "No CB configs found";
      }

      File[] files = var10000;
      if (files.length == 0) {
         return "No CB configs found";
      }

      String timestamp;
      try {
         if (!backupDir.exists()) {
            backupDir.mkdirs();
         }

         timestamp = LocalDateTime.now().format(timestampFormat);
         File zipFile = new File(backupDir, "cb_backup_" + timestamp + ".zip");
         var var4 = new ZipOutputStream(new FileOutputStream(zipFile));
         Throwable var5 = null;

         try {
            ZipOutputStream zos = var4;
            int var7/* $VF was: $i$a$-use-ConfigBackup$backup$1 */ = 0;
            int var8 = 0;

            for (int var9 = files.length; var8 < var9; var8++) {
               File file = files[var8];

               try {
                  zos.putNextEntry(new ZipEntry(file.getName()));
                  Intrinsics.checkNotNull(file);
                  var var11 = new FileInputStream(file);
                  Throwable var12 = null;

                  try {
                     FileInputStream it = var11;
                     int var15/* $VF was: $i$a$-use-ConfigBackup$backup$1$1 */ = 0;
                     long var36 = ByteStreamsKt.copyTo(it, zos, 8192);
                  } catch (Throwable var28) {
                     var12 = var28;
                     throw var28;
                  } finally {
                     CloseableKt.closeFinally(var11, var12);
                  }

                  zos.closeEntry();
               } catch (Exception var30) {
               }
            }

            Unit var35 = Unit.INSTANCE;
         } catch (Throwable var31) {
            var5 = var31;
            throw var31;
         } finally {
            CloseableKt.closeFinally(var4, var5);
         }

         timestamp = "Backup saved: cb_backup_" + timestamp + ".zip (" + files.length + " configs)";
      } catch (Exception e) {
         timestamp = "Backup failed: " + e.getMessage();
      }

      return timestamp;
   }

   private static final boolean backup$lambda$0(File f) {
      if (f.isFile()) {
         Intrinsics.checkNotNull(f);
         if (Intrinsics.areEqual(FilesKt.getExtension(f), "json")) {
            return true;
         }
      }

      return false;
   }
}
