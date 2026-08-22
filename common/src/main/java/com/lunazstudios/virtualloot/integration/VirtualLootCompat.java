package com.lunazstudios.virtualloot.integration;

import com.lunazstudios.virtualloot.VirtualLoot;
import dev.architectury.platform.Platform;

public final class VirtualLootCompat {
    public static final String COBBREEDING = "cobbreeding";
    public static final String COBBLEWORKERS = "cobbleworkers";
    public static final String COBBLEBASE = "cobblebase";
    private static boolean cobbreedingLoaded;
    private static boolean cobbleworkersLoaded;
    private static boolean cobblebaseLoaded;

    private VirtualLootCompat() {
    }

    public static void init() {
        cobbreedingLoaded = isCobbreedingLoaded();
        if (cobbreedingLoaded) {
            VirtualLoot.LOGGER.info("Cobbreeding installed, making compatibility chances");
        } else {
            VirtualLoot.LOGGER.info("Cobbreeding not installed, not making compatibility chances");
        }

        cobbleworkersLoaded = isCobbleworkersLoaded();
        if (cobbleworkersLoaded) {
            VirtualLoot.LOGGER.info("Cobbleworkers installed, making compatibility chances");
        } else {
            VirtualLoot.LOGGER.info("Cobbleworkers not installed, not making compatibility chances");
        }

        cobblebaseLoaded = isCobblebaseLoaded();
        if (cobblebaseLoaded) {
            VirtualLoot.LOGGER.info("Cobblebase installed, making compatibility chances");
        } else {
            VirtualLoot.LOGGER.info("Cobblebase not installed, not making compatibility chances");
        }
    }

    public static boolean isCobbreedingLoaded() {
        if (cobbreedingLoaded) {
            return true;
        }
        try {
            return Platform.isModLoaded(COBBREEDING);
        } catch (RuntimeException ignored) {
            return isCobbreedingClassAvailable();
        }
    }

    public static boolean isCobbreedingClassAvailable() {
        try {
            Class.forName("ludichat.cobbreeding.Cobbreeding", false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    public static boolean isCobbleworkersLoaded() {
        if (cobbleworkersLoaded) {
            return true;
        }
        try {
            return Platform.isModLoaded(COBBLEWORKERS);
        } catch (RuntimeException ignored) {
            return isCobbleworkersClassAvailable();
        }
    }

    public static boolean isCobbleworkersClassAvailable() {
        try {
            Class.forName("accieo.cobbleworkers.Cobbleworkers", false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    public static boolean isCobblebaseLoaded() {
        if (cobblebaseLoaded) {
            return true;
        }
        try {
            return Platform.isModLoaded(COBBLEBASE);
        } catch (RuntimeException ignored) {
            return isCobblebaseClassAvailable();
        }
    }

    public static boolean isCobblebaseClassAvailable() {
        try {
            Class.forName("notlown.cobblebase.core.Cobblebase", false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }
}
