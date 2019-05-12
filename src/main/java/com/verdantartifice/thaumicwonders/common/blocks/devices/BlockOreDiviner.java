package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileOreDiviner;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOreDiviner extends BlockDeviceTW<TileOreDiviner> {
    public BlockOreDiviner() {
        super(Material.GLASS, TileOreDiviner.class, "ore_diviner");
        this.setSoundType(SoundType.GLASS);
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack == null || stack.isEmpty()) {
            ThaumicWonders.LOGGER.info("Cancelling search");
        } else if (this.isOreBlock(stack) && stack.getItem() instanceof ItemBlock) {
            ThaumicWonders.LOGGER.info("Searching for {}", ((ItemBlock)stack.getItem()).getBlock().getRegistryName().toString());
        }
        return true;
    }
    
    protected boolean isOreBlock(ItemStack stack) {
        int[] oreIds = OreDictionary.getOreIDs(stack);
        if (oreIds != null && oreIds.length > 0) {
            for (int id : oreIds) {
                String name = OreDictionary.getOreName(id);
                if (name != null && name.toUpperCase().startsWith("ORE")) {
                    return true;
                }
            }
        }
        return false;
    }
}
