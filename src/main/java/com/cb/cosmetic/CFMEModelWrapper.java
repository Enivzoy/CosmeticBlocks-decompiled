package com.cb.cosmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
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
import net.minecraft.client.resources.model.geometry.BakedQuad.MaterialFlags;
import net.minecraft.client.resources.model.sprite.Material.Baked;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J@\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0014H\u0016JD\u0010\u0016\u001a\u00020\b2\u000b\u0010\u0011\u001a\u00070\u0012¢\u0006\u0002\b\u00172,\u0010\u0018\u001a(\u0012\t\u0012\u00070\u001a¢\u0006\u0002\b\u0017 \u001c*\u0012\u0012\t\u0012\u00070\u001a¢\u0006\u0002\b\u00170\u001b¢\u0006\u0002\b\u00170\u0019¢\u0006\u0002\b\u0017H\u0096\u0001J\u000e\u0010\u001d\u001a\u00070\u001e¢\u0006\u0002\b\u001fH\u0097\u0001J\u000e\u0010 \u001a\u00070!¢\u0006\u0002\b\u0017H\u0096\u0001R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006#",
   d2 = {
         "Lcom/cb/cosmetic/CFMEModelWrapper;",
         "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;",
         "wrapped",
         "<init>",
         "(Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;)V",
         "getWrapped",
         "()Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;",
         "emitQuads",
         "",
         "emitter",
         "Lnet/fabricmc/fabric/api/client/renderer/v1/mesh/QuadEmitter;",
         "blockView",
         "Lnet/minecraft/client/renderer/block/BlockAndTintGetter;",
         "pos",
         "Lnet/minecraft/core/BlockPos;",
         "state",
         "Lnet/minecraft/world/level/block/state/BlockState;",
         "random",
         "Lnet/minecraft/util/RandomSource;",
         "cullTest",
         "Ljava/util/function/Predicate;",
         "Lnet/minecraft/core/Direction;",
         "collectParts",
         "Lkotlin/jvm/internal/EnhancedNullability;",
         "output",
         "",
         "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModelPart;",
         "",
         "kotlin.jvm.PlatformType",
         "materialFlags",
         "",
         "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialFlags;",
         "particleMaterial",
         "Lnet/minecraft/client/resources/model/sprite/Material$Baked;",
         "Companion",
         "cb_client"
   }
)
public final class CFMEModelWrapper implements BlockStateModel {
   @NotNull
   public static final CFMEModelWrapper.Companion Companion = new CFMEModelWrapper.Companion();
   @NotNull
   private final BlockStateModel wrapped;
   @NotNull
   private static final List<Direction> DIRECTIONS = buildDirections();

   private static List<Direction> buildDirections() {
      List<Direction> list = new ArrayList<>(Arrays.asList(Direction.values()));
      list.add(null);
      return list;
   }

   public CFMEModelWrapper(@NotNull BlockStateModel wrapped) {
      Intrinsics.checkNotNullParameter(wrapped, "wrapped");
      super();
      this.wrapped = wrapped;
   }

   @NotNull
   public final BlockStateModel getWrapped() {
      return this.wrapped;
   }

