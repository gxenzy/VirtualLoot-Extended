package com.lunazstudios.virtualloot.registry;

import com.lunazstudios.virtualloot.VirtualLoot;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public final class VirtualLootCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(VirtualLoot.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> VIRTUAL_LOOT_TAB = CREATIVE_TABS.register(
        "cobblemon_virtual_loot",
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.virtualloot"))
            .icon(() -> VirtualLootBlocks.VIRTUAL_PASTURE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> output.accept(VirtualLootBlocks.VIRTUAL_PASTURE_ITEM.get()))
            .build()
    );

    private VirtualLootCreativeTabs() {
    }

    public static void register() {
        CREATIVE_TABS.register();
    }
}
