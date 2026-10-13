/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.EndPortalBlock
 *  net.minecraft.world.level.block.EndPortalFrameBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.Property$Value
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.cosmetic;

import com.cb.config.tabs.BlockRemapsTab;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.ReplacementCache;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
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
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u0010J\u001d\u0010\u0011\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u0012J \u0010\u0013\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001d\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u00a2\u0006\u0002\b\u0019J\u0015\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0000\u00a2\u0006\u0002\b\u001cJ\u0017\u0010\u001d\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001e\u001a\u00020\nH\u0000\u00a2\u0006\u0002\b\u001fJ\u0010\u0010 \u001a\u0004\u0018\u00010\u00172\u0006\u0010\r\u001a\u00020\nJ\u001f\u0010!\u001a\u0004\u0018\u00010\u00172\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020$H\u0000\u00a2\u0006\u0002\b%J\u0015\u0010&\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0017H\u0000\u00a2\u0006\u0002\b'J7\u0010(\u001a\u00020\n\"\u000e\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0*2\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H)0,2\n\u0010-\u001a\u0006\u0012\u0002\b\u00030*H\u0000\u00a2\u0006\u0002\b.J8\u0010/\u001a\u0004\u0018\u00010\u0017\"\u000e\b\u0000\u0010)*\b\u0012\u0004\u0012\u0002H)0*2\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010+\u001a\b\u0012\u0004\u0012\u0002H)0,2\u0006\u00100\u001a\u00020\nH\u0002J\r\u00101\u001a\u000202H\u0000\u00a2\u0006\u0002\b3J\u001a\u00104\u001a\u0004\u0018\u00010\n2\u0006\u00105\u001a\u00020\n2\u0006\u00106\u001a\u00020\nH\u0002J.\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u00172\u0006\u00109\u001a\u00020\u00172\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020;J\u0018\u0010>\u001a\u00020\u00172\u0006\u0010?\u001a\u00020\u00172\u0006\u0010@\u001a\u00020\u0017H\u0002R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2={"Lcom/cb/cosmetic/BlockStateCodec;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "baseToStair", "", "", "baseToSlab", "rotateStateToNorth", "stateStr", "rotation", "Lcom/cb/dungeon/Rotation;", "rotateStateToNorth$cb_client", "rotateStateFromNorth", "rotateStateFromNorth$cb_client", "rotateStateProperties", "toNorth", "", "rotateBlockStateFromNorth", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "rotateBlockStateFromNorth$cb_client", "normalizeBlockId", "input", "normalizeBlockId$cb_client", "resolveShapeState", "shape", "resolveShapeState$cb_client", "resolveBlockState", "computePlacementState", "blockId", "hitResult", "Lnet/minecraft/world/phys/BlockHitResult;", "computePlacementState$cb_client", "serializeBlockState", "serializeBlockState$cb_client", "getPropertyValueName", "T", "", "property", "Lnet/minecraft/world/level/block/state/properties/Property;", "value", "getPropertyValueName$cb_client", "applyPropertyValue", "valueName", "buildVariantMaps", "", "buildVariantMaps$cb_client", "resolveBaseBlock", "ns", "baseName", "applyOriginalShape", "original", "replacement", "x", "", "y", "z", "copyMatchingProperties", "from", "to", "cb_client"})
public final class BlockStateCodec {
    @NotNull
    public static final BlockStateCodec INSTANCE = new BlockStateCodec();
    @NotNull
    private static final Map<String, String> baseToStair = new LinkedHashMap();
    @NotNull
    private static final Map<String, String> baseToSlab = new LinkedHashMap();

