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
        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        BlockPos pos = null;
        if (minecraft.hitResult instanceof BlockHitResult blockHitResult && blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = blockHitResult.getBlockPos();
            BlockState hitState = minecraft.level.getBlockState(hitPos);
            if (hitState.hasProperty(PastureBlock.Companion.getPART())) {
                if (hitState.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
                    hitPos = hitPos.below();
                }
                pos = hitPos;
            }
        }

        // Fallback: search around player for nearby pasture block
        if (pos == null) {
            BlockPos playerPos = minecraft.player.blockPosition();
            outer:
            for (int dy = -2; dy <= 3; dy++) {
                for (int dx = -5; dx <= 5; dx++) {
                    for (int dz = -5; dz <= 5; dz++) {
                        BlockPos p = playerPos.offset(dx, dy, dz);
                        BlockState s = minecraft.level.getBlockState(p);
                        if (s.hasProperty(PastureBlock.Companion.getPART()) && s.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM) {
                            pos = p;
                            break outer;
                        }
                    }
                }
            }
        }

        if (pos == null) {
            return;
        }

        BlockState state = minecraft.level.getBlockState(pos);
        if (!VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            return;
        }

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;
        addRenderableWidget(new VirtualLootToggleButton(pcX + 290, pcY - 13, 20, 18, pos));
    }
}
