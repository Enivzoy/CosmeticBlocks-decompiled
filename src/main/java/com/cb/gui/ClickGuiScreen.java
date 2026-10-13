package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.config.tabs.GuiTab;
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

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0006H\u0016J\u000e\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u0014J \u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J(\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020$2\u0006\u0010 \u001a\u00020$2\u0006\u0010%\u001a\u00020\u0006H\u0016J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020\u001aH\u0016J\u0010\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+H\u0002J\u0018\u0010,\u001a\u00020'2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020'H\u0016J\u0010\u00100\u001a\u00020'2\u0006\u0010-\u001a\u00020.H\u0016J \u00101\u001a\u00020'2\u0006\u0010-\u001a\u00020.2\u0006\u00102\u001a\u00020+2\u0006\u00103\u001a\u00020+H\u0016J(\u00104\u001a\u00020'2\u0006\u0010\u001f\u001a\u00020+2\u0006\u0010 \u001a\u00020+2\u0006\u00105\u001a\u00020+2\u0006\u00106\u001a\u00020+H\u0016J\u0010\u00107\u001a\u00020'2\u0006\u0010-\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020'2\u0006\u0010-\u001a\u00020:H\u0016R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006;",
   d2 = {
         "Lcom/cb/gui/ClickGuiScreen;",
         "Lnet/minecraft/client/gui/screens/Screen;",
         "Lcom/cb/gui/NvgScreen;",
         "<init>",
         "()V",
         "value",
         "",
         "uiScale",
         "getUiScale",
         "()F",
         "uiWidth",
         "getUiWidth",
         "uiHeight",
         "getUiHeight",
         "panels",
         "",
         "Lcom/cb/gui/SettingsPanel;",
         "openedAtNanos",
         "",
         "tooltipRequest",
         "",
         "tooltipCurrent",
         "tooltipTimer",
         "tooltipX",
         "tooltipY",
         "renderOverlay",
         "",
         "delta",
         "requestTooltip",
         "text",
         "renderTooltip",
         "mouseX",
         "mouseY",
         "extractBackground",
         "extractor",
         "Lnet/minecraft/client/gui/GuiGraphicsExtractor;",
         "",
         "partialTick",
         "isPauseScreen",
         "",
         "onClose",
         "toUi",
         "scaled",
         "",
         "mouseClicked",
         "event",
         "Lnet/minecraft/client/input/MouseButtonEvent;",
         "doubled",
         "mouseReleased",
         "mouseDragged",
         "dragX",
         "dragY",
         "mouseScrolled",
         "scrollX",
         "scrollY",
         "keyPressed",
         "Lnet/minecraft/client/input/KeyEvent;",
         "charTyped",
         "Lnet/minecraft/client/input/CharacterEvent;",
         "cb_client"
   }
)
@SourceDebugExtension(
   "SMAP\nClickGuiScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClickGuiScreen.kt\ncom/cb/gui/ClickGuiScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,201:1\n1596#2:202\n1629#2,4:203\n1915#2,2:207\n1915#2,2:209\n1915#2,2:211\n1915#2,2:213\n*S KotlinDebug\n*F\n+ 1 ClickGuiScreen.kt\ncom/cb/gui/ClickGuiScreen\n*L\n38#1:202\n38#1:203,4\n81#1:207,2\n127#1:209,2\n155#1:211,2\n162#1:213,2\n*E\n"
)
public final class ClickGuiScreen extends Screen implements NvgScreen {
   private float uiScale = 1.0F;
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

   public ClickGuiScreen() {
      super(Component.literal("Cosmetic Blocks"));
      Iterable $this$mapIndexed$iv = Tabs.INSTANCE.getAll();
      ClickGuiScreen var13 = this;
      int $i$f$mapIndexed = 0;
      Iterable $this$mapIndexedTo$iv$iv = $this$mapIndexed$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
      int $i$f$mapIndexedTo = 0;
      int index$iv$iv = 0;

      for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
         int var9 = index$iv$iv++;
         if (var9 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         SettingsTab tab = (SettingsTab)item$iv$iv;
         int index = var9;
         Collection var14 = destination$iv$iv;
         int var12/* $VF was: $i$a$-mapIndexed-ClickGuiScreen$panels$1 */ = 0;
         var14.add(new SettingsPanel(this, tab, 16.0F + index * 254.0F, 16.0F));
      }

      this.panels = CollectionsKt.toMutableList(destination$iv$iv);
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

   @Override
   public void renderOverlay(float delta) {
      Minecraft var10000 = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(var10000, "getInstance(...)");
      Minecraft minecraft = var10000;
      Window var20 = minecraft.getWindow();
      Intrinsics.checkNotNullExpressionValue(var20, "getWindow(...)");
      Window window = var20;
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
         float mouseX = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
         float mouseY = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
               this.tooltipRequest = null;
               NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, 1711276032, 0.0F);
               Iterable $this$forEach$iv = this.panels;
               int $i$f$forEach = 0;

               for (Object element$iv : $this$forEach$iv) {
                  SettingsPanel it = (SettingsPanel)element$iv;
                  int var17/* $VF was: $i$a$-forEach-ClickGuiScreen$renderOverlay$1 */ = 0;
                  it.render(mouseX, mouseY, delta);
               }

               this.renderTooltip(mouseX, mouseY, delta);
            } finally {
               NVGRenderer.INSTANCE.endFrame();
            }
         }
      }
   }

   public final void requestTooltip(@NotNull String text) {
      Intrinsics.checkNotNullParameter(text, "text");
      this.tooltipRequest = text;
   }

   private final void renderTooltip(float mouseX, float mouseY, float delta) {
      String request = this.tooltipRequest;
      if (!Intrinsics.areEqual(request, this.tooltipCurrent)) {
         this.tooltipCurrent = request;
         this.tooltipTimer = 0.0F;
      }

      if (request != null) {
         this.tooltipTimer += delta;
         if (!(this.tooltipTimer < 0.55F)) {
            float size = 12.0F;
            float textWidth = NVGRenderer.INSTANCE.textWidth(request, size, null);
            float boxWidth = textWidth + 16.0F;
            float boxHeight = 22.0F;
            this.tooltipX = RangesKt.coerceAtMost(mouseX + 12.0F, this.uiWidth - boxWidth - 4.0F);
            this.tooltipY = RangesKt.coerceAtMost(mouseY + 14.0F, this.uiHeight - boxHeight - 4.0F);
            NVGRenderer.INSTANCE.shadow(this.tooltipX, this.tooltipY, boxWidth, boxHeight, 5.0F, 8.0F, 1711276032);
            NVGRenderer.INSTANCE.rect(this.tooltipX, this.tooltipY, boxWidth, boxHeight, -14935012, 5.0F);
            NVGRenderer.INSTANCE.rectOutline(this.tooltipX, this.tooltipY, boxWidth, boxHeight, 352321535, 5.0F, 0.0F);
            NVGRenderer.INSTANCE.text(request, this.tooltipX + 8.0F, this.tooltipY + boxHeight / 2.0F, size, -6513508, null);
         }
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
   public void onClose() {
      Iterable $this$forEach$iv = this.panels;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingsPanel it = (SettingsPanel)element$iv;
         int var6/* $VF was: $i$a$-forEach-ClickGuiScreen$onClose$1 */ = 0;
         it.persistState();
      }

      Config.INSTANCE.save();
      super.onClose();
   }

   private final float toUi(double scaled) {
      Window var10000 = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(var10000, "getWindow(...)");
      Window window = var10000;
      return (float)(scaled * window.getGuiScale() / this.uiScale);
   }

   @Override
   public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubled) {
      Intrinsics.checkNotNullParameter(event, "event");
      float mouseX = this.toUi(event.x());
      float mouseY = this.toUi(event.y());

      for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
         if (panel.mouseClicked(mouseX, mouseY, event.button())) {
            this.panels.remove(panel);
            this.panels.add(panel);
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean mouseReleased(@NotNull MouseButtonEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      Iterable $this$forEach$iv = this.panels;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingsPanel it = (SettingsPanel)element$iv;
         int var7/* $VF was: $i$a$-forEach-ClickGuiScreen$mouseReleased$1 */ = 0;
         it.mouseReleased(event.button());
      }

      return true;
   }

   @Override
   public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
      Intrinsics.checkNotNullParameter(event, "event");
      float mouseX = this.toUi(event.x());
      float mouseY = this.toUi(event.y());
      Iterable $this$forEach$iv = this.panels;
      int $i$f$forEach = 0;

      for (Object element$iv : $this$forEach$iv) {
         SettingsPanel it = (SettingsPanel)element$iv;
         int var13/* $VF was: $i$a$-forEach-ClickGuiScreen$mouseDragged$1 */ = 0;
         it.mouseDragged(mouseX, mouseY);
      }

      return true;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
      float uiX = this.toUi(mouseX);
      float uiY = this.toUi(mouseY);

      for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
         if (panel.mouseScrolled(uiX, uiY, (float)scrollY)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyPressed(@NotNull KeyEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");

      for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
         if (panel.keyPressed(event.key(), event.modifiers())) {
            return true;
         }
      }

      if (event.key() == 256) {
         this.onClose();
         return true;
      }

      int openKey = GuiTab.INSTANCE.getOpenGuiKeybind().getValue().intValue();
      if (openKey != -1 && event.key() == openKey) {
         if (System.nanoTime() - this.openedAtNanos > 600000000L) {
            this.onClose();
         }

         return true;
      } else {
         return super.keyPressed(event);
      }
   }

   @Override
   public boolean charTyped(@NotNull CharacterEvent event) {
      Intrinsics.checkNotNullParameter(event, "event");
      String var10000 = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(var10000, "codepointAsString(...)");
      String text = var10000;

      for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
         if (panel.charTyped(text)) {
            return true;
         }
      }

      return false;
   }
}
