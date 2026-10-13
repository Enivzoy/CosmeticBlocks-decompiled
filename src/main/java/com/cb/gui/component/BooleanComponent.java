/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.BooleanSetting;
import com.cb.setting.Setting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016J \u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J \u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\rH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lcom/cb/gui/component/BooleanComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/BooleanSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/BooleanSetting;)V", "knobAnim", "Lcom/cb/render/Anim;", "render", "", "mouseX", "", "mouseY", "delta", "mouseClicked", "", "button", "", "blend", "from", "to", "t", "cb_client"})
public final class BooleanComponent
extends SettingComponent<BooleanSetting> {
    @NotNull
    private final Anim knobAnim;

    public BooleanComponent(@NotNull ClickGuiScreen gui, @NotNull BooleanSetting setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
        super(gui, (Setting)setting);
        this.knobAnim = new Anim((Boolean)setting.getValue() != false ? 1.0f : 0.0f, 16.0f);
    }

    @Override
    public void render(float mouseX, float mouseY, float delta) {
        this.renderRowBase(mouseX, mouseY, delta);
        this.knobAnim.setTarget((Boolean)((BooleanSetting)this.getSetting()).getValue() != false ? 1.0f : 0.0f);
        float t = this.knobAnim.update(delta);
        float pillWidth = 28.0f;
        float pillHeight = 15.0f;
        float pillX = this.getPadRight() - pillWidth;
        float pillY = this.getY() + (26.0f - pillHeight) / 2.0f;
        int track = this.blend(-14013910, -1750963, t);
        NVGRenderer.INSTANCE.rect(pillX, pillY, pillWidth, pillHeight, track, pillHeight / 2.0f);
        float knobRadius = pillHeight / 2.0f - 2.5f;
        float knobX = pillX + knobRadius + 2.5f + (pillWidth - 2.0f * (knobRadius + 2.5f)) * t;
        NVGRenderer.INSTANCE.circle(knobX, pillY + pillHeight / 2.0f, knobRadius, -1184275);
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (button == 0 && this.rowHovered(mouseX, mouseY)) {
            ((BooleanSetting)this.getSetting()).toggle();
            return true;
        }
        return false;
    }

    private final int blend(int from, int to, float t) {
        float f = RangesKt.coerceIn((float)t, (float)0.0f, (float)1.0f);
        return BooleanComponent.blend$channel(from, to, f, 24) << 24 | BooleanComponent.blend$channel(from, to, f, 16) << 16 | BooleanComponent.blend$channel(from, to, f, 8) << 8 | BooleanComponent.blend$channel(from, to, f, 0);
    }

    private static final int blend$channel(int $from, int $to, float f, int shift) {
        int a = $from >> shift & 0xFF;
        int b = $to >> shift & 0xFF;
        return RangesKt.coerceIn((int)((int)((float)a + (float)(b - a) * f)), (int)0, (int)255);
    }
}


