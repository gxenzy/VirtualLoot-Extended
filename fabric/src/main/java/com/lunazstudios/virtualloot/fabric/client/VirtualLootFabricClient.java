package com.lunazstudios.virtualloot.fabric.client;

import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.RenderType;

public final class VirtualLootFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(VirtualLootBlocks.VIRTUAL_PASTURE.get(), RenderType.cutout());
    }
}
