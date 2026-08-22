package com.lunazstudios.virtualloot.integration.cobbreeding;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import ludichat.cobbreeding.Cobbreeding;
import ludichat.cobbreeding.Config;
import ludichat.cobbreeding.PastureBreedingData;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public final class CobbreedingPastureInventoryBridge {
    private static int[] slots;

    private CobbreedingPastureInventoryBridge() {
    }

    public static NonNullList<ItemStack> getItems(PokemonPastureBlockEntity pasture) {
        PastureBreedingData data = PastureBreedingData.registry.get(pasture.getBlockPos());
        if (data == null) {
            data = new PastureBreedingData(-1L, NonNullList.withSize(config().getPastureInventorySize(), ItemStack.EMPTY), newBreedingTime());
            PastureBreedingData.registry.put(pasture.getBlockPos(), data);
        }
        return data.getEggs();
    }

    public static int[] getSlotsForFace() {
        int size = config().getPastureInventorySize();
        if (slots == null || slots.length != size) {
            slots = new int[size];
            for (int index = 0; index < slots.length; index++) {
                slots[index] = index;
            }
        }
        return slots;
    }

    public static boolean canTakeItemThroughFace() {
        return config().getAllowHoppersToPullFromPastureBlock();
    }

    private static int newBreedingTime() {
        Config config = config();
        return net.minecraft.util.RandomSource.create().nextIntBetweenInclusive(config.getMinBreedingTimeInTicks(), config.getMaxBreedingTimeInTicks());
    }

    private static Config config() {
        try {
            return Cobbreeding.config;
        } catch (RuntimeException exception) {
            return new Config();
        }
    }
}
