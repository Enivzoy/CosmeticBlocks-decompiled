package me.enivzoy.cosmeticblocksreborn.command;

import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab;
import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import me.enivzoy.cosmeticblocksreborn.cosmetic.Freecam;
import me.enivzoy.cosmeticblocksreborn.gui.ClickGuiScreen;
import me.enivzoy.cosmeticblocksreborn.gui.screens.HudEditorScreen;
import me.enivzoy.cosmeticblocksreborn.gui.screens.KeybindEditorScreen;
import me.enivzoy.cosmeticblocksreborn.gui.screens.ProfileScreen;
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil;
import me.enivzoy.cosmeticblocksreborn.utils.ConfigBackup;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public final class CbCommand {
   @NotNull
   public static final CbCommand INSTANCE = new CbCommand();

   private CbCommand() {
   }

   private final void open(Function0<? extends Screen> screen) {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      mc.execute(() -> CbCommand.openScreen(mc, screen));
   }

   private final void openGui() {
      this.open(ClickGuiScreen::new);
   }

   public final void register() {
      ClientCommandRegistrationCallback.EVENT.register(CbCommand::onRegisterCommands);
   }

   private final SuggestionsBuilder suggestBlockIds(SuggestionsBuilder builder) {
      String input = builder.getRemaining();
      Intrinsics.checkNotNull(input);
      String inputLower = input.toLowerCase(Locale.ROOT);
      if (StringsKt.contains(input, '[', false)) {
         int bracketIdx = StringsKt.indexOf(input, '[', 0, false);
         String blockIdRaw = input.substring(0, bracketIdx);
         String blockId = StringsKt.contains(blockIdRaw, ':', false) ? blockIdRaw : "minecraft:" + blockIdRaw;
         Identifier loc = Identifier.tryParse(blockId);
         Block block;
         if (loc != null) {
            Identifier parsedId = loc;
            block = BuiltInRegistries.BLOCK.getOptional(parsedId).orElse(null);
         } else {
            block = null;
         }

         if (block != null && !StringsKt.endsWith(input, "]", false)) {
            String fullBlockId = loc.toString();
            String cleanInput = StringsKt.removeSuffix(input, "]");
            int lastCommaIdx = StringsKt.lastIndexOf(cleanInput, ',', 0, false);
            String prefix;
            if (lastCommaIdx > bracketIdx) {
               prefix = cleanInput.substring(0, lastCommaIdx + 1);
            } else {
               prefix = fullBlockId + "[";
            }

            String lastEntry;
            if (lastCommaIdx > bracketIdx) {
               lastEntry = cleanInput.substring(lastCommaIdx + 1);
            } else {
               lastEntry = cleanInput.substring(bracketIdx + 1);
            }

            String setPropsStr;
            if (lastCommaIdx > bracketIdx) {
               setPropsStr = cleanInput.substring(bracketIdx + 1, lastCommaIdx);
            } else {
               setPropsStr = "";
            }

            Set setProps;
            if (setPropsStr.length() == 0) {
               setProps = SetsKt.emptySet();
            } else {
               String[] separators = new String[]{","};
               Iterable propSplit = StringsKt.split(setPropsStr, separators, false, 0);
               Iterable propTokens = propSplit;
               var filteredProps = new ArrayList();

               for (Object propToken : propTokens) {
                  String token = (String)propToken;
                  if (StringsKt.contains(token, '=', false)) {
                     filteredProps.add(propToken);
                  }
               }

               var filtered = (Iterable & List)filteredProps;
               propTokens = filtered;
               filteredProps = new ArrayList(CollectionsKt.collectionSizeOrDefault(filtered, 10));

               for (Object propToken : propTokens) {
                  String token = (String)propToken;
                  Collection mappedProps = filteredProps;
                  mappedProps.add(StringsKt.substringBefore(token, '=', token));
               }

               setProps = CollectionsKt.toSet((Iterable & List)filteredProps);
            }

            if (StringsKt.contains(lastEntry, '=', false)) {
               String propName = StringsKt.substringBefore(lastEntry, '=', lastEntry);
               Property prop = block.getStateDefinition().getProperty(propName);
               if (prop != null) {
                  String propPrefix = prefix + propName + "=";

                  for (Object possibleValue : prop.getPossibleValues()) {
                     Intrinsics.checkNotNullExpressionValue(possibleValue, "next(...)");
                     String valueName = (String)prop.getName((Comparable)possibleValue);
                     builder.suggest(propPrefix + valueName + "]");
                     Collection properties = block.getStateDefinition().getProperties();
                     Intrinsics.checkNotNullExpressionValue(properties, "getProperties(...)");
                     var allProperties = properties;
                     int propertyCount;
                     if (((Collection)allProperties).isEmpty()) {
                        propertyCount = 0;
                     } else {
                        int count = 0;

                        for (Object propertyElement : allProperties) {
                           Property candidate = (Property)propertyElement;
                           if (!setProps.contains(candidate.getName()) && !Intrinsics.areEqual(candidate.getName(), propName)) {
                              if (++count < 0) {
                                 CollectionsKt.throwCountOverflow();
                              }
                           }
                        }

                        propertyCount = count;
                     }

                     int remaining = propertyCount;
                     if (remaining > 0) {
                        builder.suggest(propPrefix + valueName + ",");
                     }
                  }
               }
            } else {
               for (Object propertyElement : block.getStateDefinition().getProperties()) {
                  Intrinsics.checkNotNullExpressionValue(propertyElement, "next(...)");
                  Property prop = (Property)propertyElement;
                  if (!setProps.contains(prop.getName())) {
                     String propertyName = prop.getName();
                     String propNameLower = propertyName.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(propNameLower, "toLowerCase(...)");
                     String lastEntryLower = lastEntry.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(lastEntryLower, "toLowerCase(...)");
                     if (StringsKt.startsWith(propNameLower, lastEntryLower, false)) {
                        builder.suggest(prefix + prop.getName() + "=");
                     }
                  }
               }
            }
         }
      } else {
         for (Object blockKey : BuiltInRegistries.BLOCK.keySet()) {
            Intrinsics.checkNotNullExpressionValue(blockKey, "next(...)");
            Identifier key = (Identifier)blockKey;
            String idString = key.toString();
            Intrinsics.checkNotNullExpressionValue(idString, "toString(...)");
            String id = idString;
            if (!StringsKt.startsWith(id, inputLower, false)) {
               String path = key.getPath();
               Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
               if (!StringsKt.startsWith(path, inputLower, false)) {
                  continue;
               }
            }

            builder.suggest(id);
         }
      }

      return builder;
   }

   private final SuggestionsBuilder suggestBlockIdsGreedy(SuggestionsBuilder builder) {
      String remaining = builder.getRemaining();
      Intrinsics.checkNotNull(remaining);
      int lastSpace = StringsKt.lastIndexOf(remaining, ' ', 0, false);
      SuggestionsBuilder wordBuilder = lastSpace >= 0 ? builder.createOffset(builder.getStart() + lastSpace + 1) : builder;
      Intrinsics.checkNotNull(wordBuilder);
      return this.suggestBlockIds(wordBuilder);
   }

   private static final void openScreen(Minecraft mc, Function0 screen) {
      mc.setScreenAndShow((Screen)screen.invoke());
   }

   private static final CharSequence stripMinecraftPrefix(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final CharSequence stripMinecraftPrefixAlt(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final int onGuiCommand(CommandContext it) {
      INSTANCE.openGui();
      return 1;
   }

   private static final int onProfilesCommand(CommandContext it) {
      INSTANCE.open(ProfileScreen::new);
      return 1;
   }

   private static final int onKeybindsCommand(CommandContext it) {
      INSTANCE.open(KeybindEditorScreen::new);
      return 1;
   }

   private static final int onHudCommand(CommandContext it) {
      INSTANCE.open(HudEditorScreen::new);
      return 1;
   }

   private static final int onToggleEditModeCommand(CommandContext it) {
      CosmeticBlocks.INSTANCE.toggleEditMode();
      return 1;
   }

    private static final int onToggleFillModeCommand(CommandContext it) {
       CosmeticBlocks.INSTANCE.toggleFillMode();
       return 1;
    }

    private static final int onFreecamCommand(CommandContext it) {
       Freecam.INSTANCE.toggle();
       return 1;
    }

   private static final CompletableFuture onBlockSuggestions(CommandContext context, SuggestionsBuilder builder) {
      CbCommand command = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return command.suggestBlockIds(builder).buildFuture();
   }

   private static final int onBlockCommand(CommandContext it) {
      String blockId = StringArgumentType.getString(it, "block");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(blockId);
      blocks.setBlock(blockId);
      return 1;
   }

   private static final int onRemoveCommand(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(null));
      return 1;
   }

   private static final CompletableFuture onRemoveSuggestions(CommandContext context, SuggestionsBuilder builder) {
      CbCommand command = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return command.suggestBlockIds(builder).buildFuture();
   }

   private static final int onRemoveBlockCommand(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(block));
      return 1;
   }

   private static final int onClearCommand(CommandContext it) {
      int count = CosmeticBlocks.INSTANCE.clearDimension();
      ChatUtil.INSTANCE.send("Cleared " + count + " replacements in current dimension");
      return 1;
   }

   private static final int onListCommand(CommandContext it) {
      List list = CosmeticBlocks.INSTANCE.listReplacements();
      if (list.isEmpty()) {
         ChatUtil.INSTANCE.send("No replacements");
      } else {
         ChatUtil.INSTANCE.send("--- Replacements (" + list.size() + ") ---");
         Iterable recentReplacements = CollectionsKt.take(list, 20);

         for (Object replacement : recentReplacements) {
            String item = (String)replacement;
            ChatUtil.INSTANCE.send("  " + item);
         }

         if (list.size() > 20) {
            ChatUtil.INSTANCE.send("  ... and " + (list.size() - 20) + " more");
         }
      }

      return 1;
   }

   private static final int onSaveCommand(CommandContext it) {
      CosmeticBlocks.INSTANCE.save();
      ChatUtil.INSTANCE.send("Data saved");
      return 1;
   }

   private static final int onReloadCommand(CommandContext it) {
      CosmeticBlocks.INSTANCE.load();
      ChatUtil.INSTANCE.send("Data reloaded (" + CosmeticBlocks.INSTANCE.getReplacementCount() + " in current dimension)");
      return 1;
   }

   private static final int onCreateCommand(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (blocks.createConfig(name)) {
         ChatUtil.INSTANCE.send("Created config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Config '" + name + "' already exists");
      }

      return 1;
   }

   private static final int onLoadCommand(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (blocks.loadConfigByName(name)) {
         ChatUtil.INSTANCE.send("Loaded config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Config '" + name + "' not found");
      }

      return 1;
   }

   private static final int onDeleteCommand(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (blocks.deleteConfig(name)) {
         ChatUtil.INSTANCE.send("Deleted config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Cannot delete '" + name + "'");
      }

      return 1;
   }

   private static final int onConfigsCommand(CommandContext it) {
      List configs = CosmeticBlocks.INSTANCE.listConfigs();
      ChatUtil.INSTANCE
         .send(
            "Configs: "
               + CollectionsKt.joinToString(configs, ", ", "", "", -1, "...", null)
               + " (active: "
               + DungeonConfigTab.INSTANCE.getActiveConfig().getValue()
               + ")"
         );
      return 1;
   }

   private static final int onPos1Command(CommandContext it) {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      HitResult hit = mc.hitResult;
      if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == Type.BLOCK) {
         CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
         BlockPos pos = ((BlockHitResult)hit).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
         blocks.setPos1(pos);
      } else {
         ChatUtil.INSTANCE.send("Look at a block or: cb pos1 <x> <y> <z>");
      }

      return 1;
   }

   private static final int onPos1CoordsCommand(CommandContext it) {
      String coordsArg = StringArgumentType.getString(it, "coords");
      Intrinsics.checkNotNullExpressionValue(coordsArg, "getString(...)");
      CharSequence x = StringsKt.trim(coordsArg).toString();
      Regex y = new Regex("\\s+");
      byte z = 0;
      List parts = y.split(x, z);
      if (parts.size() >= 3) {
         Integer xx = StringsKt.toIntOrNull((String)parts.get(0));
         Integer yx = StringsKt.toIntOrNull((String)parts.get(1));
         Integer zx = StringsKt.toIntOrNull((String)parts.get(2));
         if (xx != null && yx != null && zx != null) {
            CosmeticBlocks.INSTANCE.setPos1(new BlockPos(xx, yx, zx));
         } else {
            ChatUtil.INSTANCE.send("Invalid coordinates");
         }
      } else {
         ChatUtil.INSTANCE.send("Usage: cb pos1 <x> <y> <z>");
      }

      return 1;
   }

   private static final int onPos2Command(CommandContext it) {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      HitResult hit = mc.hitResult;
      if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == Type.BLOCK) {
         CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
         BlockPos pos = ((BlockHitResult)hit).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
         blocks.setPos2(pos);
      } else {
         ChatUtil.INSTANCE.send("Look at a block or: cb pos2 <x> <y> <z>");
      }

      return 1;
   }

   private static final int onPos2CoordsCommand(CommandContext it) {
      String coordsArg = StringArgumentType.getString(it, "coords");
      Intrinsics.checkNotNullExpressionValue(coordsArg, "getString(...)");
      CharSequence x = StringsKt.trim(coordsArg).toString();
      Regex y = new Regex("\\s+");
      byte z = 0;
      List parts = y.split(x, z);
      if (parts.size() >= 3) {
         Integer xx = StringsKt.toIntOrNull((String)parts.get(0));
         Integer yx = StringsKt.toIntOrNull((String)parts.get(1));
         Integer zx = StringsKt.toIntOrNull((String)parts.get(2));
         if (xx != null && yx != null && zx != null) {
            CosmeticBlocks.INSTANCE.setPos2(new BlockPos(xx, yx, zx));
         } else {
            ChatUtil.INSTANCE.send("Invalid coordinates");
         }
      } else {
         ChatUtil.INSTANCE.send("Usage: cb pos2 <x> <y> <z>");
      }

      return 1;
   }

   private static final int onSelectionInfoCommand(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.getSelectionInfo());
      return 1;
   }

   private static final int onClearSelectionCommand(CommandContext it) {
      CosmeticBlocks.INSTANCE.clearSelection();
      return 1;
   }

   private static final CompletableFuture onFillSuggestions(CommandContext context, SuggestionsBuilder builder) {
      CbCommand command = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return command.suggestBlockIdsGreedy(builder).buildFuture();
   }

   private static final int onFillCommand(CommandContext it) {
      String blocks = StringArgumentType.getString(it, "blocks");
      ChatUtil chat = ChatUtil.INSTANCE;
      CosmeticBlocks cosmeticBlocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(blocks);
      chat.send(cosmeticBlocks.fillRegion(blocks));
      return 1;
   }

   private static final CompletableFuture onReplaceSuggestions(CommandContext context, SuggestionsBuilder builder) {
      CbCommand command = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return command.suggestBlockIdsGreedy(builder).buildFuture();
   }

   private static final int onReplaceCommand(CommandContext it) {
      String argsArg = StringArgumentType.getString(it, "args");
      Intrinsics.checkNotNullExpressionValue(argsArg, "getString(...)");
      String fromBlock = StringsKt.trim(argsArg).toString();
      Regex toSpecs = new Regex("\\s+");
      byte limit = 0;
      List args = toSpecs.split(fromBlock, limit);
      if (args.size() >= 2) {
         fromBlock = (String)args.get(0);
         String toSpecsx = CollectionsKt.joinToString(CollectionsKt.drop(args, 1), " ", "", "", -1, "...", null);
         ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.replaceRegion(fromBlock, toSpecsx));
      } else {
         ChatUtil.INSTANCE.send("Usage: cb replace <from> <to> [to:weight ...]");
      }

      return 1;
   }

   private static final int onUndoCommand(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.undoLastOperation());
      return 1;
   }

   private static final int onFavoritesCommand(CommandContext it) {
      List favs = CosmeticBlocks.INSTANCE.getFavorites();
      if (favs.isEmpty()) {
         ChatUtil.INSTANCE.send("No favorites yet. Use /cb fav add <block>");
      } else {
         ChatUtil.INSTANCE
            .send("Favorites: " + CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", CbCommand::stripMinecraftPrefix));
      }

      return 1;
   }

   private static final CompletableFuture onFavoriteAddSuggestions(CommandContext context, SuggestionsBuilder builder) {
      CbCommand command = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return command.suggestBlockIds(builder).buildFuture();
   }

   private static final int onFavoriteAddCommand(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(block);
      if (blocks.addFavorite(block)) {
         ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix(block, "minecraft:") + " to favorites");
      } else {
         ChatUtil.INSTANCE.send("Already in favorites or invalid block");
      }

      return 1;
   }

   private static final int onFavoriteAddSelectedCommand(CommandContext it) {
      if (CosmeticBlocks.INSTANCE.addFavorite(CosmeticBlocks.INSTANCE.getSelectedBlock())) {
         ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix(CosmeticBlocks.INSTANCE.getSelectedBlock(), "minecraft:") + " to favorites");
      } else {
         ChatUtil.INSTANCE.send("Already in favorites");
      }

      return 1;
   }

   private static final int onFavoriteRemoveCommand(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(block);
      if (blocks.removeFavorite(block)) {
         ChatUtil.INSTANCE.send("Removed " + StringsKt.removePrefix(block, "minecraft:") + " from favorites");
      } else {
         ChatUtil.INSTANCE.send("Not in favorites");
      }

      return 1;
   }

   private static final int onFavoriteListCommand(CommandContext it) {
      List favs = CosmeticBlocks.INSTANCE.getFavorites();
      if (favs.isEmpty()) {
         ChatUtil.INSTANCE.send("No favorites");
      } else {
         ChatUtil.INSTANCE
            .send("Favorites: " + CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", CbCommand::stripMinecraftPrefixAlt));
      }

      return 1;
   }

   private static final int onBackupCommand(CommandContext it) {
      String result = ConfigBackup.INSTANCE.backup();
      ChatUtil.INSTANCE.send(result);
      return 1;
   }

   private static final int onPropsCommand(CommandContext it) {
      CosmeticBlocks.INSTANCE.listBlockProperties();
      return 1;
   }

   private static final CompletableFuture onStatePropertySuggestions(CommandContext context, SuggestionsBuilder builder) {
      String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
      String baseId = StringsKt.contains(brush, '[', false) ? StringsKt.substringBefore(brush, '[', brush) : brush;
      Identifier loc = Identifier.tryParse(baseId);
      Block resolvedBlock;
      if (loc != null) {
         Identifier parsedId = loc;
         resolvedBlock = BuiltInRegistries.BLOCK.getOptional(parsedId).orElse(null);
      } else {
         resolvedBlock = null;
      }

      Block block = resolvedBlock;
      if (block != null) {
         String remaining = builder.getRemaining();
         Intrinsics.checkNotNullExpressionValue(remaining, "getRemaining(...)");
         String remainingLower = remaining.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(remainingLower, "toLowerCase(...)");
         String input = remainingLower;

         for (Object propertyElement : block.getStateDefinition().getProperties()) {
            Intrinsics.checkNotNullExpressionValue(propertyElement, "next(...)");
            Property prop = (Property)propertyElement;
            String propName = prop.getName();
            Intrinsics.checkNotNullExpressionValue(propName, "getName(...)");
            String propNameLower = propName.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(propNameLower, "toLowerCase(...)");
            if (StringsKt.startsWith(propNameLower, input, false)) {
               builder.suggest(prop.getName());
            }
         }
      }

      return builder.buildFuture();
   }

   private static final CompletableFuture onStateValueSuggestions(CommandContext context, SuggestionsBuilder builder) {
      String propName = StringArgumentType.getString(context, "property");
      String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
      String baseId = StringsKt.contains(brush, '[', false) ? StringsKt.substringBefore(brush, '[', brush) : brush;
      Identifier loc = Identifier.tryParse(baseId);
      Block resolvedBlock;
      if (loc != null) {
         Identifier parsedId = loc;
         resolvedBlock = BuiltInRegistries.BLOCK.getOptional(parsedId).orElse(null);
      } else {
         resolvedBlock = null;
      }

      Block block = resolvedBlock;
      if (block != null) {
         Property prop = block.getStateDefinition().getProperty(propName);
         if (prop != null) {
            String remaining = builder.getRemaining();
            Intrinsics.checkNotNullExpressionValue(remaining, "getRemaining(...)");
            String remainingLower = remaining.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(remainingLower, "toLowerCase(...)");
            String input = remainingLower;

            for (Object possibleValue : prop.getPossibleValues()) {
               Intrinsics.checkNotNullExpressionValue(possibleValue, "next(...)");
               Comparable value = (Comparable)possibleValue;
               String nameString = prop.getName(value);
               Intrinsics.checkNotNullExpressionValue(nameString, "getName(...)");
               String valueName = nameString;
               String valueNameLower = valueName.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(valueNameLower, "toLowerCase(...)");
               if (StringsKt.startsWith(valueNameLower, input, false)) {
                  builder.suggest(valueName);
               }
            }
         }
      }

      return builder.buildFuture();
   }

   private static final int onStateCommand(CommandContext it) {
      String property = StringArgumentType.getString(it, "property");
      String value = StringArgumentType.getString(it, "value");
      CosmeticBlocks blocks = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(property);
      Intrinsics.checkNotNull(value);
      blocks.setBlockProperty(property, value);
      return 1;
   }

   private static final int onDefaultCommand(CommandContext it) {
      INSTANCE.openGui();
      return 1;
   }

   private static final void onRegisterCommands(CommandDispatcher dispatcher, CommandBuildContext ignored) {
      Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
      Intrinsics.checkNotNullParameter(ignored, "<unused var>");
      dispatcher.register(
         (LiteralArgumentBuilder)ClientCommands.literal("cb")
            .then(ClientCommands.literal("gui").executes(CbCommand::onGuiCommand))
            .then(ClientCommands.literal("profiles").executes(CbCommand::onProfilesCommand))
            .then(ClientCommands.literal("keybinds").executes(CbCommand::onKeybindsCommand))
            .then(ClientCommands.literal("hud").executes(CbCommand::onHudCommand))
            .then(ClientCommands.literal("em").executes(CbCommand::onToggleEditModeCommand))
            .then(ClientCommands.literal("fillmode").executes(CbCommand::onToggleFillModeCommand))
            .then(ClientCommands.literal("freecam").executes(CbCommand::onFreecamCommand))
            .then(
               ClientCommands.literal("block")
                  .then(
                     ClientCommands.argument("block", StringArgumentType.greedyString())
                        .suggests(CbCommand::onBlockSuggestions)
                        .executes(CbCommand::onBlockCommand)
                  )
            )
            .then(
               ClientCommands.literal("remove")
                  .executes(CbCommand::onRemoveCommand)
                  .then(
                     ClientCommands.argument("block", StringArgumentType.string())
                        .suggests(CbCommand::onRemoveSuggestions)
                        .executes(CbCommand::onRemoveBlockCommand)
                  )
            )
            .then(ClientCommands.literal("clear").executes(CbCommand::onClearCommand))
            .then(ClientCommands.literal("list").executes(CbCommand::onListCommand))
            .then(ClientCommands.literal("save").executes(CbCommand::onSaveCommand))
            .then(ClientCommands.literal("reload").executes(CbCommand::onReloadCommand))
            .then(ClientCommands.literal("create").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::onCreateCommand)))
            .then(ClientCommands.literal("load").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::onLoadCommand)))
            .then(ClientCommands.literal("delete").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::onDeleteCommand)))
            .then(ClientCommands.literal("configs").executes(CbCommand::onConfigsCommand))
            .then(
               ClientCommands.literal("pos1")
                  .executes(CbCommand::onPos1Command)
                  .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(CbCommand::onPos1CoordsCommand))
            )
            .then(
               ClientCommands.literal("pos2")
                  .executes(CbCommand::onPos2Command)
                  .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(CbCommand::onPos2CoordsCommand))
            )
            .then(ClientCommands.literal("sel").executes(CbCommand::onSelectionInfoCommand))
            .then(ClientCommands.literal("clearselection").executes(CbCommand::onClearSelectionCommand))
            .then(
               ClientCommands.literal("fill")
                  .then(
                     ClientCommands.argument("blocks", StringArgumentType.greedyString())
                        .suggests(CbCommand::onFillSuggestions)
                        .executes(CbCommand::onFillCommand)
                  )
            )
            .then(
               ClientCommands.literal("replace")
                  .then(
                     ClientCommands.argument("args", StringArgumentType.greedyString())
                        .suggests(CbCommand::onReplaceSuggestions)
                        .executes(CbCommand::onReplaceCommand)
                  )
            )
            .then(ClientCommands.literal("undo").executes(CbCommand::onUndoCommand))
            .then(
               ClientCommands.literal("fav")
                  .executes(CbCommand::onFavoritesCommand)
                  .then(
                     ClientCommands.literal("add")
                        .then(
                           ClientCommands.argument("block", StringArgumentType.string())
                              .suggests(CbCommand::onFavoriteAddSuggestions)
                              .executes(CbCommand::onFavoriteAddCommand)
                        )
                        .executes(CbCommand::onFavoriteAddSelectedCommand)
                  )
                  .then(
                     ClientCommands.literal("remove")
                        .then(ClientCommands.argument("block", StringArgumentType.string()).executes(CbCommand::onFavoriteRemoveCommand))
                  )
                  .then(ClientCommands.literal("list").executes(CbCommand::onFavoriteListCommand))
            )
            .then(ClientCommands.literal("backup").executes(CbCommand::onBackupCommand))
            .then(ClientCommands.literal("props").executes(CbCommand::onPropsCommand))
            .then(
               ClientCommands.literal("state")
                  .then(
                     ClientCommands.argument("property", StringArgumentType.string())
                        .suggests(CbCommand::onStatePropertySuggestions)
                        .then(
                           ClientCommands.argument("value", StringArgumentType.string())
                              .suggests(CbCommand::onStateValueSuggestions)
                              .executes(CbCommand::onStateCommand)
                        )
                  )
            )
            .executes(CbCommand::onDefaultCommand)
      );
   }
}
