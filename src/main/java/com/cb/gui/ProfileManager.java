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
      Path var10000 = profilesDir;
      LinkOption[] var10001 = new LinkOption[0];
      if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
         return CollectionsKt.emptyList();
      }

      Iterable $this$sortedByDescending$iv;
      try {
         $this$sortedByDescending$iv = PathsKt.listDirectoryEntries(profilesDir, "*.profile");
      } catch (IOException e) {
         throw new UncheckedIOException(e);
      }
      int $i$f$map = 0;
      Iterable $this$mapTo$iv$iv = $this$sortedByDescending$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$sortedByDescending$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : $this$mapTo$iv$iv) {
         Path path = (Path)item$iv$iv;
         Collection var18 = destination$iv$iv;
         int var9/* $VF was: $i$a$-map-ProfileManager$listProfiles$1 */ = 0;

         int $this$count$iv;
         try {
            List var23 = Files.readAllLines(path);
            Intrinsics.checkNotNullExpressionValue(var23, "readAllLines(...)");
            var $this$count$ivx = var23;
            int $i$f$count = 0;
            int var24;
            if ($this$count$ivx instanceof Collection && ((Collection)$this$count$ivx).isEmpty()) {
               var24 = 0;
            } else {
               int count$iv = 0;

               for (Object element$iv : $this$count$ivx) {
                  String it = (String)element$iv;
                  int var16/* $VF was: $i$a$-count-ProfileManager$listProfiles$1$settingCount$1 */ = 0;
                  Intrinsics.checkNotNull(it);
                  if (StringsKt.contains(it, ":", false)) {
                     if (++count$iv < 0) {
                        CollectionsKt.throwCountOverflow();
                     }
                  }
               }

               var24 = count$iv;
            }

            $this$count$iv = var24;
         } catch (Exception e) {
            $this$count$iv = 0;
         }

         int settingCount = $this$count$iv;
         String var10002 = PathsKt.getNameWithoutExtension(path);
         LinkOption[] var10005 = new LinkOption[0];
         FileTime var10004;
         try {
            var10004 = Files.getLastModifiedTime(path, Arrays.copyOf(var10005, var10005.length));
         } catch (IOException e) {
            throw new UncheckedIOException(e);
         }
         Intrinsics.checkNotNullExpressionValue(var10004, "getLastModifiedTime(...)");
         var18.add(new ProfileManager.ProfileInfo(var10002, path, var10004.toMillis(), settingCount));
      }

      $this$sortedByDescending$iv = (Iterable & List)destination$iv$iv;
      $i$f$map = 0;
      return CollectionsKt.sortedWith($this$sortedByDescending$iv, new ProfileManager$listProfiles$$inlined$sortedByDescending$1<>());
   }

   public final boolean saveProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");

      boolean sanitizedName;
      try {
         CharSequence e = name;
         Regex var4 = new Regex("[^a-zA-Z0-9_-]");
         String var5 = "_";
         String sanitizedNamex = var4.replace(e, var5);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Path var10000 = profilesDir;
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            var10000 = profilesDir;
            FileAttribute[] var35 = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(var10000, Arrays.copyOf(var35, var35.length)), "createDirectories(...)");
         }

         var var30 = Files.newBufferedWriter(profileFile);
         Throwable var31 = null;

         try {
            BufferedWriter writer = var30;
            int var7/* $VF was: $i$a$-use-ProfileManager$saveProfile$1 */ = 0;
            writer.write("# CB Profile: " + name + "\n");
            writer.write("# Created: " + Instant.now() + "\n");
            writer.write("\n");
            Iterable $this$forEach$iv = Tabs.INSTANCE.getAll();
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               SettingsTab tab = (SettingsTab)element$iv;
               int var13/* $VF was: $i$a$-forEach-ProfileManager$saveProfile$1$1 */ = 0;
               writer.write("## " + tab.getName() + "\n");
               Iterable $this$forEach$ivx = tab.getSettings();
               int $i$f$forEachx = 0;

               for (Object element$ivx : $this$forEach$ivx) {
                  Setting setting = (Setting)element$ivx;
                  int var19/* $VF was: $i$a$-forEach-ProfileManager$saveProfile$1$1$1 */ = 0;
                  JsonObject obj = new JsonObject();
                  setting.writeJson(obj);
                  JsonElement var34 = obj.get(setting.getName());
                  if (var34 != null) {
                     JsonElement element = var34;
                     writer.write(tab.getName() + "." + setting.getName() + ":" + element + "\n");
                  }
               }

               writer.write("\n");
            }

            Unit var32 = Unit.INSTANCE;
         } catch (Throwable var25) {
            var31 = var25;
            throw var25;
         } finally {
            CloseableKt.closeFinally(var30, var31);
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
         String $this$forEach$iv = "_";
         String sanitizedNamex = settingsMap.replace(e, $this$forEach$iv);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(profileFile, Arrays.copyOf(var10001, var10001.length))) {
            return false;
         }

         Map settingsMapx = new LinkedHashMap();
         List var10000 = Files.readAllLines(profileFile);
         Intrinsics.checkNotNullExpressionValue(var10000, "readAllLines(...)");
         Iterable $this$forEach$ivx = var10000;
         int $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$ivx) {
            String line = (String)element$iv;
            int var10/* $VF was: $i$a$-forEach-ProfileManager$loadProfile$1 */ = 0;
            Intrinsics.checkNotNull(line);
            if (!StringsKt.startsWith(line, "#", false) && !StringsKt.isBlank(line)) {
               int colonIndex = StringsKt.indexOf(line, ':', 0, false);
               if (colonIndex > 0) {
                  String var35 = line.substring(0, colonIndex);
                  Intrinsics.checkNotNullExpressionValue(var35, "substring(...)");
                  String key = var35;
                  String var36 = line.substring(colonIndex + 1);
                  Intrinsics.checkNotNullExpressionValue(var36, "substring(...)");
                  String value = var36;
                  settingsMapx.put(key, value);
               }
            }
         }

         Iterable $this$forEach$ivxx = Tabs.INSTANCE.getAll();
         $i$f$forEach = 0;

         for (Object element$iv : $this$forEach$ivxx) {
            SettingsTab tab = (SettingsTab)element$iv;
            int var31/* $VF was: $i$a$-forEach-ProfileManager$loadProfile$2 */ = 0;
            Iterable $this$forEach$ivxxx = tab.getSettings();
            int $i$f$forEachx = 0;

            for (Object element$ivx : $this$forEach$ivxxx) {
               Setting setting = (Setting)element$ivx;
               int var16/* $VF was: $i$a$-forEach-ProfileManager$loadProfile$2$1 */ = 0;
               String key = tab.getName() + "." + setting.getName();
               String var37 = (String)settingsMapx.get(key);
               if (var37 != null) {
                  String valueStr = var37;

                  try {
                     JsonObject obj = new JsonObject();
                     obj.add(setting.getName(), JsonParser.parseString(valueStr));
                     setting.readJson(obj);
                  } catch (Exception var20) {
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
         Regex var4 = new Regex("[^a-zA-Z0-9_-]");
         String var5 = "_";
         String sanitizedNamex = var4.replace(e, var5);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] var10001 = new LinkOption[0];
         boolean var10000;
         if (Files.exists(profileFile, Arrays.copyOf(var10001, var10001.length))) {
            Files.delete(profileFile);
            var10000 = true;
         } else {
            var10000 = false;
         }

         sanitizedName = var10000;
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
         CharSequence var12 = newName;
         Regex var14 = new Regex("[^a-zA-Z0-9_-]");
         String content = "_";
         sanitizedNew = var14.replace(var12, content);
         Path oldFilex = profilesDir.resolve(sanitizedOld + ".profile");
         Path newFilex = profilesDir.resolve(sanitizedNew + ".profile");
         Intrinsics.checkNotNull(oldFilex);
         LinkOption[] var10001 = new LinkOption[0];
         if (Files.exists(oldFilex, Arrays.copyOf(var10001, var10001.length))) {
            Intrinsics.checkNotNull(newFilex);
            var10001 = new LinkOption[0];
            if (!Files.exists(newFilex, Arrays.copyOf(var10001, var10001.length))) {
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
         CharSequence var12 = newName;
         Regex var14 = new Regex("[^a-zA-Z0-9_-]");
         String content = "_";
         sanitizedNew = var14.replace(var12, content);
         Path sourceFilex = profilesDir.resolve(sanitizedSource + ".profile");
         Path newFilex = profilesDir.resolve(sanitizedNew + ".profile");
         Intrinsics.checkNotNull(sourceFilex);
         LinkOption[] var10001 = new LinkOption[0];
         if (Files.exists(sourceFilex, Arrays.copyOf(var10001, var10001.length))) {
            Intrinsics.checkNotNull(newFilex);
            var10001 = new LinkOption[0];
            if (!Files.exists(newFilex, Arrays.copyOf(var10001, var10001.length))) {
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
         Regex var4 = new Regex("[^a-zA-Z0-9_-]");
         String var5 = "_";
         sanitizedName = var4.replace(e, var5);
         Path profileFile = profilesDir.resolve(sanitizedName + ".profile");
         Intrinsics.checkNotNull(profileFile);
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(profileFile, Arrays.copyOf(var10001, var10001.length))) {
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
         Regex var5 = new Regex("[^a-zA-Z0-9_-]");
         String var6 = "_";
         String sanitizedNamex = var5.replace(e, var6);
         Path profileFile = profilesDir.resolve(sanitizedNamex + ".profile");
         Path var10000 = profilesDir;
         LinkOption[] var10001 = new LinkOption[0];
         if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
            var10000 = profilesDir;
            FileAttribute[] var11 = new FileAttribute[0];
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(var10000, Arrays.copyOf(var11, var11.length)), "createDirectories(...)");
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
      String var10000 = formatter.format(instant);
      Intrinsics.checkNotNullExpressionValue(var10000, "format(...)");
      return var10000;
   }

   static {
      Path var10000 = FabricLoader.getInstance().getConfigDir().resolve("cb/profiles");
      Intrinsics.checkNotNullExpressionValue(var10000, "resolve(...)");
      profilesDir = var10000;
      var10000 = profilesDir;
      LinkOption[] var10001 = new LinkOption[0];
      if (!Files.exists(var10000, Arrays.copyOf(var10001, var10001.length))) {
         var10000 = profilesDir;
         FileAttribute[] var2 = new FileAttribute[0];
         try {
            Intrinsics.checkNotNullExpressionValue(Files.createDirectories(var10000, Arrays.copyOf(var2, var2.length)), "createDirectories(...)");
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
         } else if (!(other instanceof ProfileManager.ProfileInfo var2)) {
            return false;
         } else if (!Intrinsics.areEqual(this.name, var2.name)) {
            return false;
         } else if (!Intrinsics.areEqual(this.path, var2.path)) {
            return false;
         } else {
            return this.lastModified != var2.lastModified ? false : this.settingCount == var2.settingCount;
         }
      }
   }
}
