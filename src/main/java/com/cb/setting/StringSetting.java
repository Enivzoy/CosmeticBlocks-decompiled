package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class StringSetting extends Setting<String> {
   private final int maxLength;
   @NotNull
   private final String placeholder;

   public StringSetting(@NotNull String name, @NotNull String description, @NotNull String defaultValue, int maxLength, @NotNull String placeholder) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      Intrinsics.checkNotNullParameter(placeholder, "placeholder");
      super(name, description, defaultValue);
      this.maxLength = maxLength;
      this.placeholder = placeholder;
   }

   public final int getMaxLength() {
      return this.maxLength;
   }

   @NotNull
   public final String getPlaceholder() {
      return this.placeholder;
   }

   @NotNull
   protected String coerce(@NotNull String newValue) {
      Intrinsics.checkNotNullParameter(newValue, "new");
      String result;
      if (newValue.length() > this.maxLength) {
         result = newValue.substring(0, this.maxLength);
         Intrinsics.checkNotNullExpressionValue(result, "substring(...)");
      } else {
         result = newValue;
      }

      return result;
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
