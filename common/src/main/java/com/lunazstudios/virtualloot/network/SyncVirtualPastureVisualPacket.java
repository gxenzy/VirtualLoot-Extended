package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.NetworkPacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.VirtualLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class SyncVirtualPastureVisualPacket implements NetworkPacket<SyncVirtualPastureVisualPacket> {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VirtualLoot.MOD_ID, "sync_virtual_pasture_visual");
    public static final CustomPacketPayload.Type<SyncVirtualPastureVisualPacket> TYPE = new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncVirtualPastureVisualPacket> STREAM_CODEC = StreamCodec.ofMember(
        SyncVirtualPastureVisualPacket::encode,
        SyncVirtualPastureVisualPacket::new
    );

    private final BlockPos pos;
    private final int mode;
    private final List<Pokemon> pokemonList;

    public SyncVirtualPastureVisualPacket(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        this.pos = pos;
        this.mode = mode;
        this.pokemonList = pokemonList != null ? pokemonList : new ArrayList<>();
    }

    public SyncVirtualPastureVisualPacket(RegistryFriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.mode = buffer.readVarInt();
        int size = buffer.readVarInt();
        this.pokemonList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Pokemon pkmn = Pokemon.Companion.getS2C_CODEC().decode(buffer);
            if (pkmn != null) {
                this.pokemonList.add(pkmn);
            }
        }
    }

    public BlockPos pos() {
        return pos;
    }

    public int mode() {
        return mode;
    }

    public List<Pokemon> pokemonList() {
        return pokemonList;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public CustomPacketPayload.Type<SyncVirtualPastureVisualPacket> type() {
        return TYPE;
    }

    @Override
    public void encode(RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeVarInt(mode);
        buffer.writeVarInt(pokemonList.size());
        for (Pokemon pkmn : pokemonList) {
            Pokemon.Companion.getS2C_CODEC().encode(buffer, pkmn);
        }
    }
}
