package com.cb.cosmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
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
         Companion.emitRetextured(emitter, this.wrapped, random, cullTest, newMaterial, skipCull, Collections.emptySet());
      } else {
         BlockState originalState = CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.getX(), pos.getY(), pos.getZ());
         if (originalState != null) {
             Baked newMaterial = Companion.particleMaterial(state);
             BlockStateModel originalModel = Minecraft.getInstance().getModelManager().getBlockStateModelSet().get(originalState);
             Intrinsics.checkNotNullExpressionValue(originalModel, "get(...)");
             if (originalModel instanceof CFMEModelWrapper) {
                originalModel = ((CFMEModelWrapper)originalModel).wrapped;
             }

             Set connectedDirs = new LinkedHashSet();

             for (Direction dir : Direction.values()) {
                BlockState adjState = blockView.getBlockState(pos.relative(dir));
                Intrinsics.checkNotNullExpressionValue(adjState, "getBlockState(...)");
                if (state.skipRendering(adjState, dir)) {
                   connectedDirs.add(dir);
                }
             }

             Companion.emitRetextured(emitter, originalModel, random, cullTest, newMaterial, true, connectedDirs);
          } else {
             Predicate<Direction> modifiedCullTest = dir -> shouldCull(pos, blockView, state, (Predicate)cullTest, dir);
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
      Baked material = this.wrapped.particleMaterial();
      Intrinsics.checkNotNullExpressionValue(material, "particleMaterial(...)");
      return material;
   }

   @MaterialFlags
   @Override
   public int materialFlags() {
      return this.wrapped.materialFlags();
   }

   private static final boolean shouldCull(BlockPos pos, BlockAndTintGetter blockView, BlockState state, Predicate cullTest, Direction dir) {
      if (dir == null) {
         return false;
      }

      int adjX = pos.getX() + dir.getStepX();
      int adjY = pos.getY() + dir.getStepY();
      int adjZ = pos.getZ() + dir.getStepZ();
      BlockState adjReplacement = CosmeticBlocks.INSTANCE.getReplacementAt(adjX, adjY, adjZ, false);
      if (adjReplacement != null) {
         if (adjReplacement.getBlock() instanceof StairBlock || adjReplacement.getBlock() instanceof SlabBlock) {
            return false;
         }

         if (CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjX, adjY, adjZ) != null) {
            return false;
         }
      }

      BlockState adjState = blockView.getBlockState(new BlockPos(adjX, adjY, adjZ));
      Intrinsics.checkNotNullExpressionValue(adjState, "getBlockState(...)");
      if (state.skipRendering(adjState, dir)) {
         return true;
      } else {
         return Intrinsics.areEqual(state.getBlock(), adjState.getBlock()) && state.getBlock() instanceof IronBarsBlock ? true : cullTest.test(dir);
      }
   }

   public static final class Companion {
      private Companion() {
      }

      private final Baked particleMaterial(BlockState state) {
         Baked material = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(state);
         Intrinsics.checkNotNullExpressionValue(material, "getParticleMaterial(...)");
         return material;
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

         for (BlockStateModelPart part : (Iterable<BlockStateModelPart>)parts) {
            TriState ao = part.useAmbientOcclusion() ? TriState.DEFAULT : TriState.FALSE;

            for (Direction dir : CFMEModelWrapper.DIRECTIONS) {
               if ((dir == null || !connectedDirs.contains(dir)) && (skipCull || !cullTest.test(dir))) {
                  for (BakedQuad quad : part.getQuads(dir)) {
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
         TextureAtlasSprite oldSprite = quad.materialInfo().sprite();
         Intrinsics.checkNotNullExpressionValue(oldSprite, "sprite(...)");
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
