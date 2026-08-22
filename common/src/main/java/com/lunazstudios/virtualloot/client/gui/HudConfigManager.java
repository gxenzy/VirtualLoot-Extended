package com.lunazstudios.virtualloot.client.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class HudConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;

    public static class HudData {
        public int cobblebaseOffsetX = 0;
        public int cobblebaseOffsetY = -16;
        public int virtualLootOffsetX = 228;
        public int virtualLootOffsetY = -14;
        public int hudBtnOffsetX = 352;
        public int hudBtnOffsetY = 4;
    }

    public static HudData data = new HudData();
    public static boolean editMode = false;

    public static File getConfigFile() {
        if (configFile == null) {
            File configDir = new File(Minecraft.getInstance().gameDirectory, "config");
            if (!configDir.exists()) {
                configDir.mkdirs();
            }
            configFile = new File(configDir, "virtualloot_hud.json");
        }
        return configFile;
    }

    public static void load() {
        try {
            File file = getConfigFile();
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    HudData loaded = GSON.fromJson(reader, HudData.class);
                    if (loaded != null) {
                        data = loaded;
                        // Auto-correct old overlapping default coordinates if found
                        if (data.hudBtnOffsetX <= 300 && data.hudBtnOffsetY < 0) {
                            data.hudBtnOffsetX = 352;
                            data.hudBtnOffsetY = 4;
                        }
                    }
                }
            } else {
                save();
            }
        } catch (Throwable ignored) {
        }
    }

    public static void save() {
        try {
            File file = getConfigFile();
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(data, writer);
            }
        } catch (Throwable ignored) {
        }
    }

    public static void resetDefaults() {
        data.cobblebaseOffsetX = 0;
        data.cobblebaseOffsetY = -16;
        data.virtualLootOffsetX = 228;
        data.virtualLootOffsetY = -14;
        data.hudBtnOffsetX = 352;
        data.hudBtnOffsetY = 4;
        save();
    }
}
