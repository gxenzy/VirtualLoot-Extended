package com.lunazstudios.virtualloot.integration.cobblebase;

import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import notlown.cobblebase.core.BaseManager;
import notlown.cobblebase.core.CobblebaseConfig;
import notlown.cobblebase.core.LogManager;
import notlown.cobblebase.core.ProducerOverrides;
import notlown.cobblebase.core.SkillDef;
import notlown.cobblebase.core.SkillEntry;
import notlown.cobblebase.core.SkillRegistry;
import notlown.cobblebase.core.SpeciesSkillRegistry;
import notlown.cobblebase.core.SpeciesSkills;
import notlown.cobblebase.core.executors.ProducerExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class CobblebaseCompat {
    private static final Map<UUID, Long> LAST_JOB_EXECUTION = new ConcurrentHashMap<>();

    private CobblebaseCompat() {
    }

    /**
     * Executes Cobblebase job logic for all Pokemon in a Virtual Pasture.
     * Returns true if at least one Pokemon had an active Cobblebase job handled.
     */
    public static boolean tick(Level world, BlockPos pos, PokemonPastureBlockEntity pasture) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return false;
        }

        long now = serverLevel.getGameTime();
        VirtualPastureInventory inventory = (VirtualPastureInventory) (Object) pasture;
        boolean anyJobHandled = false;

        for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
            if (tethering == null) continue;

            Pokemon pokemon = tethering.getPokemon();
            if (pokemon == null || pokemon.isFainted()) {
                continue;
            }

            UUID pokemonId = pokemon.getUuid();
            String assignment = BaseManager.INSTANCE.getAssignment(pokemonId);

            // Always tick passive buffs and auras for all pastured Pokemon
            try {
                BaseManager.INSTANCE.tickPassiveBuffsWithoutEntity(world, pos, pokemon);
            } catch (Throwable ignored) {
            }

            if (assignment == null) {
                // Pokemon is relaxing / idle - fall back to standard VirtualLoot species drops
                continue;
            }

            anyJobHandled = true;
            String speciesName = BaseManager.INSTANCE.resolveSpeciesName(pokemon);
            SpeciesSkills speciesData = SpeciesSkillRegistry.INSTANCE.getSkills(speciesName);
            if (speciesData == null) {
                continue;
            }

            SkillEntry skillEntry = null;
            for (SkillEntry entry : speciesData.getSkills()) {
                if (entry.getSkillId().equals(assignment)) {
                    skillEntry = entry;
                    break;
                }
            }
            if (skillEntry == null) {
                skillEntry = new SkillEntry(assignment, 3);
            }

            SkillDef skillDef = SkillRegistry.INSTANCE.getEffective(assignment);
            if (skillDef == null) {
                skillDef = SkillRegistry.INSTANCE.get(assignment);
            }
            if (skillDef == null) {
                continue;
            }

            // Execute virtual job based on executor type
            handleVirtualJob(serverLevel, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        }

        return anyJobHandled;
    }

    private static void handleVirtualJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        UUID pokemonId = pokemon.getUuid();
        String executor = skillDef.getExecutor();

        if ("producer".equalsIgnoreCase(executor)) {
            handleProducerJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        } else if ("generic_loot".equalsIgnoreCase(executor) || "finder".equalsIgnoreCase(executor) || "fishing".equalsIgnoreCase(executor)) {
            handleLootTableJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        } else if ("mining".equalsIgnoreCase(executor)) {
            handleMiningJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        } else if ("harvester".equalsIgnoreCase(executor)) {
            handleHarvesterJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        } else {
            // For other job executors, handle generic produce or loot table if defined
            if (skillDef.getLootTable() != null) {
                handleLootTableJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
            }
        }
    }

    private static void handleProducerJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        UUID pokemonId = pokemon.getUuid();
        ProducerExecutor.ProduceEntry produceEntry = ProducerOverrides.INSTANCE.getOverride(speciesName);
        if (produceEntry == null) {
            produceEntry = ProducerExecutor.INSTANCE.getProduceEntry(speciesName);
        }
        if (produceEntry == null) {
            return;
        }

        long baseCooldown = produceEntry.getCooldownSeconds() != null ? produceEntry.getCooldownSeconds() : skillDef.getCooldownSeconds();
        long cooldownTicks = CobblebaseConfig.INSTANCE.getEffectiveCooldownTicks(baseCooldown, skillEntry.getProficiency(), skillDef.getId());

        Long lastTime = LAST_JOB_EXECUTION.get(pokemonId);
        if (lastTime == null) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            return;
        }

        if (now - lastTime < cooldownTicks) {
            return;
        }

        ItemStack stack = createItemStack(world, produceEntry.getItemId(), produceEntry.getCount());
        if (stack.isEmpty()) {
            return;
        }

        if (inventory.virtualloot$insertGenerated(stack)) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            BaseManager.INSTANCE.markJobSuccess(pokemonId, now);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                produceEntry.getDisplayName() + " x" + produceEntry.getCount(),
                LogManager.Rarity.COMMON
            );
        }
    }

    private static void handleLootTableJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        UUID pokemonId = pokemon.getUuid();
        String lootTablePath = skillDef.getLootTable();
        if (lootTablePath == null || lootTablePath.isEmpty()) {
            return;
        }

        long baseCooldown = skillDef.getCooldownSeconds();
        long cooldownTicks = CobblebaseConfig.INSTANCE.getEffectiveCooldownTicks(baseCooldown, skillEntry.getProficiency(), skillDef.getId());

        Long lastTime = LAST_JOB_EXECUTION.get(pokemonId);
        if (lastTime == null) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            return;
        }

        if (now - lastTime < cooldownTicks) {
            return;
        }

        List<ItemStack> generated = rollLootTable(world, pos, lootTablePath);
        if (!generated.isEmpty() && inventory.virtualloot$insertGenerated(generated)) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            BaseManager.INSTANCE.markJobSuccess(pokemonId, now);
            ItemStack first = generated.get(0);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                first.getHoverName().getString() + " x" + first.getCount(),
                LogManager.Rarity.UNCOMMON
            );
        }
    }

    private static void handleMiningJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        UUID pokemonId = pokemon.getUuid();
        long baseCooldown = skillDef.getCooldownSeconds();
        long cooldownTicks = CobblebaseConfig.INSTANCE.getEffectiveCooldownTicks(baseCooldown, skillEntry.getProficiency(), skillDef.getId());

        Long lastTime = LAST_JOB_EXECUTION.get(pokemonId);
        if (lastTime == null) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            return;
        }

        if (now - lastTime < cooldownTicks) {
            return;
        }

        int proficiency = Math.max(1, skillEntry.getProficiency());
        String[] ores = new String[]{"cobblestone", "coal", "raw_iron", "raw_copper", "raw_gold", "redstone", "amethyst_shard"};
        String pickedOre = ores[world.random.nextInt(Math.min(ores.length, proficiency + 2))];
        int count = 1 + world.random.nextInt(Math.min(4, proficiency));

        ItemStack stack = createItemStack(world, pickedOre, count);
        if (!stack.isEmpty() && inventory.virtualloot$insertGenerated(stack)) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            BaseManager.INSTANCE.markJobSuccess(pokemonId, now);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                stack.getHoverName().getString() + " x" + count,
                LogManager.Rarity.COMMON
            );
        }
    }

    private static void handleHarvesterJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        UUID pokemonId = pokemon.getUuid();
        long baseCooldown = skillDef.getCooldownSeconds();
        long cooldownTicks = CobblebaseConfig.INSTANCE.getEffectiveCooldownTicks(baseCooldown, skillEntry.getProficiency(), skillDef.getId());

        Long lastTime = LAST_JOB_EXECUTION.get(pokemonId);
        if (lastTime == null) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            return;
        }

        if (now - lastTime < cooldownTicks) {
            return;
        }

        int proficiency = Math.max(1, skillEntry.getProficiency());
        String[] crops = new String[]{"wheat", "carrot", "potato", "beetroot", "sweet_berries", "melon_slice", "pumpkin", "sugar_cane"};
        String pickedCrop = crops[world.random.nextInt(crops.length)];
        int count = 1 + world.random.nextInt(Math.min(4, proficiency));

        ItemStack stack = createItemStack(world, pickedCrop, count);
        if (!stack.isEmpty() && inventory.virtualloot$insertGenerated(stack)) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            BaseManager.INSTANCE.markJobSuccess(pokemonId, now);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                stack.getHoverName().getString() + " x" + count,
                LogManager.Rarity.COMMON
            );
        }
    }

    private static ItemStack createItemStack(ServerLevel world, String itemId, int count) {
        ResourceLocation id = itemId.contains(":") ? ResourceLocation.tryParse(itemId) : ResourceLocation.fromNamespaceAndPath("minecraft", itemId);
        if (id == null) return ItemStack.EMPTY;
        Item item = world.registryAccess().registryOrThrow(Registries.ITEM).get(id);
        if (item == null) return ItemStack.EMPTY;
        return new ItemStack(item, count);
    }

    private static List<ItemStack> rollLootTable(ServerLevel world, BlockPos pos, String lootTableId) {
        ResourceLocation id = ResourceLocation.tryParse(lootTableId);
        if (id == null) return List.of();

        LootParams params = new LootParams.Builder(world)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .create(LootContextParamSets.CHEST);

        LootTable table = world.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, id));
        List<ItemStack> generated = new ArrayList<>();
        for (ItemStack stack : table.getRandomItems(params)) {
            if (!stack.isEmpty()) {
                generated.add(stack);
            }
        }
        return generated;
    }
}
