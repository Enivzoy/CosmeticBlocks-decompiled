package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.setting.Setting;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.path.PathsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0016\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fJ\u000e\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0019R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b",
   d2 = {
         "Lcom/cb/gui/ProfileManager;",
         "",
         "<init>",
         "()V",
         "profilesDir",
         "Ljava/nio/file/Path;",
         "listProfiles",
         "",
         "Lcom/cb/gui/ProfileManager$ProfileInfo;",
         "saveProfile",
         "",
         "name",
         "",
         "loadProfile",
         "deleteProfile",
         "renameProfile",
         "oldName",
         "newName",
         "duplicateProfile",
         "sourceName",
         "exportProfile",
         "importProfile",
         "content",
         "formatTimestamp",
         "millis",
         "",
         "ProfileInfo",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nProfileManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileManager.kt\ncom/cb/gui/ProfileManager\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,236:1\n1586#2:237\n1661#2,2:238\n1834#2,4:240\n1663#2:244\n1080#2:245\n1915#2:246\n1915#2,2:247\n1916#2:249\n1915#2,2:250\n1915#2:252\n1915#2,2:253\n1916#2:255\n*S KotlinDebug\n*F\n+ 1 ProfileManager.kt\ncom/cb/gui/ProfileManager\n*L\n35#1:237\n35#1:238,2\n37#1:240,4\n35#1:244\n47#1:245\n69#1:246\n71#1:247,2\n69#1:249\n96#1:250,2\n109#1:252\n110#1:253,2\n109#1:255\n*E\n"
)
public final class ProfileManager {
   @NotNull
   public static final ProfileManager INSTANCE = new ProfileManager();
   @NotNull
   private static final Path profilesDir;

   private ProfileManager() {
   }

   @NotNull
   public final List<ProfileManager.ProfileInfo> listProfiles() {
      Path dir = profilesDir;
      LinkOption[] linkOptions = new LinkOption[0];
      if (!Files.exists(dir, Arrays.copyOf(linkOptions, linkOptions.length))) {
         return CollectionsKt.emptyList();
      }

      Iterable entries;
      try {
         entries = PathsKt.listDirectoryEntries(profilesDir, "*.profile");
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
      Iterable mapSource = entries;
      var destination = new ArrayList(CollectionsKt.collectionSizeOrDefault(entries, 10));

      for (Object item : mapSource) {
         Path path = (Path)item;
         Collection output = destination;
         int mapLambda = 0;

         int countedSettings;
         try {
            List lines = Files.readAllLines(path);
            Intrinsics.checkNotNullExpressionValue(lines, "readAllLines(...)");
            var profileLines = lines;
            int countResult;
            if (profileLines instanceof Collection && ((Collection)profileLines).isEmpty()) {
               countResult = 0;
            } else {
               int count = 0;

               for (Object element : profileLines) {
                  String it = (String)element;
                  int countLambda = 0;
                  Intrinsics.checkNotNull(it);
                  if (StringsKt.contains(it, ":", false)) {
                     if (++count < 0) {
                        CollectionsKt.throwCountOverflow();
                     }
                  }
               }

               countResult = count;
            }

            countedSettings = countResult;
         } catch (Exception e) {
            countedSettings = 0;
         }

         int settingCount = countedSettings;
         String profileName = PathsKt.getNameWithoutExtension(path);
         LinkOption[] lastModifiedLinkOptions = new LinkOption[0];
         FileTime lastModified;
         try {
            lastModified = Files.getLastModifiedTime(path, Arrays.copyOf(lastModifiedLinkOptions, lastModifiedLinkOptions.length));
         } catch (IOException e) {
            throw new UncheckedIOException(e);
         }
         Intrinsics.checkNotNullExpressionValue(lastModified, "getLastModifiedTime(...)");
         output.add(new ProfileManager.ProfileInfo(profileName, path, lastModified.toMillis(), settingCount));
      }

      entries = (Iterable & List)destination;
      return CollectionsKt.sortedWith(entries, new ProfileManager$listProfiles$$inlined$sortedByDescending$1<>());
   }

   public final boolean saveProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");

      boolean sanitizedName;
      try {
         CharSequence e = name;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String replacement = "_";
         String sanitizedNamex = sanitizePattern.replace(e, replacement);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Path dir = profilesDir;
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(dir, Arrays.copyOf(linkOptions, linkOptions.length))) {
            dir = profilesDir;
            FileAttribute[] fileAttributes = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(dir, Arrays.copyOf(fileAttributes, fileAttributes.length)), "createDirectories(...)");
         }

