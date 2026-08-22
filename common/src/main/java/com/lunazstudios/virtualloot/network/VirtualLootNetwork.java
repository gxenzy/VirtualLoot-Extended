package com.lunazstudios.virtualloot.network;

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
        SyncVirtualPastureVisualsPacket.STREAM_CODEC,
        SyncVirtualPastureVisualsHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
