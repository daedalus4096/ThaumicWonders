package com.verdantartifice.thaumicwonders.common.items.baubles;

import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.IRechargable;

public class ItemCleansingCharm extends ItemTW implements IBauble, IRechargable {
    protected static final int VIS_CAPACITY = 200;
    protected static final int ENERGY_PER_VIS = (20 * 60 * 60) / VIS_CAPACITY;

    public ItemCleansingCharm() {
        super("cleansing_charm");
        this.setMaxStackSize(1);
        this.setNoRepair();
    }
    
    @Override
    public int getMaxCharge(ItemStack stack, EntityLivingBase player) {
        return VIS_CAPACITY;
    }

    @Override
    public EnumChargeDisplay showInHud(ItemStack stack, EntityLivingBase player) {
        return IRechargable.EnumChargeDisplay.NORMAL;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.CHARM;
    }

}
