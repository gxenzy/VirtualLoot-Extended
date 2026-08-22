package com.lunazstudios.virtualloot.mixin.cobbreeding;

import com.lunazstudios.virtualloot.block.VirtualPastureBlock;
import ludichat.cobbreeding.CustomProperties;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VirtualPastureBlock.class)
public abstract class VirtualPastureBlockCobbreedingMixin extends BaseEntityBlock {
    protected VirtualPastureBlockCobbreedingMixin(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void virtualloot$initCobbreedingState(BlockBehaviour.Properties properties, CallbackInfo ci) {
        registerDefaultState(defaultBlockState()
            .setValue(CustomProperties.HAS_EGG, false)
            .setValue(CustomProperties.BREEDING_ACTIVATED, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    private void virtualloot$appendCobbreedingProperties(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(CustomProperties.HAS_EGG);
        builder.add(CustomProperties.BREEDING_ACTIVATED);
    }
}
