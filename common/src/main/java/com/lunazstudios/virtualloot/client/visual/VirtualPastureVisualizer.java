package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.levelgen.Heightmap;
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
        public int slotIndex;

        public VisualPokemonHolder(UUID pokemonId, PokemonEntity entity, int mode, int slotIndex) {
            this.pokemonId = pokemonId;
            this.entity = entity;
            this.mode = mode;
            this.slotIndex = slotIndex;
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
        int total = pokemonList.size();

        for (int i = 0; i < total; i++) {
            Pokemon pkmn = pokemonList.get(i);
            if (pkmn == null) continue;

            UUID id = pkmn.getUuid();
            syncedUuids.add(id);

            VisualPokemonHolder existing = currentHolders.stream()
                .filter(h -> h.pokemonId.equals(id))
                .findFirst()
                .orElse(null);

            // Wide non-overlapping radial distribution around pasture
            double angle = (2.0 * Math.PI / Math.max(1, total)) * i;
            double dist = 4.0 + (i % 2) * 1.5;
            double spawnX = pos.getX() + 0.5 + Math.cos(angle) * dist;
            double spawnZ = pos.getZ() + 0.5 + Math.sin(angle) * dist;

            // Heightmap motion blocking gives exact top solid block Y
            int groundY = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) Math.floor(spawnX), (int) Math.floor(spawnZ));
            double spawnY = (mode == 3) ? (groundY + 0.75) : groundY;

            if (existing == null) {
                // Non-physical virtual entity that cannot push or collide with players
                PokemonEntity visualEntity = new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON) {
                    @Override
                    public boolean isPushable() {
                        return false;
                    }

                    @Override
                    public boolean canCollideWith(Entity other) {
                        return false;
                    }

                    @Override
                    public boolean canBeCollidedWith() {
                        return false;
                    }

                    @Override
                    public void push(Entity entity) {
                        // Virtual hologram/visual has zero physical push on player
                    }

                    @Override
                    protected void doPush(Entity entity) {
                        // Virtual hologram/visual has zero physical push on player
                    }

                    @Override
                    protected void pushEntities() {
                        // Virtual hologram/visual has zero physical push on player
                    }

                    @Override
                    public boolean isPickable() {
                        return false;
                    }

                    @Override
                    public boolean isAttackable() {
                        return false;
                    }
                };

                visualEntity.setPokemon(pkmn);
                visualEntity.setId(ENTITY_COUNTER.incrementAndGet());
                visualEntity.setNoAi(false);
                visualEntity.setInvulnerable(true);
                visualEntity.setSilent(true);
                
                visualEntity.noPhysics = true;
                visualEntity.blocksBuilding = false;
                visualEntity.setOnGround(mode != 3);

                try {
                    visualEntity.getEntityData().set(PokemonEntity.getLABEL_LEVEL(), pkmn.getLevel());
                    visualEntity.getEntityData().set(PokemonEntity.getHIDE_LABEL(), false);
                    visualEntity.getEntityData().set(PokemonEntity.getUNBATTLEABLE(), true);
                } catch (Throwable ignored) {}

                visualEntity.setCustomName(Component.literal(pkmn.getDisplayName(false).getString() + " Lv. " + pkmn.getLevel()));
                visualEntity.setCustomNameVisible(true);

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

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity, mode, i);
                newHolder.targetX = spawnX;
                newHolder.targetZ = spawnZ;
                currentHolders.add(newHolder);
            } else {
                existing.mode = mode;
                existing.slotIndex = i;
                existing.entity.setPokemon(pkmn);
                try {
                    existing.entity.getEntityData().set(PokemonEntity.getLABEL_LEVEL(), pkmn.getLevel());
                } catch (Throwable ignored) {}
                existing.entity.setCustomName(Component.literal(pkmn.getDisplayName(false).getString() + " Lv. " + pkmn.getLevel()));
                existing.entity.removeTag("virtualloot_visual_mode_1");
                existing.entity.removeTag("virtualloot_visual_mode_2");
                existing.entity.removeTag("virtualloot_visual_mode_3");
                existing.entity.addTag("virtualloot_visual_mode_" + mode);
                existing.entity.noPhysics = true;
                existing.entity.blocksBuilding = false;
                existing.entity.setOnGround(mode != 3);
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

        // ONLY Ghost (mode 3) has subtle spirit particles
        if (mode == 3) {
            if (rand.nextFloat() < 0.15f) {
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ex + (rand.nextDouble() - 0.5) * 0.5, ey + (rand.nextDouble() - 0.5) * 0.5, ez + (rand.nextDouble() - 0.5) * 0.5, 0, 0.02, 0);
            }
        }

        // Roaming targets strictly pinned around distinct slot angles
        if (holder.roamCooldown-- <= 0) {
            holder.roamCooldown = 120 + rand.nextInt(100);

            double baseAngle = (2.0 * Math.PI / 6.0) * holder.slotIndex;
            double angleVar = (rand.nextDouble() - 0.5) * 0.9;
            double angle = baseAngle + angleVar;
            double dist = 3.0 + rand.nextDouble() * 3.5;
            holder.targetX = pasturePos.getX() + 0.5 + Math.cos(angle) * dist;
            holder.targetZ = pasturePos.getZ() + 0.5 + Math.sin(angle) * dist;
        }

        double dx = holder.targetX - entity.getX();
        double dz = holder.targetZ - entity.getZ();
        double distSq = dx * dx + dz * dz;

        entity.xo = entity.getX();
        entity.yo = entity.getY();
        entity.zo = entity.getZ();
        entity.yRotO = entity.getYRot();
        entity.yHeadRotO = entity.yHeadRot;
        entity.yBodyRotO = entity.yBodyRot;

        if (distSq > 0.3) {
            double angle = Math.atan2(dz, dx);
            float targetYaw = (float) (angle * (180 / Math.PI)) - 90f;
            entity.setYRot(targetYaw);
            entity.yHeadRot = targetYaw;
            entity.yBodyRot = targetYaw;

            double speed = (mode == 3) ? 0.025 : 0.035;
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            entity.move(MoverType.SELF, new Vec3(vx, 0.0, vz));
        }

        int groundY = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) Math.floor(entity.getX()), (int) Math.floor(entity.getZ()));

        if (mode == 3) {
            // Mode 3 (Ghost): Floating bob above ground
            double hoverY = groundY + 0.75 + 0.15 * Math.sin(entity.tickCount * 0.08);
            entity.setPos(entity.getX(), hoverY, entity.getZ());
        } else {
            // Mode 1 (Wireframe) and Mode 2 (Hologram): Feet placed directly on top ground block
            entity.setPos(entity.getX(), (double) groundY, entity.getZ());
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
