package me.enivzoy.cosmeticblocksreborn.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.PreferencesTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.ConfigCompression
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks
import me.enivzoy.cosmeticblocksreborn.cosmetic.Freecam
import me.enivzoy.cosmeticblocksreborn.cosmetic.ReplacementIO
import me.enivzoy.cosmeticblocksreborn.cosmetic.RoomOverrideManager
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen
import me.enivzoy.cosmeticblocksreborn.gui.hud.DebugStats
import me.enivzoy.cosmeticblocksreborn.gui.screens.HistoryScreen
import me.enivzoy.cosmeticblocksreborn.gui.screens.HudEditorScreen
import me.enivzoy.cosmeticblocksreborn.gui.screens.KeybindEditorScreen
import me.enivzoy.cosmeticblocksreborn.gui.screens.ProfileScreen
import me.enivzoy.cosmeticblocksreborn.render.BlockIconStore
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import me.enivzoy.cosmeticblocksreborn.utils.ConfigBackup
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.commands.CommandBuildContext
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import java.util.Locale
import java.util.concurrent.CompletableFuture

class CbCommand private constructor() {
   private fun open(screen: () -> Screen) {
      val mc = Minecraft.getInstance()
      mc.execute { openScreen(mc, screen) }
   }

   private fun openGui() {
      open { ClickGuiScreen() }
   }

   fun register() {
      ClientCommandRegistrationCallback.EVENT.register(::onRegisterCommands)
   }

   private fun suggestBlockIds(builder: SuggestionsBuilder): SuggestionsBuilder {
      val input = builder.remaining
      val inputLower = input.lowercase(Locale.ROOT)
      if (input.contains('[')) {
         val bracketIdx = input.indexOf('[')
         val blockIdRaw = input.substring(0, bracketIdx)
         val blockId = if (blockIdRaw.contains(':')) blockIdRaw else "minecraft:$blockIdRaw"
         val loc = Identifier.tryParse(blockId)
         val block = if (loc != null) BuiltInRegistries.BLOCK.getOptional(loc).orElse(null) else null

         if (block != null && !input.endsWith("]")) {
            val fullBlockId = loc.toString()
            val cleanInput = input.removeSuffix("]")
            val lastCommaIdx = cleanInput.lastIndexOf(',')

            val prefix = if (lastCommaIdx > bracketIdx) cleanInput.substring(0, lastCommaIdx + 1) else "$fullBlockId["
            val lastEntry = if (lastCommaIdx > bracketIdx) cleanInput.substring(lastCommaIdx + 1) else cleanInput.substring(bracketIdx + 1)
            val setPropsStr = if (lastCommaIdx > bracketIdx) cleanInput.substring(bracketIdx + 1, lastCommaIdx) else ""

            val setProps: Set<String> = if (setPropsStr.isEmpty()) {
               emptySet()
            } else {
               setPropsStr.split(",").filter { it.contains('=') }.map { it.substringBefore('=') }.toSet()
            }

            if (lastEntry.contains('=')) {
               val propName = lastEntry.substringBefore('=')
               val prop = block.stateDefinition.getProperty(propName)
               if (prop != null) {
                  val propPrefix = "$prefix$propName="

                  for (possibleValue in prop.possibleValues) {
                     val valueName = valueName(prop, possibleValue as Any)
                     builder.suggest("$propPrefix$valueName]")

                     val allProperties = block.stateDefinition.properties
                     val propertyCount = if (allProperties.isEmpty()) {
                        0
                     } else {
                        var count = 0
                        for (candidate in allProperties) {
                           if (!setProps.contains(candidate.name) && candidate.name != propName) {
                              count++
                           }
                        }
                        count
                     }

                     if (propertyCount > 0) {
                        builder.suggest("$propPrefix$valueName,")
                     }
                  }
               }
            } else {
               for (prop in block.stateDefinition.properties) {
                  if (!setProps.contains(prop.name)) {
                     val propNameLower = prop.name.lowercase(Locale.ROOT)
                     val lastEntryLower = lastEntry.lowercase(Locale.ROOT)
                     if (propNameLower.startsWith(lastEntryLower)) {
                        builder.suggest("$prefix${prop.name}=")
                     }
                  }
               }
            }
         }
      } else {
         for (key in BuiltInRegistries.BLOCK.keySet()) {
            val id = key.toString()
            if (!id.startsWith(inputLower)) {
               val path = key.path
               if (!path.startsWith(inputLower)) {
                  continue
               }
            }

            builder.suggest(id)
         }
      }

      return builder
   }

