package com.lunazstudios.virtualloot.integration.cobbleworkers;

import accieo.cobbleworkers.job.JobRegistry;
import accieo.cobbleworkers.job.context.LootGenerationContext;
import accieo.cobbleworkers.job.definition.CompiledComponent;
import accieo.cobbleworkers.job.definition.CooldownScope;
import accieo.cobbleworkers.job.definition.Job;
import accieo.cobbleworkers.job.definition.LootGenerationComponent;
import accieo.cobbleworkers.services.PlayerCooldownService;
import accieo.cobbleworkers.services.PokemonCooldownService;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class CobbleworkersCompat {
    private static final int RANGE = 5;
    private static final TagKey<Block> ARCHAEOLOGY_BLOCKS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("cobbleworkers", "archaeology_blocks"));
    private static final List<String> JOBS = List.of(
        "cobbleworkers:archaeologist",
        "cobbleworkers:dive_looter",
        "cobbleworkers:fishing_looter",
        "cobbleworkers:pickup_looter"
    );

    private CobbleworkersCompat() {
    }

    public static void tick(Level world, BlockPos pos, PokemonPastureBlockEntity pasture) {
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }

        boolean hasWater = hasWaterWithAir(serverLevel, pos);
        boolean hasArchaeologyBlock = hasArchaeologyBlockWithAir(serverLevel, pos);
        VirtualPastureInventory inventory = (VirtualPastureInventory) (Object) pasture;

        for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
            Pokemon pokemon = tethering.getPokemon();
            if (pokemon == null || pokemon.isFainted()) {
                continue;
            }

            for (String jobId : JOBS) {
                Job job = JobRegistry.INSTANCE.get(jobId);
                if (job == null || !environmentMatches(jobId, hasWater, hasArchaeologyBlock) || isOnCooldown(job, pokemon.getUuid(), tethering.getPlayerId(), serverLevel.getGameTime())) {
                    continue;
                }
                if (!CobbleworkersPokemonRequirements.matches(serverLevel, pokemon, job.getRequirements(), hasWater)) {
                    continue;
                }

                List<ItemStack> generated = generateLoot(serverLevel, pos, job);
                if (!generated.isEmpty() && !inventory.virtualloot$insertGenerated(generated)) {
                    continue;
                }
                markCooldown(job, pokemon.getUuid(), tethering.getPlayerId(), serverLevel.getGameTime());
            }
        }
    }

    private static boolean environmentMatches(String jobId, boolean hasWater, boolean hasArchaeologyBlock) {
        return switch (jobId) {
            case "cobbleworkers:dive_looter", "cobbleworkers:fishing_looter" -> hasWater;
            case "cobbleworkers:archaeologist" -> hasArchaeologyBlock;
            default -> true;
        };
    }

    private static boolean hasWaterWithAir(ServerLevel world, BlockPos origin) {
        return hasBlockWithAir(world, origin, state -> state.getFluidState().is(Fluids.WATER));
    }

    private static boolean hasArchaeologyBlockWithAir(ServerLevel world, BlockPos origin) {
        return hasBlockWithAir(world, origin, state -> state.is(ARCHAEOLOGY_BLOCKS));
    }

    private static boolean hasBlockWithAir(ServerLevel world, BlockPos origin, BlockMatcher matcher) {
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int x = origin.getX() - RANGE; x <= origin.getX() + RANGE; x++) {
            for (int y = origin.getY() - RANGE; y <= origin.getY() + RANGE; y++) {
                for (int z = origin.getZ() - RANGE; z <= origin.getZ() + RANGE; z++) {
                    cursor.set(x, y, z);
                    if (!world.hasChunkAt(cursor) || !world.hasChunkAt(cursor.above())) {
                        continue;
                    }
                    if (matcher.matches(world.getBlockState(cursor)) && world.getBlockState(cursor.above()).isAir()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static List<ItemStack> generateLoot(ServerLevel world, BlockPos pos, Job job) {
        List<String> lootTables = lootTables(job);
        if (lootTables.isEmpty()) {
            return List.of();
        }

        ResourceLocation selected = ResourceLocation.tryParse(lootTables.get(world.random.nextInt(lootTables.size())));
        if (selected == null) {
            return List.of();
        }

        LootParams params = new LootParams.Builder(world)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .create(LootContextParamSets.CHEST);
        LootTable table = world.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, selected));
        List<ItemStack> generated = new ArrayList<>();
        for (ItemStack stack : table.getRandomItems(params)) {
            if (!stack.isEmpty()) {
                generated.add(stack);
            }
        }
        return generated;
    }

    private static List<String> lootTables(Job job) {
        List<String> lootTables = new ArrayList<>();
        for (CompiledComponent compiled : job.getComponents()) {
            if (compiled.getComponent() instanceof LootGenerationComponent loot && loot.getContext() == LootGenerationContext.STATIC_TABLE && loot.getLootTables() != null) {
                lootTables.addAll(loot.getLootTables());
            }
        }
        return lootTables;
    }

    private static boolean isOnCooldown(Job job, UUID pokemonId, UUID playerId, long gameTime) {
        if (job.getCooldownScope() == CooldownScope.PER_PLAYER) {
            return playerId == null || PlayerCooldownService.INSTANCE.isOnCooldown(playerId, job.getId(), gameTime);
        }
        return PokemonCooldownService.INSTANCE.isOnCooldown(pokemonId, job.getId(), gameTime);
    }

    private static void markCooldown(Job job, UUID pokemonId, UUID playerId, long gameTime) {
        long cooldownTicks = Math.max(0, job.getCooldown()) * 20L;
        if (job.getCooldownScope() == CooldownScope.PER_PLAYER) {
            if (playerId != null) {
                PlayerCooldownService.INSTANCE.markExecuted(playerId, job.getId(), gameTime, cooldownTicks);
            }
            return;
        }
        PokemonCooldownService.INSTANCE.markExecuted(pokemonId, job.getId(), gameTime, cooldownTicks);
    }

    private interface BlockMatcher {
        boolean matches(BlockState state);
    }
}
