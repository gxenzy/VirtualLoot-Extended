package com.lunazstudios.virtualloot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PastureLootConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_FILE = "config/PastureLoot.json";
    private static PastureLootConfig instance;

    private int tickPerMinute = Defaults.TICK_PER_MINUTE;
    private float dropChance = Defaults.DROP_CHANCE;
    private String[] itemBlacklist = Defaults.ITEM_BLACKLIST;
    private boolean flattenItemQuantity = Defaults.FLATTEN_ITEM_QUANTITY;
    private transient double dropChancePerTick;
    private transient Set<String> blacklistSet;

    private PastureLootConfig() {
        updateDerivedValues();
    }

    public static PastureLootConfig get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public double dropChancePerTick() {
        return dropChancePerTick;
    }

    public boolean isBlacklisted(String itemId) {
        return blacklistSet.contains(itemId);
    }

    public boolean flattenItemQuantity() {
        return flattenItemQuantity;
    }

    private void updateDerivedValues() {
        dropChancePerTick = 1.0D - Math.pow(1.0D - dropChance, 1.0D / tickPerMinute);
        blacklistSet = new HashSet<>(Arrays.asList(itemBlacklist));
    }

    private static PastureLootConfig load() {
        PastureLootConfig config = new PastureLootConfig();
        boolean needsSave = false;
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            config.tickPerMinute = json.get("tick_per_minute").getAsInt();
            config.dropChance = json.get("drop_chance_per_minute").getAsFloat();
            config.itemBlacklist = GSON.fromJson(json.get("item_blacklist"), String[].class);
            if (json.has("flatten_item_quantity")) {
                config.flattenItemQuantity = json.get("flatten_item_quantity").getAsBoolean();
            } else if (json.has("legacy_flatten_item_quantity")) {
                config.flattenItemQuantity = json.get("legacy_flatten_item_quantity").getAsBoolean();
                needsSave = true;
            } else {
                needsSave = true;
            }
            config.updateDerivedValues();
        } catch (IOException | RuntimeException ignored) {
            config = new PastureLootConfig();
            needsSave = true;
        }

        if (needsSave) {
            config.save();
        }
        return config;
    }

    private void save() {
        JsonObject json = new JsonObject();
        json.addProperty("tick_per_minute", tickPerMinute);
        json.addProperty("drop_chance_per_minute", dropChance);
        json.add("item_blacklist", GSON.toJsonTree(itemBlacklist));
        json.addProperty("flatten_item_quantity", flattenItemQuantity);
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (IOException ignored) {
        }
    }

    private static final class Defaults {
        private static final int TICK_PER_MINUTE = 1200;
        private static final float DROP_CHANCE = 0.05F;
        private static final boolean FLATTEN_ITEM_QUANTITY = false;
        private static final String[] ITEM_BLACKLIST = new String[]{
            "minecraft:porkchop",
            "minecraft:beef",
            "minecraft:chicken",
            "minecraft:mutton",
            "minecraft:rabbit",
            "minecraft:fish",
            "minecraft:cooked_porkchop",
            "minecraft:cooked_beef",
            "minecraft:cooked_chicken",
            "minecraft:cooked_mutton",
            "minecraft:cooked_rabbit",
            "minecraft:cooked_fish",
            "minecraft:leather",
            "minecraft:bone",
            "minecraft:spider_eye",
            "minecraft:rotten_flesh",
            "minecraft:rabbit_hide",
            "minecraft:rabbit_foot",
            "minecraft:cod",
            "minecraft:pufferfish",
            "minecraft:bone_block",
            "minecraft:bone_meal",
            "cobblemon:sharp_beak",
            "minecraft:honey_bottle",
            "minecraft:salmon",
            "minecraft:white_wool"
        };
    }
}
