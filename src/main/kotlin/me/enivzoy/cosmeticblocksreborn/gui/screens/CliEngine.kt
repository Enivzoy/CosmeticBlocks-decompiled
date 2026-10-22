package me.enivzoy.cosmeticblocksreborn.gui.screens

import java.awt.Color
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.io.path.createParentDirectories
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.nameWithoutExtension
import kotlin.random.Random
import me.enivzoy.cosmeticblocksreborn.config.Config
import me.enivzoy.cosmeticblocksreborn.config.Tabs
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting
import me.enivzoy.cosmeticblocksreborn.setting.Setting
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import me.enivzoy.cosmeticblocksreborn.utils.ConfigBackup
import me.enivzoy.cosmeticblocksreborn.utils.Scheduler
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries

object CliEngine {
   private const val MAX_HISTORY = 50
   private const val MAX_SUGGESTIONS = 10
   private const val CB_USAGE =
      "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>"
   private const val CB_ROOM_USAGE = "Usage: cb room [info|clear|clearall|list|scan]"
   private const val CB_FAV_USAGE = "Usage: cb fav [add|remove|list|gui]"

   private val configDir: Path = FabricLoader.getInstance().configDir.resolve("cb")
   private val commandHistory = ArrayList<String>()
   private val savedColors = ArrayList<Color>()
   private val notes = ArrayList<String>()
   private val reminders = ArrayList<Reminder>()
   private var nextReminderId = 1
   private var loaded = false

   private val TOP_COMMANDS: List<String> = listOf(
      "w",
      "export",
      "import",
      "save",
      "load",
      "profiles",
      "delprofile",
      "scale",
      "fps",
      "ping",
      "calc",
      "c",
      "coin",
      "flip",
      "roll",
      "dice",
      "d",
      "note",
      "notes",
      "noteclear",
      "noteexport",
      "remind",
      "reminder",
      "timer",
      "reminders",
      "cancelreminder",
      "clearreminders",
      "t",
      "toggle",
      "bind",
      "binds",
      "keybinds",
      "cb"
   )

   private val CB_SUBCOMMANDS: List<String> = listOf(
      "em",
      "edit",
      "block",
      "b",
      "remove",
      "rm",
      "clear",
      "clearall",
      "list",
      "ls",
      "save",
      "reload",
      "create",
      "load",
      "delete",
      "del",
      "configs",
      "pos1",
      "p1",
      "pos2",
      "p2",
      "sel",
      "selection",
      "fill",
      "f",
      "replace",
      "rep",
      "r",
      "undo",
      "u",
      "fav",
      "favorites",
      "shape",
      "room",
      "backup",
      "gui",
      "state",
      "st",
      "props",
      "properties"
   )

   private fun getSavedColorsFile(): Path = configDir.resolve("saved_colors.txt")

   private fun getNotesFile(): Path = configDir.resolve("notes.txt")

   private fun getProfilesDir(): Path = configDir.resolve("profiles")

   fun getSavedColors(): List<Color> = savedColors

   fun getNotes(): List<String> = notes

   fun getReminders(): List<Reminder> = reminders

   private fun ensureLoaded() {
      if (!loaded) {
         loaded = true
         this.loadSavedColors()
         this.loadNotes()
      }
   }

   fun execute(input: String): String {
      this.ensureLoaded()
      val cmd = input.trim()
      if (cmd.isNotEmpty() && (commandHistory.isEmpty() || commandHistory.last() != cmd)) {
         commandHistory.add(cmd)
         if (commandHistory.size > MAX_HISTORY) {
            commandHistory.removeAt(0)
         }
      }

      return try {
         this.executeCliCommand(cmd)
      } catch (t: Throwable) {
         "Error: ${t.message}"
      }
   }

   fun history(): List<String> = commandHistory.toList()

   fun suggestions(input: String): List<Suggestion> = this.computeSuggestions(input)

   fun checkReminders() {
      if (reminders.isEmpty()) {
         return
      }

      val now = System.currentTimeMillis()
      val triggered = reminders.filter { it.triggerTime <= now }
      for (reminder in triggered) {
         reminders.remove(reminder)
         ChatUtil.info("Reminder: ${reminder.message}")
      }
   }

   fun addReminder(delayMs: Long, message: String): Int {
      val id = nextReminderId++
      reminders.add(Reminder(System.currentTimeMillis() + delayMs, message, id))
      if (reminders.size > 1) {
         reminders.sortBy { it.triggerTime }
      }

      return id
   }

   fun cancelReminder(id: Int): Boolean = reminders.removeAll { it.id == id }

   private fun computeSuggestions(raw: String): List<Suggestion> {
      val text = raw.trimStart()
      if (text.isEmpty()) {
         return emptyList()
      }

      val firstSpace = text.indexOf(' ')
      if (firstSpace < 0) {
         val q = text.lowercase()
         return TOP_COMMANDS.distinct()
            .filter { it.startsWith(q) }
            .map { Suggestion(it, it, "") }
            .take(MAX_SUGGESTIONS)
      }

      val head = text.substring(0, firstSpace).lowercase()
      val rest = text.substring(firstSpace + 1)
      return when (head) {
         "delprofile", "load" -> {
            val q = rest.lowercase()
            try {
               this.listProfiles()
                  .filter { it.lowercase().startsWith(q) }
                  .map { Suggestion("$head $it", "$head $it", if (head == "load") "Load profile" else "Delete profile") }
                  .take(MAX_SUGGESTIONS)
            } catch (_: Throwable) {
               emptyList()
            }
         }

         "toggle", "t" -> {
            val q = rest.lowercase()
            this.booleanSettings().map { it.name }
               .filter { it.lowercase().contains(q) }
               .distinct()
               .take(MAX_SUGGESTIONS)
               .map { Suggestion("$head $it", "$head $it", "toggle") }
         }

         "cb" -> this.cbSuggestions(rest)
         else -> emptyList()
      }
   }

