package com.lunazstudios.virtualloot.mixin;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.net.messages.client.pasture.OpenPasturePacket;
import com.cobblemon.mod.common.net.messages.client.pasture.PokemonPasturedPacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import com.lunazstudios.virtualloot.integration.VirtualLootCompat;
import com.lunazstudios.virtualloot.integration.cobbreeding.CobbreedingPastureInventoryBridge;
import com.lunazstudios.virtualloot.network.SyncVirtualPastureVisualPacket;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Mixin(value = PokemonPastureBlockEntity.class, priority = 1500)
public abstract class PokemonPastureBlockEntityMixin implements VirtualPastureInventory, WorldlyContainer {
    @Unique
    private static final int VIRTUALLOOT$SIZE = 27;
    @Unique
    private static final int[] VIRTUALLOOT$SLOTS = IntStream.range(0, VIRTUALLOOT$SIZE).toArray();
    @Unique
    private static final int[] VIRTUALLOOT$NO_SLOTS = new int[0];
    @Unique
    private final NonNullList<ItemStack> virtualloot$items = NonNullList.withSize(VIRTUALLOOT$SIZE, ItemStack.EMPTY);
    @Unique
    private long virtualloot$cobbreedingTime = -1L;
    @Unique
    private int virtualloot$cobbreedingRequiredTicks = -1;

    @Shadow(remap = false)
    public abstract List<PokemonPastureBlockEntity.Tethering> getTetheredPokemon();

    @Shadow(remap = false)
    public abstract int getMaxTethered();

    @Shadow(remap = false)
    public abstract BlockPos getMinRoamPos();

    @Shadow(remap = false)
    public abstract BlockPos getMaxRoamPos();

    @Override
    public NonNullList<ItemStack> virtualloot$getItems() {
        return virtualloot$items;
    }

    @Override
    public boolean virtualloot$insertGenerated(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        return virtualloot$insertGenerated(List.of(stack));
    }

