package com.cb.events;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004À\u0006\u0003",
   d2 = {"Lcom/cb/events/Event;", "", "postAndCatch", "", "cb_client"}
)
public interface Event {
   default boolean postAndCatch() {
      Object result;
      try {
         EventBus.post(this);
         result = Unit.INSTANCE;
      } catch (Throwable var5) {
         result = ResultKt.createFailure(var5);
      }

      Throwable var10000 = result instanceof Result.Failure ? ((Result.Failure)result).exception : null;
      if (var10000 != null) {
         Throwable it = var10000;
         it.printStackTrace();
      }

      return false;
   }

   @Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
   final class DefaultImpls {
      @Deprecated
      public static boolean postAndCatch(@NotNull Event $this) {
         return $this.postAndCatch();
      }
   }
}
