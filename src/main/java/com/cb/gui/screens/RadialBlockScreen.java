/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
 *  kotlin.Result
 *  kotlin.ResultKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.gui.screens;

import com.cb.cosmetic.CosmeticBlocks;
import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0016J0\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0006H\u0002J@\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\fH\u0002J(\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\f2\u0006\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020\u0006H\u0016J\b\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020&H\u0016J\u0010\u0010+\u001a\u00020&2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020&2\u0006\u0010(\u001a\u00020-H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2={"Lcom/cb/gui/screens/RadialBlockScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "uiScale", "", "uiWidth", "uiHeight", "hoverAnim", "", "hoveredIdx", "", "favs", "", "", "twoPi", "renderOverlay", "", "delta", "drawCircleOutline", "cx", "cy", "r", "argb", "stroke", "drawWedge", "rInner", "rOuter", "a0", "a1", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "mouseX", "mouseY", "partialTick", "isPauseScreen", "", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "mouseReleased", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "cb_client"})
@SourceDebugExtension(value={"SMAP\nRadialBlockScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RadialBlockScreen.kt\ncom/cb/gui/screens/RadialBlockScreen\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,229:1\n1#2:230\n*E\n"})
public final class RadialBlockScreen
extends Screen
implements NvgScreen {
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    @NotNull
    private float[] hoverAnim = new float[0];
    private int hoveredIdx = -1;
    @NotNull
    private List<String> favs = CollectionsKt.emptyList();
    private final float twoPi = (float)Math.PI * 2;

    public RadialBlockScreen() {
        super((Component)Component.literal((String)"Block Wheel"));
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
        float mx = (float)minecraft2.mouseHandler.xpos() * fbScaleX / this.uiScale;
        float my = (float)minecraft2.mouseHandler.ypos() * fbScaleY / this.uiScale;
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            int i;
            int n;
            Object $this$renderOverlay_u24lambda_u240;
            NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, -1946157056, 0.0f, 32, null);
            float cx = this.uiWidth * 0.5f;
            float cy = this.uiHeight * 0.5f;
            float rOuter = Math.min(this.uiWidth, this.uiHeight) * 0.3f;
            float rInner = rOuter * 0.42f;
            float rMid = (rOuter + rInner) * 0.5f;
            Object object = this;
            RadialBlockScreen radialBlockScreen = this;
            try {
                $this$renderOverlay_u24lambda_u240 = object;
                boolean bl = false;
                $this$renderOverlay_u24lambda_u240 = Result.constructor-impl(CosmeticBlocks.INSTANCE.getFavorites());
            }
            catch (Throwable bl) {
                $this$renderOverlay_u24lambda_u240 = Result.constructor-impl((Object)ResultKt.createFailure((Throwable)bl));
            }
            object = $this$renderOverlay_u24lambda_u240;
            $this$renderOverlay_u24lambda_u240 = CollectionsKt.emptyList();
            radialBlockScreen.favs = (List)(Result.isFailure-impl((Object)object) ? $this$renderOverlay_u24lambda_u240 : object);
            NVGRenderer.INSTANCE.circle(cx, cy, rOuter, Theme.INSTANCE.withAlpha(-183233516, 0.92f));
            if (this.favs.isEmpty()) {
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "no favorites", cx, cy - 12.0f, 13.0f, -6513508, null, 32, null);
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "right-click any block in the selector to add", cx, cy + 10.0f, 12.0f, -10724260, null, 32, null);
                this.hoveredIdx = -1;
                return;
            }
            float dx = mx - cx;
            float dy = my - cy;
            float dist = (float)Math.sqrt(dx * dx + dy * dy);
            float mouseAngle = (float)((Math.atan2(dy, dx) + Math.PI * 2) % (Math.PI * 2));
            int n2 = this.favs.size();
            float sliceAngle = this.twoPi / (float)n2;
            if (this.hoverAnim.length != n2) {
                this.hoverAnim = new float[n2];
            }
            boolean bl = rInner <= dist ? dist <= rOuter + 20.0f : false;
            if (bl) {
                float shifted = (mouseAngle + sliceAngle * 0.5f) % this.twoPi;
                n = (int)(shifted / sliceAngle) % n2;
            } else {
                n = -1;
            }
            this.hoveredIdx = n;
            for (i = 0; i < n2; ++i) {
                float target = i == this.hoveredIdx ? 1.0f : 0.0f;
                float[] fArray = this.hoverAnim;
                int n3 = i;
                fArray[n3] = fArray[n3] + (target - this.hoverAnim[i]) * RangesKt.coerceIn((float)(delta * 12.0f), (float)0.0f, (float)1.0f);
            }
            this.drawCircleOutline(cx, cy, rOuter, Theme.INSTANCE.withAlpha(-1750963, 0.9f), 2.0f);
            this.drawCircleOutline(cx, cy, rInner, Theme.INSTANCE.withAlpha(-1750963, 0.4f), 1.5f);
            for (i = 0; i < n2; ++i) {
                float a0 = (float)i * sliceAngle - sliceAngle * 0.5f;
                float a1 = a0 + sliceAngle;
                float t = this.hoverAnim[i];
                float rOuterE = rOuter + t * 8.0f;
                if (t > 0.02f) {
                    this.drawWedge(cx, cy, rInner, rOuterE, a0, a1, Theme.INSTANCE.withAlpha(-1750963, 0.18f + 0.35f * t));
                }
                float sx1 = cx + rInner * (float)Math.cos(a0);
                float sy1 = cy + rInner * (float)Math.sin(a0);
                float sx2 = cx + rOuterE * (float)Math.cos(a0);
                float sy2 = cy + rOuterE * (float)Math.sin(a0);
                NVGRenderer.INSTANCE.line(sx1, sy1, sx2, sy2, Theme.INSTANCE.withAlpha(-1750963, 0.3f), 1.0f);
                float midA = a0 + sliceAngle * 0.5f;
                float lx = cx + rMid * (float)Math.cos(midA);
                float ly = cy + rMid * (float)Math.sin(midA);
                String name = StringsKt.removePrefix((String)this.favs.get(i), (CharSequence)"minecraft:");
                String string = name.length() > 12 ? StringsKt.take((String)name, (int)11) + "..." : name;
                int textCol = i == this.hoveredIdx ? -1184275 : -6513508;
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, string, lx, ly, 12.0f, textCol, null, 32, null);
            }
            NVGRenderer.INSTANCE.circle(cx, cy, rInner - 4.0f, Theme.INSTANCE.withAlpha(-14935012, 0.95f));
            this.drawCircleOutline(cx, cy, rInner - 4.0f, Theme.INSTANCE.withAlpha(-1750963, 0.6f), 1.5f);
            if (this.hoveredIdx >= 0) {
                String fullName = StringsKt.replace$default((String)StringsKt.removePrefix((String)this.favs.get(this.hoveredIdx), (CharSequence)"minecraft:"), (char)'_', (char)' ', (boolean)false, (int)4, null);
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "SELECT", cx, cy - 10.0f, 12.0f, -1750963, null, 32, null);
                NVGRenderer.INSTANCE.textCentered(fullName, cx, cy + 8.0f, 13.0f, -1184275, NVGRenderer.Font.BOLD);
            } else {
                String current = StringsKt.replace$default((String)StringsKt.removePrefix((String)CosmeticBlocks.INSTANCE.getSelectedBlock(), (CharSequence)"minecraft:"), (char)'_', (char)' ', (boolean)false, (int)4, null);
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "CURRENT", cx, cy - 10.0f, 12.0f, -6513508, null, 32, null);
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, current, cx, cy + 8.0f, 13.0f, -1184275, null, 32, null);
            }
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "LMB pick   |   RMB / Esc close", cx, this.uiHeight - 48.0f, 12.0f, -10724260, null, 32, null);
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    private final void drawCircleOutline(float cx, float cy, float r, int argb, float stroke) {
        int segments = 96;
        float prevX = cx + r;
        float prevY = cy;
        int i = 1;
        while (true) {
            float a = this.twoPi * (float)i / (float)segments;
            float nx = cx + r * (float)Math.cos(a);
            float ny = cy + r * (float)Math.sin(a);
            NVGRenderer.INSTANCE.line(prevX, prevY, nx, ny, argb, stroke);
            prevX = nx;
            prevY = ny;
            if (i == segments) break;
            ++i;
        }
    }

    private final void drawWedge(float cx, float cy, float rInner, float rOuter, float a0, float a1, int argb) {
        float span = a1 - a0;
        int steps = RangesKt.coerceIn((int)((int)(rOuter * span / 2.0f)), (int)8, (int)96);
        float stroke = rOuter * span / (float)steps + 1.5f;
        int s = 0;
        if (s <= steps) {
            while (true) {
                float a = a0 + span * (float)s / (float)steps;
                float x1 = cx + rInner * (float)Math.cos(a);
                float y1 = cy + rInner * (float)Math.sin(a);
                float x2 = cx + rOuter * (float)Math.cos(a);
                float y2 = cy + rOuter * (float)Math.sin(a);
                NVGRenderer.INSTANCE.line(x1, y1, x2, y2, argb, stroke);
                if (s == steps) break;
                ++s;
            }
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
        switch (event.button()) {
            case 0: {
                int n = ((Collection)this.favs).size();
                int n2 = this.hoveredIdx;
                boolean bl = 0 <= n2 ? n2 < n : false;
                if (bl) {
                    CosmeticBlocks.INSTANCE.setBlock(this.favs.get(this.hoveredIdx));
                    this.onClose();
                }
                return true;
            }
            case 1: {
                this.onClose();
                return true;
            }
        }
        return true;
    }

    public boolean mouseReleased(@NotNull MouseButtonEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.button() == 0) {
            int n = ((Collection)this.favs).size();
            int n2 = this.hoveredIdx;
            boolean bl = 0 <= n2 ? n2 < n : false;
            if (bl) {
                CosmeticBlocks.INSTANCE.setBlock(this.favs.get(this.hoveredIdx));
                this.onClose();
            }
        }
        return true;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        if (event.key() == 256) {
            this.onClose();
            return true;
        }
        return super.keyPressed(event);
    }
}


