package com.cb.gui.screens;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.CosmeticBlocks;
import com.cb.dungeon.RoomInfo;
import com.cb.dungeon.RoomScanner;
import com.cb.dungeon.ScannedRoom;
import com.cb.setting.BooleanSetting;
import com.cb.setting.KeybindSetting;
import com.cb.setting.Setting;
import com.cb.utils.ChatUtil;
import com.cb.utils.ConfigBackup;
import com.cb.utils.Scheduler;
import com.mojang.blaze3d.platform.Window;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003XYZB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u000e\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u0011J\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00110#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u0010!\u001a\u00020\u0011J\u0006\u0010&\u001a\u00020\u001fJ\u0016\u0010'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010+\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u0005J\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00101\u001a\u00020\u0011H\u0002J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00103\u001a\u00020\u0011H\u0002J&\u00104\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00105\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020\u0011H\u0002J\u001e\u00108\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00109\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u0011H\u0002J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020;0#H\u0002J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020=0#H\u0002J\u0017\u0010>\u001a\u0004\u0018\u00010)2\u0006\u0010!\u001a\u00020\u0011H\u0002¢\u0006\u0002\u0010?J\u0010\u0010@\u001a\u00020\u00112\u0006\u0010A\u001a\u00020)H\u0002J\u0017\u0010B\u001a\u0004\u0018\u00010C2\u0006\u0010D\u001a\u00020\u0011H\u0002¢\u0006\u0002\u0010EJ\u0010\u0010F\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u0011H\u0002J\u0010\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u0011H\u0002J\b\u0010I\u001a\u00020\u001dH\u0002J\b\u0010J\u001a\u00020\u001dH\u0002J\u0010\u0010K\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u0010\u0010M\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u000e\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00110#H\u0002J\u0010\u0010O\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u0014\u0010P\u001a\u00020\u00112\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030RH\u0002J\u001c\u0010S\u001a\u00020\u001d2\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030R2\u0006\u00101\u001a\u00020\u0011H\u0002J\u0006\u0010T\u001a\u00020\u001fJ\b\u0010U\u001a\u00020\u001fH\u0002J\b\u0010V\u001a\u00020\u001fH\u0002J\b\u0010W\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00110#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110#X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006[",
   d2 = {
         "Lcom/cb/gui/screens/CliEngine;",
         "",
         "<init>",
         "()V",
         "MAX_HISTORY",
         "",
         "configDir",
         "Ljava/nio/file/Path;",
         "savedColorsFile",
         "getSavedColorsFile",
         "()Ljava/nio/file/Path;",
         "notesFile",
         "getNotesFile",
         "profilesDir",
         "getProfilesDir",
         "commandHistory",
         "",
         "",
         "savedColors",
         "Ljava/awt/Color;",
         "getSavedColors",
         "()Ljava/util/List;",
         "notes",
         "getNotes",
         "reminders",
         "Lcom/cb/gui/screens/CliEngine$Reminder;",
         "getReminders",
         "nextReminderId",
         "loaded",
         "",
         "ensureLoaded",
         "",
         "execute",
         "input",
         "history",
         "",
         "suggestions",
         "Lcom/cb/gui/screens/CliEngine$Suggestion;",
         "checkReminders",
         "addReminder",
         "delayMs",
         "",
         "message",
         "cancelReminder",
         "id",
         "MAX_SUGGESTIONS",
         "TOP_COMMANDS",
         "CB_SUBCOMMANDS",
         "computeSuggestions",
         "raw",
         "cbSuggestions",
         "rest",
         "blockSuggestions",
         "token",
         "prefix",
         "hint",
         "favSuggestions",
         "args",
         "booleanSettings",
         "Lcom/cb/setting/BooleanSetting;",
         "keybindSettings",
         "Lcom/cb/setting/KeybindSetting;",
         "parseTimeString",
         "(Ljava/lang/String;)Ljava/lang/Long;",
         "formatDuration",
         "ms",
         "evaluateCalcExpression",
         "",
         "expr",
         "(Ljava/lang/String;)Ljava/lang/Double;",
         "executeCliCommand",
         "executeCbCommand",
         "arg",
         "exportToClipboard",
         "importFromClipboard",
         "saveProfile",
         "name",
         "loadProfile",
         "listProfiles",
         "deleteProfile",
         "valueToString",
         "setting",
         "Lcom/cb/setting/Setting;",
         "stringToValue",
         "saveSavedColors",
         "loadSavedColors",
         "saveNotes",
         "loadNotes",
         "Reminder",
         "Suggestion",
         "CalcParser",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nCliEngine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CliEngine.kt\ncom/cb/gui/screens/CliEngine\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,1108:1\n777#2:1109\n873#2,2:1110\n1915#2,2:1112\n1021#2,2:1114\n777#2:1116\n873#2,2:1117\n1586#2:1119\n1661#2,3:1120\n1586#2:1123\n1661#2,3:1124\n777#2:1127\n873#2,2:1128\n1586#2:1130\n1661#2,3:1131\n777#2:1134\n873#2,2:1135\n1586#2:1137\n1661#2,3:1138\n777#2:1141\n873#2,2:1142\n1586#2:1144\n1661#2,3:1145\n1834#2,4:1148\n777#2:1153\n873#2,2:1154\n1586#2:1156\n1661#2,3:1157\n777#2:1160\n873#2,2:1161\n1586#2:1163\n1661#2,3:1164\n777#2:1167\n873#2,2:1168\n1586#2:1170\n1661#2,3:1171\n777#2:1174\n873#2,2:1175\n1586#2:1177\n1661#2,3:1178\n1586#2:1181\n1661#2,3:1182\n777#2:1185\n873#2,2:1186\n1586#2:1188\n1661#2,3:1189\n1391#2:1192\n1480#2,5:1193\n812#2,12:1198\n1391#2:1210\n1480#2,5:1211\n812#2,12:1216\n1586#2:1228\n1661#2,3:1229\n777#2:1232\n873#2,2:1233\n1586#2:1235\n1661#2,3:1236\n1915#2:1239\n1915#2,2:1240\n1916#2:1242\n1915#2,2:1243\n1915#2:1245\n1915#2,2:1246\n1916#2:1248\n1915#2,2:1249\n1915#2:1251\n1915#2,2:1252\n1916#2:1254\n1586#2:1255\n1661#2,3:1256\n1915#2,2:1261\n1915#2,2:1263\n1#3:1152\n1401#4,2:1259\n*S KotlinDebug\n*F\n+ 1 CliEngine.kt\ncom/cb/gui/screens/CliEngine\n*L\n95#1:1109\n95#1:1110,2\n96#1:1112,2\n105#1:1114,2\n155#1:1116\n155#1:1117,2\n156#1:1119\n156#1:1120,3\n168#1:1123\n168#1:1124,3\n169#1:1127\n169#1:1128,2\n172#1:1130\n172#1:1131,3\n178#1:1134\n178#1:1135,2\n179#1:1137\n179#1:1138,3\n192#1:1141\n192#1:1142,2\n193#1:1144\n193#1:1145,3\n207#1:1148,4\n214#1:1153\n214#1:1154,2\n215#1:1156\n215#1:1157,3\n220#1:1160\n220#1:1161,2\n221#1:1163\n221#1:1164,3\n225#1:1167\n225#1:1168,2\n226#1:1170\n226#1:1171,3\n248#1:1174\n248#1:1175,2\n249#1:1177\n249#1:1178,3\n256#1:1181\n256#1:1182,3\n257#1:1185\n257#1:1186,2\n258#1:1188\n258#1:1189,3\n270#1:1192\n270#1:1193,5\n270#1:1198,12\n273#1:1210\n273#1:1211,5\n273#1:1216,12\n583#1:1228\n583#1:1229,3\n664#1:1232\n664#1:1233,2\n668#1:1235\n668#1:1236,3\n924#1:1239\n925#1:1240,2\n924#1:1242\n941#1:1243,2\n967#1:1245\n968#1:1246,2\n967#1:1248\n986#1:1249,2\n991#1:1251\n992#1:1252,2\n991#1:1254\n1009#1:1255\n1009#1:1256,3\n1072#1:1261,2\n1093#1:1263,2\n1051#1:1259,2\n*E\n"
)
public final class CliEngine {
   @NotNull
   public static final CliEngine INSTANCE = new CliEngine();
   private static final int MAX_HISTORY = 50;
   @NotNull
   private static final Path configDir;
   @NotNull
   private static final List<String> commandHistory = new ArrayList<>();
   @NotNull
   private static final List<Color> savedColors = new ArrayList<>();
   @NotNull
   private static final List<String> notes = new ArrayList<>();
   @NotNull
   private static final List<CliEngine.Reminder> reminders = new ArrayList<>();
   private static int nextReminderId = 1;
   private static boolean loaded;
   private static final int MAX_SUGGESTIONS = 10;
   @NotNull
   private static final List<String> TOP_COMMANDS;
   @NotNull
   private static final List<String> CB_SUBCOMMANDS;

   private CliEngine() {
   }

   private final Path getSavedColorsFile() {
      Path var10000 = configDir.resolve("saved_colors.txt");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      return var10000;
   }

   private final Path getNotesFile() {
      Path var10000 = configDir.resolve("notes.txt");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      return var10000;
   }

   private final Path getProfilesDir() {
      Path var10000 = configDir.resolve("profiles");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      return var10000;
   }

   @NotNull
   public final List<Color> getSavedColors() {
      return savedColors;
   }

   @NotNull
   public final List<String> getNotes() {
      return notes;
   }

   @NotNull
   public final List<CliEngine.Reminder> getReminders() {
      return reminders;
   }

   private final void ensureLoaded() {
      if (!loaded) {
         loaded = true;
         this.loadSavedColors();
         this.loadNotes();
      }
   }

   @NotNull
   public final String execute(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      this.ensureLoaded();
      String cmd = StringsKt.trim(input).toString();
      if (cmd.length() > 0 && (commandHistory.isEmpty() || !Intrinsics.areEqual(CollectionsKt.last(commandHistory), cmd))) {
         commandHistory.add(cmd);
         if (commandHistory.size() > 50) {
            commandHistory.remove(0);
         }
      }

      String var3;
      try {
         var3 = this.executeCliCommand(cmd);
      } catch (Throwable t) {
         var3 = "Error: " + t.getMessage();
      }

      return var3;
   }

   @NotNull
   public final List<String> history() {
      return CollectionsKt.toList(commandHistory);
   }

   @NotNull
   public final List<CliEngine.Suggestion> suggestions(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      return this.computeSuggestions(input);
   }

