/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.Unit
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.events;

import com.cb.events.EventBus;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016\u00a8\u0006\u0004\u00c0\u0006\u0003"}, d2={"Lcom/cb/events/Event;", "", "postAndCatch", "", "cb_client"})
public interface Event {
    default public boolean postAndCatch() {
        block2: {
            Object object;
            Object object2 = this;
            try {
                Event $this$postAndCatch_u24lambda_u240 = object2;
                boolean bl = false;
                EventBus.post($this$postAndCatch_u24lambda_u240);
                object = Result.constructor-impl((Object)Unit.INSTANCE);
            }
            catch (Throwable bl) {
                object = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)bl));
            }
            object2 = object;
            Throwable throwable = Result.exceptionOrNull-impl((Object)object2);
            if (throwable == null) break block2;
            Object it = object = throwable;
            boolean bl = false;
            ((Throwable)it).printStackTrace();
        }
        return false;
    }

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @Deprecated
        public static boolean postAndCatch(@NotNull Event $this) {
            return $this.postAndCatch();
        }
    }
}