   private fun suggestBlockIdsGreedy(builder: SuggestionsBuilder): SuggestionsBuilder {
      val remaining = builder.remaining
      val lastSpace = remaining.lastIndexOf(' ')
      val wordBuilder = if (lastSpace >= 0) builder.createOffset(builder.start + lastSpace + 1) else builder
      return suggestBlockIds(wordBuilder)
   }

   companion object {
      @JvmField
      val INSTANCE: CbCommand = CbCommand()

      @Suppress("UNCHECKED_CAST")
      private fun valueName(prop: Property<*>, value: Any): String =
         (prop as Property<Comparable<Any>>).getName(value as Comparable<Any>)

      private fun openScreen(mc: Minecraft, screen: () -> Screen) {
         mc.setScreenAndShow(screen())
      }

      private fun stripMinecraftPrefix(it: String): CharSequence = it.removePrefix("minecraft:")

      private fun stripMinecraftPrefixAlt(it: String): CharSequence = it.removePrefix("minecraft:")

      private fun onGuiCommand(it: CommandContext<*>): Int {
         INSTANCE.openGui()
         return 1
      }

      private fun onProfilesCommand(it: CommandContext<*>): Int {
         INSTANCE.open { ProfileScreen() }
         return 1
      }

      private fun onKeybindsCommand(it: CommandContext<*>): Int {
         INSTANCE.open { KeybindEditorScreen() }
         return 1
      }

      private fun onHudCommand(it: CommandContext<*>): Int {
         INSTANCE.open { HudEditorScreen() }
         return 1
      }

      private fun onToggleEditModeCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.toggleEditMode()
         return 1
      }

