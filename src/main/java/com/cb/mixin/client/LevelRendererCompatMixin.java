package com.cb.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererCompatMixin {
    public void setSectionDirty(int x, int y, int z) {
        Minecraft.getInstance().levelExtractor.setSectionDirty(x, y, z);
    }

    public void allChanged() {
        Minecraft.getInstance().levelExtractor.allChanged();
    }
}
