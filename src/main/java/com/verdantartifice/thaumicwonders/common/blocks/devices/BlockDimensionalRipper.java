package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.blocks.base.IBlockEnableable;
import com.verdantartifice.thaumicwonders.common.blocks.base.IBlockOrientable;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileDimensionalRipper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockDimensionalRipper extends BlockDeviceTW<TileDimensionalRipper> implements IBlockOrientable, IBlockEnableable {
    
    public BlockDimensionalRipper() {
        super(Material.IRON, TileDimensionalRipper.class, "dimensional_ripper");
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public boolean getEnableableDefault() {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        EnumFacing blockFacing = state.getValue(IBlockOrientable.FACING);
        if (blockFacing == face) {
            return BlockFaceShape.UNDEFINED;
        } else {
            return BlockFaceShape.SOLID;
        }
    }
}
