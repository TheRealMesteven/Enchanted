/*
 * Copyright (c) 2021. Favouriteless
 * Enchanted, a minecraft mod.
 * GNU GPLv3 License
 *
 *     This file is part of Enchanted.
 *
 *     Enchanted is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Enchanted is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Enchanted.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.favouriteless.enchanted.common.rites;

import com.favouriteless.enchanted.api.rites.AbstractRite;
import com.favouriteless.enchanted.common.init.EnchantedBlocks;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.rites.util.CircleSize;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public class RiteOfTotalEclipseCharged extends AbstractRite {

    public RiteOfTotalEclipseCharged() {
        super(0, 0); // Power, power per tick
        CIRCLES_REQUIRED.put(CircleSize.SMALL, EnchantedBlocks.CHALK_WHITE.get());
        ITEMS_REQUIRED.put(Items.STONE_AXE, 1);
        ITEMS_REQUIRED.put(EnchantedItems.QUICKLIME.get(), 1);
        ITEMS_REQUIRED.put(EnchantedItems.ATTUNED_STONE_CHARGED.get(), 1);
    }

    @Override
    public void execute() {
        // Do ritual effects here
        if(!world.isClientSide) {
            ((ServerWorld) world).setDayTime(18000);
            world.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundCategory.MASTER, 1.0F, 1.0F);
        }
        stopExecuting();
    }

    @Override
    public void onTick() {
        // Do tick based ritual stuff here
    }

    @Override
    public RiteOfTotalEclipseCharged create() {
        return new RiteOfTotalEclipseCharged();
    }
}