package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.common.blocks.base.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TilePortalGenerator;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPortalGenerator extends BlockDeviceTW<TilePortalGenerator> {
    public BlockPortalGenerator() {
        super(Material.IRON, TilePortalGenerator.class, "portal_generator");
        this.setSoundType(SoundType.METAL);
    }
}
