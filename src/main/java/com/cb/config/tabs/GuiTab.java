package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.setting.KeybindSetting;
import org.jetbrains.annotations.NotNull;

public final class GuiTab extends SettingsTab {
   @NotNull
   public static final GuiTab INSTANCE = new GuiTab();
   @NotNull
   private static final KeybindSetting openGuiKeybind = INSTANCE.keybind("Open GUI Keybind", "Opens this settings screen.", 344);
    @NotNull
    private static final KeybindSetting openCliKeybind = INSTANCE.keybind("Open CLI Keybind", "Opens the command-line overlay.", -1);
    @NotNull
    private static final KeybindSetting freecamKeybind = INSTANCE.keybind("Freecam Keybind", "Toggles the freecam.", 70);

    private GuiTab() {
       super("GUI");
    }

    @NotNull
    public final KeybindSetting getOpenGuiKeybind() {
       return openGuiKeybind;
    }

    @NotNull
    public final KeybindSetting getOpenCliKeybind() {
       return openCliKeybind;
    }

    @NotNull
    public final KeybindSetting getFreecamKeybind() {
       return freecamKeybind;
    }

   static {
      INSTANCE.subcategory("Keybinds");
   }
}
