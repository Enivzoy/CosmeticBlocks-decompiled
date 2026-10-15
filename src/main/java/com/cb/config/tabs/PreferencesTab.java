package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.setting.BooleanSetting;
import com.cb.setting.SettingKt;
import org.jetbrains.annotations.NotNull;

public final class PreferencesTab extends SettingsTab {
   @NotNull
   public static final PreferencesTab INSTANCE = new PreferencesTab();
   @NotNull
   private static final BooleanSetting autoLoadFloor = INSTANCE.bool(
      "Auto-load Floor Config", "Loads the config tagged for the current dungeon floor on entry.", true
   );
   @NotNull
   private static final BooleanSetting revertAfterCompletion = SettingKt.visibleWhen(
      INSTANCE.bool("Revert After Completion", "Switch back to the config that was active before the dungeon started.", true),
      PreferencesTab::autoLoadVisible
   );
   @NotNull
   private static final BooleanSetting showTitleAfterLoad = SettingKt.visibleWhen(
      INSTANCE.bool("Show Title After Load", "Shows a HUD title when a floor config is found and loaded.", true),
      PreferencesTab::autoLoadVisible
   );
   @NotNull
   private static final BooleanSetting loadChatMessage = SettingKt.visibleWhen(
      INSTANCE.bool("Load Chat Message", "Prints a chat line when a config is loaded automatically.", false),
      PreferencesTab::autoLoadVisible
   );

   private PreferencesTab() {
      super("Settings");
   }

   @NotNull
   public final BooleanSetting getAutoLoadFloor() {
      return autoLoadFloor;
   }

   @NotNull
   public final BooleanSetting getRevertAfterCompletion() {
      return revertAfterCompletion;
   }

   @NotNull
   public final BooleanSetting getShowTitleAfterLoad() {
      return showTitleAfterLoad;
   }

   @NotNull
   public final BooleanSetting getLoadChatMessage() {
      return loadChatMessage;
   }

   private static final boolean autoLoadVisible() {
      return autoLoadFloor.getValue();
   }

   static {
      INSTANCE.subcategory("Auto-load");
   }
}
