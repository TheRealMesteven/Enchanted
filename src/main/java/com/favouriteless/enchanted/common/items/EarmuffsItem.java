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

package com.favouriteless.enchanted.common.items;

import com.favouriteless.enchanted.Enchanted;
import com.favouriteless.enchanted.client.render.entity.armor.EarmuffsModel;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import javax.annotation.Nullable;

@EventBusSubscriber(modid=Enchanted.MOD_ID, bus=Bus.FORGE)
public class EarmuffsItem extends ArmorItem {

	private static float previousGain = 1.0F;
	private static boolean isMuted = false;

	public EarmuffsItem(Properties pProperties) {
		super(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, pProperties);
	}

	@SubscribeEvent
	public static void clientTick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		PlayerEntity player = mc.player;

		if(player != null) {
			if(player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == EnchantedItems.EARMUFFS.get()) {
				if(!isMuted) {
					isMuted = true;
					SoundHandler soundHandler = Minecraft.getInstance().getSoundManager();
					previousGain = soundHandler.soundEngine.listener.getGain();
					soundHandler.soundEngine.listener.setGain(0.03F);
				}
			}
			else if(isMuted) {
				isMuted = false;
				SoundHandler soundHandler = Minecraft.getInstance().getSoundManager();
				soundHandler.soundEngine.listener.setGain(previousGain);
			}
		}
	}

	@Nullable
	@Override
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
		return (A)new EarmuffsModel();
	}

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return EarmuffsModel.TEXTURE.toString();
	}
}
