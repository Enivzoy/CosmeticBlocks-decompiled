package com.cb.mixin.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public abstract class GuiCompatMixin {
    public void setOverlayMessage(Component component, boolean animate) {
        ((Gui) (Object) this).hud.setOverlayMessage(component, animate);
    }
}
