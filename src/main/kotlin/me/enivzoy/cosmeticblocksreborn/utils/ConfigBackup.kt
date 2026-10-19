package me.enivzoy.cosmeticblocksreborn.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.io.copyTo
import kotlin.io.extension
import kotlin.io.inputStream
import kotlin.io.use

object ConfigBackup {
   private val cbDir = File("config/cb/cb")
   private val backupDir = File("config/cb/cb_backups")
   private val timestampFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")

   fun backup(): String {
      if (!cbDir.exists()) {
         return "No CB configs found"
      }

      val files = cbDir.listFiles { f -> isJsonFile(f) } ?: return "No CB configs found"
      if (files.isEmpty()) {
         return "No CB configs found"
      }

      var message: String
      try {
         if (!backupDir.exists()) {
            backupDir.mkdirs()
         }

         val timestamp = LocalDateTime.now().format(timestampFormat)
         val zipFile = File(backupDir, "cb_backup_$timestamp.zip")

         ZipOutputStream(FileOutputStream(zipFile)).use { zos ->
            for (file in files) {
               try {
                  zos.putNextEntry(ZipEntry(file.name))
                  FileInputStream(file).use { input -> input.copyTo(zos, 8192) }
                  zos.closeEntry()
               } catch (ignored: Exception) {
               }
            }
         }

         message = "Backup saved: cb_backup_$timestamp.zip (${files.size} configs)"
      } catch (e: Exception) {
         message = "Backup failed: ${e.message}"
      }

      return message
   }

   private fun isJsonFile(f: File): Boolean = f.isFile && f.extension == "json"
}
