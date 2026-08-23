package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualPastureVisualizer {

    public static final int MAX_SPAWN_TICKS = 30; // 1.5s materialization duration
    private static final Map<UUID, Integer> SPAWN_TICKS = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> LAST_MODES = new ConcurrentHashMap<>();

    private static final class PastureVisualRecord {
        final BlockPos pos;
        final int mode;
        final Set<Integer> entityIds = ConcurrentHashMap.newKeySet();
        final Set<UUID> pokemonUuids = ConcurrentHashMap.newKeySet();

        PastureVisualRecord(BlockPos pos, int mode) {
            this.pos = pos;
            this.mode = mode;
        }
    }

    private static final Map<BlockPos, PastureVisualRecord> ACTIVE_PASTURES = new ConcurrentHashMap<>();

    public static float getSpawnScale(UUID pokemonId, float partialTicks) {
        if (pokemonId == null) return 1.0f;
        Integer ticks = SPAWN_TICKS.get(pokemonId);
        if (ticks == null || ticks <= 0) return 1.0f;
        float progress = 1.0f - ((ticks - partialTicks) / (float) MAX_SPAWN_TICKS);
        progress = Math.max(0.01f, Math.min(1.0f, progress));
        return (float) Math.sin(progress * Math.PI * 0.5);
    }

    public static void handleServerSync(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        handleServerSync(pos, mode, new java.util.ArrayList<>(), pokemonList);
    }

    public static void handleServerSync(BlockPos pos, int mode, List<Integer> entityIds, List<Pokemon> pokemonList) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;

        PastureVisualRecord record = new PastureVisualRecord(pos, mode);
        if (entityIds != null) {
            record.entityIds.addAll(entityIds);
        }

        if (pokemonList != null) {
            for (Pokemon pkmn : pokemonList) {
                if (pkmn == null) continue;
                UUID id = pkmn.getUuid();
                record.pokemonUuids.add(id);
                Integer lastMode = LAST_MODES.get(id);

                if (mode > 0 && (lastMode == null || lastMode != mode)) {
                    SPAWN_TICKS.put(id, MAX_SPAWN_TICKS);
                    LAST_MODES.put(id, mode);

                    if (world != null) {
                        double px = pos.getX() + 0.5;
                        double py = pos.getY() + 0.5;
                        double pz = pos.getZ() + 0.5;

                        try {
                            world.playLocalSound(px, py, pz, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.BLOCKS, 0.9f, 1.25f, false);
                        } catch (Throwable ignored) {}

                        spawnTeleportBeamPillar(world, px, py, pz, 2.0f, mode);
                    }
                } else if (mode == 0) {
                    LAST_MODES.put(id, 0);
                    SPAWN_TICKS.remove(id);
                }
            }
        }

        ACTIVE_PASTURES.put(pos, record);
        syncVisualHelper();
    }

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

        boolean changed = false;
        // Verify all pastures are actually still VirtualPastureBlocks in the world
        for (BlockPos pos : ACTIVE_PASTURES.keySet()) {
            if (world.hasChunkAt(pos)) {
                if (!VirtualLootBlocks.isVirtualPastureBlock(world.getBlockState(pos).getBlock())) {
                    ACTIVE_PASTURES.remove(pos);
                    changed = true;
                }
            }
        }

        if (changed) {
            syncVisualHelper();
        }

        for (Map.Entry<UUID, Integer> entry : SPAWN_TICKS.entrySet()) {
            UUID id = entry.getKey();
            int ticks = entry.getValue();
            if (ticks > 0) {
                SPAWN_TICKS.put(id, ticks - 1);
            } else {
                SPAWN_TICKS.remove(id);
            }
        }
    }

    private static void syncVisualHelper() {
        Map<UUID, Integer> pokemonModes = new ConcurrentHashMap<>();
        Map<Integer, Integer> entityModes = new ConcurrentHashMap<>();

        for (PastureVisualRecord record : ACTIVE_PASTURES.values()) {
            for (UUID uuid : record.pokemonUuids) {
                pokemonModes.put(uuid, record.mode);
            }
            for (int entityId : record.entityIds) {
                entityModes.put(entityId, record.mode);
            }
        }

        VirtualRenderShaderHelper.updateActiveVisuals(pokemonModes, entityModes);
    }

    private static void spawnTeleportBeamPillar(ClientLevel world, double x, double y, double z, float height, int mode) {
        float h = Math.max(1.5f, height + 0.8f);
        for (double dy = 0; dy <= h; dy += 0.2) {
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
                }
            }
        }
    }

    public static void clearAll() {
        ACTIVE_PASTURES.clear();
        VirtualRenderShaderHelper.clear();
        SPAWN_TICKS.clear();
        LAST_MODES.clear();
    }
}
