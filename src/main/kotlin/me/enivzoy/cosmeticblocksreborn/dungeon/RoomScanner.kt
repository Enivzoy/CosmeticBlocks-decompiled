package me.enivzoy.cosmeticblocksreborn.dungeon

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.LevelChunk

object RoomScanner {
   private const val START = -185
   private const val ROOM_SIZE_SHIFT = 5
   private val horizontals = listOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
   private val mutableBlockPos = BlockPos.MutableBlockPos()

   private var roomDatabase: List<RoomInfo> = emptyList()
   private var coreToRoom: Map<Int, RoomInfo> = emptyMap()
   private var loaded = false
   private var scannedRoom: ScannedRoom? = null
   private var lastRoomCenter: Vec2i? = null

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   val currentRoom: ScannedRoom?
      get() = scannedRoom

   fun init() {
      if (!loaded) {
         this.loadRoomDatabase()
         loaded = true
      }
   }

   fun unload() {
      scannedRoom = null
      lastRoomCenter = null
   }

   private fun loadRoomDatabase() {
      try {
         val stream = RoomScanner::class.java.getResourceAsStream("/assets/cb/rooms.json") ?: return

         val json = InputStreamReader(stream, Charsets.UTF_8).buffered(8192).use { it.readText() }

         val type = object : TypeToken<List<RoomJsonEntry>>() {}.type
         val entries: List<RoomJsonEntry> = Gson().fromJson(json, type)

         val database = ArrayList<RoomInfo>(entries.size)
         for (entry in entries) {
            database.add(
               RoomInfo(
                  entry.name,
                  RoomType.fromString(entry.type),
                  entry.cores,
                  entry.secrets,
                  RoomShape.fromString(entry.shape)
               )
            )
         }

         roomDatabase = database

         val coreRooms = mutableListOf<Pair<Int, RoomInfo>>()
         for (room in roomDatabase) {
            for (core in room.cores) {
               coreRooms.add(core to room)
            }
         }

         coreToRoom = coreRooms.toMap()
      } catch (e: Exception) {
         e.printStackTrace()
      }
   }

   fun tick(): Boolean {
      val player = this.mc.player ?: return false
      val level = this.mc.level ?: return false

      val center = this.getRoomCenter(player.blockPosition().x, player.blockPosition().z)
      val current = scannedRoom
      if (current != null && current.components.contains(center)) {
         return false
      } else if (center == lastRoomCenter) {
         return false
      } else {
         lastRoomCenter = center
         val room = this.scanRoom(center, level)
         if (room != null && room.rotation != Rotation.NONE) {
            val changed = current == null
               || current.data.name != room.data.name
               || !current.components.containsAll(room.components)
            scannedRoom = room
            return changed
         } else {
            return false
         }
      }
   }

   fun getRoomCenter(posX: Int, posZ: Int): Vec2i {
      val roomX = (posX - START + 16) shr ROOM_SIZE_SHIFT
      val roomZ = (posZ - START + 16) shr ROOM_SIZE_SHIFT
      return Vec2i((roomX shl ROOM_SIZE_SHIFT) + START, (roomZ shl ROOM_SIZE_SHIFT) + START)
   }

   private fun scanRoom(center: Vec2i, level: ClientLevel): ScannedRoom? {
      val chunk: LevelChunk
      try {
         chunk = level.getChunk(center.x shr 4, center.z shr 4)
      } catch (ignored: Exception) {
         return null
      }

      val roomHeight = this.getTopLayerOfRoom(center.x, center.z, chunk)
      if (roomHeight <= 0) {
         return null
      }

      val core = this.getCoreAtHeight(center.x, center.z, roomHeight, chunk)
      val roomInfo = coreToRoom[core] ?: return null

      val components = this.findComponentsRecursive(center, roomInfo.cores, roomHeight, level, linkedSetOf(), linkedSetOf())
      val room = ScannedRoom(roomInfo, Rotation.NONE, BlockPos.ZERO, components)
      this.findRotation(room, roomHeight, level)
      return room
   }

   private fun getTopLayerOfRoom(x: Int, z: Int, chunk: LevelChunk): Int {
      for (y in 140 downTo 60) {
         mutableBlockPos.set(x, y, z)
         val state = chunk.getBlockState(mutableBlockPos)
         if (!state.isAir) {
            return if (state.block == Blocks.GOLD_BLOCK) y - 1 else y
         }
      }

      return 0
   }

