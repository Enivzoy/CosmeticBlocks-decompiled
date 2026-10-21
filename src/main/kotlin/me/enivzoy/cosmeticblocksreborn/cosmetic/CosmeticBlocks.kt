package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab
import me.enivzoy.cosmeticblocksreborn.dungeon.RoomScanner
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CrossCollisionBlock
import net.minecraft.world.level.block.FenceGateBlock
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.WallBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.WallSide
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW

class CosmeticBlocks private constructor() {
   private val mc: Minecraft
      get() = Minecraft.getInstance()

   fun getLastLookBlockPos(): BlockPos? = lastLookBlockPos

   fun register() {
      BlockStateCodec.buildVariantMaps()
      ReplacementIO.INSTANCE.migrateOldConfig()
      ReplacementIO.INSTANCE.load()
      GlobalRemap.load()
      Favorites.loadFavorites()
      RoomStore.loadRoomBlocks()
      RoomStore.resolveAllRoomBlocks()
      RoomScanner.init()
      RoomProfileManager.init()
      RoomOverrideManager.init()
      ReplacementCache.rebuildActiveCache()
      ClientTickEvents.END_CLIENT_TICK.register(::onEndClientTick)
   }

   fun flushAll() {
      SaveScheduler.flushAll()
   }

   @JvmOverloads
   fun getReplacementAt(x: Int, y: Int, z: Int, originalAir: Boolean = false): BlockState? =
      ReplacementCache.getReplacementAt(x, y, z, originalAir)

   fun getReplacement(pos: BlockPos): BlockState? = ReplacementCache.getReplacement(pos)

   fun getRetextureAt(x: Int, y: Int, z: Int): BlockState? = ReplacementCache.getRetextureAt(x, y, z)

   fun getOriginalShapeAt(x: Int, y: Int, z: Int): BlockState? = ReplacementCache.getOriginalShapeAt(x, y, z)

   fun updateConnections(state: BlockState, x: Int, y: Int, z: Int, getter: BlockStateGetter?): BlockState {
      val block = state.block
      if (block !is CrossCollisionBlock && block !is WallBlock) {
         return state
      }

      if (updatingConnections.get()) {
         return state
      }

      updatingConnections.set(true)
      try {
         var result = state
         if (block is WallBlock) {
            val directions = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
            for (dir in directions) {
               val wallProp = WallBlock.PROPERTY_BY_DIRECTION[dir] ?: continue
               val neighborState = getter?.get(x + dir.stepX, y + dir.stepY, z + dir.stepZ)
               val connects = if (neighborState != null && !neighborState.isAir) {
                  neighborState.block is WallBlock
                     || neighborState.block is CrossCollisionBlock
                     || neighborState.block is FenceGateBlock
                     || neighborState.canOcclude()
               } else {
                  false
               }

               result = result.setValue(wallProp, if (connects) WallSide.LOW else WallSide.NONE)
            }

            result = result.setValue(WallBlock.UP, true)
         } else {
            val directions = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
            for (dir in directions) {
               val propName = dir.serializedName
               val property = result.block.stateDefinition.getProperty(propName)
               if (property !is BooleanProperty) {
                  continue
               }

               val neighborState = getter?.get(x + dir.stepX, y + dir.stepY, z + dir.stepZ)
               val connects = if (neighborState != null && !neighborState.isAir) {
                  neighborState.block is CrossCollisionBlock
                     || neighborState.block is FenceGateBlock
                     || neighborState.canOcclude()
                     || neighborState.block is WallBlock
               } else {
                  false
               }

               result = result.setValue(property, connects)
            }
         }

         return result
      } finally {
         updatingConnections.set(false)
      }
   }

   fun applyOriginalShape(original: BlockState, replacement: BlockState, x: Int, y: Int, z: Int): BlockState =
      BlockStateCodec.applyOriginalShape(original, replacement, x, y, z)

   fun hasActiveReplacements(): Boolean = ReplacementCache.hasActiveReplacements()

   fun hasAdjacentReplacement(x: Int, y: Int, z: Int): Boolean = ReplacementCache.hasAdjacentReplacement(x, y, z)

   fun keepBlockSizeEnabled(): Boolean = CosmeticBlocksTab.INSTANCE.keepBlockSize.value

   fun customWaterColorEnabled(): Boolean = WaterConfig.INSTANCE.allowWaterColor()

   fun waterColorRgb(): Int = WaterConfig.INSTANCE.rgbWaterColor()

   fun remapKeepShapeEnabled(): Boolean =
      if (LocationTracker.inDungeon && ConfigRemap.INSTANCE.isActiveForActiveConfig()) {
         ConfigRemap.INSTANCE.keepShape
      } else {
         GlobalCosmeticsTab.INSTANCE.globalRemapKeepShape.value
      }

