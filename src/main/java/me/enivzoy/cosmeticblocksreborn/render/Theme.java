package me.enivzoy.cosmeticblocksreborn.render;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

public final class Theme {
   @NotNull
   public static final Theme INSTANCE = new Theme();
   public static final int SCREEN_DIM = 1711276032;
   public static final int SURFACE = -183233516;
   public static final int SURFACE_RAISED = -14935012;
   public static final int SURFACE_HOVER = 318767103;
   public static final int SURFACE_SUNKEN = 1711276032;
   public static final int OUTLINE = 352321535;
   public static final int ACCENT = -1750963;
   public static final int ACCENT_SOFT = 1088768077;
   public static final int TEXT = -1184275;
   public static final int TEXT_MUTED = -6513508;
   public static final int TEXT_FAINT = -10724260;
   public static final int TRACK = -14013910;
   public static final float PANEL_WIDTH = 240.0F;
   public static final float PANEL_RADIUS = 10.0F;
   public static final float HEADER_HEIGHT = 36.0F;
   public static final float MODULE_HEIGHT = 30.0F;
   public static final float SETTING_HEIGHT = 26.0F;
   public static final float PADDING = 10.0F;
   public static final float FONT_SIZE = 13.0F;
   public static final float FONT_SIZE_SMALL = 12.0F;
   public static final float FONT_SIZE_HEADER = 14.0F;

   private Theme() {
   }

   public final int withAlpha(int color, float alpha) {
      int a = RangesKt.coerceIn((int)((color >>> 24) * RangesKt.coerceIn(alpha, 0.0F, 1.0F)), 0, 255);
      return a << 24 | color & 16777215;
   }
}
