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
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cJ\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\rJ\u0006\u0010!\u001a\u00020\tJ\u0016\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\rJ\u0006\u0010$\u001a\u00020\u001cJ\u001b\u0010%\u001a\u0010\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(\u0018\u00010&H\u0000¢\u0006\u0002\b)J\u000f\u0010*\u001a\u0004\u0018\u00010'H\u0000¢\u0006\u0002\b+JP\u0010,\u001a\u00020(2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\u0006\u00100\u001a\u00020.2\u0006\u00101\u001a\u00020.2\u0006\u00102\u001a\u00020.2\u0006\u00103\u001a\u00020.2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002052\u0006\u00107\u001a\u000205H\u0002J\r\u00108\u001a\u00020\u001cH\u0000¢\u0006\u0002\b9R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR$\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\r@@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001e\u0010\u0019\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\f¨\u0006:",
   d2 = {
         "Lcom/cb/cosmetic/EditMode;",
         "",
         "<init>",
         "()V",
         "mc",
         "Lnet/minecraft/client/Minecraft;",
         "getMc",
         "()Lnet/minecraft/client/Minecraft;",
         "value",
         "",
         "editMode",
         "getEditMode",
         "()Z",
         "",
         "selectedBlock",
         "getSelectedBlock",
         "()Ljava/lang/String;",
         "setSelectedBlock$cb_client",
         "(Ljava/lang/String;)V",
         "leftWasPressed",
         "rightWasPressed",
         "middleWasPressed",
         "currentShape",
         "getCurrentShape",
         "setCurrentShape",
         "fillMode",
         "getFillMode",
         "toggleFillMode",
         "",
         "toggleEditMode",
         "tryPickCosmeticAtCrosshair",
         "setBlock",
         "blockId",
         "hasExplicitProperties",
         "setBlockProperty",
         "property",
         "listBlockProperties",
         "findPhantomHitResult",
         "Lkotlin/Pair;",
         "Lnet/minecraft/core/BlockPos;",
         "Lnet/minecraft/core/Direction;",
         "findPhantomHitResult$cb_client",
         "findPhantomTarget",
         "findPhantomTarget$cb_client",
         "getEntryFace",
         "eyeX",
         "",
         "eyeY",
         "eyeZ",
         "dirX",
         "dirY",
         "dirZ",
         "blockX",
         "",
         "blockY",
         "blockZ",
         "tickEditMode",
         "tickEditMode$cb_client",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nEditMode.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EditMode.kt\ncom/cb/cosmetic/EditMode\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,404:1\n1205#2,2:405\n1282#2,4:407\n*S KotlinDebug\n*F\n+ 1 EditMode.kt\ncom/cb/cosmetic/EditMode\n*L\n170#1:405,2\n170#1:407,4\n*E\n"
)
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
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      return var10000;
   }

   public final boolean getEditMode() {
      return editMode;
   }

   @NotNull
   public final String getSelectedBlock() {
      return selectedBlock;
   }

   public final void setSelectedBlock$cb_client(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1/* $VF was: <set-?> */, "<set-?>");
      selectedBlock = var1/* $VF was: <set-?> */;
   }

   @Nullable
   public final String getCurrentShape() {
      return currentShape;
   }

   public final void setCurrentShape(@Nullable String var1) {
      currentShape = var1/* $VF was: <set-?> */;
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
         BlockPos var10000 = ((BlockHitResult)hitResult).getBlockPos();
         Intrinsics.checkNotNullExpressionValue(var10000, "getBlockPos(...)");
         BlockPos pos = var10000;
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
      String var10000;
      if (bracketIdx != -1) {
         var10000 = selectedBlock.substring(0, bracketIdx);
         Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
      } else {
         var10000 = selectedBlock;
      }

      String baseId = var10000;
      Identifier var19 = Identifier.parse(baseId);
      Intrinsics.checkNotNullExpressionValue(var19, "parse(...)");
      Identifier loc = var19;
      Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (block == null) {
         ChatUtil.INSTANCE.error("Current brush is invalid");
      } else {
         Property prop = block.getStateDefinition().getProperty(property);
         if (prop == null) {
            Collection var24 = block.getStateDefinition().getProperties();
            Intrinsics.checkNotNullExpressionValue(var24, "getProperties(...)");
            String available = CollectionsKt.joinToString(var24, ", ", "", "", -1, "...", it -> setBlockProperty$lambda$0((Property)it));
            ChatUtil.INSTANCE.error("'" + property + "' is not a valid property. Available: " + available);
         } else {
            Map props = new LinkedHashMap();
            if (bracketIdx != -1) {
               var10000 = selectedBlock.substring(bracketIdx + 1, selectedBlock.length() - 1);
               Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
               String propsStr = var10000;
               var10000 = propsStr;
               String[] blockName = new String[]{","};

               for (String entry : StringsKt.split(var10000, blockName, false, 0)) {
                  int eqIdx = StringsKt.indexOf(entry, '=', 0, false);
                  if (eqIdx != -1) {
                     String var10001 = entry.substring(0, eqIdx);
                     Intrinsics.checkNotNullExpressionValue(var10001, "substring(...)");
                     String var10002 = entry.substring(eqIdx + 1);
                     Intrinsics.checkNotNullExpressionValue(var10002, "substring(...)");
                     props.put(var10001, var10002);
                  }
               }
            }

            Optional var22 = prop.getValue(value);
            Intrinsics.checkNotNullExpressionValue(var22, "getValue(...)");
            Optional parsed = var22;
            if (parsed.isEmpty()) {
               List var23 = prop.getPossibleValues();
               Intrinsics.checkNotNullExpressionValue(var23, "getPossibleValues(...)");
               String allowed = CollectionsKt.joinToString(var23, ", ", "", "", -1, "...", it -> setBlockProperty$lambda$1(prop, (Comparable)it));
               ChatUtil.INSTANCE.error("'" + value + "' is not valid for '" + property + "'. Options: " + allowed);
            } else {
               props.put(property, value);
               String propsStr = CollectionsKt.joinToString(props.entrySet(), ",", "", "", -1, "...", it -> setBlockProperty$lambda$2((Entry)it));
               selectedBlock = baseId + "[" + propsStr + "]";
               String blockName = StringsKt.removePrefix(selectedBlock, "minecraft:");
               ChatUtil.INSTANCE.success("Brush set to " + blockName);
            }
         }
      }
   }

   public final void listBlockProperties() {
      int bracketIdx = StringsKt.indexOf(selectedBlock, '[', 0, false);
      String var10000;
      if (bracketIdx != -1) {
         var10000 = selectedBlock.substring(0, bracketIdx);
         Intrinsics.checkNotNullExpressionValue(var10000, "substring(...)");
      } else {
         var10000 = selectedBlock;
      }

      String baseId = var10000;
      Identifier var24 = Identifier.parse(baseId);
      Intrinsics.checkNotNullExpressionValue(var24, "parse(...)");
      Identifier loc = var24;
      Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
      if (block == null) {
         ChatUtil.INSTANCE.error("Current brush is invalid");
      } else {
         Map var10000m;
         label47: {
            BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(selectedBlock);
            if (state != null) {
               Stream var25 = state.getValues();
               if (var25 != null) {
                  List var26 = var25.toList();
                  if (var26 != null) {
                     Iterable $this$associate$iv = var26;
                     int $i$f$associate = 0;
                     int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
                     Iterable $this$associateTo$iv$iv = $this$associate$iv;
                     Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                     int $i$f$associateTo = 0;

                     for (Object element$iv$iv : $this$associateTo$iv$iv) {
                        Map var18 = destination$iv$iv;
                        Value it = (Value)element$iv$iv;
                        int var20/* $VF was: $i$a$-associate-EditMode$listBlockProperties$stateProps$1 */ = 0;
                        Pair var23 = TuplesKt.to(it.property(), it.value());
                        var18.put(var23.getFirst(), var23.getSecond());
                     }

                     var10000m = destination$iv$iv;
                     break label47;
                  }
               }
            }

            var10000m = MapsKt.emptyMap();
         }

         Map stateProps = var10000m;
         Collection var28 = block.getStateDefinition().getProperties();
         Intrinsics.checkNotNullExpressionValue(var28, "getProperties(...)");
         Collection props = var28;
         if (props.isEmpty()) {
            ChatUtil.INSTANCE.warning(StringsKt.removePrefix(baseId, "minecraft:") + " has no properties");
         } else {
            ChatUtil.INSTANCE.success("Properties for " + StringsKt.removePrefix(baseId, "minecraft:") + ":");

            for (Object var29 : props) {
               Intrinsics.checkNotNullExpressionValue(var29, "next(...)");
               Property p = (Property)var29;
               String var31;
               if (stateProps.containsKey(p)) {
                  BlockStateCodec var30 = BlockStateCodec.INSTANCE;
                  Object var10002 = stateProps.get(p);
                  Intrinsics.checkNotNull(var10002, "null cannot be cast to non-null type kotlin.Comparable<kotlin.Any>");
                  var31 = var30.getPropertyValueName$cb_client(p, (Comparable<?>)var10002);
               } else {
                  var31 = "?";
               }

               String current = var31;
               List var32 = p.getPossibleValues();
               Intrinsics.checkNotNullExpressionValue(var32, "getPossibleValues(...)");
               String options = CollectionsKt.joinToString(var32, ", ", "", "", -1, "...", it -> listBlockProperties$lambda$1(p, (Comparable)it));
               ChatUtil.INSTANCE.send("  " + p.getName() + "=" + current + " §8[" + options + "]");
            }
         }
      }
   }

   @Nullable
   public final Pair<BlockPos, Direction> findPhantomHitResult$cb_client() {
      LocalPlayer var10000 = this.getMc().player;
      if (var10000 == null) {
         return null;
      }

      LocalPlayer player = var10000;
      Vec3 var17 = player.getEyePosition(1.0F);
      Intrinsics.checkNotNullExpressionValue(var17, "getEyePosition(...)");
      Vec3 eyePos = var17;
      Vec3 var18 = player.getLookAngle();
      Intrinsics.checkNotNullExpressionValue(var18, "getLookAngle(...)");
      Vec3 lookVec = var18;
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
      Pair var10000 = this.findPhantomHitResult$cb_client();
      return var10000 != null ? (BlockPos)var10000.getFirst() : null;
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
                  boolean var50;
                  label164: {
                     ClientLevel level = this.getMc().level;
                     BlockState hitState = level != null ? level.getBlockState(((BlockHitResult)hitResult).getBlockPos()) : null;
                     hitIsFull = hitState != null && hitState.isCollisionShapeFullBlock(level, ((BlockHitResult)hitResult).getBlockPos());
                     BlockPos var40 = hitIsFull
                        ? ((BlockHitResult)hitResult).getBlockPos().relative(((BlockHitResult)hitResult).getDirection())
                        : ((BlockHitResult)hitResult).getBlockPos();
                     Intrinsics.checkNotNull(var40);
                     pos = var40;
                     if (level != null) {
                        BlockState var49 = level.getBlockState(pos);
                        if (var49 != null) {
                           var50 = var49.isAir();
                           break label164;
                        }
                     }

                     var50 = true;
                  }

                  boolean isAir = var50;
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
                  ChatUtil var51 = ChatUtil.INSTANCE;
                  WorldEditMode var10001 = WorldEditMode.INSTANCE;
                  BlockPos var10002 = ((BlockHitResult)hitResult).getBlockPos();
                  Intrinsics.checkNotNullExpressionValue(var10002, "getBlockPos(...)");
                  var51.send(var10001.floodFill(var10002, selectedBlock));
               } else {
                  BlockPos var52 = ((BlockHitResult)hitResult).getBlockPos();
                  Intrinsics.checkNotNullExpressionValue(var52, "getBlockPos(...)");
                  BlockPos pos = var52;
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
                  boolean var48;
                  label148: {
                     Pair blockName = phantomHit;
                     phantomPos = (BlockPos)blockName.component1();
                     face = (Direction)blockName.component2();
                     BlockPos var10000 = phantomPos.relative(face);
                     Intrinsics.checkNotNullExpressionValue(var10000, "relative(...)");
                     pos = var10000;
                     ClientLevel level = this.getMc().level;
                     if (level != null) {
                        BlockState var47 = level.getBlockState(pos);
                        if (var47 != null) {
                           var48 = var47.isAir();
                           break label148;
                        }
                     }

                     var48 = true;
                  }

                  boolean isAir = var48;
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
               BlockPos var53 = ((BlockHitResult)hitResult).getBlockPos();
               Intrinsics.checkNotNullExpressionValue(var53, "getBlockPos(...)");
               BlockPos pos = var53;
               removed = ReplacementStore.INSTANCE.removeReplacement(pos);
               removedPos = pos;
               if (!removed) {
                  removedPos = pos.relative(((BlockHitResult)hitResult).getDirection());
                  ReplacementStore var54 = ReplacementStore.INSTANCE;
                  Intrinsics.checkNotNull(removedPos);
                  removed = var54.removeReplacement(removedPos);
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
               BlockPos var55 = ((BlockHitResult)hitResult).getBlockPos();
               Intrinsics.checkNotNullExpressionValue(var55, "getBlockPos(...)");
               BlockPos pos = var55;
               BlockState replacement = ReplacementCache.INSTANCE.getReplacementAt(pos.getX(), pos.getY(), pos.getZ(), false);
               BlockState var56 = replacement;
               if (replacement == null) {
                  ClientLevel var57 = this.getMc().level;
                  var56 = var57 != null ? var57.getBlockState(pos) : null;
               }

               BlockState state = var56;
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

   private static final CharSequence setBlockProperty$lambda$0(Property it) {
      Intrinsics.checkNotNullParameter(it, "it");
      String var10000 = it.getName();
      Intrinsics.checkNotNullExpressionValue(var10000, "getName(...)");
      return var10000;
   }

   private static final CharSequence setBlockProperty$lambda$1(Property $prop, Comparable it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client($prop, it);
   }

   private static final CharSequence setBlockProperty$lambda$2(Entry it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return it.getKey() + "=" + it.getValue();
   }

   private static final CharSequence listBlockProperties$lambda$1(Property $p, Comparable it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client($p, it);
   }
}
