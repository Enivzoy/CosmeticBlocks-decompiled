/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.path.PathsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.sequences.Sequence
 *  kotlin.sequences.SequencesKt
 *  kotlin.text.CharsKt
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.gui.screens;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.CosmeticBlocks;
import com.cb.dungeon.RoomScanner;
import com.cb.gui.screens.BlockSelectorScreen;
import com.cb.setting.BooleanSetting;
import com.cb.setting.KeybindSetting;
import com.cb.setting.Setting;
import com.cb.utils.ChatUtil;
import com.cb.utils.ConfigBackup;
import com.cb.utils.Scheduler;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.lang.invoke.LambdaMetafactory;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
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
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.CharsKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003XYZB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u000e\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u0011J\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00110#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u0010!\u001a\u00020\u0011J\u0006\u0010&\u001a\u00020\u001fJ\u0016\u0010'\u001a\u00020\u00052\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010+\u001a\u00020\u001d2\u0006\u0010,\u001a\u00020\u0005J\u0016\u00100\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00101\u001a\u00020\u0011H\u0002J\u0016\u00102\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00103\u001a\u00020\u0011H\u0002J&\u00104\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00105\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020\u0011H\u0002J\u001e\u00108\u001a\b\u0012\u0004\u0012\u00020%0#2\u0006\u00109\u001a\u00020\u00112\u0006\u00106\u001a\u00020\u0011H\u0002J\u000e\u0010:\u001a\b\u0012\u0004\u0012\u00020;0#H\u0002J\u000e\u0010<\u001a\b\u0012\u0004\u0012\u00020=0#H\u0002J\u0017\u0010>\u001a\u0004\u0018\u00010)2\u0006\u0010!\u001a\u00020\u0011H\u0002\u00a2\u0006\u0002\u0010?J\u0010\u0010@\u001a\u00020\u00112\u0006\u0010A\u001a\u00020)H\u0002J\u0017\u0010B\u001a\u0004\u0018\u00010C2\u0006\u0010D\u001a\u00020\u0011H\u0002\u00a2\u0006\u0002\u0010EJ\u0010\u0010F\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u0011H\u0002J\u0010\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020\u0011H\u0002J\b\u0010I\u001a\u00020\u001dH\u0002J\b\u0010J\u001a\u00020\u001dH\u0002J\u0010\u0010K\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u0010\u0010M\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u000e\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00110#H\u0002J\u0010\u0010O\u001a\u00020\u001d2\u0006\u0010L\u001a\u00020\u0011H\u0002J\u0014\u0010P\u001a\u00020\u00112\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030RH\u0002J\u001c\u0010S\u001a\u00020\u001d2\n\u0010Q\u001a\u0006\u0012\u0002\b\u00030R2\u0006\u00101\u001a\u00020\u0011H\u0002J\u0006\u0010T\u001a\u00020\u001fJ\b\u0010U\u001a\u00020\u001fH\u0002J\b\u0010V\u001a\u00020\u001fH\u0002J\b\u0010W\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u000e\u0010\u001b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00110#X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110#X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006["}, d2={"Lcom/cb/gui/screens/CliEngine;", "", "<init>", "()V", "MAX_HISTORY", "", "configDir", "Ljava/nio/file/Path;", "savedColorsFile", "getSavedColorsFile", "()Ljava/nio/file/Path;", "notesFile", "getNotesFile", "profilesDir", "getProfilesDir", "commandHistory", "", "", "savedColors", "Ljava/awt/Color;", "getSavedColors", "()Ljava/util/List;", "notes", "getNotes", "reminders", "Lcom/cb/gui/screens/CliEngine$Reminder;", "getReminders", "nextReminderId", "loaded", "", "ensureLoaded", "", "execute", "input", "history", "", "suggestions", "Lcom/cb/gui/screens/CliEngine$Suggestion;", "checkReminders", "addReminder", "delayMs", "", "message", "cancelReminder", "id", "MAX_SUGGESTIONS", "TOP_COMMANDS", "CB_SUBCOMMANDS", "computeSuggestions", "raw", "cbSuggestions", "rest", "blockSuggestions", "token", "prefix", "hint", "favSuggestions", "args", "booleanSettings", "Lcom/cb/setting/BooleanSetting;", "keybindSettings", "Lcom/cb/setting/KeybindSetting;", "parseTimeString", "(Ljava/lang/String;)Ljava/lang/Long;", "formatDuration", "ms", "evaluateCalcExpression", "", "expr", "(Ljava/lang/String;)Ljava/lang/Double;", "executeCliCommand", "executeCbCommand", "arg", "exportToClipboard", "importFromClipboard", "saveProfile", "name", "loadProfile", "listProfiles", "deleteProfile", "valueToString", "setting", "Lcom/cb/setting/Setting;", "stringToValue", "saveSavedColors", "loadSavedColors", "saveNotes", "loadNotes", "Reminder", "Suggestion", "CalcParser", "cb_client"})
@SourceDebugExtension(value={"SMAP\nCliEngine.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CliEngine.kt\ncom/cb/gui/screens/CliEngine\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,1108:1\n777#2:1109\n873#2,2:1110\n1915#2,2:1112\n1021#2,2:1114\n777#2:1116\n873#2,2:1117\n1586#2:1119\n1661#2,3:1120\n1586#2:1123\n1661#2,3:1124\n777#2:1127\n873#2,2:1128\n1586#2:1130\n1661#2,3:1131\n777#2:1134\n873#2,2:1135\n1586#2:1137\n1661#2,3:1138\n777#2:1141\n873#2,2:1142\n1586#2:1144\n1661#2,3:1145\n1834#2,4:1148\n777#2:1153\n873#2,2:1154\n1586#2:1156\n1661#2,3:1157\n777#2:1160\n873#2,2:1161\n1586#2:1163\n1661#2,3:1164\n777#2:1167\n873#2,2:1168\n1586#2:1170\n1661#2,3:1171\n777#2:1174\n873#2,2:1175\n1586#2:1177\n1661#2,3:1178\n1586#2:1181\n1661#2,3:1182\n777#2:1185\n873#2,2:1186\n1586#2:1188\n1661#2,3:1189\n1391#2:1192\n1480#2,5:1193\n812#2,12:1198\n1391#2:1210\n1480#2,5:1211\n812#2,12:1216\n1586#2:1228\n1661#2,3:1229\n777#2:1232\n873#2,2:1233\n1586#2:1235\n1661#2,3:1236\n1915#2:1239\n1915#2,2:1240\n1916#2:1242\n1915#2,2:1243\n1915#2:1245\n1915#2,2:1246\n1916#2:1248\n1915#2,2:1249\n1915#2:1251\n1915#2,2:1252\n1916#2:1254\n1586#2:1255\n1661#2,3:1256\n1915#2,2:1261\n1915#2,2:1263\n1#3:1152\n1401#4,2:1259\n*S KotlinDebug\n*F\n+ 1 CliEngine.kt\ncom/cb/gui/screens/CliEngine\n*L\n95#1:1109\n95#1:1110,2\n96#1:1112,2\n105#1:1114,2\n155#1:1116\n155#1:1117,2\n156#1:1119\n156#1:1120,3\n168#1:1123\n168#1:1124,3\n169#1:1127\n169#1:1128,2\n172#1:1130\n172#1:1131,3\n178#1:1134\n178#1:1135,2\n179#1:1137\n179#1:1138,3\n192#1:1141\n192#1:1142,2\n193#1:1144\n193#1:1145,3\n207#1:1148,4\n214#1:1153\n214#1:1154,2\n215#1:1156\n215#1:1157,3\n220#1:1160\n220#1:1161,2\n221#1:1163\n221#1:1164,3\n225#1:1167\n225#1:1168,2\n226#1:1170\n226#1:1171,3\n248#1:1174\n248#1:1175,2\n249#1:1177\n249#1:1178,3\n256#1:1181\n256#1:1182,3\n257#1:1185\n257#1:1186,2\n258#1:1188\n258#1:1189,3\n270#1:1192\n270#1:1193,5\n270#1:1198,12\n273#1:1210\n273#1:1211,5\n273#1:1216,12\n583#1:1228\n583#1:1229,3\n664#1:1232\n664#1:1233,2\n668#1:1235\n668#1:1236,3\n924#1:1239\n925#1:1240,2\n924#1:1242\n941#1:1243,2\n967#1:1245\n968#1:1246,2\n967#1:1248\n986#1:1249,2\n991#1:1251\n992#1:1252,2\n991#1:1254\n1009#1:1255\n1009#1:1256,3\n1072#1:1261,2\n1093#1:1263,2\n1051#1:1259,2\n*E\n"})
public final class CliEngine {
    @NotNull
    public static final CliEngine INSTANCE = new CliEngine();
    private static final int MAX_HISTORY = 50;
    @NotNull
    private static final Path configDir;
    @NotNull
    private static final List<String> commandHistory;
    @NotNull
    private static final List<Color> savedColors;
    @NotNull
    private static final List<String> notes;
    @NotNull
    private static final List<Reminder> reminders;
    private static int nextReminderId;
    private static boolean loaded;
    private static final int MAX_SUGGESTIONS = 10;
    @NotNull
    private static final List<String> TOP_COMMANDS;
    @NotNull
    private static final List<String> CB_SUBCOMMANDS;

    private CliEngine() {
    }

    private final Path getSavedColorsFile() {
        Path path = configDir.resolve("saved_colors.txt");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        return path;
    }

    private final Path getNotesFile() {
        Path path = configDir.resolve("notes.txt");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        return path;
    }

    private final Path getProfilesDir() {
        Path path = configDir.resolve("profiles");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        return path;
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
    public final List<Reminder> getReminders() {
        return reminders;
    }

    private final void ensureLoaded() {
        if (loaded) {
            return;
        }
        loaded = true;
        this.loadSavedColors();
        this.loadNotes();
    }

    @NotNull
    public final String execute(@NotNull String input) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        this.ensureLoaded();
        String cmd = ((Object)StringsKt.trim((CharSequence)input)).toString();
        if (((CharSequence)cmd).length() > 0 && (commandHistory.isEmpty() || !Intrinsics.areEqual((Object)CollectionsKt.last(commandHistory), (Object)cmd))) {
            commandHistory.add(cmd);
            if (commandHistory.size() > 50) {
                commandHistory.remove(0);
            }
        }
        try {
            object = this.executeCliCommand(cmd);
        }
        catch (Throwable t) {
            object = "Error: " + t.getMessage();
        }
        return object;
    }

    @NotNull
    public final List<String> history() {
        return CollectionsKt.toList((Iterable)commandHistory);
    }

    @NotNull
    public final List<Suggestion> suggestions(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        return this.computeSuggestions(input);
    }

