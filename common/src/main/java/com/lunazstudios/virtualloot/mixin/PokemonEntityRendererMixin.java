package com.lunazstudios.virtualloot.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.lunazstudios.virtualloot.client.visual.VirtualRenderShaderHelper;
import com.lunazstudios.virtualloot.client.visual.VirtualShaderBufferWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class PokemonEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    protected PokemonEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void virtualloot$applyVisualTransformations(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (entity instanceof PokemonEntity pkmn) {
            int mode = VirtualRenderShaderHelper.getVisualMode(pkmn);
            if (mode == 2) {
                // Hologram subtle jitter/flicker
                double glitch = (Math.sin((entity.tickCount + partialTicks) * 0.4D) > 0.95D) ? 0.015D : 0.0D;
                poseStack.translate(glitch, 0.0D, 0.0D);
            } else if (mode == 3) {
                // Ghost levitation floating
                double hover = 0.45D + 0.15D * Math.sin((entity.tickCount + partialTicks) * 0.09D);
                poseStack.translate(0.0D, hover, 0.0D);
            }
        }
    }

    @ModifyVariable(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private MultiBufferSource virtualloot$wrapBuffer(MultiBufferSource buffer, T entity) {
        if (entity instanceof PokemonEntity pkmn) {
            int mode = VirtualRenderShaderHelper.getVisualMode(pkmn);
            if (mode > 0) {
                ResourceLocation texture = getTextureLocation(entity);
                return new VirtualShaderBufferWrapper(buffer, mode, texture);
            }
        }
        return buffer;
    }
}
