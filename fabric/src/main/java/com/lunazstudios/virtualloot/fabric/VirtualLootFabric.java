package com.lunazstudios.virtualloot.fabric;

import com.cobblemon.mod.fabric.net.FabricPacketInfo;
import com.lunazstudios.virtualloot.VirtualLoot;
import com.lunazstudios.virtualloot.network.VirtualLootNetwork;
import net.fabricmc.api.ModInitializer;

public final class VirtualLootFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VirtualLoot.init();

        FabricPacketInfo<?> toggleInfo = new FabricPacketInfo<>(VirtualLootNetwork.TOGGLE_VIRTUAL_LOOT);
        toggleInfo.registerPacket(false);
        toggleInfo.registerServerHandler();

        FabricPacketInfo<?> visualModeInfo = new FabricPacketInfo<>(VirtualLootNetwork.SET_VISUAL_MODE);
        visualModeInfo.registerPacket(false);
        visualModeInfo.registerServerHandler();

        FabricPacketInfo<?> syncVisualInfo = new FabricPacketInfo<>(VirtualLootNetwork.SYNC_VISUAL_MODE);
        syncVisualInfo.registerPacket(true);
    }
}
