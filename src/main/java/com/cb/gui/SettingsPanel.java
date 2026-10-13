/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.gui.ClickGuiScreen;
import com.cb.gui.component.SettingComponent;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.setting.Setting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 B2\u00020\u0001:\u0001BB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\b\u0010(\u001a\u00020\u0007H\u0002J\b\u0010)\u001a\u00020\u0007H\u0002J\u0006\u0010*\u001a\u00020\u0007J\u0016\u0010+\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007J\u0018\u0010.\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002J\u001e\u0010/\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u00101\u001a\u00020\u0007J\u0010\u00102\u001a\u0002002\u0006\u00101\u001a\u00020\u0007H\u0002J\u0018\u00103\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007H\u0002J\u001e\u00104\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u00105\u001a\u000206J\u000e\u00107\u001a\u0002002\u0006\u00105\u001a\u000206J\u0016\u00108\u001a\u0002002\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u0007J\u001e\u00109\u001a\u00020\u00162\u0006\u0010,\u001a\u00020\u00072\u0006\u0010-\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u0007J\u0016\u0010;\u001a\u00020\u00162\u0006\u0010<\u001a\u0002062\u0006\u0010=\u001a\u000206J\u000e\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020@J\u0006\u0010A\u001a\u000200R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0017\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010\u000f\u00a8\u0006C"}, d2={"Lcom/cb/gui/SettingsPanel;", "", "gui", "Lcom/cb/gui/ClickGuiScreen;", "tab", "Lcom/cb/config/SettingsTab;", "defaultX", "", "defaultY", "<init>", "(Lcom/cb/gui/ClickGuiScreen;Lcom/cb/config/SettingsTab;FF)V", "getTab", "()Lcom/cb/config/SettingsTab;", "x", "getX", "()F", "setX", "(F)V", "y", "getY", "setY", "collapsed", "", "components", "", "Lcom/cb/gui/component/SettingComponent;", "collapseAnim", "Lcom/cb/render/Anim;", "scroll", "scrollAnim", "dragging", "dragOffsetX", "dragOffsetY", "tiltAngle", "tiltVelocity", "smoothedVx", "prevX", "shownContent", "width", "getWidth", "contentHeight", "maxContentHeight", "totalHeight", "contains", "mouseX", "mouseY", "headerHovered", "render", "", "delta", "updateTilt", "renderHeader", "mouseClicked", "button", "", "mouseReleased", "mouseDragged", "mouseScrolled", "amount", "keyPressed", "key", "modifiers", "charTyped", "text", "", "persistState", "Companion", "cb_client"})
@SourceDebugExtension(value={"SMAP\nSettingsPanel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SettingsPanel.kt\ncom/cb/gui/SettingsPanel\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n*L\n1#1,251:1\n1642#2,10:252\n1915#2:262\n1916#2:264\n1652#2:265\n1915#2,2:266\n1915#2,2:272\n1915#2,2:277\n1915#2,2:279\n1915#2,2:281\n1807#2,3:283\n1807#2,3:286\n1#3:263\n445#4,4:268\n450#4,3:274\n*S KotlinDebug\n*F\n+ 1 SettingsPanel.kt\ncom/cb/gui/SettingsPanel\n*L\n28#1:252,10\n28#1:262\n28#1:264\n28#1:265\n68#1:266,2\n136#1:272,2\n222#1:277,2\n228#1:279,2\n232#1:281,2\n242#1:283,3\n245#1:286,3\n28#1:263\n133#1:268,4\n133#1:274,3\n*E\n"})
public final class SettingsPanel {
    @NotNull
    private static final Companion Companion = new Companion(null);
    @NotNull
    private final ClickGuiScreen gui;
    @NotNull
    private final SettingsTab tab;
    private float x;
    private float y;
    private boolean collapsed;
    @NotNull
    private final List<SettingComponent<?>> components;
    @NotNull
    private final Anim collapseAnim;
    private float scroll;
    @NotNull
    private final Anim scrollAnim;
    private boolean dragging;
    private float dragOffsetX;
    private float dragOffsetY;
    private float tiltAngle;
    private float tiltVelocity;
    private float smoothedVx;
    private float prevX;
    private float shownContent;
    @Deprecated
    public static final float TILT_PER_SPEED = 9.0E-5f;
    @Deprecated
    public static final float TILT_MAX = 0.14f;
    @Deprecated
    public static final float TILT_STIFFNESS = 170.0f;
    @Deprecated
    public static final float TILT_DAMPING = 10.5f;
    @Deprecated
    public static final float VELOCITY_SMOOTHING = 14.0f;
    @Deprecated
    public static final float SUBHEADER_HEIGHT = 18.0f;

