package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class BooleanSetting extends Setting<Boolean> {
   public BooleanSetting(@NotNull String name, @NotNull String description, boolean defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      super(name, description, defaultValue);
   }

   public final void toggle() {
      this.setValue(!this.getValue());
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
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
            this.setValue(element.getAsBoolean());
         }
      }
   }
}
