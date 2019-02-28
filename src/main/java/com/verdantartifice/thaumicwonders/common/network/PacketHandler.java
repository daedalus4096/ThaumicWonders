package com.verdantartifice.thaumicwonders.common.network;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ThaumicWonders.MODID);
    
    public static void registerMessages() {
    }
}
