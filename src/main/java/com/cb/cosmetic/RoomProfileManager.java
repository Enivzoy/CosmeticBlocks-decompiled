package com.cb.cosmetic;

import com.cb.config.Config;
import com.cb.config.tabs.RoomProfilesTab;
import com.cb.dungeon.RoomScanner;
import com.cb.utils.ChatUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class RoomProfileManager {
   @NotNull
   public static final RoomProfileManager INSTANCE = new RoomProfileManager();
   @NotNull
   private static final File profilesDir = ReplacementStore.INSTANCE.getConfigDir$cb_client();
   @NotNull
   private static final String PROFILE_EXT = ".roomprofile";
   @NotNull
   private static final String DEFAULT_PROFILE = "default";
   @Nullable
   private static String lastActive;
   private static boolean initialized;

   private RoomProfileManager() {
   }

   @NotNull
   public final File getProfilesDir$cb_client() {
      return profilesDir;
   }

   public final void init() {
      if (!profilesDir.exists()) {
         profilesDir.mkdirs();
      }
      if (this.profileFiles().length == 0) {
         this.writeProfileFile(DEFAULT_PROFILE);
      }
   }

   public final void checkSwitch() {
      String current = this.currentActive();
      if (!initialized) {
         initialized = true;
         lastActive = current;
         return;
      }
      if (current != null && !Intrinsics.areEqual(current, lastActive)) {
         this.switchTo(current);
      }
   }

   @NotNull
   public final List<String> listProfiles() {
      List<String> names = new ArrayList<>();
      for (File file : this.profileFiles()) {
         names.add(FilesKt.getNameWithoutExtension(file));
      }
      if (names.isEmpty()) {
         return CollectionsKt.listOf(DEFAULT_PROFILE);
      }
      return CollectionsKt.sorted(names);
   }

   public final boolean createProfile(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String trimmed = this.sanitize(name);
      if (trimmed == null) {
         return false;
      }
      if (this.profileFile(trimmed).exists()) {
         return false;
      }
      this.writeProfileFile(trimmed);
      return true;
   }

   public final boolean saveAs(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String trimmed = this.sanitize(name);
      if (trimmed == null) {
         ChatUtil.INSTANCE.error("Invalid name. Use letters, numbers, spaces, _ or - (max 32).");
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      if (!this.writeProfileFile(trimmed)) {
         ChatUtil.INSTANCE.error("Failed to save room profile " + trimmed + ".");
         return false;
      }

      this.setActive(trimmed);
      lastActive = trimmed;
      Config.INSTANCE.save();
      ChatUtil.INSTANCE.success("Saved room profile " + trimmed + ".");
      return true;
   }

   public final boolean overwriteActive() {
      String name = this.currentActive();
      if (name == null || name.isEmpty()) {
         ChatUtil.INSTANCE.error("No active room profile.");
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      if (!this.writeProfileFile(name)) {
         ChatUtil.INSTANCE.error("Failed to save room profile " + name + ".");
         return false;
      }

      lastActive = name;
      Config.INSTANCE.save();
      ChatUtil.INSTANCE.success("Updated room profile " + name + ".");
      return true;
   }

   public final boolean deleteActive() {
      String name = this.currentActive();
      if (name == null || name.isEmpty()) {
         ChatUtil.INSTANCE.error("No active room profile.");
         return false;
      }
      if (this.profileFiles().length <= 1) {
         ChatUtil.INSTANCE.error("Cannot delete the only room profile.");
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      File file = this.profileFile(name);
      if (!file.exists() || !file.delete()) {
         ChatUtil.INSTANCE.error("Failed to delete room profile " + name + ".");
         return false;
      }

      List<String> remaining = this.listProfiles();
      String next = remaining.isEmpty() ? DEFAULT_PROFILE : remaining.get(0);
      if (!this.loadProfileInto(next)) {
         RoomStore.INSTANCE.getRoomBlocks$cb_client().clear();
      }
      this.reapplyCurrentRoom();
      this.setActive(next);
      lastActive = next;
      Config.INSTANCE.save();
      ChatUtil.INSTANCE.success("Deleted room profile " + name + ". Now active: " + next + ".");
      return true;
   }

   public final boolean loadByName(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      String trimmed = this.sanitize(name);
      if (trimmed == null || Intrinsics.areEqual(trimmed, this.currentActive())) {
         return false;
      }
      this.switchTo(trimmed);
      return true;
   }

   private final void switchTo(String name) {
      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      if (lastActive != null && !lastActive.isEmpty() && !Intrinsics.areEqual(lastActive, name)) {
         this.writeProfileFile(lastActive);
      }
      if (!this.loadProfileInto(name)) {
         RoomStore.INSTANCE.getRoomBlocks$cb_client().clear();
      }
      this.reapplyCurrentRoom();
      lastActive = name;
      Config.INSTANCE.save();
      ChatUtil.INSTANCE.info("Loaded room profile " + name + ".");
   }

   private final void reapplyCurrentRoom() {
      RoomStore.INSTANCE.resolveAllRoomBlocks();
      RoomStore.INSTANCE.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      RoomStore.INSTANCE.forceRoomSave();
   }

   private final boolean loadProfileInto(String name) {
      File file = this.profileFile(name);
      if (!file.exists()) {
         return false;
      }

      Map<String, Map<String, String>> target = RoomStore.INSTANCE.getRoomBlocks$cb_client();
      target.clear();
      try {
         String content = FilesKt.readText(file, StandardCharsets.UTF_8);
         if (!StringsKt.isBlank(content)) {
            JsonObject root = JsonParser.parseString(content).getAsJsonObject();
            for (Entry<String, JsonElement> entry : root.entrySet()) {
               Map<String, String> roomMap = new LinkedHashMap<>();
               JsonObject roomObj = entry.getValue().getAsJsonObject();
               for (Entry<String, JsonElement> posEntry : roomObj.entrySet()) {
                  roomMap.put(posEntry.getKey(), posEntry.getValue().getAsString());
               }
               target.put(entry.getKey(), roomMap);
            }
         }
         return true;
      } catch (Exception ex) {
         System.err.println("[CB] Failed to parse room profile " + name + ": " + ex.getMessage());
         return false;
      }
   }

   private final boolean writeProfileFile(String name) {
      File file = this.profileFile(name);
      try {
         Map<String, Map<String, String>> snapshot = new LinkedHashMap<>();
         for (Entry<String, Map<String, String>> entry : RoomStore.INSTANCE.getRoomBlocks$cb_client().entrySet()) {
            snapshot.put(entry.getKey(), new LinkedHashMap<>(entry.getValue()));
         }
         this.writeJson(file, snapshot);
         return true;
      } catch (Exception ex) {
         System.err.println("[CB] Failed to write room profile " + name + ": " + ex.getMessage());
         return false;
      }
   }

   private final void writeJson(File file, Map<String, Map<String, String>> snapshot) throws IOException {
      File parent = file.getParentFile();
      if (parent != null) {
         parent.mkdirs();
      }

      File tmpFile = new File(parent, file.getName() + ".tmp");
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile), 65536)) {
         JsonWriter jw = new JsonWriter(writer);
         jw.setIndent("  ");
         jw.beginObject();
         for (Entry<String, Map<String, String>> entry : snapshot.entrySet()) {
            jw.name(entry.getKey());
            jw.beginObject();
            for (Entry<String, String> posEntry : entry.getValue().entrySet()) {
               jw.name(posEntry.getKey()).value(posEntry.getValue());
            }
            jw.endObject();
         }
         jw.endObject();
         jw.flush();
      }

      if (file.exists()) {
         file.delete();
      }
      tmpFile.renameTo(file);
   }

   @NotNull
   private final File[] profileFiles() {
      if (!profilesDir.exists()) {
         return new File[0];
      }
      File[] files = profilesDir.listFiles((dir, fileName) -> fileName.endsWith(PROFILE_EXT));
      return files != null ? files : new File[0];
   }

   @NotNull
   private final File profileFile(String name) {
      return new File(profilesDir, name + PROFILE_EXT);
   }

   @Nullable
   private final String currentActive() {
      return RoomProfilesTab.INSTANCE.getActiveProfile().getValue();
   }

   private final void setActive(String name) {
      RoomProfilesTab.INSTANCE.getActiveProfile().setValue(name);
   }

   @Nullable
   private final String sanitize(String name) {
      String trimmed = name.trim();
      if (trimmed.isEmpty() || trimmed.length() > 32 || !trimmed.matches("[A-Za-z0-9 _-]+")) {
         return null;
      }
      return trimmed;
   }
}
