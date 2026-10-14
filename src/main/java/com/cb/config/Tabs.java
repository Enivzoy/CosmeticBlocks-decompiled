package com.cb.config;

import com.cb.config.tabs.BlockRemapsTab;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.config.tabs.GuiTab;
import com.cb.config.tabs.HelpTab;
import com.cb.config.tabs.RoomProfilesTab;
import java.util.List;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

public final class Tabs {
   @NotNull
   public static final Tabs INSTANCE = new Tabs();
   @NotNull
   private static final List<SettingsTab> all;

   private Tabs() {
   }

   @NotNull
   public final List<SettingsTab> getAll() {
      return all;
   }

   static {
      SettingsTab[] tabs = new SettingsTab[]{
         CosmeticBlocksTab.INSTANCE, BlockRemapsTab.INSTANCE, RoomProfilesTab.INSTANCE, GuiTab.INSTANCE, HelpTab.INSTANCE
      };
      all = CollectionsKt.listOf(tabs);
   }
}
