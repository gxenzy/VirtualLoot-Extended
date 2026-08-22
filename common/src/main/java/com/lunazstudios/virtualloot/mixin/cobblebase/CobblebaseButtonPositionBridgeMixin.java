package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Unique
    private static Button virtualloot$getCobblebaseButton() {
        try {
            Class<?> holderClass = Class.forName("notlown.cobblebase.fabric.client.gui.CobblebaseButtonHolder", false, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Field field = holderClass.getField("activeButton");
            Object obj = field.get(null);
            if (obj instanceof Button btn) return btn;
        } catch (Throwable ignored) {
        }
        try {
            Class<?> holderClass = Class.forName("notlown.cobblebase.neoforge.client.gui.CobblebaseButtonHolder", false, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Field field = holderClass.getField("activeButton");
            Object obj = field.get(null);
            if (obj instanceof Button btn) return btn;
        } catch (Throwable ignored) {
        }
        return null;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void virtualloot$initCobblebaseButton(CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        Button activeBtn = virtualloot$getCobblebaseButton();
        if (activeBtn != null) {
            activeBtn.visible = false;
        }

        // Place Cobblebase button flush to the TOP-LEFT of PC GUI window
        Button customBtn = Button.builder(Component.literal("§bCobblebase"), b -> {
            Button target = virtualloot$getCobblebaseButton();
            if (target != null) {
                target.onPress();
            }
        }).bounds(pcX, pcY - 16, 78, 16).build();

        addRenderableWidget(customBtn);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void virtualloot$hideOriginalCobblebaseButton(net.minecraft.client.gui.GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (configuration instanceof PasturePCGUIConfiguration) {
            Button activeBtn = virtualloot$getCobblebaseButton();
            if (activeBtn != null) {
                activeBtn.visible = false;
            }
        }
    }
}
