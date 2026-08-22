package com.lunazstudios.virtualloot.registry;

import com.lunazstudios.virtualloot.VirtualLoot;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public final class VirtualLootBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(VirtualLoot.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(VirtualLoot.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<VirtualPastureBlock> VIRTUAL_PASTURE = BLOCKS.register(
        "virtual_pasture",
        () -> new VirtualPastureBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .strength(2.0F)
            .noOcclusion())
    );

    public static final RegistrySupplier<BlockItem> VIRTUAL_PASTURE_ITEM = ITEMS.register(
        "virtual_pasture",
        () -> new BlockItem(VIRTUAL_PASTURE.get(), new Item.Properties())
    );

    private static final ResourceLocation VIRTUAL_PASTURE_ID = ResourceLocation.fromNamespaceAndPath(VirtualLoot.MOD_ID, "virtual_pasture");

    private VirtualLootBlocks() {
    }

    public static void register() {
        BLOCKS.register();
        ITEMS.register();
    }

    public static boolean isVirtualPastureBlock(Block block) {
        return VIRTUAL_PASTURE_ID.equals(BuiltInRegistries.BLOCK.getKey(block));
    }
}
