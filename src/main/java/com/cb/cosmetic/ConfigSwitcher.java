/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.io.FilesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.config.Config;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.SaveScheduler;
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

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\r\u001a\u00020\u000eH\u0000\u00a2\u0006\u0002\b\u000fJ\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0005J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0015J\u0006\u0010\u0016\u001a\u00020\u000eR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f\u00a8\u0006\u0017"}, d2={"Lcom/cb/cosmetic/ConfigSwitcher;", "", "<init>", "()V", "lastActiveConfig", "", "dropdownInitialized", "", "loadedConfigName", "getLoadedConfigName$cb_client", "()Ljava/lang/String;", "setLoadedConfigName$cb_client", "(Ljava/lang/String;)V", "checkConfigSwitch", "", "checkConfigSwitch$cb_client", "createConfig", "name", "loadConfigByName", "deleteConfig", "listConfigs", "", "updateConfigDropdown", "cb_client"})
@SourceDebugExtension(value={"SMAP\nConfigSwitcher.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigSwitcher.kt\ncom/cb/cosmetic/ConfigSwitcher\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,114:1\n3938#2:115\n4474#2,2:116\n1586#3:118\n1661#3,3:119\n*S KotlinDebug\n*F\n+ 1 ConfigSwitcher.kt\ncom/cb/cosmetic/ConfigSwitcher\n*L\n102#1:115\n102#1:116,2\n103#1:118\n103#1:119,3\n*E\n"})
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

    public final void setLoadedConfigName$cb_client(@Nullable String string) {
        loadedConfigName = string;
    }

    public final void checkConfigSwitch$cb_client() {
        if (!dropdownInitialized) {
            this.updateConfigDropdown();
            dropdownInitialized = true;
            lastActiveConfig = (String)CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue();
            if (loadedConfigName != null && !Intrinsics.areEqual((Object)loadedConfigName, CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue())) {
                ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.getConfigFile$cb_client$default(ReplacementStore.INSTANCE, null, 1, null));
                DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            }
            return;
        }
        String current = (String)CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue();
        if (!Intrinsics.areEqual((Object)current, (Object)lastActiveConfig)) {
            SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
            String string = lastActiveConfig;
            if (string == null) {
                string = "default";
            }
            ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(string));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            ReplacementIO.INSTANCE.loadFrom$cb_client(ReplacementStore.INSTANCE.getConfigFile$cb_client(current));
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            lastActiveConfig = current;
        }
    }

    public final boolean createConfig(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        if (StringsKt.isBlank((CharSequence)name)) {
            return false;
        }
        File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
        if (file.exists()) {
            return false;
        }
        if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
            ReplacementStore.INSTANCE.getConfigDir$cb_client().mkdirs();
        }
        FilesKt.writeText$default((File)file, (String)"{}", null, (int)2, null);
        this.updateConfigDropdown();
        return true;
    }

    public final boolean loadConfigByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        if (Intrinsics.areEqual((Object)name, CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue())) {
            return true;
        }
        File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
        if (!file.exists()) {
            return false;
        }
        SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
        ReplacementIO.INSTANCE.saveToFileSync$cb_client(ReplacementStore.getConfigFile$cb_client$default(ReplacementStore.INSTANCE, null, 1, null));
        DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        CosmeticBlocksTab.INSTANCE.getActiveConfig().setValue(name);
        lastActiveConfig = name;
        ReplacementIO.INSTANCE.loadFrom$cb_client(file);
        DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        Config.INSTANCE.save();
        return true;
    }

    public final boolean deleteConfig(@NotNull String name) {
        boolean deleted;
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        if (Intrinsics.areEqual((Object)name, (Object)"default")) {
            return false;
        }
        File file = ReplacementStore.INSTANCE.getConfigFile$cb_client(name);
        if (!file.exists()) {
            return false;
        }
        SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
        if (Intrinsics.areEqual(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue(), (Object)name)) {
            SaveScheduler.INSTANCE.setNeedsSave$cb_client(false);
        }
        if (deleted = file.delete()) {
            if (Intrinsics.areEqual(CosmeticBlocksTab.INSTANCE.getActiveConfig().getValue(), (Object)name)) {
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

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @NotNull
    public final List<String> listConfigs() {
        List list;
        if (!ReplacementStore.INSTANCE.getConfigDir$cb_client().exists()) {
            return CollectionsKt.listOf((Object)"default");
        }
        File[] fileArray = ReplacementStore.INSTANCE.getConfigDir$cb_client().listFiles();
        if (fileArray != null) {
            void $this$mapTo$iv$iv;
            File it;
            void $this$filterTo$iv$iv;
            File[] $this$filter$iv = fileArray;
            boolean $i$f$filter = false;
            File[] fileArray2 = $this$filter$iv;
            Iterable<void> destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (void element$iv$iv : $this$filterTo$iv$iv) {
                it = element$iv$iv;
                boolean bl = false;
                Intrinsics.checkNotNull((Object)it);
                if (!Intrinsics.areEqual((Object)FilesKt.getExtension((File)it), (Object)"json")) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$map$iv = (List)destination$iv$iv;
            boolean $i$f$map = false;
            destination$iv$iv = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                it = (File)item$iv$iv;
                Collection collection = destination$iv$iv2;
                boolean bl = false;
                Intrinsics.checkNotNull((Object)it);
                collection.add(FilesKt.getNameWithoutExtension((File)it));
            }
            List list2 = CollectionsKt.sorted((Iterable)((List)destination$iv$iv2));
            if (list2 != null) {
                list = list2;
                return list;
            }
        }
        list = CollectionsKt.listOf((Object)"default");
        return list;
    }

    public final void updateConfigDropdown() {
    }
}


