package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    public static CompoundTag serializePokemon(Pokemon pokemon) {
        CompoundTag tag = new CompoundTag();
        if (pokemon == null) return tag;
        try {
            pokemon.saveToNBT(tag);
            return tag;
        } catch (Throwable ignored) {}

        try {
            pokemon.writeToNBT(tag);
            return tag;
        } catch (Throwable ignored) {}

        try {
            for (Method m : pokemon.getClass().getMethods()) {
                if ((m.getName().equals("saveToNBT") || m.getName().equals("writeToNBT")) && m.getParameterCount() == 1) {
                    m.invoke(pokemon, tag);
                    return tag;
                }
            }
        } catch (Throwable ignored) {}

        return tag;
    }

    public static Pokemon deserializePokemon(CompoundTag tag) {
        if (tag == null || tag.isEmpty()) return null;
        try {
            Pokemon pokemon = new Pokemon();
            pokemon.loadFromNBT(tag);
            return pokemon;
        } catch (Throwable ignored) {}

        try {
            Pokemon pokemon = new Pokemon();
            pokemon.readFromNBT(tag);
            return pokemon;
        } catch (Throwable ignored) {}

        try {
            Pokemon pokemon = new Pokemon();
            for (Method m : pokemon.getClass().getMethods()) {
                if ((m.getName().equals("loadFromNBT") || m.getName().equals("readFromNBT")) && m.getParameterCount() == 1) {
                    m.invoke(pokemon, tag);
                    return pokemon;
                }
            }
        } catch (Throwable ignored) {}

        return null;
    }
}
