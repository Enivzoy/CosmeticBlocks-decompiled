/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ModeSetting;
import com.cb.setting.Setting;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u001e2\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001:\u0001\u001eB\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rH\u0016J \u0010\u0017\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J \u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\rH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f\u00a8\u0006\u001f"}, d2={"Lcom/cb/gui/component/ModeComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/ModeSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ModeSetting;)V", "open", "", "openAnim", "Lcom/cb/render/Anim;", "expandedExtra", "", "getExpandedExtra", "()F", "height", "getHeight", "render", "", "mouseX", "mouseY", "delta", "mouseClicked", "button", "", "chevron", "cx", "cy", "t", "Companion", "cb_client"})
@SourceDebugExtension(value={"SMAP\nModeComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModeComponent.kt\ncom/cb/gui/component/ModeComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,91:1\n445#2,4:92\n450#2,3:99\n1924#3,3:96\n*S KotlinDebug\n*F\n+ 1 ModeComponent.kt\ncom/cb/gui/component/ModeComponent\n*L\n46#1:92,4\n46#1:99,3\n47#1:96,3\n*E\n"})
public final class ModeComponent
extends SettingComponent<ModeSetting<?>> {
    @NotNull
    private static final Companion Companion = new Companion(null);
    private boolean open;
    @NotNull
    private final Anim openAnim;
    @Deprecated
    public static final float OPTION_HEIGHT = 22.0f;

    public ModeComponent(@NotNull ClickGuiScreen gui, @NotNull ModeSetting<?> setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter(setting, (String)"setting");
        super(gui, (Setting)setting);
        this.openAnim = new Anim(0.0f, 16.0f);
    }

    private final float getExpandedExtra() {
        return (float)((ModeSetting)this.getSetting()).getOptions().size() * 22.0f + 4.0f;
    }

    @Override
    public float getHeight() {
        return 26.0f + this.openAnim.getValue() * this.getExpandedExtra();
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
        float midY = this.getY() + 13.0f;
        NVGRenderer.INSTANCE.textRight(((ModeSetting)this.getSetting()).currentLabel(), this.getPadRight() - 14.0f, midY, 12.0f, -6513508, NVGRenderer.Font.MEDIUM);
        this.chevron(this.getPadRight() - 5.0f, midY, this.openAnim.getValue());
        if (this.openAnim.getValue() <= 0.01f) {
            return;
        }
        float listTop = this.getY() + 26.0f;
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = this.getX();
        float f2 = listTop;
        float f3 = this.getWidth();
        float h$iv = this.openAnim.getValue() * this.getExpandedExtra();
        boolean $i$f$scissor = false;
        this_$iv.save();
        this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            Iterable $this$forEachIndexed$iv = ((ModeSetting)this.getSetting()).getOptions();
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                boolean hovered;
                void option;
                int n;
                if ((n = index$iv++) < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Enum enum_ = (Enum)item$iv;
                int index = n;
                boolean bl2 = false;
                float rowY = listTop + (float)index * 22.0f;
                boolean selected = Intrinsics.areEqual((Object)option, ((ModeSetting)this.getSetting()).getValue());
                float f4 = this.getPadLeft();
                boolean bl3 = hovered = (mouseX <= this.getPadRight() ? f4 <= mouseX : false) && mouseY >= rowY && mouseY < rowY + 22.0f;
                if (hovered) {
                    NVGRenderer.INSTANCE.rect(this.getX() + 10.0f, rowY + 1.0f, this.getWidth() - 20.0f, 20.0f, 0x12FFFFFF, 4.0f);
                }
                if (selected) {
                    NVGRenderer.INSTANCE.rect(this.getPadLeft(), rowY + 6.0f, 2.5f, 10.0f, -1750963, 1.25f);
                }
                NVGRenderer.INSTANCE.text(((ModeSetting)this.getSetting()).label((Enum<?>)option), this.getPadLeft() + 10.0f, rowY + 11.0f, 12.0f, selected ? -1184275 : -6513508, NVGRenderer.Font.REGULAR);
            }
        }
        finally {
            this_$iv.restore();
        }
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
        if (this.open && this.contains(mouseX, mouseY)) {
            int index = (int)((mouseY - (this.getY() + 26.0f)) / 22.0f);
            boolean bl = 0 <= index ? index < ((Collection)((ModeSetting)this.getSetting()).getOptions()).size() : false;
            if (bl) {
                ((ModeSetting)this.getSetting()).select((Enum)((ModeSetting)this.getSetting()).getOptions().get(index));
                this.open = false;
            }
            return true;
        }
        return false;
    }

    private final void chevron(float cx, float cy, float t) {
        float dir = 1.0f - 2.0f * t;
        float w = 3.5f;
        float h = 2.0f * dir;
        NVGRenderer.INSTANCE.line(cx - w, cy - h / 2.0f, cx, cy + h / 2.0f, -6513508, 1.5f);
        NVGRenderer.INSTANCE.line(cx, cy + h / 2.0f, cx + w, cy - h / 2.0f, -6513508, 1.5f);
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lcom/cb/gui/component/ModeComponent$Companion;", "", "<init>", "()V", "OPTION_HEIGHT", "", "cb_client"})
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


