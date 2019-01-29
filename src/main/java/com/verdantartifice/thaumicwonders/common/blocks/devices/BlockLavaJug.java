package com.verdantartifice.thaumicwonders.common.blocks.devices;

import com.verdantartifice.thaumicwonders.common.blocks.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileLavaJug;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockLavaJug extends BlockDeviceTW<TileLavaJug> {
    public BlockLavaJug() {
        super(Material.ROCK, TileLavaJug.class, "everburning_urn");
        setSoundType(SoundType.STONE);
    }
}
