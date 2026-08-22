package com.lunazstudios.virtualloot.client.cobbreeding;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.client.gui.HudConfigManager;
import com.lunazstudios.virtualloot.network.ToggleVirtualLootPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public final class VirtualLootToggleButton extends AbstractWidget {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("virtualloot", "textures/gui/loot_button.png");
    private final BlockPos blockPos;
    private final ItemStack icon = new ItemStack(Items.CHEST);

    private Boolean optimisticEnabled = null;
    private boolean isDragging = false;
    private double dragStartX;
    private double dragStartY;
    private int initialOffsetX;
    private int initialOffsetY;

    public VirtualLootToggleButton(int x, int y, int width, int height, BlockPos blockPos) {
        super(x, y, width, height, Component.empty());
        this.blockPos = blockPos;
        setTooltip(Tooltip.create(Component.translatable("virtualloot.msg.pasture_loot_button.tooltip")));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (HudConfigManager.editMode) {
            return;
        }
        boolean currentState = isLootEnabled();
        optimisticEnabled = !currentState;
        if (blockPos != null) {
            Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendToServer(new ToggleVirtualLootPacket(blockPos));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            if (HudConfigManager.editMode) {
                isDragging = true;
                dragStartX = mouseX;
                dragStartY = mouseY;
                initialOffsetX = HudConfigManager.data.virtualLootOffsetX;
                initialOffsetY = HudConfigManager.data.virtualLootOffsetY;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isDragging) {
            isDragging = false;
            HudConfigManager.save();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && HudConfigManager.editMode) {
            int deltaX = (int) (mouseX - dragStartX);
            int deltaY = (int) (mouseY - dragStartY);
            HudConfigManager.data.virtualLootOffsetX = initialOffsetX + deltaX;
            HudConfigManager.data.virtualLootOffsetY = initialOffsetY + deltaY;

            Minecraft mc = Minecraft.getInstance();
            int pcX = (mc.getWindow().getGuiScaledWidth() - PCGUI.BASE_WIDTH) / 2;
            int pcY = (mc.getWindow().getGuiScaledHeight() - PCGUI.BASE_HEIGHT) / 2;
            setX(pcX + HudConfigManager.data.virtualLootOffsetX);
            setY(pcY + HudConfigManager.data.virtualLootOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        int x = getX();
        int y = getY();

        // 1. Draw base button texture
        guiGraphics.blit(TEXTURE, x, y, width, height, 0.0F, 0.0F, width, height, width, height);

        // 2. Draw chest item icon
        guiGraphics.renderItem(icon, x + (width - 16) / 2, y + (height - 16) / 2);

        // 3. Draw red overlay & bold X mark if disabled
        boolean enabled = isLootEnabled();
        if (!enabled) {
            // Semi-transparent red overlay
            guiGraphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, 0x88CC1111);

            // Bold red X badge
            Font font = Minecraft.getInstance().font;
            int textW = font.width("✕");
            int textX = x + (width - textW) / 2;
            int textY = y + (height - 8) / 2;
            guiGraphics.drawString(font, "§c§l✕", textX, textY, 0xFF5555, true);
        }

        // 4. Highlight dashed/golden border in HUD edit mode
        if (HudConfigManager.editMode) {
            int outlineColor = isDragging ? 0xFFFFD700 : 0xFF58A6FF;
            guiGraphics.fill(x - 1, y - 1, x + width + 1, y, outlineColor);
            guiGraphics.fill(x - 1, y + height, x + width + 1, y + height + 1, outlineColor);
            guiGraphics.fill(x - 1, y, x, y + height, outlineColor);
            guiGraphics.fill(x + width, y, x + width + 1, y + height, outlineColor);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, isLootEnabled() ? "Virtual loot ON" : "Virtual loot OFF");
    }

    @Override
    public void playDownSound(net.minecraft.client.sounds.SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(CobblemonSounds.PC_CLICK, 1.0F));
    }

    public boolean isLootEnabled() {
        if (optimisticEnabled != null) {
            return optimisticEnabled;
        }
        if (blockPos == null) {
            return true;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return true;
        }
        BlockState state = minecraft.level.getBlockState(blockPos);
        return VirtualPastureBlock.isLootEnabled(state);
    }
}
