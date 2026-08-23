package com.lunazstudios.virtualloot.client.visual;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class VirtualShaderBufferWrapper implements MultiBufferSource {

    private final MultiBufferSource delegate;
    private final int mode;
    private final ResourceLocation texture;
    private final long time;

    public VirtualShaderBufferWrapper(MultiBufferSource delegate, int mode, ResourceLocation texture) {
        this.delegate = delegate;
        this.mode = mode;
        this.texture = texture;
        this.time = System.currentTimeMillis();
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        if (mode == 1) {
            // Mode 1: 100% PURE VECTOR WIREFRAME (All layers including flames/emissives redirected to lines)
            VertexConsumer linesConsumer = delegate.getBuffer(RenderType.lines());
            return new WireframeVertexConsumer(linesConsumer);
        } else if (mode == 2) {
            // Mode 2: AUTHENTIC ENERGY HOLOGRAM (Luminous electric cyan see-through projection with scanlines)
            RenderType hologramType = (texture != null) ? RenderType.itemEntityTranslucentCull(texture) : renderType;
            VertexConsumer original = delegate.getBuffer(hologramType);
            return new HologramVertexConsumer(original, time);
        } else if (mode == 3) {
            // Mode 3: NATIVE MINECRAFT SPECTATOR GHOST (See-Through Spectator Translucency)
            RenderType ghostType = (texture != null) ? RenderType.itemEntityTranslucentCull(texture) : renderType;
            VertexConsumer original = delegate.getBuffer(ghostType);
            return new SpectatorGhostVertexConsumer(original);
        }
        return delegate.getBuffer(renderType);
    }

    /**
     * Mode 1: 100% Vector Wireframe.
     * Draws pure glowing cyan line geometry for all quads, triangles, and animated layers (like flames).
     */
    private static class WireframeVertexConsumer implements VertexConsumer {
        private final VertexConsumer lines;
        private final float[] x = new float[4];
        private final float[] y = new float[4];
        private final float[] z = new float[4];
        private int count = 0;

        public WireframeVertexConsumer(VertexConsumer lines) {
            this.lines = lines;
        }

        @Override
        public VertexConsumer addVertex(float vx, float vy, float vz) {
            int idx = count % 4;
            x[idx] = vx;
            y[idx] = vy;
            z[idx] = vz;
            count++;

            if (idx == 3) {
                int r = 0;
                int g = 230;
                int b = 255;
                int a = 255;

                drawLine(x[0], y[0], z[0], x[1], y[1], z[1], r, g, b, a);
                drawLine(x[1], y[1], z[1], x[2], y[2], z[2], r, g, b, a);
                drawLine(x[2], y[2], z[2], x[3], y[3], z[3], r, g, b, a);
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

            lines.addVertex(x1, y1, z1);
            lines.setColor(r, g, b, a);
            lines.setNormal(nx, ny, nz);

            lines.addVertex(x2, y2, z2);
            lines.setColor(r, g, b, a);
            lines.setNormal(nx, ny, nz);
        }

        @Override public VertexConsumer setColor(int red, int green, int blue, int alpha) { return this; }
        @Override public VertexConsumer setColor(int color) { return this; }
        @Override public VertexConsumer setUv(float u, float v) { return this; }
        @Override public VertexConsumer setUv1(int u, int v) { return this; }
        @Override public VertexConsumer setUv2(int u, int v) { return this; }
        @Override public VertexConsumer setNormal(float nx, float ny, float nz) { return this; }
        @Override public VertexConsumer setOverlay(int overlay) { return this; }
        @Override public VertexConsumer setLight(int light) { return this; }
    }

    /**
     * Mode 2: Authentic Energy Hologram.
     * Renders a luminous, see-through electric cyan silhouette with horizontal scanlines and emissive brightness.
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
            // Horizontal energy scanline modulation
            float scanline = (float) (Math.sin((lastY * 20.0) - (time * 0.008)) * 0.25 + 0.75);
            int r = 0;
            int g = 230;
            int b = 255;
            int a = Math.max(50, Math.min(220, (int) (130 * scanline)));
            parent.setColor(r, g, b, a);
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            return setColor(0, 230, 255, 255);
        }

        @Override public VertexConsumer setUv(float u, float v) { parent.setUv(u, v); return this; }
        @Override public VertexConsumer setUv1(int u, int v) { parent.setUv1(u, v); return this; }
        @Override public VertexConsumer setUv2(int u, int v) { parent.setUv2(0x00F0, 0x00F0); return this; }
        @Override public VertexConsumer setNormal(float x, float y, float z) { parent.setNormal(x, y, z); return this; }
        @Override public VertexConsumer setOverlay(int overlay) { parent.setOverlay(overlay); return this; }
        @Override public VertexConsumer setLight(int light) { parent.setLight(0x00F000F0); return this; }
    }

    /**
     * Mode 3: Native Minecraft Spectator Ghost.
     * Renders exact authentic Pokémon skin with 40% see-through Spectator translucency.
     */
    private static class SpectatorGhostVertexConsumer implements VertexConsumer {
        private final VertexConsumer parent;

        public SpectatorGhostVertexConsumer(VertexConsumer parent) {
            this.parent = parent;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            parent.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            // Native Minecraft Spectator alpha: 40% see-through opacity on authentic colors
            parent.setColor(red, green, blue, 105);
            return this;
        }

        @Override
        public VertexConsumer setColor(int color) {
            int r = (color >> 16) & 255;
            int g = (color >> 8) & 255;
            int b = color & 255;
            return setColor(r, g, b, 105);
        }

        @Override public VertexConsumer setUv(float u, float v) { parent.setUv(u, v); return this; }
        @Override public VertexConsumer setUv1(int u, int v) { parent.setUv1(u, v); return this; }
        @Override public VertexConsumer setUv2(int u, int v) { parent.setUv2(u, v); return this; }
        @Override public VertexConsumer setNormal(float x, float y, float z) { parent.setNormal(x, y, z); return this; }
        @Override public VertexConsumer setOverlay(int overlay) { parent.setOverlay(overlay); return this; }
        @Override public VertexConsumer setLight(int light) { parent.setLight(light); return this; }
    }
}
