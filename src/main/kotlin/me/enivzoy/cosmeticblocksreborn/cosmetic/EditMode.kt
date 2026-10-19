package me.enivzoy.cosmeticblocksreborn.cosmetic

import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.utils.ChatUtil
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.HitResult
import net.minecraft.world.phys.Vec3
import org.lwjgl.glfw.GLFW

object EditMode {
   private var editModeState = false
   private var selectedBlockState = "minecraft:stone"
   private var leftWasPressed = false
   private var rightWasPressed = false
   private var middleWasPressed = false

   @Volatile
   private var shape: String? = null

   private var fillModeState = false

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   val editMode: Boolean
      get() = editModeState

   val selectedBlock: String
      get() = selectedBlockState

   var currentShape: String?
      get() = shape
      set(value) {
         shape = value
      }

   val fillMode: Boolean
      get() = fillModeState

   internal fun setSelectedBlock(value: String) {
      selectedBlockState = value
   }

   fun toggleFillMode() {
      fillModeState = !fillModeState
      if (fillModeState) {
         ChatUtil.success(
            "Fill mode ON — R-click flood-fills connected same-type blocks (limit " + CosmeticBlocksTab.INSTANCE.fillModeLimit.value.toInt() + ")"
         )
      } else {
         ChatUtil.success("Fill mode OFF")
      }
   }

   fun toggleEditMode() {
      if (editModeState) {
         editModeState = false
         leftWasPressed = false
         rightWasPressed = false
         middleWasPressed = false
         ReplacementIO.INSTANCE.save()
         if (SaveScheduler.needsRoomSave) {
            RoomStore.forceRoomSave()
         }

         ChatUtil.success("Edit mode OFF")
      } else {
         editModeState = true
         CosmeticBlocksTab.INSTANCE.enabled.value = true
         val blockName = selectedBlockState.removePrefix("minecraft:")
         ChatUtil.success("Edit mode ON — brush: $blockName")
         ChatUtil.send("R-click: place, L-click: remove, M-click: pick, 'cb em' to stop")
      }
   }

   fun tryPickCosmeticAtCrosshair(): Boolean {
      if (!CosmeticBlocksTab.INSTANCE.enabled.value) {
         return false
      }

      val hitResult = this.mc.hitResult
      if (hitResult is BlockHitResult && hitResult.type == HitResult.Type.BLOCK) {
         val pos = hitResult.blockPos
         val replacement = ReplacementCache.getReplacementAt(pos.x, pos.y, pos.z, false)
         if (replacement != null) {
            selectedBlockState = BlockStateCodec.serializeBlockState(replacement)
            ChatUtil.success("Brush set to " + selectedBlock.removePrefix("minecraft:"))
            return true
         }
      }

      if (CosmeticBlocksTab.INSTANCE.placeMode.value) {
         val phantomPos = this.findPhantomTarget()
         if (phantomPos != null) {
            val state = ReplacementCache.getReplacementAt(phantomPos.x, phantomPos.y, phantomPos.z, false)
            if (state != null) {
               selectedBlockState = BlockStateCodec.serializeBlockState(state)
               ChatUtil.success("Brush set to " + selectedBlock.removePrefix("minecraft:"))
               return true
            }
         }
      }

      return false
   }

   fun setBlock(blockId: String) {
      val normalized = BlockStateCodec.normalizeBlockId(blockId)
      val state = BlockStateCodec.resolveBlockState(normalized)
      if (state == null) {
         ChatUtil.error("Unknown block '$blockId'")
      } else {
         selectedBlockState = normalized
         ChatUtil.success("Brush set to " + normalized.removePrefix("minecraft:"))
      }
   }

   fun hasExplicitProperties(): Boolean = selectedBlock.contains("[")

