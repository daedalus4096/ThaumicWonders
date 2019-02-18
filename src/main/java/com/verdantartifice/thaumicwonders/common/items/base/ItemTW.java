package com.verdantartifice.thaumicwonders.common.items.base;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.item.Item;

public class ItemTW extends Item {
    public ItemTW(String name) {
        super();
        setRegistryName(ThaumicWonders.MODID, name);
        setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);
    }
}
