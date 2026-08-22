package com.lunazstudios.virtualloot.fabric.client;

import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlay;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.renderer.RenderType;

public final class VirtualLootFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VirtualLootBlocks.VIRTUAL_PASTURE.get(), RenderType.cutout());

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen.getClass().getName().contains("AdminScreen")) {
                ScreenEvents.afterRender(screen).register((scr, context, mouseX, mouseY, delta) -> {
                    AdminStatsOverlay.render(context, scr, mouseX, mouseY, delta);
                });
                ScreenMouseEvents.beforeMouseClick(screen).register((scr, mouseX, mouseY, button) -> {
                    return !AdminStatsOverlay.mouseClicked(scr, mouseX, mouseY, button);
                });
                ScreenMouseEvents.beforeMouseScroll(screen).register((scr, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                    return !AdminStatsOverlay.mouseScrolled(scr, mouseX, mouseY, verticalAmount);
                });
            }
        });
    }
}
