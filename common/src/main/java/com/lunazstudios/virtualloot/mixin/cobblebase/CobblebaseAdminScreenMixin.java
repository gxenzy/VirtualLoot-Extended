package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlayWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class CobblebaseAdminScreenMixin {

    @Shadow
    protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);

    @Inject(method = "init()V", at = @At("TAIL"))
    private void virtualloot$onScreenInit(CallbackInfo ci) {
        if (this.getClass().getName().contains("AdminScreen")) {
            this.addRenderableWidget(new AdminStatsOverlayWidget((Screen) (Object) this));
        }
    }
}
