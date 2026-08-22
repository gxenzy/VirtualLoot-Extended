package com.lunazstudios.virtualloot.client.cobbreeding;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonSounds;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.network.ToggleVirtualLootPacket;
import net.minecraft.client.Minecraft;
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
    private static final ResourceLocation CROSS = ResourceLocation.fromNamespaceAndPath("cobbreeding", "textures/gui/cross.png");
    private final BlockPos blockPos;
    private final ItemStack icon = new ItemStack(Items.STICK);

    public VirtualLootToggleButton(int x, int y, int width, int height, BlockPos blockPos) {
        super(x, y, width, height, Component.empty());
        this.blockPos = blockPos;
        setTooltip(Tooltip.create(Component.translatable("virtualloot.msg.pasture_loot_button.tooltip")));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendToServer(new ToggleVirtualLootPacket(blockPos));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(TEXTURE, getX(), getY(), width, height, 0.0F, 0.0F, width, height, width, height);
        guiGraphics.renderItem(icon, getX() + 2, getY() + 1);
        if (!isLootEnabled()) {
            guiGraphics.blit(CROSS, getX() + 3, getY() + 3, 14, 14, 0.0F, 0.0F, 14, 14, 14, 14);
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

    private boolean isLootEnabled() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            return true;
        }
        BlockState state = minecraft.level.getBlockState(blockPos);
        return VirtualPastureBlock.isLootEnabled(state);
    }
}
