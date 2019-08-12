package com.verdantartifice.thaumicwonders.common.items.consumables;

import java.util.Iterator;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.items.base.IVariantItem;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import thaumcraft.common.lib.potions.PotionWarpWard;

public class ItemPanacea extends ItemFood implements IVariantItem {
    protected final String baseName;
    protected String[] variants;
    protected int[] variantsMeta;
    
    public ItemPanacea() {
        super(6, 1.8F, false);
        this.baseName = "panacea";
        this.setRegistryName(ThaumicWonders.MODID, this.baseName);
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        this.setAlwaysEdible();
        
        this.variants = new String[] { "normal", "enchanted" };
        this.setHasSubtypes(true);
        this.variantsMeta = new int[this.variants.length];
        for (int index = 0; index < this.variants.length; index++) {
            this.variantsMeta[index] = index;
        }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (this.getHasSubtypes() && stack.getMetadata() < this.variants.length && this.variants[stack.getMetadata()] != this.baseName) {
            return String.format(super.getUnlocalizedName() + ".%s", this.variants[stack.getMetadata()]);
        } else {
            return super.getUnlocalizedName(stack);
        }
    }
    
    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack) || stack.getMetadata() > 0;
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.getMetadata() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
    }
    
    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
            while (iterator.hasNext()) {
                PotionEffect effect = iterator.next();
                if (effect.getPotion().isBadEffect()) {
                    player.removePotionEffect(effect.getPotion());
                }
            }
            if (stack.getMetadata() > 0) {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
                player.addPotionEffect(new PotionEffect(PotionWarpWard.instance, 18000, 0, true, true));
            } else {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
            }
        }
    }
    
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == ThaumicWonders.CREATIVE_TAB || tab == CreativeTabs.SEARCH) {
            if (!this.getHasSubtypes()) {
                super.getSubItems(tab, items);
            } else {
                for (int meta : this.variantsMeta) {
                    items.add(new ItemStack(this, 1, meta));
                }
            }
        }
    }

    @Override
    public Item getItem() {
        return this;
    }
    
    @Override
    public String[] getVariantNames() {
        return this.variants;
    }
    
    @Override
    public int[] getVariantMeta() {
        return this.variantsMeta;
    }
    
    @Override
    public ModelResourceLocation getCustomModelResourceLocation(String variant) {
        if (this.baseName.equals(variant)) {
            return new ModelResourceLocation(ThaumicWonders.MODID + ":" + this.baseName);
        } else {
            return new ModelResourceLocation(ThaumicWonders.MODID + ":" + this.baseName, variant);
        }
    }
}
