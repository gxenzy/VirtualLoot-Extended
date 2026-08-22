package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler;
import com.cobblemon.mod.common.net.PacketRegisterInfo;
import kotlin.jvm.functions.Function1;
import net.minecraft.network.RegistryFriendlyByteBuf;

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

    public static final Function1<RegistryFriendlyByteBuf, SyncVirtualPastureVisualPacket> SYNC_DECODER = SyncVirtualPastureVisualPacket::new;

    public static final PacketRegisterInfo<SyncVirtualPastureVisualPacket> SYNC_VISUAL_MODE = new PacketRegisterInfo<SyncVirtualPastureVisualPacket>(
        SyncVirtualPastureVisualPacket.ID,
        SYNC_DECODER,
        (ServerNetworkPacketHandler<SyncVirtualPastureVisualPacket>) null,
        SyncVirtualPastureVisualHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