   private fun cbSuggestions(rest: String): List<Suggestion> {
      val nextSpace = rest.indexOf(' ')
      if (nextSpace < 0) {
         val q = rest.lowercase()
         return CB_SUBCOMMANDS.distinct()
            .filter { it.startsWith(q) }
            .map { Suggestion("cb $it", "cb $it", "") }
            .take(MAX_SUGGESTIONS)
      }

      val sub = rest.substring(0, nextSpace).lowercase()
      val args = rest.substring(nextSpace + 1)
      val lastToken = args.substringAfterLast(' ').lowercase()
      val argsBeforeLast = args.substringBeforeLast(' ', "")
      val prefix = if (argsBeforeLast.isEmpty()) "cb $sub" else "cb $sub $argsBeforeLast"
      return when (sub) {
         "r", "rep", "replace" -> {
            val tokenCount = args.trim().split(Regex("\\s+")).count { it.isNotEmpty() }
            val hint = if (tokenCount <= 1) "From" else "To"
            this.blockSuggestions(lastToken, prefix, hint)
         }

         "favorites", "fav" -> this.favSuggestions(args, prefix)
         "delete", "del", "load" -> {
            val configs = try {
               CosmeticBlocks.INSTANCE.listConfigs()
            } catch (_: Throwable) {
               emptyList()
            }

            configs.filter { lastToken.isEmpty() || it.lowercase().contains(lastToken) }
               .map { Suggestion("$prefix $it", "$prefix $it", if (sub == "load") "Load" else "Delete") }
               .take(MAX_SUGGESTIONS)
         }

         "b", "block" -> this.blockSuggestions(lastToken, prefix, "Brush")
         "f", "fill" -> this.blockSuggestions(lastToken, prefix, "Fill")
         "room" -> listOf("info", "clear")
            .filter { it.startsWith(lastToken) }
            .map { Suggestion("$prefix $it", "$prefix $it", "Room") }

         "shape" -> listOf("none", "fence", "wall")
            .filter { it.startsWith(lastToken) }
            .map { Suggestion("$prefix $it", "$prefix $it", "Shape") }

         else -> emptyList()
      }
   }

   private fun blockSuggestions(token: String, prefix: String, hint: String): List<Suggestion> {
      val q = token.removePrefix("minecraft:").replace(' ', '_')
      return BuiltInRegistries.BLOCK.keySet()
         .asSequence()
         .map { it.path }
         .filter { q.isEmpty() || it.contains(q) }
         .take(MAX_SUGGESTIONS)
         .map { Suggestion("$prefix $it", it, hint) }
         .toList()
   }

   private fun favSuggestions(args: String, prefix: String): List<Suggestion> {
      val parts = args.split(' ', limit = 2)
      val favSub = parts[0].lowercase()
      val favArg = parts.getOrNull(1)?.trim() ?: ""
      if (parts.size < 2) {
         return listOf("add", "remove", "rm", "list", "gui")
            .filter { it.startsWith(favSub) }
            .map { Suggestion("$prefix $it", "$prefix $it", "Favorites") }
      }

      return when (favSub) {
         "remove", "rm" -> {
            val favs = try {
               CosmeticBlocks.INSTANCE.getFavorites()
            } catch (_: Throwable) {
               emptyList()
            }

            val q = favArg.lowercase()
            favs.map { it.removePrefix("minecraft:") }
               .filter { q.isEmpty() || it.contains(q) }
               .map { Suggestion("$prefix $favSub $it", it, "Remove fav") }
               .take(MAX_SUGGESTIONS)
         }

         "add" -> {
            val blockId = favArg.substringAfterLast(' ').lowercase()
            this.blockSuggestions(blockId, "$prefix add", "Add fav")
         }

         else -> emptyList()
      }
   }

   private fun booleanSettings(): List<BooleanSetting> =
      Tabs.all.flatMap { it.settings }.filterIsInstance<BooleanSetting>()

   private fun keybindSettings(): List<KeybindSetting> =
      Tabs.all.flatMap { it.settings }.filterIsInstance<KeybindSetting>()

   private fun parseTimeString(input: String): Long? {
      val str = input.lowercase().trim()
      val total = str.toLongOrNull()
      if (total != null) {
         return total * 1000
      }

      var totalMs = 0L
      var currentNum = ""
      for (c in str) {
         when {
            c.isDigit() -> currentNum += c
            c == 'h' -> {
               val hours = currentNum.toLongOrNull() ?: return null
               totalMs += hours * 3600 * 1000
               currentNum = ""
            }

            c == 'm' -> {
               val minutes = currentNum.toLongOrNull() ?: return null
               totalMs += minutes * 60 * 1000
               currentNum = ""
            }

            c == 's' -> {
               val seconds = currentNum.toLongOrNull() ?: return null
               totalMs += seconds * 1000
               currentNum = ""
            }

            else -> return null
         }
      }

      if (currentNum.isNotEmpty()) {
         val trailing = currentNum.toLongOrNull() ?: return null
         totalMs += trailing * 1000
      }

      return if (totalMs > 0L) totalMs else null
   }

