package com.verdantartifice.thaumicwonders.common.blocks.essentia;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.essentia.BlockJarItem;

public class ItemBlockCreativeEssentiaJar extends BlockJarItem {
    public ItemBlockCreativeEssentiaJar(Block block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.DARK_PURPLE + "Creative only");
        tooltip.add("Returns infinite amount of");
        tooltip.add("contained essentia type.");
        tooltip.add("Empty by shift-right-clicking");
        tooltip.add("with an empty hand.");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
