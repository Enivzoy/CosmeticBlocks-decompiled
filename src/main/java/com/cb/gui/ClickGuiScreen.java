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
package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.config.tabs.GuiTab;
import com.cb.gui.NvgScreen;
import com.cb.gui.SettingsPanel;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0006H\u0016J\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0014J \u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J(\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020$2\u0006\u0010 \u001a\u00020$2\u0006\u0010%\u001a\u00020\u0006H\u0016J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u001aH\u0016J\u0010\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+H\u0002J\u0018\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020'H\u0016J\u0010\u00100\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0016J \u00101\u001a\u00020'2\u0006\u0010-\u001a\u00020.2\u0006\u00102\u001a\u00020+2\u0006\u00103\u001a\u00020+H\u0016J(\u00104\u001a\u00020'2\u0006\u0010\u001f\u001a\u00020+2\u0006\u0010 \u001a\u00020+2\u0006\u00105\u001a\u00020+2\u0006\u00106\u001a\u00020+H\u0016J\u0010\u00107\u001a\u00020'2\u0006\u0010-\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020'2\u0006\u0010-\u001a\u00020:H\u0016R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2={"Lcom/cb/gui/ClickGuiScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "value", "", "uiScale", "getUiScale", "()F", "uiWidth", "getUiWidth", "uiHeight", "getUiHeight", "panels", "", "Lcom/cb/gui/SettingsPanel;", "openedAtNanos", "", "tooltipRequest", "", "tooltipCurrent", "tooltipTimer", "tooltipX", "tooltipY", "renderOverlay", "", "delta", "requestTooltip", "text", "renderTooltip", "mouseX", "mouseY", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "", "partialTick", "isPauseScreen", "", "onClose", "toUi", "scaled", "", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseReleased", "mouseDragged", "dragX", "dragY", "mouseScrolled", "scrollX", "scrollY", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "cb_client"})
@SourceDebugExtension(value={"SMAP\nClickGuiScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClickGuiScreen.kt\ncom/cb/gui/ClickGuiScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,201:1\n1596#2:202\n1629#2,4:203\n1915#2,2:207\n1915#2,2:209\n1915#2,2:211\n1915#2,2:213\n*S KotlinDebug\n*F\n+ 1 ClickGuiScreen.kt\ncom/cb/gui/ClickGuiScreen\n*L\n38#1:202\n38#1:203,4\n81#1:207,2\n127#1:209,2\n155#1:211,2\n162#1:213,2\n*E\n"})
public final class ClickGuiScreen
extends Screen
implements NvgScreen {
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    @NotNull
    private final List<SettingsPanel> panels;
    private final long openedAtNanos;
    @Nullable
    private String tooltipRequest;
    @Nullable
    private String tooltipCurrent;
    private float tooltipTimer;
    private float tooltipX;
    private float tooltipY;

    /*
     * WARNING - void declaration
     */
    public ClickGuiScreen() {
        super((Component)Component.literal((String)"Cosmetic Blocks"));
        void $this$mapIndexedTo$iv$iv;
        void $this$mapIndexed$iv;
        Iterable iterable = Tabs.INSTANCE.getAll();
        ClickGuiScreen clickGuiScreen = this;
        boolean $i$f$mapIndexed = false;
        void var3_4 = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$mapIndexed$iv, (int)10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void index;
            void tab;
            int n;
            if ((n = index$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            SettingsTab settingsTab = (SettingsTab)item$iv$iv;
            int n2 = n;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new SettingsPanel(this, (SettingsTab)tab, 16.0f + (float)index * 254.0f, 16.0f));
        }
        clickGuiScreen.panels = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        this.openedAtNanos = System.nanoTime();
    }

    public final float getUiScale() {
        return this.uiScale;
    }

    public final float getUiWidth() {
        return this.uiWidth;
    }

    public final float getUiHeight() {
        return this.uiHeight;
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
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            this.tooltipRequest = null;
            NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, 0x66000000, 0.0f, 32, null);
            Iterable $this$forEach$iv = this.panels;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SettingsPanel it = (SettingsPanel)element$iv;
                boolean bl = false;
                it.render(mouseX, mouseY, delta);
            }
            this.renderTooltip(mouseX, mouseY, delta);
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    public final void requestTooltip(@NotNull String text) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        this.tooltipRequest = text;
    }

    private final void renderTooltip(float mouseX, float mouseY, float delta) {
        String request = this.tooltipRequest;
        if (!Intrinsics.areEqual((Object)request, (Object)this.tooltipCurrent)) {
            this.tooltipCurrent = request;
            this.tooltipTimer = 0.0f;
        }
        if (request == null) {
            return;
        }
        this.tooltipTimer += delta;
        if (this.tooltipTimer < 0.55f) {
            return;
        }
        float size = 12.0f;
        float textWidth = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, request, size, null, 4, null);
        float boxWidth = textWidth + 16.0f;
        float boxHeight = 22.0f;
        this.tooltipX = RangesKt.coerceAtMost((float)(mouseX + 12.0f), (float)(this.uiWidth - boxWidth - 4.0f));
        this.tooltipY = RangesKt.coerceAtMost((float)(mouseY + 14.0f), (float)(this.uiHeight - boxHeight - 4.0f));
        NVGRenderer.INSTANCE.shadow(this.tooltipX, this.tooltipY, boxWidth, boxHeight, 5.0f, 8.0f, 0x66000000);
        NVGRenderer.INSTANCE.rect(this.tooltipX, this.tooltipY, boxWidth, boxHeight, -14935012, 5.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, this.tooltipX, this.tooltipY, boxWidth, boxHeight, 0x14FFFFFF, 5.0f, 0.0f, 64, null);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, request, this.tooltipX + 8.0f, this.tooltipY + boxHeight / 2.0f, size, -6513508, null, 32, null);
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        Iterable $this$forEach$iv = this.panels;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingsPanel it = (SettingsPanel)element$iv;
            boolean bl = false;
            it.persistState();
        }
        Config.INSTANCE.save();
        super.onClose();
    }

    private final float toUi(double scaled) {
        Window window = Minecraft.getInstance().getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)window, (String)"getWindow(...)");
        Window window2 = window;
        return (float)(scaled * (double)window2.getGuiScale() / (double)this.uiScale);
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        float mouseX = this.toUi(event.x());
        float mouseY = this.toUi(event.y());
        for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
            if (!panel.mouseClicked(mouseX, mouseY, event.button())) continue;
            this.panels.remove(panel);
            this.panels.add(panel);
            return true;
        }
        return false;
    }

    public boolean mouseReleased(@NotNull MouseButtonEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        Iterable $this$forEach$iv = this.panels;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingsPanel it = (SettingsPanel)element$iv;
            boolean bl = false;
            it.mouseReleased(event.button());
        }
        return true;
    }

    public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        float mouseX = this.toUi(event.x());
        float mouseY = this.toUi(event.y());
        Iterable $this$forEach$iv = this.panels;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SettingsPanel it = (SettingsPanel)element$iv;
            boolean bl = false;
            it.mouseDragged(mouseX, mouseY);
        }
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        float uiX = this.toUi(mouseX);
        float uiY = this.toUi(mouseY);
        for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
            if (!panel.mouseScrolled(uiX, uiY, (float)scrollY)) continue;
            return true;
        }
        return false;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
            if (!panel.keyPressed(event.key(), event.modifiers())) continue;
            return true;
        }
        if (event.key() == 256) {
            this.onClose();
            return true;
        }
        int openKey = ((Number)GuiTab.INSTANCE.getOpenGuiKeybind().getValue()).intValue();
        if (openKey != -1 && event.key() == openKey) {
            if (System.nanoTime() - this.openedAtNanos > 600000000L) {
                this.onClose();
            }
            return true;
        }
        return super.keyPressed(event);
    }

    public boolean charTyped(@NotNull CharacterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        String string = event.codepointAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"codepointAsString(...)");
        String text = string;
        for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
            if (!panel.charTyped(text)) continue;
            return true;
        }
        return false;
    }
}


