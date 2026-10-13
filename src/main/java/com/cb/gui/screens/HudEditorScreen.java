/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.cb.gui.screens;

import com.cb.gui.NvgScreen;
import com.cb.gui.hud.HudElement;
import com.cb.gui.hud.HudManager;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\b\u0010\u0016\u001a\u00020\u0006H\u0002J\b\u0010\u0017\u001a\u00020\u0006H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0013H\u0016J\b\u0010\u001b\u001a\u00020\u0019H\u0002J\u0018\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013H\u0002J\u0018\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u0013H\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\tH\u0002J\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020#H\u0016J \u0010'\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020)H\u0016J\u0010\u0010+\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010,\u001a\u00020#2\u0006\u0010$\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\tH\u0002J(\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00062\u0006\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\u0013H\u0016J\b\u00105\u001a\u00020#H\u0016J\b\u00106\u001a\u00020\u0019H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2={"Lcom/cb/gui/screens/HudEditorScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "snapThreshold", "", "gridSize", "draggingElement", "Lcom/cb/gui/hud/HudElement;", "activeSnapLinesX", "", "Lkotlin/Pair;", "activeSnapLinesY", "colorCenter", "colorGrid", "colorEdge", "colorElement", "uiScale", "", "screenW", "screenH", "scaledWidth", "scaledHeight", "renderOverlay", "", "delta", "draw", "drawGrid", "w", "h", "drawSnapLines", "drawElement", "element", "mouseClicked", "", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseDragged", "dragX", "", "dragY", "mouseReleased", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "applySmartSnap", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "mouseX", "mouseY", "partialTick", "isPauseScreen", "onClose", "cb_client"})
public final class HudEditorScreen
extends Screen
implements NvgScreen {
    private final int snapThreshold;
    private final int gridSize;
    @Nullable
    private HudElement draggingElement;
    @NotNull
    private final List<Pair<Integer, Integer>> activeSnapLinesX = new ArrayList();
    @NotNull
    private final List<Pair<Integer, Integer>> activeSnapLinesY = new ArrayList();
    private final int colorCenter;
    private final int colorGrid;
    private final int colorEdge;
    private final int colorElement;
    private float uiScale = 1.0f;
    private int screenW;
    private int screenH;

    public HudEditorScreen() {
        super((Component)Component.literal((String)"HUD Editor"));
        this.snapThreshold = 6;
        this.gridSize = 20;
        this.colorCenter = -16711681;
        this.colorGrid = -5570646;
        this.colorEdge = -256;
        this.colorElement = -30720;
    }

    private final int scaledWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    private final int scaledHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
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
        this.uiScale = RangesKt.coerceAtLeast((float)window2.getGuiScale(), (float)1.0f);
        this.gui.getScreen()W = window2.getGuiScaledWidth();
        this.gui.getScreen()H = window2.getGuiScaledHeight();
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            this.draw();
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    private final void draw() {
        float w = this.gui.getScreen()W;
        float h = this.gui.getScreen()H;
        NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, w, h, 0x60000000, 0.0f, 32, null);
        this.drawGrid(w, h);
        this.drawSnapLines(w, h);
        for (HudElement element : HudManager.INSTANCE.getElements()) {
            this.drawElement(element);
        }
        NVGRenderer.INSTANCE.text("HUD Editor", 10.0f, 12.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, "Cyan=Center  Green=Grid  Yellow=Edge  Orange=Element", 10.0f, 28.0f, 12.0f, -6513508, null, 32, null);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, "Drag elements \u00b7 ESC saves and exits", 10.0f, 42.0f, 12.0f, -10724260, null, 32, null);
    }

    private final void drawGrid(float w, float h) {
        int centerX = this.gui.getScreen()W / 2;
        int centerY = this.gui.getScreen()H / 2;
        int grid = 0x25FFFFFF;
        int center = 0x60FFFFFF;
        for (int x = this.gridSize; x < this.gui.getScreen()W; x += this.gridSize) {
            NVGRenderer.INSTANCE.line(x, 0.0f, x, h, x == centerX ? center : grid, 1.0f);
        }
        for (int y = this.gridSize; y < this.gui.getScreen()H; y += this.gridSize) {
            NVGRenderer.INSTANCE.line(0.0f, y, w, y, y == centerY ? center : grid, 1.0f);
        }
        NVGRenderer.INSTANCE.line(centerX, 0.0f, centerX, h, 0x50FFFFFF, 2.0f);
        NVGRenderer.INSTANCE.line(0.0f, centerY, w, centerY, 0x50FFFFFF, 2.0f);
    }

    private final void drawSnapLines(float w, float h) {
        int color;
        for (Pair<Integer, Integer> pair : this.activeSnapLinesX) {
            int x = ((Number)pair.component1()).intValue();
            color = ((Number)pair.component2()).intValue();
            NVGRenderer.INSTANCE.line(x, 0.0f, x, h, color, 2.0f);
        }
        for (Pair<Integer, Integer> pair : this.activeSnapLinesY) {
            int y = ((Number)pair.component1()).intValue();
            color = ((Number)pair.component2()).intValue();
            NVGRenderer.INSTANCE.line(0.0f, y, w, y, color, 2.0f);
        }
    }

    private final void drawElement(HudElement element) {
        float ex = element.getX();
        float ey = element.getY();
        float ew = element.getWidth();
        float eh = element.getHeight();
        boolean active = element == this.draggingElement;
        NVGRenderer.rect$default(NVGRenderer.INSTANCE, ex, ey, ew, eh, Integer.MIN_VALUE, 0.0f, 32, null);
        NVGRenderer.INSTANCE.rectOutline(ex, ey, ew, eh, active ? this.colorCenter : this.colorElement, 0.0f, 1.0f);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, element.getId(), ex + 4.0f, ey + eh / 2.0f, 12.0f, -1184275, null, 32, null);
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.button() != 0) {
            return false;
        }
        int mouseX = (int)event.x();
        int mouseY = (int)event.y();
        for (HudElement element : HudManager.INSTANCE.getElements()) {
            if (!element.contains(mouseX, mouseY)) continue;
            this.draggingElement = element;
            element.startDrag(mouseX, mouseY);
            return true;
        }
        return false;
    }

    public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        int mouseX = (int)event.x();
        int mouseY = (int)event.y();
        HudElement hudElement = this.draggingElement;
        if (hudElement != null) {
            HudElement element = hudElement;
            boolean bl = false;
            element.updateDrag(mouseX, mouseY, this.scaledWidth(), this.scaledHeight());
            this.applySmartSnap(element);
            return true;
        }
        return false;
    }

    public boolean mouseReleased(@NotNull MouseButtonEvent event) {
        HudElement hudElement;
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.button() == 0 && (hudElement = this.draggingElement) != null) {
            HudElement it = hudElement;
            boolean bl = false;
            it.stopDrag();
            this.draggingElement = null;
            this.activeSnapLinesX.clear();
            this.activeSnapLinesY.clear();
            return true;
        }
        return false;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.key() == 256) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }

    private final void applySmartSnap(HudElement element) {
        this.activeSnapLinesX.clear();
        this.activeSnapLinesY.clear();
        int width = this.scaledWidth();
        int height = this.scaledHeight();
        int elementLeft = element.getX();
        int elementRight = element.getX() + element.getWidth();
        int elementCenterX = element.getX() + element.getWidth() / 2;
        int elementTop = element.getY();
        int elementBottom = element.getY() + element.getHeight();
        int elementCenterY = element.getY() + element.getHeight() / 2;
        int centerX = width / 2;
        int centerY = height / 2;
        List snapTargetsX = new ArrayList();
        snapTargetsX.add(new Triple((Object)0, (Object)0, (Object)this.colorEdge));
        snapTargetsX.add(new Triple((Object)width, (Object)(width - element.getWidth()), (Object)this.colorEdge));
        snapTargetsX.add(new Triple((Object)centerX, (Object)(centerX - element.getWidth() / 2), (Object)this.colorCenter));
        for (int gx = this.gridSize; gx < width; gx += this.gridSize) {
            if (gx == centerX) continue;
            snapTargetsX.add(new Triple((Object)gx, (Object)(gx - element.getWidth() / 2), (Object)this.colorGrid));
        }
        for (HudElement other : HudManager.INSTANCE.getElements()) {
            if (other == element) continue;
            snapTargetsX.add(new Triple((Object)other.getX(), (Object)other.getX(), (Object)this.colorElement));
            snapTargetsX.add(new Triple((Object)(other.getX() + other.getWidth()), (Object)(other.getX() + other.getWidth() - element.getWidth()), (Object)this.colorElement));
            snapTargetsX.add(new Triple((Object)(other.getX() + other.getWidth() / 2), (Object)(other.getX() + other.getWidth() / 2 - element.getWidth() / 2), (Object)this.colorElement));
            snapTargetsX.add(new Triple((Object)(other.getX() + other.getWidth()), (Object)(other.getX() + other.getWidth()), (Object)this.colorElement));
            snapTargetsX.add(new Triple((Object)other.getX(), (Object)(other.getX() - element.getWidth()), (Object)this.colorElement));
        }
        for (HudElement other : snapTargetsX) {
            int target = ((Number)other.component1()).intValue();
            int snapTo = ((Number)other.component2()).intValue();
            int color = ((Number)other.component3()).intValue();
            if (Math.abs(elementCenterX - target) < this.snapThreshold) {
                element.setX(snapTo);
                this.activeSnapLinesX.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
                break;
            }
            if (Math.abs(elementLeft - target) < this.snapThreshold) {
                element.setX(target);
                this.activeSnapLinesX.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
                break;
            }
            if (Math.abs(elementRight - target) >= this.snapThreshold) continue;
            element.setX(target - element.getWidth());
            this.activeSnapLinesX.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
            break;
        }
        List snapTargetsY = new ArrayList();
        snapTargetsY.add(new Triple((Object)0, (Object)0, (Object)this.colorEdge));
        snapTargetsY.add(new Triple((Object)height, (Object)(height - element.getHeight()), (Object)this.colorEdge));
        snapTargetsY.add(new Triple((Object)centerY, (Object)(centerY - element.getHeight() / 2), (Object)this.colorCenter));
        for (int gy = this.gridSize; gy < height; gy += this.gridSize) {
            if (gy == centerY) continue;
            snapTargetsY.add(new Triple((Object)gy, (Object)(gy - element.getHeight() / 2), (Object)this.colorGrid));
        }
        for (HudElement other : HudManager.INSTANCE.getElements()) {
            if (other == element) continue;
            snapTargetsY.add(new Triple((Object)other.getY(), (Object)other.getY(), (Object)this.colorElement));
            snapTargetsY.add(new Triple((Object)(other.getY() + other.getHeight()), (Object)(other.getY() + other.getHeight() - element.getHeight()), (Object)this.colorElement));
            snapTargetsY.add(new Triple((Object)(other.getY() + other.getHeight() / 2), (Object)(other.getY() + other.getHeight() / 2 - element.getHeight() / 2), (Object)this.colorElement));
            snapTargetsY.add(new Triple((Object)(other.getY() + other.getHeight()), (Object)(other.getY() + other.getHeight()), (Object)this.colorElement));
            snapTargetsY.add(new Triple((Object)other.getY(), (Object)(other.getY() - element.getHeight()), (Object)this.colorElement));
        }
        for (Triple triple : snapTargetsY) {
            int target = ((Number)triple.component1()).intValue();
            int snapTo = ((Number)triple.component2()).intValue();
            int color = ((Number)triple.component3()).intValue();
            if (Math.abs(elementCenterY - target) < this.snapThreshold) {
                element.setY(snapTo);
                this.activeSnapLinesY.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
                break;
            }
            if (Math.abs(elementTop - target) < this.snapThreshold) {
                element.setY(target);
                this.activeSnapLinesY.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
                break;
            }
            if (Math.abs(elementBottom - target) >= this.snapThreshold) continue;
            element.setY(target - element.getHeight());
            this.activeSnapLinesY.add((Pair<Integer, Integer>)TuplesKt.to((Object)target, (Object)color));
            break;
        }
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        HudManager.INSTANCE.savePositions();
        super.onClose();
    }
}


