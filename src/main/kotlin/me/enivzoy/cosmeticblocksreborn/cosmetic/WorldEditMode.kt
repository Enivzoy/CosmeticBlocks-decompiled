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

object WorldEditMode {
   private var worldEditModeState = false
   private var weLeftWasPressed = false
   private var weRightWasPressed = false

   private var pos1State: BlockPos? = null
   private var pos2State: BlockPos? = null

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

   class PatternEntry(val state: BlockState, val weight: Int)

   class Pattern(val layers: MutableList<MutableList<PatternEntry>>, val gradient: Boolean) {
      val flat: MutableList<PatternEntry>
         get() {
            val all = mutableListOf<PatternEntry>()
            for (layer in layers) {
               all.addAll(layer)
            }
            return all
         }

      fun isEmpty(): Boolean = layers.isEmpty() || layers.all { it.isEmpty() }

      fun forHeight(t: Float): MutableList<PatternEntry> {
         if (!gradient || layers.size == 1) {
            return layers[0]
         }

         val scaled = t.coerceIn(0f, 1f) * (layers.size - 1)
         val lower = scaled.toInt().coerceIn(0, layers.size - 1)
         val upper = (lower + 1).coerceIn(0, layers.size - 1)
         val frac = scaled - lower
         if (upper == lower) {
            return layers[lower]
         }

         val a = layers[lower]
         val b = layers[upper]
         val merged = LinkedHashMap<BlockState, Int>()
         for (e in a) {
            merged[e.state] = (merged[e.state] ?: 0) + Math.round(e.weight * (1f - frac))
         }
         for (e in b) {
            merged[e.state] = (merged[e.state] ?: 0) + Math.round(e.weight * frac)
         }

         val out = mutableListOf<PatternEntry>()
         for ((state, weight) in merged) {
            if (weight > 0) {
               out.add(PatternEntry(state, weight))
            }
         }
         return if (out.isEmpty()) a else out
      }
   }

   fun parsePattern(input: String): Pattern? {
      val trimmed = input.trim()
      if (trimmed.isEmpty()) {
         return null
      }

      val segments = trimmed.split("|")
      val layers = mutableListOf<MutableList<PatternEntry>>()
      for (segment in segments) {
         val layer = parseLayer(segment) ?: return null
         if (layer.isEmpty()) {
            return null
         }
         layers.add(layer)
      }

      if (layers.isEmpty()) {
         return null
      }

      return Pattern(layers, segments.size > 1)
   }

   private fun parseLayer(segment: String): MutableList<PatternEntry>? {
      val result = mutableListOf<PatternEntry>()
      val items = segment.split(",")
      for (raw in items) {
         val item = raw.trim()
         if (item.isEmpty()) {
            continue
         }

         var blockPart = item
         var weight = 1

         val percentIdx = item.indexOf('%')
         if (percentIdx > 0) {
            val weightText = item.substring(0, percentIdx).trim()
            val parsed = parseIntOrNull(weightText)
            if (parsed != null && parsed > 0) {
               weight = parsed
               blockPart = item.substring(percentIdx + 1).trim()
            }
         } else {
            val colonIdx = item.lastIndexOf(':')
            if (colonIdx > 0 && colonIdx < item.length - 1) {
               val weightText = item.substring(colonIdx + 1).trim()
               val parsed = parseIntOrNull(weightText)
               if (parsed != null && parsed > 0) {
                  weight = parsed
                  blockPart = item.substring(0, colonIdx).trim()
               }
            }
         }

         if (blockPart.isEmpty()) {
            return null
         }

         val normalized = BlockStateCodec.normalizeBlockId(blockPart)
         val state = BlockStateCodec.resolveBlockState(normalized) ?: return null
         result.add(PatternEntry(state, weight))
      }

      return result
   }

   fun parseMask(input: String): MutableList<Block>? {
      val trimmed = input.trim()
      if (trimmed.isEmpty()) {
         return null
      }

      val result = mutableListOf<Block>()
      val items = trimmed.split(",")
      for (raw in items) {
         var item = raw.trim()
         if (item.isEmpty()) {
            continue
         }

         val percentIdx = item.indexOf('%')
         if (percentIdx > 0) {
            item = item.substring(percentIdx + 1).trim()
         }

         if (item.isEmpty()) {
            continue
         }

         val normalized = BlockStateCodec.normalizeBlockId(item)
         val state = BlockStateCodec.resolveBlockState(normalized) ?: return null
         result.add(state.block)
      }

      return if (result.isEmpty()) null else result
   }

