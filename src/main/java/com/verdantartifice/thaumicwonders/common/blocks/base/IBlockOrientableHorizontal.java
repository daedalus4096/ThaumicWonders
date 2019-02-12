package com.verdantartifice.thaumicwonders.common.blocks.base;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

public interface IBlockOrientableHorizontal {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
}
