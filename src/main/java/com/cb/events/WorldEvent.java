/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.events;

import com.cb.events.Event;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u00a8\u0006\u0004\u00c0\u0006\u0003"}, d2={"Lcom/cb/events/WorldEvent;", "Lcom/cb/events/Event;", "Load", "Unload", "cb_client"})
public interface WorldEvent
extends Event {

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @Deprecated
        public static boolean postAndCatch(@NotNull WorldEvent $this) {
            return $this.postAndCatch();
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cb/events/WorldEvent$Load;", "Lcom/cb/events/WorldEvent;", "<init>", "()V", "cb_client"})
    public static final class Load
    implements WorldEvent {
        @NotNull
        public static final Load INSTANCE = new Load();

        private Load() {
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cb/events/WorldEvent$Unload;", "Lcom/cb/events/WorldEvent;", "<init>", "()V", "cb_client"})
    public static final class Unload
    implements WorldEvent {
        @NotNull
        public static final Unload INSTANCE = new Unload();

        private Unload() {
        }
    }
}