         var bufferedWriter = Files.newBufferedWriter(profileFile);
         Throwable throwable = null;

         try {
            BufferedWriter writer = bufferedWriter;
            int useLambda = 0;
            writer.write("# CB Profile: " + name + "\n");
            writer.write("# Created: " + Instant.now() + "\n");
            writer.write("\n");
            Iterable tabs = Tabs.INSTANCE.getAll();

            for (Object tabElement : tabs) {
               SettingsTab tab = (SettingsTab)tabElement;
               int forEachLambda = 0;
               writer.write("## " + tab.getName() + "\n");
               Iterable settings = tab.getSettings();

               for (Object settingElement : settings) {
                  Setting setting = (Setting)settingElement;
                  int forEachLambdaInner = 0;
                  JsonObject obj = new JsonObject();
                  setting.writeJson(obj);
                  JsonElement element = obj.get(setting.getName());
                  if (element != null) {
                     writer.write(tab.getName() + "." + setting.getName() + ":" + element + "\n");
                  }
               }

               writer.write("\n");
            }

            Unit unit = Unit.INSTANCE;
         } catch (Throwable cause) {
            throwable = cause;
            throw cause;
         } finally {
            CloseableKt.closeFinally(bufferedWriter, throwable);
         }

         sanitizedName = true;
      } catch (Exception e) {
         e.printStackTrace();
         sanitizedName = false;
      }

      return sanitizedName;
   }

   public final boolean loadProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");

      boolean sanitizedName;
      try {
         CharSequence e = name;
         Regex settingsMap = new Regex("[^a-zA-Z0-9_-]");
         String replacement = "_";
         String sanitizedNamex = settingsMap.replace(e, replacement);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(profileFile, Arrays.copyOf(linkOptions, linkOptions.length))) {
            return false;
         }

         Map settingsMapx = new LinkedHashMap();
         List lines = Files.readAllLines(profileFile);
         Intrinsics.checkNotNullExpressionValue(lines, "readAllLines(...)");
         Iterable fileLines = lines;

         for (Object element : fileLines) {
            String line = (String)element;
            int forEachLambda = 0;
            Intrinsics.checkNotNull(line);
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               int colonIndex = StringsKt.indexOf(line, ':', 0, false);
               if (colonIndex > 0) {
                  String keyPart = line.substring(0, colonIndex);
                  Intrinsics.checkNotNullExpressionValue(keyPart, "substring(...)");
                  String key = keyPart;
                  String valuePart = line.substring(colonIndex + 1);
                  Intrinsics.checkNotNullExpressionValue(valuePart, "substring(...)");
                  String value = valuePart;
                  settingsMapx.put(key, value);
               }
            }
         }

         Iterable tabs = Tabs.INSTANCE.getAll();

         for (Object tabElement : tabs) {
            SettingsTab tab = (SettingsTab)tabElement;
            int forEachLambda2 = 0;
            Iterable settings = tab.getSettings();

            for (Object settingElement : settings) {
               Setting setting = (Setting)settingElement;
               int forEachLambda3 = 0;
               String key = tab.getName() + "." + setting.getName();
               String valueStr = (String)settingsMapx.get(key);
               if (valueStr != null) {
                  try {
                     JsonObject obj = new JsonObject();
                     obj.add(setting.getName(), JsonParser.parseString(valueStr));
                     setting.readJson(obj);
                  } catch (Exception ignored) {
                  }
               }
            }
         }

         Config.INSTANCE.save();
         sanitizedName = true;
      } catch (Exception e) {
         e.printStackTrace();
         sanitizedName = false;
      }

      return sanitizedName;
   }

   public final boolean deleteProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");

      boolean sanitizedName;
      try {
         CharSequence e = name;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String replacement = "_";
         String sanitizedNamex = sanitizePattern.replace(e, replacement);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] linkOptions = new LinkOption[0];
         boolean deleted;
         if (Files.exists(profileFile, Arrays.copyOf(linkOptions, linkOptions.length))) {
            Files.delete(profileFile);
            deleted = true;
         } else {
            deleted = false;
         }

         sanitizedName = deleted;
      } catch (Exception e) {
         e.printStackTrace();
         sanitizedName = false;
      }

      return sanitizedName;
   }

   public final boolean renameProfile(@NotNull String oldName, @NotNull String newName) {
      Intrinsics.checkNotNullParameter(oldName, "oldName");
      Intrinsics.checkNotNullParameter(newName, "newName");

      try {
         String sanitizedNew = oldName;
         Regex oldFile = new Regex("[^a-zA-Z0-9_-]");
         String newFile = "_";
         String sanitizedOld = oldFile.replace(sanitizedNew, newFile);
         CharSequence newNameCharSeq = newName;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String content = "_";
         sanitizedNew = sanitizePattern.replace(newNameCharSeq, content);
         Path oldFilex = profilesDir.resolve(sanitizedOld + ".profile");
         Path newFilex = profilesDir.resolve(sanitizedNew + ".profile");
         Intrinsics.checkNotNull(oldFilex);
         LinkOption[] linkOptions = new LinkOption[0];
         if (Files.exists(oldFilex, Arrays.copyOf(linkOptions, linkOptions.length))) {
            Intrinsics.checkNotNull(newFilex);
            linkOptions = new LinkOption[0];
            if (!Files.exists(newFilex, Arrays.copyOf(linkOptions, linkOptions.length))) {
               content = Files.readString(oldFilex);
               Intrinsics.checkNotNull(content);
               String updatedContent = StringsKt.replaceFirst(content, "# CB Profile: " + oldName, "# CB Profile: " + newName, false);
               Files.writeString(newFilex, updatedContent);
               Files.delete(oldFilex);
               return true;
            }
         }

         return false;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   public final boolean duplicateProfile(@NotNull String sourceName, @NotNull String newName) {
      Intrinsics.checkNotNullParameter(sourceName, "sourceName");
      Intrinsics.checkNotNullParameter(newName, "newName");

      try {
         String sanitizedNew = sourceName;
         Regex sourceFile = new Regex("[^a-zA-Z0-9_-]");
         String newFile = "_";
         String sanitizedSource = sourceFile.replace(sanitizedNew, newFile);
         CharSequence newNameCharSeq = newName;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String content = "_";
         sanitizedNew = sanitizePattern.replace(newNameCharSeq, content);
         Path sourceFilex = profilesDir.resolve(sanitizedSource + ".profile");
         Path newFilex = profilesDir.resolve(sanitizedNew + ".profile");
         Intrinsics.checkNotNull(sourceFilex);
         LinkOption[] linkOptions = new LinkOption[0];
         if (Files.exists(sourceFilex, Arrays.copyOf(linkOptions, linkOptions.length))) {
            Intrinsics.checkNotNull(newFilex);
            linkOptions = new LinkOption[0];
            if (!Files.exists(newFilex, Arrays.copyOf(linkOptions, linkOptions.length))) {
               content = Files.readString(sourceFilex);
               Intrinsics.checkNotNull(content);
               String updatedContent = StringsKt.replaceFirst(content, "# CB Profile: " + sourceName, "# CB Profile: " + newName, false);
               Files.writeString(newFilex, updatedContent);
               return true;
            }
         }

         return false;
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      }
   }

   @Nullable
   public final String exportProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");

      String sanitizedName;
      try {
         CharSequence e = name;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String replacement = "_";
         sanitizedName = sanitizePattern.replace(e, replacement);
         Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(profileFile, Arrays.copyOf(linkOptions, linkOptions.length))) {
            return null;
         }

         sanitizedName = Files.readString(profileFile);
      } catch (Exception e) {
         e.printStackTrace();
         sanitizedName = null;
      }

      return sanitizedName;
   }

   public final boolean importProfile(@NotNull String name, @NotNull String content) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(content, "content");

      boolean sanitizedName;
      try {
         CharSequence e = name;
         Regex sanitizePattern = new Regex("[^a-zA-Z0-9_-]");
         String replacement = "_";
         String sanitizedNamex = sanitizePattern.replace(e, replacement);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Path dir = profilesDir;
         LinkOption[] linkOptions = new LinkOption[0];
         if (!Files.exists(dir, Arrays.copyOf(linkOptions, linkOptions.length))) {
            dir = profilesDir;
            FileAttribute[] fileAttributes = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(dir, Arrays.copyOf(fileAttributes, fileAttributes.length)), "createDirectories(...)");
         }

         Files.writeString(profileFile, content);
         sanitizedName = true;
      } catch (Exception e) {
         e.printStackTrace();
         sanitizedName = false;
      }

      return sanitizedName;
   }

   @NotNull
   public final String formatTimestamp(long millis) {
      Instant instant = Instant.ofEpochMilli(millis);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm").withZone(ZoneId.systemDefault());
      String timestamp = formatter.format(instant);
      Intrinsics.checkNotNullExpressionValue(timestamp, "format(...)");
      return timestamp;
   }

   static {
      Path dir = FabricLoader.getInstance().getConfigDir().resolve("cb/profiles");
      Intrinsics.checkNotNullExpressionValue(dir, "resolve(...)");
      profilesDir = dir;
      dir = profilesDir;
      LinkOption[] linkOptions = new LinkOption[0];
      if (!Files.exists(dir, Arrays.copyOf(linkOptions, linkOptions.length))) {
         dir = profilesDir;
         FileAttribute[] fileAttributes = new FileAttribute[0];
         try {
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(dir, Arrays.copyOf(fileAttributes, fileAttributes.length)), "createDirectories(...)");
         } catch (IOException e) {
            throw new UncheckedIOException(e);
         }
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0017\u001a\u00020\tHÆ\u0003J1\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0014\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u001c\u001a\u00020\tHÖ\u0081\u0004J\n\u0010\u001d\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001e",
      d2 = {
            "Lcom/cb/gui/ProfileManager$ProfileInfo;",
            "",
            "name",
            "",
            "path",
            "Ljava/nio/file/Path;",
            "lastModified",
            "",
            "settingCount",
            "",
            "<init>",
            "(Ljava/lang/String;Ljava/nio/file/Path;JI)V",
            "getName",
            "()Ljava/lang/String;",
            "getPath",
            "()Ljava/nio/file/Path;",
            "getLastModified",
            "()J",
            "getSettingCount",
            "()I",
            "component1",
            "component2",
            "component3",
            "component4",
            "copy",
            "equals",
            "",
            "other",
            "hashCode",
            "toString",
            "cb_client"
      }
   )
   public static final class ProfileInfo {
      @NotNull
      private final String name;
      @NotNull
      private final Path path;
      private final long lastModified;
      private final int settingCount;

      public ProfileInfo(@NotNull String name, @NotNull Path path, long lastModified, int settingCount) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(path, "path");
         super();
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
      public final ProfileManager.ProfileInfo copy(@NotNull String name, @NotNull Path path, long lastModified, int settingCount) {
         Intrinsics.checkNotNullParameter(name, "name");
         Intrinsics.checkNotNullParameter(path, "path");
         return new ProfileManager.ProfileInfo(name, path, lastModified, settingCount);
      }

      @NotNull
      @Override
      public String toString() {
         return "ProfileInfo(name=" + this.name + ", path=" + this.path + ", lastModified=" + this.lastModified + ", settingCount=" + this.settingCount + ")";
      }

      @Override
      public int hashCode() {
         int result = this.name.hashCode();
         result = result * 31 + this.path.hashCode();
         result = result * 31 + Long.hashCode(this.lastModified);
         return result * 31 + Integer.hashCode(this.settingCount);
      }

      @Override
      public boolean equals(@Nullable Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ProfileManager.ProfileInfo that)) {
            return false;
         } else if (!Intrinsics.areEqual(this.name, that.name)) {
            return false;
         } else if (!Intrinsics.areEqual(this.path, that.path)) {
            return false;
         } else {
            return this.lastModified != that.lastModified ? false : this.settingCount == that.settingCount;
         }
      }
   }
}
