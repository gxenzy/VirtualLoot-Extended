package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    public static CompoundTag serializePokemon(Pokemon pokemon) {
        if (pokemon == null) return new CompoundTag();
        try {
            return pokemon.saveToNBT(new CompoundTag());
        } catch (Throwable t) {
            return new CompoundTag();
        }
    }

    public static CompoundTag serializePokemon(Pokemon pokemon, HolderLookup.Provider registries) {
        return serializePokemon(pokemon);
    }

    public static Pokemon deserializePokemon(CompoundTag tag) {
        if (tag == null || tag.isEmpty()) return null;
        try {
            Pokemon pokemon = new Pokemon();
            pokemon.loadFromNBT(tag);
            return pokemon;
        } catch (Throwable t) {
            return null;
        }
    }

    public static Pokemon deserializePokemon(CompoundTag tag, HolderLookup.Provider registries) {
        return deserializePokemon(tag);
    }
}
