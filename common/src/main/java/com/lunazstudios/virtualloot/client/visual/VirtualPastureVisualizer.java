package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;

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

    public static void handleServerSync(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

        List<VisualPokemonHolder> currentHolders = ACTIVE_PASTURE_VISUALS.computeIfAbsent(pos, k -> new ArrayList<>());

        if (mode == 0 || pokemonList == null || pokemonList.isEmpty()) {
            for (VisualPokemonHolder h : currentHolders) {
                h.entity.discard();
            }
            currentHolders.clear();
            ACTIVE_PASTURE_VISUALS.remove(pos);
            return;
        }

        Set<UUID> syncedUuids = new HashSet<>();

        for (int i = 0; i < pokemonList.size(); i++) {
            Pokemon pkmn = pokemonList.get(i);
            if (pkmn == null) continue;

            UUID id = pkmn.getUuid();
            syncedUuids.add(id);

            VisualPokemonHolder existing = currentHolders.stream()
                .filter(h -> h.pokemonId.equals(id))
                .findFirst()
                .orElse(null);

            if (existing == null) {
                PokemonEntity visualEntity = new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON);
                visualEntity.setPokemon(pkmn);
                visualEntity.setId(ENTITY_COUNTER.incrementAndGet());
                visualEntity.setNoAi(false);
                visualEntity.setInvulnerable(true);
                visualEntity.setSilent(true);
                visualEntity.noPhysics = (mode == 3);
                visualEntity.setOnGround(mode != 3);

                // Set authentic Level and Label on Cobblemon entity data
                try {
                    visualEntity.getEntityData().set(PokemonEntity.getLABEL_LEVEL(), pkmn.getLevel());
                    visualEntity.getEntityData().set(PokemonEntity.getHIDE_LABEL(), false);
                    visualEntity.getEntityData().set(PokemonEntity.getUNBATTLEABLE(), true);
                } catch (Throwable ignored) {}

                visualEntity.setCustomName(Component.literal(pkmn.getDisplayName().getString() + " Lv. " + pkmn.getLevel()));
                visualEntity.setCustomNameVisible(true);
                visualEntity.setGlowingTag(mode == 1 || mode == 2);

                // Spread Pokémon around pasture radius so they don't stack on each other
                double baseRadius = 3.5;
                double angle = (2.0 * Math.PI / Math.max(1, pokemonList.size())) * i + (id.hashCode() % 100) * 0.005;
                double dist = baseRadius + (i % 3) * 1.5;
                double spawnX = pos.getX() + 0.5 + Math.cos(angle) * dist;
                double spawnZ = pos.getZ() + 0.5 + Math.sin(angle) * dist;
                double spawnY = pos.getY() + 1.0 + (mode == 3 ? 0.5 : 0.0);

                visualEntity.setPos(spawnX, spawnY, spawnZ);
                visualEntity.xo = spawnX;
                visualEntity.yo = spawnY;
                visualEntity.zo = spawnZ;
                visualEntity.xOld = spawnX;
                visualEntity.yOld = spawnY;
                visualEntity.zOld = spawnZ;

                float yaw = (float) (angle * (180.0 / Math.PI)) + 90f;
                visualEntity.setYRot(yaw);
                visualEntity.yHeadRot = yaw;
                visualEntity.yBodyRot = yaw;
                visualEntity.yRotO = yaw;
                visualEntity.yHeadRotO = yaw;
                visualEntity.yBodyRotO = yaw;

                visualEntity.addTag("virtualloot_visual_mode_" + mode);
                world.addEntity(visualEntity);

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity, mode);
                newHolder.targetX = spawnX;
                newHolder.targetZ = spawnZ;
                currentHolders.add(newHolder);
            } else {
                existing.mode = mode;
                existing.entity.setPokemon(pkmn);
                try {
                    existing.entity.getEntityData().set(PokemonEntity.getLABEL_LEVEL(), pkmn.getLevel());
                } catch (Throwable ignored) {}
                existing.entity.setCustomName(Component.literal(pkmn.getDisplayName().getString() + " Lv. " + pkmn.getLevel()));
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

        double ex = entity.getX();
        double ey = entity.getY() + entity.getBbHeight() * 0.5;
        double ez = entity.getZ();

        if (mode == 1) {
            // Mode 1: WIREFRAME / CYBER (Green matrix spark particles)
            if (rand.nextFloat() < 0.4f) {
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, 0, 0.05, 0);
            }
        } else if (mode == 2) {
            // Mode 2: HOLOGRAM (Projector beam from PC + Cyan electric sparks)
            if (rand.nextFloat() < 0.5f) {
                world.addParticle(ParticleTypes.ELECTRIC_SPARK, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, (rand.nextDouble() - 0.5) * 0.05, 0.05, (rand.nextDouble() - 0.5) * 0.05);
            }
            if (rand.nextFloat() < 0.25f) {
                // Projector beam particles from pasture top block
                double beamX = pasturePos.getX() + 0.5;
                double beamY = pasturePos.getY() + 1.2;
                double beamZ = pasturePos.getZ() + 0.5;
                double toX = (ex - beamX) * 0.08;
                double toY = (ey - beamY) * 0.08;
                double toZ = (ez - beamZ) * 0.08;
                world.addParticle(ParticleTypes.GLOW, beamX, beamY, beamZ, toX, toY, toZ);
            }
        } else if (mode == 3) {
            // Mode 3: GHOST (Floating spirit with soul fire flames & purple witch mist)
            if (rand.nextFloat() < 0.6f) {
                world.addParticle(ParticleTypes.WITCH, ex + (rand.nextDouble() - 0.5) * 0.8, ey + (rand.nextDouble() - 0.5) * 0.8, ez + (rand.nextDouble() - 0.5) * 0.8, (rand.nextDouble() - 0.5) * 0.02, 0.03, (rand.nextDouble() - 0.5) * 0.02);
            }
            if (rand.nextFloat() < 0.3f) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ex + (rand.nextDouble() - 0.5) * 0.5, ey + (rand.nextDouble() - 0.5) * 0.5, ez + (rand.nextDouble() - 0.5) * 0.5, 0, 0.02, 0);
            }
        }

        // Roaming behavior within a wide 8-block pasture boundary
        if (holder.roamCooldown-- <= 0) {
            holder.roamCooldown = 100 + rand.nextInt(120);

            double angle = rand.nextDouble() * Math.PI * 2.0;
            double dist = 2.5 + rand.nextDouble() * 5.5;
            holder.targetX = pasturePos.getX() + 0.5 + Math.cos(angle) * dist;
            holder.targetZ = pasturePos.getZ() + 0.5 + Math.sin(angle) * dist;
        }

        double dx = holder.targetX - entity.getX();
        double dz = holder.targetZ - entity.getZ();
        double distSq = dx * dx + dz * dz;

        // Maintain previous positions for smooth lerping (no visual lag/stutter)
        entity.xo = entity.getX();
        entity.yo = entity.getY();
        entity.zo = entity.getZ();
        entity.yRotO = entity.getYRot();
        entity.yHeadRotO = entity.yHeadRot;
        entity.yBodyRotO = entity.yBodyRot;

        if (distSq > 0.4) {
            double angle = Math.atan2(dz, dx);
            float targetYaw = (float) (angle * (180 / Math.PI)) - 90f;
            entity.setYRot(targetYaw);
            entity.yHeadRot = targetYaw;
            entity.yBodyRot = targetYaw;

            double speed = (mode == 3) ? 0.03 : 0.045;
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = (mode == 3) ? Math.sin(entity.tickCount * 0.1) * 0.015 : 0.0;
            
            entity.move(MoverType.SELF, new Vec3(vx, vy, vz));
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
