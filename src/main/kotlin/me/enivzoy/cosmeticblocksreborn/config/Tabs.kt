package me.enivzoy.cosmeticblocksreborn.config

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GuiTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.HelpTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.PreferencesTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.RoomProfilesTab

object Tabs {
   val all: List<SettingsTab> = listOf(
      CosmeticBlocksTab.INSTANCE,
      GlobalCosmeticsTab.INSTANCE,
      DungeonConfigTab.INSTANCE,
      RoomProfilesTab.INSTANCE,
      PreferencesTab.INSTANCE,
      GuiTab.INSTANCE,
      HelpTab.INSTANCE
   )
}
