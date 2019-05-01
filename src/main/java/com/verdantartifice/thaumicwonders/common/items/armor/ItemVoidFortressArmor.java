package com.verdantartifice.thaumicwonders.common.items.armor;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.client.renderers.models.gear.ModelVoidFortressArmor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IWarpingGear;
import thaumcraft.api.items.ItemsTC;

public class ItemVoidFortressArmor extends ItemArmor implements IWarpingGear {
    public static ItemArmor.ArmorMaterial MATERIAL = EnumHelper.addArmorMaterial("VOID_FORTRESS", "VOID_FORTRESS", 50, new int[] { 4, 7, 9, 4 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F);

    protected ModelBiped model1 = null;
    protected ModelBiped model2 = null;
    protected ModelBiped model = null;

    public ItemVoidFortressArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        this.setRegistryName(ThaumicWonders.MODID, name);
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if (this.model1 == null) {
            this.model1 = new ModelVoidFortressArmor(1.0F);
        }
        if (this.model2 == null) {
            this.model2 = new ModelVoidFortressArmor(0.5F);
        }
        this.model = CustomArmorHelper.getCustomArmorModel(entityLiving, itemStack, armorSlot, this.model, this.model1, this.model2);
        return this.model;
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return ThaumicWonders.MODID + ":textures/entities/armor/void_fortress_armor.png";
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 1)) ? true : super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if (!world.isRemote && itemStack.getItemDamage() > 0 && player.ticksExisted % 20 == 0) {
            itemStack.damageItem(-1, player);
        }
    }

    @Override
    public int getWarp(ItemStack arg0, EntityPlayer arg1) {
        return 3;
    }
}
