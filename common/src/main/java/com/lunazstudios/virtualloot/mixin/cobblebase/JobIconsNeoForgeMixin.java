package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.lunazstudios.virtualloot.integration.cobblebase.CobblebaseJobIconBridge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "notlown.cobblebase.neoforge.client.gui.JobIcons", remap = false)
public class JobIconsNeoForgeMixin {

    @Inject(method = "itemFor", at = @At("RETURN"), cancellable = true, remap = false)
    private void virtualloot$upgradeItemIcon(String skillId, CallbackInfoReturnable<Item> cir) {
        Item original = cir.getReturnValue();
        Item upgraded = CobblebaseJobIconBridge.getItemFor(skillId, original);
        if (upgraded != original) {
            cir.setReturnValue(upgraded);
        }
    }

    @Inject(method = "stackFor", at = @At("RETURN"), cancellable = true, remap = false)
    private void virtualloot$upgradeStackIcon(String skillId, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack original = cir.getReturnValue();
        ItemStack upgraded = CobblebaseJobIconBridge.getStackFor(skillId, original);
        if (upgraded != original) {
            cir.setReturnValue(upgraded);
        }
    }
}
