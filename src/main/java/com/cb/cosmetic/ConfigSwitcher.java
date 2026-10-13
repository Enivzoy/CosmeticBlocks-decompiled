package com.cb.cosmetic;

import com.cb.config.Config;
import com.cb.config.tabs.CosmeticBlocksTab;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015J\u0006\u0010\u0016\u001a\u00020\u000eR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0017",
   d2 = {
         "Lcom/cb/cosmetic/ConfigSwitcher;",
         "",
         "<init>",
         "()V",
         "lastActiveConfig",
         "",
         "dropdownInitialized",
         "",
         "loadedConfigName",
         "getLoadedConfigName$cb_client",
         "()Ljava/lang/String;",
         "setLoadedConfigName$cb_client",
         "(Ljava/lang/String;)V",
         "checkConfigSwitch",
         "",
         "checkConfigSwitch$cb_client",
         "createConfig",
         "name",
         "loadConfigByName",
         "deleteConfig",
         "listConfigs",
         "",
         "updateConfigDropdown",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nConfigSwitcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigSwitcher.kt\ncom/cb/cosmetic/ConfigSwitcher\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,114:1\n3938#2:115\n4474#2,2:116\n1586#3:118\n1661#3,3:119\n*S KotlinDebug\n*F\n+ 1 ConfigSwitcher.kt\ncom/cb/cosmetic/ConfigSwitcher\n*L\n102#1:115\n102#1:116,2\n103#1:118\n103#1:119,3\n*E\n"
)
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
