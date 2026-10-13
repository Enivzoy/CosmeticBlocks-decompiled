/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.enums.EnumEntries
 *  kotlin.enums.EnumEntriesKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.gui.screens;

import com.cb.cosmetic.GlobalRemap;
import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002:\u0003\\]^B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\fH\u0016J\b\u0010.\u001a\u00020,H\u0002J\u0018\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\n2\u0006\u00101\u001a\u00020\fH\u0002J\b\u00102\u001a\u00020,H\u0002J8\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\b\b\u0002\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J.\u0010;\u001a\u00020\f2\u0006\u0010<\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J6\u0010=\u001a\u00020,2\u0006\u00104\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\u0006\u0010>\u001a\u00020\f2\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J\u0010\u0010?\u001a\u0002082\u0006\u00106\u001a\u00020\fH\u0002J\b\u0010@\u001a\u00020,H\u0002J\u0012\u0010A\u001a\u00020,2\b\b\u0002\u0010B\u001a\u000208H\u0002J\u001a\u0010C\u001a\u00020,2\u0006\u0010D\u001a\u00020\b2\b\u0010E\u001a\u0004\u0018\u00010\nH\u0002J\u0010\u0010F\u001a\u00020,2\u0006\u0010G\u001a\u00020\nH\u0002J\u0018\u0010H\u001a\u0002082\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u000208H\u0016J(\u0010L\u001a\u0002082\u0006\u0010\u000f\u001a\u00020M2\u0006\u0010\u0010\u001a\u00020M2\u0006\u0010N\u001a\u00020M2\u0006\u0010O\u001a\u00020MH\u0016J\u0010\u0010P\u001a\u0002082\u0006\u0010I\u001a\u00020QH\u0016J\u0010\u0010R\u001a\u0002082\u0006\u0010I\u001a\u00020SH\u0016J(\u0010T\u001a\u00020,2\u0006\u0010U\u001a\u00020V2\u0006\u0010\u000f\u001a\u00020&2\u0006\u0010\u0010\u001a\u00020&2\u0006\u0010W\u001a\u00020\fH\u0016J\b\u0010X\u001a\u000208H\u0016J\u0010\u0010Y\u001a\u00020\f2\u0006\u0010Z\u001a\u00020MH\u0002J\u0010\u0010[\u001a\u00020\n2\u0006\u0010G\u001a\u00020\nH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u001dj\b\u0012\u0004\u0012\u00020\u001e`\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006_"}, d2={"Lcom/cb/gui/screens/GlobalRemapEditorScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "view", "Lcom/cb/gui/screens/GlobalRemapEditorScreen$View;", "pickPhase", "Lcom/cb/gui/screens/GlobalRemapEditorScreen$PickPhase;", "pendingSource", "", "uiScale", "", "uiWidth", "uiHeight", "mouseX", "mouseY", "px", "py", "panelW", "getPanelW", "()F", "panelH", "getPanelH", "rowH", "listScroll", "listTop", "listBottom", "hits", "Ljava/util/ArrayList;", "Lcom/cb/gui/screens/GlobalRemapEditorScreen$Hit;", "Lkotlin/collections/ArrayList;", "allBlocks", "", "filtered", "search", "lastFilterKey", "highlightIdx", "", "pickScroll", "pickListTop", "pickListBottom", "blink", "renderOverlay", "", "delta", "renderListView", "renderSourceBlock", "src", "startY", "renderPickView", "pillButton", "label", "x", "y", "accent", "", "act", "Lkotlin/Function0;", "iconButton", "glyph", "iconButtonLabeled", "wdt", "withinList", "ensureBlocks", "applyFilter", "force", "enterPick", "phase", "source", "commitPick", "id", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseScrolled", "", "scrollX", "scrollY", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "partialTick", "isPauseScreen", "toUi", "scaled", "prettify", "View", "PickPhase", "Hit", "cb_client"})
@SourceDebugExtension(value={"SMAP\nGlobalRemapEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalRemapEditorScreen.kt\ncom/cb/gui/screens/GlobalRemapEditorScreen\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,401:1\n445#2,8:402\n445#2,8:411\n1#3:410\n1586#4:419\n1661#4,3:420\n777#4:423\n873#4,2:424\n1088#5,2:426\n*S KotlinDebug\n*F\n+ 1 GlobalRemapEditorScreen.kt\ncom/cb/gui/screens/GlobalRemapEditorScreen\n*L\n139#1:402,8\n235#1:411,8\n289#1:419\n289#1:420,3\n298#1:423\n298#1:424,2\n381#1:426,2\n*E\n"})
public final class GlobalRemapEditorScreen
extends Screen
implements NvgScreen {
    @NotNull
    private View view = View.LIST;
    @NotNull
    private PickPhase pickPhase = PickPhase.SOURCE;
    @Nullable
    private String pendingSource;
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    private float mouseX;
    private float mouseY;
    private float px;
    private float py;
    private final float rowH;
    private float listScroll;
    private float listTop;
    private float listBottom;
    @NotNull
    private final ArrayList<Hit> hits = new ArrayList();
    @NotNull
    private List<String> allBlocks = CollectionsKt.emptyList();
    @NotNull
    private List<String> filtered = CollectionsKt.emptyList();
    @NotNull
    private String search = "";
    @NotNull
    private String lastFilterKey = "__none__";
    private int highlightIdx;
    private float pickScroll;
    private float pickListTop;
    private float pickListBottom;
    private float blink;

    public GlobalRemapEditorScreen() {
        super((Component)Component.literal((String)"Block Remaps"));
        this.rowH = 20.0f;
    }

    private final float getPanelW() {
        return RangesKt.coerceIn((float)(this.uiWidth * 0.6f), (float)420.0f, (float)640.0f);
    }

    private final float getPanelH() {
        return RangesKt.coerceIn((float)(this.uiHeight * 0.78f), (float)300.0f, (float)560.0f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void renderOverlay(float delta) {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft minecraft2 = minecraft;
        Window window = minecraft2.getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)window, (String)"getWindow(...)");
        Window window2 = window;
        int fbWidth = window2.getWidth();
        int fbHeight = window2.getHeight();
        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }
        this.blink += delta;
        this.uiScale = RangesKt.coerceIn((float)((float)window2.getGuiScale() * 0.75f), (float)1.0f, (float)3.0f);
        this.uiWidth = (float)fbWidth / this.uiScale;
        this.uiHeight = (float)fbHeight / this.uiScale;
        int screenWidth = window2.getScreenWidth();
        int screenHeight = window2.getScreenHeight();
        float fbScaleX = screenWidth > 0 ? (float)fbWidth / (float)screenWidth : 1.0f;
        float fbScaleY = screenHeight > 0 ? (float)fbHeight / (float)screenHeight : 1.0f;
        this.mouseX = (float)minecraft2.mouseHandler.xpos() * fbScaleX / this.uiScale;
        this.mouseY = (float)minecraft2.mouseHandler.ypos() * fbScaleY / this.uiScale;
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, 0x66000000, 0.0f, 32, null);
            float w = this.getPanelW();
            float h = this.getPanelH();
            this.px = (this.uiWidth - w) / 2.0f;
            this.py = (this.uiHeight - h) / 2.0f;
            NVGRenderer.INSTANCE.shadow(this.px, this.py, w, h, 10.0f, 16.0f, Integer.MIN_VALUE);
            NVGRenderer.INSTANCE.rect(this.px, this.py, w, h, -183233516, 10.0f);
            NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.px, this.py, w, h, 0x14FFFFFF, 10.0f, 0.0f, 64, null);
            if (this.view == View.LIST) {
                this.renderListView();
            } else {
                this.renderPickView();
            }
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private final void renderListView() {
        void w$iv;
        void y$iv;
        void x$iv;
        this.hits.clear();
        float w = this.getPanelW();
        float h = this.getPanelH();
        float cy = this.py + 10.0f;
        NVGRenderer.INSTANCE.text("Block Remaps", this.px + 10.0f, cy + 7.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
        NVGRenderer.textRight$default(NVGRenderer.INSTANCE, GlobalRemap.INSTANCE.sources().size() + " sources", this.px + w - 10.0f, cy + 7.0f, 12.0f, -6513508, null, 32, null);
        float bx = this.px + 10.0f;
        bx += this.pillButton("+ Add Remap", bx, cy += 24.0f, true, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderListView$lambda$0(this))) + 6.0f;
        bx += GlobalRemapEditorScreen.pillButton$default(this, "Export", bx, cy, false, GlobalRemapEditorScreen::renderListView$lambda$1, 8, null) + 6.0f;
        bx += GlobalRemapEditorScreen.pillButton$default(this, "Import", bx, cy, false, GlobalRemapEditorScreen::renderListView$lambda$2, 8, null) + 6.0f;
        GlobalRemapEditorScreen.pillButton$default(this, "Clear", bx, cy, false, GlobalRemapEditorScreen::renderListView$lambda$3, 8, null);
        this.listTop = cy += 24.0f;
        this.listBottom = this.py + h - 20.0f;
        float viewH = this.listBottom - this.listTop;
        List<String> sources = GlobalRemap.INSTANCE.sources();
        float totalH = 0.0f;
        for (String string : sources) {
            totalH += this.rowH + (float)GlobalRemap.INSTANCE.targetsOf(string).size() * this.rowH + this.rowH;
        }
        float maxScroll = RangesKt.coerceAtLeast((float)(totalH - viewH), (float)0.0f);
        this.listScroll = RangesKt.coerceIn((float)this.listScroll, (float)0.0f, (float)maxScroll);
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = this.px;
        float f2 = this.listTop;
        float f3 = w;
        float h$iv = viewH;
        boolean $i$f$scissor = false;
        nVGRenderer.save();
        nVGRenderer.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            float ry = this.listTop - this.listScroll;
            for (String src3 : sources) {
                ry = this.renderSourceBlock(src3, ry);
            }
        }
        finally {
            nVGRenderer.restore();
        }
        if (sources.isEmpty()) {
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "No remaps yet \u2014 click \"+ Add Remap\".", this.px + w / 2.0f, this.listTop + 24.0f, 12.0f, -10724260, null, 32, null);
        }
        NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "ESC to close", this.px + w / 2.0f, this.py + h - 10.0f, 12.0f, -10724260, null, 32, null);
    }

    private final float renderSourceBlock(String src, float startY) {
        float w = this.getPanelW();
        Function1 visible = arg_0 -> GlobalRemapEditorScreen.renderSourceBlock$lambda$0(this, arg_0);
        float ry = startY;
        if (((Boolean)visible.invoke((Object)Float.valueOf(ry))).booleanValue()) {
            NVGRenderer.INSTANCE.rect(this.px + 4.0f, ry + 1.0f, w - 8.0f, this.rowH - 2.0f, -14935012, 4.0f);
            NVGRenderer.INSTANCE.text(this.prettify(src), this.px + 10.0f, ry + this.rowH / 2.0f, 12.0f, -1184275, NVGRenderer.Font.MEDIUM);
            float delW = this.iconButton("x", this.px + w - 10.0f - 16.0f, ry + (this.rowH - 14.0f) / 2.0f, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderSourceBlock$lambda$1(src)));
            this.iconButtonLabeled("+ target", this.px + w - 10.0f - 16.0f - delW - 64.0f, ry + (this.rowH - 14.0f) / 2.0f, 60.0f, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderSourceBlock$lambda$2(this, src)));
        }
        ry += this.rowH;
        List<GlobalRemap.RemapTarget> targets = GlobalRemap.INSTANCE.targetsOf(src);
        Iterator iterator = ((Iterable)targets).iterator();
        int n = 0;
        while (iterator.hasNext()) {
            int i = n++;
            GlobalRemap.RemapTarget t = (GlobalRemap.RemapTarget)iterator.next();
            if (((Boolean)visible.invoke((Object)Float.valueOf(ry))).booleanValue()) {
                NVGRenderer.text$default(NVGRenderer.INSTANCE, "> " + this.prettify(t.getId()), this.px + 10.0f + 12.0f, ry + this.rowH / 2.0f, 12.0f, -6513508, null, 32, null);
                float cx = this.px + w - 10.0f - 16.0f;
                this.iconButton("x", cx, ry + (this.rowH - 14.0f) / 2.0f, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderSourceBlock$lambda$3(src, i)));
                this.iconButton("+", cx -= 22.0f, ry + (this.rowH - 14.0f) / 2.0f, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderSourceBlock$lambda$4(src, i, t)));
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "x" + t.getWeight(), (cx -= 30.0f) + 14.0f, ry + this.rowH / 2.0f, 12.0f, -1184275, null, 32, null);
                this.iconButton("-", cx -= 18.0f, ry + (this.rowH - 14.0f) / 2.0f, (Function0<Unit>)((Function0)() -> GlobalRemapEditorScreen.renderSourceBlock$lambda$5(src, i, t)));
            }
            ry += this.rowH;
        }
        return ry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private final void renderPickView() {
        block16: {
            this.ensureBlocks();
            GlobalRemapEditorScreen.applyFilter$default(this, false, 1, null);
            w = this.getPanelW();
            h = this.getPanelH();
            cy = this.py + 10.0f;
            if (this.pickPhase == PickPhase.SOURCE) {
                v0 = "Pick source block";
            } else {
                v1 = this.pendingSource;
                if (v1 == null) {
                    v1 = "";
                }
                v0 = "Pick target for " + this.prettify(v1);
            }
            title = v0;
            NVGRenderer.INSTANCE.text(title, this.px + 10.0f, cy + 7.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
            searchH = 22.0f;
            NVGRenderer.INSTANCE.rect(this.px + 10.0f, cy += 24.0f, w - 20.0f, searchH, 0x66000000, 5.0f);
            NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.px + 10.0f, cy, w - 20.0f, searchH, 0x14FFFFFF, 5.0f, 0.0f, 64, null);
            var7_6 = this.search;
            if (var7_6.length() == 0) {
                $i$a$-ifEmpty-GlobalRemapEditorScreen$renderPickView$searchText$1 = false;
                v2 = "type to filter...";
            } else {
                v2 = var7_6;
            }
            searchText = (String)v2;
            searchColor = ((CharSequence)this.search).length() == 0 != false ? -10724260 : -1184275;
            NVGRenderer.text$default(NVGRenderer.INSTANCE, searchText, this.px + 10.0f + 8.0f, cy + searchH / 2.0f, 13.0f, searchColor, null, 32, null);
            if (this.blink % 1.0f < 0.55f) {
                caretX = this.px + 10.0f + 8.0f + NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, this.search, 13.0f, null, 4, null);
                NVGRenderer.rect$default(NVGRenderer.INSTANCE, caretX + 1.0f, cy + searchH / 2.0f - 6.0f, 1.2f, 12.0f, -1750963, 0.0f, 32, null);
            }
            NVGRenderer.text$default(NVGRenderer.INSTANCE, this.filtered.size() + " result" + (this.filtered.size() == 1 ? "" : "s"), this.px + 10.0f, (cy += searchH + 6.0f) + 6.0f, 12.0f, -10724260, null, 32, null);
            this.pickListTop = cy += 16.0f;
            this.pickListBottom = this.py + h - 20.0f;
            viewH = this.pickListBottom - this.pickListTop;
            totalH = (float)this.filtered.size() * this.rowH;
            maxScroll = RangesKt.coerceAtLeast((float)(totalH - viewH), (float)0.0f);
            this.pickScroll = RangesKt.coerceIn((float)this.pickScroll, (float)0.0f, (float)maxScroll);
            var11_14 = NVGRenderer.INSTANCE;
            var12_15 = this.px;
            var13_16 = this.pickListTop;
            var14_17 = w;
            h$iv = viewH;
            $i$f$scissor = false;
            this_$iv.save();
            this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
            try {
                $i$a$-scissor-GlobalRemapEditorScreen$renderPickView$1 = false;
                first = RangesKt.coerceAtLeast((int)((int)(this.pickScroll / this.rowH)), (int)0);
                last = RangesKt.coerceAtMost((int)((int)((this.pickScroll + viewH) / this.rowH)), (int)(this.filtered.size() - 1));
                i = first;
                if (i > last) break block16;
                while (true) {
                    id = this.filtered.get(i);
                    ry = this.pickListTop + (float)i * this.rowH - this.pickScroll;
                    var23_26 = this.px;
                    var24_27 = this.px + w;
                    var25_28 = this.mouseX;
                    v3 = var23_26 <= var25_28 ? var25_28 <= var24_27 : false;
                    if (!v3) ** GOTO lbl-1000
                    var23_26 = ry + this.rowH;
                    var24_27 = this.mouseY;
                    v4 = ry <= var24_27 ? var24_27 <= var23_26 : false;
                    if (v4) {
                        v5 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v5 = hovered = false;
                    }
                    if (i == this.highlightIdx) {
                        NVGRenderer.INSTANCE.rect(this.px + 2.0f, ry, w - 4.0f, this.rowH, Theme.INSTANCE.withAlpha(-1750963, 0.28f), 4.0f);
                    } else if (hovered) {
                        NVGRenderer.INSTANCE.rect(this.px + 2.0f, ry, w - 4.0f, this.rowH, 0x12FFFFFF, 4.0f);
                    }
                    NVGRenderer.text$default(NVGRenderer.INSTANCE, this.prettify(id), this.px + 10.0f + 4.0f, ry + this.rowH / 2.0f, 12.0f, -1184275, null, 32, null);
                    if (i != last) {
                        ++i;
                        continue;
                    }
                    break;
                }
            }
            finally {
                this_$iv.restore();
            }
        }
        NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "Enter confirm   |   ESC back", this.px + w / 2.0f, this.py + h - 10.0f, 12.0f, -10724260, null, 32, null);
    }

    /*
     * Unable to fully structure code
     */
    private final float pillButton(String label, float x, float y, boolean accent, Function0<Unit> act) {
        bw = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, label, 12.0f, null, 4, null) + 16.0f;
        bh = 16.0f;
        var9_8 = x + bw;
        var10_10 = this.mouseX;
        v0 = x <= var10_10 ? var10_10 <= var9_8 : false;
        if (!v0) ** GOTO lbl-1000
        var9_8 = y + bh;
        var10_10 = this.mouseY;
        v1 = y <= var10_10 ? var10_10 <= var9_8 : false;
        if (v1) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = hovered = false;
        }
        bg = accent != false ? -1750963 : (hovered != false ? 0x12FFFFFF : -14013910);
        NVGRenderer.INSTANCE.rect(x, y, bw, bh, bg, 4.0f);
        NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, label, x + bw / 2.0f, y + bh / 2.0f, 12.0f, -1184275, null, 32, null);
        this.hits.add(new Hit(x, y, bw, bh, act));
        return bw;
    }

    static /* synthetic */ float pillButton$default(GlobalRemapEditorScreen globalRemapEditorScreen, String string, float f, float f2, boolean bl, Function0 function0, int n, Object object) {
        if ((n & 8) != 0) {
            bl = false;
        }
        return globalRemapEditorScreen.pillButton(string, f, f2, bl, (Function0<Unit>)function0);
    }

    /*
     * Unable to fully structure code
     */
    private final float iconButton(String glyph, float x, float y, Function0<Unit> act) {
        s = 14.0f;
        var7_6 = x + s;
        var8_7 = this.mouseX;
        v0 = x <= var8_7 ? var8_7 <= var7_6 : false;
        if (!v0) ** GOTO lbl-1000
        var7_6 = y + s;
        var8_7 = this.mouseY;
        v1 = y <= var8_7 ? var8_7 <= var7_6 : false;
        if (v1) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = false;
        }
        hovered = v2;
        NVGRenderer.INSTANCE.rect(x, y, s, s, hovered != false ? -1750963 : -14013910, 3.0f);
        NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, glyph, x + s / 2.0f, y + s / 2.0f, 12.0f, -1184275, null, 32, null);
        if (this.withinList(y)) {
            this.hits.add(new Hit(x, y, s, s, act));
        }
        return s;
    }

    /*
     * Unable to fully structure code
     */
    private final void iconButtonLabeled(String label, float x, float y, float wdt, Function0<Unit> act) {
        s = 14.0f;
        var8_7 = x + wdt;
        var9_8 = this.mouseX;
        v0 = x <= var9_8 ? var9_8 <= var8_7 : false;
        if (!v0) ** GOTO lbl-1000
        var8_7 = y + s;
        var9_8 = this.mouseY;
        v1 = y <= var9_8 ? var9_8 <= var8_7 : false;
        if (v1) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = false;
        }
        hovered = v2;
        NVGRenderer.INSTANCE.rect(x, y, wdt, s, hovered != false ? 0x12FFFFFF : -14013910, 3.0f);
        NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, label, x + wdt / 2.0f, y + s / 2.0f, 12.0f, -1184275, null, 32, null);
        if (this.withinList(y)) {
            this.hits.add(new Hit(x, y, wdt, s, act));
        }
    }

    private final boolean withinList(float y) {
        return y >= this.listTop - 2.0f && y + this.rowH <= this.listBottom + 2.0f;
    }

    /*
     * WARNING - void declaration
     */
    private final void ensureBlocks() {
        if (this.allBlocks.isEmpty()) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Set set = BuiltInRegistries.BLOCK.keySet();
            Intrinsics.checkNotNullExpressionValue((Object)set, (String)"keySet(...)");
            Iterable iterable = set;
            GlobalRemapEditorScreen globalRemapEditorScreen = this;
            boolean $i$f$map = false;
            void var3_4 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Identifier identifier = (Identifier)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.toString());
            }
            globalRemapEditorScreen.allBlocks = CollectionsKt.sorted((Iterable)((List)destination$iv$iv));
            this.applyFilter(true);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void applyFilter(boolean force) {
        List list;
        String string = ((Object)StringsKt.trim((CharSequence)this.search)).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"toLowerCase(...)");
        String q = string;
        if (!force && Intrinsics.areEqual((Object)q, (Object)this.lastFilterKey)) {
            return;
        }
        this.lastFilterKey = q;
        GlobalRemapEditorScreen globalRemapEditorScreen = this;
        if (((CharSequence)q).length() == 0) {
            list = this.allBlocks;
        } else {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            Iterable iterable = this.allBlocks;
            GlobalRemapEditorScreen globalRemapEditorScreen2 = globalRemapEditorScreen;
            boolean $i$f$filter = false;
            void var5_6 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                String it = (String)element$iv$iv;
                boolean bl = false;
                if (!StringsKt.contains$default((CharSequence)it, (CharSequence)q, (boolean)false, (int)2, null)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            list = (List)destination$iv$iv;
            globalRemapEditorScreen = globalRemapEditorScreen2;
        }
        globalRemapEditorScreen.filtered = list;
        this.highlightIdx = RangesKt.coerceIn((int)this.highlightIdx, (int)0, (int)RangesKt.coerceAtLeast((int)(this.filtered.size() - 1), (int)0));
    }

    static /* synthetic */ void applyFilter$default(GlobalRemapEditorScreen globalRemapEditorScreen, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        globalRemapEditorScreen.applyFilter(bl);
    }

    private final void enterPick(PickPhase phase, String source) {
        this.view = View.PICK;
        this.pickPhase = phase;
        this.pendingSource = source;
        this.search = "";
        this.lastFilterKey = "__none__";
        this.highlightIdx = 0;
        this.pickScroll = 0.0f;
        this.ensureBlocks();
        this.applyFilter(true);
    }

    private final void commitPick(String id) {
        switch (WhenMappings.$EnumSwitchMapping$0[this.pickPhase.ordinal()]) {
            case 1: {
                this.enterPick(PickPhase.TARGET, id);
                break;
            }
            case 2: {
                String src = this.pendingSource;
                if (src != null) {
                    GlobalRemap.INSTANCE.addTarget(src, id, 1);
                }
                this.view = View.LIST;
                this.pendingSource = null;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        float mx = this.toUi(event.x());
        float my = this.toUi(event.y());
        if (event.button() != 0) {
            return true;
        }
        if (this.view == View.LIST) {
            Iterator<Hit> iterator = this.hits.iterator();
            Intrinsics.checkNotNullExpressionValue(iterator, (String)"iterator(...)");
            Iterator<Hit> iterator2 = iterator;
            while (iterator2.hasNext()) {
                Hit hit;
                Intrinsics.checkNotNullExpressionValue((Object)iterator2.next(), (String)"next(...)");
                float f = hit.getX();
                boolean bl = mx <= hit.getX() + hit.getW() ? f <= mx : false;
                if (!bl) continue;
                f = hit.getY();
                boolean bl2 = my <= hit.getY() + hit.getH() ? f <= my : false;
                if (!bl2) continue;
                hit.getAct().invoke();
                return true;
            }
            if (mx < this.px || mx > this.px + this.getPanelW() || my < this.py || my > this.py + this.getPanelH()) {
                this.onClose();
            }
            return true;
        }
        float f = this.px;
        boolean bl = mx <= this.px + this.getPanelW() ? f <= mx : false;
        if (bl) {
            f = this.pickListTop;
            boolean bl3 = my <= this.pickListBottom ? f <= my : false;
            if (bl3) {
                int idx = (int)((my - this.pickListTop + this.pickScroll) / this.rowH);
                boolean bl4 = 0 <= idx ? idx < ((Collection)this.filtered).size() : false;
                if (bl4) {
                    this.commitPick(this.filtered.get(idx));
                }
                return true;
            }
        }
        if (mx < this.px || mx > this.px + this.getPanelW() || my < this.py || my > this.py + this.getPanelH()) {
            this.view = View.LIST;
        }
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (this.view == View.LIST) {
            this.listScroll -= (float)scrollY * this.rowH * 3.0f;
        } else {
            this.pickScroll -= (float)scrollY * this.rowH * 3.0f;
        }
        return true;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (this.view == View.PICK) {
            switch (event.key()) {
                case 256: {
                    this.view = View.LIST;
                    return true;
                }
                case 257: 
                case 335: {
                    if (!((Collection)this.filtered).isEmpty()) {
                        this.commitPick(this.filtered.get(RangesKt.coerceIn((int)this.highlightIdx, (int)0, (int)(this.filtered.size() - 1))));
                    }
                    return true;
                }
                case 264: {
                    this.highlightIdx = RangesKt.coerceAtMost((int)(this.highlightIdx + 1), (int)RangesKt.coerceAtLeast((int)(this.filtered.size() - 1), (int)0));
                    return true;
                }
                case 265: {
                    this.highlightIdx = RangesKt.coerceAtLeast((int)(this.highlightIdx - 1), (int)0);
                    return true;
                }
                case 259: {
                    if (((CharSequence)this.search).length() > 0) {
                        this.search = StringsKt.dropLast((String)this.search, (int)1);
                        GlobalRemapEditorScreen.applyFilter$default(this, false, 1, null);
                    }
                    return true;
                }
            }
            return super.keyPressed(event);
        }
        if (event.key() == 256) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    public boolean charTyped(@NotNull CharacterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (this.view != View.PICK) {
            return false;
        }
        String string = event.codepointAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"codepointAsString(...)");
        String s = string;
        if (((CharSequence)s).length() > 0) {
            boolean bl;
            block4: {
                CharSequence $this$all$iv = s;
                boolean $i$f$all = false;
                for (int i = 0; i < $this$all$iv.length(); ++i) {
                    char element$iv;
                    char it = element$iv = $this$all$iv.charAt(i);
                    boolean bl2 = false;
                    if (it >= ' ') continue;
                    bl = false;
                    break block4;
                }
                bl = true;
            }
            if (bl) {
                this.search = this.search + s;
                GlobalRemapEditorScreen.applyFilter$default(this, false, 1, null);
                return true;
            }
        }
        return false;
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    private final float toUi(double scaled) {
        Window window = Minecraft.getInstance().getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)window, (String)"getWindow(...)");
        Window window2 = window;
        return (float)(scaled * (double)window2.getGuiScale() / (double)this.uiScale);
    }

    private final String prettify(String id) {
        return StringsKt.removePrefix((String)id, (CharSequence)"minecraft:");
    }

    private static final Unit renderListView$lambda$0(GlobalRemapEditorScreen this$0) {
        this$0.enterPick(PickPhase.SOURCE, null);
        return Unit.INSTANCE;
    }

    private static final Unit renderListView$lambda$1() {
        Minecraft.getInstance().keyboardHandler.setClipboard(GlobalRemap.INSTANCE.exportJson());
        return Unit.INSTANCE;
    }

    private static final Unit renderListView$lambda$2() {
        String string = Minecraft.getInstance().keyboardHandler.getClipboard();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getClipboard(...)");
        GlobalRemap.INSTANCE.importJson(string);
        return Unit.INSTANCE;
    }

    private static final Unit renderListView$lambda$3() {
        GlobalRemap.INSTANCE.clear();
        return Unit.INSTANCE;
    }

    private static final boolean renderSourceBlock$lambda$0(GlobalRemapEditorScreen this$0, float y) {
        return y + this$0.rowH > this$0.listTop && y < this$0.listBottom;
    }

    private static final Unit renderSourceBlock$lambda$1(String $src) {
        GlobalRemap.INSTANCE.removeSource($src);
        return Unit.INSTANCE;
    }

    private static final Unit renderSourceBlock$lambda$2(GlobalRemapEditorScreen this$0, String $src) {
        this$0.enterPick(PickPhase.TARGET, $src);
        return Unit.INSTANCE;
    }

    private static final Unit renderSourceBlock$lambda$3(String $src, int $i) {
        GlobalRemap.INSTANCE.removeTarget($src, $i);
        return Unit.INSTANCE;
    }

    private static final Unit renderSourceBlock$lambda$4(String $src, int $i, GlobalRemap.RemapTarget $t) {
        GlobalRemap.INSTANCE.setWeight($src, $i, $t.getWeight() + 1);
        return Unit.INSTANCE;
    }

    private static final Unit renderSourceBlock$lambda$5(String $src, int $i, GlobalRemap.RemapTarget $t) {
        GlobalRemap.INSTANCE.setWeight($src, $i, $t.getWeight() - 1);
        return Unit.INSTANCE;
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0013"}, d2={"Lcom/cb/gui/screens/GlobalRemapEditorScreen$Hit;", "", "x", "", "y", "w", "h", "act", "Lkotlin/Function0;", "", "<init>", "(FFFFLkotlin/jvm/functions/Function0;)V", "getX", "()F", "getY", "getW", "getH", "getAct", "()Lkotlin/jvm/functions/Function0;", "cb_client"})
    private static final class Hit {
        private final float x;
        private final float y;
        private final float w;
        private final float h;
        @NotNull
        private final Function0<Unit> act;

        public Hit(float x, float y, float w, float h, @NotNull Function0<Unit> act) {
            Intrinsics.checkNotNullParameter(act, (String)"act");
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.act = act;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final float getW() {
            return this.w;
        }

        public final float getH() {
            return this.h;
        }

        @NotNull
        public final Function0<Unit> getAct() {
            return this.act;
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cb/gui/screens/GlobalRemapEditorScreen$PickPhase;", "", "<init>", "(Ljava/lang/String;I)V", "SOURCE", "TARGET", "cb_client"})
    private static final class PickPhase
    extends Enum<PickPhase> {
        public static final /* enum */ PickPhase SOURCE = new PickPhase();
        public static final /* enum */ PickPhase TARGET = new PickPhase();
        private static final /* synthetic */ PickPhase[] $VALUES;
        private static final /* synthetic */ EnumEntries $ENTRIES;

        public static PickPhase[] values() {
            return (PickPhase[])$VALUES.clone();
        }

        public static PickPhase valueOf(String value) {
            return Enum.valueOf(PickPhase.class, value);
        }

        @NotNull
        public static EnumEntries<PickPhase> getEntries() {
            return $ENTRIES;
        }

        static {
            $VALUES = pickPhaseArray = new PickPhase[]{PickPhase.SOURCE, PickPhase.TARGET};
            $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cb/gui/screens/GlobalRemapEditorScreen$View;", "", "<init>", "(Ljava/lang/String;I)V", "LIST", "PICK", "cb_client"})
    private static final class View
    extends Enum<View> {
        public static final /* enum */ View LIST = new View();
        public static final /* enum */ View PICK = new View();
        private static final /* synthetic */ View[] $VALUES;
        private static final /* synthetic */ EnumEntries $ENTRIES;

        public static View[] values() {
            return (View[])$VALUES.clone();
        }

        public static View valueOf(String value) {
            return Enum.valueOf(View.class, value);
        }

        @NotNull
        public static EnumEntries<View> getEntries() {
            return $ENTRIES;
        }

        static {
            $VALUES = viewArray = new View[]{View.LIST, View.PICK};
            $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);
        }
    }

    @Metadata(mv={2, 3, 0}, k=3, xi=48)
    public static final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[PickPhase.values().length];
            try {
                nArray[PickPhase.SOURCE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[PickPhase.TARGET.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}


