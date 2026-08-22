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
    private Button virtualloot$doneBtn;

    @Unique
    private Button virtualloot$resetBtn;

    @Unique
    private boolean virtualloot$isDraggingCobblebase = false;

    @Unique
    private boolean virtualloot$isDraggingHudBtn = false;

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

        // 1. Draggable Cobblebase Button
        virtualloot$cobblebaseBtn = Button.builder(Component.literal("§bCobblebase"), b -> {
            if (HudConfigManager.editMode) return;
            Button target = virtualloot$getCobblebaseButton();
            if (target != null) {
                target.onPress();
            }
        }).bounds(pcX + HudConfigManager.data.cobblebaseOffsetX, pcY + HudConfigManager.data.cobblebaseOffsetY, 78, 16).build();

        addRenderableWidget(virtualloot$cobblebaseBtn);

        // 2. Draggable [⚙] Button (opens HUD edit mode)
        virtualloot$hudEditBtn = Button.builder(Component.literal("§7⚙"), b -> {
            if (HudConfigManager.editMode) return;
            HudConfigManager.editMode = true;
            virtualloot$updateEditModeVisibility();
        }).bounds(pcX + HudConfigManager.data.hudBtnOffsetX, pcY + HudConfigManager.data.hudBtnOffsetY, 18, 18).build();

        addRenderableWidget(virtualloot$hudEditBtn);

        // 3. Fixed Top Control Bar: [↺ Reset] and [✓ Done] (at top center of screen)
        int centerX = width / 2;
        virtualloot$resetBtn = Button.builder(Component.literal("§c↺ Reset"), b -> {
            HudConfigManager.resetDefaults();
            int curPcX = (width - PCGUI.BASE_WIDTH) / 2;
            int curPcY = (height - PCGUI.BASE_HEIGHT) / 2;
            if (virtualloot$cobblebaseBtn != null) {
                virtualloot$cobblebaseBtn.setX(curPcX + HudConfigManager.data.cobblebaseOffsetX);
                virtualloot$cobblebaseBtn.setY(curPcY + HudConfigManager.data.cobblebaseOffsetY);
            }
            if (virtualloot$hudEditBtn != null) {
                virtualloot$hudEditBtn.setX(curPcX + HudConfigManager.data.hudBtnOffsetX);
                virtualloot$hudEditBtn.setY(curPcY + HudConfigManager.data.hudBtnOffsetY);
            }
            this.repositionElements();
        }).bounds(centerX - 64, 4, 60, 18).build();

        virtualloot$doneBtn = Button.builder(Component.literal("§6✓ Done"), b -> {
            HudConfigManager.editMode = false;
            HudConfigManager.save();
            virtualloot$updateEditModeVisibility();
        }).bounds(centerX + 4, 4, 60, 18).build();

        virtualloot$updateEditModeVisibility();

        addRenderableWidget(virtualloot$resetBtn);
        addRenderableWidget(virtualloot$doneBtn);
    }

    @Unique
    private void virtualloot$updateEditModeVisibility() {
        if (virtualloot$resetBtn != null) {
            virtualloot$resetBtn.visible = HudConfigManager.editMode;
        }
        if (virtualloot$doneBtn != null) {
            virtualloot$doneBtn.visible = HudConfigManager.editMode;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && HudConfigManager.editMode) {
            // Drag Cobblebase button
            if (virtualloot$cobblebaseBtn != null && virtualloot$cobblebaseBtn.isMouseOver(mouseX, mouseY)) {
                virtualloot$isDraggingCobblebase = true;
                virtualloot$dragStartX = mouseX;
                virtualloot$dragStartY = mouseY;
                virtualloot$initialOffsetX = HudConfigManager.data.cobblebaseOffsetX;
                virtualloot$initialOffsetY = HudConfigManager.data.cobblebaseOffsetY;
                return true;
            }
            // Drag [⚙] HUD button
            if (virtualloot$hudEditBtn != null && virtualloot$hudEditBtn.isMouseOver(mouseX, mouseY)) {
                virtualloot$isDraggingHudBtn = true;
                virtualloot$dragStartX = mouseX;
                virtualloot$dragStartY = mouseY;
                virtualloot$initialOffsetX = HudConfigManager.data.hudBtnOffsetX;
                virtualloot$initialOffsetY = HudConfigManager.data.hudBtnOffsetY;
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
        if (virtualloot$isDraggingHudBtn) {
            virtualloot$isDraggingHudBtn = false;
            HudConfigManager.save();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;

        if (virtualloot$isDraggingCobblebase && HudConfigManager.editMode && virtualloot$cobblebaseBtn != null) {
            int deltaX = (int) (mouseX - virtualloot$dragStartX);
            int deltaY = (int) (mouseY - virtualloot$dragStartY);
            HudConfigManager.data.cobblebaseOffsetX = virtualloot$initialOffsetX + deltaX;
            HudConfigManager.data.cobblebaseOffsetY = virtualloot$initialOffsetY + deltaY;

            virtualloot$cobblebaseBtn.setX(pcX + HudConfigManager.data.cobblebaseOffsetX);
            virtualloot$cobblebaseBtn.setY(pcY + HudConfigManager.data.cobblebaseOffsetY);
            return true;
        }

        if (virtualloot$isDraggingHudBtn && HudConfigManager.editMode && virtualloot$hudEditBtn != null) {
            int deltaX = (int) (mouseX - virtualloot$dragStartX);
            int deltaY = (int) (mouseY - virtualloot$dragStartY);
            HudConfigManager.data.hudBtnOffsetX = virtualloot$initialOffsetX + deltaX;
            HudConfigManager.data.hudBtnOffsetY = virtualloot$initialOffsetY + deltaY;

            virtualloot$hudEditBtn.setX(pcX + HudConfigManager.data.hudBtnOffsetX);
            virtualloot$hudEditBtn.setY(pcY + HudConfigManager.data.hudBtnOffsetY);
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
                // Golden highlight on Cobblebase button
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

                // Golden highlight on [⚙] button
                if (virtualloot$hudEditBtn != null) {
                    int bx = virtualloot$hudEditBtn.getX();
                    int by = virtualloot$hudEditBtn.getY();
                    int bw = virtualloot$hudEditBtn.getWidth();
                    int bh = virtualloot$hudEditBtn.getHeight();
                    int color = virtualloot$isDraggingHudBtn ? 0xFFFFD700 : 0xFF58A6FF;
                    context.fill(bx - 1, by - 1, bx + bw + 1, by, color);
                    context.fill(bx - 1, by + bh, bx + bw + 1, by + bh + 1, color);
                    context.fill(bx - 1, by, bx, by + bh, color);
                    context.fill(bx + bw, by, bx + bw + 1, by + bh, color);
                }

                // Top banner instruction text
                Font font = Minecraft.getInstance().font;
                String banner = "§6§l[HUD Edit Mode] §eDrag [Cobblebase], [Virtual Loot], [Visuals], or [⚙] anywhere • Click [✓ Done] to save";
                int textW = font.width(banner);
                int textX = (width - textW) / 2;
                context.drawString(font, banner, textX, 26, 0xFFFFFF, true);
            }
        }
    }
}
