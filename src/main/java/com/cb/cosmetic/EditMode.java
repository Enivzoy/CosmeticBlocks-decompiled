package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.utils.ChatUtil;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.Property.Value;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

public final class EditMode {
   @NotNull
   public static final EditMode INSTANCE = new EditMode();
   private static boolean editMode;
   @NotNull
   private static String selectedBlock = "minecraft:stone";
   private static boolean leftWasPressed;
   private static boolean rightWasPressed;
   private static boolean middleWasPressed;
   @Nullable
   private static String currentShape;
   private static boolean fillMode;

   private EditMode() {
   }

   private final Minecraft getMc() {
      Minecraft instance = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
      return instance;
   }

   public final boolean getEditMode() {
      return editMode;
   }

   @NotNull
   public final String getSelectedBlock() {
      return selectedBlock;
   }

   public final void setSelectedBlock$cb_client(@NotNull String value) {
      Intrinsics.checkNotNullParameter(value, "<set-?>");
      selectedBlock = value;
   }

   @Nullable
   public final String getCurrentShape() {
      return currentShape;
   }

   public final void setCurrentShape(@Nullable String value) {
      currentShape = value;
   }

   public final boolean getFillMode() {
      return fillMode;
   }

   public final void toggleFillMode() {
      fillMode = !fillMode;
      if (fillMode) {
         ChatUtil.INSTANCE
            .success(
               "Fill mode ON — R-click flood-fills connected same-type blocks (limit "
                  + (int)CosmeticBlocksTab.INSTANCE.getFillModeLimit().getValue().doubleValue()
                  + ")"
            );
      } else {
         ChatUtil.INSTANCE.success("Fill mode OFF");
      }
   }

   public final void toggleEditMode() {
      if (editMode) {
         editMode = false;
         leftWasPressed = false;
         rightWasPressed = false;
         middleWasPressed = false;
         ReplacementIO.INSTANCE.save();
         if (SaveScheduler.INSTANCE.getNeedsRoomSave$cb_client()) {
            RoomStore.INSTANCE.forceRoomSave();
         }

         ChatUtil.INSTANCE.success("Edit mode OFF");
      } else {
         editMode = true;
         CosmeticBlocksTab.INSTANCE.getEnabled().setValue(true);
         String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
         ChatUtil.INSTANCE.success("Edit mode ON — brush: " + blockName);
         ChatUtil.INSTANCE.send("R-click: place, L-click: remove, M-click: pick, 'cb em' to stop");
      }
   }

   public final boolean tryPickCosmeticAtCrosshair() {
      if (!CosmeticBlocksTab.INSTANCE.getEnabled().getValue()) {
         return false;
      }

      HitResult hitResult = this.getMc().hitResult;
      if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == Type.BLOCK) {
         BlockPos pos = ((BlockHitResult)hitResult).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
         BlockState replacement = ReplacementCache.INSTANCE.getReplacementAt(pos.getX(), pos.getY(), pos.getZ(), false);
         if (replacement != null) {
            selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(replacement);
            ChatUtil.INSTANCE.success("Brush set to " + StringsKt.removePrefix(selectedBlock, "minecraft:"));
            return true;
         }
      }

      if (CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue()) {
         BlockPos phantomPos = this.findPhantomTarget$cb_client();
         if (phantomPos != null) {
            BlockState state = ReplacementCache.INSTANCE.getReplacementAt(phantomPos.getX(), phantomPos.getY(), phantomPos.getZ(), false);
            if (state != null) {
               selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
               ChatUtil.INSTANCE.success("Brush set to " + StringsKt.removePrefix(selectedBlock, "minecraft:"));
               return true;
            }
         }
      }

