package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PastureWidget;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = PastureWidget.class, priority = 2000)
public abstract class CobblebaseButtonPositionBridgeMixin {

    @Inject(method = "renderWidget", at = @At("TAIL"))
    private void virtualloot$adjustCobblebaseButtonPosition(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (client.screen == null) return;

        int screenW = client.getWindow().getGuiScaledWidth();
        int screenH = client.getWindow().getGuiScaledHeight();
        int pcW = PCGUI.BASE_WIDTH;
        int pcH = PCGUI.BASE_HEIGHT;
        int pcX = (screenW - pcW) / 2;
        int pcY = (screenH - pcH) / 2;

        int targetX = pcX + 210;
        int targetY = pcY - 16;

        // Reposition Fabric Cobblebase button away from CloudTweak overlay
        try {
            Class<?> holderClass = Class.forName("notlown.cobblebase.fabric.client.gui.CobblebaseButtonHolder", false, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Field field = holderClass.getField("activeButton");
            Object btnObj = field.get(null);
            if (btnObj instanceof Button btn) {
                btn.setX(targetX);
                btn.setY(targetY);
            }
        } catch (Throwable ignored) {
        }

        // Reposition NeoForge Cobblebase button away from CloudTweak overlay
        try {
            Class<?> holderClass = Class.forName("notlown.cobblebase.neoforge.client.gui.CobblebaseButtonHolder", false, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Field field = holderClass.getField("activeButton");
            Object btnObj = field.get(null);
            if (btnObj instanceof Button btn) {
                btn.setX(targetX);
                btn.setY(targetY);
            }
        } catch (Throwable ignored) {
        }
    }
}