   fun onWorldRender(modelView: Matrix4f, projection: Matrix4f, camPos: Vec3) {
      CosmeticRenderer.onWorldRender(modelView, projection, camPos)
   }

   fun tryPickCosmeticAtCrosshair(): Boolean = EditMode.tryPickCosmeticAtCrosshair()

   fun getEditMode(): Boolean = EditMode.editMode

   fun getSelectedBlock(): String = EditMode.selectedBlock

   fun setSelectedBlock(value: String) {
      EditMode.setSelectedBlock(value)
   }

   fun getCurrentShape(): String? = EditMode.currentShape

   fun setCurrentShape(value: String?) {
      EditMode.currentShape = value
   }

   fun toggleEditMode() {
      EditMode.toggleEditMode()
   }

   fun getFillMode(): Boolean = EditMode.fillMode

   fun toggleFillMode() {
      EditMode.toggleFillMode()
   }

   fun setBlock(blockId: String) {
      EditMode.setBlock(blockId)
   }

   fun setBlockProperty(property: String, value: String) {
      EditMode.setBlockProperty(property, value)
   }

   fun listBlockProperties() {
      EditMode.listBlockProperties()
   }

   fun hasExplicitProperties(): Boolean = EditMode.hasExplicitProperties()

   fun getWorldEditMode(): Boolean = WorldEditMode.worldEditMode

   fun getPos1(): BlockPos? = WorldEditMode.pos1

   fun getPos2(): BlockPos? = WorldEditMode.pos2

   fun setPos1(pos: BlockPos) {
      WorldEditMode.setPos1(pos)
   }

   fun setPos2(pos: BlockPos) {
      WorldEditMode.setPos2(pos)
   }

   fun getSelectionInfo(): String = WorldEditMode.selectionInfo

   fun clearSelection() {
      WorldEditMode.clearSelection()
   }

   fun fillRegion(blockSpecs: String): String = WorldEditMode.fillRegion(blockSpecs)

   fun replaceRegion(fromBlock: String, toSpecs: String): String = WorldEditMode.replaceRegion(fromBlock, toSpecs)

   fun replacenearRegion(size: Int, fromBlock: String, toSpecs: String): String = WorldEditMode.replacenear(size, fromBlock, toSpecs)

   fun removeInRegion(filterBlock: String?): String = WorldEditMode.removeInRegion(filterBlock)

   fun undoLastOperation(): String = WorldEditMode.undoLastOperation()

   fun redoLastOperation(): String = WorldEditMode.redoLastOperation()

   fun jumpHistoryTo(depth: Int): String = WorldEditMode.jumpTo(depth)

   fun historyInfo(): Pair<Int, List<String>> = WorldEditMode.historyInfo()

   fun parseWeightedBlocks(input: String): List<Pair<BlockState, Int>>? = WorldEditMode.parseWeightedBlocks(input)

   @JvmOverloads
   fun setReplacement(pos: BlockPos, blockId: String, phantom: Boolean = false) {
      ReplacementStore.setReplacement(pos, blockId, phantom)
   }

   @JvmOverloads
   fun setReplacementState(pos: BlockPos, state: BlockState, phantom: Boolean = false) {
      ReplacementStore.setReplacementState(pos, state, phantom)
   }

   fun removeReplacement(pos: BlockPos): Boolean = ReplacementStore.removeReplacement(pos)

   fun removeAt(x: Int, y: Int, z: Int): Boolean = ReplacementStore.removeAt(x, y, z)

   fun clearDimension(): Int = ReplacementStore.clearDimension()

   fun clearAll(): Int = ReplacementStore.clearAll()

   fun listReplacements(): List<String> = ReplacementStore.listReplacements()

   fun getReplacementCount(): Int = ReplacementStore.getReplacementCount()

   fun save() {
      ReplacementIO.INSTANCE.save()
   }

   fun load() {
      ReplacementIO.INSTANCE.load()
   }

   fun createConfig(name: String): Boolean = ConfigSwitcher.INSTANCE.createConfig(name)

   fun loadConfigByName(name: String): Boolean = ConfigSwitcher.INSTANCE.loadConfigByName(name)

   fun deleteConfig(name: String): Boolean = ConfigSwitcher.INSTANCE.deleteConfig(name)

   fun listConfigs(): List<String> = ConfigSwitcher.INSTANCE.listConfigs()

   fun diffConfigs(nameA: String, nameB: String) {
      ReplacementIO.INSTANCE.diffConfigs(nameA, nameB)
   }

