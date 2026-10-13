package com.cb.command;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.CosmeticBlocks;
import com.cb.gui.ClickGuiScreen;
import com.cb.gui.screens.HudEditorScreen;
import com.cb.gui.screens.KeybindEditorScreen;
import com.cb.gui.screens.ProfileScreen;
import com.cb.utils.ChatUtil;
import com.cb.utils.ConfigBackup;
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
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\b\u0010\t\u001a\u00020\u0005H\u0002J\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002¨\u0006\u000f",
   d2 = {
         "Lcom/cb/command/CbCommand;",
         "",
         "<init>",
         "()V",
         "open",
         "",
         "screen",
         "Lkotlin/Function0;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "openGui",
         "register",
         "suggestBlockIds",
         "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;",
         "builder",
         "suggestBlockIdsGreedy",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nCbCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CbCommand.kt\ncom/cb/command/CbCommand\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,415:1\n1#2:416\n777#3:417\n873#3,2:418\n1586#3:420\n1661#3,3:421\n1834#3,4:424\n1915#3,2:428\n*S KotlinDebug\n*F\n+ 1 CbCommand.kt\ncom/cb/command/CbCommand\n*L\n373#1:417\n373#1:418,2\n373#1:420\n373#1:421,3\n383#1:424,4\n113#1:428,2\n*E\n"
)
public final class CbCommand {
   @NotNull
   public static final CbCommand INSTANCE = new CbCommand();

   private CbCommand() {
   }

   private final void open(Function0<? extends Screen> screen) {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft mc = var10000;
      mc.execute(() -> CbCommand.open$lambda$0(mc, screen));
   }

   private final void openGui() {
      this.open(ClickGuiScreen::new);
   }

   public final void register() {
      ClientCommandRegistrationCallback.EVENT.register(CbCommand::register$lambda$0);
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
            Identifier it = loc;
            int var11/* $VF was: $i$a$-let-CbCommand$suggestBlockIds$block$1 */ = 0;
            block = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
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

            Set var64;
            if (setPropsStr.length() == 0) {
               var64 = SetsKt.emptySet();
            } else {
               String[] propName = new String[]{","};
               Iterable $this$filter$iv = StringsKt.split(setPropsStr, propName, false, 0);
               int $i$f$filter = 0;
               Iterable propPrefix = $this$filter$iv;
               var destination$iv$iv = new ArrayList();
               int $i$f$filterTo = 0;

               for (Object element$iv$iv : propPrefix) {
                  String it = (String)element$iv$iv;
                  int var24/* $VF was: $i$a$-filter-CbCommand$suggestBlockIds$setProps$1 */ = 0;
                  if (StringsKt.contains(it, '=', false)) {
                     destination$iv$iv.add(element$iv$iv);
                  }
               }

               var var37 = (Iterable & List)destination$iv$iv;
               $i$f$filter = 0;
               propPrefix = var37;
               destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var37, 10));
               $i$f$filterTo = 0;

               for (Object item$iv$iv : propPrefix) {
                  String var53 = (String)item$iv$iv;
                  Collection var30 = destination$iv$iv;
                  int var55/* $VF was: $i$a$-map-CbCommand$suggestBlockIds$setProps$2 */ = 0;
                  var30.add(StringsKt.substringBefore(var53, '=', var53));
               }

