package com.lunazstudios.virtualloot.fabric;

import com.cobblemon.mod.fabric.net.FabricPacketInfo;
import com.lunazstudios.virtualloot.VirtualLoot;
import com.lunazstudios.virtualloot.network.VirtualLootNetwork;
import net.fabricmc.api.ModInitializer;

public final class VirtualLootFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VirtualLoot.init();
        FabricPacketInfo<?> packetInfo = new FabricPacketInfo<>(VirtualLootNetwork.TOGGLE_VIRTUAL_LOOT);
        packetInfo.registerPacket(false);
        packetInfo.registerServerHandler();
    }
}
