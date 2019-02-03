package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.tiles.TileTW;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import thaumcraft.api.aura.AuraHelper;

public class TileLavaJug extends TileTW implements ITickable {
    protected static final int CAPACITY = 1000;
    protected static final int MAX_PER_FILL = 40;
    
    protected int counter = 0;
    protected FluidTank tank = new FluidTank(new FluidStack(FluidRegistry.LAVA, 0), CAPACITY);

    public FluidTank getTank() {
        return this.tank;
    }

    @Override
    public void update() {
        this.counter++;
        if (!this.world.isRemote && this.counter % 5 == 0 && this.tank.getFluidAmount() < CAPACITY) {
            float visToDrain = (CAPACITY - this.tank.getFluidAmount()) / (float)MAX_PER_FILL;     // A full tank costs 25 vis
            if (visToDrain > 0.1F) {
                // Cap drain per op at 0.1 vis
                visToDrain = 0.1F;
            }
            float actualVisDrain = AuraHelper.drainVis(getWorld(), getPos(), visToDrain, false);
            int mbToAdd = (int)((float)MAX_PER_FILL * actualVisDrain);
            if (mbToAdd > 0) {
                this.tank.fill(new FluidStack(FluidRegistry.LAVA, mbToAdd), true);
                this.markDirty();
                if (this.tank.getFluidAmount() >= this.tank.getCapacity()) {
                    this.syncTile(false);
                }
            }
        }
    }

    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.tank.readFromNBT(compound);
    }

    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        this.tank.writeToNBT(compound);
        return compound;
    }
    
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        boolean wasFull = (this.tank.getFluidAmount() >= this.tank.getCapacity());
        FluidStack fluidStack = this.tank.drain(resource, doDrain);
        this.markDirty();
        if (wasFull && (this.tank.getFluidAmount() < this.tank.getCapacity())) {
            this.syncTile(false);
        }
        return fluidStack;
    }
}
