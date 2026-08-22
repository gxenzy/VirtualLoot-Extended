package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import com.lunazstudios.virtualloot.integration.cobblebase.CobblebaseCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    @Inject(method = "init", at = @At("HEAD"))
    private void virtualloot$onInitCobblebaseCorner(CallbackInfo ci) {
        if (configuration instanceof PasturePCGUIConfiguration) {
            CobblebaseCompat.configureCobblebaseButtonCorner();
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void virtualloot$onRenderCobblebasePosition(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }

        CobblebaseCompat.configureCobblebaseButtonCorner();

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        int btnX = pcX + 208;
        int btnY = pcY - 13;

        Button btn = virtualloot$getCobblebaseButton();
        if (btn != null) {
            btn.setX(btnX);
            btn.setY(btnY);
            btn.visible = true;
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, priority = 500)
    private void virtualloot$onCobblebaseButtonClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (!(configuration instanceof PasturePCGUIConfiguration) || button != 0) {
            return;
        }

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        int btnX = pcX + 208;
        int btnY = pcY - 13;
        int btnW = 78;
        int btnH = 16;

        if (mouseX >= btnX && mouseX <= btnX + btnW && mouseY >= btnY && mouseY <= btnY + btnH) {
            Button btn = virtualloot$getCobblebaseButton();
            if (btn != null) {
                btn.setX(btnX);
                btn.setY(btnY);
                btn.playDownSound(Minecraft.getInstance().getSoundManager());
                btn.onPress();
                cir.setReturnValue(true);
            }
        }
    }
}
