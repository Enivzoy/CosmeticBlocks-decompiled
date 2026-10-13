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
      Path file = configDir.resolve("saved_colors.txt");
      Intrinsics.checkNotNullExpressionValue(file, "resolve(...)");
      return file;
   }

   private final Path getNotesFile() {
      Path file = configDir.resolve("notes.txt");
      Intrinsics.checkNotNullExpressionValue(file, "resolve(...)");
      return file;
   }

   private final Path getProfilesDir() {
      Path dir = configDir.resolve("profiles");
      Intrinsics.checkNotNullExpressionValue(dir, "resolve(...)");
      return dir;
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

      String output;
      try {
         output = this.executeCliCommand(cmd);
      } catch (Throwable t) {
         output = "Error: " + t.getMessage();
      }

      return output;
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
         Iterable items = reminders;
         Iterable filterSource = items;
         var dest = new ArrayList();

         for (Object element : filterSource) {
            CliEngine.Reminder reminder = (CliEngine.Reminder)element;
            if (reminder.getTriggerTime() <= now) {
               dest.add(element);
            }
         }

         List triggered = (List)dest;
         items = triggered;

         for (Object element : items) {
            CliEngine.Reminder reminder = (CliEngine.Reminder)element;
            reminders.remove(reminder);
            ChatUtil.INSTANCE.info("Reminder: " + reminder.getMessage());
         }
      }
   }

   public final int addReminder(long delayMs, @NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      int newId = nextReminderId++;
      int id = newId;
      reminders.add(new CliEngine.Reminder(System.currentTimeMillis() + delayMs, message, id));
      List reminderList = reminders;
      if (reminderList.size() > 1) {
         CollectionsKt.sortWith(reminderList, new CliEngine$addReminder$$inlined$sortBy$1());
      }

      return id;
   }

   public final boolean cancelReminder(int id) {
      return reminders.removeIf(it -> cancelReminderPredicate(id, (CliEngine.Reminder)it));
   }

   private final List<CliEngine.Suggestion> computeSuggestions(String raw) {
      String text = StringsKt.trimStart(raw).toString();
      if (text.length() == 0) {
         return CollectionsKt.emptyList();
      }

      int firstSpace = StringsKt.indexOf(text, ' ', 0, false);
      if (firstSpace < 0) {
         String lowerText = text.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(lowerText, "toLowerCase(...)");
         String q = lowerText;
         Iterable topCommands = CollectionsKt.distinct(TOP_COMMANDS);
         Iterable source = topCommands;
         var dest = new ArrayList();

         for (Object element : source) {
            String it = (String)element;
            if (StringsKt.startsWith(it, q, false)) {
               dest.add(element);
            }
         }

         topCommands = (Iterable & List)dest;
         source = topCommands;
         dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(topCommands, 10));

         for (Object item : source) {
            String name = (String)item;
            Collection collector = dest;
            collector.add(new CliEngine.Suggestion(name, name, ""));
         }

         return CollectionsKt.take(dest, 10);
      } else {
         String head;
         String rest;
         label153: {
            String tmp = text.substring(0, firstSpace);
            Intrinsics.checkNotNullExpressionValue(tmp, "substring(...)");
            tmp = tmp.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
            head = tmp;
            tmp = text.substring(firstSpace + 1);
            Intrinsics.checkNotNullExpressionValue(tmp, "substring(...)");
            rest = tmp;
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

            tmp = rest.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
            String q = tmp;
            Iterable working = this.booleanSettings();
            Iterable source = working;
            var dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(working, 10));

            for (Object item : source) {
               BooleanSetting setting = (BooleanSetting)item;
               Collection collector = dest;
               collector.add(setting.getName());
            }

            working = (Iterable & List)dest;
            source = working;
            dest = new ArrayList();

            for (Object element : source) {
               String it = (String)element;
               tmp = it.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
               if (StringsKt.contains(tmp, q, false)) {
                  dest.add(element);
               }
            }

            working = CollectionsKt.take(CollectionsKt.distinct((Iterable & List)dest), 10);
            source = working;
            dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(working, 10));

            for (Object item : source) {
               String name = (String)item;
               Collection collector = dest;
               collector.add(new CliEngine.Suggestion(head + " " + name, head + " " + name, "toggle"));
            }

            return (List<CliEngine.Suggestion>)dest;
         }

         CliEngine engine = this;

         List<CliEngine.Suggestion> result = null;
         try {
            String lower = rest.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
            String q = lower;
            Iterable profiles = engine.listProfiles();
            Iterable profileSource = profiles;
            var dest = new ArrayList();

            for (Object element : profileSource) {
               String it = (String)element;
               lower = it.toLowerCase(Locale.ROOT);
               Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
               if (StringsKt.startsWith(lower, q, false)) {
                  dest.add(element);
               }
            }

            profiles = (Iterable & List)dest;
            profileSource = profiles;
            dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(profiles, 10));

            for (Object item : profileSource) {
               String name = (String)item;
               Collection collector = dest;
               collector.add(new CliEngine.Suggestion(head + " " + name, head + " " + name, Intrinsics.areEqual(head, "load") ? "Load profile" : "Delete profile"));
            }

            result = (List<CliEngine.Suggestion>)CollectionsKt.take((Iterable & List)dest, 10);
         } catch (Throwable exception) {
            result = null;
         }

         return result != null ? result : CollectionsKt.emptyList();
      }
   }

   private final List<CliEngine.Suggestion> cbSuggestions(String rest) {
      int nextSpace = StringsKt.indexOf(rest, ' ', 0, false);
      if (nextSpace < 0) {
         String lower = rest.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
         String q = lower;
         Iterable subCommands = CollectionsKt.distinct(CB_SUBCOMMANDS);
         Iterable source = subCommands;
         var dest = new ArrayList();

         for (Object element : source) {
            String it = (String)element;
            if (StringsKt.startsWith(it, q, false)) {
               dest.add(element);
            }
         }

         subCommands = (Iterable & List)dest;
         source = subCommands;
         dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(subCommands, 10));

         for (Object item : source) {
            String name = (String)item;
            Collection collector = dest;
            collector.add(new CliEngine.Suggestion("cb " + name, "cb " + name, ""));
         }

         return CollectionsKt.take(dest, 10);
      } else {
         String args;
         String lastToken;
         String prefix;
         label270: {
            String tmp = rest.substring(0, nextSpace);
            Intrinsics.checkNotNullExpressionValue(tmp, "substring(...)");
            tmp = tmp.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
            String sub = tmp;
            tmp = rest.substring(nextSpace + 1);
            Intrinsics.checkNotNullExpressionValue(tmp, "substring(...)");
            args = tmp;
            tmp = StringsKt.substringAfterLast(args, ' ', args).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
            lastToken = tmp;
            String argsBeforeLast = StringsKt.substringBeforeLast(args, ' ', "");
            prefix = argsBeforeLast.length() == 0 ? "cb " + sub : "cb " + sub + " " + argsBeforeLast;
            String switchKey = sub;
            switch (switchKey.hashCode()) {
               case -1785238953:
                  if (switchKey.equals("favorites")) {
                     return this.favSuggestions(args, prefix);
                  }

                  return CollectionsKt.emptyList();
               case -1335458389:
                  if (!switchKey.equals("delete")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 98:
                  if (switchKey.equals("b")) {
                     return this.blockSuggestions(lastToken, prefix, "Brush");
                  }

                  return CollectionsKt.emptyList();
               case 102:
                  if (switchKey.equals("f")) {
                     return this.blockSuggestions(lastToken, prefix, "Fill");
                  }

                  return CollectionsKt.emptyList();
               case 114:
                  if (!switchKey.equals("r")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               case 99339:
                  if (!switchKey.equals("del")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 101147:
                  if (switchKey.equals("fav")) {
                     return this.favSuggestions(args, prefix);
                  }

                  return CollectionsKt.emptyList();
               case 112797:
                  if (!switchKey.equals("rep")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               case 3143043:
                  if (switchKey.equals("fill")) {
                     return this.blockSuggestions(lastToken, prefix, "Fill");
                  }

                  return CollectionsKt.emptyList();
               case 3327206:
                  if (!switchKey.equals("load")) {
                     return CollectionsKt.emptyList();
                  }
                  break;
               case 3506395:
                  if (switchKey.equals("room")) {
                     String[] roomSubcommands = new String[]{"info", "clear"};
                     Iterable roomSubs = CollectionsKt.listOf(roomSubcommands);
                     Iterable roomSource = roomSubs;
                     var dest = new ArrayList();

                     for (Object element : roomSource) {
                        String it = (String)element;
                        if (StringsKt.startsWith(it, lastToken, false)) {
                           dest.add(element);
                        }
                     }

                     var roomFiltered = (Iterable & List)dest;
                     roomSource = roomFiltered;
                     dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(roomFiltered, 10));

                     for (Object item : roomSource) {
                        String roomName = (String)item;
                        Collection collector = dest;
                        collector.add(new CliEngine.Suggestion(prefix + " " + roomName, prefix + " " + roomName, "Room"));
                     }

                     return (List<CliEngine.Suggestion>)dest;
                  }

                  return CollectionsKt.emptyList();
               case 93832333:
                  if (switchKey.equals("block")) {
                     return this.blockSuggestions(lastToken, prefix, "Brush");
                  }

                  return CollectionsKt.emptyList();
               case 109399969:
                  if (switchKey.equals("shape")) {
                     String[] shapeArray = new String[]{"none", "fence", "wall"};
                     Iterable shapes = CollectionsKt.listOf(shapeArray);
                     Iterable shapeSource = shapes;
                     var dest = new ArrayList();

                     for (Object element : shapeSource) {
                        String it = (String)element;
                        if (StringsKt.startsWith(it, lastToken, false)) {
                           dest.add(element);
                        }
                     }

                     var shapeList = (Iterable & List)dest;
                     shapeSource = shapeList;
                     dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(shapeList, 10));

                     for (Object item : shapeSource) {
                        String shapeName = (String)item;
                        Collection collector = dest;
                        collector.add(new CliEngine.Suggestion(prefix + " " + shapeName, prefix + " " + shapeName, "Shape"));
                     }

                     return (List<CliEngine.Suggestion>)dest;
                  }

                  return CollectionsKt.emptyList();
               case 1094496948:
                  if (!switchKey.equals("replace")) {
                     return CollectionsKt.emptyList();
                  }
                  break label270;
               default:
                  return CollectionsKt.emptyList();
            }

            List configs;
            try {
               configs = (List)CosmeticBlocks.INSTANCE.listConfigs();
            } catch (Throwable ignored) {
               configs = CollectionsKt.emptyList();
            }

            Iterable configSource = configs;
            Iterable configItems = configSource;
            var dest = new ArrayList();

            for (Object element : configItems) {
               boolean matches;
               label193: {
                  String it = (String)element;
                  if (lastToken.length() != 0) {
                     tmp = it.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
                     if (!StringsKt.contains(tmp, lastToken, false)) {
                        matches = false;
                        break label193;
                     }
                  }

                  matches = true;
               }

               if (matches) {
                  dest.add(element);
               }
            }

            configSource = (Iterable & List)dest;
            configItems = configSource;
            dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(configSource, 10));

            for (Object item : configItems) {
               String config = (String)item;
               Collection collector = dest;
               collector.add(new CliEngine.Suggestion(prefix + " " + config, prefix + " " + config, Intrinsics.areEqual(sub, "load") ? "Load" : "Delete"));
            }

            return CollectionsKt.take(dest, 10);
         }

         String hint = StringsKt.trim(args).toString();
         Regex whitespaceRegex = new Regex("\\s+");
         int count = 0;
         var tokens = whitespaceRegex.split(hint, count);
         int rawCount;
         if (tokens instanceof Collection && ((Collection)tokens).isEmpty()) {
            rawCount = 0;
         } else {
            count = 0;

            for (Object element : tokens) {
               String it = (String)element;
               if (it.length() > 0) {
                  if (++count < 0) {
                     CollectionsKt.throwCountOverflow();
                  }
               }
            }

            rawCount = count;
         }

         int tokenCount = rawCount;
         hint = tokenCount <= 1 ? "From" : "To";
         return this.blockSuggestions(lastToken, prefix, hint);
      }
   }

   private final List<CliEngine.Suggestion> blockSuggestions(String token, String prefix, String hint) {
      String q = StringsKt.replace(StringsKt.removePrefix(token, "minecraft:"), ' ', '_', false);
      Set<Identifier> blockKeys = BuiltInRegistries.BLOCK.keySet();
      Intrinsics.checkNotNullExpressionValue(blockKeys, "keySet(...)");
      return SequencesKt.toList(
         SequencesKt.map(
            SequencesKt.take(
               SequencesKt.filter(
                  SequencesKt.map(CollectionsKt.asSequence(blockKeys), it -> blockIdPath(it)), it -> blockPathFilter(q, it)
               ),
               10
            ),
            it -> blockSuggestion(prefix, hint, it)
         )
      );
   }

   private final List<CliEngine.Suggestion> favSuggestions(String args, String prefix) {
      CharSequence argChars = args;
      char[] favSub = new char[]{' '};
      List parts = StringsKt.split(argChars, favSub, false, 2);
      String temp = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(temp, "toLowerCase(...)");
      String favSubx = temp;
      temp = (String)CollectionsKt.getOrNull(parts, 1);
      temp = temp != null ? StringsKt.trim(temp).toString() : null;
      if (temp == null) {
         temp = "";
      }

      String favArg = temp;
      if (parts.size() < 2) {
         String[] subcommands = new String[]{"add", "remove", "rm", "list", "gui"};
         Iterable subs = CollectionsKt.listOf(subcommands);
         Iterable source = subs;
         var dest = new ArrayList();

         for (Object element : source) {
            String it = (String)element;
            if (StringsKt.startsWith(it, favSubx, false)) {
               dest.add(element);
            }
         }

         var favFiltered = (Iterable & List)dest;
         source = favFiltered;
         dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(favFiltered, 10));

         for (Object item : source) {
            String name = (String)item;
            Collection collector = dest;
            collector.add(new CliEngine.Suggestion(prefix + " " + name, prefix + " " + name, "Favorites"));
         }

         return (List<CliEngine.Suggestion>)dest;
      } else {
         String favKey = favSubx;
         switch (favKey.hashCode()) {
            case -934610812:
               if (!favKey.equals("remove")) {
                  return CollectionsKt.emptyList();
               }
               break;
            case 3643:
               if (!favKey.equals("rm")) {
                  return CollectionsKt.emptyList();
               }
               break;
            case 96417:
               if (favKey.equals("add")) {
                  String blockId = StringsKt.substringAfterLast(favArg, ' ', favArg).toLowerCase(Locale.ROOT);
                  Intrinsics.checkNotNullExpressionValue(blockId, "toLowerCase(...)");
                  return this.blockSuggestions(blockId, prefix + " add", "Add fav");
               }

               return CollectionsKt.emptyList();
            default:
               return CollectionsKt.emptyList();
         }

         CliEngine q = this;

         List favs;
         try {
            favs = (List)CosmeticBlocks.INSTANCE.getFavorites();
         } catch (Throwable exception) {
            favs = CollectionsKt.emptyList();
         }

         temp = favArg.toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(temp, "toLowerCase(...)");
         String qx = temp;
         Iterable favsMapped = favs;
         Iterable favSource = favsMapped;
         var dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(favsMapped, 10));

         for (Object item : favSource) {
            String it = (String)item;
            Collection collector = dest;
            collector.add(StringsKt.removePrefix(it, "minecraft:"));
         }

         favsMapped = dest;
         favSource = favsMapped;
         dest = new ArrayList();

         for (Object element : favSource) {
            String it = (String)element;
            if (qx.length() == 0 || StringsKt.contains(it, qx, false)) {
               dest.add(element);
            }
         }

         favsMapped = dest;
         favSource = favsMapped;
         dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(favsMapped, 10));

         for (Object item : favSource) {
            String fav = (String)item;
            Collection collector = dest;
            collector.add(new CliEngine.Suggestion(prefix + " " + favSubx + " " + fav, fav, "Remove fav"));
         }

         return CollectionsKt.take(dest, 10);
      }
   }

   private final List<BooleanSetting> booleanSettings() {
      Iterable tabs = Tabs.INSTANCE.getAll();
      Iterable source = tabs;
      var dest = new ArrayList();

      for (Object element : source) {
         SettingsTab tab = (SettingsTab)element;
         Iterable settings = tab.getSettings();
         CollectionsKt.addAll(dest, settings);
      }

      tabs = (Iterable & List)dest;
      source = tabs;
      dest = new ArrayList();

      for (Object element : source) {
         if (element instanceof BooleanSetting) {
            dest.add(element);
         }
      }

      return (List<BooleanSetting>)dest;
   }

   private final List<KeybindSetting> keybindSettings() {
      Iterable tabs = Tabs.INSTANCE.getAll();
      Iterable source = tabs;
      var dest = new ArrayList();

      for (Object element : source) {
         SettingsTab tab = (SettingsTab)element;
         Iterable settings = tab.getSettings();
         CollectionsKt.addAll(dest, settings);
      }

      tabs = (Iterable & List)dest;
      source = tabs;
      dest = new ArrayList();

      for (Object element : source) {
         if (element instanceof KeybindSetting) {
            dest.add(element);
         }
      }

      return (List<KeybindSetting>)dest;
   }

   private final Long parseTimeString(String input) {
      String lower = input.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
      String str = StringsKt.trim(lower).toString();
      Long totalMs = StringsKt.toLongOrNull(str);
      if (totalMs != null) {
         long it = totalMs.longValue();
         return it * 1000;
      }

      long totalMsx = 0L;
      String currentNum = "";
      int index = 0;

      for (int length = str.length(); index < length; index++) {
         char c = str.charAt(index);
         if (Character.isDigit(c)) {
            currentNum = currentNum + c;
         } else if (c == 'h') {
            Long hours = StringsKt.toLongOrNull(currentNum);
            if (hours == null) {
               return null;
            }

            totalMsx += hours * 3600 * 1000;
            currentNum = "";
         } else if (c == 'm') {
            Long minutes = StringsKt.toLongOrNull(currentNum);
            if (minutes == null) {
               return null;
            }

            totalMsx += minutes * 60 * 1000;
            currentNum = "";
         } else {
            if (c != 's') {
               return null;
            }

            Long seconds = StringsKt.toLongOrNull(currentNum);
            if (seconds == null) {
               return null;
            }

            totalMsx += seconds * 1000;
            currentNum = "";
         }
      }

      if (currentNum.length() > 0) {
         Long trailingSeconds = StringsKt.toLongOrNull(currentNum);
         if (trailingSeconds == null) {
            return null;
         }

         totalMsx += trailingSeconds * 1000;
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
      Double result;
      try {
         result = new CliEngine.CalcParser(expr).parse();
      } catch (Exception ignored) {
         result = null;
      }

      return result;
   }

   private final String executeCliCommand(String input) {
      String cmd;
      String output;
      label548: {
         CharSequence tmp = input;
         String[] cmdx = new String[]{" "};
         List parts = StringsKt.split(tmp, cmdx, false, 2);
         String part = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(part, "toLowerCase(...)");
         cmd = part;
                     part = (String)CollectionsKt.getOrNull(parts, 1);
         if (part != null) {
            output = StringsKt.trim(part).toString();
            if (output != null) {
               break label548;
            }
         }

         output = "";
      }

      String arg;
      label668: {
         label669: {
            label670: {
               label671: {
                  label672: {
                     label673: {
                        arg = output;
                        String command = cmd;
                        switch (command.hashCode()) {
                           case -1405258946:
                              if (command.equals("delprofile")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.deleteProfile(arg);
                                    output = success ? "Profile '" + arg + "' deleted" : "Profile not found";
                                 } else {
                                    output = "Usage: delprofile <profile name>";
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case -1289153612:
                              if (command.equals("export")) {
                                 boolean success = this.exportToClipboard();
                                 return success ? "Settings exported to clipboard" : "Export failed";
                              }

                              return "Unknown command: " + cmd;
                           case -1184795739:
                              if (command.equals("import")) {
                                 boolean success = this.importFromClipboard();
                                 return success ? "Settings imported from clipboard" : "Import failed";
                              }

                              return "Unknown command: " + cmd;
                           case -1002263574:
                              if (command.equals("profiles")) {
                                 List profiles = this.listProfiles();
                                 return profiles.isEmpty()
                                    ? "No profiles saved"
                                    : "Profiles: " + CollectionsKt.joinToString(profiles, ", ", "", "", -1, "...", null);
                              }

                              return "Unknown command: " + cmd;
                           case -934616827:
                              if (!command.equals("remind")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case -890346579:
                              if (!command.equals("rmreminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label668;
                           case -868304044:
                              if (!command.equals("toggle")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label671;
                           case -719093612:
                              if (command.equals("clearnotes")) {
                                 return "Unknown command: " + cmd;
                              }

                              return "Unknown command: " + cmd;
                           case -715981186:
                              if (!command.equals("coinflip")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case -518602638:
                              if (!command.equals("reminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case -448690650:
                              if (command.equals("noteexport")) {
                                 if (notes.isEmpty()) {
                                    output = "No notes to export";
                                 } else {
                                    String export = CollectionsKt.joinToString(notes, "\n", "", "", -1, "...", null);
                                    GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), export);
                                    output = "Exported " + notes.size() + " notes to clipboard";
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 0:
                              if (command.equals("")) {
                                 return "";
                              }

                              return "Unknown command: " + cmd;
                           case 61:
                              if (!command.equals("=")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 99:
                              if (!command.equals("c")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 100:
                              if (!command.equals("d")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 116:
                              if (!command.equals("t")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label671;
                           case 119:
                              if (command.equals("w")) {
                                 Config.INSTANCE.save();
                                 return "Settings saved";
                              }

                              return "Unknown command: " + cmd;
                           case 3167:
                              if (command.equals("cb")) {
                                 return this.executeCbCommand(arg);
                              }

                              return "Unknown command: " + cmd;
                           case 101609:
                              if (command.equals("fps")) {
                                 Minecraft mc = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
                                 return "FPS: " + mc.getFps();
                              }

                              return "Unknown command: " + cmd;
                           case 3023933:
                              if (command.equals("bind")) {
                                 String settingName;
                                 label451: {
                                    CharSequence argChars = arg;
                                    String[] space = new String[]{" "};
                                    List bindParts = StringsKt.split(argChars, space, false, 2);
                                    String part = ((String)bindParts.get(0)).toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue(part, "toLowerCase(...)");
                                    settingName = part;
                                    part = (String)CollectionsKt.getOrNull(bindParts, 1);
                                    if (part != null) {
                                       output = part.toUpperCase(Locale.ROOT);
                                       Intrinsics.checkNotNullExpressionValue(output, "toUpperCase(...)");
                                       if (output != null) {
                                          break label451;
                                       }
                                    }

                                    output = "";
                                 }

                                 String keyName = output;
                                 if (settingName.length() != 0) {
                                    if (keyName.length() == 0) {
                                       Iterable keybinds = this.keybindSettings();
                                       Iterator keybindIterator = keybinds.iterator();
                                       Object foundSetting;

                                       while (true) {
                                          if (!keybindIterator.hasNext()) {
                                             foundSetting = null;
                                             break;
                                          }

                                          Object keybindCandidate = keybindIterator.next();
                                          KeybindSetting it = (KeybindSetting)keybindCandidate;
                                          output = it.getName().toLowerCase(Locale.ROOT);
                                          Intrinsics.checkNotNullExpressionValue(output, "toLowerCase(...)");
                                          if (StringsKt.contains(output, settingName, false)) {
                                             foundSetting = keybindCandidate;
                                             break;
                                          }
                                       }

                                       KeybindSetting setting = (KeybindSetting)foundSetting;
                                       if (setting != null) {
                                          int currentKey = setting.getValue().intValue();
                                          if (currentKey != -1 && currentKey > 0) {
                                             String keyChar = (32 <= currentKey ? currentKey < 127 : false)
                                                ? String.valueOf((char)currentKey)
                                                : "KEY_" + currentKey;
                                             output = "Current bind for '" + setting.getName() + "': " + keyChar;
                                          } else {
                                             output = "'" + setting.getName() + "' has no keybind";
                                          }

                                          return output;
                                       } else {
                                          return "Setting not found: " + settingName;
                                       }
                                    } else {
                                       Integer keyValue;
                                       if (keyName.length() == 1) {
                                          keyValue = Integer.valueOf(Character.toUpperCase(keyName.charAt(0)));
                                       } else {
                                          label408:
                                          if (StringsKt.startsWith(keyName, "F", false) && keyName.length() > 1) {
                                             output = keyName.substring(1);
                                             Intrinsics.checkNotNullExpressionValue(output, "substring(...)");
                                             Integer fNum = StringsKt.toIntOrNull(output);
                                             if (fNum != null) {
                                                int fIndex = fNum;
                                                if (1 <= fIndex ? fIndex < 26 : false) {
                                                   keyValue = 290 + fNum - 1;
                                                   break label408;
                                                }
                                             }

                                             keyValue = null;
                                          } else {
                                             keyValue = !Intrinsics.areEqual(keyName, "NONE") && !Intrinsics.areEqual(keyName, "UNBIND") ? null : 0;
                                          }
                                       }

                                       Integer keyCode = keyValue;
                                       if (keyCode != null) {
                                          Iterable keybinds2 = this.keybindSettings();
                                          Iterator keybindIterator2 = keybinds2.iterator();
                                          Object foundSetting2;

                                          while (true) {
                                             if (!keybindIterator2.hasNext()) {
                                                foundSetting2 = null;
                                                break;
                                             }

                                             Object keybindCandidate2 = keybindIterator2.next();
                                             KeybindSetting it = (KeybindSetting)keybindCandidate2;
                                             output = it.getName().toLowerCase(Locale.ROOT);
                                             Intrinsics.checkNotNullExpressionValue(output, "toLowerCase(...)");
                                             if (StringsKt.contains(output, settingName, false)) {
                                                foundSetting2 = keybindCandidate2;
                                                break;
                                             }
                                          }

                                          KeybindSetting setting = (KeybindSetting)foundSetting2;
                                          if (setting != null) {
                                             if (keyCode == 0) {
                                                setting.setValue(-1);
                                                Config.INSTANCE.save();
                                                output = "Unbound '" + setting.getName() + "'";
                                             } else {
                                                setting.setValue(keyCode);
                                                Config.INSTANCE.save();
                                                output = "Bound '" + setting.getName() + "' to " + keyName;
                                             }

                                             return output;
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
                              if (!command.equals("calc")) {
                                 return "Unknown command: " + cmd;
                              }
                              break;
                           case 3059345:
                              if (!command.equals("coin")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case 3083175:
                              if (!command.equals("dice")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 3145837:
                              if (!command.equals("flip")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label670;
                           case 3327206:
                              if (command.equals("load")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.loadProfile(arg);
                                    output = success ? "Profile '" + arg + "' loaded" : "Profile not found";
                                 } else {
                                    output = "Usage: load <profile name>";
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 3387378:
                              if (command.equals("note")) {
                                 if (arg.length() > 0) {
                                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                                    notes.add("[" + timestamp + "] " + arg);
                                    this.saveNotes();
                                    output = "Note saved (" + notes.size() + " total)";
                                 } else {
                                    output = "Usage: note <text>";
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 3441010:
                              if (command.equals("ping")) {
                                 Minecraft mc = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
                                 LocalPlayer player = mc.player;
                                 int latency;
                                 label379:
                                 if (player != null) {
                                    ClientPacketListener connection = mc.getConnection();
                                    if (connection != null) {
                                       PlayerInfo playerInfo = connection.getPlayerInfo(player.getUUID());
                                       if (playerInfo != null) {
                                          latency = playerInfo.getLatency();
                                          break label379;
                                       }
                                    }

                                    latency = -1;
                                 } else {
                                    latency = -1;
                                 }

                                 int ping = latency;
                                 return ping >= 0 ? "Ping: " + ping + "ms" : "Ping: N/A";
                              }

                              return "Unknown command: " + cmd;
                           case 3506301:
                              if (!command.equals("roll")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label669;
                           case 3522941:
                              if (command.equals("save")) {
                                 if (arg.length() > 0) {
                                    boolean success = this.saveProfile(arg);
                                    output = success ? "Profile '" + arg + "' saved" : "Failed to save profile";
                                 } else {
                                    output = "Usage: save <profile name>";
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 93742038:
                              if (!command.equals("binds")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label672;
                           case 105008833:
                              if (command.equals("notes")) {
                                 if (notes.isEmpty()) {
                                    output = "No notes saved";
                                 } else {
                                    List recent = CollectionsKt.reversed(CollectionsKt.takeLast(notes, 3));
                                    output = CollectionsKt.joinToString(recent, " | ", "", "", -1, "...", it -> CliEngine.truncateNoteText((String)it));
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 109250890:
                              if (command.equals("scale")) {
                                 Minecraft mc = Minecraft.getInstance();
                                 Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
                                 Window windowW = mc.getWindow();
                                 int screenW = windowW != null ? windowW.getGuiScaledWidth() : 800;
                                 Window windowH = mc.getWindow();
                                 int screenH = windowH != null ? windowH.getGuiScaledHeight() : 600;
                                 float baseWindowWidth = 450.0F;
                                 float baseWindowHeight = 350.0F;
                                 float maxScaleW = (screenW - 40) / baseWindowWidth;
                                 float maxScaleH = (screenH - 40) / baseWindowHeight;
                                 float maxScale = Math.min(maxScaleW, Math.min(maxScaleH, 2.0F));
                                 if (arg.length() > 0) {
                                    Float scale = StringsKt.toFloatOrNull(arg);
                                    if (scale != null) {
                                       float scaleValue = scale;
                                       if (0.5F <= scaleValue ? scaleValue <= maxScale : false) {
                                          String fmt1 = "%.1f";
                                          Object[] args1 = new Object[]{scale};
                                          output = String.format(fmt1, Arrays.copyOf(args1, args1.length));
                                          Intrinsics.checkNotNullExpressionValue(output, "format(...)");
                                          return "Scale set to " + output + " (apply in GUI)";
                                       }
                                    }

                                    if (scale != null && scale > maxScale) {
                                       String fmt2 = "%.1f";
                                       Object[] args2 = new Object[]{maxScale};
                                       output = String.format(fmt2, Arrays.copyOf(args2, args2.length));
                                       Intrinsics.checkNotNullExpressionValue(output, "format(...)");
                                       output = "Max scale for screen: " + output;
                                    } else {
                                       String fmt3 = "%.1f";
                                       Object[] args3 = new Object[]{maxScale};
                                       output = String.format(fmt3, Arrays.copyOf(args3, args3.length));
                                       Intrinsics.checkNotNullExpressionValue(output, "format(...)");
                                       output = "Scale must be between 0.5 and " + output;
                                    }

                                    return output;
                                 } else {
                                    String fmt4 = "%.1f";
                                    Object[] args4 = new Object[]{maxScale};
                                    output = String.format(fmt4, Arrays.copyOf(args4, args4.length));
                                    Intrinsics.checkNotNullExpressionValue(output, "format(...)");
                                    return "Scale range: 0.5-" + output;
                                 }
                              }

                              return "Unknown command: " + cmd;
                           case 110364485:
                              if (!command.equals("timer")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label673;
                           case 503572695:
                              if (!command.equals("keybinds")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label672;
                           case 1103187521:
                              if (command.equals("reminders")) {
                                 if (reminders.isEmpty()) {
                                    output = "No active reminders";
                                 } else {
                                    long now = System.currentTimeMillis();
                                    Iterable mapSource = CollectionsKt.take(reminders, 3);
                                    Iterable dest = mapSource;
                                    var destMapped = new ArrayList(CollectionsKt.collectionSizeOrDefault(mapSource, 10));

                                    for (Object item : dest) {
                                       CliEngine.Reminder reminder = (CliEngine.Reminder)item;
                                       Collection collector = destMapped;
                                       long remaining = (reminder.getTriggerTime() - now) / 1000;
                                       collector.add("#" + reminder.getId() + ": " + INSTANCE.formatDuration(remaining * 1000));
                                    }

                                    List list = (List)destMapped;
                                    output = "Reminders: " + CollectionsKt.joinToString(list, ", ", "", "", -1, "...", null);
                                 }

                                 return output;
                              }

                              return "Unknown command: " + cmd;
                           case 1780471020:
                              if (!command.equals("cancelreminder")) {
                                 return "Unknown command: " + cmd;
                              }
                              break label668;
                           case 1784425883:
                              if (command.equals("noteclear")) {
                                 return "Unknown command: " + cmd;
                              }

                              return "Unknown command: " + cmd;
                           case 1983864468:
                              if (command.equals("clearreminders")) {
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
                                 output = String.valueOf((long)result.doubleValue());
                              } else {
                                 String fmtPrecise = "%.10f";
                                 Object[] argsResult = new Object[]{result};
                                 output = String.format(fmtPrecise, Arrays.copyOf(argsResult, argsResult.length));
                                 Intrinsics.checkNotNullExpressionValue(output, "format(...)");
                                 char[] zeroChars = new char[]{'0'};
                                 output = StringsKt.trimEnd(output, zeroChars);
                                 char[] dotChars = new char[]{'.'};
                                 output = StringsKt.trimEnd(output, dotChars);
                              }

                              String formatted = output;
                              GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), formatted);
                              output = arg + " = " + formatted + " (copied)";
                           } else {
                              output = "Invalid expression";
                           }

                           return output;
                        } else {
                           return "Usage: calc <expr>";
                        }
                     }

                     if (arg.length() > 0) {
                        String work = arg;
                        String[] space = new String[]{" "};
                        List timeParts = StringsKt.split(work, space, false, 2);
                        String timeStr = (String)timeParts.get(0);
                        work = (String)CollectionsKt.getOrNull(timeParts, 1);
                        if (work == null) {
                           work = "Reminder!";
                        }

                        String message = work;
                        Long delayMs = this.parseTimeString(timeStr);
                        if (delayMs != null && delayMs > 0L) {
                           int id = this.addReminder(delayMs, message);
                           String timeFormatted = this.formatDuration(delayMs);
                           work = "Reminder #" + id + " set for " + timeFormatted;
                        } else {
                           work = "Invalid time. Use: 30s, 5m, 1h, 1h30m";
                        }

                        return work;
                     } else {
                        return "Usage: remind <time> [message]";
                     }
                  }

                  Iterable keybindSource = this.keybindSettings();
                  Iterable filterSource = keybindSource;
                  var dest = new ArrayList();

                  for (Object element : filterSource) {
                     KeybindSetting it = (KeybindSetting)element;
                     if (it.getValue().intValue() != -1 && it.getValue().intValue() > 0) {
                        dest.add(element);
                     }
                  }

                  List bound = (List)dest;
                  if (bound.isEmpty()) {
                     output = "No keybinds set";
                  } else {
                     Iterable mapSource = CollectionsKt.take(bound, 5);
                     Iterable mapItems = mapSource;
                     var destMapped = new ArrayList(CollectionsKt.collectionSizeOrDefault(mapSource, 10));

                     for (Object item : mapItems) {
                        KeybindSetting bindingSetting = (KeybindSetting)item;
                        Collection collector = destMapped;
                        int v = bindingSetting.getValue().intValue();
                        String keyChar = (32 <= v ? v < 127 : false) ? String.valueOf((char)v) : "F" + (v - 290 + 1);
                        collector.add(bindingSetting.getName() + "=" + keyChar);
                     }

                     keybindSource = (List)destMapped;
                     output = CollectionsKt.joinToString(keybindSource, ", ", "", "", -1, "...", null);
                  }

                  return output;
               }

               if (arg.length() > 0) {
                  Iterable features = this.booleanSettings();
                  Iterator featureIterator = features.iterator();
                  Object foundFeature;

                  while (true) {
                     if (!featureIterator.hasNext()) {
                        foundFeature = null;
                        break;
                     }

                     Object featureCandidate = featureIterator.next();
                     BooleanSetting it = (BooleanSetting)featureCandidate;
                     output = it.getName().toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(output, "toLowerCase(...)");
                     String lowerArg = arg.toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(lowerArg, "toLowerCase(...)");
                     if (Intrinsics.areEqual(output, lowerArg)) {
                        foundFeature = featureCandidate;
                        break;
                     }
                  }

                  BooleanSetting setting = (BooleanSetting)foundFeature;
                  if (setting != null) {
                     boolean newValue = !setting.getValue();
                     setting.setValue(newValue);
                     String status = newValue ? "§aON" : "§cOFF";
                     Config.INSTANCE.save();
                     output = setting.getName() + ": " + status;
                  } else {
                     output = "Feature not found: " + arg;
                  }

                  return output;
               } else {
                  return "Usage: t <feature name>";
               }
            }

            String result = Math.random() < 0.5 ? "Heads" : "Tails";
            return "Coin flip: " + result;
         }

         Integer maxOpt = StringsKt.toIntOrNull(arg);
         int max = maxOpt != null ? maxOpt : 6;
         if (max < 1) {
            output = "Max must be at least 1";
         } else {
            int result = RangesKt.random(new IntRange(1, max), Random.Default);
            output = "Roll (1-" + max + "): " + result;
         }

         return output;
      }

      Integer id = StringsKt.toIntOrNull(arg);
      if (id != null) {
         boolean success = this.cancelReminder(id);
         output = success ? "Reminder #" + id + " cancelled" : "Reminder #" + id + " not found";
      } else {
         output = "Usage: cancelreminder <id>";
      }

      return output;
   }

   private final String executeCbCommand(String arg) {
      String subCmd;
      String output;
      label500: {
         CharSequence tmp = arg;
         String[] subCmdx = new String[]{" "};
         List cbParts = StringsKt.split(tmp, subCmdx, false, 2);
         String part = ((String)cbParts.get(0)).toLowerCase(Locale.ROOT);
         Intrinsics.checkNotNullExpressionValue(part, "toLowerCase(...)");
         subCmd = part;
         part = (String)CollectionsKt.getOrNull(cbParts, 1);
         if (part != null) {
            output = StringsKt.trim(part).toString();
            if (output != null) {
               break label500;
            }
         }

         output = "";
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
                              cbArg = output;
                              String command = subCmd;
                              switch (command.hashCode()) {
                                 case -1785238953:
                                    if (!command.equals("favorites")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label620;
                                 case -1715965556:
                                    if (command.equals("selection")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -1396673086:
                                    if (command.equals("backup")) {
                                       return ConfigBackup.INSTANCE.backup();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -1352294148:
                                    if (command.equals("create")) {
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
                                    if (!command.equals("delete")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0
                                       ? (CosmeticBlocks.INSTANCE.deleteConfig(cbArg) ? "Deleted config '" + cbArg + "'" : "Cannot delete '" + cbArg + "'")
                                       : "Usage: cb delete <name>";
                                 case -934641255:
                                    if (command.equals("reload")) {
                                       CosmeticBlocks.INSTANCE.load();
                                       return "Reloaded (" + CosmeticBlocks.INSTANCE.getReplacementCount() + " in dimension)";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case -934610812:
                                    if (!command.equals("remove")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break;
                                 case -926053069:
                                    if (command.equals("properties")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 0:
                                    if (command.equals("")) {
                                       int count;
                                       String mode;
                                       label334: {
                                          count = CosmeticBlocks.INSTANCE.getReplacementCount();
                                          mode = CosmeticBlocks.INSTANCE.getEditMode() ? "ON" : "OFF";
                                          ScannedRoom scannedRoom = RoomScanner.INSTANCE.getCurrentRoom();
                                          if (scannedRoom != null) {
                                             ScannedRoom it = scannedRoom;
                                             output = " | Room: " + it.getData().getName();
                                             if (output != null) {
                                                break label334;
                                             }
                                          }

                                          output = "";
                                       }

                                       String roomInfo = output;
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
                                    if (!command.equals("b")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label617;
                                 case 102:
                                    if (!command.equals("f")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0 ? CosmeticBlocks.INSTANCE.fillRegion(cbArg) : "Usage: cb fill <block[:weight]...>";
                                 case 114:
                                    if (!command.equals("r")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 case 117:
                                    if (command.equals("u")) {
                                       return CosmeticBlocks.INSTANCE.undoLastOperation();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3240:
                                    if (!command.equals("em")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label618;
                                 case 3463:
                                    if (!command.equals("ls")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label616;
                                 case 3521:
                                    if (!command.equals("p1")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label623;
                                 case 3522:
                                    if (!command.equals("p2")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label622;
                                 case 3643:
                                    if (!command.equals("rm")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break;
                                 case 3681:
                                    if (!command.equals("st")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label619;
                                 case 99339:
                                    if (!command.equals("del")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0
                                       ? (CosmeticBlocks.INSTANCE.deleteConfig(cbArg) ? "Deleted config '" + cbArg + "'" : "Cannot delete '" + cbArg + "'")
                                       : "Usage: cb delete <name>";
                                 case 101147:
                                    if (!command.equals("fav")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label620;
                                 case 102715:
                                    if (command.equals("gui")) {
                                       Scheduler.INSTANCE.scheduleTickEnd(CliEngine::openBlockSelectorScreen);
                                       return "Opening block selector...";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 112797:
                                    if (!command.equals("rep")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 case 113754:
                                    if (command.equals("sel")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3108362:
                                    if (!command.equals("edit")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label618;
                                 case 3143043:
                                    if (!command.equals("fill")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return cbArg.length() > 0 ? CosmeticBlocks.INSTANCE.fillRegion(cbArg) : "Usage: cb fill <block[:weight]...>";
                                 case 3322014:
                                    if (!command.equals("list")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label616;
                                 case 3327206:
                                    if (command.equals("load")) {
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
                                    if (!command.equals("pos1")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label623;
                                 case 3446878:
                                    if (!command.equals("pos2")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label622;
                                 case 3506395:
                                    if (command.equals("room")) {
                                       label626: {
                                          CharSequence argChars = cbArg;
                                          String[] mode = new String[]{" "};
                                          List roomParts = StringsKt.split(argChars, mode, false, 2);
                                          String roomText = ((String)roomParts.get(0)).toLowerCase(Locale.ROOT);
                                          Intrinsics.checkNotNullExpressionValue(roomText, "toLowerCase(...)");
                                          String roomCmd = roomText;
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
                                                      RoomInfo roomData = room.getData();
                                                      if (roomData != null) {
                                                         roomText = roomData.getName();
                                                         if (roomText != null) {
                                                            return "Room: " + roomText;
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
                                                      roomText = "Cleared " + count + " blocks from " + room.getData().getName();
                                                   } else {
                                                      roomText = "Not in a scanned room";
                                                   }

                                                   return roomText;
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
                                             roomText = "Room: " + room.getData().getName() + " | Rot: " + room.getRotation() + " | Blocks: " + blockCount;
                                          } else {
                                             roomText = "Not in a scanned dungeon room";
                                          }

                                          return roomText;
                                       }

                                       List rooms = CosmeticBlocks.INSTANCE.listRoomNames();
                                       return rooms.isEmpty()
                                          ? "No room blocks saved"
                                          : CollectionsKt.joinToString(rooms, ", ", "", "", -1, "...", it -> CliEngine.roomWithBlockCount((String)it));
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3522941:
                                    if (command.equals("save")) {
                                       CosmeticBlocks.INSTANCE.save();
                                       return "CB data saved";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 3594468:
                                    if (command.equals("undo")) {
                                       return CosmeticBlocks.INSTANCE.undoLastOperation();
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 93832333:
                                    if (!command.equals("block")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label617;
                                 case 94746189:
                                    if (command.equals("clear")) {
                                       int count = CosmeticBlocks.INSTANCE.clearDimension();
                                       return "Cleared " + count + " replacements";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 106940784:
                                    if (command.equals("props")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 109399969:
                                    if (command.equals("shape")) {
                                       if (cbArg.length() == 0) {
                                          output = CosmeticBlocks.INSTANCE.getCurrentShape();
                                          if (output == null) {
                                             output = "none";
                                          }
                                       } else if (!Intrinsics.areEqual(cbArg, "none") && !Intrinsics.areEqual(cbArg, "off")) {
                                          String[] shapeNames = new String[]{"fence", "wall", "pane"};
                                          if (CollectionsKt.listOf(shapeNames).contains(cbArg)) {
                                             CosmeticBlocks.INSTANCE.setCurrentShape(cbArg);
                                             output = "Shape: " + cbArg + " (next blocks will use this shape)";
                                          } else {
                                             output = "Shapes: fence, wall, pane, none";
                                          }

                                          return output;
                                       } else {
                                          CosmeticBlocks.INSTANCE.setCurrentShape(null);
                                          return "Shape override cleared";
                                       }

                                       return "Shape: " + output;
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 109757585:
                                    if (!command.equals("state")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label619;
                                 case 790299700:
                                    if (command.equals("clearall")) {
                                       int count = CosmeticBlocks.INSTANCE.clearAll();
                                       return "Cleared " + count + " total replacements";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 951117169:
                                    if (command.equals("configs")) {
                                       List configs = CosmeticBlocks.INSTANCE.listConfigs();
                                       return "Configs: "
                                          + CollectionsKt.joinToString(configs, ", ", "", "", -1, "...", null)
                                          + " (active: "
                                          + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue()
                                          + ")";
                                    }

                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                 case 1094496948:
                                    if (!command.equals("replace")) {
                                       return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                                    }
                                    break label621;
                                 default:
                                    return "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
                              }

                              String removeResult = cbArg;
                              String[] space = new String[]{" "};
                              List coords = StringsKt.split(removeResult, space, false, 0);
                              if (coords.size() >= 3) {
                                 Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                                 Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                                 Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                                 removeResult = x != null && y != null && z != null
                                    ? (
                                       CosmeticBlocks.INSTANCE.removeAt(x, y, z)
                                          ? "Removed at " + x + "," + y + "," + z
                                          : "No replacement at " + x + "," + y + "," + z
                                    )
                                    : "Invalid coords";
                              } else {
                                 removeResult = "Usage: cb remove <x> <y> <z>";
                              }

                              return removeResult;
                           }

                           String pos1Result = cbArg;
                           String[] space = new String[]{" "};
                           List coords = StringsKt.split(pos1Result, space, false, 0);
                           if (coords.size() >= 3) {
                              Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                              Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                              Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                              if (x != null && y != null && z != null) {
                                 CosmeticBlocks.INSTANCE.setPos1(new BlockPos(x, y, z));
                                 pos1Result = "Pos1: " + x + ", " + y + ", " + z;
                              } else {
                                 pos1Result = "Invalid coordinates";
                              }

                              return pos1Result;
                           } else {
                              BlockPos pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                              if (pos != null) {
                                 CosmeticBlocks.INSTANCE.setPos1(pos);
                                 pos1Result = "Pos1: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                              } else {
                                 pos1Result = "Look at a block first, then open CLI. Or: cb pos1 <x> <y> <z>";
                              }

                              return pos1Result;
                           }
                        }

                        String pos2Result = cbArg;
                        String[] space = new String[]{" "};
                        List coords = StringsKt.split(pos2Result, space, false, 0);
                        if (coords.size() >= 3) {
                           Integer x = StringsKt.toIntOrNull((String)coords.get(0));
                           Integer y = StringsKt.toIntOrNull((String)coords.get(1));
                           Integer z = StringsKt.toIntOrNull((String)coords.get(2));
                           if (x != null && y != null && z != null) {
                              CosmeticBlocks.INSTANCE.setPos2(new BlockPos(x, y, z));
                              pos2Result = "Pos2: " + x + ", " + y + ", " + z;
                           } else {
                              pos2Result = "Invalid coordinates";
                           }

                           return pos2Result;
                        } else {
                           BlockPos pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                           if (pos != null) {
                              CosmeticBlocks.INSTANCE.setPos2(pos);
                              pos2Result = "Pos2: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                           } else {
                              pos2Result = "Look at a block first, then open CLI. Or: cb pos2 <x> <y> <z>";
                           }

                           return pos2Result;
                        }
                     }

                     String from;
                     label403: {
                        CharSequence replaceChars = cbArg;
                        String[] space = new String[]{" "};
                        List rParts = StringsKt.split(replaceChars, space, false, 2);
                        from = (String)rParts.get(0);
                        replaceChars = (String)CollectionsKt.getOrNull(rParts, 1);
                        if (replaceChars != null) {
                           output = StringsKt.trim(replaceChars).toString();
                           if (output != null) {
                              break label403;
                           }
                        }

                        output = "";
                     }

                     String to = output;
                     if (from.length() > 0 && to.length() > 0) {
                        return CosmeticBlocks.INSTANCE.replaceRegion(from, to);
                     }

                     return "Usage: cb replace <from> <to[:weight]...>";
                  }

                  String favCmd;
                  label392: {
                     CharSequence favChars = cbArg;
                     String[] space = new String[]{" "};
                     List favParts = StringsKt.split(favChars, space, false, 2);
                     String subPart = ((String)favParts.get(0)).toLowerCase(Locale.ROOT);
                     Intrinsics.checkNotNullExpressionValue(subPart, "toLowerCase(...)");
                     favCmd = subPart;
                     subPart = (String)CollectionsKt.getOrNull(favParts, 1);
                     if (subPart != null) {
                        output = StringsKt.trim(subPart).toString();
                        if (output != null) {
                           break label392;
                        }
                     }

                     output = "";
                  }

                  String favArg = output;
                  if (favCmd.length() != 0 && !Intrinsics.areEqual(favCmd, "gui")) {
                     if (Intrinsics.areEqual(favCmd, "add")) {
                        CharSequence favArgChars = favArg;
                        if (favArgChars.length() == 0) {
                           output = CosmeticBlocks.INSTANCE.getSelectedBlock();
                        } else {
                            output = favArgChars.toString();
                        }

                        String block = output;
                        return CosmeticBlocks.INSTANCE.addFavorite(block)
                           ? "Added " + StringsKt.removePrefix(block, "minecraft:") + " to favorites"
                           : "Already in favorites or invalid block";
                     } else if (Intrinsics.areEqual(favCmd, "remove") || Intrinsics.areEqual(favCmd, "rm")) {
                        CharSequence favArgChars2 = favArg;
                        if (favArgChars2.length() == 0) {
                           output = CosmeticBlocks.INSTANCE.getSelectedBlock();
                        } else {
                           output = favArgChars2.toString();
                        }

                        String block = output;
                        return CosmeticBlocks.INSTANCE.removeFavorite(block)
                           ? "Removed " + StringsKt.removePrefix(block, "minecraft:") + " from favorites"
                           : "Not in favorites";
                     } else {
                        if (!Intrinsics.areEqual(favCmd, "list") && !Intrinsics.areEqual(favCmd, "ls")) {
                           output = "Usage: cb fav [add|remove|list|gui]";
                        } else {
                           List favs = CosmeticBlocks.INSTANCE.getFavorites();
                           output = favs.isEmpty()
                              ? "No favorites"
                              : CollectionsKt.joinToString(favs, ", ", "", "", -1, "...", it -> CliEngine.stripMinecraftPrefix((String)it));
                        }

                        return output;
                     }
                  }

                  Scheduler.INSTANCE.scheduleTickEnd(CliEngine::openFavoritesScreen);
                  return "Opening favorites...";
               }

               String property;
               label346: {
                  CharSequence stateChars = cbArg;
                  String[] space = new String[]{" "};
                  List stateParts = StringsKt.split(stateChars, space, false, 2);
                  property = (String)stateParts.get(0);
                  stateChars = (String)CollectionsKt.getOrNull(stateParts, 1);
                  if (stateChars != null) {
                     output = StringsKt.trim(stateChars).toString();
                     if (output != null) {
                        break label346;
                     }
                  }

                  output = "";
               }

               String value = output;
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
            output = "Brush: " + StringsKt.replace(cbArg, " ", "_", false);
         } else {
            output = "Usage: cb block <block_id>";
         }

         return output;
      }

      List list = CosmeticBlocks.INSTANCE.listReplacements();
      return list.isEmpty()
         ? "No CB replacements"
         : CollectionsKt.joinToString(CollectionsKt.take(list, 5), " | ", "", "", -1, "...", it -> CliEngine.truncateReplacement((String)it));
   }

   private final boolean exportToClipboard() {
      boolean success;
      try {
         StringBuilder builder = new StringBuilder();
         builder.append("# CB Settings Export").append('\n');
         Iterable tabs = Tabs.INSTANCE.getAll();

         for (Object element : tabs) {
            SettingsTab tab = (SettingsTab)element;
            Iterable settings = tab.getSettings();

            for (Object settingElement : settings) {
               Setting setting = (Setting)settingElement;
               builder.append(tab.getName() + "." + setting.getName() + "=" + INSTANCE.valueToString(setting)).append('\n');
            }
         }

         GLFW.glfwSetClipboardString(GLFW.glfwGetCurrentContext(), builder.toString());
         success = true;
      } catch (Exception e) {
         e.printStackTrace();
         success = false;
      }

      return success;
   }

   private final boolean importFromClipboard() {
      boolean data;
      try {
         String clipboard = GLFW.glfwGetClipboardString(GLFW.glfwGetCurrentContext());
         if (clipboard == null) {
            return false;
         }

         String datax = clipboard;
         int imported = 0;
         Iterable lines = StringsKt.lines(datax);

         for (Object element : lines) {
            String line = (String)element;
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               CharSequence lineChars = line;
               String[] keyParts = new String[]{"="};
               List parts = StringsKt.split(lineChars, keyParts, false, 2);
               if (parts.size() == 2) {
                  lineChars = (CharSequence)parts.get(0);
                  String[] tabName = new String[]{"."};
                  List keyPartsx = StringsKt.split(lineChars, tabName, false, 2);
                  if (keyPartsx.size() == 2) {
                     String tabNamex = (String)keyPartsx.get(0);
                     String propName = (String)keyPartsx.get(1);
                     Iterable tabs = Tabs.INSTANCE.getAll();
                     Iterator tabIterator = tabs.iterator();
                     Object foundTab;

                     while (true) {
                        if (tabIterator.hasNext()) {
                           Object tabCandidate = tabIterator.next();
                           SettingsTab it = (SettingsTab)tabCandidate;
                           if (!Intrinsics.areEqual(it.getName(), tabNamex)) {
                              continue;
                           }

                           foundTab = tabCandidate;
                           break;
                        }

                        foundTab = null;
                        break;
                     }

                     SettingsTab tabResult = (SettingsTab)foundTab;
                     if ((SettingsTab)foundTab != null) {
                        SettingsTab tab = tabResult;
                        Iterable settings = tab.getSettings();
                        Iterator settingIterator = settings.iterator();
                        Object foundSetting;

                        while (true) {
                           if (settingIterator.hasNext()) {
                              Object settingCandidate = settingIterator.next();
                              Setting it = (Setting)settingCandidate;
                              if (!Intrinsics.areEqual(it.getName(), propName)) {
                                 continue;
                              }

                              foundSetting = settingCandidate;
                              break;
                           }

                           foundSetting = null;
                           break;
                        }

                        Setting foundSetting2 = (Setting)foundSetting;
                        if ((Setting)foundSetting != null) {
                           Setting setting = foundSetting2;
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
         Regex invalidChars = new Regex("[^a-zA-Z0-9_-]");
         String underscore = "_";
         String sanitizedx = invalidChars.replace(e, underscore);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Path profilesDir = this.getProfilesDir();
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(profilesDir, Arrays.copyOf(linkOptions, linkOptions.length))) {
            Files.createDirectories(this.getProfilesDir());
         }

         var bufferedWriter = Files.newBufferedWriter(file);
         Throwable cause = null;

         try {
            BufferedWriter writer = bufferedWriter;
            writer.write("# CB Profile: " + name + "\n");
            Iterable tabs = Tabs.INSTANCE.getAll();

            for (Object element : tabs) {
               SettingsTab tab = (SettingsTab)element;
               Iterable settings = tab.getSettings();

               for (Object settingElement : settings) {
                  Setting setting = (Setting)settingElement;
                  writer.write(tab.getName() + "." + setting.getName() + ":" + INSTANCE.valueToString(setting) + "\n");
               }
            }

            Unit unit = Unit.INSTANCE;
         } catch (Throwable thrown) {
            cause = thrown;
            throw thrown;
         } finally {
            CloseableKt.closeFinally(bufferedWriter, cause);
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
         String underscore = "_";
         String sanitizedx = map.replace(e, underscore);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Intrinsics.checkNotNull(file);
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            return false;
         }

         Map mapx = new LinkedHashMap();
         List lines = Files.readAllLines(file);
         Intrinsics.checkNotNullExpressionValue(lines, "readAllLines(...)");
         Iterable readLines = lines;

         for (Object element : readLines) {
            String line = (String)element;
            Intrinsics.checkNotNull(line);
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               int idx = StringsKt.indexOf(line, ':', 0, false);
               if (idx > 0) {
                  String key = line.substring(0, idx);
                  Intrinsics.checkNotNullExpressionValue(key, "substring(...)");
                  String val = line.substring(idx + 1);
                  Intrinsics.checkNotNullExpressionValue(val, "substring(...)");
                  mapx.put(key, val);
               }
            }
         }

         Iterable tabs = Tabs.INSTANCE.getAll();

         for (Object element : tabs) {
            SettingsTab tab = (SettingsTab)element;
            Iterable settings = tab.getSettings();

            for (Object settingElement : settings) {
               Setting setting = (Setting)settingElement;
               String stored = (String)mapx.get(tab.getName() + "." + setting.getName());
               if (stored != null) {
                  String v = stored;
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
      Path profilesDir = this.getProfilesDir();
      LinkOption[] linkOptions = new LinkOption[0];
      if (!Files.exists(profilesDir, Arrays.copyOf(linkOptions, linkOptions.length))) {
         return CollectionsKt.emptyList();
      }

      CliEngine engine = this;

      List<String> result = null;
      try {
         Iterable entries = PathsKt.listDirectoryEntries(engine.getProfilesDir(), "*.profile");
         Iterable entrySource = entries;
         var dest = new ArrayList(CollectionsKt.collectionSizeOrDefault(entries, 10));

         for (Object item : entrySource) {
            Path it = (Path)item;
            Collection collector = dest;
            collector.add(PathsKt.getNameWithoutExtension(it));
         }

         result = (List<String>)CollectionsKt.sorted((Iterable & List)dest);
      } catch (Throwable exception) {
         result = null;
      }

      return result != null ? result : CollectionsKt.emptyList();
   }

   private final boolean deleteProfile(String name) {
      boolean sanitized;
      try {
         CharSequence e = name;
         Regex invalidChars = new Regex("[^a-zA-Z0-9_-]");
         String underscore = "_";
         String sanitizedx = invalidChars.replace(e, underscore);
         Path file = this.getProfilesDir().resolve(sanitizedx + ".profile");
         Intrinsics.checkNotNull(file);
         LinkOption[] linkOptions = new LinkOption[0];
         boolean deleted;
         if (Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            Files.delete(file);
            deleted = true;
         } else {
            deleted = false;
         }

         sanitized = deleted;
      } catch (Exception e) {
         e.printStackTrace();
         sanitized = false;
      }

      return sanitized;
   }

   private final String valueToString(Setting<?> setting) {
      Object v = setting.getValue();
      Object raw = v;
      return raw instanceof Integer
         ? String.valueOf(((Number)v).intValue())
         : (
            raw instanceof Double
               ? String.valueOf(((Number)v).doubleValue())
               : (
                  raw instanceof Float
                     ? String.valueOf(((Number)v).floatValue())
                     : (raw instanceof Boolean ? String.valueOf(((Boolean)v).booleanValue()) : (raw instanceof Enum ? ((Enum)v).name() : String.valueOf(v)))
               )
         );
   }

   private final boolean stringToValue(Setting<?> setting, String raw) {
      boolean cur;
      try {
         Object curx = setting.getValue();
         Setting rawSetting = setting;
         boolean converted;
         if (curx instanceof Integer) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Int>");
            rawSetting.setValue(Integer.parseInt(raw));
            converted = true;
         } else if (curx instanceof Double) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Double>");
            rawSetting.setValue(Double.parseDouble(raw));
            converted = true;
         } else if (curx instanceof Float) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Float>");
            rawSetting.setValue(Float.parseFloat(raw));
            converted = true;
         } else if (curx instanceof Boolean) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Boolean>");
            rawSetting.setValue(Boolean.parseBoolean(raw));
            converted = true;
         } else if (curx instanceof String) {
            Intrinsics.checkNotNull(setting, "null cannot be cast to non-null type com.cb.setting.Setting<kotlin.String>");
            rawSetting.setValue(raw);
            converted = true;
         } else if (!(curx instanceof Enum)) {
            converted = false;
         } else {
            Enum[] constants = (Enum[])curx.getClass().getEnumConstants();
            Intrinsics.checkNotNull(constants);
            Object[] constantsArray = constants;
            int index = 0;
            int count = constantsArray.length;
            Enum match = null;

            while (true) {
               if (index < count) {
                  Object element = constantsArray[index];
                  Enum it = (Enum)element;
                  if (!Intrinsics.areEqual(it.name(), raw)) {
                     index++;
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
            converted = true;
         }

         cur = converted;
      } catch (Exception ignored) {
         cur = false;
      }

      return cur;
   }

   public final void saveSavedColors() {
      try {
         Path file = this.getSavedColorsFile();
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            PathsKt.createParentDirectories(this.getSavedColorsFile());
            file = this.getSavedColorsFile();
            FileAttribute[] attrs = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createFile(file, Arrays.copyOf(attrs, attrs.length)), "createFile(...)");
         }

         var bufferedWriter = Files.newBufferedWriter(this.getSavedColorsFile());
         Throwable cause = null;

         try {
            BufferedWriter writer = bufferedWriter;
            Iterable colors = savedColors;

            for (Object element : colors) {
               Color color = (Color)element;
               writer.write(color.getRGB() + "\n");
            }

            Unit unit = Unit.INSTANCE;
         } catch (Throwable thrown) {
            cause = thrown;
            throw thrown;
         } finally {
            CloseableKt.closeFinally(bufferedWriter, cause);
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void loadSavedColors() {
      try {
         Path file = this.getSavedColorsFile();
         LinkOption[] linkOptions = new LinkOption[0];
         if (Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            savedColors.clear();
            var bufferedReader = Files.newBufferedReader(this.getSavedColorsFile());
            Throwable cause = null;

            try {
               BufferedReader reader = bufferedReader;

               while (true) {
                  String lineRead = reader.readLine();
                  if (lineRead == null) {
                     Unit unit = Unit.INSTANCE;
                     return;
                  }

                  String line = lineRead;
                  Integer parsed = StringsKt.toIntOrNull(line);
                  if (parsed != null) {
                     int it = parsed.intValue();
                     savedColors.add(new Color(it, true));
                  }
               }
            } catch (Throwable thrown) {
               cause = thrown;
               throw thrown;
            } finally {
               CloseableKt.closeFinally(bufferedReader, cause);
            }
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void saveNotes() {
      try {
         Path file = this.getNotesFile();
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            PathsKt.createParentDirectories(this.getNotesFile());
            file = this.getNotesFile();
            FileAttribute[] attrs = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createFile(file, Arrays.copyOf(attrs, attrs.length)), "createFile(...)");
         }

         var bufferedWriter = Files.newBufferedWriter(this.getNotesFile());
         Throwable cause = null;

         try {
            BufferedWriter writer = bufferedWriter;
            Iterable notesList = notes;

            for (Object element : notesList) {
               String note = (String)element;
               writer.write(note + "\n");
            }

            Unit unit = Unit.INSTANCE;
         } catch (Throwable thrown) {
            cause = thrown;
            throw thrown;
         } finally {
            CloseableKt.closeFinally(bufferedWriter, cause);
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private final void loadNotes() {
      try {
         Path file = this.getNotesFile();
         LinkOption[] linkOptions = new LinkOption[0];
         if (Files.exists(file, Arrays.copyOf(linkOptions, linkOptions.length))) {
            notes.clear();
            var bufferedReader = Files.newBufferedReader(this.getNotesFile());
            Throwable cause = null;

            try {
               BufferedReader reader = bufferedReader;

               while (true) {
                  String lineRead = reader.readLine();
                  if (lineRead == null) {
                     Unit unit = Unit.INSTANCE;
                     return;
                  }

                  String line = lineRead;
                  if (!StringsKt.isBlank(line)) {
                     notes.add(line);
                  }
               }
            } catch (Throwable thrown) {
               cause = thrown;
               throw thrown;
            } finally {
               CloseableKt.closeFinally(bufferedReader, cause);
            }
         }
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
   }

   private static final boolean cancelReminderPredicate(int targetId, CliEngine.Reminder it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getId() == targetId;
   }

   private static final boolean invokePredicate(Function1 function, Object argument) {
      return (Boolean)function.invoke(argument);
   }

   private static final String blockIdPath(Identifier it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getPath();
   }

   private static final boolean blockPathFilter(String query, String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return query.length() == 0 || StringsKt.contains(it, query, false);
   }

   private static final CliEngine.Suggestion blockSuggestion(String prefix, String hint, String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return new CliEngine.Suggestion(prefix + " " + it, it, hint);
   }

   private static final CharSequence truncateNoteText(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.take(it, 30) + (it.length() > 30 ? "..." : "");
   }

   private static final CharSequence truncateReplacement(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.take(it, 25);
   }

   private static final Unit openFavoritesScreen() {
      Minecraft.getInstance().setScreenAndShow(new BlockSelectorScreen());
      return Unit.INSTANCE;
   }

   private static final CharSequence stripMinecraftPrefix(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return StringsKt.removePrefix(it, "minecraft:");
   }

   private static final CharSequence roomWithBlockCount(String it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it + " (" + CosmeticBlocks.INSTANCE.getRoomBlockCount(it) + ")";
   }

   private static final Unit openBlockSelectorScreen() {
      Minecraft.getInstance().setScreenAndShow(new BlockSelectorScreen());
      return Unit.INSTANCE;
   }

   static {
      Path configPath = FabricLoader.getInstance().getConfigDir().resolve("cb");
      Intrinsics.checkNotNullExpressionValue(configPath, "resolve(...)");
      configDir = configPath;
      String[] commands = new String[]{
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
      TOP_COMMANDS = CollectionsKt.listOf(commands);
      commands = new String[]{
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
      CB_SUBCOMMANDS = CollectionsKt.listOf(commands);
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

            String tmp = this.expr.substring(start, this.pos);
            Intrinsics.checkNotNullExpressionValue(tmp, "substring(...)");
            tmp = tmp.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(tmp, "toLowerCase(...)");
            String name = tmp;
            String funcKey = name;
            double computed;
            switch (funcKey.hashCode()) {
               case 101:
                  if (!funcKey.equals("e")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  computed = Math.E;
                  break;
               case 3458: {
                  if (!funcKey.equals("ln")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.log(arg);
                  break;
               }
               case 3577:
                  if (!funcKey.equals("pi")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  computed = Math.PI;
                  break;
               case 96370: {
                  if (!funcKey.equals("abs")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.abs(arg);
                  break;
               }
               case 98695: {
                  if (!funcKey.equals("cos")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.cos(arg);
                  break;
               }
               case 107332: {
                  if (!funcKey.equals("log")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.log10(arg);
                  break;
               }
               case 113880: {
                  if (!funcKey.equals("sin")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.sin(arg);
                  break;
               }
               case 114593: {
                  if (!funcKey.equals("tan")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.tan(arg);
                  break;
               }
               case 3049733: {
                  if (!funcKey.equals("ceil")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.ceil(arg);
                  break;
               }
               case 3538208: {
                  if (!funcKey.equals("sqrt")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.sqrt(arg);
                  break;
               }
               case 97526796: {
                  if (!funcKey.equals("floor")) {
                     throw new IllegalArgumentException("Unknown function: " + name);
                  }

                  this.expect('(');
                  double arg = this.parseExpression();
                  this.expect(')');
                  computed = Math.floor(arg);
                  break;
               }
               case 108704142: {
                  if (funcKey.equals("round")) {
                     this.expect('(');
                     double arg = this.parseExpression();
                     this.expect(')');
                     computed = Math.rint(arg);
                     break;
                  }

                  throw new IllegalArgumentException("Unknown function: " + name);
               }
               default:
                  throw new IllegalArgumentException("Unknown function: " + name);
            }

            return computed;
         }
      }

      private final double parseNumber() {
         this.skipWhitespace();
         int start = this.pos;
         if (this.pos < this.length && (this.expr.charAt(this.pos) == '-' || this.expr.charAt(this.pos) == '+')) {
            int consumed = this.pos++;
         }

         while (this.pos < this.length && (Character.isDigit(this.expr.charAt(this.pos)) || this.expr.charAt(this.pos) == '.')) {
            int digitAdvance = this.pos++;
         }

         if (start == this.pos) {
            throw new IllegalArgumentException("Expected number at " + this.pos);
         }

         String numberText = this.expr.substring(start, this.pos);
         Intrinsics.checkNotNullExpressionValue(numberText, "substring(...)");
         return Double.parseDouble(numberText);
      }

      private final boolean match(char c) {
         this.skipWhitespace();
         boolean matched;
         if (this.pos < this.length && this.expr.charAt(this.pos) == c) {
            int consumed = this.pos++;
            matched = true;
         } else {
            matched = false;
         }

         return matched;
      }

      private final void expect(char c) {
         if (!this.match(c)) {
            throw new IllegalArgumentException("Expected '" + c + "'");
         }
      }

      private final void skipWhitespace() {
         while (this.pos < this.length && CharsKt.isWhitespace(this.expr.charAt(this.pos))) {
            int space = this.pos++;
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
         } else if (!(other instanceof CliEngine.Reminder that)) {
            return false;
         } else if (this.triggerTime != that.triggerTime) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.message, that.message) ? false : this.id == that.id;
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
         } else if (!(other instanceof CliEngine.Suggestion that)) {
            return false;
         } else if (!Intrinsics.areEqual(this.insert, that.insert)) {
            return false;
         } else {
            return !Intrinsics.areEqual(this.display, that.display) ? false : Intrinsics.areEqual(this.hint, that.hint);
         }
      }
   }
}