    /*
     * WARNING - void declaration
     */
    public SettingsPanel(@NotNull ClickGuiScreen gui, @NotNull SettingsTab tab, float defaultX, float defaultY) {
        Config.PanelState saved;
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        Intrinsics.checkNotNullParameter((Object)gui, (String)"gui");
        Intrinsics.checkNotNullParameter((Object)tab, (String)"tab");
        this.gui = gui;
        this.tab = tab;
        Iterable iterable = this.tab.getSettings();
        SettingsPanel settingsPanel = this;
        boolean $i$f$mapNotNull = false;
        void var7_8 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            SettingComponent<?> it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            Setting it = (Setting)element$iv$iv;
            boolean bl2 = false;
            if (SettingComponent.Companion.create(this.gui, it) == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        settingsPanel.components = (List)destination$iv$iv;
        Config.PanelState panelState = saved = Config.INSTANCE.getPanelState().get(this.tab.getName());
        this.x = panelState != null ? panelState.getX() : defaultX;
        Config.PanelState panelState2 = saved;
        this.y = panelState2 != null ? panelState2.getY() : defaultY;
        Config.PanelState panelState3 = saved;
        this.collapsed = panelState3 != null ? panelState3.getCollapsed() : false;
        this.collapseAnim = new Anim(this.collapsed ? 0.0f : 1.0f, 14.0f);
        this.scrollAnim = new Anim(0.0f, 20.0f);
    }

    @NotNull
    public final SettingsTab getTab() {
        return this.tab;
    }

    public final float getX() {
        return this.x;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getY() {
        return this.y;
    }

    public final void setY(float f) {
        this.y = f;
    }

    private final float getWidth() {
        return 240.0f;
    }

    private final float contentHeight() {
        float h = 0.0f;
        h = 6.0f;
        String lastSub = null;
        lastSub = "";
        Iterable $this$forEach$iv = this.components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingComponent c = (SettingComponent)element$iv;
            boolean bl = false;
            if (!((Setting)c.getSetting()).getVisible()) continue;
            String sub = ((Setting)c.getSetting()).getSubcategory();
            if (((CharSequence)sub).length() > 0 && !Intrinsics.areEqual((Object)sub, (Object)lastSub)) {
                h += 18.0f;
                lastSub = sub;
            }
            h += c.getHeight();
        }
        return h;
    }

    private final float maxContentHeight() {
        return RangesKt.coerceAtLeast((float)(this.gui.getUiHeight() - this.y - 36.0f - 16.0f), (float)60.0f);
    }

    public final float totalHeight() {
        return 36.0f + this.shownContent;
    }

    public final boolean contains(float mouseX, float mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.getWidth() && mouseY >= this.y && mouseY <= this.y + this.totalHeight();
    }

    private final boolean headerHovered(float mouseX, float mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.getWidth() && mouseY >= this.y && mouseY <= this.y + 36.0f;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public final void render(float mouseX, float mouseY, float delta) {
        boolean tilting;
        if (this.dragging) {
            this.x = mouseX - this.dragOffsetX;
            this.y = mouseY - this.dragOffsetY;
        }
        this.x = RangesKt.coerceIn((float)this.x, (float)(4.0f - this.getWidth() + 40.0f), (float)(this.gui.getUiWidth() - 40.0f));
        this.y = RangesKt.coerceIn((float)this.y, (float)4.0f, (float)(this.gui.getUiHeight() - 36.0f - 4.0f));
        this.updateTilt(delta);
        this.collapseAnim.setTarget(this.collapsed ? 0.0f : 1.0f);
        this.collapseAnim.update(delta);
        float rawContent = RangesKt.coerceAtMost((float)this.contentHeight(), (float)this.maxContentHeight());
        this.shownContent = rawContent * this.collapseAnim.getValue();
        float maxScroll = RangesKt.coerceAtLeast((float)(this.contentHeight() - this.maxContentHeight()), (float)0.0f);
        this.scroll = RangesKt.coerceIn((float)this.scroll, (float)0.0f, (float)maxScroll);
        this.scrollAnim.setTarget(this.scroll);
        this.scrollAnim.update(delta);
        float panelHeight = this.totalHeight();
        boolean bl = !(this.tiltAngle == 0.0f) || !(this.tiltVelocity == 0.0f) ? true : (tilting = false);
        if (tilting) {
            float pivotX = this.x + this.dragOffsetX;
            float pivotY = this.y + this.dragOffsetY;
            NVGRenderer.INSTANCE.save();
            NVGRenderer.INSTANCE.translate(pivotX, pivotY);
            NVGRenderer.INSTANCE.rotate(this.tiltAngle);
            NVGRenderer.INSTANCE.translate(-pivotX, -pivotY);
        }
        NVGRenderer.INSTANCE.shadow(this.x, this.y, this.getWidth(), panelHeight, 10.0f, 14.0f, 0x59000000);
        NVGRenderer.INSTANCE.rect(this.x, this.y, this.getWidth(), panelHeight, -183233516, 10.0f);
        this.renderHeader(mouseX, mouseY);
        if (this.shownContent > 0.5f) {
            void w$iv;
            void y$iv;
            void x$iv;
            void this_$iv;
            float clipTop = this.y + 36.0f;
            NVGRenderer pivotY = NVGRenderer.INSTANCE;
            float f = this.x;
            float f2 = clipTop;
            float f3 = this.getWidth();
            float h$iv = this.shownContent;
            boolean $i$f$scissor = false;
            this_$iv.save();
            this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
            try {
                boolean bl2 = false;
                float offset = 0.0f;
                offset = clipTop + 3.0f - this.scrollAnim.getValue();
                String lastSub = null;
                lastSub = "";
                Iterable $this$forEach$iv = this.components;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    SettingComponent component = (SettingComponent)element$iv;
                    boolean bl3 = false;
                    if (!((Setting)component.getSetting()).getVisible()) continue;
                    String sub = ((Setting)component.getSetting()).getSubcategory();
                    if (((CharSequence)sub).length() > 0 && !Intrinsics.areEqual((Object)sub, (Object)lastSub)) {
                        lastSub = sub;
                        if (offset + 18.0f >= clipTop && offset <= clipTop + this.shownContent) {
                            String string = sub.toUpperCase(Locale.ROOT);
                            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toUpperCase(...)");
                            NVGRenderer.INSTANCE.text(string, this.x + 14.0f, offset + 9.0f, 11.0f, -10724260, NVGRenderer.Font.MEDIUM);
                        }
                        offset += 18.0f;
                    }
                    component.setX(this.x);
                    component.setY(offset);
                    component.setWidth(this.getWidth());
                    if (offset + component.getHeight() >= clipTop && offset <= clipTop + this.shownContent) {
                        component.render(mouseX, mouseY, delta);
                    }
                    offset += component.getHeight();
                }
            }
            finally {
                this_$iv.restore();
            }
        }
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.x, this.y, this.getWidth(), panelHeight, 0x14FFFFFF, 10.0f, 0.0f, 64, null);
        if (tilting) {
            NVGRenderer.INSTANCE.restore();
        }
    }

