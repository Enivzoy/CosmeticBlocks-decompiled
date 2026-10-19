package me.enivzoy.cosmeticblocksreborn.cosmetic

import com.google.gson.JsonParser
import com.google.gson.stream.JsonWriter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.concurrent.ConcurrentHashMap
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.dungeon.Rotation
import me.enivzoy.cosmeticblocksreborn.dungeon.ScannedRoom
import me.enivzoy.cosmeticblocksreborn.dungeon.rotateAroundNorth
import me.enivzoy.cosmeticblocksreborn.dungeon.rotateToNorth
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState

object RoomStore {
   internal val roomBlocks = LinkedHashMap<String, MutableMap<String, String>>()
   private val roomBlocksFile = File("config/cb/cb_rooms.json")
   internal val roomCacheKeys: MutableSet<Long> = ConcurrentHashMap.newKeySet()

   @Volatile
   internal var lastScannedRoom: String? = null

   private val resolvedRoomCache = LinkedHashMap<String, MutableList<ResolvedRoomBlock>>()

   private data class ResolvedRoomBlock(
      val relPos: BlockPos,
      val state: BlockState,
      val phantom: Boolean,
      val shapeState: BlockState?,
      val textureState: BlockState?
   )

   fun loadRoomBlocks() {
      roomBlocks.clear()
      resolvedRoomCache.clear()
      val tmpFile = File(roomBlocksFile.parentFile, roomBlocksFile.name + ".tmp")
      val loaded = this.tryLoadRoomFile(roomBlocksFile) || this.tryLoadRoomFile(tmpFile)
      if (!loaded && (roomBlocksFile.exists() || tmpFile.exists())) {
         System.err.println("[CB] Failed to load room blocks")
      }
   }

   private fun tryLoadRoomFile(file: File): Boolean {
      if (!file.exists()) {
         return false
      }

      return try {
         val content = file.readText(Charsets.UTF_8)
         if (content.isBlank()) {
            return false
         }

         val root = JsonParser.parseString(content).asJsonObject

         for ((roomName, element) in root.entrySet()) {
            val roomObj = element.asJsonObject
            val roomMap = LinkedHashMap<String, String>()

            for ((pos, block) in roomObj.entrySet()) {
               roomMap[pos] = block.asString
            }

            roomBlocks[roomName] = roomMap
         }

         true
      } catch (e: Exception) {
         System.err.println("[CB] Failed to parse room file " + file.name + ": " + e.message)
         false
      }
   }

   internal fun saveRoomBlocksAsync() {
      SaveScheduler.needsRoomSave = false
      SaveScheduler.lastRoomSaveTime = System.currentTimeMillis()
      val snapshot = this.snapshotRoomBlocks()
      SaveScheduler.saveExecutor.execute { this.writeRoomConfig(snapshot) }
   }

   fun forceRoomSave() {
      SaveScheduler.needsRoomSave = false
      SaveScheduler.lastRoomSaveTime = System.currentTimeMillis()
      val snapshot = this.snapshotRoomBlocks()
      SaveScheduler.saveExecutor.execute { this.writeRoomConfig(snapshot) }
   }

   private fun snapshotRoomBlocks(): HashMap<String, HashMap<String, String>> {
      val snapshot = HashMap<String, HashMap<String, String>>(roomBlocks.size)

      for ((room, map) in roomBlocks) {
         snapshot[room] = HashMap(map)
      }

      return snapshot
   }

   private fun writeRoomConfig(snapshot: Map<String, Map<String, String>>) {
      roomBlocksFile.parentFile?.mkdirs()
      val tmpFile = File(roomBlocksFile.parentFile, roomBlocksFile.name + ".tmp")

      BufferedWriter(FileWriter(tmpFile), 65536).use { writer ->
         JsonWriter(writer).use { jw ->
            jw.setIndent("  ")
            jw.beginObject()

            for ((room, map) in snapshot) {
               jw.name(room)
               jw.beginObject()

               for ((pos, block) in map) {
                  jw.name(pos).value(block)
               }

               jw.endObject()
            }

            jw.endObject()
         }
      }

      if (roomBlocksFile.exists()) {
         roomBlocksFile.delete()
      }

      tmpFile.renameTo(roomBlocksFile)
   }

