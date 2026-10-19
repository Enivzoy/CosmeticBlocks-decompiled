package me.enivzoy.cosmeticblocksreborn.config;

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.HelpTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.PreferencesTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.RoomProfilesTab;
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
         CosmeticBlocksTab.INSTANCE,
         GlobalCosmeticsTab.INSTANCE,
         DungeonConfigTab.INSTANCE,
         RoomProfilesTab.INSTANCE,
         PreferencesTab.INSTANCE,
         GuiTab.INSTANCE,
         HelpTab.INSTANCE
      };
      all = CollectionsKt.listOf(tabs);
   }
}
