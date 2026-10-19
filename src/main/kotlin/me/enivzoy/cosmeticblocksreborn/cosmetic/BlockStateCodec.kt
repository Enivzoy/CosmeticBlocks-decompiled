package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.Locale
import me.enivzoy.cosmeticblocksreborn.config.tabs.CosmeticBlocksTab
import me.enivzoy.cosmeticblocksreborn.dungeon.Rotation
import me.enivzoy.cosmeticblocksreborn.dungeon.rotateAroundNorth
import me.enivzoy.cosmeticblocksreborn.dungeon.rotateToNorth
import net.minecraft.client.Minecraft
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Direction.Axis
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CrossCollisionBlock
import net.minecraft.world.level.block.EndPortalBlock
import net.minecraft.world.level.block.EndPortalFrameBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.WallBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.BlockHitResult

object BlockStateCodec {
   /**
    * Erased stand-in for any `Property<T : Comparable<T>>` value type. Casting through
    * this marker satisfies the Kotlin bound on `Property` while remaining a no-op at
    * runtime (everything is erased), so it mirrors the raw-type casts the Java sources
    * relied on and works for every MC property type without naming them.
    */
   private interface RawValue : Comparable<RawValue>

   private val baseToStair = LinkedHashMap<String, String>()
   private val baseToSlab = LinkedHashMap<String, String>()

   private val mc: Minecraft
      get() = Minecraft.getInstance()

   internal fun rotateStateToNorth(stateStr: String, rotation: Rotation): String =
      if (rotation != Rotation.SOUTH && rotation != Rotation.NONE) this.rotateStateProperties(stateStr, rotation, true) else stateStr

   internal fun rotateStateFromNorth(stateStr: String, rotation: Rotation): String =
      if (rotation != Rotation.SOUTH && rotation != Rotation.NONE) this.rotateStateProperties(stateStr, rotation, false) else stateStr

   private fun rotateStateProperties(stateStr: String, rotation: Rotation, toNorth: Boolean): String {
      val bracketIdx = stateStr.indexOf('[')
      if (bracketIdx == -1) {
         return stateStr
      }

      val blockId = stateStr.substring(0, bracketIdx)
      val propsStr = stateStr.substring(bracketIdx + 1, stateStr.length - 1)
      val newProps = propsStr.split(",").joinToString(",") { rotateProperty(toNorth, rotation, it) }
      return blockId + "[" + newProps + "]"
   }

   internal fun rotateBlockStateFromNorth(state: BlockState, rotation: Rotation): BlockState {
      if (rotation == Rotation.SOUTH || rotation == Rotation.NONE) {
         return state
      }

      var result = state
      for (entry in state.values.toList()) {
         val prop = entry.property()
         when (prop.name) {
            "facing" -> {
               val facing = state.getValue(prop)
               if (facing is Direction && facing.axis.isHorizontal) {
                  result = this.setDirection(result, prop, facing.rotateAroundNorth(rotation))
               }
            }

            "axis" -> {
               val axis = state.getValue(prop)
               if (axis is Axis && axis != Axis.Y) {
                  result = this.setAxis(result, prop, axis.rotateAroundNorth(rotation))
               }
            }
         }
      }

      return result
   }

   internal fun normalizeBlockId(input: String): String {
      val lower = input.lowercase(Locale.ROOT)
      val cleaned = lower.trim()
      return if (cleaned.contains(":")) cleaned else "minecraft:$cleaned"
   }

   internal fun resolveShapeState(shape: String): BlockState? = when (shape) {
      "pane" -> Blocks.GLASS_PANE.defaultBlockState()
      "wall" -> Blocks.COBBLESTONE_WALL.defaultBlockState()
      "fence" -> Blocks.OAK_FENCE.defaultBlockState()
      else -> null
   }

