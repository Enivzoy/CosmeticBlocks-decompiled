/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.cosmetic;

import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.RoomStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u001b\n\u0002\u0010\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\r\u0010+\u001a\u00020,H\u0000\u00a2\u0006\u0002\b-J\r\u0010.\u001a\u00020,H\u0000\u00a2\u0006\u0002\b/J\r\u00100\u001a\u00020,H\u0000\u00a2\u0006\u0002\b1J\u0006\u00102\u001a\u00020,J\r\u00103\u001a\u00020,H\u0000\u00a2\u0006\u0002\b4R\u001c\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R\u000e\u0010\u0018\u001a\u00020\u0010X\u0080T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0080T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001a\u001a\u00020\nX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\f\"\u0004\b\u001c\u0010\u000eR\u001a\u0010\u001d\u001a\u00020\nX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\f\"\u0004\b\u001f\u0010\u000eR\u001a\u0010 \u001a\u00020\nX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010\u000eR\u001a\u0010#\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0012\"\u0004\b%\u0010\u0014R\u001a\u0010&\u001a\u00020\u0010X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0012\"\u0004\b(\u0010\u0014R\u000e\u0010)\u001a\u00020\u0010X\u0080T\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0010X\u0080T\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2={"Lcom/cb/cosmetic/SaveScheduler;", "", "<init>", "()V", "saveExecutor", "Ljava/util/concurrent/ExecutorService;", "kotlin.jvm.PlatformType", "getSaveExecutor$cb_client", "()Ljava/util/concurrent/ExecutorService;", "needsSave", "", "getNeedsSave$cb_client", "()Z", "setNeedsSave$cb_client", "(Z)V", "lastEditTime", "", "getLastEditTime$cb_client", "()J", "setLastEditTime$cb_client", "(J)V", "lastSaveTime", "getLastSaveTime$cb_client", "setLastSaveTime$cb_client", "SAVE_DELAY_MS", "SAVE_MAX_INTERVAL_MS", "saveInFlight", "getSaveInFlight$cb_client", "setSaveInFlight$cb_client", "saveAgain", "getSaveAgain$cb_client", "setSaveAgain$cb_client", "needsRoomSave", "getNeedsRoomSave$cb_client", "setNeedsRoomSave$cb_client", "lastRoomEditTime", "getLastRoomEditTime$cb_client", "setLastRoomEditTime$cb_client", "lastRoomSaveTime", "getLastRoomSaveTime$cb_client", "setLastRoomSaveTime$cb_client", "ROOM_SAVE_DELAY_MS", "ROOM_SAVE_MAX_INTERVAL_MS", "scheduleSave", "", "scheduleSave$cb_client", "scheduleRoomSave", "scheduleRoomSave$cb_client", "flushSave", "flushSave$cb_client", "flushAll", "awaitPendingSaves", "awaitPendingSaves$cb_client", "cb_client"})
@SourceDebugExtension(value={"SMAP\nSaveScheduler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SaveScheduler.kt\ncom/cb/cosmetic/SaveScheduler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,70:1\n1#2:71\n*E\n"})
public final class SaveScheduler {
    @NotNull
    public static final SaveScheduler INSTANCE = new SaveScheduler();
    private static final ExecutorService saveExecutor = Executors.newSingleThreadExecutor(SaveScheduler::saveExecutor$lambda$0);
    private static boolean needsSave;
    private static long lastEditTime;
    private static long lastSaveTime;
    public static final long SAVE_DELAY_MS = 2000L;
    public static final long SAVE_MAX_INTERVAL_MS = 10000L;
    private static volatile boolean saveInFlight;
    private static volatile boolean saveAgain;
    private static boolean needsRoomSave;
    private static long lastRoomEditTime;
    private static long lastRoomSaveTime;
    public static final long ROOM_SAVE_DELAY_MS = 2000L;
    public static final long ROOM_SAVE_MAX_INTERVAL_MS = 10000L;

    private SaveScheduler() {
    }

    public final ExecutorService getSaveExecutor$cb_client() {
        return saveExecutor;
    }

    public final boolean getNeedsSave$cb_client() {
        return needsSave;
    }

    public final void setNeedsSave$cb_client(boolean bl) {
        needsSave = bl;
    }

    public final long getLastEditTime$cb_client() {
        return lastEditTime;
    }

    public final void setLastEditTime$cb_client(long l) {
        lastEditTime = l;
    }

    public final long getLastSaveTime$cb_client() {
        return lastSaveTime;
    }

    public final void setLastSaveTime$cb_client(long l) {
        lastSaveTime = l;
    }

    public final boolean getSaveInFlight$cb_client() {
        return saveInFlight;
    }

    public final void setSaveInFlight$cb_client(boolean bl) {
        saveInFlight = bl;
    }

    public final boolean getSaveAgain$cb_client() {
        return saveAgain;
    }

    public final void setSaveAgain$cb_client(boolean bl) {
        saveAgain = bl;
    }

    public final boolean getNeedsRoomSave$cb_client() {
        return needsRoomSave;
    }

    public final void setNeedsRoomSave$cb_client(boolean bl) {
        needsRoomSave = bl;
    }

    public final long getLastRoomEditTime$cb_client() {
        return lastRoomEditTime;
    }

    public final void setLastRoomEditTime$cb_client(long l) {
        lastRoomEditTime = l;
    }

    public final long getLastRoomSaveTime$cb_client() {
        return lastRoomSaveTime;
    }

    public final void setLastRoomSaveTime$cb_client(long l) {
        lastRoomSaveTime = l;
    }

    public final void scheduleSave$cb_client() {
        needsSave = true;
        lastEditTime = System.currentTimeMillis();
    }

    public final void scheduleRoomSave$cb_client() {
        needsRoomSave = true;
        lastRoomEditTime = System.currentTimeMillis();
    }

    public final void flushSave$cb_client() {
        if (needsSave) {
            ReplacementIO.INSTANCE.save();
        }
        if (needsRoomSave) {
            RoomStore.INSTANCE.forceRoomSave();
        }
    }

    public final void flushAll() {
        this.flushSave$cb_client();
        this.awaitPendingSaves$cb_client();
    }

    public final void awaitPendingSaves$cb_client() {
        try {
            saveExecutor.submit(SaveScheduler::awaitPendingSaves$lambda$0).get();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static final Thread saveExecutor$lambda$0(Runnable r) {
        Thread thread;
        Thread $this$saveExecutor_u24lambda_u240_u240 = thread = new Thread(r, "CFME-Save");
        boolean bl = false;
        $this$saveExecutor_u24lambda_u240_u240.setDaemon(true);
        return thread;
    }

    private static final void awaitPendingSaves$lambda$0() {
    }
}


