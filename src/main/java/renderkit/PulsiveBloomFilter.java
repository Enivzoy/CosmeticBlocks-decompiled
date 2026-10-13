package renderkit;

import renderkit.mixin.GuiGraphicsExtractorAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public final class PulsiveBloomFilter {
    private static final Set<GuiElementRenderState> SUPPRESSED =
            Collections.newSetFromMap(new IdentityHashMap<>());

    @Nullable
    private static BlurConsumer current;

    private PulsiveBloomFilter() {
    }

    public static void begin(BlurConsumer consumer) {
        current = consumer;
    }

    public static void end() {
        current = null;
    }

    public static void submit(GuiGraphicsExtractor extractor, GuiElementRenderState element) {
        BlurConsumer consumer = current;
        if (consumer != null && RenderSettings.getMode(consumer) == null) {
            SUPPRESSED.add(element);
        }
        ((GuiGraphicsExtractorAccessor) extractor).pulsive$guiRenderState().addGuiElement(element);
    }

    public static boolean isSuppressed(GuiElementRenderState element) {
        return !SUPPRESSED.isEmpty() && SUPPRESSED.contains(element);
    }

    public static void endFrame() {
        if (!SUPPRESSED.isEmpty()) {
            SUPPRESSED.clear();
        }
    }
}
