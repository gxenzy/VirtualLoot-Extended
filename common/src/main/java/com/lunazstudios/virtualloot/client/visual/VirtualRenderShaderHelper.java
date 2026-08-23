package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualRenderShaderHelper {

    private static final Map<UUID, Integer> POKEMON_VISUAL_MODES = new ConcurrentHashMap<>();

    public static void setPokemonVisualMode(UUID pokemonId, int mode) {
        if (pokemonId == null) return;
        POKEMON_VISUAL_MODES.put(pokemonId, mode);
    }

    public static void removePokemon(UUID pokemonId) {
        if (pokemonId == null) return;
        POKEMON_VISUAL_MODES.remove(pokemonId);
    }

    public static void clear() {
        POKEMON_VISUAL_MODES.clear();
    }

    public static boolean isVirtualPasturePokemon(LivingEntity entity) {
        if (entity instanceof PokemonEntity pkmn) {
            // 1. Direct pasture block inspection (if valid non-zero block pos)
            PokemonPastureBlockEntity.Tethering tethering = pkmn.getTethering();
            if (tethering != null) {
                BlockPos pasturePos = tethering.getPasturePos();
                if (pasturePos != null && !pasturePos.equals(BlockPos.ZERO) && pkmn.level() != null) {
                    BlockState state = pkmn.level().getBlockState(pasturePos);
                    if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                        return true;
                    }
                    if (state.getBlock() instanceof com.cobblemon.mod.common.block.PastureBlock) {
                        return false;
                    }
                }
            }

            // 2. Synced packet cache (primary on client)
            if (pkmn.getPokemon() != null && POKEMON_VISUAL_MODES.containsKey(pkmn.getPokemon().getUuid())) {
                return true;
            }

            // 3. Entity tags
            if (entity.getTags().contains("virtualloot_virtual_pasture")
                    || entity.getTags().contains("virtualloot_visual_mode_1")
                    || entity.getTags().contains("virtualloot_visual_mode_2")
                    || entity.getTags().contains("virtualloot_visual_mode_3")) {
                return true;
            }
        }
        return false;
    }

    public static int getVisualMode(LivingEntity entity) {
        if (entity instanceof PokemonEntity pkmn) {
            // 1. Direct pasture block inspection (if valid non-zero block pos)
            PokemonPastureBlockEntity.Tethering tethering = pkmn.getTethering();
            if (tethering != null) {
                BlockPos pasturePos = tethering.getPasturePos();
                if (pasturePos != null && !pasturePos.equals(BlockPos.ZERO) && pkmn.level() != null) {
                    BlockState state = pkmn.level().getBlockState(pasturePos);
                    if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                        return VirtualPastureBlock.getVisualMode(state);
                    }
                    if (state.getBlock() instanceof com.cobblemon.mod.common.block.PastureBlock) {
                        return 0;
                    }
                }
            }

            // 2. Synced packet cache (primary on client)
            if (pkmn.getPokemon() != null) {
                Integer mode = POKEMON_VISUAL_MODES.get(pkmn.getPokemon().getUuid());
                if (mode != null) return mode;
            }

            // 3. Entity tags
            if (entity.getTags().contains("virtualloot_visual_mode_1")) return 1;
            if (entity.getTags().contains("virtualloot_visual_mode_2")) return 2;
            if (entity.getTags().contains("virtualloot_visual_mode_3")) return 3;
        }
        return 0;
    }

    public static RenderType getCustomRenderType(LivingEntity entity, ResourceLocation texture, RenderType defaultType) {
        int mode = getVisualMode(entity);
        if (mode == 1) {
            // Cyber Emissive Wireframe
            return RenderType.entityCutoutNoCull(texture);
        } else if (mode == 2) {
            // Glowing Translucent Hologram
            return RenderType.entityTranslucentCull(texture);
        } else if (mode == 3) {
            // Ethereal Ghost
            return RenderType.entityTranslucent(texture);
        }
        return defaultType;
    }
}
