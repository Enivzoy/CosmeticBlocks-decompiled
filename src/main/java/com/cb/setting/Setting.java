package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Setting<T> {
   @NotNull
   private final String name;
   @NotNull
   private final String description;
   private final T defaultValue;
   @Nullable
   private Function1<? super T, Unit> onChanged;
   @NotNull
   private Function0<Boolean> visibility;
   @NotNull
   private String subcategory;
   private T value;

   public Setting(@NotNull String name, @NotNull String description, T defaultValue) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(description, "description");
      super();
      this.name = name;
      this.description = description;
      this.defaultValue = (T)defaultValue;
      this.visibility = Setting::defaultVisibility;
      this.subcategory = "";
      this.value = this.defaultValue;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final String getDescription() {
      return this.description;
   }

   public final T getDefaultValue() {
      return this.defaultValue;
   }

   @Nullable
   public final Function1<T, Unit> getOnChanged() {
      return (Function1<T, Unit>)this.onChanged;
   }

   public final void setOnChanged(@Nullable Function1<? super T, Unit> onChanged) {
      this.onChanged = onChanged;
   }

   @NotNull
   public final Function0<Boolean> getVisibility() {
      return this.visibility;
   }

   public final void setVisibility(@NotNull Function0<Boolean> visibility) {
      Intrinsics.checkNotNullParameter(visibility, "<set-?>");
      this.visibility = visibility;
   }

   @NotNull
   public final String getSubcategory() {
      return this.subcategory;
   }

   public final void setSubcategory(@NotNull String subcategory) {
      Intrinsics.checkNotNullParameter(subcategory, "<set-?>");
      this.subcategory = subcategory;
   }

   public final T getValue() {
      return this.value;
   }

   public final void setValue(T newValue) {
      Object coerced = this.coerce(newValue);
      if (!Intrinsics.areEqual(coerced, this.value)) {
         this.value = (T)coerced;
         if (this.onChanged != null) {
            this.onChanged.invoke((T)coerced);
         }
      }
   }

   public final boolean getVisible() {
      return this.visibility.invoke();
   }

   public final void reset() {
      this.setValue(this.defaultValue);
   }

   protected T coerce(T newValue) {
      return newValue;
   }

   public abstract void writeJson(@NotNull JsonObject json);

   public abstract void readJson(@NotNull JsonObject json);

   private static final boolean defaultVisibility() {
      return true;
   }
}
