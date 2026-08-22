package com.lunazstudios.virtualloot.network;

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

    public static final PacketRegisterInfo<SyncVirtualPastureVisualsPacket> SYNC_VISUALS = new PacketRegisterInfo<>(
        SyncVirtualPastureVisualsPacket.ID,
        SyncVirtualPastureVisualsPacket::new,
        (ServerNetworkPacketHandler<SyncVirtualPastureVisualsPacket>) null,
        SyncVirtualPastureVisualsHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
