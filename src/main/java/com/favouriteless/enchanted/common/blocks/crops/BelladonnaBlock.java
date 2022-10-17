/*
 *
 *   Copyright (c) 2022. Favouriteless
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

package com.favouriteless.enchanted.common.blocks.crops;

import com.favouriteless.enchanted.common.init.EnchantedItems;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.ItemLike;

public class BelladonnaBlock extends CropsBlockAgeFive {

    public BelladonnaBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected ItemLike getBaseSeedId() {
        return EnchantedItems.BELLADONNA_SEEDS.get();
    }

}
