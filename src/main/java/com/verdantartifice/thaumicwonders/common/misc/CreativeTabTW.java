package com.verdantartifice.thaumicwonders.common.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;

public class CreativeTabTW extends CreativeTabs {

    public CreativeTabTW(String label) {
        super(label);
    }

    public CreativeTabTW(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemsTC.thaumonomicon);
    }

}
