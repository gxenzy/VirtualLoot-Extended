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
        private float lastX = 0f;
        private float lastY = 0f;
        private float lastZ = 0f;
        private float lastU = 0f;
        private float lastV = 0f;

        public FilteredVertexConsumer(VertexConsumer parent, int mode, long time) {
            this.parent = parent;
            this.mode = mode;
            this.time = time;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.lastX = x;
            this.lastY = y;
            this.lastZ = z;
            parent.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            if (mode == 1) {
                // 1. WIREFRAME MESH:
                // Wireframe renders bright neon green on polygon edges and completely transparent on interior faces
                float uFrac = Math.abs((lastU * 16.0f) % 1.0f);
                float vFrac = Math.abs((lastV * 16.0f) % 1.0f);
                boolean isEdge = (uFrac < 0.12f || uFrac > 0.88f || vFrac < 0.12f || vFrac > 0.88f);

                if (isEdge) {
                    parent.setColor(0, 255, 140, 255); // Glowing neon green line
                } else {
                    parent.setColor(0, 40, 20, 20); // See-through dark mesh interior
                }
            } else if (mode == 2) {
                // 2. HOLOGRAM:
                // Luminous translucent holographic projection with moving cyan scanlines over authentic Pokemon skin
                float scanline = (float) (Math.sin((lastY * 25.0) - (time * 0.007)) * 0.35 + 0.65);
                float wave = (float) (Math.sin((lastX + lastZ) * 6.0 + (time * 0.004)) * 0.15 + 0.85);
                float intensity = scanline * wave;

                int r = Math.min(255, (int) (red * 0.35f + 15 * intensity));
                int g = Math.min(255, (int) (green * 0.70f + 180 * intensity));
                int b = Math.min(255, (int) (blue * 0.70f + 255 * intensity));
                int a = Math.max(50, Math.min(220, (int) (140 + 70 * intensity)));

                parent.setColor(r, g, b, a);
            } else if (mode == 3) {
                // 3. GHOST:
                // Spectral undulating spirit with soft see-through purple/white alpha
                float pulse = (float) (Math.sin((time * 0.003) + (lastY * 2.5)) * 0.25 + 0.75);
                int r = Math.min(255, (int) (red * 0.75f + 50 * pulse));
                int g = Math.min(255, (int) (green * 0.60f + 20 * pulse));
                int b = Math.min(255, (int) (blue * 0.90f + 70 * pulse));
                int a = Math.max(40, Math.min(180, (int) (110 * pulse)));

                parent.setColor(r, g, b, a);
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
            this.lastU = u;
            this.lastV = v;
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
