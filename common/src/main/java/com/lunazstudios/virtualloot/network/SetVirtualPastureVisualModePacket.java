package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.NetworkPacket;
import com.lunazstudios.virtualloot.VirtualLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class SetVirtualPastureVisualModePacket implements NetworkPacket<SetVirtualPastureVisualModePacket> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VirtualLoot.MOD_ID, "set_virtual_pasture_visual_mode");
    private final BlockPos pos;
    private final int mode;

    public SetVirtualPastureVisualModePacket(BlockPos pos, int mode) {
        this.pos = pos;
        this.mode = mode;
    }

    public SetVirtualPastureVisualModePacket(RegistryFriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readVarInt());
    }

    public BlockPos pos() {
        return pos;
    }

    public int mode() {
        return mode;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(mode);
    }
}
