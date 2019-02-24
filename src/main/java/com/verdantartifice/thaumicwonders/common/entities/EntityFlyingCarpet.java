package com.verdantartifice.thaumicwonders.common.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFlyingCarpet extends Entity {
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(EntityFlyingCarpet.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer>createKey(EntityFlyingCarpet.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(EntityFlyingCarpet.class, DataSerializers.FLOAT);

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;

    public EntityFlyingCarpet(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.375F, 0.0625F);
    }

    public EntityFlyingCarpet(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }
    
    public EntityFlyingCarpet(World worldIn, BlockPos pos) {
        this(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
    }
    
    @Override
    protected void entityInit() {
        this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
        this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
        this.dataManager.register(DAMAGE_TAKEN, Float.valueOf(0.0F));
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {}

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        // FIXME confirm or replace this value
//        return -0.1D;
        return 0.0D;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // TODO Auto-generated method stub
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        // TODO Auto-generated method stub
        super.applyEntityCollision(entityIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = (double)yaw;
        this.lerpPitch = (double)pitch;
        this.lerpSteps = 10;
    }

    @Override
    public EnumFacing getAdjustedHorizontalFacing() {
        return this.getHorizontalFacing().rotateY();
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        super.onUpdate();
    }
    
    private void tickLerp() {
        if (this.lerpSteps > 0 && !this.canPassengerSteer()) {
            double newX = this.posX + ((this.lerpX - this.posX) / (double)this.lerpSteps);
            double newY = this.posY + ((this.lerpY - this.posY) / (double)this.lerpSteps);
            double newZ = this.posZ + ((this.lerpZ - this.posZ) / (double)this.lerpSteps);
            double deltaYaw = MathHelper.wrapDegrees(this.lerpYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + (deltaYaw / (double)this.lerpSteps));
            this.rotationPitch = (float)((double)this.rotationPitch + ((this.lerpPitch - (double)this.rotationPitch) / (double)this.lerpSteps));
            this.lerpSteps--;
            this.setPosition(newX, newY, newZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }
    
    /**
     * Update the carpet's speed, based on momentum
     */
    private void updateMotion() {
        // TODO Stub
    }
    
    private void controlCarpet() {
        // TODO Stub
    }

    @Override
    public void updatePassenger(Entity passenger) {
        // TODO Auto-generated method stub
        super.updatePassenger(passenger);
    }
    
    /**
     * Applies this carpet's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity entityToUpdate) {
        
    }
    
    /**
     * Applies this carpet's pitch to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyPitchToEntity(Entity entityToUpdate) {
        
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
        this.applyPitchToEntity(entityToUpdate);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!this.world.isRemote) {
            if (player.isSneaking()) {
                this.dropItem(ItemsTW.FLYING_CARPET, 1);
                this.setDead();
            } else {
                player.startRiding(this);
            }
        }
        return true;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        // TODO Auto-generated method stub
        super.updateFallState(y, onGroundIn, state, pos);
    }
    
    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        this.dataManager.set(DAMAGE_TAKEN, Float.valueOf(damageTaken));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE_TAKEN).floatValue();
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT).intValue();
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int forwardDirection) {
        this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection() {
        return this.dataManager.get(FORWARD_DIRECTION).intValue();
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.posX = this.lerpX;
            this.posY = this.lerpY;
            this.posZ = this.lerpZ;
            this.rotationYaw = (float)this.lerpYaw;
            this.rotationPitch = (float)this.lerpPitch;
        }
    }
}