   public final void checkReminders() {
      if (!reminders.isEmpty()) {
         long now = System.currentTimeMillis();
         Iterable $this$filter$iv = reminders;
         int $i$f$filter = 0;
         Iterable $this$filterTo$iv$iv = $this$filter$iv;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;

         for (Object element$iv$iv : $this$filterTo$iv$iv) {
            CliEngine.Reminder it = (CliEngine.Reminder)element$iv$iv;
            int var12/* $VF was: $i$a$-filter-CliEngine$checkReminders$triggered$1 */ = 0;
            if (it.getTriggerTime() <= now) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         List triggered = (List)destination$iv$iv;
         $this$filter$iv = triggered;
         $i$f$filter = 0;

         for (Object element$iv : $this$filter$iv) {
            CliEngine.Reminder reminder = (CliEngine.Reminder)element$iv;
            int var18/* $VF was: $i$a$-forEach-CliEngine$checkReminders$1 */ = 0;
            reminders.remove(reminder);
            ChatUtil.INSTANCE.info("Reminder: " + reminder.getMessage());
         }
      }
   }

   public final int addReminder(long delayMs, @NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      int $this$sortBy$iv = nextReminderId++;
      int id = $this$sortBy$iv;
      reminders.add(new CliEngine.Reminder(System.currentTimeMillis() + delayMs, message, id));
      List $this$sortBy$ivx = reminders;
      int $i$f$sortBy = 0;
      if ($this$sortBy$ivx.size() > 1) {
         CollectionsKt.sortWith($this$sortBy$ivx, new CliEngine$addReminder$$inlined$sortBy$1());
      }

      return id;
   }

   public final boolean cancelReminder(int id) {
      return reminders.removeIf(it -> cancelReminder$lambda$0(id, (CliEngine.Reminder)it));
   }

   private final List<CliEngine.Suggestion> computeSuggestions(String raw) {
      String text = StringsKt.trimStart(raw).toString();
      if (text.length() == 0) {
         return CollectionsKt.emptyList();
      }

      int firstSpace = StringsKt.indexOf(text, ' ', 0, false);
      if (firstSpace < 0) {
         String var91 = text.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var91, "toLowerCase(...)");
         String q = var91;
         Iterable var24 = CollectionsKt.distinct(TOP_COMMANDS);
         int $i$f$filter = 0;
         Iterable var30 = var24;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;

         for (Object element$iv$iv : var30) {
            String it = (String)element$iv$iv;
            int var65/* $VF was: $i$a$-filter-CliEngine$computeSuggestions$1 */ = 0;
            if (StringsKt.startsWith(it, q, false)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var24 = (Iterable & List)destination$iv$iv;
         $i$f$filter = 0;
         var30 = var24;
         destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));
         $i$f$filterTo = 0;

         for (Object item$iv$iv : var30) {
            String var60 = (String)item$iv$iv;
            Collection var83 = destination$iv$iv;
            int var66/* $VF was: $i$a$-map-CliEngine$computeSuggestions$2 */ = 0;
            var83.add(new CliEngine.Suggestion(var60, var60, ""));
         }

         return CollectionsKt.take(destination$iv$iv, 10);
      } else {
         String head;
         String rest;
         label153: {
            String var10000 = text.substring(0, firstSpace);
            Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
            var10000 = var10000.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
            head = var10000;
            var10000 = text.substring(firstSpace + 1);
            Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
            rest = var10000;
            String switchHead = head;
            switch (switchHead.hashCode()) {
               case -1405258946:
                  if (!switchHead.equals("delprofile")) {
                     return CollectionsKt.emptyList();
                  }
                  break label153;
               case -868304044:
                  if (!switchHead.equals("toggle")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 116:
                  if (!switchHead.equals("t")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 3167:
                  if (switchHead.equals("cb")) {
                     return this.cbSuggestions(rest);
                  }

                  return CollectionsKt.emptyList();
               case 3327206:
                  if (!switchHead.equals("load")) {
                     return CollectionsKt.emptyList();
                  }
                  break label153;
               default:
                  return CollectionsKt.emptyList();
            }

            var10000 = rest.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
            String q = var10000;
            Iterable $this$computeSuggestions_u24lambda_u245 = this.booleanSettings();
            int $i$f$map = 0;
            Iterable qx = $this$computeSuggestions_u24lambda_u245;
            var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$computeSuggestions_u24lambda_u245, 10));
            int $i$f$mapTo = 0;

            for (Object item$iv$iv : qx) {
               BooleanSetting $i$f$mapTox = (BooleanSetting)item$iv$iv;
               Collection var21 = destination$iv$iv;
               int var16/* $VF was: $i$a$-map-CliEngine$computeSuggestions$3 */ = 0;
               var21.add($i$f$mapTox.getName());
            }

            $this$computeSuggestions_u24lambda_u245 = (Iterable & List)destination$iv$iv;
            $i$f$map = 0;
            qx = $this$computeSuggestions_u24lambda_u245;
            destination$iv$iv = new ArrayList();
            $i$f$mapTo = 0;

            for (Object element$iv$iv : qx) {
               String it = (String)element$iv$iv;
               int var75/* $VF was: $i$a$-filter-CliEngine$computeSuggestions$4 */ = 0;
               var10000 = it.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
               if (StringsKt.contains(var10000, q, false)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $this$computeSuggestions_u24lambda_u245 = CollectionsKt.take(CollectionsKt.distinct((Iterable & List)destination$iv$iv), 10);
            $i$f$map = 0;
            qx = $this$computeSuggestions_u24lambda_u245;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$computeSuggestions_u24lambda_u245, 10));
            $i$f$mapTo = 0;

            for (Object item$iv$iv : qx) {
               String var72 = (String)item$iv$iv;
               Collection var82 = destination$iv$iv;
               int var76/* $VF was: $i$a$-map-CliEngine$computeSuggestions$5 */ = 0;
               var82.add(new CliEngine.Suggestion(head + " " + var72, head + " " + var72, "toggle"));
            }

            return (List<CliEngine.Suggestion>)destination$iv$iv;
         }

         CliEngine var28 = this;

         List<CliEngine.Suggestion> var29 = null;
         try {
            int var41/* $VF was: $i$a$-runCatching-CliEngine$computeSuggestions$6 */ = 0;
            String var89 = rest.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var89, "toLowerCase(...)");
            String q = var89;
            Iterable var51 = var28.listProfiles();
            int $i$f$filter = 0;
            Iterable var63 = var51;
            var destination$iv$iv = new ArrayList();
            int $i$f$filterTo = 0;

            for (Object element$iv$iv : var63) {
               String it = (String)element$iv$iv;
               int var19/* $VF was: $i$a$-filter-CliEngine$computeSuggestions$6$1 */ = 0;
               var89 = it.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(var89, "toLowerCase(...)");
               if (StringsKt.startsWith(var89, q, false)) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            var51 = (Iterable & List)destination$iv$iv;
            $i$f$filter = 0;
            var63 = var51;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var51, 10));
            $i$f$filterTo = 0;

            for (Object item$iv$iv : var63) {
               String var80 = (String)item$iv$iv;
               Collection var20 = destination$iv$iv;
               int var81/* $VF was: $i$a$-map-CliEngine$computeSuggestions$6$2 */ = 0;
               var20.add(new CliEngine.Suggestion(head + " " + var80, head + " " + var80, Intrinsics.areEqual(head, "load") ? "Load profile" : "Delete profile"));
            }

            var29 = (List<CliEngine.Suggestion>)CollectionsKt.take((Iterable & List)destination$iv$iv, 10);
         } catch (Throwable var22) {
            var29 = null;
         }

