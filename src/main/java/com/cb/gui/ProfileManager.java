/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.io.CloseableKt
 *  kotlin.io.path.PathsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.fabricmc.loader.api.FabricLoader
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.CloseableKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0016\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fJ\u000e\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lcom/cb/gui/ProfileManager;", "", "<init>", "()V", "profilesDir", "Ljava/nio/file/Path;", "listProfiles", "", "Lcom/cb/gui/ProfileManager$ProfileInfo;", "saveProfile", "", "name", "", "loadProfile", "deleteProfile", "renameProfile", "oldName", "newName", "duplicateProfile", "sourceName", "exportProfile", "importProfile", "content", "formatTimestamp", "millis", "", "ProfileInfo", "cb_client"})
@SourceDebugExtension(value={"SMAP\nProfileManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileManager.kt\ncom/cb/gui/ProfileManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,236:1\n1586#2:237\n1661#2,2:238\n1834#2,4:240\n1663#2:244\n1080#2:245\n1915#2:246\n1915#2,2:247\n1916#2:249\n1915#2,2:250\n1915#2:252\n1915#2,2:253\n1916#2:255\n*S KotlinDebug\n*F\n+ 1 ProfileManager.kt\ncom/cb/gui/ProfileManager\n*L\n35#1:237\n35#1:238,2\n37#1:240,4\n35#1:244\n47#1:245\n69#1:246\n71#1:247,2\n69#1:249\n96#1:250,2\n109#1:252\n110#1:253,2\n109#1:255\n*E\n"})
public final class ProfileManager {
    @NotNull
    public static final ProfileManager INSTANCE = new ProfileManager();
    @NotNull
    private static final Path profilesDir;

