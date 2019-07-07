package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.tiles.base.TileTWInventory;

import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TilePrimordialAccretionChamber extends TileTWInventory implements ITickable {
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
    public void update() {
        // TODO Auto-generated method stub

    }
}
