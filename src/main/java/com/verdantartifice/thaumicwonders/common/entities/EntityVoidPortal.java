package com.verdantartifice.thaumicwonders.common.entities;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TilePortalAnchor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ITeleporter;

public class EntityVoidPortal extends Entity {
    private static final DataParameter<Integer> LINK_X = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_Y = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_Z = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_DIM = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    
    public int activeCounter = 0;
    
    public EntityVoidPortal(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 3.0F);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(LINK_X, Integer.valueOf(0));
        this.dataManager.register(LINK_Y, Integer.valueOf(0));
        this.dataManager.register(LINK_Z, Integer.valueOf(0));
        this.dataManager.register(LINK_DIM, Integer.valueOf(0));
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.setLinkX(compound.getInteger("linkX"));
        this.setLinkY(compound.getInteger("linkY"));
        this.setLinkZ(compound.getInteger("linkZ"));
        this.setLinkDim(compound.getInteger("linkDim"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("linkX", this.getLinkX());
        compound.setInteger("linkY", this.getLinkY());
        compound.setInteger("linkZ", this.getLinkZ());
        compound.setInteger("linkDim", this.getLinkDim());
    }
    
    public int getLinkX() {
        return this.dataManager.get(LINK_X).intValue();
    }
    
    public int getLinkY() {
        return this.dataManager.get(LINK_Y).intValue();
    }
    
    public int getLinkZ() {
        return this.dataManager.get(LINK_Z).intValue();
    }
    
    public int getLinkDim() {
        return this.dataManager.get(LINK_DIM).intValue();
    }
    
    public void setLinkX(int linkX) {
        this.dataManager.set(LINK_X, Integer.valueOf(linkX));
    }
    
    public void setLinkY(int linkY) {
        this.dataManager.set(LINK_Y, Integer.valueOf(linkY));
    }
    
    public void setLinkZ(int linkZ) {
        this.dataManager.set(LINK_Z, Integer.valueOf(linkZ));
    }
    
    public void setLinkDim(int linkDim) {
        this.dataManager.set(LINK_DIM, Integer.valueOf(linkDim));
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return true;
    }
    
    @Override
    public boolean canBePushed() {
        return false;
    }
    
    @Override
    public void move(MoverType type, double x, double y, double z) {
        // Do nothing
    }
    
    @Override
    public float getBrightness() {
        return 1.0F;
    }
    
    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!this.world.isRemote) {
            ThaumicWonders.LOGGER.info("Using void portal to teleport to {}, {}, {} in dim {}", this.getLinkX(), this.getLinkY(), this.getLinkZ(), this.getLinkDim());
            BlockPos linkPos = new BlockPos(this.getLinkX(), this.getLinkY(), this.getLinkZ());
            WorldServer targetWorld = DimensionManager.getWorld(this.getLinkDim());
            if (targetWorld != null) {
                TileEntity tile = targetWorld.getTileEntity(linkPos);
                if (tile != null && tile instanceof TilePortalAnchor) {
                    ThaumicWonders.LOGGER.info("Found matching anchor!");
                    if (player.world.provider.getDimension() != this.getLinkDim()) {
                        ThaumicWonders.LOGGER.info("Changing to dimension {}", this.getLinkDim());
                        player.changeDimension(this.getLinkDim(), new ITeleporter() {
                            @Override
                            public void placeEntity(World world, Entity entity, float yaw) {}
                        });
                    }
                    ThaumicWonders.LOGGER.info("Setting position");
                    player.setPositionAndUpdate(this.getLinkX() + 0.5D, this.getLinkY() + 1.0D, this.getLinkZ() + 0.5D);
                } else {
                    ThaumicWonders.LOGGER.info("No anchor found at link position");
                }
            } else {
                ThaumicWonders.LOGGER.info("No target world found!");
            }
        }
        return super.processInitialInteract(player, hand);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.activeCounter++;
    }
}
