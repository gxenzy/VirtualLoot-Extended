package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class VirtualPastureVisualizer {

    public static class VisualPokemonHolder {
        public final UUID pokemonId;
        public final PokemonEntity entity;
        public double targetX;
        public double targetZ;
        public int roamCooldown = 0;

        public VisualPokemonHolder(UUID pokemonId, PokemonEntity entity) {
            this.pokemonId = pokemonId;
            this.entity = entity;
        }
    }

    private static final Map<BlockPos, List<VisualPokemonHolder>> ACTIVE_PASTURE_VISUALS = new HashMap<>();

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null || mc.player == null) {
            clearAll();
            return;
        }

        BlockPos playerPos = mc.player.blockPosition();
        int searchRadius = 32;

        Set<BlockPos> visiblePastures = new HashSet<>();

        // Search for active virtual pastures within player render distance
        int minX = (playerPos.getX() - searchRadius) >> 4;
        int maxX = (playerPos.getX() + searchRadius) >> 4;
        int minZ = (playerPos.getZ() - searchRadius) >> 4;
        int maxZ = (playerPos.getZ() + searchRadius) >> 4;

        for (int cx = minX; cx <= maxX; cx++) {
            for (int cz = minZ; cz <= maxZ; cz++) {
                if (world.getChunkSource().hasChunk(cx, cz)) {
                    var chunk = world.getChunk(cx, cz);
                    for (BlockPos pos : chunk.getBlockEntitiesPos()) {
                        BlockEntity be = world.getBlockEntity(pos);
                        if (be instanceof PokemonPastureBlockEntity pasture) {
                            BlockState state = world.getBlockState(pos);
                            if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                                int mode = VirtualPastureBlock.getVisualMode(state);
                                if (mode > 0) {
                                    visiblePastures.add(pos);
                                    updatePastureVisuals(world, pos, pasture, mode);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Clean up visual entities for pastures no longer in range or turned OFF
        Iterator<Map.Entry<BlockPos, List<VisualPokemonHolder>>> it = ACTIVE_PASTURE_VISUALS.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<BlockPos, List<VisualPokemonHolder>> entry = it.next();
            if (!visiblePastures.contains(entry.getKey())) {
                for (VisualPokemonHolder holder : entry.getValue()) {
                    holder.entity.discard();
                }
                it.remove();
            }
        }
    }

    private static void updatePastureVisuals(ClientLevel world, BlockPos pos, PokemonPastureBlockEntity pasture, int visualMode) {
        List<VisualPokemonHolder> currentHolders = ACTIVE_PASTURE_VISUALS.computeIfAbsent(pos, k -> new ArrayList<>());

        List<Pokemon> tetheredPokemon = pasture.getTetheredPokemon().stream()
            .map(PokemonPastureBlockEntity.Tethering::getPokemon)
            .filter(Objects::nonNull)
            .toList();

        Set<UUID> activeUuids = new HashSet<>();

        for (Pokemon pkmn : tetheredPokemon) {
            UUID id = pkmn.getUuid();
            activeUuids.add(id);

            VisualPokemonHolder existing = currentHolders.stream()
                .filter(h -> h.pokemonId.equals(id))
                .findFirst()
                .orElse(null);

            if (existing == null) {
                // Spawn client-side visual entity
                PokemonEntity visualEntity = new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON);
                visualEntity.setNoAi(false);
                visualEntity.setInvulnerable(true);
                visualEntity.setSilent(true);
                visualEntity.noPhysics = false;

                // Position within pasture radius
                Random rand = new Random(id.hashCode());
                double ox = (rand.nextDouble() - 0.5) * 6.0;
                double oz = (rand.nextDouble() - 0.5) * 6.0;
                visualEntity.setPos(pos.getX() + 0.5 + ox, pos.getY(), pos.getZ() + 0.5 + oz);
                visualEntity.setYRot(rand.nextFloat() * 360f);
                visualEntity.yHeadRot = visualEntity.getYRot();
                visualEntity.yBodyRot = visualEntity.getYRot();

                // Tag with visual mode
                visualEntity.addTag("virtualloot_visual_mode_" + visualMode);

                world.addEntity(visualEntity);

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity);
                newHolder.targetX = visualEntity.getX();
                newHolder.targetZ = visualEntity.getZ();
                currentHolders.add(newHolder);
            } else {
                // Update tag in case mode changed
                existing.entity.removeTag("virtualloot_visual_mode_1");
                existing.entity.removeTag("virtualloot_visual_mode_2");
                existing.entity.removeTag("virtualloot_visual_mode_3");
                existing.entity.addTag("virtualloot_visual_mode_" + visualMode);

                // Gentle client-side wandering behavior inside pasture boundary
                tickVisualEntityRoaming(existing, pos);
            }
        }

        // Remove holders for Pokemon no longer in pasture
        Iterator<VisualPokemonHolder> holderIt = currentHolders.iterator();
        while (holderIt.hasNext()) {
            VisualPokemonHolder h = holderIt.next();
            if (!activeUuids.contains(h.pokemonId)) {
                h.entity.discard();
                holderIt.remove();
            }
        }
    }

    private static void tickVisualEntityRoaming(VisualPokemonHolder holder, BlockPos pasturePos) {
        PokemonEntity entity = holder.entity;
        if (holder.roamCooldown-- <= 0) {
            Random rand = new Random();
            holder.roamCooldown = 100 + rand.nextInt(120);

            // Choose target position within 3.5 blocks of pasture center
            double ox = (rand.nextDouble() - 0.5) * 7.0;
            double oz = (rand.nextDouble() - 0.5) * 7.0;
            holder.targetX = pasturePos.getX() + 0.5 + ox;
            holder.targetZ = pasturePos.getZ() + 0.5 + oz;
        }

        // Smoothly walk towards target if not yet there
        double dx = holder.targetX - entity.getX();
        double dz = holder.targetZ - entity.getZ();
        double distSq = dx * dx + dz * dz;

        if (distSq > 0.3) {
            double angle = Math.atan2(dz, dx);
            float targetYaw = (float) (angle * (180 / Math.PI)) - 90f;
            entity.setYRot(targetYaw);
            entity.yHeadRot = targetYaw;
            entity.yBodyRot = targetYaw;

            double speed = 0.035;
            entity.setDeltaMovement(Math.cos(angle) * speed, entity.getDeltaMovement().y, Math.sin(angle) * speed);
        } else {
            entity.setDeltaMovement(0, entity.getDeltaMovement().y, 0);
        }
    }

    public static void clearAll() {
        for (List<VisualPokemonHolder> holders : ACTIVE_PASTURE_VISUALS.values()) {
            for (VisualPokemonHolder h : holders) {
                h.entity.discard();
            }
        }
        ACTIVE_PASTURE_VISUALS.clear();
    }
}
