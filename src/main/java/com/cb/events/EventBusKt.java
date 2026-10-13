/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 */
package com.cb.events;

import com.cb.events.Event;
import com.cb.events.EventBus;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mv={2, 3, 0}, k=2, xi=48, d1={"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aK\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0019\b\b\u0010\t\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00010\n\u00a2\u0006\u0002\b\u000bH\u0086\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\f"}, d2={"on", "", "T", "Lcom/cb/events/Event;", "", "priority", "", "ignoreCancelled", "", "handler", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "cb_client"})
public final class EventBusKt {
    public static final /* synthetic */ <T extends Event> void on(Object $this$on, int priority, boolean ignoreCancelled, Function1<? super T, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)$this$on, (String)"<this>");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        boolean $i$f$on = false;
        Class<?> clazz = $this$on.getClass();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        Intrinsics.needClassReification();
        EventBus.INSTANCE.registerListener(clazz, Event.class, priority, ignoreCancelled, (Function1)new Function1<T, Unit>(handler){
            final /* synthetic */ Function1<T, Unit> $handler;
            {
                this.$handler = $handler;
            }

            public final void invoke(T it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                this.$handler.invoke(it);
            }
        });
    }

    public static /* synthetic */ void on$default(Object $this$on_u24default, int priority, boolean ignoreCancelled, Function1 handler, int n, Object object) {
        if ((n & 1) != 0) {
            priority = 0;
        }
        if ((n & 2) != 0) {
            ignoreCancelled = false;
        }
        Intrinsics.checkNotNullParameter((Object)$this$on_u24default, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)handler, (String)"handler");
        boolean $i$f$on = false;
        Class<?> clazz = $this$on_u24default.getClass();
        Intrinsics.reifiedOperationMarker((int)4, (String)"T");
        Intrinsics.needClassReification();
        EventBus.INSTANCE.registerListener(clazz, Event.class, priority, ignoreCancelled, (Function1)new /* invalid duplicate definition of identical inner class */);
    }
}


