/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.component;

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.NVGRenderer;
import com.cb.setting.Setting;
import com.cb.setting.StringSetting;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0010H\u0016J \u0010\u0016\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u000eH\u0016J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0016J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2={"Lcom/cb/gui/component/StringComponent;", "Lcom/cb/gui/component/SettingComponent;", "Lcom/cb/setting/StringSetting;", "gui", "Lcom/cb/gui/ClickGuiScreen;", "setting", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/setting/StringSetting;)V", "value", "", "focused", "getFocused", "()Z", "cursor", "", "blinkTimer", "", "render", "", "mouseX", "mouseY", "delta", "mouseClicked", "button", "keyPressed", "key", "modifiers", "charTyped", "text", "", "insert", "cb_client"})
@SourceDebugExtension(value={"SMAP\nStringComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringComponent.kt\ncom/cb/gui/component/StringComponent\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,116:1\n445#2,8:117\n437#3:125\n513#3,5:126\n*S KotlinDebug\n*F\n+ 1 StringComponent.kt\ncom/cb/gui/component/StringComponent\n*L\n48#1:117,8\n107#1:125\n107#1:126,5\n*E\n"})
public final class StringComponent
extends SettingComponent<StringSetting> {
    private boolean focused;
    private int cursor;
    private float blinkTimer;

    public StringComponent(@NotNull ClickGuiScreen gui, @NotNull StringSetting setting) {
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
        super(gui, (Setting)setting);
    }

    public final boolean getFocused() {
        return this.focused;
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
        this.blinkTimer += delta;
        String value = (String)((StringSetting)this.getSetting()).getValue();
        boolean empty = ((CharSequence)value).length() == 0;
        String display = empty && !this.focused ? ((StringSetting)this.getSetting()).getPlaceholder() : value;
        int color = this.focused ? -1184275 : (empty ? -10724260 : -6513508);
        float nameWidth = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, ((StringSetting)this.getSetting()).getName(), 12.0f, null, 4, null) + 12.0f;
        float maxTextWidth = RangesKt.coerceAtLeast((float)(this.getPadRight() - this.getPadLeft() - nameWidth), (float)20.0f);
        float midY = this.getY() + 13.0f;
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = this.getPadRight() - maxTextWidth;
        float f2 = this.getY();
        float f3 = maxTextWidth;
        float h$iv = 26.0f;
        boolean $i$f$scissor = false;
        this_$iv.save();
        this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            NVGRenderer.INSTANCE.textRight(display, this.getPadRight(), midY, 12.0f, color, NVGRenderer.Font.REGULAR);
            if (this.focused && this.blinkTimer % 1.0f < 0.55f) {
                float textWidth = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, value, 12.0f, null, 4, null);
                float textStart = this.getPadRight() - textWidth;
                String string = value.substring(0, this.cursor);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
                float caretX = textStart + NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, string, 12.0f, null, 4, null);
                NVGRenderer.rect$default(NVGRenderer.INSTANCE, RangesKt.coerceAtMost((float)caretX, (float)this.getPadRight()), midY - 6.0f, 1.2f, 12.0f, -1750963, 0.0f, 32, null);
            }
        }
        finally {
            this_$iv.restore();
        }
        if (this.focused) {
            NVGRenderer.INSTANCE.rect(this.getPadRight() - maxTextWidth, this.getY() + 26.0f - 4.0f, maxTextWidth, 1.5f, 1088768077, 0.75f);
        }
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        boolean inside = this.rowHovered(mouseX, mouseY);
        if (button == 0 && inside) {
            this.focused = true;
            this.cursor = ((String)((StringSetting)this.getSetting()).getValue()).length();
            this.blinkTimer = 0.0f;
            return true;
        }
        if (this.focused && !inside) {
            this.focused = false;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int key, int modifiers) {
        if (!this.focused) {
            return false;
        }
        this.blinkTimer = 0.0f;
        switch (key) {
            case 256: 
            case 257: 
            case 335: {
                this.focused = false;
                break;
            }
            case 259: {
                if (this.cursor <= 0) break;
                String string = (String)((StringSetting)this.getSetting()).getValue();
                int n = this.cursor - 1;
                int n2 = this.cursor;
                ((StringSetting)this.getSetting()).setValue(((Object)StringsKt.removeRange((CharSequence)string, (int)n, (int)n2)).toString());
                int n3 = this.cursor;
                this.cursor = n3 + -1;
                break;
            }
            case 261: {
                if (this.cursor >= ((String)((StringSetting)this.getSetting()).getValue()).length()) break;
                String string = (String)((StringSetting)this.getSetting()).getValue();
                int n = this.cursor;
                int n4 = this.cursor + 1;
                ((StringSetting)this.getSetting()).setValue(((Object)StringsKt.removeRange((CharSequence)string, (int)n, (int)n4)).toString());
                break;
            }
            case 263: {
                this.cursor = RangesKt.coerceAtLeast((int)(this.cursor - 1), (int)0);
                break;
            }
            case 262: {
                this.cursor = RangesKt.coerceAtMost((int)(this.cursor + 1), (int)((String)((StringSetting)this.getSetting()).getValue()).length());
                break;
            }
            case 268: {
                this.cursor = 0;
                break;
            }
            case 269: {
                this.cursor = ((String)((StringSetting)this.getSetting()).getValue()).length();
                break;
            }
            case 86: {
                if ((modifiers & 2) == 0) break;
                String string = Minecraft.getInstance().keyboardHandler.getClipboard();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getClipboard(...)");
                this.insert(string);
            }
        }
        return true;
    }

    @Override
    public boolean charTyped(@NotNull String text) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        if (!this.focused) {
            return false;
        }
        this.insert(text);
        return true;
    }

    /*
     * WARNING - void declaration
     */
    private final void insert(String text) {
        void $this$filterTo$iv$iv;
        String $this$filter$iv = text;
        boolean $i$f$filter = false;
        CharSequence charSequence = $this$filter$iv;
        Appendable destination$iv$iv = new StringBuilder();
        boolean $i$f$filterTo = false;
        int n = $this$filterTo$iv$iv.length();
        for (int index$iv$iv = 0; index$iv$iv < n; ++index$iv$iv) {
            char element$iv$iv;
            char it = element$iv$iv = $this$filterTo$iv$iv.charAt(index$iv$iv);
            boolean bl = false;
            if (!(it >= ' ' && it != '\u007f')) continue;
            destination$iv$iv.append(element$iv$iv);
        }
        String clean = ((StringBuilder)destination$iv$iv).toString();
        if (((CharSequence)clean).length() == 0) {
            return;
        }
        String before = (String)((StringSetting)this.getSetting()).getValue();
        StringSetting stringSetting = (StringSetting)this.getSetting();
        String string = new StringBuilder(before).insert(this.cursor, clean).toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toString(...)");
        stringSetting.setValue(string);
        this.cursor = RangesKt.coerceIn((int)(this.cursor + (((String)((StringSetting)this.getSetting()).getValue()).length() - before.length())), (int)0, (int)((String)((StringSetting)this.getSetting()).getValue()).length());
        this.blinkTimer = 0.0f;
    }
}


