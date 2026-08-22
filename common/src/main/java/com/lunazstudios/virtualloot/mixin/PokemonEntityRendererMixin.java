package com.lunazstudios.virtualloot.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.lunazstudios.virtualloot.client.visual.VirtualRenderShaderHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class PokemonEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    protected PokemonEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void virtualloot$getCustomRenderType(T entity, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<RenderType> cir) {
        if (entity instanceof PokemonEntity pkmn) {
            int mode = VirtualRenderShaderHelper.getVisualMode(pkmn);
            if (mode == 1) {
                // Wireframe / Neon Outline
                cir.setReturnValue(RenderType.outline(this.getTextureLocation(entity)));
            } else if (mode == 2) {
                // Translucent Cyan Hologram
                cir.setReturnValue(RenderType.entityTranslucentCull(this.getTextureLocation(entity)));
            } else if (mode == 3) {
                // Ethereal Ghost
                cir.setReturnValue(RenderType.entityTranslucent(this.getTextureLocation(entity)));
            }
        }
    }
}
