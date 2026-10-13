package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004",
   d2 = {"Lcom/cb/config/tabs/HelpTab;", "Lcom/cb/config/SettingsTab;", "<init>", "()V", "cb_client"}
)
public final class HelpTab extends SettingsTab {
   @NotNull
   public static final HelpTab INSTANCE = new HelpTab();

   private HelpTab() {
      super("Help");
   }

   static {
      INSTANCE.label("--- Getting Started ---", false);
      INSTANCE.label("Turn on Cosmetic Blocks in the General section", false);
      INSTANCE.label("Open Block Selector keybind to pick a block", false);
      INSTANCE.label("Right-click in the world to replace blocks", false);
      INSTANCE.label("--- Place Mode ---", true);
      INSTANCE.label("With Place Mode on, right-click places on air", false);
      INSTANCE.label("Without Place Mode, right-click replaces existing blocks", false);
      INSTANCE.label("--- Edit Mode ---", true);
      INSTANCE.label("Right-click to place or replace with your block", false);
      INSTANCE.label("Left-click to remove a cosmetic block", false);
      INSTANCE.label("Middle-click to pick a block from the world", false);
      INSTANCE.label("--- World Edit ---", true);
      INSTANCE.label("Click to set Pos1 and Pos2 corners of a region", false);
      INSTANCE.label("Fills the region with your selected block", false);
      INSTANCE.label("Replaces only matching blocks in the region", false);
      INSTANCE.label("Removes all cosmetic blocks in the region", false);
      INSTANCE.label("WE Fill on Click auto-fills when you set Pos2", false);
      INSTANCE.label("--- Ruler ---", true);
      INSTANCE.label("Right-click start, right-click end to place a line", false);
      INSTANCE.label("Scroll to change how far the ruler reaches", false);
      INSTANCE.label("Left-click to cancel the current line", false);
      INSTANCE.label("--- Block Tools ---", true);
      INSTANCE.label("Block Selector: searchable grid with favorites tab", false);
      INSTANCE.label("Block Wheel: hold keybind for a radial menu of favorites", false);
      INSTANCE.label("--- Other Settings ---", true);
      INSTANCE.label("Keep Block Size preserves stair/slab shape", false);
      INSTANCE.label("Lava as Water makes lava look like water", false);
      INSTANCE.label("Active Config switches between saved block layouts", false);
      INSTANCE.label("Only in Dungeons limits blocks to dungeon areas", false);
      INSTANCE.label("Room Scanning auto-detects rooms for per-room saves", false);
   }
}
