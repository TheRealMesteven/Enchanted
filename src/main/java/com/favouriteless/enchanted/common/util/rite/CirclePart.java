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

package com.favouriteless.enchanted.common.util.rite;


import com.favouriteless.enchanted.common.init.EnchantedBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public enum CirclePart {
    SMALL(
            "  XXX  " +
            " XOOOX " +
            "XOOOOOX" +
            "XOOOOOX" +
            "XOOOOOX" +
            " XOOOX " +
            "  XXX  "),
    MEDIUM(
            "   XXXXX   " +
            "  XOOOOOX  " +
            " XOOOOOOOX " +
            "XOOOOOOOOOX" +
            "XOOOOOOOOOX" +
            "XOOOOOOOOOX" +
            "XOOOOOOOOOX" +
            "XOOOOOOOOOX" +
            " XOOOOOOOX " +
            "  XOOOOOX  " +
            "   XXXXX   "),
    LARGE(
            "    XXXXXXX    " +
            "   XOOOOOOOX   " +
            "  XOOOOOOOOOX  " +
            " XOOOOOOOOOOOX " +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            "XOOOOOOOOOOOOOX" +
            " XOOOOOOOOOOOX " +
            "  XOOOOOOOOOX  " +
            "   XOOOOOOOX   " +
            "    XXXXXXX    ");

    private final List<BlockPos> circlePoints = new ArrayList<>();
    private final List<BlockPos> insideCirclePoints = new ArrayList<>();
    private final List<AABB> insideBoxes = new ArrayList<>();

    CirclePart(String shape) {
        int size = (int) Math.sqrt(shape.length());
        int offset = size /2;

        // Create edge positions
        for(int z = 0; z < size; z++) {
            for(int x = 0; x < size; x++) {
                char c = shape.charAt(x + (size * z));
                BlockPos pos = new BlockPos(x-offset, 0, z-offset);
                if(c == 'X') {
                    circlePoints.add(pos);
                    insideCirclePoints.add(pos);
                }
                else if(c == 'O') {
                    insideCirclePoints.add(pos);
                }
            }
        }

        // Create inside boxes
        for(int z = 0; z < size; z++) {
            for(int x = 0; x < size; x++) {
                int index = x + (size *z);
                if(shape.charAt(index) == 'X' || shape.charAt(index) == 'O') {
                    AABBBuilder builder = new AABBBuilder(shape, x, z, size);
                    shape = builder.build();
                    insideBoxes.add(builder.get());
                }
            }
        }
    }

    public boolean match(Level world, BlockPos centerPos, Block block) {
        for(BlockPos pos : circlePoints) {
            if(!world.getBlockState(centerPos.offset(pos)).is(block)) {
                return false;
            }
        }
        return true;
    }

    public void destroy(Level world, BlockPos centerPos) {
        for(BlockPos pos : circlePoints) {
            world.setBlockAndUpdate(centerPos.offset(pos), Blocks.AIR.defaultBlockState());
        }
    }

    public boolean canPlace(Level world, BlockPos centerPos) {
        for(BlockPos pos : circlePoints) {
            if(!world.getBlockState(centerPos.offset(pos)).isAir() || !EnchantedBlocks.CHALK_WHITE.get().canSurvive(null, world, centerPos.offset(pos))) { // Not air or chalk can't survive
                return false;
            }
        }
        return true;
    }

    public void place(Level world, BlockPos centerPos, Block block, UseOnContext context) {
        for(BlockPos pos : circlePoints) {
            world.setBlockAndUpdate(centerPos.offset(pos), block.getStateForPlacement(new BlockPlaceContext(context)));
        }
    }

    public List<Entity> getEntitiesInside(Level world, BlockPos centerPos, int height) {
        List<Entity> outlist = new ArrayList<>();

        for(AABB aabb : insideBoxes) {
            outlist.addAll(world.getEntities((Entity) null, aabb.move(centerPos).setMaxY(height), entity -> !outlist.contains(entity))); // Add all entities which aren't already added
        }

        return outlist;
    }

    public List<Entity> getEntitiesInside(Level world, BlockPos centerPos, int height, Predicate<Entity> predicate) {
        List<Entity> outlist = getEntitiesInside(world, centerPos, height);
        outlist.removeIf(entity -> !predicate.test(entity));
        return outlist;
    }

    public Entity getClosestEntity(Level world, BlockPos centerPos) {
        return closest(getEntitiesInside(world, centerPos, 1), centerPos);
    }

    public Entity closest(List<Entity> entities, BlockPos pos) {
        Vec3 center = new Vec3(pos.getX()+0.5D, 0.0D, pos.getZ()+0.5D);
        double closest = Double.MAX_VALUE;
        Entity entity = null;

        for(Entity newEntity : entities) {
            double newDistance = newEntity.distanceToSqr(center);
            if(newDistance < closest) {
                closest = newDistance;
                entity = newEntity;
            }
        }
        return entity;
    }

    public Entity getClosestEntity(Level world, BlockPos centerPos, Predicate<Entity> predicate) {
        List<Entity> entities = getEntitiesInside(world, centerPos, 1);
        entities.removeIf(entity -> !predicate.test(entity));

        return closest(entities, centerPos);
    }

    public List<BlockPos> getCirclePoints() {
        return circlePoints;
    }

    public List<BlockPos> getInsideCirclePoints() {
        return insideCirclePoints;
    }

    private static class AABBBuilder {
        private String shape;
        private final int xStart;
        private final int zStart;
        private final int size;

        private AABB aabb = null;

        private AABBBuilder(String shape, int xStart, int zStart, int size) {
            this.shape = shape;
            this.xStart = xStart;
            this.zStart = zStart;
            this.size = size;
        }

        private String build() {
            String row;
            int x = xStart;
            int z = zStart;
            int startIndex = x + (size*z);
            int offset = size/2;


            while(true) { // Grab entire "row" needed for shape
                int index = x + (size*z);
                char nextChar = shape.charAt(index+1);

                if(x+1 >= size || (nextChar != 'X' && nextChar != 'O')) { // End of row or next char not part of shape
                    row = shape.substring(startIndex, index+1);
                    break;
                }
                x++;
            }

            while(true) {
                int rowStartIndex = startIndex+size*(z-zStart+1);
                int rowEndIndex = rowStartIndex+row.length();

                if(z+1 >= size) { // End of string
                    aabb = new AABB(xStart - offset, 0, zStart - offset, x+1 - offset, 5, z+1 - offset);
                    removeShapeSub(xStart, zStart, x, z);
                    break;
                }
                else {
                    String newRow = shape.substring(rowStartIndex, rowEndIndex);

                    if(!rowEquals(row, newRow)) { // Not same quad
                        aabb = new AABB(xStart - offset, 0, zStart - offset, x+1 - offset, 5, z+1 - offset);
                        removeShapeSub(xStart, zStart, x, z);
                        break;
                    }
                }
                z++;
            }

            return shape;
        }

        private boolean rowEquals(String row, String newRow) {
            row = row.replace('O', 'X');
            newRow = newRow.replace('O', 'X');
            return row.equals(newRow);
        }

        private void removeShapeSub(int x, int y, int x2, int y2) {
            char[] shapeChars = shape.toCharArray();
            for(int a = x; a <= x2; a++) {
                for(int b = y; b <= y2; b++) {
                    shapeChars[a+size*b] = ' ';
                }
            }

            shape = String.valueOf(shapeChars);
        }

        private AABB get() {
            return aabb;
        }
    }
}
