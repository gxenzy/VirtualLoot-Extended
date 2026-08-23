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
    private static final java.util.Set<UUID> VIRTUAL_PASTURE_POKEMON = ConcurrentHashMap.newKeySet();

    public static void setPokemonVisualMode(UUID pokemonId, int mode) {
        if (pokemonId == null) return;
        VIRTUAL_PASTURE_POKEMON.add(pokemonId);
        POKEMON_VISUAL_MODES.put(pokemonId, mode);
    }

    public static void clear() {
        POKEMON_VISUAL_MODES.clear();
        VIRTUAL_PASTURE_POKEMON.clear();
    }

    public static boolean isVirtualPasturePokemon(LivingEntity entity) {
        if (entity instanceof PokemonEntity pkmn) {
            PokemonPastureBlockEntity.Tethering tethering = pkmn.getTethering();
            if (tethering != null) {
                BlockPos pasturePos = tethering.getPasturePos();
                if (pasturePos != null && pkmn.level() != null) {
                    BlockState state = pkmn.level().getBlockState(pasturePos);
                    if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                        return true;
                    }
                    // Normal Cobblemon pasture or non-virtual block -> strictly NOT a virtual pasture pokemon
                    return false;
                }
            }
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
            // 1. Direct pasture block inspection (absolute source of truth)
            PokemonPastureBlockEntity.Tethering tethering = pkmn.getTethering();
            if (tethering != null) {
                BlockPos pasturePos = tethering.getPasturePos();
                if (pasturePos != null && pkmn.level() != null) {
                    BlockState state = pkmn.level().getBlockState(pasturePos);
                    if (VirtualLootBlocks.isVirtualPastureBlock(state.getBlock())) {
                        return VirtualPastureBlock.getVisualMode(state);
                    }
                    // Normal Cobblemon pasture -> always render standard physical texture (0)
                    return 0;
                }
            }

            // 2. Entity tags
            if (entity.getTags().contains("virtualloot_visual_mode_1")) return 1;
            if (entity.getTags().contains("virtualloot_visual_mode_2")) return 2;
            if (entity.getTags().contains("virtualloot_visual_mode_3")) return 3;

            // 3. Synced packet cache (only if verified virtual pasture)
            if (isVirtualPasturePokemon(entity) && pkmn.getPokemon() != null) {
                Integer mode = POKEMON_VISUAL_MODES.get(pkmn.getPokemon().getUuid());
                if (mode != null) return mode;
            }
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
