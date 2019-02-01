package com.verdantartifice.thaumicwonders.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTW extends TileEntity {
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.readFromTileNBT(compound);
    }
    
    protected void readFromTileNBT(NBTTagCompound compound) {}

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(this.writeToTileNBT(compound));
    }
    
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        return compound;
    }

    public void syncTile(boolean rerender) {
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(this.pos, state, state, (rerender ? 6 : 2));
    }
}
