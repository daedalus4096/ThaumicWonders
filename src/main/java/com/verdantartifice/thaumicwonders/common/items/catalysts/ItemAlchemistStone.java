package com.verdantartifice.thaumicwonders.common.items.catalysts;

import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemAlchemistStone extends ItemTW implements ICatalystStone {
    public ItemAlchemistStone() {
        super("alchemist_stone");
        this.setMaxDamage(63);  // Gets one last use at durability 0
        this.setMaxStackSize(1);
        this.setNoRepair();
    }
    
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public ItemStack getRefiningResult(ItemStack input) {
        // TODO Real mapping
        return new ItemStack(Blocks.DIAMOND_BLOCK);
    }
}
