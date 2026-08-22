package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import net.minecraft.client.Minecraft;
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
        // Try Fabric holder
        try {
            Class<?> holderClass = Class.forName("notlown.cobblebase.fabric.client.gui.CobblebaseButtonHolder", false, Thread.currentThread().getContextClassLoader());
            java.lang.reflect.Field field = holderClass.getField("activeButton");
            Object obj = field.get(null);
            if (obj instanceof Button btn) return btn;
        } catch (Throwable ignored) {
        }

        // Try NeoForge holder
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
    private void virtualloot$onInitCobblebaseButton(CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        int btnX = pcX + 208;
        int btnY = pcY - 13;
        int btnW = 78;
        int btnH = 16;

        Button activeBtn = virtualloot$getCobblebaseButton();
        if (activeBtn != null) {
            // Hide Cobblebase's own hardcoded button so it doesn't draw at (pcX + 271)
            activeBtn.visible = false;
        }

        // Add our clean, perfectly-aligned button widget to the screen
        Button customBtn = Button.builder(Component.literal("§bCobblebase"), b -> {
            Button target = virtualloot$getCobblebaseButton();
            if (target != null) {
                target.onPress();
            }
        }).bounds(btnX, btnY, btnW, btnH).build();

        addRenderableWidget(customBtn);
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void virtualloot$ensureOriginalHidden(net.minecraft.client.gui.GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (configuration instanceof PasturePCGUIConfiguration) {
            Button activeBtn = virtualloot$getCobblebaseButton();
            if (activeBtn != null) {
                activeBtn.visible = false;
            }
        }
    }
}
