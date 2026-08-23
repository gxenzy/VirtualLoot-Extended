package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.*;

public class VirtualPastureVisualizer {

    public static class VisualPokemonHolder {
        public static final int MAX_SPAWN_TICKS = 30; // 1.5s materialization duration
        public final UUID pokemonId;
        public final PokemonEntity entity;
        public double targetX;
        public double targetY;
        public double targetZ;
        public int roamCooldown = 0;
        public int mode;
        public int slotIndex;
        public int spawnTicks = MAX_SPAWN_TICKS;
        public boolean isIdle = false;

        public VisualPokemonHolder(UUID pokemonId, PokemonEntity entity, int mode, int slotIndex) {
            this.pokemonId = pokemonId;
            this.entity = entity;
            this.mode = mode;
            this.slotIndex = slotIndex;
        }
    }

    private static final Map<BlockPos, List<VisualPokemonHolder>> ACTIVE_PASTURE_VISUALS = new HashMap<>();
    private static final java.util.concurrent.atomic.AtomicInteger ENTITY_COUNTER = new java.util.concurrent.atomic.AtomicInteger(500000);

    public static float getSpawnScale(UUID pokemonId, float partialTicks) {
        if (pokemonId == null) return 1.0f;
        for (List<VisualPokemonHolder> holders : ACTIVE_PASTURE_VISUALS.values()) {
            for (VisualPokemonHolder h : holders) {
                if (h.pokemonId.equals(pokemonId)) {
                    if (h.spawnTicks <= 0) return 1.0f;
                    float progress = 1.0f - ((h.spawnTicks - partialTicks) / (float) VisualPokemonHolder.MAX_SPAWN_TICKS);
                    progress = Math.max(0.01f, Math.min(1.0f, progress));
                    return (float) Math.sin(progress * Math.PI * 0.5);
                }
            }
        }
        return 1.0f;
    }

    public static List<Vec3> scanIndoorRoomFloor(ClientLevel world, BlockPos pasturePos) {
        List<Vec3> validSpots = new ArrayList<>();
        int pastureY = pasturePos.getY();
        int radius = 5;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx == 0 && dz == 0) continue;

