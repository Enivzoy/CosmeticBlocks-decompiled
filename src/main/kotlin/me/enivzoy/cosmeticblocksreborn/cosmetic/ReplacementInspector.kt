package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.RoomProfilesTab
import me.enivzoy.cosmeticblocksreborn.dungeon.Rotation
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult

object ReplacementInspector {
   private val mc: Minecraft
      get() = Minecraft.getInstance()

   fun inspect(): Boolean {
      val hit = this.mc.hitResult
      if (hit !is BlockHitResult || hit.type != HitResult.Type.BLOCK) {
         ChatUtil.warning("Look at a block to inspect it.")
         return false
      }

      val level = this.mc.level
      if (level == null) {
         ChatUtil.error("No world is loaded.")
         return false
      }

      val pos = hit.blockPos
      val original = level.getBlockState(pos)
      val room = RoomScanner.currentRoom
      val roomEntriesActive = LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE
      val roomRaw = if (roomEntriesActive) RoomStore.rawAtWorld(pos, room) else null
      val dimension = LocationTracker.getCurrentDimension()
      val positionRaw = if (dimension == null) {
         null
      } else {
         ReplacementStore.replacements[dimension]?.get(pos.x.toString() + "," + pos.y + "," + pos.z)
      }

      val cached = ReplacementCache.activeCache[BlockPos.asLong(pos.x, pos.y, pos.z)]
      val configActive = LocationTracker.inDungeon && ConfigRemap.INSTANCE.isActiveForActiveConfig()
      val configRemap = if (configActive) ConfigRemap.INSTANCE.remapFor(original.block, pos.x, pos.y, pos.z) else null
      val globalRemap = if (configRemap == null) GlobalRemap.remapFor(original.block, pos.x, pos.y, pos.z) else null
      val result = cached ?: configRemap ?: globalRemap
      val source = when {
         cached != null && roomRaw != null -> "room entry"
         cached != null && positionRaw != null -> "position entry"
         cached != null -> "active cache"
         configRemap != null -> "config remap"
         globalRemap != null -> "global remap"
         else -> "none"
      }

      ChatUtil.info("Inspect ${pos.x}, ${pos.y}, ${pos.z}")
      ChatUtil.info("Original: " + this.describe(original))
      ChatUtil.info(
         "Room: " + (if (room == null) "not detected" else room.data.name + " (" + room.rotation + ")") +
            " | profile: " + RoomProfilesTab.INSTANCE.activeProfile.value +
            " | config: " + DungeonConfigTab.INSTANCE.activeConfig.value
      )
      ChatUtil.info(
         "Entries: room=" + (if (roomRaw == null) "none" else this.describeRaw(roomRaw)) +
            " | position=" + (if (positionRaw == null) "none" else this.describeRaw(positionRaw))
      )
      ChatUtil.info(
         "Remaps: " + this.remapStatus(configActive) +
            " | result: " + (if (result == null) "none" else this.describe(result) + " (" + source + ")")
      )

      if (room != null && LocationTracker.inDungeon && room.rotation == Rotation.NONE) {
         ChatUtil.warning("Why: room rotation is not resolved; room entries are inactive.")
      } else if (!CosmeticBlocksTab.INSTANCE.enabled.value) {
         ChatUtil.warning("Why: cosmetics are disabled.")
      } else if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && !LocationTracker.inDungeon) {
         ChatUtil.warning("Why: Only in Dungeons is enabled and you are outside a dungeon.")
      } else if (result == null && (roomRaw != null || positionRaw != null)) {
         ChatUtil.warning("Why: the stored entry did not resolve to a valid block state.")
      } else if (result == null) {
         ChatUtil.warning("Why: no room/position entry and no matching remap.")
      }

      if (PeekController.isPeeking) {
         ChatUtil.info("Peek is active; the world is currently showing original blocks.")
      }

      return true
   }

   private fun remapStatus(configActive: Boolean): String = when {
      configActive -> "config " + (ConfigRemap.INSTANCE.loadedName ?: "active")
      GlobalCosmeticsTab.INSTANCE.globalRemapEnabled.value -> "global active"
      else -> "disabled"
   }

   private fun describe(state: BlockState): String = BlockStateCodec.serializeBlockState(state)

   private fun describeRaw(raw: String): String {
      val phantom = raw.startsWith("!")
      val clean = if (phantom) raw.substring(1) else raw
      val parts = clean.split("|shape=", limit = 2)
      val state = BlockStateCodec.resolveBlockState(parts[0])
      val stateText = if (state == null) "invalid " + parts[0] else this.describe(state)
      if (parts.size == 1) {
         return stateText + (if (phantom) " (phantom)" else "")
      }

      val shape = BlockStateCodec.resolveShapeState(parts[1])
      val shapeText = if (shape == null) "invalid shape " + parts[1] else this.describe(shape)
      return stateText + " with shape " + shapeText + (if (phantom) " (phantom)" else "")
   }
}
