package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTWInventory;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;

public class TilePrimordialAccretionChamber extends TileTWInventory implements IAspectContainer, ITickable {
    private static final int CAPACITY = 125;
    private static final AspectList REQUIRED_FUEL = new AspectList().add(Aspect.AIR, 125).add(Aspect.EARTH, 125).add(Aspect.FIRE, 125).add(Aspect.WATER, 125).add(Aspect.ORDER, 125).add(Aspect.ENTROPY, 125);
    
    protected AspectList essentia = new AspectList();
    
    public TilePrimordialAccretionChamber() {
        super(32);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
            this.getPos().getX() - 1.3D, this.getPos().getY() - 1.3D, this.getPos().getZ() - 1.3D,
            this.getPos().getX() + 2.3D, this.getPos().getY() + 2.3D, this.getPos().getZ() + 1.3D
        );
    }
    
    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.essentia.readFromNBT(compound, "essentia");
    }
    
    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        this.essentia.writeToNBT(compound, "essentia");
        return compound;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
    
    public Aspect getCurrentSuction() {
        for (Aspect aspect : REQUIRED_FUEL.getAspectsSortedByName()) {
            if (!this.doesContainerContainAmount(aspect, CAPACITY)) {
                return aspect;
            }
        }
        return null;
    }
    
    public boolean isEssentiaFull() {
        return this.doesContainerContain(REQUIRED_FUEL);
    }

    @Override
    public int addToContainer(Aspect aspect, int toAdd) {
        if (toAdd == 0) {
            return 0;
        } else if (this.essentia.getAmount(aspect) >= CAPACITY || !this.doesContainerAccept(aspect)) {
            // Incompatible addition; return all of it
            this.syncTile(false);
            this.markDirty();
            return toAdd;
        } else {
            // Add as much as possible and return the remainder
            int added = Math.min(toAdd, CAPACITY - this.essentia.getAmount(aspect));
            this.essentia.add(aspect, added);
            this.syncTile(false);
            this.markDirty();
            ThaumicWonders.LOGGER.info("Added {} {} to central accretion chamber, new total {}", added, aspect.getName(), this.essentia.getAmount(aspect));
            return (toAdd - added);
        }
    }

    @Override
    public int containerContains(Aspect aspect) {
        return this.doesContainerAccept(aspect) ? this.essentia.getAmount(aspect) : 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return (REQUIRED_FUEL.getAmount(aspect) > 0);
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        boolean satisfied = true;
        for (Aspect aspect : aspectList.getAspects()) {
            satisfied = satisfied && this.doesContainerContainAmount(aspect, aspectList.getAmount(aspect));
        }
        return satisfied;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amt) {
        return this.essentia.getAmount(aspect) >= amt;
    }

    @Override
    public AspectList getAspects() {
        return this.essentia.copy();
    }

    @Override
    public void setAspects(AspectList aspects) {
        if (aspects != null) {
            this.essentia = aspects.copy();
        }
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        if (!this.doesContainerContain(aspectList)) {
            return false;
        } else {
            boolean satisfied = true;
            for (Aspect aspect : aspectList.getAspects()) {
                satisfied = satisfied && this.takeFromContainer(aspect, aspectList.getAmount(aspect));
            }
            return satisfied;
        }
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amt) {
        if (this.doesContainerContainAmount(aspect, amt)) {
            this.essentia.reduce(aspect, amt);
            this.syncTile(false);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }
}