      return false;
   }

   public final void setBlock(@NotNull String blockId) {
      Intrinsics.checkNotNullParameter(blockId, "blockId");
      String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
      BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
      if (state == null) {
         ChatUtil.INSTANCE.error("Unknown block '" + blockId + "'");
      } else {
         selectedBlock = normalized;
         String blockName = StringsKt.removePrefix(normalized, "minecraft:");
         ChatUtil.INSTANCE.success("Brush set to " + blockName);
      }
   }

   public final boolean hasExplicitProperties() {
      return StringsKt.contains(selectedBlock, '[', false);
   }

   public final void setBlockProperty(@NotNull String property, @NotNull String value) {
      Intrinsics.checkNotNullParameter(property, "property");
      Intrinsics.checkNotNullParameter(value, "value");
      int bracketIdx = StringsKt.indexOf(selectedBlock, '[', 0, false);
      String baseId;
      if (bracketIdx != -1) {
         baseId = selectedBlock.substring(0, bracketIdx);
         Intrinsics.checkNotNullExpressionValue(baseId, "substring(...)");
      } else {
         baseId = selectedBlock;
      }

      Identifier loc = Identifier.parse(baseId);
      Intrinsics.checkNotNullExpressionValue(loc, "parse(...)");
      Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (block == null) {
         ChatUtil.INSTANCE.error("Current brush is invalid");
      } else {
         Property prop = block.getStateDefinition().getProperty(property);
         if (prop == null) {
            Collection properties = block.getStateDefinition().getProperties();
            Intrinsics.checkNotNullExpressionValue(properties, "getProperties(...)");
            String available = CollectionsKt.joinToString(properties, ", ", "", "", -1, "...", it -> propertyName((Property)it));
            ChatUtil.INSTANCE.error("'" + property + "' is not a valid property. Available: " + available);
         } else {
            Map props = new LinkedHashMap();
            if (bracketIdx != -1) {
               String propsStr = selectedBlock.substring(bracketIdx + 1, selectedBlock.length() - 1);
               Intrinsics.checkNotNullExpressionValue(propsStr, "substring(...)");
               String[] delimiters = new String[]{","};

               for (String entry : StringsKt.split(propsStr, delimiters, false, 0)) {
                  int eqIdx = StringsKt.indexOf(entry, '=', 0, false);
                  if (eqIdx != -1) {
                     String propKey = entry.substring(0, eqIdx);
                     Intrinsics.checkNotNullExpressionValue(propKey, "substring(...)");
                     String propValue = entry.substring(eqIdx + 1);
                     Intrinsics.checkNotNullExpressionValue(propValue, "substring(...)");
                     props.put(propKey, propValue);
                  }
               }
            }

            Optional parsed = prop.getValue(value);
            Intrinsics.checkNotNullExpressionValue(parsed, "getValue(...)");
            if (parsed.isEmpty()) {
               List possibleValues = prop.getPossibleValues();
               Intrinsics.checkNotNullExpressionValue(possibleValues, "getPossibleValues(...)");
               String allowed = CollectionsKt.joinToString(possibleValues, ", ", "", "", -1, "...", it -> propertyValueName(prop, (Comparable)it));
               ChatUtil.INSTANCE.error("'" + value + "' is not valid for '" + property + "'. Options: " + allowed);
            } else {
               props.put(property, value);
               String propsStr = CollectionsKt.joinToString(props.entrySet(), ",", "", "", -1, "...", it -> formatEntry((Entry)it));
               selectedBlock = baseId + "[" + propsStr + "]";
               String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
               ChatUtil.INSTANCE.success("Brush set to " + blockName);
            }
         }
      }
   }

   public final void listBlockProperties() {
      int bracketIdx = StringsKt.indexOf(selectedBlock, '[', 0, false);
      String baseId;
      if (bracketIdx != -1) {
         baseId = selectedBlock.substring(0, bracketIdx);
         Intrinsics.checkNotNullExpressionValue(baseId, "substring(...)");
      } else {
         baseId = selectedBlock;
      }

      Identifier loc = Identifier.parse(baseId);
      Intrinsics.checkNotNullExpressionValue(loc, "parse(...)");
      Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (block == null) {
         ChatUtil.INSTANCE.error("Current brush is invalid");
      } else {
         Map stateProps;
         label47: {
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(selectedBlock);
            if (state != null) {
               Stream values = state.getValues();
               if (values != null) {
                  List valuesList = values.toList();
                  if (valuesList != null) {
                     int initialCapacity = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(valuesList, 10)), 16);
                     Map statePropsMap = new LinkedHashMap(initialCapacity);

                     for (Object element : valuesList) {
                        Value propertyValue = (Value)element;
                        Pair pair = TuplesKt.to(propertyValue.property(), propertyValue.value());
                        statePropsMap.put(pair.getFirst(), pair.getSecond());
                     }

                     stateProps = statePropsMap;
                     break label47;
                  }
               }
            }

            stateProps = MapsKt.emptyMap();
         }

         Collection props = block.getStateDefinition().getProperties();
         Intrinsics.checkNotNullExpressionValue(props, "getProperties(...)");
         if (props.isEmpty()) {
            ChatUtil.INSTANCE.warning(StringsKt.removePrefix(baseId, "minecraft:") + " has no properties");
         } else {
            ChatUtil.INSTANCE.success("Properties for " + StringsKt.removePrefix(baseId, "minecraft:") + ":");

            for (Object element : props) {
               Intrinsics.checkNotNullExpressionValue(element, "next(...)");
               Property p = (Property)element;
               String current;
               if (stateProps.containsKey(p)) {
                  BlockStateCodec codec = BlockStateCodec.INSTANCE;
                  Object storedValue = stateProps.get(p);
                  Intrinsics.checkNotNull(storedValue, "null cannot be cast to non-null type kotlin.Comparable<kotlin.Any>");
                  current = codec.getPropertyValueName$cb_client(p, (Comparable<?>)storedValue);
               } else {
                  current = "?";
               }

               List possibleValues = p.getPossibleValues();
               Intrinsics.checkNotNullExpressionValue(possibleValues, "getPossibleValues(...)");
               String options = CollectionsKt.joinToString(possibleValues, ", ", "", "", -1, "...", it -> formatPropertyValue(p, (Comparable)it));
               ChatUtil.INSTANCE.send("  " + p.getName() + "=" + current + " §8[" + options + "]");
            }
         }
      }
   }

   @Nullable
   public final Pair<BlockPos, Direction> findPhantomHitResult$cb_client() {
      LocalPlayer player = this.getMc().player;
      if (player == null) {
         return null;
      }

      Vec3 eyePos = player.getEyePosition(1.0F);
      Intrinsics.checkNotNullExpressionValue(eyePos, "getEyePosition(...)");
      Vec3 lookVec = player.getLookAngle();
      Intrinsics.checkNotNullExpressionValue(lookVec, "getLookAngle(...)");
      double reach = 5.0;
      double step = 0.15;
      Set checked = new LinkedHashSet();

      for (double dist = 0.0; dist <= reach; dist += step) {
         int bx = (int)Math.floor(eyePos.x + lookVec.x * dist);
         int by = (int)Math.floor(eyePos.y + lookVec.y * dist);
         int bz = (int)Math.floor(eyePos.z + lookVec.z * dist);
         long packed = BlockPos.asLong(bx, by, bz);
         if (checked.add(packed) && ReplacementCache.INSTANCE.getPhantomPositions$cb_client().contains(packed)) {
            Direction face = this.getEntryFace(eyePos.x, eyePos.y, eyePos.z, lookVec.x, lookVec.y, lookVec.z, bx, by, bz);
            return new Pair<>(new BlockPos(bx, by, bz), face);
         }
      }

      return null;
   }

   @Nullable
   public final BlockPos findPhantomTarget$cb_client() {
      Pair hit = this.findPhantomHitResult$cb_client();
      return hit != null ? (BlockPos)hit.getFirst() : null;
   }

   private final Direction getEntryFace(double eyeX, double eyeY, double eyeZ, double dirX, double dirY, double dirZ, int blockX, int blockY, int blockZ) {
      double minX = blockX;
      double minY = blockY;
      double minZ = blockZ;
      double maxX = minX + 1.0;
      double maxY = minY + 1.0;
      double maxZ = minZ + 1.0;
      double tNear = Double.NEGATIVE_INFINITY;
      Direction entryFace = Direction.NORTH;
      if (dirX != 0.0) {
         double t1 = (minX - eyeX) / dirX;
         double t2 = (maxX - eyeX) / dirX;
         double tEntry = Math.min(t1, t2);
         if (tEntry > tNear) {
            tNear = tEntry;
            entryFace = dirX > 0.0 ? Direction.WEST : Direction.EAST;
         }
      }

      if (dirY != 0.0) {
         double t1 = (minY - eyeY) / dirY;
         double t2 = (maxY - eyeY) / dirY;
         double tEntry = Math.min(t1, t2);
         if (tEntry > tNear) {
            tNear = tEntry;
            entryFace = dirY > 0.0 ? Direction.DOWN : Direction.UP;
         }
      }

      if (dirZ != 0.0) {
         double t1 = (minZ - eyeZ) / dirZ;
         double t2 = (maxZ - eyeZ) / dirZ;
         double tEntry = Math.min(t1, t2);
         if (tEntry > tNear) {
            entryFace = dirZ > 0.0 ? Direction.NORTH : Direction.SOUTH;
         }
      }

      return entryFace;
   }

   public final void tickEditMode$cb_client() {
      if (this.getMc().gui.screen() == null) {
         long window = GLFW.glfwGetCurrentContext();
         boolean leftPressed = GLFW.glfwGetMouseButton(window, 0) == 1;
         boolean rightPressed = GLFW.glfwGetMouseButton(window, 1) == 1;
         boolean middlePressed = GLFW.glfwGetMouseButton(window, 2) == 1;
         if (rightPressed && !rightWasPressed) {
            HitResult hitResult = this.getMc().hitResult;
            boolean placeMode = CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue();
            boolean hasExplicit = this.hasExplicitProperties();
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == Type.BLOCK) {
               if (placeMode) {
                  boolean hitIsFull;
                  BlockPos pos;
                  boolean isAir;
                  label164: {
                     ClientLevel level = this.getMc().level;
                     BlockState hitState = level != null ? level.getBlockState(((BlockHitResult)hitResult).getBlockPos()) : null;
                     hitIsFull = hitState != null && hitState.isCollisionShapeFullBlock(level, ((BlockHitResult)hitResult).getBlockPos());
                     pos = hitIsFull
                        ? ((BlockHitResult)hitResult).getBlockPos().relative(((BlockHitResult)hitResult).getDirection())
                        : ((BlockHitResult)hitResult).getBlockPos();
                     Intrinsics.checkNotNull(pos);
                     if (level != null) {
                        BlockState targetState = level.getBlockState(pos);
                        if (targetState != null) {
                           isAir = targetState.isAir();
                           break label164;
                        }
                     }

                     isAir = true;
                  }
                  boolean isNonFull = !hitIsFull && Intrinsics.areEqual(pos, ((BlockHitResult)hitResult).getBlockPos());
                  boolean hasCFME = ReplacementCache.INSTANCE.getActiveCache$cb_client().containsKey(BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ()));
                  if ((isAir || isNonFull) && !hasCFME) {
                     if (hasExplicit) {
                        ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, true);
                     } else {
                        BlockState placementState = BlockStateCodec.INSTANCE.computePlacementState$cb_client(selectedBlock, (BlockHitResult)hitResult);
                        if (placementState != null) {
                           ReplacementStore.INSTANCE.setReplacementState(pos, placementState, true);
                        } else {
                           ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, true);
                        }
                     }

                     String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
                     ChatUtil.INSTANCE.success(pos.getX() + "," + pos.getY() + "," + pos.getZ() + " -> " + blockName);
                  }
               } else if (fillMode) {
                  ChatUtil chat = ChatUtil.INSTANCE;
                  WorldEditMode worldEdit = WorldEditMode.INSTANCE;
                  BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                  Intrinsics.checkNotNullExpressionValue(blockPos, "getBlockPos(...)");
                  chat.send(worldEdit.floodFill(blockPos, selectedBlock));
               } else {
                  BlockPos pos = ((BlockHitResult)hitResult).getBlockPos();
                  Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
                  if (hasExplicit) {
                     ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, false);
                  } else {
                     BlockState placementState = BlockStateCodec.INSTANCE.computePlacementState$cb_client(selectedBlock, (BlockHitResult)hitResult);
                     if (placementState != null) {
                        ReplacementStore.INSTANCE.setReplacementState(pos, placementState, false);
                     } else {
                        ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, false);
                     }
                  }

                  String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
                  ChatUtil.INSTANCE.success(pos.getX() + "," + pos.getY() + "," + pos.getZ() + " -> " + blockName);
               }
            } else if (placeMode) {
               Pair phantomHit = this.findPhantomHitResult$cb_client();
               if (phantomHit != null) {
                  BlockPos phantomPos;
                  Direction face;
                  BlockPos pos;
                  boolean isAir;
                  label148: {
                     phantomPos = (BlockPos)phantomHit.component1();
                     face = (Direction)phantomHit.component2();
                     pos = phantomPos.relative(face);
                     Intrinsics.checkNotNullExpressionValue(pos, "relative(...)");
                     ClientLevel level = this.getMc().level;
                     if (level != null) {
                        BlockState state = level.getBlockState(pos);
                        if (state != null) {
                           isAir = state.isAir();
                           break label148;
                        }
                     }

                     isAir = true;
                  }
                  boolean hasCFME = ReplacementCache.INSTANCE.getActiveCache$cb_client().containsKey(BlockPos.asLong(pos.getX(), pos.getY(), pos.getZ()));
                  if (isAir && !hasCFME) {
                     if (hasExplicit) {
                        ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, true);
                     } else {
                        BlockHitResult syntheticHit = new BlockHitResult(
                           new Vec3(phantomPos.getX() + 0.5, phantomPos.getY() + 0.5, phantomPos.getZ() + 0.5), face, phantomPos, false
                        );
                        BlockState placementState = BlockStateCodec.INSTANCE.computePlacementState$cb_client(selectedBlock, syntheticHit);
                        if (placementState != null) {
                           ReplacementStore.INSTANCE.setReplacementState(pos, placementState, true);
                        } else {
                           ReplacementStore.INSTANCE.setReplacement(pos, selectedBlock, true);
                        }
                     }

                     String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
                     ChatUtil.INSTANCE.success(pos.getX() + "," + pos.getY() + "," + pos.getZ() + " -> " + blockName);
                  }
               }
            }
         }

         if (leftPressed && !leftWasPressed) {
            boolean removed = false;
            BlockPos removedPos = null;
            HitResult hitResult = this.getMc().hitResult;
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == Type.BLOCK) {
               BlockPos pos = ((BlockHitResult)hitResult).getBlockPos();
               Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
               removed = ReplacementStore.INSTANCE.removeReplacement(pos);
               removedPos = pos;
               if (!removed) {
                  removedPos = pos.relative(((BlockHitResult)hitResult).getDirection());
                  ReplacementStore store = ReplacementStore.INSTANCE;
                  Intrinsics.checkNotNull(removedPos);
                  removed = store.removeReplacement(removedPos);
               }
            }

            if (!removed) {
               BlockPos phantomPos = this.findPhantomTarget$cb_client();
               if (phantomPos != null) {
                  removed = ReplacementStore.INSTANCE.removeReplacement(phantomPos);
                  removedPos = phantomPos;
               }
            }

            if (removed && removedPos != null) {
               ChatUtil.INSTANCE.success("Removed at " + removedPos.getX() + "," + removedPos.getY() + "," + removedPos.getZ());
            }
         }

         if (middlePressed && !middleWasPressed) {
            boolean picked = false;
            HitResult hitResult = this.getMc().hitResult;
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == Type.BLOCK) {
               BlockPos pos = ((BlockHitResult)hitResult).getBlockPos();
               Intrinsics.checkNotNullExpressionValue(pos, "getBlockPos(...)");
               BlockState replacement = ReplacementCache.INSTANCE.getReplacementAt(pos.getX(), pos.getY(), pos.getZ(), false);
               BlockState state = replacement;
               if (replacement == null) {
                  ClientLevel level = this.getMc().level;
                  state = level != null ? level.getBlockState(pos) : null;
               }
               if (state != null && !state.isAir()) {
                  selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
                  String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
                  ChatUtil.INSTANCE.success("Brush set to " + blockName);
                  picked = true;
               }
            }

            if (!picked && CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue()) {
               BlockPos phantomPos = this.findPhantomTarget$cb_client();
               if (phantomPos != null) {
                  BlockState state = ReplacementCache.INSTANCE.getReplacementAt(phantomPos.getX(), phantomPos.getY(), phantomPos.getZ(), false);
                  if (state != null) {
                     selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
                     String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
                     ChatUtil.INSTANCE.success("Brush set to " + blockName);
                  }
               }
            }
         }

         leftWasPressed = leftPressed;
         rightWasPressed = rightPressed;
         middleWasPressed = middlePressed;
      }
   }

   private static final CharSequence propertyName(Property it) {
      Intrinsics.checkNotNullParameter(it, "it");
      String name = it.getName();
      Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
      return name;
   }

   private static final CharSequence propertyValueName(Property property, Comparable it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client(property, it);
   }

   private static final CharSequence formatEntry(Entry it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getKey() + "=" + it.getValue();
   }

   private static final CharSequence formatPropertyValue(Property property, Comparable it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client(property, it);
   }
}
