/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.JvmStatic
 *  kotlin.jvm.internal.Intrinsics
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.LiquidBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WallSide
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.ConfigSwitcher;
import com.cb.cosmetic.CosmeticRenderer;
import com.cb.cosmetic.DirtySections;
import com.cb.cosmetic.EditMode;
import com.cb.cosmetic.Favorites;
import com.cb.cosmetic.GlobalRemap;
import com.cb.cosmetic.LocationTracker;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.RoomStore;
import com.cb.cosmetic.Ruler;
import com.cb.cosmetic.SaveScheduler;
import com.cb.cosmetic.WorldEditMode;
import com.cb.dungeon.RoomScanner;
import com.cb.utils.ChatUtil;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b)\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\"\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u008d\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018J,\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\b\b\u0002\u0010#\u001a\u00020\tH\u0007J4\u0010$\u001a\u0004\u0018\u00010\u001e2\b\u0010%\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020\tH\u0007J\u001a\u0010&\u001a\u0004\u0018\u00010\u001e2\u0006\u0010'\u001a\u00020\u00132\u0006\u0010%\u001a\u00020\u001eH\u0007J\u0010\u0010(\u001a\u0004\u0018\u00010\u001e2\u0006\u0010'\u001a\u00020\u0013J\u0012\u0010)\u001a\u0004\u0018\u00010\u001e2\u0006\u0010%\u001a\u00020\u001eH\u0007J \u0010*\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 J \u0010+\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 J0\u0010,\u001a\u00020\u001e2\u0006\u0010-\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 2\b\u0010.\u001a\u0004\u0018\u00010/J.\u00100\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\u001e2\u0006\u00101\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 J\u0006\u00102\u001a\u00020\tJ\u001e\u00103\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 J\u0006\u00104\u001a\u00020\tJ\u0006\u00105\u001a\u00020\tJ\u0006\u00106\u001a\u00020 J\u0010\u00107\u001a\u00020\u00182\u0006\u00108\u001a\u000209H\u0007J\u0010\u0010:\u001a\u00020\u00182\u0006\u00108\u001a\u00020;H\u0007J\u001e\u0010<\u001a\u00020\u00182\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\u0006\u0010@\u001a\u00020AJ\u0006\u0010B\u001a\u00020\tJ\u0006\u0010N\u001a\u00020\u0018J\u0006\u0010Q\u001a\u00020\u0018J\u000e\u0010R\u001a\u00020\u00182\u0006\u0010S\u001a\u00020\rJ\u0016\u0010T\u001a\u00020\u00182\u0006\u0010U\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rJ\u0006\u0010V\u001a\u00020\u0018J\u0006\u0010W\u001a\u00020\tJ\u000e\u0010^\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0013J\u000e\u0010_\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0013J\u0006\u0010`\u001a\u00020\rJ\u0006\u0010a\u001a\u00020\u0018J\u000e\u0010b\u001a\u00020\r2\u0006\u0010c\u001a\u00020\rJ\u0016\u0010d\u001a\u00020\r2\u0006\u0010e\u001a\u00020\r2\u0006\u0010f\u001a\u00020\rJ\u0010\u0010g\u001a\u00020\r2\b\u0010h\u001a\u0004\u0018\u00010\rJ\u0006\u0010i\u001a\u00020\rJ\"\u0010j\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020 0l\u0018\u00010k2\u0006\u0010m\u001a\u00020\rJ \u0010n\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u00132\u0006\u0010S\u001a\u00020\r2\b\b\u0002\u0010o\u001a\u00020\tJ \u0010p\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u00132\u0006\u0010-\u001a\u00020\u001e2\b\b\u0002\u0010o\u001a\u00020\tJ\u000e\u0010q\u001a\u00020\t2\u0006\u0010'\u001a\u00020\u0013J\u001e\u0010r\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 J\u0006\u0010s\u001a\u00020 J\u0006\u0010t\u001a\u00020 J\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\r0kJ\u0006\u0010v\u001a\u00020 J\u0006\u0010w\u001a\u00020\u0018J\u0006\u0010x\u001a\u00020\u0018J\u000e\u0010y\u001a\u00020\t2\u0006\u0010z\u001a\u00020\rJ\u000e\u0010{\u001a\u00020\t2\u0006\u0010z\u001a\u00020\rJ\u000e\u0010|\u001a\u00020\t2\u0006\u0010z\u001a\u00020\rJ\f\u0010}\u001a\b\u0012\u0004\u0012\u00020\r0kJ\f\u0010~\u001a\b\u0012\u0004\u0012\u00020\r0kJ\u000e\u0010\u007f\u001a\u00020\t2\u0006\u0010S\u001a\u00020\rJ\u000f\u0010\u0080\u0001\u001a\u00020\t2\u0006\u0010S\u001a\u00020\rJ\u000f\u0010\u0081\u0001\u001a\u00020\t2\u0006\u0010S\u001a\u00020\rJ\u0010\u0010\u0082\u0001\u001a\u00020 2\u0007\u0010\u0083\u0001\u001a\u00020\rJ\u0007\u0010\u0084\u0001\u001a\u00020 J\r\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u00020\r0kJ\u0010\u0010\u0086\u0001\u001a\u00020 2\u0007\u0010\u0083\u0001\u001a\u00020\rJ\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010\rR\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R2\u0010\u001a\u001a&\u0012\f\u0012\n \u001c*\u0004\u0018\u00010\t0\t \u001c*\u0012\u0012\f\u0012\n \u001c*\u0004\u0018\u00010\t0\t\u0018\u00010\u001b0\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010C\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\bD\u0010ER$\u0010F\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\r8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR(\u0010K\u001a\u0004\u0018\u00010\r2\b\u0010\u0012\u001a\u0004\u0018\u00010\r8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bL\u0010H\"\u0004\bM\u0010JR\u0011\u0010O\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\bP\u0010ER\u0011\u0010X\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\bY\u0010ER\u0013\u0010Z\u001a\u0004\u0018\u00010\u00138F\u00a2\u0006\u0006\u001a\u0004\b[\u0010\u0016R\u0013\u0010\\\u001a\u0004\u0018\u00010\u00138F\u00a2\u0006\u0006\u001a\u0004\b]\u0010\u0016R\u0013\u0010\u0087\u0001\u001a\u00020\t8F\u00a2\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010ER\u0013\u0010\u0088\u0001\u001a\u00020\r8F\u00a2\u0006\u0007\u001a\u0005\b\u0089\u0001\u0010HR\u0013\u0010\u008a\u0001\u001a\u00020\t8F\u00a2\u0006\u0007\u001a\u0005\b\u008b\u0001\u0010E\u00a8\u0006\u008e\u0001"}, d2={"Lcom/cb/cosmetic/CosmeticBlocks;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "editModeKeyWasPressed", "", "placeModeKeyWasPressed", "worldEditKeyWasPressed", "lastDimension", "", "lastEnabledState", "lastOnlyInDungeonsState", "lastKeepBlockSizeState", "lastLavaAsWaterState", "value", "Lnet/minecraft/core/BlockPos;", "lastLookBlockPos", "getLastLookBlockPos", "()Lnet/minecraft/core/BlockPos;", "register", "", "flushAll", "updatingConnections", "Ljava/lang/ThreadLocal;", "kotlin.jvm.PlatformType", "getReplacementAt", "Lnet/minecraft/world/level/block/state/BlockState;", "x", "", "y", "z", "originalAir", "getReplacementOrRemap", "original", "getEffectReplacement", "pos", "getReplacement", "swapFluid", "getRetextureAt", "getOriginalShapeAt", "updateConnections", "state", "getter", "Lcom/cb/cosmetic/CosmeticBlocks$BlockStateGetter;", "applyOriginalShape", "replacement", "hasActiveReplacements", "hasAdjacentReplacement", "keepBlockSizeEnabled", "customWaterColorEnabled", "waterColorRgb", "onPlayerInfoUpdate", "packet", "Lnet/minecraft/network/protocol/game/ClientboundPlayerInfoUpdatePacket;", "onSetObjective", "Lnet/minecraft/network/protocol/game/ClientboundSetObjectivePacket;", "onWorldRender", "modelView", "Lorg/joml/Matrix4f;", "projection", "camPos", "Lnet/minecraft/world/phys/Vec3;", "tryPickCosmeticAtCrosshair", "editMode", "getEditMode", "()Z", "selectedBlock", "getSelectedBlock", "()Ljava/lang/String;", "setSelectedBlock", "(Ljava/lang/String;)V", "currentShape", "getCurrentShape", "setCurrentShape", "toggleEditMode", "fillMode", "getFillMode", "toggleFillMode", "setBlock", "blockId", "setBlockProperty", "property", "listBlockProperties", "hasExplicitProperties", "worldEditMode", "getWorldEditMode", "pos1", "getPos1", "pos2", "getPos2", "setPos1", "setPos2", "getSelectionInfo", "clearSelection", "fillRegion", "blockSpecs", "replaceRegion", "fromBlock", "toSpecs", "removeInRegion", "filterBlock", "undoLastOperation", "parseWeightedBlocks", "", "Lkotlin/Pair;", "input", "setReplacement", "phantom", "setReplacementState", "removeReplacement", "removeAt", "clearDimension", "clearAll", "listReplacements", "getReplacementCount", "save", "load", "createConfig", "name", "loadConfigByName", "deleteConfig", "listConfigs", "getFavorites", "addFavorite", "removeFavorite", "isFavorite", "clearRoom", "roomName", "clearAllRooms", "listRoomNames", "getRoomBlockCount", "isInSkyblock", "currentArea", "getCurrentArea", "inDungeon", "getInDungeon", "getCurrentDimension", "BlockStateGetter", "cb_client"})
public final class CosmeticBlocks {
    @NotNull
    public static final CosmeticBlocks INSTANCE = new CosmeticBlocks();
    private static boolean editModeKeyWasPressed;
    private static boolean placeModeKeyWasPressed;
    private static boolean worldEditKeyWasPressed;
    @Nullable
    private static String lastDimension;
    private static boolean lastEnabledState;
    private static boolean lastOnlyInDungeonsState;
    private static boolean lastKeepBlockSizeState;
    private static boolean lastLavaAsWaterState;
    @Nullable
    private static BlockPos lastLookBlockPos;
    private static final ThreadLocal<Boolean> updatingConnections;

    private CosmeticBlocks() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    @Nullable
    public final BlockPos getLastLookBlockPos() {
        return lastLookBlockPos;
    }

    public final void register() {
        BlockStateCodec.INSTANCE.buildVariantMaps$cb_client();
        ReplacementIO.INSTANCE.migrateOldConfig$cb_client();
        ReplacementIO.INSTANCE.load();
        GlobalRemap.INSTANCE.load();
        Favorites.INSTANCE.loadFavorites();
        RoomStore.INSTANCE.loadRoomBlocks();
        RoomStore.INSTANCE.resolveAllRoomBlocks();
        RoomScanner.INSTANCE.init();
        ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
        ClientTickEvents.END_CLIENT_TICK.register(CosmeticBlocks::register$lambda$0);
    }

    public final void flushAll() {
        SaveScheduler.INSTANCE.flushAll();
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z, boolean originalAir) {
        return ReplacementCache.INSTANCE.getReplacementAt(x, y, z, originalAir);
    }

    public static /* synthetic */ BlockState getReplacementAt$default(CosmeticBlocks cosmeticBlocks, int n, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 8) != 0) {
            bl = false;
        }
        return cosmeticBlocks.getReplacementAt(n, n2, n3, bl);
    }

    @JvmStatic
    @Nullable
    public static final BlockState getReplacementOrRemap(@Nullable BlockState original, int x, int y, int z, boolean originalAir) {
        BlockState perPos = ReplacementCache.INSTANCE.getReplacementAt(x, y, z, originalAir);
        if (perPos != null) {
            return perPos;
        }
        if (original == null || original.isAir()) {
            return null;
        }
        Block block = original.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        return GlobalRemap.INSTANCE.remapFor(block, x, y, z);
    }

    @JvmStatic
    @Nullable
    public static final BlockState getEffectReplacement(@NotNull BlockPos pos, @NotNull BlockState original) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        BlockState perPos = ReplacementCache.INSTANCE.getReplacement(pos);
        if (perPos != null) {
            return perPos;
        }
        if (original.isAir()) {
            return null;
        }
        Block block = original.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        return GlobalRemap.INSTANCE.remapFor(block, pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    public final BlockState getReplacement(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return ReplacementCache.INSTANCE.getReplacement(pos);
    }

    @JvmStatic
    @Nullable
    public static final BlockState swapFluid(@NotNull BlockState original) {
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue()).booleanValue()) {
            return null;
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue()).booleanValue() && !LocationTracker.INSTANCE.getInDungeon()) {
            return null;
        }
        if (!(original.getBlock() instanceof LiquidBlock)) {
            return null;
        }
        if (original.getFluidState().is(Fluids.LAVA) || original.getFluidState().is(Fluids.FLOWING_LAVA)) {
            Comparable comparable = original.getValue((Property)LiquidBlock.LEVEL);
            Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"getValue(...)");
            int level = ((Number)((Object)comparable)).intValue();
            return (BlockState)Blocks.WATER.defaultBlockState().setValue((Property)LiquidBlock.LEVEL, (Comparable)Integer.valueOf(level));
        }
        return null;
    }

    @Nullable
    public final BlockState getRetextureAt(int x, int y, int z) {
        return ReplacementCache.INSTANCE.getRetextureAt(x, y, z);
    }

    @Nullable
    public final BlockState getOriginalShapeAt(int x, int y, int z) {
        return ReplacementCache.INSTANCE.getOriginalShapeAt(x, y, z);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public final BlockState updateConnections(@NotNull BlockState state, int x, int y, int z, @Nullable BlockStateGetter getter) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Block block = state.getBlock();
        Intrinsics.checkNotNullExpressionValue((Object)block, (String)"getBlock(...)");
        Block block2 = block;
        if (!(block2 instanceof CrossCollisionBlock) && !(block2 instanceof WallBlock)) {
            return state;
        }
        if (updatingConnections.get().booleanValue()) {
            return state;
        }
        updatingConnections.set(true);
        try {
            BlockState result = state;
            if (block2 instanceof WallBlock) {
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                for (Direction dir2 : directions) {
                    EnumProperty<WallSide> wallProp;
                    BlockState neighborState;
                    wallProp = WallBlock.PROPERTY_BY_DIRECTION.get(dir2);
                    if (wallProp == null) continue;
                    BlockStateGetter blockStateGetter = getter;
                    BlockState blockState = neighborState = blockStateGetter != null ? blockStateGetter.get(x + dir2.getStepX(), y + dir2.getStepY(), z + dir2.getStepZ()) : null;
                    boolean connects = neighborState != null && !neighborState.isAir() ? neighborState.getBlock() instanceof WallBlock || neighborState.getBlock() instanceof CrossCollisionBlock || neighborState.getBlock() instanceof FenceGateBlock || neighborState.canOcclude() : false;
                    Object object = result.setValue((Property)wallProp, (Comparable)(connects ? WallSide.LOW : WallSide.NONE));
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                }
                Object object = result.setValue((Property)WallBlock.UP, (Comparable)Boolean.valueOf(true));
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                result = (BlockState)object;
            } else {
                Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
                for (Direction dir2 : directions) {
                    BlockState neighborState;
                    BooleanProperty prop;
                    String propName = dir2.getSerializedName();
                    Intrinsics.checkNotNullExpressionValue((Object)dir2.getSerializedName(), (String)"getSerializedName(...)");
                    Property property = result.getBlock().getStateDefinition().getProperty(propName);
                    if (!(property instanceof BooleanProperty booleanProperty)) continue;
                    prop = booleanProperty;
                    BlockStateGetter blockStateGetter = getter;
                    BlockState blockState = neighborState = blockStateGetter != null ? blockStateGetter.get(x + dir2.getStepX(), y + dir2.getStepY(), z + dir2.getStepZ()) : null;
                    boolean connects = neighborState != null && !neighborState.isAir() ? neighborState.getBlock() instanceof CrossCollisionBlock || neighborState.getBlock() instanceof FenceGateBlock || neighborState.canOcclude() || neighborState.getBlock() instanceof WallBlock : false;
                    Object object = result.setValue((Property)prop, (Comparable)Boolean.valueOf(connects));
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"setValue(...)");
                    result = (BlockState)object;
                }
            }
            BlockState blockState = result;
            return blockState;
        }
        finally {
            updatingConnections.set(false);
        }
    }

    @NotNull
    public final BlockState applyOriginalShape(@NotNull BlockState original, @NotNull BlockState replacement, int x, int y, int z) {
        Intrinsics.checkNotNullParameter((Object)original, (String)"original");
        Intrinsics.checkNotNullParameter((Object)replacement, (String)"replacement");
        return BlockStateCodec.INSTANCE.applyOriginalShape(original, replacement, x, y, z);
    }

    public final boolean hasActiveReplacements() {
        return ReplacementCache.INSTANCE.hasActiveReplacements();
    }

    public final boolean hasAdjacentReplacement(int x, int y, int z) {
        return ReplacementCache.INSTANCE.hasAdjacentReplacement(x, y, z);
    }

    public final boolean keepBlockSizeEnabled() {
        return (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue();
    }

    public final boolean customWaterColorEnabled() {
        return (Boolean)CosmeticBlocksTab.INSTANCE.getCustomWaterColor().getValue();
    }

    public final int waterColorRgb() {
        return ((Number)CosmeticBlocksTab.INSTANCE.getWaterColor().getValue()).intValue() & 0xFFFFFF;
    }

    @JvmStatic
    public static final void onPlayerInfoUpdate(@NotNull ClientboundPlayerInfoUpdatePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        LocationTracker.onPlayerInfoUpdate(packet);
    }

    @JvmStatic
    public static final void onSetObjective(@NotNull ClientboundSetObjectivePacket packet) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        LocationTracker.onSetObjective(packet);
    }

    public final void onWorldRender(@NotNull Matrix4f modelView, @NotNull Matrix4f projection, @NotNull Vec3 camPos) {
        Intrinsics.checkNotNullParameter((Object)modelView, (String)"modelView");
        Intrinsics.checkNotNullParameter((Object)projection, (String)"projection");
        Intrinsics.checkNotNullParameter((Object)camPos, (String)"camPos");
        CosmeticRenderer.INSTANCE.onWorldRender(modelView, projection, camPos);
    }

    public final boolean tryPickCosmeticAtCrosshair() {
        return EditMode.INSTANCE.tryPickCosmeticAtCrosshair();
    }

    public final boolean getEditMode() {
        return EditMode.INSTANCE.getEditMode();
    }

    @NotNull
    public final String getSelectedBlock() {
        return EditMode.INSTANCE.getSelectedBlock();
    }

    public final void setSelectedBlock(@NotNull String value) {
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        EditMode.INSTANCE.setSelectedBlock$cb_client(value);
    }

    @Nullable
    public final String getCurrentShape() {
        return EditMode.INSTANCE.getCurrentShape();
    }

    public final void setCurrentShape(@Nullable String value) {
        EditMode.INSTANCE.setCurrentShape(value);
    }

    public final void toggleEditMode() {
        EditMode.INSTANCE.toggleEditMode();
    }

    public final boolean getFillMode() {
        return EditMode.INSTANCE.getFillMode();
    }

    public final void toggleFillMode() {
        EditMode.INSTANCE.toggleFillMode();
    }

    public final void setBlock(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        EditMode.INSTANCE.setBlock(blockId);
    }

    public final void setBlockProperty(@NotNull String property, @NotNull String value) {
        Intrinsics.checkNotNullParameter((Object)property, (String)"property");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        EditMode.INSTANCE.setBlockProperty(property, value);
    }

    public final void listBlockProperties() {
        EditMode.INSTANCE.listBlockProperties();
    }

    public final boolean hasExplicitProperties() {
        return EditMode.INSTANCE.hasExplicitProperties();
    }

    public final boolean getWorldEditMode() {
        return WorldEditMode.INSTANCE.getWorldEditMode();
    }

    @Nullable
    public final BlockPos getPos1() {
        return WorldEditMode.INSTANCE.getPos1();
    }

    @Nullable
    public final BlockPos getPos2() {
        return WorldEditMode.INSTANCE.getPos2();
    }

    public final void setPos1(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        WorldEditMode.INSTANCE.setPos1(pos);
    }

    public final void setPos2(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        WorldEditMode.INSTANCE.setPos2(pos);
    }

    @NotNull
    public final String getSelectionInfo() {
        return WorldEditMode.INSTANCE.getSelectionInfo();
    }

    public final void clearSelection() {
        WorldEditMode.INSTANCE.clearSelection();
    }

    @NotNull
    public final String fillRegion(@NotNull String blockSpecs) {
        Intrinsics.checkNotNullParameter((Object)blockSpecs, (String)"blockSpecs");
        return WorldEditMode.INSTANCE.fillRegion(blockSpecs);
    }

    @NotNull
    public final String replaceRegion(@NotNull String fromBlock, @NotNull String toSpecs) {
        Intrinsics.checkNotNullParameter((Object)fromBlock, (String)"fromBlock");
        Intrinsics.checkNotNullParameter((Object)toSpecs, (String)"toSpecs");
        return WorldEditMode.INSTANCE.replaceRegion(fromBlock, toSpecs);
    }

    @NotNull
    public final String removeInRegion(@Nullable String filterBlock) {
        return WorldEditMode.INSTANCE.removeInRegion(filterBlock);
    }

    @NotNull
    public final String undoLastOperation() {
        return WorldEditMode.INSTANCE.undoLastOperation();
    }

    @Nullable
    public final List<Pair<BlockState, Integer>> parseWeightedBlocks(@NotNull String input) {
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        return WorldEditMode.INSTANCE.parseWeightedBlocks(input);
    }

    public final void setReplacement(@NotNull BlockPos pos, @NotNull String blockId, boolean phantom) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        ReplacementStore.INSTANCE.setReplacement(pos, blockId, phantom);
    }

    public static /* synthetic */ void setReplacement$default(CosmeticBlocks cosmeticBlocks, BlockPos blockPos, String string, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        cosmeticBlocks.setReplacement(blockPos, string, bl);
    }

    public final void setReplacementState(@NotNull BlockPos pos, @NotNull BlockState state, boolean phantom) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        ReplacementStore.INSTANCE.setReplacementState(pos, state, phantom);
    }

    public static /* synthetic */ void setReplacementState$default(CosmeticBlocks cosmeticBlocks, BlockPos blockPos, BlockState blockState, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            bl = false;
        }
        cosmeticBlocks.setReplacementState(blockPos, blockState, bl);
    }

    public final boolean removeReplacement(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return ReplacementStore.INSTANCE.removeReplacement(pos);
    }

    public final boolean removeAt(int x, int y, int z) {
        return ReplacementStore.INSTANCE.removeAt(x, y, z);
    }

    public final int clearDimension() {
        return ReplacementStore.INSTANCE.clearDimension();
    }

    public final int clearAll() {
        return ReplacementStore.INSTANCE.clearAll();
    }

    @NotNull
    public final List<String> listReplacements() {
        return ReplacementStore.INSTANCE.listReplacements();
    }

    public final int getReplacementCount() {
        return ReplacementStore.INSTANCE.getReplacementCount();
    }

    public final void save() {
        ReplacementIO.INSTANCE.save();
    }

    public final void load() {
        ReplacementIO.INSTANCE.load();
    }

    public final boolean createConfig(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.createConfig(name);
    }

    public final boolean loadConfigByName(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.loadConfigByName(name);
    }

    public final boolean deleteConfig(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        return ConfigSwitcher.INSTANCE.deleteConfig(name);
    }

    @NotNull
    public final List<String> listConfigs() {
        return ConfigSwitcher.INSTANCE.listConfigs();
    }

    @NotNull
    public final List<String> getFavorites() {
        return Favorites.INSTANCE.getFavorites();
    }

    public final boolean addFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.addFavorite(blockId);
    }

    public final boolean removeFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.removeFavorite(blockId);
    }

    public final boolean isFavorite(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        return Favorites.INSTANCE.isFavorite(blockId);
    }

    public final int clearRoom(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        return RoomStore.INSTANCE.clearRoom(roomName);
    }

    public final int clearAllRooms() {
        return RoomStore.INSTANCE.clearAllRooms();
    }

    @NotNull
    public final List<String> listRoomNames() {
        return RoomStore.INSTANCE.listRoomNames();
    }

    public final int getRoomBlockCount(@NotNull String roomName) {
        Intrinsics.checkNotNullParameter((Object)roomName, (String)"roomName");
        return RoomStore.INSTANCE.getRoomBlockCount(roomName);
    }

    public final boolean isInSkyblock() {
        return LocationTracker.INSTANCE.isInSkyblock();
    }

    @NotNull
    public final String getCurrentArea() {
        return LocationTracker.INSTANCE.getCurrentArea();
    }

    public final boolean getInDungeon() {
        return LocationTracker.INSTANCE.getInDungeon();
    }

    @Nullable
    public final String getCurrentDimension() {
        return LocationTracker.INSTANCE.getCurrentDimension();
    }

    @JvmOverloads
    @Nullable
    public final BlockState getReplacementAt(int x, int y, int z) {
        return this.getReplacementAt(x, y, z, false);
    }

    private static final void register$lambda$0(Minecraft minecraft) {
        boolean pressed;
        long window;
        HitResult hit;
        long now;
        long now2;
        boolean roomChanged;
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"<unused var>");
        String dim = LocationTracker.INSTANCE.getCurrentDimension();
        if (!Intrinsics.areEqual((Object)dim, (Object)lastDimension)) {
            SaveScheduler.INSTANCE.flushSave$cb_client();
            if (dim != null && lastDimension != null) {
                LocationTracker.INSTANCE.resetLocationState();
            }
            lastDimension = dim;
            ReplacementCache.INSTANCE.rebuildActiveCache$cb_client();
            if (dim != null) {
                DirtySections.INSTANCE.markAllCachedDirty$cb_client();
            }
        }
        if (DirtySections.INSTANCE.hasPending$cb_client()) {
            DirtySections.INSTANCE.drainDirtySections$cb_client();
        }
        if ((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue() != lastEnabledState || (Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue() != lastOnlyInDungeonsState || (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue() != lastKeepBlockSizeState || (Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue() != lastLavaAsWaterState) {
            ReplacementCache.INSTANCE.getRetextureCache$cb_client().clear();
            ReplacementCache.INSTANCE.getOriginalShapeCache$cb_client().clear();
            lastEnabledState = (Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue();
            lastOnlyInDungeonsState = (Boolean)CosmeticBlocksTab.INSTANCE.getOnlyInDungeons().getValue();
            lastKeepBlockSizeState = (Boolean)CosmeticBlocksTab.INSTANCE.getKeepBlockSize().getValue();
            lastLavaAsWaterState = (Boolean)CosmeticBlocksTab.INSTANCE.getLavaAsWater().getValue();
            DirtySections.INSTANCE.markAllCachedDirty$cb_client();
        }
        ConfigSwitcher.INSTANCE.checkConfigSwitch$cb_client();
        LocationTracker.INSTANCE.updateDungeonCheck$cb_client();
        if (LocationTracker.INSTANCE.getInDungeon() && RoomScanner.INSTANCE.isReady() && ((Boolean)CosmeticBlocksTab.INSTANCE.getRoomScanning().getValue()).booleanValue() && (roomChanged = RoomScanner.INSTANCE.tick())) {
            RoomStore.INSTANCE.onRoomChanged$cb_client(RoomScanner.INSTANCE.getCurrentRoom());
        }
        if (SaveScheduler.INSTANCE.getNeedsSave$cb_client() && ((now2 = System.currentTimeMillis()) - SaveScheduler.INSTANCE.getLastEditTime$cb_client() >= 2000L || now2 - SaveScheduler.INSTANCE.getLastSaveTime$cb_client() >= 10000L)) {
            ReplacementIO.INSTANCE.save();
        }
        if (SaveScheduler.INSTANCE.getNeedsRoomSave$cb_client() && ((now = System.currentTimeMillis()) - SaveScheduler.INSTANCE.getLastRoomEditTime$cb_client() >= 2000L || now - SaveScheduler.INSTANCE.getLastRoomSaveTime$cb_client() >= 10000L)) {
            RoomStore.INSTANCE.saveRoomBlocksAsync$cb_client();
        }
        if ((hit = CosmeticBlocks.INSTANCE.getMc().hitResult) instanceof BlockHitResult && ((BlockHitResult)hit).getType() == HitResult.Type.BLOCK) {
            lastLookBlockPos = ((BlockHitResult)hit).getBlockPos();
        }
        if ((window = GLFW.glfwGetCurrentContext()) != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getEditModeKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getEditModeKeybind().getValue()).intValue()) == 1;
            if (pressed && !editModeKeyWasPressed) {
                INSTANCE.toggleEditMode();
            }
            editModeKeyWasPressed = pressed;
        } else {
            editModeKeyWasPressed = false;
        }
        if (window != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getPlaceModeKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getPlaceModeKeybind().getValue()).intValue()) == 1;
            if (pressed && !placeModeKeyWasPressed) {
                CosmeticBlocksTab.INSTANCE.getPlaceMode().setValue((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue() == false);
                ChatUtil.INSTANCE.send((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue() != false ? "Place mode \u00a7aON" : "Place mode \u00a7cOFF");
            }
            placeModeKeyWasPressed = pressed;
        } else {
            placeModeKeyWasPressed = false;
        }
        if (window != 0L && CosmeticBlocks.INSTANCE.getMc().gui.screen() == null && ((Number)CosmeticBlocksTab.INSTANCE.getWorldEditKeybind().getValue()).intValue() != -1) {
            boolean bl = pressed = GLFW.glfwGetKey((long)window, (int)((Number)CosmeticBlocksTab.INSTANCE.getWorldEditKeybind().getValue()).intValue()) == 1;
            if (pressed && !worldEditKeyWasPressed) {
                WorldEditMode.INSTANCE.setWorldEditMode$cb_client(!WorldEditMode.INSTANCE.getWorldEditMode());
                ChatUtil.INSTANCE.send(WorldEditMode.INSTANCE.getWorldEditMode() ? "World edit mode \u00a7aON" : "World edit mode \u00a7cOFF");
            }
            worldEditKeyWasPressed = pressed;
        } else {
            worldEditKeyWasPressed = false;
        }
        if (WorldEditMode.INSTANCE.getWorldEditMode()) {
            WorldEditMode.INSTANCE.tickWorldEditMode$cb_client();
        }
        if (EditMode.INSTANCE.getEditMode()) {
            EditMode.INSTANCE.tickEditMode$cb_client();
        }
        Ruler.INSTANCE.tick();
        CosmeticRenderer.INSTANCE.emitGizmos();
    }

    private static final Boolean updatingConnections$lambda$0() {
        return false;
    }

    static {
        updatingConnections = ThreadLocal.withInitial(CosmeticBlocks::updatingConnections$lambda$0);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00e6\u0080\u0001\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H&\u00a8\u0006\b\u00c0\u0006\u0003"}, d2={"Lcom/cb/cosmetic/CosmeticBlocks$BlockStateGetter;", "", "get", "Lnet/minecraft/world/level/block/state/BlockState;", "x", "", "y", "z", "cb_client"})
    public static interface BlockStateGetter {
        @Nullable
        public BlockState get(int var1, int var2, int var3);
    }
}

