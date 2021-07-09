package com.favouriteless.enchanted.common.items;

import com.favouriteless.enchanted.common.blocks.chalk.ChalkBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.Objects;

public class ChalkItem extends Item {

    private final ChalkBlockBase chalkBlock;

    public ChalkItem(Properties builder, Block block) {
        super(builder);
        if(block instanceof ChalkBlockBase) {
            chalkBlock = (ChalkBlockBase) block;
        } else {
            chalkBlock = null;
        }
    }

    @Override
    public ActionResultType useOn(ItemUseContext context)
    {
        if(context.getClickedFace() == Direction.UP) {
            BlockPos targetLocation = context.getClickedPos().above();

            if (context.getLevel().getBlockState(targetLocation).is(Blocks.AIR)) {
                if (chalkBlock.canSurvive(chalkBlock.defaultBlockState(),context.getLevel(), targetLocation)) {

                    if(!context.getLevel().isClientSide()) {
                        context.getLevel().setBlock(context.getClickedPos().offset(0, 1, 0), Objects.requireNonNull(chalkBlock.getStateForPlacement(new BlockItemUseContext(context))), Constants.BlockFlags.DEFAULT);
                    }
                    context.getLevel().playSound(context.getPlayer(), context.getClickedPos().offset(0, 1, 0), SoundEvents.STONE_PLACE, SoundCategory.BLOCKS, 1f, 1f);
                    Objects.requireNonNull(context.getPlayer()).getItemInHand(context.getHand()).hurtAndBreak(1, context.getPlayer(), (p_220038_0_) -> { });

                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.FAIL;
    }

}