package com.verdantartifice.thaumicwonders.common.misc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreHelper {
    public static boolean isOreNamed(@Nonnull ItemStack stack, @Nonnull String name) {
        List<String> oreNames = OreHelper.getOreNames(stack);
        return oreNames.contains(name);
    }
    
    public static List<String> getOreNames(@Nonnull ItemStack stack) {
        List<String> names = new ArrayList<String>();
        if (stack.isEmpty()) {
            return names;
        }
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            names.add(OreDictionary.getOreName(id));
        }
        return names;
    }
    
    public static boolean isOreBlock(ItemStack stack) {
        for (String name : OreHelper.getOreNames(stack)) {
            if (name != null && name.toUpperCase().startsWith("ORE")) {
                return true;
            }
        }
        return false;
    }
}
