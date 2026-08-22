package com.lunazstudios.virtualloot.mixin.cobblebase;

import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import com.lunazstudios.virtualloot.client.gui.HudConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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

@Mixin(value = PCGUI.class, priority = 2000)
public abstract class CobblebaseButtonPositionBridgeMixin extends Screen {

    @Shadow(remap = false)
    @Final
    private PCGUIConfiguration configuration;

    @Unique
    private Button virtualloot$cobblebaseBtn;

    @Unique
    private Button virtualloot$hudEditBtn;

    @Unique
    private Button virtualloot$resetHudBtn;

    @Unique
    private boolean virtualloot$isDraggingCobblebase = false;

    @Unique
    private double virtualloot$dragStartX;

    @Unique
    private double virtualloot$dragStartY;

    @Unique
    private int virtualloot$initialOffsetX;

    @Unique
    private int virtualloot$initialOffsetY;

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

        HudConfigManager.load();

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        Button activeBtn = virtualloot$getCobblebaseButton();
        if (activeBtn != null) {
            activeBtn.visible = false;
        }

        // 1. Cobblebase Button with draggable capability
        virtualloot$cobblebaseBtn = Button.builder(Component.literal("§bCobblebase"), b -> {
            if (HudConfigManager.editMode) return;
            Button target = virtualloot$getCobblebaseButton();
            if (target != null) {
                target.onPress();
            }
        }).bounds(pcX + HudConfigManager.data.cobblebaseOffsetX, pcY + HudConfigManager.data.cobblebaseOffsetY, 78, 16).build();

        addRenderableWidget(virtualloot$cobblebaseBtn);

        // 2. HUD Edit Mode Toggle Button (small gear icon at pcX + 332, pcY - 14)
        virtualloot$hudEditBtn = Button.builder(Component.literal(HudConfigManager.editMode ? "§6✓ Done" : "§7⚙ HUD"), b -> {
            HudConfigManager.editMode = !HudConfigManager.editMode;
            b.setMessage(Component.literal(HudConfigManager.editMode ? "§6✓ Done" : "§7⚙ HUD"));
            if (virtualloot$resetHudBtn != null) {
                virtualloot$resetHudBtn.visible = HudConfigManager.editMode;
            }
            if (!HudConfigManager.editMode) {
                HudConfigManager.save();
            }
        }).bounds(pcX + 288, pcY - 14, 46, 14).build();

        addRenderableWidget(virtualloot$hudEditBtn);

        // 3. Reset Defaults Button (visible only in HUD edit mode)
        virtualloot$resetHudBtn = Button.builder(Component.literal("§c↺ Reset"), b -> {
            HudConfigManager.resetDefaults();
            if (virtualloot$cobblebaseBtn != null) {
                virtualloot$cobblebaseBtn.setX(pcX + HudConfigManager.data.cobblebaseOffsetX);
                virtualloot$cobblebaseBtn.setY(pcY + HudConfigManager.data.cobblebaseOffsetY);
            }
            this.repositionElements();
        }).bounds(pcX + 242, pcY - 14, 44, 14).build();
        virtualloot$resetHudBtn.visible = HudConfigManager.editMode;

        addRenderableWidget(virtualloot$resetHudBtn);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && HudConfigManager.editMode && virtualloot$cobblebaseBtn != null) {
            if (virtualloot$cobblebaseBtn.isMouseOver(mouseX, mouseY)) {
                virtualloot$isDraggingCobblebase = true;
                virtualloot$dragStartX = mouseX;
                virtualloot$dragStartY = mouseY;
                virtualloot$initialOffsetX = HudConfigManager.data.cobblebaseOffsetX;
                virtualloot$initialOffsetY = HudConfigManager.data.cobblebaseOffsetY;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (virtualloot$isDraggingCobblebase) {
            virtualloot$isDraggingCobblebase = false;
            HudConfigManager.save();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (virtualloot$isDraggingCobblebase && HudConfigManager.editMode && virtualloot$cobblebaseBtn != null) {
            int deltaX = (int) (mouseX - virtualloot$dragStartX);
            int deltaY = (int) (mouseY - virtualloot$dragStartY);
            HudConfigManager.data.cobblebaseOffsetX = virtualloot$initialOffsetX + deltaX;
            HudConfigManager.data.cobblebaseOffsetY = virtualloot$initialOffsetY + deltaY;

            int pcX = (width - PCGUI.BASE_WIDTH) / 2;
            int pcY = (height - PCGUI.BASE_HEIGHT) / 2;
            virtualloot$cobblebaseBtn.setX(pcX + HudConfigManager.data.cobblebaseOffsetX);
            virtualloot$cobblebaseBtn.setY(pcY + HudConfigManager.data.cobblebaseOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void virtualloot$renderHudOverlay(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (configuration instanceof PasturePCGUIConfiguration) {
            Button activeBtn = virtualloot$getCobblebaseButton();
            if (activeBtn != null) {
                activeBtn.visible = false;
            }

            if (HudConfigManager.editMode) {
                // Highlight Cobblebase button with golden border
                if (virtualloot$cobblebaseBtn != null) {
                    int bx = virtualloot$cobblebaseBtn.getX();
                    int by = virtualloot$cobblebaseBtn.getY();
                    int bw = virtualloot$cobblebaseBtn.getWidth();
                    int bh = virtualloot$cobblebaseBtn.getHeight();
                    int color = virtualloot$isDraggingCobblebase ? 0xFFFFD700 : 0xFF58A6FF;
                    context.fill(bx - 1, by - 1, bx + bw + 1, by, color);
                    context.fill(bx - 1, by + bh, bx + bw + 1, by + bh + 1, color);
                    context.fill(bx - 1, by, bx, by + bh, color);
                    context.fill(bx + bw, by, bx + bw + 1, by + bh, color);
                }

                // Draw Edit Mode banner text
                Font font = Minecraft.getInstance().font;
                String banner = "§6§l[HUD Edit Mode] §eDrag buttons to reposition anywhere • Click [✓ Done] to save";
                context.drawString(font, banner, 8, 8, 0xFFFFFF, true);
            }
        }
    }
}