   internal fun onRoomChanged(newRoom: ScannedRoom?) {
      val dirtySections = HashSet<Long>()

      for (key in roomCacheKeys) {
         dirtySections.add(DirtySections.sectionKey(BlockPos.getX(key), BlockPos.getY(key), BlockPos.getZ(key)))
         ReplacementCache.activeCache.remove(key)
         ReplacementCache.phantomPositions.remove(key)
         ReplacementCache.retextureCache.remove(key)
         ReplacementCache.originalShapeCache.remove(key)
         ReplacementCache.shapedPositions.remove(key)
      }

      roomCacheKeys.clear()
      if (newRoom != null && newRoom.rotation != Rotation.NONE) {
         val resolved = resolvedRoomCache[newRoom.data.name]
         if (resolved != null) {
            val rot = newRoom.rotation

            for (entry in resolved) {
               val absPos = entry.relPos.rotateAroundNorth(rot).offset(newRoom.clayPos.x, 0, newRoom.clayPos.z)
               val packed = BlockPos.asLong(absPos.x, absPos.y, absPos.z)
               if (entry.shapeState != null && entry.textureState != null) {
                  ReplacementCache.activeCache[packed] = entry.shapeState
                  ReplacementCache.retextureCache[packed] = BlockStateCodec.rotateBlockStateFromNorth(entry.textureState, rot)
                  ReplacementCache.shapedPositions.add(packed)
               } else {
                  ReplacementCache.activeCache[packed] = BlockStateCodec.rotateBlockStateFromNorth(entry.state, rot)
               }

               roomCacheKeys.add(packed)
               if (entry.phantom) {
                  ReplacementCache.phantomPositions.add(packed)
               }

               dirtySections.add(DirtySections.sectionKey(absPos.x, absPos.y, absPos.z))
            }
         }

         lastScannedRoom = newRoom.data.name
      } else {
         lastScannedRoom = null
      }

      DirtySections.queueDirtySections(dirtySections)
   }

