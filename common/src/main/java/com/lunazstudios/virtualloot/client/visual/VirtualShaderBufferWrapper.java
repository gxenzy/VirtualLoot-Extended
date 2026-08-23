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
        if (mode == 1) {
            // Mode 1: TRUE 3D WIREFRAME MESH (CS2 Style)
            // Redirects to lines buffer to render pure vector wireframe outlines with 100% empty/see-through faces
            VertexConsumer linesConsumer = delegate.getBuffer(RenderType.lines());
            return new WireframeVertexConsumer(linesConsumer);
        } else if (mode == 2) {
            // Mode 2: HOLOGRAM (Fortnite / Cobblemon Energy Beam Style)
            VertexConsumer original = delegate.getBuffer(renderType);
            return new HologramVertexConsumer(original, time);
        } else if (mode == 3) {
            // Mode 3: GHOST SPIRIT (Genshin Seelie / Spectral Style)
            VertexConsumer original = delegate.getBuffer(renderType);
            return new GhostVertexConsumer(original, time);
        }
        return delegate.getBuffer(renderType);
    }

    /**
     * Mode 1: Real CS2-style vector wireframe mesh generator.
     * Takes quads and renders 4 crisp line edges into the line buffer.
     */
    private static class WireframeVertexConsumer implements VertexConsumer {
        private final VertexConsumer lines;
        private final float[] x = new float[4];
        private final float[] y = new float[4];
        private final float[] z = new float[4];
        private int vertexCount = 0;

        public WireframeVertexConsumer(VertexConsumer lines) {
            this.lines = lines;
        }

        @Override
        public VertexConsumer addVertex(float vx, float vy, float vz) {
            int idx = vertexCount % 4;
            x[idx] = vx;
            y[idx] = vy;
            z[idx] = vz;
            vertexCount++;

            if (idx == 3) {
                // Quad complete: Emit 4 wireframe edge lines (CS2 vector lattice)
                // Line color: Luminous Cyan Wireframe (#00E5FF)
                int r = 0;
                int g = 230;
                int b = 255;
                int a = 255;

                // Edge 0 -> 1
                drawLine(x[0], y[0], z[0], x[1], y[1], z[1], r, g, b, a);
                // Edge 1 -> 2
                drawLine(x[1], y[1], z[1], x[2], y[2], z[2], r, g, b, a);
                // Edge 2 -> 3
                drawLine(x[2], y[2], z[2], x[3], y[3], z[3], r, g, b, a);
                // Edge 3 -> 0
                drawLine(x[3], y[3], z[3], x[0], y[0], z[0], r, g, b, a);
            }
            return this;
        }

        private void drawLine(float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
            float nx = x2 - x1;
            float ny = y2 - y1;
            float nz = z2 - z1;
            float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (len > 0.0001f) {
                nx /= len;
                ny /= len;
                nz /= len;
            } else {
                ny = 1.0f;
            }

            lines.addVertex(x1, y1, z1).setColor(r, g, b, a).setNormal(nx, ny, nz);
            lines.addVertex(x2, y2, z2).setColor(r, g, b, a).setNormal(nx, ny, nz);
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer setNormal(float nx, float ny, float nz) {
            return this;
        }

        @Override
        public VertexConsumer setOverlay(int overlay) {
            return this;
        }

        @Override
        public VertexConsumer setLight(int light) {
            return this;
        }
    }

    /**
     * Mode 2: Hologram energy projection (Fortnite / Pokéball Energy Beam style).
     */
    private static class HologramVertexConsumer implements VertexConsumer {
        private final VertexConsumer parent;
        private final long time;
        private float lastY = 0f;

        public HologramVertexConsumer(VertexConsumer parent, long time) {
            this.parent = parent;
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
            // High-tech electric cyan hologram energy with scanlines and shimmer
            float scanline = (float) (Math.sin((lastY * 28.0) - (time * 0.008)) * 0.35 + 0.65);
            float flicker = (float) (0.90 + 0.10 * Math.sin(time * 0.05));
            float intensity = scanline * flicker;

            int r = Math.min(255, (int) (15 + 40 * intensity));
            int g = Math.min(255, (int) (160 + 95 * intensity));
            int b = 255;
            int a = Math.max(50, Math.min(230, (int) (140 + 75 * intensity)));

            parent.setColor(r, g, b, a);
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
            parent.setUv2(0x00F0, 0x00F0); // Emissive fullbright
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
            parent.setLight(0x00F000F0);
            return this;
        }
    }

    /**
     * Mode 3: Genshin Seelie spirit style (Luminous core fading into ethereal spectral mist).
     */
    private static class GhostVertexConsumer implements VertexConsumer {
        private final VertexConsumer parent;
        private final long time;
        private float lastY = 0f;

        public GhostVertexConsumer(VertexConsumer parent, long time) {
            this.parent = parent;
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
            // Ethereal gradient: luminous top/core fading into translucent spirit mist
            float pulse = (float) (Math.sin((time * 0.003) + (lastY * 2.5)) * 0.25 + 0.75);
            int r = Math.min(255, (int) (140 + 70 * pulse));
            int g = Math.min(255, (int) (210 + 45 * pulse));
            int b = 255;
            int a = Math.max(35, Math.min(180, (int) (100 * pulse)));

            parent.setColor(r, g, b, a);
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
            parent.setUv2(u, v);
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
            parent.setLight(light);
            return this;
        }
    }
}
