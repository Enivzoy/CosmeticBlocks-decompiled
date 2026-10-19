package me.enivzoy.cosmeticblocksreborn.cosmetic

import java.util.Collections
import java.util.LinkedHashSet
import java.util.function.Predicate
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter
import net.fabricmc.fabric.api.client.renderer.v1.mesh.ShadeMode
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.BlockAndTintGetter
import net.minecraft.client.renderer.block.dispatch.BlockStateModel
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart
import net.minecraft.client.resources.model.geometry.BakedQuad
import net.minecraft.client.resources.model.sprite.Material.Baked
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.IronBarsBlock
import net.minecraft.world.level.block.SlabBlock
import net.minecraft.world.level.block.StairBlock
import net.minecraft.world.level.block.state.BlockState

class CFMEModelWrapper(val wrapped: BlockStateModel) : BlockStateModel {
   override fun emitQuads(
      emitter: QuadEmitter,
      blockView: BlockAndTintGetter,
      pos: BlockPos,
      state: BlockState,
      random: RandomSource,
      cullTest: Predicate<Direction?>
   ) {
      val retextureState = CosmeticBlocks.INSTANCE.getRetextureAt(pos.x, pos.y, pos.z)
      if (retextureState != null) {
         val newMaterial = particleMaterial(retextureState)
         val skipCull = true
         emitRetextured(emitter, wrapped, random, cullTest, newMaterial, skipCull, Collections.emptySet())
      } else {
         val originalState = CosmeticBlocks.INSTANCE.getOriginalShapeAt(pos.x, pos.y, pos.z)
         if (originalState != null) {
            val newMaterial = particleMaterial(state)
            var originalModel: BlockStateModel =
               Minecraft.getInstance().modelManager.blockStateModelSet.get(originalState)
            if (originalModel is CFMEModelWrapper) {
               originalModel = originalModel.wrapped
            }

            val connectedDirs: MutableSet<Direction> = LinkedHashSet()

            for (dir in Direction.values()) {
               val adjState = blockView.getBlockState(pos.relative(dir))
               if (state.skipRendering(adjState, dir)) {
                  connectedDirs.add(dir)
               }
            }

            emitRetextured(emitter, originalModel, random, cullTest, newMaterial, true, connectedDirs)
         } else {
            val modifiedCullTest = Predicate<Direction?> { dir -> shouldCull(pos, blockView, state, cullTest, dir) }
            wrapped.emitQuads(emitter, blockView, pos, state, random, modifiedCullTest)
         }
      }
   }

   override fun collectParts(random: RandomSource, output: MutableList<BlockStateModelPart>) {
      wrapped.collectParts(random, output)
   }

   override fun particleMaterial(): Baked = wrapped.particleMaterial()

   override fun materialFlags(): Int = wrapped.materialFlags()

   companion object {
      private val DIRECTIONS: List<Direction?> = buildDirections()

      private fun buildDirections(): List<Direction?> {
         val list = ArrayList<Direction?>()
         list.addAll(Direction.values())
         list.add(null)
         return list
      }

      private fun shouldCull(
         pos: BlockPos,
         blockView: BlockAndTintGetter,
         state: BlockState,
         cullTest: Predicate<Direction?>,
         dir: Direction?
      ): Boolean {
         if (dir == null) {
            return false
         }

         val adjX = pos.x + dir.stepX
         val adjY = pos.y + dir.stepY
         val adjZ = pos.z + dir.stepZ
         val adjReplacement = CosmeticBlocks.INSTANCE.getReplacementAt(adjX, adjY, adjZ, false)
         if (adjReplacement != null) {
            if (adjReplacement.block is StairBlock || adjReplacement.block is SlabBlock) {
               return false
            }

            if (CosmeticBlocks.INSTANCE.getOriginalShapeAt(adjX, adjY, adjZ) != null) {
               return false
            }
         }

         val adjState = blockView.getBlockState(BlockPos(adjX, adjY, adjZ))
         if (state.skipRendering(adjState, dir)) {
            return true
         }

         return (state.block === adjState.block && state.block is IronBarsBlock) || cullTest.test(dir)
      }

      private fun particleMaterial(state: BlockState): Baked =
         Minecraft.getInstance().modelManager.blockStateModelSet.getParticleMaterial(state)

      private fun emitRetextured(
         emitter: QuadEmitter,
         geometryModel: BlockStateModel,
         random: RandomSource,
         cullTest: Predicate<Direction?>,
         newMaterial: Baked,
         skipCull: Boolean,
         connectedDirs: Set<Direction?>
      ) {
         val parts = ArrayList<BlockStateModelPart>()
         geometryModel.collectParts(random, parts)

         for (part in parts) {
            val ao = if (part.useAmbientOcclusion()) TriState.DEFAULT else TriState.FALSE

            for (dir in DIRECTIONS) {
               if ((dir == null || !connectedDirs.contains(dir)) && (skipCull || !cullTest.test(dir))) {
                  for (quad in part.getQuads(dir)) {
                     emitter.cullFace(if (skipCull) null else dir)
                     emitter.fromBakedQuad(quad)
                     normalizeUVs(emitter, quad)
                     emitter.materialBake(newMaterial, 32)
                     emitter.tintIndex(-1)
                     emitter.ambientOcclusion(ao)
                     emitter.shadeMode(ShadeMode.VANILLA)
                     emitter.emit()
                  }
               }
            }
         }
      }

      private fun normalizeUVs(emitter: QuadEmitter, quad: BakedQuad) {
         val oldSprite = quad.materialInfo().sprite()
         val u0 = oldSprite.getU0()
         val v0 = oldSprite.getV0()
         val uRange = oldSprite.getU1() - u0
         val vRange = oldSprite.getV1() - v0
         if (!(uRange <= 0.0f) && !(vRange <= 0.0f)) {
            for (v in 0..3) {
               emitter.uv(v, (emitter.u(v) - u0) / uRange, (emitter.v(v) - v0) / vRange)
            }
         }
      }
   }
}

