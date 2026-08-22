package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
            if (storage != null) {
                Pokemon found = searchForPokemon(storage, pokemonId, 0);
                if (found != null) return found;
            }
        } catch (Throwable ignored) {}
        return null;
    }

    private static Pokemon searchForPokemon(Object obj, java.util.UUID targetId, int depth) {
        if (obj == null || depth > 4) return null;

        if (obj instanceof Pokemon p) {
            if (targetId.equals(p.getUuid())) return p;
            return null;
        }

        if (obj instanceof Iterable<?> iter) {
            for (Object item : iter) {
                Pokemon found = searchForPokemon(item, targetId, depth + 1);
                if (found != null) return found;
            }
            return null;
        }

        for (Method m : obj.getClass().getMethods()) {
            if (m.getParameterCount() == 0 && m.getName().startsWith("get") && !m.getName().equals("getClass")) {
                try {
                    Class<?> returnType = m.getReturnType();
                    if (Pokemon.class.isAssignableFrom(returnType) || Iterable.class.isAssignableFrom(returnType) || returnType.getName().contains("Store") || returnType.getName().contains("Box") || returnType.getName().contains("Slot")) {
                        Object val = m.invoke(obj);
                        if (val != null) {
                            Pokemon found = searchForPokemon(val, targetId, depth + 1);
                            if (found != null) return found;
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }

        return null;
    }

    public static List<Pokemon> getAllPokemonFromScreen(Object screen) {
        List<Pokemon> list = new ArrayList<>();
        if (screen == null) return list;
        collectAllPokemon(screen, list, 0);
        return list;
    }

    private static void collectAllPokemon(Object obj, List<Pokemon> list, int depth) {
        if (obj == null || depth > 4) return;

        if (obj instanceof Pokemon p) {
            if (!list.contains(p)) list.add(p);
            return;
        }

        if (obj instanceof Iterable<?> iter) {
            for (Object item : iter) {
                collectAllPokemon(item, list, depth + 1);
            }
            return;
        }

        for (Method m : obj.getClass().getMethods()) {
            if (m.getParameterCount() == 0 && m.getName().startsWith("get") && !m.getName().equals("getClass")) {
                try {
                    Class<?> returnType = m.getReturnType();
                    if (Pokemon.class.isAssignableFrom(returnType) || Iterable.class.isAssignableFrom(returnType) || returnType.getName().contains("Pokemon") || returnType.getName().contains("Slot") || returnType.getName().contains("Pasture")) {
                        Object val = m.invoke(obj);
                        if (val != null) {
                            collectAllPokemon(val, list, depth + 1);
                        }
                    }
                } catch (Throwable ignored) {}
            }
        }
    }
}
