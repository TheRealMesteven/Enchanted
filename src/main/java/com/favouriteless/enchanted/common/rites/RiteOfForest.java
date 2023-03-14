/*
 *
 *   Copyright (c) 2023. Favouriteless
 *   Enchanted, a minecraft mod.
 *   GNU GPLv3 License
 *
 *       This file is part of Enchanted.
 *
 *       Enchanted is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       Enchanted is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with Enchanted.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.favouriteless.enchanted.common.rites;

import com.favouriteless.enchanted.Enchanted;
import com.favouriteless.enchanted.EnchantedConfig;
import com.favouriteless.enchanted.api.rites.AbstractRite;
import com.favouriteless.enchanted.common.init.*;
import com.favouriteless.enchanted.common.util.rite.CirclePart;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class RiteOfForest extends AbstractRite {

    private final int treeCount = EnchantedConfig.FOREST_TREE_COUNT.get();
    private int treesPlaced = 0;
    private int tries = 0;
    private final List<BlockPos> usedPositions = new ArrayList<>();
    private SaplingBlock saplingBlock = null;

    public RiteOfForest() {
        super(EnchantedRiteTypes.FOREST.get(), 4000, 0); // Power, power per tick
        CIRCLES_REQUIRED.put(CirclePart.MEDIUM, EnchantedBlocks.CHALK_WHITE.get());
        ITEMS_REQUIRED.put(EnchantedItems.WICKER_BUNDLE.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.BREW_OF_SPROUTING.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.ENT_TWIG.get(), 1);
    }

    @Override
    public void execute() {
        List<Entity> entities = CirclePart.MEDIUM.getEntitiesInside(level, pos);
        for(Entity entity : entities) {
            if(entity instanceof ItemEntity itemEntity) {
                ItemStack stack = itemEntity.getItem();
                if(stack.getItem() instanceof BlockItem blockItem) {
                    if(blockItem.getBlock() instanceof SaplingBlock block) {
                        saplingBlock = block;
                        consumeItemNoRequirement(itemEntity);
                        break;
                    }
                }
            }
        }
        if(saplingBlock != null)
            level.playSound(null, pos, SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.5F, 1.0F);
        else
            cancel();
    }

    @Override
    public void onTick() {
        if(ticks % 20 == 0) {
            Vec3 randomPos = new Vec3(Enchanted.RANDOM.nextGaussian(), 0, Enchanted.RANDOM.nextGaussian()).normalize().scale(Math.cbrt(Math.random()) * EnchantedConfig.FOREST_RADIUS.get());
            int minHeight = Math.max(level.getMinBuildHeight(), pos.getY() - EnchantedConfig.FOREST_RADIUS.get()/2);
            int maxHeight = Math.min(level.getMaxBuildHeight(), pos.getY() + EnchantedConfig.FOREST_RADIUS.get()/2);

            for(int y = minHeight; y < maxHeight; y++) {
                BlockPos treePos = new BlockPos((int)Math.round(randomPos.x()) + pos.getX(), y, (int)Math.round(randomPos.z()) + pos.getZ());
                if(notNearUsed(treePos)) {
                    BlockState state = level.getBlockState(treePos);
                    if(state.isAir() || ForgeRegistries.BLOCKS.tags().getTag(EnchantedTags.Blocks.RITE_FOREST_REPLACEABLE).contains(state.getBlock())) {
                        if(saplingBlock.canSurvive(saplingBlock.defaultBlockState(), level, treePos)) {
                            if(saplingBlock.treeGrower.growTree(level, level.getChunkSource().getGenerator(), treePos, state, Enchanted.RANDOM)) {
                                treesPlaced++;
                                level.playSound(null, pos, SoundEvents.FUNGUS_PLACE, SoundSource.MASTER, 3.0F, 1.0F);
                                usedPositions.add(treePos);
                            }
                            break;
                        }
                    }
                }
            }
            level.sendParticles(EnchantedParticles.FERTILITY_SEED.get(), pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            tries++;
            if(treesPlaced == treeCount)
                stopExecuting();
            else if(tries >= treeCount*2.5D)
                stopExecuting();
        }
    }

    @Override
    protected CompoundTag saveAdditional(CompoundTag nbt) {
        ListTag usedPosTag = new ListTag();
        for(BlockPos pos : usedPositions) {
            usedPosTag.add(NbtUtils.writeBlockPos(pos));
        }
        nbt.put("usedPositions", usedPosTag);
        return nbt;
    }

    @Override
    protected void loadAdditional(CompoundTag nbt) {
        usedPositions.clear();
        ListTag usedPosTag = nbt.getList("usedPositions", 10);
        for(Tag tag : usedPosTag) {
            usedPositions.add(NbtUtils.readBlockPos((CompoundTag)tag));
        }
    }

    private boolean notNearUsed(BlockPos pos) {
        for(BlockPos usedPos : usedPositions) {
            if(usedPos.distToCenterSqr(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D) < 16)
                return false;
        }
        return true;
    }

}
