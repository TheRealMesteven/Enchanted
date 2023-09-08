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

package com.favouriteless.enchanted.common.rites.world;

import com.favouriteless.enchanted.EnchantedConfig;
import com.favouriteless.enchanted.api.rites.AbstractRite;
import com.favouriteless.enchanted.common.init.registry.EnchantedBlocks;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.init.registry.RiteTypes;
import com.favouriteless.enchanted.common.rites.CirclePart;
import com.favouriteless.enchanted.common.rites.RiteType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

public class RiteTotalEclipse extends AbstractRite {

    private static long LAST_USE_TIME = System.currentTimeMillis();

    protected RiteTotalEclipse(RiteType<?> type, int power, int powerTick) {
        super(type, power, powerTick);
    }

    public RiteTotalEclipse() {
        this(RiteTypes.TOTAL_ECLIPSE.get(), 3000, 0); // Power, power per tick
        CIRCLES_REQUIRED.put(CirclePart.SMALL, EnchantedBlocks.CHALK_WHITE.get());
        ITEMS_REQUIRED.put(Items.STONE_AXE, 1);
        ITEMS_REQUIRED.put(EnchantedItems.QUICKLIME.get(), 1);
    }

    @Override
    public void execute() {
        level.setDayTime(18000);
        level.playSound(null, pos, SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.MASTER, 0.5F, 1.0F);
        LAST_USE_TIME = System.currentTimeMillis();
        stopExecuting();
    }

    @Override
    protected boolean checkAdditional() {
        if(System.currentTimeMillis() > LAST_USE_TIME + EnchantedConfig.TOTAL_ECLIPSE_COOLDOWN.get() * 1000)
            return true;

        Player caster = level.getServer().getPlayerList().getPlayer(casterUUID);
        caster.displayClientMessage(new TextComponent("The moon is not ready to be called forth.").withStyle(ChatFormatting.RED), false);
        return false;
    }
}