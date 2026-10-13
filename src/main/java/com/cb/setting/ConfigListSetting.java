package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ConfigListSetting extends Setting<String> {
   @NotNull
   private final Function0<List<String>> optionsProvider;

   public ConfigListSetting(
      @NotNull String name, @NotNull String description, @NotNull String defaultValue, @NotNull Function0<? extends List<String>> optionsProvider
   ) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      Intrinsics.checkNotNullParameter(optionsProvider, "optionsProvider");
      super(name, description, defaultValue);
      this.optionsProvider = (Function0<List<String>>)(Function0<?>)optionsProvider;
   }

   @NotNull
   public final List<String> options() {
      return this.optionsProvider.invoke();
   }

   @Override
   public void writeJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      json.addProperty(this.getName(), this.getValue());
   }

   @Override
   public void readJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      JsonElement element = json.get(this.getName());
      if (element != null) {
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String value = element.getAsString();
            Intrinsics.checkNotNullExpressionValue(value, "getAsString(...)");
            this.setValue(value);
         }
      }
   }
}
