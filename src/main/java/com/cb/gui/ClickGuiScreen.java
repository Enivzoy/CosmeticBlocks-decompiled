package com.cb.gui;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.config.Tabs;
import com.cb.config.tabs.GuiTab;
import com.cb.render.NVGRenderer;
import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
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
      Iterable tabs = Tabs.INSTANCE.getAll();
      ClickGuiScreen screen = this;
      Iterable tabsToMap = tabs;
      var panels = new ArrayList(CollectionsKt.collectionSizeOrDefault(tabs, 10));
      int index = 0;

      for (Object item : tabsToMap) {
         int currentIndex = index++;
         if (currentIndex < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         SettingsTab tab = (SettingsTab)item;
         int panelIndex = currentIndex;
         panels.add(new SettingsPanel(this, tab, 16.0F + panelIndex * 254.0F, 16.0F));
      }

      this.panels = CollectionsKt.toMutableList(panels);
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
         float mouseX = (float)minecraft.mouseHandler.xpos() * fbScaleX / this.uiScale;
         float mouseY = (float)minecraft.mouseHandler.ypos() * fbScaleY / this.uiScale;
         if (NVGRenderer.INSTANCE.beginFrame(fbWidth, fbHeight, this.uiScale)) {
            try {
                this.tooltipRequest = null;
                NVGRenderer.INSTANCE.rect(0.0F, 0.0F, this.uiWidth, this.uiHeight, 1711276032, 0.0F);

                for (SettingsPanel panel : this.panels) {
                   panel.render(mouseX, mouseY, delta);
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
      for (SettingsPanel panel : this.panels) {
         panel.persistState();
      }

      Config.INSTANCE.save();
      super.onClose();
   }

   private final float toUi(double scaled) {
      Window window = Minecraft.getInstance().getWindow();
      Intrinsics.checkNotNullExpressionValue(window, "getWindow(...)");
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

      for (SettingsPanel panel : this.panels) {
         panel.mouseReleased(event.button());
      }

      return true;
   }

   @Override
   public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
      Intrinsics.checkNotNullParameter(event, "event");
      float mouseX = this.toUi(event.x());
      float mouseY = this.toUi(event.y());

      for (SettingsPanel panel : this.panels) {
         panel.mouseDragged(mouseX, mouseY);
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
      String text = event.codepointAsString();
      Intrinsics.checkNotNullExpressionValue(text, "codepointAsString(...)");

      for (SettingsPanel panel : CollectionsKt.asReversedMutable(this.panels)) {
         if (panel.charTyped(text)) {
            return true;
         }
      }

      return false;
   }
}
