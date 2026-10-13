package com.cb.mixin.client;

import com.cb.dungeon.RoomScanner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RoomScanner.class)
public abstract class RoomScannerCompatMixin {
    @Redirect(method = "findRotation", at = @At(value = "FIELD", opcode = 178, target = "Lnet/minecraft/world/level/block/Blocks;BLUE_TERRACOTTA:Lnet/minecraft/world/level/block/Block;"))
    private static Block cb$blueTerracottaForRotation() {
        return Blocks.DYED_TERRACOTTA.blue();
    }

    @Redirect(method = "validateClay", at = @At(value = "FIELD", opcode = 178, target = "Lnet/minecraft/world/level/block/Blocks;BLUE_TERRACOTTA:Lnet/minecraft/world/level/block/Block;"))
    private static Block cb$blueTerracottaForClay() {
        return Blocks.DYED_TERRACOTTA.blue();
    }

    @Redirect(method = "detectDoorType", at = @At(value = "FIELD", opcode = 178, target = "Lnet/minecraft/world/level/block/Blocks;RED_TERRACOTTA:Lnet/minecraft/world/level/block/Block;"))
    private static Block cb$redTerracotta() {
        return Blocks.DYED_TERRACOTTA.red();
    }
}
