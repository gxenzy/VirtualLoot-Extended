/*
 * Portions adapted from Cobblemon's PastureBlock.
 *
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.lunazstudios.virtualloot.block;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonBlockEntities;
import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.pasture.PastureLink;
import com.cobblemon.mod.common.api.pasture.PastureLinkManager;
import com.cobblemon.mod.common.api.pasture.PasturePermissionControllers;
import com.cobblemon.mod.common.block.PastureBlock;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.net.messages.client.pasture.OpenPasturePacket;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import com.cobblemon.mod.common.util.VectorShapeExtensionsKt;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.CobblemonEntities;
import net.minecraft.world.entity.Entity;
import com.lunazstudios.virtualloot.integration.VirtualPastureInventory;
import com.lunazstudios.virtualloot.integration.VirtualLootCompat;
import com.lunazstudios.virtualloot.integration.cobbreeding.CobbreedingCompat;
import com.lunazstudios.virtualloot.integration.cobbleworkers.CobbleworkersCompat;
import com.lunazstudios.virtualloot.integration.cobblebase.CobblebaseCompat;
import com.lunazstudios.virtualloot.loot.PastureLootGenerator;
import com.lunazstudios.virtualloot.registry.VirtualLootBlocks;
import com.lunazstudios.virtualloot.client.visual.PokemonSyncHelper;
import com.lunazstudios.virtualloot.network.SyncVirtualPastureVisualPacket;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.UUID;
import java.util.function.BiConsumer;

@SuppressWarnings("deprecation")
public final class VirtualPastureBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<VirtualPastureBlock> CODEC = simpleCodec(VirtualPastureBlock::new);
    public static final BooleanProperty LOOT_ENABLED = BooleanProperty.create("loot_enabled");
    public static final net.minecraft.world.level.block.state.properties.IntegerProperty VISUAL_MODE = net.minecraft.world.level.block.state.properties.IntegerProperty.create("visual_mode", 0, 3);
    private static final Component INVENTORY_TITLE = Component.translatable("container.virtualloot.virtual_pasture");
    private static final VoxelShape SOUTH_AABB_TOP = buildCollider(true, Direction.NORTH);
    private static final VoxelShape NORTH_AABB_TOP = buildCollider(true, Direction.SOUTH);
    private static final VoxelShape WEST_AABB_TOP = buildCollider(true, Direction.WEST);
    private static final VoxelShape EAST_AABB_TOP = buildCollider(true, Direction.EAST);
    private static final VoxelShape EAST_AABB_BOTTOM = buildCollider(false, Direction.EAST);
    private static final VoxelShape SOUTH_AABB_BOTTOM = VectorShapeExtensionsKt.rotateShape(Direction.EAST, Direction.SOUTH, EAST_AABB_BOTTOM);
    private static final VoxelShape NORTH_AABB_BOTTOM = VectorShapeExtensionsKt.rotateShape(Direction.EAST, Direction.NORTH, EAST_AABB_BOTTOM);
    private static final VoxelShape WEST_AABB_BOTTOM = VectorShapeExtensionsKt.rotateShape(Direction.EAST, Direction.WEST, EAST_AABB_BOTTOM);

    public VirtualPastureBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
            .setValue(PastureBlock.Companion.getPART(), PastureBlock.PasturePart.BOTTOM)
            .setValue(PastureBlock.Companion.getON(), false)
            .setValue(LOOT_ENABLED, true)
            .setValue(VISUAL_MODE, 0)
            .setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM
            ? new PokemonPastureBlockEntity(pos, state)
            : null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos above = context.getClickedPos().above();
        Level world = context.getLevel();
        if (!world.isOutsideBuildHeight(above) && world.getBlockState(above).canBeReplaced(context)) {
            return defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection())
                .setValue(PastureBlock.Companion.getPART(), PastureBlock.PasturePart.BOTTOM)
                .setValue(BlockStateProperties.WATERLOGGED, world.getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        }
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING);
        builder.add(PastureBlock.Companion.getPART());
        builder.add(PastureBlock.Companion.getON());
        builder.add(LOOT_ENABLED);
        builder.add(VISUAL_MODE);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    public BlockPos getBasePosition(BlockState state, BlockPos pos) {
        return state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM ? pos : pos.below();
    }

    public BlockPos getPositionOfOtherPart(BlockState state, BlockPos pos) {
        return state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM ? pos.above() : pos.below();
    }

    private void checkBreakEntity(LevelAccessor world, BlockState state, BlockPos pos) {
        if (state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PokemonPastureBlockEntity pasture) {
            dropBufferedLoot(world, pos, pasture);
            if (VirtualLootCompat.isCobbreedingLoaded()) {
                CobbreedingCompat.cleanup(world, pos);
            }
            pasture.onBroken();
        }
    }

    private void dropBufferedLoot(LevelAccessor world, BlockPos pos, PokemonPastureBlockEntity pasture) {
        if (!(world instanceof Level level) || level.isClientSide) {
            return;
        }
        for (ItemStack stack : ((VirtualPastureInventory) (Object) pasture).virtualloot$drainItems()) {
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, stack));
        }
        pasture.setChanged();
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        checkBreakEntity(world, state, pos);
        if (!world.isClientSide && player.isCreative()) {
            PastureBlock.PasturePart part = state.getValue(PastureBlock.Companion.getPART());
            if (part == PastureBlock.PasturePart.TOP) {
                BlockPos bottomPos = pos.below();
                BlockState bottomState = world.getBlockState(bottomPos);
                if (bottomState.is(this) && bottomState.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.BOTTOM) {
                    checkBreakEntity(world, bottomState, bottomPos);
                    BlockState replacement = bottomState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                    world.setBlock(bottomPos, replacement, UPDATE_ALL | UPDATE_SUPPRESS_DROPS);
                    world.levelEvent(player, 2001, bottomPos, Block.getId(bottomState));
                }
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected void onExplosionHit(BlockState state, Level level, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (explosion.getBlockInteraction() == Explosion.BlockInteraction.DESTROY || explosion.getBlockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
            checkBreakEntity(level, state, getBasePosition(state, pos));
        }
        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (type != CobblemonBlockEntities.PASTURE || state.getValue(PastureBlock.Companion.getPART()) != PastureBlock.PasturePart.BOTTOM) {
            return null;
        }
        return (level, pos, blockState, blockEntity) -> tickVirtualPasture(level, pos, blockState, (PokemonPastureBlockEntity) blockEntity);
    }

    private static void tickVirtualPasture(Level world, BlockPos pos, BlockState state, PokemonPastureBlockEntity pasture) {
        if (world.isClientSide) {
            return;
        }

        pasture.setTicksUntilCheck(pasture.getTicksUntilCheck() - 1);
        if (pasture.getTicksUntilCheck() <= 0) {
            pasture.checkPokemon();
        }

        pasture.getTetheredPokemon().stream()
            .map(PokemonPastureBlockEntity.Tethering::getPokemon)
            .filter(java.util.Objects::nonNull)
            .forEach(pokemon -> {
                if (pokemon.getCurrentFullness() > 0) {
                    pokemon.tickMetabolism(1);
                }
                if (!pokemon.getInteractionCooldowns().isEmpty()) {
                    pokemon.tickInteractionCooldown(1);
                }
            });

        if (isLootEnabled(state)) {
            if (VirtualLootCompat.isCobblebaseLoaded()) {
                CobblebaseCompat.tick(world, pos, pasture);
            }
            PastureLootGenerator.tick(world, pos, pasture);
            if (VirtualLootCompat.isCobbleworkersLoaded()) {
                CobbleworkersCompat.tick(world, pos, pasture);
            }
        }
        if (VirtualLootCompat.isCobbreedingLoaded()) {
            CobbreedingCompat.tick(world, pos, state, pasture);
        }

        int visualMode = getVisualMode(state);
        if (world instanceof ServerLevel serverLevel) {
            for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
                Pokemon pokemon = tethering.getPokemon();
                if (pokemon == null) continue;

                Entity currentEntity = serverLevel.getEntity(tethering.getEntityId());
                if (visualMode == 0) {
                    if (currentEntity != null && !currentEntity.isRemoved()) {
                        currentEntity.discard();
                        tethering.setEntityId(-1);
                    }
                } else {
                    if (currentEntity == null || currentEntity.isRemoved() || tethering.getEntityId() == -1) {
                        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
                        Direction directionToBehind = facing.getOpposite();
                        PokemonEntity entity = new PokemonEntity(serverLevel, pokemon, CobblemonEntities.POKEMON);
                        entity.setPokemon(pokemon);
                        entity.setBeamMode(2);

                        double spawnX = pos.getX() + 0.5 + directionToBehind.getStepX() * 1.5;
                        double spawnY = pos.getY();
                        double spawnZ = pos.getZ() + 0.5 + directionToBehind.getStepZ() * 1.5;
                        entity.setPos(spawnX, spawnY, spawnZ);

                        if (serverLevel.addFreshEntity(entity)) {
                            tethering.setEntityId(entity.getId());
                            entity.setTethering(tethering);
                            pasture.setChanged();
                        }
                    }
                }
            }

            if (serverLevel.getGameTime() % 20L == 0L) {
                List<Pokemon> pokemonList = new ArrayList<>();
                for (PokemonPastureBlockEntity.Tethering tethering : pasture.getTetheredPokemon()) {
                    Pokemon pokemon = tethering.getPokemon();
                    if (pokemon != null) {
                        pokemonList.add(pokemon);
                    }
                }
                new SyncVirtualPastureVisualPacket(pos, visualMode, pokemonList).sendToPlayersAround(serverLevel, pos, 48.0);
            }
        }

        setOnState(world, pos, state, hasLinkedViewer(world, pos));
    }

    private static boolean hasLinkedViewer(Level world, BlockPos pos) {
        double range = 5.0D;
        AABB box = new AABB(
            pos.getX() - range,
            pos.getY() - range,
            pos.getZ() - range,
            pos.getX() + 1.0D + range,
            pos.getY() + 1.0D + range,
            pos.getZ() + 1.0D + range
        );
        return !world.getEntitiesOfClass(
            ServerPlayer.class,
            box,
            player -> {
                PastureLink link = PastureLinkManager.getLinkByPlayer(player);
                ResourceLocation dimension = player.level().dimensionTypeRegistration().unwrapKey().orElseThrow().location();
                return link != null && link.getPos().equals(pos) && link.getDimension().equals(dimension);
            }
        ).isEmpty();
    }

    private static void setOnState(Level world, BlockPos pos, BlockState state, boolean on) {
        if (state.getValue(PastureBlock.Companion.getON()) == on) {
            return;
        }
        BlockPos topPos = pos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.is(VirtualLootBlocks.VIRTUAL_PASTURE.get())) {
            world.setBlockAndUpdate(topPos, topState.setValue(PastureBlock.Companion.getON(), on));
        }
        world.setBlockAndUpdate(pos, state.setValue(PastureBlock.Companion.getON(), on));
    }

    public static boolean isLootEnabled(BlockState state) {
        return state.hasProperty(LOOT_ENABLED) && state.getValue(LOOT_ENABLED);
    }

    public static void setLootEnabled(Level world, BlockPos pos, BlockState state, boolean enabled) {
        if (!state.hasProperty(LOOT_ENABLED) || state.getValue(PastureBlock.Companion.getPART()) != PastureBlock.PasturePart.BOTTOM) {
            return;
        }
        BlockState updated = state.setValue(LOOT_ENABLED, enabled);
        BlockPos topPos = pos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.is(VirtualLootBlocks.VIRTUAL_PASTURE.get()) && topState.hasProperty(LOOT_ENABLED)) {
            world.setBlockAndUpdate(topPos, topState.setValue(LOOT_ENABLED, enabled));
        }
        world.setBlockAndUpdate(pos, updated);
    }

    public static int getVisualMode(BlockState state) {
        return state.hasProperty(VISUAL_MODE) ? state.getValue(VISUAL_MODE) : 0;
    }

    public static void setVisualMode(Level world, BlockPos pos, BlockState state, int mode) {
        if (!state.hasProperty(VISUAL_MODE) || state.getValue(PastureBlock.Companion.getPART()) != PastureBlock.PasturePart.BOTTOM) {
            return;
        }
        int clamped = Math.max(0, Math.min(3, mode));
        BlockState updated = state.setValue(VISUAL_MODE, clamped);
        BlockPos topPos = pos.above();
        BlockState topState = world.getBlockState(topPos);
        if (topState.is(VirtualLootBlocks.VIRTUAL_PASTURE.get()) && topState.hasProperty(VISUAL_MODE)) {
            world.setBlockAndUpdate(topPos, topState.setValue(VISUAL_MODE, clamped));
        }
        world.setBlockAndUpdate(pos, updated);

        if (world instanceof ServerLevel serverLevel) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof PokemonPastureBlockEntity pasture) {
                pasture.setChanged();
                serverLevel.sendBlockUpdated(pos, state, updated, 3);
            }
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlock(
            pos.above(),
            state
                .setValue(PastureBlock.Companion.getPART(), PastureBlock.PasturePart.TOP)
                .setValue(LOOT_ENABLED, state.getValue(LOOT_ENABLED))
                .setValue(VISUAL_MODE, state.getValue(VISUAL_MODE))
                .setValue(BlockStateProperties.WATERLOGGED, world.getFluidState(pos.above()).getType() == Fluids.WATER),
            3
        );
        world.blockUpdated(pos, Blocks.AIR);
        state.updateNeighbourShapes(world, pos, 3);

        if (world instanceof ServerLevel && placer instanceof ServerPlayer player) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PokemonPastureBlockEntity pasture) {
                pasture.setOwnerId(player.getUUID());
                pasture.setOwnerName(player.getGameProfile().getName());
                pasture.setChanged();
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(player instanceof ServerPlayer serverPlayer) || PlayerExtensionsKt.isInBattle(serverPlayer)) {
            return InteractionResult.SUCCESS;
        }
        BlockPos basePos = getBasePosition(state, pos);
        BlockEntity blockEntity = world.getBlockEntity(basePos);
        if (player.isShiftKeyDown()) {
            if ((Object) blockEntity instanceof Container container) {
                serverPlayer.openMenu(new SimpleMenuProvider(
                    (containerId, inventory, menuPlayer) -> ChestMenu.threeRows(containerId, inventory, container),
                    INVENTORY_TITLE
                ));
                world.gameEvent(serverPlayer, GameEvent.BLOCK_OPEN, pos);
            }
            return InteractionResult.SUCCESS;
        }

        BlockEntity topEntity = world.getBlockEntity(basePos.above());
        if (topEntity != null) {
            topEntity.setRemoved();
        }

        if (!(blockEntity instanceof PokemonPastureBlockEntity pasture)) {
            return InteractionResult.SUCCESS;
        }

        UUID pcId = Cobblemon.INSTANCE.getStorage().getPC(serverPlayer).getUuid();
        UUID linkId = UUID.randomUUID();
        var permissions = PasturePermissionControllers.permit(serverPlayer, pasture);
        CobblemonNetwork.INSTANCE.sendPacketToPlayer(
            serverPlayer,
            new OpenPasturePacket(
                pcId,
                linkId,
                pasture.getMaxTethered(),
                pasture.getTetheredPokemon().stream().map(tethering -> tethering.toDTO(serverPlayer)).filter(java.util.Objects::nonNull).toList(),
                permissions
            )
        );

        PastureLinkManager.createLink(
            serverPlayer.getUUID(),
            new PastureLink(linkId, pcId, world.dimensionTypeRegistration().unwrapKey().orElseThrow().location(), basePos, permissions)
        );

        int visualMode = getVisualMode(state);
        List<Pokemon> pokemonList = new ArrayList<>();
        for (PokemonPastureBlockEntity.Tethering t : pasture.getTetheredPokemon()) {
            Pokemon pkmn = t.getPokemon();
            if (pkmn != null) {
                pokemonList.add(pkmn);
            }
        }
        new SyncVirtualPastureVisualPacket(basePos, visualMode, pokemonList).sendToPlayer(serverPlayer);

        world.playSound(null, pos, CobblemonSounds.PC_ON, SoundSource.BLOCKS, 0.5F, 1.0F);
        world.gameEvent(serverPlayer, GameEvent.BLOCK_OPEN, pos);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean top = state.getValue(PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP;
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        if (top) {
            return switch (facing) {
                case SOUTH -> SOUTH_AABB_TOP;
                case WEST -> WEST_AABB_TOP;
                case EAST -> EAST_AABB_TOP;
                default -> NORTH_AABB_TOP;
            };
        }
        return switch (facing) {
            case SOUTH -> SOUTH_AABB_BOTTOM;
            case WEST -> WEST_AABB_BOTTOM;
            case EAST -> EAST_AABB_BOTTOM;
            default -> NORTH_AABB_BOTTOM;
        };
    }

    private static VoxelShape buildCollider(boolean top, Direction direction) {
        if (top) {
            return Shapes.or(
                voxelShape(0.1875, 0.0, 0.0625, 0.8125, 0.0625, 0.3125, direction),
                voxelShape(0.125, 0.0, 0.375, 0.875, 0.1875, 0.9375, direction),
                voxelShape(0.1875, 0.1875, 0.4375, 0.8125, 0.6875, 0.9375, direction),
                voxelShape(0.8125, 0.1875, 0.375, 0.875, 0.6875, 0.9375, direction),
                voxelShape(0.125, 0.1875, 0.375, 0.1875, 0.6875, 0.9375, direction),
                voxelShape(0.125, 0.6875, 0.375, 0.875, 0.75, 0.9375, direction)
            );
        }
        return Shapes.or(
            voxelShape(0.875, 0.0, 0.0, 1.0, 1.0, 0.125, direction),
            voxelShape(0.125, 0.0, 0.0, 0.875, 0.125, 0.125, direction),
            voxelShape(0.125, 0.875, 0.0, 0.875, 1.0, 0.125, direction),
            voxelShape(0.125, 0.125, 0.0625, 0.875, 0.875, 0.125, direction),
            voxelShape(0.0625, 0.125, 0.125, 0.125, 0.875, 0.875, direction),
            voxelShape(0.0, 0.0, 0.0, 0.125, 1.0, 0.125, direction),
            voxelShape(0.125, 0.0, 0.125, 0.875, 0.125, 1.0, direction),
            voxelShape(0.875, 0.125, 0.125, 0.9375, 0.875, 0.875, direction),
            voxelShape(0.125, 0.875, 0.125, 0.875, 1.0, 1.0, direction),
            voxelShape(0.875, 0.0, 0.875, 1.0, 1.0, 1.0, direction),
            voxelShape(0.875, 0.0, 0.125, 1.0, 0.125, 0.875, direction),
            voxelShape(0.875, 0.875, 0.125, 1.0, 1.0, 0.875, direction),
            voxelShape(0.0, 0.875, 0.125, 0.125, 1.0, 0.875, direction),
            voxelShape(0.0, 0.0, 0.125, 0.125, 0.125, 0.875, direction),
            voxelShape(0.0, 0.0, 0.875, 0.125, 1.0, 1.0, direction),
            voxelShape(0.0, 0.125, 0.375, 0.0625, 0.875, 0.625, direction),
            voxelShape(0.9375, 0.125, 0.375, 1.0, 0.875, 0.625, direction),
            voxelShape(0.1875, 0.1875, 0.05625, 0.8125, 0.75, 0.05625, direction),
            voxelShape(0.1875, 0.125, 0.3125, 0.8125, 0.3125, 0.875, direction),
            voxelShape(0.1875, 0.125, 0.3125, 0.8125, 0.3125, 0.875, direction),
            voxelShape(0.1875, 0.0625, 0.875, 0.8125, 0.25, 0.875, direction),
            voxelShape(0.1875, 0.25, 0.25, 0.1875, 0.4375, 0.875, direction),
            voxelShape(0.8125, 0.25, 0.25, 0.8125, 0.4375, 0.875, direction),
            voxelShape(0.1875, 0.3125, 0.3125, 0.8125, 0.5, 0.3125, direction),
            voxelShape(0.25, 0.75, 0.3125, 0.75, 1.0, 0.8125, direction),
            voxelShape(0.0, 0.0, 0.0, 0.0625, 1.0, 1.0, direction),
            voxelShape(0.9375, 0.0, 0.0, 1.0, 1.0, 1.0, direction)
        );
    }

    private static VoxelShape voxelShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Direction direction) {
        return VectorShapeExtensionsKt.voxelShape(minX, minY, minZ, maxX, maxY, maxZ, direction);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        boolean isPasture = neighborState.is(this);
        PastureBlock.PasturePart part = state.getValue(PastureBlock.Companion.getPART());
        if (!isPasture && part == PastureBlock.PasturePart.TOP && neighborPos.equals(pos.below())) {
            return Blocks.AIR.defaultBlockState();
        }
        if (!isPasture && part == PastureBlock.PasturePart.BOTTOM && neighborPos.equals(pos.above())) {
            checkBreakEntity(world, state, pos);
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }
}
