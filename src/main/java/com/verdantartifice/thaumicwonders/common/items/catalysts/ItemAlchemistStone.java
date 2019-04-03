package com.verdantartifice.thaumicwonders.common.items.catalysts;

import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import net.minecraft.item.ItemStack;

public class ItemAlchemistStone extends ItemTW {
    public ItemAlchemistStone() {
        super("alchemist_stone");
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setNoRepair();
    }
    
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }
}