                for (int dy = 1; dy >= -2; dy--) {
                    BlockPos floorPos = pasturePos.offset(dx, dy, dz);
                    BlockPos feetPos = floorPos.above();
                    BlockPos headPos = feetPos.above();

                    BlockState floorState = world.getBlockState(floorPos);
                    BlockState feetState = world.getBlockState(feetPos);
                    BlockState headState = world.getBlockState(headPos);

                    if (!floorState.isAir() && !floorState.getCollisionShape(world, floorPos).isEmpty()
                            && feetState.getCollisionShape(world, feetPos).isEmpty()
                            && headState.getCollisionShape(world, headPos).isEmpty()) {
                        validSpots.add(new Vec3(floorPos.getX() + 0.5, floorPos.getY() + 1.0, floorPos.getZ() + 0.5));
                        break;
                    }
                }
            }
        }

        if (validSpots.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                double angle = (2.0 * Math.PI / 8.0) * i;
                double dist = 1.8;
                double fx = pasturePos.getX() + 0.5 + Math.cos(angle) * dist;
                double fz = pasturePos.getZ() + 0.5 + Math.sin(angle) * dist;
                double fy = findLocalFloorY(world, fx, fz, pastureY);
                validSpots.add(new Vec3(fx, fy, fz));
            }
        }

        return validSpots;
    }

    public static void handleServerSync(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

        List<VisualPokemonHolder> currentHolders = ACTIVE_PASTURE_VISUALS.computeIfAbsent(pos, k -> new ArrayList<>());

        if (pokemonList == null || pokemonList.isEmpty()) {
            for (VisualPokemonHolder h : currentHolders) {
                h.entity.discard();
            }
            currentHolders.clear();
            ACTIVE_PASTURE_VISUALS.remove(pos);
            return;
        }

        List<Vec3> roomSpots = scanIndoorRoomFloor(world, pos);
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

            int spotIdx = (i * 2 + (i % 3)) % roomSpots.size();
            Vec3 spawnSpot = roomSpots.get(spotIdx);
            double spawnX = spawnSpot.x;
            double spawnY = (mode == 3) ? (spawnSpot.y + 0.5) : spawnSpot.y;
            double spawnZ = spawnSpot.z;

            if (existing == null) {
                PokemonEntity visualEntity = new PokemonEntity(world, pkmn, CobblemonEntities.POKEMON);
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

                visualEntity.setCustomName(pkmn.getDisplayName(false));
                visualEntity.setCustomNameVisible(true);

                visualEntity.setPos(spawnX, spawnY, spawnZ);
                visualEntity.xo = spawnX;
                visualEntity.yo = spawnY;
                visualEntity.zo = spawnZ;
                visualEntity.xOld = spawnX;
                visualEntity.yOld = spawnY;
                visualEntity.zOld = spawnZ;

                float yaw = (float) (Math.random() * 360.0);
                visualEntity.setYRot(yaw);
                visualEntity.yHeadRot = yaw;
                visualEntity.yBodyRot = yaw;
                visualEntity.yRotO = yaw;
                visualEntity.yHeadRotO = yaw;
                visualEntity.yBodyRotO = yaw;

                if (mode > 0) {
                    visualEntity.addTag("virtualloot_visual_mode_" + mode);
                }
                world.addEntity(visualEntity);

                // Play teleport sound & particles
                try {
                    world.playLocalSound(spawnX, spawnY, spawnZ, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 0.8f, 1.3f, false);
                } catch (Throwable ignored) {}

                spawnTeleportBeamPillar(world, spawnX, spawnY, spawnZ, visualEntity.getBbHeight(), mode);

                VisualPokemonHolder newHolder = new VisualPokemonHolder(id, visualEntity, mode, i);
                newHolder.targetX = spawnX;
                newHolder.targetY = spawnY;
                newHolder.targetZ = spawnZ;
                newHolder.spawnTicks = VisualPokemonHolder.MAX_SPAWN_TICKS;
                currentHolders.add(newHolder);
            } else {
                if (existing.mode != mode) {
                    existing.spawnTicks = VisualPokemonHolder.MAX_SPAWN_TICKS;
                    spawnTeleportBeamPillar(world, existing.entity.getX(), existing.entity.getY(), existing.entity.getZ(), existing.entity.getBbHeight(), mode);
                }
                existing.mode = mode;
                existing.slotIndex = i;
                existing.entity.setPokemon(pkmn);
                try {
                    existing.entity.getEntityData().set(PokemonEntity.getLABEL_LEVEL(), pkmn.getLevel());
                } catch (Throwable ignored) {}
                existing.entity.setCustomName(pkmn.getDisplayName(false));
                existing.entity.removeTag("virtualloot_visual_mode_1");
                existing.entity.removeTag("virtualloot_visual_mode_2");
                existing.entity.removeTag("virtualloot_visual_mode_3");
                if (mode > 0) {
                    existing.entity.addTag("virtualloot_visual_mode_" + mode);
                }
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

    private static void spawnTeleportBeamPillar(ClientLevel world, double x, double y, double z, float height, int mode) {
        float h = Math.max(1.5f, height + 0.8f);
        for (double dy = 0; dy <= h; dy += 0.25) {
            for (int r = 0; r < 6; r++) {
                double rad = (2.0 * Math.PI / 6.0) * r + (dy * 1.2);
                double px = x + Math.cos(rad) * 0.55;
                double pz = z + Math.sin(rad) * 0.55;
                double py = y + dy;

                if (mode == 1) {
                    world.addParticle(ParticleTypes.END_ROD, px, py, pz, 0.0, 0.08, 0.0);
                    world.addParticle(ParticleTypes.ELECTRIC_SPARK, px, py, pz, 0.0, 0.05, 0.0);
                } else if (mode == 2) {
                    world.addParticle(ParticleTypes.REVERSE_PORTAL, px, py, pz, 0.0, 0.12, 0.0);
                    world.addParticle(ParticleTypes.GLOW, px, py, pz, 0.0, 0.04, 0.0);
                } else if (mode == 3) {
                    world.addParticle(ParticleTypes.PORTAL, px, py, pz, (Math.random() - 0.5) * 0.2, 0.15, (Math.random() - 0.5) * 0.2);
                    world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 0.0, 0.06, 0.0);
                } else {
                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, px, py, pz, 0.0, 0.04, 0.0);
                }
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
            List<VisualPokemonHolder> holders = entry.getValue();
            int total = holders.size();
            for (int i = 0; i < total; i++) {
                VisualPokemonHolder holder = holders.get(i);
                tickVisualEntity(holder, pasturePos, world, holders, i, total);
            }
        }
    }

    private static void tickVisualEntity(
        VisualPokemonHolder holder,
        BlockPos pasturePos,
        ClientLevel world,
        List<VisualPokemonHolder> allHolders,
        int slotIndex,
        int totalSlots
    ) {
        PokemonEntity entity = holder.entity;
        if (entity.isRemoved()) return;

        int mode = holder.mode;
        net.minecraft.util.RandomSource rand = world.random;

        double ex = entity.getX();
        double ey = entity.getY() + entity.getBbHeight() * 0.5;
        double ez = entity.getZ();

        if (holder.spawnTicks > 0) {
            holder.spawnTicks--;
            for (int i = 0; i < 6; i++) {
                double spiralAngle = (holder.spawnTicks * 0.35) + (i * (Math.PI / 3.0));
                double radius = 0.55 * (holder.spawnTicks / (float) VisualPokemonHolder.MAX_SPAWN_TICKS);
                double px = ex + Math.cos(spiralAngle) * radius;
                double pz = ez + Math.sin(spiralAngle) * radius;
                double py = entity.getY() + (rand.nextDouble() * (entity.getBbHeight() + 0.6));

                if (mode == 1) {
                    world.addParticle(ParticleTypes.END_ROD, px, py, pz, 0.0, 0.06, 0.0);
                } else if (mode == 2) {
                    world.addParticle(ParticleTypes.REVERSE_PORTAL, px, py, pz, 0.0, 0.09, 0.0);
                } else if (mode == 3) {
                    world.addParticle(ParticleTypes.PORTAL, px, py, pz, (rand.nextDouble() - 0.5) * 0.2, 0.1, (rand.nextDouble() - 0.5) * 0.2);
                }
            }
        }

        if (mode == 3 && rand.nextFloat() < 0.15f) {
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, ex + (rand.nextDouble() - 0.5) * 0.5, ey + (rand.nextDouble() - 0.5) * 0.5, ez + (rand.nextDouble() - 0.5) * 0.5, 0, 0.02, 0);
        }

        // Natural pasture roaming: pick a new room floor tile every 100-220 ticks
        if (holder.roamCooldown-- <= 0) {
            holder.roamCooldown = 100 + rand.nextInt(120);
            holder.isIdle = rand.nextFloat() < 0.40f;
            if (!holder.isIdle) {
                List<Vec3> roomSpots = scanIndoorRoomFloor(world, pasturePos);
                if (!roomSpots.isEmpty()) {
                    Vec3 newSpot = roomSpots.get(rand.nextInt(roomSpots.size()));
                    holder.targetX = newSpot.x;
                    holder.targetY = newSpot.y;
                    holder.targetZ = newSpot.z;
                }
            }
        }

        entity.xo = entity.getX();
        entity.yo = entity.getY();
        entity.zo = entity.getZ();
        entity.yRotO = entity.getYRot();
        entity.yHeadRotO = entity.yHeadRot;
        entity.yBodyRotO = entity.yBodyRot;

        if (!holder.isIdle) {
            double dx = holder.targetX - entity.getX();
            double dz = holder.targetZ - entity.getZ();
            double distSq = dx * dx + dz * dz;

            if (distSq > 0.3) {
                double angle = Math.atan2(dz, dx);
                float targetYaw = (float) (angle * (180 / Math.PI)) - 90f;
                float currentYaw = entity.getYRot();
                float yawDiff = Mth.wrapDegrees(targetYaw - currentYaw);
                float newYaw = currentYaw + yawDiff * 0.18f;

                entity.setYRot(newYaw);
                entity.yHeadRot = newYaw;
                entity.yBodyRot = newYaw;

                double speed = (mode == 3) ? 0.020 : 0.028;
                double vx = Math.cos(angle) * speed;
                double vz = Math.sin(angle) * speed;
                entity.move(MoverType.SELF, new Vec3(vx, 0.0, vz));
            } else {
                holder.isIdle = true;
            }
        } else {
            if (rand.nextFloat() < 0.05f) {
                float headJitter = (rand.nextFloat() - 0.5f) * 30.0f;
                entity.yHeadRot = entity.getYRot() + headJitter;
            }
        }

        // Soft anti-stacking entity repulsion: guarantees no overlapping
        for (VisualPokemonHolder other : allHolders) {
            if (other == holder || other.entity.isRemoved()) continue;
            double sepDx = entity.getX() - other.entity.getX();
            double sepDz = entity.getZ() - other.entity.getZ();
            double sepDistSq = sepDx * sepDx + sepDz * sepDz;
            double minSep = Math.max(2.2, (entity.getBbWidth() + other.entity.getBbWidth()) * 0.75);
            if (sepDistSq < minSep * minSep && sepDistSq > 0.0001) {
                double sepDist = Math.sqrt(sepDistSq);
                double push = (minSep - sepDist) * 0.05;
                entity.setPos(entity.getX() + (sepDx / sepDist) * push, entity.getY(), entity.getZ() + (sepDz / sepDist) * push);
            }
        }

        double groundY = findLocalFloorY(world, entity.getX(), entity.getZ(), pasturePos.getY());

        if (mode == 3) {
            double hoverY = groundY + 0.5 + 0.12 * Math.sin(entity.tickCount * 0.08);
            entity.setPos(entity.getX(), hoverY, entity.getZ());
        } else {
            entity.setPos(entity.getX(), groundY, entity.getZ());
        }
    }

    public static double findLocalFloorY(ClientLevel world, double x, double z, int pastureY) {
        int bx = (int) Math.floor(x);
        int bz = (int) Math.floor(z);

        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(bx, pastureY + 2, bz);
        for (int y = pastureY + 2; y >= pastureY - 3; y--) {
            mpos.setY(y);
            BlockState state = world.getBlockState(mpos);
            if (!state.isAir() && !state.getCollisionShape(world, mpos).isEmpty()) {
                BlockPos above = mpos.above();
                BlockState aboveState = world.getBlockState(above);
                if (aboveState.getCollisionShape(world, above).isEmpty()) {
                    return y + 1.0;
                }
            }
        }
        return pastureY;
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
