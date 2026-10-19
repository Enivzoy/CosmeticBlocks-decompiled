package me.enivzoy.cosmeticblocksreborn.config.tabs;

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab;
import me.enivzoy.cosmeticblocksreborn.cosmetic.RoomProfileManager;
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting;
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting;
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting;
import java.util.List;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

public final class RoomProfilesTab extends SettingsTab {
   @NotNull
   public static final RoomProfilesTab INSTANCE = new RoomProfilesTab();
   @NotNull
   private static final ConfigListSetting activeProfile = INSTANCE.configList(
      "Active Profile", "Switch between saved dungeon room layouts.", "default", RoomProfilesTab::listProfileOptions
   );
   @NotNull
   private static final StringSetting profileName = INSTANCE.string(
      "Profile Name", "Name to save the current room layout as.", "", 32, "e.g. config 1"
   );
   @NotNull
   private static final ButtonSetting saveAs = INSTANCE.button(
      "Save Current As", "Save the current room blocks as a new profile.", "Save", RoomProfilesTab::saveAsAction
   );
   @NotNull
   private static final ButtonSetting overwriteActive = INSTANCE.button(
      "Overwrite Active", "Overwrite the active profile with the current room blocks.", "Overwrite", RoomProfilesTab::overwriteAction
   );
   @NotNull
   private static final ButtonSetting deleteActive = INSTANCE.button(
      "Delete Active", "Delete the active profile.", "Delete", RoomProfilesTab::deleteAction
   );

   private RoomProfilesTab() {
      super("Room Profiles");
   }

   @NotNull
   public final ConfigListSetting getActiveProfile() {
      return activeProfile;
   }

   @NotNull
   private static final List<String> listProfileOptions() {
      return RoomProfileManager.INSTANCE.listProfiles();
   }

   private static final Unit saveAsAction() {
      RoomProfileManager.INSTANCE.saveAs(profileName.getValue());
      return Unit.INSTANCE;
   }

   private static final Unit overwriteAction() {
      RoomProfileManager.INSTANCE.overwriteActive();
      return Unit.INSTANCE;
   }

   private static final Unit deleteAction() {
      RoomProfileManager.INSTANCE.deleteActive();
      return Unit.INSTANCE;
   }

   static {
      INSTANCE.label("Save the current room blocks, then switch between profiles to swap layouts.", false);
      INSTANCE.label("Profiles are stored in config/cb/cb as .roomprofile files.", false);
   }
}
