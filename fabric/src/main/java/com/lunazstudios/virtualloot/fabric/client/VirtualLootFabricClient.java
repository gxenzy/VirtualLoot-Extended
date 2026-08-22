package com.lunazstudios.virtualloot.fabric.client;

import com.cobblemon.mod.common.client.net.CobblemonClientNetwork;
import com.cobblemon.mod.fabric.net.FabricPacketInfo;
import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlay;
import com.lunazstudios.virtualloot.client.visual.VirtualPastureVisualizer;
import com.lunazstudios.virtualloot.network.SyncVirtualPastureVisualHandler;
import com.lunazstudios.virtualloot.network.SyncVirtualPastureVisualPacket;
import com.lunazstudios.virtualloot.network.VirtualLootNetwork;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.renderer.RenderType;

public final class VirtualLootFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VirtualLootBlocks.VIRTUAL_PASTURE.get(), RenderType.cutout());

        CobblemonClientNetwork.INSTANCE.registerPacket(
            SyncVirtualPastureVisualPacket.ID,
            SyncVirtualPastureVisualPacket::new,
            SyncVirtualPastureVisualHandler.INSTANCE
        );

        FabricPacketInfo<SyncVirtualPastureVisualPacket> syncVisualInfo = new FabricPacketInfo<>(VirtualLootNetwork.SYNC_VISUAL_MODE);
        syncVisualInfo.registerClientHandler();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            VirtualPastureVisualizer.clientTick();
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen.getClass().getName().contains("AdminScreen")) {
                ScreenEvents.afterRender(screen).register((scr, context, mouseX, mouseY, delta) -> {
                    AdminStatsOverlay.render(context, scr, mouseX, mouseY, delta);
                });
                ScreenMouseEvents.allowMouseClick(screen).register((scr, mouseX, mouseY, button) -> {
                    boolean handled = AdminStatsOverlay.mouseClicked(scr, mouseX, mouseY, button);
                    return !handled;
                });
                ScreenMouseEvents.allowMouseRelease(screen).register((scr, mouseX, mouseY, button) -> {
                    boolean handled = AdminStatsOverlay.mouseReleased(scr, mouseX, mouseY, button);
                    return !handled;
                });
                ScreenMouseEvents.allowMouseScroll(screen).register((scr, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                    boolean handled = AdminStatsOverlay.mouseScrolled(scr, mouseX, mouseY, verticalAmount);
                    return !handled;
                });
            }
        });
    }
}
