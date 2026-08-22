package com.lunazstudios.virtualloot.client.visual;

import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static List<Pokemon> getPasturePokemonFromScreen(Screen screen) {
        List<Pokemon> result = new ArrayList<>();
        if (screen == null) return result;

        try {
            for (GuiEventListener child : screen.children()) {
                if (child.getClass().getName().contains("PasturePokemonScrollList")) {
                    if (child instanceof ContainerEventHandler container) {
                        for (GuiEventListener slotObj : container.children()) {
                            if (slotObj.getClass().getName().contains("PastureSlot")) {
                                Pokemon pkmn = extractPokemonFromSlot(slotObj);
                                if (pkmn != null) {
                                    result.add(pkmn);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable ignored) {}
        return result;
    }

    private static Pokemon extractPokemonFromSlot(Object slot) {
        if (slot == null) return null;

        // 1. Try direct fields on PastureSlot
        for (Field f : slot.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object val = f.get(slot);
                if (val instanceof Pokemon p) return p;
                if (val instanceof PokemonEntity pe) return pe.getPokemon();
            } catch (Throwable ignored) {}
        }

        // 2. Try getPokemon() or getEntity() methods
        for (Method m : slot.getClass().getMethods()) {
            if (m.getParameterCount() == 0) {
                try {
                    String name = m.getName();
                    if (name.equals("getPokemon")) {
                        Object dto = m.invoke(slot);
                        if (dto instanceof Pokemon p) return p;
                        if (dto != null) {
                            Pokemon p = extractPokemonFromDTO(dto);
                            if (p != null) return p;
                        }
                    } else if (name.equals("getEntity")) {
                        Object ent = m.invoke(slot);
                        if (ent instanceof PokemonEntity pe) return pe.getPokemon();
                    }
                } catch (Throwable ignored) {}
            }
        }
        return null;
    }

    private static Pokemon extractPokemonFromDTO(Object dto) {
        if (dto == null) return null;
        if (dto instanceof Pokemon p) return p;

        // Try fields
        for (Field f : dto.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                Object val = f.get(dto);
                if (val instanceof Pokemon p) return p;
                if (val instanceof UUID uuid) {
                    Pokemon p = getPokemonFromPC(uuid);
                    if (p != null) return p;
                }
            } catch (Throwable ignored) {}
        }

        // Try getters
        for (Method m : dto.getClass().getMethods()) {
            if (m.getParameterCount() == 0) {
                try {
                    if (m.getReturnType() == Pokemon.class) {
                        return (Pokemon) m.invoke(dto);
                    }
                    if (m.getReturnType() == UUID.class) {
                        UUID uuid = (UUID) m.invoke(dto);
                        Pokemon p = getPokemonFromPC(uuid);
                        if (p != null) return p;
                    }
                } catch (Throwable ignored) {}
            }
        }
        return null;
    }

    public static Pokemon getPokemonFromPC(UUID pokemonId) {
        if (pokemonId == null) return null;
        try {
            Object storage = CobblemonClient.INSTANCE.getStorage();
            if (storage == null) return null;

            // Direct check on party & pc stores
            for (Method m : storage.getClass().getMethods()) {
                if (m.getParameterCount() == 0 && (m.getName().equals("getParty") || m.getName().equals("getPc") || m.getName().equals("getMyParty") || m.getName().equals("getPC"))) {
                    try {
                        Object store = m.invoke(storage);
                        if (store != null) {
                            Pokemon found = findPokemonInObject(store, pokemonId);
                            if (found != null) return found;
                        }
                    } catch (Throwable ignored) {}
                }
            }
        } catch (Throwable ignored) {}
        return null;
    }

    private static Pokemon findPokemonInObject(Object obj, UUID pokemonId) {
        if (obj == null) return null;

        if (obj instanceof Pokemon p && pokemonId.equals(p.getUuid())) {
            return p;
        }

        // Check if array (e.g. Pokemon[] slots)
        if (obj.getClass().isArray()) {
            int len = java.lang.reflect.Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object elem = java.lang.reflect.Array.get(obj, i);
                if (elem instanceof Pokemon p && pokemonId.equals(p.getUuid())) {
                    return p;
                }
            }
            return null;
        }

        // Check if Iterable (e.g. List<ClientPCBox> or List<Pokemon>)
        if (obj instanceof Iterable<?> iter) {
            for (Object elem : iter) {
                if (elem instanceof Pokemon p && pokemonId.equals(p.getUuid())) {
                    return p;
                } else if (elem != null) {
                    for (Field f : elem.getClass().getDeclaredFields()) {
                        try {
                            f.setAccessible(true);
                            Object val = f.get(elem);
                            Pokemon p = findPokemonInObject(val, pokemonId);
                            if (p != null) return p;
                        } catch (Throwable ignored) {}
                    }
                }
            }
        }

        return null;
    }
}
