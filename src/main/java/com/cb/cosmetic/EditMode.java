/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.Property$Value
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.cosmetic;

import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.cosmetic.BlockStateCodec;
import com.cb.cosmetic.ReplacementCache;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.RoomStore;
import com.cb.cosmetic.SaveScheduler;
import com.cb.cosmetic.WorldEditMode;
import com.cb.utils.ChatUtil;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cJ\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020\rJ\u0006\u0010!\u001a\u00020\tJ\u0016\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\rJ\u0006\u0010$\u001a\u00020\u001cJ\u001b\u0010%\u001a\u0010\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020(\u0018\u00010&H\u0000\u00a2\u0006\u0002\b)J\u000f\u0010*\u001a\u0004\u0018\u00010'H\u0000\u00a2\u0006\u0002\b+JP\u0010,\u001a\u00020(2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.2\u0006\u00100\u001a\u00020.2\u0006\u00101\u001a\u00020.2\u0006\u00102\u001a\u00020.2\u0006\u00103\u001a\u00020.2\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002052\u0006\u00107\u001a\u000205H\u0002J\r\u00108\u001a\u00020\u001cH\u0000\u00a2\u0006\u0002\b9R\u0014\u0010\u0004\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR$\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\r@@X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001e\u0010\u0019\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\f\u00a8\u0006:"}, d2={"Lcom/cb/cosmetic/EditMode;", "", "<init>", "()V", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "value", "", "editMode", "getEditMode", "()Z", "", "selectedBlock", "getSelectedBlock", "()Ljava/lang/String;", "setSelectedBlock$cb_client", "(Ljava/lang/String;)V", "leftWasPressed", "rightWasPressed", "middleWasPressed", "currentShape", "getCurrentShape", "setCurrentShape", "fillMode", "getFillMode", "toggleFillMode", "", "toggleEditMode", "tryPickCosmeticAtCrosshair", "setBlock", "blockId", "hasExplicitProperties", "setBlockProperty", "property", "listBlockProperties", "findPhantomHitResult", "Lkotlin/Pair;", "Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/core/Direction;", "findPhantomHitResult$cb_client", "findPhantomTarget", "findPhantomTarget$cb_client", "getEntryFace", "eyeX", "", "eyeY", "eyeZ", "dirX", "dirY", "dirZ", "blockX", "", "blockY", "blockZ", "tickEditMode", "tickEditMode$cb_client", "cb_client"})
@SourceDebugExtension(value={"SMAP\nEditMode.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EditMode.kt\ncom/cb/cosmetic/EditMode\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,404:1\n1205#2,2:405\n1282#2,4:407\n*S KotlinDebug\n*F\n+ 1 EditMode.kt\ncom/cb/cosmetic/EditMode\n*L\n170#1:405,2\n170#1:407,4\n*E\n"})
public final class EditMode {
    @NotNull
    public static final EditMode INSTANCE = new EditMode();
    private static boolean editMode;
    @NotNull
    private static String selectedBlock;
    private static boolean leftWasPressed;
    private static boolean rightWasPressed;
    private static boolean middleWasPressed;
    @Nullable
    private static String currentShape;
    private static boolean fillMode;

    private EditMode() {
    }

