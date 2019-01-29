package com.verdantartifice.thaumicwonders.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockDeviceTW<T extends TileEntity> extends BlockTileTW<T> {
    public BlockDeviceTW(Material mat, Class<T> tileClass, String name) {
        super(mat, tileClass, name);
    }
}
