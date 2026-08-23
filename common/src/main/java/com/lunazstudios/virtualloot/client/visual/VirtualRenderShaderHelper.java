package com.lunazstudios.virtualloot.client.visual;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class VirtualRenderShaderHelper {

    public static int getVisualMode(LivingEntity entity) {
        if (entity != null) {
            if (entity.getTags().contains("virtualloot_visual_mode_1")) return 1; // WIREFRAME / CYBER
            if (entity.getTags().contains("virtualloot_visual_mode_2")) return 2; // HOLOGRAM
            if (entity.getTags().contains("virtualloot_visual_mode_3")) return 3; // GHOST
        }
        return 0;
    }

    public static RenderType getCustomRenderType(LivingEntity entity, ResourceLocation texture, RenderType defaultType) {
        int mode = getVisualMode(entity);
        if (mode == 1) {
            // Cyber Emissive Wireframe
            return RenderType.entityCutoutNoCull(texture);
        } else if (mode == 2) {
            // Glowing Translucent Hologram
            return RenderType.entityTranslucentCull(texture);
        } else if (mode == 3) {
            // Ethereal Ghost
            return RenderType.entityTranslucent(texture);
        }
        return defaultType;
    }
}
