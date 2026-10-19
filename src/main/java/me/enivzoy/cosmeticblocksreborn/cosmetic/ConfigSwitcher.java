package me.enivzoy.cosmeticblocksreborn.cosmetic;

import me.enivzoy.cosmeticblocksreborn.config.Config;
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.PreferencesTab;
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ConfigSwitcher {
   @NotNull
   public static final ConfigSwitcher INSTANCE = new ConfigSwitcher();
   @Nullable
   private static String lastActiveConfig;
   private static boolean dropdownInitialized;
   @Nullable
   private static String loadedConfigName;
   @Nullable
   private static volatile String lastDetectedFloor;
   private static boolean dungeonActive;
   @Nullable
   private static String configBeforeDungeon;

   private ConfigSwitcher() {
   }

   @Nullable
   public final String getLoadedConfigName$cb_client() {
      return loadedConfigName;
   }

   public final void setLoadedConfigName$cb_client(@Nullable String value) {
      loadedConfigName = value;
   }

   public final void checkConfigSwitch$cb_client() {
      if (!dropdownInitialized) {
         this.updateConfigDropdown();
         dropdownInitialized = true;
         lastActiveConfig = DungeonConfigTab.INSTANCE.getActiveConfig().getValue();
         if (loadedConfigName != null && !Intrinsics.areEqual(loadedConfigName, DungeonConfigTab.INSTANCE.getActiveConfig().getValue())) {
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue()));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
         }

         DungeonConfigTab.markFloorReady();
         DungeonConfigTab.syncFloorFromStore();
      } else {
         String current = DungeonConfigTab.INSTANCE.getActiveConfig().getValue();
         if (!Intrinsics.areEqual(current, lastActiveConfig)) {
            SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
            String previousConfigName = lastActiveConfig;
            if (lastActiveConfig == null) {
               previousConfigName = "default";
            }

            ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(previousConfigName));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(current));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            lastActiveConfig = current;
            DungeonConfigTab.syncFloorFromStore();
         }
      }
   }

   public final void checkFloorAutoLoad$cb_client() {
      if (!((Boolean)PreferencesTab.INSTANCE.getAutoLoadFloor().getValue()).booleanValue()) {
         lastDetectedFloor = null;
         return;
      }

      if (!LocationTracker.INSTANCE.getInDungeon()) {
         lastDetectedFloor = null;
         return;
      }

      String floor = LocationTracker.INSTANCE.getCurrentFloor();
      if (floor == null || floor.isEmpty() || floor.equals("e")) {
         return;
      }

      if (Intrinsics.areEqual(floor, lastDetectedFloor)) {
         return;
      }

      lastDetectedFloor = floor;
      String match = FloorUtil.findConfigForFloor(floor);
      if (match == null) {
         return;
      }

      if (Intrinsics.areEqual(match, DungeonConfigTab.INSTANCE.getActiveConfig().getValue())) {
         return;
      }

      if (((Boolean)PreferencesTab.INSTANCE.getShowTitleAfterLoad().getValue()).booleanValue()) {
         this.showFloorTitles(FloorUtil.display(floor), match);
      }

      if (((Boolean)PreferencesTab.INSTANCE.getLoadChatMessage().getValue()).booleanValue()) {
         ChatUtil.INSTANCE.send("Found a config for \u00a7b" + FloorUtil.display(floor) + "\u00a7f - loading \u00a7b" + match);
      }

      this.loadConfigByName(match);
   }

   public final void checkDungeonRevert$cb_client() {
      if (!((Boolean)PreferencesTab.INSTANCE.getRevertAfterCompletion().getValue()).booleanValue()) {
         dungeonActive = LocationTracker.INSTANCE.getInDungeon();
         configBeforeDungeon = null;
         return;
      }

      boolean nowInDungeon = LocationTracker.INSTANCE.getInDungeon();
      if (nowInDungeon && !dungeonActive) {
         configBeforeDungeon = DungeonConfigTab.INSTANCE.getActiveConfig().getValue();
      } else if (!nowInDungeon && dungeonActive && configBeforeDungeon != null) {
         String target = configBeforeDungeon;
         configBeforeDungeon = null;
         if (!Intrinsics.areEqual(target, DungeonConfigTab.INSTANCE.getActiveConfig().getValue())
            && ReplacementStore.INSTANCE.getConfigFile$cb_client(target).exists()) {
            if (((Boolean)PreferencesTab.INSTANCE.getLoadChatMessage().getValue()).booleanValue()) {
               ChatUtil.INSTANCE.send("Dungeon done \u00a7f- reverting to config \u00a7b" + target);
            }

            this.loadConfigByName(target);
         }
      }

      dungeonActive = nowInDungeon;
   }

   private final void showFloorTitles(String floorDisplay, String configName) {
      Minecraft minecraft = Minecraft.getInstance();
      minecraft.gui.hud.setTimes(5, 60, 10);
      minecraft.gui.hud
         .setTitle(Component.literal("Found a Config for " + floorDisplay).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(5635925))));
      minecraft.gui.hud.setSubtitle(Component.literal("Loading " + configName).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777045))));
   }

   public final boolean createConfig(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      if (StringsKt.isBlank(name)) {
         return false;
      }

      File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
      if (file.exists()) {
         return false;
      }

      if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
         ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
      }

      FilesKt.writeText(file, "{}", kotlin.text.Charsets.UTF_8);
      this.updateConfigDropdown();
      return true;
   }

   public final boolean loadConfigByName(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      if (Intrinsics.areEqual(name, DungeonConfigTab.INSTANCE.getActiveConfig().getValue())) {
         return true;
      }

      File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
      if (!file.exists()) {
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue()));
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      DungeonConfigTab.INSTANCE.getActiveConfig().setValue(name);
      lastActiveConfig = name;
      ReplacementIO.INSTANCE.loadFrom$cb_client(file);
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      DungeonConfigTab.syncFloorFromStore();
      Config.INSTANCE.save();
      return true;
   }

   public final boolean deleteConfig(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      if (Intrinsics.areEqual(name, "default")) {
         return false;
      }

      File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
      if (!file.exists()) {
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      if (Intrinsics.areEqual(DungeonConfigTab.INSTANCE.getActiveConfig().getValue(), name)) {
         SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
      }

      File remapFile = ConfigRemap.INSTANCE.fileFor(name);
      boolean deleted = file.delete();
      if (deleted) {
         if (remapFile.exists()) {
            remapFile.delete();
         }

         if (Intrinsics.areEqual(DungeonConfigTab.INSTANCE.getActiveConfig().getValue(), name)) {
            DungeonConfigTab.INSTANCE.getActiveConfig().setValue("default");
            lastActiveConfig = "default";
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client("default"));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            DungeonConfigTab.syncFloorFromStore();
            Config.INSTANCE.save();
         }

         this.updateConfigDropdown();
      }

      return deleted;
   }

   @NotNull
   public final List<String> listConfigs() {
      if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
         return CollectionsKt.listOf("default");
      }

      File[] files = ReplacementStore.INSTANCE.getConfigDir$cb_client().listFiles();
      if (files != null) {
         List<String> configNames = new ArrayList<>();

         for (File file : files) {
            Intrinsics.checkNotNull(file);
            if (Intrinsics.areEqual(FilesKt.getExtension(file), "json") && !file.getName().endsWith("-BlockRemap.json")) {
               configNames.add(FilesKt.getNameWithoutExtension(file));
            }
         }

         List<String> sorted = CollectionsKt.sorted(configNames);
         if (sorted != null) {
            return sorted;
         }
      }

      return CollectionsKt.listOf("default");
   }

   public final void updateConfigDropdown() {
   }
}