   private fun formatDuration(ms: Long): String {
      val totalSeconds = ms / 1000
      val hours = totalSeconds / 3600
      val minutes = totalSeconds % 3600 / 60
      val seconds = totalSeconds % 60
      return if (hours > 0L) {
         "${hours}h ${minutes}m"
      } else if (minutes > 0L) {
         "${minutes}m ${seconds}s"
      } else {
         "${seconds}s"
      }
   }

   private fun evaluateCalcExpression(expr: String): Double? =
      try {
         CalcParser(expr).parse()
      } catch (_: Exception) {
         null
      }

   private fun executeCliCommand(input: String): String {
      val parts = input.split(' ', limit = 2)
      val cmd = parts[0].lowercase()
      val arg = parts.getOrNull(1)?.trim() ?: ""
      return when (cmd) {
         "delprofile" -> {
            if (arg.isNotEmpty()) {
               if (this.deleteProfile(arg)) "Profile '$arg' deleted" else "Profile not found"
            } else {
               "Usage: delprofile <profile name>"
            }
         }

         "export" -> if (this.exportToClipboard()) "Settings exported to clipboard" else "Export failed"
         "import" -> if (this.importFromClipboard()) "Settings imported from clipboard" else "Import failed"
         "profiles" -> {
            val profiles = this.listProfiles()
            if (profiles.isEmpty()) "No profiles saved" else "Profiles: ${profiles.joinToString(", ")}"
         }

         "noteexport" -> {
            if (notes.isEmpty()) {
               "No notes to export"
            } else {
               val export = notes.joinToString("\n")
               Minecraft.getInstance().keyboardHandler.setClipboard(export)
               "Exported ${notes.size} notes to clipboard"
            }
         }

         "" -> ""
         "w" -> {
            Config.INSTANCE.save()
            "Settings saved"
         }

         "cb" -> this.executeCbCommand(arg)
         "fps" -> {
            val mc = Minecraft.getInstance()
            "FPS: ${mc.fps}"
         }

         "bind" -> {
            val bindParts = arg.split(' ', limit = 2)
            val settingName = bindParts[0].lowercase()
            val keyName = bindParts.getOrNull(1)?.uppercase() ?: ""
            if (settingName.isNotEmpty()) {
               if (keyName.isEmpty()) {
                  val setting = this.keybindSettings().firstOrNull { it.name.lowercase().contains(settingName) }
                  if (setting != null) {
                     val currentKey = setting.value
                     if (currentKey != -1 && currentKey > 0) {
                        val keyChar = if (currentKey in 32..126) currentKey.toChar().toString() else "KEY_$currentKey"
                        "Current bind for '${setting.name}': $keyChar"
                     } else {
                        "'${setting.name}' has no keybind"
                     }
                  } else {
                     "Setting not found: $settingName"
                  }
               } else {
                  val keyValue: Int? = if (keyName.length == 1) {
                     keyName[0].uppercaseChar().code
                  } else if (keyName.startsWith("F") && keyName.length > 1) {
                     val fNum = keyName.substring(1).toIntOrNull()
                     if (fNum != null && fNum in 1..25) 290 + fNum - 1 else null
                  } else if (keyName == "NONE" || keyName == "UNBIND") {
                     0
                  } else {
                     null
                  }

                  if (keyValue != null) {
                     val setting = this.keybindSettings().firstOrNull { it.name.lowercase().contains(settingName) }
                     if (setting != null) {
                        if (keyValue == 0) {
                           setting.value = -1
                           Config.INSTANCE.save()
                           "Unbound '${setting.name}'"
                        } else {
                           setting.value = keyValue
                           Config.INSTANCE.save()
                           "Bound '${setting.name}' to $keyName"
                        }
                     } else {
                        "Setting not found: $settingName"
                     }
                  } else {
                     "Invalid key: $keyName (use a-z, 0-9, f1-f12, or 'none')"
                  }
               }
            } else {
               "Usage: bind <setting> <key> (e.g., bind 'edit mode' g)"
            }
         }

         "calc", "c", "=" -> {
            if (arg.isNotEmpty()) {
               val result = this.evaluateCalcExpression(arg)
               if (result != null) {
                  val formatted = if (result == result.toLong().toDouble() && Math.abs(result) < 9.223372E18) {
                     result.toLong().toString()
                  } else {
                     "%.10f".format(result).trimEnd('0').trimEnd('.')
                  }

                  Minecraft.getInstance().keyboardHandler.setClipboard(formatted)
                  "$arg = $formatted (copied)"
               } else {
                  "Invalid expression"
               }
            } else {
               "Usage: calc <expr>"
            }
         }

         "load" -> {
            if (arg.isNotEmpty()) {
               if (this.loadProfile(arg)) "Profile '$arg' loaded" else "Profile not found"
            } else {
               "Usage: load <profile name>"
            }
         }

         "note" -> {
            if (arg.isNotEmpty()) {
               val timestamp = SimpleDateFormat("HH:mm:ss").format(Date())
               notes.add("[$timestamp] $arg")
               this.saveNotes()
               "Note saved (${notes.size} total)"
            } else {
               "Usage: note <text>"
            }
         }

         "notes" -> {
            if (notes.isEmpty()) {
               "No notes saved"
            } else {
               notes.takeLast(3).reversed().joinToString(" | ") { it.take(30) + if (it.length > 30) "..." else "" }
            }
         }

         "ping" -> {
            val mc = Minecraft.getInstance()
            val player = mc.player
            val latency = if (player != null) {
               val connection = mc.connection
               if (connection != null) {
                  val playerInfo = connection.getPlayerInfo(player.uuid)
                  if (playerInfo != null) playerInfo.latency else -1
               } else {
                  -1
               }
            } else {
               -1
            }

            if (latency >= 0) "Ping: ${latency}ms" else "Ping: N/A"
         }

         "reminders" -> {
            if (reminders.isEmpty()) {
               "No active reminders"
            } else {
               val now = System.currentTimeMillis()
               "Reminders: " + reminders.take(3)
                  .joinToString(", ") { "#${it.id}: ${formatDuration((it.triggerTime - now) / 1000 * 1000)}" }
            }
         }

         "clearreminders" -> {
            val count = reminders.size
            reminders.clear()
            "Cleared $count reminders"
         }

         "scale" -> {
            val mc = Minecraft.getInstance()
            val screenW = mc.window?.guiScaledWidth ?: 800
            val screenH = mc.window?.guiScaledHeight ?: 600
            val baseWindowWidth = 450.0F
            val baseWindowHeight = 350.0F
            val maxScaleW = (screenW - 40) / baseWindowWidth
            val maxScaleH = (screenH - 40) / baseWindowHeight
            val maxScale = Math.min(maxScaleW, Math.min(maxScaleH, 2.0F))
            if (arg.isNotEmpty()) {
               val scale = arg.toFloatOrNull()
               if (scale != null && 0.5F <= scale && scale <= maxScale) {
                  "Scale set to ${"%.1f".format(scale)} (apply in GUI)"
               } else if (scale != null && scale > maxScale) {
                  "Max scale for screen: ${"%.1f".format(maxScale)}"
               } else {
                  "Scale must be between 0.5 and ${"%.1f".format(maxScale)}"
               }
            } else {
               "Scale range: 0.5-${"%.1f".format(maxScale)}"
            }
         }

         "save" -> {
            if (arg.isNotEmpty()) {
               if (this.saveProfile(arg)) "Profile '$arg' saved" else "Failed to save profile"
            } else {
               "Usage: save <profile name>"
            }
         }

         "toggle", "t" -> {
            if (arg.isNotEmpty()) {
               val lowerArg = arg.lowercase()
               val setting = this.booleanSettings().firstOrNull { it.name.lowercase() == lowerArg }
               if (setting != null) {
                  val newValue = !setting.value
                  setting.value = newValue
                  val status = if (newValue) "\u00c2\u00a7aON" else "\u00c2\u00a7cOFF"
                  Config.INSTANCE.save()
                  "${setting.name}: $status"
               } else {
                  "Feature not found: $arg"
               }
            } else {
               "Usage: t <feature name>"
            }
         }

         "remind", "reminder", "timer" -> {
            if (arg.isNotEmpty()) {
               val timeParts = arg.split(' ', limit = 2)
               val timeStr = timeParts[0]
               val message = timeParts.getOrNull(1) ?: "Reminder!"
               val delayMs = this.parseTimeString(timeStr)
               if (delayMs != null && delayMs > 0L) {
                  val id = this.addReminder(delayMs, message)
                  "Reminder #$id set for ${this.formatDuration(delayMs)}"
               } else {
                  "Invalid time. Use: 30s, 5m, 1h, 1h30m"
               }
            } else {
               "Usage: remind <time> [message]"
            }
         }

         "binds", "keybinds" -> {
            val bound = this.keybindSettings().filter { it.value != -1 && it.value > 0 }
            if (bound.isEmpty()) {
               "No keybinds set"
            } else {
               bound.take(5).joinToString(", ") { setting ->
                  val v = setting.value
                  val keyChar = if (v in 32..126) v.toChar().toString() else "F${v - 290 + 1}"
                  "${setting.name}=$keyChar"
               }
            }
         }

         "coinflip", "coin", "flip" -> "Coin flip: " + (if (Math.random() < 0.5) "Heads" else "Tails")
         "d", "dice", "roll" -> {
            val max = arg.toIntOrNull() ?: 6
            if (max < 1) {
               "Max must be at least 1"
            } else {
               val result = (1..max).random(Random.Default)
               "Roll (1-$max): $result"
            }
         }

         "rmreminder", "cancelreminder" -> {
            val id = arg.toIntOrNull()
            if (id != null) {
               if (this.cancelReminder(id)) "Reminder #$id cancelled" else "Reminder #$id not found"
            } else {
               "Usage: cancelreminder <id>"
            }
         }

         else -> "Unknown command: $cmd"
      }
   }