    /*
     * WARNING - void declaration
     */
    public final void checkReminders() {
        void $this$filterTo$iv$iv;
        if (reminders.isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        Iterable $this$filter$iv = reminders;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Reminder it = (Reminder)element$iv$iv;
            boolean bl = false;
            if (!(it.getTriggerTime() <= now)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List triggered = (List)destination$iv$iv;
        Iterable $this$forEach$iv = triggered;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Reminder reminder = (Reminder)element$iv;
            boolean bl = false;
            reminders.remove(reminder);
            ChatUtil.INSTANCE.info("Reminder: " + reminder.getMessage());
        }
    }

    public final int addReminder(long delayMs, @NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        int n = nextReminderId;
        nextReminderId = n + 1;
        int id = n;
        reminders.add(new Reminder(System.currentTimeMillis() + delayMs, message, id));
        List<Reminder> $this$sortBy$iv = reminders;
        boolean $i$f$sortBy = false;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, (Comparator)new Comparator(){

                public final int compare(T a, T b) {
                    Reminder it = (Reminder)a;
                    boolean bl = false;
                    Comparable comparable = Long.valueOf(it.getTriggerTime());
                    it = (Reminder)b;
                    Comparable comparable2 = comparable;
                    bl = false;
                    return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)Long.valueOf(it.getTriggerTime()));
                }
            });
        }
        return id;
    }

    public final boolean cancelReminder(int id) {
        return reminders.removeIf(arg_0 -> CliEngine.cancelReminder$lambda$1(arg_0 -> CliEngine.cancelReminder$lambda$0(id, arg_0), arg_0));
    }

    /*
     * Unable to fully structure code
     */
    private final List<Suggestion> computeSuggestions(String raw) {
        block21: {
            block20: {
                text = StringsKt.trimStart((CharSequence)raw).toString();
                if (((CharSequence)text).length() == 0) {
                    return CollectionsKt.emptyList();
                }
                firstSpace = StringsKt.indexOf$default((CharSequence)text, (char)' ', (int)0, (boolean)false, (int)6, null);
                if (firstSpace < 0) {
                    v0 = text.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"toLowerCase(...)");
                    q = v0;
                    var5_6 = CollectionsKt.distinct((Iterable)CliEngine.TOP_COMMANDS);
                    $i$f$filter = false;
                    var7_10 = $this$filter$iv;
                    destination$iv$iv = new ArrayList<E>();
                    $i$f$filterTo = false;
                    for (T element$iv$iv : $this$filterTo$iv$iv) {
                        it = (String)element$iv$iv;
                        $i$a$-filter-CliEngine$computeSuggestions$1 = false;
                        if (!StringsKt.startsWith$default((String)it, (String)q, (boolean)false, (int)2, null)) continue;
                        destination$iv$iv.add(element$iv$iv);
                    }
                    $this$filter$iv = (List)destination$iv$iv;
                    $i$f$map = false;
                    $this$filterTo$iv$iv = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        it = (String)item$iv$iv;
                        var21_32 = destination$iv$iv;
                        $i$a$-map-CliEngine$computeSuggestions$2 = false;
                        var21_32.add(new Suggestion(it, null, null, 6, null));
                    }
                    return CollectionsKt.take((Iterable)((List)destination$iv$iv), (int)10);
                }
                v1 = text.substring(0, firstSpace);
                Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"substring(...)");
                v2 = v1.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"toLowerCase(...)");
                head = v2;
                v3 = text.substring(firstSpace + 1);
                Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"substring(...)");
                rest = v3;
                var6_9 = head;
                switch (var6_9.hashCode()) {
                    case -1405258946: {
                        if (!var6_9.equals("delprofile")) {
                            ** break;
                        }
                        break block20;
                    }
                    case 116: {
                        if (var6_9.equals("t")) break;
                        ** break;
                    }
                    case 3327206: {
                        if (!var6_9.equals("load")) {
                            ** break;
                        }
                        break block20;
                    }
                    case -868304044: {
                        if (var6_9.equals("toggle")) break;
                        ** break;
                    }
                    case 3167: {
                        if (!var6_9.equals("cb")) ** break;
                        v4 = this.cbSuggestions(rest);
                        break block21;
                    }
                }
                v5 = rest.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v5, (String)"toLowerCase(...)");
                q = v5;
                destination$iv$iv = this.booleanSettings();
                $i$f$map = false;
                var10_21 = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var15_36 = (BooleanSetting)item$iv$iv;
                    var21_33 = destination$iv$iv;
                    $i$a$-map-CliEngine$computeSuggestions$3 = false;
                    var21_33.add(it.getName());
                }
                $this$map$iv = (List)destination$iv$iv;
                $i$f$filter = false;
                $this$mapTo$iv$iv = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (String)element$iv$iv;
                    $i$a$-filter-CliEngine$computeSuggestions$4 = false;
                    v6 = it.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v6, (String)"toLowerCase(...)");
                    if (!StringsKt.contains$default((CharSequence)v6, (CharSequence)q, (boolean)false, (int)2, null)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = CollectionsKt.take((Iterable)CollectionsKt.distinct((Iterable)((List)destination$iv$iv)), (int)10);
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var21_33 = destination$iv$iv;
                    $i$a$-map-CliEngine$computeSuggestions$5 = false;
                    var21_33.add(new Suggestion(head + " " + it, null, "toggle", 2, null));
                }
                v4 = (List)destination$iv$iv;
                break block21;
            }
            var7_12 = this;
            try {
                $this$computeSuggestions_u24lambda_u245 = var7_12;
                $i$a$-runCatching-CliEngine$computeSuggestions$6 = false;
                v7 = rest.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v7, (String)"toLowerCase(...)");
                q = v7;
                destination$iv$iv = $this$computeSuggestions_u24lambda_u245.listProfiles();
                $i$f$filter = false;
                $i$a$-map-CliEngine$computeSuggestions$2 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (String)element$iv$iv;
                    $i$a$-filter-CliEngine$computeSuggestions$6$1 = false;
                    v8 = it.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v8, (String)"toLowerCase(...)");
                    if (!StringsKt.startsWith$default((String)v8, (String)q, (boolean)false, (int)2, null)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var20_43 = destination$iv$iv;
                    $i$a$-map-CliEngine$computeSuggestions$6$2 = false;
                    var20_43.add(new Suggestion(head + " " + it, null, Intrinsics.areEqual((Object)head, (Object)"load") != false ? "Load profile" : "Delete profile", 2, null));
                }
                var8_15 = Result.constructor-impl((Object)CollectionsKt.take((Iterable)((List)destination$iv$iv), (int)10));
            }
            catch (Throwable var9_19) {
                var8_15 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)var9_19));
            }
            var7_12 = var8_15;
            var8_15 = CollectionsKt.emptyList();
            v4 = (List)(Result.isFailure-impl((Object)var7_12) != false ? var8_15 : var7_12);
            break block21;
