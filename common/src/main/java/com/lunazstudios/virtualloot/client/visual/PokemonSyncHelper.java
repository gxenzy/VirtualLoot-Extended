package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;

public final class PokemonSyncHelper {

    private PokemonSyncHelper() {}

    private static Object getRegistryLookup() {
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
            String name = m.getName();
            if (name.equals("saveToNBT") || name.equals("writeToNBT") || name.equals("saveToNbt")) {
                try {
                    Class<?>[] params = m.getParameterTypes();
                    if (params.length == 1 && params[0] == CompoundTag.class) {
                        Object res = m.invoke(pokemon, tag);
                        return res instanceof CompoundTag ct ? ct : tag;
                    } else if (params.length == 2) {
                        Object lookup = getRegistryLookup();
                        if (params[0] == CompoundTag.class) {
                            Object res = m.invoke(pokemon, tag, lookup);
                            return res instanceof CompoundTag ct ? ct : tag;
                        } else if (params[1] == CompoundTag.class) {
                            Object res = m.invoke(pokemon, lookup, tag);
                            return res instanceof CompoundTag ct ? ct : tag;
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }

        return tag;
    }

    public static Pokemon deserializePokemon(CompoundTag tag) {
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
                            Object lookup = getRegistryLookup();
                            if (params[0] == CompoundTag.class) {
                                Object res = m.invoke(pokemon, tag, lookup);
                                return res instanceof Pokemon p ? p : pokemon;
                            } else if (params[1] == CompoundTag.class) {
                                Object res = m.invoke(pokemon, lookup, tag);
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

    public static Pokemon getPokemonFromPC(java.util.UUID pokemonId) {
        if (pokemonId == null) return null;
        try {
            Object storage = CobblemonClient.INSTANCE.getStorage();
            if (storage == null) return null;

            for (Method m : storage.getClass().getMethods()) {
                if (m.getParameterCount() == 1 && m.getParameterTypes()[0] == java.util.UUID.class) {
                    try {
                        Object res = m.invoke(storage, pokemonId);
                        if (res instanceof Pokemon p) return p;
                    } catch (Throwable ignored) {}
                }
            }

            for (Method m : storage.getClass().getMethods()) {
                if (m.getParameterCount() == 0) {
                    try {
                        Object val = m.invoke(storage);
                        if (val instanceof Iterable<?> iter) {
                            for (Object obj : iter) {
                                if (obj instanceof Pokemon p && pokemonId.equals(p.getUuid())) return p;
                            }
                        }
                        if (val != null) {
                            for (Method storeMethod : val.getClass().getMethods()) {
                                if (storeMethod.getParameterCount() == 0) {
                                    try {
                                        Object inner = storeMethod.invoke(val);
                                        if (inner instanceof Iterable<?> innerIter) {
                                            for (Object obj : innerIter) {
                                                if (obj instanceof Pokemon p && pokemonId.equals(p.getUuid())) return p;
                                            }
                                        }
                                    } catch (Throwable ignored) {}
                                }
                            }
                        }
                    } catch (Throwable ignored) {}
                }
            }
        } catch (Throwable ignored) {}
        return null;
    }
}
