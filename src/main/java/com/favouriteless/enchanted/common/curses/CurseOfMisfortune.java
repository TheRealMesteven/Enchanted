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

package com.favouriteless.enchanted.common.curses;

import com.favouriteless.enchanted.api.curses.AbstractRandomCurse;
import com.favouriteless.enchanted.common.init.EnchantedCurseTypes;
import net.minecraft.network.chat.TextComponent;

public class CurseOfMisfortune extends AbstractRandomCurse {

	public CurseOfMisfortune() {
		super(EnchantedCurseTypes.MISFORTUNE.get(), 120, 300); // Executes once every 2-5 minutes
	}

	@Override
	protected void execute() {
		targetPlayer.displayClientMessage(new TextComponent("cursed" + ticks), false);
	}
}
