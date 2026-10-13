/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
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
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.gui.screens;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.gui.ClickGuiScreen;
import com.cb.gui.NvgScreen;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.cb.setting.KeybindSetting;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
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
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002:\u0001HB\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\tH\u0016J \u0010&\u001a\u00020$2\u0006\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0002JH\u0010)\u001a\u00020$2\u0006\u0010*\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010'\u001a\u00020\t2\u0006\u0010(\u001a\u00020\t2\u0006\u0010%\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u0016H\u0002J\u0010\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u000201H\u0002J\u0018\u00102\u001a\u00020\u00162\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\u0016H\u0016J(\u00106\u001a\u00020\u00162\u0006\u0010'\u001a\u0002012\u0006\u0010(\u001a\u0002012\u0006\u00107\u001a\u0002012\u0006\u00108\u001a\u000201H\u0016J\u0010\u00109\u001a\u00020\u00162\u0006\u00103\u001a\u00020:H\u0016J\u0010\u0010;\u001a\u00020\u00162\u0006\u00103\u001a\u00020<H\u0016J8\u0010=\u001a\u00020\u00162\u0006\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\t2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020\t2\u0006\u0010@\u001a\u00020\tH\u0002J(\u0010A\u001a\u00020$2\u0006\u0010B\u001a\u00020C2\u0006\u0010'\u001a\u00020D2\u0006\u0010(\u001a\u00020D2\u0006\u0010E\u001a\u00020\tH\u0016J\b\u0010F\u001a\u00020\u0016H\u0016J\b\u0010G\u001a\u00020$H\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\tX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\u001e\u001a\u001e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020 0\u001fj\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020 `!X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006I"}, d2={"Lcom/cb/gui/screens/KeybindEditorScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "allRows", "", "Lcom/cb/gui/screens/KeybindEditorScreen$Row;", "uiScale", "", "uiWidth", "uiHeight", "panelWidth", "panelHeight", "panelX", "panelY", "headerHeight", "searchRowHeight", "rowHeight", "search", "", "searchFocused", "", "capturing", "Lcom/cb/setting/KeybindSetting;", "scroll", "maxScroll", "caretBlink", "listTop", "listBottom", "rowHover", "Ljava/util/HashMap;", "Lcom/cb/render/Anim;", "Lkotlin/collections/HashMap;", "filtered", "renderOverlay", "", "delta", "draw", "mouseX", "mouseY", "drawRow", "row", "x", "y", "w", "sectionStart", "toUi", "scaled", "", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseScrolled", "scrollX", "scrollY", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "inside", "mx", "my", "h", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "", "partialTick", "isPauseScreen", "onClose", "Row", "cb_client"})
@SourceDebugExtension(value={"SMAP\nKeybindEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 KeybindEditorScreen.kt\ncom/cb/gui/screens/KeybindEditorScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,325:1\n1391#2:326\n1480#2,2:327\n812#2,12:329\n1586#2:341\n1661#2,3:342\n1482#2,3:345\n777#2:348\n873#2,2:349\n445#3,8:351\n410#4,7:359\n*S KotlinDebug\n*F\n+ 1 KeybindEditorScreen.kt\ncom/cb/gui/screens/KeybindEditorScreen\n*L\n32#1:326\n32#1:327,2\n33#1:329,12\n33#1:341\n33#1:342,3\n32#1:345,3\n65#1:348\n65#1:349,2\n152#1:351,8\n172#1:359,7\n*E\n"})
public final class KeybindEditorScreen
extends Screen
implements NvgScreen {
    @NotNull
    private final List<Row> allRows;
    private float uiScale;
    private float uiWidth;
    private float uiHeight;
    private final float panelWidth;
    private final float panelHeight;
    private float panelX;
    private float panelY;
    private final float headerHeight;
    private final float searchRowHeight;
    private final float rowHeight;
    @NotNull
    private String search;
    private boolean searchFocused;
    @Nullable
    private KeybindSetting capturing;
    private float scroll;
    private float maxScroll;
    private float caretBlink;
    private float listTop;
    private float listBottom;
    @NotNull
    private final HashMap<String, Anim> rowHover;

    /*
     * WARNING - void declaration
     */
    public KeybindEditorScreen() {
        super((Component)Component.literal((String)"Keybinds"));
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Iterable iterable = Tabs.INSTANCE.getAll();
        KeybindEditorScreen keybindEditorScreen = this;
        boolean $i$f$flatMap = false;
        void var3_4 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            void $this$filterIsInstanceTo$iv$iv;
            SettingsTab tab = (SettingsTab)element$iv$iv;
            boolean bl = false;
            Iterable $this$filterIsInstance$iv = tab.getSettings();
            boolean $i$f$filterIsInstance = false;
            Iterable iterable2 = $this$filterIsInstance$iv;
            Collection destination$iv$iv2 = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv2 : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv2 instanceof KeybindSetting)) continue;
                destination$iv$iv2.add(element$iv$iv2);
            }
            $this$filterIsInstance$iv = (List)destination$iv$iv2;
            boolean $i$f$map = false;
            $this$filterIsInstanceTo$iv$iv = $this$map$iv;
            destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                KeybindSetting keybindSetting = (KeybindSetting)item$iv$iv;
                Collection collection = destination$iv$iv2;
                boolean bl2 = false;
                collection.add(new Row(tab.getName(), (KeybindSetting)it));
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        keybindEditorScreen.allRows = (List)destination$iv$iv;
        this.uiScale = 1.0f;
        this.panelWidth = 440.0f;
        this.panelHeight = 400.0f;
        this.headerHeight = 44.0f;
        this.searchRowHeight = 34.0f;
        this.rowHeight = 28.0f;
        this.search = "";
        this.rowHover = new HashMap();
    }

    /*
     * Unable to fully structure code
     */
    private final List<Row> filtered() {
        v0 = StringsKt.trim((CharSequence)this.search).toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"toLowerCase(...)");
        q = v0;
        if (((CharSequence)q).length() == 0) {
            return this.allRows;
        }
        $this$filter$iv = this.allRows;
        $i$f$filter = false;
        var4_4 = $this$filter$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$filterTo = false;
        for (T element$iv$iv : $this$filterTo$iv$iv) {
            it = (Row)element$iv$iv;
            $i$a$-filter-KeybindEditorScreen$filtered$1 = false;
            v1 = it.getTabName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"toLowerCase(...)");
            if (StringsKt.contains$default((CharSequence)v1, (CharSequence)q, (boolean)false, (int)2, null)) ** GOTO lbl-1000
            v2 = it.getSetting().getName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"toLowerCase(...)");
            if (StringsKt.contains$default((CharSequence)v2, (CharSequence)q, (boolean)false, (int)2, null)) lbl-1000:
            // 2 sources

            {
                v3 = true;
            } else {
                v3 = false;
            }
            if (!v3) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
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
        this.uiScale = RangesKt.coerceIn((float)((float)window2.getGuiScale() * 0.75f), (float)1.0f, (float)3.0f);
        this.uiWidth = (float)fbWidth / this.uiScale;
        this.uiHeight = (float)fbHeight / this.uiScale;
        int screenWidth = window2.getScreenWidth();
        int screenHeight = window2.getScreenHeight();
        float fbScaleX = screenWidth > 0 ? (float)fbWidth / (float)screenWidth : 1.0f;
        float fbScaleY = screenHeight > 0 ? (float)fbHeight / (float)screenHeight : 1.0f;
        float mouseX = (float)minecraft2.mouseHandler.xpos() * fbScaleX / this.uiScale;
        float mouseY = (float)minecraft2.mouseHandler.ypos() * fbScaleY / this.uiScale;
        this.caretBlink += delta;
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            this.draw(mouseX, mouseY, delta);
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    private final void draw(float mouseX, float mouseY, float delta) {
        void w$iv;
        void y$iv;
        void x$iv;
        void this_$iv;
        this.panelX = (this.uiWidth - this.panelWidth) / 2.0f;
        this.panelY = (this.uiHeight - this.panelHeight) / 2.0f;
        NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, 0x66000000, 0.0f, 32, null);
        NVGRenderer.INSTANCE.shadow(this.panelX, this.panelY, this.panelWidth, this.panelHeight, 10.0f, 18.0f, Integer.MIN_VALUE);
        NVGRenderer.INSTANCE.rect(this.panelX, this.panelY, this.panelWidth, this.panelHeight, -183233516, 10.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.panelX, this.panelY, this.panelWidth, this.panelHeight, 0x14FFFFFF, 10.0f, 0.0f, 64, null);
        NVGRenderer.INSTANCE.rectVarying(this.panelX, this.panelY, this.panelWidth, this.headerHeight, 10.0f, 10.0f, 0.0f, 0.0f, -14935012);
        NVGRenderer.INSTANCE.text("Keybinds", this.panelX + 10.0f + 4.0f, this.panelY + 16.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, "Click a row to rebind \u00b7 ESC/DEL clears", this.panelX + 10.0f + 4.0f, this.panelY + 33.0f, 12.0f, -10724260, null, 32, null);
        float closeSize = 18.0f;
        float closeX = this.panelX + this.panelWidth - closeSize - 10.0f;
        float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0f;
        boolean closeHover = this.inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize);
        NVGRenderer.INSTANCE.textCentered("x", closeX + closeSize / 2.0f, closeY + closeSize / 2.0f, 14.0f, closeHover ? -1750963 : -6513508, NVGRenderer.Font.BOLD);
        float searchY = this.panelY + this.headerHeight;
        float fieldX = this.panelX + 10.0f;
        float fieldY = searchY + (this.searchRowHeight - 24.0f) / 2.0f;
        float fieldWidth = this.panelWidth - 20.0f;
        float fieldHeight = 24.0f;
        NVGRenderer.INSTANCE.rect(fieldX, fieldY, fieldWidth, fieldHeight, 0x66000000, 5.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, fieldX, fieldY, fieldWidth, fieldHeight, this.searchFocused ? -1750963 : 0x14FFFFFF, 5.0f, 0.0f, 64, null);
        boolean showCaret = this.searchFocused && this.caretBlink % 1.0f < 0.5f;
        String display = ((CharSequence)this.search).length() == 0 && !this.searchFocused ? "filter keybinds\u2026" : this.search + (showCaret ? "|" : "");
        int tc = ((CharSequence)this.search).length() == 0 && !this.searchFocused ? -10724260 : -1184275;
        NVGRenderer.text$default(NVGRenderer.INSTANCE, display, fieldX + 8.0f, fieldY + fieldHeight / 2.0f, 13.0f, tc, null, 32, null);
        this.listTop = searchY + this.searchRowHeight + 2.0f;
        this.listBottom = this.panelY + this.panelHeight - 10.0f;
        float listX = this.panelX + 10.0f;
        float listWidth = this.panelWidth - 20.0f;
        float listHeight = this.listBottom - this.listTop;
        List<Row> rows = this.filtered();
        float contentHeight = (float)rows.size() * this.rowHeight;
        this.maxScroll = RangesKt.coerceAtLeast((float)(contentHeight - listHeight), (float)0.0f);
        this.scroll = RangesKt.coerceIn((float)this.scroll, (float)0.0f, (float)this.maxScroll);
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = listX;
        float f2 = this.listTop;
        float f3 = listWidth;
        float h$iv = listHeight;
        boolean $i$f$scissor = false;
        this_$iv.save();
        this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            if (rows.isEmpty()) {
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "No matches.", this.panelX + this.panelWidth / 2.0f, this.listTop + 24.0f, 13.0f, -10724260, null, 32, null);
            } else {
                float rowY = this.listTop - this.scroll;
                String currentTab = "";
                for (Row row : rows) {
                    if (rowY + this.rowHeight >= this.listTop && rowY <= this.listBottom) {
                        this.drawRow(row, listX, rowY, listWidth, mouseX, mouseY, delta, !Intrinsics.areEqual((Object)row.getTabName(), (Object)currentTab));
                    }
                    currentTab = row.getTabName();
                    rowY += this.rowHeight;
                }
            }
        }
        finally {
            this_$iv.restore();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void drawRow(Row row, float x, float y, float w, float mouseX, float mouseY, float delta, boolean sectionStart) {
        Object object;
        void $this$getOrPut$iv;
        String key = row.getTabName() + "/" + row.getSetting().getName();
        boolean hovered = this.inside(mouseX, mouseY, x, y, w, this.rowHeight) && mouseY >= this.listTop && mouseY <= this.listBottom;
        Map map = this.rowHover;
        String key$iv = key;
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(key$iv);
        if (value$iv == null) {
            boolean bl = false;
            Anim answer$iv = new Anim(0.0f, 0.0f, 2, null);
            $this$getOrPut$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        Anim anim = (Anim)object;
        anim.setTarget(hovered ? 1.0f : 0.0f);
        anim.update(delta);
        if (anim.getValue() > 0.001f) {
            NVGRenderer.INSTANCE.rect(x, y, w, this.rowHeight, Theme.INSTANCE.withAlpha(-1, anim.getValue() * 0.07f), 5.0f);
        }
        String label = row.getTabName() + " \u00b7 " + row.getSetting().getName();
        NVGRenderer.text$default(NVGRenderer.INSTANCE, label, x + 8.0f, y + this.rowHeight / 2.0f, 13.0f, -1184275, null, 32, null);
        float btnW = 110.0f;
        float btnH = 20.0f;
        float btnX = x + w - btnW - 6.0f;
        float btnY = y + (this.rowHeight - btnH) / 2.0f;
        boolean isCapturing = this.capturing == row.getSetting();
        String text = isCapturing ? "press a key\u2026" : row.getSetting().displayName();
        int bg = isCapturing ? 1088768077 : (hovered ? -14013910 : 0x66000000);
        int fg = isCapturing ? -1750963 : (!row.getSetting().getBound() ? -10724260 : -1184275);
        NVGRenderer.INSTANCE.rect(btnX, btnY, btnW, btnH, bg, 5.0f);
        NVGRenderer.INSTANCE.textCentered(text, btnX + btnW / 2.0f, btnY + btnH / 2.0f, 12.0f, fg, NVGRenderer.Font.MEDIUM);
    }

    private final float toUi(double scaled) {
        Window window = Minecraft.getInstance().getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)window, (String)"getWindow(...)");
        Window window2 = window;
        return (float)(scaled * (double)window2.getGuiScale() / (double)this.uiScale);
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        float closeY;
        float closeSize;
        float closeX;
        float my;
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.button() != 0) {
            return false;
        }
        float mx = this.toUi(event.x());
        if (this.inside(mx, my = this.toUi(event.y()), closeX = this.panelX + this.panelWidth - (closeSize = 18.0f) - 10.0f, closeY = this.panelY + (this.headerHeight - closeSize) / 2.0f, closeSize, closeSize)) {
            this.onClose();
            return true;
        }
        KeybindSetting wasCapturing = this.capturing;
        this.capturing = null;
        float searchY = this.panelY + this.headerHeight;
        float fieldX = this.panelX + 10.0f;
        float fieldY = searchY + (this.searchRowHeight - 24.0f) / 2.0f;
        float fieldWidth = this.panelWidth - 20.0f;
        float fieldHeight = 24.0f;
        this.searchFocused = this.inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight);
        if (this.searchFocused) {
            return true;
        }
        if (my >= this.listTop && my <= this.listBottom) {
            float listX = this.panelX + 10.0f;
            float listWidth = this.panelWidth - 20.0f;
            float rowY = this.listTop - this.scroll;
            for (Row row : this.filtered()) {
                if (this.inside(mx, my, listX, rowY, listWidth, this.rowHeight)) {
                    this.capturing = wasCapturing == row.getSetting() ? null : row.getSetting();
                    return true;
                }
                rowY += this.rowHeight;
            }
        }
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll = RangesKt.coerceIn((float)(this.scroll - (float)scrollY * 28.0f), (float)0.0f, (float)this.maxScroll);
        return true;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        int key = event.key();
        KeybindSetting target = this.capturing;
        if (target != null) {
            switch (key) {
                case 256: 
                case 259: 
                case 261: {
                    target.clear();
                    break;
                }
                default: {
                    target.setValue(key);
                }
            }
            this.capturing = null;
            Config.INSTANCE.save();
            return true;
        }
        if (this.searchFocused) {
            switch (key) {
                case 259: {
                    if (((CharSequence)this.search).length() > 0) {
                        this.search = StringsKt.dropLast((String)this.search, (int)1);
                    }
                    return true;
                }
                case 256: {
                    this.searchFocused = false;
                    return true;
                }
            }
        }
        if (key == 256) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    public boolean charTyped(@NotNull CharacterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (!this.searchFocused) {
            return false;
        }
        this.search = this.search + event.codepointAsString();
        this.scroll = 0.0f;
        return true;
    }

    private final boolean inside(float mx, float my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        block0: {
            Config.INSTANCE.save();
            Minecraft minecraft = this.minecraft;
            if (minecraft == null) break block0;
            minecraft.gui.setScreen((Screen)new ClickGuiScreen());
        }
    }

    @Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0014\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0083\u0004J\n\u0010\u0012\u001a\u00020\u0013H\u00d6\u0081\u0004J\n\u0010\u0014\u001a\u00020\u0003H\u00d6\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2={"Lcom/cb/gui/screens/KeybindEditorScreen$Row;", "", "tabName", "", "setting", "Lcom/cb/setting/KeybindSetting;", "<init>", "(Ljava/lang/String;Lcom/cb/setting/KeybindSetting;)V", "getTabName", "()Ljava/lang/String;", "getSetting", "()Lcom/cb/setting/KeybindSetting;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "cb_client"})
    private static final class Row {
        @NotNull
        private final String tabName;
        @NotNull
        private final KeybindSetting setting;

        public Row(@NotNull String tabName, @NotNull KeybindSetting setting) {
            Intrinsics.checkNotNullParameter((Object)tabName, (String)"tabName");
            Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
            this.tabName = tabName;
            this.setting = setting;
        }

        @NotNull
        public final String getTabName() {
            return this.tabName;
        }

        @NotNull
        public final KeybindSetting getSetting() {
            return this.setting;
        }

        @NotNull
        public final String component1() {
            return this.tabName;
        }

        @NotNull
        public final KeybindSetting component2() {
            return this.setting;
        }

        @NotNull
        public final Row copy(@NotNull String tabName, @NotNull KeybindSetting setting) {
            Intrinsics.checkNotNullParameter((Object)tabName, (String)"tabName");
            Intrinsics.checkNotNullParameter((Object)setting, (String)"setting");
            return new Row(tabName, setting);
        }

        public static /* synthetic */ Row copy$default(Row row, String string, KeybindSetting keybindSetting, int n, Object object) {
            if ((n & 1) != 0) {
                string = row.tabName;
            }
            if ((n & 2) != 0) {
                keybindSetting = row.setting;
            }
            return row.copy(string, keybindSetting);
        }

        @NotNull
        public String toString() {
            return "Row(tabName=" + this.tabName + ", setting=" + this.setting + ")";
        }

        public int hashCode() {
            int result = this.tabName.hashCode();
            result = result * 31 + this.setting.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Row)) {
                return false;
            }
            Row row = (Row)other;
            if (!Intrinsics.areEqual((Object)this.tabName, (Object)row.tabName)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.setting, (Object)row.setting);
        }
    }
}


