package com.verdantartifice.thaumicwonders.common.crafting.recipes;

import javax.annotation.Nonnull;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.fluids.FluidQuicksilver;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.IForgeRegistryEntry;
import thaumcraft.api.items.ItemsTC;

public class RecipeQuicksilverBucketDeconstruct extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public RecipeQuicksilverBucketDeconstruct() {
        super();
        setRegistryName(ThaumicWonders.MODID, "quicksilver_bucket_deconstruct");
    }
    
    @Override
    public boolean matches(@Nonnull InventoryCrafting inv, @Nonnull World worldIn) {
        boolean foundBucket = false;
        ItemStack bucketStack = FluidUtil.getFilledBucket(new FluidStack(FluidQuicksilver.INSTANCE, 1000));
        
        for (int index = 0; index < inv.getSizeInventory(); index++) {
            ItemStack stack = inv.getStackInSlot(index);
            if (!stack.isEmpty()) {
                if (ItemStack.areItemsEqual(stack, bucketStack) && ItemStack.areItemStackTagsEqual(stack, bucketStack) && !foundBucket) {
                    foundBucket = true;
                } else {
                    // Invalid item, abort
                    return false;
                }
            }
        }

        return foundBucket;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
        return new ItemStack(ItemsTC.quicksilver, 8);
    }

    @Override
    public boolean canFit(int width, int height) {
        return (width * height) >= 1;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return new ItemStack(ItemsTC.quicksilver, 8);
    }
}
