package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ConfigListSetting extends Setting<String> {
   @NotNull
   private final Function0<List<String>> optionsProvider;
   @Nullable
   private Function1<String, String> labelProvider;
   @Nullable
   private Function1<String, Integer> colorProvider;

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

   @Nullable
   public final Function1<String, String> getLabelProvider() {
      return this.labelProvider;
   }

   public final void setLabelProvider(@Nullable Function1<String, String> provider) {
      this.labelProvider = provider;
   }

   @Nullable
   public final Function1<String, Integer> getColorProvider() {
      return this.colorProvider;
   }

   public final void setColorProvider(@Nullable Function1<String, Integer> provider) {
      this.colorProvider = provider;
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
