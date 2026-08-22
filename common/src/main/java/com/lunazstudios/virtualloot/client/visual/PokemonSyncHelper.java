package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    public static CompoundTag serializePokemon(Pokemon pokemon) {
        return serializePokemon(pokemon, null);
    }

    public static CompoundTag serializePokemon(Pokemon pokemon, HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        if (pokemon == null) return tag;

        for (Method m : pokemon.getClass().getMethods()) {
            String name = m.getName();
            if (name.equals("saveToNBT") || name.equals("writeToNBT") || name.equals("saveToNbt")) {
                try {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length == 1 && params[0] == CompoundTag.class) {
                        Object res = m.invoke(pokemon, tag);
                        return res instanceof CompoundTag ct ? ct : tag;
                    } else if (params.length == 2) {
                        if (params[0] == CompoundTag.class) {
                            Object res = m.invoke(pokemon, tag, registries);
                            return res instanceof CompoundTag ct ? ct : tag;
                        } else if (params[1] == CompoundTag.class) {
                            Object res = m.invoke(pokemon, registries, tag);
                            return res instanceof CompoundTag ct ? ct : tag;
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }

        return tag;
    }

    public static Pokemon deserializePokemon(CompoundTag tag) {
        return deserializePokemon(tag, null);
    }

    public static Pokemon deserializePokemon(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag == null || tag.isEmpty()) return null;

        try {
            Pokemon pokemon = new Pokemon();
            for (Method m : pokemon.getClass().getMethods()) {
                String name = m.getName();
                if (name.equals("loadFromNBT") || name.equals("readFromNBT") || name.equals("loadFromNbt")) {
                    try {
                        Class<?>[] params = m.getParameterTypes();
                        if (params.length == 1 && params[0] == CompoundTag.class) {
                            Object res = m.invoke(pokemon, tag);
                            return res instanceof Pokemon p ? p : pokemon;
                        } else if (params.length == 2) {
                            if (params[0] == CompoundTag.class) {
                                Object res = m.invoke(pokemon, tag, registries);
                                return res instanceof Pokemon p ? p : pokemon;
                            } else if (params[1] == CompoundTag.class) {
                                Object res = m.invoke(pokemon, registries, tag);
                                return res instanceof Pokemon p ? p : pokemon;
                            }
                        }
                    } catch (Throwable ignored) {}
                }
            }
            return pokemon;
        } catch (Throwable t) {
            return null;
        }
    }
}
