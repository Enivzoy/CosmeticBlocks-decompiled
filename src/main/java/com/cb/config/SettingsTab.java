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
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003H\u0004J#\u0010\u0011\u001a\u0002H\u0012\"\f\b\u0000\u0010\u0012*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0013\u001a\u0002H\u0012H\u0004¢\u0006\u0002\u0010\u0014J \u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u0004JD\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\b\b\u0002\u0010\u001f\u001a\u00020\u001c2\b\b\u0002\u0010 \u001a\u00020\u0003H\u0004J;\u0010!\u001a\b\u0012\u0004\u0012\u0002H#0\"\"\u000e\b\u0000\u0010#*\b\u0012\u0004\u0012\u0002H#0$2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u0002H#H\u0004¢\u0006\u0002\u0010%J \u0010&\u001a\u00020'2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020(H\u0004J\"\u0010)\u001a\u00020*2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020(H\u0004J4\u0010+\u001a\u00020,2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\b\b\u0002\u0010-\u001a\u00020(2\b\b\u0002\u0010.\u001a\u00020\u0003H\u0004J6\u0010/\u001a\u0002002\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00032\u0012\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030302H\u0004J\u001a\u0010\u0010\u001a\u0002042\u0006\u00105\u001a\u00020\u00032\b\b\u0002\u00106\u001a\u00020\u0019H\u0004J.\u00107\u001a\u0002082\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u00109\u001a\u00020\u00032\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u000f02H\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;",
   d2 = {
         "Lcom/cb/config/SettingsTab;",
         "",
         "name",
         "",
         "<init>",
         "(Ljava/lang/String;)V",
         "getName",
         "()Ljava/lang/String;",
         "settings",
         "",
         "Lcom/cb/setting/Setting;",
         "getSettings",
         "()Ljava/util/List;",
         "currentSubcategory",
         "subcategory",
         "",
         "label",
         "register",
         "S",
         "setting",
         "(Lcom/cb/setting/Setting;)Lcom/cb/setting/Setting;",
         "boolean",
         "Lcom/cb/setting/BooleanSetting;",
         "description",
         "default",
         "",
         "number",
         "Lcom/cb/setting/NumberSetting;",
         "",
         "min",
         "max",
         "step",
         "unit",
         "mode",
         "Lcom/cb/setting/ModeSetting;",
         "E",
         "",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Enum;)Lcom/cb/setting/ModeSetting;",
         "color",
         "Lcom/cb/setting/ColorSetting;",
         "",
         "keybind",
         "Lcom/cb/setting/KeybindSetting;",
         "string",
         "Lcom/cb/setting/StringSetting;",
         "maxLength",
         "placeholder",
         "configList",
         "Lcom/cb/setting/ConfigListSetting;",
         "options",
         "Lkotlin/Function0;",
         "",
         "Lcom/cb/setting/LabelSetting;",
         "text",
         "spaceBefore",
         "button",
         "Lcom/cb/setting/ButtonSetting;",
         "actionLabel",
         "onClick",
         "cb_client"
   }
)
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
   protected final BooleanSetting bool(@NotNull String name, @NotNull String description, boolean var3) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new BooleanSetting(name, description, var3));
   }

   @NotNull
   protected final NumberSetting number(
      @NotNull String name, @NotNull String description, double var3, double min, double max, double step, @NotNull String unit
   ) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(unit, "unit");
      return this.register(new NumberSetting(name, description, var3, min, max, step, unit));
   }

   @NotNull
   protected final <E extends Enum<E>> ModeSetting<E> mode(@NotNull String name, @NotNull String description, @NotNull E var3) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(var3, "default");
      return this.register(new ModeSetting<>(name, description, (E)var3));
   }

   @NotNull
   protected final ColorSetting color(@NotNull String name, @NotNull String description, int var3) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new ColorSetting(name, description, var3));
   }

   @NotNull
   protected final KeybindSetting keybind(@NotNull String name, @NotNull String description, int var3) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      return this.register(new KeybindSetting(name, description, var3));
   }

   @NotNull
   protected final StringSetting string(@NotNull String name, @NotNull String description, @NotNull String var3, int maxLength, @NotNull String placeholder) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(var3, "default");
      Intrinsics.checkNotNullParameter(placeholder, "placeholder");
      return this.register(new StringSetting(name, description, var3, maxLength, placeholder));
   }

   @NotNull
   protected final ConfigListSetting configList(
      @NotNull String name, @NotNull String description, @NotNull String var3, @NotNull Function0<? extends List<String>> options
   ) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(var3, "default");
      Intrinsics.checkNotNullParameter(options, "options");
      return this.register(new ConfigListSetting(name, description, var3, options));
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
