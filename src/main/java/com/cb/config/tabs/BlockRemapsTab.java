/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  org.jetbrains.annotations.NotNull
 */
package com.cb.config.tabs;

import com.cb.config.SettingsTab;
import com.cb.cosmetic.GlobalRemap;
import com.cb.gui.screens.GlobalRemapEditorScreen;
import com.cb.setting.BooleanSetting;
import com.cb.setting.ButtonSetting;
import com.cb.setting.Setting;
import com.cb.setting.SettingKt;
import com.cb.utils.ChatUtil;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={2, 3, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r\u00a8\u0006\u0012"}, d2={"Lcom/cb/config/tabs/BlockRemapsTab;", "Lcom/cb/config/SettingsTab;", "<init>", "()V", "enabled", "Lcom/cb/setting/BooleanSetting;", "getEnabled", "()Lcom/cb/setting/BooleanSetting;", "keepShape", "getKeepShape", "openEditor", "Lcom/cb/setting/ButtonSetting;", "getOpenEditor", "()Lcom/cb/setting/ButtonSetting;", "export", "getExport", "import", "getImport", "cb_client"})
public final class BlockRemapsTab
extends SettingsTab {
    @NotNull
    public static final BlockRemapsTab INSTANCE = new BlockRemapsTab();
    @NotNull
    private static final BooleanSetting enabled;
    @NotNull
    private static final BooleanSetting keepShape;
    @NotNull
    private static final ButtonSetting openEditor;
    @NotNull
    private static final ButtonSetting export;
    @NotNull
    private static final ButtonSetting importButton;

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
        return importButton;
    }

    private static final Unit enabled$lambda$0(boolean it) {
        GlobalRemap.INSTANCE.rebuild$cb_client();
        return Unit.INSTANCE;
    }

    private static final Unit keepShape$lambda$0(boolean it) {
        GlobalRemap.INSTANCE.rebuild$cb_client();
        return Unit.INSTANCE;
    }

    private static final void openEditor$lambda$0$0(Minecraft $mc) {
        $mc.gui.setScreen((Screen)new GlobalRemapEditorScreen());
    }

    private static final Unit openEditor$lambda$0() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        mc.execute(() -> BlockRemapsTab.openEditor$lambda$0$0(mc));
        return Unit.INSTANCE;
    }

    private static final Unit export$lambda$0() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        mc.keyboardHandler.setClipboard(GlobalRemap.INSTANCE.exportJson());
        ChatUtil.INSTANCE.success("Copied " + GlobalRemap.INSTANCE.sources().size() + " remaps to clipboard");
        return Unit.INSTANCE;
    }

    private static final Unit import$lambda$0() {
        Minecraft minecraft = Minecraft.getInstance();
        Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance(...)");
        Minecraft mc = minecraft;
        String string = mc.keyboardHandler.getClipboard();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"getClipboard(...)");
        String json = string;
        if (GlobalRemap.INSTANCE.importJson(json)) {
            ChatUtil.INSTANCE.success("Imported " + GlobalRemap.INSTANCE.sources().size() + " remaps");
        } else {
            ChatUtil.INSTANCE.error("Clipboard is not a valid remap export");
        }
        return Unit.INSTANCE;
    }

    static {
        INSTANCE.subcategory("General");
        enabled = (BooleanSetting)SettingKt.onChange((Setting)INSTANCE.bool("Enabled", "Render all blocks of a source type as their mapped target.", false), BlockRemapsTab::enabled$lambda$0);
        keepShape = (BooleanSetting)SettingKt.onChange((Setting)INSTANCE.bool("Keep Block Shape", "Preserve stair/slab geometry of the source block when remapping.", false), BlockRemapsTab::keepShape$lambda$0);
        INSTANCE.subcategory("Editor");
        openEditor = INSTANCE.button("Remap Editor", "Add or remove block remaps with a searchable picker.", "Open", (Function0<Unit>)((Function0)BlockRemapsTab::openEditor$lambda$0));
        INSTANCE.subcategory("Share");
        export = INSTANCE.button("Export", "Copy all remaps to the clipboard as JSON.", "Copy", (Function0<Unit>)((Function0)BlockRemapsTab::export$lambda$0));
        importButton = INSTANCE.button("Import", "Replace all remaps from clipboard JSON.", "Paste", (Function0<Unit>)((Function0)BlockRemapsTab::import$lambda$0));
    }
}