lbl158:
            // 6 sources

            v4 = CollectionsKt.emptyList();
        }
        return v4;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private final List<Suggestion> cbSuggestions(String rest) {
        nextSpace = StringsKt.indexOf$default((CharSequence)rest, (char)' ', (int)0, (boolean)false, (int)6, null);
        if (nextSpace < 0) {
            v0 = rest.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"toLowerCase(...)");
            q = v0;
            var4_5 = CollectionsKt.distinct((Iterable)CliEngine.CB_SUBCOMMANDS);
            $i$f$filter = false;
            var6_9 = $this$filter$iv;
            destination$iv$iv = new ArrayList<E>();
            $i$f$filterTo = false;
            for (T element$iv$iv : $this$filterTo$iv$iv) {
                it = (String)element$iv$iv;
                $i$a$-filter-CliEngine$cbSuggestions$1 = false;
                if (!StringsKt.startsWith$default((String)it, (String)q, (boolean)false, (int)2, null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            $i$f$map = false;
            $this$filterTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            $i$f$mapTo = false;
            for (T item$iv$iv : $this$mapTo$iv$iv) {
                it = (String)item$iv$iv;
                var19_39 = destination$iv$iv;
                $i$a$-map-CliEngine$cbSuggestions$2 = false;
                var19_39.add(new Suggestion("cb " + it, null, null, 6, null));
            }
            return CollectionsKt.take((Iterable)((List)destination$iv$iv), (int)10);
        }
        v1 = rest.substring(0, nextSpace);
        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"substring(...)");
        v2 = v1.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"toLowerCase(...)");
        sub = v2;
        v3 = rest.substring(nextSpace + 1);
        Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"substring(...)");
        args = v3;
        v4 = StringsKt.substringAfterLast$default((String)args, (char)' ', null, (int)2, null).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)v4, (String)"toLowerCase(...)");
        lastToken = v4;
        argsBeforeLast = StringsKt.substringBeforeLast((String)args, (char)' ', (String)"");
        prefix = ((CharSequence)argsBeforeLast).length() == 0 != false ? "cb " + sub : "cb " + sub + " " + argsBeforeLast;
        var8_14 = sub;
        tmp = -1;
        switch (var8_14.hashCode()) {
            case -1785238953: {
                if (var8_14.equals("favorites")) {
                    tmp = 1;
                }
                break;
            }
            case 98: {
                if (var8_14.equals("b")) {
                    tmp = 2;
                }
                break;
            }
            case 109399969: {
                if (var8_14.equals("shape")) {
                    tmp = 3;
                }
                break;
            }
            case 102: {
                if (var8_14.equals("f")) {
                    tmp = 4;
                }
                break;
            }
            case 1094496948: {
                if (var8_14.equals("replace")) {
                    tmp = 5;
                }
                break;
            }
            case 99339: {
                if (var8_14.equals("del")) {
                    tmp = 6;
                }
                break;
            }
            case 3143043: {
                if (var8_14.equals("fill")) {
                    tmp = 4;
                }
                break;
            }
            case -1335458389: {
                if (var8_14.equals("delete")) {
                    tmp = 6;
                }
                break;
            }
            case 3506395: {
                if (var8_14.equals("room")) {
                    tmp = 7;
                }
                break;
            }
            case 114: {
                if (var8_14.equals("r")) {
                    tmp = 5;
                }
                break;
            }
            case 3327206: {
                if (var8_14.equals("load")) {
                    tmp = 6;
                }
                break;
            }
            case 93832333: {
                if (var8_14.equals("block")) {
                    tmp = 2;
                }
                break;
            }
            case 101147: {
                if (var8_14.equals("fav")) {
                    tmp = 1;
                }
                break;
            }
            case 112797: {
                if (var8_14.equals("rep")) {
                    tmp = 5;
                }
                break;
            }
        }
        switch (tmp) {
            case 2: {
                v5 = this.blockSuggestions(lastToken, prefix, "Brush");
                break;
            }
            case 4: {
                v5 = this.blockSuggestions(lastToken, prefix, "Fill");
                break;
            }
            case 5: {
                item$iv$iv = StringsKt.trim((CharSequence)args).toString();
                it = new Regex("\\s+");
                $i$a$-map-CliEngine$cbSuggestions$2 = 0;
                item$iv$iv = it.split((CharSequence)item$iv$iv, $i$a$-map-CliEngine$cbSuggestions$2);
                $i$f$count = false;
                if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                    v6 = 0;
                } else {
                    count$iv = 0;
                    for (T element$iv : $this$count$iv) {
                        it = (String)element$iv;
                        $i$a$-count-CliEngine$cbSuggestions$tokenCount$1 = false;
                        v7 = ((CharSequence)it).length() > 0;
                        if (!v7 || ++count$iv >= 0) continue;
                        CollectionsKt.throwCountOverflow();
                    }
                    v6 = count$iv;
                }
                tokenCount = v6;
                hint = tokenCount <= 1 ? "From" : "To";
                v5 = this.blockSuggestions(lastToken, prefix, hint);
                break;
            }
            case 1: {
                v5 = this.favSuggestions(args, prefix);
                break;
            }
            case 6: {
                hint = this;
                try {
                    $this$cbSuggestions_u24lambda_u243 = hint;
                    $i$a$-runCatching-CliEngine$cbSuggestions$configs$1 = false;
                    $this$cbSuggestions_u24lambda_u243 = Result.constructor-impl(CosmeticBlocks.INSTANCE.listConfigs());
                }
                catch (Throwable $i$a$-runCatching-CliEngine$cbSuggestions$configs$1) {
                    $this$cbSuggestions_u24lambda_u243 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-CliEngine$cbSuggestions$configs$1));
                }
                hint = $this$cbSuggestions_u24lambda_u243;
                $this$cbSuggestions_u24lambda_u243 = CollectionsKt.emptyList();
                configs = (List)(Result.isFailure-impl((Object)hint) != false ? $this$cbSuggestions_u24lambda_u243 : hint);
                $this$filter$iv = configs;
                $i$f$filter = false;
                $i$a$-runCatching-CliEngine$cbSuggestions$configs$1 = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (String)element$iv$iv;
                    $i$a$-filter-CliEngine$cbSuggestions$3 = false;
                    if (((CharSequence)lastToken).length() == 0) ** GOTO lbl-1000
                    v8 = it.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v8, (String)"toLowerCase(...)");
                    if (StringsKt.contains$default((CharSequence)v8, (CharSequence)lastToken, (boolean)false, (int)2, null)) lbl-1000:
                    // 2 sources

                    {
                        v9 = true;
                    } else {
                        v9 = false;
                    }
                    if (!v9) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var19_40 = destination$iv$iv;
                    $i$a$-map-CliEngine$cbSuggestions$4 = false;
                    var19_40.add(new Suggestion(prefix + " " + it, null, Intrinsics.areEqual((Object)sub, (Object)"load") != false ? "Load" : "Delete", 2, null));
                }
                v5 = CollectionsKt.take((Iterable)((List)destination$iv$iv), (int)10);
                break;
            }
            case 3: {
                configs /* !! */  = new String[]{"none", "fence", "wall"};
                configs /* !! */  = CollectionsKt.listOf((Object[])configs /* !! */ );
                $i$f$filter = false;
                $i$f$map = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (String)element$iv$iv;
                    $i$a$-filter-CliEngine$cbSuggestions$5 = false;
                    if (!StringsKt.startsWith$default((String)it, (String)lastToken, (boolean)false, (int)2, null)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var19_41 = destination$iv$iv;
                    $i$a$-map-CliEngine$cbSuggestions$6 = false;
                    var19_41.add(new Suggestion(prefix + " " + it, null, "Shape", 2, null));
                }
                v5 = (List)destination$iv$iv;
                break;
            }
            case 7: {
                $this$map$iv /* !! */  = new String[]{"info", "clear"};
                $this$map$iv /* !! */  = CollectionsKt.listOf((Object[])$this$map$iv /* !! */ );
                $i$f$filter = false;
                $this$mapTo$iv$iv = $this$filter$iv;
                destination$iv$iv = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv /* !! */ ) {
                    it = (String)element$iv$iv;
                    $i$a$-filter-CliEngine$cbSuggestions$7 = false;
                    if (!StringsKt.startsWith$default((String)it, (String)lastToken, (boolean)false, (int)2, null)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv /* !! */  = $this$map$iv /* !! */ ;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv /* !! */ , (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var19_42 = destination$iv$iv;
                    $i$a$-map-CliEngine$cbSuggestions$8 = false;
                    var19_42.add(new Suggestion(prefix + " " + it, null, "Room", 2, null));
                }
                v5 = (List)destination$iv$iv;
                break;
            }
            default: {
                v5 = CollectionsKt.emptyList();
            }
        }
        return v5;
    }

    private final List<Suggestion> blockSuggestions(String token, String prefix, String hint) {
        String q = StringsKt.replace$default((String)StringsKt.removePrefix((String)token, (CharSequence)"minecraft:"), (char)' ', (char)'_', (boolean)false, (int)4, null);
        Set set = BuiltInRegistries.BLOCK.keySet();
        Intrinsics.checkNotNullExpressionValue((Object)set, (String)"keySet(...)");
        return SequencesKt.toList((Sequence)SequencesKt.map((Sequence)SequencesKt.take((Sequence)SequencesKt.filter((Sequence)SequencesKt.map((Sequence)CollectionsKt.asSequence((Iterable)set), CliEngine::blockSuggestions$lambda$0), arg_0 -> CliEngine.blockSuggestions$lambda$1(q, arg_0)), (int)10), arg_0 -> CliEngine.blockSuggestions$lambda$2(prefix, hint, arg_0)));
    }

    /*
     * WARNING - void declaration
     */
    private final List<Suggestion> favSuggestions(String args, String prefix) {
        String favArg;
        char[] cArray = new char[]{' '};
        List parts = StringsKt.split$default((CharSequence)args, (char[])cArray, (boolean)false, (int)2, (int)2, null);
        String string = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toLowerCase(...)");
        String favSub = string;
        String string2 = (String)CollectionsKt.getOrNull((List)parts, (int)1);
        String string3 = string2 != null ? ((Object)StringsKt.trim((CharSequence)string2)).toString() : null;
        if (string3 == null) {
            string3 = favArg = "";
        }
        if (parts.size() < 2) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            String it;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv;
            Object object = new String[]{"add", "remove", "rm", "list", "gui"};
            object = CollectionsKt.listOf((Object[])object);
            boolean $i$f$filter = false;
            void var8_10 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (String)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.startsWith$default((String)it, (String)favSub, (boolean)false, (int)2, null)) continue;
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
                collection.add(new Suggestion(prefix + " " + it, null, "Favorites", 2, null));
            }
            return (List)destination$iv$iv;
        }
        return switch (favSub) {
            case "add" -> {
                String v3 = StringsKt.substringAfterLast$default((String)favArg, (char)' ', null, (int)2, null).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"toLowerCase(...)");
                yield this.blockSuggestions(v3, prefix + " add", "Add fav");
            }
            case "rm", "remove" -> {
                Iterable $this$filterTo$iv$iv;
                Iterable $this$filter$iv;
                String it;
                Collection var18_27;
                void $this$mapTo$iv$iv;
                Object $this$favSuggestions_u24lambda_u242;
                Object $this$mapTo$iv$iv = this;
                try {
                    $this$favSuggestions_u24lambda_u242 = $this$mapTo$iv$iv;
                    boolean $i$a$-runCatching-CliEngine$favSuggestions$favs$1 = false;
                    $this$favSuggestions_u24lambda_u242 = Result.constructor-impl(CosmeticBlocks.INSTANCE.getFavorites());
                }
                catch (Throwable $i$a$-runCatching-CliEngine$favSuggestions$favs$1) {
                    $this$favSuggestions_u24lambda_u242 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-CliEngine$favSuggestions$favs$1));
                }
                $this$mapTo$iv$iv = $this$favSuggestions_u24lambda_u242;
                $this$favSuggestions_u24lambda_u242 = CollectionsKt.emptyList();
                List favs = (List)(Result.isFailure-impl((Object)$this$mapTo$iv$iv) ? $this$favSuggestions_u24lambda_u242 : $this$mapTo$iv$iv);
                String v5 = favArg.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v5, (String)"toLowerCase(...)");
                String q = v5;
                Iterable $this$map$iv = favs;
                boolean $i$f$map = false;
                Iterable var11_19 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    String var16_29 = (String)item$iv$iv;
                    var18_27 = destination$iv$iv;
                    boolean $i$a$-map-CliEngine$favSuggestions$3 = false;
                    var18_27.add(StringsKt.removePrefix((String)it, (CharSequence)"minecraft:"));
                }
                $this$map$iv = (List)destination$iv$iv;
                boolean $i$f$filter = false;
                $this$mapTo$iv$iv = $this$filter$iv;
                destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv) {
                    it = (String)element$iv$iv;
                    boolean $i$a$-filter-CliEngine$favSuggestions$4 = false;
                    if (!(((CharSequence)q).length() == 0 || StringsKt.contains$default((CharSequence)it, (CharSequence)q, (boolean)false, (int)2, null))) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                $i$f$map = false;
                $this$filterTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    it = (String)item$iv$iv;
                    var18_27 = destination$iv$iv;
                    boolean $i$a$-map-CliEngine$favSuggestions$5 = false;
                    var18_27.add(new Suggestion(prefix + " " + favSub + " " + it, it, "Remove fav"));
                }
                yield CollectionsKt.take((Iterable)((List)destination$iv$iv), (int)10);
            }
            default -> CollectionsKt.emptyList();
        };
    }

    /*
     * WARNING - void declaration
     */
    private final List<BooleanSetting> booleanSettings() {
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = Tabs.INSTANCE.getAll();
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            SettingsTab it = (SettingsTab)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getSettings();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        Iterable $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance = false;
        $this$flatMapTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof BooleanSetting)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final List<KeybindSetting> keybindSettings() {
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = Tabs.INSTANCE.getAll();
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            SettingsTab it = (SettingsTab)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getSettings();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        Iterable $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance = false;
        $this$flatMapTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof KeybindSetting)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    private final Long parseTimeString(String input) {
        String string = input.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toLowerCase(...)");
        String str = ((Object)StringsKt.trim((CharSequence)string)).toString();
        Long l = StringsKt.toLongOrNull((String)str);
        if (l != null) {
            long it = ((Number)l).longValue();
            boolean bl = false;
            return it * (long)1000;
        }
        long totalMs = 0L;
        Object currentNum = "";
        int n = str.length();
        for (int i = 0; i < n; ++i) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                currentNum = (String)currentNum + c;
                continue;
            }
            if (c == 'h') {
                Long l2 = StringsKt.toLongOrNull((String)currentNum);
                if (l2 == null) {
                    return null;
                }
                totalMs += l2 * (long)3600 * (long)1000;
                currentNum = "";
                continue;
            }
            if (c == 'm') {
                Long l3 = StringsKt.toLongOrNull((String)currentNum);
                if (l3 == null) {
                    return null;
                }
                totalMs += l3 * (long)60 * (long)1000;
                currentNum = "";
                continue;
            }
            if (c == 's') {
                Long l4 = StringsKt.toLongOrNull((String)currentNum);
                if (l4 == null) {
                    return null;
                }
                totalMs += l4 * (long)1000;
                currentNum = "";
                continue;
            }
            return null;
        }
        if (((CharSequence)currentNum).length() > 0) {
            Long l5 = StringsKt.toLongOrNull((String)currentNum);
            if (l5 == null) {
                return null;
            }
            totalMs += l5 * (long)1000;
        }
        return totalMs > 0L ? Long.valueOf(totalMs) : null;
    }

    private final String formatDuration(long ms) {
        long totalSeconds = ms / (long)1000;
        long hours = totalSeconds / (long)3600;
        long minutes = totalSeconds % (long)3600 / (long)60;
        long seconds = totalSeconds % (long)60;
        return hours > 0L ? hours + "h " + minutes + "m" : (minutes > 0L ? minutes + "m " + seconds + "s" : seconds + "s");
    }

    private final Double evaluateCalcExpression(String expr) {
        Double d;
        try {
            d = new CalcParser(expr).parse();
        }
        catch (Exception exception) {
            d = null;
        }
        return d;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final String executeCliCommand(String input) {
        var3_2 = new String[]{" "};
        parts = StringsKt.split$default((CharSequence)input, (String[])var3_2, (boolean)false, (int)2, (int)2, null);
        v0 = ((String)parts.get(0)).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"toLowerCase(...)");
        cmd = v0;
        v1 = (String)CollectionsKt.getOrNull((List)parts, (int)1);
        if (v1 == null || (v1 = StringsKt.trim((CharSequence)v1).toString()) == null) {
            v1 = "";
        }
        arg = v1;
        var5_5 = cmd;
        tmp = -1;
        switch (var5_5.hashCode()) {
            case 0: {
                if (var5_5.equals("")) {
                    tmp = 1;
                }
                break;
            }
            case 1103187521: {
                if (var5_5.equals("reminders")) {
                    tmp = 2;
                }
                break;
            }
            case 93742038: {
                if (var5_5.equals("binds")) {
                    tmp = 3;
                }
                break;
            }
            case 3387378: {
                if (var5_5.equals("note")) {
                    tmp = 4;
                }
                break;
            }
            case -1405258946: {
                if (var5_5.equals("delprofile")) {
                    tmp = 5;
                }
                break;
            }
            case 105008833: {
                if (var5_5.equals("notes")) {
                    tmp = 6;
                }
                break;
            }
            case -890346579: {
                if (var5_5.equals("rmreminder")) {
                    tmp = 7;
                }
                break;
            }
            case -1184795739: {
                if (var5_5.equals("import")) {
                    tmp = 8;
                }
                break;
            }
            case 3441010: {
                if (var5_5.equals("ping")) {
                    tmp = 9;
                }
                break;
            }
            case 1784425883: {
                if (var5_5.equals("noteclear")) {
                    tmp = 10;
                }
                break;
            }
            case 3522941: {
                if (var5_5.equals("save")) {
                    tmp = 11;
                }
                break;
            }
            case 3506301: {
                if (var5_5.equals("roll")) {
                    tmp = 12;
                }
                break;
            }
            case 3083175: {
                if (var5_5.equals("dice")) {
                    tmp = 12;
                }
                break;
            }
            case 109250890: {
                if (var5_5.equals("scale")) {
                    tmp = 13;
                }
                break;
            }
            case -934616827: {
                if (var5_5.equals("remind")) {
                    tmp = 14;
                }
                break;
            }
            case 110364485: {
                if (var5_5.equals("timer")) {
                    tmp = 14;
                }
                break;
            }
            case 3023933: {
                if (var5_5.equals("bind")) {
                    tmp = 15;
                }
                break;
            }
            case 3327206: {
                if (var5_5.equals("load")) {
                    tmp = 16;
                }
                break;
            }
            case 503572695: {
                if (var5_5.equals("keybinds")) {
                    tmp = 3;
                }
                break;
            }
            case -1289153612: {
                if (var5_5.equals("export")) {
                    tmp = 17;
                }
                break;
            }
            case 3145837: {
                if (var5_5.equals("flip")) {
                    tmp = 18;
                }
                break;
            }
            case 3167: {
                if (var5_5.equals("cb")) {
                    tmp = 19;
                }
                break;
            }
            case 99: {
                if (var5_5.equals("c")) {
                    tmp = 20;
                }
                break;
            }
            case 100: {
                if (var5_5.equals("d")) {
                    tmp = 12;
                }
                break;
            }
            case -518602638: {
                if (var5_5.equals("reminder")) {
                    tmp = 14;
                }
                break;
            }
            case -448690650: {
                if (var5_5.equals("noteexport")) {
                    tmp = 21;
                }
                break;
            }
            case -1002263574: {
                if (var5_5.equals("profiles")) {
                    tmp = 22;
                }
                break;
            }
            case 101609: {
                if (var5_5.equals("fps")) {
                    tmp = 23;
                }
                break;
            }
            case -868304044: {
                if (var5_5.equals("toggle")) {
                    tmp = 24;
                }
                break;
            }
            case 1983864468: {
                if (var5_5.equals("clearreminders")) {
                    tmp = 25;
                }
                break;
            }
            case -715981186: {
                if (var5_5.equals("coinflip")) {
                    tmp = 18;
                }
                break;
            }
            case 1780471020: {
                if (var5_5.equals("cancelreminder")) {
                    tmp = 7;
                }
                break;
            }
            case 116: {
                if (var5_5.equals("t")) {
                    tmp = 24;
                }
                break;
            }
            case 119: {
                if (var5_5.equals("w")) {
                    tmp = 26;
                }
                break;
            }
            case -719093612: {
                if (var5_5.equals("clearnotes")) {
                    tmp = 10;
                }
                break;
            }
            case 3045973: {
                if (var5_5.equals("calc")) {
                    tmp = 20;
                }
                break;
            }
            case 61: {
                if (var5_5.equals("=")) {
                    tmp = 20;
                }
                break;
            }
            case 3059345: {
                if (var5_5.equals("coin")) {
                    tmp = 18;
                }
                break;
            }
        }
        switch (tmp) {
            case 1: {
                v2 = "";
                break;
            }
            case 26: {
                Config.INSTANCE.save();
                v2 = "Settings saved";
                break;
            }
            case 17: {
                success = this.exportToClipboard();
                if (success) {
                    v2 = "Settings exported to clipboard";
                    break;
                }
                v2 = "Export failed";
                break;
            }
            case 8: {
                success = this.importFromClipboard();
                if (success) {
                    v2 = "Settings imported from clipboard";
                    break;
                }
                v2 = "Import failed";
                break;
            }
            case 11: {
                if (((CharSequence)arg).length() > 0) {
                    success = this.saveProfile(arg);
                    if (success) {
                        v2 = "Profile '" + arg + "' saved";
                        break;
                    }
                    v2 = "Failed to save profile";
                    break;
                }
                v2 = "Usage: save <profile name>";
                break;
            }
            case 16: {
                if (((CharSequence)arg).length() > 0) {
                    success = this.loadProfile(arg);
                    if (success) {
                        v2 = "Profile '" + arg + "' loaded";
                        break;
                    }
                    v2 = "Profile not found";
                    break;
                }
                v2 = "Usage: load <profile name>";
                break;
            }
            case 22: {
                profiles = this.listProfiles();
                if (profiles.isEmpty()) {
                    v2 = "No profiles saved";
                    break;
                }
                v2 = "Profiles: " + CollectionsKt.joinToString$default((Iterable)profiles, (CharSequence)", ", null, null, (int)0, null, null, (int)62, null);
                break;
            }
            case 5: {
                if (((CharSequence)arg).length() > 0) {
                    success = this.deleteProfile(arg);
                    if (success) {
                        v2 = "Profile '" + arg + "' deleted";
                        break;
                    }
                    v2 = "Profile not found";
                    break;
                }
                v2 = "Usage: delprofile <profile name>";
                break;
            }
            case 13: {
                v3 = Minecraft.getInstance();
                Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"getInstance(...)");
                mc = v3;
                v4 = mc.getWindow();
                screenW = v4 != null ? v4.getGuiScaledWidth() : 800;
                v5 = mc.getWindow();
                screenH = v5 != null ? v5.getGuiScaledHeight() : 600;
                baseWindowWidth = 450.0f;
                baseWindowHeight = 350.0f;
                maxScaleW = (float)(screenW - 40) / baseWindowWidth;
                maxScaleH = (float)(screenH - 40) / baseWindowHeight;
                maxScale = Math.min(maxScaleW, Math.min(maxScaleH, 2.0f));
                if (((CharSequence)arg).length() > 0) {
                    scale = StringsKt.toFloatOrNull((String)arg);
                    if (scale != null) {
                        var15_89 = scale.floatValue();
                        v6 = 0.5f <= var15_89 ? var15_89 <= maxScale : false;
                        if (v6) {
                            var15_90 = "%.1f";
                            var16_99 = new Object[]{scale};
                            v7 = String.format(var15_90, Arrays.copyOf(var16_99, var16_99.length));
                            Intrinsics.checkNotNullExpressionValue((Object)v7, (String)"format(...)");
                            v2 = "Scale set to " + v7 + " (apply in GUI)";
                            break;
                        }
                    }
                    if (scale != null && scale.floatValue() > maxScale) {
                        var15_91 = "%.1f";
                        var16_100 = new Object[]{Float.valueOf(maxScale)};
                        v8 = String.format(var15_91, Arrays.copyOf(var16_100, var16_100.length));
                        Intrinsics.checkNotNullExpressionValue((Object)v8, (String)"format(...)");
                        v2 = "Max scale for screen: " + v8;
                        break;
                    }
                    var15_92 = "%.1f";
                    var16_101 = new Object[]{Float.valueOf(maxScale)};
                    v9 = String.format(var15_92, Arrays.copyOf(var16_101, var16_101.length));
                    Intrinsics.checkNotNullExpressionValue((Object)v9, (String)"format(...)");
                    v2 = "Scale must be between 0.5 and " + v9;
                    break;
                }
                scale = "%.1f";
                var15_93 = new Object[]{Float.valueOf(maxScale)};
                v10 = String.format(scale, Arrays.copyOf(var15_93, var15_93.length));
                Intrinsics.checkNotNullExpressionValue((Object)v10, (String)"format(...)");
                v2 = "Scale range: 0.5-" + v10;
                break;
            }
            case 23: {
                v11 = Minecraft.getInstance();
                Intrinsics.checkNotNullExpressionValue((Object)v11, (String)"getInstance(...)");
                mc = v11;
                v2 = "FPS: " + mc.getFps();
                break;
            }
            case 9: {
                v12 = Minecraft.getInstance();
                Intrinsics.checkNotNullExpressionValue((Object)v12, (String)"getInstance(...)");
                mc = v12;
                player = mc.player;
                v13 = player != null ? ((v14 = mc.getConnection()) != null && (v14 = v14.getPlayerInfo(player.getUUID())) != null ? v14.getLatency() : -1) : (ping = -1);
                if (ping >= 0) {
                    v2 = "Ping: " + ping + "ms";
                    break;
                }
                v2 = "Ping: N/A";
                break;
            }
            case 20: {
                if (((CharSequence)arg).length() > 0) {
                    result = this.evaluateCalcExpression(arg);
                    if (result != null) {
                        if (Intrinsics.areEqual((Double)result, (double)((long)result.doubleValue())) && Math.abs(result) < 9.223372036854776E18) {
                            v15 = String.valueOf((long)result.doubleValue());
                        } else {
                            ping = "%.10f";
                            baseWindowWidth = new Object[]{result};
                            v16 = String.format((String)ping, Arrays.copyOf(baseWindowWidth, baseWindowWidth.length));
                            Intrinsics.checkNotNullExpressionValue((Object)v16, (String)"format(...)");
                            ping = new char[1];
                            ping[0] = 48;
                            v17 = StringsKt.trimEnd((String)v16, (char[])ping);
                            ping = new char[1];
                            ping[0] = 46;
                            v15 = StringsKt.trimEnd((String)v17, (char[])ping);
                        }
                        formatted = v15;
                        GLFW.glfwSetClipboardString((long)GLFW.glfwGetCurrentContext(), (CharSequence)formatted);
                        v2 = arg + " = " + formatted + " (copied)";
                        break;
                    }
                    v2 = "Invalid expression";
                    break;
                }
                v2 = "Usage: calc <expr>";
                break;
            }
            case 18: {
                result = Math.random() < 0.5 ? "Heads" : "Tails";
                v2 = "Coin flip: " + result;
                break;
            }
            case 12: {
                v18 = StringsKt.toIntOrNull((String)arg);
                v19 = max = v18 != null ? v18 : 6;
                if (max < 1) {
                    v2 = "Max must be at least 1";
                    break;
                }
                result = RangesKt.random((IntRange)new IntRange(1, max), (Random)((Random)Random.Default));
                v2 = "Roll (1-" + max + "): " + result;
                break;
            }
            case 4: {
                if (((CharSequence)arg).length() > 0) {
                    timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    CliEngine.notes.add("[" + timestamp + "] " + arg);
                    this.saveNotes();
                    v2 = "Note saved (" + CliEngine.notes.size() + " total)";
                    break;
                }
                v2 = "Usage: note <text>";
                break;
            }
            case 6: {
                if (CliEngine.notes.isEmpty()) {
                    v2 = "No notes saved";
                    break;
                }
                recent = CollectionsKt.reversed((Iterable)CollectionsKt.takeLast(CliEngine.notes, (int)3));
                v2 = CollectionsKt.joinToString$default((Iterable)recent, (CharSequence)" | ", null, null, (int)0, null, (Function1)(Function1)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, executeCliCommand$lambda$0(java.lang.String ), (Ljava/lang/String;)Ljava/lang/CharSequence;)(), (int)30, null);
                break;
            }
            case 10: {
                count = CliEngine.notes.size();
                CliEngine.notes.clear();
                this.saveNotes();
                v2 = "Cleared " + count + " notes";
                break;
            }
            case 21: {
                if (CliEngine.notes.isEmpty()) {
                    v2 = "No notes to export";
                    break;
                }
                export = CollectionsKt.joinToString$default((Iterable)CliEngine.notes, (CharSequence)"\n", null, null, (int)0, null, null, (int)62, null);
                GLFW.glfwSetClipboardString((long)GLFW.glfwGetCurrentContext(), (CharSequence)export);
                v2 = "Exported " + CliEngine.notes.size() + " notes to clipboard";
                break;
            }
            case 14: {
                if (((CharSequence)arg).length() > 0) {
                    result = new String[]{" "};
                    timeParts = StringsKt.split$default((CharSequence)arg, (String[])result, (boolean)false, (int)2, (int)2, null);
                    timeStr = (String)timeParts.get(0);
                    v20 = (String)CollectionsKt.getOrNull((List)timeParts, (int)1);
                    if (v20 == null) {
                        v20 = message = "Reminder!";
                    }
                    if ((delayMs = this.parseTimeString(timeStr)) != null && delayMs > 0L) {
                        id = this.addReminder(delayMs, message);
                        timeFormatted = this.formatDuration(delayMs);
                        v2 = "Reminder #" + id + " set for " + timeFormatted;
                        break;
                    }
                    v2 = "Invalid time. Use: 30s, 5m, 1h, 1h30m";
                    break;
                }
                v2 = "Usage: remind <time> [message]";
                break;
            }
            case 2: {
                if (CliEngine.reminders.isEmpty()) {
                    v2 = "No active reminders";
                    break;
                }
                now = System.currentTimeMillis();
                $this$map$iv = CollectionsKt.take((Iterable)CliEngine.reminders, (int)3);
                $i$f$map = false;
                timeFormatted = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    var16_102 = (Reminder)item$iv$iv;
                    var20_109 = destination$iv$iv;
                    $i$a$-map-CliEngine$executeCliCommand$list$1 = false;
                    remaining = (r.getTriggerTime() - now) / (long)1000;
                    var20_109.add("#" + r.getId() + ": " + CliEngine.INSTANCE.formatDuration(remaining * (long)1000));
                }
                list = (List)destination$iv$iv;
                v2 = "Reminders: " + CollectionsKt.joinToString$default((Iterable)list, (CharSequence)", ", null, null, (int)0, null, null, (int)62, null);
                break;
            }
            case 7: {
                id = StringsKt.toIntOrNull((String)arg);
                if (id != null) {
                    success = this.cancelReminder(id);
                    if (success) {
                        v2 = "Reminder #" + id + " cancelled";
                        break;
                    }
                    v2 = "Reminder #" + id + " not found";
                    break;
                }
                v2 = "Usage: cancelreminder <id>";
                break;
            }
            case 25: {
                count = CliEngine.reminders.size();
                CliEngine.reminders.clear();
                v2 = "Cleared " + count + " reminders";
                break;
            }
            case 24: {
                if (!(((CharSequence)arg).length() > 0)) ** GOTO lbl472
                list = this.booleanSettings();
                for (T $i$f$map : list) {
                    it = (BooleanSetting)$i$f$map;
                    $i$a$-find-CliEngine$executeCliCommand$setting$1 = false;
                    v21 = it.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v21, (String)"toLowerCase(...)");
                    v22 = arg.toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v22, (String)"toLowerCase(...)");
                    if (!Intrinsics.areEqual((Object)v21, (Object)v22)) continue;
                    v23 = $i$f$map;
                    ** GOTO lbl462
                }
                v23 = null;
