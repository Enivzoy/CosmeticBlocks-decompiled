package com.cb.config;

import com.cb.config.tabs.BlockRemapsTab;
import com.cb.config.tabs.CosmeticBlocksTab;
import com.cb.config.tabs.GuiTab;
import com.cb.config.tabs.HelpTab;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t",
   d2 = {"Lcom/cb/config/Tabs;", "", "<init>", "()V", "all", "", "Lcom/cb/config/SettingsTab;", "getAll", "()Ljava/util/List;", "cb_client"}
)
public final class Tabs {
   @NotNull
   public static final Tabs INSTANCE = new Tabs();
   @NotNull
   private static final List<SettingsTab> all;

   private Tabs() {
   }

   @NotNull
   public final List<SettingsTab> getAll() {
      return all;
   }

   static {
      SettingsTab[] tabs = new SettingsTab[]{CosmeticBlocksTab.INSTANCE, BlockRemapsTab.INSTANCE, GuiTab.INSTANCE, HelpTab.INSTANCE};
      all = CollectionsKt.listOf(tabs);
   }
}
