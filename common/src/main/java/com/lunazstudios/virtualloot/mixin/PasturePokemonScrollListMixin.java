package com.lunazstudios.virtualloot.mixin;

import com.cobblemon.mod.common.net.messages.client.pasture.OpenPasturePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.cobblemon.mod.common.client.gui.pasture.PasturePokemonScrollList$PastureSlot", remap = false)
public abstract class PasturePokemonScrollListMixin {
    @Shadow(remap = false)
    public abstract OpenPasturePacket.PasturePokemonDataDTO getPokemon();

    @Inject(method = "canDefend", at = @At("HEAD"), cancellable = true, remap = false)
    private void virtualloot$hideDefendForVirtualPokemon(CallbackInfoReturnable<Boolean> cir) {
        if (!getPokemon().getEntityKnown()) {
            cir.setReturnValue(false);
        }
    }
}
