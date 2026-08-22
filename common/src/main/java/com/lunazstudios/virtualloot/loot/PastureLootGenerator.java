package com.lunazstudios.virtualloot.loot;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.config.PastureLootConfig;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public final class PastureLootGenerator {
    private PastureLootGenerator() {
    }

    public static void tick(Level world, BlockPos pos, PokemonPastureBlockEntity pasture) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        PastureLootConfig config = PastureLootConfig.get();
        VirtualPastureInventory inventory = (VirtualPastureInventory) (Object) pasture;
        boolean changed = false;

        for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
            if (serverLevel.random.nextDouble() >= config.dropChancePerTick()) {
                continue;
            }

            Pokemon pokemon = tethering.getPokemon();
            if (pokemon == null || pokemon.isFainted()) {
                continue;
            }

            DropTable dropTable = pokemon.getForm().getDrops();
            List<DropEntry> drops = dropTable.getDrops(dropTable.getAmount(), pokemon);
            if (drops.isEmpty()) {
                continue;
            }

            DropEntry drop = drops.get(serverLevel.random.nextInt(drops.size()));
            if (!(drop instanceof ItemDropEntry itemDrop)) {
                continue;
            }

            ItemStack stack = createStack(serverLevel, itemDrop, config);
            if (!stack.isEmpty() && inventory.virtualloot$insertGenerated(stack)) {
                changed = true;
            }
        }

        if (changed) {
            pasture.setChanged();
        }
    }

    private static ItemStack createStack(ServerLevel world, ItemDropEntry drop, PastureLootConfig config) {
        String itemId = drop.getItem().toString();
        if (config.isBlacklisted(itemId)) {
            return ItemStack.EMPTY;
        }

        Item item = world.registryAccess().registryOrThrow(Registries.ITEM).get(drop.getItem());
        if (item == null) {
            return ItemStack.EMPTY;
        }

        int count = config.flattenItemQuantity() ? 1 : quantity(world, drop);
        if (count <= 0) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = new ItemStack(item, count);
        if (drop.getComponents() != null) {
            DataComponentPatch.Builder builder = DataComponentPatch.builder();
            drop.getComponents().forEach(builder::set);
            stack.applyComponentsAndValidate(builder.build());
        }
        return stack;
    }

    private static int quantity(ServerLevel world, ItemDropEntry drop) {
        if (drop.getQuantityRange() != null) {
            int first = drop.getQuantityRange().getFirst();
            int last = drop.getQuantityRange().getLast();
            return first + world.random.nextInt(last - first + 1);
        }
        return drop.getQuantity();
    }
}
