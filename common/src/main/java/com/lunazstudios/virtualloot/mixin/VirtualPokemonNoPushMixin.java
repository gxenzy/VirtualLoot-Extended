package com.lunazstudios.virtualloot.mixin;

import com.lunazstudios.virtualloot.client.visual.VirtualRenderShaderHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class VirtualPokemonNoPushMixin {

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    private void virtualloot$disablePushable(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (VirtualRenderShaderHelper.getVisualMode(self) > 0) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "push", at = @At("HEAD"), cancellable = true)
    private void virtualloot$cancelPush(Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (VirtualRenderShaderHelper.getVisualMode(self) > 0) {
            ci.cancel();
        }
    }

    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
    private void virtualloot$cancelDoPush(Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (VirtualRenderShaderHelper.getVisualMode(self) > 0) {
            ci.cancel();
        }
    }
}