    private BlockStateCodec() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    @NotNull
    public final String rotateStateToNorth$cb_client(@NotNull String stateStr, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)stateStr, (String)"stateStr");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        if (rotation == Rotation.SOUTH || rotation == Rotation.NONE) {
            return stateStr;
        }
        return this.rotateStateProperties(stateStr, rotation, true);
    }

    @NotNull
    public final String rotateStateFromNorth$cb_client(@NotNull String stateStr, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)stateStr, (String)"stateStr");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        if (rotation == Rotation.SOUTH || rotation == Rotation.NONE) {
            return stateStr;
        }
        return this.rotateStateProperties(stateStr, rotation, false);
    }

    private final String rotateStateProperties(String stateStr, Rotation rotation, boolean toNorth) {
        int bracketIdx = StringsKt.indexOf$default((CharSequence)stateStr, (char)'[', (int)0, (boolean)false, (int)6, null);
        if (bracketIdx == -1) {
            return stateStr;
        }
        String string = stateStr.substring(0, bracketIdx);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
        String blockId = string;
        String string2 = stateStr.substring(bracketIdx + 1, stateStr.length() - 1);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
        String propsStr = string2;
        String[] stringArray = new String[]{","};
        String newProps = CollectionsKt.joinToString$default((Iterable)StringsKt.split$default((CharSequence)propsStr, (String[])stringArray, (boolean)false, (int)0, (int)6, null), (CharSequence)",", null, null, (int)0, null, arg_0 -> BlockStateCodec.rotateStateProperties$lambda$0(toNorth, rotation, arg_0), (int)30, null);
        return blockId + "[" + newProps + "]";
    }

    @NotNull
    public final BlockState rotateBlockStateFromNorth$cb_client(@NotNull BlockState state, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)rotation), (String)"rotation");
        if (rotation == Rotation.SOUTH || rotation == Rotation.NONE) {
            return state;
        }
        BlockState result = state;
        for (Property.Value entry : state.getValues().toList()) {
            Property prop;
            Intrinsics.checkNotNullExpressionValue((Object)entry.property(), (String)"property(...)");
            Property p = prop;
            switch (prop.getName()) {
                case "facing": {
                    Comparable facing;
                    Intrinsics.checkNotNullExpressionValue((Object)state.getValue(p), (String)"getValue(...)");
                    if (!(facing instanceof Direction) || !((Direction)facing).getAxis().isHorizontal()) break;
                    Direction direction = RoomDataKt.rotateAroundNorth((Direction)facing, rotation);
                    Intrinsics.checkNotNull((Object)direction, (String)"null cannot be cast to non-null type kotlin.Comparable<kotlin.Any>");
                    Object object = result.setValue(p, (Comparable)direction);
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                    break;
                }
                case "axis": {
                    Comparable axis;
                    Intrinsics.checkNotNullExpressionValue((Object)state.getValue(p), (String)"getValue(...)");
                    if (!(axis instanceof Direction.Axis) || axis == Direction.Axis.Y) break;
                    Direction.Axis axis2 = RoomDataKt.rotateAroundNorth((Direction.Axis)axis, rotation);
                    Intrinsics.checkNotNull((Object)axis2, (String)"null cannot be cast to non-null type kotlin.Comparable<kotlin.Any>");
                    Object object = result.setValue(p, (Comparable)axis2);
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                }
            }
        }
        return result;
    }

    @NotNull
    public final String normalizeBlockId$cb_client(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        String string = input.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toLowerCase(...)");
        String cleaned = ((Object)StringsKt.trim((CharSequence)string)).toString();
        return StringsKt.contains$default((CharSequence)cleaned, (CharSequence)":", (boolean)false, (int)2, null) ? cleaned : "minecraft:" + cleaned;
    }

    @Nullable
    public final BlockState resolveShapeState$cb_client(@NotNull String shape) {
        Intrinsics.checkNotNullParameter((Object)shape, (String)"shape");
        return switch (shape) {
            case "fence" -> Blocks.OAK_FENCE.defaultBlockState();
            case "wall" -> Blocks.COBBLESTONE_WALL.defaultBlockState();
            case "pane" -> Blocks.GLASS_PANE.defaultBlockState();
            default -> null;
        };
    }

    @Nullable
    public final BlockState resolveBlockState(@NotNull String stateStr) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)stateStr, (String)"stateStr");
        try {
            Object object;
            String string;
            if (StringsKt.startsWith$default((String)stateStr, (String)"!", (boolean)false, (int)2, null)) {
                String string2 = stateStr.substring(1);
                string = string2;
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
            } else {
                string = stateStr;
            }
            String cleaned = string;
            int bracketIdx = StringsKt.indexOf$default((CharSequence)cleaned, (char)'[', (int)0, (boolean)false, (int)6, null);
            if (bracketIdx == -1) {
                Identifier identifier = Identifier.parse((String)cleaned);
                Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"parse(...)");
                Identifier loc = identifier;
                Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
                object = block != null ? block.defaultBlockState() : null;
            } else {
                String string3 = cleaned.substring(0, bracketIdx);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
                String blockId = string3;
                String string4 = cleaned.substring(bracketIdx + 1, cleaned.length() - 1);
                Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"substring(...)");
                String propsStr = string4;
                Identifier identifier = Identifier.parse((String)blockId);
                Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"parse(...)");
                Identifier loc = identifier;
                Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
                if (block == null) {
                    return null;
                }
                Block block2 = block;
                BlockState blockState2 = block2.defaultBlockState();
                Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"defaultBlockState(...)");
                BlockState state = blockState2;
                String[] stringArray = new String[]{","};
                for (String entry : StringsKt.split$default((CharSequence)propsStr, (String[])stringArray, (boolean)false, (int)0, (int)6, null)) {
                    String value;
                    Property property;
                    String name;
                    int eqIdx = StringsKt.indexOf$default((CharSequence)entry, (char)'=', (int)0, (boolean)false, (int)6, null);
                    if (eqIdx == -1) continue;
                    Intrinsics.checkNotNullExpressionValue((Object)entry.substring(0, eqIdx), (String)"substring(...)");
                    Intrinsics.checkNotNullExpressionValue((Object)entry.substring(eqIdx + 1), (String)"substring(...)");
                    if (block2.getStateDefinition().getProperty(name) == null) continue;
                    BlockState blockState3 = this.applyPropertyValue(state, property, value);
                    if (blockState3 == null) {
                        blockState3 = state;
                    }
                    state = blockState3;
                }
                object = state;
            }
            blockState = object;
        }
        catch (Exception exception) {
            blockState = null;
        }
        return blockState;
    }

    @Nullable
    public final BlockState computePlacementState$cb_client(@NotNull String blockId, @NotNull BlockHitResult hitResult) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        Intrinsics.checkNotNullParameter((Object)hitResult, (String)"hitResult");
        LocalPlayer localPlayer = this.getMc().player;
        if (localPlayer == null) {
            return null;
        }
        LocalPlayer player = localPlayer;
        Identifier identifier = Identifier.parse((String)this.normalizeBlockId$cb_client(blockId));
        Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"parse(...)");
        Identifier loc = identifier;
        Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
        if (block == null) {
            return null;
        }
        Block block2 = block;
        ItemStack itemStack = new ItemStack((ItemLike)block2.asItem());
        BlockPlaceContext context = new BlockPlaceContext((Player)player, InteractionHand.MAIN_HAND, itemStack, hitResult);
        return block2.getStateForPlacement(context);
    }

    @NotNull
    public final String serializeBlockState$cb_client(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        String string = BuiltInRegistries.BLOCK.getKey((Object)state.getBlock()).toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toString(...)");
        String blockId = string;
        List properties = state.getValues().toList();
        if (properties.isEmpty()) {
            return blockId;
        }
        Intrinsics.checkNotNull(properties);
        String propsStr = CollectionsKt.joinToString$default((Iterable)properties, (CharSequence)",", null, null, (int)0, null, BlockStateCodec::serializeBlockState$lambda$0, (int)30, null);
        return blockId + "[" + propsStr + "]";
    }

    @NotNull
    public final <T extends Comparable<? super T>> String getPropertyValueName$cb_client(@NotNull Property<T> property, @NotNull Comparable<?> value) {
        Intrinsics.checkNotNullParameter(property, (String)"property");
        Intrinsics.checkNotNullParameter(value, (String)"value");
        String string = property.getName(value);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getName(...)");
        return string;
    }

    private final <T extends Comparable<? super T>> BlockState applyPropertyValue(BlockState state, Property<T> property, String valueName) {
        Optional optional = property.getValue(valueName);
        Intrinsics.checkNotNullExpressionValue((Object)optional, (String)"getValue(...)");
        Optional value = optional;
        return value.isPresent() ? (BlockState)state.setValue(property, (Comparable)value.get()) : null;
    }

    public final void buildVariantMaps$cb_client() {
        for (Object e : BuiltInRegistries.BLOCK.keySet()) {
            String baseName;
            String ns;
            String baseId;
            String path;
            Block block;
            Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
            Identifier key = (Identifier)e;
            if ((Block)BuiltInRegistries.BLOCK.getOptional(key).orElse(null) == null) continue;
            Intrinsics.checkNotNullExpressionValue((Object)key.getPath(), (String)"getPath(...)");
            Intrinsics.checkNotNullExpressionValue((Object)key.getNamespace(), (String)"getNamespace(...)");
            if (block instanceof StairBlock && StringsKt.endsWith$default((String)path, (String)"_stairs", (boolean)false, (int)2, null) && (baseId = this.resolveBaseBlock(ns, baseName = StringsKt.removeSuffix((String)path, (CharSequence)"_stairs"))) != null) {
                baseToStair.put(baseId, ns + ":" + path);
            }
            if (!(block instanceof SlabBlock) || !StringsKt.endsWith$default((String)path, (String)"_slab", (boolean)false, (int)2, null) || (baseId = this.resolveBaseBlock(ns, baseName = StringsKt.removeSuffix((String)path, (CharSequence)"_slab"))) == null) continue;
            baseToSlab.put(baseId, ns + ":" + path);
        }
    }

    private final String resolveBaseBlock(String ns, String baseName) {
        String[] stringArray = new String[]{ns + ":" + baseName, ns + ":" + baseName + "s", ns + ":" + baseName + "_planks", ns + ":" + baseName + "_block"};
        List candidates = CollectionsKt.listOf((Object[])stringArray);
        for (String candidate : candidates) {
            if (BuiltInRegistries.BLOCK.getOptional(Identifier.parse((String)candidate)).orElse(null) == null) continue;
            return candidate;
        }
        return null;
    }

    @NotNull
    public final BlockState applyOriginalShape(@NotNull BlockState original, @NotNull BlockState replacement, int x, int y, int z) {
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        Intrinsics.checkNotNullParameter((Object)replacement, (String)"replacement");
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue()).booleanValue() && !((Boolean)BlockRemapsTab.INSTANCE.getKeepShape().getValue()).booleanValue()) {
            return replacement;
        }
        if (original.isAir()) {
            return replacement;
        }
        long packed = BlockPos.asLong((int)x, (int)y, (int)z);
        if (ReplacementCache.INSTANCE.getShapedPositions$cb_client().contains(packed)) {
            return replacement;
        }
        Block block = original.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        Block originalBlock = block;
        Block block2 = replacement.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block2, (String)"getBlock(...)");
        Block replacementBlock = block2;
        String string = BuiltInRegistries.BLOCK.getKey((Object)replacementBlock).toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toString(...)");
        String replacementId = string;
        if (originalBlock instanceof StairBlock) {
            BlockState stairState;
            if (replacementBlock instanceof StairBlock) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, replacement);
            }
            String stairId = baseToStair.get(replacementId);
            if (stairId != null && (stairState = this.resolveBlockState(stairId)) != null) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, stairState);
            }
            if (!replacement.canOcclude()) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ((Map)ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client()).put(packed, original);
                return replacement;
            }
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, replacement);
            return original;
        }
        if (originalBlock instanceof SlabBlock) {
            BlockState slabState;
            if (replacementBlock instanceof SlabBlock) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, replacement);
            }
            String slabId = baseToSlab.get(replacementId);
            if (slabId != null && (slabState = this.resolveBlockState(slabId)) != null) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, slabState);
            }
            if (!replacement.canOcclude()) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ((Map)ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client()).put(packed, original);
                return replacement;
            }
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, replacement);
            return original;
        }
        if (originalBlock instanceof CrossCollisionBlock || originalBlock instanceof WallBlock) {
            if (replacementBlock instanceof CrossCollisionBlock || replacementBlock instanceof WallBlock) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, replacement);
            }
            if (!replacement.canOcclude()) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ((Map)ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client()).put(packed, original);
                return replacement;
            }
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, replacement);
            return original;
        }
        if (originalBlock instanceof EndPortalBlock || originalBlock instanceof EndPortalFrameBlock) {
            if (replacementBlock instanceof EndPortalBlock || replacementBlock instanceof EndPortalFrameBlock) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
                return this.copyMatchingProperties(original, replacement);
            }
            if (!replacement.canOcclude()) {
                ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
                ((Map)ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client()).put(packed, original);
                return replacement;
            }
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
            ((Map)ReplacementCache.INSTANCE.getRetextureCache$cb_client()).put(packed, replacement);
            return original;
        }
        ReplacementCache.INSTANCE.getRetextureCache$cb_client().remove(packed);
        ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().remove(packed);
        return replacement;
    }

    private final BlockState copyMatchingProperties(BlockState from, BlockState to) {
        BlockState result = to;
        for (Property.Value entry : from.getValues().toList()) {
            Property prop;
            Intrinsics.checkNotNullExpressionValue((Object)entry.property(), (String)"property(...)");
            if (!result.hasProperty(prop)) continue;
            Property p = prop;
            Object object = result.setValue(p, from.getValue(p));
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
            result = (BlockState)object;
        }
        return result;
    }

    private static final CharSequence rotateStateProperties$lambda$0(boolean $toNorth, Rotation $rotation, String prop) {
        CharSequence charSequence;
        Intrinsics.checkNotNullParameter((Object)prop, (String)"prop");
        int eqIdx = StringsKt.indexOf$default((CharSequence)prop, (char)'=', (int)0, (boolean)false, (int)6, null);
        if (eqIdx == -1) {
            return prop;
        }
        String string = prop.substring(0, eqIdx);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
        String name = string;
        String string2 = prop.substring(eqIdx + 1);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
        String value = string2;
        String string3 = name;
        if (Intrinsics.areEqual((Object)string3, (Object)"facing")) {
            Object object;
            Direction dir = Direction.byName((String)value);
            if (dir != null && dir.getAxis().isHorizontal()) {
                Direction rotated = $toNorth ? RoomDataKt.rotateToNorth(dir, $rotation) : RoomDataKt.rotateAroundNorth(dir, $rotation);
                object = name + "=" + rotated.getSerializedName();
            } else {
                object = prop;
            }
            charSequence = (CharSequence)object;
        } else if (Intrinsics.areEqual((Object)string3, (Object)"axis")) {
            String string4 = value;
            Direction.Axis axis = Intrinsics.areEqual((Object)string4, (Object)"x") ? Direction.Axis.X : (Intrinsics.areEqual((Object)string4, (Object)"z") ? Direction.Axis.Z : null);
            charSequence = (CharSequence)(axis != null ? name + "=" + RoomDataKt.rotateToNorth(axis, $rotation).getSerializedName() : prop);
        } else {
            charSequence = prop;
        }
        return charSequence;
    }

    private static final CharSequence serializeBlockState$lambda$0(Property.Value entry) {
        String string = entry.property().getName();
        Property property = entry.property();
        Intrinsics.checkNotNullExpressionValue((Object)property, (String)"property(...)");
        Comparable comparable = entry.value();
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"value(...)");
        return string + "=" + INSTANCE.getPropertyValueName$cb_client(property, comparable);
    }
}


