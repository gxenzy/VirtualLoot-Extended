package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.NetworkPacket;
import com.lunazstudios.virtualloot.VirtualLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class ToggleVirtualLootPacket implements NetworkPacket<ToggleVirtualLootPacket> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VirtualLoot.MOD_ID, "toggle_virtual_loot");
    private final BlockPos pos;

    public ToggleVirtualLootPacket(BlockPos pos) {
        this.pos = pos;
    }

    public ToggleVirtualLootPacket(RegistryFriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public BlockPos pos() {
        return pos;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }
}
