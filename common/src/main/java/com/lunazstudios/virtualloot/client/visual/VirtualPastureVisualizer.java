package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualPastureVisualizer {

    public static final int MAX_SPAWN_TICKS = 30; // 1.5s materialization duration
    private static final Map<UUID, Integer> SPAWN_TICKS = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> LAST_MODES = new ConcurrentHashMap<>();
    private static final Map<BlockPos, java.util.Set<UUID>> PASTURE_POKEMON_MAP = new ConcurrentHashMap<>();

    public static float getSpawnScale(UUID pokemonId, float partialTicks) {
        if (pokemonId == null) return 1.0f;
        Integer ticks = SPAWN_TICKS.get(pokemonId);
        if (ticks == null || ticks <= 0) return 1.0f;
        float progress = 1.0f - ((ticks - partialTicks) / (float) MAX_SPAWN_TICKS);
        progress = Math.max(0.01f, Math.min(1.0f, progress));
        return (float) Math.sin(progress * Math.PI * 0.5);
    }

    public static void handleServerSync(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;

        java.util.Set<UUID> newUuids = ConcurrentHashMap.newKeySet();
        if (pokemonList != null) {
            for (Pokemon pkmn : pokemonList) {
                if (pkmn == null) continue;
                UUID id = pkmn.getUuid();
                newUuids.add(id);
                Integer lastMode = LAST_MODES.get(id);

                VirtualRenderShaderHelper.setPokemonVisualMode(id, mode);

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

        // Clean up any pokemon that were removed from this virtual pasture
        java.util.Set<UUID> oldUuids = PASTURE_POKEMON_MAP.put(pos, newUuids);
        if (oldUuids != null) {
            for (UUID oldId : oldUuids) {
                if (!newUuids.contains(oldId)) {
                    VirtualRenderShaderHelper.removePokemon(oldId);
                    SPAWN_TICKS.remove(oldId);
                    LAST_MODES.remove(oldId);
                }
            }
        }
    }

    public static void clientTick() {
        Minecraft mc = Minecraft.getInstance();
        ClientLevel world = mc.level;
        if (world == null) return;

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
        VirtualRenderShaderHelper.clear();
        SPAWN_TICKS.clear();
        LAST_MODES.clear();
    }
}
