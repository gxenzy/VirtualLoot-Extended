package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    public static CompoundTag serializePokemon(Pokemon pokemon, RegistryAccess registries) {
        if (pokemon == null) return new CompoundTag();
        try {
            return pokemon.saveToNBT(registries, new CompoundTag());
        } catch (Throwable t) {
            return new CompoundTag();
        }
    }

    public static Pokemon deserializePokemon(CompoundTag tag, RegistryAccess registries) {
        if (tag == null || tag.isEmpty() || registries == null) return null;
        try {
            return Pokemon.Companion.loadFromNBT(registries, tag);
        } catch (Throwable t) {
            return null;
        }
    }
}
