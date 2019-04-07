package com.verdantartifice.thaumicwonders.common.blocks.misc;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockHexamite extends BlockTNT {
    public BlockHexamite() {
        super();
        setRegistryName(ThaumicWonders.MODID, "hexamite");
        setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);        
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        // TODO Auto-generated method stub
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }
    
    @Override
    public void explode(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase igniter) {
        // TODO Auto-generated method stub
        super.explode(worldIn, pos, state, igniter);
    }
}
