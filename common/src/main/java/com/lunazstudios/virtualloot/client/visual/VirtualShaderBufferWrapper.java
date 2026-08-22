package com.lunazstudios.virtualloot.client.visual;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class VirtualShaderBufferWrapper implements MultiBufferSource {

    private final MultiBufferSource delegate;
    private final int mode;
    private final long time;

    public VirtualShaderBufferWrapper(MultiBufferSource delegate, int mode) {
        this.delegate = delegate;
        this.mode = mode;
        this.time = System.currentTimeMillis();
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        VertexConsumer original = delegate.getBuffer(renderType);
        return new FilteredVertexConsumer(original, mode, time);
    }

    private static class FilteredVertexConsumer implements VertexConsumer {
        private final VertexConsumer parent;
        private final int mode;
        private final long time;
        private float lastY = 0f;

        public FilteredVertexConsumer(VertexConsumer parent, int mode, long time) {
            this.parent = parent;
            this.mode = mode;
            this.time = time;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.lastY = y;
            parent.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            if (mode == 1) {
                // Cyber Wireframe: Neon Matrix Green
                parent.setColor(25, 255, 150, 255);
            } else if (mode == 2) {
                // Hologram: Cyan matrix glow with animated scanlines
                float scanline = (float) (Math.sin(lastY * 25.0 + time * 0.006) * 0.25 + 0.75);
                int r = (int) (50 * scanline);
                int g = (int) (220 * scanline);
                int b = (int) (255 * scanline);
                parent.setColor(r, g, b, 160);
            } else if (mode == 3) {
                // Ghost: Spectral Ethereal Purple with transparency
                parent.setColor(210, 120, 255, 120);
            } else {
                parent.setColor(red, green, blue, alpha);
            }
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            int a = (color >> 24) & 255;
            int r = (color >> 16) & 255;
            int g = (color >> 8) & 255;
            int b = color & 255;
            return setColor(r, g, b, a);
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            parent.setUv(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            parent.setUv1(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            if (mode == 1 || mode == 2) {
                // Full bright emissive for Cyber Wireframe and Hologram
                parent.setUv2(0x00F0, 0x00F0);
            } else {
                parent.setUv2(u, v);
            }
            return this;
        }

        @Override
        public VertexConsumer setNormal(float x, float y, float z) {
            parent.setNormal(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setOverlay(int overlay) {
            parent.setOverlay(overlay);
            return this;
        }

        @Override
        public VertexConsumer setLight(int light) {
            if (mode == 1 || mode == 2) {
                parent.setLight(0x00F000F0);
            } else {
                parent.setLight(light);
            }
            return this;
        }
    }
}
