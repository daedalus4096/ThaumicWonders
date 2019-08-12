package com.verdantartifice.thaumicwonders.common.items.consumables;

import java.util.Iterator;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPanacea extends ItemFood {
    public ItemPanacea() {
        super(6, 1.8F, false);
        this.setRegistryName(ThaumicWonders.MODID, "panacea");
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        this.setAlwaysEdible();
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
    
    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        if (!worldIn.isRemote) {
            Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
            while (iterator.hasNext()) {
                PotionEffect effect = iterator.next();
                if (!effect.getPotion().isBeneficial()) {
                    player.removePotionEffect(effect.getPotion());
                }
            }
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 0));
        }
    }
}
