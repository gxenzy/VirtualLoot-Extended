package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.UUID;

public class VirtualPastureVisualizer {

    public static float getSpawnScale(UUID pokemonId, float partialTicks) {
        return 1.0f;
    }

    public static void handleServerSync(BlockPos pos, int mode, List<Pokemon> pokemonList) {
        if (pokemonList != null) {
            for (Pokemon pkmn : pokemonList) {
                if (pkmn != null) {
                    VirtualRenderShaderHelper.setPokemonVisualMode(pkmn.getUuid(), mode);
                }
            }
        }
    }

    public static void clientTick() {
        // Cobblemon handles 100% of real entity simulation, pathfinding, AI, and collision natively
    }

    public static void clearAll() {
        VirtualRenderShaderHelper.clear();
    }
}
