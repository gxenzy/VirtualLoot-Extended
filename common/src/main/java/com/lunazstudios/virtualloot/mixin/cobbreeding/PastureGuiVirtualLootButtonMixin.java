package com.lunazstudios.virtualloot.mixin.cobbreeding;

import com.cobblemon.mod.common.block.PastureBlock;
import com.cobblemon.mod.common.client.gui.pasture.PasturePCGUIConfiguration;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.PCGUIConfiguration;
import com.lunazstudios.virtualloot.client.cobbreeding.VirtualLootToggleButton;
import com.lunazstudios.virtualloot.client.gui.HudConfigManager;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mixin(PCGUI.class)
public abstract class PastureGuiVirtualLootButtonMixin extends Screen {
    @Shadow(remap = false)
    @Final
    private PCGUIConfiguration configuration;

    protected PastureGuiVirtualLootButtonMixin(Component title) {
        super(title);
    }

    private BlockPos virtualloot$getPasturePos() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            return null;
        }

        // 1. Try extracting from configuration object via reflection
        try {
            for (Field f : configuration.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(configuration);
                if (val instanceof BlockPos bp) {
                    BlockState s = minecraft.level.getBlockState(bp);
                    if (s.hasProperty(PastureBlock.Companion.getPART())) {
                        return s.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP ? bp.below() : bp;
                    }
                    return bp;
                }
            }
            for (Method m : configuration.getClass().getMethods()) {
                if (m.getParameterCount() == 0 && BlockPos.class.isAssignableFrom(m.getReturnType())) {
                    BlockPos bp = (BlockPos) m.invoke(configuration);
                    if (bp != null) {
                        BlockState s = minecraft.level.getBlockState(bp);
                        if (s.hasProperty(PastureBlock.Companion.getPART())) {
                            return s.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP ? bp.below() : bp;
                        }
                        return bp;
                    }
                }
            }
        } catch (Throwable ignored) {
        }

        // 2. Fallback to raytrace
        if (minecraft.hitResult instanceof BlockHitResult blockHitResult && blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = blockHitResult.getBlockPos();
            BlockState hitState = minecraft.level.getBlockState(hitPos);
            if (hitState.hasProperty(PastureBlock.Companion.getPART())) {
                if (hitState.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
                    hitPos = hitPos.below();
                }
                return hitPos;
            }
        }

        // 3. Fallback: search around player for nearby pasture block
        BlockPos playerPos = minecraft.player.blockPosition();
        for (int dy = -2; dy <= 3; dy++) {
            for (int dx = -5; dx <= 5; dx++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos p = playerPos.offset(dx, dy, dz);
                    BlockState s = minecraft.level.getBlockState(p);
                    if (s.hasProperty(PastureBlock.Companion.getPART()) && s.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM) {
                        return p;
                    }
                }
            }
        }

        return null;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void virtualloot$addVirtualLootToggle(CallbackInfo ci) {
        if (!(configuration instanceof PasturePCGUIConfiguration)) {
            return;
        }

        BlockPos pos = virtualloot$getPasturePos();
        if (pos == null) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        BlockState state = minecraft.level.getBlockState(pos);
        if (!VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            return;
        }

        HudConfigManager.load();

        int pcX = (width - PCGUI.BASE_WIDTH) / 2;
        int pcY = (height - PCGUI.BASE_HEIGHT) / 2;
        addRenderableWidget(new VirtualLootToggleButton(
            pcX + HudConfigManager.data.virtualLootOffsetX,
            pcY + HudConfigManager.data.virtualLootOffsetY,
            18,
            18,
            pos
        ));
    }
}
