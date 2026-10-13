package com.cb.setting;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010)\u001a\u00020\u0011J\u0015\u0010*\u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010+J\u0010\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020.H&J\u0010\u0010/\u001a\u00020\u00112\u0006\u0010-\u001a\u00020.H&R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR(\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\n\"\u0004\b\u001f\u0010 R&\u0010\"\u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u0000@FX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000e\u001a\u0004\b#\u0010\r\"\u0004\b$\u0010%R\u0011\u0010&\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b'\u0010(¨\u00060",
   d2 = {
         "Lcom/cb/setting/Setting;",
         "T",
         "",
         "name",
         "",
         "description",
         "defaultValue",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V",
         "getName",
         "()Ljava/lang/String;",
         "getDescription",
         "getDefaultValue",
         "()Ljava/lang/Object;",
         "Ljava/lang/Object;",
         "onChanged",
         "Lkotlin/Function1;",
         "",
         "getOnChanged",
         "()Lkotlin/jvm/functions/Function1;",
         "setOnChanged",
         "(Lkotlin/jvm/functions/Function1;)V",
         "visibility",
         "Lkotlin/Function0;",
         "",
         "getVisibility",
         "()Lkotlin/jvm/functions/Function0;",
         "setVisibility",
         "(Lkotlin/jvm/functions/Function0;)V",
         "subcategory",
         "getSubcategory",
         "setSubcategory",
         "(Ljava/lang/String;)V",
         "new",
         "value",
         "getValue",
         "setValue",
         "(Ljava/lang/Object;)V",
         "visible",
         "getVisible",
         "()Z",
         "reset",
         "coerce",
         "(Ljava/lang/Object;)Ljava/lang/Object;",
         "writeJson",
         "json",
         "Lcom/google/gson/JsonObject;",
         "readJson",
         "cb_client"
   }
)
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