    private ProfileManager() {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<ProfileInfo> listProfiles() {
        void $this$mapTo$iv$iv;
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(profilesDir, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = PathsKt.listDirectoryEntries((Path)profilesDir, (String)"*.profile");
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            int n;
            void path;
            Path path2 = (Path)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            try {
                int n2;
                List<String> list = Files.readAllLines((Path)path);
                Intrinsics.checkNotNullExpressionValue(list, (String)"readAllLines(...)");
                Iterable $this$count$iv = list;
                boolean $i$f$count = false;
                if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                    n2 = 0;
                } else {
                    int count$iv = 0;
                    for (Object element$iv : $this$count$iv) {
                        String it = (String)element$iv;
                        boolean bl2 = false;
                        Intrinsics.checkNotNull((Object)it);
                        if (!StringsKt.contains$default((CharSequence)it, (CharSequence)":", (boolean)false, (int)2, null) || ++count$iv >= 0) continue;
                        CollectionsKt.throwCountOverflow();
                    }
                    n2 = count$iv;
                }
                n = n2;
            }
            catch (Exception e) {
                n = 0;
            }
            int settingCount = n;
            String string = PathsKt.getNameWithoutExtension((Path)path);
            LinkOption[] linkOptionArray2 = new LinkOption[]{};
            FileTime fileTime = Files.getLastModifiedTime((Path)path, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length));
            Intrinsics.checkNotNullExpressionValue((Object)fileTime, (String)"getLastModifiedTime(...)");
            collection.add(new ProfileInfo(string, (Path)path, fileTime.toMillis(), settingCount));
        }
        Iterable $this$sortedByDescending$iv = (List)destination$iv$iv;
        boolean $i$f$sortedByDescending = false;
        return CollectionsKt.sortedWith((Iterable)$this$sortedByDescending$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                ProfileInfo it = (ProfileInfo)b;
                boolean bl = false;
                Comparable comparable = Long.valueOf(it.getLastModified());
                it = (ProfileInfo)a;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)Long.valueOf(it.getLastModified()));
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean saveProfile(@NotNull String name) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        try {
            CharSequence charSequence = name;
            Object object = new Regex("[^a-zA-Z0-9_-]");
            Object object2 = "_";
            String sanitizedName = object.replace(charSequence, (String)object2);
            Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(profilesDir, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                FileAttribute[] fileAttributeArray = new FileAttribute[]{};
                Intrinsics.checkNotNullExpressionValue((Object)Files.createDirectories(profilesDir, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length)), (String)"createDirectories(...)");
            }
            object = Files.newBufferedWriter(profileFile, new OpenOption[0]);
            object2 = null;
            try {
                BufferedWriter writer = (BufferedWriter)object;
                boolean bl2 = false;
                writer.write("# CB Profile: " + name + "\n");
                writer.write("# Created: " + Instant.now() + "\n");
                writer.write("\n");
                Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    SettingsTab tab = (SettingsTab)element$iv;
                    boolean bl3 = false;
                    writer.write("## " + tab.getName() + "\n");
                    Iterable $this$forEach$iv2 = tab.getSettings();
                    boolean $i$f$forEach2 = false;
                    for (Object element$iv2 : $this$forEach$iv2) {
                        JsonElement element;
                        Setting setting = (Setting)element$iv2;
                        boolean bl4 = false;
                        JsonObject obj = new JsonObject();
                        setting.writeJson(obj);
                        if (obj.get(setting.getName()) == null) continue;
                        writer.write(tab.getName() + "." + setting.getName() + ":" + element + "\n");
                    }
                    writer.write("\n");
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

    public final boolean loadProfile(@NotNull String name) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        try {
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string = "_";
            String sanitizedName = regex.replace(charSequence, string);
            Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
            Intrinsics.checkNotNull((Object)profileFile);
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(profileFile, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                return false;
            }
            Map settingsMap = new LinkedHashMap();
            List<String> list = Files.readAllLines(profileFile);
            Intrinsics.checkNotNullExpressionValue(list, (String)"readAllLines(...)");
            Iterable $this$forEach$iv = list;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                String value;
                String key;
                int colonIndex;
                String line = (String)element$iv;
                boolean bl2 = false;
                Intrinsics.checkNotNull((Object)line);
                if (StringsKt.startsWith$default((String)line, (String)"#", (boolean)false, (int)2, null) || StringsKt.isBlank((CharSequence)line) || (colonIndex = StringsKt.indexOf$default((CharSequence)line, (char)':', (int)0, (boolean)false, (int)6, null)) <= 0) continue;
                Intrinsics.checkNotNullExpressionValue((Object)line.substring(0, colonIndex), (String)"substring(...)");
                Intrinsics.checkNotNullExpressionValue((Object)line.substring(colonIndex + 1), (String)"substring(...)");
                settingsMap.put(key, value);
            }
            $this$forEach$iv = Tabs.INSTANCE.getAll();
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SettingsTab tab = (SettingsTab)element$iv;
                boolean bl3 = false;
                Iterable $this$forEach$iv2 = tab.getSettings();
                boolean $i$f$forEach2 = false;
                for (Object element$iv2 : $this$forEach$iv2) {
                    Setting setting = (Setting)element$iv2;
                    boolean bl4 = false;
                    String key = tab.getName() + "." + setting.getName();
                    if ((String)settingsMap.get(key) == null) continue;
                    try {
                        String valueStr;
                        JsonObject obj = new JsonObject();
                        obj.add(setting.getName(), JsonParser.parseString((String)valueStr));
                        setting.readJson(obj);
                    }
                    catch (Exception exception) {
                    }
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

    public final boolean deleteProfile(@NotNull String name) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        try {
            boolean bl2;
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string = "_";
            String sanitizedName = regex.replace(charSequence, string);
            Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
            Intrinsics.checkNotNull((Object)profileFile);
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (Files.exists(profileFile, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                Files.delete(profileFile);
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

    public final boolean renameProfile(@NotNull String oldName, @NotNull String newName) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)oldName, (String)"oldName");
        Intrinsics.checkNotNullParameter((Object)newName, (String)"newName");
        try {
            Path newFile;
            Path oldFile;
            block5: {
                block4: {
                    CharSequence charSequence = oldName;
                    Object object = new Regex("[^a-zA-Z0-9_-]");
                    String string = "_";
                    String sanitizedOld = object.replace(charSequence, string);
                    object = newName;
                    string = new Regex("[^a-zA-Z0-9_-]");
                    String string2 = "_";
                    String sanitizedNew = string.replace((CharSequence)object, string2);
                    oldFile = profilesDir.resolve(sanitizedOld + ".profile");
                    newFile = profilesDir.resolve(sanitizedNew + ".profile");
                    Intrinsics.checkNotNull((Object)oldFile);
                    LinkOption[] linkOptionArray = new LinkOption[]{};
                    if (!Files.exists(oldFile, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) break block4;
                    Intrinsics.checkNotNull((Object)newFile);
                    LinkOption[] linkOptionArray2 = new LinkOption[]{};
                    if (!Files.exists(newFile, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length))) break block5;
                }
                return false;
            }
            String content = Files.readString(oldFile);
            Intrinsics.checkNotNull((Object)content);
            String updatedContent = StringsKt.replaceFirst$default((String)content, (String)("# CB Profile: " + oldName), (String)("# CB Profile: " + newName), (boolean)false, (int)4, null);
            Files.writeString(newFile, (CharSequence)updatedContent, new OpenOption[0]);
            Files.delete(oldFile);
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    public final boolean duplicateProfile(@NotNull String sourceName, @NotNull String newName) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)sourceName, (String)"sourceName");
        Intrinsics.checkNotNullParameter((Object)newName, (String)"newName");
        try {
            Path newFile;
            Path sourceFile;
            block5: {
                block4: {
                    CharSequence charSequence = sourceName;
                    Object object = new Regex("[^a-zA-Z0-9_-]");
                    String string = "_";
                    String sanitizedSource = object.replace(charSequence, string);
                    object = newName;
                    string = new Regex("[^a-zA-Z0-9_-]");
                    String string2 = "_";
                    String sanitizedNew = string.replace((CharSequence)object, string2);
                    sourceFile = profilesDir.resolve(sanitizedSource + ".profile");
                    newFile = profilesDir.resolve(sanitizedNew + ".profile");
                    Intrinsics.checkNotNull((Object)sourceFile);
                    LinkOption[] linkOptionArray = new LinkOption[]{};
                    if (!Files.exists(sourceFile, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) break block4;
                    Intrinsics.checkNotNull((Object)newFile);
                    LinkOption[] linkOptionArray2 = new LinkOption[]{};
                    if (!Files.exists(newFile, Arrays.copyOf(linkOptionArray2, linkOptionArray2.length))) break block5;
                }
                return false;
            }
            String content = Files.readString(sourceFile);
            Intrinsics.checkNotNull((Object)content);
            String updatedContent = StringsKt.replaceFirst$default((String)content, (String)("# CB Profile: " + sourceName), (String)("# CB Profile: " + newName), (boolean)false, (int)4, null);
            Files.writeString(newFile, (CharSequence)updatedContent, new OpenOption[0]);
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    @Nullable
    public final String exportProfile(@NotNull String name) {
        String string;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        try {
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string2 = "_";
            String sanitizedName = regex.replace(charSequence, string2);
            Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
            Intrinsics.checkNotNull((Object)profileFile);
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(profileFile, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                return null;
            }
            string = Files.readString(profileFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            string = null;
        }
        return string;
    }

    public final boolean importProfile(@NotNull String name, @NotNull String content) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)content, (String)"content");
        try {
            CharSequence charSequence = name;
            Regex regex = new Regex("[^a-zA-Z0-9_-]");
            String string = "_";
            String sanitizedName = regex.replace(charSequence, string);
            Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
            LinkOption[] linkOptionArray = new LinkOption[]{};
            if (!Files.exists(profilesDir, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
                FileAttribute[] fileAttributeArray = new FileAttribute[]{};
                Intrinsics.checkNotNullExpressionValue((Object)Files.createDirectories(profilesDir, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length)), (String)"createDirectories(...)");
            }
            Files.writeString(profileFile, (CharSequence)content, new OpenOption[0]);
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    @NotNull
    public final String formatTimestamp(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm").withZone(ZoneId.systemDefault());
        String string = formatter.format(instant);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"format(...)");
        return string;
    }

    static {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("cb/profiles");
        Intrinsics.checkNotNullExpressionValue((Object)path, (String)"resolve(...)");
        profilesDir = path;
        LinkOption[] linkOptionArray = new LinkOption[]{};
        if (!Files.exists(profilesDir, Arrays.copyOf(linkOptionArray, linkOptionArray.length))) {
            FileAttribute[] fileAttributeArray = new FileAttribute[]{};
            Intrinsics.checkNotNullExpressionValue((Object)Files.createDirectories(profilesDir, Arrays.copyOf(fileAttributeArray, fileAttributeArray.length)), (String)"createDirectories(...)");
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\tH\u00c6\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0014\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u001c\u001a\u00020\tH\u00d6\u0081\u0004J\n\u0010\u001d\u001a\u00020\u0003H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, d2={"Lcom/cb/gui/ProfileManager$ProfileInfo;", "", "name", "", "path", "Ljava/nio/file/Path;", "lastModified", "", "settingCount", "", "<init>", "(Ljava/lang/String;Ljava/nio/file/Path;JI)V", "getName", "()Ljava/lang/String;", "getPath", "()Ljava/nio/file/Path;", "getLastModified", "()J", "getSettingCount", "()I", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "cb_client"})
    public static final class ProfileInfo {
        @NotNull
        private final String name;
        @NotNull
        private final Path path;
        private final long lastModified;
        private final int settingCount;

        public ProfileInfo(@NotNull String name, @NotNull Path path, long lastModified, int settingCount) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter((Object)path, (String)"path");
            this.name = name;
            this.path = path;
            this.lastModified = lastModified;
            this.settingCount = settingCount;
        }

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final Path getPath() {
            return this.path;
        }

        public final long getLastModified() {
            return this.lastModified;
        }

        public final int getSettingCount() {
            return this.settingCount;
        }

        @NotNull
        public final String component1() {
            return this.name;
        }

        @NotNull
        public final Path component2() {
            return this.path;
        }

        public final long component3() {
            return this.lastModified;
        }

        public final int component4() {
            return this.settingCount;
        }

        @NotNull
        public final ProfileInfo copy(@NotNull String name, @NotNull Path path, long lastModified, int settingCount) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter((Object)path, (String)"path");
            return new ProfileInfo(name, path, lastModified, settingCount);
        }

        public static /* synthetic */ ProfileInfo copy$default(ProfileInfo profileInfo, String string, Path path, long l, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                string = profileInfo.name;
            }
            if ((n2 & 2) != 0) {
                path = profileInfo.path;
            }
            if ((n2 & 4) != 0) {
                l = profileInfo.lastModified;
            }
            if ((n2 & 8) != 0) {
                n = profileInfo.settingCount;
            }
            return profileInfo.copy(string, path, l, n);
        }

        @NotNull
        public String toString() {
            return "ProfileInfo(name=" + this.name + ", path=" + this.path + ", lastModified=" + this.lastModified + ", settingCount=" + this.settingCount + ")";
        }

        public int hashCode() {
            int result = this.name.hashCode();
            result = result * 31 + ((Object)this.path).hashCode();
            result = result * 31 + Long.hashCode(this.lastModified);
            result = result * 31 + Integer.hashCode(this.settingCount);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ProfileInfo)) {
                return false;
            }
            ProfileInfo profileInfo = (ProfileInfo)other;
            if (!Intrinsics.areEqual((Object)this.name, (Object)profileInfo.name)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.path, (Object)profileInfo.path)) {
                return false;
            }
            if (this.lastModified != profileInfo.lastModified) {
                return false;
            }
            return this.settingCount == profileInfo.settingCount;
        }
    }
}


