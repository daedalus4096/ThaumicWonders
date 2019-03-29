package com.verdantartifice.thaumicwonders.common.crafting.recipes;

import javax.annotation.Nonnull;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.fluids.FluidQuicksilver;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.api.items.ItemsTC;

public class RecipeQuicksilverBucket extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public RecipeQuicksilverBucket() {
        super();
        setRegistryName(ThaumicWonders.MODID, "quicksilver_bucket");
    }
    
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundBucket = false;
        int quicksilverCount = 0;
        
        for (int index = 0; index < inv.getSizeInventory(); index++) {
            ItemStack stack = inv.getStackInSlot(index);
            if (!stack.isEmpty()) {
                if (stack.getItem() == Items.BUCKET && !foundBucket) {
                    foundBucket = true;
                } else if (stack.getItem() == ItemsTC.quicksilver) {
                    quicksilverCount++;
                } else {
                    // Invalid item, abort
                    return false;
                }
            }
        }
        
        return foundBucket && (quicksilverCount >= 8);
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000));
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width * height) >= 9;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000));
    }
}
