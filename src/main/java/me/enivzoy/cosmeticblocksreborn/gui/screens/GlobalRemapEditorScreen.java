package me.enivzoy.cosmeticblocksreborn.gui.screens;

import me.enivzoy.cosmeticblocksreborn.cosmetic.GlobalRemap;
import me.enivzoy.cosmeticblocksreborn.cosmetic.RemapStore;
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
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
   @NotNull
   private final RemapStore store;
   private final boolean showImportExport;

   public GlobalRemapEditorScreen() {
      this(GlobalRemap.INSTANCE, true);
   }

   public GlobalRemapEditorScreen(@NotNull RemapStore store, boolean showImportExport) {
      super(Component.literal("Block Remaps"));
      Intrinsics.checkNotNullParameter(store, "store");
      this.store = store;
      this.showImportExport = showImportExport;
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
               NVGRenderer.INSTANCE.rectOutline(this.px, this.py, w, h, 352321535, 10.0F, 1.0F);
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
      NVGRenderer.INSTANCE.textRight(this.store.sources().size() + " sources", this.px + w - 10.0F, cy + 7.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
      cy += 24.0F;
      float bx = this.px + 10.0F;
      bx += this.pillButton("+ Add Remap", bx, cy, true, () -> this.onAddRemap()) + 6.0F;
      if (this.showImportExport) {
         bx += this.pillButton("Export", bx, cy, false, () -> this.onExport()) + 6.0F;
         bx += this.pillButton("Import", bx, cy, false, () -> this.onImport()) + 6.0F;
      }

      this.pillButton("Clear", bx, cy, false, () -> this.onClear());
      cy += 24.0F;
      this.listTop = cy;
      this.listBottom = this.py + h - 20.0F;
      float viewH = this.listBottom - this.listTop;
      List<String> sources = this.store.sources();
      float totalH = 0.0F;

      for (String src : sources) {
         totalH += this.rowH + this.store.targetsOf(src).size() * this.rowH + this.rowH;
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
         NVGRenderer.INSTANCE.textCentered("No remaps yet â€” click \"+ Add Remap\".", this.px + w / 2.0F, this.listTop + 24.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
      }

      NVGRenderer.INSTANCE.textCentered("ESC to close", this.px + w / 2.0F, this.py + h - 10.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
   }

   private final float renderSourceBlock(String src, float startY) {
      float w = this.getPanelW();
      float ry = startY;
      Function1<Float, Boolean> visible = y -> this.isRowVisible(y);
      if ((Boolean)visible.invoke(ry)) {
         NVGRenderer.INSTANCE.rect(this.px + 4.0F, ry + 1.0F, w - 8.0F, this.rowH - 2.0F, -14935012, 4.0F);
         NVGRenderer.INSTANCE.text(this.prettify(src), this.px + 10.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.MEDIUM);
         float delW = this.iconButton("x", this.px + w - 10.0F - 16.0F, ry + (this.rowH - 14.0F) / 2.0F, () -> this.onRemoveSource(src));
         this.iconButtonLabeled(
            "+ target", this.px + w - 10.0F - 16.0F - delW - 64.0F, ry + (this.rowH - 14.0F) / 2.0F, 60.0F, () -> this.onAddTarget(src)
         );
      }

      ry += this.rowH;
      List<GlobalRemap.RemapTarget> targets = this.store.targetsOf(src);
      Iterator iterator = targets.iterator();
      int targetIndex = 0;

      while (iterator.hasNext()) {
         int i = targetIndex++;
         GlobalRemap.RemapTarget t = (GlobalRemap.RemapTarget)iterator.next();
         if ((Boolean)visible.invoke(ry)) {
            NVGRenderer.INSTANCE.text("> " + this.prettify(t.getId()), this.px + 10.0F + 12.0F, ry + this.rowH / 2.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
            float cx = this.px + w - 10.0F - 16.0F;
            this.iconButton("x", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> this.onRemoveTarget(src, i));
            cx -= 22.0F;
            this.iconButton("+", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> this.onIncreaseWeight(src, i, t));
            cx -= 30.0F;
            NVGRenderer.INSTANCE.textCentered("x" + t.getWeight(), cx + 14.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
            cx -= 18.0F;
            this.iconButton("-", cx, ry + (this.rowH - 14.0F) / 2.0F, () -> this.onDecreaseWeight(src, i, t));
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
      NVGRenderer.INSTANCE.rectOutline(this.px + 10.0F, cy, w - 20.0F, searchH, 352321535, 5.0F, 1.0F);
      String searchText;
      if (this.search.length() == 0) {
         searchText = "type to filter...";
      } else {
         searchText = this.search;
      }
      int searchColorx = this.search.length() == 0 ? -10724260 : -1184275;
      NVGRenderer.INSTANCE.text(searchText, this.px + 10.0F + 8.0F, cy + searchH / 2.0F, 13.0F, searchColorx, NVGRenderer.Font.REGULAR);
      if (this.blink % 1.0F < 0.55F) {
         float caretX = this.px + 10.0F + 8.0F + NVGRenderer.INSTANCE.textWidth(this.search, 13.0F, NVGRenderer.Font.REGULAR);
         NVGRenderer.INSTANCE.rect(caretX + 1.0F, cy + searchH / 2.0F - 6.0F, 1.2F, 12.0F, -1750963, 0.0F);
      }

      cy += searchH + 6.0F;
      NVGRenderer.INSTANCE.text(this.filtered.size() + " result" + (this.filtered.size() == 1 ? "" : "s"), this.px + 10.0F, cy + 6.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
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

               NVGRenderer.INSTANCE.text(this.prettify(id), this.px + 10.0F + 4.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
               if (i == last) {
                  break;
               }

               i++;
            }
         }
      } finally {
         renderer.restore();
      }

      NVGRenderer.INSTANCE.textCentered("Enter confirm   |   ESC back", this.px + w / 2.0F, this.py + h - 10.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
   }

   private final float pillButton(String label, float x, float y, boolean accent, Function0<Unit> act) {
      float bw;
      float bh;
      boolean hovered;
      label35: {
         bw = NVGRenderer.INSTANCE.textWidth(label, 12.0F, NVGRenderer.Font.REGULAR) + 16.0F;
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
      NVGRenderer.INSTANCE.textCentered(label, x + bw / 2.0F, y + bh / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
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
      NVGRenderer.INSTANCE.textCentered(glyph, x + s / 2.0F, y + s / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
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
      NVGRenderer.INSTANCE.textCentered(label, x + wdt / 2.0F, y + s / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
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
               this.store.addTarget(src, id, 1);
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

   private final Unit onAddRemap() {
      this.enterPick(GlobalRemapEditorScreen.PickPhase.SOURCE, null);
      return Unit.INSTANCE;
   }

   private final Unit onExport() {
      Minecraft.getInstance().keyboardHandler.setClipboard(this.store.exportJson());
      return Unit.INSTANCE;
   }

   private final Unit onImport() {
      RemapStore remap = this.store;
      String clipboard = Minecraft.getInstance().keyboardHandler.getClipboard();
      Intrinsics.checkNotNullExpressionValue(clipboard, "getClipboard(...)");
      remap.importJson(clipboard);
      return Unit.INSTANCE;
   }

   private final Unit onClear() {
      this.store.clear();
      return Unit.INSTANCE;
   }

   private final boolean isRowVisible(float y) {
      return y + this.rowH > this.listTop && y < this.listBottom;
   }

   private final Unit onRemoveSource(String src) {
      this.store.removeSource(src);
      return Unit.INSTANCE;
   }

   private final Unit onAddTarget(String src) {
      this.enterPick(GlobalRemapEditorScreen.PickPhase.TARGET, src);
      return Unit.INSTANCE;
   }

   private final Unit onRemoveTarget(String src, int index) {
      this.store.removeTarget(src, index);
      return Unit.INSTANCE;
   }

   private final Unit onIncreaseWeight(String src, int index, GlobalRemap.RemapTarget target) {
      this.store.setWeight(src, index, target.getWeight() + 1);
      return Unit.INSTANCE;
   }

   private final Unit onDecreaseWeight(String src, int index, GlobalRemap.RemapTarget target) {
      this.store.setWeight(src, index, target.getWeight() - 1);
      return Unit.INSTANCE;
   }

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

   private enum PickPhase {
      SOURCE,
      TARGET;

      @NotNull
      public static EnumEntries<GlobalRemapEditorScreen.PickPhase> getEntries() {
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }

   private enum View {
      LIST,
      PICK;

      @NotNull
      public static EnumEntries<GlobalRemapEditorScreen.View> getEntries() {
         return kotlin.enums.EnumEntriesKt.enumEntries(values());
      }
   }
}
