package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.blocks.base.IBlockEnableable;
import com.verdantartifice.thaumicwonders.common.blocks.base.IBlockOrientable;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileDimensionalRipper;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDimensionalRipper extends BlockDeviceTW<TileDimensionalRipper> implements IBlockOrientable, IBlockEnableable {
    private static final int DISTANCE = 10;
    
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te != null && te instanceof TileDimensionalRipper) {
                TileDimensionalRipper tile = (TileDimensionalRipper)te;
                EnumFacing blockFacing = state.getValue(IBlockOrientable.FACING);
                boolean blockEnabled = state.getValue(IBlockEnableable.ENABLED);
                ThaumicWonders.LOGGER.info("This ripper: facing = {}, enabled = {}", blockFacing.getName2(), blockEnabled);
                BlockPos otherPos = pos.offset(blockFacing, DISTANCE);
                TileEntity otherTe = worldIn.getTileEntity(otherPos);
                if (otherTe != null && otherTe instanceof TileDimensionalRipper) {
                    TileDimensionalRipper otherTile = (TileDimensionalRipper)otherTe;
                    IBlockState otherState = worldIn.getBlockState(otherPos);
                    EnumFacing otherBlockFacing = otherState.getValue(IBlockOrientable.FACING);
                    boolean otherBlockEnabled = otherState.getValue(IBlockEnableable.ENABLED);
                    if (otherBlockFacing == blockFacing.getOpposite()) {
                        ThaumicWonders.LOGGER.info("Matching ripper found!");
                        if (blockEnabled && otherBlockEnabled) {
                            ThaumicWonders.LOGGER.info("Both rippers are enabled!");
                        } else {
                            ThaumicWonders.LOGGER.info("One or both rippers are disabled");
                        }
                    } else {
                        ThaumicWonders.LOGGER.info("Other ripper found, but facing wrong way {}, expected {}", otherBlockFacing.getName2(), blockFacing.getOpposite().getName2());
                    }
                } else {
                    ThaumicWonders.LOGGER.info("No other ripper found");
                }
            }
        }
        return true;
    }
    
}
