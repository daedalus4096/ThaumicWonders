package com.verdantartifice.thaumicwonders.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntityHexamitePrimed extends EntityTNTPrimed {
    public EntityHexamitePrimed(World worldIn) {
        super(worldIn);
    }

    public EntityHexamitePrimed(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
        super(worldIn, x, y, z, igniter);
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        super.onUpdate();
    }
    
    protected void explode() {
        // TODO create explosion object and process it
    }
}