lbl462:
                // 2 sources

                setting = v23;
                if (setting != null) {
                    newValue = (Boolean)setting.getValue() == false;
                    setting.setValue(newValue);
                    status = newValue != false ? "\u00a7aON" : "\u00a7cOFF";
                    Config.INSTANCE.save();
                    v2 = setting.getName() + ": " + status;
                    break;
                }
                v2 = "Feature not found: " + arg;
                break;
lbl472:
                // 1 sources

                v2 = "Usage: t <feature name>";
                break;
            }
            case 15: {
                newValue = new String[]{" "};
                bindParts = StringsKt.split$default((CharSequence)arg, (String[])newValue, (boolean)false, (int)2, (int)2, null);
                v24 = ((String)bindParts.get(0)).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v24, (String)"toLowerCase(...)");
                settingName = v24;
                v25 = (String)CollectionsKt.getOrNull((List)bindParts, (int)1);
                if (v25 == null) ** GOTO lbl486
                v26 = v25.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v26, (String)"toUpperCase(...)");
                v25 = v26;
                if (v26 != null) ** GOTO lbl487
lbl486:
                // 2 sources

                v25 = keyName = "";
lbl487:
                // 2 sources

                if (((CharSequence)settingName).length() == 0) {
                    v2 = "Usage: bind <setting> <key> (e.g., bind 'edit mode' g)";
                    break;
                }
                if (!(((CharSequence)keyName).length() == 0)) ** GOTO lbl512
                it = this.keybindSettings();
                for (T $i$f$mapTo : it) {
                    it = (KeybindSetting)$i$f$mapTo;
                    $i$a$-find-CliEngine$executeCliCommand$setting$2 = false;
                    v27 = it.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v27, (String)"toLowerCase(...)");
                    if (!StringsKt.contains$default((CharSequence)v27, (CharSequence)settingName, (boolean)false, (int)2, null)) continue;
                    v28 = $i$f$mapTo;
                    ** GOTO lbl501
                }
                v28 = null;
