package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.util.List;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.entities.EntityVoidPortal;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;

public class TilePortalGenerator extends TileTW {
    protected int linkX = 0;
    protected int linkY = 0;
    protected int linkZ = 0;
    protected int linkDim = 0;
    
    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.linkX = compound.getInteger("linkX");
        this.linkY = compound.getInteger("linkY");
        this.linkZ = compound.getInteger("linkZ");
        this.linkDim = compound.getInteger("linkDim");
    }
    
    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        compound.setInteger("linkX", this.linkX);
        compound.setInteger("linkY", this.linkY);
        compound.setInteger("linkZ", this.linkZ);
        compound.setInteger("linkDim", this.linkDim);
        return super.writeToTileNBT(compound);
    }
    
    public void setLink(int linkX, int linkY, int linkZ, int linkDim) {
        this.linkX = linkX;
        this.linkY = linkY;
        this.linkZ = linkZ;
        this.linkDim = linkDim;
    }
    
    public void spawnPortal() {
        if (!this.world.isRemote) {
            ThaumicWonders.LOGGER.info("Spawning portal linked to {}, {}, {} in dim {}", this.linkX, this.linkY, this.linkZ, this.linkDim);
            double posX = this.pos.up().getX() + 0.5D;
            double posY = this.pos.up().getY();
            double posZ = this.pos.up().getZ() + 0.5D;
            EntityVoidPortal portal = new EntityVoidPortal(this.world);
            portal.setPosition(posX, posY, posZ);
            portal.setLinkX(this.linkX);
            portal.setLinkY(this.linkY);
            portal.setLinkZ(this.linkZ);
            portal.setLinkDim(this.linkDim);
            this.world.spawnEntity(portal);
        }
    }
    
    public void despawnPortal() {
        if (!this.world.isRemote) {
            ThaumicWonders.LOGGER.info("Killing portal");
            AxisAlignedBB bb = new AxisAlignedBB(this.pos.up());
            List<EntityVoidPortal> portalList = this.world.getEntitiesWithinAABB(EntityVoidPortal.class, bb);
            if (portalList.size() > 0) {
                portalList.get(0).setDead();
            } else {
                ThaumicWonders.LOGGER.info("Couldn't find void portal entity to kill!");
            }
        }
    }
}
