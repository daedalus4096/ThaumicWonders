package com.verdantartifice.thaumicwonders.common.items.base;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.item.Item;

public class ItemTW extends Item {
    public ItemTW(String name) {
        super();
        setUnlocalizedName(name);
        setRegistryName(ThaumicWonders.MODID, name);
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);
    }
}
