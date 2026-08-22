package com.lunazstudios.virtualloot.integration.cobbleworkers;

import accieo.cobbleworkers.job.definition.Condition;
import accieo.cobbleworkers.job.definition.Requirements;
import com.cobblemon.mod.common.api.abilities.Ability;
import com.cobblemon.mod.common.api.mark.Mark;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;
import java.util.Set;

final class CobbleworkersPokemonRequirements {
    private CobbleworkersPokemonRequirements() {
    }

    static boolean matches(ServerLevel world, Pokemon pokemon, Requirements requirements, boolean hasWater) {
        return requirements == null
            || matchesSpecies(pokemon, requirements.getSpecies())
            && matchesTypes(pokemon, requirements.getTypes())
            && matchesAbilities(pokemon, requirements.getAbilities())
            && matchesMoves(pokemon, requirements.getMoves())
            && matchesMarks(pokemon, requirements.getMarks())
            && matchesLevel(pokemon, requirements.getLevel())
            && matchesShiny(pokemon, requirements.getShiny())
            && matchesFriendship(pokemon, requirements.getFriendship())
            && matchesAspects(pokemon, requirements.getAspects())
            && matchesCosmetic(pokemon, requirements.getCosmetic())
            && matchesConditions(world, requirements.getConditions(), hasWater);
    }

    private static boolean matchesSpecies(Pokemon pokemon, List<String> species) {
        return empty(species) || species.contains(pokemon.getSpecies().getName().toLowerCase(Locale.ROOT));
    }

    private static boolean matchesTypes(Pokemon pokemon, List<String> types) {
        if (empty(types)) {
            return true;
        }
        for (ElementalType type : pokemon.getTypes()) {
            if (types.contains(type.getName().toLowerCase(Locale.ROOT)) || types.contains(type.showdownId())) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesAbilities(Pokemon pokemon, List<String> abilities) {
        if (empty(abilities)) {
            return true;
        }
        Ability ability = pokemon.getAbility();
        return ability != null && abilities.contains(normalize(ability.getName()));
    }

    private static boolean matchesMoves(Pokemon pokemon, List<String> moves) {
        if (empty(moves)) {
            return true;
        }
        for (Move move : pokemon.getMoveSet()) {
            if (move != null && moves.contains(normalize(move.getTemplate().getName()))) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesMarks(Pokemon pokemon, List<String> marks) {
        if (empty(marks)) {
            return true;
        }
        for (Mark mark : pokemon.getMarks()) {
            ResourceLocation identifier = mark.getIdentifier();
            if (identifier != null && (marks.contains(identifier.toString()) || marks.contains(identifier.getPath()))) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesLevel(Pokemon pokemon, Integer level) {
        return level == null || pokemon.getLevel() >= level;
    }

    private static boolean matchesShiny(Pokemon pokemon, Boolean shiny) {
        return shiny == null || pokemon.getShiny() == shiny;
    }

    private static boolean matchesFriendship(Pokemon pokemon, Integer friendship) {
        return friendship == null || pokemon.getFriendship() >= friendship;
    }

    private static boolean matchesAspects(Pokemon pokemon, List<String> aspects) {
        if (empty(aspects)) {
            return true;
        }
        Set<String> pokemonAspects = pokemon.getAspects();
        for (String aspect : aspects) {
            if (pokemonAspects.contains(aspect)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matchesCosmetic(Pokemon pokemon, String cosmetic) {
        if (cosmetic == null || cosmetic.isEmpty()) {
            return true;
        }
        ItemStack cosmeticItem = pokemon.getCosmeticItem();
        if (cosmeticItem.isEmpty()) {
            return false;
        }
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(cosmeticItem.getItem());
        return itemId != null && (cosmetic.equals(itemId.toString()) || cosmetic.equals(itemId.getPath()) || cosmetic.equals(cosmeticItem.getItem().toString()));
    }

    private static boolean matchesConditions(ServerLevel world, List<Condition> conditions, boolean hasWater) {
        if (empty(conditions)) {
            return true;
        }
        for (Condition condition : conditions) {
            if (!matchesCondition(world, condition, hasWater)) {
                return false;
            }
        }
        return true;
    }

    private static boolean matchesCondition(ServerLevel world, Condition condition, boolean hasWater) {
        if (condition == Condition.IN_WATER) {
            return hasWater;
        }
        if (condition == Condition.IS_DAY) {
            return world.isDay();
        }
        if (condition == Condition.IS_NIGHT) {
            return world.isNight();
        }
        if (condition == Condition.IS_RAINING) {
            return world.isRaining();
        }
        if (condition == Condition.IS_THUNDERING) {
            return world.isThundering();
        }
        return false;
    }

    private static boolean empty(List<?> values) {
        return values == null || values.isEmpty();
    }

    private static String normalize(String value) {
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }
}
