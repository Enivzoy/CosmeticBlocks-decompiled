package me.enivzoy.cosmeticblocksreborn.cosmetic;

import me.enivzoy.cosmeticblocksreborn.config.tabs.DungeonConfigTab;
import me.enivzoy.cosmeticblocksreborn.config.tabs.GlobalCosmeticsTab;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WaterConfig {
   @NotNull
   public static final WaterConfig INSTANCE = new WaterConfig();
   private static volatile boolean overrideActive;

   private WaterConfig() {
   }

   public final boolean getOverrideActive$cb_client() {
      return overrideActive;
   }

   public final void onConfigLoaded$cb_client(@Nullable Boolean allow, @Nullable Integer rgb, @Nullable Boolean lava) {
      overrideActive = allow != null || rgb != null || lava != null;
      Boolean effectiveAllow = allow != null ? allow : GlobalCosmeticsTab.INSTANCE.getAllowWaterColor().getValue();
      Integer effectiveRgb = rgb != null ? rgb : GlobalCosmeticsTab.INSTANCE.getRgbWaterColor().getValue();
      Boolean effectiveLava = lava != null ? lava : GlobalCosmeticsTab.INSTANCE.getLavaAsWater().getValue();
      DungeonConfigTab.applyWater$cb_client(effectiveAllow, effectiveRgb, effectiveLava);
   }

   public final void onPerConfigChanged$cb_client() {
      overrideActive = true;
      ReplacementIO.INSTANCE.save();
   }

   public final void onGlobalChanged$cb_client() {
      if (!overrideActive) {
         DungeonConfigTab.applyWater$cb_client(
            GlobalCosmeticsTab.INSTANCE.getAllowWaterColor().getValue(),
            GlobalCosmeticsTab.INSTANCE.getRgbWaterColor().getValue(),
            GlobalCosmeticsTab.INSTANCE.getLavaAsWater().getValue()
         );
      }
   }

   public final boolean allowWaterColor() {
      return overrideActive
         ? (Boolean)DungeonConfigTab.INSTANCE.getAllowWaterColor().getValue()
         : (Boolean)GlobalCosmeticsTab.INSTANCE.getAllowWaterColor().getValue();
   }

   public final int rgbWaterColor() {
      Number value = overrideActive
         ? (Number)DungeonConfigTab.INSTANCE.getRgbWaterColor().getValue()
         : (Number)GlobalCosmeticsTab.INSTANCE.getRgbWaterColor().getValue();
      return value.intValue() & 0xFFFFFF;
   }

   public final boolean lavaAsWater() {
      return overrideActive
         ? (Boolean)DungeonConfigTab.INSTANCE.getLavaAsWater().getValue()
         : (Boolean)GlobalCosmeticsTab.INSTANCE.getLavaAsWater().getValue();
   }

   @NotNull
   public final WaterConfig.Fields captureFields() {
      Boolean allow = overrideActive ? (Boolean)DungeonConfigTab.INSTANCE.getAllowWaterColor().getValue() : null;
      Integer rgb = overrideActive ? ((Number)DungeonConfigTab.INSTANCE.getRgbWaterColor().getValue()).intValue() : null;
      Boolean lava = overrideActive ? (Boolean)DungeonConfigTab.INSTANCE.getLavaAsWater().getValue() : null;
      return new WaterConfig.Fields(allow, rgb, lava);
   }

   public static final class Fields {
      @Nullable
      private final Boolean allow;
      @Nullable
      private final Integer rgb;
      @Nullable
      private final Boolean lava;

      public Fields(@Nullable Boolean allow, @Nullable Integer rgb, @Nullable Boolean lava) {
         this.allow = allow;
         this.rgb = rgb;
         this.lava = lava;
      }

      public final boolean hasAny() {
         return this.allow != null || this.rgb != null || this.lava != null;
      }

      @Nullable
      public final Boolean getAllow() {
         return this.allow;
      }

      @Nullable
      public final Integer getRgb() {
         return this.rgb;
      }

      @Nullable
      public final Boolean getLava() {
         return this.lava;
      }
   }
}
