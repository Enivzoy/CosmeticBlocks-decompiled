/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.ConfigListSetting;
import com.cb.setting.Setting;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0010H\u0016J \u0010\u001b\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0010X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0012\u00a8\u0006\u001e"}, d2={"Lcom/cb/gui/component/ConfigListComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/ConfigListSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/ConfigListSetting;)V", "open", "", "expandAnim", "Lcom/cb/render/Anim;", "options", "", "", "rowH", "", "getRowH", "()F", "optionH", "height", "getHeight", "render", "", "mouseX", "mouseY", "delta", "mouseClicked", "button", "", "cb_client"})
@SourceDebugExtension(value={"SMAP\nConfigListComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigListComponent.kt\ncom/cb/gui/component/ConfigListComponent\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,72:1\n1#2:73\n445#3,4:74\n450#3,3:80\n1915#4,2:78\n*S KotlinDebug\n*F\n+ 1 ConfigListComponent.kt\ncom/cb/gui/component/ConfigListComponent\n*L\n37#1:74,4\n37#1:80,3\n39#1:78,2\n*E\n"})
public final class ConfigListComponent
extends SettingComponent<ConfigListSetting> {
    private boolean open;
    @NotNull
    private final Anim expandAnim;
    @NotNull
    private List<String> options;
    private final float optionH;

    public ConfigListComponent(@NotNull ClickGuiScreen gui, @NotNull ConfigListSetting setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
        super(gui, (Setting)setting);
        this.expandAnim = new Anim(0.0f, 16.0f);
        this.options = CollectionsKt.emptyList();
        this.optionH = 18.0f;
    }

    private final float getRowH() {
        return 26.0f;
    }

    @Override
    public float getHeight() {
        return this.getRowH() + this.expandAnim.getValue() * ((float)this.options.size() * this.optionH + 4.0f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    public void render(float mouseX, float mouseY, float delta) {
        CharSequence charSequence;
        this.renderRowBase(mouseX, mouseY, delta);
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        CharSequence charSequence2 = (CharSequence)((ConfigListSetting)this.getSetting()).getValue();
        if (charSequence2.length() == 0) {
            NVGRenderer nVGRenderer2 = nVGRenderer;
            boolean bl = false;
            charSequence = "\u2014";
            nVGRenderer = nVGRenderer2;
        } else {
            charSequence = charSequence2;
        }
        NVGRenderer.textRight$default(nVGRenderer, (String)charSequence, this.getPadRight(), this.getY() + this.getRowH() / 2.0f, 12.0f, -6513508, null, 32, null);
        this.expandAnim.setTarget(this.open ? 1.0f : 0.0f);
        this.expandAnim.update(delta);
        if (this.expandAnim.getValue() > 0.01f && !((Collection)this.options).isEmpty()) {
            void w$iv;
            void y$iv;
            void x$iv;
            void this_$iv;
            float listHeight = this.expandAnim.getValue() * ((float)this.options.size() * this.optionH + 4.0f);
            NVGRenderer bl = NVGRenderer.INSTANCE;
            float f = this.getX();
            float f2 = this.getY() + this.getRowH();
            float f3 = this.getWidth();
            float h$iv = listHeight;
            boolean $i$f$scissor = false;
            this_$iv.save();
            this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
            try {
                boolean bl2 = false;
                float oy = 0.0f;
                oy = this.getY() + this.getRowH() + 2.0f;
                Iterable $this$forEach$iv = this.options;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    boolean hovered;
                    String opt = (String)element$iv;
                    boolean bl3 = false;
                    boolean bl4 = hovered = mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH;
                    if (hovered) {
                        NVGRenderer.INSTANCE.rect(this.getX() + 6.0f, oy, this.getWidth() - 12.0f, this.optionH, 0x12FFFFFF, 4.0f);
                    }
                    int color = Intrinsics.areEqual((Object)opt, ((ConfigListSetting)this.getSetting()).getValue()) ? -1750963 : -6513508;
                    NVGRenderer.text$default(NVGRenderer.INSTANCE, opt, this.getX() + 18.0f, oy + this.optionH / 2.0f, 12.0f, color, null, 32, null);
                    oy += this.optionH;
                }
            }
            finally {
                this_$iv.restore();
            }
        }
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (this.rowHovered(mouseX, mouseY)) {
            boolean bl = this.open = !this.open;
            if (this.open) {
                this.options = ((ConfigListSetting)this.getSetting()).options();
            }
            return true;
        }
        if (this.open) {
            float oy = this.getY() + this.getRowH() + 2.0f;
            for (String opt : this.options) {
                if (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= oy && mouseY <= oy + this.optionH) {
                    ((ConfigListSetting)this.getSetting()).setValue(opt);
                    this.open = false;
                    return true;
                }
                oy += this.optionH;
            }
        }
        return false;
    }
}


