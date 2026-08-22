package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "notlown.cobblebase.neoforge.client.gui.AdminScreen")
public abstract class CobblebaseNeoForgeAdminScreenMixin extends Screen {

    protected CobblebaseNeoForgeAdminScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void virtualloot$onAdminScreenRender(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        AdminStatsOverlay.render(context, this, mouseX, mouseY, delta);
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void virtualloot$onAdminScreenMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (AdminStatsOverlay.mouseClicked(this, mouseX, mouseY, button)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mouseScrolled", at = @At("HEAD"), cancellable = true)
    private void virtualloot$onAdminScreenMouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
        if (AdminStatsOverlay.mouseScrolled(this, mouseX, mouseY, verticalAmount)) {
            cir.setReturnValue(true);
        }
    }
}
