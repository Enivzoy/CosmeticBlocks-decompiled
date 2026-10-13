package com.cb.setting;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\u0002H\u0001\"\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u0002*\u0002H\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006\u001a9\u0010\u0007\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\b\"\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\b0\u0002*\u0002H\u00012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\f¨\u0006\r",
   d2 = {
         "visibleWhen",
         "S",
         "Lcom/cb/setting/Setting;",
         "predicate",
         "Lkotlin/Function0;",
         "",
         "(Lcom/cb/setting/Setting;Lkotlin/jvm/functions/Function0;)Lcom/cb/setting/Setting;",
         "onChange",
         "T",
         "listener",
         "Lkotlin/Function1;",
         "",
         "(Lcom/cb/setting/Setting;Lkotlin/jvm/functions/Function1;)Lcom/cb/setting/Setting;",
         "cb_client"
   }
)
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