   fun resolveBlockState(stateStr: String): BlockState? {
      return try {
         val cleaned = if (stateStr.startsWith("!")) stateStr.substring(1) else stateStr
         val bracketIdx = cleaned.indexOf('[')
         if (bracketIdx == -1) {
            val loc = Identifier.parse(cleaned)
            val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null)
            block?.defaultBlockState()
         } else {
            val blockId = cleaned.substring(0, bracketIdx)
            val propsStr = cleaned.substring(bracketIdx + 1, cleaned.length - 1)
            val loc = Identifier.parse(blockId)
            val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null) ?: return null
            var state = block.defaultBlockState()

            for (entry in propsStr.split(",")) {
               val eqIdx = entry.indexOf('=')
               if (eqIdx != -1) {
                  val name = entry.substring(0, eqIdx)
                  val value = entry.substring(eqIdx + 1)
                  val property = block.stateDefinition.getProperty(name)
                  if (property != null) {
                     val applied = this.applyPropertyValue(state, property, value)
                     if (applied != null) {
                        state = applied
                     }
                  }
               }
            }

            state
         }
      } catch (ignored: Exception) {
         null
      }
   }

   internal fun computePlacementState(blockId: String, hitResult: BlockHitResult): BlockState? {
      val player = this.mc.player ?: return null
      val loc = Identifier.parse(this.normalizeBlockId(blockId))
      val block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null) ?: return null
      val itemStack = ItemStack(block.asItem())
      val context = BlockPlaceContext(player, InteractionHand.MAIN_HAND, itemStack, hitResult)
      return block.getStateForPlacement(context)
   }

   internal fun serializeBlockState(state: BlockState): String {
      val blockId = BuiltInRegistries.BLOCK.getKey(state.block).toString()
      val properties = state.values.toList()
      if (properties.isEmpty()) {
         return blockId
      }

      val propsStr = properties.joinToString(",") { entry ->
         val propName = entry.property().name
         propName + "=" + this.getPropertyValueName(entry.property(), entry.value())
      }

      return blockId + "[" + propsStr + "]"
   }

   @Suppress("UNCHECKED_CAST")
   internal fun getPropertyValueName(property: Property<*>, value: Comparable<*>): String =
      (property as Property<RawValue>).getName(value as RawValue)

   private fun applyPropertyValue(state: BlockState, property: Property<*>, valueName: String): BlockState? {
      val value = property.getValue(valueName)
      return if (value.isPresent) this.setValueUnchecked(state, property, value.get()) else null
   }

   internal fun buildVariantMaps() {
      for (key in BuiltInRegistries.BLOCK.keySet()) {
         val block = BuiltInRegistries.BLOCK.getOptional(key).orElse(null)
         if (block != null) {
            val path = key.path
            val ns = key.namespace
            if (block is StairBlock && path.endsWith("_stairs")) {
               val baseName = path.removeSuffix("_stairs")
               val baseId = this.resolveBaseBlock(ns, baseName)
               if (baseId != null) {
                  baseToStair[baseId] = ns + ":" + path
               }
            }

            if (block is SlabBlock && path.endsWith("_slab")) {
               val baseName = path.removeSuffix("_slab")
               val baseId = this.resolveBaseBlock(ns, baseName)
               if (baseId != null) {
                  baseToSlab[baseId] = ns + ":" + path
               }
            }
         }
      }
   }

   private fun resolveBaseBlock(ns: String, baseName: String): String? {
      val candidates = listOf(ns + ":" + baseName, ns + ":" + baseName + "s", ns + ":" + baseName + "_planks", ns + ":" + baseName + "_block")

      for (candidate in candidates) {
         if (BuiltInRegistries.BLOCK.getOptional(Identifier.parse(candidate)).orElse(null) != null) {
            return candidate
         }
      }

      return null
   }

   fun applyOriginalShape(original: BlockState, replacement: BlockState, x: Int, y: Int, z: Int): BlockState {
      if (!CosmeticBlocksTab.INSTANCE.keepBlockSize.value && !CosmeticBlocks.INSTANCE.remapKeepShapeEnabled()) {
         return replacement
      }

      if (original.isAir) {
         return replacement
      }

      val packed = BlockPos.asLong(x, y, z)
      if (ReplacementCache.shapedPositions.contains(packed)) {
         return replacement
      }

      val originalBlock = original.block
      val replacementBlock = replacement.block
      val replacementId = BuiltInRegistries.BLOCK.getKey(replacementBlock).toString()
      if (originalBlock is StairBlock) {
         if (replacementBlock is StairBlock) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache.remove(packed)
            return this.copyMatchingProperties(original, replacement)
         }

         val stairId = baseToStair[replacementId]
         if (stairId != null) {
            val stairState = this.resolveBlockState(stairId)
            if (stairState != null) {
               ReplacementCache.retextureCache.remove(packed)
               ReplacementCache.originalShapeCache.remove(packed)
               return this.copyMatchingProperties(original, stairState)
            }
         }

         return if (!replacement.canOcclude()) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache[packed] = original
            replacement
         } else {
            ReplacementCache.originalShapeCache.remove(packed)
            ReplacementCache.retextureCache[packed] = replacement
            original
         }
      } else if (originalBlock is SlabBlock) {
         if (replacementBlock is SlabBlock) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache.remove(packed)
            return this.copyMatchingProperties(original, replacement)
         }

         val slabId = baseToSlab[replacementId]
         if (slabId != null) {
            val slabState = this.resolveBlockState(slabId)
            if (slabState != null) {
               ReplacementCache.retextureCache.remove(packed)
               ReplacementCache.originalShapeCache.remove(packed)
               return this.copyMatchingProperties(original, slabState)
            }
         }

         return if (!replacement.canOcclude()) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache[packed] = original
            replacement
         } else {
            ReplacementCache.originalShapeCache.remove(packed)
            ReplacementCache.retextureCache[packed] = replacement
            original
         }
      } else if (originalBlock !is CrossCollisionBlock && originalBlock !is WallBlock) {
         if (originalBlock !is EndPortalBlock && originalBlock !is EndPortalFrameBlock) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache.remove(packed)
            return replacement
         } else if (replacementBlock is EndPortalBlock || replacementBlock is EndPortalFrameBlock) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache.remove(packed)
            return this.copyMatchingProperties(original, replacement)
         } else if (!replacement.canOcclude()) {
            ReplacementCache.retextureCache.remove(packed)
            ReplacementCache.originalShapeCache[packed] = original
            return replacement
         } else {
            ReplacementCache.originalShapeCache.remove(packed)
            ReplacementCache.retextureCache[packed] = replacement
            return original
         }
      } else if (replacementBlock is CrossCollisionBlock || replacementBlock is WallBlock) {
         ReplacementCache.retextureCache.remove(packed)
         ReplacementCache.originalShapeCache.remove(packed)
         return this.copyMatchingProperties(original, replacement)
      } else if (!replacement.canOcclude()) {
         ReplacementCache.retextureCache.remove(packed)
         ReplacementCache.originalShapeCache[packed] = original
         return replacement
      } else {
         ReplacementCache.originalShapeCache.remove(packed)
         ReplacementCache.retextureCache[packed] = replacement
         return original
      }
   }

   private fun copyMatchingProperties(from: BlockState, to: BlockState): BlockState {
      var result = to

      for (entry in from.values.toList()) {
         val prop = entry.property()
         if (result.hasProperty(prop)) {
            result = this.setValueUnchecked(result, prop, from.getValue(prop))
         }
      }

      return result
   }

   @Suppress("UNCHECKED_CAST")
   private fun setDirection(state: BlockState, prop: Property<*>, value: Direction): BlockState =
      state.setValue(prop as Property<Direction>, value)

   @Suppress("UNCHECKED_CAST")
   private fun setAxis(state: BlockState, prop: Property<*>, value: Axis): BlockState =
      state.setValue(prop as Property<Axis>, value)

   @Suppress("UNCHECKED_CAST")
   private fun setValueUnchecked(state: BlockState, prop: Property<*>, value: Comparable<*>): BlockState =
      state.setValue(prop as Property<RawValue>, value as RawValue)

   private fun rotateProperty(toNorth: Boolean, rotation: Rotation, prop: String): String {
      val eqIdx = prop.indexOf('=')
      if (eqIdx == -1) {
         return prop
      }

      val name = prop.substring(0, eqIdx)
      val value = prop.substring(eqIdx + 1)
      if (name == "facing") {
         val dir = Direction.byName(value)
         if (dir != null && dir.axis.isHorizontal) {
            val rotated = if (toNorth) dir.rotateToNorth(rotation) else dir.rotateAroundNorth(rotation)
            return name + "=" + rotated.serializedName
         }

         return prop
      } else if (name == "axis") {
         val axis = if (value == "x") Axis.X else if (value == "z") Axis.Z else null
         return axis?.let { name + "=" + it.rotateToNorth(rotation).serializedName } ?: prop
      } else {
         return prop
      }
   }
}
