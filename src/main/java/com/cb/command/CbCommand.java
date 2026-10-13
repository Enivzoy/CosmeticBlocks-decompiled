/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
 *  net.fabricmc.fabric.api.client.command.v2.ClientCommands
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.commands.CommandBuildContext
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.command;

import com.cb.command.CbCommand;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.CosmeticBlocks;
import com.cb.utils.ChatUtil;
import com.cb.utils.ConfigBackup;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
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
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\b\u0010\t\u001a\u00020\u0005H\u0002J\u0006\u0010\n\u001a\u00020\u0005J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0002\u00a8\u0006\u000f"}, d2={"Lcom/cb/command/CbCommand;", "", "<init>", "()V", "open", "", "screen", "Lkotlin/Function0;", "Lnet/minecraft/client/gui/screens/Screen;", "openGui", "register", "suggestBlockIds", "Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;", "builder", "suggestBlockIdsGreedy", "cb_client"})
@SourceDebugExtension(value={"SMAP\nCbCommand.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CbCommand.kt\ncom/cb/command/CbCommand\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,415:1\n1#2:416\n777#3:417\n873#3,2:418\n1586#3:420\n1661#3,3:421\n1834#3,4:424\n1915#3,2:428\n*S KotlinDebug\n*F\n+ 1 CbCommand.kt\ncom/cb/command/CbCommand\n*L\n373#1:417\n373#1:418,2\n373#1:420\n373#1:421,3\n383#1:424,4\n113#1:428,2\n*E\n"})
public final class CbCommand {
    @NotNull
    public static final CbCommand INSTANCE = new CbCommand();

    private CbCommand() {
    }

    private final void open(Function0<? extends Screen> screen) {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        mc.execute(() -> CbCommand.open$lambda$0(mc, screen));
    }

    private final void openGui() {
        this.open((Function0<? extends Screen>)((Function0)openGui.1.INSTANCE));
    }

    public final void register() {
        ClientCommandRegistrationCallback.EVENT.register(CbCommand::register$lambda$0);
    }

