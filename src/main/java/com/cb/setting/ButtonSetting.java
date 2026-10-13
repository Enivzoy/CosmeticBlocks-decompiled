package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ButtonSetting extends Setting<Unit> {
   @NotNull
   private final String actionLabel;
   @NotNull
   private final Function0<Unit> onClick;

   public ButtonSetting(@NotNull String name, @NotNull String description, @NotNull String actionLabel, @NotNull Function0<Unit> onClick) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(actionLabel, "actionLabel");
      Intrinsics.checkNotNullParameter(onClick, "onClick");
      super(name, description, Unit.INSTANCE);
      this.actionLabel = actionLabel;
      this.onClick = onClick;
   }

   @NotNull
   public final String getActionLabel() {
      return this.actionLabel;
   }

   @NotNull
   public final Function0<Unit> getOnClick() {
      return this.onClick;
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
