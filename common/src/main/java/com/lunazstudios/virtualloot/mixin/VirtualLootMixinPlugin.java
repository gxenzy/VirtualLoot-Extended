package com.lunazstudios.virtualloot.mixin;

import com.lunazstudios.virtualloot.integration.VirtualLootCompat;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class VirtualLootMixinPlugin implements IMixinConfigPlugin {
    private static final String COBBREEDING_PACKAGE = "com.lunazstudios.virtualloot.mixin.cobbreeding.";
    private static final String COBBLEBASE_PACKAGE = "com.lunazstudios.virtualloot.mixin.cobblebase.";

    public static boolean isFabricLoader() {
        try {
            Class.forName("net.fabricmc.loader.api.FabricLoader", false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static boolean isNeoForgeLoader() {
        try {
            Class.forName("net.neoforged.fml.loading.FMLLoader", false, Thread.currentThread().getContextClassLoader());
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith(COBBREEDING_PACKAGE)) {
            return VirtualLootCompat.isCobbreedingClassAvailable();
        }
        if (mixinClassName.startsWith(COBBLEBASE_PACKAGE)) {
            if (!VirtualLootCompat.isCobblebaseClassAvailable()) {
                return false;
            }
            if (mixinClassName.contains("FabricAdminScreenMixin")) {
                return isFabricLoader();
            }
            if (mixinClassName.contains("NeoForgeAdminScreenMixin")) {
                return isNeoForgeLoader();
            }
            return true;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
