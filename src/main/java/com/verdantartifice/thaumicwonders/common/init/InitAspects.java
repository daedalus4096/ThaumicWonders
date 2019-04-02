package com.verdantartifice.thaumicwonders.common.init;

import com.verdantartifice.thaumicwonders.common.fluids.FluidQuicksilver;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.internal.CommonInternals;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;

public class InitAspects {
    public static void initAspects() {
        registerItemAspects();
    }
    
    private static void registerItemAspects() {
        appendAspects(FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000)), new AspectList().add(Aspect.DEATH, 15).add(Aspect.ALCHEMY, 15));
    }
    
    private static void appendAspects(ItemStack stack, AspectList toAdd) {
        toAdd = toAdd.copy();
        AspectList existing = ThaumcraftCraftingManager.getObjectTags(stack);
        if (existing != null) {
            toAdd = toAdd.add(existing);
        }
        CommonInternals.objectTags.put(CommonInternals.generateUniqueItemstackId(stack), toAdd);
    }
}