   private fun executeCbCommand(arg: String): String {
      val parts = arg.split(' ', limit = 2)
      val subCmd = parts[0].lowercase()
      val cbArg = parts.getOrNull(1)?.trim() ?: ""
      return when (subCmd) {
         "favorites", "fav" -> {
            val favParts = cbArg.split(' ', limit = 2)
            val favCmd = favParts[0].lowercase()
            val favArg = favParts.getOrNull(1)?.trim() ?: ""
            if (favCmd.isNotEmpty() && favCmd != "gui") {
               when (favCmd) {
                  "add" -> {
                     val block = if (favArg.isEmpty()) CosmeticBlocks.INSTANCE.getSelectedBlock() else favArg
                     if (CosmeticBlocks.INSTANCE.addFavorite(block)) {
                        "Added ${block.removePrefix("minecraft:")} to favorites"
                     } else {
                        "Already in favorites or invalid block"
                     }
                  }

                  "remove", "rm" -> {
                     val block = if (favArg.isEmpty()) CosmeticBlocks.INSTANCE.getSelectedBlock() else favArg
                     if (CosmeticBlocks.INSTANCE.removeFavorite(block)) {
                        "Removed ${block.removePrefix("minecraft:")} from favorites"
                     } else {
                        "Not in favorites"
                     }
                  }

                  else -> if (favCmd != "list" && favCmd != "ls") {
                     CB_FAV_USAGE
                  } else {
                     val favs = CosmeticBlocks.INSTANCE.getFavorites()
                     if (favs.isEmpty()) "No favorites" else favs.joinToString(", ") { it.removePrefix("minecraft:") }
                  }
               }
            } else {
               Scheduler.scheduleTickEnd { openFavoritesScreen() }
               "Opening favorites..."
            }
         }

         "selection", "sel", "properties", "props" -> CB_USAGE
         "backup" -> ConfigBackup.backup()
         "create" -> if (cbArg.isNotEmpty()) {
            if (CosmeticBlocks.INSTANCE.createConfig(cbArg)) {
               "Created config '$cbArg'"
            } else {
               "Config '$cbArg' already exists"
            }
         } else {
            "Usage: cb create <name>"
         }

         "delete", "del" -> if (cbArg.isNotEmpty()) {
            if (CosmeticBlocks.INSTANCE.deleteConfig(cbArg)) {
               "Deleted config '$cbArg'"
            } else {
               "Cannot delete '$cbArg'"
            }
         } else {
            "Usage: cb delete <name>"
         }

         "reload" -> {
            CosmeticBlocks.INSTANCE.load()
            "Reloaded (${CosmeticBlocks.INSTANCE.getReplacementCount()} in dimension)"
         }

         "remove", "rm" -> {
            val coords = cbArg.split(' ')
            if (coords.size >= 3) {
               val x = coords[0].toIntOrNull()
               val y = coords[1].toIntOrNull()
               val z = coords[2].toIntOrNull()
               if (x != null && y != null && z != null) {
                  if (CosmeticBlocks.INSTANCE.removeAt(x, y, z)) {
                     "Removed at $x,$y,$z"
                  } else {
                     "No replacement at $x,$y,$z"
                  }
               } else {
                  "Invalid coords"
               }
            } else {
               "Usage: cb remove <x> <y> <z>"
            }
         }

         "" -> {
            val count = CosmeticBlocks.INSTANCE.getReplacementCount()
            val mode = if (CosmeticBlocks.INSTANCE.getEditMode()) "ON" else "OFF"
            val room = RoomScanner.currentRoom
            val roomInfo = if (room != null) " | Room: ${room.data.name}" else ""
            "CB: $count replacements, edit: $mode, config: ${DungeonConfigTab.INSTANCE.activeConfig.value}$roomInfo"
         }

         "b", "block" -> if (cbArg.isNotEmpty()) {
            CosmeticBlocks.INSTANCE.setBlock(cbArg.replace(' ', '_'))
            "Brush: ${cbArg.replace(' ', '_')}"
         } else {
            "Usage: cb block <block_id>"
         }

         "f", "fill" -> if (cbArg.isNotEmpty()) {
            CosmeticBlocks.INSTANCE.fillRegion(cbArg)
         } else {
            "Usage: cb fill <block[:weight]...>"
         }

         "r", "rep", "replace" -> {
            val rParts = cbArg.split(' ', limit = 2)
            val from = rParts[0]
            val to = rParts.getOrNull(1)?.trim() ?: ""
            if (from.isNotEmpty() && to.isNotEmpty()) {
               CosmeticBlocks.INSTANCE.replaceRegion(from, to)
            } else {
               "Usage: cb replace <from> <to[:weight]...>"
            }
         }

         "u", "undo" -> CosmeticBlocks.INSTANCE.undoLastOperation()
         "em", "edit" -> {
            CosmeticBlocks.INSTANCE.toggleEditMode()
            if (CosmeticBlocks.INSTANCE.getEditMode()) "CB edit mode ON" else "CB edit mode OFF"
         }

         "ls", "list" -> {
            val list = CosmeticBlocks.INSTANCE.listReplacements()
            if (list.isEmpty()) {
               "No CB replacements"
            } else {
               list.take(5).joinToString(" | ") { it.take(25) }
            }
         }

         "p1", "pos1" -> {
            val coords = cbArg.split(' ')
            if (coords.size >= 3) {
               val x = coords[0].toIntOrNull()
               val y = coords[1].toIntOrNull()
               val z = coords[2].toIntOrNull()
               if (x != null && y != null && z != null) {
                  CosmeticBlocks.INSTANCE.setPos1(BlockPos(x, y, z))
                  "Pos1: $x, $y, $z"
               } else {
                  "Invalid coordinates"
               }
            } else {
               val pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos()
               if (pos != null) {
                  CosmeticBlocks.INSTANCE.setPos1(pos)
                  "Pos1: ${pos.x}, ${pos.y}, ${pos.z}"
               } else {
                  "Look at a block first, then open CLI. Or: cb pos1 <x> <y> <z>"
               }
            }
         }

         "p2", "pos2" -> {
            val coords = cbArg.split(' ')
            if (coords.size >= 3) {
               val x = coords[0].toIntOrNull()
               val y = coords[1].toIntOrNull()
               val z = coords[2].toIntOrNull()
               if (x != null && y != null && z != null) {
                  CosmeticBlocks.INSTANCE.setPos2(BlockPos(x, y, z))
                  "Pos2: $x, $y, $z"
               } else {
                  "Invalid coordinates"
               }
            } else {
               val pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos()
               if (pos != null) {
                  CosmeticBlocks.INSTANCE.setPos2(pos)
                  "Pos2: ${pos.x}, ${pos.y}, ${pos.z}"
               } else {
                  "Look at a block first, then open CLI. Or: cb pos2 <x> <y> <z>"
               }
            }
         }

         "st", "state" -> {
            val stateParts = cbArg.split(' ', limit = 2)
            val property = stateParts[0]
            val value = stateParts.getOrNull(1)?.trim() ?: ""
            if (property.isNotEmpty() && value.isNotEmpty()) {
               CosmeticBlocks.INSTANCE.setBlockProperty(property, value)
               "Brush: ${CosmeticBlocks.INSTANCE.getSelectedBlock().removePrefix("minecraft:")}"
            } else {
               "Usage: cb state <property> <value>"
            }
         }

         "gui" -> {
            Scheduler.scheduleTickEnd { openBlockSelectorScreen() }
            "Opening block selector..."
         }

         "load" -> if (cbArg.isNotEmpty()) {
            if (CosmeticBlocks.INSTANCE.loadConfigByName(cbArg)) {
               "Loaded config '$cbArg'"
            } else {
               "Config '$cbArg' not found"
            }
         } else {
            "Usage: cb load <name>"
         }

         "room" -> {
            val roomCmd = cbArg.split(' ')[0].lowercase()
            when (roomCmd) {
               "", "info" -> {
                  val room = RoomScanner.currentRoom
                  if (room != null) {
                     "Room: ${room.data.name} | Rot: ${room.rotation} | Blocks: ${CosmeticBlocks.INSTANCE.getRoomBlockCount(room.data.name)}"
                  } else {
                     "Not in a scanned dungeon room"
                  }
               }

               "ls", "list" -> {
                  val rooms = CosmeticBlocks.INSTANCE.listRoomNames()
                  if (rooms.isEmpty()) {
                     "No room blocks saved"
                  } else {
                     rooms.joinToString(", ") { "$it (${CosmeticBlocks.INSTANCE.getRoomBlockCount(it)})" }
                  }
               }

               "scan" -> {
                  val name = RoomScanner.currentRoom?.data?.name
                  if (name != null) "Room: $name" else "Room: Unknown"
               }

               "clear" -> {
                  val room = RoomScanner.currentRoom
                  if (room != null) {
                     "Cleared ${CosmeticBlocks.INSTANCE.clearRoom(room.data.name)} blocks from ${room.data.name}"
                  } else {
                     "Not in a scanned room"
                  }
               }

               "clearall" -> "Cleared ${CosmeticBlocks.INSTANCE.clearAllRooms()} blocks from all rooms"
               else -> CB_ROOM_USAGE
            }
         }

         "save" -> {
            CosmeticBlocks.INSTANCE.save()
            "CB data saved"
         }

         "clear" -> {
            val count = CosmeticBlocks.INSTANCE.clearDimension()
            "Cleared $count replacements"
         }

         "shape" -> if (cbArg.isEmpty()) {
            val current = CosmeticBlocks.INSTANCE.getCurrentShape() ?: "none"
            "Shape: $current"
         } else if (cbArg != "none" && cbArg != "off") {
            if (listOf("fence", "wall", "pane").contains(cbArg)) {
               CosmeticBlocks.INSTANCE.setCurrentShape(cbArg)
               "Shape: $cbArg (next blocks will use this shape)"
            } else {
               "Shapes: fence, wall, pane, none"
            }
         } else {
            CosmeticBlocks.INSTANCE.setCurrentShape(null)
            "Shape override cleared"
         }

         "clearall" -> {
            val count = CosmeticBlocks.INSTANCE.clearAll()
            "Cleared $count total replacements"
         }

         "configs" -> {
            val configs = CosmeticBlocks.INSTANCE.listConfigs()
            "Configs: ${configs.joinToString(", ")} (active: ${DungeonConfigTab.INSTANCE.activeConfig.value})"
         }

         else -> CB_USAGE
      }
   }