   fun getFavorites(): List<String> = Favorites.getFavorites()

   fun addFavorite(blockId: String): Boolean = Favorites.addFavorite(blockId)

   fun removeFavorite(blockId: String): Boolean = Favorites.removeFavorite(blockId)

   fun isFavorite(blockId: String): Boolean = Favorites.isFavorite(blockId)

   fun clearRoom(roomName: String): Int = RoomStore.clearRoom(roomName)

   fun clearAllRooms(): Int = RoomStore.clearAllRooms()

   fun listRoomNames(): List<String> = RoomStore.listRoomNames()

   fun getRoomBlockCount(roomName: String): Int = RoomStore.getRoomBlockCount(roomName)

   fun isInSkyblock(): Boolean = LocationTracker.isInSkyblock

   fun getCurrentArea(): String = LocationTracker.currentArea

   fun getInDungeon(): Boolean = LocationTracker.inDungeon

   fun getCurrentDimension(): String? = LocationTracker.getCurrentDimension()

   fun interface BlockStateGetter {
      fun get(x: Int, y: Int, z: Int): BlockState?
   }

   companion object {
      @JvmField
      val INSTANCE: CosmeticBlocks = CosmeticBlocks()

      @JvmStatic
      fun getReplacementOrRemap(original: BlockState?, x: Int, y: Int, z: Int, originalAir: Boolean): BlockState? {
         val perPos = ReplacementCache.getReplacementAt(x, y, z, originalAir)
         if (perPos != null) {
            return perPos
         }

         if (original == null || original.isAir) {
            return null
         }

         return remapFor(original.block, x, y, z)
      }

      @JvmStatic
      fun remapFor(block: Block, x: Int, y: Int, z: Int): BlockState? =
         if (LocationTracker.inDungeon && ConfigRemap.INSTANCE.isActiveForActiveConfig()) {
            ConfigRemap.INSTANCE.remapFor(block, x, y, z)
         } else {
            GlobalRemap.remapFor(block, x, y, z)
         }

      @JvmStatic
      fun getEffectReplacement(pos: BlockPos, original: BlockState): BlockState? {
         val perPos = ReplacementCache.getReplacement(pos)
         if (perPos != null) {
            return perPos
         }

         if (original.isAir) {
            return null
         }

         return remapFor(original.block, pos.x, pos.y, pos.z)
      }

      @JvmStatic
      fun swapFluid(original: BlockState): BlockState? {
         if (!WaterConfig.INSTANCE.lavaAsWater()) {
            return null
         }

         if (CosmeticBlocksTab.INSTANCE.onlyInDungeons.value && !LocationTracker.inDungeon) {
            return null
         }

         if (original.block !is LiquidBlock) {
            return null
         }

         if (original.fluidState.`is`(Fluids.LAVA) || original.fluidState.`is`(Fluids.FLOWING_LAVA)) {
            val level = original.getValue(LiquidBlock.LEVEL)
            return Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, level)
         }

         return null
      }

      @JvmStatic
      fun onPlayerInfoUpdate(packet: ClientboundPlayerInfoUpdatePacket) {
         LocationTracker.onPlayerInfoUpdate(packet)
      }

      @JvmStatic
      fun onSetObjective(packet: ClientboundSetObjectivePacket) {
         LocationTracker.onSetObjective(packet)
      }

      @JvmStatic
      fun onTeamPacket(packet: ClientboundSetPlayerTeamPacket) {
         LocationTracker.onTeamPacket(packet)
      }

      private var editModeKeyWasPressed = false
      private var placeModeKeyWasPressed = false
      private var worldEditKeyWasPressed = false
      private var lastDimension: String? = null
      private var lastEnabledState = false
      private var lastOnlyInDungeonsState = false
      private var lastKeepBlockSizeState = false
      private var lastLavaAsWaterState = false
      private var lastLookBlockPos: BlockPos? = null
      private val updatingConnections: ThreadLocal<Boolean> = ThreadLocal.withInitial { false }