    /*
     * WARNING - void declaration
     */
    private final SuggestionsBuilder suggestBlockIds(SuggestionsBuilder builder) {
        block20: {
            String inputLower;
            block19: {
                Set setProps;
                String lastEntry;
                String prefix;
                Block block;
                block21: {
                    String setPropsStr;
                    String string;
                    Identifier loc;
                    String input = builder.getRemaining();
                    Intrinsics.checkNotNull((Object)input);
                    String string2 = input.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"toLowerCase(...)");
                    inputLower = string2;
                    if (!StringsKt.contains$default((CharSequence)input, (char)'[', (boolean)false, (int)2, null)) break block19;
                    int bracketIdx = StringsKt.indexOf$default((CharSequence)input, (char)'[', (int)0, (boolean)false, (int)6, null);
                    String string3 = input.substring(0, bracketIdx);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
                    String blockIdRaw = string3;
                    Object blockId = StringsKt.contains$default((CharSequence)blockIdRaw, (char)':', (boolean)false, (int)2, null) ? blockIdRaw : "minecraft:" + blockIdRaw;
                    Identifier identifier = loc = Identifier.tryParse((String)blockId);
                    if (identifier != null) {
                        Identifier it = identifier;
                        boolean bl = false;
                        v3 = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
                    } else {
                        v3 = block = null;
                    }
                    if (block == null || StringsKt.endsWith$default((String)input, (String)"]", (boolean)false, (int)2, null)) break block20;
                    String string4 = loc.toString();
                    Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"toString(...)");
                    String fullBlockId = string4;
                    String cleanInput = StringsKt.removeSuffix((String)input, (CharSequence)"]");
                    int lastCommaIdx = StringsKt.lastIndexOf$default((CharSequence)cleanInput, (char)',', (int)0, (boolean)false, (int)6, null);
                    if (lastCommaIdx > bracketIdx) {
                        String string5 = cleanInput.substring(0, lastCommaIdx + 1);
                        v6 = string5;
                        Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"substring(...)");
                    } else {
                        v6 = prefix = fullBlockId + "[";
                    }
                    if (lastCommaIdx > bracketIdx) {
                        String string6 = cleanInput.substring(lastCommaIdx + 1);
                        string = string6;
                        Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"substring(...)");
                    } else {
                        String string7 = cleanInput.substring(bracketIdx + 1);
                        string = string7;
                        Intrinsics.checkNotNullExpressionValue((Object)string7, (String)"substring(...)");
                    }
                    lastEntry = string;
                    if (lastCommaIdx > bracketIdx) {
                        String string8 = cleanInput.substring(bracketIdx + 1, lastCommaIdx);
                        v11 = string8;
                        Intrinsics.checkNotNullExpressionValue((Object)string8, (String)"substring(...)");
                    } else {
                        v11 = setPropsStr = "";
                    }
                    if (((CharSequence)setPropsStr).length() == 0) {
                        v12 = SetsKt.emptySet();
                    } else {
                        void $this$mapTo$iv$iv;
                        void $this$map$iv;
                        String it;
                        void $this$filterTo$iv$iv;
                        String[] stringArray = new String[]{","};
                        Iterable $this$filter$iv = StringsKt.split$default((CharSequence)setPropsStr, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
                        boolean $i$f$filter = false;
                        Iterable iterable = $this$filter$iv;
                        Collection destination$iv$iv = new ArrayList();
                        boolean $i$f$filterTo = false;
                        for (Object element$iv$iv : $this$filterTo$iv$iv) {
                            it = (String)element$iv$iv;
                            boolean bl = false;
                            if (!StringsKt.contains$default((CharSequence)it, (char)'=', (boolean)false, (int)2, null)) continue;
                            destination$iv$iv.add(element$iv$iv);
                        }
                        $this$filter$iv = (List)destination$iv$iv;
                        boolean $i$f$map = false;
                        $this$filterTo$iv$iv = $this$map$iv;
                        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                        boolean $i$f$mapTo = false;
                        for (Object item$iv$iv : $this$mapTo$iv$iv) {
                            it = (String)item$iv$iv;
                            Collection collection = destination$iv$iv;
                            boolean bl = false;
                            collection.add(StringsKt.substringBefore$default((String)it, (char)'=', null, (int)2, null));
                        }
                        v12 = setProps = CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
                    }
                    if (!StringsKt.contains$default((CharSequence)lastEntry, (char)'=', (boolean)false, (int)2, null)) break block21;
                    String propName = StringsKt.substringBefore$default((String)lastEntry, (char)'=', null, (int)2, null);
                    Property prop = block.getStateDefinition().getProperty(propName);
                    if (prop == null) break block20;
                    String propPrefix = prefix + propName + "=";
                    for (Object e : prop.getPossibleValues()) {
                        int remaining;
                        int n;
                        String valueName;
                        Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                        Comparable value = (Comparable)e;
                        Intrinsics.checkNotNullExpressionValue((Object)prop.getName(value), (String)"getName(...)");
                        builder.suggest(propPrefix + valueName + "]");
                        Collection collection = block.getStateDefinition().getProperties();
                        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"getProperties(...)");
                        Iterable $this$count$iv = collection;
                        boolean $i$f$count = false;
                        if (((Collection)$this$count$iv).isEmpty()) {
                            n = 0;
                        } else {
                            int count$iv = 0;
                            for (Object element$iv : $this$count$iv) {
                                Property it = (Property)element$iv;
                                boolean bl = false;
                                if (!(!setProps.contains(it.getName()) && !Intrinsics.areEqual((Object)it.getName(), (Object)propName)) || ++count$iv >= 0) continue;
                                CollectionsKt.throwCountOverflow();
                            }
                            n = count$iv;
                        }
                        if ((remaining = n) <= 0) continue;
                        builder.suggest(propPrefix + valueName + ",");
                    }
                    break block20;
                }
                for (Object e : block.getStateDefinition().getProperties()) {
                    Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                    Property prop = (Property)e;
                    if (setProps.contains(prop.getName())) continue;
                    String string = prop.getName();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getName(...)");
                    String string9 = string.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string9, (String)"toLowerCase(...)");
                    String string10 = lastEntry.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)string10, (String)"toLowerCase(...)");
                    if (!StringsKt.startsWith$default((String)string9, (String)string10, (boolean)false, (int)2, null)) continue;
                    builder.suggest(prefix + prop.getName() + "=");
                }
                break block20;
            }
            for (Object e : BuiltInRegistries.BLOCK.keySet()) {
                String id;
                Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                Identifier key = (Identifier)e;
                Intrinsics.checkNotNullExpressionValue((Object)key.toString(), (String)"toString(...)");
                if (!StringsKt.startsWith$default((String)id, (String)inputLower, (boolean)false, (int)2, null)) {
                    String string = key.getPath();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getPath(...)");
                    if (!StringsKt.startsWith$default((String)string, (String)inputLower, (boolean)false, (int)2, null)) continue;
                }
                builder.suggest(id);
            }
        }
        return builder;
    }

    private final SuggestionsBuilder suggestBlockIdsGreedy(SuggestionsBuilder builder) {
        String remaining = builder.getRemaining();
        Intrinsics.checkNotNull((Object)remaining);
        int lastSpace = StringsKt.lastIndexOf$default((CharSequence)remaining, (char)' ', (int)0, (boolean)false, (int)6, null);
        SuggestionsBuilder wordBuilder = lastSpace >= 0 ? builder.createOffset(builder.getStart() + lastSpace + 1) : builder;
        Intrinsics.checkNotNull((Object)wordBuilder);
        return this.suggestBlockIds(wordBuilder);
    }

    private static final void open$lambda$0(Minecraft $mc, Function0 $screen) {
        $mc.gui.setScreen((Screen)$screen.invoke());
    }

    private static final CharSequence register$lambda$0$30$0(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return StringsKt.removePrefix((String)it, (CharSequence)"minecraft:");
    }

    private static final CharSequence register$lambda$0$35$0(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return StringsKt.removePrefix((String)it, (CharSequence)"minecraft:");
    }

    private static final int register$lambda$0$0(CommandContext it) {
        INSTANCE.openGui();
        return 1;
    }

    private static final int register$lambda$0$1(CommandContext it) {
        INSTANCE.open((Function0<? extends Screen>)((Function0)register.1.2.1.INSTANCE));
        return 1;
    }

    private static final int register$lambda$0$2(CommandContext it) {
        INSTANCE.open((Function0<? extends Screen>)((Function0)register.1.3.1.INSTANCE));
        return 1;
    }

    private static final int register$lambda$0$3(CommandContext it) {
        INSTANCE.open((Function0<? extends Screen>)((Function0)register.1.4.1.INSTANCE));
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

    private static final CompletableFuture register$lambda$0$6(CommandContext commandContext, SuggestionsBuilder builder) {
        Intrinsics.checkNotNull((Object)builder);
        return INSTANCE.suggestBlockIds(builder).buildFuture();
    }

    private static final int register$lambda$0$7(CommandContext it) {
        String blockId = StringArgumentType.getString((CommandContext)it, (String)"block");
        Intrinsics.checkNotNull((Object)blockId);
        CosmeticBlocks.INSTANCE.setBlock(blockId);
        return 1;
    }

    private static final int register$lambda$0$8(CommandContext it) {
        ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(null));
        return 1;
    }

    private static final CompletableFuture register$lambda$0$9(CommandContext commandContext, SuggestionsBuilder builder) {
        Intrinsics.checkNotNull((Object)builder);
        return INSTANCE.suggestBlockIds(builder).buildFuture();
    }

    private static final int register$lambda$0$10(CommandContext it) {
        String block = StringArgumentType.getString((CommandContext)it, (String)"block");
        ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.removeInRegion(block));
        return 1;
    }

    private static final int register$lambda$0$11(CommandContext it) {
        int count = CosmeticBlocks.INSTANCE.clearDimension();
        ChatUtil.INSTANCE.send("Cleared " + count + " replacements in current dimension");
        return 1;
    }

    private static final int register$lambda$0$12(CommandContext it) {
        List<String> list = CosmeticBlocks.INSTANCE.listReplacements();
        if (list.isEmpty()) {
            ChatUtil.INSTANCE.send("No replacements");
        } else {
            ChatUtil.INSTANCE.send("--- Replacements (" + list.size() + ") ---");
            Iterable $this$forEach$iv = CollectionsKt.take((Iterable)list, (int)20);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String it2 = (String)element$iv;
                boolean bl = false;
                ChatUtil.INSTANCE.send("  " + it2);
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
        String name = StringArgumentType.getString((CommandContext)it, (String)"name");
        Intrinsics.checkNotNull((Object)name);
        if (CosmeticBlocks.INSTANCE.createConfig(name)) {
            ChatUtil.INSTANCE.send("Created config '" + name + "'");
        } else {
            ChatUtil.INSTANCE.send("Config '" + name + "' already exists");
        }
        return 1;
    }

    private static final int register$lambda$0$16(CommandContext it) {
        String name = StringArgumentType.getString((CommandContext)it, (String)"name");
        Intrinsics.checkNotNull((Object)name);
        if (CosmeticBlocks.INSTANCE.loadConfigByName(name)) {
            ChatUtil.INSTANCE.send("Loaded config '" + name + "'");
        } else {
            ChatUtil.INSTANCE.send("Config '" + name + "' not found");
        }
        return 1;
    }

    private static final int register$lambda$0$17(CommandContext it) {
        String name = StringArgumentType.getString((CommandContext)it, (String)"name");
        Intrinsics.checkNotNull((Object)name);
        if (CosmeticBlocks.INSTANCE.deleteConfig(name)) {
            ChatUtil.INSTANCE.send("Deleted config '" + name + "'");
        } else {
            ChatUtil.INSTANCE.send("Cannot delete '" + name + "'");
        }
        return 1;
    }

    private static final int register$lambda$0$18(CommandContext it) {
        List<String> configs = CosmeticBlocks.INSTANCE.listConfigs();
        ChatUtil.INSTANCE.send("Configs: " + CollectionsKt.joinToString$default((Iterable)configs, (CharSequence)", ", null, null, (int)0, null, null, (int)62, null) + " (active: " + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue() + ")");
        return 1;
    }

    private static final int register$lambda$0$19(CommandContext it) {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        HitResult hit = mc.hitResult;
        if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hit).getBlockPos();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
            CosmeticBlocks.INSTANCE.setPos1(blockPos);
        } else {
            ChatUtil.INSTANCE.send("Look at a block or: cb pos1 <x> <y> <z>");
        }
        return 1;
    }

    private static final int register$lambda$0$20(CommandContext it) {
        String string = StringArgumentType.getString((CommandContext)it, (String)"coords");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getString(...)");
        CharSequence charSequence = ((Object)StringsKt.trim((CharSequence)string)).toString();
        Regex regex = new Regex("\\s+");
        int n = 0;
        List parts = regex.split(charSequence, n);
        if (parts.size() >= 3) {
            Integer x = StringsKt.toIntOrNull((String)((String)parts.get(0)));
            Integer y = StringsKt.toIntOrNull((String)((String)parts.get(1)));
            Integer z = StringsKt.toIntOrNull((String)((String)parts.get(2)));
            if (x != null && y != null && z != null) {
                CosmeticBlocks.INSTANCE.setPos1(new BlockPos(x.intValue(), y.intValue(), z.intValue()));
            } else {
                ChatUtil.INSTANCE.send("Invalid coordinates");
            }
        } else {
            ChatUtil.INSTANCE.send("Usage: cb pos1 <x> <y> <z>");
        }
        return 1;
    }

    private static final int register$lambda$0$21(CommandContext it) {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        HitResult hit = mc.hitResult;
        if (hit instanceof BlockHitResult && ((BlockHitResult)hit).getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hit).getBlockPos();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
            CosmeticBlocks.INSTANCE.setPos2(blockPos);
        } else {
            ChatUtil.INSTANCE.send("Look at a block or: cb pos2 <x> <y> <z>");
        }
        return 1;
    }

    private static final int register$lambda$0$22(CommandContext it) {
        String string = StringArgumentType.getString((CommandContext)it, (String)"coords");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getString(...)");
        CharSequence charSequence = ((Object)StringsKt.trim((CharSequence)string)).toString();
        Regex regex = new Regex("\\s+");
        int n = 0;
        List parts = regex.split(charSequence, n);
        if (parts.size() >= 3) {
            Integer x = StringsKt.toIntOrNull((String)((String)parts.get(0)));
            Integer y = StringsKt.toIntOrNull((String)((String)parts.get(1)));
            Integer z = StringsKt.toIntOrNull((String)((String)parts.get(2)));
            if (x != null && y != null && z != null) {
                CosmeticBlocks.INSTANCE.setPos2(new BlockPos(x.intValue(), y.intValue(), z.intValue()));
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

    private static final CompletableFuture register$lambda$0$25(CommandContext commandContext, SuggestionsBuilder builder) {
        Intrinsics.checkNotNull((Object)builder);
        return INSTANCE.suggestBlockIdsGreedy(builder).buildFuture();
    }

    private static final int register$lambda$0$26(CommandContext it) {
        String blocks = StringArgumentType.getString((CommandContext)it, (String)"blocks");
        Intrinsics.checkNotNull((Object)blocks);
        ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.fillRegion(blocks));
        return 1;
    }

    private static final CompletableFuture register$lambda$0$27(CommandContext commandContext, SuggestionsBuilder builder) {
        Intrinsics.checkNotNull((Object)builder);
        return INSTANCE.suggestBlockIdsGreedy(builder).buildFuture();
    }

    private static final int register$lambda$0$28(CommandContext it) {
        String string = StringArgumentType.getString((CommandContext)it, (String)"args");
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getString(...)");
        CharSequence charSequence = ((Object)StringsKt.trim((CharSequence)string)).toString();
        Regex regex = new Regex("\\s+");
        int n = 0;
        List args = regex.split(charSequence, n);
        if (args.size() >= 2) {
            String fromBlock = (String)args.get(0);
            String toSpecs = CollectionsKt.joinToString$default((Iterable)CollectionsKt.drop((Iterable)args, (int)1), (CharSequence)" ", null, null, (int)0, null, null, (int)62, null);
            ChatUtil.INSTANCE.send(CosmeticBlocks.INSTANCE.replaceRegion(fromBlock, toSpecs));
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
        List<String> favs = CosmeticBlocks.INSTANCE.getFavorites();
        if (favs.isEmpty()) {
            ChatUtil.INSTANCE.send("No favorites yet. Use /cb fav add <block>");
        } else {
            ChatUtil.INSTANCE.send("Favorites: " + CollectionsKt.joinToString$default((Iterable)favs, (CharSequence)", ", null, null, (int)0, null, CbCommand::register$lambda$0$30$0, (int)30, null));
        }
        return 1;
    }

    private static final CompletableFuture register$lambda$0$31(CommandContext commandContext, SuggestionsBuilder builder) {
        Intrinsics.checkNotNull((Object)builder);
        return INSTANCE.suggestBlockIds(builder).buildFuture();
    }

    private static final int register$lambda$0$32(CommandContext it) {
        String block = StringArgumentType.getString((CommandContext)it, (String)"block");
        Intrinsics.checkNotNull((Object)block);
        if (CosmeticBlocks.INSTANCE.addFavorite(block)) {
            ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix((String)block, (CharSequence)"minecraft:") + " to favorites");
        } else {
            ChatUtil.INSTANCE.send("Already in favorites or invalid block");
        }
        return 1;
    }

    private static final int register$lambda$0$33(CommandContext it) {
        if (CosmeticBlocks.INSTANCE.addFavorite(CosmeticBlocks.INSTANCE.getSelectedBlock())) {
            ChatUtil.INSTANCE.send("Added " + StringsKt.removePrefix((String)CosmeticBlocks.INSTANCE.getSelectedBlock(), (CharSequence)"minecraft:") + " to favorites");
        } else {
            ChatUtil.INSTANCE.send("Already in favorites");
        }
        return 1;
    }

    private static final int register$lambda$0$34(CommandContext it) {
        String block = StringArgumentType.getString((CommandContext)it, (String)"block");
        Intrinsics.checkNotNull((Object)block);
        if (CosmeticBlocks.INSTANCE.removeFavorite(block)) {
            ChatUtil.INSTANCE.send("Removed " + StringsKt.removePrefix((String)block, (CharSequence)"minecraft:") + " from favorites");
        } else {
            ChatUtil.INSTANCE.send("Not in favorites");
        }
        return 1;
    }

    private static final int register$lambda$0$35(CommandContext it) {
        List<String> favs = CosmeticBlocks.INSTANCE.getFavorites();
        if (favs.isEmpty()) {
            ChatUtil.INSTANCE.send("No favorites");
        } else {
            ChatUtil.INSTANCE.send("Favorites: " + CollectionsKt.joinToString$default((Iterable)favs, (CharSequence)", ", null, null, (int)0, null, CbCommand::register$lambda$0$35$0, (int)30, null));
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

    private static final CompletableFuture register$lambda$0$38(CommandContext commandContext, SuggestionsBuilder builder) {
        Block block;
        Identifier loc;
        String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
        String baseId = StringsKt.contains$default((CharSequence)brush, (char)'[', (boolean)false, (int)2, null) ? StringsKt.substringBefore$default((String)brush, (char)'[', null, (int)2, null) : brush;
        Identifier identifier = loc = Identifier.tryParse((String)baseId);
        if (identifier != null) {
            Identifier it = identifier;
            boolean bl = false;
            v1 = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
        } else {
            v1 = block = null;
        }
        if (block != null) {
            String string = builder.getRemaining();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getRemaining(...)");
            String string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"toLowerCase(...)");
            String input = string2;
            for (Object e : block.getStateDefinition().getProperties()) {
                Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                Property prop = (Property)e;
                String string3 = prop.getName();
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"getName(...)");
                String string4 = string3.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"toLowerCase(...)");
                if (!StringsKt.startsWith$default((String)string4, (String)input, (boolean)false, (int)2, null)) continue;
                builder.suggest(prop.getName());
            }
        }
        return builder.buildFuture();
    }

    private static final CompletableFuture register$lambda$0$39(CommandContext context, SuggestionsBuilder builder) {
        Property prop;
        Block block;
        Identifier loc;
        String propName = StringArgumentType.getString((CommandContext)context, (String)"property");
        String brush = CosmeticBlocks.INSTANCE.getSelectedBlock();
        String baseId = StringsKt.contains$default((CharSequence)brush, (char)'[', (boolean)false, (int)2, null) ? StringsKt.substringBefore$default((String)brush, (char)'[', null, (int)2, null) : brush;
        Identifier identifier = loc = Identifier.tryParse((String)baseId);
        if (identifier != null) {
            Identifier it = identifier;
            boolean bl = false;
            v1 = BuiltInRegistries.BLOCK.getOptional(it).orElse(null);
        } else {
            v1 = block = null;
        }
        if (block != null && (prop = block.getStateDefinition().getProperty(propName)) != null) {
            String string = builder.getRemaining();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getRemaining(...)");
            String string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"toLowerCase(...)");
            String input = string2;
            for (Object e : prop.getPossibleValues()) {
                String valueName;
                Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                Comparable value = (Comparable)e;
                Intrinsics.checkNotNullExpressionValue((Object)prop.getName(value), (String)"getName(...)");
                String string3 = valueName.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"toLowerCase(...)");
                if (!StringsKt.startsWith$default((String)string3, (String)input, (boolean)false, (int)2, null)) continue;
                builder.suggest(valueName);
            }
        }
        return builder.buildFuture();
    }

    private static final int register$lambda$0$40(CommandContext it) {
        String property = StringArgumentType.getString((CommandContext)it, (String)"property");
        String value = StringArgumentType.getString((CommandContext)it, (String)"value");
        Intrinsics.checkNotNull((Object)property);
        Intrinsics.checkNotNull((Object)value);
        CosmeticBlocks.INSTANCE.setBlockProperty(property, value);
        return 1;
    }

    private static final int register$lambda$0$41(CommandContext it) {
        INSTANCE.openGui();
        return 1;
    }

    private static final void register$lambda$0(CommandDispatcher dispatcher, CommandBuildContext commandBuildContext) {
        Intrinsics.checkNotNullParameter((Object)dispatcher, (String)"dispatcher");
        Intrinsics.checkNotNullParameter((Object)commandBuildContext, (String)"<unused var>");
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommands.literal((String)"cb").then(ClientCommands.literal((String)"gui").executes(CbCommand::register$lambda$0$0))).then(ClientCommands.literal((String)"profiles").executes(CbCommand::register$lambda$0$1))).then(ClientCommands.literal((String)"keybinds").executes(CbCommand::register$lambda$0$2))).then(ClientCommands.literal((String)"hud").executes(CbCommand::register$lambda$0$3))).then(ClientCommands.literal((String)"em").executes(CbCommand::register$lambda$0$4))).then(ClientCommands.literal((String)"fillmode").executes(CbCommand::register$lambda$0$5))).then(ClientCommands.literal((String)"block").then(ClientCommands.argument((String)"block", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).suggests(CbCommand::register$lambda$0$6).executes(CbCommand::register$lambda$0$7)))).then(((LiteralArgumentBuilder)ClientCommands.literal((String)"remove").executes(CbCommand::register$lambda$0$8)).then(ClientCommands.argument((String)"block", (ArgumentType)((ArgumentType)StringArgumentType.string())).suggests(CbCommand::register$lambda$0$9).executes(CbCommand::register$lambda$0$10)))).then(ClientCommands.literal((String)"clear").executes(CbCommand::register$lambda$0$11))).then(ClientCommands.literal((String)"list").executes(CbCommand::register$lambda$0$12))).then(ClientCommands.literal((String)"save").executes(CbCommand::register$lambda$0$13))).then(ClientCommands.literal((String)"reload").executes(CbCommand::register$lambda$0$14))).then(ClientCommands.literal((String)"create").then(ClientCommands.argument((String)"name", (ArgumentType)((ArgumentType)StringArgumentType.string())).executes(CbCommand::register$lambda$0$15)))).then(ClientCommands.literal((String)"load").then(ClientCommands.argument((String)"name", (ArgumentType)((ArgumentType)StringArgumentType.string())).executes(CbCommand::register$lambda$0$16)))).then(ClientCommands.literal((String)"delete").then(ClientCommands.argument((String)"name", (ArgumentType)((ArgumentType)StringArgumentType.string())).executes(CbCommand::register$lambda$0$17)))).then(ClientCommands.literal((String)"configs").executes(CbCommand::register$lambda$0$18))).then(((LiteralArgumentBuilder)ClientCommands.literal((String)"pos1").executes(CbCommand::register$lambda$0$19)).then(ClientCommands.argument((String)"coords", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).executes(CbCommand::register$lambda$0$20)))).then(((LiteralArgumentBuilder)ClientCommands.literal((String)"pos2").executes(CbCommand::register$lambda$0$21)).then(ClientCommands.argument((String)"coords", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).executes(CbCommand::register$lambda$0$22)))).then(ClientCommands.literal((String)"sel").executes(CbCommand::register$lambda$0$23))).then(ClientCommands.literal((String)"clearselection").executes(CbCommand::register$lambda$0$24))).then(ClientCommands.literal((String)"fill").then(ClientCommands.argument((String)"blocks", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).suggests(CbCommand::register$lambda$0$25).executes(CbCommand::register$lambda$0$26)))).then(ClientCommands.literal((String)"replace").then(ClientCommands.argument((String)"args", (ArgumentType)((ArgumentType)StringArgumentType.greedyString())).suggests(CbCommand::register$lambda$0$27).executes(CbCommand::register$lambda$0$28)))).then(ClientCommands.literal((String)"undo").executes(CbCommand::register$lambda$0$29))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)ClientCommands.literal((String)"fav").executes(CbCommand::register$lambda$0$30)).then(((LiteralArgumentBuilder)ClientCommands.literal((String)"add").then(ClientCommands.argument((String)"block", (ArgumentType)((ArgumentType)StringArgumentType.string())).suggests(CbCommand::register$lambda$0$31).executes(CbCommand::register$lambda$0$32))).executes(CbCommand::register$lambda$0$33))).then(ClientCommands.literal((String)"remove").then(ClientCommands.argument((String)"block", (ArgumentType)((ArgumentType)StringArgumentType.string())).executes(CbCommand::register$lambda$0$34)))).then(ClientCommands.literal((String)"list").executes(CbCommand::register$lambda$0$35)))).then(ClientCommands.literal((String)"backup").executes(CbCommand::register$lambda$0$36))).then(ClientCommands.literal((String)"props").executes(CbCommand::register$lambda$0$37))).then(ClientCommands.literal((String)"state").then(ClientCommands.argument((String)"property", (ArgumentType)((ArgumentType)StringArgumentType.string())).suggests(CbCommand::register$lambda$0$38).then(ClientCommands.argument((String)"value", (ArgumentType)((ArgumentType)StringArgumentType.string())).suggests(CbCommand::register$lambda$0$39).executes(CbCommand::register$lambda$0$40))))).executes(CbCommand::register$lambda$0$41));
    }
}


