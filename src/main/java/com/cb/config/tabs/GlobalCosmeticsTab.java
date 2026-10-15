package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.cosmetic.GlobalRemap;
import com.cb.cosmetic.WaterConfig;
import com.cb.gui.screens.GlobalRemapEditorScreen;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.ColorSetting;
import com.cb.setting.SettingKt;
import com.cb.utils.ChatUtil;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public final class GlobalCosmeticsTab extends SettingsTab {
   @NotNull
   public static final GlobalCosmeticsTab INSTANCE = new GlobalCosmeticsTab();
   @NotNull
   private static final BooleanSetting allowWaterColor = SettingKt.onChange(
      INSTANCE.bool("Allow Water Color", "Tints water with a custom colour for configs that do not set their own.", false),
      it -> GlobalCosmeticsTab.onGlobalChanged(it)
   );
   @NotNull
   private static final ColorSetting rgbWaterColor = SettingKt.visibleWhen(
      SettingKt.onChange(INSTANCE.color("RGB Water Color", "Custom water tint used when a config does not set its own.", -12618012),
         it -> GlobalCosmeticsTab.onGlobalColorChanged(it)),
      GlobalCosmeticsTab::allowWaterColorVisible
   );
   @NotNull
   private static final BooleanSetting lavaAsWater = SettingKt.onChange(
      INSTANCE.bool("Lava as Water", "Makes lava look like water for configs that do not set their own.", false),
      it -> GlobalCosmeticsTab.onGlobalChanged(it)
   );
   @NotNull
   private static final BooleanSetting globalRemapEnabled = SettingKt.onChange(
      INSTANCE.bool("Enabled", "Render all blocks of a source type as their mapped target.", false), GlobalCosmeticsTab::onRemapEnabledChanged
   );
   @NotNull
   private static final BooleanSetting globalRemapKeepShape = SettingKt.onChange(
      INSTANCE.bool("Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false),
      GlobalCosmeticsTab::onRemapKeepShapeChanged
   );
   @NotNull
   private static final ButtonSetting openEditor = INSTANCE.button(
      "Remap Editor", "Add or remove block remaps with a searchable picker.", "Open", GlobalCosmeticsTab::openEditorAction
   );
   @NotNull
   private static final ButtonSetting export = INSTANCE.button("Export", "Copy all remaps to the clipboard as JSON.", "Copy", GlobalCosmeticsTab::exportAction);
   @NotNull
   private static final ButtonSetting importBtn = INSTANCE.button("Import", "Replace all remaps from clipboard JSON.", "Paste", GlobalCosmeticsTab::importAction);

   private GlobalCosmeticsTab() {
      super("Global Cosmetics");
   }

   @NotNull
   public final BooleanSetting getAllowWaterColor() {
      return allowWaterColor;
   }

   @NotNull
   public final ColorSetting getRgbWaterColor() {
      return rgbWaterColor;
   }

   @NotNull
   public final BooleanSetting getLavaAsWater() {
      return lavaAsWater;
   }

   @NotNull
   public final BooleanSetting getGlobalRemapEnabled() {
      return globalRemapEnabled;
   }

   @NotNull
   public final BooleanSetting getGlobalRemapKeepShape() {
      return globalRemapKeepShape;
   }

   @NotNull
   public final ButtonSetting getOpenEditor() {
      return openEditor;
   }

   @NotNull
   public final ButtonSetting getExport() {
      return export;
   }

   @NotNull
   public final ButtonSetting getImport() {
      return importBtn;
   }

   private static final boolean allowWaterColorVisible() {
      return allowWaterColor.getValue();
   }

   private static final Unit onGlobalChanged(boolean it) {
      WaterConfig.INSTANCE.onGlobalChanged$cb_client();
      return Unit.INSTANCE;
   }

   private static final Unit onGlobalColorChanged(int it) {
      WaterConfig.INSTANCE.onGlobalChanged$cb_client();
      return Unit.INSTANCE;
   }

   private static final Unit onRemapEnabledChanged(boolean it) {
      GlobalRemap.INSTANCE.rebuild$cb_client();
      return Unit.INSTANCE;
   }

   private static final Unit onRemapKeepShapeChanged(boolean it) {
      GlobalRemap.INSTANCE.rebuild$cb_client();
      return Unit.INSTANCE;
   }

   private static final void openEditorOnClientThread(Minecraft mc) {
      mc.setScreenAndShow(new GlobalRemapEditorScreen(GlobalRemap.INSTANCE, true));
   }

   private static final Unit openEditorAction() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      mc.execute(() -> openEditorOnClientThread(mc));
      return Unit.INSTANCE;
   }

   private static final Unit exportAction() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      mc.keyboardHandler.setClipboard(GlobalRemap.INSTANCE.exportJson());
      ChatUtil.INSTANCE.success("Copied " + GlobalRemap.INSTANCE.sources().size() + " remaps to clipboard");
      return Unit.INSTANCE;
   }

   private static final Unit importAction() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      String json = mc.keyboardHandler.getClipboard();
      Intrinsics.checkNotNullExpressionValue(json, "getClipboard(...)");
      if (GlobalRemap.INSTANCE.importJson(json)) {
         ChatUtil.INSTANCE.success("Imported " + GlobalRemap.INSTANCE.sources().size() + " remaps");
      } else {
         ChatUtil.INSTANCE.error("Clipboard is not a valid remap export");
      }

      return Unit.INSTANCE;
   }

   static {
      INSTANCE.subcategory("Water");
      INSTANCE.subcategory("Block Remaps");
      INSTANCE.subcategory("Editor");
      INSTANCE.subcategory("Share");
   }
}
