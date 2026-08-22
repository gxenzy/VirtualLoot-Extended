package com.lunazstudios.virtualloot;

import com.lunazstudios.virtualloot.integration.VirtualLootCompat;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import com.lunazstudios.virtualloot.registry.VirtualLootCreativeTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VirtualLoot {
    public static final String MOD_ID = "virtualloot";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private VirtualLoot() {
    }

    public static void init() {
        VirtualLootBlocks.register();
        VirtualLootCreativeTabs.register();
        VirtualLootCompat.init();
    }
}
