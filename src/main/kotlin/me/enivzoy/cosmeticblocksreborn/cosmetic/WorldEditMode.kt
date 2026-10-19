package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.ArrayDeque
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.dungeon.Rotation
import me.enivzoy.cosmeticblocksreborn.dungeon.ScannedRoom
import me.enivzoy.cosmeticblocksreborn.dungeon.rotateToNorth
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import org.lwjgl.glfw.GLFW

object WorldEditMode {
   private var worldEditModeState = false
   private var weLeftWasPressed = false
   private var weRightWasPressed = false

   private var pos1State: BlockPos? = null
   private var pos2State: BlockPos? = null

   private var undoSnapshot: MutableMap<String, String?>? = null
   private var undoDimension: String? = null
   private var undoRoomSnapshot: MutableMap<String, String?>? = null
   private var undoRoom: ScannedRoom? = null

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   val worldEditMode: Boolean
      get() = worldEditModeState

   val pos1: BlockPos?
      get() = pos1State

   val pos2: BlockPos?
      get() = pos2State

   internal fun setWorldEditMode(value: Boolean) {
      worldEditModeState = value
   }

   fun setPos1(pos: BlockPos) {
      pos1State = pos
      ChatUtil.success("Pos1 set to " + pos.x + ", " + pos.y + ", " + pos.z)
   }

   fun setPos2(pos: BlockPos) {
      pos2State = pos
      ChatUtil.success("Pos2 set to " + pos.x + ", " + pos.y + ", " + pos.z)
   }

   fun clearSelection() {
      pos1State = null
      pos2State = null
      ChatUtil.success("Selection cleared")
   }

   val selectionInfo: String
      get() {
         val p1 = this.pos1State
         val p2 = this.pos2State

         if (p1 == null && p2 == null) {
            return "No selection set"
         }

         if (p1 == null) {
            return "Pos1 not set (Pos2: " + p2!!.x + ", " + p2.y + ", " + p2.z + ")"
         }

         if (p2 == null) {
            return "Pos2 not set (Pos1: " + p1.x + ", " + p1.y + ", " + p1.z + ")"
         }

         val sizeX = abs(p1.x - p2.x) + 1
         val sizeY = abs(p1.y - p2.y) + 1
         val sizeZ = abs(p1.z - p2.z) + 1

         return sizeX.toString() + "x" + sizeY + "x" + sizeZ + " (" + sizeX * sizeY * sizeZ + ") | P1: " +
            p1.x + "," + p1.y + "," + p1.z +
            " | P2: " + p2.x + "," + p2.y + "," + p2.z
      }

   fun parseWeightedBlocks(input: String): MutableList<Pair<BlockState, Int>>? {
      val trimmed = input.trim()
      if (trimmed.isEmpty()) {
         return null
      }

      val tokens = trimmed.split(Regex("\\s+"))
      val result = mutableListOf<Pair<BlockState, Int>>()

      for (token in tokens) {
         if (token.isEmpty()) {
            continue
         }

         val colonIdx = token.lastIndexOf(':')
         var blockId: String
         var weight: Int

         if (colonIdx > 0 && colonIdx < token.length - 1) {
            val weightText = token.substring(colonIdx + 1).trim()
            val parsedWeight = parseIntOrNull(weightText)
            if (parsedWeight != null && parsedWeight > 0) {
               blockId = token.substring(0, colonIdx).trim()
               weight = parsedWeight
            } else {
               blockId = token
               weight = 1
            }
         } else {
            blockId = token
            weight = 1
         }

         if (blockId.isEmpty()) {
            return null
         }

         val normalized = BlockStateCodec.normalizeBlockId(blockId)
         val state = BlockStateCodec.resolveBlockState(normalized)
         if (state == null) {
            return null
         }

         result.add(Pair(state, weight))
      }

      return if (result.isEmpty()) null else result
   }

   private fun pickWeightedBlock(blocks: List<Pair<BlockState, Int>>): BlockState {
      if (blocks.isEmpty()) {
         throw IllegalArgumentException("No block weights provided")
      }

      if (blocks.size == 1) {
         return blocks[0].first
      }

      var totalWeight = 0
      for (block in blocks) {
         totalWeight += block.second
      }

      var roll = ThreadLocalRandom.current().nextInt(totalWeight)
      for (block in blocks) {
         val weight = block.second
         roll -= weight
         if (roll < 0) {
            return block.first
         }
      }

      return blocks[blocks.size - 1].first
   }

