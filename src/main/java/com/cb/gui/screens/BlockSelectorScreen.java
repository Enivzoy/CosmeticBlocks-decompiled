/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.Triple
 *  kotlin.collections.CollectionsKt
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
 */
package com.cb.gui.screens;

import com.cb.cosmetic.CosmeticBlocks;
import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
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

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\b\u0010#\u001a\u00020$H\u0002J\u0012\u0010%\u001a\u00020$2\b\b\u0002\u0010&\u001a\u00020'H\u0002J\u0010\u0010(\u001a\u00020$2\u0006\u0010)\u001a\u00020\u0006H\u0016J\u0016\u0010*\u001a\u00020$2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J(\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u0006H\u0016J\b\u00100\u001a\u00020'H\u0016J\u0018\u00101\u001a\u00020'2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020'H\u0016J(\u00105\u001a\u00020'2\u0006\u0010\u0013\u001a\u0002062\u0006\u0010\u0014\u001a\u0002062\u0006\u00107\u001a\u0002062\u0006\u00108\u001a\u000206H\u0016J\u0010\u00109\u001a\u00020'2\u0006\u00102\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020'2\u0006\u00102\u001a\u00020<H\u0016J\u0010\u0010=\u001a\u00020\u00062\u0006\u0010>\u001a\u000206H\u0002J\u0010\u0010?\u001a\u00020\u000b2\u0006\u0010@\u001a\u00020\u000bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u000e\u0010\u001c\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R&\u0010!\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\"0\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2={"Lcom/cb/gui/screens/BlockSelectorScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "uiScale", "", "uiWidth", "uiHeight", "allBlocks", "", "", "filtered", "lastFilterKey", "search", "highlightIdx", "", "lastHighlightIdx", "scroll", "mouseX", "mouseY", "blink", "rowH", "panelW", "getPanelW", "()F", "panelH", "getPanelH", "px", "py", "listTop", "listBottom", "favStripBottom", "favRects", "Lkotlin/Triple;", "ensureBlocks", "", "applyFilter", "force", "", "renderOverlay", "delta", "renderList", "favs", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "partialTick", "isPauseScreen", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseScrolled", "", "scrollX", "scrollY", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "toUi", "scaled", "prettify", "id", "cb_client"})
@SourceDebugExtension(value={"SMAP\nBlockSelectorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockSelectorScreen.kt\ncom/cb/gui/screens/BlockSelectorScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 5 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,325:1\n1586#2:326\n1661#2,3:327\n777#2:330\n873#2,2:331\n1#3:333\n445#4,8:334\n1088#5,2:342\n*S KotlinDebug\n*F\n+ 1 BlockSelectorScreen.kt\ncom/cb/gui/screens/BlockSelectorScreen\n*L\n62#1:326\n62#1:327,3\n71#1:330\n71#1:331,2\n202#1:334,8\n310#1:342,2\n*E\n"})
public final class BlockSelectorScreen
extends Screen
implements NvgScreen {
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    @NotNull
    private List<String> allBlocks = CollectionsKt.emptyList();
    @NotNull
    private List<String> filtered = CollectionsKt.emptyList();
    @NotNull
    private String lastFilterKey = "__none__";
    @NotNull
    private String search = "";
    private int highlightIdx;
    private int lastHighlightIdx = -1;
    private float scroll;
    private float mouseX;
    private float mouseY;
    private float blink;
    private final float rowH;
    private float px;
    private float py;
    private float listTop;
    private float listBottom;
    private float favStripBottom;
    @NotNull
    private List<Triple<String, Float, Float>> favRects = CollectionsKt.emptyList();

    public BlockSelectorScreen() {
        super((Component)Component.literal((String)"Block Selector"));
        this.rowH = 18.0f;
    }

    private final float getPanelW() {
        return RangesKt.coerceIn((float)(this.uiWidth * 0.55f), (float)360.0f, (float)560.0f);
    }

    private final float getPanelH() {
        return RangesKt.coerceIn((float)(this.uiHeight * 0.75f), (float)280.0f, (float)540.0f);
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
            BlockSelectorScreen blockSelectorScreen = this;
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
            blockSelectorScreen.allBlocks = CollectionsKt.sorted((Iterable)((List)destination$iv$iv));
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
        BlockSelectorScreen blockSelectorScreen = this;
        if (((CharSequence)q).length() == 0) {
            list = this.allBlocks;
        } else {
            void $this$filterTo$iv$iv;
            void $this$filter$iv;
            Iterable iterable = this.allBlocks;
            BlockSelectorScreen blockSelectorScreen2 = blockSelectorScreen;
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
            blockSelectorScreen = blockSelectorScreen2;
        }
        blockSelectorScreen.filtered = list;
        this.highlightIdx = RangesKt.coerceIn((int)this.highlightIdx, (int)0, (int)RangesKt.coerceAtLeast((int)(this.filtered.size() - 1), (int)0));
    }

    static /* synthetic */ void applyFilter$default(BlockSelectorScreen blockSelectorScreen, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        blockSelectorScreen.applyFilter(bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    @Override
    public void renderOverlay(float delta) {
        this.ensureBlocks();
        BlockSelectorScreen.applyFilter$default(this, false, 1, null);
        this.blink += delta;
        v0 = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"getInstance(...)");
        minecraft = v0;
        v1 = minecraft.getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"getWindow(...)");
        window = v1;
        fbWidth = window.getWidth();
        fbHeight = window.getHeight();
        if (fbWidth <= 0 || fbHeight <= 0) {
            return;
        }
        this.uiScale = RangesKt.coerceIn((float)((float)window.getGuiScale() * 0.75f), (float)1.0f, (float)3.0f);
        this.uiWidth = (float)fbWidth / this.uiScale;
        this.uiHeight = (float)fbHeight / this.uiScale;
        screenWidth = window.getScreenWidth();
        screenHeight = window.getScreenHeight();
        fbScaleX = screenWidth > 0 ? (float)fbWidth / (float)screenWidth : 1.0f;
        fbScaleY = screenHeight > 0 ? (float)fbHeight / (float)screenHeight : 1.0f;
        this.mouseX = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
        this.mouseY = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            block14: {
                NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, 0x66000000, 0.0f, 32, null);
                w = this.getPanelW();
                h = this.getPanelH();
                this.px = (this.uiWidth - w) / 2.0f;
                this.py = (this.uiHeight - h) / 2.0f;
                NVGRenderer.INSTANCE.shadow(this.px, this.py, w, h, 10.0f, 16.0f, -2147483648);
                NVGRenderer.INSTANCE.rect(this.px, this.py, w, h, -183233516, 10.0f);
                NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.px, this.py, w, h, 0x14FFFFFF, 10.0f, 0.0f, 64, null);
                cy = this.py + 10.0f;
                NVGRenderer.INSTANCE.text("Brush Block", this.px + 10.0f, cy + 7.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
                NVGRenderer.textRight$default(NVGRenderer.INSTANCE, "Current: " + this.prettify(CosmeticBlocks.INSTANCE.getSelectedBlock()), this.px + w - 10.0f, cy + 7.0f, 12.0f, -6513508, null, 32, null);
                searchH = 22.0f;
                NVGRenderer.INSTANCE.rect(this.px + 10.0f, cy += 22.0f, w - 20.0f, searchH, 0x66000000, 5.0f);
                NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.px + 10.0f, cy, w - 20.0f, searchH, 0x14FFFFFF, 5.0f, 0.0f, 64, null);
                var15_15 = this.search;
                if (var15_15.length() == 0) {
                    $i$a$-ifEmpty-BlockSelectorScreen$renderOverlay$searchText$1 = false;
                    v2 = "type to filter...";
                } else {
                    v2 = var15_15;
                }
                searchText = (String)v2;
                searchColor = ((CharSequence)this.search).length() == 0 != false ? -10724260 : -1184275;
                NVGRenderer.text$default(NVGRenderer.INSTANCE, searchText, this.px + 10.0f + 8.0f, cy + searchH / 2.0f, 13.0f, searchColor, null, 32, null);
                if (this.blink % 1.0f < 0.55f) {
                    caretX = this.px + 10.0f + 8.0f + NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, this.search, 13.0f, null, 4, null);
                    NVGRenderer.rect$default(NVGRenderer.INSTANCE, caretX + 1.0f, cy + searchH / 2.0f - 6.0f, 1.2f, 12.0f, -1750963, 0.0f, 32, null);
                }
                cy += searchH + 6.0f;
                var17_21 = this;
                try {
                    $this$renderOverlay_u24lambda_u241 = var17_21;
                    $i$a$-runCatching-BlockSelectorScreen$renderOverlay$favs$1 = false;
                    $this$renderOverlay_u24lambda_u241 = Result.constructor-impl(CosmeticBlocks.INSTANCE.getFavorites());
                }
                catch (Throwable $i$a$-runCatching-BlockSelectorScreen$renderOverlay$favs$1) {
                    $this$renderOverlay_u24lambda_u241 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)$i$a$-runCatching-BlockSelectorScreen$renderOverlay$favs$1));
                }
                var17_21 = $this$renderOverlay_u24lambda_u241;
                $this$renderOverlay_u24lambda_u241 = CollectionsKt.emptyList();
                favs = (List)(Result.isFailure-impl((Object)var17_21) != false ? $this$renderOverlay_u24lambda_u241 : var17_21);
                this.favRects = CollectionsKt.emptyList();
                if (!(((Collection)favs).isEmpty() == false)) break block14;
                NVGRenderer.text$default(NVGRenderer.INSTANCE, "FAVORITES", this.px + 10.0f, cy + 6.0f, 12.0f, -10724260, null, 32, null);
                cy += 14.0f;
                chips = new ArrayList<E>();
                chipX = this.px + 10.0f;
                chipH = 18.0f;
                maxX = this.px + w - 10.0f;
                var21_28 = favs.iterator();
                while (var21_28.hasNext() && !(chipX + (cw = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, label = "\u2605 " + this.prettify(id = (String)var21_28.next()), 12.0f, null, 4, null) + 14.0f) > maxX)) {
                    var26_33 = chipX + cw;
                    var27_34 = this.mouseX;
                    v3 = chipX <= var27_34 ? var27_34 <= var26_33 : false;
                    if (!v3) ** GOTO lbl-1000
                    var26_33 = cy + chipH;
                    var27_34 = this.mouseY;
                    v4 = cy <= var27_34 ? var27_34 <= var26_33 : false;
                    if (v4) {
                        v5 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v5 = false;
                    }
                    hovered = v5;
                    NVGRenderer.INSTANCE.rect(chipX, cy, cw, chipH, hovered != false ? 0x12FFFFFF : -14935012, 4.0f);
                    NVGRenderer.text$default(NVGRenderer.INSTANCE, label, chipX + 7.0f, cy + chipH / 2.0f, 12.0f, -1750963, null, 32, null);
                    chips.add(new Triple((Object)id, (Object)Float.valueOf(chipX), (Object)Float.valueOf(cw)));
                    chipX += cw + 6.0f;
                }
                this.favRects = chips;
                cy += chipH + 8.0f;
            }
            this.favStripBottom = cy;
            NVGRenderer.text$default(NVGRenderer.INSTANCE, this.filtered.size() + " result" + (this.filtered.size() == 1 ? "" : "s"), this.px + 10.0f, cy + 6.0f, 12.0f, -10724260, null, 32, null);
            this.listTop = cy += 16.0f;
            this.listBottom = this.py + h - 24.0f;
            this.renderList(favs);
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "Up/Down navigate   |   Enter confirm   |   Right-click toggle favorite", this.px + w / 2.0f, this.py + h - 12.0f, 12.0f, -10724260, null, 32, null);
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private final void renderList(List<String> favs) {
        block18: {
            w = this.getPanelW();
            viewH = this.listBottom - this.listTop;
            totalH = (float)this.filtered.size() * this.rowH;
            maxScroll = RangesKt.coerceAtLeast((float)(totalH - viewH), (float)0.0f);
            this.scroll = RangesKt.coerceIn((float)this.scroll, (float)0.0f, (float)maxScroll);
            if (this.highlightIdx != this.lastHighlightIdx) {
                this.lastHighlightIdx = this.highlightIdx;
                hlY = (float)this.highlightIdx * this.rowH - this.scroll;
                if (hlY < 0.0f) {
                    this.scroll = RangesKt.coerceAtLeast((float)((float)this.highlightIdx * this.rowH), (float)0.0f);
                } else if (hlY + this.rowH > viewH) {
                    this.scroll = RangesKt.coerceAtLeast((float)((float)this.highlightIdx * this.rowH - viewH + this.rowH), (float)0.0f);
                }
                this.scroll = RangesKt.coerceIn((float)this.scroll, (float)0.0f, (float)maxScroll);
            }
            hlY = NVGRenderer.INSTANCE;
            var7_8 = this.px;
            var8_9 = this.listTop;
            var9_10 = w;
            h$iv = viewH;
            $i$f$scissor = false;
            this_$iv.save();
            this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
            try {
                $i$a$-scissor-BlockSelectorScreen$renderList$1 = false;
                first = RangesKt.coerceAtLeast((int)((int)(this.scroll / this.rowH)), (int)0);
                last = RangesKt.coerceAtMost((int)((int)((this.scroll + viewH) / this.rowH)), (int)(this.filtered.size() - 1));
                i = first;
                if (i > last) break block18;
                while (true) {
                    id = this.filtered.get(i);
                    ry = this.listTop + (float)i * this.rowH - this.scroll;
                    isHighlight = i == this.highlightIdx;
                    var19_21 = this.px;
                    var20_23 = this.px + w;
                    var21_24 = this.mouseX;
                    v0 = var19_21 <= var21_24 ? var21_24 <= var20_23 : false;
                    if (!v0) ** GOTO lbl-1000
                    var19_21 = ry + this.rowH;
                    var20_23 = this.mouseY;
                    v1 = ry <= var20_23 ? var20_23 <= var19_21 : false;
                    if (v1) {
                        v2 = true;
                    } else lbl-1000:
                    // 2 sources

                    {
                        v2 = hovered = false;
                    }
                    if (isHighlight) {
                        NVGRenderer.INSTANCE.rect(this.px + 2.0f, ry, w - 4.0f, this.rowH, Theme.INSTANCE.withAlpha(-1750963, 0.28f), 4.0f);
                    } else if (hovered) {
                        NVGRenderer.INSTANCE.rect(this.px + 2.0f, ry, w - 4.0f, this.rowH, 0x12FFFFFF, 4.0f);
                    }
                    NVGRenderer.text$default(NVGRenderer.INSTANCE, this.prettify(id), this.px + 10.0f + 4.0f, ry + this.rowH / 2.0f, 12.0f, -1184275, null, 32, null);
                    isCurrent = Intrinsics.areEqual((Object)id, (Object)CosmeticBlocks.INSTANCE.getSelectedBlock());
                    isFav = favs.contains(id);
                    if (isCurrent) {
                        NVGRenderer.textRight$default(NVGRenderer.INSTANCE, "\u25cf active", this.px + w - 10.0f, ry + this.rowH / 2.0f, 12.0f, -1750963, null, 32, null);
                    } else if (isFav) {
                        NVGRenderer.textRight$default(NVGRenderer.INSTANCE, "\u2605", this.px + w - 10.0f, ry + this.rowH / 2.0f, 12.0f, -15821, null, 32, null);
                    }
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
        if (this.filtered.isEmpty()) {
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "No blocks match.", this.px + w / 2.0f, this.listTop + 16.0f, 12.0f, -10724260, null, 32, null);
        }
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        float mx = this.toUi(event.x());
        float my = this.toUi(event.y());
        int button = event.button();
        for (Triple<String, Float, Float> triple : this.favRects) {
            String id = (String)triple.component1();
            float fx = ((Number)triple.component2()).floatValue();
            float fw = ((Number)triple.component3()).floatValue();
            boolean bl = fx <= mx ? mx <= fx + fw : false;
            if (!bl) continue;
            float f = this.favStripBottom - 26.0f;
            boolean bl2 = my <= this.favStripBottom ? f <= my : false;
            if (!bl2 || button != 0) continue;
            CosmeticBlocks.INSTANCE.setBlock(id);
            this.onClose();
            return true;
        }
        float f = this.px;
        boolean bl = mx <= this.px + this.getPanelW() ? f <= mx : false;
        if (bl) {
            f = this.listTop;
            boolean bl3 = my <= this.listBottom ? f <= my : false;
            if (bl3) {
                int idx = (int)((my - this.listTop + this.scroll) / this.rowH);
                boolean bl4 = 0 <= idx ? idx < ((Collection)this.filtered).size() : false;
                if (bl4) {
                    String id = this.filtered.get(idx);
                    switch (button) {
                        case 0: {
                            CosmeticBlocks.INSTANCE.setBlock(id);
                            this.onClose();
                            return true;
                        }
                        case 1: {
                            boolean bl5 = CosmeticBlocks.INSTANCE.isFavorite(id) ? CosmeticBlocks.INSTANCE.removeFavorite(id) : CosmeticBlocks.INSTANCE.addFavorite(id);
                            return true;
                        }
                    }
                }
            }
        }
        if (mx < this.px || mx > this.px + this.getPanelW() || my < this.py || my > this.py + this.getPanelH()) {
            this.onClose();
            return true;
        }
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll -= (float)scrollY * this.rowH * 3.0f;
        return true;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        switch (event.key()) {
            case 256: {
                this.onClose();
                return true;
            }
            case 257: 
            case 335: {
                if (!((Collection)this.filtered).isEmpty()) {
                    CosmeticBlocks.INSTANCE.setBlock(this.filtered.get(RangesKt.coerceIn((int)this.highlightIdx, (int)0, (int)(this.filtered.size() - 1))));
                    this.onClose();
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
                    BlockSelectorScreen.applyFilter$default(this, false, 1, null);
                }
                return true;
            }
        }
        return super.keyPressed(event);
    }

    public boolean charTyped(@NotNull CharacterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        String string = event.codepointAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"codepointAsString(...)");
        String s = string;
        if (((CharSequence)s).length() > 0) {
            boolean bl;
            block3: {
                CharSequence $this$all$iv = s;
                boolean $i$f$all = false;
                for (int i = 0; i < $this$all$iv.length(); ++i) {
                    char element$iv;
                    char it = element$iv = $this$all$iv.charAt(i);
                    boolean bl2 = false;
                    if (it >= ' ') continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
            if (bl) {
                this.search = this.search + s;
                BlockSelectorScreen.applyFilter$default(this, false, 1, null);
                return true;
            }
        }
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
}


