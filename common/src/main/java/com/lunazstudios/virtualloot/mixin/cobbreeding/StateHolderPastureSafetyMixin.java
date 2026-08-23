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
        StateHolder<?, ?> self = (StateHolder<?, ?>) (Object) this;
        if (!self.hasProperty(property)) {
            // Prevent crash when Cobbreeding or third-party GUI mods query properties (like breeding_activated or PART)
            // on non-pasture blocks (e.g. grass_block or air when opening PC GUI)
            if (property == PastureBlock.Companion.getPART()) {
                cir.setReturnValue((T) PastureBlock.PasturePart.BOTTOM);
            } else if (property != null && property.getPossibleValues() != null && !property.getPossibleValues().isEmpty()) {
                cir.setReturnValue(property.getPossibleValues().iterator().next());
            }
        }
    }
}
