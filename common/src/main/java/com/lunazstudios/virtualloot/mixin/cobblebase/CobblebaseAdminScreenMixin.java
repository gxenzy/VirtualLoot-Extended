package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlay;
import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlayWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class CobblebaseAdminScreenMixin {

    @Shadow
    protected abstract <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T widget);

    @Shadow
    public abstract List<? extends GuiEventListener> children();

    @Inject(method = "render", at = @At("TAIL"))
    private void virtualloot$onAdminScreenRender(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.getClass().getName().contains("AdminScreen")) {
            boolean hasWidget = false;
            for (GuiEventListener child : children()) {
                if (child instanceof AdminStatsOverlayWidget) {
                    hasWidget = true;
                    break;
                }
            }
            if (!hasWidget) {
                this.addRenderableWidget(new AdminStatsOverlayWidget((Screen) (Object) this));
            }
            AdminStatsOverlay.render(context, this, mouseX, mouseY, delta);
        }
    }
}
