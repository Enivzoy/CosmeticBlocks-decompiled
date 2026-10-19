package me.enivzoy.cosmeticblocksreborn.config

import me.enivzoy.cosmeticblocksreborn.setting.BooleanSetting
import me.enivzoy.cosmeticblocksreborn.setting.ButtonSetting
import me.enivzoy.cosmeticblocksreborn.setting.ColorSetting
import me.enivzoy.cosmeticblocksreborn.setting.ConfigListSetting
import me.enivzoy.cosmeticblocksreborn.setting.KeybindSetting
import me.enivzoy.cosmeticblocksreborn.setting.LabelSetting
import me.enivzoy.cosmeticblocksreborn.setting.ModeSetting
import me.enivzoy.cosmeticblocksreborn.setting.NumberSetting
import me.enivzoy.cosmeticblocksreborn.setting.Setting
import me.enivzoy.cosmeticblocksreborn.setting.StringSetting

abstract class SettingsTab(val name: String) {
   val settings: MutableList<Setting<*>> = mutableListOf()
   private var currentSubcategory: String = ""

   protected fun subcategory(label: String) {
      this.currentSubcategory = label
   }

   protected fun <S : Setting<*>> register(setting: S): S {
      setting.subcategory = this.currentSubcategory
      this.settings.add(setting)
      return setting
   }

   protected fun bool(name: String, description: String, defaultValue: Boolean): BooleanSetting =
      this.register(BooleanSetting(name, description, defaultValue))

   protected fun number(
      name: String, description: String, defaultValue: Double, min: Double, max: Double, step: Double, unit: String
   ): NumberSetting = this.register(NumberSetting(name, description, defaultValue, min, max, step, unit))

   protected fun <E : Enum<E>> mode(name: String, description: String, defaultValue: E): ModeSetting<E> =
      this.register(ModeSetting(name, description, defaultValue))

   protected fun color(name: String, description: String, defaultValue: Int): ColorSetting =
      this.register(ColorSetting(name, description, defaultValue))

   protected fun keybind(name: String, description: String, defaultValue: Int): KeybindSetting =
      this.register(KeybindSetting(name, description, defaultValue))

   protected fun string(name: String, description: String, defaultValue: String, maxLength: Int, placeholder: String): StringSetting =
      this.register(StringSetting(name, description, defaultValue, maxLength, placeholder))

   protected fun configList(name: String, description: String, defaultValue: String, options: () -> List<String>): ConfigListSetting =
      this.register(ConfigListSetting(name, description, defaultValue, options))

   protected fun label(text: String, spaceBefore: Boolean): LabelSetting =
      this.register(LabelSetting("label_" + this.settings.size, text, spaceBefore))

   protected fun button(name: String, description: String, actionLabel: String, onClick: () -> Unit): ButtonSetting =
      this.register(ButtonSetting(name, description, actionLabel, onClick))
}
