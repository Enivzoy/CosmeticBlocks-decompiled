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
 *  org.lwjgl.glfw.GLFW
 */
package com.cb.gui.screens;

import com.cb.gui.NvgScreen;
import com.cb.gui.screens.CliEngine;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.lwjgl.glfw.GLFW;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0006H\u0016J(\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u0006H\u0002J \u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J \u0010$\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\u0002J\u0018\u0010%\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u00062\u0006\u0010#\u001a\u00020\u0006H\u0002J(\u0010&\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\f2\u0006\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020\u0006H\u0016J\b\u0010,\u001a\u00020-H\u0016J\u0018\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020-H\u0016J\u0010\u00102\u001a\u00020-2\u0006\u0010/\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020-2\u0006\u0010/\u001a\u000205H\u0016J\b\u00106\u001a\u00020\u001aH\u0002J\u0010\u00107\u001a\u00020\u001a2\u0006\u00108\u001a\u00020\u000fH\u0002J\b\u00109\u001a\u00020\u001aH\u0002J\u0010\u0010:\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020\nH\u0002J\u0010\u0010<\u001a\u00020\u00062\u0006\u0010=\u001a\u00020>H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006?"}, d2={"Lcom/cb/gui/screens/CliScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "uiScale", "", "uiWidth", "uiHeight", "input", "", "caret", "", "suggestions", "", "Lcom/cb/gui/screens/CliEngine$Suggestion;", "suggestionIdx", "historyIdx", "lastResult", "lastResultUntil", "", "caretBlink", "barWidth", "getBarWidth", "()F", "renderOverlay", "", "delta", "renderBar", "x", "y", "w", "h", "renderSuggestions", "barX", "barY", "renderFeedback", "renderHistoryHint", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "mouseX", "mouseY", "partialTick", "isPauseScreen", "", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "onInputChanged", "applySuggestion", "s", "cycleHistory", "executeAndRecord", "cmd", "toUi", "scaled", "", "cb_client"})
@SourceDebugExtension(value={"SMAP\nCliScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CliScreen.kt\ncom/cb/gui/screens/CliScreen\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,298:1\n1088#2,2:299\n*S KotlinDebug\n*F\n+ 1 CliScreen.kt\ncom/cb/gui/screens/CliScreen\n*L\n251#1:299,2\n*E\n"})
public final class CliScreen
extends Screen
implements NvgScreen {
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    @NotNull
    private String input = "";
    private int caret;
    @NotNull
    private List<CliEngine.Suggestion> suggestions = CollectionsKt.emptyList();
    private int suggestionIdx;
    private int historyIdx = -1;
    @NotNull
    private String lastResult = "";
    private long lastResultUntil;
    private float caretBlink;

    public CliScreen() {
        super((Component)Component.literal((String)"CB Command Line"));
    }

    private final float getBarWidth() {
        return RangesKt.coerceIn((float)(this.uiWidth * 0.6f), (float)280.0f, (float)620.0f);
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
        this.caretBlink += delta;
        if (!NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            return;
        }
        try {
            NVGRenderer.rect$default(NVGRenderer.INSTANCE, 0.0f, 0.0f, this.uiWidth, this.uiHeight, 0x59000000, 0.0f, 32, null);
            float barH = 30.0f;
            float barX = (this.uiWidth - this.getBarWidth()) / 2.0f;
            float barY = this.uiHeight * 0.62f;
            this.suggestions = CliEngine.INSTANCE.suggestions(this.input);
            if (this.suggestionIdx >= this.suggestions.size()) {
                this.suggestionIdx = 0;
            }
            this.renderSuggestions(barX, barY, this.getBarWidth());
            this.renderBar(barX, barY, this.getBarWidth(), barH);
            this.renderFeedback(barX, barY + barH + 6.0f, this.getBarWidth());
            this.renderHistoryHint(barX, barY);
        }
        finally {
            NVGRenderer.INSTANCE.endFrame();
        }
    }

    private final void renderBar(float x, float y, float w, float h) {
        NVGRenderer.INSTANCE.shadow(x, y, w, h, 10.0f, 12.0f, Integer.MIN_VALUE);
        NVGRenderer.INSTANCE.rect(x, y, w, h, -183233516, 10.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, x, y, w, h, Theme.INSTANCE.withAlpha(-1750963, 0.55f), 10.0f, 0.0f, 64, null);
        float padX = 12.0f;
        float cy = y + h / 2.0f;
        NVGRenderer.INSTANCE.text(">", x + padX, cy, 13.0f, -1750963, NVGRenderer.Font.BOLD);
        float textX = x + padX + 16.0f;
        float f = ((CharSequence)this.input).length() == 0 ? NVGRenderer.text$default(NVGRenderer.INSTANCE, "type a command, Tab to complete, Enter to run", textX, cy, 13.0f, -10724260, null, 32, null) : NVGRenderer.text$default(NVGRenderer.INSTANCE, this.input, textX, cy, 13.0f, -1184275, null, 32, null);
        if ((int)(this.caretBlink * 2.0f) % 2 == 0) {
            String before = StringsKt.take((String)this.input, (int)RangesKt.coerceIn((int)this.caret, (int)0, (int)this.input.length()));
            float caretX = textX + NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, before, 13.0f, null, 4, null);
            NVGRenderer.rect$default(NVGRenderer.INSTANCE, caretX, cy - 6.5f, 1.0f, 13.0f, -1184275, 0.0f, 32, null);
        }
    }

    private final void renderSuggestions(float barX, float barY, float w) {
        if (this.suggestions.isEmpty()) {
            return;
        }
        float rowH = 20.0f;
        float popH = (float)this.suggestions.size() * rowH + 8.0f;
        float x = barX;
        float y = barY - popH - 6.0f;
        NVGRenderer.INSTANCE.shadow(x, y, w, popH, 8.0f, 12.0f, Integer.MIN_VALUE);
        NVGRenderer.INSTANCE.rect(x, y, w, popH, -14935012, 8.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, x, y, w, popH, Theme.INSTANCE.withAlpha(-1750963, 0.3f), 8.0f, 0.0f, 64, null);
        float ry = y + 4.0f;
        Iterator iterator = ((Iterable)this.suggestions).iterator();
        int n = 0;
        while (iterator.hasNext()) {
            boolean highlight;
            int i = n++;
            CliEngine.Suggestion s = (CliEngine.Suggestion)iterator.next();
            boolean bl = highlight = i == this.suggestionIdx;
            if (highlight) {
                NVGRenderer.INSTANCE.rect(x + 3.0f, ry, w - 6.0f, rowH, Theme.INSTANCE.withAlpha(-1750963, 0.22f), 4.0f);
            }
            int labelColor = highlight ? -1750963 : -1184275;
            NVGRenderer.text$default(NVGRenderer.INSTANCE, s.getDisplay(), x + 12.0f, ry + rowH / 2.0f, 12.0f, labelColor, null, 32, null);
            if (((CharSequence)s.getHint()).length() > 0) {
                NVGRenderer.INSTANCE.textRight(s.getHint(), x + w - 12.0f, ry + rowH / 2.0f, 12.0f, -6513508, NVGRenderer.Font.REGULAR);
            }
            ry += rowH;
        }
    }

    private final void renderFeedback(float x, float y, float w) {
        long now = System.currentTimeMillis();
        if (now >= this.lastResultUntil || ((CharSequence)this.lastResult).length() == 0) {
            return;
        }
        float alpha = RangesKt.coerceIn((float)((float)(this.lastResultUntil - now) / 3500.0f), (float)0.0f, (float)1.0f);
        NVGRenderer.text$default(NVGRenderer.INSTANCE, this.lastResult, x + 4.0f, y + 8.0f, 12.0f, Theme.INSTANCE.withAlpha(-6513508, alpha), null, 32, null);
    }

    private final void renderHistoryHint(float barX, float barY) {
        if (((CharSequence)this.input).length() > 0) {
            return;
        }
        List<String> hist = CliEngine.INSTANCE.history();
        if (hist.isEmpty()) {
            return;
        }
        List recent = CollectionsKt.reversed((Iterable)CollectionsKt.takeLast(hist, (int)3));
        float ry = barY + 36.0f;
        for (String h : recent) {
            NVGRenderer.text$default(NVGRenderer.INSTANCE, "\u00b7 " + h, barX + 4.0f, ry, 12.0f, -10724260, null, 32, null);
            ry += 14.0f;
        }
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    /*
     * Unable to fully structure code
     */
    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        barH = 30.0f;
        barX = (this.uiWidth - this.getBarWidth()) / 2.0f;
        barY = this.uiHeight * 0.62f;
        popH = this.suggestions.isEmpty() != false ? 0.0f : (float)this.suggestions.size() * 20.0f + 8.0f;
        mx = this.toUi(event.x());
        my = this.toUi(event.y());
        v0 = barX <= mx ? mx <= barX + this.getBarWidth() : false;
        if (!v0) ** GOTO lbl-1000
        v1 = barY <= my ? my <= barY + barH : false;
        if (v1) {
            v2 = true;
        } else lbl-1000:
        // 2 sources

        {
            v2 = insideBar = false;
        }
        v3 = barX <= mx ? mx <= barX + this.getBarWidth() : false;
        if (!v3) ** GOTO lbl-1000
        v4 = barY - popH - 6.0f <= my ? my <= barY : false;
        if (v4) {
            v5 = true;
        } else lbl-1000:
        // 2 sources

        {
            v5 = insidePop = false;
        }
        if (!insideBar && !insidePop) {
            this.onClose();
            return true;
        }
        if (insidePop && ((Collection)this.suggestions).isEmpty() == false) {
            popY = barY - popH - 6.0f + 4.0f;
            rowH = 20.0f;
            idx = (int)((my - popY) / rowH);
            v6 = 0 <= idx ? idx < ((Collection)this.suggestions).size() : false;
            if (v6) {
                this.applySuggestion(this.suggestions.get(idx));
            }
        }
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
                String cmd22 = ((Object)StringsKt.trim((CharSequence)this.input)).toString();
                if (((CharSequence)cmd22).length() > 0) {
                    this.executeAndRecord(cmd22);
                }
                return true;
            }
            case 258: {
                if (!((Collection)this.suggestions).isEmpty()) {
                    this.applySuggestion(this.suggestions.get(RangesKt.coerceIn((int)this.suggestionIdx, (int)0, (int)(this.suggestions.size() - 1))));
                }
                return true;
            }
            case 264: {
                if (!((Collection)this.suggestions).isEmpty()) {
                    this.suggestionIdx = RangesKt.coerceAtMost((int)(this.suggestionIdx + 1), (int)(this.suggestions.size() - 1));
                }
                return true;
            }
            case 265: {
                if (!((Collection)this.suggestions).isEmpty() && this.suggestionIdx > 0) {
                    --this.suggestionIdx;
                } else {
                    this.cycleHistory();
                }
                return true;
            }
            case 259: {
                if (this.caret > 0 && ((CharSequence)this.input).length() > 0) {
                    String cmd22 = this.input;
                    int n = this.caret - 1;
                    int n2 = this.caret;
                    this.input = ((Object)StringsKt.removeRange((CharSequence)cmd22, (int)n, (int)n2)).toString();
                    int cmd22 = this.caret;
                    this.caret = cmd22 + -1;
                    this.onInputChanged();
                }
                return true;
            }
            case 261: {
                if (this.caret < this.input.length()) {
                    String cmd22 = this.input;
                    int n = this.caret;
                    int n3 = this.caret + 1;
                    this.input = ((Object)StringsKt.removeRange((CharSequence)cmd22, (int)n, (int)n3)).toString();
                    this.onInputChanged();
                }
                return true;
            }
            case 263: {
                this.caret = RangesKt.coerceAtLeast((int)(this.caret - 1), (int)0);
                return true;
            }
            case 262: {
                this.caret = RangesKt.coerceAtMost((int)(this.caret + 1), (int)this.input.length());
                return true;
            }
            case 268: {
                this.caret = 0;
                return true;
            }
            case 269: {
                this.caret = this.input.length();
                return true;
            }
            case 86: {
                if ((event.modifiers() & 2) == 0) break;
                String clip = GLFW.glfwGetClipboardString((long)GLFW.glfwGetCurrentContext());
                if (clip != null) {
                    String string = this.input.substring(0, this.caret);
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"substring(...)");
                    String string2 = this.input.substring(this.caret);
                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
                    this.input = string + clip + string2;
                    this.caret += clip.length();
                    this.onInputChanged();
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
                String string2 = this.input.substring(0, this.caret);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"substring(...)");
                String string3 = this.input.substring(this.caret);
                Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"substring(...)");
                this.input = string2 + s + string3;
                this.caret += s.length();
                this.onInputChanged();
                return true;
            }
        }
        return false;
    }

    private final void onInputChanged() {
        this.suggestionIdx = 0;
        this.historyIdx = -1;
    }

    private final void applySuggestion(CliEngine.Suggestion s) {
        boolean needsSpace = !StringsKt.endsWith$default((CharSequence)s.getInsert(), (char)' ', (boolean)false, (int)2, null);
        this.input = needsSpace ? s.getInsert() + " " : s.getInsert();
        this.caret = this.input.length();
        this.suggestionIdx = 0;
    }

    private final void cycleHistory() {
        List<String> hist = CliEngine.INSTANCE.history();
        if (hist.isEmpty()) {
            return;
        }
        this.historyIdx = this.historyIdx < 0 ? hist.size() - 1 : RangesKt.coerceAtLeast((int)(this.historyIdx - 1), (int)0);
        this.input = hist.get(this.historyIdx);
        this.caret = this.input.length();
    }

    private final void executeAndRecord(String cmd) {
        this.lastResult = CliEngine.INSTANCE.execute(cmd);
        this.lastResultUntil = System.currentTimeMillis() + (long)3500;
        this.input = "";
        this.caret = 0;
        this.historyIdx = -1;
        this.suggestionIdx = 0;
    }

    private final float toUi(double scaled) {
        Window window = Minecraft.getInstance().getWindow();
        Intrinsics.checkNotNullExpressionValue((Object)window, (String)"getWindow(...)");
        Window window2 = window;
        return (float)(scaled * (double)window2.getGuiScale() / (double)this.uiScale);
    }
}


