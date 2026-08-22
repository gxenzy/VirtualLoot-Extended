package com.lunazstudios.virtualloot.network;

import com.cobblemon.mod.common.api.net.ServerNetworkPacketHandler;
import com.cobblemon.mod.common.block.PastureBlock;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.client.visual.PokemonSyncHelper;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public final class SetVirtualPastureVisualModeHandler implements ServerNetworkPacketHandler<SetVirtualPastureVisualModePacket> {
    public static final SetVirtualPastureVisualModeHandler INSTANCE = new SetVirtualPastureVisualModeHandler();

    private SetVirtualPastureVisualModeHandler() {
    }

    @Override
    public void handle(SetVirtualPastureVisualModePacket packet, MinecraftServer server, ServerPlayer player) {
        Level world = player.level();
        BlockPos basePos = packet.pos();
        if (!world.isLoaded(basePos) || player.distanceToSqr(basePos.getX() + 0.5D, basePos.getY() + 0.5D, basePos.getZ() + 0.5D) > 64.0D) {
            return;
        }
        BlockState state = world.getBlockState(basePos);
        if (!VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
            return;
        }
        if (state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
            basePos = basePos.below();
            state = world.getBlockState(basePos);
            if (!VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                return;
            }
        }
        VirtualPastureBlock.setVisualMode(world, basePos, state, packet.mode());

        List<CompoundTag> tags = new ArrayList<>();
        if (packet.mode() > 0) {
            BlockEntity be = world.getBlockEntity(basePos);
            if (be instanceof PokemonPastureBlockEntity pasture) {
                for (PokemonPastureBlockEntity.Tethering t : pasture.getTetheredPokemon()) {
                    Pokemon pkmn = t.getPokemon();
                    if (pkmn != null) {
                        CompoundTag tag = PokemonSyncHelper.serializePokemon(pkmn, world.registryAccess());
                        if (!tag.isEmpty()) {
                            tags.add(tag);
                        }
                    }
                }
            }
        }

        SyncVirtualPastureVisualPacket syncPacket = new SyncVirtualPastureVisualPacket(basePos, packet.mode(), tags);
        if (world instanceof ServerLevel serverLevel) {
            for (ServerPlayer p : serverLevel.players()) {
                if (p.distanceToSqr(basePos.getX() + 0.5D, basePos.getY() + 0.5D, basePos.getZ() + 0.5D) <= 64.0D * 64.0D) {
                    syncPacket.sendToPlayer(p);
                }
            }
        }
    }
}