      @Suppress("UNUSED_PARAMETER")
      private fun onEndClientTick(minecraft: Minecraft) {
         val dim = LocationTracker.getCurrentDimension()
         if (dim != lastDimension) {
            SaveScheduler.flushSave()
            if (dim != null && lastDimension != null) {
               LocationTracker.resetLocationState()
            }

            lastDimension = dim
            ReplacementCache.rebuildActiveCache()
            if (dim != null) {
               DirtySections.markAllCachedDirty()
            }
         }

         if (DirtySections.hasPending()) {
            DirtySections.drainDirtySections()
         }

         if (CosmeticBlocksTab.INSTANCE.enabled.value != lastEnabledState
            || CosmeticBlocksTab.INSTANCE.onlyInDungeons.value != lastOnlyInDungeonsState
            || CosmeticBlocksTab.INSTANCE.keepBlockSize.value != lastKeepBlockSizeState
            || WaterConfig.INSTANCE.lavaAsWater() != lastLavaAsWaterState
         ) {
            ReplacementCache.retextureCache.clear()
            ReplacementCache.originalShapeCache.clear()
            lastEnabledState = CosmeticBlocksTab.INSTANCE.enabled.value
            lastOnlyInDungeonsState = CosmeticBlocksTab.INSTANCE.onlyInDungeons.value
            lastKeepBlockSizeState = CosmeticBlocksTab.INSTANCE.keepBlockSize.value
            lastLavaAsWaterState = WaterConfig.INSTANCE.lavaAsWater()
            DirtySections.markAllCachedDirty()
         }

         ConfigSwitcher.INSTANCE.checkDungeonRevert()
         ConfigSwitcher.INSTANCE.checkConfigSwitch()
         ConfigSwitcher.INSTANCE.checkFloorAutoLoad()
         RoomProfileManager.checkSwitch()
         LocationTracker.updateDungeonCheck()
         if (LocationTracker.inDungeon
            && RoomScanner.isReady()
            && CosmeticBlocksTab.INSTANCE.roomScanning.value
            && RoomScanner.tick()
         ) {
            RoomStore.onRoomChanged(RoomScanner.currentRoom)
         }

         RoomOverrideManager.checkRoomOverride()

         if (SaveScheduler.needsSave
            && (System.currentTimeMillis() - SaveScheduler.lastEditTime >= 2000L
               || System.currentTimeMillis() - SaveScheduler.lastSaveTime >= 10000L)
         ) {
            ReplacementIO.INSTANCE.save()
         }

         if (SaveScheduler.needsRoomSave
            && (System.currentTimeMillis() - SaveScheduler.lastRoomEditTime >= 2000L
               || System.currentTimeMillis() - SaveScheduler.lastRoomSaveTime >= 10000L)
         ) {
            RoomStore.saveRoomBlocksAsync()
         }

         val hit = INSTANCE.mc.hitResult
         if (hit is BlockHitResult && hit.type == HitResult.Type.BLOCK) {
            lastLookBlockPos = hit.blockPos
         }

         val window = Minecraft.getInstance().window.handle()
         if (window != 0L
            && INSTANCE.mc.gui.screen() == null
            && CosmeticBlocksTab.INSTANCE.editModeKeybind.value != -1
         ) {
            val pressed = GLFW.glfwGetKey(window, CosmeticBlocksTab.INSTANCE.editModeKeybind.value) == 1
            if (pressed && !editModeKeyWasPressed) {
               INSTANCE.toggleEditMode()
            }

            editModeKeyWasPressed = pressed
         } else {
            editModeKeyWasPressed = false
         }

         if (window != 0L
            && INSTANCE.mc.gui.screen() == null
            && CosmeticBlocksTab.INSTANCE.placeModeKeybind.value != -1
         ) {
            val pressed = GLFW.glfwGetKey(window, CosmeticBlocksTab.INSTANCE.placeModeKeybind.value) == 1
            if (pressed && !placeModeKeyWasPressed) {
               CosmeticBlocksTab.INSTANCE.placeMode.value = !CosmeticBlocksTab.INSTANCE.placeMode.value
               ChatUtil.send(if (CosmeticBlocksTab.INSTANCE.placeMode.value) "Place mode §aON" else "Place mode §cOFF")
            }

            placeModeKeyWasPressed = pressed
         } else {
            placeModeKeyWasPressed = false
         }

         if (window != 0L
            && INSTANCE.mc.gui.screen() == null
            && CosmeticBlocksTab.INSTANCE.worldEditKeybind.value != -1
         ) {
            val pressed = GLFW.glfwGetKey(window, CosmeticBlocksTab.INSTANCE.worldEditKeybind.value) == 1
            if (pressed && !worldEditKeyWasPressed) {
               WorldEditMode.setWorldEditMode(!WorldEditMode.worldEditMode)
               ChatUtil.send(if (WorldEditMode.worldEditMode) "World edit mode §aON" else "World edit mode §cOFF")
            }

            worldEditKeyWasPressed = pressed
         } else {
            worldEditKeyWasPressed = false
         }

         if (WorldEditMode.worldEditMode) {
            WorldEditMode.tickWorldEditMode()
         }

         if (EditMode.editMode) {
            EditMode.tickEditMode()
         }

         Ruler.tick()
         Freecam.tick()
         CosmeticRenderer.emitGizmos()
      }
   }
}
