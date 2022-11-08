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

package com.favouriteless.enchanted.api.rites;

import com.favouriteless.enchanted.api.curses.AbstractCurse;
import com.favouriteless.enchanted.common.init.EnchantedParticles;
import com.favouriteless.enchanted.common.init.EnchantedSoundEvents;
import com.favouriteless.enchanted.common.util.curse.CurseManager;
import com.favouriteless.enchanted.common.util.curse.CurseSavedData;
import com.favouriteless.enchanted.common.util.curse.CurseType;
import com.favouriteless.enchanted.common.util.rite.RiteType;
import net.minecraft.sounds.SoundSource;

import java.util.List;

/**
 * Simple AbstractRite implementation for creating a curse
 */
public abstract class AbstractRemoveCurseRite extends AbstractRite {

    public static final int RAISE = 300;
    public static final int START_SOUND = 190;
    private final CurseType<?> curseType;

    public AbstractRemoveCurseRite(RiteType<?> type, int power, int powerTick, CurseType<?> curseType) {
        super(type, power, powerTick);
        this.curseType = curseType;
    }

    @Override
    protected void execute() {
        if(targetUUID == null)
            cancel();
        else
            level.sendParticles(EnchantedParticles.REMOVE_CURSE_SEED.get(), pos.getX()+0.5D, pos.getY() + 2.5D, pos.getZ()+0.5D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    @Override
    protected void onTick() {
        if(ticks == START_SOUND) {
            level.playSound(null, pos, EnchantedSoundEvents.REMOVE_CURSE.get(), SoundSource.MASTER, 1.0F, 1.0F);
        }
        else if(ticks == RAISE) {
            List<AbstractCurse> curses = CurseSavedData.get(level).curses.get(targetUUID);
            if(curses != null) {
                for(AbstractCurse curse : curses) {
                    if(curse.getType() == curseType) {
                        CurseManager.removeCurse(level, curse);
                        break;
                    }
                }
            }
            stopExecuting();
        }
    }
}
