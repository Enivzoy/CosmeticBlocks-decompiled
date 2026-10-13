/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.NVGRenderer;
import com.cb.setting.KeybindSetting;
import com.cb.setting.Setting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J \u0010\u0013\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0015H\u0016R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0019"}, d2={"Lcom/cb/gui/component/KeybindComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/KeybindSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/KeybindSetting;)V", "value", "", "listening", "getListening", "()Z", "render", "", "mouseX", "", "mouseY", "delta", "mouseClicked", "button", "", "keyPressed", "key", "modifiers", "cb_client"})
public final class KeybindComponent
extends SettingComponent<KeybindSetting> {
    private boolean listening;

    public KeybindComponent(@NotNull ClickGuiScreen gui, @NotNull KeybindSetting setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
        super(gui, (Setting)setting);
    }

    public final boolean getListening() {
        return this.listening;
    }

    @Override
    public void render(float mouseX, float mouseY, float delta) {
        this.renderRowBase(mouseX, mouseY, delta);
        String label = this.listening ? "\u2026" : ((KeybindSetting)this.getSetting()).displayName();
        float chipWidth = NVGRenderer.INSTANCE.textWidth(label, 12.0f, NVGRenderer.Font.MEDIUM) + 14.0f;
        float chipHeight = 16.0f;
        float chipX = this.getPadRight() - chipWidth;
        float chipY = this.getY() + (26.0f - chipHeight) / 2.0f;
        NVGRenderer.INSTANCE.rect(chipX, chipY, chipWidth, chipHeight, -14013910, 4.0f);
        if (this.listening) {
            NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, chipX, chipY, chipWidth, chipHeight, -1750963, 4.0f, 0.0f, 64, null);
        }
        NVGRenderer.INSTANCE.textCentered(label, chipX + chipWidth / 2.0f, chipY + chipHeight / 2.0f, 12.0f, this.listening ? -1750963 : (((KeybindSetting)this.getSetting()).getBound() ? -1184275 : -10724260), NVGRenderer.Font.MEDIUM);
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (this.listening) {
            this.listening = false;
            return this.rowHovered(mouseX, mouseY);
        }
        if (button == 0 && this.rowHovered(mouseX, mouseY)) {
            this.listening = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int key, int modifiers) {
        if (!this.listening) {
            return false;
        }
        switch (key) {
            case 256: 
            case 261: {
                ((KeybindSetting)this.getSetting()).clear();
                break;
            }
            default: {
                ((KeybindSetting)this.getSetting()).setValue(key);
            }
        }
        this.listening = false;
        return true;
    }
}


