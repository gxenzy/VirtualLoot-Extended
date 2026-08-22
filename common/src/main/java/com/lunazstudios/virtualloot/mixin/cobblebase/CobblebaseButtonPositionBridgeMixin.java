package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PCGUI.class, priority = 2000)
public abstract class CobblebaseButtonPositionBridgeMixin extends Screen {

    @Shadow(remap = false)
    @Final
    private PCGUIConfiguration configuration;

    protected CobblebaseButtonPositionBridgeMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void virtualloot$onInitCobblebaseButton(CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }
        try {
            me.shedaniel.autoconfig.AutoConfig.getConfigHolder(notlown.cobblebase.core.CobblebaseClothConfig.class)
                .getConfig().getGeneral().setMainButtonCorner(notlown.cobblebase.core.MainButtonCorner.TOP_RIGHT);
        } catch (Throwable ignored) {
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void virtualloot$adjustCobblebaseButtonPosition(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

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