   fun fillRegion(blockSpecs: String): String {
      val p1 = this.pos1State ?: return "Â§cPos1 not set"
      val p2 = this.pos2State ?: return "Â§cPos2 not set"

      val dim = LocationTracker.getCurrentDimension() ?: return "Â§cNot in a world"

      val blocks = parseWeightedBlocks(blockSpecs) ?: return "Â§cInvalid block specification"

      val minX = min(p1.x, p2.x)
      val maxX = max(p1.x, p2.x)
      val minY = min(p1.y, p2.y)
      val maxY = max(p1.y, p2.y)
      val minZ = min(p1.z, p2.z)
      val maxZ = max(p1.z, p2.z)

      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val snapshot = LinkedHashMap<String, String?>()

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               snapshot[key] = dimMap[key]
            }
         }
      }

      undoSnapshot = snapshot
      undoDimension = dim
      undoRoomSnapshot = null
      undoRoom = null

      val level: ClientLevel? = mc.level
      var count = 0

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               val state = pickWeightedBlock(blocks)
               val serialized = BlockStateCodec.serializeBlockState(state)
               val isAir = level != null && level.getBlockState(BlockPos(x, y, z)).isAir
               val raw = if (isAir) "!" + serialized else serialized
               dimMap[key] = raw
               ReplacementCache.applyCacheForEntry(x, y, z, raw)
               count++
            }
         }
      }

      DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
      SaveScheduler.scheduleSave()

      val sizeX = maxX - minX + 1
      val sizeY = maxY - minY + 1
      val sizeZ = maxZ - minZ + 1

      val blockDesc = if (blocks.size == 1) {
         BuiltInRegistries.BLOCK.getKey(blocks[0].first.block).path
      } else {
         blocks.size.toString() + " block types"
      }

      return "Â§aFilled $count blocks (${sizeX}x${sizeY}x${sizeZ}) with $blockDesc"
   }

   fun replaceRegion(fromBlock: String, toSpecs: String): String {
      val p1 = this.pos1State ?: return "Â§cPos1 not set"
      val p2 = this.pos2State ?: return "Â§cPos2 not set"

      val dim = LocationTracker.getCurrentDimension() ?: return "Â§cNot in a world"

      val level: ClientLevel = mc.level ?: return "Â§cNot in a world"

      val normalizedFrom = BlockStateCodec.normalizeBlockId(fromBlock)
      val fromState = BlockStateCodec.resolveBlockState(normalizedFrom) ?: return "Â§cUnknown block: $fromBlock"

      val toBlocks = parseWeightedBlocks(toSpecs) ?: return "Â§cInvalid target block specification"

      val minX = min(p1.x, p2.x)
      val maxX = max(p1.x, p2.x)
      val minY = min(p1.y, p2.y)
      val maxY = max(p1.y, p2.y)
      val minZ = min(p1.z, p2.z)
      val maxZ = max(p1.z, p2.z)

      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val snapshot = LinkedHashMap<String, String?>()

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               snapshot[key] = dimMap[key]
            }
         }
      }

      val fromBlockObj: Block = fromState.block
      var count = 0

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               val existing = dimMap[key]
               var matches = false

               if (existing != null) {
                  val clean = if (existing.startsWith("!")) existing.substring(1) else existing
                  val existingState = BlockStateCodec.resolveBlockState(clean)
                  matches = existingState != null && existingState.block === fromBlockObj
               } else {
                  matches = level.getBlockState(BlockPos(x, y, z)).block === fromBlockObj
               }

               if (matches) {
                  val state = pickWeightedBlock(toBlocks)
                  val wasPhantom = existing != null && existing.startsWith("!")
                  val serialized = BlockStateCodec.serializeBlockState(state)
                  val raw = if (wasPhantom) "!" + serialized else serialized
                  dimMap[key] = raw
                  ReplacementCache.applyCacheForEntry(x, y, z, raw)
                  count++
               }
            }
         }
      }

      if (count > 0) {
         undoSnapshot = snapshot
         undoDimension = dim
         undoRoomSnapshot = null
         undoRoom = null
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val fromName = if (normalizedFrom.startsWith("minecraft:")) normalizedFrom.substring("minecraft:".length) else normalizedFrom
      return "Â§aReplaced $count '$fromName' blocks"
   }

   fun removeInRegion(filterBlock: String?): String {
      val p1 = this.pos1State ?: return "Â§cPos1 not set"
      val p2 = this.pos2State ?: return "Â§cPos2 not set"

      val dim = LocationTracker.getCurrentDimension() ?: return "Â§cNot in a world"

      val dimMap = ReplacementStore.replacements[dim] ?: return "Â§cNo replacements in this dimension"

      var filterState: BlockState? = null
      if (filterBlock != null) {
         val normalized = BlockStateCodec.normalizeBlockId(filterBlock)
         filterState = BlockStateCodec.resolveBlockState(normalized)
         if (filterState == null) {
            return "Â§cUnknown block: $filterBlock"
         }
      }

      val minX = min(p1.x, p2.x)
      val maxX = max(p1.x, p2.x)
      val minY = min(p1.y, p2.y)
      val maxY = max(p1.y, p2.y)
      val minZ = min(p1.z, p2.z)
      val maxZ = max(p1.z, p2.z)

      val snapshot = LinkedHashMap<String, String?>()
      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               snapshot[key] = dimMap[key]
            }
         }
      }

      var count = 0
      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               val existing = dimMap[key]
               if (existing == null) {
                  continue
               }

               if (filterState == null || matchesState(existing, filterState.block)) {
                  dimMap.remove(key)
                  val packed = BlockPos.asLong(x, y, z)
                  ReplacementCache.activeCache.remove(packed)
                  SectionIndex.remove(packed)
                  ReplacementCache.phantomPositions.remove(packed)
                  ReplacementCache.retextureCache.remove(packed)
                  ReplacementCache.originalShapeCache.remove(packed)
                  ReplacementCache.shapedPositions.remove(packed)
                  count++
               }
            }
         }
      }

      if (count > 0) {
         if (dimMap.isEmpty()) {
            ReplacementStore.replacements.remove(dim)
         }

         undoSnapshot = snapshot
         undoDimension = dim
         undoRoomSnapshot = null
         undoRoom = null
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val desc = if (filterBlock != null) "'" + filterBlock.replace("minecraft:", "") + "' blocks" else "blocks"
      return "Â§aRemoved $count cosmetic $desc in selection"
   }

   fun floodFill(start: BlockPos, blockSpecs: String): String {
      val level: ClientLevel = mc.level ?: return "Â§cNot in a world"

      val blocks = parseWeightedBlocks(blockSpecs) ?: return "Â§cInvalid block specification"

      if (level.getBlockState(start).isAir) {
         return "Â§cLook at a solid block to fill"
      }

      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         return floodFillRoom(start, blocks, room, level)
      }

      return floodFillFlat(start, blocks, level)
   }

   private fun floodFillFlat(start: BlockPos, blocks: List<Pair<BlockState, Int>>, level: ClientLevel): String {
      val dim = LocationTracker.getCurrentDimension() ?: return "Â§cNot in a world"

      val matchBlock = level.getBlockState(start).block
      val limit = CosmeticBlocksTab.INSTANCE.fillModeLimit.value.toInt()
      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val snapshot = LinkedHashMap<String, String?>()
      val visited = HashSet<Long>()
      val queue = ArrayDeque<BlockPos>()
      queue.add(start)
      visited.add(BlockPos.asLong(start.x, start.y, start.z))

      var count = 0
      var minX = Int.MAX_VALUE
      var minY = Int.MAX_VALUE
      var minZ = Int.MAX_VALUE
      var maxX = Int.MIN_VALUE
      var maxY = Int.MIN_VALUE
      var maxZ = Int.MIN_VALUE
      var hitLimit = false

      while (queue.isNotEmpty()) {
         val pos = queue.removeFirst()
         if (level.getBlockState(pos).block !== matchBlock) {
            continue
         }

         if (count >= limit) {
            hitLimit = true
            break
         }

         val key = "${pos.x},${pos.y},${pos.z}"
         snapshot.putIfAbsent(key, dimMap[key])

         val serialized = BlockStateCodec.serializeBlockState(pickWeightedBlock(blocks))
         dimMap[key] = serialized
         ReplacementCache.applyCacheForEntry(pos.x, pos.y, pos.z, serialized)
         count++

         minX = min(minX, pos.x)
         minY = min(minY, pos.y)
         minZ = min(minZ, pos.z)
         maxX = max(maxX, pos.x)
         maxY = max(maxY, pos.y)
         maxZ = max(maxZ, pos.z)

         for (dir in Direction.values()) {
            val next = pos.relative(dir)
            val nextPacked = BlockPos.asLong(next.x, next.y, next.z)
            if (!visited.add(nextPacked)) {
               continue
            }

            queue.add(next)
         }
      }

      if (count > 0) {
         undoSnapshot = snapshot
         undoDimension = dim
         undoRoomSnapshot = null
         undoRoom = null
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val name = BuiltInRegistries.BLOCK.getKey(matchBlock).path
      val suffix = if (hitLimit) " Â§7(limit $limit reached)" else ""
      return "Â§aFilled $count '$name' blocks$suffix"
   }

   private fun floodFillRoom(start: BlockPos, blocks: List<Pair<BlockState, Int>>, room: ScannedRoom, level: ClientLevel): String {
      val matchBlock = level.getBlockState(start).block
      val limit = CosmeticBlocksTab.INSTANCE.fillModeLimit.value.toInt()
      val snapshot = LinkedHashMap<String, String?>()
      val visited = HashSet<Long>()
      val queue = ArrayDeque<BlockPos>()
      queue.add(start)
      visited.add(BlockPos.asLong(start.x, start.y, start.z))

      var count = 0
      var hitLimit = false

      while (queue.isNotEmpty()) {
         val pos = queue.removeFirst()
         if (level.getBlockState(pos).block !== matchBlock) {
            continue
         }

         if (count >= limit) {
            hitLimit = true
            break
         }

         val relPos = pos.subtract(room.clayPos.atY(0)).rotateToNorth(room.rotation)
         val relKey = "${relPos.x},${relPos.y},${relPos.z}"
         snapshot.putIfAbsent(relKey, RoomStore.rawAtWorld(pos, room))

         val serialized = BlockStateCodec.serializeBlockState(pickWeightedBlock(blocks))
         RoomStore.setRoomReplacementRaw(pos, serialized, false, room)
         count++

         for (dir in Direction.values()) {
            val next = pos.relative(dir)
            val nextPacked = BlockPos.asLong(next.x, next.y, next.z)
            if (!visited.add(nextPacked)) {
               continue
            }

            queue.add(next)
         }
      }

      if (count > 0) {
         RoomStore.resolveRoomBlocks(room.data.name)
         SaveScheduler.scheduleRoomSave()
         undoRoomSnapshot = snapshot
         undoRoom = room
         undoSnapshot = null
         undoDimension = null
      }

      val name = BuiltInRegistries.BLOCK.getKey(matchBlock).path
      val suffix = if (hitLimit) " Â§7(limit $limit reached)" else ""
      return "Â§aFilled $count '$name' blocks (room-relative)$suffix"
   }

   fun undoLastOperation(): String {
      val roomSnap = this.undoRoomSnapshot
      val undoR = this.undoRoom
      if (roomSnap != null && undoR != null) {
         RoomStore.restoreRoomFill(roomSnap, undoR)
         undoRoomSnapshot = null
         undoRoom = null
         return "Â§aUndone: ${roomSnap.size} blocks restored"
      }

      val snapshot = this.undoSnapshot
      val undoDim = this.undoDimension
      if (snapshot == null || undoDim == null) {
         return "Â§cNothing to undo"
      }

      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(undoDim) { LinkedHashMap() }

      var restored = 0
      var minX = Int.MAX_VALUE
      var minY = Int.MAX_VALUE
      var minZ = Int.MAX_VALUE
      var maxX = Int.MIN_VALUE
      var maxY = Int.MIN_VALUE
      var maxZ = Int.MIN_VALUE

      for ((key, previousValue) in snapshot) {
         val parts = key.split(",")
         if (parts.size != 3) {
            continue
         }

         val x = parseIntOrNull(parts[0])
         val y = parseIntOrNull(parts[1])
         val z = parseIntOrNull(parts[2])
         if (x == null || y == null || z == null) {
            continue
         }

         if (previousValue == null) {
            dimMap.remove(key)
            ReplacementCache.clearCacheForPos(x, y, z)
         } else {
            dimMap[key] = previousValue
            ReplacementCache.applyCacheForEntry(x, y, z, previousValue)
         }

         minX = min(minX, x)
         minY = min(minY, y)
         minZ = min(minZ, z)
         maxX = max(maxX, x)
         maxY = max(maxY, y)
         maxZ = max(maxZ, z)
         restored++
      }

      if (dimMap.isEmpty()) {
         replacements.remove(undoDim)
      }

      undoSnapshot = null
      undoDimension = null

      if (restored > 0) {
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      return "Â§aUndone: $restored blocks restored"
   }

   internal fun tickWorldEditMode() {
      if (mc.gui.screen() != null) {
         return
      }

      val window = mc.window.handle()
      val leftPressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == 1
      val rightPressed = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == 1

      if (leftPressed && !weLeftWasPressed && mc.hitResult.let { it is BlockHitResult && it.type == HitResult.Type.BLOCK }) {
         val pos = (mc.hitResult as BlockHitResult).blockPos
         setPos1(pos)
      }

      if (rightPressed && !weRightWasPressed && mc.hitResult.let { it is BlockHitResult && it.type == HitResult.Type.BLOCK }) {
         val pos = (mc.hitResult as BlockHitResult).blockPos
         setPos2(pos)
         if (CosmeticBlocksTab.INSTANCE.weFillOnClick.value && pos1 != null) {
            val result = fillRegion(EditMode.selectedBlock)
            ChatUtil.send(result)
         }
      }

      weLeftWasPressed = leftPressed
      weRightWasPressed = rightPressed
   }

   private fun matchesState(existing: String, filterBlock: Block): Boolean {
      val clean = if (existing.startsWith("!")) existing.substring(1) else existing
      val state = BlockStateCodec.resolveBlockState(clean)
      return state != null && state.block === filterBlock
   }

   private fun parseIntOrNull(value: String): Int? = try {
      value.toInt()
   } catch (_: NumberFormatException) {
      null
   }
}
