package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0004\b\b\u0010\tJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0012\u0010\u0010\u001a\u00020\u00052\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0002J\u0006\u0010\u0012\u001a\u00020\u0005J\u0012\u0010\u0013\u001a\u00020\u000f2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0002J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018",
   d2 = {
         "Lcom/cb/setting/ModeSetting;",
         "E",
         "",
         "Lcom/cb/setting/Setting;",
         "name",
         "",
         "description",
         "defaultValue",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Enum;)V",
         "options",
         "",
         "getOptions",
         "()Ljava/util/List;",
         "cycle",
         "",
         "label",
         "option",
         "currentLabel",
         "select",
         "writeJson",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nModeSetting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModeSetting.kt\ncom/cb/setting/ModeSetting\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1#2:65\n296#3,2:66\n*S KotlinDebug\n*F\n+ 1 ModeSetting.kt\ncom/cb/setting/ModeSetting\n*L\n61#1:66,2\n*E\n"
)
public final class ModeSetting<E extends Enum<E>> extends Setting<E> {
   @NotNull
   private final List<E> options;

   public ModeSetting(@NotNull String name, @NotNull String description, @NotNull E defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
      super(name, description, (E)defaultValue);
      Class<?> declaringClass = defaultValue.getDeclaringClass();
      Intrinsics.checkNotNullExpressionValue(declaringClass, "getDeclaringClass(...)");
      Object[] constants = declaringClass.getEnumConstants();
      Intrinsics.checkNotNullExpressionValue(constants, "getEnumConstants(...)");
      this.options = ArraysKt.toList((E[])constants);
   }

   @NotNull
   public final List<E> getOptions() {
      return this.options;
   }

   public final void cycle() {
      this.setValue(this.options.get((this.options.indexOf(this.getValue()) + 1) % this.options.size()));
   }

   @NotNull
   public final String label(@NotNull Enum<?> option) {
      Intrinsics.checkNotNullParameter(option, "option");
      String lower = option.name();
      Locale locale = Locale.ROOT;
      Intrinsics.checkNotNullExpressionValue(Locale.ROOT, "ROOT");
      String lowercased = lower.toLowerCase(locale);
      Intrinsics.checkNotNullExpressionValue(lowercased, "toLowerCase(...)");
      CharSequence sequence = lowercased;
      char[] delimiters = new char[]{'_'};
      return CollectionsKt.joinToString(StringsKt.split(sequence, delimiters, false, 0), " ", "", "", -1, "...", word -> capitalizeWord((String)word));
   }

   @NotNull
   public final String currentLabel() {
      return this.label(this.getValue());
   }

   public final void select(@NotNull Enum<?> option) {
      Intrinsics.checkNotNullParameter(option, "option");
      if (!CollectionsKt.contains(this.options, (E)option)) {
         throw new IllegalArgumentException(this.getName() + ": " + option.name() + " is not a valid option");
      }

      this.setValue((E)option);
   }

   @Override
   public void writeJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      json.addProperty(this.getName(), this.getValue().name());
   }

   @Override
   public void readJson(@NotNull JsonObject json) {
      Intrinsics.checkNotNullParameter(json, "json");
      JsonElement element = json.get(this.getName());
      if (element != null) {
         if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String stored = element.getAsString();
            Object found = null;

            for (E item : this.options) {
               if (Intrinsics.areEqual(item.name(), stored)) {
                  found = item;
                  break;
               }
            }

            Enum match = (Enum)found;
            if (match != null) {
               this.setValue((E)match);
            }
         }
      }
   }

   private static final CharSequence capitalizeWord(String word) {
      Intrinsics.checkNotNullParameter(word, "word");
      String result;
      if (word.length() > 0) {
         StringBuilder builder = new StringBuilder();
         char first = word.charAt(0);
         Locale locale = Locale.ROOT;
         Intrinsics.checkNotNullExpressionValue(Locale.ROOT, "ROOT");
         builder.append((Object)CharsKt.uppercase(first, locale));
         String rest = word.substring(1);
         Intrinsics.checkNotNullExpressionValue(rest, "substring(...)");
         result = builder.append(rest).toString();
      } else {
         result = word;
      }

      return result;
   }
}
