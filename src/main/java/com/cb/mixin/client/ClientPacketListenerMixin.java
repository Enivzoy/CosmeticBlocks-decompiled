/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
 *  net.minecraft.network.protocol.game.ClientboundSetObjectivePacket
 *  net.minecraft.network.protocol.game.ClientboundSetTimePacket
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.cb.mixin.client;

import com.cb.cosmetic.CosmeticBlocks;
import com.cb.utils.ServerUtils;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public class ClientPacketListenerMixin {
    @Inject(method={"handleSetTime"}, at={@At(value="HEAD")})
    private void onHandleSetTime(ClientboundSetTimePacket packet, CallbackInfo ci) {
        ServerUtils.INSTANCE.onTimePacket();
    }

    @Inject(method={"handlePlayerInfoUpdate"}, at={@At(value="HEAD")})
    private void cfme$onPlayerInfoUpdate(ClientboundPlayerInfoUpdatePacket packet, CallbackInfo ci) {
        CosmeticBlocks.onPlayerInfoUpdate(packet);
    }

    @Inject(method={"handleAddObjective"}, at={@At(value="HEAD")})
    private void cfme$onSetObjective(ClientboundSetObjectivePacket packet, CallbackInfo ci) {
        CosmeticBlocks.onSetObjective(packet);
    }
}


