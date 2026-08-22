package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.net.PacketRegisterInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

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

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncVirtualPastureVisualPacket> SYNC_VISUAL_CODEC = StreamCodec.ofMember(
        SyncVirtualPastureVisualPacket::encode,
        SyncVirtualPastureVisualPacket::new
    );

    public static final PacketRegisterInfo<SyncVirtualPastureVisualPacket> SYNC_VISUAL_MODE = new PacketRegisterInfo<>(
        SyncVirtualPastureVisualPacket.ID,
        SYNC_VISUAL_CODEC,
        null,
        SyncVirtualPastureVisualHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
