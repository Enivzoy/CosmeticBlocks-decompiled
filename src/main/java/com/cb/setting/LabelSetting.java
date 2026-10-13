package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0004\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012",
   d2 = {
         "Lcom/cb/setting/LabelSetting;",
         "Lcom/cb/setting/Setting;",
         "",
         "name",
         "text",
         "spaceBefore",
         "",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Z)V",
         "getText",
         "()Ljava/lang/String;",
         "getSpaceBefore",
         "()Z",
         "writeJson",
         "",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
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
