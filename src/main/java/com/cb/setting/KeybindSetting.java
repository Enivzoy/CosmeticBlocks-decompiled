package com.cb.setting;

import com.cb.util.KeyNames;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class KeybindSetting extends Setting<Integer> {
   public KeybindSetting(@NotNull String name, @NotNull String description, int defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      super(name, description, defaultValue);
   }

   public final boolean getBound() {
      return this.getValue().intValue() != -1;
   }

   @NotNull
   public final String displayName() {
      return KeyNames.INSTANCE.name(this.getValue().intValue());
   }

   public final void clear() {
      this.setValue(-1);
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
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            this.setValue(element.getAsInt());
         }
      }
   }
}
