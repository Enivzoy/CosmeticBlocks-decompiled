package me.enivzoy.cosmeticblocksreborn.gui.screens;

import me.enivzoy.cosmeticblocksreborn.cosmetic.CosmeticBlocks;
import me.enivzoy.cosmeticblocksreborn.gui.NvgScreen;
import me.enivzoy.cosmeticblocksreborn.render.NVGRenderer;
import me.enivzoy.cosmeticblocksreborn.render.Theme;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
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

public final class BlockSelectorScreen extends Screen implements NvgScreen {
   private float uiScale = 1.0F;
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
   private final float rowH = 18.0F;
   private float px;
   private float py;
   private float listTop;
   private float listBottom;
   private float favStripBottom;
   @NotNull
   private List<Triple<String, Float, Float>> favRects = CollectionsKt.emptyList();

   public BlockSelectorScreen() {
      super(Component.literal("Block Selector"));
   }

   private final float getPanelW() {
      return RangesKt.coerceIn(this.uiWidth * 0.55F, 360.0F, 560.0F);
   }

   private final float getPanelH() {
      return RangesKt.coerceIn(this.uiHeight * 0.75F, 280.0F, 540.0F);
   }

   private final void ensureBlocks() {
      if (this.allBlocks.isEmpty()) {
         Set blockKeys = BuiltInRegistries.BLOCK.keySet();
         Intrinsics.checkNotNullExpressionValue(blockKeys, "keySet(...)");
         var mapped = new ArrayList(CollectionsKt.collectionSizeOrDefault(blockKeys, 10));

         for (Object item : blockKeys) {
            Identifier it = (Identifier)item;
            mapped.add(it.toString());
         }

         this.allBlocks = CollectionsKt.sorted(mapped);
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
            var matches = new ArrayList();

            for (Object element : this.allBlocks) {
               String it = (String)element;
               if (StringsKt.contains(it, q, false)) {
                  matches.add(element);
               }
            }

            result = (List)matches;
         }

         this.filtered = result;
         this.highlightIdx = RangesKt.coerceIn(this.highlightIdx, 0, RangesKt.coerceAtLeast(this.filtered.size() - 1, 0));
      }
   }

   @Override
   public void renderOverlay(float delta) {
      this.ensureBlocks();
      this.applyFilter(false);
      this.blink += delta;
      Minecraft minecraft = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(minecraft, "getInstance(...)");
      Window window = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
      int fbWidth = window.getWidth();
      int fbHeight = window.getHeight();
      if (fbWidth > 0 && fbHeight > 0) {
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
               float cy = this.py + 10.0F;
               NVGRenderer.INSTANCE.text("Brush Block", this.px + 10.0F, cy + 7.0F, 14.0F, -1184275, NVGRenderer.Font.BOLD);
               NVGRenderer.INSTANCE.textRight("Current: " + this.prettify(CosmeticBlocks.INSTANCE.getSelectedBlock()), this.px + w - 10.0F, cy + 7.0F, 12.0F, -6513508, NVGRenderer.Font.REGULAR);
               cy += 22.0F;
               float searchH = 22.0F;
               NVGRenderer.INSTANCE.rect(this.px + 10.0F, cy, w - 20.0F, searchH, 1711276032, 5.0F);
               NVGRenderer.INSTANCE.rectOutline(this.px + 10.0F, cy, w - 20.0F, searchH, 352321535, 5.0F, 1.0F);
               CharSequence searchChars = this.search;
               CharSequence searchShown;
               if (searchChars.length() == 0) {
                  searchShown = "type to filter...";
               } else {
                  searchShown = searchChars;
               }

               String searchText = (String)searchShown;
               int searchTextColor = this.search.length() == 0 ? -10724260 : -1184275;
               NVGRenderer.INSTANCE.text(searchText, this.px + 10.0F + 8.0F, cy + searchH / 2.0F, 13.0F, searchTextColor, NVGRenderer.Font.REGULAR);
               if (this.blink % 1.0F < 0.55F) {
                  float caretX = this.px + 10.0F + 8.0F + NVGRenderer.INSTANCE.textWidth(this.search, 13.0F, NVGRenderer.Font.REGULAR);
                  NVGRenderer.INSTANCE.rect(caretX + 1.0F, cy + searchH / 2.0F - 6.0F, 1.2F, 12.0F, -1750963, 0.0F);
               }

               cy += searchH + 6.0F;
               List<String> favs;
               try {
                  favs = CosmeticBlocks.INSTANCE.getFavorites();
               } catch (Throwable ignored) {
                  favs = CollectionsKt.emptyList();
               }

               this.favRects = CollectionsKt.emptyList();
               if (!favs.isEmpty()) {
                  NVGRenderer.INSTANCE.text("FAVORITES", this.px + 10.0F, cy + 6.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
                  cy += 14.0F;
                  List chips = new ArrayList();
                  float chipX = this.px + 10.0F;
                  float chipH = 18.0F;
                  float maxX = this.px + w - 10.0F;

                  for (String id : favs) {
                     String label = "â˜… " + this.prettify(id);
                     float cw = NVGRenderer.INSTANCE.textWidth(label, 12.0F, NVGRenderer.Font.REGULAR) + 14.0F;
                     if (chipX + cw > maxX) {
                        break;
                     }

                     boolean hovered;
                     label167: {
                        float edge = chipX + cw;
                        float mouse = this.mouseX;
                        if (chipX <= mouse ? mouse <= edge : false) {
                           edge = cy + chipH;
                           mouse = this.mouseY;
                           if (cy <= mouse ? mouse <= edge : false) {
                              hovered = true;
                              break label167;
                           }
                        }

                        hovered = false;
                     }
                     NVGRenderer.INSTANCE.rect(chipX, cy, cw, chipH, hovered ? 318767103 : -14935012, 4.0F);
                     NVGRenderer.INSTANCE.text(label, chipX + 7.0F, cy + chipH / 2.0F, 12.0F, -1750963, NVGRenderer.Font.REGULAR);
                     chips.add(new Triple<>(id, chipX, cw));
                     chipX += cw + 6.0F;
                  }

                  this.favRects = chips;
                  cy += chipH + 8.0F;
               }

               this.favStripBottom = cy;
               NVGRenderer.INSTANCE.text(this.filtered.size() + " result" + (this.filtered.size() == 1 ? "" : "s"), this.px + 10.0F, cy + 6.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
               cy += 16.0F;
               this.listTop = cy;
               this.listBottom = this.py + h - 24.0F;
               this.renderList(favs);
               NVGRenderer.INSTANCE.textCentered("Up/Down navigate   |   Enter confirm   |   Right-click toggle favorite", this.px + w / 2.0F, this.py + h - 12.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   private final void renderList(List<String> favs) {
      float w = this.getPanelW();
      float viewH = this.listBottom - this.listTop;
      float totalH = this.filtered.size() * this.rowH;
      float maxScroll = RangesKt.coerceAtLeast(totalH - viewH, 0.0F);
      this.scroll = RangesKt.coerceIn(this.scroll, 0.0F, maxScroll);
      if (this.highlightIdx != this.lastHighlightIdx) {
         this.lastHighlightIdx = this.highlightIdx;
         float hlY = this.highlightIdx * this.rowH - this.scroll;
         if (hlY < 0.0F) {
            this.scroll = RangesKt.coerceAtLeast(this.highlightIdx * this.rowH, 0.0F);
         } else if (hlY + this.rowH > viewH) {
            this.scroll = RangesKt.coerceAtLeast(this.highlightIdx * this.rowH - viewH + this.rowH, 0.0F);
         }

         this.scroll = RangesKt.coerceIn(this.scroll, 0.0F, maxScroll);
      }

      NVGRenderer renderer = NVGRenderer.INSTANCE;
      float x = this.px;
      float y = this.listTop;
      float scissorW = w;
      float scissorH = viewH;
      renderer.save();
      renderer.intersectScissor(x, y, scissorW, scissorH);

      try {
         int first = RangesKt.coerceAtLeast((int)(this.scroll / this.rowH), 0);
         int last = RangesKt.coerceAtMost((int)((this.scroll + viewH) / this.rowH), this.filtered.size() - 1);
         int i = first;
         if (i <= last) {
            while (true) {
               String id;
               float ry;
               boolean isHighlight;
               boolean hovered;
               label116: {
                  id = this.filtered.get(i);
                  ry = this.listTop + i * this.rowH - this.scroll;
                  isHighlight = i == this.highlightIdx;
                  float isCurrent = this.px;
                  float isFav = this.px + w;
                  float mouse = this.mouseX;
                  if (isCurrent <= mouse ? mouse <= isFav : false) {
                     isCurrent = ry + this.rowH;
                     isFav = this.mouseY;
                     if (ry <= isFav ? isFav <= isCurrent : false) {
                        hovered = true;
                        break label116;
                     }
                  }

                  hovered = false;
               }
               if (isHighlight) {
                  NVGRenderer.INSTANCE.rect(this.px + 2.0F, ry, w - 4.0F, this.rowH, Theme.INSTANCE.withAlpha(-1750963, 0.28F), 4.0F);
               } else if (hovered) {
                  NVGRenderer.INSTANCE.rect(this.px + 2.0F, ry, w - 4.0F, this.rowH, 318767103, 4.0F);
               }

               NVGRenderer.INSTANCE.text(this.prettify(id), this.px + 10.0F + 4.0F, ry + this.rowH / 2.0F, 12.0F, -1184275, NVGRenderer.Font.REGULAR);
               boolean isCurrent = Intrinsics.areEqual(id, CosmeticBlocks.INSTANCE.getSelectedBlock());
               boolean isFav = favs.contains(id);
               if (isCurrent) {
                  NVGRenderer.INSTANCE.textRight("â— active", this.px + w - 10.0F, ry + this.rowH / 2.0F, 12.0F, -1750963, NVGRenderer.Font.REGULAR);
               } else if (isFav) {
                  NVGRenderer.INSTANCE.textRight("â˜…", this.px + w - 10.0F, ry + this.rowH / 2.0F, 12.0F, -15821, NVGRenderer.Font.REGULAR);
               }

               if (i == last) {
                  break;
               }

               i++;
            }
         }
      } finally {
         renderer.restore();
      }

      if (this.filtered.isEmpty()) {
         NVGRenderer.INSTANCE.textCentered("No blocks match.", this.px + w / 2.0F, this.listTop + 16.0F, 12.0F, -10724260, NVGRenderer.Font.REGULAR);
      }
   }

   @Override
   public void extractBackground(@NotNull GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
      Intrinsics.checkNotNullParameter(extractor, "extractor");
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      float mx = this.toUi(event.x());
      float my = this.toUi(event.y());
      int button = event.button();

      for (Triple favRect : this.favRects) {
         String favId = (String)favRect.component1();
         float fx = ((Number)favRect.component2()).floatValue();
         float fw = ((Number)favRect.component3()).floatValue();
         if (fx <= mx ? mx <= fx + fw : false) {
            float favStripTop = this.favStripBottom - 26.0F;
            if ((my <= this.favStripBottom ? favStripTop <= my : false) && button == 0) {
               CosmeticBlocks.INSTANCE.setBlock(favId);
               this.onClose();
               return true;
            }
         }
      }

      float minX = this.px;
      if (mx <= this.px + this.getPanelW() ? minX <= mx : false) {
         minX = this.listTop;
         if (my <= this.listBottom ? minX <= my : false) {
            int index = (int)((my - this.listTop + this.scroll) / this.rowH);
            if (0 <= index ? index < this.filtered.size() : false) {
               String blockId = this.filtered.get(index);
               switch (button) {
                  case 0:
                     CosmeticBlocks.INSTANCE.setBlock(blockId);
                     this.onClose();
                     return true;
                  case 1:
                     if (CosmeticBlocks.INSTANCE.isFavorite(blockId)) {
                        CosmeticBlocks.INSTANCE.removeFavorite(blockId);
                     } else {
                        CosmeticBlocks.INSTANCE.addFavorite(blockId);
                     }

                     return true;
               }
            }
         }
      }

      if (!(mx < this.px) && !(mx > this.px + this.getPanelW()) && !(my < this.py) && !(my > this.py + this.getPanelH())) {
         return true;
      }

      this.onClose();
      return true;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      this.scroll = this.scroll - (float)scrollY * this.rowH * 3.0F;
      return true;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      switch (event.key()) {
         case 256:
            this.onClose();
            return true;
         case 257:
         case 335:
            if (!this.filtered.isEmpty()) {
               CosmeticBlocks.INSTANCE.setBlock(this.filtered.get(RangesKt.coerceIn(this.highlightIdx, 0, this.filtered.size() - 1)));
               this.onClose();
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
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String s = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(s, "codepointAsString(...)");
      if (s.length() > 0) {
         CharSequence chars = s;

         boolean printable;
         label31: {
            for (int index = 0; index < chars.length(); index++) {
               char it = chars.charAt(index);
               if (it < ' ') {
                  printable = false;
                  break label31;
               }
            }

            printable = true;
         }

         if (printable) {
            this.search = this.search + s;
            this.applyFilter(false);
            return true;
         }
      }

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
}
