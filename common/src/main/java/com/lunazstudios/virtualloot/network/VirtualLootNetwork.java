package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler;
import com.cobblemon.mod.common.net.PacketRegisterInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

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

    private static final ServerNetworkPacketHandler<SyncVirtualPastureVisualPacket> NO_OP_SERVER_HANDLER = new ServerNetworkPacketHandler<>() {
        @Override
        public void handle(SyncVirtualPastureVisualPacket packet, MinecraftServer server, ServerPlayer player) {
        }
    };

    public static final PacketRegisterInfo<SyncVirtualPastureVisualPacket> SYNC_VISUAL_MODE = new PacketRegisterInfo<>(
        SyncVirtualPastureVisualPacket.ID,
        SyncVirtualPastureVisualPacket::new,
        NO_OP_SERVER_HANDLER,
        SyncVirtualPastureVisualHandler.INSTANCE
    );

    private VirtualLootNetwork() {
    }
}
