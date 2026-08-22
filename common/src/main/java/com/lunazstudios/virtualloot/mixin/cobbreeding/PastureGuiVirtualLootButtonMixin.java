package com.lunazstudios.virtualloot.mixin.cobbreeding;

import com.cobblemon.mod.common.block.PastureBlock;
import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import com.lunazstudios.virtualloot.client.cobbreeding.VirtualLootToggleButton;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PCGUI.class)
public abstract class PastureGuiVirtualLootButtonMixin extends Screen {
    @Shadow(remap = false)
    @Final
    private PCGUIConfiguration configuration;

    protected PastureGuiVirtualLootButtonMixin(Component title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void virtualloot$addVirtualLootToggle(CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || !(minecraft.hitResult instanceof BlockHitResult blockHitResult) || blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = minecraft.level.getBlockState(pos);
        if (!state.hasProperty(PastureBlock.Companion.getPART())) {
            return;
        }
        if (state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
            pos = pos.below();
            state = minecraft.level.getBlockState(pos);
        }
        if (!VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            return;
        }
        int x = (width - PCGUI.BASE_WIDTH) / 2;
        int y = (height - PCGUI.BASE_HEIGHT) / 2;
        addRenderableWidget(new VirtualLootToggleButton(x + 292, y - 10, 20, 18, pos));
    }
}
