package com.verdantartifice.thaumicwonders.common.init;

import java.util.HashSet;
import java.util.Set;

import com.verdantartifice.thaumicwonders.common.items.catalysts.ItemAlchemistStone;
import com.verdantartifice.thaumicwonders.common.items.entities.ItemFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.items.misc.ItemDisjunctionCloth;
import com.verdantartifice.thaumicwonders.common.items.misc.ItemTimewinder;
import com.verdantartifice.thaumicwonders.common.items.tools.ItemPrimalDestroyer;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class InitItems {
    public static final Set<Item> ITEMS = new HashSet<Item>();
    
    public static void initItems(IForgeRegistry<Item> forgeRegistry) {
        registerItem(forgeRegistry, new ItemDisjunctionCloth());
        registerItem(forgeRegistry, new ItemPrimalDestroyer());
        registerItem(forgeRegistry, new ItemFlyingCarpet());
        registerItem(forgeRegistry, new ItemTimewinder());
        registerItem(forgeRegistry, new ItemAlchemistStone());
    }
    
    private static void registerItem(IForgeRegistry<Item> forgeRegistry, Item item) {
        forgeRegistry.register(item);
        InitItems.ITEMS.add(item);
    }
}