   fun parseWeightedBlocks(input: String): MutableList<Pair<BlockState, Int>>? {
      val pattern = parsePattern(input) ?: return null
      if (pattern.gradient) {
         return null
      }

      val result = mutableListOf<Pair<BlockState, Int>>()
      for (entry in pattern.layers[0]) {
         result.add(Pair(entry.state, entry.weight))
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

   private fun pickFromEntries(entries: List<PatternEntry>): BlockState {
      if (entries.isEmpty()) {
         throw IllegalArgumentException("No block weights provided")
      }

      if (entries.size == 1) {
         return entries[0].state
      }

      var totalWeight = 0
      for (e in entries) {
         totalWeight += e.weight
      }

      if (totalWeight <= 0) {
         return entries[0].state
      }

      var roll = ThreadLocalRandom.current().nextInt(totalWeight)
      for (e in entries) {
         roll -= e.weight
         if (roll < 0) {
            return e.state
         }
      }

      return entries[entries.size - 1].state
   }

   fun fillRegion(blockSpecs: String): String {
      val p1 = this.pos1State ?: return "§cPos1 not set"
      val p2 = this.pos2State ?: return "§cPos2 not set"

      val dim = LocationTracker.getCurrentDimension() ?: return "§cNot in a world"

      val blocks = parseWeightedBlocks(blockSpecs) ?: return "§cInvalid block specification"

      val minX = min(p1.x, p2.x)
      val maxX = max(p1.x, p2.x)
      val minY = min(p1.y, p2.y)
      val maxY = max(p1.y, p2.y)
      val minZ = min(p1.z, p2.z)
      val maxZ = max(p1.z, p2.z)

      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val before = LinkedHashMap<String, String?>()

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               before[key] = dimMap[key]
            }
         }
      }

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

      val after = LinkedHashMap<String, String?>()
      for (key in before.keys) {
         after[key] = dimMap[key]
      }

      EditHistory.record(EditHistory.Operation("fill", dim, null, before, after))
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

