package com.cb.config;

import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.ColorSetting;
import com.cb.setting.ConfigListSetting;
import com.cb.setting.KeybindSetting;
import com.cb.setting.LabelSetting;
import com.cb.setting.ModeSetting;
import com.cb.setting.NumberSetting;
import com.cb.setting.Setting;
import com.cb.setting.StringSetting;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public abstract class SettingsTab {
   @NotNull
   private final String name;
   @NotNull
   private final List<Setting<?>> settings;
   @NotNull
   private String currentSubcategory;

   public SettingsTab(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      super();
      this.name = name;
      this.settings = new ArrayList<>();
      this.currentSubcategory = "";
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final List<Setting<?>> getSettings() {
      return this.settings;
   }

   protected final void subcategory(@NotNull String label) {
      Intrinsics.checkNotNullParameter(label, "label");
      this.currentSubcategory = label;
   }

   @NotNull
   protected final <S extends Setting<?>> S register(@NotNull S setting) {
      Intrinsics.checkNotNullParameter(setting, "setting");
      setting.setSubcategory(this.currentSubcategory);
      this.settings.add(setting);
      return (S)setting;
   }

   @NotNull
   protected final BooleanSetting bool(@NotNull String name, @NotNull String description, boolean defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new BooleanSetting(name, description, defaultValue));
   }

   @NotNull
   protected final NumberSetting number(
      @NotNull String name, @NotNull String description, double defaultValue, double min, double max, double step, @NotNull String unit
   ) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(unit, "unit");
      return this.register(new NumberSetting(name, description, defaultValue, min, max, step, unit));
   }

   @NotNull
   protected final <E extends Enum<E>> ModeSetting<E> mode(@NotNull String name, @NotNull String description, @NotNull E defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "default");
      return this.register(new ModeSetting<>(name, description, (E)defaultValue));
   }

   @NotNull
   protected final ColorSetting color(@NotNull String name, @NotNull String description, int defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new ColorSetting(name, description, defaultValue));
   }

   @NotNull
   protected final KeybindSetting keybind(@NotNull String name, @NotNull String description, int defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new KeybindSetting(name, description, defaultValue));
   }

   @NotNull
   protected final StringSetting string(@NotNull String name, @NotNull String description, @NotNull String defaultValue, int maxLength, @NotNull String placeholder) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "default");
      Intrinsics.checkNotNullParameter(placeholder, "placeholder");
      return this.register(new StringSetting(name, description, defaultValue, maxLength, placeholder));
   }

   @NotNull
   protected final ConfigListSetting configList(
      @NotNull String name, @NotNull String description, @NotNull String defaultValue, @NotNull Function0<? extends List<String>> options
   ) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "default");
      Intrinsics.checkNotNullParameter(options, "options");
      return this.register(new ConfigListSetting(name, description, defaultValue, options));
   }

   @NotNull
   protected final LabelSetting label(@NotNull String text, boolean spaceBefore) {
      Intrinsics.checkNotNullParameter(text, "text");
      return this.register(new LabelSetting("label_" + this.settings.size(), text, spaceBefore));
   }

   @NotNull
   protected final ButtonSetting button(@NotNull String name, @NotNull String description, @NotNull String actionLabel, @NotNull Function0<Unit> onClick) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(actionLabel, "actionLabel");
      Intrinsics.checkNotNullParameter(onClick, "onClick");
      return this.register(new ButtonSetting(name, description, actionLabel, onClick));
   }
}
