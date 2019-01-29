package com.verdantartifice.thaumicwonders.common.blocks;

import com.verdantartifice.thaumicwonders.common.config.ConfigItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockTW extends Block {
    public BlockTW(Material material, String name) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName("thaumicwonders", name);
        setCreativeTab(ConfigItems.TABTW);
        setResistance(2.0F);
        setHardness(1.5F);
    }
    
    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }
}
