package com.cb.config.tabs;

import com.cb.config.Config;
import com.cb.config.SettingsTab;
import com.cb.cosmetic.ConfigRemap;
import com.cb.cosmetic.ConfigSwitcher;
import com.cb.cosmetic.FloorUtil;
import com.cb.cosmetic.ReplacementIO;
import com.cb.cosmetic.ReplacementStore;
import com.cb.cosmetic.SaveScheduler;
import com.cb.gui.screens.GlobalRemapEditorScreen;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.ColorSetting;
import com.cb.setting.ConfigListSetting;
import com.cb.setting.SettingKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DungeonConfigTab extends SettingsTab {
   @NotNull
   public static final DungeonConfigTab INSTANCE = new DungeonConfigTab();
   private static boolean floorReady;
   private static boolean floorSyncing;
   private static boolean syncing;
   @NotNull
   private static final ConfigListSetting activeConfig = SettingKt.optionLabel(
      SettingKt.optionColor(
         INSTANCE.configList("Active Config", "Switch between saved block layouts.", "default", DungeonConfigTab::listConfigOptions),
         DungeonConfigTab::activeConfigColor
      ),
      DungeonConfigTab::activeConfigLabel
   );
   @NotNull
   private static final ConfigListSetting configFloor = SettingKt.onChange(
      SettingKt.optionColor(
         INSTANCE.configList("Config Floor", "Which dungeon floor this config is for (auto-loaded on entry).", "None", DungeonConfigTab::listFloorOptions),
         DungeonConfigTab::floorOptionColor
      ),
      DungeonConfigTab::onFloorChanged
   );
   @NotNull
   private static final BooleanSetting allowWaterColor = SettingKt.onChange(
      INSTANCE.bool("Allow Water Color", "Tints water with a custom colour for this config.", false), it -> DungeonConfigTab.onPerConfigWaterChanged(it)
   );
   @NotNull
   private static final ColorSetting rgbWaterColor = SettingKt.visibleWhen(
      SettingKt.onChange(INSTANCE.color("RGB Water Color", "Custom water tint for this config.", -12618012), it -> DungeonConfigTab.onPerConfigWaterColorChanged(it)),
      DungeonConfigTab::allowWaterColorVisible
   );
   @NotNull
   private static final BooleanSetting lavaAsWater = SettingKt.onChange(
      INSTANCE.bool("Lava as Water", "Makes lava look like water for this config.", false), it -> DungeonConfigTab.onPerConfigWaterChanged(it)
   );
   @NotNull
   private static final BooleanSetting remapEnabled = SettingKt.onChange(
      INSTANCE.bool("Block Remaps", "Render all blocks of a source type as their mapped target for this config.", false),
      DungeonConfigTab::onRemapEnabledChanged
   );
   @NotNull
   private static final BooleanSetting remapKeepShape = SettingKt.visibleWhen(
      SettingKt.onChange(
         INSTANCE.bool("Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false),
         DungeonConfigTab::onRemapKeepShapeChanged
      ),
      DungeonConfigTab::remapEnabledVisible
   );
   @NotNull
   private static final ButtonSetting openEditor = SettingKt.visibleWhen(
      INSTANCE.button("Remap Editor", "Add or remove block remaps with a searchable picker.", "Open", DungeonConfigTab::openEditorAction),
      DungeonConfigTab::remapEnabledVisible
   );

   private DungeonConfigTab() {
      super("Dungeon Config");
   }

   @NotNull
   public final ConfigListSetting getActiveConfig() {
      return activeConfig;
   }

   @NotNull
   public final ConfigListSetting getConfigFloor() {
      return configFloor;
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
   public final BooleanSetting getRemapEnabled() {
      return remapEnabled;
   }

   @NotNull
   public final BooleanSetting getRemapKeepShape() {
      return remapKeepShape;
   }

   @NotNull
   public final ButtonSetting getOpenEditor() {
      return openEditor;
   }

   public static final void applyWater$cb_client(@Nullable Boolean allow, @Nullable Integer rgb, @Nullable Boolean lava) {
      syncing = true;

      try {
         if (allow != null) {
            allowWaterColor.setValue(allow);
         }

         if (rgb != null) {
            rgbWaterColor.setValue(rgb);
         }

         if (lava != null) {
            lavaAsWater.setValue(lava);
         }
      } finally {
         syncing = false;
      }
   }

   public static final void applyRemap$cb_client(boolean enabled, boolean keepShape) {
      syncing = true;

      try {
         remapEnabled.setValue(enabled);
         remapKeepShape.setValue(keepShape);
      } finally {
         syncing = false;
      }
   }

   public static final void beginSync$cb_client() {
      syncing = true;
   }

   public static final void endSync$cb_client() {
      syncing = false;
   }

   private static final Unit onPerConfigWaterChanged(boolean it) {
      if (!syncing) {
         com.cb.cosmetic.WaterConfig.INSTANCE.onPerConfigChanged$cb_client();
      }

      return Unit.INSTANCE;
   }

   private static final Unit onPerConfigWaterColorChanged(int it) {
      if (!syncing) {
         com.cb.cosmetic.WaterConfig.INSTANCE.onPerConfigChanged$cb_client();
      }

      return Unit.INSTANCE;
   }

   private static final Unit onRemapEnabledChanged(boolean it) {
      if (!syncing) {
         ConfigRemap.INSTANCE.onSettingChanged$cb_client(it, remapKeepShape.getValue());
      }

      return Unit.INSTANCE;
   }

   private static final Unit onRemapKeepShapeChanged(boolean it) {
      if (!syncing) {
         ConfigRemap.INSTANCE.onSettingChanged$cb_client(remapEnabled.getValue(), it);
      }

      return Unit.INSTANCE;
   }

   private static final void openEditorOnClientThread(Minecraft mc) {
      mc.setScreenAndShow(new GlobalRemapEditorScreen(ConfigRemap.INSTANCE, false));
   }

   private static final Unit openEditorAction() {
      Minecraft mc = Minecraft.getInstance();
      Intrinsics.checkNotNullExpressionValue(mc, "getInstance(...)");
      mc.execute(() -> openEditorOnClientThread(mc));
      return Unit.INSTANCE;
   }

   private static final List listConfigOptions() {
      List<String> configs = ConfigSwitcher.INSTANCE.listConfigs();
      List<String> sorted = new ArrayList<>(configs);
      sorted.sort((a, b) -> Integer.compare(FloorUtil.floorNumber(FloorUtil.floorOfConfig(b)), FloorUtil.floorNumber(FloorUtil.floorOfConfig(a))));
      return sorted;
   }

   private static final List listFloorOptions() {
      return FloorUtil.listFloorOptions();
   }

   private static final String activeConfigLabel(String name) {
      return FloorUtil.configLabel(name);
   }

   private static final int activeConfigColor(String name) {
      return FloorUtil.colorForFloor(FloorUtil.floorOfConfig(name));
   }

   private static final int floorOptionColor(String label) {
      return FloorUtil.colorForFloor(label);
   }

   private static final Unit onFloorChanged(String value) {
      if (floorReady && !floorSyncing) {
         String normalized = FloorUtil.normalize(value);
         ReplacementStore.INSTANCE.setFloor$cb_client(normalized == null || normalized.equals(FloorUtil.NONE) ? null : normalized);
         SaveScheduler.INSTANCE.awaitPendingSaves$cb_client();
         ReplacementIO.INSTANCE.saveToFileSync$cb_client(
            ReplacementStore.INSTANCE.getConfigFile$cb_client(DungeonConfigTab.INSTANCE.getActiveConfig().getValue())
         );
         Config.INSTANCE.save();
      }

      return Unit.INSTANCE;
   }

   public static final void syncFloorFromStore() {
      floorSyncing = true;
      try {
         configFloor.setValue(FloorUtil.displayOrNone(ReplacementStore.INSTANCE.getFloor$cb_client()));
      } finally {
         floorSyncing = false;
      }
   }

   public static final void markFloorReady() {
      floorReady = true;
   }

   private static final boolean allowWaterColorVisible() {
      return allowWaterColor.getValue();
   }

   private static final boolean remapEnabledVisible() {
      return remapEnabled.getValue();
   }

   static {
      INSTANCE.subcategory("Config");
      INSTANCE.subcategory("Water");
      INSTANCE.subcategory("Block Remaps");
   }
}
