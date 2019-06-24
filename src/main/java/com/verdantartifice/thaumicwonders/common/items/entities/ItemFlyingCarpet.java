package com.verdantartifice.thaumicwonders.common.items.entities;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.items.IRechargable;
import thaumcraft.api.items.RechargeHelper;

public class ItemFlyingCarpet extends ItemTW implements IRechargable {
    public static final int CAPACITY = 240;
    
    public ItemFlyingCarpet() {
        super("flying_carpet");
        
        this.addPropertyOverride(new ResourceLocation(ThaumicWonders.MODID, "color"), new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                EnumDyeColor color = null;
                if (stack != null && stack.getItem() instanceof ItemFlyingCarpet) {
                    color = ((ItemFlyingCarpet)stack.getItem()).getDyeColor(stack);
                }
                if (color == null) {
                    // Default to red if no dye color is applied
                    color = EnumDyeColor.RED;
                }
                return ((float)color.getMetadata() / 16.0F);
            }
        });
    }
    
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote && world.getBlockState(pos).getBlock() != BlocksTC.rechargePedestal) {
            if (side != EnumFacing.UP) {
                return EnumActionResult.PASS;
            }
            double posX = (double)pos.getX() + (double)hitX;
            double posY = (double)pos.getY() + (double)hitY;
            double posZ = (double)pos.getZ() + (double)hitZ;
            EntityFlyingCarpet entityCarpet = new EntityFlyingCarpet(world, posX, posY, posZ);
            if (player.getHeldItem(hand).hasTagCompound()) {
                entityCarpet.setVisCharge(RechargeHelper.getCharge(player.getHeldItem(hand)));
                entityCarpet.setEnergy(player.getHeldItem(hand).getTagCompound().getInteger("energy"));
                entityCarpet.setDyeColor(this.getDyeColor(player.getHeldItem(hand)));
            }
            entityCarpet.rotationYaw = player.rotationYaw;
            world.spawnEntity(entityCarpet);
            world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.PASS;
        }
    }

    @Override
    public int getMaxCharge(ItemStack stack, EntityLivingBase player) {
        return CAPACITY;
    }

    @Override
    public IRechargable.EnumChargeDisplay showInHud(ItemStack stack, EntityLivingBase player) {
        return IRechargable.EnumChargeDisplay.NORMAL;
    }
    
    public EnumDyeColor getDyeColor(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null) {
            NBTTagCompound innerCompound = compound.getCompoundTag("display");
            if (innerCompound != null && innerCompound.hasKey("color")) {
                return EnumDyeColor.byMetadata(innerCompound.getInteger("color"));
            }
        }
        return null;
    }
    
    public void setDyeColor(ItemStack stack, EnumDyeColor color) {
        if (color == null) {
            return;
        }
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound compound = stack.getTagCompound();
        if (!compound.hasKey("display")) {
            compound.setTag("display", new NBTTagCompound());
        }
        compound.getCompoundTag("display").setInteger("color", color.getMetadata());
    }
    
    public void removeDyeColor(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null) {
            NBTTagCompound innerCompound = compound.getCompoundTag("display");
            if (innerCompound != null && innerCompound.hasKey("color")) {
                innerCompound.removeTag("color");
            }
        }
    }
}
