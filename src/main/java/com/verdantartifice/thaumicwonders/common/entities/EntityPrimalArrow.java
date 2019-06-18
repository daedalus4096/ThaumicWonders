package com.verdantartifice.thaumicwonders.common.entities;

import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPrimalArrow extends EntityArrow {
    private static final DataParameter<Integer> ARROW_TYPE = EntityDataManager.<Integer>createKey(EntityPrimalArrow.class, DataSerializers.VARINT);

    public EntityPrimalArrow(World worldIn) {
        super(worldIn);
        this.pickupStatus = PickupStatus.CREATIVE_ONLY;
    }
    
    public EntityPrimalArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.pickupStatus = PickupStatus.CREATIVE_ONLY;
    }
    
    public EntityPrimalArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        this.pickupStatus = PickupStatus.CREATIVE_ONLY;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ARROW_TYPE, Integer.valueOf(0));
    }
    
    public int getArrowType() {
        return this.dataManager.get(ARROW_TYPE).intValue();
    }
    
    public void setArrowType(int type) {
        this.dataManager.set(ARROW_TYPE, Integer.valueOf(type));
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemsTW.PRIMAL_ARROW, 1, this.getArrowType());
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        // TODO Auto-generated method stub
        super.onHit(raytraceResultIn);
    }
    
    @Override
    protected void arrowHit(EntityLivingBase living) {
        // TODO Auto-generated method stub
        super.arrowHit(living);
    }
}
