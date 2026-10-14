package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.dungeon.RoomScanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Action;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket.Entry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LocationTracker {
   @NotNull
   public static final LocationTracker INSTANCE = new LocationTracker();
   private static final Pattern FORMAT_CODES = Pattern.compile("\u00a7.");
   private static final Pattern CATACOMBS_FLOOR = Pattern.compile("The Catacombs\\b(?:\\s*\\((\\w+)\\))?");
   private static volatile boolean isInSkyblock;
   @NotNull
   private static volatile String currentArea = "";
   private static volatile boolean inDungeon;
   private static boolean lastDungeonState;
   @Nullable
   private static volatile String currentFloor;

   private LocationTracker() {
   }

   private final Minecraft getMc() {
      Minecraft instance = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
      return instance;
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

   @Nullable
   public final String getCurrentFloor() {
      return currentFloor;
   }

   @JvmStatic
   public static final void onPlayerInfoUpdate(@NotNull ClientboundPlayerInfoUpdatePacket packet) {
      Intrinsics.checkNotNullParameter(packet, "packet");
      if (packet.actions().contains(Action.UPDATE_DISPLAY_NAME)) {
         for (Object entryObject : packet.entries()) {
            Intrinsics.checkNotNullExpressionValue(entryObject, "next(...)");
            Entry entry = (Entry)entryObject;
            Component display = entry.displayName();
            if (display != null) {
               String text = display.getString();
               if (text != null) {
                  if (StringsKt.startsWith(text, "Area: ", false) || StringsKt.startsWith(text, "Dungeon: ", false)) {
                     String area = StringsKt.trim(StringsKt.removePrefix(StringsKt.removePrefix(text, "Area: "), "Dungeon: ")).toString();
                     currentArea = area;
                     String floor = parseFloorFromText(area);
                     if (floor != null) {
                        currentFloor = floor;
                     }
                     boolean nowInDungeon = StringsKt.contains(area, "Catacombs", true);
                     INSTANCE.updateDungeonState(nowInDungeon);
                     return;
                  }
               }
            }
         }
      }
   }

   @JvmStatic
   public static final void onSetObjective(@NotNull ClientboundSetObjectivePacket packet) {
      Intrinsics.checkNotNullParameter(packet, "packet");
      if (Intrinsics.areEqual(packet.getObjectiveName(), "SBScoreboard")) {
         isInSkyblock = true;
      }
   }

   @JvmStatic
   public static final void onTeamPacket(@NotNull ClientboundSetPlayerTeamPacket packet) {
      Intrinsics.checkNotNullParameter(packet, "packet");
      if (packet.getParameters().isEmpty()) {
         return;
      }

      ClientboundSetPlayerTeamPacket.Parameters parameters = packet.getParameters().get();
      String text = parameters.playerPrefix().getString() + parameters.playerSuffix().getString();
      String floor = parseFloorFromText(text);
      if (floor != null) {
         currentFloor = floor;
      }
   }

   @Nullable
   private static final String parseFloorFromText(@Nullable String text) {
      if (text == null || text.isEmpty()) {
         return null;
      }

      String clean = FORMAT_CODES.matcher(text).replaceAll("");
      Matcher matcher = CATACOMBS_FLOOR.matcher(clean);
      if (!matcher.find()) {
         return null;
      }

      String code = matcher.group(1);
      if (code == null) {
         return "e";
      }

      String normalized = FloorUtil.normalize(code);
      return normalized == null || normalized.equals(FloorUtil.NONE) ? null : normalized;
   }

   public final void resetLocationState() {
      currentArea = "";
      currentFloor = null;
      isInSkyblock = false;
      this.updateDungeonState(false);
      RoomScanner.INSTANCE.unload();
      for (long key : RoomStore.INSTANCE.getRoomCacheKeys$cb_client()) {
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
         if (CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() && !ReplacementCache.INSTANCE.getActiveCache$cb_client().isEmpty()) {
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
         }
      } else {
         inDungeon = nowInDungeon;
      }
   }

   public final void updateDungeonCheck$cb_client() {
      if (CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() && inDungeon != lastDungeonState) {
         lastDungeonState = inDungeon;
         if (!ReplacementCache.INSTANCE.getActiveCache$cb_client().isEmpty()) {
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
         }
      }
   }

   @Nullable
   public final String getCurrentDimension() {
      ClientLevel level = this.getMc().level;
      if (level == null) {
         return null;
      }

      return level.dimension().identifier().toString();
   }
}
