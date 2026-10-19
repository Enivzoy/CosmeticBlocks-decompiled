package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab
import me.enivzoy.cosmeticblocksreborn.cosmetic.RoomProfileManager
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting

class RoomProfilesTab private constructor() : SettingsTab("Room Profiles") {
   val activeProfile: ConfigListSetting = this.configList(
      "Active Profile", "Switch between saved dungeon room layouts.", "default"
   ) { listProfileOptions() }

   val profileName: StringSetting = this.string(
      "Profile Name", "Name to save the current room layout as.", "", 32, "e.g. config 1"
   )

   val saveAs: ButtonSetting = this.button(
      "Save Current As", "Save the current room blocks as a new profile.", "Save"
   ) { saveAsAction() }

   val overwriteActive: ButtonSetting = this.button(
      "Overwrite Active", "Overwrite the active profile with the current room blocks.", "Overwrite"
   ) { overwriteAction() }

   val deleteActive: ButtonSetting = this.button(
      "Delete Active", "Delete the active profile.", "Delete"
   ) { deleteAction() }

   init {
      this.label("Save the current room blocks, then switch between profiles to swap layouts.", false)
      this.label("Profiles are stored in config/cb/cb as .roomprofile files.", false)
   }

   companion object {
      @JvmField
      val INSTANCE: RoomProfilesTab = RoomProfilesTab()

      private fun listProfileOptions(): List<String> = RoomProfileManager.listProfiles()

      private fun saveAsAction() {
         RoomProfileManager.saveAs(INSTANCE.profileName.value)
      }

      private fun overwriteAction() {
         RoomProfileManager.overwriteActive()
      }

      private fun deleteAction() {
         RoomProfileManager.deleteActive()
      }
   }
}
