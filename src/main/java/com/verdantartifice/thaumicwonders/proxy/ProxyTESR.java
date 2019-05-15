package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.client.renderers.tile.TesrOreDiviner;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileOreDiviner;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ProxyTESR {
    public void setupTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileOreDiviner.class, new TesrOreDiviner());
    }
}