   private fun exportToClipboard(): Boolean = try {
      val builder = StringBuilder()
      builder.append("# CB Settings Export").append('\n')
      for (tab in Tabs.all) {
         for (setting in tab.settings) {
            builder.append("${tab.name}.${setting.name}=${this.valueToString(setting)}").append('\n')
         }
      }

      Minecraft.getInstance().keyboardHandler.setClipboard(builder.toString())
      true
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   private fun importFromClipboard(): Boolean = try {
      val clipboard = Minecraft.getInstance().keyboardHandler.getClipboard()
      if (clipboard == null) {
         false
      } else {
         var imported = 0
         for (line in clipboard.lines()) {
            if (!line.startsWith("#") && !line.isBlank()) {
               val parts = line.split("=", limit = 2)
               if (parts.size == 2) {
                  val keyParts = parts[0].split(".", limit = 2)
                  if (keyParts.size == 2) {
                     val tabName = keyParts[0]
                     val propName = keyParts[1]
                     val tab = Tabs.all.firstOrNull { it.name == tabName }
                     if (tab != null) {
                        val setting = tab.settings.firstOrNull { it.name == propName }
                        if (setting != null && this.stringToValue(setting, parts[1])) {
                           imported++
                        }
                     }
                  }
               }
            }
         }

         if (imported > 0) {
            Config.INSTANCE.save()
         }

         imported > 0
      }
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   private fun saveProfile(name: String): Boolean = try {
      val sanitized = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val file = this.getProfilesDir().resolve("$sanitized.profile")
      if (!Files.exists(this.getProfilesDir())) {
         Files.createDirectories(this.getProfilesDir())
      }

      Files.newBufferedWriter(file).use { writer ->
         writer.write("# CB Profile: $name\n")
         for (tab in Tabs.all) {
            for (setting in tab.settings) {
               writer.write("${tab.name}.${setting.name}:${this.valueToString(setting)}\n")
            }
         }
      }

      true
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   private fun loadProfile(name: String): Boolean = try {
      val sanitized = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val file = this.getProfilesDir().resolve("$sanitized.profile")
      if (!Files.exists(file)) {
         false
      } else {
         val map = LinkedHashMap<String, String>()
         for (line in Files.readAllLines(file)) {
            if (!line.startsWith("#") && !line.isBlank()) {
               val idx = line.indexOf(':', 0, false)
               if (idx > 0) {
                  map[line.substring(0, idx)] = line.substring(idx + 1)
               }
            }
         }

         for (tab in Tabs.all) {
            for (setting in tab.settings) {
               val stored = map["${tab.name}.${setting.name}"]
               if (stored != null) {
                  this.stringToValue(setting, stored)
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

   private fun listProfiles(): List<String> {
      if (!Files.exists(this.getProfilesDir())) {
         return emptyList()
      }

      return try {
         this.getProfilesDir().listDirectoryEntries("*.profile")
            .map { it.nameWithoutExtension }
            .sorted()
      } catch (_: Throwable) {
         emptyList()
      }
   }

   private fun deleteProfile(name: String): Boolean = try {
      val sanitized = name.replace(Regex("[^a-zA-Z0-9_-]"), "_")
      val file = this.getProfilesDir().resolve("$sanitized.profile")
      if (Files.exists(file)) {
         Files.delete(file)
         true
      } else {
         false
      }
   } catch (e: Exception) {
      e.printStackTrace()
      false
   }

   private fun valueToString(setting: Setting<*>): String {
      val v = setting.value
      return when (v) {
         is Int -> v.toString()
         is Double -> v.toString()
         is Float -> v.toString()
         is Boolean -> v.toString()
         is Enum<*> -> v.name
         else -> v.toString()
      }
   }

   @Suppress("UNCHECKED_CAST")
   private fun stringToValue(setting: Setting<*>, raw: String): Boolean = try {
      val current = setting.value
      when (current) {
         is Int -> {
            (setting as Setting<Int>).value = raw.toInt()
            true
         }

         is Double -> {
            (setting as Setting<Double>).value = raw.toDouble()
            true
         }

         is Float -> {
            (setting as Setting<Float>).value = raw.toFloat()
            true
         }

         is Boolean -> {
            (setting as Setting<Boolean>).value = raw.toBoolean()
            true
         }

         is String -> {
            (setting as Setting<String>).value = raw
            true
         }

         is Enum<*> -> {
            val match = current.declaringJavaClass.enumConstants?.firstOrNull { (it as Enum<*>).name == raw }
            if (match == null) {
               false
            } else {
               (setting as Setting<Any>).value = match
               true
            }
         }

         else -> false
      }
   } catch (_: Exception) {
      false
   }

   fun saveSavedColors() {
      try {
         if (!Files.exists(this.getSavedColorsFile())) {
            this.getSavedColorsFile().createParentDirectories()
            Files.createFile(this.getSavedColorsFile())
         }

         Files.newBufferedWriter(this.getSavedColorsFile()).use { writer ->
            for (color in savedColors) {
               writer.write("${color.rgb}\n")
            }
         }
      } catch (e: IOException) {
         throw UncheckedIOException(e)
      }
   }

   private fun loadSavedColors() {
      try {
         val file = this.getSavedColorsFile()
         if (Files.exists(file)) {
            savedColors.clear()
            Files.newBufferedReader(this.getSavedColorsFile()).use { reader ->
               while (true) {
                  val line = reader.readLine() ?: return
                  val parsed = line.toIntOrNull()
                  if (parsed != null) {
                     savedColors.add(Color(parsed, true))
                  }
               }
            }
         }
      } catch (e: IOException) {
         throw UncheckedIOException(e)
      }
   }

   private fun saveNotes() {
      try {
         if (!Files.exists(this.getNotesFile())) {
            this.getNotesFile().createParentDirectories()
            Files.createFile(this.getNotesFile())
         }

         Files.newBufferedWriter(this.getNotesFile()).use { writer ->
            for (note in notes) {
               writer.write("$note\n")
            }
         }
      } catch (e: IOException) {
         throw UncheckedIOException(e)
      }
   }

   private fun loadNotes() {
      try {
         val file = this.getNotesFile()
         if (Files.exists(file)) {
            notes.clear()
            Files.newBufferedReader(this.getNotesFile()).use { reader ->
               while (true) {
                  val line = reader.readLine() ?: return
                  if (!line.isBlank()) {
                     notes.add(line)
                  }
               }
            }
         }
      } catch (e: IOException) {
         throw UncheckedIOException(e)
      }
   }

   private fun openFavoritesScreen() {
      Minecraft.getInstance().setScreenAndShow(BlockSelectorScreen())
   }

   private fun openBlockSelectorScreen() {
      Minecraft.getInstance().setScreenAndShow(BlockSelectorScreen())
   }

   private class CalcParser(private val expr: String) {
      private var pos = 0
      private val length = expr.length

      fun parse(): Double {
         val result = this.parseExpression()
         if (this.pos < this.length) {
            throw IllegalArgumentException("Unexpected character")
         }

         return result
      }

      private fun parseExpression(): Double {
         var result = this.parseTerm()
         while (this.pos < this.length) {
            if (this.match('+')) {
               result += this.parseTerm()
            } else if (this.match('-')) {
               result -= this.parseTerm()
            } else {
               break
            }
         }

         return result
      }

      private fun parseTerm(): Double {
         var result = this.parseFactor()
         while (this.pos < this.length) {
            if (this.match('*')) {
               result *= this.parseFactor()
            } else if (this.match('/')) {
               result /= this.parseFactor()
            } else if (this.match('%')) {
               result %= this.parseFactor()
            } else {
               break
            }
         }

         return result
      }

      private fun parseFactor(): Double {
         val base = this.parseUnary()
         return if (this.match('^')) Math.pow(base, this.parseFactor()) else base
      }

      private fun parseUnary(): Double =
         if (this.match('-')) -this.parseUnary() else if (this.match('+')) this.parseUnary() else this.parsePrimary()

      private fun parsePrimary(): Double {
         this.skipWhitespace()
         if (this.match('(')) {
            val result = this.parseExpression()
            if (!this.match(')')) {
               throw IllegalArgumentException("Missing )")
            }

            return result
         }

         val start = this.pos
         while (this.pos < this.length && (this.expr[this.pos].isLetter() || this.expr[this.pos] == '_')) {
            this.pos++
         }

         if (this.pos <= start) {
            return this.parseNumber()
         }

         val name = this.expr.substring(start, this.pos).lowercase()
         return when (name) {
            "e" -> Math.E
            "ln" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.log(arg)
            }

            "pi" -> Math.PI
            "abs" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.abs(arg)
            }

            "cos" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.cos(arg)
            }

            "log" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.log10(arg)
            }

            "sin" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.sin(arg)
            }

            "tan" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.tan(arg)
            }

            "ceil" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.ceil(arg)
            }

            "sqrt" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.sqrt(arg)
            }

            "floor" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.floor(arg)
            }

            "round" -> {
               this.expect('(')
               val arg = this.parseExpression()
               this.expect(')')
               Math.rint(arg)
            }

            else -> throw IllegalArgumentException("Unknown function: $name")
         }
      }

      private fun parseNumber(): Double {
         this.skipWhitespace()
         val start = this.pos
         if (this.pos < this.length && (this.expr[this.pos] == '-' || this.expr[this.pos] == '+')) {
            this.pos++
         }

         while (this.pos < this.length && (this.expr[this.pos].isDigit() || this.expr[this.pos] == '.')) {
            this.pos++
         }

         if (start == this.pos) {
            throw IllegalArgumentException("Expected number at ${this.pos}")
         }

         return this.expr.substring(start, this.pos).toDouble()
      }

      private fun match(c: Char): Boolean {
         this.skipWhitespace()
         if (this.pos < this.length && this.expr[this.pos] == c) {
            this.pos++
            return true
         }

         return false
      }

      private fun expect(c: Char) {
         if (!this.match(c)) {
            throw IllegalArgumentException("Expected '$c'")
         }
      }

      private fun skipWhitespace() {
         while (this.pos < this.length && this.expr[this.pos].isWhitespace()) {
            this.pos++
         }
      }
   }

   data class Reminder(val triggerTime: Long, val message: String, val id: Int)

   data class Suggestion(val insert: String, val display: String, val hint: String)
}
