package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ClientNetworkPacketHandler;
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

    private static final Function1<RegistryFriendlyByteBuf, SyncVirtualPastureVisualsPacket> SYNC_VISUALS_DECODER = SyncVirtualPastureVisualsPacket::new;

    public static final PacketRegisterInfo<SyncVirtualPastureVisualsPacket> SYNC_VISUALS = new PacketRegisterInfo<>(
        SyncVirtualPastureVisualsPacket.ID,
        SYNC_VISUALS_DECODER,
        (ServerNetworkPacketHandler<SyncVirtualPastureVisualsPacket>) null,
        (ClientNetworkPacketHandler<SyncVirtualPastureVisualsPacket>) SyncVirtualPastureVisualsHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
