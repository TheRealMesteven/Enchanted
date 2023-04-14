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

package com.favouriteless.enchanted.common.menus;

import com.favouriteless.enchanted.common.blockentities.DistilleryBlockEntity;
import com.favouriteless.enchanted.common.init.EnchantedBlocks;
import com.favouriteless.enchanted.common.init.EnchantedContainers;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.blockentities.InventoryBlockEntityBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class DistilleryMenu extends ProcessingMenuBase {

    public DistilleryMenu(final int windowId, final Inventory playerInventory, final InventoryBlockEntityBase blockEntity, final ContainerData data) {
        super(EnchantedContainers.DISTILLERY.get(), windowId, blockEntity, ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), EnchantedBlocks.DISTILLERY.get(), data);

        // SpinningWheelBlockEntity Inventory
        addSlot(new SlotJarInput(blockEntity, 0, 32, 35)); // Jar input
        addSlot(new SlotInput(blockEntity, 1, 54, 25)); // Ingredient input
        addSlot(new SlotInput(blockEntity, 2, 54, 45)); // Ingredient input
        addSlot(new SlotOutput(blockEntity, 3, 127, 7)); // Distillery output
        addSlot(new SlotOutput(blockEntity, 4, 127, 26)); // Distillery output
        addSlot(new SlotOutput(blockEntity, 5, 127, 45)); // Distillery output
        addSlot(new SlotOutput(blockEntity, 6, 127, 64)); // Distillery output

        addInventorySlots(playerInventory, 8, 84);
    }

    public DistilleryMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, (InventoryBlockEntityBase) getBlockEntity(playerInventory, data, DistilleryBlockEntity.class), new SimpleContainerData(3));
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {

            ItemStack slotItem = slot.getItem();
            itemstack = slotItem.copy();

            if (index <= 6) { // If container slot
                if (!this.moveItemStackTo(slotItem, 7, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else { // If not a container slot
                if (itemstack.getItem() == EnchantedItems.CLAY_JAR.get()) { // Item is clay jar
                    if (!this.moveItemStackTo(slotItem, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(slotItem, 1, 3, false)) { // Item is in player inventory, attempt to fit
                    return ItemStack.EMPTY;
                } else if (index < 35) { // Item is in main player inventory and cannot fit
                    if (!this.moveItemStackTo(slotItem, 34, 43, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 43) { // Item is in player hotbar and cannot fit
                    if(!this.moveItemStackTo(slotItem, 7, 34, false)) {
                        return ItemStack.EMPTY;
                    }
                }

                if (slotItem.isEmpty()) {
                    slot.set(ItemStack.EMPTY);
                } else {
                    slot.setChanged();
                }

                if (slotItem.getCount() == itemstack.getCount()) {
                    return ItemStack.EMPTY;
                }

                slot.onTake(playerIn, slotItem);
            }
        }
        return super.quickMoveStack(playerIn, index);
    }

}