lbl501:
                // 2 sources

                setting = v28;
                if (setting != null) {
                    currentKey = ((Number)setting.getValue()).intValue();
                    if (currentKey != -1 && currentKey > 0) {
                        keyChar = (32 <= currentKey ? currentKey < 127 : false) != false ? String.valueOf((char)currentKey) : "KEY_" + currentKey;
                        v2 = "Current bind for '" + setting.getName() + "': " + (String)keyChar;
                        break;
                    }
                    v2 = "'" + setting.getName() + "' has no keybind";
                    break;
                }
                v2 = "Setting not found: " + settingName;
                break;
lbl512:
                // 1 sources

                if (keyName.length() != 1) ** GOTO lbl515
                v29 = Character.toUpperCase(keyName.charAt(0));
                ** GOTO lbl528
lbl515:
                // 1 sources

                if (!StringsKt.startsWith$default((String)keyName, (String)"F", (boolean)false, (int)2, null) || keyName.length() <= 1) ** GOTO lbl527
                v30 = keyName.substring(1);
                Intrinsics.checkNotNullExpressionValue((Object)v30, (String)"substring(...)");
                fNum = StringsKt.toIntOrNull((String)v30);
                if (fNum == null) ** GOTO lbl-1000
                keyChar = fNum;
                v31 = 1 <= keyChar ? keyChar < 26 : false;
                if (v31) {
                    v29 = 290 + fNum - 1;
                } else lbl-1000:
                // 2 sources

                {
                    v29 = null;
                }
                ** GOTO lbl528
lbl527:
                // 1 sources

                v29 = keyCode = Intrinsics.areEqual((Object)keyName, (Object)"NONE") != false || Intrinsics.areEqual((Object)keyName, (Object)"UNBIND") != false ? Integer.valueOf(0) : null;
lbl528:
                // 4 sources

                if (keyCode == null) {
                    v2 = "Invalid key: " + keyName + " (use a-z, 0-9, f1-f12, or 'none')";
                    break;
                }
                $i$a$-find-CliEngine$executeCliCommand$setting$1 = this.keybindSettings();
                for (T it : $i$a$-find-CliEngine$executeCliCommand$setting$1) {
                    it = (KeybindSetting)it;
                    $i$a$-find-CliEngine$executeCliCommand$setting$3 = false;
                    v32 = it.getName().toLowerCase(Locale.ROOT);
                    Intrinsics.checkNotNullExpressionValue((Object)v32, (String)"toLowerCase(...)");
                    if (!StringsKt.contains$default((CharSequence)v32, (CharSequence)settingName, (boolean)false, (int)2, null)) continue;
                    v33 = it;
                    ** GOTO lbl541
                }
                v33 = null;
