package com.cb.cosmetic;

import com.cb.config.tabs.BlockRemapsTab;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.dungeon.RoomDataKt;
import com.cb.dungeon.Rotation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.EndPortalBlock;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.Property.Value;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BlockStateCodec {
   @NotNull
   public static final BlockStateCodec INSTANCE = new BlockStateCodec();
   @NotNull
   private static final Map<String, String> baseToStair = new LinkedHashMap<>();
   @NotNull
   private static final Map<String, String> baseToSlab = new LinkedHashMap<>();

   private BlockStateCodec() {
   }

   private final Minecraft getMc() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      return mc;
   }

   @NotNull
   public final String rotateStateToNorth$cb_client(@NotNull String stateStr, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(stateStr, "stateStr");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return rotation != Rotation.SOUTH && rotation != Rotation.NONE ? this.rotateStateProperties(stateStr, rotation, true) : stateStr;
   }

   @NotNull
   public final String rotateStateFromNorth$cb_client(@NotNull String stateStr, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(stateStr, "stateStr");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      return rotation != Rotation.SOUTH && rotation != Rotation.NONE ? this.rotateStateProperties(stateStr, rotation, false) : stateStr;
   }

   private final String rotateStateProperties(String stateStr, Rotation rotation, boolean toNorth) {
      int bracketIdx = StringsKt.indexOf(stateStr, '[', 0, false);
      if (bracketIdx == -1) {
         return stateStr;
      }

      String blockId = stateStr.substring(0, bracketIdx);
      String propsStr = stateStr.substring(bracketIdx + 1, stateStr.length() - 1);
      String newProps = CollectionsKt.joinToString(
         StringsKt.split(propsStr, new String[]{","}, false, 0), ",", "", "", -1, "...", prop -> rotateProperty(toNorth, rotation, prop)
      );
      return blockId + "[" + newProps + "]";
   }

   @NotNull
   public final BlockState rotateBlockStateFromNorth$cb_client(@NotNull BlockState state, @NotNull Rotation rotation) {
      Intrinsics.checkNotNullParameter(state, "state");
      Intrinsics.checkNotNullParameter(rotation, "rotation");
      if (rotation != Rotation.SOUTH && rotation != Rotation.NONE) {
         BlockState result = state;

         for (Value entry : state.getValues().toList()) {
            Property prop = entry.property();
            String propName = prop.getName();
            switch (propName.hashCode()) {
               case -1282158630:
                  if (propName.equals("facing")) {
                     Comparable facing = (Comparable)state.getValue(prop);
                     if (facing instanceof Direction && ((Direction)facing).getAxis().isHorizontal()) {
                        Direction rotated = RoomDataKt.rotateAroundNorth((Direction)facing, rotation);
                        result = result.setValue(prop, rotated);
                     }
                  }
                  break;
               case 3008417:
                  if (propName.equals("axis")) {
                     Comparable axis = (Comparable)state.getValue(prop);
                     if (axis instanceof Axis && axis != Axis.Y) {
                        Axis rotatedAxis = RoomDataKt.rotateAroundNorth((Axis)axis, rotation);
                        result = result.setValue(prop, rotatedAxis);
                     }
                  }
            }
         }

         return result;
      } else {
         return state;
      }
   }

   @NotNull
   public final String normalizeBlockId$cb_client(@NotNull String input) {
      Intrinsics.checkNotNullParameter(input, "input");
      String lower = input.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(lower, "toLowerCase(...)");
      String cleaned = StringsKt.trim(lower).toString();
      return StringsKt.contains(cleaned, ":", false) ? cleaned : "minecraft:" + cleaned;
   }

   @Nullable
   public final BlockState resolveShapeState$cb_client(@NotNull String shape) {
      Intrinsics.checkNotNullParameter(shape, "shape");
      switch (shape.hashCode()) {
         case 3433320:
            if (shape.equals("pane")) {
               return Blocks.GLASS_PANE.defaultBlockState();
            }
            break;
         case 3641802:
            if (shape.equals("wall")) {
               return Blocks.COBBLESTONE_WALL.defaultBlockState();
            }
            break;
         case 97316913:
            if (shape.equals("fence")) {
               return Blocks.OAK_FENCE.defaultBlockState();
            }
      }

      return null;
   }

   @Nullable
   public final BlockState resolveBlockState(@NotNull String stateStr) {
      Intrinsics.checkNotNullParameter(stateStr, "stateStr");

      BlockState cleaned;
      try {
         String cleanedx;
         if (StringsKt.startsWith(stateStr, "!", false)) {
            cleanedx = stateStr.substring(1);
            Intrinsics.checkNotNullExpressionValue(cleanedx, "substring(...)");
         } else {
            cleanedx = stateStr;
         }

         int bracketIdx = StringsKt.indexOf(cleanedx, '[', 0, false);
         if (bracketIdx == -1) {
            Identifier loc = Identifier.parse(cleanedx);
            Intrinsics.checkNotNullExpressionValue(loc, "parse(...)");
            Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
            cleaned = block != null ? block.defaultBlockState() : null;
         } else {
            String blockId = cleanedx.substring(0, bracketIdx);
            Intrinsics.checkNotNullExpressionValue(blockId, "substring(...)");
            String propsStr = cleanedx.substring(bracketIdx + 1, cleanedx.length() - 1);
            Intrinsics.checkNotNullExpressionValue(propsStr, "substring(...)");
            Identifier loc = Identifier.parse(blockId);
            Intrinsics.checkNotNullExpressionValue(loc, "parse(...)");
            Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
            if (block == null) {
               return null;
            }

            BlockState state = block.defaultBlockState();
            Intrinsics.checkNotNullExpressionValue(state, "defaultBlockState(...)");

            for (String entry : StringsKt.split(propsStr, new String[]{","}, false, 0)) {
               int eqIdx = StringsKt.indexOf(entry, '=', 0, false);
               if (eqIdx != -1) {
                  String name = entry.substring(0, eqIdx);
                  Intrinsics.checkNotNullExpressionValue(name, "substring(...)");
                  String value = entry.substring(eqIdx + 1);
                  Intrinsics.checkNotNullExpressionValue(value, "substring(...)");
                  Property property = block.getStateDefinition().getProperty(name);
                  if (property != null) {
                     BlockState applied = this.applyPropertyValue(state, property, value);
                     if (applied != null) {
                        state = applied;
                     }
                  }
               }
            }

            cleaned = state;
         }
      } catch (Exception ignored) {
         cleaned = null;
      }

      return cleaned;
   }

   @Nullable
   public final BlockState computePlacementState$cb_client(@NotNull String blockId, @NotNull BlockHitResult hitResult) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      Intrinsics.checkNotNullParameter(hitResult, "hitResult");
      LocalPlayer player = this.getMc().player;
      if (player == null) {
         return null;
      }

      Identifier loc = Identifier.parse(this.normalizeBlockId$cb_client(blockId));
      Intrinsics.checkNotNullExpressionValue(loc, "parse(...)");
      Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (block == null) {
         return null;
      }

      ItemStack itemStack = new ItemStack(block.asItem());
      BlockPlaceContext context = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, itemStack, hitResult);
      return block.getStateForPlacement(context);
   }

   @NotNull
   public final String serializeBlockState$cb_client(@NotNull BlockState state) {
      Intrinsics.checkNotNullParameter(state, "state");
      String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
      Intrinsics.checkNotNullExpressionValue(blockId, "toString(...)");
      List properties = state.getValues().toList();
      if (properties.isEmpty()) {
         return blockId;
      }

      Intrinsics.checkNotNull(properties);
      String propsStr = CollectionsKt.joinToString(properties, ",", "", "", -1, "...", entry -> formatProperty((Value)entry));
      return blockId + "[" + propsStr + "]";
   }

   @NotNull
   public final <T extends Comparable<T>> String getPropertyValueName$cb_client(@NotNull Property<T> property, @NotNull Comparable<?> value) {
      Intrinsics.checkNotNullParameter(property, "property");
      Intrinsics.checkNotNullParameter(value, "value");
      String name = property.getName((T)value);
      Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
      return name;
   }

   private final <T extends Comparable<T>> BlockState applyPropertyValue(BlockState state, Property<T> property, String valueName) {
      Optional<T> value = property.getValue(valueName);
      return value.isPresent() ? state.setValue(property, value.get()) : null;
   }

   public final void buildVariantMaps$cb_client() {
      for (Identifier key : BuiltInRegistries.BLOCK.keySet()) {
         Block block = BuiltInRegistries.BLOCK.getOptional(key).orElse(null);
         if (block != null) {
            String path = key.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            String ns = key.getNamespace();
            Intrinsics.checkNotNullExpressionValue(ns, "getNamespace(...)");
            if (block instanceof StairBlock && StringsKt.endsWith(path, "_stairs", false)) {
               String baseName = StringsKt.removeSuffix(path, "_stairs");
               String baseId = this.resolveBaseBlock(ns, baseName);
               if (baseId != null) {
                  baseToStair.put(baseId, ns + ":" + path);
               }
            }

            if (block instanceof SlabBlock && StringsKt.endsWith(path, "_slab", false)) {
               String baseName = StringsKt.removeSuffix(path, "_slab");
               String baseId = this.resolveBaseBlock(ns, baseName);
               if (baseId != null) {
                  baseToSlab.put(baseId, ns + ":" + path);
               }
            }
         }
      }
   }

   private final String resolveBaseBlock(String ns, String baseName) {
      String[] candidates = new String[]{ns + ":" + baseName, ns + ":" + baseName + "s", ns + ":" + baseName + "_planks", ns + ":" + baseName + "_block"};

      for (String candidate : CollectionsKt.listOf(candidates)) {
         if (BuiltInRegistries.BLOCK.getOptional(Identifier.parse(candidate)).orElse(null) != null) {
            return candidate;
         }
      }

      return null;
   }

   @NotNull
   public final BlockState applyOriginalShape(@NotNull BlockState original, @NotNull BlockState replacement, int x, int y, int z) {
      Intrinsics.checkNotNullParameter(original, "original");
      Intrinsics.checkNotNullParameter(replacement, "replacement");
      if (!CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue() && !BlockRemapsTab.INSTANCE.getKeepShape().getValue()) {
         return replacement;
      }

      if (original.isAir()) {
         return replacement;
      }

      long packed = BlockPos.asLong(x, y, z);
      if (ReplacementCache.INSTANCE.getShapedPositions$cb_client().contains(packed)) {
         return replacement;
      }

      Block originalBlock = original.getBlock();
      Intrinsics.checkNotNullExpressionValue(originalBlock, "getBlock(...)");
      Block replacementBlock = replacement.getBlock();
      Intrinsics.checkNotNullExpressionValue(replacementBlock, "getBlock(...)");
      String replacementId = BuiltInRegistries.BLOCK.getKey(replacementBlock).toString();
      Intrinsics.checkNotNullExpressionValue(replacementId, "toString(...)");
      if (originalBlock instanceof StairBlock) {
         if (replacementBlock instanceof StairBlock) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            return this.copyMatchingProperties(original, replacement);
         }

         String stairId = baseToStair.get(replacementId);
         if (stairId != null) {
            BlockState stairState = this.resolveBlockState(stairId);
            if (stairState != null) {
               ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
               ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
               return this.copyMatchingProperties(original, stairState);
            }
         }

         if (!replacement.canOcclude()) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().put(packed, original);
            return replacement;
         } else {
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(packed, replacement);
            return original;
         }
      } else if (originalBlock instanceof SlabBlock) {
         if (replacementBlock instanceof SlabBlock) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            return this.copyMatchingProperties(original, replacement);
         }

         String slabId = baseToSlab.get(replacementId);
         if (slabId != null) {
            BlockState slabState = this.resolveBlockState(slabId);
            if (slabState != null) {
               ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
               ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
               return this.copyMatchingProperties(original, slabState);
            }
         }

         if (!replacement.canOcclude()) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().put(packed, original);
            return replacement;
         } else {
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(packed, replacement);
            return original;
         }
      } else if (!(originalBlock instanceof CrossCollisionBlock) && !(originalBlock instanceof WallBlock)) {
         if (!(originalBlock instanceof EndPortalBlock) && !(originalBlock instanceof EndPortalFrameBlock)) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            return replacement;
         } else if (replacementBlock instanceof EndPortalBlock || replacementBlock instanceof EndPortalFrameBlock) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            return this.copyMatchingProperties(original, replacement);
         } else if (!replacement.canOcclude()) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().put(packed, original);
            return replacement;
         } else {
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(packed, replacement);
            return original;
         }
      } else if (replacementBlock instanceof CrossCollisionBlock || replacementBlock instanceof WallBlock) {
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
         return this.copyMatchingProperties(original, replacement);
      } else if (!replacement.canOcclude()) {
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().put(packed, original);
         return replacement;
      } else {
         ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
         ReplacementCache.INSTANCE.getRetextureCache$cb_client().put(packed, replacement);
         return original;
      }
   }

   private final BlockState copyMatchingProperties(BlockState from, BlockState to) {
      BlockState result = to;

      for (Value entry : from.getValues().toList()) {
         Property prop = entry.property();
         if (result.hasProperty(prop)) {
            result = result.setValue(prop, from.getValue(prop));
         }
      }

      return result;
   }

   private static final CharSequence rotateProperty(boolean toNorth, Rotation rotation, String prop) {
      Intrinsics.checkNotNullParameter(prop, "prop");
      int eqIdx = StringsKt.indexOf(prop, '=', 0, false);
      if (eqIdx == -1) {
         return prop;
      }

      String name = prop.substring(0, eqIdx);
      Intrinsics.checkNotNullExpressionValue(name, "substring(...)");
      String value = prop.substring(eqIdx + 1);
      Intrinsics.checkNotNullExpressionValue(value, "substring(...)");
      if (Intrinsics.areEqual(name, "facing")) {
         Direction dir = Direction.byName(value);
         if (dir != null && dir.getAxis().isHorizontal()) {
            Direction rotated = toNorth ? RoomDataKt.rotateToNorth(dir, rotation) : RoomDataKt.rotateAroundNorth(dir, rotation);
            return name + "=" + rotated.getSerializedName();
         }

         return prop;
      } else if (Intrinsics.areEqual(name, "axis")) {
         Axis axis = Intrinsics.areEqual(value, "x") ? Axis.X : (Intrinsics.areEqual(value, "z") ? Axis.Z : null);
         return axis != null ? name + "=" + RoomDataKt.rotateToNorth(axis, rotation).getSerializedName() : prop;
      } else {
         return prop;
      }
   }

   private static final CharSequence formatProperty(Value entry) {
      String propName = entry.property().getName();
      Property property = entry.property();
      Intrinsics.checkNotNullExpressionValue(property, "property(...)");
      Comparable value = (Comparable)entry.value();
      Intrinsics.checkNotNullExpressionValue(value, "value(...)");
      return propName + "=" + INSTANCE.getPropertyValueName$cb_client(property, value);
   }
}