   private fun getCoreAtHeight(x: Int, z: Int, roomHeight: Int, chunk: LevelChunk): Int {
      val sb = StringBuilder(150)
      val clampedHeight = roomHeight.coerceIn(11..140)
      var bedrock = 0
      val paddingSize = 140 - clampedHeight
      val padding = CharArray(paddingSize)

      while (bedrock < paddingSize) {
         padding[bedrock] = '0'
         bedrock++
      }

      sb.append(padding)
      bedrock = 0

      for (y in clampedHeight downTo 12) {
         mutableBlockPos.set(x, y, z)
         val block = chunk.getBlockState(mutableBlockPos).block
         if (block === Blocks.AIR && bedrock >= 2 && y < 69) {
            val remaining = y - 11
            val tail = CharArray(remaining)

            for (i in 0 until remaining) {
               tail[i] = '0'
            }

            sb.append(tail)
            break
         }

         if (block === Blocks.BEDROCK) {
            bedrock++
         } else {
            bedrock = 0
            if (block === Blocks.OAK_PLANKS || block === Blocks.TRAPPED_CHEST || block === Blocks.CHEST) {
               continue
            }
         }

         sb.append(block)
      }

      return sb.toString().hashCode()
   }

   private fun findComponentsRecursive(
      center: Vec2i,
      cores: List<Int>,
      roomHeight: Int,
      level: ClientLevel,
      visited: MutableSet<Vec2i>,
      tiles: MutableSet<Vec2i>
   ): Set<Vec2i> {
      if (visited.contains(center)) {
         return tiles
      }

      visited.add(center)

      val chunk: LevelChunk
      try {
         chunk = level.getChunk(center.x shr 4, center.z shr 4)
      } catch (ignored: Exception) {
         return tiles
      }

      val core = this.getCoreAtHeight(center.x, center.z, roomHeight, chunk)
      if (!cores.contains(core)) {
         return tiles
      }

      tiles.add(center)

      for (dir in horizontals) {
         val nx = center.x + ((if (dir.axis == Axis.X) dir.stepX else 0) shl ROOM_SIZE_SHIFT)
         val nz = center.z + ((if (dir.axis == Axis.Z) dir.stepZ else 0) shl ROOM_SIZE_SHIFT)
         this.findComponentsRecursive(Vec2i(nx, nz), cores, roomHeight, level, visited, tiles)
      }

      return tiles
   }

   private fun findRotation(room: ScannedRoom, roomHeight: Int, level: ClientLevel) {
      if (room.data.name == "Fairy") {
         val first = room.components.firstOrNull()
         if (first != null) {
            room.clayPos = BlockPos(first.x - 15, roomHeight, first.z - 15)
            room.rotation = Rotation.SOUTH
         }
      } else {
         for (rotation in Rotation.entries) {
            if (rotation != Rotation.NONE) {
               for (component in room.components) {
                  val testPos = BlockPos(component.x + rotation.x, roomHeight, component.z + rotation.z)

                  val state = try {
                     level.getBlockState(testPos)
                  } catch (ignored: Exception) {
                     continue
                  }

                  if (state.block === Blocks.DYED_TERRACOTTA.blue()
                     && (room.components.size == 1 || this.validateClay(testPos, level))
                  ) {
                     room.clayPos = testPos
                     room.rotation = rotation
                     return
                  }
               }
            }
         }
      }
   }

   private fun validateClay(pos: BlockPos, level: ClientLevel): Boolean {
      for (dir in horizontals) {
         val neighbor = pos.offset(
            if (dir.axis == Axis.X) dir.stepX else 0,
            0,
            if (dir.axis == Axis.Z) dir.stepZ else 0
         )

         val block = try {
            level.getBlockState(neighbor).block
         } catch (ignored: Exception) {
            return false
         }

         if (block !== Blocks.AIR && block !== Blocks.DYED_TERRACOTTA.blue()) {
            return false
         }
      }

      return true
   }

   fun isReady(): Boolean = coreToRoom.isNotEmpty()

   fun scanAt(center: Vec2i, level: ClientLevel): ScannedRoom? = this.scanRoom(center, level)

   fun detectDoorType(worldX: Int, worldZ: Int, level: ClientLevel): String? {
      var height = 0

      for (y in 140 downTo 12) {
         mutableBlockPos.set(worldX, y, worldZ)

         val state = try {
            level.getBlockState(mutableBlockPos)
         } catch (ignored: Exception) {
            return null
         }

         if (!state.isAir) {
            height = y
            break
         }
      }

      if (height < 73 || 83 <= height) {
         return null
      }

      mutableBlockPos.set(worldX, 69, worldZ)

      val block = try {
         level.getBlockState(mutableBlockPos).block
      } catch (ignored: Exception) {
         return null
      }

      return if (block === Blocks.COAL_BLOCK) {
         "wither"
      } else if (block === Blocks.DYED_TERRACOTTA.red()) {
         "blood"
      } else if (block === Blocks.AIR) {
         "normal"
      } else {
         null
      }
   }

   private data class RoomJsonEntry(
      val name: String = "",
      val type: String = "NORMAL",
      val cores: List<Int> = emptyList(),
      val secrets: Int = 0,
      val crypts: Int = 0,
      val trappedChests: Int = 0,
      val shape: String = "1x1"
   )
}
