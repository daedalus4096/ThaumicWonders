package com.verdantartifice.thaumicwonders.common.blocks.base;

import java.util.ArrayList;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.common.lib.utils.BlockStateUtils;

public class BlockDeviceTW<T extends TileEntity> extends BlockTileTW<T> {
    public BlockDeviceTW(Material mat, Class<T> tileClass, String name) {
        super(mat, tileClass, name);
        
        IBlockState blockState = this.blockState.getBaseState();
        if (this instanceof IBlockOrientableHorizontal) {
            blockState.withProperty(IBlockOrientableHorizontal.FACING, EnumFacing.NORTH);
        } else if (this instanceof IBlockOrientable) {
            blockState.withProperty(IBlockOrientable.FACING, EnumFacing.UP);
        }
        if (this instanceof IBlockEnableable) {
            blockState.withProperty(IBlockEnableable.ENABLED, ((IBlockEnableable)this).getEnableableDefault());
        }
        this.setDefaultState(blockState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.updateState(worldIn, pos, state);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        this.updateState(worldIn, pos, state);
    }
    
    protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
        if (this instanceof IBlockEnableable) {
            boolean flag = !worldIn.isBlockPowered(pos);
            if (flag != state.getValue(IBlockEnableable.ENABLED)) {
                worldIn.setBlockState(pos, state.withProperty(IBlockEnableable.ENABLED, flag), 0x3);
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState state = this.getDefaultState();
        if (this instanceof IBlockOrientableHorizontal) {
            EnumFacing placerFacing = placer.getHorizontalFacing();
            state = state.withProperty(IBlockOrientableHorizontal.FACING, placer.isSneaking() ? placerFacing : placerFacing.getOpposite());
        } else if (this instanceof IBlockOrientable) {
            EnumFacing direction = EnumFacing.getDirectionFromEntityLiving(pos, placer);
            state = state.withProperty(IBlockOrientable.FACING, placer.isSneaking() ? direction.getOpposite() : direction);
        }
        if (this instanceof IBlockEnableable) {
            state = state.withProperty(IBlockEnableable.ENABLED, ((IBlockEnableable)this).getEnableableDefault());
        }
        return state;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        try {
            if (this instanceof IBlockOrientableHorizontal) {
                try {
                    state = state.withProperty(IBlockOrientableHorizontal.FACING, BlockStateUtils.getFacing(meta));
                } catch (Exception e) {
                    ThaumicWonders.LOGGER.error("Failed getting state from meta for {} with meta {}", this.toString(), meta);
                    throw e;
                }
            } else if (this instanceof IBlockOrientable) {
                state = state.withProperty(IBlockOrientable.FACING, BlockStateUtils.getFacing(meta));
            }
            if (this instanceof IBlockEnableable) {
                state = state.withProperty(IBlockEnableable.ENABLED, BlockStateUtils.isEnabled(meta));
            }
        } catch (Exception e) {
            ThaumicWonders.LOGGER.catching(e);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        if (this instanceof IBlockOrientableHorizontal) {
            meta = state.getValue(IBlockOrientableHorizontal.FACING).getIndex();
        } else if (this instanceof IBlockOrientable) {
            meta = state.getValue(IBlockOrientable.FACING).getIndex();
        }
        if (this instanceof IBlockEnableable && !state.getValue(IBlockEnableable.ENABLED)) {
            meta |= 0x8;
        }
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        ArrayList<IProperty<?>> properties = new ArrayList<>();
        if (this instanceof IBlockOrientableHorizontal) {
            properties.add(IBlockOrientableHorizontal.FACING);
        } else if (this instanceof IBlockOrientable) {
            properties.add(IBlockOrientable.FACING);
        }
        if (this instanceof IBlockEnableable) {
            properties.add(IBlockEnableable.ENABLED);
        }
        return properties.size() == 0 ?
            super.createBlockState() :
            new BlockStateContainer(this, properties.toArray(new IProperty<?>[properties.size()]));
    }
}
