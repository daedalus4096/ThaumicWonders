package com.verdantartifice.thaumicwonders.common.misc;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreHelper {
    public static boolean isOreNamed(@Nonnull ItemStack stack, @Nonnull String name) {
        if (stack.isEmpty()) {
            return false;
        }
        int[] oreIds = OreDictionary.getOreIDs(stack);
        for (int id : oreIds) {
            if (OreDictionary.getOreName(id) == name) {
                return true;
            }
        }
        return false;
    }
}
