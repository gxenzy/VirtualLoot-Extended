package com.lunazstudios.virtualloot.neoforge.client;

import com.lunazstudios.virtualloot.VirtualLoot;
import com.lunazstudios.virtualloot.client.cobblebase.AdminStatsOverlay;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = VirtualLoot.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class VirtualLootNeoForgeClient {

    @SubscribeEvent
    public static void onScreenRenderPost(ScreenEvent.Render.Post event) {
        if (event.getScreen().getClass().getName().contains("AdminScreen")) {
            AdminStatsOverlay.render(event.getGuiGraphics(), event.getScreen(), event.getMouseX(), event.getMouseY(), event.getPartialTick());
        }
    }

    @SubscribeEvent
    public static void onScreenClickPre(ScreenEvent.MouseButtonPressed.Pre event) {
        if (event.getScreen().getClass().getName().contains("AdminScreen")) {
            if (AdminStatsOverlay.mouseClicked(event.getScreen(), event.getMouseX(), event.getMouseY(), event.getButton())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onScreenScrollPre(ScreenEvent.MouseScrolled.Pre event) {
        if (event.getScreen().getClass().getName().contains("AdminScreen")) {
            if (AdminStatsOverlay.mouseScrolled(event.getScreen(), event.getMouseX(), event.getMouseY(), event.getScrollDeltaY())) {
                event.setCanceled(true);
            }
        }
    }

    @EventBusSubscriber(modid = VirtualLoot.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(VirtualLootBlocks.VIRTUAL_PASTURE.get(), RenderType.cutout()));
        }
    }
}
