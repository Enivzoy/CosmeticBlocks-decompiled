/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.CrossCollisionBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package me.enivzoy.cosmeticblocksreborn.mixin.client;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import java.lang.reflect.Method;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets={"net.caffeinemc.mods.sodium.client.world.LevelSlice"}, remap=false)
public class SodiumLevelSliceMixin {
    @Unique
    private static final ThreadLocal<Boolean> cfme$inSwap = ThreadLocal.withInitial(() -> false);
    @Unique
    private static final ThreadLocal<Boolean> cfme$handledByPrimitive = ThreadLocal.withInitial(() -> false);
    @Unique
    private static volatile Method cfme$cachedMethod = null;

    @Unique
    private Method cfme$ensureMethod() {
        if (cfme$cachedMethod == null) {
            try {
                cfme$cachedMethod = this.getClass().getMethod("getBlockState", Integer.TYPE, Integer.TYPE, Integer.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return cfme$cachedMethod;
    }

    @Inject(method={"getBlockState(III)Lnet/minecraft/world/level/block/state/BlockState;"}, at={@At(value="RETURN")}, cancellable=true, require=0, remap=true)
    private void cfme$swapBlockState(int x, int y, int z, CallbackInfoReturnable<BlockState> cir) {
        BlockState swapped;
        if (cfme$inSwap.get().booleanValue()) {
            return;
        }
        cfme$handledByPrimitive.set(true);
        BlockState original = (BlockState)cir.getReturnValue();
        boolean isAir = original == null || original.isAir();
        BlockState replacement = CosmeticBlocks.getReplacementOrRemap(original, x, y, z, isAir);
        if (replacement != null) {
            BlockState result = CosmeticBlocks.INSTANCE.applyOriginalShape(original, replacement, x, y, z);
            SodiumLevelSliceMixin self = this;
            Method method = this.cfme$ensureMethod();
            result = CosmeticBlocks.INSTANCE.updateConnections(result, x, y, z, (nx, ny, nz) -> {
                try {
                    return method != null ? (BlockState)method.invoke(self, nx, ny, nz) : null;
                }
                catch (Exception e) {
                    return null;
                }
            });
            cir.setReturnValue(result);
            return;
        }
        if (!isAir && CosmeticBlocks.INSTANCE.hasActiveReplacements() && (original.getBlock() instanceof CrossCollisionBlock || original.getBlock() instanceof WallBlock)) {
            BlockState updated;
            SodiumLevelSliceMixin self = this;
            Method method = this.cfme$ensureMethod();
            if (method != null && (updated = CosmeticBlocks.INSTANCE.updateConnections(original, x, y, z, (nx, ny, nz) -> {
                try {
                    return (BlockState)method.invoke(self, nx, ny, nz);
                }
                catch (Exception e) {
                    return null;
                }
            })) != original) {
                cir.setReturnValue(updated);
                return;
            }
        }
        if ((swapped = CosmeticBlocks.swapFluid(original)) != null) {
            cir.setReturnValue(swapped);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Inject(method={"getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"}, at={@At(value="RETURN")}, cancellable=true, require=0, remap=true)
    private void cfme$swapBlockStatePos(BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (cfme$inSwap.get().booleanValue()) {
            return;
        }
        if (cfme$handledByPrimitive.get().booleanValue()) {
            cfme$handledByPrimitive.set(false);
            return;
        }
        cfme$inSwap.set(true);
        try {
            BlockState swapped;
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            BlockState original = (BlockState)cir.getReturnValue();
            boolean isAir = original == null || original.isAir();
            BlockState replacement = CosmeticBlocks.getReplacementOrRemap(original, x, y, z, isAir);
            if (replacement != null) {
                BlockState result = CosmeticBlocks.INSTANCE.applyOriginalShape(original, replacement, x, y, z);
                SodiumLevelSliceMixin self = this;
                Method method = this.cfme$ensureMethod();
                result = CosmeticBlocks.INSTANCE.updateConnections(result, x, y, z, (nx, ny, nz) -> {
                    try {
                        return method != null ? (BlockState)method.invoke(self, nx, ny, nz) : null;
                    }
                    catch (Exception e) {
                        return null;
                    }
                });
                cir.setReturnValue(result);
                return;
            }
            if (!isAir && CosmeticBlocks.INSTANCE.hasActiveReplacements() && (original.getBlock() instanceof CrossCollisionBlock || original.getBlock() instanceof WallBlock)) {
                BlockState updated;
                SodiumLevelSliceMixin self = this;
                Method method = this.cfme$ensureMethod();
                if (method != null && (updated = CosmeticBlocks.INSTANCE.updateConnections(original, x, y, z, (nx, ny, nz) -> {
                    try {
                        return (BlockState)method.invoke(self, nx, ny, nz);
                    }
                    catch (Exception e) {
                        return null;
                    }
                })) != original) {
                    cir.setReturnValue(updated);
                    return;
                }
            }
            if ((swapped = CosmeticBlocks.swapFluid(original)) != null) {
                cir.setReturnValue(swapped);
            }
        }
        finally {
            cfme$inSwap.set(false);
        }
    }
}