    private final void updateTilt(float delta) {
        if (delta <= 0.0f || delta > 0.1f) {
            this.prevX = this.x;
            return;
        }
        float vx = (this.x - this.prevX) / delta;
        this.prevX = this.x;
        this.smoothedVx += (vx - this.smoothedVx) * RangesKt.coerceAtMost((float)(delta * 14.0f), (float)1.0f);
        float target = this.dragging ? RangesKt.coerceIn((float)(-this.smoothedVx * 9.0E-5f), (float)-0.14f, (float)0.14f) : 0.0f;
        this.tiltVelocity += (target - this.tiltAngle) * 170.0f * delta;
        this.tiltVelocity *= (float)Math.exp(-10.5f * delta);
        this.tiltAngle += this.tiltVelocity * delta;
        if (!this.dragging && Math.abs(this.tiltAngle) < 4.0E-4f && Math.abs(this.tiltVelocity) < 4.0E-4f) {
            this.tiltAngle = 0.0f;
            this.tiltVelocity = 0.0f;
        }
    }

    private final void renderHeader(float mouseX, float mouseY) {
        float radius = 10.0f;
        float bottomRadius = this.shownContent > 0.5f ? 0.0f : radius;
        NVGRenderer.INSTANCE.rectVarying(this.x, this.y, this.getWidth(), 36.0f, radius, radius, bottomRadius, bottomRadius, -14935012);
        if (this.shownContent > 0.5f) {
            NVGRenderer.line$default(NVGRenderer.INSTANCE, this.x, this.y + 36.0f, this.x + this.getWidth(), this.y + 36.0f, 0x14FFFFFF, 0.0f, 32, null);
        }
        NVGRenderer.INSTANCE.text(this.tab.getName(), this.x + 14.0f, this.y + 18.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
        int dotColor = this.collapsed ? -10724260 : -1750963;
        NVGRenderer.INSTANCE.circle(this.x + this.getWidth() - 16.0f, this.y + 18.0f, 3.0f, dotColor);
    }

    public final boolean mouseClicked(float mouseX, float mouseY, int button) {
        if (this.headerHovered(mouseX, mouseY)) {
            switch (button) {
                case 0: {
                    this.dragging = true;
                    this.dragOffsetX = mouseX - this.x;
                    this.dragOffsetY = mouseY - this.y;
                    this.smoothedVx = 0.0f;
                    this.prevX = this.x;
                    break;
                }
                case 1: {
                    this.collapsed = !this.collapsed;
                }
            }
            return true;
        }
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        Iterable $this$forEach$iv = this.components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingComponent it = (SettingComponent)element$iv;
            boolean bl = false;
            if (!((Setting)it.getSetting()).getVisible() || !it.mouseClicked(mouseX, mouseY, button)) continue;
            return true;
        }
        return true;
    }

