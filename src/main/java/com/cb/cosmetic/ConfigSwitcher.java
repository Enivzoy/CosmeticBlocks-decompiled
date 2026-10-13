package com.cb.cosmetic;

import com.cb.config.Config;
import com.cb.config.tabs.CosmeticBlocksTab;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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

   public final void setLoadedConfigName$cb_client(@Nullable String var1) {
      loadedConfigName = var1/* $VF was: <set-?> */;
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
            String var10002 = lastActiveConfig;
            if (lastActiveConfig == null) {
               var10002 = "default";
            }

            ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(var10002));
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

      File[] var1 = ReplacementStore.INSTANCE.getConfigDir$cb_client().listFiles();
      if (var1 != null) {
         File[] $this$filter$iv = var1;
         int $i$f$filter = 0;
         File[] $i$f$map = $this$filter$iv;
         var destination$iv$iv = new ArrayList();
         int $i$f$filterTo = 0;
         int $i$f$mapTo = 0;

         for (int var8 = $i$f$map.length; $i$f$mapTo < var8; $i$f$mapTo++) {
            Object element$iv$iv = $i$f$map[$i$f$mapTo];
            File it = (File)element$iv$iv;
            int var11/* $VF was: $i$a$-filter-ConfigSwitcher$listConfigs$1 */ = 0;
            Intrinsics.checkNotNull(it);
            if (Intrinsics.areEqual(FilesKt.getExtension(it), "json")) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         var var13 = (Iterable & List)destination$iv$iv;
         int $i$f$mapx = 0;
         Iterable var16 = var13;
         var destination$iv$ivx = new ArrayList(CollectionsKt.collectionSizeOrDefault(var13, 10));
         $i$f$mapTo = 0;

         for (Object item$iv$iv : var16) {
            File var21 = (File)item$iv$iv;
            Collection var12 = destination$iv$ivx;
            int var22/* $VF was: $i$a$-map-ConfigSwitcher$listConfigs$2 */ = 0;
            Intrinsics.checkNotNull(var21);
            var12.add(FilesKt.getNameWithoutExtension(var21));
         }

         List var14 = CollectionsKt.sorted((Iterable & List)destination$iv$ivx);
         if (var14 != null) {
            return var14;
         }
      }

      return CollectionsKt.listOf("default");
   }

   public final void updateConfigDropdown() {
   }
}
