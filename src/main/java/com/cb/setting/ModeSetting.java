package com.cb.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Locale;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

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
