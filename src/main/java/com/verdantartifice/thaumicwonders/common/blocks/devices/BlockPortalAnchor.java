package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TilePortalAnchor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPortalAnchor extends BlockDeviceTW<TilePortalAnchor> {
    public BlockPortalAnchor() {
        super(Material.IRON, TilePortalAnchor.class, "portal_anchor");
        this.setSoundType(SoundType.METAL);
    }
}