    @Override
    public boolean virtualloot$insertGenerated(List<ItemStack> stacks) {
        if (stacks.isEmpty() || stacks.stream().allMatch(ItemStack::isEmpty)) {
            return false;
        }

        NonNullList<ItemStack> simulated = NonNullList.withSize(virtualloot$items.size(), ItemStack.EMPTY);
        for (int slot = 0; slot < virtualloot$items.size(); slot++) {
            simulated.set(slot, virtualloot$items.get(slot).copy());
        }

        for (ItemStack stack : stacks) {
            if (!stack.isEmpty() && !virtualloot$insertInto(simulated, stack.copy())) {
                return false;
            }
        }

        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                virtualloot$insertInto(virtualloot$items, stack.copy());
            }
        }
        ((PokemonPastureBlockEntity) (Object) this).setChanged();
        return true;
    }

    @Override
    public List<ItemStack> virtualloot$drainItems() {
        List<ItemStack> drained = new ArrayList<>();
        for (int slot = 0; slot < virtualloot$items.size(); slot++) {
            ItemStack stack = virtualloot$items.get(slot);
            if (!stack.isEmpty()) {
                drained.add(stack.copy());
                virtualloot$items.set(slot, ItemStack.EMPTY);
            }
        }
        if (!drained.isEmpty()) {
            ((PokemonPastureBlockEntity) (Object) this).setChanged();
        }
        return drained;
    }

    @Override
    public long virtualloot$getCobbreedingTime() {
        return virtualloot$cobbreedingTime;
    }

    @Override
    public void virtualloot$setCobbreedingTime(long time) {
        virtualloot$cobbreedingTime = time;
        ((PokemonPastureBlockEntity) (Object) this).setChanged();
    }

    @Override
    public int virtualloot$getCobbreedingRequiredTicks() {
        return virtualloot$cobbreedingRequiredTicks;
    }

    @Override
    public void virtualloot$setCobbreedingRequiredTicks(int ticks) {
        virtualloot$cobbreedingRequiredTicks = ticks;
        ((PokemonPastureBlockEntity) (Object) this).setChanged();
    }

    @Unique
    private boolean virtualloot$insertInto(NonNullList<ItemStack> items, ItemStack incoming) {
        for (int slot = 0; slot < items.size() && !incoming.isEmpty(); slot++) {
            ItemStack existing = items.get(slot);
            if (!existing.isEmpty() && ItemStack.isSameItemSameComponents(existing, incoming)) {
                int transferable = Math.min(incoming.getCount(), existing.getMaxStackSize() - existing.getCount());
                if (transferable > 0) {
                    existing.grow(transferable);
                    incoming.shrink(transferable);
                }
            }
        }

        for (int slot = 0; slot < items.size() && !incoming.isEmpty(); slot++) {
            if (items.get(slot).isEmpty()) {
                int transferable = Math.min(incoming.getCount(), incoming.getMaxStackSize());
                ItemStack inserted = incoming.copyWithCount(transferable);
                items.set(slot, inserted);
                incoming.shrink(transferable);
            }
        }

        return incoming.isEmpty();
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void virtualloot$saveInventory(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (virtualloot$isVirtualPasture()) {
            CompoundTag inventoryTag = new CompoundTag();
            ContainerHelper.saveAllItems(inventoryTag, virtualloot$items, registries);
            tag.put("VirtualLootInventory", inventoryTag);
            CompoundTag cobbreedingTag = new CompoundTag();
            cobbreedingTag.putLong("Time", virtualloot$cobbreedingTime);
            cobbreedingTag.putInt("RequiredTicks", virtualloot$cobbreedingRequiredTicks);
            tag.put("VirtualLootCobbreeding", cobbreedingTag);
        }
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    private void virtualloot$loadInventory(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        if (tag.contains("VirtualLootInventory", CompoundTag.TAG_COMPOUND)) {
            ContainerHelper.loadAllItems(tag.getCompound("VirtualLootInventory"), virtualloot$items, registries);
        }
        if (tag.contains("VirtualLootCobbreeding", CompoundTag.TAG_COMPOUND)) {
            CompoundTag cobbreedingTag = tag.getCompound("VirtualLootCobbreeding");
            virtualloot$cobbreedingTime = cobbreedingTag.getLong("Time");
            virtualloot$cobbreedingRequiredTicks = cobbreedingTag.getInt("RequiredTicks");
        }
    }

    @Unique
    private boolean virtualloot$isVirtualPasture() {
        return VirtualLootBlocks.isVirtualPastureBlock(((PokemonPastureBlockEntity) (Object) this).getBlockState().getBlock());
    }

    @Unique
    private NonNullList<ItemStack> virtualloot$getContainerItems() {
        if (virtualloot$isVirtualPasture() || !VirtualLootCompat.isCobbreedingLoaded()) {
            return virtualloot$items;
        }
        return CobbreedingPastureInventoryBridge.getItems((PokemonPastureBlockEntity) (Object) this);
    }

    @Override
    public int getContainerSize() {
        return virtualloot$getContainerItems().size();
    }

    @Override
    public boolean isEmpty() {
        return virtualloot$getContainerItems().stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return virtualloot$getContainerItems().get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(virtualloot$getContainerItems(), slot, amount);
        if (!stack.isEmpty()) {
            ((PokemonPastureBlockEntity) (Object) this).setChanged();
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(virtualloot$getContainerItems(), slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        virtualloot$getContainerItems().set(slot, stack);
        ((PokemonPastureBlockEntity) (Object) this).setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        NonNullList<ItemStack> items = virtualloot$getContainerItems();
        for (int slot = 0; slot < items.size(); slot++) {
            items.set(slot, ItemStack.EMPTY);
        }
        ((PokemonPastureBlockEntity) (Object) this).setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (virtualloot$isVirtualPasture()) {
            return side == Direction.DOWN ? VIRTUALLOOT$SLOTS : VIRTUALLOOT$NO_SLOTS;
        }
        return VirtualLootCompat.isCobbreedingLoaded() ? CobbreedingPastureInventoryBridge.getSlotsForFace() : VIRTUALLOOT$NO_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        if (virtualloot$isVirtualPasture()) {
            return direction == Direction.DOWN;
        }
        return VirtualLootCompat.isCobbreedingLoaded() && CobbreedingPastureInventoryBridge.canTakeItemThroughFace();
    }
}
