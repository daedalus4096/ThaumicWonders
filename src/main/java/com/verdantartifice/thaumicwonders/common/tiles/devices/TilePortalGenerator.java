package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

public class TilePortalGenerator extends TileTW {
    protected int linkX;
    protected int linkY;
    protected int linkZ;
    protected int linkDim;
    
    public void setLink(int linkX, int linkY, int linkZ, int linkDim) {
        this.linkX = linkX;
        this.linkY = linkY;
        this.linkZ = linkZ;
        this.linkDim = linkDim;
    }
}
