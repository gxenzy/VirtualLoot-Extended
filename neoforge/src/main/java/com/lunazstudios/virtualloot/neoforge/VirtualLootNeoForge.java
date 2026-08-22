package com.lunazstudios.virtualloot.neoforge;

import com.cobblemon.mod.neoforge.net.NeoForgePacketInfo;
import com.lunazstudios.virtualloot.VirtualLoot;
import com.lunazstudios.virtualloot.network.VirtualLootNetwork;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(VirtualLoot.MOD_ID)
public final class VirtualLootNeoForge {
    public VirtualLootNeoForge(IEventBus modBus) {
        VirtualLoot.init();
        modBus.addListener(this::registerPayloadHandlers);
    }

    private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(VirtualLoot.MOD_ID).versioned("1.0.0");
        new NeoForgePacketInfo<>(VirtualLootNetwork.TOGGLE_VIRTUAL_LOOT).registerToServer(registrar);
        new NeoForgePacketInfo<>(VirtualLootNetwork.SET_VISUAL_MODE).registerToServer(registrar);
        new NeoForgePacketInfo<>(VirtualLootNetwork.SYNC_VISUALS).registerToClient(registrar);
    }
}
