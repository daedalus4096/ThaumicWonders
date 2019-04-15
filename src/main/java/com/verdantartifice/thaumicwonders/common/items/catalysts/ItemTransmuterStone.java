package com.verdantartifice.thaumicwonders.common.items.catalysts;

import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;

public class ItemTransmuterStone extends ItemTW implements ICatalystStone {
    public ItemTransmuterStone() {
        super("transmuter_stone");
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getFluxChance() {
        return 33;
    }

    @Override
    public int getSparkleColor() {
        return Aspect.EXCHANGE.getColor();
    }
}
