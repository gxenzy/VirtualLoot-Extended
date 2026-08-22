package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.net.PacketRegisterInfo;

public final class VirtualLootNetwork {
    public static final PacketRegisterInfo<ToggleVirtualLootPacket> TOGGLE_VIRTUAL_LOOT = new PacketRegisterInfo<>(
        ToggleVirtualLootPacket.ID,
        ToggleVirtualLootPacket::new,
        ToggleVirtualLootHandler.INSTANCE,
        null
    );

    private VirtualLootNetwork() {
    }
}
