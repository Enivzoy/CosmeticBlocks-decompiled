package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.regex.Pattern
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import net.minecraft.client.Minecraft
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket

object LocationTracker {
   private val FORMAT_CODES = Pattern.compile("§.")
   private val CATACOMBS_FLOOR = Pattern.compile("The Catacombs\\b(?:\\s*\\((\\w+)\\))?")

   @Volatile
   private var skyblock = false

   @Volatile
   private var area = ""

   @Volatile
   private var dungeon = false

   private var lastDungeonState = false

   @Volatile
   private var floor: String? = null

   val isInSkyblock: Boolean
      get() = skyblock

   val currentArea: String
      get() = area

   val inDungeon: Boolean
      get() = dungeon

   val currentFloor: String?
      get() = floor

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   @JvmStatic
   fun onPlayerInfoUpdate(packet: ClientboundPlayerInfoUpdatePacket) {
      if (!packet.actions().contains(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_DISPLAY_NAME)) {
         return
      }

      for (entry in packet.entries()) {
         val display = entry.displayName ?: continue
         val text = display.string
         if (text.startsWith("Area: ") || text.startsWith("Dungeon: ")) {
            val areaName = text.removePrefix("Area: ").removePrefix("Dungeon: ").trim()
            area = areaName
            val parsedFloor = parseFloorFromText(areaName)
            if (parsedFloor != null) {
               floor = parsedFloor
            }

            val nowInDungeon = areaName.contains("Catacombs", ignoreCase = true)
            this.updateDungeonState(nowInDungeon)
            return
         }
      }
   }

   @JvmStatic
   fun onSetObjective(packet: ClientboundSetObjectivePacket) {
      if (packet.objectiveName == "SBScoreboard") {
         skyblock = true
      }
   }

   @JvmStatic
   fun onTeamPacket(packet: ClientboundSetPlayerTeamPacket) {
      if (packet.parameters.isEmpty) {
         return
      }

      val parameters = packet.parameters.get()
      val text = parameters.playerPrefix.string + parameters.playerSuffix.string
      val parsedFloor = parseFloorFromText(text)
      if (parsedFloor != null) {
         floor = parsedFloor
      }
   }

   private fun parseFloorFromText(text: String?): String? {
      if (text.isNullOrEmpty()) {
         return null
      }

      val clean = FORMAT_CODES.matcher(text).replaceAll("")
      val matcher = CATACOMBS_FLOOR.matcher(clean)
      if (!matcher.find()) {
         return null
      }

      val code = matcher.group(1) ?: return "e"
      val normalized = FloorUtil.normalize(code) ?: return null
      return if (normalized == FloorUtil.NONE) null else normalized
   }

   fun resetLocationState() {
      area = ""
      floor = null
      skyblock = false
      this.updateDungeonState(false)
      RoomScanner.unload()

      for (key in RoomStore.roomCacheKeys) {
         ReplacementCache.activeCache.remove(key)
         SectionIndex.remove(key)
         ReplacementCache.phantomPositions.remove(key)
         ReplacementCache.shapedPositions.remove(key)
      }

      RoomStore.roomCacheKeys.clear()
      RoomStore.lastScannedRoom = null
      if (SaveScheduler.needsRoomSave) {
         RoomStore.forceRoomSave()
      }
   }

   private fun updateDungeonState(nowInDungeon: Boolean) {
      if (nowInDungeon != lastDungeonState) {
         lastDungeonState = nowInDungeon
         dungeon = nowInDungeon
         if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && ReplacementCache.activeCache.isNotEmpty()) {
            DirtySections.markAllCachedDirty()
         }
      } else {
         dungeon = nowInDungeon
      }
   }

   internal fun updateDungeonCheck() {
      if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && dungeon != lastDungeonState) {
         lastDungeonState = dungeon
         if (ReplacementCache.activeCache.isNotEmpty()) {
            DirtySections.markAllCachedDirty()
         }
      }
   }

   fun getCurrentDimension(): String? {
      val level = this.mc.level ?: return null
      return level.dimension().identifier().toString()
   }
}
