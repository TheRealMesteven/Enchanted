package com.favouriteless.enchanted.common.trades;

import com.favouriteless.enchanted.Enchanted;
import com.favouriteless.enchanted.common.init.EnchantedItems;
import com.favouriteless.enchanted.common.init.registry.EnchantedEntityTypes;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vazkii.patchouli.common.item.PatchouliItems;

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
                    4, 4, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.ROWAN_LOG.get(), 16),
                    4, 4, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.MUTANDIS.get(), 16),
                    6, 6, 0.11F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(EnchantedItems.ROWAN_BERRIES.get(), 10),
                    new ItemStack(Items.EMERALD, 1),
                    6, 4, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.ROWAN_SAPLING.get(), 1),
                    10, 4, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(Items.CANDLE, 1),
                    4, 3, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(EnchantedItems.GARLIC.get(), 8),
                    4, 2, 0.09F));
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(EnchantedItems.BELLADONNA_FLOWER.get(), 16),
                    new ItemStack(Items.EMERALD, 2),
                    6, 6, 0.09F));
            CompoundTag tag_getting_started = new CompoundTag();
            tag_getting_started.putString("patchouli:book", "enchanted:getting_started");
            ItemStack book_getting_started = new ItemStack(PatchouliItems.BOOK, 1);
            book_getting_started.setTag(tag_getting_started);
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    book_getting_started,
                    1, 6, 0.09F));
            // Level 2
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.BROOM.get(), 1),
                    3, 8, 0.09F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(EnchantedItems.WOOL_OF_BAT.get(), 1),
                    5, 9, 0.16F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.CREEPER_HEART.get(), 1),
                    5, 8, 0.16F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(EnchantedItems.TONGUE_OF_DOG.get(), 2),
                    4, 9, 0.12F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(EnchantedItems.POPPET.get(), 1),
                    new ItemStack(Items.EMERALD, 5),
                    3, 9, 0.09F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.GLINT_WEED.get(), 6),
                    4, 7, 0.12F));
            CompoundTag tag_brewing = new CompoundTag();
            tag_brewing.putString("patchouli:book", "enchanted:brewing");
            ItemStack book_brewing = new ItemStack(PatchouliItems.BOOK, 1);
            book_brewing.setTag(tag_brewing);
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    book_brewing,
                    1, 6, 0.09F));
            CompoundTag tag_extraction = new CompoundTag();
            tag_extraction.putString("patchouli:book", "enchanted:extraction");
            ItemStack book_extraction = new ItemStack(PatchouliItems.BOOK, 1);
            book_extraction.setTag(tag_extraction);
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    book_extraction,
                    1, 6, 0.09F));
            // Level 3
            /*trades.get(3).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 10),
                    new ItemStack(EnchantedItems.SPECTRAL_DUST.get(), 1),
                    2, 20, 0.20F));*/
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 4),
                    new ItemStack(EnchantedItems.MUTANDIS_EXTREMIS.get(), 20),
                    3, 12, 0.11F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(EnchantedItems.CHALK_WHITE.get(), 1),
                    4, 11, 0.11F));
            trades.get(2).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(EnchantedItems.WAYSTONE.get(), 6),
                    3, 11, 0.11F));
            CompoundTag tag_circle_magic = new CompoundTag();
            tag_circle_magic.putString("patchouli:book", "enchanted:circle_magic");
            ItemStack book_circle_magic = new ItemStack(PatchouliItems.BOOK, 1);
            book_circle_magic.setTag(tag_circle_magic);
            trades.get(1).add((trader, rand) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 1),
                    book_circle_magic,
                    1, 6, 0.09F));
        }
    }
}
