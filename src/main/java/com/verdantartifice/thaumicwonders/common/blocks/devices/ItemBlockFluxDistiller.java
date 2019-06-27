package com.verdantartifice.thaumicwonders.common.blocks.devices;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockFluxDistiller extends ItemBlock {
    public ItemBlockFluxDistiller(Block block) {
        super(block);
        this.addPropertyOverride(new ResourceLocation(ThaumicWonders.MODID, "flux"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return stack.hasTagCompound() ? (float)stack.getTagCompound().getInteger("charge") : 0.0F;
            }
        });
    }

}
