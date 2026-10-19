package me.enivzoy.cosmeticblocksreborn.gui

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.BufferedWriter
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.Tabs
import net.fabricmc.loader.api.FabricLoader

object ProfileManager {
   private val profilesDir: Path = FabricLoader.getInstance().configDir.resolve("cb/profiles")

   init {
      if (!Files.exists(profilesDir)) {
         try {
            Files.createDirectories(profilesDir)
         } catch (e: IOException) {
            throw UncheckedIOException(e)
         }
      }
   }

   fun listProfiles(): List<ProfileInfo> {
      if (!Files.exists(profilesDir)) {
         return emptyList()
      }

      val entries = try {
         profilesDir.listDirectoryEntries("*.profile")
      } catch (e: IOException) {
         throw UncheckedIOException(e)
      }

      return entries.map { path ->
         val settingCount = try {
            Files.readAllLines(path).count { it.contains(":") }
         } catch (_: Exception) {
            0
         }

         val profileName = path.nameWithoutExtension
         val lastModified = try {
            Files.getLastModifiedTime(path).toMillis()
         } catch (e: IOException) {
            throw UncheckedIOException(e)
         }

         ProfileInfo(profileName, path, lastModified, settingCount)
      }.sortedByDescending { it.lastModified }
   }

   fun saveProfile(name: String): Boolean = try {
      val sanitizedName = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val profileFile = profilesDir.resolve("$sanitizedName.profile")
      if (!Files.exists(profilesDir)) {
         Files.createDirectories(profilesDir)
      }

      Files.newBufferedWriter(profileFile).use { writer: BufferedWriter ->
         writer.write("# CB Profile: $name\n")
         writer.write("# Created: ${Instant.now()}\n")
         writer.write("\n")
         for (tab in Tabs.all) {
            writer.write("## ${tab.name}\n")
            for (setting in tab.settings) {
               val obj = JsonObject()
               setting.writeJson(obj)
               val element = obj.get(setting.name)
               if (element != null) {
                  writer.write("${tab.name}.${setting.name}:$element\n")
               }
            }

            writer.write("\n")
         }
      }

      true
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun loadProfile(name: String): Boolean = try {
      val sanitizedName = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val profileFile = profilesDir.resolve("$sanitizedName.profile")
      if (!Files.exists(profileFile)) {
         false
      } else {
         val settingsMap = LinkedHashMap<String, String>()
         for (line in Files.readAllLines(profileFile)) {
            if (!line.startsWith("#") && !line.isBlank()) {
               val colonIndex = line.indexOf(':')
               if (colonIndex > 0) {
                  settingsMap[line.substring(0, colonIndex)] = line.substring(colonIndex + 1)
               }
            }
         }

         for (tab in Tabs.all) {
            for (setting in tab.settings) {
               val key = "${tab.name}.${setting.name}"
               val valueStr = settingsMap[key]
               if (valueStr != null) {
                  try {
                     val obj = JsonObject()
                     obj.add(setting.name, JsonParser.parseString(valueStr))
                     setting.readJson(obj)
                  } catch (_: Exception) {
                  }
               }
            }
         }

         Config.INSTANCE.save()
         true
      }
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun deleteProfile(name: String): Boolean = try {
      val sanitizedName = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val profileFile = profilesDir.resolve("$sanitizedName.profile")
      if (Files.exists(profileFile)) {
         Files.delete(profileFile)
         true
      } else {
         false
      }
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun renameProfile(oldName: String, newName: String): Boolean = try {
      val sanitizedOld = oldName.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val sanitizedNew = newName.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val oldFile = profilesDir.resolve("$sanitizedOld.profile")
      val newFile = profilesDir.resolve("$sanitizedNew.profile")
      if (Files.exists(oldFile)) {
         if (!Files.exists(newFile)) {
            val content = Files.readString(oldFile)
            val updatedContent = content.replaceFirst("# CB Profile: $oldName", "# CB Profile: $newName")
            Files.writeString(newFile, updatedContent)
            Files.delete(oldFile)
            return true
         }
      }

      false
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun duplicateProfile(sourceName: String, newName: String): Boolean = try {
      val sanitizedSource = sourceName.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val sanitizedNew = newName.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val sourceFile = profilesDir.resolve("$sanitizedSource.profile")
      val newFile = profilesDir.resolve("$sanitizedNew.profile")
      if (Files.exists(sourceFile)) {
         if (!Files.exists(newFile)) {
            val content = Files.readString(sourceFile)
            val updatedContent = content.replaceFirst("# CB Profile: $sourceName", "# CB Profile: $newName")
            Files.writeString(newFile, updatedContent)
            return true
         }
      }

      false
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun exportProfile(name: String): String? = try {
      val sanitizedName = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val profileFile = profilesDir.resolve("$sanitizedName.profile")
      if (!Files.exists(profileFile)) {
         null
      } else {
         Files.readString(profileFile)
      }
   } catch (e: Exception) {
      e.printStackTrace()
      null
   }

   fun importProfile(name: String, content: String): Boolean = try {
      val sanitizedName = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val profileFile = profilesDir.resolve("$sanitizedName.profile")
      if (!Files.exists(profilesDir)) {
         Files.createDirectories(profilesDir)
      }

      Files.writeString(profileFile, content)
      true
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   fun formatTimestamp(millis: Long): String {
      val instant = Instant.ofEpochMilli(millis)
      val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm").withZone(ZoneId.systemDefault())
      return formatter.format(instant)
   }

   data class ProfileInfo(val name: String, val path: Path, val lastModified: Long, val settingCount: Int)
}
