package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.NetworkPacket;
import com.lunazstudios.virtualloot.VirtualLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class SyncVirtualPastureVisualPacket implements NetworkPacket<SyncVirtualPastureVisualPacket> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VirtualLoot.MOD_ID, "sync_virtual_pasture_visual");
    private final BlockPos pos;
    private final int mode;
    private final List<CompoundTag> pokemonTags;

    public SyncVirtualPastureVisualPacket(BlockPos pos, int mode, List<CompoundTag> pokemonTags) {
        this.pos = pos;
        this.mode = mode;
        this.pokemonTags = pokemonTags != null ? pokemonTags : new ArrayList<>();
    }

    public SyncVirtualPastureVisualPacket(RegistryFriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.mode = buffer.readVarInt();
        int size = buffer.readVarInt();
        this.pokemonTags = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            CompoundTag tag = buffer.readNbt();
            if (tag != null) {
                this.pokemonTags.add(tag);
            }
        }
    }

    public BlockPos pos() {
        return pos;
    }

    public int mode() {
        return mode;
    }

    public List<CompoundTag> pokemonTags() {
        return pokemonTags;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(mode);
        buffer.writeVarInt(pokemonTags.size());
        for (CompoundTag tag : pokemonTags) {
            buffer.writeNbt(tag);
        }
    }
}
