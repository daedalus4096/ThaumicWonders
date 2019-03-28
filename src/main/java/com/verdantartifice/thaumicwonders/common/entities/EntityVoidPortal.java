package com.verdantartifice.thaumicwonders.common.entities;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TilePortalAnchor;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ITeleporter;
import thaumcraft.common.lib.SoundsTC;

public class EntityVoidPortal extends Entity {
    private static final DataParameter<Integer> LINK_X = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_Y = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_Z = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LINK_DIM = EntityDataManager.<Integer>createKey(EntityVoidPortal.class, DataSerializers.VARINT);
    
    private int soundTime = 0;
    
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
            BlockPos linkPos = new BlockPos(this.getLinkX(), this.getLinkY(), this.getLinkZ());
            WorldServer targetWorld = DimensionManager.getWorld(this.getLinkDim());
            if (targetWorld == null) {
                DimensionManager.initDimension(this.getLinkDim());
                targetWorld = DimensionManager.getWorld(this.getLinkDim());
            }
            if (targetWorld != null) {
                TileEntity tile = targetWorld.getTileEntity(linkPos);
                if (tile != null && tile instanceof TilePortalAnchor) {
                    if (player.world.provider.getDimension() != this.getLinkDim()) {
                        player.changeDimension(this.getLinkDim(), new ITeleporter() {
                            @Override
                            public void placeEntity(World world, Entity entity, float yaw) {}
                        });
                    } else {
                        this.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL, 0.25F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                    }
                    player.setPositionAndUpdate(this.getLinkX() + 0.5D, this.getLinkY() + 1.0D, this.getLinkZ() + 0.5D);
                } else {
                    player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + I18n.format("event.void_portal.no_anchor")), true);
                }
            } else {
                player.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + I18n.format("event.void_portal.no_world")), true);
                ThaumicWonders.LOGGER.error("Target dimension {} not found!", this.getLinkDim());
            }
        }
        return super.processInitialInteract(player, hand);
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        
        // Play ambient sound at most every 540 ticks
        if (!this.isDead && this.rand.nextInt(1000) < this.soundTime++) {
            this.soundTime = -540;
            this.playSound(SoundsTC.monolith, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }
    }
}
