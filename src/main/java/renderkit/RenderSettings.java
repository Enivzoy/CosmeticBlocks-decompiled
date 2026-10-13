package renderkit;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public final class RenderSettings {

    public enum BlurMode {
        GAUSSIAN,
        KAWASE,
        RISE;

        public static final BlurMode[] VALUES = values();
    }

    private static boolean blur = true;
    private static boolean bloom = true;
    private static int blurRadius = 12;
    private static int bloomRadius = 8;
    private static BlurMode bloomMode = BlurMode.GAUSSIAN;
    private static BlurMode defaultMode = BlurMode.GAUSSIAN;

    private static final Map<BlurConsumer, BlurMode> PER_CONSUMER = new EnumMap<>(BlurConsumer.class);

    private RenderSettings() {
    }

    public static boolean isBlur() {
        return blur;
    }

    public static void setBlur(boolean value) {
        blur = value;
    }

    public static boolean isBloom() {
        return bloom;
    }

    public static void setBloom(boolean value) {
        bloom = value;
    }

    public static int getBlurRadius() {
        return blurRadius;
    }

    public static void setBlurRadius(int value) {
        blurRadius = Math.max(1, value);
    }

    public static int getBloomRadius() {
        return bloomRadius;
    }

    public static void setBloomRadius(int value) {
        bloomRadius = Math.max(1, value);
    }

    public static BlurMode getBloomMode() {
        return bloomMode;
    }

    public static void setBloomMode(BlurMode value) {
        bloomMode = value;
    }

    public static void setDefaultMode(@Nullable BlurMode value) {
        defaultMode = value;
    }

    public static void setMode(BlurConsumer consumer, @Nullable BlurMode mode) {
        if (mode == null) {
            PER_CONSUMER.remove(consumer);
        } else {
            PER_CONSUMER.put(consumer, mode);
        }
    }

    @Nullable
    public static BlurMode getMode(BlurConsumer consumer) {
        BlurMode mode = PER_CONSUMER.get(consumer);
        return mode != null ? mode : defaultMode;
    }
}
