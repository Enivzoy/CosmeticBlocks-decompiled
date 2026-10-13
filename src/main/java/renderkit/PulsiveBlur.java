package renderkit;

import renderkit.mixin.GuiGraphicsExtractorAccessor;
import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public final class PulsiveBlur {
    private static final CrossFrameResourcePool RESOURCE_POOL = new CrossFrameResourcePool(3);

    private static final GpuTextureView[] BACKDROP_VIEWS = new GpuTextureView[RenderSettings.BlurMode.VALUES.length];

    private static TextureTarget glowTarget;

    private static final boolean[] BACKDROP_REQUESTED = new boolean[RenderSettings.BlurMode.VALUES.length];

    private static final long GLOW_UPDATE_INTERVAL_NS = 1_000_000_000L / 60L;
    private static long lastGlowUpdate;

    private PulsiveBlur() {
    }

    public static boolean shouldUpdateGlow() {
        long now = System.nanoTime();
        if (now - lastGlowUpdate < GLOW_UPDATE_INTERVAL_NS) {
            return false;
        }
        lastGlowUpdate = now;
        return true;
    }

    public static void endFrame() {
        RESOURCE_POOL.endFrame();

        PulsiveBlurChains.closePending();
        PulsiveBloomFilter.endFrame();

        for (RenderSettings.BlurMode mode : RenderSettings.BlurMode.VALUES) {
            RenderTarget out = PulsiveBlurChains.outputTarget(PulsiveBlurChains.Slot.backdrop(mode));
            BACKDROP_VIEWS[mode.ordinal()] = out == null ? null : out.getColorTextureView();
        }
    }

    @Nullable
    public static TextureTarget glowTarget() {
        RenderTarget main = Minecraft.getInstance().gameRenderer.mainRenderTarget();
        int width = main.width;
        int height = main.height;
        if (width <= 0 || height <= 0) {
            return null;
        }
        if (glowTarget == null) {
            glowTarget = new TextureTarget("pulsive_glow", width, height, false, GpuFormat.RGBA8_UNORM);
        } else if (glowTarget.width != width || glowTarget.height != height) {
            glowTarget.resize(width, height);
        }
        return glowTarget;
    }

    public static void blurGlow() {
        Minecraft mc = Minecraft.getInstance();
        if (!mc.isGameLoadFinished() || glowTarget == null) {
            return;
        }

        PostChain chain = PulsiveBlurChains.get(PulsiveBlurChains.Slot.GLOW,
                RenderSettings.getBloomMode(), RenderSettings.getBloomRadius(),
                glowTarget.width, glowTarget.height);
        if (chain != null) {
            chain.process(glowTarget, RESOURCE_POOL);
        }
    }

    public static void compositeBloom(GuiGraphicsExtractor extractor) {
        if (!RenderSettings.isBloom() || glowTarget == null) {
            return;
        }

        PulsiveBloomFilter.submit(extractor,
                BloomCompositeRenderState.of(extractor.pose(), 0, 0, extractor.guiWidth(), extractor.guiHeight(),
                        0xFFFFFFFF, glowTarget.getColorTextureView(),
                        RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)));
    }

    public static void drawBlurredRound(GuiGraphicsExtractor extractor, BlurConsumer consumer, float x, float y,
                                        float width, float height, float cornerRadius) {
        drawBlurredRound(extractor, consumer, x, y, width, height, cornerRadius, 1.0f, null);
    }

    public static void drawBlurredRound(GuiGraphicsExtractor extractor, BlurConsumer consumer, float x, float y,
                                        float width, float height, float cornerRadius, float alpha,
                                        @Nullable ScreenRectangle scissorArea) {
        if (!RenderSettings.isBlur() || width <= 0.0f || height <= 0.0f || alpha <= 0.0f) {
            return;
        }

        RenderSettings.BlurMode mode = RenderSettings.getMode(consumer);
        if (mode == null) {
            return;
        }
        BACKDROP_REQUESTED[mode.ordinal()] = true;
        GpuTextureView backdropView = BACKDROP_VIEWS[mode.ordinal()];
        if (backdropView == null) {
            return;
        }

        int alphaByte = Math.round(Math.max(0.0f, Math.min(1.0f, alpha)) * 255.0f);
        int color = (alphaByte << 24) | 0xFFFFFF;

        GpuSampler sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);
        if (cornerRadius < FLAT_RADIUS_THRESHOLD) {
            int x0 = Math.round(x);
            int y0 = Math.round(y);
            int x1 = x0 + Math.max(1, Math.round(width));
            int y1 = y0 + Math.max(1, Math.round(height));
            PulsiveBloomFilter.submit(extractor,
                    BlurRectFlatGroupRenderState.of(extractor.pose(),
                            java.util.List.of(new BlurRectFlatGroupRenderState.Rect(x0, y0, x1, y1)),
                            color, backdropView, sampler, scissorArea));
        } else {
            int rx0 = Math.round(x);
            int ry0 = Math.round(y);
            int rx1 = rx0 + Math.max(1, Math.round(width));
            int ry1 = ry0 + Math.max(1, Math.round(height));
            PulsiveBloomFilter.submit(extractor,
                    BlurRectRenderState.of(extractor.pose(), rx0, ry0, rx1, ry1,
                            cornerRadius, color, backdropView, sampler, scissorArea));
        }
    }

    private static final float FLAT_RADIUS_THRESHOLD = 0.5f;

    public static void drawBlurredRects(GuiGraphicsExtractor extractor, BlurConsumer consumer,
                                        java.util.List<float[]> boxes, float cornerRadius, float alpha) {
        if (!RenderSettings.isBlur() || boxes.isEmpty() || alpha <= 0.0f) {
            return;
        }

        RenderSettings.BlurMode mode = RenderSettings.getMode(consumer);
        if (mode == null) {
            return;
        }
        BACKDROP_REQUESTED[mode.ordinal()] = true;
        GpuTextureView backdropView = BACKDROP_VIEWS[mode.ordinal()];
        if (backdropView == null) {
            return;
        }

        int alphaByte = Math.round(Math.max(0.0f, Math.min(1.0f, alpha)) * 255.0f);
        int color = (alphaByte << 24) | 0xFFFFFF;

        GpuSampler sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);

        if (cornerRadius < FLAT_RADIUS_THRESHOLD) {
            java.util.List<BlurRectFlatGroupRenderState.Rect> rects = new java.util.ArrayList<>(boxes.size());
            for (float[] box : boxes) {
                float x = box[0], y = box[1], width = box[2], height = box[3];
                if (width <= 0.0f || height <= 0.0f) {
                    continue;
                }
                int x0 = Math.round(x);
                int y0 = Math.round(y);
                int x1 = x0 + Math.max(1, Math.round(width));
                int y1 = y0 + Math.max(1, Math.round(height));
                rects.add(new BlurRectFlatGroupRenderState.Rect(x0, y0, x1, y1));
            }
            if (rects.isEmpty()) {
                return;
            }
            PulsiveBloomFilter.submit(extractor,
                    BlurRectFlatGroupRenderState.of(extractor.pose(), rects, color, backdropView, sampler, null));
            return;
        }

        java.util.List<BlurRectGroupRenderState.Rect> rects = new java.util.ArrayList<>(boxes.size());
        for (float[] box : boxes) {
            float x = box[0], y = box[1], width = box[2], height = box[3];
            if (width <= 0.0f || height <= 0.0f) {
                continue;
            }

            int x0 = Math.round(x);
            int y0 = Math.round(y);
            int x1 = x0 + Math.max(1, Math.round(width));
            int y1 = y0 + Math.max(1, Math.round(height));
            rects.add(new BlurRectGroupRenderState.Rect(x0, y0, x1, y1, cornerRadius));
        }
        if (rects.isEmpty()) {
            return;
        }

        PulsiveBloomFilter.submit(extractor,
                BlurRectGroupRenderState.of(extractor.pose(), rects, color, backdropView, sampler, null));
    }

    public static void captureNow() {
        Minecraft mc = Minecraft.getInstance();

        if (!mc.isGameLoadFinished()) {
            return;
        }

        boolean blurring = RenderSettings.isBlur();
        RenderTarget main = mc.gameRenderer.mainRenderTarget();

        for (RenderSettings.BlurMode mode : RenderSettings.BlurMode.VALUES) {
            boolean requested = BACKDROP_REQUESTED[mode.ordinal()];
            BACKDROP_REQUESTED[mode.ordinal()] = false;
            if (!requested || !blurring) {
                continue;
            }

            PostChain chain = PulsiveBlurChains.get(PulsiveBlurChains.Slot.backdrop(mode),
                    mode, RenderSettings.getBlurRadius(), main.width, main.height);
            if (chain != null) {
                chain.process(main, RESOURCE_POOL);
            }
        }
    }
}
