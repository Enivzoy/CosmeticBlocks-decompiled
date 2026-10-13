package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.cosmetic.GlobalRemap;
import com.cb.gui.screens.GlobalRemapEditorScreen;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.SettingKt;
import com.cb.utils.ChatUtil;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public final class BlockRemapsTab extends SettingsTab {
   @NotNull
   public static final BlockRemapsTab INSTANCE = new BlockRemapsTab();
   @NotNull
   private static final BooleanSetting enabled = SettingKt.onChange(
      INSTANCE.bool("Enabled", "Render all blocks of a source type as their mapped target.", false), BlockRemapsTab::onEnabledChanged
   );
   @NotNull
   private static final BooleanSetting keepShape = SettingKt.onChange(
      INSTANCE.bool("Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false), BlockRemapsTab::onKeepShapeChanged
   );
   @NotNull
   private static final ButtonSetting openEditor = INSTANCE.button(
      "Remap Editor", "Add or remove block remaps with a searchable picker.", "Open", BlockRemapsTab::openEditorAction
   );
   @NotNull
   private static final ButtonSetting export = INSTANCE.button("Export", "Copy all remaps to the clipboard as JSON.", "Copy", BlockRemapsTab::exportAction);
   @NotNull
   private static final ButtonSetting importBtn = INSTANCE.button("Import", "Replace all remaps from clipboard JSON.", "Paste", BlockRemapsTab::importAction);

   private BlockRemapsTab() {
      super("Block Remaps");
   }

   @NotNull
   public final BooleanSetting getEnabled() {
      return enabled;
   }

   @NotNull
   public final BooleanSetting getKeepShape() {
      return keepShape;
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

   private static final Unit onEnabledChanged(boolean it) {
      GlobalRemap.INSTANCE.rebuild$cb_client();
      return Unit.INSTANCE;
   }

   private static final Unit onKeepShapeChanged(boolean it) {
      GlobalRemap.INSTANCE.rebuild$cb_client();
      return Unit.INSTANCE;
   }

   private static final void openEditorOnClientThread(Minecraft mc) {
      mc.setScreenAndShow(new GlobalRemapEditorScreen());
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
      INSTANCE.subcategory("General");
      INSTANCE.subcategory("Editor");
      INSTANCE.subcategory("Share");
   }
}
