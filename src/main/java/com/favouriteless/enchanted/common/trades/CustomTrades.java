package com.favouriteless.enchanted.common.trades;

import com.favouriteless.enchanted.Enchanted;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.init.registry.EnchantedEntityTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Enchanted.MOD_ID)
public class CustomTrades {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == EnchantedEntityTypes.APOTHECARY.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.CLAY_JAR_SOFT.get(), 12),
                    4, 12, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.ROWAN_LOG.get(), 16),
                    4, 12, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.MUTANDIS.get(), 16),
                    6, 12, 0.11F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(EnchantedItems.ROWAN_BERRIES.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    6, 12, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.ROWAN_SAPLING.get(), 1),
                    10, 12, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(Items.CANDLE, 1),
                    4, 12, 0.09F));
            // Level 2
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.BROOM.get(), 1),
                    3, 14, 0.09F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(EnchantedItems.WOOL_OF_BAT.get(), 1),
                    5, 16, 0.16F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.CREEPER_HEART.get(), 1),
                    5, 16, 0.16F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(EnchantedItems.TONGUE_OF_DOG.get(), 2),
                    4, 14, 0.12F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(EnchantedItems.POPPET.get(), 1),
                    new ItemStack(Items.EMERALD, 3),
                    3, 12, 0.09F));
            // Level 3
            /*trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(EnchantedItems.SPECTRAL_DUST.get(), 1),
                    2, 20, 0.20F));*/
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(EnchantedItems.ENT_TWIG.get(), 1),
                    3, 14, 0.11F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.CHALK_WHITE.get(), 1),
                    4, 14, 0.11F));
        }
    }
}
