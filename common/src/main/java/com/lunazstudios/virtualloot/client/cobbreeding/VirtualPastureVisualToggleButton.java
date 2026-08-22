package com.lunazstudios.virtualloot.client.cobbreeding;

import com.cobblemon.mod.common.api.net.NetworkPacket;
import com.cobblemon.mod.common.block.PastureBlock;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.client.gui.HudConfigManager;
import com.lunazstudios.virtualloot.network.SetVirtualPastureVisualModePacket;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public final class VirtualPastureVisualToggleButton extends AbstractWidget {

    private final BlockPos pasturePos;
    private int currentMode;
    private boolean isDragging = false;
    private double dragStartX;
    private double dragStartY;
    private int initialOffsetX;
    private int initialOffsetY;

    public VirtualPastureVisualToggleButton(int x, int y, BlockPos pasturePos) {
        super(x, y, 18, 18, Component.empty());
        this.pasturePos = pasturePos;
        this.currentMode = resolveCurrentMode();
        updateTooltip();
    }

    private int resolveCurrentMode() {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        if (level != null && pasturePos != null) {
            BlockPos targetPos = pasturePos;
            BlockState state = level.getBlockState(targetPos);
            if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                if (state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
                    targetPos = targetPos.below();
                    state = level.getBlockState(targetPos);
                }
                return VirtualPastureBlock.getVisualMode(state);
            }
        }
        return 0;
    }

    private void updateTooltip() {
        String title;
        String desc;
        if (currentMode == 1) {
            title = "§a§lVisuals: WIREFRAME";
            desc = "§7Cybernetic wireframe mesh.\n§70 Server Lag • Client Visuals Only.";
        } else if (currentMode == 2) {
            title = "§b§lVisuals: HOLOGRAM";
            desc = "§7Glowing cyan holographic projection.\n§70 Server Lag • Client Visuals Only.";
        } else if (currentMode == 3) {
            title = "§d§lVisuals: GHOST";
            desc = "§7Ethereal translucent spirit models.\n§70 Server Lag • Client Visuals Only.";
        } else {
            title = "§c§lVisuals: OFF";
            desc = "§7Zero visual entities spawned.\n§aMaximum Performance Mode.";
        }
        setTooltip(Tooltip.create(Component.literal(title + "\n" + desc)));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (HudConfigManager.editMode) {
            return;
        }

        // Cycle through: OFF (0) -> WIREFRAME (1) -> HOLOGRAM (2) -> GHOST (3) -> OFF (0)
        currentMode = (currentMode + 1) % 4;
        updateTooltip();

        if (pasturePos != null) {
            NetworkPacket<?> packet = new SetVirtualPastureVisualModePacket(pasturePos, currentMode);
            packet.sendToServer();

            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                net.minecraft.world.level.block.entity.BlockEntity be = mc.level.getBlockEntity(pasturePos);
                if (be instanceof com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity pasture) {
                    java.util.List<net.minecraft.nbt.CompoundTag> clientTags = new java.util.ArrayList<>();
                    if (currentMode > 0) {
                        for (com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity.Tethering t : pasture.getTetheredPokemon()) {
                            com.cobblemon.mod.common.pokemon.Pokemon pkmn = t.getPokemon();
                            if (pkmn == null) {
                                pkmn = com.lunazstudios.virtualloot.client.visual.PokemonSyncHelper.getPokemonFromPC(t.getPokemonId());
                            }
                            if (pkmn != null) {
                                net.minecraft.nbt.CompoundTag tag = com.lunazstudios.virtualloot.client.visual.PokemonSyncHelper.serializePokemon(pkmn);
                                if (!tag.isEmpty()) clientTags.add(tag);
                            }
                        }
                    }
                    com.lunazstudios.virtualloot.client.visual.VirtualPastureVisualizer.handleServerSync(pasturePos, currentMode, clientTags);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && HudConfigManager.editMode && isMouseOver(mouseX, mouseY)) {
            isDragging = true;
            dragStartX = mouseX;
            dragStartY = mouseY;
            initialOffsetX = HudConfigManager.data.visualModeOffsetX;
            initialOffsetY = HudConfigManager.data.visualModeOffsetY;
            return true;
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
            HudConfigManager.data.visualModeOffsetX = initialOffsetX + deltaX;
            HudConfigManager.data.visualModeOffsetY = initialOffsetY + deltaY;

            int pcX = (Minecraft.getInstance().getWindow().getGuiScaledWidth() - com.cobblemon.mod.common.client.gui.pc.PCGUI.BASE_WIDTH) / 2;
            int pcY = (Minecraft.getInstance().getWindow().getGuiScaledHeight() - com.cobblemon.mod.common.client.gui.pc.PCGUI.BASE_HEIGHT) / 2;
            setX(pcX + HudConfigManager.data.visualModeOffsetX);
            setY(pcY + HudConfigManager.data.visualModeOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        int x = getX();
        int y = getY();
        int w = getWidth();
        int h = getHeight();

        // 1. Draw Slot Background & Border
        int borderCol = (currentMode == 1) ? 0xFF00FFCC : (currentMode == 2) ? 0xFF00D4FF : (currentMode == 3) ? 0xFFDA70D6 : 0xFF555566;
        int bgCol = (currentMode == 0) ? 0xFF14141E : 0xFF1E2538;
        context.fill(x, y, x + w, y + h, borderCol);
        context.fill(x + 1, y + 1, x + w - 1, y + h - 1, bgCol);

        // 2. Draw Mode Icon / Badge
        Font font = Minecraft.getInstance().font;
        String icon = (currentMode == 1) ? "§a🌐" : (currentMode == 2) ? "§b💠" : (currentMode == 3) ? "§d👻" : "§8🔮";
        int textW = font.width(icon);
        int tx = x + (w - textW) / 2;
        int ty = y + (h - 8) / 2;
        context.drawString(font, icon, tx, ty, 0xFFFFFF, false);

        // 3. Highlight in HUD Edit Mode
        if (HudConfigManager.editMode) {
            int editBorder = isDragging ? 0xFFFFD700 : 0xFF58A6FF;
            context.fill(x - 1, y - 1, x + w + 1, y, editBorder);
            context.fill(x - 1, y + h, x + w + 1, y + h + 1, editBorder);
            context.fill(x - 1, y, x, y + h, editBorder);
            context.fill(x + w, y, x + w + 1, y + h, editBorder);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}
