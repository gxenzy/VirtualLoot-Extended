package com.lunazstudios.virtualloot.integration.cobblebase;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public final class CobblebaseJobIconBridge {
    private static final Map<String, String> SKILL_ITEM_MAP = new HashMap<>();

    static {
        // High-fidelity Cobblemon item mappings
        SKILL_ITEM_MAP.put("cobblebase:finder_evo", "cobblemon:fire_stone");
        SKILL_ITEM_MAP.put("cobblebase:finder_exp", "cobblemon:rare_candy");
        SKILL_ITEM_MAP.put("cobblebase:finder_stat", "cobblemon:protein");
        SKILL_ITEM_MAP.put("cobblebase:finder_held", "cobblemon:choice_band");
        SKILL_ITEM_MAP.put("cobblebase:lucky_charm", "cobblemon:shiny_charm");
        SKILL_ITEM_MAP.put("cobblebase:mentor", "cobblemon:exp_share");
        SKILL_ITEM_MAP.put("cobblebase:healer", "cobblemon:sacred_ash");
        SKILL_ITEM_MAP.put("cobblebase:guard", "cobblemon:assault_vest");
        SKILL_ITEM_MAP.put("cobblebase:finder_see", "cobblemon:miracle_seed");
        SKILL_ITEM_MAP.put("cobblebase:producer", "cobblemon:moomoo_milk");
        SKILL_ITEM_MAP.put("cobblebase:finder_food", "cobblemon:lava_cookie");
        SKILL_ITEM_MAP.put("cobblebase:speed_boost", "cobblemon:carbos");
        SKILL_ITEM_MAP.put("cobblebase:strength_boost", "cobblemon:muscle_band");
        SKILL_ITEM_MAP.put("cobblebase:resistance_boost", "cobblemon:iron");
        SKILL_ITEM_MAP.put("cobblebase:jump_boost", "cobblemon:running_shoes");
        SKILL_ITEM_MAP.put("cobblebase:haste_boost", "cobblemon:zinc");
        SKILL_ITEM_MAP.put("cobblebase:egg_hatcher", "cobbreeding:pokemon_egg");
        SKILL_ITEM_MAP.put("cobblebase:finder_bal", "cobblemon:ultra_ball");
    }

    private CobblebaseJobIconBridge() {
    }

    public static Item getItemFor(String skillId, Item fallback) {
        String itemId = SKILL_ITEM_MAP.get(skillId);
        if (itemId != null) {
            ResourceLocation rl = ResourceLocation.tryParse(itemId);
            if (rl != null && BuiltInRegistries.ITEM.containsKey(rl)) {
                Item item = BuiltInRegistries.ITEM.get(rl);
                if (item != null && item != Items.AIR) {
                    return item;
                }
            }
        }
        return fallback;
    }

    public static ItemStack getStackFor(String skillId, ItemStack fallback) {
        Item item = getItemFor(skillId, fallback.getItem());
        if (item != fallback.getItem()) {
            return new ItemStack(item);
        }
        return fallback;
    }
}
