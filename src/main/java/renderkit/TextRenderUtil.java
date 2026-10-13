package renderkit;

import renderkit.font.GlyphQuad;
import renderkit.font.PulsiveFont;
import renderkit.mixin.GuiGraphicsExtractorAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import org.jetbrains.annotations.Nullable;

public final class TextRenderUtil {
    private TextRenderUtil() {
    }

    public static float drawString(GuiGraphicsExtractor extractor, PulsiveFont font,
                                    CharSequence text, float x, float y, float size, int color) {
        return drawString(extractor, font, text, x, y, size, color, null);
    }

    public static float drawString(GuiGraphicsExtractor extractor, PulsiveFont font,
                                    CharSequence text, float x, float y, float size, int color,
                                    @Nullable ScreenRectangle scissorArea) {
        if (text.length() == 0 || !PulsivePipelines.ready()) {
            return 0.0f;
        }

        GlyphQuad[] glyphs = font.layout(text, x, y, size);
        if (glyphs.length > 0) {
            ((GuiGraphicsExtractorAccessor) extractor).pulsive$guiRenderState().addGuiElement(
                    TextRenderState.of(extractor.pose(), glyphs, color,
                            font.textureView(size), font.sampler(size), scissorArea));
        }

        return font.stringWidth(text, size);
    }

    public static float drawGradientString(GuiGraphicsExtractor extractor, PulsiveFont font,
                                            CharSequence text, float x, float y, float size,
                                            int colorLeft, int colorRight) {
        return drawGradientString(extractor, font, text, x, y, size, colorLeft, colorRight, null);
    }

    public static float drawGradientString(GuiGraphicsExtractor extractor, PulsiveFont font,
                                            CharSequence text, float x, float y, float size,
                                            int colorLeft, int colorRight, @Nullable ScreenRectangle scissorArea) {
        if (text.length() == 0 || !PulsivePipelines.ready()) {
            return 0.0f;
        }

        GlyphQuad[] glyphs = font.layout(text, x, y, size);
        if (glyphs.length > 0) {
            ((GuiGraphicsExtractorAccessor) extractor).pulsive$guiRenderState().addGuiElement(
                    GradientTextRenderState.of(extractor.pose(), glyphs, colorLeft, colorRight,
                            font.textureView(size), font.sampler(size), scissorArea));
        }

        return font.stringWidth(text, size);
    }
}
