package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class LabelSetting extends Setting<String> {
   @NotNull
   private final String text;
   private final boolean spaceBefore;

   public LabelSetting(@NotNull String name, @NotNull String text, boolean spaceBefore) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(text, "text");
      super(name, "", text);
      this.text = text;
      this.spaceBefore = spaceBefore;
   }

   @NotNull
   public final String getText() {
      return this.text;
   }

   public final boolean getSpaceBefore() {
      return this.spaceBefore;
   }

   @Override
   public void writeJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
   }

   @Override
   public void readJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
   }
}
