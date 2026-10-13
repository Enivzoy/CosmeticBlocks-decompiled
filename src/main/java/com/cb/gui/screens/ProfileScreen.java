/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Window
 *  kotlin.Metadata
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

import com.cb.gui.ClickGuiScreen;
import com.cb.gui.NvgScreen;
import com.cb.gui.ProfileManager;
import com.cb.render.Anim;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
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

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0003\u0010\u0004J\b\u0010'\u001a\u00020(H\u0002J\u0010\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020\u0006H\u0016J \u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J@\u0010.\u001a\u00020(2\u0006\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u00062\u0006\u0010,\u001a\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0002J\u0010\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u000205H\u0002J\u0018\u00106\u001a\u00020\u00192\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u0019H\u0016J\b\u0010:\u001a\u00020(H\u0002J(\u0010;\u001a\u00020\u00192\u0006\u0010,\u001a\u0002052\u0006\u0010-\u001a\u0002052\u0006\u0010<\u001a\u0002052\u0006\u0010=\u001a\u000205H\u0016J\u0010\u0010>\u001a\u00020\u00192\u0006\u00107\u001a\u00020?H\u0016J\u0010\u0010@\u001a\u00020\u00192\u0006\u00107\u001a\u00020AH\u0016J\u0010\u0010B\u001a\u00020(2\u0006\u0010C\u001a\u00020\u0017H\u0002J8\u0010D\u001a\u00020\u00192\u0006\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\u0006\u00102\u001a\u00020\u00062\u0006\u0010G\u001a\u00020\u0006H\u0002J \u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020I2\u0006\u0010K\u001a\u00020I2\u0006\u0010L\u001a\u00020\u0006H\u0002J(\u0010M\u001a\u00020(2\u0006\u0010N\u001a\u00020O2\u0006\u0010,\u001a\u00020I2\u0006\u0010-\u001a\u00020I2\u0006\u0010P\u001a\u00020\u0006H\u0016J\b\u0010Q\u001a\u00020\u0019H\u0016J\b\u0010R\u001a\u00020(H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\"\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020!0#j\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020!`$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006S"}, d2={"Lcom/cb/gui/screens/ProfileScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cb/gui/NvgScreen;", "<init>", "()V", "uiScale", "", "uiWidth", "uiHeight", "panelWidth", "panelHeight", "panelX", "panelY", "headerHeight", "saveRowHeight", "cardHeight", "cardGap", "profiles", "", "Lcom/cb/gui/ProfileManager$ProfileInfo;", "scroll", "maxScroll", "nameInput", "", "nameFocused", "", "caretBlink", "pendingDelete", "pendingDeleteUntil", "", "toast", "toastUntil", "saveBtn", "Lcom/cb/render/Anim;", "cardHover", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "listClipTop", "listClipBottom", "refresh", "", "renderOverlay", "delta", "draw", "mouseX", "mouseY", "drawCard", "p", "x", "y", "w", "toUi", "scaled", "", "mouseClicked", "event", "Lnet/minecraft/client/input/MouseButtonEvent;", "doubled", "doSave", "mouseScrolled", "scrollX", "scrollY", "keyPressed", "Lnet/minecraft/client/input/KeyEvent;", "charTyped", "Lnet/minecraft/client/input/CharacterEvent;", "showToast", "msg", "inside", "mx", "my", "h", "lerp", "", "a", "b", "t", "extractBackground", "extractor", "Lnet/minecraft/client/gui/GuiGraphicsExtractor;", "partialTick", "isPauseScreen", "onClose", "cb_client"})
@SourceDebugExtension(value={"SMAP\nProfileScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileScreen.kt\ncom/cb/gui/screens/ProfileScreen\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,394:1\n445#2,8:395\n410#3,7:403\n*S KotlinDebug\n*F\n+ 1 ProfileScreen.kt\ncom/cb/gui/screens/ProfileScreen\n*L\n163#1:395,8\n197#1:403,7\n*E\n"})
public final class ProfileScreen
extends Screen
implements NvgScreen {
    private float uiScale = 1.0f;
    private float uiWidth;
    private float uiHeight;
    private final float panelWidth;
    private final float panelHeight;
    private float panelX;
    private float panelY;
    private final float headerHeight;
    private final float saveRowHeight;
    private final float cardHeight;
    private final float cardGap;
    @NotNull
    private List<ProfileManager.ProfileInfo> profiles = ProfileManager.INSTANCE.listProfiles();
    private float scroll;
    private float maxScroll;
    @NotNull
    private String nameInput = "";
    private boolean nameFocused;
    private float caretBlink;
    @Nullable
    private String pendingDelete;
    private long pendingDeleteUntil;
    @NotNull
    private String toast = "";
    private long toastUntil;
    @NotNull
    private final Anim saveBtn = new Anim(0.0f, 0.0f, 2, null);
    @NotNull
    private final HashMap<String, Anim> cardHover = new HashMap();
    private float listClipTop;
    private float listClipBottom;

    public ProfileScreen() {
        super((Component)Component.literal((String)"Profiles"));
        this.panelWidth = 460.0f;
        this.panelHeight = 380.0f;
        this.headerHeight = 44.0f;
        this.saveRowHeight = 36.0f;
        this.cardHeight = 58.0f;
        this.cardGap = 8.0f;
    }

    private final void refresh() {
        this.profiles = ProfileManager.INSTANCE.listProfiles();
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
        long now;
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
        NVGRenderer.INSTANCE.text("Profiles", this.panelX + 10.0f + 4.0f, this.panelY + this.headerHeight / 2.0f, 14.0f, -1184275, NVGRenderer.Font.BOLD);
        float closeSize = 18.0f;
        float closeX = this.panelX + this.panelWidth - closeSize - 10.0f;
        float closeY = this.panelY + (this.headerHeight - closeSize) / 2.0f;
        boolean closeHover = this.inside(mouseX, mouseY, closeX, closeY, closeSize, closeSize);
        NVGRenderer.INSTANCE.textCentered("x", closeX + closeSize / 2.0f, closeY + closeSize / 2.0f, 14.0f, closeHover ? -1750963 : -6513508, NVGRenderer.Font.BOLD);
        String countText = this.profiles.size() + " profile" + (this.profiles.size() == 1 ? "" : "s");
        NVGRenderer.textRight$default(NVGRenderer.INSTANCE, countText, this.panelX + this.panelWidth - 10.0f - 4.0f, this.panelY + this.headerHeight + this.saveRowHeight / 2.0f, 12.0f, -10724260, null, 32, null);
        float saveRowY = this.panelY + this.headerHeight;
        float fieldX = this.panelX + 10.0f;
        float fieldY = saveRowY + (this.saveRowHeight - 24.0f) / 2.0f;
        float fieldWidth = 240.0f;
        float fieldHeight = 24.0f;
        NVGRenderer.INSTANCE.rect(fieldX, fieldY, fieldWidth, fieldHeight, 0x66000000, 5.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, fieldX, fieldY, fieldWidth, fieldHeight, this.nameFocused ? -1750963 : 0x14FFFFFF, 5.0f, 0.0f, 64, null);
        boolean showCaret = this.nameFocused && this.caretBlink % 1.0f < 0.5f;
        String display = ((CharSequence)this.nameInput).length() == 0 && !this.nameFocused ? "new profile name\u2026" : this.nameInput + (showCaret ? "|" : "");
        int textColor = ((CharSequence)this.nameInput).length() == 0 && !this.nameFocused ? -10724260 : -1184275;
        NVGRenderer.text$default(NVGRenderer.INSTANCE, display, fieldX + 8.0f, fieldY + fieldHeight / 2.0f, 13.0f, textColor, null, 32, null);
        float saveW = 70.0f;
        float saveX = fieldX + fieldWidth + 8.0f;
        boolean saveHover = this.inside(mouseX, mouseY, saveX, fieldY, saveW, fieldHeight);
        this.saveBtn.setTarget(saveHover ? 1.0f : 0.0f);
        this.saveBtn.update(delta);
        int saveBg = this.lerp(1088768077, -1750963, this.saveBtn.getValue());
        NVGRenderer.INSTANCE.rect(saveX, fieldY, saveW, fieldHeight, saveBg, 5.0f);
        NVGRenderer.INSTANCE.textCentered("Save", saveX + saveW / 2.0f, fieldY + fieldHeight / 2.0f, 13.0f, -1184275, NVGRenderer.Font.MEDIUM);
        float listTop = saveRowY + this.saveRowHeight + 4.0f;
        float listBottom = this.panelY + this.panelHeight - 10.0f;
        this.listClipTop = listTop;
        this.listClipBottom = listBottom;
        float listX = this.panelX + 10.0f;
        float listWidth = this.panelWidth - 20.0f;
        float listHeight = listBottom - listTop;
        float contentHeight = this.profiles.isEmpty() ? 0.0f : (float)this.profiles.size() * (this.cardHeight + this.cardGap);
        this.maxScroll = RangesKt.coerceAtLeast((float)(contentHeight - listHeight), (float)0.0f);
        this.scroll = RangesKt.coerceIn((float)this.scroll, (float)0.0f, (float)this.maxScroll);
        NVGRenderer nVGRenderer = NVGRenderer.INSTANCE;
        float f = listX;
        float f2 = listTop;
        float f3 = listWidth;
        float h$iv = listHeight;
        boolean $i$f$scissor = false;
        this_$iv.save();
        this_$iv.intersectScissor((float)x$iv, (float)y$iv, (float)w$iv, h$iv);
        try {
            boolean bl = false;
            if (this.profiles.isEmpty()) {
                NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "No saved profiles yet.", this.panelX + this.panelWidth / 2.0f, listTop + 30.0f, 13.0f, -10724260, null, 32, null);
            } else {
                float cardY = listTop - this.scroll;
                for (ProfileManager.ProfileInfo p : this.profiles) {
                    if (cardY + this.cardHeight >= listTop && cardY <= listBottom) {
                        this.drawCard(p, listX, cardY, listWidth, mouseX, mouseY, delta);
                    }
                    cardY += this.cardHeight + this.cardGap;
                }
            }
        }
        finally {
            this_$iv.restore();
        }
        if ((now = System.currentTimeMillis()) < this.toastUntil && ((CharSequence)this.toast).length() > 0) {
            float alpha = RangesKt.coerceIn((float)((float)(this.toastUntil - now) / 1800.0f), (float)0.0f, (float)1.0f);
            float tw = NVGRenderer.textWidth$default(NVGRenderer.INSTANCE, this.toast, 13.0f, null, 4, null);
            float boxW = tw + 28.0f;
            float boxH = 26.0f;
            float tx = this.panelX + (this.panelWidth - boxW) / 2.0f;
            float ty = this.panelY + this.panelHeight - boxH - 12.0f;
            NVGRenderer.INSTANCE.save();
            NVGRenderer.INSTANCE.globalAlpha(alpha);
            NVGRenderer.INSTANCE.rect(tx, ty, boxW, boxH, -14935012, 6.0f);
            NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, tx, ty, boxW, boxH, -1750963, 6.0f, 0.0f, 64, null);
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, this.toast, tx + boxW / 2.0f, ty + boxH / 2.0f, 13.0f, -1184275, null, 32, null);
            NVGRenderer.INSTANCE.restore();
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void drawCard(ProfileManager.ProfileInfo p, float x, float y, float w, float mouseX, float mouseY, float delta) {
        boolean delHover;
        Object object;
        void $this$getOrPut$iv;
        boolean hovered = this.inside(mouseX, mouseY, x, y, w, this.cardHeight) && mouseY >= this.listClipTop && mouseY <= this.listClipBottom;
        Map map = this.cardHover;
        String key$iv = p.getName();
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
        int bg = this.lerp(-14935012, -1, anim.getValue() * 0.4f);
        NVGRenderer.INSTANCE.rect(x, y, w, this.cardHeight, bg, 8.0f);
        NVGRenderer.rectOutline$default(NVGRenderer.INSTANCE, x, y, w, this.cardHeight, 0x14FFFFFF, 8.0f, 0.0f, 64, null);
        NVGRenderer.INSTANCE.rectVarying(x, y, 3.0f, this.cardHeight, 8.0f, 0.0f, 0.0f, 8.0f, -1750963);
        NVGRenderer.INSTANCE.text(p.getName(), x + 14.0f, y + 18.0f, 14.0f, -1184275, NVGRenderer.Font.MEDIUM);
        String sub = p.getSettingCount() + " settings  \u00b7  " + ProfileManager.INSTANCE.formatTimestamp(p.getLastModified());
        NVGRenderer.text$default(NVGRenderer.INSTANCE, sub, x + 14.0f, y + 40.0f, 12.0f, -10724260, null, 32, null);
        float btnW = 76.0f;
        float btnH = 26.0f;
        float btnY = y + (this.cardHeight - btnH) / 2.0f;
        float deleteX = x + w - btnW - 10.0f;
        float loadX = deleteX - btnW - 8.0f;
        boolean loadHover = this.inside(mouseX, mouseY, loadX, btnY, btnW, btnH) && hovered;
        NVGRenderer.INSTANCE.rect(loadX, btnY, btnW, btnH, loadHover ? -1750963 : 1088768077, 6.0f);
        NVGRenderer.INSTANCE.textCentered("Load", loadX + btnW / 2.0f, btnY + btnH / 2.0f, 13.0f, -1184275, NVGRenderer.Font.MEDIUM);
        boolean isPending = Intrinsics.areEqual((Object)this.pendingDelete, (Object)p.getName()) && System.currentTimeMillis() < this.pendingDeleteUntil;
        boolean bl = delHover = this.inside(mouseX, mouseY, deleteX, btnY, btnW, btnH) && hovered;
        if (isPending) {
            NVGRenderer.INSTANCE.rect(deleteX, btnY, btnW, btnH, delHover ? -1750963 : -5096899, 6.0f);
            NVGRenderer.INSTANCE.textCentered("Confirm?", deleteX + btnW / 2.0f, btnY + btnH / 2.0f, 12.0f, -1184275, NVGRenderer.Font.MEDIUM);
        } else {
            NVGRenderer.INSTANCE.rect(deleteX, btnY, btnW, btnH, delHover ? -14013910 : 0x66000000, 6.0f);
            NVGRenderer.textCentered$default(NVGRenderer.INSTANCE, "Delete", deleteX + btnW / 2.0f, btnY + btnH / 2.0f, 13.0f, delHover ? -1750963 : -6513508, null, 32, null);
        }
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
        float saveRowY = this.panelY + this.headerHeight;
        float fieldX = this.panelX + 10.0f;
        float fieldY = saveRowY + (this.saveRowHeight - 24.0f) / 2.0f;
        float fieldWidth = 240.0f;
        float fieldHeight = 24.0f;
        this.nameFocused = this.inside(mx, my, fieldX, fieldY, fieldWidth, fieldHeight);
        float saveX = fieldX + fieldWidth + 8.0f;
        float saveW = 70.0f;
        if (this.inside(mx, my, saveX, fieldY, saveW, fieldHeight)) {
            this.doSave();
            return true;
        }
        float listTop = saveRowY + this.saveRowHeight + 4.0f;
        if (my >= this.listClipTop && my <= this.listClipBottom) {
            float listX = this.panelX + 10.0f;
            float listWidth = this.panelWidth - 20.0f;
            float cardY = listTop - this.scroll;
            for (ProfileManager.ProfileInfo p : this.profiles) {
                if (this.inside(mx, my, listX, cardY, listWidth, this.cardHeight)) {
                    float btnW = 76.0f;
                    float deleteX = listX + listWidth - btnW - 10.0f;
                    float loadX = deleteX - btnW - 8.0f;
                    float btnH = 26.0f;
                    float btnY = cardY + (this.cardHeight - btnH) / 2.0f;
                    if (this.inside(mx, my, loadX, btnY, btnW, btnH)) {
                        boolean ok = ProfileManager.INSTANCE.loadProfile(p.getName());
                        this.showToast((String)(ok ? "Loaded \"" + p.getName() + "\"" : "Load failed"));
                        return true;
                    }
                    if (this.inside(mx, my, deleteX, btnY, btnW, btnH)) {
                        boolean isPending;
                        boolean bl = isPending = Intrinsics.areEqual((Object)this.pendingDelete, (Object)p.getName()) && System.currentTimeMillis() < this.pendingDeleteUntil;
                        if (isPending) {
                            boolean ok = ProfileManager.INSTANCE.deleteProfile(p.getName());
                            this.showToast((String)(ok ? "Deleted \"" + p.getName() + "\"" : "Delete failed"));
                            this.pendingDelete = null;
                            this.refresh();
                        } else {
                            this.pendingDelete = p.getName();
                            this.pendingDeleteUntil = System.currentTimeMillis() + (long)3000;
                        }
                        return true;
                    }
                    return true;
                }
                cardY += this.cardHeight + this.cardGap;
            }
        }
        return false;
    }

    private final void doSave() {
        String n = ((Object)StringsKt.trim((CharSequence)this.nameInput)).toString();
        if (((CharSequence)n).length() == 0) {
            this.showToast("Enter a name first");
            return;
        }
        boolean ok = ProfileManager.INSTANCE.saveProfile(n);
        this.showToast((String)(ok ? "Saved \"" + n + "\"" : "Save failed"));
        if (ok) {
            this.nameInput = "";
            this.nameFocused = false;
            this.refresh();
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scroll = RangesKt.coerceIn((float)(this.scroll - (float)scrollY * 28.0f), (float)0.0f, (float)this.maxScroll);
        return true;
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        int key = event.key();
        if (this.nameFocused) {
            switch (key) {
                case 259: {
                    if (((CharSequence)this.nameInput).length() > 0) {
                        this.nameInput = StringsKt.dropLast((String)this.nameInput, (int)1);
                    }
                    return true;
                }
                case 257: 
                case 335: {
                    this.doSave();
                    return true;
                }
                case 256: {
                    this.nameFocused = false;
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
        if (!this.nameFocused) {
            return false;
        }
        String string = event.codepointAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"codepointAsString(...)");
        String s = string;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            char ch = s.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_' && ch != '-' && ch != ' ' || this.nameInput.length() >= 32) continue;
            this.nameInput = this.nameInput + ch;
        }
        return true;
    }

    private final void showToast(String msg) {
        this.toast = msg;
        this.toastUntil = System.currentTimeMillis() + (long)1800;
    }

    private final boolean inside(float mx, float my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    private final int lerp(int a, int b, float t) {
        float tt = RangesKt.coerceIn((float)t, (float)0.0f, (float)1.0f);
        return ProfileScreen.lerp$ch(a, b, tt, 24) << 24 | ProfileScreen.lerp$ch(a, b, tt, 16) << 16 | ProfileScreen.lerp$ch(a, b, tt, 8) << 8 | ProfileScreen.lerp$ch(a, b, tt, 0);
    }

    public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        Intrinsics.checkNotNullParameter((Object)extractor, (String)"extractor");
    }

    public boolean isPauseScreen() {
        return false;
    }

    public void onClose() {
        block0: {
            Minecraft minecraft = this.minecraft;
            if (minecraft == null) break block0;
            minecraft.gui.setScreen((Screen)new ClickGuiScreen());
        }
    }

    private static final int lerp$ch(int $a, int $b, float tt, int shift) {
        int ca = $a >>> shift & 0xFF;
        int cb = $b >>> shift & 0xFF;
        return RangesKt.coerceIn((int)((int)((float)ca + (float)(cb - ca) * tt)), (int)0, (int)255);
    }
}


