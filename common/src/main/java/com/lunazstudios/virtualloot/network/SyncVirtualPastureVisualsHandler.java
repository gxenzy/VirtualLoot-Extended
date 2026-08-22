package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler;
import com.lunazstudios.virtualloot.client.visual.VirtualPastureVisualizer;
import net.minecraft.client.Minecraft;

public final class SyncVirtualPastureVisualsHandler implements ClientNetworkPacketHandler<SyncVirtualPastureVisualsPacket> {
    public static final SyncVirtualPastureVisualsHandler INSTANCE = new SyncVirtualPastureVisualsHandler();

    private SyncVirtualPastureVisualsHandler() {}

    @Override
    public void handle(SyncVirtualPastureVisualsPacket packet, Minecraft client) {
        VirtualPastureVisualizer.handleServerSync(packet.pos(), packet.mode(), packet.pokemonTags());
    }
}
