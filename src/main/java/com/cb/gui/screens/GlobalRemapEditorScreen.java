package com.cb.gui.screens;

import com.cb.cosmetic.GlobalRemap;
import com.cb.gui.NvgScreen;
import com.cb.render.NVGRenderer;
import com.cb.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002:\u0003\\]^B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\fH\u0016J\b\u0010.\u001a\u00020,H\u0002J\u0018\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\n2\u0006\u00101\u001a\u00020\fH\u0002J\b\u00102\u001a\u00020,H\u0002J8\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\b\b\u0002\u00107\u001a\u0002082\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J.\u0010;\u001a\u00020\f2\u0006\u0010<\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J6\u0010=\u001a\u00020,2\u0006\u00104\u001a\u00020\n2\u0006\u00105\u001a\u00020\f2\u0006\u00106\u001a\u00020\f2\u0006\u0010>\u001a\u00020\f2\f\u00109\u001a\b\u0012\u0004\u0012\u00020,0:H\u0002J\u0010\u0010?\u001a\u0002082\u0006\u00106\u001a\u00020\fH\u0002J\b\u0010@\u001a\u00020,H\u0002J\u0012\u0010A\u001a\u00020,2\b\b\u0002\u0010B\u001a\u000208H\u0002J\u001a\u0010C\u001a\u00020,2\u0006\u0010D\u001a\u00020\b2\b\u0010E\u001a\u0004\u0018\u00010\nH\u0002J\u0010\u0010F\u001a\u00020,2\u0006\u0010G\u001a\u00020\nH\u0002J\u0018\u0010H\u001a\u0002082\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u000208H\u0016J(\u0010L\u001a\u0002082\u0006\u0010\u000f\u001a\u00020M2\u0006\u0010\u0010\u001a\u00020M2\u0006\u0010N\u001a\u00020M2\u0006\u0010O\u001a\u00020MH\u0016J\u0010\u0010P\u001a\u0002082\u0006\u0010I\u001a\u00020QH\u0016J\u0010\u0010R\u001a\u0002082\u0006\u0010I\u001a\u00020SH\u0016J(\u0010T\u001a\u00020,2\u0006\u0010U\u001a\u00020V2\u0006\u0010\u000f\u001a\u00020&2\u0006\u0010\u0010\u001a\u00020&2\u0006\u0010W\u001a\u00020\fH\u0016J\b\u0010X\u001a\u000208H\u0016J\u0010\u0010Y\u001a\u00020\f2\u0006\u0010Z\u001a\u00020MH\u0002J\u0010\u0010[\u001a\u00020\n2\u0006\u0010G\u001a\u00020\nH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u000e\u0010\u0018\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u001dj\b\u0012\u0004\u0012\u00020\u001e`\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006_",
   d2 = {
         "Lcom/cb/gui/screens/GlobalRemapEditorScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "view",
         "Lcom/cb/gui/screens/GlobalRemapEditorScreen$View;",
         "pickPhase",
         "Lcom/cb/gui/screens/GlobalRemapEditorScreen$PickPhase;",
         "pendingSource",
         "",
         "uiScale",
         "",
         "uiWidth",
         "uiHeight",
         "mouseX",
         "mouseY",
         "px",
         "py",
         "panelW",
         "getPanelW",
         "()F",
         "panelH",
         "getPanelH",
         "rowH",
         "listScroll",
         "listTop",
         "listBottom",
         "hits",
         "Ljava/util/ArrayList;",
         "Lcom/cb/gui/screens/GlobalRemapEditorScreen$Hit;",
         "Lkotlin/collections/ArrayList;",
         "allBlocks",
         "",
         "filtered",
         "search",
         "lastFilterKey",
         "highlightIdx",
         "",
         "pickScroll",
         "pickListTop",
         "pickListBottom",
         "blink",
         "renderOverlay",
         "",
         "delta",
         "renderListView",
         "renderSourceBlock",
         "src",
         "startY",
         "renderPickView",
         "pillButton",
         "label",
         "x",
         "y",
         "accent",
         "",
         "act",
         "Lkotlin/Function0;",
         "iconButton",
         "glyph",
         "iconButtonLabeled",
         "wdt",
         "withinList",
         "ensureBlocks",
         "applyFilter",
         "force",
         "enterPick",
         "phase",
         "source",
         "commitPick",
         "id",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "mouseScrolled",
         "",
         "scrollX",
         "scrollY",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "charTyped",
         "Lnet/minecraft/client/input/CharacterEvent;",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "partialTick",
         "isPauseScreen",
         "toUi",
         "scaled",
         "prettify",
         "View",
         "PickPhase",
         "Hit",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nGlobalRemapEditorScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalRemapEditorScreen.kt\ncom/cb/gui/screens/GlobalRemapEditorScreen\n+ 2 NVGRenderer.kt\ncom/cb/render/NVGRenderer\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n*L\n1#1,401:1\n445#2,8:402\n445#2,8:411\n1#3:410\n1586#4:419\n1661#4,3:420\n777#4:423\n873#4,2:424\n1088#5,2:426\n*S KotlinDebug\n*F\n+ 1 GlobalRemapEditorScreen.kt\ncom/cb/gui/screens/GlobalRemapEditorScreen\n*L\n139#1:402,8\n235#1:411,8\n289#1:419\n289#1:420,3\n298#1:423\n298#1:424,2\n381#1:426,2\n*E\n"
)
public final class GlobalRemapEditorScreen extends Screen implements NvgScreen {
   @NotNull
   private GlobalRemapEditorScreen.View view = GlobalRemapEditorScreen.View.LIST;
   @NotNull
   private GlobalRemapEditorScreen.PickPhase pickPhase = GlobalRemapEditorScreen.PickPhase.SOURCE;
   @Nullable
   private String pendingSource;
   private float uiScale = 1.0F;
   private float uiWidth;
   private float uiHeight;
   private float mouseX;
   private float mouseY;
   private float px;
   private float py;
   private final float rowH = 20.0F;
   private float listScroll;
   private float listTop;
   private float listBottom;
   @NotNull
   private final ArrayList<GlobalRemapEditorScreen.Hit> hits = new ArrayList<>();
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
      super(Component.literal("Block Remaps"));
   }

   private final float getPanelW() {
      return RangesKt.coerceIn(this.uiWidth * 0.6F, 420.0F, 640.0F);
   }

   private final float getPanelH() {
      return RangesKt.coerceIn(this.uiHeight * 0.78F, 300.0F, 560.0F);
   }

   @Override
   public void renderOverlay(float delta) {
      Minecraft minecraft = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(minecraft, "getInstance(...)");
      Window window = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      int fbWidth = window.getWidth();
      int fbHeight = window.getHeight();
      if (fbWidth > 0 && fbHeight > 0) {
         this.blink += delta;
         this.uiScale = RangesKt.coerceIn(window.getGuiScale() * 0.75F, 1.0F, 3.0F);
         this.uiWidth = fbWidth / this.uiScale;
         this.uiHeight = fbHeight / this.uiScale;
         int screenWidth = window.getScreenWidth();
         int screenHeight = window.getScreenHeight();
         float fbScaleX = screenWidth > 0 ? (float)fbWidth / screenWidth : 1.0F;
         float fbScaleY = screenHeight > 0 ? (float)fbHeight / screenHeight : 1.0F;
         this.mouseX = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
         this.mouseY = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, 1711276032, 0.0F);
               float w = this.getPanelW();
               float h = this.getPanelH();
               this.px = (this.uiWidth - w) / 2.0F;
               this.py = (this.uiHeight - h) / 2.0F;
               NVGRenderer.INSTANCE.shadow(this.px, this.py, w, h, 10.0F, 16.0F, Integer.MIN_VALUE);
               NVGRenderer.INSTANCE.rect(this.px, this.py, w, h, -183233516, 10.0F);
               NVGRenderer.INSTANCE.rectOutline(this.px, this.py, w, h, 352321535, 10.0F, 0.0F);
               if (this.view == GlobalRemapEditorScreen.View.LIST) {
                  this.renderListView();
               } else {
                  this.renderPickView();
               }
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void renderListView() {
      this.hits.clear();
      float w = this.getPanelW();
      float h = this.getPanelH();
      float cy = this.py + 10.0F;
      NVGRenderer.INSTANCE.text("Block Remaps", this.px + 10.0F, cy + 7.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      NVGRenderer.INSTANCE.textRight(GlobalRemap.INSTANCE.sources().size() + " sources", this.px + w - 10.0F, cy + 7.0F, 12.0F, -6513508, null);
      cy += 24.0F;
      float bx = this.px + 10.0F;
      bx += this.pillButton("+ Add Remap", bx, cy, true, () -> onAddRemap(this)) + 6.0F;
      bx += this.pillButton("Export", bx, cy, false, GlobalRemapEditorScreen::onExport) + 6.0F;
      bx += this.pillButton("Import", bx, cy, false, GlobalRemapEditorScreen::onImport) + 6.0F;
      this.pillButton("Clear", bx, cy, false, GlobalRemapEditorScreen::onClear);
      cy += 24.0F;
      this.listTop = cy;
      this.listBottom = this.py + h - 20.0F;
      float viewH = this.listBottom - this.listTop;
      List<String> sources = GlobalRemap.INSTANCE.sources();
      float totalH = 0.0F;

      for (String src : sources) {
         totalH += this.rowH + GlobalRemap.INSTANCE.targetsOf(src).size() * this.rowH + this.rowH;
      }

      float maxScroll = RangesKt.coerceAtLeast(totalH - viewH, 0.0F);
      this.listScroll = RangesKt.coerceIn(this.listScroll, 0.0F, maxScroll);
      NVGRenderer renderer = NVGRenderer.INSTANCE;
      float scissorX = this.px;
      float scissorY = this.listTop;
      float scissorW = w;
      float scissorH = viewH;
      renderer.save();
      renderer.intersectScissor(scissorX, scissorY, scissorW, scissorH);

      try {
         float ry = this.listTop - this.listScroll;

         for (String src : sources) {
            ry = this.renderSourceBlock(src, ry);
         }
      } finally {
         renderer.restore();
      }

      if (sources.isEmpty()) {
         NVGRenderer.INSTANCE.textCentered("No remaps yet — click \"+ Add Remap\".", this.px + w / 2.0F, this.listTop + 24.0F, 12.0F, -10724260, null);
      }

      NVGRenderer.INSTANCE.textCentered("ESC to close", this.px + w / 2.0F, this.py + h - 10.0F, 12.0F, -10724260, null);
   }

   private final float renderSourceBlock(String src, float startY) {
      float w = this.getPanelW();
      float ry = startY;
      Function1<Float, Boolean> visible = y -> isRowVisible(this, y);
      if ((Boolean)visible.invoke(ry)) {
         NVGRenderer.INSTANCE.rect(this.px + 4.0F, ry + 1.0F, w - 8.0F, this.rowH - 2.0F, -14935012, 4.0F);
         NVGRenderer.INSTANCE.text(this.prettify(src), this.px + 10.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.MEDIUM);
         float delW = this.iconButton("x", this.px + w - 10.0F - 16.0F, ry + (this.rowH - 14.0F) / 2.0F, () -> onRemoveSource(src));
         this.iconButtonLabeled(
            "+ target", this.px + w - 10.0F - 16.0F - delW - 64.0F, ry + (this.rowH - 14.0F) / 2.0F, 60.0F, () -> onAddTarget(this, src)
         );
      }

      ry += this.rowH;
      List<GlobalRemap.RemapTarget> targets = GlobalRemap.INSTANCE.targetsOf(src);
      Iterator iterator = targets.iterator();
      int targetIndex = 0;

      while (iterator.hasNext()) {
         int i = targetIndex++;
         GlobalRemap.RemapTarget t = (GlobalRemap.RemapTarget)iterator.next();
         if ((Boolean)visible.invoke(ry)) {
            NVGRenderer.INSTANCE.text("> " + this.prettify(t.getId()), this.px + 10.0F + 12.0F, ry + this.rowH / 2.0F, 12.0F, -6513508, null);
            float cx = this.px + w - 10.0F - 16.0F;
            this.iconButton("x", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> onRemoveTarget(src, i));
            cx -= 22.0F;
            this.iconButton("+", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> onIncreaseWeight(src, i, t));
            cx -= 30.0F;
            NVGRenderer.INSTANCE.textCentered("x" + t.getWeight(), cx + 14.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, null);
            cx -= 18.0F;
            this.iconButton("-", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> onDecreaseWeight(src, i, t));
         }

         ry += this.rowH;
      }

      return ry;
   }

   private final void renderPickView() {
      this.ensureBlocks();
      this.applyFilter(false);
      float w = this.getPanelW();
      float h = this.getPanelH();
      float cy = this.py + 10.0F;
      String title;
      if (this.pickPhase == GlobalRemapEditorScreen.PickPhase.SOURCE) {
         title = "Pick source block";
      } else {
         String source = this.pendingSource;
         if (this.pendingSource == null) {
            source = "";
         }

         title = "Pick target for " + this.prettify(source);
      }

      NVGRenderer.INSTANCE.text(title, this.px + 10.0F, cy + 7.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
      cy += 24.0F;
      float searchH = 22.0F;
      NVGRenderer.INSTANCE.rect(this.px + 10.0F, cy, w - 20.0F, searchH, 1711276032, 5.0F);
      NVGRenderer.INSTANCE.rectOutline(this.px + 10.0F, cy, w - 20.0F, searchH, 352321535, 5.0F, 0.0F);
      String searchText;
      if (this.search.length() == 0) {
         searchText = "type to filter...";
      } else {
         searchText = this.search;
      }
      int searchColorx = this.search.length() == 0 ? -10724260 : -1184275;
      NVGRenderer.INSTANCE.text(searchText, this.px + 10.0F + 8.0F, cy + searchH / 2.0F, 13.0F, searchColorx, null);
      if (this.blink % 1.0F < 0.55F) {
         float caretX = this.px + 10.0F + 8.0F + NVGRenderer.INSTANCE.textWidth(this.search, 13.0F, null);
         NVGRenderer.INSTANCE.rect(caretX + 1.0F, cy + searchH / 2.0F - 6.0F, 1.2F, 12.0F, -1750963, 0.0F);
      }

      cy += searchH + 6.0F;
      NVGRenderer.INSTANCE.text(this.filtered.size() + " result" + (this.filtered.size() == 1 ? "" : "s"), this.px + 10.0F, cy + 6.0F, 12.0F, -10724260, null);
      cy += 16.0F;
      this.pickListTop = cy;
      this.pickListBottom = this.py + h - 20.0F;
      float viewH = this.pickListBottom - this.pickListTop;
      float totalH = this.filtered.size() * this.rowH;
      float maxScroll = RangesKt.coerceAtLeast(totalH - viewH, 0.0F);
      this.pickScroll = RangesKt.coerceIn(this.pickScroll, 0.0F, maxScroll);
      NVGRenderer renderer = NVGRenderer.INSTANCE;
      float scissorX = this.px;
      float scissorY = this.pickListTop;
      float scissorW = w;
      float scissorH = viewH;
      renderer.save();
      renderer.intersectScissor(scissorX, scissorY, scissorW, scissorH);

      try {
         int first = RangesKt.coerceAtLeast((int)(this.pickScroll / this.rowH), 0);
         int last = RangesKt.coerceAtMost((int)((this.pickScroll + viewH) / this.rowH), this.filtered.size() - 1);
         int i = first;
         if (i <= last) {
            while (true) {
               String id;
               float ry;
               boolean hovered;
               label130: {
                  id = this.filtered.get(i);
                  ry = this.pickListTop + i * this.rowH - this.pickScroll;
                  float boundA = this.px;
                  float boundB = this.px + w;
                  float cursor = this.mouseX;
                  if (boundA <= cursor ? cursor <= boundB : false) {
                     boundA = ry + this.rowH;
                     boundB = this.mouseY;
                     if (ry <= boundB ? boundB <= boundA : false) {
                        hovered = true;
                        break label130;
                     }
                  }

                  hovered = false;
               }

               if (i == this.highlightIdx) {
                  NVGRenderer.INSTANCE.rect(this.px + 2.0F, ry, w - 4.0F, this.rowH, Theme.INSTANCE.withAlpha(-1750963, 0.28F), 4.0F);
               } else if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.px + 2.0F, ry, w - 4.0F, this.rowH, 318767103, 4.0F);
               }

               NVGRenderer.INSTANCE.text(this.prettify(id), this.px + 10.0F + 4.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, null);
               if (i == last) {
                  break;
               }

               i++;
            }
         }
      } finally {
         renderer.restore();
      }

      NVGRenderer.INSTANCE.textCentered("Enter confirm   |   ESC back", this.px + w / 2.0F, this.py + h - 10.0F, 12.0F, -10724260, null);
   }

   private final float pillButton(String label, float x, float y, boolean accent, Function0<Unit> act) {
      float bw;
      float bh;
      boolean hovered;
      label35: {
         bw = NVGRenderer.INSTANCE.textWidth(label, 12.0F, null) + 16.0F;
         bh = 16.0F;
         float bg = x + bw;
         float cursor = this.mouseX;
         if (x <= cursor ? cursor <= bg : false) {
            bg = y + bh;
            cursor = this.mouseY;
            if (y <= cursor ? cursor <= bg : false) {
               hovered = true;
               break label35;
            }
         }

         hovered = false;
      }

      int bg = accent ? -1750963 : (hovered ? 318767103 : -14013910);
      NVGRenderer.INSTANCE.rect(x, y, bw, bh, bg, 4.0F);
      NVGRenderer.INSTANCE.textCentered(label, x + bw / 2.0F, y + bh / 2.0F, 12.0F, -1184275, null);
      this.hits.add(new GlobalRemapEditorScreen.Hit(x, y, bw, bh, act));
      return bw;
   }

   private final float iconButton(String glyph, float x, float y, Function0<Unit> act) {
      float s;
      boolean hovered;
      label36: {
         s = 14.0F;
         float edge = x + s;
         float cursor = this.mouseX;
         if (x <= cursor ? cursor <= edge : false) {
            edge = y + s;
            cursor = this.mouseY;
            if (y <= cursor ? cursor <= edge : false) {
               hovered = true;
               break label36;
            }
         }

         hovered = false;
      }

      NVGRenderer.INSTANCE.rect(x, y, s, s, hovered ? -1750963 : -14013910, 3.0F);
      NVGRenderer.INSTANCE.textCentered(glyph, x + s / 2.0F, y + s / 2.0F, 12.0F, -1184275, null);
      if (this.withinList(y)) {
         this.hits.add(new GlobalRemapEditorScreen.Hit(x, y, s, s, act));
      }

      return s;
   }

   private final void iconButtonLabeled(String label, float x, float y, float wdt, Function0<Unit> act) {
      float s;
      boolean hovered;
      label36: {
         s = 14.0F;
         float edge = x + wdt;
         float cursor = this.mouseX;
         if (x <= cursor ? cursor <= edge : false) {
            edge = y + s;
            cursor = this.mouseY;
            if (y <= cursor ? cursor <= edge : false) {
               hovered = true;
               break label36;
            }
         }

         hovered = false;
      }

      NVGRenderer.INSTANCE.rect(x, y, wdt, s, hovered ? 318767103 : -14013910, 3.0F);
      NVGRenderer.INSTANCE.textCentered(label, x + wdt / 2.0F, y + s / 2.0F, 12.0F, -1184275, null);
      if (this.withinList(y)) {
         this.hits.add(new GlobalRemapEditorScreen.Hit(x, y, wdt, s, act));
      }
   }

   private final boolean withinList(float y) {
      return y >= this.listTop - 2.0F && y + this.rowH <= this.listBottom + 2.0F;
   }

   private final void ensureBlocks() {
      if (this.allBlocks.isEmpty()) {
         Set keys = BuiltInRegistries.BLOCK.keySet();
         Intrinsics.checkNotNullExpressionValue(keys, "keySet(...)");
         GlobalRemapEditorScreen screen = this;
         Iterable blocksToMap = keys;
         var mapped = new ArrayList(CollectionsKt.collectionSizeOrDefault(keys, 10));

         for (Object element : blocksToMap) {
            Identifier blockId = (Identifier)element;
            mapped.add(blockId.toString());
         }

         screen.allBlocks = CollectionsKt.sorted(mapped);
         this.applyFilter(true);
      }
   }

   private final void applyFilter(boolean force) {
      String q = StringsKt.trim(this.search).toString().toLowerCase(Locale.ROOT);
      Intrinsics.checkNotNullExpressionValue(q, "toLowerCase(...)");
      if (force || !Intrinsics.areEqual(q, this.lastFilterKey)) {
         this.lastFilterKey = q;
         List result;
         if (q.length() == 0) {
            result = this.allBlocks;
         } else {
            Iterable filterSource = this.allBlocks;
            var filteredList = new ArrayList();

            for (Object element : filterSource) {
               String it = (String)element;
               if (StringsKt.contains(it, q, false)) {
                  filteredList.add(element);
               }
            }

            result = (List)filteredList;
         }

         this.filtered = result;
         this.highlightIdx = RangesKt.coerceIn(this.highlightIdx, 0, RangesKt.coerceAtLeast(this.filtered.size() - 1, 0));
      }
   }

   private final void enterPick(GlobalRemapEditorScreen.PickPhase phase, String source) {
      this.view = GlobalRemapEditorScreen.View.PICK;
      this.pickPhase = phase;
      this.pendingSource = source;
      this.search = "";
      this.lastFilterKey = "__none__";
      this.highlightIdx = 0;
      this.pickScroll = 0.0F;
      this.ensureBlocks();
      this.applyFilter(true);
   }

   private final void commitPick(String id) {
      switch (this.pickPhase) {
         case SOURCE:
            this.enterPick(GlobalRemapEditorScreen.PickPhase.TARGET, id);
            break;
         case TARGET:
            String src = this.pendingSource;
            if (src != null) {
               GlobalRemap.INSTANCE.addTarget(src, id, 1);
            }

            this.view = GlobalRemapEditorScreen.View.LIST;
            this.pendingSource = null;
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   @Override
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      float mx = this.toUi(event.x());
      float my = this.toUi(event.y());
      if (event.button() != 0) {
         return true;
      }

      if (this.view == GlobalRemapEditorScreen.View.LIST) {
         for (GlobalRemapEditorScreen.Hit hit : this.hits) {
            float minCoord = hit.getX();
            if (mx <= hit.getX() + hit.getW() ? minCoord <= mx : false) {
               minCoord = hit.getY();
               if (my <= hit.getY() + hit.getH() ? minCoord <= my : false) {
                  hit.getAct().invoke();
                  return true;
               }
            }
         }

         if (mx < this.px || mx > this.px + this.getPanelW() || my < this.py || my > this.py + this.getPanelH()) {
            this.onClose();
         }

         return true;
      } else {
         float idx = this.px;
         if (mx <= this.px + this.getPanelW() ? idx <= mx : false) {
            idx = this.pickListTop;
            if (my <= this.pickListBottom ? idx <= my : false) {
               int idxx = (int)((my - this.pickListTop + this.pickScroll) / this.rowH);
               if (0 <= idxx ? idxx < this.filtered.size() : false) {
                  this.commitPick(this.filtered.get(idxx));
               }

               return true;
            }
         }

         if (mx < this.px || mx > this.px + this.getPanelW() || my < this.py || my > this.py + this.getPanelH()) {
            this.view = GlobalRemapEditorScreen.View.LIST;
         }

         return true;
      }
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      if (this.view == GlobalRemapEditorScreen.View.LIST) {
         this.listScroll = this.listScroll - (float)scrollY * this.rowH * 3.0F;
      } else {
         this.pickScroll = this.pickScroll - (float)scrollY * this.rowH * 3.0F;
      }

      return true;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.view == GlobalRemapEditorScreen.View.PICK) {
         switch (event.key()) {
            case 256:
               this.view = GlobalRemapEditorScreen.View.LIST;
               return true;
            case 257:
            case 335:
               if (!this.filtered.isEmpty()) {
                  this.commitPick(this.filtered.get(RangesKt.coerceIn(this.highlightIdx, 0, this.filtered.size() - 1)));
               }

               return true;
            case 259:
               if (this.search.length() > 0) {
                  this.search = StringsKt.dropLast(this.search, 1);
                  this.applyFilter(false);
               }

               return true;
            case 264:
               this.highlightIdx = RangesKt.coerceAtMost(this.highlightIdx + 1, RangesKt.coerceAtLeast(this.filtered.size() - 1, 0));
               return true;
            case 265:
               this.highlightIdx = RangesKt.coerceAtLeast(this.highlightIdx - 1, 0);
               return true;
            default:
               return super.keyPressed(event);
         }
      } else if (event.key() == 256) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(event);
      }
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      if (this.view != GlobalRemapEditorScreen.View.PICK) {
         return false;
      }

      String s = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(s, "codepointAsString(...)");
      if (s.length() > 0) {
         CharSequence text = s;

         boolean allPrintable;
         label33: {
            for (int index = 0; index < text.length(); index++) {
               char element = text.charAt(index);
               if (element < ' ') {
                  allPrintable = false;
                  break label33;
               }
            }

            allPrintable = true;
         }

         if (allPrintable) {
            this.search = this.search + s;
            this.applyFilter(false);
            return true;
         }
      }

      return false;
   }

   @Override
   public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
      Intrinsics.checkNotNullParameter(extractor, "extractor");
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   private final float toUi(double scaled) {
      Window window = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      return (float)(scaled * window.getGuiScale() / this.uiScale);
   }

   private final String prettify(String id) {
      return StringsKt.removePrefix(id, "minecraft:");
   }

   private static final Unit onAddRemap(GlobalRemapEditorScreen this$0) {
      this$0.enterPick(GlobalRemapEditorScreen.PickPhase.SOURCE, null);
      return Unit.INSTANCE;
   }

   private static final Unit onExport() {
      Minecraft.getInstance().keyboardHandler.setClipboard(GlobalRemap.INSTANCE.exportJson());
      return Unit.INSTANCE;
   }

   private static final Unit onImport() {
      GlobalRemap remap = GlobalRemap.INSTANCE;
      String clipboard = Minecraft.getInstance().keyboardHandler.getClipboard();
      Intrinsics.checkNotNullExpressionValue(clipboard, "getClipboard(...)");
      remap.importJson(clipboard);
      return Unit.INSTANCE;
   }

   private static final Unit onClear() {
      GlobalRemap.INSTANCE.clear();
      return Unit.INSTANCE;
   }

   private static final boolean isRowVisible(GlobalRemapEditorScreen this$0, float y) {
      return y + this$0.rowH > this$0.listTop && y < this$0.listBottom;
   }

   private static final Unit onRemoveSource(String src) {
      GlobalRemap.INSTANCE.removeSource(src);
      return Unit.INSTANCE;
   }

   private static final Unit onAddTarget(GlobalRemapEditorScreen this$0, String src) {
      this$0.enterPick(GlobalRemapEditorScreen.PickPhase.TARGET, src);
      return Unit.INSTANCE;
   }

   private static final Unit onRemoveTarget(String src, int index) {
      GlobalRemap.INSTANCE.removeTarget(src, index);
      return Unit.INSTANCE;
   }

   private static final Unit onIncreaseWeight(String src, int index, GlobalRemap.RemapTarget target) {
      GlobalRemap.INSTANCE.setWeight(src, index, target.getWeight() + 1);
      return Unit.INSTANCE;
   }

   private static final Unit onDecreaseWeight(String src, int index, GlobalRemap.RemapTarget target) {
      GlobalRemap.INSTANCE.setWeight(src, index, target.getWeight() - 1);
      return Unit.INSTANCE;
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013",
      d2 = {
            "Lcom/cb/gui/screens/GlobalRemapEditorScreen$Hit;",
            "",
            "x",
            "",
            "y",
            "w",
            "h",
            "act",
            "Lkotlin/Function0;",
            "",
            "<init>",
            "(FFFFLkotlin/jvm/functions/Function0;)V",
            "getX",
            "()F",
            "getY",
            "getW",
            "getH",
            "getAct",
            "()Lkotlin/jvm/functions/Function0;",
            "cb_client"
      }
   )
   private static final class Hit {
      private final float x;
      private final float y;
      private final float w;
      private final float h;
      @NotNull
      private final Function0<Unit> act;

      public Hit(float x, float y, float w, float h, @NotNull Function0<Unit> act) {
         Intrinsics.checkNotNullParameter(act, "act");
         super();
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

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006",
      d2 = {"Lcom/cb/gui/screens/GlobalRemapEditorScreen$PickPhase;", "", "<init>", "(Ljava/lang/String;I)V", "SOURCE", "TARGET", "cb_client"}
   )
   private enum PickPhase {
      SOURCE,
      TARGET;

      @NotNull
      public static EnumEntries<GlobalRemapEditorScreen.PickPhase> getEntries() {
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }

   @Metadata(
      mv = {2, 3, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006",
      d2 = {"Lcom/cb/gui/screens/GlobalRemapEditorScreen$View;", "", "<init>", "(Ljava/lang/String;I)V", "LIST", "PICK", "cb_client"}
   )
   private enum View {
      LIST,
      PICK;

      @NotNull
      public static EnumEntries<GlobalRemapEditorScreen.View> getEntries() {
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }
}
