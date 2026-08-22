package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;

import java.util.*;

public class VirtualPastureVisualizer {

    public static class VisualPokemonHolder {
        public final UUID pokemonId;
        public final PokemonEntity entity;
        public double targetX;
        public double targetZ;
        public int roamCooldown = 0;
        public int mode;

        public VisualPokemonHolder(UUID pokemonId, PokemonEntity entity, int mode) {
            this.pokemonId = pokemonId;
            this.entity = entity;
            this.mode = mode;
        }
    }

    private static final Map<BlockPos, List<VisualPokemonHolder>> ACTIVE_PASTURE_VISUALS = new HashMap<>();
    private static final java.util.concurrent.atomic.AtomicInteger ENTITY_COUNTER = new java.util.concurrent.atomic.AtomicInteger(500000);

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
                visualEntity.setId(ENTITY_COUNTER.incrementAndGet());
                visualEntity.setNoAi(false);
                visualEntity.setInvulnerable(true);
                visualEntity.setSilent(true);
                visualEntity.noPhysics = (mode == 3); // No clip / fly through for ghosts
                visualEntity.setOnGround(mode != 3);

                visualEntity.setGlowingTag(mode == 1 || mode == 2);

                Random rand = new Random(id.hashCode());
                double ox = (rand.nextDouble() - 0.5) * 4.0;
                double oz = (rand.nextDouble() - 0.5) * 4.0;
                double initialY = pos.getY() + 1.0 + (mode == 3 ? 0.5 : 0.0);
                visualEntity.setPos(pos.getX() + 0.5 + ox, initialY, pos.getZ() + 0.5 + oz);
                float yaw = rand.nextFloat() * 360f;
                visualEntity.setYRot(yaw);
                visualEntity.yHeadRot = yaw;
                visualEntity.yBodyRot = yaw;

                visualEntity.addTag("virtualloot_visual_mode_" + mode);
                world.addEntity(visualEntity);

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity, mode);
                newHolder.targetX = visualEntity.getX();
                newHolder.targetZ = visualEntity.getZ();
                currentHolders.add(newHolder);
            } else {
                existing.mode = mode;
                existing.entity.removeTag("virtualloot_visual_mode_1");
                existing.entity.removeTag("virtualloot_visual_mode_2");
                existing.entity.removeTag("virtualloot_visual_mode_3");
                existing.entity.addTag("virtualloot_visual_mode_" + mode);
                existing.entity.setGlowingTag(mode == 1 || mode == 2);
                existing.entity.noPhysics = (mode == 3);
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
                tickVisualEntity(holder, pasturePos, world);
            }
        }
    }

    private static void tickVisualEntity(VisualPokemonHolder holder, BlockPos pasturePos, ClientLevel world) {
        PokemonEntity entity = holder.entity;
        if (entity.isRemoved()) return;

        int mode = holder.mode;
        net.minecraft.util.RandomSource rand = world.random;

        // 1. Particle and floating effect per mode
        double ex = entity.getX();
        double ey = entity.getY() + entity.getBbHeight() * 0.5;
        double ez = entity.getZ();

        if (mode == 1) {
            // Mode 1: WIREFRAME / CYBER (Green matrix code particles)
            if (rand.nextFloat() < 0.4f) {
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, 0, 0.05, 0);
            }
        } else if (mode == 2) {
            // Mode 2: HOLOGRAM (Cyan electric / end rod digital aura)
            if (rand.nextFloat() < 0.5f) {
                world.addParticle(ParticleTypes.ELECTRIC_SPARK, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, (rand.nextDouble() - 0.5) * 0.05, 0.05, (rand.nextDouble() - 0.5) * 0.05);
            }
            if (rand.nextFloat() < 0.2f) {
                world.addParticle(ParticleTypes.END_ROD, ex + (rand.nextDouble() - 0.5) * 0.6, ey + (rand.nextDouble() - 0.5) * 0.6, ez + (rand.nextDouble() - 0.5) * 0.6, 0, 0.02, 0);
            }
        } else if (mode == 3) {
            // Mode 3: GHOST (Floating spirit with witch magic & soul fire flames)
            if (rand.nextFloat() < 0.6f) {
                world.addParticle(ParticleTypes.WITCH, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, (rand.nextDouble() - 0.5) * 0.02, 0.03, (rand.nextDouble() - 0.5) * 0.02);
            }
            if (rand.nextFloat() < 0.3f) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ex + (rand.nextDouble() - 0.5) * 0.5, ey + (rand.nextDouble() - 0.5) * 0.5, ez + (rand.nextDouble() - 0.5) * 0.5, 0, 0.02, 0);
            }
        }

        // 2. Roaming behavior
        if (holder.roamCooldown-- <= 0) {
            holder.roamCooldown = 80 + rand.nextInt(100);

            double ox = (rand.nextDouble() - 0.5) * 5.0;
            double oz = (rand.nextDouble() - 0.5) * 5.0;
            holder.targetX = pasturePos.getX() + 0.5 + ox;
            holder.targetZ = pasturePos.getZ() + 0.5 + oz;
        }

        double dx = holder.targetX - entity.getX();
        double dz = holder.targetZ - entity.getZ();
        double distSq = dx * dx + dz * dz;

        if (distSq > 0.3) {
            double angle = Math.atan2(dz, dx);
            float targetYaw = (float) (angle * (180 / Math.PI)) - 90f;
            entity.setYRot(targetYaw);
            entity.yHeadRot = targetYaw;
            entity.yBodyRot = targetYaw;

            double speed = (mode == 3) ? 0.025 : 0.035;
            double vy = (mode == 3) ? Math.sin(entity.tickCount * 0.1) * 0.015 : entity.getDeltaMovement().y;
            entity.setDeltaMovement(Math.cos(angle) * speed, vy, Math.sin(angle) * speed);
        } else {
            double vy = (mode == 3) ? Math.sin(entity.tickCount * 0.1) * 0.015 : entity.getDeltaMovement().y;
            entity.setDeltaMovement(0, vy, 0);
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
