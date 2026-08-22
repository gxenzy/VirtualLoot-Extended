package com.lunazstudios.virtualloot.integration.cobbreeding;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import kotlin.Pair;
import ludichat.cobbreeding.BreedingUtilities;
import ludichat.cobbreeding.Cobbreeding;
import ludichat.cobbreeding.CustomProperties;
import ludichat.cobbreeding.EggUtilities;
import ludichat.cobbreeding.utils.CobbreedingFile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class CobbreedingCompat {
    private CobbreedingCompat() {
    }

    public static void tick(Level world, BlockPos pos, BlockState state, PokemonPastureBlockEntity pasture) {
        if (!(world instanceof ServerLevel serverLevel) || !state.hasProperty(CustomProperties.BREEDING_ACTIVATED)) {
            return;
        }

        VirtualPastureInventory inventory = (VirtualPastureInventory) (Object) pasture;
        List<Pokemon> pokemon = pokemonIn(pasture);
        if (serverLevel.getGameTime() % Cobbreeding.config.getMirrorHerbTimeInTicks() == 0) {
            BreedingUtilities.applyMirrorHerb(pokemon);
        }

        Collection<Map.Entry<FormData, List<Pair<Pokemon, Pokemon>>>> possibleEggs = BreedingUtilities.getPossibleEggs(pokemon);
        if (!state.getValue(CustomProperties.BREEDING_ACTIVATED) || possibleEggs.isEmpty()) {
            inventory.virtualloot$setCobbreedingTime(-1L);
            return;
        }

        long pastureTime = inventory.virtualloot$getCobbreedingTime();
        if (pastureTime < 0L) {
            pastureTime = serverLevel.getGameTime();
            inventory.virtualloot$setCobbreedingTime(pastureTime);
        }

        int requiredTicks = inventory.virtualloot$getCobbreedingRequiredTicks();
        if (requiredTicks <= 0) {
            requiredTicks = newBreedingTime();
            inventory.virtualloot$setCobbreedingRequiredTicks(requiredTicks);
        }

        long timeDifference = serverLevel.getGameTime() - pastureTime;
        boolean eggCreated = false;
        while (timeDifference >= requiredTicks) {
            ItemStack egg = createEgg(possibleEggs);
            if (egg.isEmpty() || !inventory.virtualloot$insertGenerated(egg)) {
                break;
            }

            eggCreated = true;
            timeDifference -= requiredTicks;
            requiredTicks = newBreedingTime();
            inventory.virtualloot$setCobbreedingRequiredTicks(requiredTicks);
        }

        if (eggCreated) {
            inventory.virtualloot$setCobbreedingTime(serverLevel.getGameTime());
            updateHasEgg(serverLevel, pos, state, true);
            serverLevel.playSound(null, pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0F, 1.0F);
            pasture.setChanged();
        }
    }

    public static void cleanup(LevelAccessor world, BlockPos pos) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        Path path = serverLevel.getServer().getWorldPath(LevelResource.ROOT).resolve("pokemon").resolve("cobbreeding");
        File pastureFile = path.resolve("pastures").toFile();
        UUID ownerUuid = CobbreedingFile.getUuid(pastureFile, pos.toString());
        CobbreedingFile.removeCompoundTag(pastureFile, pos.toString());
        if (ownerUuid != null) {
            File playerFile = path.resolve(ownerUuid.toString().substring(0, 2)).resolve(ownerUuid.toString()).toFile();
            CobbreedingFile.removeCompoundTag(playerFile, pos.toString());
        }
    }

    private static List<Pokemon> pokemonIn(PokemonPastureBlockEntity pasture) {
        List<Pokemon> pokemon = new ArrayList<>();
        for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
            Pokemon tethered = tethering.getPokemon();
            if (tethered != null) {
                pokemon.add(tethered);
            }
        }
        return pokemon;
    }

    private static ItemStack createEgg(Collection<Map.Entry<FormData, List<Pair<Pokemon, Pokemon>>>> possibleEggs) {
        PokemonProperties properties = BreedingUtilities.chooseEgg(possibleEggs);
        if (properties == null) {
            return ItemStack.EMPTY;
        }
        ItemStack egg = EggUtilities.getEggFromPokemonProperties(properties, null);
        return egg == null ? ItemStack.EMPTY : egg;
    }

    private static int newBreedingTime() {
        int min = Cobbreeding.config.getMinBreedingTimeInTicks();
        int max = Cobbreeding.config.getMaxBreedingTimeInTicks();
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static void updateHasEgg(ServerLevel world, BlockPos pos, BlockState state, boolean hasEgg) {
        if (state.hasProperty(CustomProperties.HAS_EGG) && state.getValue(CustomProperties.HAS_EGG) != hasEgg) {
            world.setBlockAndUpdate(pos, state.setValue(CustomProperties.HAS_EGG, hasEgg));
        }
    }
}
