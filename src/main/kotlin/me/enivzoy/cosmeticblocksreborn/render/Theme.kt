package me.enivzoy.cosmeticblocksreborn.render

object Theme {
   const val SCREEN_DIM = 1711276032
   const val SURFACE = -183233516
   const val SURFACE_RAISED = -14935012
   const val SURFACE_HOVER = 318767103
   const val SURFACE_SUNKEN = 1711276032
   const val OUTLINE = 352321535
   const val ACCENT = -1750963
   const val ACCENT_SOFT = 1088768077
   const val TEXT = -1184275
   const val TEXT_MUTED = -6513508
   const val TEXT_FAINT = -10724260
   const val TRACK = -14013910
   const val PANEL_WIDTH = 240.0f
   const val PANEL_RADIUS = 10.0f
   const val HEADER_HEIGHT = 36.0f
   const val MODULE_HEIGHT = 30.0f
   const val SETTING_HEIGHT = 26.0f
   const val PADDING = 10.0f
   const val FONT_SIZE = 13.0f
   const val FONT_SIZE_SMALL = 12.0f
   const val FONT_SIZE_HEADER = 14.0f

   fun withAlpha(color: Int, alpha: Float): Int {
      val a = ((color ushr 24) * alpha.coerceIn(0.0f, 1.0f)).toInt().coerceIn(0, 255)
      return a shl 24 or (color and 0xFFFFFF)
   }
}
