/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.Unit
 */
package com.cb.events;

import com.cb.events.Event;
import com.cb.events.EventBus;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\tJ\b\u0010\n\u001a\u00020\u0005H\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lcom/cb/events/CancellableEvent;", "Lcom/cb/events/Event;", "<init>", "()V", "value", "", "isCancelled", "()Z", "cancel", "", "postAndCatch", "cb_client"})
public abstract class CancellableEvent
implements Event {
    private boolean isCancelled;

    public final boolean isCancelled() {
        return this.isCancelled;
    }

    public final void cancel() {
        this.isCancelled = true;
    }

    @Override
    public boolean postAndCatch() {
        block2: {
            Object object;
            Object object2 = this;
            try {
                CancellableEvent $this$postAndCatch_u24lambda_u240 = object2;
                boolean bl = false;
                EventBus.post((Event)$this$postAndCatch_u24lambda_u240);
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
        return this.isCancelled;
    }
}


