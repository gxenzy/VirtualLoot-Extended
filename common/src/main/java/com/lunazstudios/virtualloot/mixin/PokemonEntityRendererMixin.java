package com.lunazstudios.virtualloot.mixin;

import com.cobblemon.mod.common.client.render.pokemon.PokemonRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.lunazstudios.virtualloot.client.visual.VirtualRenderShaderHelper;
import com.lunazstudios.virtualloot.client.visual.VirtualShaderBufferWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PokemonRenderer.class, remap = false)
public abstract class PokemonEntityRendererMixin {

    @Inject(method = "render(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void virtualloot$applyVisualTransformations(PokemonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        int mode = VirtualRenderShaderHelper.getVisualMode(entity);
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

    @ModifyVariable(method = "render(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private MultiBufferSource virtualloot$wrapBuffer(MultiBufferSource buffer, PokemonEntity entity) {
        int mode = VirtualRenderShaderHelper.getVisualMode(entity);
        if (mode > 0) {
            return new VirtualShaderBufferWrapper(buffer, mode);
        }
        return buffer;
    }
}
