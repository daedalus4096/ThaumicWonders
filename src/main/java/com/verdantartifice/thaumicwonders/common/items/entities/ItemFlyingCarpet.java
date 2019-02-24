package com.verdantartifice.thaumicwonders.common.items.entities;

import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlyingCarpet extends ItemTW {
    public ItemFlyingCarpet() {
        super("flying_carpet");
    }
    
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote) {
            EntityFlyingCarpet entityCarpet = new EntityFlyingCarpet(world, pos.offset(side, 1));
            entityCarpet.rotationYaw = player.rotationYaw;
            world.spawnEntity(entityCarpet);
            world.playSound(null, pos.offset(side, 1), SoundEvents.BLOCK_CLOTH_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.PASS;
        }
    }
}
