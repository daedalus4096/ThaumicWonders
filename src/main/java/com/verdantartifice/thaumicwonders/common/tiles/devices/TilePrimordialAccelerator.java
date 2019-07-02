package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.tiles.base.TileTWInventory;

import net.minecraft.util.ITickable;

public class TilePrimordialAccelerator extends TileTWInventory implements ITickable {
    public TilePrimordialAccelerator() {
        super(1);
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

}
