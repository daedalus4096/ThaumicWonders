package com.verdantartifice.thaumicwonders.common.blocks.base;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockTW extends Block {
    public BlockTW(Material material, String name) {
        super(material);
        setRegistryName(ThaumicWonders.MODID, name);
        setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        setResistance(2.0F);
        setHardness(1.5F);
    }
    
    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