   internal fun resolveRoomBlocks(roomName: String) {
      val rawMap = roomBlocks[roomName]
      if (rawMap != null && rawMap.isNotEmpty()) {
         val list = mutableListOf<ResolvedRoomBlock>()

         for ((relKey, raw) in rawMap) {
            val parts = relKey.split(",")
            if (parts.size == 3) {
               try {
                  val relBlockPos = BlockPos(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                  val phantom = raw.startsWith("!")
                  val cleanRaw = if (phantom) raw.substring(1) else raw
                  val shapeSplit = cleanRaw.split("|shape=", limit = 2)
                  val blockText = shapeSplit[0]
                  val shape = shapeSplit.getOrNull(1)
                  if (shape != null) {
                     val shapeState = BlockStateCodec.resolveShapeState(shape)
                     val textureState = BlockStateCodec.resolveBlockState(blockText)
                     if (shapeState != null && textureState != null) {
                        list.add(ResolvedRoomBlock(relBlockPos, shapeState, phantom, shapeState, textureState))
                     }
                  } else {
                     val state = BlockStateCodec.resolveBlockState(blockText)
                     if (state != null) {
                        list.add(ResolvedRoomBlock(relBlockPos, state, phantom, null, null))
                     }
                  }
               } catch (ignored: Exception) {
               }
            }
         }

         resolvedRoomCache[roomName] = list
      } else {
         resolvedRoomCache.remove(roomName)
      }
   }

   fun resolveAllRoomBlocks() {
      resolvedRoomCache.clear()

      for (roomName in roomBlocks.keys.toList()) {
         this.resolveRoomBlocks(roomName)
      }
   }

   internal fun setRoomReplacement(pos: BlockPos, blockId: String, phantom: Boolean, room: ScannedRoom) {
      this.setRoomReplacementRaw(pos, blockId, phantom, room)
      this.resolveRoomBlocks(room.data.name)
      SaveScheduler.scheduleRoomSave()
   }

   internal fun setRoomReplacementRaw(pos: BlockPos, blockId: String, phantom: Boolean, room: ScannedRoom) {
      val localPos = pos.subtract(room.clayPos.atY(0))
      val relPos = localPos.rotateToNorth(room.rotation)
      val relKey = relPos.x.toString() + "," + relPos.y + "," + relPos.z
      val roomMap = roomBlocks.getOrPut(room.data.name) { LinkedHashMap() }
      val rotatedId = BlockStateCodec.rotateStateToNorth(blockId, room.rotation)
      val shape = EditMode.currentShape
      val value = if (shape != null) rotatedId + "|shape=" + shape else rotatedId
      roomMap[relKey] = if (phantom) "!" + value else value
      val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
      if (shape != null) {
         val shapeState = BlockStateCodec.resolveShapeState(shape)
         val textureState = BlockStateCodec.resolveBlockState(blockId)
         if (shapeState != null && textureState != null) {
            ReplacementCache.activeCache[packed] = shapeState
            ReplacementCache.retextureCache[packed] = textureState
            ReplacementCache.shapedPositions.add(packed)
            roomCacheKeys.add(packed)
         }
      } else {
         val state = BlockStateCodec.resolveBlockState(blockId)
         if (state != null) {
            ReplacementCache.activeCache[packed] = state
            roomCacheKeys.add(packed)
         }
      }

      if (phantom) {
         ReplacementCache.phantomPositions.add(packed)
      } else {
         ReplacementCache.phantomPositions.remove(packed)
      }

      DirtySections.markDirty(pos)
   }

   internal fun rawAtWorld(pos: BlockPos, room: ScannedRoom): String? {
      val localPos = pos.subtract(room.clayPos.atY(0))
      val relPos = localPos.rotateToNorth(room.rotation)
      val roomMap = roomBlocks[room.data.name] ?: return null
      return roomMap[relPos.x.toString() + "," + relPos.y + "," + relPos.z]
   }

   internal fun restoreRoomFill(snapshot: Map<String, String>, room: ScannedRoom) {
      val roomMap = roomBlocks.getOrPut(room.data.name) { LinkedHashMap() }

      for ((relKey, value) in snapshot) {
         if (value == null) {
            roomMap.remove(relKey)
         } else {
            roomMap[relKey] = value
         }
      }

      if (roomMap.isEmpty()) {
         roomBlocks.remove(room.data.name)
      }

      this.resolveRoomBlocks(room.data.name)
      val currentRoom = RoomScanner.currentRoom
      val currentRoomName: String? = currentRoom?.data?.name
      if (currentRoomName == room.data.name) {
         this.onRoomChanged(RoomScanner.currentRoom)
      }

      SaveScheduler.scheduleRoomSave()
   }

   internal fun setRoomReplacementState(pos: BlockPos, state: BlockState, phantom: Boolean, room: ScannedRoom) {
      val localPos = pos.subtract(room.clayPos.atY(0))
      val relPos = localPos.rotateToNorth(room.rotation)
      val relKey = relPos.x.toString() + "," + relPos.y + "," + relPos.z
      val roomMap = roomBlocks.getOrPut(room.data.name) { LinkedHashMap() }
      val serializedState = BlockStateCodec.serializeBlockState(state)
      val value = BlockStateCodec.rotateStateToNorth(serializedState, room.rotation)
      roomMap[relKey] = if (phantom) "!" + value else value
      val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
      ReplacementCache.activeCache[packed] = state
      roomCacheKeys.add(packed)
      if (phantom) {
         ReplacementCache.phantomPositions.add(packed)
      } else {
         ReplacementCache.phantomPositions.remove(packed)
      }

      DirtySections.markDirty(pos)
      this.resolveRoomBlocks(room.data.name)
      SaveScheduler.scheduleRoomSave()
   }

   internal fun removeRoomReplacement(pos: BlockPos, room: ScannedRoom): Boolean {
      val localPos = pos.subtract(room.clayPos.atY(0))
      val relPos = localPos.rotateToNorth(room.rotation)
      val relKey = relPos.x.toString() + "," + relPos.y + "," + relPos.z
      val roomMap = roomBlocks[room.data.name] ?: return false
      val removed = roomMap.remove(relKey) != null
      if (removed) {
         if (roomMap.isEmpty()) {
            roomBlocks.remove(room.data.name)
         }

         val packed = BlockPos.asLong(pos.x, pos.y, pos.z)
         ReplacementCache.activeCache.remove(packed)
         roomCacheKeys.remove(packed)
         ReplacementCache.phantomPositions.remove(packed)
         ReplacementCache.retextureCache.remove(packed)
         ReplacementCache.originalShapeCache.remove(packed)
         ReplacementCache.shapedPositions.remove(packed)
         DirtySections.markDirtyImmediate(pos)
         this.resolveRoomBlocks(room.data.name)
         SaveScheduler.scheduleRoomSave()
      }

      return removed
   }

   fun clearRoom(roomName: String): Int {
      val roomMap = roomBlocks.remove(roomName) ?: return 0
      val count = roomMap.size
      resolvedRoomCache.remove(roomName)

      for (key in roomCacheKeys.toList()) {
         ReplacementCache.activeCache.remove(key)
         ReplacementCache.phantomPositions.remove(key)
      }

      roomCacheKeys.clear()
      ReplacementCache.retextureCache.clear()
      ReplacementCache.originalShapeCache.clear()
      DirtySections.markAllCachedDirty()
      this.forceRoomSave()
      return count
   }

   fun clearAllRooms(): Int {
      var count = 0
      for (map in roomBlocks.values) {
         count += map.size
      }

      roomBlocks.clear()
      resolvedRoomCache.clear()

      for (key in roomCacheKeys.toList()) {
         ReplacementCache.activeCache.remove(key)
         ReplacementCache.phantomPositions.remove(key)
      }

      roomCacheKeys.clear()
      DirtySections.markAllCachedDirty()
      this.forceRoomSave()
      return count
   }

   fun listRoomNames(): List<String> = roomBlocks.keys.sorted()

   fun getRoomBlockCount(roomName: String): Int = roomBlocks[roomName]?.size ?: 0
}
