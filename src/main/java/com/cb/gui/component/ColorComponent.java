/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ColorSetting;
import com.cb.setting.Setting;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 +2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002+,B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u000fH\u0016J\b\u0010\u001f\u001a\u00020\u001bH\u0002J\b\u0010 \u001a\u00020\u001bH\u0002J\b\u0010!\u001a\u00020\u001bH\u0002J\u0018\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020\u000fH\u0002J \u0010%\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020'H\u0016J\u0018\u0010(\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fH\u0016J\u0010\u0010)\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020'H\u0016J\u0018\u0010*\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0011R\u0014\u0010\u0016\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0011R\u0014\u0010\u0018\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011\u00a8\u0006-"}, d2={"Lcom/cb/gui/component/ColorComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/ColorSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ColorSetting;)V", "open", "", "drag", "Lcom/cb/gui/component/ColorComponent$Drag;", "openAnim", "Lcom/cb/render/Anim;", "height", "", "getHeight", "()F", "svY", "getSvY", "hueY", "getHueY", "alphaY", "getAlphaY", "innerWidth", "getInnerWidth", "render", "", "mouseX", "mouseY", "delta", "renderSvBox", "renderHueBar", "renderAlphaBar", "selectorLine", "selX", "barY", "mouseClicked", "button", "", "mouseDragged", "mouseReleased", "updateDrag", "Companion", "Drag", "cb_client"})
@SourceDebugExtension(value={"SMAP\nColorComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ColorComponent.kt\ncom/cb/gui/component/ColorComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n*L\n1#1,150:1\n445#2,8:151\n*S KotlinDebug\n*F\n+ 1 ColorComponent.kt\ncom/cb/gui/component/ColorComponent\n*L\n59#1:151,8\n*E\n"})
public final class ColorComponent
extends SettingComponent<ColorSetting> {
    @NotNull
    private static final Companion Companion = new Companion(null);
    private boolean open;
    @NotNull
    private Drag drag;
    @NotNull
    private final Anim openAnim;
    @Deprecated
    public static final float SV_HEIGHT = 86.0f;
    @Deprecated
    public static final float BAR_HEIGHT = 10.0f;
    @Deprecated
    public static final float GAP = 7.0f;
    @Deprecated
    public static final float EXTRA = 134.0f;

    public ColorComponent(@NotNull ClickGuiScreen gui, @NotNull ColorSetting setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
        super(gui, (Setting)setting);
        this.drag = Drag.NONE;
        this.openAnim = new Anim(0.0f, 16.0f);
    }

    @Override
    public float getHeight() {
        return 26.0f + this.openAnim.getValue() * 134.0f;
    }

    private final float getSvY() {
        return this.getY() + 26.0f + 7.0f;
    }

    private final float getHueY() {
        return this.getSvY() + 86.0f + 7.0f;
    }

    private final float getAlphaY() {
        return this.getHueY() + 10.0f + 7.0f;
    }

    private final float getInnerWidth() {
        return this.getPadRight() - this.getPadLeft();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    public void render(float mouseX, float mouseY, float delta) {
        void w$iv;
        void y$iv;
        void x$iv;
        void this_$iv;
        this.renderRowBase(mouseX, mouseY, delta);
        this.openAnim.setTarget(this.open ? 1.0f : 0.0f);
        this.openAnim.update(delta);
        float swatchWidth = 26.0f;
        float swatchHeight = 14.0f;
        float swatchX = this.getPadRight() - swatchWidth;
        float swatchY = this.getY() + (26.0f - swatchHeight) / 2.0f;
        NVGRenderer.INSTANCE.rect(swatchX, swatchY, swatchWidth, swatchHeight, ((Number)((ColorSetting)this.getSetting()).getValue()).intValue(), 4.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, swatchX, swatchY, swatchWidth, swatchHeight, 0x14FFFFFF, 4.0f, 0.0f, 64, null);
        if (this.openAnim.getValue() <= 0.01f) {
            return;
        }
        if (this.drag != Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
        }
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = this.getX();
        float f2 = this.getY() + 26.0f;
        float f3 = this.getWidth();
        float h$iv = this.openAnim.getValue() * 134.0f;
        boolean $i$f$scissor = false;
        this_$iv.save();
        this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            this.renderSvBox();
            this.renderHueBar();
            this.renderAlphaBar();
        }
        finally {
            this_$iv.restore();
        }
    }

    private final void renderSvBox() {
        int hueRgb = Color.HSBtoRGB(((ColorSetting)this.getSetting()).getHue(), 1.0f, 1.0f) & 0xFFFFFF | 0xFF000000;
        NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0f, 5.0f, -1, hueRgb, true);
        NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0f, 5.0f, 0, -16777216, false);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.getPadLeft(), this.getSvY(), this.getInnerWidth(), 86.0f, 0x14FFFFFF, 5.0f, 0.0f, 64, null);
        float selX = this.getPadLeft() + ((ColorSetting)this.getSetting()).getSaturation() * this.getInnerWidth();
        float selY = this.getSvY() + (1.0f - ((ColorSetting)this.getSetting()).getBrightness()) * 86.0f;
        NVGRenderer.INSTANCE.circle(selX, selY, 5.0f, -16777216);
        NVGRenderer.INSTANCE.circle(selX, selY, 4.0f, -1);
        NVGRenderer.INSTANCE.circle(selX, selY, 2.5f, ((ColorSetting)this.getSetting()).getOpaqueRgb());
    }

    private final void renderHueBar() {
        int segments = 6;
        float segmentWidth = this.getInnerWidth() / (float)segments;
        for (int i = 0; i < segments; ++i) {
            int from = Color.HSBtoRGB((float)i / (float)segments, 1.0f, 1.0f) & 0xFFFFFF | 0xFF000000;
            int to = Color.HSBtoRGB((float)(i + 1) / (float)segments, 1.0f, 1.0f) & 0xFFFFFF | 0xFF000000;
            NVGRenderer.INSTANCE.gradientRect(this.getPadLeft() + (float)i * segmentWidth, this.getHueY(), segmentWidth + 0.5f, 10.0f, 0.0f, from, to, true);
        }
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.getPadLeft(), this.getHueY(), this.getInnerWidth(), 10.0f, 0x14FFFFFF, 3.0f, 0.0f, 64, null);
        this.selectorLine(this.getPadLeft() + ((ColorSetting)this.getSetting()).getHue() * this.getInnerWidth(), this.getHueY());
    }

    private final void renderAlphaBar() {
        NVGRenderer.INSTANCE.rect(this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0f, -14013910, 3.0f);
        NVGRenderer.INSTANCE.gradientRect(this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0f, 3.0f, ((ColorSetting)this.getSetting()).getOpaqueRgb() & 0xFFFFFF, ((ColorSetting)this.getSetting()).getOpaqueRgb(), true);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.getPadLeft(), this.getAlphaY(), this.getInnerWidth(), 10.0f, 0x14FFFFFF, 3.0f, 0.0f, 64, null);
        this.selectorLine(this.getPadLeft() + ((ColorSetting)this.getSetting()).getAlpha() * this.getInnerWidth(), this.getAlphaY());
    }

    private final void selectorLine(float selX, float barY) {
        float clamped = RangesKt.coerceIn((float)selX, (float)(this.getPadLeft() + 1.0f), (float)(this.getPadRight() - 1.0f));
        NVGRenderer.INSTANCE.rect(clamped - 1.5f, barY - 1.5f, 3.0f, 13.0f, -16777216, 1.5f);
        NVGRenderer.INSTANCE.rect(clamped - 0.75f, barY - 0.75f, 1.5f, 11.5f, -1, 0.75f);
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (button != 0) {
            return false;
        }
        if (this.rowHovered(mouseX, mouseY)) {
            this.open = !this.open;
            return true;
        }
        if (!this.open || !this.contains(mouseX, mouseY)) {
            return false;
        }
        Drag drag = mouseY >= this.getSvY() && mouseY <= this.getSvY() + 86.0f ? Drag.SV : (mouseY >= this.getHueY() && mouseY <= this.getHueY() + 10.0f ? Drag.HUE : (this.drag = mouseY >= this.getAlphaY() && mouseY <= this.getAlphaY() + 10.0f ? Drag.ALPHA : Drag.NONE));
        if (this.drag != Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
        }
        return true;
    }

    @Override
    public void mouseDragged(float mouseX, float mouseY) {
        if (this.drag != Drag.NONE) {
            this.updateDrag(mouseX, mouseY);
        }
    }

    @Override
    public void mouseReleased(int button) {
        if (button == 0) {
            this.drag = Drag.NONE;
        }
    }

    private final void updateDrag(float mouseX, float mouseY) {
        if (this.getInnerWidth() <= 0.0f) {
            return;
        }
        float fx = RangesKt.coerceIn((float)((mouseX - this.getPadLeft()) / this.getInnerWidth()), (float)0.0f, (float)1.0f);
        switch (WhenMappings.$EnumSwitchMapping$0[this.drag.ordinal()]) {
            case 1: {
                ColorSetting.setHsba$default((ColorSetting)this.getSetting(), 0.0f, fx, 1.0f - RangesKt.coerceIn((float)((mouseY - this.getSvY()) / 86.0f), (float)0.0f, (float)1.0f), 0.0f, 9, null);
                break;
            }
            case 2: {
                ColorSetting.setHsba$default((ColorSetting)this.getSetting(), fx, 0.0f, 0.0f, 0.0f, 14, null);
                break;
            }
            case 3: {
                ColorSetting.setHsba$default((ColorSetting)this.getSetting(), 0.0f, 0.0f, 0.0f, fx, 7, null);
                break;
            }
            case 4: {
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lcom/cb/gui/component/ColorComponent$Companion;", "", "<init>", "()V", "SV_HEIGHT", "", "BAR_HEIGHT", "GAP", "EXTRA", "cb_client"})
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2={"Lcom/cb/gui/component/ColorComponent$Drag;", "", "<init>", "(Ljava/lang/String;I)V", "NONE", "SV", "HUE", "ALPHA", "cb_client"})
    private static final class Drag
    extends Enum<Drag> {
        public static final /* enum */ Drag NONE = new Drag();
        public static final /* enum */ Drag SV = new Drag();
        public static final /* enum */ Drag HUE = new Drag();
        public static final /* enum */ Drag ALPHA = new Drag();
        private static final /* synthetic */ Drag[] $VALUES;
        private static final /* synthetic */ EnumEntries $ENTRIES;

        public static Drag[] values() {
            return (Drag[])$VALUES.clone();
        }

        public static Drag valueOf(String value) {
            return Enum.valueOf(Drag.class, value);
        }

        @NotNull
        public static EnumEntries<Drag> getEntries() {
            return $ENTRIES;
        }

        static {
            $VALUES = dragArray = new Drag[]{Drag.NONE, Drag.SV, Drag.HUE, Drag.ALPHA};
            $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        }
    }

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Drag.values().length];
            try {
                nArray[Drag.SV.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Drag.HUE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Drag.ALPHA.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Drag.NONE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}


