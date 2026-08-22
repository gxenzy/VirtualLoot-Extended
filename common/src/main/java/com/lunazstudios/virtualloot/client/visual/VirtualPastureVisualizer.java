package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

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

    public static void handleServerSync(BlockPos pos, int mode, List<CompoundTag> pokemonTags) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

        List<VisualPokemonHolder> currentHolders = ACTIVE_PASTURE_VISUALS.computeIfAbsent(pos, k -> new ArrayList<>());

        if (mode == 0 || pokemonTags == null || pokemonTags.isEmpty()) {
            for (VisualPokemonHolder h : currentHolders) {
                h.entity.discard();
            }
            currentHolders.clear();
            ACTIVE_PASTURE_VISUALS.remove(pos);
            return;
        }

        Set<UUID> syncedUuids = new HashSet<>();

        for (CompoundTag tag : pokemonTags) {
            Pokemon pkmn = PokemonSyncHelper.deserializePokemon(tag);
            if (pkmn == null) continue;

            UUID id = pkmn.getUuid();
            syncedUuids.add(id);

            VisualPokemonHolder existing = currentHolders.stream()
                .filter(h -> h.pokemonId.equals(id))
                .findFirst()
                .orElse(null);

            if (existing == null) {
                PokemonEntity visualEntity = new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON);
                visualEntity.setNoAi(false);
                visualEntity.setInvulnerable(true);
                visualEntity.setSilent(true);
                visualEntity.noPhysics = false;

                Random rand = new Random(id.hashCode());
                double ox = (rand.nextDouble() - 0.5) * 6.0;
                double oz = (rand.nextDouble() - 0.5) * 6.0;
                visualEntity.setPos(pos.getX() + 0.5 + ox, pos.getY(), pos.getZ() + 0.5 + oz);
                visualEntity.setYRot(rand.nextFloat() * 360f);
                visualEntity.yHeadRot = visualEntity.getYRot();
                visualEntity.yBodyRot = visualEntity.getYRot();

                visualEntity.addTag("virtualloot_visual_mode_" + mode);
                world.addEntity(visualEntity);

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity);
                newHolder.targetX = visualEntity.getX();
                newHolder.targetZ = visualEntity.getZ();
                currentHolders.add(newHolder);
            } else {
                existing.entity.removeTag("virtualloot_visual_mode_1");
                existing.entity.removeTag("virtualloot_visual_mode_2");
                existing.entity.removeTag("virtualloot_visual_mode_3");
                existing.entity.addTag("virtualloot_visual_mode_" + mode);
            }
        }

        Iterator<VisualPokemonHolder> it = currentHolders.iterator();
        while (it.hasNext()) {
            VisualPokemonHolder h = it.next();
            if (!syncedUuids.contains(h.pokemonId)) {
                h.entity.discard();
                it.remove();
            }
        }
    }

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null || mc.player == null) {
            clearAll();
            return;
        }

        for (Map.Entry<BlockPos, List<VisualPokemonHolder>> entry : ACTIVE_PASTURE_VISUALS.entrySet()) {
            BlockPos pasturePos = entry.getKey();
            for (VisualPokemonHolder holder : entry.getValue()) {
                tickVisualEntityRoaming(holder, pasturePos);
            }
        }
    }

    private static void tickVisualEntityRoaming(VisualPokemonHolder holder, BlockPos pasturePos) {
        PokemonEntity entity = holder.entity;
        if (entity.isRemoved()) return;

        if (holder.roamCooldown-- <= 0) {
            Random rand = new Random();
            holder.roamCooldown = 100 + rand.nextInt(120);

            // Choose target position within 3.5 blocks of pasture center
            double ox = (rand.nextDouble() - 0.5) * 7.0;
            double oz = (rand.nextDouble() - 0.5) * 7.0;
            holder.targetX = pasturePos.getX() + 0.5 + ox;
            holder.targetZ = pasturePos.getZ() + 0.5 + oz;
        }

        // Smoothly walk towards target
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