lbl541:
                // 2 sources

                setting = v33;
                if (setting != null) {
                    if (keyCode == 0) {
                        setting.setValue(-1);
                        Config.INSTANCE.save();
                        v2 = "Unbound '" + setting.getName() + "'";
                        break;
                    }
                    setting.setValue(keyCode);
                    Config.INSTANCE.save();
                    v2 = "Bound '" + setting.getName() + "' to " + keyName;
                    break;
                }
                v2 = "Setting not found: " + settingName;
                break;
            }
            case 3: {
                $this$filter$iv = this.keybindSettings();
                $i$f$filter = false;
                keyCode = $this$filter$iv;
                destination$iv$iv /* !! */  = new ArrayList<E>();
                $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    it = (KeybindSetting)element$iv$iv;
                    $i$a$-filter-CliEngine$executeCliCommand$bound$1 = false;
                    if (!(((Number)it.getValue()).intValue() != -1 && ((Number)it.getValue()).intValue() > 0)) continue;
                    destination$iv$iv /* !! */ .add(element$iv$iv);
                }
                bound = (List)destination$iv$iv /* !! */ ;
                if (bound.isEmpty()) {
                    v2 = "No keybinds set";
                    break;
                }
                $this$map$iv = CollectionsKt.take((Iterable)bound, (int)5);
                $i$f$map = false;
                destination$iv$iv /* !! */  = $this$map$iv;
                destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    $i$a$-filter-CliEngine$executeCliCommand$bound$1 = (KeybindSetting)item$iv$iv;
                    var20_110 = destination$iv$iv;
                    $i$a$-map-CliEngine$executeCliCommand$bindList$1 = false;
                    v = ((Number)s.getValue()).intValue();
                    keyChar = (32 <= v ? v < 127 : false) != false ? String.valueOf((char)v) : "F" + (v - 290 + 1);
                    var20_110.add(s.getName() + "=" + keyChar);
                }
                bindList = (List)destination$iv$iv;
                v2 = CollectionsKt.joinToString$default((Iterable)bindList, (CharSequence)", ", null, null, (int)0, null, null, (int)62, null);
                break;
            }
            case 19: {
                v2 = this.executeCbCommand(arg);
                break;
            }
            default: {
                v2 = "Unknown command: " + cmd;
            }
        }
        return v2;
    }

    /*
     * Unable to fully structure code
     */
    private final String executeCbCommand(String arg) {
        var3_2 = new String[]{" "};
        cbParts = StringsKt.split$default((CharSequence)arg, (String[])var3_2, (boolean)false, (int)2, (int)2, null);
        v0 = ((String)cbParts.get(0)).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"toLowerCase(...)");
        subCmd = v0;
        v1 = (String)CollectionsKt.getOrNull((List)cbParts, (int)1);
        if (v1 == null || (v1 = StringsKt.trim((CharSequence)v1).toString()) == null) {
            v1 = "";
        }
        cbArg = v1;
        var5_5 = subCmd;
        tmp = -1;
        switch (var5_5.hashCode()) {
            case -1785238953: {
                if (var5_5.equals("favorites")) {
                    tmp = 1;
                }
                break;
            }
            case 0: {
                if (var5_5.equals("")) {
                    tmp = 2;
                }
                break;
            }
            case 951117169: {
                if (var5_5.equals("configs")) {
                    tmp = 3;
                }
                break;
            }
            case 3521: {
                if (var5_5.equals("p1")) {
                    tmp = 4;
                }
                break;
            }
            case 3522: {
                if (var5_5.equals("p2")) {
                    tmp = 5;
                }
                break;
            }
            case -1396673086: {
                if (var5_5.equals("backup")) {
                    tmp = 6;
                }
                break;
            }
            case 3463: {
                if (var5_5.equals("ls")) {
                    tmp = 7;
                }
                break;
            }
            case 3522941: {
                if (var5_5.equals("save")) {
                    tmp = 8;
                }
                break;
            }
            case 1094496948: {
                if (var5_5.equals("replace")) {
                    tmp = 9;
                }
                break;
            }
            case 99339: {
                if (var5_5.equals("del")) {
                    tmp = 10;
                }
                break;
            }
            case -1335458389: {
                if (var5_5.equals("delete")) {
                    tmp = 10;
                }
                break;
            }
            case -934610812: {
                if (var5_5.equals("remove")) {
                    tmp = 11;
                }
                break;
            }
            case 3594468: {
                if (var5_5.equals("undo")) {
                    tmp = 12;
                }
                break;
            }
            case -934641255: {
                if (var5_5.equals("reload")) {
                    tmp = 13;
                }
                break;
            }
            case 3327206: {
                if (var5_5.equals("load")) {
                    tmp = 14;
                }
                break;
            }
            case -1352294148: {
                if (var5_5.equals("create")) {
                    tmp = 15;
                }
                break;
            }
            case 93832333: {
                if (var5_5.equals("block")) {
                    tmp = 16;
                }
                break;
            }
            case 101147: {
                if (var5_5.equals("fav")) {
                    tmp = 1;
                }
                break;
            }
            case 113754: {
                if (var5_5.equals("sel")) {
                    tmp = 17;
                }
                break;
            }
            case 109757585: {
                if (var5_5.equals("state")) {
                    tmp = 18;
                }
                break;
            }
            case 112797: {
                if (var5_5.equals("rep")) {
                    tmp = 9;
                }
                break;
            }
            case 3681: {
                if (var5_5.equals("st")) {
                    tmp = 18;
                }
                break;
            }
            case 98: {
                if (var5_5.equals("b")) {
                    tmp = 16;
                }
                break;
            }
            case 109399969: {
                if (var5_5.equals("shape")) {
                    tmp = 19;
                }
                break;
            }
            case 3108362: {
                if (var5_5.equals("edit")) {
                    tmp = 20;
                }
                break;
            }
            case 102: {
                if (var5_5.equals("f")) {
                    tmp = 21;
                }
                break;
            }
            case 3240: {
                if (var5_5.equals("em")) {
                    tmp = 20;
                }
                break;
            }
            case 94746189: {
                if (var5_5.equals("clear")) {
                    tmp = 22;
                }
                break;
            }
            case 3446877: {
                if (var5_5.equals("pos1")) {
                    tmp = 4;
                }
                break;
            }
            case 3446878: {
                if (var5_5.equals("pos2")) {
                    tmp = 5;
                }
                break;
            }
            case 3322014: {
                if (var5_5.equals("list")) {
                    tmp = 7;
                }
                break;
            }
            case 3143043: {
                if (var5_5.equals("fill")) {
                    tmp = 21;
                }
                break;
            }
            case 3506395: {
                if (var5_5.equals("room")) {
                    tmp = 23;
                }
                break;
            }
            case 790299700: {
                if (var5_5.equals("clearall")) {
                    tmp = 24;
                }
                break;
            }
            case 106940784: {
                if (var5_5.equals("props")) {
                    tmp = 25;
                }
                break;
            }
            case 114: {
                if (var5_5.equals("r")) {
                    tmp = 9;
                }
                break;
            }
            case -1715965556: {
                if (var5_5.equals("selection")) {
                    tmp = 17;
                }
                break;
            }
            case 117: {
                if (var5_5.equals("u")) {
                    tmp = 12;
                }
                break;
            }
            case 102715: {
                if (var5_5.equals("gui")) {
                    tmp = 26;
                }
                break;
            }
            case 3643: {
                if (var5_5.equals("rm")) {
                    tmp = 11;
                }
                break;
            }
            case -926053069: {
                if (var5_5.equals("properties")) {
                    tmp = 25;
                }
                break;
            }
        }
        block43 : switch (tmp) {
            case 20: {
                CosmeticBlocks.INSTANCE.toggleEditMode();
                if (CosmeticBlocks.INSTANCE.getEditMode()) {
                    v2 = "CB edit mode ON";
                    break;
                }
                v2 = "CB edit mode OFF";
                break;
            }
            case 16: {
                if (((CharSequence)cbArg).length() > 0) {
                    CosmeticBlocks.INSTANCE.setBlock(StringsKt.replace$default((String)cbArg, (String)" ", (String)"_", (boolean)false, (int)4, null));
                    v2 = "Brush: " + StringsKt.replace$default((String)cbArg, (String)" ", (String)"_", (boolean)false, (int)4, null);
                    break;
                }
                v2 = "Usage: cb block <block_id>";
                break;
            }
            case 11: {
                var7_6 = new String[]{" "};
                coords = StringsKt.split$default((CharSequence)cbArg, (String[])var7_6, (boolean)false, (int)0, (int)6, null);
                if (coords.size() >= 3) {
                    x = StringsKt.toIntOrNull((String)((String)coords.get(0)));
                    y = StringsKt.toIntOrNull((String)((String)coords.get(1)));
                    z = StringsKt.toIntOrNull((String)((String)coords.get(2)));
                    if (x != null && y != null && z != null) {
                        if (CosmeticBlocks.INSTANCE.removeAt(x, y, z)) {
                            v2 = "Removed at " + x + "," + y + "," + z;
                            break;
                        }
                        v2 = "No replacement at " + x + "," + y + "," + z;
                        break;
                    }
                    v2 = "Invalid coords";
                    break;
                }
                v2 = "Usage: cb remove <x> <y> <z>";
                break;
            }
            case 22: {
                count = CosmeticBlocks.INSTANCE.clearDimension();
                v2 = "Cleared " + count + " replacements";
                break;
            }
            case 24: {
                count = CosmeticBlocks.INSTANCE.clearAll();
                v2 = "Cleared " + count + " total replacements";
                break;
            }
            case 7: {
                list = CosmeticBlocks.INSTANCE.listReplacements();
                if (list.isEmpty()) {
                    v2 = "No CB replacements";
                    break;
                }
                v2 = CollectionsKt.joinToString$default((Iterable)CollectionsKt.take((Iterable)list, (int)5), (CharSequence)" | ", null, null, (int)0, null, (Function1)(Function1)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, executeCbCommand$lambda$0(java.lang.String ), (Ljava/lang/String;)Ljava/lang/CharSequence;)(), (int)30, null);
                break;
            }
            case 8: {
                CosmeticBlocks.INSTANCE.save();
                v2 = "CB data saved";
                break;
            }
            case 13: {
                CosmeticBlocks.INSTANCE.load();
                v2 = "Reloaded (" + CosmeticBlocks.INSTANCE.getReplacementCount() + " in dimension)";
                break;
            }
            case 15: {
                if (((CharSequence)cbArg).length() > 0) {
                    if (CosmeticBlocks.INSTANCE.createConfig(cbArg)) {
                        v2 = "Created config '" + cbArg + "'";
                        break;
                    }
                    v2 = "Config '" + cbArg + "' already exists";
                    break;
                }
                v2 = "Usage: cb create <name>";
                break;
            }
            case 14: {
                if (((CharSequence)cbArg).length() > 0) {
                    if (CosmeticBlocks.INSTANCE.loadConfigByName(cbArg)) {
                        v2 = "Loaded config '" + cbArg + "'";
                        break;
                    }
                    v2 = "Config '" + cbArg + "' not found";
                    break;
                }
                v2 = "Usage: cb load <name>";
                break;
            }
            case 10: {
                if (((CharSequence)cbArg).length() > 0) {
                    if (CosmeticBlocks.INSTANCE.deleteConfig(cbArg)) {
                        v2 = "Deleted config '" + cbArg + "'";
                        break;
                    }
                    v2 = "Cannot delete '" + cbArg + "'";
                    break;
                }
                v2 = "Usage: cb delete <name>";
                break;
            }
            case 3: {
                configs = CosmeticBlocks.INSTANCE.listConfigs();
                v2 = "Configs: " + CollectionsKt.joinToString$default((Iterable)configs, (CharSequence)", ", null, null, (int)0, null, null, (int)62, null) + " (active: " + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue() + ")";
                break;
            }
            case 4: {
                x = new String[]{" "};
                coords = StringsKt.split$default((CharSequence)cbArg, (String[])x, (boolean)false, (int)0, (int)6, null);
                if (coords.size() >= 3) {
                    x = StringsKt.toIntOrNull((String)((String)coords.get(0)));
                    y = StringsKt.toIntOrNull((String)((String)coords.get(1)));
                    z = StringsKt.toIntOrNull((String)((String)coords.get(2)));
                    if (x != null && y != null && z != null) {
                        CosmeticBlocks.INSTANCE.setPos1(new BlockPos(x.intValue(), y.intValue(), z.intValue()));
                        v2 = "Pos1: " + (Integer)x + ", " + y + ", " + z;
                        break;
                    }
                    v2 = "Invalid coordinates";
                    break;
                }
                pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                if (pos != null) {
                    CosmeticBlocks.INSTANCE.setPos1(pos);
                    v2 = "Pos1: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                    break;
                }
                v2 = "Look at a block first, then open CLI. Or: cb pos1 <x> <y> <z>";
                break;
            }
            case 5: {
                pos = new String[]{" "};
                coords = StringsKt.split$default((CharSequence)cbArg, (String[])pos, (boolean)false, (int)0, (int)6, null);
                if (coords.size() >= 3) {
                    x = StringsKt.toIntOrNull((String)((String)coords.get(0)));
                    y = StringsKt.toIntOrNull((String)((String)coords.get(1)));
                    z = StringsKt.toIntOrNull((String)((String)coords.get(2)));
                    if (x != null && y != null && z != null) {
                        CosmeticBlocks.INSTANCE.setPos2(new BlockPos(x.intValue(), y.intValue(), z.intValue()));
                        v2 = "Pos2: " + x + ", " + y + ", " + z;
                        break;
                    }
                    v2 = "Invalid coordinates";
                    break;
                }
                pos = CosmeticBlocks.INSTANCE.getLastLookBlockPos();
                if (pos != null) {
                    CosmeticBlocks.INSTANCE.setPos2((BlockPos)pos);
                    v2 = "Pos2: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
                    break;
                }
                v2 = "Look at a block first, then open CLI. Or: cb pos2 <x> <y> <z>";
                break;
            }
            case 17: {
                v2 = CosmeticBlocks.INSTANCE.getSelectionInfo();
                break;
            }
            case 21: {
                if (((CharSequence)cbArg).length() > 0) {
                    v2 = CosmeticBlocks.INSTANCE.fillRegion(cbArg);
                    break;
                }
                v2 = "Usage: cb fill <block[:weight]...>";
                break;
            }
            case 9: {
                pos = new String[]{" "};
                rParts = StringsKt.split$default((CharSequence)cbArg, (String[])pos, (boolean)false, (int)2, (int)2, null);
                from = (String)rParts.get(0);
                v3 = (String)CollectionsKt.getOrNull((List)rParts, (int)1);
                if (v3 == null || (v3 = StringsKt.trim((CharSequence)v3).toString()) == null) {
                    v3 = to = "";
                }
                if (((CharSequence)from).length() > 0 && ((CharSequence)to).length() > 0) {
                    v2 = CosmeticBlocks.INSTANCE.replaceRegion(from, to);
                    break;
                }
                v2 = "Usage: cb replace <from> <to[:weight]...>";
                break;
            }
            case 12: {
                v2 = CosmeticBlocks.INSTANCE.undoLastOperation();
                break;
            }
            case 1: {
                from = new String[]{" "};
                favParts = StringsKt.split$default((CharSequence)cbArg, (String[])from, (boolean)false, (int)2, (int)2, null);
                v4 = ((String)favParts.get(0)).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v4, (String)"toLowerCase(...)");
                favCmd = v4;
                v5 = (String)CollectionsKt.getOrNull((List)favParts, (int)1);
                if (v5 == null || (v5 = StringsKt.trim((CharSequence)v5).toString()) == null) {
                    v5 = "";
                }
                favArg = v5;
                if (((CharSequence)favCmd).length() == 0 || Intrinsics.areEqual((Object)favCmd, (Object)"gui")) {
                    Scheduler.INSTANCE.scheduleTickEnd((Function0<Unit>)(Function0)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, executeCbCommand$lambda$1(), ()Lkotlin/Unit;)());
                    v2 = "Opening favorites...";
                    break;
                }
                if (Intrinsics.areEqual((Object)favCmd, (Object)"add")) {
                    var10_46 = favArg;
                    if (var10_46.length() == 0) {
                        $i$a$-ifEmpty-CliEngine$executeCbCommand$block$1 = false;
                        v6 = CosmeticBlocks.INSTANCE.getSelectedBlock();
                    } else {
                        v6 = var10_46;
                    }
                    block = (String)v6;
                    if (CosmeticBlocks.INSTANCE.addFavorite(block)) {
                        v2 = "Added " + StringsKt.removePrefix((String)block, (CharSequence)"minecraft:") + " to favorites";
                        break;
                    }
                    v2 = "Already in favorites or invalid block";
                    break;
                }
                if (Intrinsics.areEqual((Object)favCmd, (Object)"remove") || Intrinsics.areEqual((Object)favCmd, (Object)"rm")) {
                    var10_47 = favArg;
                    if (var10_47.length() == 0) {
                        $i$a$-ifEmpty-CliEngine$executeCbCommand$block$2 = false;
                        v7 = CosmeticBlocks.INSTANCE.getSelectedBlock();
                    } else {
                        v7 = var10_47;
                    }
                    block = (String)v7;
                    if (CosmeticBlocks.INSTANCE.removeFavorite(block)) {
                        v2 = "Removed " + StringsKt.removePrefix((String)block, (CharSequence)"minecraft:") + " from favorites";
                        break;
                    }
                    v2 = "Not in favorites";
                    break;
                }
                if (Intrinsics.areEqual((Object)favCmd, (Object)"list") || Intrinsics.areEqual((Object)favCmd, (Object)"ls")) {
                    favs = CosmeticBlocks.INSTANCE.getFavorites();
                    if (favs.isEmpty()) {
                        v2 = "No favorites";
                        break;
                    }
                    v2 = CollectionsKt.joinToString$default((Iterable)favs, (CharSequence)", ", null, null, (int)0, null, (Function1)(Function1)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, executeCbCommand$lambda$4(java.lang.String ), (Ljava/lang/String;)Ljava/lang/CharSequence;)(), (int)30, null);
                    break;
                }
                v2 = "Usage: cb fav [add|remove|list|gui]";
                break;
            }
            case 19: {
                if (((CharSequence)cbArg).length() == 0) {
                    v8 = CosmeticBlocks.INSTANCE.getCurrentShape();
                    if (v8 == null) {
                        v8 = "none";
                    }
                    v2 = "Shape: " + v8;
                    break;
                }
                if (Intrinsics.areEqual((Object)cbArg, (Object)"none") || Intrinsics.areEqual((Object)cbArg, (Object)"off")) {
                    CosmeticBlocks.INSTANCE.setCurrentShape(null);
                    v2 = "Shape override cleared";
                    break;
                }
                favParts = new String[]{"fence", "wall", "pane"};
                if (CollectionsKt.listOf((Object[])favParts).contains(cbArg)) {
                    CosmeticBlocks.INSTANCE.setCurrentShape(cbArg);
                    v2 = "Shape: " + cbArg + " (next blocks will use this shape)";
                    break;
                }
                v2 = "Shapes: fence, wall, pane, none";
                break;
            }
            case 23: {
                favCmd = new String[]{" "};
                roomParts = StringsKt.split$default((CharSequence)cbArg, (String[])favCmd, (boolean)false, (int)2, (int)2, null);
                v9 = ((String)roomParts.get(0)).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)v9, (String)"toLowerCase(...)");
                favArg = roomCmd = v9;
                tmp = -1;
                switch (favArg.hashCode()) {
                    case 0: {
                        if (favArg.equals("")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case 3463: {
                        if (favArg.equals("ls")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case 94746189: {
                        if (favArg.equals("clear")) {
                            tmp = 3;
                        }
                        break;
                    }
                    case 3524221: {
                        if (favArg.equals("scan")) {
                            tmp = 4;
                        }
                        break;
                    }
                    case 3322014: {
                        if (favArg.equals("list")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case 3237038: {
                        if (favArg.equals("info")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case 790299700: {
                        if (favArg.equals("clearall")) {
                            tmp = 5;
                        }
                        break;
                    }
                }
                switch (tmp) {
                    case 1: {
                        room = RoomScanner.INSTANCE.getCurrentRoom();
                        if (room != null) {
                            blockCount = CosmeticBlocks.INSTANCE.getRoomBlockCount(room.getData().getName());
                            v2 = "Room: " + room.getData().getName() + " | Rot: " + room.getRotation() + " | Blocks: " + blockCount;
                            break block43;
                        }
                        v2 = "Not in a scanned dungeon room";
                        break block43;
                    }
                    case 3: {
                        room = RoomScanner.INSTANCE.getCurrentRoom();
                        if (room != null) {
                            count = CosmeticBlocks.INSTANCE.clearRoom(room.getData().getName());
                            v2 = "Cleared " + count + " blocks from " + room.getData().getName();
                            break block43;
                        }
                        v2 = "Not in a scanned room";
                        break block43;
                    }
                    case 5: {
                        count = CosmeticBlocks.INSTANCE.clearAllRooms();
                        v2 = "Cleared " + count + " blocks from all rooms";
                        break block43;
                    }
                    case 2: {
                        rooms = CosmeticBlocks.INSTANCE.listRoomNames();
                        if (rooms.isEmpty()) {
                            v2 = "No room blocks saved";
                            break block43;
                        }
                        v2 = CollectionsKt.joinToString$default((Iterable)rooms, (CharSequence)", ", null, null, (int)0, null, (Function1)(Function1)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, executeCbCommand$lambda$5(java.lang.String ), (Ljava/lang/String;)Ljava/lang/CharSequence;)(), (int)30, null);
                        break block43;
                    }
                    case 4: {
                        room = RoomScanner.INSTANCE.getCurrentRoom();
                        v10 = room;
                        if (v10 == null || (v10 = v10.getData()) == null || (v10 = v10.getName()) == null) {
                            v10 = "Unknown";
                        }
                        v2 = "Room: " + (String)v10;
                        break block43;
                    }
                }
                v2 = "Usage: cb room [info|clear|clearall|list|scan]";
                break;
            }
            case 6: {
                v2 = ConfigBackup.INSTANCE.backup();
                break;
            }
            case 26: {
                Scheduler.INSTANCE.scheduleTickEnd((Function0<Unit>)(Function0)LambdaMetafactory.metafactory(null, null, null, ()Ljava/lang/Object;, executeCbCommand$lambda$6(), ()Lkotlin/Unit;)());
                v2 = "Opening block selector...";
                break;
            }
            case 18: {
                roomCmd = new String[]{" "};
                stateParts = StringsKt.split$default((CharSequence)cbArg, (String[])roomCmd, (boolean)false, (int)2, (int)2, null);
                property = (String)stateParts.get(0);
                v11 = (String)CollectionsKt.getOrNull((List)stateParts, (int)1);
                if (v11 == null || (v11 = StringsKt.trim((CharSequence)v11).toString()) == null) {
                    v11 = value = "";
                }
                if (((CharSequence)property).length() > 0 && ((CharSequence)value).length() > 0) {
                    CosmeticBlocks.INSTANCE.setBlockProperty(property, value);
                    v2 = "Brush: " + StringsKt.removePrefix((String)CosmeticBlocks.INSTANCE.getSelectedBlock(), (CharSequence)"minecraft:");
                    break;
                }
                v2 = "Usage: cb state <property> <value>";
                break;
            }
            case 25: {
                CosmeticBlocks.INSTANCE.listBlockProperties();
                v2 = "See chat for properties";
                break;
            }
            case 2: {
                count = CosmeticBlocks.INSTANCE.getReplacementCount();
                mode = CosmeticBlocks.INSTANCE.getEditMode() != false ? "ON" : "OFF";
                v12 = RoomScanner.INSTANCE.getCurrentRoom();
                if (v12 == null) ** GOTO lbl556
                it = v12;
                $i$a$-let-CliEngine$executeCbCommand$roomInfo$1 = false;
                v13 = " | Room: " + it.getData().getName();
                v12 = v13;
                if (v13 != null) ** GOTO lbl557
lbl556:
                // 2 sources

                v12 = "";
lbl557:
                // 2 sources

                roomInfo = v12;
                v2 = "CB: " + count + " replacements, edit: " + mode + ", config: " + CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue() + (String)roomInfo;
                break;
            }
            default: {
                v2 = "Usage: cb <em|block|remove|clear|list|save|reload|fill|replace|pos1|pos2|sel|undo|fav|shape|room|state|props|gui|backup>";
            }
        }
        return v2;
    }

    private final boolean exportToClipboard() {
        boolean bl;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("# CB Settings Export").append('\n');
            Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SettingsTab tab = (SettingsTab)element$iv;
                boolean bl2 = false;
                Iterable $this$forEach$iv2 = tab.getSettings();
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Setting setting = (Setting)element$iv2;
                    boolean bl3 = false;
                    sb.append(tab.getName() + "." + setting.getName() + "=" + INSTANCE.valueToString(setting)).append('\n');
                }
            }
            GLFW.glfwSetClipboardString((long)GLFW.glfwGetCurrentContext(), (CharSequence)sb.toString());
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    private final boolean importFromClipboard() {
        boolean bl;
        try {
            String string = GLFW.glfwGetClipboardString((long)GLFW.glfwGetCurrentContext());
            if (string == null) {
                return false;
            }
            String data = string;
            int imported = 0;
            Iterable $this$forEach$iv = StringsKt.lines((CharSequence)data);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Setting setting;
                Object v2;
                List parts;
                block8: {
                    SettingsTab tab;
                    Object v1;
                    Object object2;
                    String propName;
                    block7: {
                        String line = (String)element$iv;
                        boolean bl2 = false;
                        if (StringsKt.startsWith$default((String)line, (String)"#", (boolean)false, (int)2, null) || StringsKt.isBlank((CharSequence)line)) continue;
                        String[] stringArray = new String[]{"="};
                        parts = StringsKt.split$default((CharSequence)line, (String[])stringArray, (boolean)false, (int)2, (int)2, null);
                        if (parts.size() != 2) continue;
                        String[] stringArray2 = new String[]{"."};
                        List keyParts = StringsKt.split$default((CharSequence)((CharSequence)parts.get(0)), (String[])stringArray2, (boolean)false, (int)2, (int)2, null);
                        if (keyParts.size() != 2) continue;
                        String tabName = (String)keyParts.get(0);
                        propName = (String)keyParts.get(1);
                        Iterable iterable = Tabs.INSTANCE.getAll();
                        for (Object object2 : iterable) {
                            SettingsTab it = (SettingsTab)object2;
                            boolean bl3 = false;
                            if (!Intrinsics.areEqual((Object)it.getName(), (Object)tabName)) continue;
                            v1 = object2;
                            break block7;
                        }
                        v1 = null;
                    }
                    if ((SettingsTab)v1 == null) continue;
                    Iterable iterable = tab.getSettings();
                    object2 = iterable.iterator();
                    while (object2.hasNext()) {
                        Object e = object2.next();
                        Setting it = (Setting)e;
                        boolean bl4 = false;
                        if (!Intrinsics.areEqual((Object)it.getName(), (Object)propName)) continue;
                        v2 = e;
                        break block8;
                    }
                    v2 = null;
                }
                if ((Setting)v2 == null || !INSTANCE.stringToValue(setting, (String)parts.get(1))) continue;
                ++imported;
            }
            if (imported > 0) {
                Config.INSTANCE.save();
            }
            bl = imported > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final boolean saveProfile(String name) {
        boolean bl;
        try {
            CharSequence charSequence = name;
            Object object = new Regex("[^a-zA-Z0-9_-]");
            Object object2 = "_";
            String sanitized = object.replace(charSequence, (String)object2);
            Path file = this.getProfilesDir().resolve(sanitized + ".profile");
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(this.getProfilesDir(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                Files.createDirectories(this.getProfilesDir(), new FileAttribute[0]);
            }
            object = Files.newBufferedWriter(file, new OpenOption[0]);
            object2 = null;
            try {
                BufferedWriter writer = (BufferedWriter)object;
                boolean bl2 = false;
                writer.write("# CB Profile: " + name + "\n");
                Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    SettingsTab tab = (SettingsTab)element$iv;
                    boolean bl3 = false;
                    Iterable $this$forEach$iv2 = tab.getSettings();
                    boolean $i$f$forEach2 = false;
                    for (Object element$iv2 : $this$forEach$iv2) {
                        Setting setting = (Setting)element$iv2;
                        boolean bl4 = false;
                        writer.write(tab.getName() + "." + setting.getName() + ":" + INSTANCE.valueToString(setting) + "\n");
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
            catch (Throwable throwable) {
                object2 = throwable;
                throw throwable;
            }
            finally {
                CloseableKt.closeFinally((Closeable)object, (Throwable)object2);
            }
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    private final boolean loadProfile(String name) {
        boolean bl;
        try {
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string = "_";
            String sanitized = regex.replace(charSequence, string);
            Path file = this.getProfilesDir().resolve(sanitized + ".profile");
            Intrinsics.checkNotNull((Object)file);
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(file, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                return false;
            }
            Map map = new LinkedHashMap();
            List<String> list = Files.readAllLines(file);
            Intrinsics.checkNotNullExpressionValue(list, (String)"readAllLines(...)");
            Iterable $this$forEach$iv = list;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                int idx;
                String line = (String)element$iv;
                boolean bl2 = false;
                Intrinsics.checkNotNull((Object)line);
                if (StringsKt.startsWith$default((String)line, (String)"#", (boolean)false, (int)2, null) || StringsKt.isBlank((CharSequence)line) || (idx = StringsKt.indexOf$default((CharSequence)line, (char)':', (int)0, (boolean)false, (int)6, null)) <= 0) continue;
                String string2 = line.substring(0, idx);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
                String string3 = line.substring(idx + 1);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
                map.put(string2, string3);
            }
            $this$forEach$iv = Tabs.INSTANCE.getAll();
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SettingsTab tab = (SettingsTab)element$iv;
                boolean bl3 = false;
                Iterable $this$forEach$iv2 = tab.getSettings();
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    String v;
                    Setting setting = (Setting)element$iv2;
                    boolean bl4 = false;
                    if ((String)map.get(tab.getName() + "." + setting.getName()) == null) continue;
                    INSTANCE.stringToValue(setting, v);
                }
            }
            Config.INSTANCE.save();
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final List<String> listProfiles() {
        Object object;
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(this.getProfilesDir(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            return CollectionsKt.emptyList();
        }
        Object object2 = this;
        try {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            CliEngine $this$listProfiles_u24lambda_u240 = object2;
            boolean bl = false;
            Iterable iterable = PathsKt.listDirectoryEntries((Path)$this$listProfiles_u24lambda_u240.getProfilesDir(), (String)"*.profile");
            boolean $i$f$map = false;
            void var6_7 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Path path = (Path)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl2 = false;
                collection.add(PathsKt.getNameWithoutExtension((Path)it));
            }
            object = Result.constructor-impl((Object)CollectionsKt.sorted((Iterable)((List)destination$iv$iv)));
        }
        catch (Throwable throwable) {
            object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)throwable));
        }
        object2 = object;
        object = CollectionsKt.emptyList();
        return (List)(Result.isFailure-impl((Object)object2) ? object : object2);
    }

    private final boolean deleteProfile(String name) {
        boolean bl;
        try {
            boolean bl2;
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string = "_";
            String sanitized = regex.replace(charSequence, string);
            Path file = this.getProfilesDir().resolve(sanitized + ".profile");
            Intrinsics.checkNotNull((Object)file);
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (Files.exists(file, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                Files.delete(file);
                bl2 = true;
            } else {
                bl2 = false;
            }
            bl = bl2;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    private final String valueToString(Setting<?> setting) {
        Object v = setting.getValue();
        Object obj = v;
        return obj instanceof Integer ? String.valueOf(((Number)v).intValue()) : (obj instanceof Double ? String.valueOf(((Number)v).doubleValue()) : (obj instanceof Float ? String.valueOf(((Number)v).floatValue()) : (obj instanceof Boolean ? String.valueOf((Boolean)v) : (obj instanceof Enum ? ((Enum)v).name() : String.valueOf(v)))));
    }

    private final boolean stringToValue(Setting<?> setting, String raw) {
        boolean bl;
        try {
            boolean bl2;
            Object cur = setting.getValue();
            if (cur instanceof Integer) {
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Int>");
                setting.setValue(Integer.parseInt(raw));
                bl2 = true;
            } else if (cur instanceof Double) {
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Double>");
                setting.setValue(Double.parseDouble(raw));
                bl2 = true;
            } else if (cur instanceof Float) {
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Float>");
                setting.setValue(Float.valueOf(Float.parseFloat(raw)));
                bl2 = true;
            } else if (cur instanceof Boolean) {
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Boolean>");
                setting.setValue(Boolean.parseBoolean(raw));
                bl2 = true;
            } else if (cur instanceof String) {
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.String>");
                setting.setValue(raw);
                bl2 = true;
            } else if (cur instanceof Enum) {
                Enum enum_;
                block16: {
                    Enum[] constants = (Enum[])cur.getClass().getEnumConstants();
                    Intrinsics.checkNotNull((Object)constants);
                    Enum[] $this$firstOrNull$iv = constants;
                    boolean $i$f$firstOrNull = false;
                    int n = $this$firstOrNull$iv.length;
                    for (int i = 0; i < n; ++i) {
                        Enum element$iv;
                        Enum it = element$iv = $this$firstOrNull$iv[i];
                        boolean bl3 = false;
                        if (!Intrinsics.areEqual((Object)it.name(), (Object)raw)) continue;
                        enum_ = element$iv;
                        break block16;
                    }
                    enum_ = null;
                }
                if (enum_ == null) {
                    return false;
                }
                Enum match = enum_;
                Intrinsics.checkNotNull(setting, (String)"null cannot be cast to non-null type com.cb.setting.Setting<kotlin.Any>");
                setting.setValue(match);
                bl2 = true;
            } else {
                bl2 = false;
            }
            bl = bl2;
        }
        catch (Exception exception) {
            bl = false;
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void saveSavedColors() {
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(this.getSavedColorsFile(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            PathsKt.createParentDirectories((Path)this.getSavedColorsFile(), (FileAttribute[])new FileAttribute[0]);
            FileAttribute[] fileAttributeArray = new FileAttribute[]{};
            Intrinsics.checkNotNullExpressionValue((Object)Files.createFile(this.getSavedColorsFile(), Arrays.copyOf(fileAttributeArray, fileAttributeArray.length)), (String)"createFile(...)");
        }
        Closeable closeable = Files.newBufferedWriter(this.getSavedColorsFile(), new OpenOption[0]);
        Throwable throwable = null;
        try {
            BufferedWriter writer = (BufferedWriter)closeable;
            boolean bl = false;
            Iterable $this$forEach$iv = savedColors;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Color color = (Color)element$iv;
                boolean bl2 = false;
                writer.write(color.getRGB() + "\n");
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void loadSavedColors() {
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(this.getSavedColorsFile(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            return;
        }
        savedColors.clear();
        Closeable closeable = Files.newBufferedReader(this.getSavedColorsFile());
        Throwable throwable = null;
        try {
            BufferedReader reader = (BufferedReader)closeable;
            boolean bl = false;
            while (reader.readLine() != null) {
                String line;
                Integer n = StringsKt.toIntOrNull((String)line);
                if (n == null) continue;
                int it = ((Number)n).intValue();
                boolean bl2 = false;
                savedColors.add(new Color(it, true));
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void saveNotes() {
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(this.getNotesFile(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            PathsKt.createParentDirectories((Path)this.getNotesFile(), (FileAttribute[])new FileAttribute[0]);
            FileAttribute[] fileAttributeArray = new FileAttribute[]{};
            Intrinsics.checkNotNullExpressionValue((Object)Files.createFile(this.getNotesFile(), Arrays.copyOf(fileAttributeArray, fileAttributeArray.length)), (String)"createFile(...)");
        }
        Closeable closeable = Files.newBufferedWriter(this.getNotesFile(), new OpenOption[0]);
        Throwable throwable = null;
        try {
            BufferedWriter writer = (BufferedWriter)closeable;
            boolean bl = false;
            Iterable $this$forEach$iv = notes;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String note = (String)element$iv;
                boolean bl2 = false;
                writer.write(note + "\n");
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void loadNotes() {
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(this.getNotesFile(), Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            return;
        }
        notes.clear();
        Closeable closeable = Files.newBufferedReader(this.getNotesFile());
        Throwable throwable = null;
        try {
            BufferedReader reader = (BufferedReader)closeable;
            boolean bl = false;
            while (reader.readLine() != null) {
                String line;
                if (!(!StringsKt.isBlank((CharSequence)line))) continue;
                notes.add(line);
            }
            Unit unit = Unit.INSTANCE;
        }
        catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        }
        finally {
            CloseableKt.closeFinally((Closeable)closeable, (Throwable)throwable);
        }
    }

    private static final boolean cancelReminder$lambda$0(int $id, Reminder it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return it.getId() == $id;
    }

    private static final boolean cancelReminder$lambda$1(Function1 $tmp0, Object p0) {
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final String blockSuggestions$lambda$0(Identifier it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return it.getPath();
    }

    private static final boolean blockSuggestions$lambda$1(String $q, String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return ((CharSequence)$q).length() == 0 || StringsKt.contains$default((CharSequence)it, (CharSequence)$q, (boolean)false, (int)2, null);
    }

    private static final Suggestion blockSuggestions$lambda$2(String $prefix, String $hint, String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new Suggestion($prefix + " " + it, it, $hint);
    }

    private static final CharSequence executeCliCommand$lambda$0(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return StringsKt.take((String)it, (int)30) + (it.length() > 30 ? "..." : "");
    }

    private static final CharSequence executeCbCommand$lambda$0(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return StringsKt.take((String)it, (int)25);
    }

    private static final Unit executeCbCommand$lambda$1() {
        Minecraft.getInstance().gui.setScreen((Screen)new BlockSelectorScreen());
        return Unit.INSTANCE;
    }

    private static final CharSequence executeCbCommand$lambda$4(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return StringsKt.removePrefix((String)it, (CharSequence)"minecraft:");
    }

    private static final CharSequence executeCbCommand$lambda$5(String it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return it + " (" + CosmeticBlocks.INSTANCE.getRoomBlockCount(it) + ")";
    }

    private static final Unit executeCbCommand$lambda$6() {
        Minecraft.getInstance().gui.setScreen((Screen)new BlockSelectorScreen());
        return Unit.INSTANCE;
    }

    static {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("cb");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        configDir = path;
        commandHistory = new ArrayList();
        savedColors = new ArrayList();
        notes = new ArrayList();
        reminders = new ArrayList();
        nextReminderId = 1;
        Object[] objectArray = new String[]{"w", "export", "import", "save", "load", "profiles", "delprofile", "scale", "fps", "ping", "calc", "c", "coin", "flip", "roll", "dice", "d", "note", "notes", "noteclear", "noteexport", "remind", "reminder", "timer", "reminders", "cancelreminder", "clearreminders", "t", "toggle", "bind", "binds", "keybinds", "cb"};
        TOP_COMMANDS = CollectionsKt.listOf((Object[])objectArray);
        objectArray = new String[]{"em", "edit", "block", "b", "remove", "rm", "clear", "clearall", "list", "ls", "save", "reload", "create", "load", "delete", "del", "configs", "pos1", "p1", "pos2", "p2", "sel", "selection", "fill", "f", "replace", "rep", "r", "undo", "u", "fav", "favorites", "shape", "room", "backup", "gui", "state", "st", "props", "properties"};
        CB_SUBCOMMANDS = CollectionsKt.listOf((Object[])objectArray);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u000b\u001a\u00020\nH\u0002J\b\u0010\f\u001a\u00020\nH\u0002J\b\u0010\r\u001a\u00020\nH\u0002J\b\u0010\u000e\u001a\u00020\nH\u0002J\b\u0010\u000f\u001a\u00020\nH\u0002J\b\u0010\u0010\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lcom/cb/gui/screens/CliEngine$CalcParser;", "", "expr", "", "<init>", "(Ljava/lang/String;)V", "pos", "", "length", "parse", "", "parseExpression", "parseTerm", "parseFactor", "parseUnary", "parsePrimary", "parseNumber", "match", "", "c", "", "expect", "", "skipWhitespace", "cb_client"})
    private static final class CalcParser {
        @NotNull
        private final String expr;
        private int pos;
        private final int length;

        public CalcParser(@NotNull String expr) {
            Intrinsics.checkNotNullParameter((Object)expr, (String)"expr");
            this.expr = expr;
            this.length = this.expr.length();
        }

        public final double parse() {
            double result = this.parseExpression();
            if (this.pos < this.length) {
                throw new IllegalArgumentException("Unexpected character");
            }
            return result;
        }

        private final double parseExpression() {
            double result = this.parseTerm();
            while (this.pos < this.length) {
                if (this.match('+')) {
                    result += this.parseTerm();
                    continue;
                }
                if (!this.match('-')) break;
                result -= this.parseTerm();
            }
            return result;
        }

        private final double parseTerm() {
            double result = this.parseFactor();
            while (this.pos < this.length) {
                if (this.match('*')) {
                    result *= this.parseFactor();
                    continue;
                }
                if (this.match('/')) {
                    result /= this.parseFactor();
                    continue;
                }
                if (!this.match('%')) break;
                result %= this.parseFactor();
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
            block43: {
                String name;
                this.skipWhitespace();
                if (this.match('(')) {
                    double result = this.parseExpression();
                    if (!this.match(')')) {
                        throw new IllegalArgumentException("Missing )");
                    }
                    return result;
                }
                int start = this.pos;
                while (this.pos < this.length && (Character.isLetter(this.expr.charAt(this.pos)) || this.expr.charAt(this.pos) == '_')) {
                    int n = this.pos;
                    this.pos = n + 1;
                }
                if (this.pos <= start) break block43;
                String string = this.expr.substring(start, this.pos);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
                String string2 = string.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"toLowerCase(...)");
                return switch (name = string2) {
                    case "pi" -> Math.PI;
                    case "e" -> Math.E;
                    case "sqrt" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.sqrt(arg);
                    }
                    case "sin" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.sin(arg);
                    }
                    case "cos" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.cos(arg);
                    }
                    case "tan" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.tan(arg);
                    }
                    case "abs" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.abs(arg);
                    }
                    case "log" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.log10(arg);
                    }
                    case "ln" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.log(arg);
                    }
                    case "floor" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.floor(arg);
                    }
                    case "ceil" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.ceil(arg);
                    }
                    case "round" -> {
                        this.expect('(');
                        double arg = this.parseExpression();
                        this.expect(')');
                        yield Math.rint(arg);
                    }
                    default -> throw new IllegalArgumentException("Unknown function: " + name);
                };
            }
            return this.parseNumber();
        }

        private final double parseNumber() {
            int n;
            this.skipWhitespace();
            int start = this.pos;
            if (this.pos < this.length && (this.expr.charAt(this.pos) == '-' || this.expr.charAt(this.pos) == '+')) {
                n = this.pos;
                this.pos = n + 1;
            }
            while (this.pos < this.length && (Character.isDigit(this.expr.charAt(this.pos)) || this.expr.charAt(this.pos) == '.')) {
                n = this.pos;
                this.pos = n + 1;
            }
            if (start == this.pos) {
                throw new IllegalArgumentException("Expected number at " + this.pos);
            }
            String string = this.expr.substring(start, this.pos);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
            return Double.parseDouble(string);
        }

        private final boolean match(char c) {
            boolean bl;
            this.skipWhitespace();
            if (this.pos < this.length && this.expr.charAt(this.pos) == c) {
                int n = this.pos;
                this.pos = n + 1;
                bl = true;
            } else {
                bl = false;
            }
            return bl;
        }

        private final void expect(char c) {
            if (!this.match(c)) {
                throw new IllegalArgumentException("Expected '" + c + "'");
            }
        }

        private final void skipWhitespace() {
            while (this.pos < this.length && CharsKt.isWhitespace((char)this.expr.charAt(this.pos))) {
                int n = this.pos;
                this.pos = n + 1;
            }
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0014\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0017\u001a\u00020\u0007H\u00d6\u0081\u0004J\n\u0010\u0018\u001a\u00020\u0005H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, d2={"Lcom/cb/gui/screens/CliEngine$Reminder;", "", "triggerTime", "", "message", "", "id", "", "<init>", "(JLjava/lang/String;I)V", "getTriggerTime", "()J", "getMessage", "()Ljava/lang/String;", "getId", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "cb_client"})
    public static final class Reminder {
        private final long triggerTime;
        @NotNull
        private final String message;
        private final int id;

        public Reminder(long triggerTime, @NotNull String message, int id) {
            Intrinsics.checkNotNullParameter((Object)message, (String)"message");
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
        public final Reminder copy(long triggerTime, @NotNull String message, int id) {
            Intrinsics.checkNotNullParameter((Object)message, (String)"message");
            return new Reminder(triggerTime, message, id);
        }

        public static /* synthetic */ Reminder copy$default(Reminder reminder, long l, String string, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                l = reminder.triggerTime;
            }
            if ((n2 & 2) != 0) {
                string = reminder.message;
            }
            if ((n2 & 4) != 0) {
                n = reminder.id;
            }
            return reminder.copy(l, string, n);
        }

        @NotNull
        public String toString() {
            return "Reminder(triggerTime=" + this.triggerTime + ", message=" + this.message + ", id=" + this.id + ")";
        }

        public int hashCode() {
            int result = Long.hashCode(this.triggerTime);
            result = result * 31 + this.message.hashCode();
            result = result * 31 + Integer.hashCode(this.id);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Reminder)) {
                return false;
            }
            Reminder reminder = (Reminder)other;
            if (this.triggerTime != reminder.triggerTime) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.message, (Object)reminder.message)) {
                return false;
            }
            return this.id == reminder.id;
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0003H\u00c6\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0014\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0013\u001a\u00020\u0014H\u00d6\u0081\u0004J\n\u0010\u0015\u001a\u00020\u0003H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0016"}, d2={"Lcom/cb/gui/screens/CliEngine$Suggestion;", "", "insert", "", "display", "hint", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getInsert", "()Ljava/lang/String;", "getDisplay", "getHint", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "cb_client"})
    public static final class Suggestion {
        @NotNull
        private final String insert;
        @NotNull
        private final String display;
        @NotNull
        private final String hint;

        public Suggestion(@NotNull String insert, @NotNull String display, @NotNull String hint) {
            Intrinsics.checkNotNullParameter((Object)insert, (String)"insert");
            Intrinsics.checkNotNullParameter((Object)display, (String)"display");
            Intrinsics.checkNotNullParameter((Object)hint, (String)"hint");
            this.insert = insert;
            this.display = display;
            this.hint = hint;
        }

        public /* synthetic */ Suggestion(String string, String string2, String string3, int n, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n & 2) != 0) {
                string2 = string;
            }
            if ((n & 4) != 0) {
                string3 = "";
            }
            this(string, string2, string3);
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
        public final Suggestion copy(@NotNull String insert, @NotNull String display, @NotNull String hint) {
            Intrinsics.checkNotNullParameter((Object)insert, (String)"insert");
            Intrinsics.checkNotNullParameter((Object)display, (String)"display");
            Intrinsics.checkNotNullParameter((Object)hint, (String)"hint");
            return new Suggestion(insert, display, hint);
        }

        public static /* synthetic */ Suggestion copy$default(Suggestion suggestion, String string, String string2, String string3, int n, Object object) {
            if ((n & 1) != 0) {
                string = suggestion.insert;
            }
            if ((n & 2) != 0) {
                string2 = suggestion.display;
            }
            if ((n & 4) != 0) {
                string3 = suggestion.hint;
            }
            return suggestion.copy(string, string2, string3);
        }

        @NotNull
        public String toString() {
            return "Suggestion(insert=" + this.insert + ", display=" + this.display + ", hint=" + this.hint + ")";
        }

        public int hashCode() {
            int result = this.insert.hashCode();
            result = result * 31 + this.display.hashCode();
            result = result * 31 + this.hint.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Suggestion)) {
                return false;
            }
            Suggestion suggestion = (Suggestion)other;
            if (!Intrinsics.areEqual((Object)this.insert, (Object)suggestion.insert)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.display, (Object)suggestion.display)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.hint, (Object)suggestion.hint);
        }
    }
}


