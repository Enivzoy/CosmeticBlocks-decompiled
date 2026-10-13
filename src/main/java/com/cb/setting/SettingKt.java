package com.cb.setting;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class SettingKt {
   @NotNull
   public static final <S extends Setting<?>> S visibleWhen(@NotNull S setting, @NotNull Function0<Boolean> predicate) {
      Intrinsics.checkNotNullParameter(setting, "<this>");
      Intrinsics.checkNotNullParameter(predicate, "predicate");
      setting.setVisibility(predicate);
      return setting;
   }

   @NotNull
   public static final <T, S extends Setting<T>> S onChange(@NotNull S setting, @NotNull Function1<? super T, Unit> listener) {
      Intrinsics.checkNotNullParameter(setting, "<this>");
      Intrinsics.checkNotNullParameter(listener, "listener");
      setting.setOnChanged(listener);
      return setting;
   }
}
