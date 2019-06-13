package com.verdantartifice.thaumicwonders.common.items.tools;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemBoneBow extends ItemBow {
    public ItemBoneBow() {
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        this.setRegistryName(ThaumicWonders.MODID, "bone_bow");
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        this.setMaxStackSize(1);
        this.setMaxDamage(512);
        
        this.addPropertyOverride(new ResourceLocation(ThaumicWonders.MODID, "pull"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    return entityIn.getActiveItemStack().getItem() != ItemsTW.BONE_BOW ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
    }
    
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        // TODO Auto-generated method stub
//        player.stopActiveHand();
        super.onUsingTick(stack, player, count);
    }
}
