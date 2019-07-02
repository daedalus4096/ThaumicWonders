package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TilePrimordialAccelerator;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.blocks.IBlockFacingHorizontal;

public class BlockPrimordialAccelerator extends BlockDeviceTW<TilePrimordialAccelerator> implements IBlockEnabled, IBlockFacingHorizontal {
    public BlockPrimordialAccelerator() {
        super(Material.IRON, TilePrimordialAccelerator.class, "primordial_accelerator");
        this.setSoundType(SoundType.METAL);
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        EnumFacing blockFacing = state.getValue(IBlockFacingHorizontal.FACING);
        if (blockFacing.getOpposite() == face) {
            return BlockFaceShape.SOLID;
        } else {
            return BlockFaceShape.UNDEFINED;
        }
    }
}
