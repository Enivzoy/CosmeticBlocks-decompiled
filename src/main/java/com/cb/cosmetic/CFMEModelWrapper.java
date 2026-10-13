/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter
 *  net.fabricmc.fabric.api.client.renderer.v1.mesh.ShadeMode
 *  net.fabricmc.fabric.api.util.TriState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.block.BlockAndTintGetter
 *  net.minecraft.client.renderer.block.dispatch.BlockStateModel
 *  net.minecraft.client.renderer.block.dispatch.BlockStateModelPart
 *  net.minecraft.client.renderer.texture.TextureAtlasSprite
 *  net.minecraft.client.resources.model.geometry.BakedQuad
 *  net.minecraft.client.resources.model.geometry.BakedQuad$MaterialFlags
 *  net.minecraft.client.resources.model.sprite.Material$Baked
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.IronBarsBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.cosmetic;

import com.cb.cosmetic.CosmeticBlocks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.ShadeMode;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0003\u0010\u0004J@\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0014H\u0016JD\u0010\u0016\u001a\u00020\b2\u000b\u0010\u0011\u001a\u00070\u0012\u00a2\u0006\u0002\b\u00172,\u0010\u0018\u001a(\u0012\t\u0012\u00070\u001a\u00a2\u0006\u0002\b\u0017 \u001c*\u0012\u0012\t\u0012\u00070\u001a\u00a2\u0006\u0002\b\u00170\u001b\u00a2\u0006\u0002\b\u00170\u0019\u00a2\u0006\u0002\b\u0017H\u0096\u0001J\u000e\u0010\u001d\u001a\u00070\u001e\u00a2\u0006\u0002\b\u001fH\u0097\u0001J\u000e\u0010 \u001a\u00070!\u00a2\u0006\u0002\b\u0017H\u0096\u0001R\u0011\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006#"}, d2={"Lcom/cb/cosmetic/CFMEModelWrapper;", "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;", "wrapped", "<init>", "(Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;)V", "getWrapped", "()Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;", "emitQuads", "", "emitter", "Lnet/fabricmc/fabric/api/client/renderer/v1/mesh/QuadEmitter;", "blockView", "Lnet/minecraft/client/renderer/block/BlockAndTintGetter;", "pos", "Lnet/minecraft/core/BlockPos;", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "random", "Lnet/minecraft/util/RandomSource;", "cullTest", "Ljava/util/function/Predicate;", "Lnet/minecraft/core/Direction;", "collectParts", "Lkotlin/jvm/internal/EnhancedNullability;", "output", "", "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModelPart;", "", "kotlin.jvm.PlatformType", "materialFlags", "", "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialFlags;", "particleMaterial", "Lnet/minecraft/client/resources/model/sprite/Material$Baked;", "Companion", "cb_client"})
public final class CFMEModelWrapper
implements BlockStateModel {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BlockStateModel wrapped;
    @NotNull
    private static final List<Direction> DIRECTIONS = CollectionsKt.plus((Collection)((Collection)EntriesMappings.entries$0), (Iterable)CollectionsKt.listOf(null));

    public CFMEModelWrapper(@NotNull BlockStateModel wrapped) {
        Intrinsics.checkNotNullParameter((Object)wrapped, (String)"wrapped");
        this.wrapped = wrapped;
    }

    @NotNull
    public final BlockStateModel getWrapped() {
        return this.wrapped;
    }

    public void emitQuads(@NotNull QuadEmitter emitter, @NotNull BlockAndTintGetter blockView, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull RandomSource random, @NotNull Predicate<Direction> cullTest) {
        Intrinsics.checkNotNullParameter((Object)emitter, (String)"emitter");
        Intrinsics.checkNotNullParameter((Object)blockView, (String)"blockView");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter(cullTest, (String)"cullTest");
        BlockState retextureState = CosmeticBlocks.INSTANCE.getRetextureAt(pos.getX(), pos.getY(), pos.getZ());
        if (retextureState != null) {
            Material.Baked newMaterial = CFMEModelWrapper.Companion.particleMaterial(retextureState);
            boolean skipCull = true;
            com.cb.cosmetic.CFMEModelWrapper$Companion.emitRetextured$default(Companion, emitter, this.wrapped, random, cullTest, newMaterial, skipCull, null, 64, null);
            return;
        }
        BlockState originalState = CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.getX(), pos.getY(), pos.getZ());
        if (originalState != null) {
            Material.Baked newMaterial = CFMEModelWrapper.Companion.particleMaterial(state);
            BlockStateModel blockStateModel = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(originalState);
            Intrinsics.checkNotNullExpressionValue((Object)blockStateModel, (String)"get(...)");
            BlockStateModel originalModel = blockStateModel;
            if (originalModel instanceof CFMEModelWrapper) {
                originalModel = ((CFMEModelWrapper)originalModel).wrapped;
            }
            Set connectedDirs = new LinkedHashSet();
            for (Direction dir : EntriesMappings.entries$0) {
                BlockState adjState;
                Intrinsics.checkNotNullExpressionValue((Object)blockView.getBlockState(pos.relative(dir)), (String)"getBlockState(...)");
                if (!state.skipRendering(adjState, dir)) continue;
                connectedDirs.add(dir);
            }
            CFMEModelWrapper.Companion.emitRetextured(emitter, originalModel, random, cullTest, newMaterial, true, connectedDirs);
            return;
        }
        Predicate<Direction> modifiedCullTest = arg_0 -> CFMEModelWrapper.emitQuads$lambda$0(pos, blockView, state, cullTest, arg_0);
        this.wrapped.emitQuads(emitter, blockView, pos, state, random, modifiedCullTest);
    }

    public void collectParts(@NotNull RandomSource random, @NotNull List<BlockStateModelPart> output) {
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter(output, (String)"output");
        this.wrapped.collectParts(random, output);
    }

    @NotNull
    public Material.Baked particleMaterial() {
        Material.Baked baked = this.wrapped.particleMaterial();
        Intrinsics.checkNotNullExpressionValue((Object)baked, (String)"particleMaterial(...)");
        return baked;
    }

    @BakedQuad.MaterialFlags
    public int materialFlags() {
        return this.wrapped.materialFlags();
    }

    private static final boolean emitQuads$lambda$0(BlockPos $pos, BlockAndTintGetter $blockView, BlockState $state, Predicate $cullTest, Direction dir) {
        int adjZ;
        int adjY;
        if (dir == null) {
            return false;
        }
        int adjX = $pos.getX() + dir.getStepX();
        BlockState adjReplacement = CosmeticBlocks.getReplacementAt$default(CosmeticBlocks.INSTANCE, adjX, adjY = $pos.getY() + dir.getStepY(), adjZ = $pos.getZ() + dir.getStepZ(), false, 8, null);
        if (adjReplacement != null) {
            if (adjReplacement.getBlock() instanceof StairBlock || adjReplacement.getBlock() instanceof SlabBlock) {
                return false;
            }
            if (CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjX, adjY, adjZ) != null) {
                return false;
            }
        }
        BlockState blockState = $blockView.getBlockState(new BlockPos(adjX, adjY, adjZ));
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"getBlockState(...)");
        BlockState adjState = blockState;
        if ($state.skipRendering(adjState, dir)) {
            return true;
        }
        if (Intrinsics.areEqual((Object)$state.getBlock(), (Object)adjState.getBlock()) && $state.getBlock() instanceof IronBarsBlock) {
            return true;
        }
        return $cullTest.test(dir);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002JP\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00142\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lcom/cb/cosmetic/CFMEModelWrapper$Companion;", "", "<init>", "()V", "DIRECTIONS", "", "Lnet/minecraft/core/Direction;", "particleMaterial", "Lnet/minecraft/client/resources/model/sprite/Material$Baked;", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "emitRetextured", "", "emitter", "Lnet/fabricmc/fabric/api/client/renderer/v1/mesh/QuadEmitter;", "geometryModel", "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;", "random", "Lnet/minecraft/util/RandomSource;", "cullTest", "Ljava/util/function/Predicate;", "newMaterial", "skipCull", "", "connectedDirs", "", "normalizeUVs", "quad", "Lnet/minecraft/client/resources/model/geometry/BakedQuad;", "cb_client"})
    public static final class Companion {
        private Companion() {
        }

        private final Material.Baked particleMaterial(BlockState state) {
            Material.Baked baked = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(state);
            Intrinsics.checkNotNullExpressionValue((Object)baked, (String)"getParticleMaterial(...)");
            return baked;
        }

        private final void emitRetextured(QuadEmitter emitter, BlockStateModel geometryModel, RandomSource random, Predicate<Direction> cullTest, Material.Baked newMaterial, boolean skipCull, Set<? extends Direction> connectedDirs) {
            ArrayList parts = new ArrayList();
            geometryModel.collectParts(random, (List)parts);
            Iterator iterator = parts.iterator();
            Intrinsics.checkNotNullExpressionValue(iterator, (String)"iterator(...)");
            Iterator iterator2 = iterator;
            while (iterator2.hasNext()) {
                Object e = iterator2.next();
                Intrinsics.checkNotNullExpressionValue(e, (String)"next(...)");
                BlockStateModelPart part = (BlockStateModelPart)e;
                TriState ao = part.useAmbientOcclusion() ? TriState.DEFAULT : TriState.FALSE;
                for (Direction dir : DIRECTIONS) {
                    if (dir != null && connectedDirs.contains(dir) || !skipCull && cullTest.test(dir)) continue;
                    for (Object e2 : part.getQuads(dir)) {
                        Intrinsics.checkNotNullExpressionValue(e2, (String)"next(...)");
                        BakedQuad quad = (BakedQuad)e2;
                        emitter.cullFace(skipCull ? null : dir);
                        emitter.fromBakedQuad(quad);
                        this.normalizeUVs(emitter, quad);
                        emitter.materialBake(newMaterial, 32);
                        emitter.tintIndex(-1);
                        emitter.ambientOcclusion(ao);
                        emitter.shadeMode(ShadeMode.VANILLA);
                        emitter.emit();
                    }
                }
            }
        }

        static /* synthetic */ void emitRetextured$default(Companion companion, QuadEmitter quadEmitter, BlockStateModel blockStateModel, RandomSource randomSource, Predicate predicate, Material.Baked baked, boolean bl, Set set, int n, Object object) {
            if ((n & 0x40) != 0) {
                set = SetsKt.emptySet();
            }
            companion.emitRetextured(quadEmitter, blockStateModel, randomSource, predicate, baked, bl, set);
        }

        private final void normalizeUVs(QuadEmitter emitter, BakedQuad quad) {
            TextureAtlasSprite textureAtlasSprite = quad.materialInfo().sprite();
            Intrinsics.checkNotNullExpressionValue((Object)textureAtlasSprite, (String)"sprite(...)");
            TextureAtlasSprite oldSprite = textureAtlasSprite;
            float u0 = oldSprite.getU0();
            float v0 = oldSprite.getV0();
            float uRange = oldSprite.getU1() - u0;
            float vRange = oldSprite.getV1() - v0;
            if (uRange <= 0.0f || vRange <= 0.0f) {
                return;
            }
            for (int v = 0; v < 4; ++v) {
                emitter.uv(v, (emitter.u(v) - u0) / uRange, (emitter.v(v) - v0) / vRange);
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class EntriesMappings {
        public static final /* synthetic */ EnumEntries<Direction> entries$0;

        static {
            entries$0 = EnumEntriesKt.enumEntries((Enum[])((Enum[])Direction.values()));
        }
    }
}