               var64 = CollectionsKt.toSet((Iterable & List)destination$iv$iv);
            }

            Set setProps = var64;
            if (StringsKt.contains(lastEntry, '=', false)) {
               String propName = StringsKt.substringBefore(lastEntry, '=', lastEntry);
               Property prop = block.getStateDefinition().getProperty(propName);
               if (prop != null) {
                  String propPrefix = prefix + propName + "=";

                  for (Object possibleValue : prop.getPossibleValues()) {
                     Intrinsics.checkNotNullExpressionValue(possibleValue, "next(...)");
                     String valueName = (String)prop.getName((Comparable)possibleValue);
                     builder.suggest(propPrefix + valueName + "]");
                     Collection var67 = block.getStateDefinition().getProperties();
                     Intrinsics.checkNotNullExpressionValue(var67, "getProperties(...)");
                     var $this$count$iv = var67;
                     int $i$f$count = 0;
                     int var68;
                     if (((Collection)$this$count$iv).isEmpty()) {
                        var68 = 0;
                     } else {
                        int count$iv = 0;

                        for (Object element$iv : $this$count$iv) {
                           Property it = (Property)element$iv;
                           int var29/* $VF was: $i$a$-count-CbCommand$suggestBlockIds$remaining$1 */ = 0;
                           if (!setProps.contains(it.getName()) && !Intrinsics.areEqual(it.getName(), propName)) {
                              if (++count$iv < 0) {
                                 CollectionsKt.throwCountOverflow();
                              }
                           }
                        }

                        var68 = count$iv;
                     }

                     int remaining = var68;
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
                     String var71 = propertyName.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var71, "toLowerCase(...)");
                     String var10001 = lastEntry.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
                     if (StringsKt.startsWith(var71, var10001, false)) {
                        builder.suggest(prefix + prop.getName() + "=");
                     }
                  }
               }
            }
         }
      } else {
         for (Object var72 : BuiltInRegistries.BLOCK.keySet()) {
            Intrinsics.checkNotNullExpressionValue(var72, "next(...)");
            Identifier key = (Identifier)var72;
            String var73 = key.toString();
            Intrinsics.checkNotNullExpressionValue(var73, "toString(...)");
            String id = var73;
            if (!StringsKt.startsWith(id, inputLower, false)) {
               String var74 = key.getPath();
               Intrinsics.checkNotNullExpressionValue(var74, "getPath(...)");
               if (!StringsKt.startsWith(var74, inputLower, false)) {
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

   private static final void open$lambda$0(Minecraft $mc, Function0 $screen) {
      $mc.setScreenAndShow((Screen)$screen.invoke());
   }

   private static final CharSequence register$lambda$0$30$0(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final CharSequence register$lambda$0$35$0(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final int register$lambda$0$0(CommandContext it) {
      INSTANCE.openGui();
      return 1;
   }

   private static final int register$lambda$0$1(CommandContext it) {
      INSTANCE.open(ProfileScreen::new);
      return 1;
   }

   private static final int register$lambda$0$2(CommandContext it) {
      INSTANCE.open(KeybindEditorScreen::new);
      return 1;
   }

   private static final int register$lambda$0$3(CommandContext it) {
      INSTANCE.open(HudEditorScreen::new);
      return 1;
   }

   private static final int register$lambda$0$4(CommandContext it) {
      CosmeticBlocks.INSTANCE.toggleEditMode();
      return 1;
   }

   private static final int register$lambda$0$5(CommandContext it) {
      CosmeticBlocks.INSTANCE.toggleFillMode();
      return 1;
   }

   private static final CompletableFuture register$lambda$0$6(CommandContext var0, SuggestionsBuilder builder) {
      CbCommand var10000 = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return var10000.suggestBlockIds(builder).buildFuture();
   }

   private static final int register$lambda$0$7(CommandContext it) {
      String blockId = StringArgumentType.getString(it, "block");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(blockId);
      var10000.setBlock(blockId);
      return 1;
   }

   private static final int register$lambda$0$8(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(null));
      return 1;
   }

   private static final CompletableFuture register$lambda$0$9(CommandContext var0, SuggestionsBuilder builder) {
      CbCommand var10000 = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return var10000.suggestBlockIds(builder).buildFuture();
   }

   private static final int register$lambda$0$10(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(block));
      return 1;
   }

   private static final int register$lambda$0$11(CommandContext it) {
      int count = CosmeticBlocks.INSTANCE.clearDimension();
      ChatUtil.INSTANCE.send("Cleared " + count + " replacements in current dimension");
      return 1;
   }

   private static final int register$lambda$0$12(CommandContext it) {
      List list = CosmeticBlocks.INSTANCE.listReplacements();
      if (list.isEmpty()) {
         ChatUtil.INSTANCE.send("No replacements");
      } else {
         ChatUtil.INSTANCE.send("--- Replacements (" + list.size() + ") ---");
         Iterable $this$forEach$iv = CollectionsKt.take(list, 20);
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$iv) {
            String itx = (String)element$iv;
            int var7/* $VF was: $i$a$-forEach-CbCommand$register$1$13$1 */ = 0;
            ChatUtil.INSTANCE.send("  " + itx);
         }

         if (list.size() > 20) {
            ChatUtil.INSTANCE.send("  ... and " + (list.size() - 20) + " more");
         }
      }

      return 1;
   }

   private static final int register$lambda$0$13(CommandContext it) {
      CosmeticBlocks.INSTANCE.save();
      ChatUtil.INSTANCE.send("Data saved");
      return 1;
   }

   private static final int register$lambda$0$14(CommandContext it) {
      CosmeticBlocks.INSTANCE.load();
      ChatUtil.INSTANCE.send("Data reloaded (" + CosmeticBlocks.INSTANCE.getReplacementCount() + " in current dimension)");
      return 1;
   }

   private static final int register$lambda$0$15(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (var10000.createConfig(name)) {
         ChatUtil.INSTANCE.send("Created config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Config '" + name + "' already exists");
      }

      return 1;
   }

   private static final int register$lambda$0$16(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (var10000.loadConfigByName(name)) {
         ChatUtil.INSTANCE.send("Loaded config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Config '" + name + "' not found");
      }

      return 1;
   }

   private static final int register$lambda$0$17(CommandContext it) {
      String name = StringArgumentType.getString(it, "name");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(name);
      if (var10000.deleteConfig(name)) {
         ChatUtil.INSTANCE.send("Deleted config '" + name + "'");
      } else {
         ChatUtil.INSTANCE.send("Cannot delete '" + name + "'");
      }

      return 1;
   }

   private static final int register$lambda$0$18(CommandContext it) {
      List configs = CosmeticBlocks.INSTANCE.listConfigs();
      ChatUtil.INSTANCE
         .send(
            "Configs: "
               + CollectionsKt.joinToString(configs, ", ", "", "", -1, "...", null)
               + " (active: "
               + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue()
               + ")"
         );
      return 1;
   }

   private static final int register$lambda$0$19(CommandContext it) {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft mc = var10000;
      HitResult hit = mc.hitResult;
      if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == Type.BLOCK) {
         CosmeticBlocks var3 = CosmeticBlocks.INSTANCE;
         BlockPos var10001 = ((BlockHitResult)hit).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(var10001, "getBlockPos(...)");
         var3.setPos1(var10001);
      } else {
         ChatUtil.INSTANCE.send("Look at a block or: cb pos1 <x> <y> <z>");
      }

      return 1;
   }

   private static final int register$lambda$0$20(CommandContext it) {
      String var10000 = StringArgumentType.getString(it, "coords");
      Intrinsics.checkNotNullExpressionValue(var10000, "getString(...)");
      CharSequence x = StringsKt.trim(var10000).toString();
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

   private static final int register$lambda$0$21(CommandContext it) {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft mc = var10000;
      HitResult hit = mc.hitResult;
      if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == Type.BLOCK) {
         CosmeticBlocks var3 = CosmeticBlocks.INSTANCE;
         BlockPos var10001 = ((BlockHitResult)hit).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(var10001, "getBlockPos(...)");
         var3.setPos2(var10001);
      } else {
         ChatUtil.INSTANCE.send("Look at a block or: cb pos2 <x> <y> <z>");
      }

      return 1;
   }

   private static final int register$lambda$0$22(CommandContext it) {
      String var10000 = StringArgumentType.getString(it, "coords");
      Intrinsics.checkNotNullExpressionValue(var10000, "getString(...)");
      CharSequence x = StringsKt.trim(var10000).toString();
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

   private static final int register$lambda$0$23(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.getSelectionInfo());
      return 1;
   }

   private static final int register$lambda$0$24(CommandContext it) {
      CosmeticBlocks.INSTANCE.clearSelection();
      return 1;
   }

   private static final CompletableFuture register$lambda$0$25(CommandContext var0, SuggestionsBuilder builder) {
      CbCommand var10000 = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return var10000.suggestBlockIdsGreedy(builder).buildFuture();
   }

   private static final int register$lambda$0$26(CommandContext it) {
      String blocks = StringArgumentType.getString(it, "blocks");
      ChatUtil var10000 = ChatUtil.INSTANCE;
      CosmeticBlocks var10001 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(blocks);
      var10000.send(var10001.fillRegion(blocks));
      return 1;
   }

   private static final CompletableFuture register$lambda$0$27(CommandContext var0, SuggestionsBuilder builder) {
      CbCommand var10000 = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return var10000.suggestBlockIdsGreedy(builder).buildFuture();
   }

   private static final int register$lambda$0$28(CommandContext it) {
      String var10000 = StringArgumentType.getString(it, "args");
      Intrinsics.checkNotNullExpressionValue(var10000, "getString(...)");
      String fromBlock = StringsKt.trim(var10000).toString();
      Regex toSpecs = new Regex("\\s+");
      byte var4 = 0;
      List args = toSpecs.split(fromBlock, var4);
      if (args.size() >= 2) {
         fromBlock = (String)args.get(0);
         String toSpecsx = CollectionsKt.joinToString(CollectionsKt.drop(args, 1), " ", "", "", -1, "...", null);
         ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.replaceRegion(fromBlock, toSpecsx));
      } else {
         ChatUtil.INSTANCE.send("Usage: cb replace <from> <to> [to:weight ...]");
      }

      return 1;
   }

   private static final int register$lambda$0$29(CommandContext it) {
      ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.undoLastOperation());
      return 1;
   }

   private static final int register$lambda$0$30(CommandContext it) {
      List favs = CosmeticBlocks.INSTANCE.getFavorites();
      if (favs.isEmpty()) {
         ChatUtil.INSTANCE.send("No favorites yet. Use /cb fav add <block>");
      } else {
         ChatUtil.INSTANCE
            .send("Favorites: " + CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", CbCommand::register$lambda$0$30$0));
      }

      return 1;
   }

   private static final CompletableFuture register$lambda$0$31(CommandContext var0, SuggestionsBuilder builder) {
      CbCommand var10000 = INSTANCE;
      Intrinsics.checkNotNull(builder);
      return var10000.suggestBlockIds(builder).buildFuture();
   }

   private static final int register$lambda$0$32(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(block);
      if (var10000.addFavorite(block)) {
         ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix(block, "minecraft:") + " to favorites");
      } else {
         ChatUtil.INSTANCE.send("Already in favorites or invalid block");
      }

      return 1;
   }

   private static final int register$lambda$0$33(CommandContext it) {
      if (CosmeticBlocks.INSTANCE.addFavorite(CosmeticBlocks.INSTANCE.getSelectedBlock())) {
         ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix(CosmeticBlocks.INSTANCE.getSelectedBlock(), "minecraft:") + " to favorites");
      } else {
         ChatUtil.INSTANCE.send("Already in favorites");
      }

      return 1;
   }

   private static final int register$lambda$0$34(CommandContext it) {
      String block = StringArgumentType.getString(it, "block");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(block);
      if (var10000.removeFavorite(block)) {
         ChatUtil.INSTANCE.send("Removed " + StringsKt.removePrefix(block, "minecraft:") + " from favorites");
      } else {
         ChatUtil.INSTANCE.send("Not in favorites");
      }

      return 1;
   }

   private static final int register$lambda$0$35(CommandContext it) {
      List favs = CosmeticBlocks.INSTANCE.getFavorites();
      if (favs.isEmpty()) {
         ChatUtil.INSTANCE.send("No favorites");
      } else {
         ChatUtil.INSTANCE
            .send("Favorites: " + CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", CbCommand::register$lambda$0$35$0));
      }

      return 1;
   }

   private static final int register$lambda$0$36(CommandContext it) {
      String result = ConfigBackup.INSTANCE.backup();
      ChatUtil.INSTANCE.send(result);
      return 1;
   }

   private static final int register$lambda$0$37(CommandContext it) {
      CosmeticBlocks.INSTANCE.listBlockProperties();
      return 1;
   }

   private static final CompletableFuture register$lambda$0$38(CommandContext var0, SuggestionsBuilder builder) {
      String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
      String baseId = StringsKt.contains(brush, '[', false) ? StringsKt.substringBefore(brush, '[', brush) : brush;
      Identifier loc = Identifier.tryParse(baseId);
      Block var10000;
      if (loc != null) {
         Identifier it = loc;
         int var8/* $VF was: $i$a$-let-CbCommand$register$1$39$block$1 */ = 0;
         var10000 = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
      } else {
         var10000 = null;
      }

      Block block = var10000;
      if (block != null) {
         String var11 = builder.getRemaining();
         Intrinsics.checkNotNullExpressionValue(var11, "getRemaining(...)");
         String var12 = var11.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var12, "toLowerCase(...)");
         String input = var12;

         for (Object var13 : block.getStateDefinition().getProperties()) {
            Intrinsics.checkNotNullExpressionValue(var13, "next(...)");
            Property prop = (Property)var13;
            String var14 = prop.getName();
            Intrinsics.checkNotNullExpressionValue(var14, "getName(...)");
            String var15 = var14.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var15, "toLowerCase(...)");
            if (StringsKt.startsWith(var15, input, false)) {
               builder.suggest(prop.getName());
            }
         }
      }

      return builder.buildFuture();
   }

   private static final CompletableFuture register$lambda$0$39(CommandContext context, SuggestionsBuilder builder) {
      String propName = StringArgumentType.getString(context, "property");
      String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
      String baseId = StringsKt.contains(brush, '[', false) ? StringsKt.substringBefore(brush, '[', brush) : brush;
      Identifier loc = Identifier.tryParse(baseId);
      Block var10000;
      if (loc != null) {
         Identifier it = loc;
         int var9/* $VF was: $i$a$-let-CbCommand$register$1$40$block$1 */ = 0;
         var10000 = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
      } else {
         var10000 = null;
      }

      Block block = var10000;
      if (block != null) {
         Property prop = block.getStateDefinition().getProperty(propName);
         if (prop != null) {
            String var14 = builder.getRemaining();
            Intrinsics.checkNotNullExpressionValue(var14, "getRemaining(...)");
            String var15 = var14.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var15, "toLowerCase(...)");
            String input = var15;

            for (Object var16 : prop.getPossibleValues()) {
               Intrinsics.checkNotNullExpressionValue(var16, "next(...)");
               Comparable value = (Comparable)var16;
               String var17 = prop.getName(value);
               Intrinsics.checkNotNullExpressionValue(var17, "getName(...)");
               String valueName = var17;
               String var18 = valueName.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var18, "toLowerCase(...)");
               if (StringsKt.startsWith(var18, input, false)) {
                  builder.suggest(valueName);
               }
            }
         }
      }

      return builder.buildFuture();
   }

   private static final int register$lambda$0$40(CommandContext it) {
      String property = StringArgumentType.getString(it, "property");
      String value = StringArgumentType.getString(it, "value");
      CosmeticBlocks var10000 = CosmeticBlocks.INSTANCE;
      Intrinsics.checkNotNull(property);
      Intrinsics.checkNotNull(value);
      var10000.setBlockProperty(property, value);
      return 1;
   }

   private static final int register$lambda$0$41(CommandContext it) {
      INSTANCE.openGui();
      return 1;
   }

   private static final void register$lambda$0(CommandDispatcher dispatcher, CommandBuildContext var1) {
      Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
      Intrinsics.checkNotNullParameter(var1, "<unused var>");
      dispatcher.register(
         (LiteralArgumentBuilder)ClientCommands.literal("cb")
            .then(ClientCommands.literal("gui").executes(CbCommand::register$lambda$0$0))
            .then(ClientCommands.literal("profiles").executes(CbCommand::register$lambda$0$1))
            .then(ClientCommands.literal("keybinds").executes(CbCommand::register$lambda$0$2))
            .then(ClientCommands.literal("hud").executes(CbCommand::register$lambda$0$3))
            .then(ClientCommands.literal("em").executes(CbCommand::register$lambda$0$4))
            .then(ClientCommands.literal("fillmode").executes(CbCommand::register$lambda$0$5))
            .then(
               ClientCommands.literal("block")
                  .then(
                     ClientCommands.argument("block", StringArgumentType.greedyString())
                        .suggests(CbCommand::register$lambda$0$6)
                        .executes(CbCommand::register$lambda$0$7)
                  )
            )
            .then(
               ClientCommands.literal("remove")
                  .executes(CbCommand::register$lambda$0$8)
                  .then(
                     ClientCommands.argument("block", StringArgumentType.string())
                        .suggests(CbCommand::register$lambda$0$9)
                        .executes(CbCommand::register$lambda$0$10)
                  )
            )
            .then(ClientCommands.literal("clear").executes(CbCommand::register$lambda$0$11))
            .then(ClientCommands.literal("list").executes(CbCommand::register$lambda$0$12))
            .then(ClientCommands.literal("save").executes(CbCommand::register$lambda$0$13))
            .then(ClientCommands.literal("reload").executes(CbCommand::register$lambda$0$14))
            .then(ClientCommands.literal("create").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::register$lambda$0$15)))
            .then(ClientCommands.literal("load").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::register$lambda$0$16)))
            .then(ClientCommands.literal("delete").then(ClientCommands.argument("name", StringArgumentType.string()).executes(CbCommand::register$lambda$0$17)))
            .then(ClientCommands.literal("configs").executes(CbCommand::register$lambda$0$18))
            .then(
               ClientCommands.literal("pos1")
                  .executes(CbCommand::register$lambda$0$19)
                  .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(CbCommand::register$lambda$0$20))
            )
            .then(
               ClientCommands.literal("pos2")
                  .executes(CbCommand::register$lambda$0$21)
                  .then(ClientCommands.argument("coords", StringArgumentType.greedyString()).executes(CbCommand::register$lambda$0$22))
            )
            .then(ClientCommands.literal("sel").executes(CbCommand::register$lambda$0$23))
            .then(ClientCommands.literal("clearselection").executes(CbCommand::register$lambda$0$24))
            .then(
               ClientCommands.literal("fill")
                  .then(
                     ClientCommands.argument("blocks", StringArgumentType.greedyString())
                        .suggests(CbCommand::register$lambda$0$25)
                        .executes(CbCommand::register$lambda$0$26)
                  )
            )
            .then(
               ClientCommands.literal("replace")
                  .then(
                     ClientCommands.argument("args", StringArgumentType.greedyString())
                        .suggests(CbCommand::register$lambda$0$27)
                        .executes(CbCommand::register$lambda$0$28)
                  )
            )
            .then(ClientCommands.literal("undo").executes(CbCommand::register$lambda$0$29))
            .then(
               ClientCommands.literal("fav")
                  .executes(CbCommand::register$lambda$0$30)
                  .then(
                     ClientCommands.literal("add")
                        .then(
                           ClientCommands.argument("block", StringArgumentType.string())
                              .suggests(CbCommand::register$lambda$0$31)
                              .executes(CbCommand::register$lambda$0$32)
                        )
                        .executes(CbCommand::register$lambda$0$33)
                  )
                  .then(
                     ClientCommands.literal("remove")
                        .then(ClientCommands.argument("block", StringArgumentType.string()).executes(CbCommand::register$lambda$0$34))
                  )
                  .then(ClientCommands.literal("list").executes(CbCommand::register$lambda$0$35))
            )
            .then(ClientCommands.literal("backup").executes(CbCommand::register$lambda$0$36))
            .then(ClientCommands.literal("props").executes(CbCommand::register$lambda$0$37))
            .then(
               ClientCommands.literal("state")
                  .then(
                     ClientCommands.argument("property", StringArgumentType.string())
                        .suggests(CbCommand::register$lambda$0$38)
                        .then(
                           ClientCommands.argument("value", StringArgumentType.string())
                              .suggests(CbCommand::register$lambda$0$39)
                              .executes(CbCommand::register$lambda$0$40)
                        )
                  )
            )
            .executes(CbCommand::register$lambda$0$41)
      );
   }
}
