package com.verdantartifice.thaumicwonders.common.items.plants;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.BlocksTW;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class ItemCinderpearlSeed extends ItemSeeds {
    public ItemCinderpearlSeed() {
        super(BlocksTW.CINDERPEARL_CROP, Blocks.SAND);
        setRegistryName(ThaumicWonders.MODID, "cinderpearl_seed");
        setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Desert;
    }
}
