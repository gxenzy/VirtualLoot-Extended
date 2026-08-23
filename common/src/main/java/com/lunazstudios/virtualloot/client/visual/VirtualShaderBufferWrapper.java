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
                // 1. WIREFRAME MESH: Cyber Neon Matrix with transparent grid polygons
                float gridY = (float) Math.abs(Math.sin((lastY * 20.0) + (time * 0.003)));
                float gridXZ = (float) Math.abs(Math.sin(lastX * 16.0) * Math.sin(lastZ * 16.0));
                float gridUV = (float) Math.abs(Math.sin(lastU * 32.0) * Math.sin(lastV * 32.0));

                boolean isGridLine = (gridY > 0.60f || gridXZ > 0.55f || gridUV > 0.65f);

                if (isGridLine) {
                    // Glowing Neon Matrix Green wireframe line
                    parent.setColor(0, 255, 136, 255);
                } else {
                    // Transparent interior polygon so you can see right through the wireframe mesh
                    parent.setColor(0, 40, 20, 25);
                }
            } else if (mode == 2) {
                // 2. HOLOGRAM: Electric Cyan with moving scanlines, frequency waves, and micro-flicker
                float scanline = (float) (Math.sin((lastY * 30.0) - (time * 0.008)) * 0.35 + 0.65);
                float flicker = (float) (0.88 + 0.12 * Math.sin(time * 0.04) * Math.cos(time * 0.023));
                float wave = (float) (Math.sin((lastX + lastZ) * 8.0 + (time * 0.005)) * 0.15 + 0.85);

                float intensity = scanline * flicker * wave;
                int r = (int) (10 * intensity);
                int g = (int) (225 * intensity);
                int b = (int) (255 * intensity);
                int a = Math.max(30, Math.min(230, (int) (150 * intensity)));

                parent.setColor(r, g, b, a);
            } else if (mode == 3) {
                // 3. GHOST: Spectral Ethereal Spirit with undulating ghostly fade
                float pulse = (float) (Math.sin((time * 0.0025) + (lastY * 3.0)) * 0.3 + 0.7);
                int r = (int) (190 * pulse);
                int g = (int) (125 * pulse);
                int b = 255;
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
                // Emissive fullbright lighting for Wireframe and Hologram
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
