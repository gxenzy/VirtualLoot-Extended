package com.lunazstudios.virtualloot.integration;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface VirtualPastureInventory {
    NonNullList<ItemStack> virtualloot$getItems();

    boolean virtualloot$insertGenerated(ItemStack stack);

    boolean virtualloot$insertGenerated(List<ItemStack> stacks);

    List<ItemStack> virtualloot$drainItems();

    long virtualloot$getCobbreedingTime();

    void virtualloot$setCobbreedingTime(long time);

    int virtualloot$getCobbreedingRequiredTicks();

    void virtualloot$setCobbreedingRequiredTicks(int ticks);
}
