package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.blocks.BlocksTW;
import com.verdantartifice.thaumicwonders.common.blocks.devices.BlockFluxCapacitor;
import com.verdantartifice.thaumicwonders.common.blocks.devices.BlockFluxDistiller;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileFluxDistiller extends TileTW implements IAspectContainer, IEssentiaTransport, ITickable {
    protected static final int PROCESS_TIME = 20;
    protected static final int MAX_CHARGE = 10;
    protected static final int MAX_ESSENTIA = 10;
    
    protected int amount = 0;
    protected int tickCounter = 0;
    
    public int getAmount() {
        return this.amount;
    }
    
    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.amount = compound.getShort("essentia");
    }
    
    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        compound.setShort("essentia", (short)this.amount);
        return compound;
    }

    @Override
    public void update() {
        if (!this.world.isRemote && (++this.tickCounter % PROCESS_TIME == 0)) {
            IBlockState capacitorState = this.world.getBlockState(this.pos.down());
            if (capacitorState != null && capacitorState.getBlock() == BlocksTW.FLUX_CAPACITOR) {
                BlockFluxCapacitor capacitorBlock = (BlockFluxCapacitor)capacitorState.getBlock();
                int capacitorCharge = capacitorBlock.getCharge(this.world, this.pos.down());
                int distillerCharge = this.getCharge();
                if (capacitorCharge > 0 && distillerCharge < MAX_CHARGE) {
                    capacitorBlock.decrementCharge(this.world, this.pos.down(), 1);
                    this.setCharge(distillerCharge + 1);
                }
            }
        }
    }
    
    protected int getCharge() {
        IBlockState state = this.world.getBlockState(this.pos);
        if (state.getBlock() == BlocksTW.FLUX_DISTILLER) {
            return state.getBlock().getMetaFromState(state);
        } else {
            return 0;
        }
    }
    
    protected void setCharge(int charge) {
        IBlockState state = this.world.getBlockState(this.pos);
        if (state.getBlock() == BlocksTW.FLUX_DISTILLER) {
            this.world.setBlockState(this.pos, state.withProperty(BlockFluxDistiller.CHARGE, Integer.valueOf(charge)));
        }
    }

    @Override
    public int addEssentia(Aspect aspect, int amt, EnumFacing face) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMinimumSuction() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setSuction(Aspect arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public int takeEssentia(Aspect arg0, int arg1, EnumFacing face) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addToContainer(Aspect arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int containerContains(Aspect arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AspectList getAspects() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAspects(AspectList arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean takeFromContainer(AspectList arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean takeFromContainer(Aspect arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