      private fun onToggleFillModeCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.toggleFillMode()
         return 1
      }

      private fun onFreecamCommand(it: CommandContext<*>): Int {
         Freecam.toggle()
         return 1
      }

      private fun onBlockSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> =
         INSTANCE.suggestBlockIds(builder).buildFuture()

      private fun onBlockCommand(it: CommandContext<*>): Int {
         val blockId = StringArgumentType.getString(it, "block")
         CosmeticBlocks.INSTANCE.setBlock(blockId)
         return 1
      }

      private fun onRemoveCommand(it: CommandContext<*>): Int {
         ChatUtil.send(CosmeticBlocks.INSTANCE.removeInRegion(null))
         return 1
      }

      private fun onRemoveSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> =
         INSTANCE.suggestBlockIds(builder).buildFuture()

      private fun onRemoveBlockCommand(it: CommandContext<*>): Int {
         val block = StringArgumentType.getString(it, "block")
         ChatUtil.send(CosmeticBlocks.INSTANCE.removeInRegion(block))
         return 1
      }

      private fun onClearCommand(it: CommandContext<*>): Int {
         val count = CosmeticBlocks.INSTANCE.clearDimension()
         ChatUtil.send("Cleared $count replacements in current dimension")
         return 1
      }

      private fun onListCommand(it: CommandContext<*>): Int {
         val list = CosmeticBlocks.INSTANCE.listReplacements()
         if (list.isEmpty()) {
            ChatUtil.send("No replacements")
         } else {
            ChatUtil.send("--- Replacements (${list.size}) ---")
            for (item in list.take(20)) {
               ChatUtil.send("  $item")
            }
            if (list.size > 20) {
               ChatUtil.send("  ... and ${list.size - 20} more")
            }
         }

         return 1
      }

      private fun onSaveCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.save()
         ChatUtil.send("Data saved")
         return 1
      }

      private fun onReloadCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.load()
         ChatUtil.send("Data reloaded (${CosmeticBlocks.INSTANCE.getReplacementCount()} in current dimension)")
         return 1
      }

      private fun onMigrateSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
         val input = builder.remaining.removeSuffix(".json")
         for (config in CosmeticBlocks.INSTANCE.listConfigs()) {
            if (config.startsWith(input, ignoreCase = true)) {
               builder.suggest(config)
            }
         }

         return builder.buildFuture()
      }

      private fun onConfigSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
         val input = builder.remaining.removeSuffix(".json")
         for (config in CosmeticBlocks.INSTANCE.listConfigs()) {
            if (config.startsWith(input, ignoreCase = true)) {
               builder.suggest(config)
            }
         }

         return builder.buildFuture()
      }

      private fun onMigrateCommand(it: CommandContext<*>): Int {
         val name = StringArgumentType.getString(it, "name")
         ReplacementIO.INSTANCE.migrateAsync(name, 0)
         return 1
      }

      private fun onMigrateFlagSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
         for (flag in MIGRATE_FLAGS) {
            if (flag.startsWith(builder.remaining, ignoreCase = true)) {
               builder.suggest(flag)
            }
         }

         return builder.buildFuture()
      }

      private fun onMigrateFlagsCommand(it: CommandContext<*>): Int {
         val name = StringArgumentType.getString(it, "name")
         val flags = parseMigrateFlags(StringArgumentType.getString(it, "flags")) ?: return 0
         ReplacementIO.INSTANCE.migrateAsync(name, flags)
         return 1
      }

      private fun parseMigrateFlags(raw: String): Int? {
         var flags = 0
         var useSettings = false
         for (token in raw.trim().split(" ").filter { it.isNotEmpty() }) {
            when (token.lowercase(Locale.ROOT)) {
               "-m", "-mini", "-minified" -> flags = flags or ConfigCompression.MINI_STAGES
               "-minify", "--minify", "--minified" -> flags = flags or ConfigCompression.MINIFY
               "-gzip", "-gz", "--gzip" -> flags = flags or ConfigCompression.GZIP
               "-dict", "-dictionary", "--dict", "--dictionary" -> flags = flags or ConfigCompression.DICTIONARY
               "-palette", "-state", "--palette", "--state" ->
                  flags = flags or ConfigCompression.DICTIONARY or ConfigCompression.PALETTE
               "-rle", "--rle" -> flags = flags or ConfigCompression.RLE
               "-s", "-settings", "--settings" -> useSettings = true
               else -> {
                  ChatUtil.send("Unknown migrate option '" + token + "'. Try " + MIGRATE_FLAGS.joinToString(" "))
                  return null
               }
            }
         }

         if (useSettings) {
            val prefs = PreferencesTab.INSTANCE
            if (!prefs.minimizeConfig.value) {
               ChatUtil.send("Enable 'Minimize Config' in the Settings tab to use --settings.")
               return null
            }

            if (prefs.compressGzip.value) {
               flags = flags or ConfigCompression.GZIP
            }

            if (prefs.compressMinify.value) {
               flags = flags or ConfigCompression.MINIFY
            }

            if (prefs.compressDictionary.value) {
               flags = flags or ConfigCompression.DICTIONARY
            }

            if (prefs.compressPalette.value && prefs.compressDictionary.value) {
               flags = flags or ConfigCompression.PALETTE
            }

            if (prefs.compressRle.value) {
               flags = flags or ConfigCompression.RLE
            }
         }

         if (flags and ConfigCompression.PALETTE != 0) {
            flags = flags or ConfigCompression.DICTIONARY
         }

         return flags
      }

      private fun onCreateCommand(it: CommandContext<*>): Int {
         val name = StringArgumentType.getString(it, "name")
         if (CosmeticBlocks.INSTANCE.createConfig(name)) {
            ChatUtil.send("Created config '$name'")
         } else {
            ChatUtil.send("Config '$name' already exists")
         }

         return 1
      }

      private fun onLoadCommand(it: CommandContext<*>): Int {
         val name = StringArgumentType.getString(it, "name")
         if (CosmeticBlocks.INSTANCE.loadConfigByName(name)) {
            ChatUtil.send("Loaded config '$name'")
         } else {
            ChatUtil.send("Config '$name' not found")
         }

         return 1
      }

      private fun onDeleteCommand(it: CommandContext<*>): Int {
         val name = StringArgumentType.getString(it, "name")
         if (CosmeticBlocks.INSTANCE.deleteConfig(name)) {
            ChatUtil.send("Deleted config '$name'")
         } else {
            ChatUtil.send("Cannot delete '$name'")
         }

         return 1
      }

      private fun onRoomOverrideListCommand(it: CommandContext<*>): Int {
         val map = RoomOverrideManager.listOverrides()
         if (map.isEmpty()) {
            ChatUtil.info("No room overrides set")
         } else {
            ChatUtil.info("Room overrides:")
            for ((room, config) in map) {
               ChatUtil.send("  §b" + room + "§f -> §a" + config)
            }
         }

         return 1
      }

      private fun onRoomOverrideSetCommand(it: CommandContext<*>): Int {
         val room = StringArgumentType.getString(it, "room")
         val config = StringArgumentType.getString(it, "config")
         if (!RoomOverrideManager.setOverride(room, config)) {
            ChatUtil.error("Config '$config' not found")
            return 0
         }

         ChatUtil.success("Room '" + room + "' now uses config '" + config + "'")
         return 1
      }

      private fun onRoomOverrideRemoveCommand(it: CommandContext<*>): Int {
         val room = StringArgumentType.getString(it, "room")
         if (!RoomOverrideManager.removeOverride(room)) {
            ChatUtil.error("No override for room '" + room + "'")
            return 0
         }

         ChatUtil.success("Removed override for room '" + room + "'")
         return 1
      }

      private fun onRoomOverrideClearCommand(it: CommandContext<*>): Int {
         val count = RoomOverrideManager.clearOverrides()
         ChatUtil.success("Cleared " + count + " room override(s)")
         return 1
      }

      private fun onRoomOverrideHereCommand(it: CommandContext<*>): Int {
         val room = RoomScanner.currentRoom?.data?.name
         if (room == null) {
            ChatUtil.error("No room detected")
            return 0
         }

         val config = StringArgumentType.getString(it, "config")
         if (!RoomOverrideManager.setOverride(room, config)) {
            ChatUtil.error("Config '$config' not found")
            return 0
         }

         ChatUtil.success("Current room '" + room + "' now uses config '" + config + "'")
         return 1
      }

      private fun onDiffCommand(it: CommandContext<*>): Int {
         val nameA = StringArgumentType.getString(it, "a")
         val nameB = StringArgumentType.getString(it, "b")
         CosmeticBlocks.INSTANCE.diffConfigs(nameA, nameB)
         return 1
      }

      private fun onConfigsCommand(it: CommandContext<*>): Int {
         val configs = CosmeticBlocks.INSTANCE.listConfigs()
         ChatUtil.send(
            "Configs: "
               + configs.joinToString(", ", "", "", -1, "...", null)
               + " (active: "
               + DungeonConfigTab.INSTANCE.activeConfig.value
               + ")"
         )
         return 1
      }

      private fun onPos1Command(it: CommandContext<*>): Int {
         val mc = Minecraft.getInstance()
         val hit = mc.hitResult
         if (hit is BlockHitResult && hit.type == HitResult.Type.BLOCK) {
            CosmeticBlocks.INSTANCE.setPos1(hit.blockPos)
         } else {
            ChatUtil.send("Look at a block or: cb pos1 <x> <y> <z>")
         }

         return 1
      }

      private fun onPos1CoordsCommand(it: CommandContext<*>): Int {
         val coordsArg = StringArgumentType.getString(it, "coords")
         val parts = Regex("\\s+").split(coordsArg.trim(), 0)
         if (parts.size >= 3) {
            val xx = parts[0].toIntOrNull()
            val yx = parts[1].toIntOrNull()
            val zx = parts[2].toIntOrNull()
            if (xx != null && yx != null && zx != null) {
               CosmeticBlocks.INSTANCE.setPos1(BlockPos(xx, yx, zx))
            } else {
               ChatUtil.send("Invalid coordinates")
            }
         } else {
            ChatUtil.send("Usage: cb pos1 <x> <y> <z>")
         }

         return 1
      }

      private fun onPos2Command(it: CommandContext<*>): Int {
         val mc = Minecraft.getInstance()
         val hit = mc.hitResult
         if (hit is BlockHitResult && hit.type == HitResult.Type.BLOCK) {
            CosmeticBlocks.INSTANCE.setPos2(hit.blockPos)
         } else {
            ChatUtil.send("Look at a block or: cb pos2 <x> <y> <z>")
         }

         return 1
      }

      private fun onPos2CoordsCommand(it: CommandContext<*>): Int {
         val coordsArg = StringArgumentType.getString(it, "coords")
         val parts = Regex("\\s+").split(coordsArg.trim(), 0)
         if (parts.size >= 3) {
            val xx = parts[0].toIntOrNull()
            val yx = parts[1].toIntOrNull()
            val zx = parts[2].toIntOrNull()
            if (xx != null && yx != null && zx != null) {
               CosmeticBlocks.INSTANCE.setPos2(BlockPos(xx, yx, zx))
            } else {
               ChatUtil.send("Invalid coordinates")
            }
         } else {
            ChatUtil.send("Usage: cb pos2 <x> <y> <z>")
         }

         return 1
      }

      private fun onSelectionInfoCommand(it: CommandContext<*>): Int {
         ChatUtil.send(CosmeticBlocks.INSTANCE.getSelectionInfo())
         return 1
      }

      private fun onClearSelectionCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.clearSelection()
         return 1
      }

      private fun onFillSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> =
         INSTANCE.suggestBlockIdsGreedy(builder).buildFuture()

      private fun onFillCommand(it: CommandContext<*>): Int {
         val blocks = StringArgumentType.getString(it, "blocks")
         ChatUtil.send(CosmeticBlocks.INSTANCE.fillRegion(blocks))
         return 1
      }

      private fun onReplaceSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> =
         INSTANCE.suggestBlockIdsGreedy(builder).buildFuture()

      private fun onReplaceCommand(it: CommandContext<*>): Int {
         val argsArg = StringArgumentType.getString(it, "args")
         val args = Regex("\\s+").split(argsArg.trim(), 0)
         if (args.size >= 2) {
            val fromSpec = args[0]
            val toSpecs = args.drop(1).joinToString("", "", "", -1, "...", null)
            ChatUtil.send(CosmeticBlocks.INSTANCE.replaceRegion(fromSpec, toSpecs))
         } else {
            ChatUtil.send("Usage: cb replace <from[,from...]> <to[,to...]>")
         }

         return 1
      }

      private fun onReplaceNearCommand(it: CommandContext<*>): Int {
         val argsArg = StringArgumentType.getString(it, "args")
         val args = Regex("\\s+").split(argsArg.trim(), 0)
         if (args.size >= 3) {
            val size = args[0].toIntOrNull()
            if (size == null) {
               ChatUtil.send("Usage: cb replacenear <size> <from[,from...]> <to[,to...]>")
               return 1
            }

            val fromSpec = args[1]
            val toSpecs = args.drop(2).joinToString("", "", "", -1, "...", null)
            ChatUtil.send(CosmeticBlocks.INSTANCE.replacenearRegion(size, fromSpec, toSpecs))
         } else {
            ChatUtil.send("Usage: cb replacenear <size> <from[,from...]> <to[,to...]>")
         }

         return 1
      }

      private fun onUndoCommand(it: CommandContext<*>): Int {
         ChatUtil.send(CosmeticBlocks.INSTANCE.undoLastOperation())
         return 1
      }

      private fun onRedoCommand(it: CommandContext<*>): Int {
         ChatUtil.send(CosmeticBlocks.INSTANCE.redoLastOperation())
         return 1
      }

      private fun onHistoryCommand(it: CommandContext<*>): Int {
         val (depth, labels) = CosmeticBlocks.INSTANCE.historyInfo()
         if (labels.isEmpty()) {
            ChatUtil.info("History is empty")
            return 1
         }

         ChatUtil.info("History (at $depth):")
         var index = labels.size
         for (label in labels) {
            val marker = if (index == depth) "§a> " else "§7  "
            ChatUtil.send(marker + "§f" + index + ". " + label)
            index--
         }

         return 1
      }

      private fun onHistoryGuiCommand(it: CommandContext<*>): Int {
         INSTANCE.open { HistoryScreen() }
         return 1
      }

      private fun onHistoryJumpCommand(it: CommandContext<*>): Int {
         val depth = IntegerArgumentType.getInteger(it, "depth")
         ChatUtil.send(CosmeticBlocks.INSTANCE.jumpHistoryTo(depth))
         return 1
      }

      private fun onDebugCommand(it: CommandContext<*>): Int {
         DebugStats.enabled = !DebugStats.enabled
         ChatUtil.send(if (DebugStats.enabled) "Debug overlay §aON" else "Debug overlay §cOFF")
         return 1
      }

      private fun onFavoritesCommand(it: CommandContext<*>): Int {
         val favs = CosmeticBlocks.INSTANCE.getFavorites()
         if (favs.isEmpty()) {
            ChatUtil.send("No favorites yet. Use /cb fav add <block>")
         } else {
            ChatUtil.send("Favorites: " + favs.joinToString(", ", "", "", -1, "...", ::stripMinecraftPrefix))
         }

         return 1
      }

      private fun onFavoriteAddSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> =
         INSTANCE.suggestBlockIds(builder).buildFuture()

      private fun onFavoriteAddCommand(it: CommandContext<*>): Int {
         val block = StringArgumentType.getString(it, "block")
         if (CosmeticBlocks.INSTANCE.addFavorite(block)) {
            ChatUtil.send("Added " + block.removePrefix("minecraft:") + " to favorites")
         } else {
            ChatUtil.send("Already in favorites or invalid block")
         }

         return 1
      }

      private fun onFavoriteAddSelectedCommand(it: CommandContext<*>): Int {
         if (CosmeticBlocks.INSTANCE.addFavorite(CosmeticBlocks.INSTANCE.getSelectedBlock())) {
            ChatUtil.send("Added " + CosmeticBlocks.INSTANCE.getSelectedBlock().removePrefix("minecraft:") + " to favorites")
         } else {
            ChatUtil.send("Already in favorites")
         }

         return 1
      }

      private fun onFavoriteRemoveCommand(it: CommandContext<*>): Int {
         val block = StringArgumentType.getString(it, "block")
         if (CosmeticBlocks.INSTANCE.removeFavorite(block)) {
            ChatUtil.send("Removed " + block.removePrefix("minecraft:") + " from favorites")
         } else {
            ChatUtil.send("Not in favorites")
         }

         return 1
      }

      private fun onFavoriteListCommand(it: CommandContext<*>): Int {
         val favs = CosmeticBlocks.INSTANCE.getFavorites()
         if (favs.isEmpty()) {
            ChatUtil.send("No favorites")
         } else {
            ChatUtil.send("Favorites: " + favs.joinToString(", ", "", "", -1, "...", ::stripMinecraftPrefixAlt))
         }

         return 1
      }

      private fun onBackupCommand(it: CommandContext<*>): Int {
         ChatUtil.send(ConfigBackup.backup())
         return 1
      }

      private fun onUpdateIconsCommand(it: CommandContext<*>): Int {
         BlockIconStore.updateIcons()
         return 1
      }

      private fun onValidateIconsCommand(it: CommandContext<*>): Int {
         BlockIconStore.validateAndRepair()
         return 1
      }

      private fun onPropsCommand(it: CommandContext<*>): Int {
         CosmeticBlocks.INSTANCE.listBlockProperties()
         return 1
      }

      private fun onStatePropertySuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
         val brush = CosmeticBlocks.INSTANCE.getSelectedBlock()
         val baseId = if (brush.contains('[')) brush.substringBefore('[') else brush
         val loc = Identifier.tryParse(baseId)
         val block = if (loc != null) BuiltInRegistries.BLOCK.getOptional(loc).orElse(null) else null

         if (block != null) {
            val input = builder.remaining.lowercase(Locale.ROOT)
            for (prop in block.stateDefinition.properties) {
               if (prop.name.lowercase(Locale.ROOT).startsWith(input)) {
                  builder.suggest(prop.name)
               }
            }
         }

         return builder.buildFuture()
      }

      private fun onStateValueSuggestions(context: CommandContext<*>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
         val propName = StringArgumentType.getString(context, "property")
         val brush = CosmeticBlocks.INSTANCE.getSelectedBlock()
         val baseId = if (brush.contains('[')) brush.substringBefore('[') else brush
         val loc = Identifier.tryParse(baseId)
         val block = if (loc != null) BuiltInRegistries.BLOCK.getOptional(loc).orElse(null) else null

         if (block != null) {
            val prop = block.stateDefinition.getProperty(propName)
            if (prop != null) {
               val input = builder.remaining.lowercase(Locale.ROOT)
               for (possibleValue in prop.possibleValues) {
                  val valueName = valueName(prop, possibleValue as Any)
                  if (valueName.lowercase(Locale.ROOT).startsWith(input)) {
                     builder.suggest(valueName)
                  }
               }
            }
         }

         return builder.buildFuture()
      }

      private fun onStateCommand(it: CommandContext<*>): Int {
         val property = StringArgumentType.getString(it, "property")
         val value = StringArgumentType.getString(it, "value")
         CosmeticBlocks.INSTANCE.setBlockProperty(property, value)
         return 1
      }

      private fun onDefaultCommand(it: CommandContext<*>): Int {
         INSTANCE.openGui()
         return 1
      }

      private val COMMAND_ALIASES = arrayOf("cosmeticblocks", "cb", "cosmeticb", "cosm", "cosblocks")

      private val MIGRATE_FLAGS = arrayOf("-m", "-mini", "-gzip", "-dict", "-palette", "-rle", "--settings")

      private fun onRegisterCommands(dispatcher: CommandDispatcher<FabricClientCommandSource>, ignored: CommandBuildContext) {
         for (alias in COMMAND_ALIASES) {
            dispatcher.register(buildCommand(alias))
         }
      }

      private fun buildCommand(name: String) =
            ClientCommands.literal(name)
               .then(ClientCommands.literal("gui").executes(::onGuiCommand))
               .then(ClientCommands.literal("profiles").executes(::onProfilesCommand))
               .then(ClientCommands.literal("keybinds").executes(::onKeybindsCommand))
               .then(ClientCommands.literal("hud").executes(::onHudCommand))
               .then(ClientCommands.literal("em").executes(::onToggleEditModeCommand))
               .then(ClientCommands.literal("fillmode").executes(::onToggleFillModeCommand))
               .then(ClientCommands.literal("freecam").executes(::onFreecamCommand))
               .then(
                  ClientCommands.literal("block")
                     .then(
                        ClientCommands.argument("block", StringArgumentType.greedyString())
                           .suggests(::onBlockSuggestions)
                           .executes(::onBlockCommand)
                     )
               )
               .then(
                  ClientCommands.literal("remove")
                     .executes(::onRemoveCommand)
                     .then(
                        ClientCommands.argument("block", StringArgumentType.string())
                           .suggests(::onRemoveSuggestions)
                           .executes(::onRemoveBlockCommand)
                     )
               )
               .then(ClientCommands.literal("clear").executes(::onClearCommand))
               .then(ClientCommands.literal("list").executes(::onListCommand))
               .then(ClientCommands.literal("save").executes(::onSaveCommand))
                .then(ClientCommands.literal("reload").executes(::onReloadCommand))
                .then(
                    ClientCommands.literal("migrate")
                      .then(
                         ClientCommands.argument("name", StringArgumentType.string())
                            .suggests(::onMigrateSuggestions)
                            .executes(::onMigrateCommand)
                            .then(
                               ClientCommands.argument("flags", StringArgumentType.greedyString())
                                  .suggests(::onMigrateFlagSuggestions)
                                  .executes(::onMigrateFlagsCommand)
                            )
                      )
                )
               .then(ClientCommands.literal("create").then(ClientCommands.argument("name", StringArgumentType.string()).executes(::onCreateCommand)))
               .then(ClientCommands.literal("load").then(ClientCommands.argument("name", StringArgumentType.string()).executes(::onLoadCommand)))
               .then(ClientCommands.literal("delete").then(ClientCommands.argument("name", StringArgumentType.string()).executes(::onDeleteCommand)))
               .then(ClientCommands.literal("configs").executes(::onConfigsCommand))
               .then(
                  ClientCommands.literal("roomoverride")
                     .executes(::onRoomOverrideListCommand)
                     .then(ClientCommands.literal("list").executes(::onRoomOverrideListCommand))
                     .then(ClientCommands.literal("clear").executes(::onRoomOverrideClearCommand))
                     .then(
                        ClientCommands.literal("here")
                           .then(
                              ClientCommands.argument("config", StringArgumentType.string())
                                 .suggests(::onConfigSuggestions)
                                 .executes(::onRoomOverrideHereCommand)
                           )
                     )
                     .then(
                        ClientCommands.literal("set")
                           .then(
                              ClientCommands.argument("room", StringArgumentType.string())
                                 .then(
                                    ClientCommands.argument("config", StringArgumentType.string())
                                       .suggests(::onConfigSuggestions)
                                       .executes(::onRoomOverrideSetCommand)
                                 )
                           )
                     )
                     .then(
                        ClientCommands.literal("remove")
                           .then(
                              ClientCommands.argument("room", StringArgumentType.string())
                                 .executes(::onRoomOverrideRemoveCommand)
                           )
                     )
               )
               .then(
                  ClientCommands.literal("diff")
                     .then(
                        ClientCommands.argument("a", StringArgumentType.string())
                           .suggests(::onMigrateSuggestions)
                           .then(
                              ClientCommands.argument("b", StringArgumentType.string())
                                 .suggests(::onMigrateSuggestions)
                                 .executes(::onDiffCommand)
                           )
                     )
               )
               .then(
                  ClientCommands.literal("pos1")
                     .executes(::onPos1Command)
                     .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(::onPos1CoordsCommand))
               )
               .then(
                  ClientCommands.literal("pos2")
                     .executes(::onPos2Command)
                     .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(::onPos2CoordsCommand))
               )
               .then(ClientCommands.literal("sel").executes(::onSelectionInfoCommand))
               .then(ClientCommands.literal("clearselection").executes(::onClearSelectionCommand))
               .then(
                  ClientCommands.literal("fill")
                     .then(
                        ClientCommands.argument("blocks", StringArgumentType.greedyString())
                           .suggests(::onFillSuggestions)
                           .executes(::onFillCommand)
                     )
               )
               .then(
                  ClientCommands.literal("replace")
                     .then(
                        ClientCommands.argument("args", StringArgumentType.greedyString())
                           .suggests(::onReplaceSuggestions)
                           .executes(::onReplaceCommand)
                     )
               )
               .then(
                  ClientCommands.literal("replacenear")
                     .then(
                        ClientCommands.argument("args", StringArgumentType.greedyString())
                           .suggests(::onReplaceSuggestions)
                           .executes(::onReplaceNearCommand)
                     )
               )
               .then(ClientCommands.literal("undo").executes(::onUndoCommand))
               .then(ClientCommands.literal("redo").executes(::onRedoCommand))
               .then(
                  ClientCommands.literal("history")
                     .executes(::onHistoryCommand)
                     .then(ClientCommands.literal("gui").executes(::onHistoryGuiCommand))
                     .then(
                        ClientCommands.argument("depth", IntegerArgumentType.integer(0))
                           .executes(::onHistoryJumpCommand)
                     )
               )
               .then(ClientCommands.literal("debug").executes(::onDebugCommand))
               .then(
                  ClientCommands.literal("fav")
                     .executes(::onFavoritesCommand)
                     .then(
                        ClientCommands.literal("add")
                           .then(
                              ClientCommands.argument("block", StringArgumentType.string())
                                 .suggests(::onFavoriteAddSuggestions)
                                 .executes(::onFavoriteAddCommand)
                           )
                           .executes(::onFavoriteAddSelectedCommand)
                     )
                     .then(
                        ClientCommands.literal("remove")
                           .then(ClientCommands.argument("block", StringArgumentType.string()).executes(::onFavoriteRemoveCommand))
                     )
                     .then(ClientCommands.literal("list").executes(::onFavoriteListCommand))
               )
               .then(ClientCommands.literal("backup").executes(::onBackupCommand))
               .then(ClientCommands.literal("updateicons").executes(::onUpdateIconsCommand))
               .then(ClientCommands.literal("validateicons").executes(::onValidateIconsCommand))
               .then(ClientCommands.literal("props").executes(::onPropsCommand))
               .then(
                  ClientCommands.literal("state")
                     .then(
                        ClientCommands.argument("property", StringArgumentType.string())
                           .suggests(::onStatePropertySuggestions)
                           .then(
                              ClientCommands.argument("value", StringArgumentType.string())
                                 .suggests(::onStateValueSuggestions)
                                 .executes(::onStateCommand)
                           )
                     )
               )
               .executes(::onDefaultCommand)
   }
}
