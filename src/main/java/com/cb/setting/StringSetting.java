package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0002¢\u0006\u0004\b\t\u0010\nJ\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0002H\u0014J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0016",
   d2 = {
         "Lcom/cb/setting/StringSetting;",
         "Lcom/cb/setting/Setting;",
         "",
         "name",
         "description",
         "defaultValue",
         "maxLength",
         "",
         "placeholder",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V",
         "getMaxLength",
         "()I",
         "getPlaceholder",
         "()Ljava/lang/String;",
         "coerce",
         "new",
         "writeJson",
         "",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
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
   protected String coerce(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "new");
      String var10000;
      if (var1.length() > this.maxLength) {
         var10000 = var1.substring(0, this.maxLength);
         Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
      } else {
         var10000 = var1;
      }

      return var10000;
   }

   @Override
   public void writeJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      json.addProperty(this.getName(), this.getValue());
   }

   @Override
   public void readJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      JsonElement var10000 = json.get(this.getName());
      if (var10000 != null) {
         JsonElement element = var10000;
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String var10001 = element.getAsString();
            Intrinsics.checkNotNullExpressionValue(var10001, "getAsString(...)");
            this.setValue(var10001);
         }
      }
   }
}
