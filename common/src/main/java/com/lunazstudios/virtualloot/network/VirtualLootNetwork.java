package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler;
import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler;
import com.cobblemon.mod.common.net.PacketRegisterInfo;

public final class VirtualLootNetwork {
    public static final PacketRegisterInfo<ToggleVirtualLootPacket> TOGGLE_VIRTUAL_LOOT = new PacketRegisterInfo<>(
        ToggleVirtualLootPacket.ID,
        ToggleVirtualLootPacket::new,
        ToggleVirtualLootHandler.INSTANCE,
        null
    );

    public static final PacketRegisterInfo<SetVirtualPastureVisualModePacket> SET_VISUAL_MODE = new PacketRegisterInfo<>(
        SetVirtualPastureVisualModePacket.ID,
        SetVirtualPastureVisualModePacket::new,
        SetVirtualPastureVisualModeHandler.INSTANCE,
        null
    );

    private static final ServerNetworkPacketHandler<SyncVirtualPastureVisualsPacket> NOOP_SERVER_HANDLER = (packet, server, player) -> {};

    public static final PacketRegisterInfo<SyncVirtualPastureVisualsPacket> SYNC_VISUALS = new PacketRegisterInfo<>(
        SyncVirtualPastureVisualsPacket.ID,
        SyncVirtualPastureVisualsPacket::new,
        NOOP_SERVER_HANDLER,
        SyncVirtualPastureVisualsHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
