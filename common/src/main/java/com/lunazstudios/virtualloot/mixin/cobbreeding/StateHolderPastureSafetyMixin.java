package com.lunazstudios.virtualloot.mixin.cobbreeding;

import com.cobblemon.mod.common.block.PastureBlock;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StateHolder.class)
public abstract class StateHolderPastureSafetyMixin {

    @Inject(method = "getValue", at = @At("HEAD"), cancellable = true)
    private <T extends Comparable<T>> void virtualloot$preventPasturePropertyCrash(Property<T> property, CallbackInfoReturnable<T> cir) {
        if (property == PastureBlock.Companion.getPART()) {
            StateHolder<?, ?> self = (StateHolder<?, ?>) (Object) this;
            if (!self.hasProperty(property)) {
                // Return default BOTTOM part instead of crashing when third-party mods query non-pasture blocks
                cir.setReturnValue((T) PastureBlock.PasturePart.BOTTOM);
            }
        }
    }
}
