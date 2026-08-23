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
    private final List<Integer> entityIds;
    private final List<Pokemon> pokemonList;

    public SyncVirtualPastureVisualPacket(BlockPos pos, int mode, List<Integer> entityIds, List<Pokemon> pokemonList) {
        this.pos = pos;
        this.mode = mode;
        this.entityIds = entityIds != null ? entityIds : new ArrayList<>();
        this.pokemonList = pokemonList != null ? pokemonList : new ArrayList<>();
    }

    public SyncVirtualPastureVisualPacket(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        this(pos, mode, new ArrayList<>(), pokemonList);
    }

    public SyncVirtualPastureVisualPacket(RegistryFriendlyByteBuf buffer) {
        this.pos = buffer.readBlockPos();
        this.mode = buffer.readVarInt();
        
        int entitySize = buffer.readVarInt();
        this.entityIds = new ArrayList<>(entitySize);
        for (int i = 0; i < entitySize; i++) {
            this.entityIds.add(buffer.readVarInt());
        }

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

    public List<Integer> entityIds() {
        return entityIds;
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
        
        buffer.writeVarInt(entityIds.size());
        for (int id : entityIds) {
            buffer.writeVarInt(id);
        }

        buffer.writeVarInt(pokemonList.size());
        for (Pokemon pkmn : pokemonList) {
            Pokemon.Companion.getS2C_CODEC().encode(buffer, pkmn);
        }
    }

    public void sendToPlayer(net.minecraft.server.level.ServerPlayer player) {
        if (player != null) {
            com.cobblemon.mod.common.CobblemonNetwork.INSTANCE.sendPacketToPlayer(player, this);
        }
    }

    public void sendToPlayersAround(net.minecraft.server.level.ServerLevel world, BlockPos pos, double radius) {
        if (world == null || pos == null) return;
        double rSq = radius * radius;
        for (net.minecraft.server.level.ServerPlayer player : world.players()) {
            if (player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= rSq) {
                sendToPlayer(player);
            }
        }
    }
}
