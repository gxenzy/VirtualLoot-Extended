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
        private float lastU = 0f;
        private float lastV = 0f;

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
                // Mode 1: WIREFRAME MESH (Clean vector line grid on quad edges, transparent inside)
                float uFrac = Math.abs((lastU * 16.0f) % 1.0f);
                float vFrac = Math.abs((lastV * 16.0f) % 1.0f);
                boolean isEdge = (uFrac < 0.12f || uFrac > 0.88f || vFrac < 0.12f || vFrac > 0.88f);

                if (isEdge) {
                    parent.setColor(0, 230, 255, 255); // Cyan glowing wireframe line
                } else {
                    parent.setColor(0, 30, 40, 15); // Transparent dark mesh interior
                }
            } else if (mode == 2) {
                // Mode 2: ENERGY HOLOGRAM (Translucent electric cyan with scanlines)
                float scanline = (float) (Math.sin((lastY * 25.0) - (time * 0.007)) * 0.35 + 0.65);
                int r = Math.min(255, (int) (15 + 30 * scanline));
                int g = Math.min(255, (int) (180 + 75 * scanline));
                int b = 255;
                int a = Math.max(60, Math.min(230, (int) (140 + 70 * scanline)));
                parent.setColor(r, g, b, a);
            } else if (mode == 3) {
                // Mode 3: NATIVE MINECRAFT SPECTATOR GHOST (Authentic colors with 40% see-through alpha)
                parent.setColor(red, green, blue, 105);
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
