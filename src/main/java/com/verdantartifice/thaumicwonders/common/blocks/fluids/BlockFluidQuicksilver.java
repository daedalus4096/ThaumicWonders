package com.verdantartifice.thaumicwonders.common.blocks.fluids;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.fluids.FluidQuicksilver;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockFluidQuicksilver extends BlockFluidClassic {
    public static final Material FLUID_QUICKSILVER_MATERIAL = new MaterialLiquid(MapColor.SILVER);
    
    public BlockFluidQuicksilver() {
        super(FluidQuicksilver.INSTANCE, FLUID_QUICKSILVER_MATERIAL);
        this.setRegistryName("fluid_quicksilver");
        this.setUnlocalizedName("fluid_quicksilver");
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
    }
    
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        // TODO Poison the entity for a few seconds
    }
}