    private final Minecraft getMc() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        return minecraft;
    }

    public final boolean getEditMode() {
        return editMode;
    }

    @NotNull
    public final String getSelectedBlock() {
        return selectedBlock;
    }

    public final void setSelectedBlock$cb_client(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        selectedBlock = string;
    }

    @Nullable
    public final String getCurrentShape() {
        return currentShape;
    }

    public final void setCurrentShape(@Nullable String string) {
        currentShape = string;
    }

    public final boolean getFillMode() {
        return fillMode;
    }

    public final void toggleFillMode() {
        boolean bl = fillMode = !fillMode;
        if (fillMode) {
            ChatUtil.INSTANCE.success("Fill mode ON \u2014 R-click flood-fills connected same-type blocks (limit " + (int)((Number)CosmeticBlocksTab.INSTANCE.getFillModeLimit().getValue()).doubleValue() + ")");
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
            String blockName = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
            ChatUtil.INSTANCE.success("Edit mode ON \u2014 brush: " + blockName);
            ChatUtil.INSTANCE.send("R-click: place, L-click: remove, M-click: pick, 'cb em' to stop");
        }
    }

    public final boolean tryPickCosmeticAtCrosshair() {
        BlockState state;
        BlockPos phantomPos;
        if (!((Boolean)CosmeticBlocksTab.INSTANCE.getEnabled().getValue()).booleanValue()) {
            return false;
        }
        HitResult hitResult = this.getMc().hitResult;
        if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
            BlockPos pos = blockPos;
            BlockState replacement = ReplacementCache.getReplacementAt$default(ReplacementCache.INSTANCE, pos.getX(), pos.getY(), pos.getZ(), false, 8, null);
            if (replacement != null) {
                selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(replacement);
                ChatUtil.INSTANCE.success("Brush set to " + StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:"));
                return true;
            }
        }
        if (((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue()).booleanValue() && (phantomPos = this.findPhantomTarget$cb_client()) != null && (state = ReplacementCache.getReplacementAt$default(ReplacementCache.INSTANCE, phantomPos.getX(), phantomPos.getY(), phantomPos.getZ(), false, 8, null)) != null) {
            selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
            ChatUtil.INSTANCE.success("Brush set to " + StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:"));
            return true;
        }
        return false;
    }

    public final void setBlock(@NotNull String blockId) {
        Intrinsics.checkNotNullParameter((Object)blockId, (String)"blockId");
        String normalized = BlockStateCodec.INSTANCE.normalizeBlockId$cb_client(blockId);
        BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(normalized);
        if (state == null) {
            ChatUtil.INSTANCE.error("Unknown block '" + blockId + "'");
            return;
        }
        selectedBlock = normalized;
        String blockName = StringsKt.removePrefix((String)normalized, (CharSequence)"minecraft:");
        ChatUtil.INSTANCE.success("Brush set to " + blockName);
    }

    public final boolean hasExplicitProperties() {
        return StringsKt.contains$default((CharSequence)selectedBlock, (char)'[', (boolean)false, (int)2, null);
    }

    public final void setBlockProperty(@NotNull String property, @NotNull String value) {
        String string;
        Intrinsics.checkNotNullParameter((Object)property, (String)"property");
        Intrinsics.checkNotNullParameter((Object)value, (String)"value");
        int bracketIdx = StringsKt.indexOf$default((CharSequence)selectedBlock, (char)'[', (int)0, (boolean)false, (int)6, null);
        if (bracketIdx != -1) {
            String string2 = selectedBlock.substring(0, bracketIdx);
            string = string2;
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
        } else {
            string = selectedBlock;
        }
        String baseId = string;
        Identifier identifier = Identifier.parse((String)baseId);
        Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"parse(...)");
        Identifier loc = identifier;
        Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
        if (block == null) {
            ChatUtil.INSTANCE.error("Current brush is invalid");
            return;
        }
        Property prop = block.getStateDefinition().getProperty(property);
        if (prop == null) {
            Collection collection = block.getStateDefinition().getProperties();
            Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"getProperties(...)");
            String available = CollectionsKt.joinToString$default((Iterable)collection, (CharSequence)", ", null, null, (int)0, null, EditMode::setBlockProperty$lambda$0, (int)30, null);
            ChatUtil.INSTANCE.error("'" + property + "' is not a valid property. Available: " + available);
            return;
        }
        Map props = new LinkedHashMap();
        if (bracketIdx != -1) {
            String string3 = selectedBlock.substring(bracketIdx + 1, selectedBlock.length() - 1);
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
            String propsStr = string3;
            String[] stringArray = new String[]{","};
            for (String entry : StringsKt.split$default((CharSequence)propsStr, (String[])stringArray, (boolean)false, (int)0, (int)6, null)) {
                int eqIdx = StringsKt.indexOf$default((CharSequence)entry, (char)'=', (int)0, (boolean)false, (int)6, null);
                if (eqIdx == -1) continue;
                String string4 = entry.substring(0, eqIdx);
                Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"substring(...)");
                String string5 = entry.substring(eqIdx + 1);
                Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"substring(...)");
                props.put(string4, string5);
            }
        }
        Optional optional = prop.getValue(value);
        Intrinsics.checkNotNullExpressionValue((Object)optional, (String)"getValue(...)");
        Optional parsed = optional;
        if (parsed.isEmpty()) {
            List list = prop.getPossibleValues();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"getPossibleValues(...)");
            String allowed = CollectionsKt.joinToString$default((Iterable)list, (CharSequence)", ", null, null, (int)0, null, arg_0 -> EditMode.setBlockProperty$lambda$1(prop, arg_0), (int)30, null);
            ChatUtil.INSTANCE.error("'" + value + "' is not valid for '" + property + "'. Options: " + allowed);
            return;
        }
        props.put(property, value);
        String propsStr = CollectionsKt.joinToString$default((Iterable)props.entrySet(), (CharSequence)",", null, null, (int)0, null, EditMode::setBlockProperty$lambda$2, (int)30, null);
        selectedBlock = baseId + "[" + propsStr + "]";
        String blockName = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
        ChatUtil.INSTANCE.success("Brush set to " + blockName);
    }

    /*
     * WARNING - void declaration
     */
    public final void listBlockProperties() {
        Map map;
        String string;
        int bracketIdx = StringsKt.indexOf$default((CharSequence)selectedBlock, (char)'[', (int)0, (boolean)false, (int)6, null);
        if (bracketIdx != -1) {
            String string2 = selectedBlock.substring(0, bracketIdx);
            string = string2;
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
        } else {
            string = selectedBlock;
        }
        String baseId = string;
        Identifier identifier = Identifier.parse((String)baseId);
        Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"parse(...)");
        Identifier loc = identifier;
        Block block = BuiltInRegistries.BLOCK.getOptional(loc).orElse(null);
        if (block == null) {
            ChatUtil.INSTANCE.error("Current brush is invalid");
            return;
        }
        BlockState state = BlockStateCodec.INSTANCE.resolveBlockState(selectedBlock);
        Object object = state;
        if (object != null && (object = object.getValues()) != null && (object = object.toList()) != null) {
            void $this$associateTo$iv$iv;
            Iterable $this$associate$iv = (Iterable)object;
            boolean $i$f$associate = false;
            int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
            Iterable iterable = $this$associate$iv;
            Map destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateTo = false;
            for (Object element$iv$iv : $this$associateTo$iv$iv) {
                Map map2 = destination$iv$iv;
                Property.Value it = (Property.Value)element$iv$iv;
                boolean bl = false;
                Pair pair = TuplesKt.to((Object)it.property(), (Object)it.value());
                map2.put(pair.getFirst(), pair.getSecond());
            }
            map = destination$iv$iv;
        } else {
            map = MapsKt.emptyMap();
        }
        Map stateProps = map;
        Collection collection = block.getStateDefinition().getProperties();
        Intrinsics.checkNotNullExpressionValue((Object)collection, (String)"getProperties(...)");
        Collection props = collection;
        if (props.isEmpty()) {
            ChatUtil.INSTANCE.warning(StringsKt.removePrefix((String)baseId, (CharSequence)"minecraft:") + " has no properties");
            return;
        }
        ChatUtil.INSTANCE.success("Properties for " + StringsKt.removePrefix((String)baseId, (CharSequence)"minecraft:") + ":");
        for (Object e : props) {
            String string3;
            Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
            Property p = (Property)e;
            if (stateProps.containsKey(p)) {
                Object v = stateProps.get(p);
                Intrinsics.checkNotNull(v, (String)"null cannot be cast to non-null type kotlin.Comparable<kotlin.Any>");
                string3 = BlockStateCodec.INSTANCE.getPropertyValueName$cb_client(p, (Comparable)v);
            } else {
                string3 = "?";
            }
            String current = string3;
            List list = p.getPossibleValues();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"getPossibleValues(...)");
            String options = CollectionsKt.joinToString$default((Iterable)list, (CharSequence)", ", null, null, (int)0, null, arg_0 -> EditMode.listBlockProperties$lambda$1(p, arg_0), (int)30, null);
            ChatUtil.INSTANCE.send("  " + p.getName() + "=" + current + " \u00a78[" + options + "]");
        }
    }

    @Nullable
    public final Pair<BlockPos, Direction> findPhantomHitResult$cb_client() {
        LocalPlayer localPlayer = this.getMc().player;
        if (localPlayer == null) {
            return null;
        }
        LocalPlayer player = localPlayer;
        Vec3 vec3 = player.getEyePosition(1.0f);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"getEyePosition(...)");
        Vec3 eyePos = vec3;
        Vec3 vec32 = player.getLookAngle();
        Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"getLookAngle(...)");
        Vec3 lookVec = vec32;
        double reach = 5.0;
        double step = 0.15;
        Set checked = new LinkedHashSet();
        for (double dist = 0.0; dist <= reach; dist += step) {
            int bz;
            int by;
            int bx = (int)Math.floor(eyePos.x + lookVec.x * dist);
            long packed = BlockPos.asLong((int)bx, (int)(by = (int)Math.floor(eyePos.y + lookVec.y * dist)), (int)(bz = (int)Math.floor(eyePos.z + lookVec.z * dist)));
            if (!checked.add(packed) || !ReplacementCache.INSTANCE.getPhantomPositions$cb_client().contains(packed)) continue;
            Direction face = this.getEntryFace(eyePos.x, eyePos.y, eyePos.z, lookVec.x, lookVec.y, lookVec.z, bx, by, bz);
            return new Pair((Object)new BlockPos(bx, by, bz), (Object)face);
        }
        return null;
    }

    @Nullable
    public final BlockPos findPhantomTarget$cb_client() {
        Pair<BlockPos, Direction> pair = this.findPhantomHitResult$cb_client();
        return pair != null ? (BlockPos)pair.getFirst() : null;
    }

    private final Direction getEntryFace(double eyeX, double eyeY, double eyeZ, double dirX, double dirY, double dirZ, int blockX, int blockY, int blockZ) {
        double t2;
        double t1;
        double tEntry;
        double minX = blockX;
        double minY = blockY;
        double minZ = blockZ;
        double maxX = minX + 1.0;
        double maxY = minY + 1.0;
        double maxZ = minZ + 1.0;
        double tNear = Double.NEGATIVE_INFINITY;
        Direction entryFace = Direction.NORTH;
        if (!(dirX == 0.0) && (tEntry = Math.min(t1 = (minX - eyeX) / dirX, t2 = (maxX - eyeX) / dirX)) > tNear) {
            tNear = tEntry;
            entryFace = dirX > 0.0 ? Direction.WEST : Direction.EAST;
        }
        if (!(dirY == 0.0) && (tEntry = Math.min(t1 = (minY - eyeY) / dirY, t2 = (maxY - eyeY) / dirY)) > tNear) {
            tNear = tEntry;
            entryFace = dirY > 0.0 ? Direction.DOWN : Direction.UP;
        }
        if (!(dirZ == 0.0) && (tEntry = Math.min(t1 = (minZ - eyeZ) / dirZ, t2 = (maxZ - eyeZ) / dirZ)) > tNear) {
            tNear = tEntry;
            entryFace = dirZ > 0.0 ? Direction.NORTH : Direction.SOUTH;
        }
        return entryFace;
    }

    public final void tickEditMode$cb_client() {
        Object blockName;
        BlockPos pos;
        boolean middlePressed;
        if (this.getMc().gui.getScreen() != null) {
            return;
        }
        long window = GLFW.glfwGetCurrentContext();
        boolean leftPressed = GLFW.glfwGetMouseButton((long)window, (int)0) == 1;
        boolean rightPressed = GLFW.glfwGetMouseButton((long)window, (int)1) == 1;
        boolean bl = middlePressed = GLFW.glfwGetMouseButton((long)window, (int)2) == 1;
        if (rightPressed && !rightWasPressed) {
            Pair<BlockPos, Direction> phantomHit;
            HitResult hitResult = this.getMc().hitResult;
            boolean placeMode = (Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue();
            boolean hasExplicit = this.hasExplicitProperties();
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == HitResult.Type.BLOCK) {
                if (placeMode) {
                    ClientLevel level;
                    ClientLevel clientLevel = level = this.getMc().level;
                    BlockState hitState = clientLevel != null ? clientLevel.getBlockState(((BlockHitResult)hitResult).getBlockPos()) : null;
                    boolean hitIsFull = hitState != null && hitState.isCollisionShapeFullBlock((BlockGetter)level, ((BlockHitResult)hitResult).getBlockPos());
                    BlockPos blockPos = hitIsFull ? ((BlockHitResult)hitResult).getBlockPos().relative(((BlockHitResult)hitResult).getDirection()) : ((BlockHitResult)hitResult).getBlockPos();
                    Intrinsics.checkNotNull((Object)blockPos);
                    BlockPos pos2 = blockPos;
                    ClientLevel clientLevel2 = level;
                    boolean isAir = clientLevel2 != null && (clientLevel2 = clientLevel2.getBlockState(pos2)) != null ? clientLevel2.isAir() : true;
                    boolean isNonFull = !hitIsFull && Intrinsics.areEqual((Object)pos2, (Object)((BlockHitResult)hitResult).getBlockPos());
                    boolean hasCFME = ReplacementCache.INSTANCE.getActiveCache$cb_client().containsKey(BlockPos.asLong((int)pos2.getX(), (int)pos2.getY(), (int)pos2.getZ()));
                    if ((isAir || isNonFull) && !hasCFME) {
                        if (hasExplicit) {
                            ReplacementStore.INSTANCE.setReplacement(pos2, selectedBlock, true);
                        } else {
                            BlockState placementState = BlockStateCodec.INSTANCE.computePlacementState$cb_client(selectedBlock, (BlockHitResult)hitResult);
                            if (placementState != null) {
                                ReplacementStore.INSTANCE.setReplacementState(pos2, placementState, true);
                            } else {
                                ReplacementStore.INSTANCE.setReplacement(pos2, selectedBlock, true);
                            }
                        }
                        String blockName2 = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
                        ChatUtil.INSTANCE.success(pos2.getX() + "," + pos2.getY() + "," + pos2.getZ() + " -> " + blockName2);
                    }
                } else if (fillMode) {
                    BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                    Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
                    ChatUtil.INSTANCE.send(WorldEditMode.INSTANCE.floodFill(blockPos, selectedBlock));
                } else {
                    BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                    Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
                    pos = blockPos;
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
                    blockName = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
                    ChatUtil.INSTANCE.success(pos.getX() + "," + pos.getY() + "," + pos.getZ() + " -> " + blockName);
                }
            } else if (placeMode && (phantomHit = this.findPhantomHitResult$cb_client()) != null) {
                blockName = phantomHit;
                BlockPos phantomPos = (BlockPos)blockName.component1();
                Direction face = (Direction)blockName.component2();
                BlockPos blockPos = phantomPos.relative(face);
                Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"relative(...)");
                BlockPos pos3 = blockPos;
                ClientLevel level = this.getMc().level;
                ClientLevel clientLevel = level;
                boolean isAir = clientLevel != null && (clientLevel = clientLevel.getBlockState(pos3)) != null ? clientLevel.isAir() : true;
                boolean hasCFME = ReplacementCache.INSTANCE.getActiveCache$cb_client().containsKey(BlockPos.asLong((int)pos3.getX(), (int)pos3.getY(), (int)pos3.getZ()));
                if (isAir && !hasCFME) {
                    if (hasExplicit) {
                        ReplacementStore.INSTANCE.setReplacement(pos3, selectedBlock, true);
                    } else {
                        BlockHitResult syntheticHit = new BlockHitResult(new Vec3((double)phantomPos.getX() + 0.5, (double)phantomPos.getY() + 0.5, (double)phantomPos.getZ() + 0.5), face, phantomPos, false);
                        BlockState placementState = BlockStateCodec.INSTANCE.computePlacementState$cb_client(selectedBlock, syntheticHit);
                        if (placementState != null) {
                            ReplacementStore.INSTANCE.setReplacementState(pos3, placementState, true);
                        } else {
                            ReplacementStore.INSTANCE.setReplacement(pos3, selectedBlock, true);
                        }
                    }
                    String blockName3 = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
                    ChatUtil.INSTANCE.success(pos3.getX() + "," + pos3.getY() + "," + pos3.getZ() + " -> " + blockName3);
                }
            }
        }
        if (leftPressed && !leftWasPressed) {
            BlockPos phantomPos;
            boolean removed = false;
            BlockPos removedPos = null;
            HitResult hitResult = this.getMc().hitResult;
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
                pos = blockPos;
                removed = ReplacementStore.INSTANCE.removeReplacement(pos);
                removedPos = pos;
                if (!removed) {
                    BlockPos blockPos2 = removedPos = pos.relative(((BlockHitResult)hitResult).getDirection());
                    Intrinsics.checkNotNull((Object)blockPos2);
                    removed = ReplacementStore.INSTANCE.removeReplacement(blockPos2);
                }
            }
            if (!removed && (phantomPos = this.findPhantomTarget$cb_client()) != null) {
                removed = ReplacementStore.INSTANCE.removeReplacement(phantomPos);
                removedPos = phantomPos;
            }
            if (removed && removedPos != null) {
                ChatUtil.INSTANCE.success("Removed at " + removedPos.getX() + "," + removedPos.getY() + "," + removedPos.getZ());
            }
        }
        if (middlePressed && !middleWasPressed) {
            BlockState state;
            BlockPos phantomPos;
            boolean picked = false;
            HitResult hitResult = this.getMc().hitResult;
            if (hitResult instanceof BlockHitResult && ((BlockHitResult)hitResult).getType() == HitResult.Type.BLOCK) {
                BlockState state2;
                BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
                Intrinsics.checkNotNullExpressionValue((Object)blockPos, (String)"getBlockPos(...)");
                BlockPos pos4 = blockPos;
                BlockState replacement = ReplacementCache.getReplacementAt$default(ReplacementCache.INSTANCE, pos4.getX(), pos4.getY(), pos4.getZ(), false, 8, null);
                Object object = replacement;
                if (object == null) {
                    ClientLevel clientLevel = this.getMc().level;
                    object = clientLevel != null ? clientLevel.getBlockState(pos4) : null;
                }
                if ((state2 = object) != null && !state2.isAir()) {
                    selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state2);
                    String blockName4 = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
                    ChatUtil.INSTANCE.success("Brush set to " + blockName4);
                    picked = true;
                }
            }
            if (!picked && ((Boolean)CosmeticBlocksTab.INSTANCE.getPlaceMode().getValue()).booleanValue() && (phantomPos = this.findPhantomTarget$cb_client()) != null && (state = ReplacementCache.getReplacementAt$default(ReplacementCache.INSTANCE, phantomPos.getX(), phantomPos.getY(), phantomPos.getZ(), false, 8, null)) != null) {
                selectedBlock = BlockStateCodec.INSTANCE.serializeBlockState$cb_client(state);
                blockName = StringsKt.removePrefix((String)selectedBlock, (CharSequence)"minecraft:");
                ChatUtil.INSTANCE.success("Brush set to " + blockName);
            }
        }
        leftWasPressed = leftPressed;
        rightWasPressed = rightPressed;
        middleWasPressed = middlePressed;
    }

    private static final CharSequence setBlockProperty$lambda$0(Property it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        String string = it.getName();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getName(...)");
        return string;
    }

    private static final CharSequence setBlockProperty$lambda$1(Property $prop, Comparable it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client($prop, it);
    }

    private static final CharSequence setBlockProperty$lambda$2(Map.Entry it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return it.getKey() + "=" + it.getValue();
    }

    private static final CharSequence listBlockProperties$lambda$1(Property $p, Comparable it) {
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return BlockStateCodec.INSTANCE.getPropertyValueName$cb_client($p, it);
    }

    static {
        selectedBlock = "minecraft:stone";
    }
}