    public final void mouseReleased(int button) {
        if (button == 0) {
            this.dragging = false;
        }
        Iterable $this$forEach$iv = this.components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingComponent it = (SettingComponent)element$iv;
            boolean bl = false;
            it.mouseReleased(button);
        }
    }

    public final void mouseDragged(float mouseX, float mouseY) {
        Iterable $this$forEach$iv = this.components;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingComponent it = (SettingComponent)element$iv;
            boolean bl = false;
            it.mouseDragged(mouseX, mouseY);
        }
    }

    public final boolean mouseScrolled(float mouseX, float mouseY, float amount) {
        if (!this.contains(mouseX, mouseY) || this.collapsed) {
            return false;
        }
        this.scroll -= amount * 24.0f;
        return true;
    }

    public final boolean keyPressed(int key, int modifiers) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.components;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    SettingComponent it = (SettingComponent)element$iv;
                    boolean bl2 = false;
                    if (!it.keyPressed(key, modifiers)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public final boolean charTyped(@NotNull String text) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)text, (String)"text");
            Iterable $this$any$iv = this.components;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    SettingComponent it = (SettingComponent)element$iv;
                    boolean bl2 = false;
                    if (!it.charTyped(text)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public final void persistState() {
        ((Map)Config.INSTANCE.getPanelState()).put(this.tab.getName(), new Config.PanelState(this.x, this.y, this.collapsed));
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\b\u0082\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lcom/cb/gui/SettingsPanel$Companion;", "", "<init>", "()V", "TILT_PER_SPEED", "", "TILT_MAX", "TILT_STIFFNESS", "TILT_DAMPING", "VELOCITY_SMOOTHING", "SUBHEADER_HEIGHT", "cb_client"})
    private static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}


