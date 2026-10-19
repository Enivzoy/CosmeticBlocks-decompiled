package me.enivzoy.cosmeticblocksreborn.config.tabs

import me.enivzoy.cosmeticblocksreborn.config.SettingsTab

class HelpTab private constructor() : SettingsTab("Help") {
   init {
      this.label("--- Getting Started ---", false)
      this.label("Turn on Cosmetic Blocks in the General section", false)
      this.label("Open Block Selector keybind to pick a block", false)
      this.label("Right-click in the world to replace blocks", false)
      this.label("--- Place Mode ---", true)
      this.label("With Place Mode on, right-click places on air", false)
      this.label("Without Place Mode, right-click replaces existing blocks", false)
      this.label("--- Edit Mode ---", true)
      this.label("Right-click to place or replace with your block", false)
      this.label("Left-click to remove a cosmetic block", false)
      this.label("Middle-click to pick a block from the world", false)
      this.label("--- World Edit ---", true)
      this.label("Click to set Pos1 and Pos2 corners of a region", false)
      this.label("Fills the region with your selected block", false)
      this.label("Replaces only matching blocks in the region", false)
      this.label("Removes all cosmetic blocks in the region", false)
      this.label("WE Fill on Click auto-fills when you set Pos2", false)
      this.label("--- Ruler ---", true)
      this.label("Right-click start, right-click end to place a line", false)
      this.label("Scroll to change how far the ruler reaches", false)
      this.label("Left-click to cancel the current line", false)
      this.label("--- Block Tools ---", true)
      this.label("Block Selector: searchable grid with favorites tab", false)
      this.label("Block Wheel: hold keybind for a radial menu of favorites", false)
      this.label("--- Other Settings ---", true)
      this.label("Keep Block Size preserves stair/slab shape", false)
      this.label("Lava as Water makes lava look like water", false)
      this.label("Active Config switches between saved block layouts", false)
      this.label("Only in Dungeons limits blocks to dungeon areas", false)
      this.label("Room Scanning auto-detects rooms for per-room saves", false)
   }

   companion object {
      @JvmField
      val INSTANCE: HelpTab = HelpTab()
   }
}
