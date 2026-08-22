package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    private static RegistryAccess getRegistryAccess() {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc != null && mc.level != null) {
                return mc.level.registryAccess();
            }
        } catch (Throwable ignored) {}
        return null;
    }

    public static CompoundTag serializePokemon(Pokemon pokemon) {
        CompoundTag tag = new CompoundTag();
        if (pokemon == null) return tag;

        for (Method m : pokemon.getClass().getMethods()) {
            if (m.getName().equals("saveToNBT") || m.getName().equals("writeToNBT")) {
                try {
                    if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == CompoundTag.class) {
                        m.invoke(pokemon, tag);
                        return tag;
                    } else if (m.getParameterCount() == 2) {
                        RegistryAccess ra = getRegistryAccess();
                        if (m.getParameterTypes()[0] == RegistryAccess.class) {
                            m.invoke(pokemon, ra, tag);
                            return tag;
                        } else if (m.getParameterTypes()[1] == RegistryAccess.class) {
                            m.invoke(pokemon, tag, ra);
                            return tag;
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }

        return tag;
    }

    public static Pokemon deserializePokemon(CompoundTag tag) {
        if (tag == null || tag.isEmpty()) return null;

        Pokemon pokemon = new Pokemon();
        for (Method m : pokemon.getClass().getMethods()) {
            if (m.getName().equals("loadFromNBT") || m.getName().equals("readFromNBT")) {
                try {
                    if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == CompoundTag.class) {
                        m.invoke(pokemon, tag);
                        return pokemon;
                    } else if (m.getParameterCount() == 2) {
                        RegistryAccess ra = getRegistryAccess();
                        if (m.getParameterTypes()[0] == RegistryAccess.class) {
                            m.invoke(pokemon, ra, tag);
                            return pokemon;
                        } else if (m.getParameterTypes()[1] == RegistryAccess.class) {
                            m.invoke(pokemon, tag, ra);
                            return pokemon;
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }

        return pokemon;
    }
}
