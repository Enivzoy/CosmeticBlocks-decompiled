package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0004\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0013",
   d2 = {
         "Lcom/cb/setting/ButtonSetting;",
         "Lcom/cb/setting/Setting;",
         "",
         "name",
         "",
         "description",
         "actionLabel",
         "onClick",
         "Lkotlin/Function0;",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V",
         "getActionLabel",
         "()Ljava/lang/String;",
         "getOnClick",
         "()Lkotlin/jvm/functions/Function0;",
         "writeJson",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
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
