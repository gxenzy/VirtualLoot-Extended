package com.lunazstudios.virtualloot.client.cobblebase;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;

public class AdminStatsOverlayWidget implements Renderable, GuiEventListener, NarratableEntry {

    private final Screen screen;

    public AdminStatsOverlayWidget(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        AdminStatsOverlay.render(guiGraphics, screen, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return AdminStatsOverlay.mouseClicked(screen, mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        return AdminStatsOverlay.mouseScrolled(screen, mouseX, mouseY, scrollY);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return AdminStatsOverlay.isStatsTabActive(screen);
    }

    @Override
    public void setFocused(boolean focused) {
    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }
}
