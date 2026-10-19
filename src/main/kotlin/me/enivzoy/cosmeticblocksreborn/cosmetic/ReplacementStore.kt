package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.io.File
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.dungeon.Rotation
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

object ReplacementStore {
   private val configDirPath = File("config/cb/cb")
   @Volatile
   internal var replacements = LinkedHashMap<String, MutableMap<String, String>>()

   @Volatile
   private var currentFloor: String? = null

   internal val configDir: File
      get() = configDirPath

   internal var floor: String?
      get() = currentFloor
      set(value) {
         currentFloor = value
      }

   internal fun getConfigFile(name: String): File = File(configDirPath, name + ".json")

   fun setReplacement(pos: BlockPos, blockId: String, phantom: Boolean) {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         RoomStore.setRoomReplacement(pos, BlockStateCodec.normalizeBlockId(blockId), phantom, room)
      } else {
         val dim = LocationTracker.getCurrentDimension() ?: return
         val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
         val key = pos.x.toString() + "," + pos.y + "," + pos.z
         val normalizedId = BlockStateCodec.normalizeBlockId(blockId)
         val shape = EditMode.currentShape
         val storeId = if (shape != null) normalizedId + "|shape=" + shape else normalizedId
         dimMap[key] = if (phantom) "!" + storeId else storeId
         val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
         if (shape != null) {
            val shapeState = BlockStateCodec.resolveShapeState(shape)
            val textureState = BlockStateCodec.resolveBlockState(normalizedId)
            if (shapeState != null && textureState != null) {
               ReplacementCache.activeCache[packed] = shapeState
               SectionIndex.add(packed)
               ReplacementCache.retextureCache[packed] = textureState
               ReplacementCache.shapedPositions.add(packed)
            }
         } else {
            val state = BlockStateCodec.resolveBlockState(normalizedId)
            if (state != null) {
               ReplacementCache.activeCache[packed] = state
               SectionIndex.add(packed)
            }

            ReplacementCache.shapedPositions.remove(packed)
            ReplacementCache.retextureCache.remove(packed)
         }

         if (phantom) {
            ReplacementCache.phantomPositions.add(packed)
         } else {
            ReplacementCache.phantomPositions.remove(packed)
         }

         DirtySections.markDirty(pos)
         SaveScheduler.scheduleSave()
      }
   }

   fun setReplacementState(pos: BlockPos, state: BlockState, phantom: Boolean) {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         RoomStore.setRoomReplacementState(pos, state, phantom, room)
      } else {
         val dim = LocationTracker.getCurrentDimension() ?: return
         val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
         val key = pos.x.toString() + "," + pos.y + "," + pos.z
         val serialized = BlockStateCodec.serializeBlockState(state)
         dimMap[key] = if (phantom) "!" + serialized else serialized
         val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
         ReplacementCache.activeCache[packed] = state
         SectionIndex.add(packed)
         if (phantom) {
            ReplacementCache.phantomPositions.add(packed)
         } else {
            ReplacementCache.phantomPositions.remove(packed)
         }

         DirtySections.markDirty(pos)
         SaveScheduler.scheduleSave()
      }
   }

   fun removeReplacement(pos: BlockPos): Boolean {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         return RoomStore.removeRoomReplacement(pos, room)
      }

      val dim = LocationTracker.getCurrentDimension() ?: return false
      val dimMap = replacements[dim] ?: return false
      val key = pos.x.toString() + "," + pos.y + "," + pos.z
      val removed = dimMap.remove(key) != null
      if (removed) {
         if (dimMap.isEmpty()) {
            replacements.remove(dim)
         }

         val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
         ReplacementCache.activeCache.remove(packed)
         SectionIndex.remove(packed)
         ReplacementCache.phantomPositions.remove(packed)
         ReplacementCache.retextureCache.remove(packed)
         ReplacementCache.originalShapeCache.remove(packed)
         ReplacementCache.shapedPositions.remove(packed)
         DirtySections.markDirtyImmediate(pos)
         SaveScheduler.scheduleSave()
      }

      return removed
   }

   fun removeAt(x: Int, y: Int, z: Int): Boolean = this.removeReplacement(BlockPos(x, y, z))

   fun clearDimension(): Int {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         return RoomStore.clearRoom(room.data.name)
      }

      val dim = LocationTracker.getCurrentDimension() ?: return 0
      val dimMap = replacements.remove(dim) ?: return 0
      val count = dimMap.size

      for (key in dimMap.keys) {
         val parts = key.split(",")
         if (parts.size == 3) {
            try {
               DirtySections.markDirty(BlockPos(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()))
            } catch (ignored: NumberFormatException) {
            }
         }
      }

      ReplacementCache.activeCache.clear()
      SectionIndex.clear()
      ReplacementCache.phantomPositions.clear()
      ReplacementCache.retextureCache.clear()
      ReplacementCache.originalShapeCache.clear()
      ReplacementIO.INSTANCE.save()
      return count
   }

   fun clearAll(): Int {
      var count = 0

      for (dimMap in replacements.values) {
         count += dimMap.size

         for (key in dimMap.keys) {
            val parts = key.split(",")
            if (parts.size == 3) {
               try {
                  DirtySections.markDirty(BlockPos(parts[0].toInt(), parts[1].toInt(), parts[2].toInt()))
               } catch (ignored: NumberFormatException) {
               }
            }
         }
      }

      replacements.clear()
      ReplacementCache.activeCache.clear()
      SectionIndex.clear()
      ReplacementCache.phantomPositions.clear()
      ReplacementCache.retextureCache.clear()
      ReplacementCache.originalShapeCache.clear()
      ReplacementIO.INSTANCE.save()
      return count
   }

   fun listReplacements(): List<String> {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null) {
         val roomMap = RoomStore.roomBlocks[room.data.name] ?: return emptyList()

         return roomMap.entries.map { (pos, block) ->
            "[" + room.data.name + "] " + pos + " -> " + block.removePrefix("!").removePrefix("minecraft:")
         }
      } else {
         val dim = LocationTracker.getCurrentDimension() ?: return emptyList()
         val dimMap = replacements[dim] ?: return emptyList()

         return dimMap.entries.map { (pos, block) -> pos + " -> " + block.removePrefix("!").removePrefix("minecraft:") }
      }
   }

   fun getReplacementCount(): Int {
      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null) {
         val roomMap = RoomStore.roomBlocks[room.data.name]
         return roomMap?.size ?: 0
      }

      val dim = LocationTracker.getCurrentDimension() ?: return 0
      val dimMap = replacements[dim]
      return dimMap?.size ?: 0
   }

   internal fun snapshotReplacements(): LinkedHashMap<String, LinkedHashMap<String, String>> {
      val snapshot = LinkedHashMap<String, LinkedHashMap<String, String>>(replacements.size * 2)

      for ((dim, dimMap) in replacements) {
         val copy = LinkedHashMap<String, String>(dimMap.size * 2)
         copy.putAll(dimMap)
         snapshot[dim] = copy
      }

      return snapshot
   }
}
