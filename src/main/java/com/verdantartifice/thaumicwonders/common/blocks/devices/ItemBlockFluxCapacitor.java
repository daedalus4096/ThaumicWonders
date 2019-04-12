package com.verdantartifice.thaumicwonders.common.blocks.devices;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFluxCapacitor extends ItemBlock {
    public ItemBlockFluxCapacitor(Block block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        int charge = stack.hasTagCompound() ? stack.getTagCompound().getInteger("charge") : 0;
        tooltip.add(TextFormatting.YELLOW + I18n.format("tile.thaumicwonders.flux_capacitor.tooltip.flux", charge));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
    
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        boolean success = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
        if (success && !world.isRemote) {
            int charge = stack.hasTagCompound() ? stack.getTagCompound().getInteger("charge") : 0;
            world.setBlockState(pos, newState.withProperty(BlockFluxCapacitor.CHARGE, Integer.valueOf(charge)));
        }
        return success;
    }
}
