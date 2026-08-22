package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class VirtualRenderShaderHelper {

    public static int getVisualMode(PokemonEntity entity) {
        if (entity.getTags().contains("virtualloot_visual_mode_1")) return 1; // WIREFRAME
        if (entity.getTags().contains("virtualloot_visual_mode_2")) return 2; // HOLOGRAM
        if (entity.getTags().contains("virtualloot_visual_mode_3")) return 3; // GHOST
        return 0;
    }

    public static RenderType getCustomRenderType(PokemonEntity entity, ResourceLocation texture, RenderType defaultType) {
        int mode = getVisualMode(entity);
        if (mode == 1) {
            // WIREFRAME
            return RenderType.lines();
        } else if (mode == 2) {
            // HOLOGRAM
            return RenderType.entityTranslucentCull(texture);
        } else if (mode == 3) {
            // GHOST
            return RenderType.entityTranslucent(texture);
        }
        return defaultType;
    }

    public static int getTintOverlayColor(PokemonEntity entity, int originalColor) {
        int mode = getVisualMode(entity);
        if (mode == 1) {
            // Neon Green / Cyan Wireframe
            return 0xCC00FFCC;
        } else if (mode == 2) {
            // Pulsing Holographic Cyan
            double time = System.currentTimeMillis() / 200.0;
            int alpha = (int) (130 + 50 * Math.sin(time));
            return (alpha << 24) | 0x0040E0D0;
        } else if (mode == 3) {
            // Ethereal Ghost (Purple / White tint)
            return 0x99DDA0DD;
        }
        return originalColor;
    }
}
