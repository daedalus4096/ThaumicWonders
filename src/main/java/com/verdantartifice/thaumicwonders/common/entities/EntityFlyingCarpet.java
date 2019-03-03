package com.verdantartifice.thaumicwonders.common.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.common.items.ItemsTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFlyingCarpet extends Entity {
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer>createKey(EntityFlyingCarpet.class, DataSerializers.VARINT);

    private float momentum;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;
    private boolean forwardInputDown;
    private boolean backInputDown;

    public EntityFlyingCarpet(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.375F, 0.0625F);
        this.setNoGravity(true);
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
        this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
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
        return -0.15D;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        // TODO Auto-generated method stub
        super.applyEntityCollision(entityIn);
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        super.onUpdate();
        this.tickLerp();
        
        if (this.canPassengerSteer()) {
            this.updateMotion();
            if (this.world.isRemote) {
                this.controlCarpet();
            }
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        } else {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
            if (this.world.isRemote) {
                this.updateInputs(false, false);
            }
        }
        
        this.doBlockCollisions();
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
        this.momentum = 0.9F;
        this.motionX *= (double)this.momentum;
        this.motionY *= (double)this.momentum;
        this.motionZ *= (double)this.momentum;
        this.motionY += (this.hasNoGravity() ? 0.0D : -0.04D);
    }
    
    private void controlCarpet() {
        if (this.isBeingRidden()) {
            Entity pilot = this.getControllingPassenger();
            this.prevRotationYaw = this.rotationYaw;
            this.rotationYaw = pilot.rotationYaw;
            
            float f = 0.0F;
            if (this.forwardInputDown) {
                f += 0.04F;
            }
            if (this.backInputDown) {
                f -= 0.005F;
            }
            this.motionX += (double)(MathHelper.sin(-this.rotationYaw * (float)(Math.PI / 180.0D)) * f);
            this.motionY += (double)(MathHelper.sin(-pilot.rotationPitch * (float)(Math.PI / 180.0D)) * f);
            this.motionZ += (double)(MathHelper.cos(this.rotationYaw * (float)(Math.PI / 180.0D)) * f);
        }
    }
    
    public void updateInputs(boolean forwardDown, boolean backwardDown) {
        this.forwardInputDown = forwardDown;
        this.backInputDown = backwardDown;
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
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }
    
    /**
     * Applies this carpet's pitch to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyPitchToEntity(Entity entityToUpdate) {
        // TODO
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
        Entity retVal = list.isEmpty() ? null : list.get(0);
        return retVal;
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
