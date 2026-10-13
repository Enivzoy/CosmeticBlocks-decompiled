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
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0011\u0010\u001c\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0011\u0010\u001e\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010 \u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0011\u0010\"\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010$\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0007R\u0011\u0010&\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0007R\u0011\u0010(\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010,\u001a\u00020-¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u00100\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b1\u0010\u0007R\u0011\u00102\u001a\u00020)¢\u0006\b\n\u0000\u001a\u0004\b3\u0010+R\u0011\u00104\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b5\u0010\u0015¨\u00066",
   d2 = {
         "Lcom/cb/config/tabs/CosmeticBlocksTab;",
         "Lcom/cb/config/SettingsTab;",
         "<init>",
         "()V",
         "enabled",
         "Lcom/cb/setting/BooleanSetting;",
         "getEnabled",
         "()Lcom/cb/setting/BooleanSetting;",
         "keepBlockSize",
         "getKeepBlockSize",
         "onlyInDungeons",
         "getOnlyInDungeons",
         "roomScanning",
         "getRoomScanning",
         "placeMode",
         "getPlaceMode",
         "weFillOnClick",
         "getWeFillOnClick",
         "fillModeLimit",
         "Lcom/cb/setting/NumberSetting;",
         "getFillModeLimit",
         "()Lcom/cb/setting/NumberSetting;",
         "placeModeKeybind",
         "Lcom/cb/setting/KeybindSetting;",
         "getPlaceModeKeybind",
         "()Lcom/cb/setting/KeybindSetting;",
         "editModeKeybind",
         "getEditModeKeybind",
         "worldEditKeybind",
         "getWorldEditKeybind",
         "blockSelectorKeybind",
         "getBlockSelectorKeybind",
         "blockWheelKeybind",
         "getBlockWheelKeybind",
         "rulerKeybind",
         "getRulerKeybind",
         "lavaAsWater",
         "getLavaAsWater",
         "customWaterColor",
         "getCustomWaterColor",
         "waterColor",
         "Lcom/cb/setting/ColorSetting;",
         "getWaterColor",
         "()Lcom/cb/setting/ColorSetting;",
         "activeConfig",
         "Lcom/cb/setting/ConfigListSetting;",
         "getActiveConfig",
         "()Lcom/cb/setting/ConfigListSetting;",
         "showReplacedBlocks",
         "getShowReplacedBlocks",
         "overlayColor",
         "getOverlayColor",
         "overlayRange",
         "getOverlayRange",
         "cb_client"
   }
)
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
