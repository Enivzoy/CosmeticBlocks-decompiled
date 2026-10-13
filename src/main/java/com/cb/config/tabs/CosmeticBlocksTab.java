package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.cosmetic.ConfigSwitcher;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ColorSetting;
import com.cb.setting.ConfigListSetting;
import com.cb.setting.KeybindSetting;
import com.cb.setting.NumberSetting;
import com.cb.setting.SettingKt;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class CosmeticBlocksTab extends SettingsTab {
   @NotNull
   public static final CosmeticBlocksTab INSTANCE = new CosmeticBlocksTab();
   @NotNull
   private static final BooleanSetting enabled = INSTANCE.bool("Enabled", "Master toggle for cosmetic block replacement.", false);
   @NotNull
   private static final BooleanSetting keepBlockSize = INSTANCE.bool("Keep Block Size", "Preserves stair/slab shape when replacing blocks.", false);
   @NotNull
   private static final BooleanSetting onlyInDungeons = INSTANCE.bool("Only in Dungeons", "Limits cosmetic blocks to dungeon areas only.", false);
   @NotNull
   private static final BooleanSetting roomScanning = INSTANCE.bool("Room Scanning", "Auto-detects rooms so blocks save per-room.", true);
   @NotNull
   private static final BooleanSetting placeMode = INSTANCE.bool("Place Mode", "Right-click places on air instead of replacing blocks.", false);
   @NotNull
   private static final BooleanSetting weFillOnClick = INSTANCE.bool("WE Fill on Click", "Auto-fills the region when you set Pos2.", false);
   @NotNull
   private static final NumberSetting fillModeLimit = INSTANCE.number("Fill Mode Limit", "Max blocks a single fill-mode flood fill can change.", 256.0, 1.0, 8192.0, 1.0, null);
   @NotNull
   private static final KeybindSetting placeModeKeybind = INSTANCE.keybind("Place Mode Keybind", "Toggles Place Mode.", 0);
   @NotNull
   private static final KeybindSetting editModeKeybind = INSTANCE.keybind("Edit Mode Keybind", "Toggles interactive Edit Mode.", 0);
   @NotNull
   private static final KeybindSetting worldEditKeybind = INSTANCE.keybind("World Edit Keybind", "Toggles region (WorldEdit) selection mode.", 0);
   @NotNull
   private static final KeybindSetting blockSelectorKeybind = INSTANCE.keybind("Block Selector Keybind", "Opens the block selector.", 0);
   @NotNull
   private static final KeybindSetting blockWheelKeybind = INSTANCE.keybind("Block Wheel Keybind", "Opens the radial favourites wheel.", 0);
   @NotNull
   private static final KeybindSetting rulerKeybind = INSTANCE.keybind("Ruler Keybind", "Toggles the ruler tool.", 0);
   @NotNull
   private static final BooleanSetting lavaAsWater = INSTANCE.bool("Lava as Water", "Makes lava look like water.", false);
   @NotNull
   private static final BooleanSetting customWaterColor = INSTANCE.bool("Custom Water Color", "Tints water with a custom colour.", false);
   @NotNull
   private static final ColorSetting waterColor = SettingKt.visibleWhen(
      INSTANCE.color("Water Color", "Custom water tint.", -12618012), CosmeticBlocksTab::customWaterColorVisible
   );
   @NotNull
   private static final ConfigListSetting activeConfig = INSTANCE.configList(
      "Active Config", "Switch between saved block layouts.", "default", CosmeticBlocksTab::listConfigOptions
   );
   @NotNull
   private static final BooleanSetting showReplacedBlocks = SettingKt.visibleWhen(
      INSTANCE.bool("Show Replaced Blocks", "Renders a wireframe over replaced blocks.", false), CosmeticBlocksTab::showReplacedBlocksVisible
   );
   @NotNull
   private static final ColorSetting overlayColor = SettingKt.visibleWhen(
      INSTANCE.color("Overlay Color", "Wireframe overlay colour.", -10172161), CosmeticBlocksTab::overlayColorVisible
   );
   @NotNull
   private static final NumberSetting overlayRange = SettingKt.visibleWhen(
      INSTANCE.number("Overlay Range", "Max distance to render overlays.", 32.0, 8.0, 64.0, 1.0, null),
      CosmeticBlocksTab::overlayRangeVisible
   );

   private CosmeticBlocksTab() {
      super("Cosmetic Blocks");
   }

   @NotNull
   public final BooleanSetting getEnabled() {
      return enabled;
   }

   @NotNull
   public final BooleanSetting getKeepBlockSize() {
      return keepBlockSize;
   }

   @NotNull
   public final BooleanSetting getOnlyInDungeons() {
      return onlyInDungeons;
   }

   @NotNull
   public final BooleanSetting getRoomScanning() {
      return roomScanning;
   }

   @NotNull
   public final BooleanSetting getPlaceMode() {
      return placeMode;
   }

   @NotNull
   public final BooleanSetting getWeFillOnClick() {
      return weFillOnClick;
   }

   @NotNull
   public final NumberSetting getFillModeLimit() {
      return fillModeLimit;
   }

   @NotNull
   public final KeybindSetting getPlaceModeKeybind() {
      return placeModeKeybind;
   }

   @NotNull
   public final KeybindSetting getEditModeKeybind() {
      return editModeKeybind;
   }

   @NotNull
   public final KeybindSetting getWorldEditKeybind() {
      return worldEditKeybind;
   }

   @NotNull
   public final KeybindSetting getBlockSelectorKeybind() {
      return blockSelectorKeybind;
   }

   @NotNull
   public final KeybindSetting getBlockWheelKeybind() {
      return blockWheelKeybind;
   }

   @NotNull
   public final KeybindSetting getRulerKeybind() {
      return rulerKeybind;
   }

   @NotNull
   public final BooleanSetting getLavaAsWater() {
      return lavaAsWater;
   }

   @NotNull
   public final BooleanSetting getCustomWaterColor() {
      return customWaterColor;
   }

   @NotNull
   public final ColorSetting getWaterColor() {
      return waterColor;
   }

   @NotNull
   public final ConfigListSetting getActiveConfig() {
      return activeConfig;
   }

   @NotNull
   public final BooleanSetting getShowReplacedBlocks() {
      return showReplacedBlocks;
   }

   @NotNull
   public final ColorSetting getOverlayColor() {
      return overlayColor;
   }

   @NotNull
   public final NumberSetting getOverlayRange() {
      return overlayRange;
   }

   private static final boolean customWaterColorVisible() {
      return customWaterColor.getValue();
   }

   private static final List listConfigOptions() {
      return ConfigSwitcher.INSTANCE.listConfigs();
   }

   private static final boolean showReplacedBlocksVisible() {
      return enabled.getValue();
   }

   private static final boolean overlayColorVisible() {
      return enabled.getValue();
   }

   private static final boolean overlayRangeVisible() {
      return enabled.getValue();
   }

   static {
      INSTANCE.subcategory("General");
      INSTANCE.subcategory("Modes");
      INSTANCE.subcategory("Keybinds");
      INSTANCE.subcategory("Water");
      INSTANCE.subcategory("Config");
      INSTANCE.subcategory("Overlay");
   }
}