      return "§aFilled $count blocks (${sizeX}x${sizeY}x${sizeZ}) with $blockDesc"
   }

   fun replaceRegion(fromSpec: String, toSpecs: String): String {
      val p1 = this.pos1State ?: return "§cPos1 not set"
      val p2 = this.pos2State ?: return "§cPos2 not set"

      return replacenearBounds(min(p1.x, p2.x), min(p1.y, p2.y), min(p1.z, p2.z), max(p1.x, p2.x), max(p1.y, p2.y), max(p1.z, p2.z), fromSpec, toSpecs)
   }

   fun replacenear(size: Int, fromSpec: String, toSpecs: String): String {
      if (size < 1) {
         return "§cSize must be at least 1"
      }

      val player = mc.player ?: return "§cPlayer not found"
      val cx = player.blockPosition().x
      val cy = player.blockPosition().y + 1
      val cz = player.blockPosition().z
      val radius = size - 1
      val reach = size * 2 - 1

      if (reach > 256) {
         return "§cSize too large (max 128)"
      }

      return replacenearBounds(cx - radius, cy - radius, cz - radius, cx + radius, cy + radius, cz + radius, fromSpec, toSpecs)
   }

   private fun replacenearBounds(
      minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int, fromSpec: String, toSpecs: String
   ): String {
      val dim = LocationTracker.getCurrentDimension() ?: return "§cNot in a world"

      val level: ClientLevel = mc.level ?: return "§cNot in a world"

      val fromMask = parseMask(fromSpec) ?: return "§cUnknown block: $fromSpec"
      val fromBlocks = HashSet<Block>(fromMask)

      val pattern = parsePattern(toSpecs) ?: return "§cInvalid target block specification"

      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val before = LinkedHashMap<String, String?>()

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               before[key] = dimMap[key]
            }
         }
      }

      val ySpan = (maxY - minY).coerceAtLeast(1)
      var count = 0

      for (x in minX..maxX) {
         for (y in minY..maxY) {
            val layer = pattern.forHeight((y - minY).toFloat() / ySpan.toFloat())
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               val existing = dimMap[key]
               var matches = false

               if (existing != null) {
                  val clean = if (existing.startsWith("!")) existing.substring(1) else existing
                  val existingState = BlockStateCodec.resolveBlockState(clean)
                  matches = existingState != null && fromBlocks.contains(existingState.block)
               } else {
                  matches = fromBlocks.contains(level.getBlockState(BlockPos(x, y, z)).block)
               }

               if (matches) {
                  val state = pickFromEntries(layer)
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
         val after = LinkedHashMap<String, String?>()
         for (key in before.keys) {
            after[key] = dimMap[key]
         }

         EditHistory.record(EditHistory.Operation("replace", dim, null, before, after))
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val fromName = fromMask.joinToString(",") { BuiltInRegistries.BLOCK.getKey(it).path }
      val gradientSuffix = if (pattern.gradient) " §7(gradient)" else ""
      return "§aReplaced $count '$fromName' blocks$gradientSuffix"
   }

   fun removeInRegion(filterBlock: String?): String {
      val p1 = this.pos1State ?: return "§cPos1 not set"
      val p2 = this.pos2State ?: return "§cPos2 not set"

      val dim = LocationTracker.getCurrentDimension() ?: return "§cNot in a world"

      val dimMap = ReplacementStore.replacements[dim] ?: return "§cNo replacements in this dimension"

      var filterState: BlockState? = null
      if (filterBlock != null) {
         val normalized = BlockStateCodec.normalizeBlockId(filterBlock)
         filterState = BlockStateCodec.resolveBlockState(normalized)
         if (filterState == null) {
            return "§cUnknown block: $filterBlock"
         }
      }

      val minX = min(p1.x, p2.x)
      val maxX = max(p1.x, p2.x)
      val minY = min(p1.y, p2.y)
      val maxY = max(p1.y, p2.y)
      val minZ = min(p1.z, p2.z)
      val maxZ = max(p1.z, p2.z)

      val before = LinkedHashMap<String, String?>()
      for (x in minX..maxX) {
         for (y in minY..maxY) {
            for (z in minZ..maxZ) {
               val key = "$x,$y,$z"
               before[key] = dimMap[key]
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

         val after = LinkedHashMap<String, String?>()
         for (key in before.keys) {
            after[key] = dimMap[key]
         }

         EditHistory.record(EditHistory.Operation("remove", dim, null, before, after))
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val desc = if (filterBlock != null) "'" + filterBlock.replace("minecraft:", "") + "' blocks" else "blocks"
      return "§aRemoved $count cosmetic $desc in selection"
   }

   fun floodFill(start: BlockPos, blockSpecs: String): String {
      val level: ClientLevel = mc.level ?: return "§cNot in a world"

      val blocks = parseWeightedBlocks(blockSpecs) ?: return "§cInvalid block specification"

      if (level.getBlockState(start).isAir) {
         return "§cLook at a solid block to fill"
      }

      val room = RoomScanner.currentRoom
      if (LocationTracker.inDungeon && room != null && room.rotation != Rotation.NONE) {
         return floodFillRoom(start, blocks, room, level)
      }

      return floodFillFlat(start, blocks, level)
   }

   private fun floodFillFlat(start: BlockPos, blocks: List<Pair<BlockState, Int>>, level: ClientLevel): String {
      val dim = LocationTracker.getCurrentDimension() ?: return "§cNot in a world"

      val matchBlock = level.getBlockState(start).block
      val limit = CosmeticBlocksTab.INSTANCE.fillModeLimit.value.toInt()
      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }
      val before = LinkedHashMap<String, String?>()
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
         before.putIfAbsent(key, dimMap[key])

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
         val after = LinkedHashMap<String, String?>()
         for (key in before.keys) {
            after[key] = dimMap[key]
         }

         EditHistory.record(EditHistory.Operation("floodfill", dim, null, before, after))
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      val name = BuiltInRegistries.BLOCK.getKey(matchBlock).path
      val suffix = if (hitLimit) " §7(limit $limit reached)" else ""
      return "§aFilled $count '$name' blocks$suffix"
   }

   private fun floodFillRoom(start: BlockPos, blocks: List<Pair<BlockState, Int>>, room: ScannedRoom, level: ClientLevel): String {
      val matchBlock = level.getBlockState(start).block
      val limit = CosmeticBlocksTab.INSTANCE.fillModeLimit.value.toInt()
      val before = LinkedHashMap<String, String?>()
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
         before.putIfAbsent(relKey, RoomStore.rawAtWorld(pos, room))

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
         val after = LinkedHashMap<String, String?>()
         for (relKey in before.keys) {
            after[relKey] = RoomStore.roomBlocks[room.data.name]?.get(relKey)
         }

         EditHistory.record(EditHistory.Operation("floodfill", null, room, before, after))
      }

      val name = BuiltInRegistries.BLOCK.getKey(matchBlock).path
      val suffix = if (hitLimit) " §7(limit $limit reached)" else ""
      return "§aFilled $count '$name' blocks (room-relative)$suffix"
   }

   fun undoLastOperation(): String {
      val operation = EditHistory.popUndo() ?: return "§cNothing to undo"
      val restored = applyOperation(operation, operation.before)
      return "§aUndone ${operation.label}: $restored blocks restored (${EditHistory.undoDepth()} left)"
   }

   fun redoLastOperation(): String {
      val operation = EditHistory.popRedo() ?: return "§cNothing to redo"
      val applied = applyOperation(operation, operation.after)
      return "§aRedone ${operation.label}: $applied blocks changed (${EditHistory.redoDepth()} left)"
   }

   fun jumpTo(depth: Int): String {
      val target = depth.coerceIn(0, EditHistory.undoDepth() + EditHistory.redoDepth())
      var steps = 0
      while (EditHistory.undoDepth() > target) {
         val operation = EditHistory.popUndo() ?: break
         applyOperation(operation, operation.before)
         steps++
      }

      while (EditHistory.undoDepth() < target) {
         val operation = EditHistory.popRedo() ?: break
         applyOperation(operation, operation.after)
         steps++
      }

      return "§aMoved $steps step(s) (at ${EditHistory.undoDepth()}/${EditHistory.undoDepth() + EditHistory.redoDepth()})"
   }

   fun historyInfo(): Pair<Int, List<String>> = EditHistory.undoDepth() to EditHistory.undoLabels().reversed()

   private fun applyOperation(operation: EditHistory.Operation, target: Map<String, String?>): Int {
      return if (operation.isRoom) {
         val room = operation.room!!
         RoomStore.restoreRoomFill(target, room)
         target.size
      } else {
         applyDimensionSnapshot(operation.dimension!!, target)
      }
   }

   private fun applyDimensionSnapshot(dim: String, snapshot: Map<String, String?>): Int {
      val replacements = ReplacementStore.replacements
      val dimMap = replacements.getOrPut(dim) { LinkedHashMap() }

      var restored = 0
      var minX = Int.MAX_VALUE
      var minY = Int.MAX_VALUE
      var minZ = Int.MAX_VALUE
      var maxX = Int.MIN_VALUE
      var maxY = Int.MIN_VALUE
      var maxZ = Int.MIN_VALUE

      for ((key, value) in snapshot) {
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

         if (value == null) {
            dimMap.remove(key)
            ReplacementCache.clearCacheForPos(x, y, z)
         } else {
            dimMap[key] = value
            ReplacementCache.applyCacheForEntry(x, y, z, value)
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
         replacements.remove(dim)
      }

      if (restored > 0) {
         DirtySections.markRegionDirtyByBounds(minX, minY, minZ, maxX, maxY, maxZ)
         SaveScheduler.scheduleSave()
      }

      return restored
   }

   internal fun resetHistory() {
      EditHistory.clear()
   }

   internal fun tickWorldEditMode() {
      if (mc.gui.screen() != null) {
         return
      }

      val leftPressed = mc.mouseHandler.isLeftPressed()
      val rightPressed = mc.mouseHandler.isRightPressed()

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
