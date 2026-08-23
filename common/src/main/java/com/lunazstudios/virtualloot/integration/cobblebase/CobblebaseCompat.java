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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import notlown.cobblebase.core.BaseManager;
import notlown.cobblebase.core.CobblebaseConfig;
import notlown.cobblebase.core.JobConfigOverrides;
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
     * Dynamically sets Cobblebase's button corner config to TOP_RIGHT so its own
     * reposition logic places the button in the top header away from CloudTweak.
     */
    public static void configureCobblebaseButtonCorner() {
        try {
            Class<?> autoConfigClass = Class.forName("me.shedaniel.autoconfig.AutoConfig", false, Thread.currentThread().getContextClassLoader());
            Class<?> configClass = Class.forName("notlown.cobblebase.core.CobblebaseClothConfig", false, Thread.currentThread().getContextClassLoader());
            Class<?> cornerEnumClass = Class.forName("notlown.cobblebase.core.MainButtonCorner", false, Thread.currentThread().getContextClassLoader());

            java.lang.reflect.Method getConfigHolderMethod = autoConfigClass.getMethod("getConfigHolder", Class.class);
            Object holder = getConfigHolderMethod.invoke(null, configClass);
            if (holder != null) {
                java.lang.reflect.Method getConfigMethod = holder.getClass().getMethod("getConfig");
                Object configObj = getConfigMethod.invoke(holder);
                if (configObj != null) {
                    java.lang.reflect.Method getGeneralMethod = configObj.getClass().getMethod("getGeneral");
                    Object generalObj = getGeneralMethod.invoke(configObj);
                    if (generalObj != null) {
                        Object topRightEnum = null;
                        for (Object enumConst : cornerEnumClass.getEnumConstants()) {
                            if ("TOP_RIGHT".equals(enumConst.toString())) {
                                topRightEnum = enumConst;
                                break;
                            }
                        }
                        if (topRightEnum != null) {
                            java.lang.reflect.Method setCornerMethod = generalObj.getClass().getMethod("setMainButtonCorner", cornerEnumClass);
                            setCornerMethod.invoke(generalObj, topRightEnum);
                        }
                    }
                }
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * Executes Cobblebase job logic for all Pokemon in a Virtual Pasture.
     * Returns true if at least one Pokemon had an active or auto-assigned Cobblebase job handled.
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

            String speciesName = BaseManager.INSTANCE.resolveSpeciesName(pokemon);
            SpeciesSkills speciesData = SpeciesSkillRegistry.INSTANCE.getSkills(speciesName);

            SkillEntry skillEntry = null;
            if (assignment != null) {
                if (speciesData != null) {
                    for (SkillEntry entry : speciesData.getSkills()) {
                        if (entry.getSkillId().equals(assignment)) {
                            skillEntry = entry;
                            break;
                        }
                    }
                }
                if (skillEntry == null) {
                    skillEntry = new SkillEntry(assignment, 3);
                }
            } else if (speciesData != null && !speciesData.getSkills().isEmpty()) {
                // Auto-assign the Pokemon's highest-proficiency available skill so it works automatically
                skillEntry = pickBestAutoSkill(speciesData.getSkills());
                if (skillEntry != null) {
                    assignment = skillEntry.getSkillId();
                }
            }

            if (assignment == null || skillEntry == null) {
                continue;
            }

            SkillDef skillDef = SkillRegistry.INSTANCE.getEffective(assignment);
            if (skillDef == null) {
                skillDef = SkillRegistry.INSTANCE.get(assignment);
            }
            if (skillDef == null || !JobConfigOverrides.INSTANCE.isEnabled(assignment)) {
                continue;
            }

            anyJobHandled = true;
            handleVirtualJob(serverLevel, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        }

        return anyJobHandled;
    }

    private static SkillEntry pickBestAutoSkill(List<SkillEntry> skills) {
        SkillEntry best = null;
        int bestScore = -1;
        for (SkillEntry entry : skills) {
            String id = entry.getSkillId();
            SkillDef def = SkillRegistry.INSTANCE.getEffective(id);
            if (def == null) def = SkillRegistry.INSTANCE.get(id);
            if (def == null || !JobConfigOverrides.INSTANCE.isEnabled(id)) continue;

            // Prioritize Gathering & Production jobs over purely passive ones
            int priority = 1;
            String cat = def.getCategory().toLowerCase();
            if ("gathering".equals(cat)) priority = 3;
            else if ("generation".equals(cat) || "production".equals(cat)) priority = 3;
            else if ("combat".equals(cat)) priority = 2;

            int score = (priority * 10) + entry.getProficiency();
            if (score > bestScore) {
                bestScore = score;
                best = entry;
            }
        }
        return best != null ? best : (skills.isEmpty() ? null : skills.get(0));
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
        String skillId = skillDef.getId();
        String executor = skillDef.getExecutor();

        long baseCooldown = skillDef.getCooldownSeconds();
        long cooldownTicks = CobblebaseConfig.INSTANCE.getEffectiveCooldownTicks(baseCooldown, skillEntry.getProficiency(), skillId);

        Long lastTime = LAST_JOB_EXECUTION.get(pokemonId);
        if (lastTime == null) {
            // First tick: give a small initial jitter so they don't all fire at tick 0 but start quickly
            long initialDelay = Math.min(cooldownTicks, 40L + (Math.abs(pokemonId.hashCode()) % 60L));
            LAST_JOB_EXECUTION.put(pokemonId, now - (cooldownTicks - initialDelay));
            return;
        }

        if (now - lastTime < cooldownTicks) {
            return;
        }

        boolean success = false;
        if ("producer".equalsIgnoreCase(executor) || skillId.contains("producer")) {
            success = handleProducerJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        } else {
            success = handleLootTierJob(world, pos, pokemon, speciesName, skillDef, skillEntry, inventory, now);
        }

        if (success) {
            LAST_JOB_EXECUTION.put(pokemonId, now);
            BaseManager.INSTANCE.markJobSuccess(pokemonId, now);
        }
    }

    private static String formatGameTime(ServerLevel world) {
        long day = (world.getDayTime() / 24000L) + 1;
        long timeOfDay = world.getDayTime() % 24000L;
        long hours = (timeOfDay / 1000L + 6) % 24;
        long minutes = (timeOfDay % 1000L) * 60 / 1000;
        return String.format("[Day %d, %02d:%02d] ", day, hours, minutes);
    }

    private static boolean handleProducerJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        ProducerExecutor.ProduceEntry produceEntry = ProducerOverrides.INSTANCE.getOverride(speciesName);
        if (produceEntry == null) {
            produceEntry = ProducerExecutor.INSTANCE.getProduceEntry(speciesName);
        }
        if (produceEntry == null) {
            return false;
        }

        ItemStack stack = createItemStack(world, produceEntry.getItemId(), produceEntry.getCount());
        if (stack.isEmpty()) {
            return false;
        }

        if (inventory.virtualloot$insertGenerated(stack)) {
            String timePrefix = formatGameTime(world);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                timePrefix + produceEntry.getDisplayName() + " x" + produceEntry.getCount(),
                LogManager.Rarity.COMMON
            );
            return true;
        }
        return false;
    }

    private static boolean handleLootTierJob(
        ServerLevel world,
        BlockPos pos,
        Pokemon pokemon,
        String speciesName,
        SkillDef skillDef,
        SkillEntry skillEntry,
        VirtualPastureInventory inventory,
        long now
    ) {
        String skillId = skillDef.getId();
        int prof = skillEntry.getProficiency();
        
        // Pick tier based on proficiency
        int tier = pickLootTier(world, prof);
        LogManager.Rarity rarity = switch (tier) {
            case 3 -> LogManager.Rarity.ULTRA_RARE;
            case 2 -> LogManager.Rarity.RARE;
            case 1 -> LogManager.Rarity.UNCOMMON;
            default -> LogManager.Rarity.COMMON;
        };

        String lootTableId = resolveTieredLootTable(skillId, skillDef, tier);
        List<ItemStack> generated = rollLootTable(world, pos, lootTableId);
        if (generated.isEmpty() && tier > 0) {
            generated = rollLootTable(world, pos, resolveTieredLootTable(skillId, skillDef, 0));
        }

        // Thematic fallback if loot table JSON is not present in registry
        if (generated.isEmpty()) {
            generated = getFallbackThematicLoot(world, skillId, tier, pokemon);
        }

        if (!generated.isEmpty() && inventory.virtualloot$insertGenerated(generated)) {
            ItemStack first = generated.get(0);
            String itemDesc = first.getHoverName().getString() + (generated.size() == 1 && first.getCount() > 1 ? " x" + first.getCount() : "");
            if (generated.size() > 1) {
                itemDesc += " (+" + (generated.size() - 1) + " items)";
            }
            String timePrefix = formatGameTime(world);
            LogManager.INSTANCE.log(
                pos,
                world.getGameTime(),
                pokemon.getSpecies().getName(),
                skillDef.getName(),
                timePrefix + itemDesc,
                rarity
            );
            return true;
        }
        return false;
    }

    private static List<ItemStack> getFallbackThematicLoot(ServerLevel world, String skillId, int tier, Pokemon pokemon) {
        String clean = skillId.toLowerCase().replace("cobblebase:", "");
        List<ItemStack> items = new ArrayList<>();
        net.minecraft.util.RandomSource rand = world.random;

        if (clean.contains("mining") || clean.contains("prospector") || clean.contains("excavator")) {
            Item[] ores = switch (tier) {
                case 3 -> new Item[]{Items.DIAMOND, Items.EMERALD, Items.ANCIENT_DEBRIS, Items.RAW_GOLD};
                case 2 -> new Item[]{Items.RAW_GOLD, Items.LAPIS_LAZULI, Items.REDSTONE, Items.AMETHYST_SHARD};
                case 1 -> new Item[]{Items.RAW_IRON, Items.RAW_COPPER, Items.COAL};
                default -> new Item[]{Items.COBBLESTONE, Items.COAL, Items.RAW_IRON};
            };
            Item chosen = ores[rand.nextInt(ores.length)];
            int count = (tier == 3) ? (1 + rand.nextInt(2)) : (1 + rand.nextInt(3 + tier));
            items.add(new ItemStack(chosen, count));
        } else if (clean.contains("smith") || clean.contains("armorer") || clean.contains("craftsman")) {
            Item[] smithItems = switch (tier) {
                case 3 -> new Item[]{Items.NETHERITE_SCRAP, Items.DIAMOND, Items.ANVIL};
                case 2 -> new Item[]{Items.GOLD_INGOT, Items.SHIELD, Items.IRON_BLOCK};
                case 1 -> new Item[]{Items.IRON_INGOT, Items.COPPER_INGOT, Items.CHAIN};
                default -> new Item[]{Items.IRON_NUGGET, Items.COPPER_INGOT, Items.IRON_INGOT};
            };
            Item chosen = smithItems[rand.nextInt(smithItems.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(2 + tier)));
        } else if (clean.contains("harvester") || clean.contains("forager") || clean.contains("botanist") || clean.contains("irrigator")) {
            Item[] crops = switch (tier) {
                case 3 -> new Item[]{Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.ENCHANTED_GOLDEN_APPLE};
                case 2 -> new Item[]{Items.PUMPKIN, Items.MELON_SLICE, Items.SWEET_BERRIES, Items.APPLE};
                case 1 -> new Item[]{Items.CARROT, Items.POTATO, Items.BEETROOT};
                default -> new Item[]{Items.WHEAT, Items.WHEAT_SEEDS, Items.APPLE};
            };
            Item chosen = crops[rand.nextInt(crops.length)];
            items.add(new ItemStack(chosen, (tier == 3 && chosen == Items.ENCHANTED_GOLDEN_APPLE) ? 1 : (1 + rand.nextInt(3 + tier))));
        } else if (clean.contains("alchemist") || clean.contains("pharmacist")) {
            Item[] alch = switch (tier) {
                case 3 -> new Item[]{Items.GHAST_TEAR, Items.BLAZE_ROD, Items.DRAGON_BREATH};
                case 2 -> new Item[]{Items.GLOWSTONE_DUST, Items.MAGMA_CREAM, Items.FERMENTED_SPIDER_EYE};
                case 1 -> new Item[]{Items.REDSTONE, Items.SPIDER_EYE, Items.SUGAR};
                default -> new Item[]{Items.GLASS_BOTTLE, Items.REDSTONE, Items.GUNPOWDER};
            };
            Item chosen = alch[rand.nextInt(alch.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(2 + tier)));
        } else if (clean.contains("fishing") || clean.contains("diving")) {
            Item[] fish = switch (tier) {
                case 3 -> new Item[]{Items.NAUTILUS_SHELL, Items.HEART_OF_THE_SEA, Items.PRISMARINE_CRYSTALS};
                case 2 -> new Item[]{Items.PUFFERFISH, Items.TROPICAL_FISH, Items.PRISMARINE_SHARD};
                case 1 -> new Item[]{Items.SALMON, Items.COD, Items.DRIED_KELP};
                default -> new Item[]{Items.COD, Items.KELP};
            };
            Item chosen = fish[rand.nextInt(fish.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(2 + tier)));
        } else if (clean.contains("scholar") || clean.contains("mentor") || clean.contains("trainer")) {
            Item[] expItems = switch (tier) {
                case 3 -> new Item[]{Items.ENCHANTED_BOOK, Items.EXPERIENCE_BOTTLE};
                case 2 -> new Item[]{Items.EXPERIENCE_BOTTLE, Items.BOOK, Items.LAPIS_LAZULI};
                case 1 -> new Item[]{Items.BOOK, Items.PAPER, Items.LAPIS_LAZULI};
                default -> new Item[]{Items.PAPER, Items.FEATHER};
            };
            Item chosen = expItems[rand.nextInt(expItems.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(2 + tier)));
        } else if (clean.contains("fuel") || clean.contains("lava")) {
            Item[] fuelItems = (tier >= 2) ? new Item[]{Items.BLAZE_POWDER, Items.MAGMA_CREAM, Items.COAL_BLOCK} : new Item[]{Items.CHARCOAL, Items.COAL};
            Item chosen = fuelItems[rand.nextInt(fuelItems.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(2 + tier)));
        } else if (clean.contains("guard") || clean.contains("combat") || clean.contains("scout")) {
            Item[] combatItems = (tier >= 2) ? new Item[]{Items.IRON_SWORD, Items.SHIELD, Items.ARROW, Items.SPECTRAL_ARROW} : new Item[]{Items.ARROW, Items.BONE, Items.STRING};
            Item chosen = combatItems[rand.nextInt(combatItems.length)];
            items.add(new ItemStack(chosen, 1 + rand.nextInt(3 + tier)));
        }

        if (items.isEmpty() && pokemon != null) {
            try {
                com.cobblemon.mod.common.api.drop.DropTable dt = pokemon.getForm().getDrops();
                List<com.cobblemon.mod.common.api.drop.DropEntry> drops = dt.getDrops(dt.getAmount(), pokemon);
                for (com.cobblemon.mod.common.api.drop.DropEntry d : drops) {
                    if (d instanceof com.cobblemon.mod.common.api.drop.ItemDropEntry ide) {
                        Item item = world.registryAccess().registryOrThrow(Registries.ITEM).get(ide.getItem());
                        if (item != null && item != Items.AIR) {
                            items.add(new ItemStack(item, Math.max(1, ide.getQuantity())));
                            break;
                        }
                    }
                }
            } catch (Throwable ignored) {}
        }

        return items;
    }

    private static int pickLootTier(ServerLevel world, int prof) {
        int roll = world.random.nextInt(100);
        return switch (prof) {
            case 5 -> roll < 30 ? 1 : (roll < 75 ? 2 : 3);
            case 4 -> roll < 20 ? 0 : (roll < 60 ? 1 : (roll < 90 ? 2 : 3));
            case 3 -> roll < 50 ? 0 : (roll < 85 ? 1 : 2);
            default -> roll < 80 ? 0 : 1;
        };
    }

    private static String resolveTieredLootTable(String skillId, SkillDef skillDef, int tier) {
        String cleanId = skillId.replace("cobblebase:", "");
        String suffix = switch (tier) {
            case 3 -> "_ultra_rare";
            case 2 -> "_rare";
            case 1 -> "_uncommon";
            default -> "_common";
        };

        if (cleanId.startsWith("finder_") || cleanId.equals("mining") || cleanId.equals("harvester") || cleanId.equals("fishing") || cleanId.equals("archeologist")) {
            return "cobblebase:" + cleanId + suffix;
        }

        if (skillDef.getLootTable() != null && !skillDef.getLootTable().isEmpty()) {
            return skillDef.getLootTable();
        }

        return "cobblebase:" + cleanId + suffix;
    }

    private static List<ItemStack> rollLootTable(ServerLevel world, BlockPos pos, String lootTableId) {
        ResourceLocation rl = ResourceLocation.tryParse(lootTableId);
        if (rl == null) {
            return List.of();
        }

        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, rl);
        LootTable table = world.getServer().reloadableRegistries().getLootTable(key);
        if (table == LootTable.EMPTY) {
            return List.of();
        }

        LootParams params = new LootParams.Builder(world)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .create(LootContextParamSets.CHEST);

        return new ArrayList<>(table.getRandomItems(params));
    }

    private static ItemStack createItemStack(ServerLevel world, String itemId, int count) {
        ResourceLocation rl = ResourceLocation.tryParse(itemId);
        if (rl == null) return ItemStack.EMPTY;
        Item item = world.registryAccess().registryOrThrow(Registries.ITEM).get(rl);
        if (item == null || item == Items.AIR) return ItemStack.EMPTY;
        return new ItemStack(item, Math.max(1, count));
    }
}
