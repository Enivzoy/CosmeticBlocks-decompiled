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
import kotlin.Metadata;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0010J\u001d\u0010\u0011\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0012J \u0010\u0013\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001d\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0000¢\u0006\u0002\b\u0019J\u0015\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0000¢\u0006\u0002\b\u001cJ\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001e\u001a\u00020\nH\u0000¢\u0006\u0002\b\u001fJ\u0010\u0010 \u001a\u0004\u0018\u00010\u00172\u0006\u0010\r\u001a\u00020\nJ\u001f\u0010!\u001a\u0004\u0018\u00010\u00172\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020$H\u0000¢\u0006\u0002\b%J\u0015\u0010&\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0017H\u0000¢\u0006\u0002\b'J7\u0010(\u001a\u00020\n\"\u000e\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0*2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H)0,2\n\u0010-\u001a\u0006\u0012\u0002\b\u00030*H\u0000¢\u0006\u0002\b.J8\u0010/\u001a\u0004\u0018\u00010\u0017\"\u000e\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0*2\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H)0,2\u0006\u00100\u001a\u00020\nH\u0002J\r\u00101\u001a\u000202H\u0000¢\u0006\u0002\b3J\u001a\u00104\u001a\u0004\u0018\u00010\n2\u0006\u00105\u001a\u00020\n2\u0006\u00106\u001a\u00020\nH\u0002J.\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u00172\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020;J\u0018\u0010>\u001a\u00020\u00172\u0006\u0010?\u001a\u00020\u00172\u0006\u0010@\u001a\u00020\u0017H\u0002R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006A",
   d2 = {
         "Lcom/cb/cosmetic/BlockStateCodec;",
         "",
         "<init>",
         "()V",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "baseToStair",
         "",
         "",
         "baseToSlab",
         "rotateStateToNorth",
         "stateStr",
         "rotation",
         "Lcom/cb/dungeon/Rotation;",
         "rotateStateToNorth$cb_client",
         "rotateStateFromNorth",
         "rotateStateFromNorth$cb_client",
         "rotateStateProperties",
         "toNorth",
         "",
         "rotateBlockStateFromNorth",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "state",
         "rotateBlockStateFromNorth$cb_client",
         "normalizeBlockId",
         "input",
         "normalizeBlockId$cb_client",
         "resolveShapeState",
         "shape",
         "resolveShapeState$cb_client",
         "resolveBlockState",
         "computePlacementState",
         "blockId",
         "hitResult",
         "Lnet/minecraft/world/phys/BlockHitResult;",
         "computePlacementState$cb_client",
         "serializeBlockState",
         "serializeBlockState$cb_client",
         "getPropertyValueName",
         "T",
         "",
         "property",
         "Lnet/minecraft/world/level/block/state/properties/Property;",
         "value",
         "getPropertyValueName$cb_client",
         "applyPropertyValue",
         "valueName",
         "buildVariantMaps",
         "",
         "buildVariantMaps$cb_client",
         "resolveBaseBlock",
         "ns",
         "baseName",
         "applyOriginalShape",
         "original",
         "replacement",
         "x",
         "",
         "y",
         "z",
         "copyMatchingProperties",
         "from",
         "to",
         "cb_client"
   }
)
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
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
      String[] var8 = new String[]{","};
      String newProps = CollectionsKt.joinToString(
         StringsKt.split(propsStr, var8, false, 0), ",", "", "", -1, "...", prop -> rotateStateProperties$lambda$0(toNorth, rotation, prop)
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
            String var8 = prop.getName();
            switch (var8.hashCode()) {
               case -1282158630:
                  if (var8.equals("facing")) {
                     Comparable facing = (Comparable)state.getValue(prop);
                     if (facing instanceof Direction && ((Direction)facing).getAxis().isHorizontal()) {
                        Direction var15 = RoomDataKt.rotateAroundNorth((Direction)facing, rotation);
                        result = result.setValue(prop, var15);
                     }
                  }
                  break;
               case 3008417:
                  if (var8.equals("axis")) {
                     Comparable axis = (Comparable)state.getValue(prop);
                     if (axis instanceof Axis && axis != Axis.Y) {
                        Axis var10002 = RoomDataKt.rotateAroundNorth((Axis)axis, rotation);
                        result = result.setValue(prop, var10002);
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
      String var10000 = input.toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(var10000, "toLowerCase(...)");
      String cleaned = StringsKt.trim(var10000).toString();
      return StringsKt.contains(cleaned, ":", false) ? cleaned : "minecraft:" + cleaned;
   }

   @Nullable
   public final BlockState resolveShapeState$cb_client(@NotNull String shape) {
      Intrinsics.checkNotNullParameter(shape, "shape");
      String var2 = shape;
      switch (var2.hashCode()) {
         case 3433320:
            if (var2.equals("pane")) {
               return Blocks.GLASS_PANE.defaultBlockState();
            }
            break;
         case 3641802:
            if (var2.equals("wall")) {
               return Blocks.COBBLESTONE_WALL.defaultBlockState();
            }
            break;
         case 97316913:
            if (var2.equals("fence")) {
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
            Identifier var30 = Identifier.parse(cleanedx);
            Intrinsics.checkNotNullExpressionValue(var30, "parse(...)");
            Identifier loc = var30;
            Block var31 = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
            cleaned = var31 != null ? var31.defaultBlockState() : null;
         } else {
            String blockId = cleanedx.substring(0, bracketIdx);
            Intrinsics.checkNotNullExpressionValue(blockId, "substring(...)");
            String propsStr = cleanedx.substring(bracketIdx + 1, cleanedx.length() - 1);
            Intrinsics.checkNotNullExpressionValue(propsStr, "substring(...)");
            Identifier var21 = Identifier.parse(blockId);
            Intrinsics.checkNotNullExpressionValue(var21, "parse(...)");
            Identifier loc = var21;
            Block var22 = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
            if (var22 == null) {
               return null;
            }

            Block block = var22;
            BlockState state = block.defaultBlockState();
            Intrinsics.checkNotNullExpressionValue(state, "defaultBlockState(...)");
            String[] entry = new String[]{","};

            for (String entryx : StringsKt.split(propsStr, entry, false, 0)) {
               int eqIdx = StringsKt.indexOf(entryx, '=', 0, false);
               if (eqIdx != -1) {
                  String name = entryx.substring(0, eqIdx);
                  Intrinsics.checkNotNullExpressionValue(name, "substring(...)");
                  String value = entryx.substring(eqIdx + 1);
                  Intrinsics.checkNotNullExpressionValue(value, "substring(...)");
                  Property var27 = block.getStateDefinition().getProperty(name);
                  if (var27 != null) {
                     Property property = var27;
                     BlockState applied = this.applyPropertyValue(state, property, value);
                     if (applied != null) {
                        state = applied;
                     }
                  }
               }
            }

            cleaned = state;
         }
      } catch (Exception var15/* $VF was: <unused var> */) {
         cleaned = null;
      }

      return cleaned;
   }

   @Nullable
   public final BlockState computePlacementState$cb_client(@NotNull String blockId, @NotNull BlockHitResult hitResult) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      Intrinsics.checkNotNullParameter(hitResult, "hitResult");
      LocalPlayer var10000 = this.getMc().player;
      if (var10000 == null) {
         return null;
      }

      LocalPlayer player = var10000;
      Identifier var8 = Identifier.parse(this.normalizeBlockId$cb_client(blockId));
      Intrinsics.checkNotNullExpressionValue(var8, "parse(...)");
      Identifier loc = var8;
      Block var9 = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (var9 == null) {
         return null;
      }

      Block block = var9;
      ItemStack itemStack = new ItemStack(block.asItem());
      BlockPlaceContext context = new BlockPlaceContext(player, InteractionHand.MAIN_HAND, itemStack, hitResult);
      return block.getStateForPlacement(context);
   }

   @NotNull
   public final String serializeBlockState$cb_client(@NotNull BlockState state) {
      Intrinsics.checkNotNullParameter(state, "state");
      String var10000 = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
      Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
      String blockId = var10000;
      List properties = state.getValues().toList();
      if (properties.isEmpty()) {
         return blockId;
      }

      Intrinsics.checkNotNull(properties);
      String propsStr = CollectionsKt.joinToString(properties, ",", "", "", -1, "...", entry -> serializeBlockState$lambda$0((Value)entry));
      return blockId + "[" + propsStr + "]";
   }

   @NotNull
   public final <T extends Comparable<T>> String getPropertyValueName$cb_client(@NotNull Property<T> property, @NotNull Comparable<?> value) {
      Intrinsics.checkNotNullParameter(property, "property");
      Intrinsics.checkNotNullParameter(value, "value");
      String var10000 = property.getName((T)value);
      Intrinsics.checkNotNullExpressionValue(var10000, "getName(...)");
      return var10000;
   }

   private final <T extends Comparable<T>> BlockState applyPropertyValue(BlockState state, Property<T> property, String valueName) {
      Optional<T> value = property.getValue(valueName);
      return value.isPresent() ? state.setValue(property, value.get()) : null;
   }

   public final void buildVariantMaps$cb_client() {
      for (Object var10000 : BuiltInRegistries.BLOCK.keySet()) {
         Intrinsics.checkNotNullExpressionValue(var10000, "next(...)");
         Identifier key = (Identifier)var10000;
         Block var10 = BuiltInRegistries.BLOCK.getOptional(key).orElse(null);
         if (var10 != null) {
            Block block = var10;
            String var11 = key.getPath();
            Intrinsics.checkNotNullExpressionValue(var11, "getPath(...)");
            String path = var11;
            String var12 = key.getNamespace();
            Intrinsics.checkNotNullExpressionValue(var12, "getNamespace(...)");
            String ns = var12;
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
      String[] var4 = new String[]{ns + ":" + baseName, ns + ":" + baseName + "s", ns + ":" + baseName + "_planks", ns + ":" + baseName + "_block"};

      for (String candidate : CollectionsKt.listOf(var4)) {
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

      Block var10000 = original.getBlock();
      Intrinsics.checkNotNullExpressionValue(var10000, "getBlock(...)");
      Block originalBlock = var10000;
      var10000 = replacement.getBlock();
      Intrinsics.checkNotNullExpressionValue(var10000, "getBlock(...)");
      Block replacementBlock = var10000;
      String var16 = BuiltInRegistries.BLOCK.getKey(replacementBlock).toString();
      Intrinsics.checkNotNullExpressionValue(var16, "toString(...)");
      String replacementId = var16;
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

   private static final CharSequence rotateStateProperties$lambda$0(boolean $toNorth, Rotation $rotation, String prop) {
      Intrinsics.checkNotNullParameter(prop, "prop");
      int eqIdx = StringsKt.indexOf(prop, '=', 0, false);
      if (eqIdx == -1) {
         return prop;
      }

      String name = prop.substring(0, eqIdx);
      Intrinsics.checkNotNullExpressionValue(name, "substring(...)");
      String value = prop.substring(eqIdx + 1);
      Intrinsics.checkNotNullExpressionValue(value, "substring(...)");
      String var6 = name;
      if (Intrinsics.areEqual(var6, "facing")) {
         Direction dir = Direction.byName(value);
         if (dir != null && dir.getAxis().isHorizontal()) {
            Direction rotated = $toNorth ? RoomDataKt.rotateToNorth(dir, $rotation) : RoomDataKt.rotateAroundNorth(dir, $rotation);
            return name + "=" + rotated.getSerializedName();
         }

         return prop;
      } else if (Intrinsics.areEqual(var6, "axis")) {
         Axis axis = Intrinsics.areEqual(value, "x") ? Axis.X : (Intrinsics.areEqual(value, "z") ? Axis.Z : null);
         return axis != null ? name + "=" + RoomDataKt.rotateToNorth(axis, $rotation).getSerializedName() : prop;
      } else {
         return prop;
      }
   }

   private static final CharSequence serializeBlockState$lambda$0(Value entry) {
      String var10000 = entry.property().getName();
      BlockStateCodec var10001 = INSTANCE;
      Property var10002 = entry.property();
      Intrinsics.checkNotNullExpressionValue(var10002, "property(...)");
      Comparable var10003 = (Comparable)entry.value();
      Intrinsics.checkNotNullExpressionValue(var10003, "value(...)");
      return var10000 + "=" + var10001.getPropertyValueName$cb_client(var10002, var10003);
   }
}
