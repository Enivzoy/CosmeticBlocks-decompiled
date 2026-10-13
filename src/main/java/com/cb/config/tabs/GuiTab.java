package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.setting.KeybindSetting;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007¨\u0006\n",
   d2 = {
         "Lcom/cb/config/tabs/GuiTab;",
         "Lcom/cb/config/SettingsTab;",
         "<init>",
         "()V",
         "openGuiKeybind",
         "Lcom/cb/setting/KeybindSetting;",
         "getOpenGuiKeybind",
         "()Lcom/cb/setting/KeybindSetting;",
         "openCliKeybind",
         "getOpenCliKeybind",
         "cb_client"
   }
)
public final class GuiTab extends SettingsTab {
   @NotNull
   public static final GuiTab INSTANCE = new GuiTab();
   @NotNull
   private static final KeybindSetting openGuiKeybind = INSTANCE.keybind("Open GUI Keybind", "Opens this settings screen.", 344);
   @NotNull
   private static final KeybindSetting openCliKeybind = INSTANCE.keybind("Open CLI Keybind", "Opens the command-line overlay.", 0);

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

   static {
      INSTANCE.subcategory("Keybinds");
   }
}