   fun setBlockProperty(property: String, value: String) {
      val bracketIdx = selectedBlock.indexOf('[')
      val baseId = if (bracketIdx != -1) selectedBlock.substring(0, bracketIdx) else selectedBlock
      val loc = Identifier.parse(baseId)
      val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null)
      if (block == null) {
         ChatUtil.error("Current brush is invalid")
         return
      }

      val prop = block.stateDefinition.getProperty(property)
      if (prop == null) {
         val properties = block.stateDefinition.properties
         val available = properties.joinToString(", ") { it.name }
         ChatUtil.error("'$property' is not a valid property. Available: $available")
         return
      }

      val props = LinkedHashMap<String, String>()
      if (bracketIdx != -1) {
         val propsStr = selectedBlock.substring(bracketIdx + 1, selectedBlock.length - 1)

         for (entry in propsStr.split(",")) {
            val eqIdx = entry.indexOf('=')
            if (eqIdx != -1) {
               props[entry.substring(0, eqIdx)] = entry.substring(eqIdx + 1)
            }
         }
      }

      val parsed = prop.getValue(value)
      if (parsed.isEmpty) {
         val possibleValues = prop.possibleValues
         val allowed = possibleValues.joinToString(", ") { BlockStateCodec.getPropertyValueName(prop, it) }
         ChatUtil.error("'$value' is not valid for '$property'. Options: $allowed")
      } else {
         props[property] = value
         val propsStr = props.entries.joinToString(",") { it.key + "=" + it.value }
         selectedBlockState = baseId + "[" + propsStr + "]"
         ChatUtil.success("Brush set to " + selectedBlock.removePrefix("minecraft:"))
      }
   }

   fun listBlockProperties() {
      val bracketIdx = selectedBlock.indexOf('[')
      val baseId = if (bracketIdx != -1) selectedBlock.substring(0, bracketIdx) else selectedBlock
      val loc = Identifier.parse(baseId)
      val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null)
      if (block == null) {
         ChatUtil.error("Current brush is invalid")
         return
      }

      val stateProps = HashMap<Property<*>, Comparable<*>>()
      val resolvedState = BlockStateCodec.resolveBlockState(selectedBlock)
      if (resolvedState != null) {
         for (value in resolvedState.values.toList()) {
            stateProps[value.property()] = value.value()
         }
      }

      val props = block.stateDefinition.properties
      if (props.isEmpty()) {
         ChatUtil.warning(baseId.removePrefix("minecraft:") + " has no properties")
         return
      }

      ChatUtil.success("Properties for " + baseId.removePrefix("minecraft:") + ":")

      for (p in props) {
         val storedValue = stateProps[p]
         val current = if (storedValue != null) BlockStateCodec.getPropertyValueName(p, storedValue) else "?"
         val options = p.possibleValues.joinToString(", ") { BlockStateCodec.getPropertyValueName(p, it) }
         ChatUtil.send("  " + p.name + "=" + current + " §8[" + options + "]")
      }
   }

   internal fun findPhantomHitResult(): Pair<BlockPos, Direction>? {
      val player = this.mc.player ?: return null
      val eyePos = player.getEyePosition(1.0F)
      val lookVec = player.lookAngle
      val reach = 5.0
      val step = 0.15
      val checked = LinkedHashSet<Long>()

      var dist = 0.0
      while (dist <= reach) {
         val bx = Math.floor(eyePos.x + lookVec.x * dist).toInt()
         val by = Math.floor(eyePos.y + lookVec.y * dist).toInt()
         val bz = Math.floor(eyePos.z + lookVec.z * dist).toInt()
         val packed = BlockPos.asLong(bx, by, bz)
         if (checked.add(packed) && ReplacementCache.phantomPositions.contains(packed)) {
            val face = this.getEntryFace(eyePos.x, eyePos.y, eyePos.z, lookVec.x, lookVec.y, lookVec.z, bx, by, bz)
            return BlockPos(bx, by, bz) to face
         }

         dist += step
      }

      return null
   }

   internal fun findPhantomTarget(): BlockPos? = this.findPhantomHitResult()?.first

   private fun getEntryFace(
      eyeX: Double, eyeY: Double, eyeZ: Double, dirX: Double, dirY: Double, dirZ: Double, blockX: Int, blockY: Int, blockZ: Int
   ): Direction {
      val minX = blockX.toDouble()
      val minY = blockY.toDouble()
      val minZ = blockZ.toDouble()
      val maxX = minX + 1.0
      val maxY = minY + 1.0
      val maxZ = minZ + 1.0
      var tNear = Double.NEGATIVE_INFINITY
      var entryFace = Direction.NORTH

      if (dirX != 0.0) {
         val tEntry = Math.min((minX - eyeX) / dirX, (maxX - eyeX) / dirX)
         if (tEntry > tNear) {
            tNear = tEntry
            entryFace = if (dirX > 0.0) Direction.WEST else Direction.EAST
         }
      }

      if (dirY != 0.0) {
         val tEntry = Math.min((minY - eyeY) / dirY, (maxY - eyeY) / dirY)
         if (tEntry > tNear) {
            tNear = tEntry
            entryFace = if (dirY > 0.0) Direction.DOWN else Direction.UP
         }
      }

      if (dirZ != 0.0) {
         val tEntry = Math.min((minZ - eyeZ) / dirZ, (maxZ - eyeZ) / dirZ)
         if (tEntry > tNear) {
            entryFace = if (dirZ > 0.0) Direction.NORTH else Direction.SOUTH
         }
      }

      return entryFace
   }

   internal fun tickEditMode() {
      if (this.mc.gui.screen() != null) {
         return
      }

      val window = mc.window.handle()
      val leftPressed = GLFW.glfwGetMouseButton(window, 0) == 1
      val rightPressed = GLFW.glfwGetMouseButton(window, 1) == 1
      val middlePressed = GLFW.glfwGetMouseButton(window, 2) == 1

      if (rightPressed && !rightWasPressed) {
         val hitResult = this.mc.hitResult
         val placeMode = CosmeticBlocksTab.INSTANCE.placeMode.value
         val hasExplicit = this.hasExplicitProperties()
         if (hitResult is BlockHitResult && hitResult.type == HitResult.Type.BLOCK) {
            if (placeMode) {
               val level = this.mc.level
               val hitState = level?.getBlockState(hitResult.blockPos)
               val hitIsFull = hitState != null && level != null && hitState.isCollisionShapeFullBlock(level, hitResult.blockPos)
               val pos = if (hitIsFull) hitResult.blockPos.relative(hitResult.direction) else hitResult.blockPos
               val targetState = level?.getBlockState(pos)
               val isAir = targetState?.isAir ?: true
               val isNonFull = !hitIsFull && pos == hitResult.blockPos
               val hasCFME = ReplacementCache.activeCache.containsKey(BlockPos.asLong(pos.x, pos.y, pos.z))
               if ((isAir || isNonFull) && !hasCFME) {
                  if (hasExplicit) {
                     ReplacementStore.setReplacement(pos, selectedBlock, true)
                  } else {
                     val placementState = BlockStateCodec.computePlacementState(selectedBlock, hitResult)
                     if (placementState != null) {
                        ReplacementStore.setReplacementState(pos, placementState, true)
                     } else {
                        ReplacementStore.setReplacement(pos, selectedBlock, true)
                     }
                  }

                  ChatUtil.success(pos.x.toString() + "," + pos.y + "," + pos.z + " -> " + selectedBlock.removePrefix("minecraft:"))
               }
            } else if (fillMode) {
               ChatUtil.send(WorldEditMode.floodFill(hitResult.blockPos, selectedBlock))
            } else {
               val pos = hitResult.blockPos
               if (hasExplicit) {
                  ReplacementStore.setReplacement(pos, selectedBlock, false)
               } else {
                  val placementState = BlockStateCodec.computePlacementState(selectedBlock, hitResult)
                  if (placementState != null) {
                     ReplacementStore.setReplacementState(pos, placementState, false)
                  } else {
                     ReplacementStore.setReplacement(pos, selectedBlock, false)
                  }
               }

               ChatUtil.success(pos.x.toString() + "," + pos.y + "," + pos.z + " -> " + selectedBlock.removePrefix("minecraft:"))
            }
         } else if (placeMode) {
            val phantomHit = this.findPhantomHitResult()
            if (phantomHit != null) {
               val (phantomPos, face) = phantomHit
               val pos = phantomPos.relative(face)
               val level = this.mc.level
               val state = level?.getBlockState(pos)
               val isAir = state?.isAir ?: true
               val hasCFME = ReplacementCache.activeCache.containsKey(BlockPos.asLong(pos.x, pos.y, pos.z))
               if (isAir && !hasCFME) {
                  if (hasExplicit) {
                     ReplacementStore.setReplacement(pos, selectedBlock, true)
                  } else {
                     val syntheticHit = BlockHitResult(
                        Vec3(phantomPos.x + 0.5, phantomPos.y + 0.5, phantomPos.z + 0.5), face, phantomPos, false
                     )
                     val placementState = BlockStateCodec.computePlacementState(selectedBlock, syntheticHit)
                     if (placementState != null) {
                        ReplacementStore.setReplacementState(pos, placementState, true)
                     } else {
                        ReplacementStore.setReplacement(pos, selectedBlock, true)
                     }
                  }

                  ChatUtil.success(pos.x.toString() + "," + pos.y + "," + pos.z + " -> " + selectedBlock.removePrefix("minecraft:"))
               }
            }
         }
      }

      if (leftPressed && !leftWasPressed) {
         var removed = false
         var removedPos: BlockPos? = null
         val hitResult = this.mc.hitResult
         if (hitResult is BlockHitResult && hitResult.type == HitResult.Type.BLOCK) {
            val pos = hitResult.blockPos
            removed = ReplacementStore.removeReplacement(pos)
            removedPos = pos
            if (!removed) {
               removedPos = pos.relative(hitResult.direction)
               removed = ReplacementStore.removeReplacement(removedPos)
            }
         }

         if (!removed) {
            val phantomPos = this.findPhantomTarget()
            if (phantomPos != null) {
               removed = ReplacementStore.removeReplacement(phantomPos)
               removedPos = phantomPos
            }
         }

         if (removed && removedPos != null) {
            ChatUtil.success("Removed at " + removedPos.x + "," + removedPos.y + "," + removedPos.z)
         }
      }

      if (middlePressed && !middleWasPressed) {
         var picked = false
         val hitResult = this.mc.hitResult
         if (hitResult is BlockHitResult && hitResult.type == HitResult.Type.BLOCK) {
            val pos = hitResult.blockPos
            val replacement = ReplacementCache.getReplacementAt(pos.x, pos.y, pos.z, false)
            val state: BlockState? = replacement ?: this.mc.level?.getBlockState(pos)
            if (state != null && !state.isAir) {
               selectedBlockState = BlockStateCodec.serializeBlockState(state)
               ChatUtil.success("Brush set to " + selectedBlock.removePrefix("minecraft:"))
               picked = true
            }
         }

         if (!picked && CosmeticBlocksTab.INSTANCE.placeMode.value) {
            val phantomPos = this.findPhantomTarget()
            if (phantomPos != null) {
               val state = ReplacementCache.getReplacementAt(phantomPos.x, phantomPos.y, phantomPos.z, false)
               if (state != null) {
                  selectedBlockState = BlockStateCodec.serializeBlockState(state)
                  ChatUtil.success("Brush set to " + selectedBlock.removePrefix("minecraft:"))
               }
            }
         }
      }

      leftWasPressed = leftPressed
      rightWasPressed = rightPressed
      middleWasPressed = middlePressed
   }
}