         return var29 != null ? var29 : CollectionsKt.emptyList();
      }
   }

   private final List<CliEngine.Suggestion> cbSuggestions(String rest) {
      int nextSpace = StringsKt.indexOf(rest, ' ', 0, false);
      if (nextSpace < 0) {
         String var115 = rest.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var115, "toLowerCase(...)");
         String q = var115;
         Iterable var22 = CollectionsKt.distinct(CB_SUBCOMMANDS);
         int $i$f$filter = 0;
         Iterable var26 = var22;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;

         for (Object element$iv$iv : var26) {
            String it = (String)element$iv$iv;
            int var73/* $VF was: $i$a$-filter-CliEngine$cbSuggestions$1 */ = 0;
            if (StringsKt.startsWith(it, q, false)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var22 = (Iterable & List)destination$iv$iv;
         $i$f$filter = 0;
         var26 = var22;
         destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var22, 10));
         $i$f$filterTo = 0;

         for (Object item$iv$iv : var26) {
            String var64 = (String)item$iv$iv;
            Collection var107 = destination$iv$iv;
            int var74/* $VF was: $i$a$-map-CliEngine$cbSuggestions$2 */ = 0;
            var107.add(new CliEngine.Suggestion("cb " + var64, "cb " + var64, ""));
         }

         return CollectionsKt.take(destination$iv$iv, 10);
      } else {
         String args;
         String lastToken;
         String prefix;
         label270: {
            String var10000 = rest.substring(0, nextSpace);
            Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
            var10000 = var10000.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
            String sub = var10000;
            var10000 = rest.substring(nextSpace + 1);
            Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
            args = var10000;
            var10000 = StringsKt.substringAfterLast(args, ' ', args).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
            lastToken = var10000;
            String argsBeforeLast = StringsKt.substringBeforeLast(args, ' ', "");
            prefix = argsBeforeLast.length() == 0 ? "cb " + sub : "cb " + sub + " " + argsBeforeLast;
            String $i$f$mapTo = sub;
            switch ($i$f$mapTo.hashCode()) {
               case -1785238953:
                  if ($i$f$mapTo.equals("favorites")) {
                     return this.favSuggestions(args, prefix);
                  }

                  return CollectionsKt.emptyList();
               case -1335458389:
                  if (!$i$f$mapTo.equals("delete")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 98:
                  if ($i$f$mapTo.equals("b")) {
                     return this.blockSuggestions(lastToken, prefix, "Brush");
                  }

                  return CollectionsKt.emptyList();
               case 102:
                  if ($i$f$mapTo.equals("f")) {
                     return this.blockSuggestions(lastToken, prefix, "Fill");
                  }

                  return CollectionsKt.emptyList();
               case 114:
                  if (!$i$f$mapTo.equals("r")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               case 99339:
                  if (!$i$f$mapTo.equals("del")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 101147:
                  if ($i$f$mapTo.equals("fav")) {
                     return this.favSuggestions(args, prefix);
                  }

                  return CollectionsKt.emptyList();
               case 112797:
                  if (!$i$f$mapTo.equals("rep")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               case 3143043:
                  if ($i$f$mapTo.equals("fill")) {
                     return this.blockSuggestions(lastToken, prefix, "Fill");
                  }

                  return CollectionsKt.emptyList();
               case 3327206:
                  if (!$i$f$mapTo.equals("load")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 3506395:
                  if ($i$f$mapTo.equals("room")) {
                     String[] var34 = new String[]{"info", "clear"};
                     Iterable var35 = CollectionsKt.listOf(var34);
                     int $i$f$filter = 0;
                     Iterable var54 = var35;
                     var destination$iv$iv = new ArrayList();
                     int $i$f$filterTo = 0;

                     for (Object element$iv$iv : var54) {
                        String it = (String)element$iv$iv;
                        int var100/* $VF was: $i$a$-filter-CliEngine$cbSuggestions$7 */ = 0;
                        if (StringsKt.startsWith(it, lastToken, false)) {
                           destination$iv$iv.add(element$iv$iv);
                        }
                     }

                     var var36 = (Iterable & List)destination$iv$iv;
                     $i$f$filter = 0;
                     var54 = var36;
                     destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var36, 10));
                     $i$f$filterTo = 0;

                     for (Object item$iv$iv : var54) {
                        String var95 = (String)item$iv$iv;
                        Collection var105 = destination$iv$iv;
                        int var101/* $VF was: $i$a$-map-CliEngine$cbSuggestions$8 */ = 0;
                        var105.add(new CliEngine.Suggestion(prefix + " " + var95, prefix + " " + var95, "Room"));
                     }

                     return (List<CliEngine.Suggestion>)destination$iv$iv;
                  }

                  return CollectionsKt.emptyList();
               case 93832333:
                  if ($i$f$mapTo.equals("block")) {
                     return this.blockSuggestions(lastToken, prefix, "Brush");
                  }

                  return CollectionsKt.emptyList();
               case 109399969:
                  if ($i$f$mapTo.equals("shape")) {
                     String[] $this$map$iv = new String[]{"none", "fence", "wall"};
                     Iterable var32 = CollectionsKt.listOf($this$map$iv);
                     int $i$f$filter = 0;
                     Iterable $this$mapTo$iv$iv = var32;
                     var destination$iv$iv = new ArrayList();
                     int $i$f$filterTo = 0;

                     for (Object element$iv$iv : $this$mapTo$iv$iv) {
                        String it = (String)element$iv$iv;
                        int var17/* $VF was: $i$a$-filter-CliEngine$cbSuggestions$5 */ = 0;
                        if (StringsKt.startsWith(it, lastToken, false)) {
                           destination$iv$iv.add(element$iv$iv);
                        }
                     }

                     var var33 = (Iterable & List)destination$iv$iv;
                     $i$f$filter = 0;
                     $this$mapTo$iv$iv = var33;
                     destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var33, 10));
                     $i$f$filterTo = 0;

                     for (Object item$iv$iv : $this$mapTo$iv$iv) {
                        String var93 = (String)item$iv$iv;
                        Collection var19 = destination$iv$iv;
                        int var99/* $VF was: $i$a$-map-CliEngine$cbSuggestions$6 */ = 0;
                        var19.add(new CliEngine.Suggestion(prefix + " " + var93, prefix + " " + var93, "Shape"));
                     }

                     return (List<CliEngine.Suggestion>)destination$iv$iv;
                  }

                  return CollectionsKt.emptyList();
               case 1094496948:
                  if (!$i$f$mapTo.equals("replace")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               default:
                  return CollectionsKt.emptyList();
            }

            CliEngine var47 = this;

            List configs;
            try {
               int var70/* $VF was: $i$a$-runCatching-CliEngine$cbSuggestions$configs$1 */ = 0;
               configs = (List)CosmeticBlocks.INSTANCE.listConfigs();
            } catch (Throwable var20) {
               configs = CollectionsKt.emptyList();
            }

            Iterable $this$filter$iv = configs;
            int $i$f$filter = 0;
            Iterable var71 = $this$filter$iv;
            var destination$iv$iv = new ArrayList();
            int $i$f$filterTo = 0;

            for (Object element$iv$iv : var71) {
               boolean var114;
               label193: {
                  String it = (String)element$iv$iv;
                  int var18/* $VF was: $i$a$-filter-CliEngine$cbSuggestions$3 */ = 0;
                  if (lastToken.length() != 0) {
                     var10000 = it.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
                     if (!StringsKt.contains(var10000, lastToken, false)) {
                        var114 = false;
                        break label193;
                     }
                  }

                  var114 = true;
               }

               if (var114) {
                  destination$iv$iv.add(element$iv$iv);
               }
            }

            $this$filter$iv = (Iterable & List)destination$iv$iv;
            int $i$f$map = 0;
            var71 = $this$filter$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10));
            $i$f$filterTo = 0;

            for (Object item$iv$iv : var71) {
               String var103 = (String)item$iv$iv;
               Collection var106 = destination$iv$iv;
               int var104/* $VF was: $i$a$-map-CliEngine$cbSuggestions$4 */ = 0;
               var106.add(new CliEngine.Suggestion(prefix + " " + var103, prefix + " " + var103, Intrinsics.areEqual(sub, "load") ? "Load" : "Delete"));
            }

            return CollectionsKt.take(destination$iv$iv, 10);
         }

         String hint = StringsKt.trim(args).toString();
         Regex var56 = new Regex("\\s+");
         int count$iv = 0;
         var var45 = var56.split(hint, count$iv);
         int $i$f$count = 0;
         int var112;
         if (var45 instanceof Collection && ((Collection)var45).isEmpty()) {
            var112 = 0;
         } else {
            count$iv = 0;

            for (Object element$iv : var45) {
               String it = (String)element$iv;
               int var96/* $VF was: $i$a$-count-CliEngine$cbSuggestions$tokenCount$1 */ = 0;
               if (it.length() > 0) {
                  if (++count$iv < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }

            var112 = count$iv;
         }

         int tokenCount = var112;
         hint = tokenCount <= 1 ? "From" : "To";
         return this.blockSuggestions(lastToken, prefix, hint);
      }
   }

   private final List<CliEngine.Suggestion> blockSuggestions(String token, String prefix, String hint) {
      String q = StringsKt.replace(StringsKt.removePrefix(token, "minecraft:"), ' ', '_', false);
      Set<Identifier> var10000 = BuiltInRegistries.BLOCK.keySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "keySet(...)");
      return SequencesKt.toList(
         SequencesKt.map(
            SequencesKt.take(
               SequencesKt.filter(
                  SequencesKt.map(CollectionsKt.asSequence(var10000), it -> blockSuggestions$lambda$0(it)), it -> blockSuggestions$lambda$1(q, it)
               ),
               10
            ),
            it -> blockSuggestions$lambda$2(prefix, hint, it)
         )
      );
   }

   private final List<CliEngine.Suggestion> favSuggestions(String args, String prefix) {
      CharSequence var10000 = args;
      char[] favSub = new char[]{' '};
      List parts = StringsKt.split(var10000, favSub, false, 2);
      String var66 = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var66, "toLowerCase(...)");
      String favSubx = var66;
      var66 = (String)CollectionsKt.getOrNull(parts, 1);
      var66 = var66 != null ? StringsKt.trim(var66).toString() : null;
      if (var66 == null) {
         var66 = "";
      }

      String favArg = var66;
      if (parts.size() < 2) {
         String[] var21 = new String[]{"add", "remove", "rm", "list", "gui"};
         Iterable var22 = CollectionsKt.listOf(var21);
         int $i$f$filter = 0;
         Iterable var28 = var22;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;

         for (Object element$iv$iv : var28) {
            String it = (String)element$iv$iv;
            int var56/* $VF was: $i$a$-filter-CliEngine$favSuggestions$1 */ = 0;
            if (StringsKt.startsWith(it, favSubx, false)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var var23 = (Iterable & List)destination$iv$iv;
         $i$f$filter = 0;
         var28 = var23;
         destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var23, 10));
         $i$f$filterTo = 0;

         for (Object item$iv$iv : var28) {
            String var53 = (String)item$iv$iv;
            Collection var65 = destination$iv$iv;
            int var57/* $VF was: $i$a$-map-CliEngine$favSuggestions$2 */ = 0;
            var65.add(new CliEngine.Suggestion(prefix + " " + var53, prefix + " " + var53, "Favorites"));
         }

         return (List<CliEngine.Suggestion>)destination$iv$iv;
      } else {
         String $this$map$iv = favSubx;
         switch ($this$map$iv.hashCode()) {
            case -934610812:
               if (!$this$map$iv.equals("remove")) {
                  return CollectionsKt.emptyList();
               }
               break;
            case 3643:
               if (!$this$map$iv.equals("rm")) {
                  return CollectionsKt.emptyList();
               }
               break;
            case 96417:
               if ($this$map$iv.equals("add")) {
                  String var10001 = StringsKt.substringAfterLast(favArg, ' ', favArg).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
                  return this.blockSuggestions(var10001, prefix + " add", "Add fav");
               }

               return CollectionsKt.emptyList();
            default:
               return CollectionsKt.emptyList();
         }

         CliEngine q = this;

         List favs;
         try {
            int var10/* $VF was: $i$a$-runCatching-CliEngine$favSuggestions$favs$1 */ = 0;
            favs = (List)CosmeticBlocks.INSTANCE.getFavorites();
         } catch (Throwable var19) {
            favs = CollectionsKt.emptyList();
         }

         var66 = favArg.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var66, "toLowerCase(...)");
         String qx = var66;
         Iterable favsMapped = favs;
         int $i$f$map = 0;
         Iterable $this$mapTo$iv$iv = favsMapped;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(favsMapped, 10));
         int $i$f$mapTo = 0;

         for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String it = (String)item$iv$iv;
            Collection var18 = destination$iv$iv;
            int var17/* $VF was: $i$a$-map-CliEngine$favSuggestions$3 */ = 0;
            var18.add(StringsKt.removePrefix(it, "minecraft:"));
         }

         favsMapped = destination$iv$iv;
         $i$f$map = 0;
         $this$mapTo$iv$iv = favsMapped;
         destination$iv$iv = new ArrayList();
         $i$f$mapTo = 0;

         for (Object element$iv$iv : $this$mapTo$iv$iv) {
            String it = (String)element$iv$iv;
            int var62/* $VF was: $i$a$-filter-CliEngine$favSuggestions$4 */ = 0;
            if (qx.length() == 0 || StringsKt.contains(it, qx, false)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         favsMapped = destination$iv$iv;
         $i$f$map = 0;
         $this$mapTo$iv$iv = favsMapped;
         destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(favsMapped, 10));
         $i$f$mapTo = 0;

         for (Object item$iv$iv : $this$mapTo$iv$iv) {
            String var61 = (String)item$iv$iv;
            Collection var64 = destination$iv$iv;
            int var63/* $VF was: $i$a$-map-CliEngine$favSuggestions$5 */ = 0;
            var64.add(new CliEngine.Suggestion(prefix + " " + favSubx + " " + var61, var61, "Remove fav"));
         }

         return CollectionsKt.take(destination$iv$iv, 10);
      }
   }

   private final List<BooleanSetting> booleanSettings() {
      Iterable $this$flatMap$iv = Tabs.INSTANCE.getAll();
      int $i$f$flatMap = 0;
      Iterable $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$flatMapTo = 0;

      for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
         SettingsTab it = (SettingsTab)element$iv$iv;
         int var9/* $VF was: $i$a$-flatMap-CliEngine$booleanSettings$1 */ = 0;
         Iterable list$iv$iv = it.getSettings();
         CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
      }

      $this$flatMap$iv = (Iterable & List)destination$iv$iv;
      $i$f$flatMap = 0;
      $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
      destination$iv$iv = new ArrayList();
      $i$f$flatMapTo = 0;

      for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
         if (element$iv$iv instanceof BooleanSetting) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List<BooleanSetting>)destination$iv$iv;
   }

   private final List<KeybindSetting> keybindSettings() {
      Iterable $this$flatMap$iv = Tabs.INSTANCE.getAll();
      int $i$f$flatMap = 0;
      Iterable $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$flatMapTo = 0;

      for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
         SettingsTab it = (SettingsTab)element$iv$iv;
         int var9/* $VF was: $i$a$-flatMap-CliEngine$keybindSettings$1 */ = 0;
         Iterable list$iv$iv = it.getSettings();
         CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
      }

      $this$flatMap$iv = (Iterable & List)destination$iv$iv;
      $i$f$flatMap = 0;
      $this$filterIsInstanceTo$iv$iv = $this$flatMap$iv;
      destination$iv$iv = new ArrayList();
      $i$f$flatMapTo = 0;

      for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
         if (element$iv$iv instanceof KeybindSetting) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      return (List<KeybindSetting>)destination$iv$iv;
   }

   private final Long parseTimeString(String input) {
      String var10000 = input.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
      String str = StringsKt.trim(var10000).toString();
      Long totalMs = StringsKt.toLongOrNull(str);
      if (totalMs != null) {
         long it = totalMs.longValue();
         int var11/* $VF was: $i$a$-let-CliEngine$parseTimeString$1 */ = 0;
         return it * 1000;
      }

      long totalMsx = 0L;
      String currentNum = "";
      int var6 = 0;

      for (int var7 = str.length(); var6 < var7; var6++) {
         char c = str.charAt(var6);
         if (Character.isDigit(c)) {
            currentNum = currentNum + c;
         } else if (c == 'h') {
            Long var10001 = StringsKt.toLongOrNull(currentNum);
            if (var10001 == null) {
               return null;
            }

            totalMsx += var10001 * 3600 * 1000;
            currentNum = "";
         } else if (c == 'm') {
            Long var12 = StringsKt.toLongOrNull(currentNum);
            if (var12 == null) {
               return null;
            }

            totalMsx += var12 * 60 * 1000;
            currentNum = "";
         } else {
            if (c != 's') {
               return null;
            }

            Long var13 = StringsKt.toLongOrNull(currentNum);
            if (var13 == null) {
               return null;
            }

            totalMsx += var13 * 1000;
            currentNum = "";
         }
      }

      if (currentNum.length() > 0) {
         Long var14 = StringsKt.toLongOrNull(currentNum);
         if (var14 == null) {
            return null;
         }

         totalMsx += var14 * 1000;
      }

      return totalMsx > 0L ? totalMsx : null;
   }

   private final String formatDuration(long ms) {
      long totalSeconds = ms / 1000;
      long hours = totalSeconds / 3600;
      long minutes = totalSeconds % 3600 / 60;
      long seconds = totalSeconds % 60;
      return hours > 0L ? hours + "h " + minutes + "m" : (minutes > 0L ? minutes + "m " + seconds + "s" : seconds + "s");
   }

   private final Double evaluateCalcExpression(String expr) {
      Double var2;
      try {
         var2 = new CliEngine.CalcParser(expr).parse();
      } catch (Exception var4/* $VF was: <unused var> */) {
         var2 = null;
      }

      return var2;
   }

   private final String executeCliCommand(String input) {
      String cmd;
      String var131;
      label548: {
         CharSequence var10000 = input;
         String[] cmdx = new String[]{" "};
         List parts = StringsKt.split(var10000, cmdx, false, 2);
         String var129 = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var129, "toLowerCase(...)");
         cmd = var129;
                     var129 = (String)CollectionsKt.getOrNull(parts, 1);
         if (var129 != null) {
            var131 = StringsKt.trim(var129).toString();
            if (var131 != null) {
               break label548;
            }
         }

         var131 = "";
      }

      String arg;
      label668: {
         label669: {
            label670: {
               label671: {
                  label672: {
                     label673: {
                        arg = var131;
                        String var5 = cmd;
                        switch (var5.hashCode()) {
                           case -1405258946:
                              if (var5.equals("delprofile")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.deleteProfile(arg);
                                    var131 = success ? "Profile '" + arg + "' deleted" : "Profile not found";
                                 } else {
                                    var131 = "Usage: delprofile <profile name>";
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case -1289153612:
                              if (var5.equals("export")) {
                                 boolean success = this.exportToClipboard();
                                 return success ? "Settings exported to clipboard" : "Export failed";
                              }

                              return "Unknown command: " + cmd;
                           case -1184795739:
                              if (var5.equals("import")) {
                                 boolean success = this.importFromClipboard();
                                 return success ? "Settings imported from clipboard" : "Import failed";
                              }

                              return "Unknown command: " + cmd;
                           case -1002263574:
                              if (var5.equals("profiles")) {
                                 List profiles = this.listProfiles();
                                 return profiles.isEmpty()
                                    ? "No profiles saved"
                                    : "Profiles: " + CollectionsKt.joinToString(profiles, ", ", "", "", -1, "...", null);
                              }

                              return "Unknown command: " + cmd;
                           case -934616827:
                              if (!var5.equals("remind")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case -890346579:
                              if (!var5.equals("rmreminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label668;
                           case -868304044:
                              if (!var5.equals("toggle")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label671;
                           case -719093612:
                              if (var5.equals("clearnotes")) {
                                 return "Unknown command: " + cmd;
                              }

                              return "Unknown command: " + cmd;
                           case -715981186:
                              if (!var5.equals("coinflip")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case -518602638:
                              if (!var5.equals("reminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case -448690650:
                              if (var5.equals("noteexport")) {
                                 if (notes.isEmpty()) {
                                    var131 = "No notes to export";
                                 } else {
                                    String export = CollectionsKt.joinToString(notes, "\n", "", "", -1, "...", null);
                                    GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), export);
                                    var131 = "Exported " + notes.size() + " notes to clipboard";
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 0:
                              if (var5.equals("")) {
                                 return "";
                              }

                              return "Unknown command: " + cmd;
                           case 61:
                              if (!var5.equals("=")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 99:
                              if (!var5.equals("c")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 100:
                              if (!var5.equals("d")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 116:
                              if (!var5.equals("t")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label671;
                           case 119:
                              if (var5.equals("w")) {
                                 Config.INSTANCE.save();
                                 return "Settings saved";
                              }

                              return "Unknown command: " + cmd;
                           case 3167:
                              if (var5.equals("cb")) {
                                 return this.executeCbCommand(arg);
                              }

                              return "Unknown command: " + cmd;
                           case 101609:
                              if (var5.equals("fps")) {
                                 Minecraft var154 = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(var154, "getInstance(...)");
                                 Minecraft mc = var154;
                                 return "FPS: " + mc.getFps();
                              }

                              return "Unknown command: " + cmd;
                           case 3023933:
                              if (var5.equals("bind")) {
                                 String settingName;
                                 label451: {
                                    CharSequence var144 = arg;
                                    String[] var47 = new String[]{" "};
                                    List bindParts = StringsKt.split(var144, var47, false, 2);
                                    String var145 = ((String)bindParts.get(0)).toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(var145, "toLowerCase(...)");
                                    settingName = var145;
                                    var145 = (String)CollectionsKt.getOrNull(bindParts, 1);
                                    if (var145 != null) {
                                       var131 = var145.toUpperCase(Locale.ROOT);
                                       Intrinsics.checkNotNullExpressionValue(var131, "toUpperCase(...)");
                                       if (var131 != null) {
                                          break label451;
                                       }
                                    }

                                    var131 = "";
                                 }

                                 String keyName = var131;
                                 if (settingName.length() != 0) {
                                    if (keyName.length() == 0) {
                                       Iterable var86 = this.keybindSettings();
                                       Iterator var94 = var86.iterator();
                                       Object var149;

                                       while (true) {
                                          if (!var94.hasNext()) {
                                             var149 = null;
                                             break;
                                          }

                                          Object var100 = var94.next();
                                          KeybindSetting it = (KeybindSetting)var100;
                                          int var116/* $VF was: $i$a$-find-CliEngine$executeCliCommand$setting$2 */ = 0;
                                          var131 = it.getName().toLowerCase(Locale.ROOT);
                                          Intrinsics.checkNotNullExpressionValue(var131, "toLowerCase(...)");
                                          if (StringsKt.contains(var131, settingName, false)) {
                                             var149 = var100;
                                             break;
                                          }
                                       }

                                       KeybindSetting setting = (KeybindSetting)var149;
                                       if (setting != null) {
                                          int currentKey = setting.getValue().intValue();
                                          if (currentKey != -1 && currentKey > 0) {
                                             String keyChar = (32 <= currentKey ? currentKey < 127 : false)
                                                ? String.valueOf((char)currentKey)
                                                : "KEY_" + currentKey;
                                             var131 = "Current bind for '" + setting.getName() + "': " + keyChar;
                                          } else {
                                             var131 = "'" + setting.getName() + "' has no keybind";
                                          }

                                          return var131;
                                       } else {
                                          return "Setting not found: " + settingName;
                                       }
                                    } else {
                                       Integer var150;
                                       if (keyName.length() == 1) {
                                          var150 = Integer.valueOf(Character.toUpperCase(keyName.charAt(0)));
                                       } else {
                                          label408:
                                          if (StringsKt.startsWith(keyName, "F", false) && keyName.length() > 1) {
                                             var131 = keyName.substring(1);
                                             Intrinsics.checkNotNullExpressionValue(var131, "substring(...)");
                                             Integer fNum = StringsKt.toIntOrNull(var131);
                                             if (fNum != null) {
                                                int var88 = fNum;
                                                if (1 <= var88 ? var88 < 26 : false) {
                                                   var150 = 290 + fNum - 1;
                                                   break label408;
                                                }
                                             }

                                             var150 = null;
                                          } else {
                                             var150 = !Intrinsics.areEqual(keyName, "NONE") && !Intrinsics.areEqual(keyName, "UNBIND") ? null : 0;
                                          }
                                       }

                                       Integer keyCode = var150;
                                       if (keyCode != null) {
                                          Iterable var95 = this.keybindSettings();
                                          Iterator var101 = var95.iterator();
                                          Object var153;

                                          while (true) {
                                             if (!var101.hasNext()) {
                                                var153 = null;
                                                break;
                                             }

                                             Object var107 = var101.next();
                                             KeybindSetting it = (KeybindSetting)var107;
                                             int var125/* $VF was: $i$a$-find-CliEngine$executeCliCommand$setting$3 */ = 0;
                                             var131 = it.getName().toLowerCase(Locale.ROOT);
                                             Intrinsics.checkNotNullExpressionValue(var131, "toLowerCase(...)");
                                             if (StringsKt.contains(var131, settingName, false)) {
                                                var153 = var107;
                                                break;
                                             }
                                          }

                                          KeybindSetting setting = (KeybindSetting)var153;
                                          if (setting != null) {
                                             if (keyCode == 0) {
                                                setting.setValue(-1);
                                                Config.INSTANCE.save();
                                                var131 = "Unbound '" + setting.getName() + "'";
                                             } else {
                                                setting.setValue(keyCode);
                                                Config.INSTANCE.save();
                                                var131 = "Bound '" + setting.getName() + "' to " + keyName;
                                             }

                                             return var131;
                                          } else {
                                             return "Setting not found: " + settingName;
                                          }
                                       }

                                       return "Invalid key: " + keyName + " (use a-z, 0-9, f1-f12, or 'none')";
                                    }
                                 }

                                 return "Usage: bind <setting> <key> (e.g., bind 'edit mode' g)";
                              }

                              return "Unknown command: " + cmd;
                           case 3045973:
                              if (!var5.equals("calc")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 3059345:
                              if (!var5.equals("coin")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case 3083175:
                              if (!var5.equals("dice")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 3145837:
                              if (!var5.equals("flip")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case 3327206:
                              if (var5.equals("load")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.loadProfile(arg);
                                    var131 = success ? "Profile '" + arg + "' loaded" : "Profile not found";
                                 } else {
                                    var131 = "Usage: load <profile name>";
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 3387378:
                              if (var5.equals("note")) {
                                 if (arg.length() > 0) {
                                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                                    notes.add("[" + timestamp + "] " + arg);
                                    this.saveNotes();
                                    var131 = "Note saved (" + notes.size() + " total)";
                                 } else {
                                    var131 = "Usage: note <text>";
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 3441010:
                              if (var5.equals("ping")) {
                                 Minecraft var140 = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(var140, "getInstance(...)");
                                 Minecraft mc = var140;
                                 LocalPlayer player = mc.player;
                                 int var141;
                                 label379:
                                 if (player != null) {
                                    ClientPacketListener var142 = mc.getConnection();
                                    if (var142 != null) {
                                       PlayerInfo var143 = var142.getPlayerInfo(player.getUUID());
                                       if (var143 != null) {
                                          var141 = var143.getLatency();
                                          break label379;
                                       }
                                    }

                                    var141 = -1;
                                 } else {
                                    var141 = -1;
                                 }

                                 int ping = var141;
                                 return ping >= 0 ? "Ping: " + ping + "ms" : "Ping: N/A";
                              }

                              return "Unknown command: " + cmd;
                           case 3506301:
                              if (!var5.equals("roll")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 3522941:
                              if (var5.equals("save")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.saveProfile(arg);
                                    var131 = success ? "Profile '" + arg + "' saved" : "Failed to save profile";
                                 } else {
                                    var131 = "Usage: save <profile name>";
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 93742038:
                              if (!var5.equals("binds")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label672;
                           case 105008833:
                              if (var5.equals("notes")) {
                                 if (notes.isEmpty()) {
                                    var131 = "No notes saved";
                                 } else {
                                    List recent = CollectionsKt.reversed(CollectionsKt.takeLast(notes, 3));
                                    var131 = CollectionsKt.joinToString(recent, " | ", "", "", -1, "...", it -> CliEngine.executeCliCommand$lambda$0((String)it));
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 109250890:
                              if (var5.equals("scale")) {
                                 Minecraft var133 = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(var133, "getInstance(...)");
                                 Minecraft mc = var133;
                                 Window var134 = mc.getWindow();
                                 int screenW = var134 != null ? var134.getGuiScaledWidth() : 800;
                                 Window var135 = mc.getWindow();
                                 int screenH = var135 != null ? var135.getGuiScaledHeight() : 600;
                                 float baseWindowWidth = 450.0F;
                                 float baseWindowHeight = 350.0F;
                                 float maxScaleW = (screenW - 40) / baseWindowWidth;
                                 float maxScaleH = (screenH - 40) / baseWindowHeight;
                                 float maxScale = Math.min(maxScaleW, Math.min(maxScaleH, 2.0F));
                                 if (arg.length() > 0) {
                                    Float scale = StringsKt.toFloatOrNull(arg);
                                    if (scale != null) {
                                       float var108 = scale;
                                       if (0.5F <= var108 ? var108 <= maxScale : false) {
                                          String var111 = "%.1f";
                                          Object[] var122 = new Object[]{scale};
                                          var122 = var122;
                                          var131 = String.format(var111, Arrays.copyOf(var122, var122.length));
                                          Intrinsics.checkNotNullExpressionValue(var131, "format(...)");
                                          return "Scale set to " + var131 + " (apply in GUI)";
                                       }
                                    }

                                    if (scale != null && scale > maxScale) {
                                       String var110 = "%.1f";
                                       Object[] var120 = new Object[]{maxScale};
                                       var120 = var120;
                                       var131 = String.format(var110, Arrays.copyOf(var120, var120.length));
                                       Intrinsics.checkNotNullExpressionValue(var131, "format(...)");
                                       var131 = "Max scale for screen: " + var131;
                                    } else {
                                       String var109 = "%.1f";
                                       Object[] var118 = new Object[]{maxScale};
                                       var118 = var118;
                                       var131 = String.format(var109, Arrays.copyOf(var118, var118.length));
                                       Intrinsics.checkNotNullExpressionValue(var131, "format(...)");
                                       var131 = "Scale must be between 0.5 and " + var131;
                                    }

                                    return var131;
                                 } else {
                                    String var103 = "%.1f";
                                    Object[] var112 = new Object[]{maxScale};
                                    var112 = var112;
                                    var131 = String.format(var103, Arrays.copyOf(var112, var112.length));
                                    Intrinsics.checkNotNullExpressionValue(var131, "format(...)");
                                    return "Scale range: 0.5-" + var131;
                                 }
                              }

                              return "Unknown command: " + cmd;
                           case 110364485:
                              if (!var5.equals("timer")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case 503572695:
                              if (!var5.equals("keybinds")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label672;
                           case 1103187521:
                              if (var5.equals("reminders")) {
                                 if (reminders.isEmpty()) {
                                    var131 = "No active reminders";
                                 } else {
                                    long now = System.currentTimeMillis();
                                    Iterable $this$map$iv = CollectionsKt.take(reminders, 3);
                                    int $i$f$map = 0;
                                    Iterable destination$iv$iv = $this$map$iv;
                                    var destination$iv$ivx = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                    int $i$f$mapTo = 0;

                                    for (Object item$iv$iv : destination$iv$iv) {
                                       CliEngine.Reminder var16 = (CliEngine.Reminder)item$iv$iv;
                                       Collection var20 = destination$iv$ivx;
                                       int var17/* $VF was: $i$a$-map-CliEngine$executeCliCommand$list$1 */ = 0;
                                       long remaining = (var16.getTriggerTime() - now) / 1000;
                                       var20.add("#" + var16.getId() + ": " + INSTANCE.formatDuration(remaining * 1000));
                                    }

                                    List list = (List)destination$iv$ivx;
                                    var131 = "Reminders: " + CollectionsKt.joinToString(list, ", ", "", "", -1, "...", null);
                                 }

                                 return var131;
                              }

                              return "Unknown command: " + cmd;
                           case 1780471020:
                              if (!var5.equals("cancelreminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label668;
                           case 1784425883:
                              if (var5.equals("noteclear")) {
                                 return "Unknown command: " + cmd;
                              }

                              return "Unknown command: " + cmd;
                           case 1983864468:
                              if (var5.equals("clearreminders")) {
                                 int count = reminders.size();
                                 reminders.clear();
                                 return "Cleared " + count + " reminders";
                              }

                              return "Unknown command: " + cmd;
                           default:
                              return "Unknown command: " + cmd;
                        }

                        if (arg.length() > 0) {
                           Double result = this.evaluateCalcExpression(arg);
                           if (result != null) {
                              if (Intrinsics.areEqual(result, (long)result.doubleValue()) && Math.abs(result) < 9.223372E18F) {
                                 var131 = String.valueOf((long)result.doubleValue());
                              } else {
                                 String var60 = "%.10f";
                                 Object[] var71 = new Object[]{result};
                                 var71 = var71;
                                 var131 = String.format(var60, Arrays.copyOf(var71, var71.length));
                                 Intrinsics.checkNotNullExpressionValue(var131, "format(...)");
                                 char[] var61 = new char[]{'0'};
                                 var131 = StringsKt.trimEnd(var131, var61);
                                 char[] var62 = new char[]{'.'};
                                 var131 = StringsKt.trimEnd(var131, var62);
                              }

                              String formatted = var131;
                              GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), formatted);
                              var131 = arg + " = " + formatted + " (copied)";
                           } else {
                              var131 = "Invalid expression";
                           }

                           return var131;
                        } else {
                           return "Usage: calc <expr>";
                        }
                     }

                     if (arg.length() > 0) {
                        String var161 = arg;
                        String[] var53 = new String[]{" "};
                        List timeParts = StringsKt.split(var161, var53, false, 2);
                        String timeStr = (String)timeParts.get(0);
                        var161 = (String)CollectionsKt.getOrNull(timeParts, 1);
                        if (var161 == null) {
                           var161 = "Reminder!";
                        }

                        String message = var161;
                        Long delayMs = this.parseTimeString(timeStr);
                        if (delayMs != null && delayMs > 0L) {
                           int id = this.addReminder(delayMs, message);
                           String timeFormatted = this.formatDuration(delayMs);
                           var161 = "Reminder #" + id + " set for " + timeFormatted;
                        } else {
                           var161 = "Invalid time. Use: 30s, 5m, 1h, 1h30m";
                        }

                        return var161;
                     } else {
                        return "Usage: remind <time> [message]";
                     }
                  }

                  Iterable $this$filter$iv = this.keybindSettings();
                  int $i$f$filter = 0;
                  Iterable var67 = $this$filter$iv;
                  var destination$iv$iv = new ArrayList();
                  int $i$f$filterTo = 0;

                  for (Object element$iv$iv : var67) {
                     KeybindSetting it = (KeybindSetting)element$iv$iv;
                     int var114/* $VF was: $i$a$-filter-CliEngine$executeCliCommand$bound$1 */ = 0;
                     if (it.getValue().intValue() != -1 && it.getValue().intValue() > 0) {
                        destination$iv$iv.add(element$iv$iv);
                     }
                  }

                  List bound = (List)destination$iv$iv;
                  if (bound.isEmpty()) {
                     var131 = "No keybinds set";
                  } else {
                     Iterable $this$map$iv = CollectionsKt.take(bound, 5);
                     int $i$f$map = 0;
                     Iterable var77 = $this$map$iv;
                     var destination$iv$ivx = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                     int $i$f$mapTo = 0;

                     for (Object item$iv$iv : var77) {
                        KeybindSetting var115 = (KeybindSetting)item$iv$iv;
                        Collection var128 = destination$iv$ivx;
                        int var124/* $VF was: $i$a$-map-CliEngine$executeCliCommand$bindList$1 */ = 0;
                        int v = var115.getValue().intValue();
                        String keyChar = (32 <= v ? v < 127 : false) ? String.valueOf((char)v) : "F" + (v - 290 + 1);
                        var128.add(var115.getName() + "=" + keyChar);
                     }

                     $this$filter$iv = (List)destination$iv$ivx;
                     var131 = CollectionsKt.joinToString($this$filter$iv, ", ", "", "", -1, "...", null);
                  }

                  return var131;
               }

               if (arg.length() > 0) {
                  Iterable var63 = this.booleanSettings();
                  Iterator var73 = var63.iterator();
                  Object var160;

                  while (true) {
                     if (!var73.hasNext()) {
                        var160 = null;
                        break;
                     }

                     Object var81 = var73.next();
                     BooleanSetting it = (BooleanSetting)var81;
                     int var96/* $VF was: $i$a$-find-CliEngine$executeCliCommand$setting$1 */ = 0;
                     var131 = it.getName().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var131, "toLowerCase(...)");
                     String var10001 = arg.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var10001, "toLowerCase(...)");
                     if (Intrinsics.areEqual(var131, var10001)) {
                        var160 = var81;
                        break;
                     }
                  }

                  BooleanSetting setting = (BooleanSetting)var160;
                  if (setting != null) {
                     boolean newValue = !setting.getValue();
                     setting.setValue(newValue);
                     String status = newValue ? "§aON" : "§cOFF";
                     Config.INSTANCE.save();
                     var131 = setting.getName() + ": " + status;
                  } else {
                     var131 = "Feature not found: " + arg;
                  }

                  return var131;
               } else {
                  return "Usage: t <feature name>";
               }
            }

            String result = Math.random() < 0.5 ? "Heads" : "Tails";
            return "Coin flip: " + result;
         }

         Integer var155 = StringsKt.toIntOrNull(arg);
         int max = var155 != null ? var155 : 6;
         if (max < 1) {
            var131 = "Max must be at least 1";
         } else {
            int result = RangesKt.random(new IntRange(1, max), Random.Default);
            var131 = "Roll (1-" + max + "): " + result;
         }

         return var131;
      }

      Integer id = StringsKt.toIntOrNull(arg);
      if (id != null) {
         boolean success = this.cancelReminder(id);
         var131 = success ? "Reminder #" + id + " cancelled" : "Reminder #" + id + " not found";
      } else {
         var131 = "Usage: cancelreminder <id>";
      }

      return var131;
   }

   private final String executeCbCommand(String arg) {
      String subCmd;
      String var66;
      label500: {
         CharSequence var10000 = arg;
         String[] subCmdx = new String[]{" "};
         List cbParts = StringsKt.split(var10000, subCmdx, false, 2);
         String var64 = ((String)cbParts.get(0)).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(var64, "toLowerCase(...)");
         subCmd = var64;
         var64 = (String)CollectionsKt.getOrNull(cbParts, 1);
         if (var64 != null) {
            var66 = StringsKt.trim(var64).toString();
            if (var66 != null) {
               break label500;
            }
         }

         var66 = "";
      }

      label616: {
         String cbArg;
         label617: {
            label618: {
               label619: {
                  label620: {
                     label621: {
                        label622: {
                           label623: {
                              cbArg = var66;
                              String var5 = subCmd;
                              switch (var5.hashCode()) {
                                 case -1785238953:
                                    if (!var5.equals("favorites")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label620;
                                 case -1715965556:
                                    if (var5.equals("selection")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -1396673086:
                                    if (var5.equals("backup")) {
                                       return ConfigBackup.INSTANCE.backup();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -1352294148:
                                    if (var5.equals("create")) {
                                       return cbArg.length() > 0
                                          ? (
                                             CosmeticBlocks.INSTANCE.createConfig(cbArg)
                                                ? "Created config '" + cbArg + "'"
                                                : "Config '" + cbArg + "' already exists"
                                          )
                                          : "Usage: cb create <name>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -1335458389:
                                    if (!var5.equals("delete")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0
                                       ? (CosmeticBlocks.INSTANCE.deleteConfig(cbArg) ? "Deleted config '" + cbArg + "'" : "Cannot delete '" + cbArg + "'")
                                       : "Usage: cb delete <name>";
                                 case -934641255:
                                    if (var5.equals("reload")) {
                                       CosmeticBlocks.INSTANCE.load();
                                       return "Reloaded (" + CosmeticBlocks.INSTANCE.getReplacementCount() + " in dimension)";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -934610812:
                                    if (!var5.equals("remove")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break;
                                 case -926053069:
                                    if (var5.equals("properties")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 0:
                                    if (var5.equals("")) {
                                       int count;
                                       String mode;
                                       label334: {
                                          count = CosmeticBlocks.INSTANCE.getReplacementCount();
                                          mode = CosmeticBlocks.INSTANCE.getEditMode() ? "ON" : "OFF";
                                          ScannedRoom var81 = RoomScanner.INSTANCE.getCurrentRoom();
                                          if (var81 != null) {
                                             ScannedRoom it = var81;
                                             int var13/* $VF was: $i$a$-let-CliEngine$executeCbCommand$roomInfo$1 */ = 0;
                                             var66 = " | Room: " + it.getData().getName();
                                             if (var66 != null) {
                                                break label334;
                                             }
                                          }

                                          var66 = "";
                                       }

                                       String roomInfo = var66;
                                       return "CB: "
                                          + count
                                          + " replacements, edit: "
                                          + mode
                                          + ", config: "
                                          + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue()
                                          + roomInfo;
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 98:
                                    if (!var5.equals("b")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label617;
                                 case 102:
                                    if (!var5.equals("f")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0 ? CosmeticBlocks.INSTANCE.fillRegion(cbArg) : "Usage: cb fill <block[:weight]...>";
                                 case 114:
                                    if (!var5.equals("r")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 case 117:
                                    if (var5.equals("u")) {
                                       return CosmeticBlocks.INSTANCE.undoLastOperation();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3240:
                                    if (!var5.equals("em")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label618;
                                 case 3463:
                                    if (!var5.equals("ls")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label616;
                                 case 3521:
                                    if (!var5.equals("p1")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label623;
                                 case 3522:
                                    if (!var5.equals("p2")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label622;
                                 case 3643:
                                    if (!var5.equals("rm")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break;
                                 case 3681:
                                    if (!var5.equals("st")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label619;
                                 case 99339:
                                    if (!var5.equals("del")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0
                                       ? (CosmeticBlocks.INSTANCE.deleteConfig(cbArg) ? "Deleted config '" + cbArg + "'" : "Cannot delete '" + cbArg + "'")
                                       : "Usage: cb delete <name>";
                                 case 101147:
                                    if (!var5.equals("fav")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label620;
                                 case 102715:
                                    if (var5.equals("gui")) {
                                       Scheduler.INSTANCE.scheduleTickEnd(CliEngine::executeCbCommand$lambda$6);
                                       return "Opening block selector...";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 112797:
                                    if (!var5.equals("rep")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 case 113754:
                                    if (var5.equals("sel")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3108362:
                                    if (!var5.equals("edit")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label618;
                                 case 3143043:
                                    if (!var5.equals("fill")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0 ? CosmeticBlocks.INSTANCE.fillRegion(cbArg) : "Usage: cb fill <block[:weight]...>";
                                 case 3322014:
                                    if (!var5.equals("list")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label616;
                                 case 3327206:
                                    if (var5.equals("load")) {
                                       return cbArg.length() > 0
                                          ? (
                                             CosmeticBlocks.INSTANCE.loadConfigByName(cbArg)
                                                ? "Loaded config '" + cbArg + "'"
                                                : "Config '" + cbArg + "' not found"
                                          )
                                          : "Usage: cb load <name>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3446877:
                                    if (!var5.equals("pos1")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label623;
                                 case 3446878:
                                    if (!var5.equals("pos2")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label622;
                                 case 3506395:
                                    if (var5.equals("room")) {
                                       label626: {
                                          CharSequence var69 = cbArg;
                                          String[] mode = new String[]{" "};
                                          List roomParts = StringsKt.split(var69, mode, false, 2);
                                          String var70 = ((String)roomParts.get(0)).toLowerCase(Locale.ROOT);
                                          Intrinsics.checkNotNullExpressionValue(var70, "toLowerCase(...)");
                                          String roomCmd = var70;
                                          String roomInfo = roomCmd;
                                          switch (roomInfo.hashCode()) {
                                             case 0:
                                                if (!roomInfo.equals("")) {
                                                   return "Usage: cb room [info|clear|clearall|list|scan]";
                                                }
                                                break;
                                             case 3463:
                                                if (!roomInfo.equals("ls")) {
                                                   return "Usage: cb room [info|clear|clearall|list|scan]";
                                                }
                                                break label626;
                                             case 3237038:
                                                if (!roomInfo.equals("info")) {
                                                   return "Usage: cb room [info|clear|clearall|list|scan]";
                                                }
                                                break;
                                             case 3322014:
                                                if (!roomInfo.equals("list")) {
                                                   return "Usage: cb room [info|clear|clearall|list|scan]";
                                                }
                                                break label626;
                                             case 3524221:
                                                if (roomInfo.equals("scan")) {
                                                   ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
                                                   if (room != null) {
                                                      RoomInfo var71 = room.getData();
                                                      if (var71 != null) {
                                                         var70 = var71.getName();
                                                         if (var70 != null) {
                                                            return "Room: " + var70;
                                                         }
                                                      }
                                                   }

                                                   return "Room: " + "Unknown";
                                                }

                                                return "Usage: cb room [info|clear|clearall|list|scan]";
                                             case 94746189:
                                                if (roomInfo.equals("clear")) {
                                                   ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
                                                   if (room != null) {
                                                      int count = CosmeticBlocks.INSTANCE.clearRoom(room.getData().getName());
                                                      var70 = "Cleared " + count + " blocks from " + room.getData().getName();
                                                   } else {
                                                      var70 = "Not in a scanned room";
                                                   }

                                                   return var70;
                                                }

                                                return "Usage: cb room [info|clear|clearall|list|scan]";
                                             case 790299700:
                                                if (roomInfo.equals("clearall")) {
                                                   int count = CosmeticBlocks.INSTANCE.clearAllRooms();
                                                   return "Cleared " + count + " blocks from all rooms";
                                                }

                                                return "Usage: cb room [info|clear|clearall|list|scan]";
                                             default:
                                                return "Usage: cb room [info|clear|clearall|list|scan]";
                                          }

                                          ScannedRoom room = RoomScanner.INSTANCE.getCurrentRoom();
                                          if (room != null) {
                                             int blockCount = CosmeticBlocks.INSTANCE.getRoomBlockCount(room.getData().getName());
                                             var70 = "Room: " + room.getData().getName() + " | Rot: " + room.getRotation() + " | Blocks: " + blockCount;
                                          } else {
                                             var70 = "Not in a scanned dungeon room";
                                          }

                                          return var70;
                                       }

                                       List rooms = CosmeticBlocks.INSTANCE.listRoomNames();
                                       return rooms.isEmpty()
                                          ? "No room blocks saved"
                                          : CollectionsKt.joinToString(rooms, ", ", "", "", -1, "...", it -> CliEngine.executeCbCommand$lambda$5((String)it));
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3522941:
                                    if (var5.equals("save")) {
                                       CosmeticBlocks.INSTANCE.save();
                                       return "CB data saved";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3594468:
                                    if (var5.equals("undo")) {
                                       return CosmeticBlocks.INSTANCE.undoLastOperation();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 93832333:
                                    if (!var5.equals("block")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label617;
                                 case 94746189:
                                    if (var5.equals("clear")) {
                                       int count = CosmeticBlocks.INSTANCE.clearDimension();
                                       return "Cleared " + count + " replacements";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 106940784:
                                    if (var5.equals("props")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 109399969:
                                    if (var5.equals("shape")) {
                                       if (cbArg.length() == 0) {
                                          var66 = CosmeticBlocks.INSTANCE.getCurrentShape();
                                          if (var66 == null) {
                                             var66 = "none";
                                          }
                                       } else if (!Intrinsics.areEqual(cbArg, "none") && !Intrinsics.areEqual(cbArg, "off")) {
                                          String[] var16 = new String[]{"fence", "wall", "pane"};
                                          if (CollectionsKt.listOf(var16).contains(cbArg)) {
                                             CosmeticBlocks.INSTANCE.setCurrentShape(cbArg);
                                             var66 = "Shape: " + cbArg + " (next blocks will use this shape)";
                                          } else {
                                             var66 = "Shapes: fence, wall, pane, none";
                                          }

                                          return var66;
                                       } else {
                                          CosmeticBlocks.INSTANCE.setCurrentShape(null);
                                          return "Shape override cleared";
                                       }

                                       return "Shape: " + var66;
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 109757585:
                                    if (!var5.equals("state")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label619;
                                 case 790299700:
                                    if (var5.equals("clearall")) {
                                       int count = CosmeticBlocks.INSTANCE.clearAll();
                                       return "Cleared " + count + " total replacements";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 951117169:
                                    if (var5.equals("configs")) {
                                       List configs = CosmeticBlocks.INSTANCE.listConfigs();
                                       return "Configs: "
                                          + CollectionsKt.joinToString(configs, ", ", "", "", -1, "...", null)
                                          + " (active: "
                                          + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue()
                                          + ")";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 1094496948:
                                    if (!var5.equals("replace")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 default:
                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                              }

                              String var83 = cbArg;
                              String[] var39 = new String[]{" "};
                              List coords = StringsKt.split(var83, var39, false, 0);
                              if (coords.size() >= 3) {
                                 Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                                 Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                                 Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                                 var83 = x != null && y != null && z != null
                                    ? (
                                       CosmeticBlocks.INSTANCE.removeAt(x, y, z)
                                          ? "Removed at " + x + "," + y + "," + z
                                          : "No replacement at " + x + "," + y + "," + z
                                    )
                                    : "Invalid coords";
                              } else {
                                 var83 = "Usage: cb remove <x> <y> <z>";
                              }

                              return var83;
                           }

                           String var77 = cbArg;
                           String[] var33 = new String[]{" "};
                           List coords = StringsKt.split(var77, var33, false, 0);
                           if (coords.size() >= 3) {
                              Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                              Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                              Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                              if (x != null && y != null && z != null) {
                                 CosmeticBlocks.INSTANCE.setPos1(new BlockPos(x, y, z));
                                 var77 = "Pos1: " + x + ", " + y + ", " + z;
                              } else {
                                 var77 = "Invalid coordinates";
                              }

                              return var77;
                           } else {
                              BlockPos pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                              if (pos != null) {
                                 CosmeticBlocks.INSTANCE.setPos1(pos);
                                 var77 = "Pos1: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                              } else {
                                 var77 = "Look at a block first, then open CLI. Or: cb pos1 <x> <y> <z>";
                              }

                              return var77;
                           }
                        }

                        String var76 = cbArg;
                        String[] var30 = new String[]{" "};
                        List coords = StringsKt.split(var76, var30, false, 0);
                        if (coords.size() >= 3) {
                           Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                           Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                           Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                           if (x != null && y != null && z != null) {
                              CosmeticBlocks.INSTANCE.setPos2(new BlockPos(x, y, z));
                              var76 = "Pos2: " + x + ", " + y + ", " + z;
                           } else {
                              var76 = "Invalid coordinates";
                           }

                           return var76;
                        } else {
                           BlockPos pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                           if (pos != null) {
                              CosmeticBlocks.INSTANCE.setPos2(pos);
                              var76 = "Pos2: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                           } else {
                              var76 = "Look at a block first, then open CLI. Or: cb pos2 <x> <y> <z>";
                           }

                           return var76;
                        }
                     }

                     String from;
                     label403: {
                        CharSequence var78 = cbArg;
                        String[] var36 = new String[]{" "};
                        List rParts = StringsKt.split(var78, var36, false, 2);
                        from = (String)rParts.get(0);
                        var78 = (String)CollectionsKt.getOrNull(rParts, 1);
                        if (var78 != null) {
                           var66 = StringsKt.trim(var78).toString();
                           if (var66 != null) {
                              break label403;
                           }
                        }

                        var66 = "";
                     }

                     String to = var66;
                     if (from.length() > 0 && to.length() > 0) {
                        return CosmeticBlocks.INSTANCE.replaceRegion(from, to);
                     }

                     return "Usage: cb replace <from> <to[:weight]...>";
                  }

                  String favCmd;
                  label392: {
                     CharSequence var84 = cbArg;
                     String[] var41 = new String[]{" "};
                     List favParts = StringsKt.split(var84, var41, false, 2);
                     String var85 = ((String)favParts.get(0)).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(var85, "toLowerCase(...)");
                     favCmd = var85;
                     var85 = (String)CollectionsKt.getOrNull(favParts, 1);
                     if (var85 != null) {
                        var66 = StringsKt.trim(var85).toString();
                        if (var66 != null) {
                           break label392;
                        }
                     }

                     var66 = "";
                  }

                  String favArg = var66;
                  if (favCmd.length() != 0 && !Intrinsics.areEqual(favCmd, "gui")) {
                     if (Intrinsics.areEqual(favCmd, "add")) {
                        CharSequence var61 = favArg;
                        if (var61.length() == 0) {
                           int var11/* $VF was: $i$a$-ifEmpty-CliEngine$executeCbCommand$block$1 */ = 0;
                           var66 = CosmeticBlocks.INSTANCE.getSelectedBlock();
                        } else {
                            var66 = var61.toString();
                        }

                        String block = var66;
                        return CosmeticBlocks.INSTANCE.addFavorite(block)
                           ? "Added " + StringsKt.removePrefix(block, "minecraft:") + " to favorites"
                           : "Already in favorites or invalid block";
                     } else if (Intrinsics.areEqual(favCmd, "remove") || Intrinsics.areEqual(favCmd, "rm")) {
                        CharSequence var62 = favArg;
                        if (var62.length() == 0) {
                           int var63/* $VF was: $i$a$-ifEmpty-CliEngine$executeCbCommand$block$2 */ = 0;
                           var66 = CosmeticBlocks.INSTANCE.getSelectedBlock();
                        } else {
                           var66 = var62.toString();
                        }

                        String block = var66;
                        return CosmeticBlocks.INSTANCE.removeFavorite(block)
                           ? "Removed " + StringsKt.removePrefix(block, "minecraft:") + " from favorites"
                           : "Not in favorites";
                     } else {
                        if (!Intrinsics.areEqual(favCmd, "list") && !Intrinsics.areEqual(favCmd, "ls")) {
                           var66 = "Usage: cb fav [add|remove|list|gui]";
                        } else {
                           List favs = CosmeticBlocks.INSTANCE.getFavorites();
                           var66 = favs.isEmpty()
                              ? "No favorites"
                              : CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", it -> CliEngine.executeCbCommand$lambda$4((String)it));
                        }

                        return var66;
                     }
                  }

                  Scheduler.INSTANCE.scheduleTickEnd(CliEngine::executeCbCommand$lambda$1);
                  return "Opening favorites...";
               }

               String property;
               label346: {
                  CharSequence var73 = cbArg;
                  String[] var28 = new String[]{" "};
                  List stateParts = StringsKt.split(var73, var28, false, 2);
                  property = (String)stateParts.get(0);
                  var73 = (String)CollectionsKt.getOrNull(stateParts, 1);
                  if (var73 != null) {
                     var66 = StringsKt.trim(var73).toString();
                     if (var66 != null) {
                        break label346;
                     }
                  }

                  var66 = "";
               }

               String value = var66;
               if (property.length() > 0 && value.length() > 0) {
                  CosmeticBlocks.INSTANCE.setBlockProperty(property, value);
                  return "Brush: " + StringsKt.removePrefix(CosmeticBlocks.INSTANCE.getSelectedBlock(), "minecraft:");
               }

               return "Usage: cb state <property> <value>";
            }

            CosmeticBlocks.INSTANCE.toggleEditMode();
            return CosmeticBlocks.INSTANCE.getEditMode() ? "CB edit mode ON" : "CB edit mode OFF";
         }

         if (cbArg.length() > 0) {
            CosmeticBlocks.INSTANCE.setBlock(StringsKt.replace(cbArg, " ", "_", false));
            var66 = "Brush: " + StringsKt.replace(cbArg, " ", "_", false);
         } else {
            var66 = "Usage: cb block <block_id>";
         }

         return var66;
      }

      List list = CosmeticBlocks.INSTANCE.listReplacements();
      return list.isEmpty()
         ? "No CB replacements"
         : CollectionsKt.joinToString(CollectionsKt.take(list, 5), " | ", "", "", -1, "...", it -> CliEngine.executeCbCommand$lambda$0((String)it));
   }

   private final boolean exportToClipboard() {
      boolean sb;
      try {
         StringBuilder sbx = new StringBuilder();
         sbx.append("# CB Settings Export").append('\n');
         Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$iv) {
            SettingsTab tab = (SettingsTab)element$iv;
            int var7/* $VF was: $i$a$-forEach-CliEngine$exportToClipboard$1 */ = 0;
            Iterable $this$forEach$ivx = tab.getSettings();
            int $i$f$forEachx = 0;

            for (Object element$ivx : $this$forEach$ivx) {
               Setting setting = (Setting)element$ivx;
               int var13/* $VF was: $i$a$-forEach-CliEngine$exportToClipboard$1$1 */ = 0;
               sbx.append(tab.getName() + "." + setting.getName() + "=" + INSTANCE.valueToString(setting)).append('\n');
            }
         }

         GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), sbx.toString());
         sb = true;
      } catch (Exception e) {
         e.printStackTrace();
         sb = false;
      }

      return sb;
   }

   private final boolean importFromClipboard() {
      boolean data;
      try {
         String var10000 = GLFW.glfwGetClipboardString(GLFW.glfwGetCurrentContext());
         if (var10000 == null) {
            return false;
         }

         String datax = var10000;
         int imported = 0;
         Iterable $this$forEach$iv = StringsKt.lines(datax);
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$iv) {
            String line = (String)element$iv;
            int var8/* $VF was: $i$a$-forEach-CliEngine$importFromClipboard$1 */ = 0;
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               CharSequence var29 = line;
               String[] keyParts = new String[]{"="};
               List parts = StringsKt.split(var29, keyParts, false, 2);
               if (parts.size() == 2) {
                  var29 = (CharSequence)parts.get(0);
                  String[] tabName = new String[]{"."};
                  List keyPartsx = StringsKt.split(var29, tabName, false, 2);
                  if (keyPartsx.size() == 2) {
                     String tabNamex = (String)keyPartsx.get(0);
                     String propName = (String)keyPartsx.get(1);
                     Iterable var13 = Tabs.INSTANCE.getAll();
                     Iterator var14 = var13.iterator();
                     Object var31;

                     while (true) {
                        if (var14.hasNext()) {
                           Object var15 = var14.next();
                           SettingsTab it = (SettingsTab)var15;
                           int var17/* $VF was: $i$a$-find-CliEngine$importFromClipboard$1$tab$1 */ = 0;
                           if (!Intrinsics.areEqual(it.getName(), tabNamex)) {
                              continue;
                           }

                           var31 = var15;
                           break;
                        }

                        var31 = null;
                        break;
                     }

                     SettingsTab var32 = (SettingsTab)var31;
                     if ((SettingsTab)var31 != null) {
                        SettingsTab tab = var32;
                        Iterable var25 = tab.getSettings();
                        Iterator var26 = var25.iterator();
                        Object var33;

                        while (true) {
                           if (var26.hasNext()) {
                              Object var27 = var26.next();
                              Setting it = (Setting)var27;
                              int var19/* $VF was: $i$a$-find-CliEngine$importFromClipboard$1$setting$1 */ = 0;
                              if (!Intrinsics.areEqual(it.getName(), propName)) {
                                 continue;
                              }

                              var33 = var27;
                              break;
                           }

                           var33 = null;
                           break;
                        }

                        Setting var34 = (Setting)var33;
                        if ((Setting)var33 != null) {
                           Setting setting = var34;
                           if (INSTANCE.stringToValue(setting, (String)parts.get(1))) {
                              imported++;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (imported > 0) {
            Config.INSTANCE.save();
         }

         data = imported > 0;
      } catch (Exception e) {
         e.printStackTrace();
         data = false;
      }

      return data;
   }

   private final boolean saveProfile(String name) {
      boolean sanitized;
      try {
         CharSequence e = name;
         Regex var4 = new Regex("[^a-zA-Z0-9_-]");
         String var5 = "_";
         String sanitizedx = var4.replace(e, var5);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Path var10000 = this.getProfilesDir();
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            Files.createDirectories(this.getProfilesDir());
         }

         var var28 = Files.newBufferedWriter(file);
         Throwable var29 = null;

         try {
            BufferedWriter writer = var28;
            int var7/* $VF was: $i$a$-use-CliEngine$saveProfile$1 */ = 0;
            writer.write("# CB Profile: " + name + "\n");
            Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               SettingsTab tab = (SettingsTab)element$iv;
               int var13/* $VF was: $i$a$-forEach-CliEngine$saveProfile$1$1 */ = 0;
               Iterable $this$forEach$ivx = tab.getSettings();
               int $i$f$forEachx = 0;

               for (Object element$ivx : $this$forEach$ivx) {
                  Setting setting = (Setting)element$ivx;
                  int var19/* $VF was: $i$a$-forEach-CliEngine$saveProfile$1$1$1 */ = 0;
                  writer.write(tab.getName() + "." + setting.getName() + ":" + INSTANCE.valueToString(setting) + "\n");
               }
            }

            Unit var30 = Unit.INSTANCE;
         } catch (Throwable var23) {
            var29 = var23;
            throw var23;
         } finally {
            CloseableKt.closeFinally(var28, var29);
         }

         sanitized = true;
      } catch (Exception e) {
         e.printStackTrace();
         sanitized = false;
      }

      return sanitized;
   }

   private final boolean loadProfile(String name) {
      boolean sanitized;
      try {
         CharSequence e = name;
         Regex map = new Regex("[^a-zA-Z0-9_-]");
         String $this$forEach$iv = "_";
         String sanitizedx = map.replace(e, $this$forEach$iv);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Intrinsics.checkNotNull(file);
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(file, Arrays.copyOf(var10001, var10001.length))) {
            return false;
         }

         Map mapx = new LinkedHashMap();
         List var10000 = Files.readAllLines(file);
         Intrinsics.checkNotNullExpressionValue(var10000, "readAllLines(...)");
         Iterable $this$forEach$ivx = var10000;
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$ivx) {
            String line = (String)element$iv;
            int var10/* $VF was: $i$a$-forEach-CliEngine$loadProfile$1 */ = 0;
            Intrinsics.checkNotNull(line);
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               int idx = StringsKt.indexOf(line, ':', 0, false);
               if (idx > 0) {
                  String var31 = line.substring(0, idx);
                  Intrinsics.checkNotNullExpressionValue(var31, "substring(...)");
                  String var10002 = line.substring(idx + 1);
                  Intrinsics.checkNotNullExpressionValue(var10002, "substring(...)");
                  mapx.put(var31, var10002);
               }
            }
         }

         Iterable $this$forEach$ivxx = Tabs.INSTANCE.getAll();
         $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$ivxx) {
            SettingsTab tab = (SettingsTab)element$iv;
            int var28/* $VF was: $i$a$-forEach-CliEngine$loadProfile$2 */ = 0;
            Iterable $this$forEach$ivxxx = tab.getSettings();
            int $i$f$forEachx = 0;

            for (Object element$ivx : $this$forEach$ivxxx) {
               Setting setting = (Setting)element$ivx;
               int var16/* $VF was: $i$a$-forEach-CliEngine$loadProfile$2$1 */ = 0;
               String var30 = (String)mapx.get(tab.getName() + "." + setting.getName());
               if (var30 != null) {
                  String v = var30;
                  INSTANCE.stringToValue(setting, v);
               }
            }
         }

         Config.INSTANCE.save();
         sanitized = true;
      } catch (Exception e) {
         e.printStackTrace();
         sanitized = false;
      }

      return sanitized;
   }

   private final List<String> listProfiles() {
      Path var10000 = this.getProfilesDir();
      LinkOption[] var10001 = new LinkOption[0];
      if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
         return CollectionsKt.emptyList();
      }

      CliEngine var1 = this;

      List<String> var15 = null;
      try {
         int var3/* $VF was: $i$a$-runCatching-CliEngine$listProfiles$1 */ = 0;
         Iterable $this$map$iv = PathsKt.listDirectoryEntries(var1.getProfilesDir(), "*.profile");
         int $i$f$map = 0;
         Iterable $this$mapTo$iv$iv = $this$map$iv;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
         int $i$f$mapTo = 0;

         for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Path it = (Path)item$iv$iv;
            Collection var12 = destination$iv$iv;
            int var13/* $VF was: $i$a$-map-CliEngine$listProfiles$1$1 */ = 0;
            var12.add(PathsKt.getNameWithoutExtension(it));
         }

         var15 = (List<String>)CollectionsKt.sorted((Iterable & List)destination$iv$iv);
      } catch (Throwable var14) {
         var15 = null;
      }

      return var15 != null ? var15 : CollectionsKt.emptyList();
   }

   private final boolean deleteProfile(String name) {
      boolean sanitized;
      try {
         CharSequence e = name;
         Regex var4 = new Regex("[^a-zA-Z0-9_-]");
         String var5 = "_";
         String sanitizedx = var4.replace(e, var5);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Intrinsics.checkNotNull(file);
         LinkOption[] var10001 = new LinkOption[0];
         boolean var10000;
         if (Files.exists(file, Arrays.copyOf(var10001, var10001.length))) {
            Files.delete(file);
            var10000 = true;
         } else {
            var10000 = false;
         }

         sanitized = var10000;
      } catch (Exception e) {
         e.printStackTrace();
         sanitized = false;
      }

      return sanitized;
   }

   private final String valueToString(Setting<?> setting) {
      Object v = setting.getValue();
      Object var3 = v;
      return var3 instanceof Integer
         ? String.valueOf(((Number)v).intValue())
         : (
            var3 instanceof Double
               ? String.valueOf(((Number)v).doubleValue())
               : (
                  var3 instanceof Float
                     ? String.valueOf(((Number)v).floatValue())
                     : (var3 instanceof Boolean ? String.valueOf(((Boolean)v).booleanValue()) : (var3 instanceof Enum ? ((Enum)v).name() : String.valueOf(v)))
               )
         );
   }

   private final boolean stringToValue(Setting<?> setting, String raw) {
      boolean cur;
      try {
         Object curx = setting.getValue();
         Setting rawSetting = setting;
         boolean var15;
         if (curx instanceof Integer) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Int>");
            rawSetting.setValue(Integer.parseInt(raw));
            var15 = true;
         } else if (curx instanceof Double) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Double>");
            rawSetting.setValue(Double.parseDouble(raw));
            var15 = true;
         } else if (curx instanceof Float) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Float>");
            rawSetting.setValue(Float.parseFloat(raw));
            var15 = true;
         } else if (curx instanceof Boolean) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Boolean>");
            rawSetting.setValue(Boolean.parseBoolean(raw));
            var15 = true;
         } else if (curx instanceof String) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.String>");
            rawSetting.setValue(raw);
            var15 = true;
         } else if (!(curx instanceof Enum)) {
            var15 = false;
         } else {
            Enum[] constants = (Enum[])curx.getClass().getEnumConstants();
            Intrinsics.checkNotNull(constants);
            Object[] $this$firstOrNull$iv = constants;
            int $i$f$firstOrNull = 0;
            int var8 = 0;
            int var9 = $this$firstOrNull$iv.length;
            Enum match = null;

            while (true) {
               if (var8 < var9) {
                  Object element$iv = $this$firstOrNull$iv[var8];
                  Enum it = (Enum)element$iv;
                  int var12/* $VF was: $i$a$-firstOrNull-CliEngine$stringToValue$match$1 */ = 0;
                  if (!Intrinsics.areEqual(it.name(), raw)) {
                     var8++;
                     continue;
                  }

                  match = it;
                  break;
               }

               match = null;
               break;
            }

            if (match == null) {
               return false;
            }

            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Any>");
            rawSetting.setValue(match);
            var15 = true;
         }

         cur = var15;
      } catch (Exception var13/* $VF was: <unused var> */) {
         cur = false;
      }

      return cur;
   }

   public final void saveSavedColors() {
      try {
         Path var10000 = this.getSavedColorsFile();
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            PathsKt.createParentDirectories(this.getSavedColorsFile());
            var10000 = this.getSavedColorsFile();
            FileAttribute[] var17 = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createFile(var10000, Arrays.copyOf(var17, var17.length)), "createFile(...)");
         }

         var var1 = Files.newBufferedWriter(this.getSavedColorsFile());
         Throwable var2 = null;

         try {
            BufferedWriter writer = var1;
            int var4/* $VF was: $i$a$-use-CliEngine$saveSavedColors$1 */ = 0;
            Iterable $this$forEach$iv = savedColors;
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               Color color = (Color)element$iv;
               int var10/* $VF was: $i$a$-forEach-CliEngine$saveSavedColors$1$1 */ = 0;
               writer.write(color.getRGB() + "\n");
            }

            Unit var15 = Unit.INSTANCE;
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            CloseableKt.closeFinally(var1, var2);
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void loadSavedColors() {
      try {
         Path var10000 = this.getSavedColorsFile();
         LinkOption[] var10001 = new LinkOption[0];
         if (Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            savedColors.clear();
            var var1 = Files.newBufferedReader(this.getSavedColorsFile());
            Throwable var2 = null;

            try {
               BufferedReader reader = var1;
               int var4/* $VF was: $i$a$-use-CliEngine$loadSavedColors$1 */ = 0;

               while (true) {
                  String var13 = reader.readLine();
                  if (var13 == null) {
                     Unit var12 = Unit.INSTANCE;
                     return;
                  }

                  String line = var13;
                  Integer var14 = StringsKt.toIntOrNull(line);
                  if (var14 != null) {
                     int it = var14.intValue();
                     int var7/* $VF was: $i$a$-let-CliEngine$loadSavedColors$1$1 */ = 0;
                     savedColors.add(new Color(it, true));
                  }
               }
            } catch (Throwable var10) {
               var2 = var10;
               throw var10;
            } finally {
               CloseableKt.closeFinally(var1, var2);
            }
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void saveNotes() {
      try {
         Path var10000 = this.getNotesFile();
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            PathsKt.createParentDirectories(this.getNotesFile());
            var10000 = this.getNotesFile();
            FileAttribute[] var17 = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createFile(var10000, Arrays.copyOf(var17, var17.length)), "createFile(...)");
         }

         var var1 = Files.newBufferedWriter(this.getNotesFile());
         Throwable var2 = null;

         try {
            BufferedWriter writer = var1;
            int var4/* $VF was: $i$a$-use-CliEngine$saveNotes$1 */ = 0;
            Iterable $this$forEach$iv = notes;
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               String note = (String)element$iv;
               int var10/* $VF was: $i$a$-forEach-CliEngine$saveNotes$1$1 */ = 0;
               writer.write(note + "\n");
            }

            Unit var15 = Unit.INSTANCE;
         } catch (Throwable var13) {
            var2 = var13;
            throw var13;
         } finally {
            CloseableKt.closeFinally(var1, var2);
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void loadNotes() {
      try {
         Path var10000 = this.getNotesFile();
         LinkOption[] var10001 = new LinkOption[0];
         if (Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            notes.clear();
            var var1 = Files.newBufferedReader(this.getNotesFile());
            Throwable var2 = null;

            try {
               BufferedReader reader = var1;
               int var4/* $VF was: $i$a$-use-CliEngine$loadNotes$1 */ = 0;

               while (true) {
                  String var11 = reader.readLine();
                  if (var11 == null) {
                     Unit var10 = Unit.INSTANCE;
                     return;
                  }

                  String line = var11;
                  if (!StringsKt.isBlank(line)) {
                     notes.add(line);
                  }
               }
            } catch (Throwable var8) {
               var2 = var8;
               throw var8;
            } finally {
               CloseableKt.closeFinally(var1, var2);
            }
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private static final boolean cancelReminder$lambda$0(int $id, CliEngine.Reminder it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getId() == $id;
   }

   private static final boolean cancelReminder$lambda$1(Function1 $tmp0, Object p0) {
      return (Boolean)$tmp0.invoke(p0);
   }

   private static final String blockSuggestions$lambda$0(Identifier it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getPath();
   }

   private static final boolean blockSuggestions$lambda$1(String $q, String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return $q.length() == 0 || StringsKt.contains(it, $q, false);
   }

   private static final CliEngine.Suggestion blockSuggestions$lambda$2(String $prefix, String $hint, String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return new CliEngine.Suggestion($prefix + " " + it, it, $hint);
   }

   private static final CharSequence executeCliCommand$lambda$0(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.take(it, 30) + (it.length() > 30 ? "..." : "");
   }

   private static final CharSequence executeCbCommand$lambda$0(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.take(it, 25);
   }

   private static final Unit executeCbCommand$lambda$1() {
      Minecraft.getInstance().setScreenAndShow(new BlockSelectorScreen());
      return Unit.INSTANCE;
   }

   private static final CharSequence executeCbCommand$lambda$4(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final CharSequence executeCbCommand$lambda$5(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it + " (" + CosmeticBlocks.INSTANCE.getRoomBlockCount(it) + ")";
   }

   private static final Unit executeCbCommand$lambda$6() {
      Minecraft.getInstance().setScreenAndShow(new BlockSelectorScreen());
      return Unit.INSTANCE;
   }

   static {
      Path var10000 = FabricLoader.getInstance().getConfigDir().resolve("cb");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      configDir = var10000;
      String[] var0 = new String[]{
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
      };
      TOP_COMMANDS = CollectionsKt.listOf(var0);
      var0 = new String[]{
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
      };
      CB_SUBCOMMANDS = CollectionsKt.listOf(var0);
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\f\u001a\u00020\nH\u0002J\b\u0010\r\u001a\u00020\nH\u0002J\b\u0010\u000e\u001a\u00020\nH\u0002J\b\u0010\u000f\u001a\u00020\nH\u0002J\b\u0010\u0010\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018",
      d2 = {
            "Lcom/cb/gui/screens/CliEngine$CalcParser;",
            "",
            "expr",
            "",
            "<init>",
            "(Ljava/lang/String;)V",
            "pos",
            "",
            "length",
            "parse",
            "",
            "parseExpression",
            "parseTerm",
            "parseFactor",
            "parseUnary",
            "parsePrimary",
            "parseNumber",
            "match",
            "",
            "c",
            "",
            "expect",
            "",
            "skipWhitespace",
            "cb_client"
      }
   )
   private static final class CalcParser {
      @NotNull
      private final String expr;
      private int pos;
      private final int length;

      public CalcParser(@NotNull String expr) {
         Intrinsics.checkNotNullParameter(expr, "expr");
         super();
         this.expr = expr;
         this.length = this.expr.length();
      }

      public final double parse() {
         double result = this.parseExpression();
         if (this.pos < this.length) {
            throw new IllegalArgumentException("Unexpected character");
         } else {
            return result;
         }
      }

      private final double parseExpression() {
         double result = this.parseTerm();

         while (this.pos < this.length) {
            if (this.match('+')) {
               result += this.parseTerm();
            } else {
               if (!this.match('-')) {
                  break;
               }

               result -= this.parseTerm();
            }
         }

         return result;
      }

      private final double parseTerm() {
         double result = this.parseFactor();

         while (this.pos < this.length) {
            if (this.match('*')) {
               result *= this.parseFactor();
            } else if (this.match('/')) {
               result /= this.parseFactor();
            } else {
               if (!this.match('%')) {
                  break;
               }

               result %= this.parseFactor();
            }
         }

         return result;
      }

      private final double parseFactor() {
         double base = this.parseUnary();
         return this.match('^') ? Math.pow(base, this.parseFactor()) : base;
      }

      private final double parseUnary() {
         return this.match('-') ? -this.parseUnary() : (this.match('+') ? this.parseUnary() : this.parsePrimary());
      }

      private final double parsePrimary() {
         this.skipWhitespace();
         if (this.match('(')) {
            double result = this.parseExpression();
            if (!this.match(')')) {
               throw new IllegalArgumentException("Missing )");
            } else {
               return result;
            }
         } else {
            int start = this.pos;

            while (this.pos < this.length && (Character.isLetter(this.expr.charAt(this.pos)) || this.expr.charAt(this.pos) == '_')) {
               int name = this.pos++;
            }

            if (this.pos <= start) {
               return this.parseNumber();
            }

            String var10000 = this.expr.substring(start, this.pos);
            Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
            var10000 = var10000.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
            String name = var10000;
            String var3 = name;
            double var18;
            switch (var3.hashCode()) {
               case 101:
                  if (!var3.equals("e")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  var18 = Math.E;
                  break;
               case 3458: {
                  if (!var3.equals("ln")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.log(arg);
                  break;
               }
               case 3577:
                  if (!var3.equals("pi")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  var18 = Math.PI;
                  break;
               case 96370: {
                  if (!var3.equals("abs")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.abs(arg);
                  break;
               }
               case 98695: {
                  if (!var3.equals("cos")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.cos(arg);
                  break;
               }
               case 107332: {
                  if (!var3.equals("log")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.log10(arg);
                  break;
               }
               case 113880: {
                  if (!var3.equals("sin")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.sin(arg);
                  break;
               }
               case 114593: {
                  if (!var3.equals("tan")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.tan(arg);
                  break;
               }
               case 3049733: {
                  if (!var3.equals("ceil")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.ceil(arg);
                  break;
               }
               case 3538208: {
                  if (!var3.equals("sqrt")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.sqrt(arg);
                  break;
               }
               case 97526796: {
                  if (!var3.equals("floor")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  var18 = Math.floor(arg);
                  break;
               }
               case 108704142: {
                  if (var3.equals("round")) {
                     this.expect('(');
                     double arg = this.parseExpression();
                     this.expect(')');
                     var18 = Math.rint(arg);
                     break;
                  }

                  throw new IllegalArgumentException("Unknown function: " + name);
               }
               default:
                  throw new IllegalArgumentException("Unknown function: " + name);
            }

            return var18;
         }
      }

      private final double parseNumber() {
         this.skipWhitespace();
         int start = this.pos;
         if (this.pos < this.length && (this.expr.charAt(this.pos) == '-' || this.expr.charAt(this.pos) == '+')) {
            int var2 = this.pos++;
         }

         while (this.pos < this.length && (Character.isDigit(this.expr.charAt(this.pos)) || this.expr.charAt(this.pos) == '.')) {
            int var3 = this.pos++;
         }

         if (start == this.pos) {
            throw new IllegalArgumentException("Expected number at " + this.pos);
         }

         String var10000 = this.expr.substring(start, this.pos);
         Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
         return Double.parseDouble(var10000);
      }

      private final boolean match(char c) {
         this.skipWhitespace();
         boolean var10000;
         if (this.pos < this.length && this.expr.charAt(this.pos) == c) {
            int var2 = this.pos++;
            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      }

      private final void expect(char c) {
         if (!this.match(c)) {
            throw new IllegalArgumentException("Expected '" + c + "'");
         }
      }

      private final void skipWhitespace() {
         while (this.pos < this.length && CharsKt.isWhitespace(this.expr.charAt(this.pos))) {
            int var1 = this.pos++;
         }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0014\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0017\u001a\u00020\u0007HÖ\u0081\u0004J\n\u0010\u0018\u001a\u00020\u0005HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0019",
      d2 = {
            "Lcom/cb/gui/screens/CliEngine$Reminder;",
            "",
            "triggerTime",
            "",
            "message",
            "",
            "id",
            "",
            "<init>",
            "(JLjava/lang/String;I)V",
            "getTriggerTime",
            "()J",
            "getMessage",
            "()Ljava/lang/String;",
            "getId",
            "()I",
            "component1",
            "component2",
            "component3",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "cb_client"
      }
   )
   public static final class Reminder {
      private final long triggerTime;
      @NotNull
      private final String message;
      private final int id;

      public Reminder(long triggerTime, @NotNull String message, int id) {
         Intrinsics.checkNotNullParameter(message, "message");
         super();
         this.triggerTime = triggerTime;
         this.message = message;
         this.id = id;
      }

      public final long getTriggerTime() {
         return this.triggerTime;
      }

      @NotNull
      public final String getMessage() {
         return this.message;
      }

      public final int getId() {
         return this.id;
      }

      public final long component1() {
         return this.triggerTime;
      }

      @NotNull
      public final String component2() {
         return this.message;
      }

      public final int component3() {
         return this.id;
      }

      @NotNull
      public final CliEngine.Reminder copy(long triggerTime, @NotNull String message, int id) {
         Intrinsics.checkNotNullParameter(message, "message");
         return new CliEngine.Reminder(triggerTime, message, id);
      }

      @NotNull
      @Override
      public String toString() {
         return "Reminder(triggerTime=" + this.triggerTime + ", message=" + this.message + ", id=" + this.id + ")";
      }

      @Override
      public int hashCode() {
         int result = Long.hashCode(this.triggerTime);
         result = result * 31 + this.message.hashCode();
         return result * 31 + Integer.hashCode(this.id);
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof CliEngine.Reminder var2)) {
            return false;
         } else if (this.triggerTime != var2.triggerTime) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.message, var2.message) ? false : this.id == var2.id;
         }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0013\u001a\u00020\u0014HÖ\u0081\u0004J\n\u0010\u0015\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016",
      d2 = {
            "Lcom/cb/gui/screens/CliEngine$Suggestion;",
            "",
            "insert",
            "",
            "display",
            "hint",
            "<init>",
            "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V",
            "getInsert",
            "()Ljava/lang/String;",
            "getDisplay",
            "getHint",
            "component1",
            "component2",
            "component3",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "",
            "toString",
            "cb_client"
      }
   )
   public static final class Suggestion {
      @NotNull
      private final String insert;
      @NotNull
      private final String display;
      @NotNull
      private final String hint;

      public Suggestion(@NotNull String insert, @NotNull String display, @NotNull String hint) {
         Intrinsics.checkNotNullParameter(insert, "insert");
         Intrinsics.checkNotNullParameter(display, "display");
         Intrinsics.checkNotNullParameter(hint, "hint");
         super();
         this.insert = insert;
         this.display = display;
         this.hint = hint;
      }

      @NotNull
      public final String getInsert() {
         return this.insert;
      }

      @NotNull
      public final String getDisplay() {
         return this.display;
      }

      @NotNull
      public final String getHint() {
         return this.hint;
      }

      @NotNull
      public final String component1() {
         return this.insert;
      }

      @NotNull
      public final String component2() {
         return this.display;
      }

      @NotNull
      public final String component3() {
         return this.hint;
      }

      @NotNull
      public final CliEngine.Suggestion copy(@NotNull String insert, @NotNull String display, @NotNull String hint) {
         Intrinsics.checkNotNullParameter(insert, "insert");
         Intrinsics.checkNotNullParameter(display, "display");
         Intrinsics.checkNotNullParameter(hint, "hint");
         return new CliEngine.Suggestion(insert, display, hint);
      }

      @NotNull
      @Override
      public String toString() {
         return "Suggestion(insert=" + this.insert + ", display=" + this.display + ", hint=" + this.hint + ")";
      }

      @Override
      public int hashCode() {
         int result = this.insert.hashCode();
         result = result * 31 + this.display.hashCode();
         return result * 31 + this.hint.hashCode();
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof CliEngine.Suggestion var2)) {
            return false;
         } else if (!Intrinsics.areEqual(this.insert, var2.insert)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.display, var2.display) ? false : Intrinsics.areEqual(this.hint, var2.hint);
         }
      }
   }
}
