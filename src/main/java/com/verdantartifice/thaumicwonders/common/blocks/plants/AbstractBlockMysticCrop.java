package com.verdantartifice.thaumicwonders.common.blocks.plants;

import java.util.Random;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractBlockMysticCrop extends BlockCrops {
    public AbstractBlockMysticCrop(String name) {
        super();
        this.setRegistryName(ThaumicWonders.MODID, name);
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
    }
    
    protected abstract IBlockState getMatureBlockState();
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (this.isMaxAge(worldIn.getBlockState(pos))) {
            worldIn.setBlockState(pos, this.getMatureBlockState(), 3);
        }
    }
    
    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        super.grow(worldIn, pos, state);
        if (this.isMaxAge(worldIn.getBlockState(pos))) {
            worldIn.setBlockState(pos, this.getMatureBlockState(), 3);
        }
    }
}
