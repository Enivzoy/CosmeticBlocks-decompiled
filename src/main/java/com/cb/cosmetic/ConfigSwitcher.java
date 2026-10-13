package com.cb.cosmetic;

import com.cb.config.Config;
import com.cb.config.tabs.CosmeticBlocksTab;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
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
         lastActiveConfig = CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue();
         if (loadedConfigName != null && !Intrinsics.areEqual(loadedConfigName, CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue())) {
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(null));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
         }
      } else {
         String current = CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue();
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
         }
      }
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
      if (Intrinsics.areEqual(name, CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue())) {
         return true;
      }

      File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
      if (!file.exists()) {
         return false;
      }

      SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
      ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(null));
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
      CosmeticBlocksTab.INSTANCE.getActiveConfig().setValue(name);
      lastActiveConfig = name;
      ReplacementIO.INSTANCE.loadFrom$cb_client(file);
      DirtySections.INSTANCE.markAllCachedDirty$cb_client();
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
      if (Intrinsics.areEqual(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue(), name)) {
         SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
      }

      boolean deleted = file.delete();
      if (deleted) {
         if (Intrinsics.areEqual(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue(), name)) {
            CosmeticBlocksTab.INSTANCE.getActiveConfig().setValue("default");
            lastActiveConfig = "default";
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client("default"));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
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
            if (Intrinsics.areEqual(FilesKt.getExtension(file), "json")) {
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