   @Override
   public void emitQuads(
      @NotNull QuadEmitter emitter,
      @NotNull BlockAndTintGetter blockView,
      @NotNull BlockPos pos,
      @NotNull BlockState state,
      @NotNull RandomSource random,
      @NotNull Predicate<Direction> cullTest
   ) {
      Intrinsics.checkNotNullParameter(emitter, "emitter");
      Intrinsics.checkNotNullParameter(blockView, "blockView");
      Intrinsics.checkNotNullParameter(pos, "pos");
      Intrinsics.checkNotNullParameter(state, "state");
      Intrinsics.checkNotNullParameter(random, "random");
      Intrinsics.checkNotNullParameter(cullTest, "cullTest");
      BlockState retextureState = CosmeticBlocks.INSTANCE.getRetextureAt(pos.getX(), pos.getY(), pos.getZ());
      if (retextureState != null) {
         Baked newMaterial = Companion.particleMaterial(retextureState);
         boolean skipCull = true;
         Companion.emitRetextured(emitter, this.wrapped, random, cullTest, newMaterial, skipCull, null);
      } else {
         BlockState originalState = CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.getX(), pos.getY(), pos.getZ());
         if (originalState != null) {
            Baked newMaterial = Companion.particleMaterial(state);
            BlockStateModel var10000 = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(originalState);
            Intrinsics.checkNotNullExpressionValue(var10000, "get(...)");
            BlockStateModel originalModel = var10000;
            if (originalModel instanceof CFMEModelWrapper) {
               originalModel = ((CFMEModelWrapper)originalModel).wrapped;
            }

            Set connectedDirs = new LinkedHashSet();

            for (Direction dir : Direction.values()) {
               BlockState var18 = blockView.getBlockState(pos.relative(dir));
               Intrinsics.checkNotNullExpressionValue(var18, "getBlockState(...)");
               BlockState adjState = var18;
               if (state.skipRendering(adjState, dir)) {
                  connectedDirs.add(dir);
               }
            }

            Companion.emitRetextured(emitter, originalModel, random, cullTest, newMaterial, true, connectedDirs);
         } else {
            Predicate<Direction> modifiedCullTest = dir -> emitQuads$lambda$0(pos, blockView, state, (Predicate)cullTest, dir);
            this.wrapped.emitQuads(emitter, blockView, pos, state, random, modifiedCullTest);
         }
      }
   }

   @Override
   public void collectParts(@NotNull RandomSource random, @NotNull List<BlockStateModelPart> output) {
      Intrinsics.checkNotNullParameter(random, "random");
      Intrinsics.checkNotNullParameter(output, "output");
      this.wrapped.collectParts(random, output);
   }

   @NotNull
   @Override
   public Baked particleMaterial() {
      Baked var10000 = this.wrapped.particleMaterial();
      Intrinsics.checkNotNullExpressionValue(var10000, "particleMaterial(...)");
      return var10000;
   }

   @MaterialFlags
   @Override
   public int materialFlags() {
      return this.wrapped.materialFlags();
   }

   private static final boolean emitQuads$lambda$0(BlockPos $pos, BlockAndTintGetter $blockView, BlockState $state, Predicate $cullTest, Direction dir) {
      if (dir == null) {
         return false;
      }

      int adjX = $pos.getX() + dir.getStepX();
      int adjY = $pos.getY() + dir.getStepY();
      int adjZ = $pos.getZ() + dir.getStepZ();
      BlockState adjReplacement = CosmeticBlocks.INSTANCE.getReplacementAt(adjX, adjY, adjZ, false);
      if (adjReplacement != null) {
         if (adjReplacement.getBlock() instanceof StairBlock || adjReplacement.getBlock() instanceof SlabBlock) {
            return false;
         }

         if (CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjX, adjY, adjZ) != null) {
            return false;
         }
      }

      BlockState var10000 = $blockView.getBlockState(new BlockPos(adjX, adjY, adjZ));
      Intrinsics.checkNotNullExpressionValue(var10000, "getBlockState(...)");
      BlockState adjState = var10000;
      if ($state.skipRendering(adjState, dir)) {
         return true;
      } else {
         return Intrinsics.areEqual($state.getBlock(), adjState.getBlock()) && $state.getBlock() instanceof IronBarsBlock ? true : $cullTest.test(dir);
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002JP\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00142\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d",
      d2 = {
            "Lcom/cb/cosmetic/CFMEModelWrapper$Companion;",
            "",
            "<init>",
            "()V",
            "DIRECTIONS",
            "",
            "Lnet/minecraft/core/Direction;",
            "particleMaterial",
            "Lnet/minecraft/client/resources/model/sprite/Material$Baked;",
            "state",
            "Lnet/minecraft/world/level/block/state/BlockState;",
            "emitRetextured",
            "",
            "emitter",
            "Lnet/fabricmc/fabric/api/client/renderer/v1/mesh/QuadEmitter;",
            "geometryModel",
            "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;",
            "random",
            "Lnet/minecraft/util/RandomSource;",
            "cullTest",
            "Ljava/util/function/Predicate;",
            "newMaterial",
            "skipCull",
            "",
            "connectedDirs",
            "",
            "normalizeUVs",
            "quad",
            "Lnet/minecraft/client/resources/model/geometry/BakedQuad;",
            "cb_client"
      }
   )
   public static final class Companion {
      private Companion() {
      }

      private final Baked particleMaterial(BlockState state) {
         Baked var10000 = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(state);
         Intrinsics.checkNotNullExpressionValue(var10000, "getParticleMaterial(...)");
         return var10000;
      }

      private final void emitRetextured(
         QuadEmitter emitter,
         BlockStateModel geometryModel,
         RandomSource random,
         Predicate<Direction> cullTest,
         Baked newMaterial,
         boolean skipCull,
         Set<? extends Direction> connectedDirs
      ) {
         ArrayList parts = new ArrayList();
         geometryModel.collectParts(random, parts);
         Iterator var9 = parts.iterator();

         while (var9.hasNext()) {
            BlockStateModelPart part = (BlockStateModelPart)var9.next();
            TriState ao = part.useAmbientOcclusion() ? TriState.DEFAULT : TriState.FALSE;

            for (Direction dir : CFMEModelWrapper.DIRECTIONS) {
               if ((dir == null || !connectedDirs.contains(dir)) && (skipCull || !cullTest.test(dir))) {
                  for (Object var17 : part.getQuads(dir)) {
                     Intrinsics.checkNotNullExpressionValue(var17, "next(...)");
                     BakedQuad quad = (BakedQuad)var17;
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
      }

      private final void normalizeUVs(QuadEmitter emitter, BakedQuad quad) {
         TextureAtlasSprite var10000 = quad.materialInfo().sprite();
         Intrinsics.checkNotNullExpressionValue(var10000, "sprite(...)");
         TextureAtlasSprite oldSprite = var10000;
         float u0 = oldSprite.getU0();
         float v0 = oldSprite.getV0();
         float uRange = oldSprite.getU1() - u0;
         float vRange = oldSprite.getV1() - v0;
         if (!(uRange <= 0.0F) && !(vRange <= 0.0F)) {
            for (int v = 0; v < 4; v++) {
               emitter.uv(v, (emitter.u(v) - u0) / uRange, (emitter.v(v) - v0) / vRange);
            }
         }
      }
   }
}
