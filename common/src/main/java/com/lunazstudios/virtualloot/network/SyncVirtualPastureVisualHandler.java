package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler;
import com.lunazstudios.virtualloot.client.visual.VirtualPastureVisualizer;
import net.minecraft.client.Minecraft;

public final class SyncVirtualPastureVisualHandler implements ClientNetworkPacketHandler<SyncVirtualPastureVisualPacket> {
    public static final SyncVirtualPastureVisualHandler INSTANCE = new SyncVirtualPastureVisualHandler();

    private SyncVirtualPastureVisualHandler() {
    }

    @Override
    public void handle(SyncVirtualPastureVisualPacket packet, Minecraft client) {
        client.execute(() -> {
            VirtualPastureVisualizer.handleServerSync(packet.pos(), packet.mode(), packet.pokemonTags());
        });
    }
}
