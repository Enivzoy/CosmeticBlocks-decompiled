/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Action
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket$Entry
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.RoomStore;
import com.cb.cosmetic.SaveScheduler;
import com.cb.dungeon.RoomScanner;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0018H\u0007J\u0006\u0010\u0019\u001a\u00020\u0014J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\tH\u0002J\r\u0010\u001c\u001a\u00020\u0014H\u0000\u00a2\u0006\u0002\b\u001dJ\b\u0010\u001e\u001a\u0004\u0018\u00010\fR\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\f@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000bR\u000e\u0010\u0012\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lcom/cb/cosmetic/LocationTracker;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "value", "", "isInSkyblock", "()Z", "", "currentArea", "getCurrentArea", "()Ljava/lang/String;", "inDungeon", "getInDungeon", "lastDungeonState", "onPlayerInfoUpdate", "", "packet", "Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket;", "onSetObjective", "Lnet/minecraft/network/protocol/game/ClientboundSetObjectivePacket;", "resetLocationState", "updateDungeonState", "nowInDungeon", "updateDungeonCheck", "updateDungeonCheck$cb_client", "getCurrentDimension", "cb_client"})
public final class LocationTracker {
    @NotNull
    public static final LocationTracker INSTANCE = new LocationTracker();
    private static volatile boolean isInSkyblock;
    @NotNull
    private static volatile String currentArea;
    private static volatile boolean inDungeon;
    private static boolean lastDungeonState;

    private LocationTracker() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    public final boolean isInSkyblock() {
        return isInSkyblock;
    }

    @NotNull
    public final String getCurrentArea() {
        return currentArea;
    }

    public final boolean getInDungeon() {
        return inDungeon;
    }

    @JvmStatic
    public static final void onPlayerInfoUpdate(@NotNull ClientboundPlayerInfoUpdatePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        if (!packet.actions().contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)) {
            return;
        }
        for (Object e : packet.entries()) {
            String area;
            String text;
            Component display;
            Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
            ClientboundPlayerInfoUpdatePacket.Entry entry = (ClientboundPlayerInfoUpdatePacket.Entry)e;
            if (entry.displayName() == null || display.getString() == null || !StringsKt.startsWith$default((String)text, (String)"Area: ", (boolean)false, (int)2, null) && !StringsKt.startsWith$default((String)text, (String)"Dungeon: ", (boolean)false, (int)2, null)) continue;
            currentArea = area = ((Object)StringsKt.trim((CharSequence)StringsKt.removePrefix((String)StringsKt.removePrefix((String)text, (CharSequence)"Area: "), (CharSequence)"Dungeon: "))).toString();
            boolean nowInDungeon = StringsKt.contains((CharSequence)area, (CharSequence)"Catacombs", (boolean)true);
            INSTANCE.updateDungeonState(nowInDungeon);
            return;
        }
    }

    @JvmStatic
    public static final void onSetObjective(@NotNull ClientboundSetObjectivePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        if (Intrinsics.areEqual((Object)packet.getObjectiveName(), (Object)"SBScoreboard")) {
            isInSkyblock = true;
        }
    }

    public final void resetLocationState() {
        currentArea = "";
        isInSkyblock = false;
        this.updateDungeonState(false);
        RoomScanner.INSTANCE.unload();
        Iterator<Long> iterator = RoomStore.INSTANCE.getRoomCacheKeys$cb_client().iterator();
        while (iterator.hasNext()) {
            long key = ((Number)iterator.next()).longValue();
            ReplacementCache.INSTANCE.getActiveCache$cb_client().remove(key);
            ReplacementCache.INSTANCE.getPhantomPositions$cb_client().remove(key);
            ReplacementCache.INSTANCE.getShapedPositions$cb_client().remove(key);
        }
        RoomStore.INSTANCE.getRoomCacheKeys$cb_client().clear();
        RoomStore.INSTANCE.setLastScannedRoom$cb_client(null);
        if (SaveScheduler.INSTANCE.getNeedsRoomSave$cb_client()) {
            RoomStore.INSTANCE.forceRoomSave();
        }
    }

    private final void updateDungeonState(boolean nowInDungeon) {
        if (nowInDungeon != lastDungeonState) {
            lastDungeonState = nowInDungeon;
            inDungeon = nowInDungeon;
            if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).isEmpty()) {
                DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            }
        } else {
            inDungeon = nowInDungeon;
        }
    }

    public final void updateDungeonCheck$cb_client() {
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && inDungeon != lastDungeonState) {
            lastDungeonState = inDungeon;
            if (!((Map)ReplacementCache.INSTANCE.getActiveCache$cb_client()).isEmpty()) {
                DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            }
        }
    }

    @Nullable
    public final String getCurrentDimension() {
        ClientLevel clientLevel = this.getMc().level;
        if (clientLevel == null) {
            return null;
        }
        ClientLevel level = clientLevel;
        return level.dimension().identifier().toString();
    }

    static {
        currentArea = "";
    }
}


