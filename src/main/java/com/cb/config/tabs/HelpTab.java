/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cb/config/tabs/HelpTab;", "Lcom/cb/config/SettingsTab;", "<init>", "()V", "cb_client"})
public final class HelpTab
extends SettingsTab {
    @NotNull
    public static final HelpTab INSTANCE = new HelpTab();

    private HelpTab() {
        super("Help");
    }

    static {
        SettingsTab.label$default(INSTANCE, "--- Getting Started ---", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Turn on Cosmetic Blocks in the General section", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Open Block Selector keybind to pick a block", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Right-click in the world to replace blocks", false, 2, null);
        INSTANCE.label("--- Place Mode ---", true);
        SettingsTab.label$default(INSTANCE, "With Place Mode on, right-click places on air", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Without Place Mode, right-click replaces existing blocks", false, 2, null);
        INSTANCE.label("--- Edit Mode ---", true);
        SettingsTab.label$default(INSTANCE, "Right-click to place or replace with your block", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Left-click to remove a cosmetic block", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Middle-click to pick a block from the world", false, 2, null);
        INSTANCE.label("--- World Edit ---", true);
        SettingsTab.label$default(INSTANCE, "Click to set Pos1 and Pos2 corners of a region", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Fills the region with your selected block", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Replaces only matching blocks in the region", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Removes all cosmetic blocks in the region", false, 2, null);
        SettingsTab.label$default(INSTANCE, "WE Fill on Click auto-fills when you set Pos2", false, 2, null);
        INSTANCE.label("--- Ruler ---", true);
        SettingsTab.label$default(INSTANCE, "Right-click start, right-click end to place a line", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Scroll to change how far the ruler reaches", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Left-click to cancel the current line", false, 2, null);
        INSTANCE.label("--- Block Tools ---", true);
        SettingsTab.label$default(INSTANCE, "Block Selector: searchable grid with favorites tab", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Block Wheel: hold keybind for a radial menu of favorites", false, 2, null);
        INSTANCE.label("--- Other Settings ---", true);
        SettingsTab.label$default(INSTANCE, "Keep Block Size preserves stair/slab shape", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Lava as Water makes lava look like water", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Active Config switches between saved block layouts", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Only in Dungeons limits blocks to dungeon areas", false, 2, null);
        SettingsTab.label$default(INSTANCE, "Room Scanning auto-detects rooms for per-room saves", false, 2, null);
    }
}


