package com.verdantartifice.thaumicwonders.common.network;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.network.packets.PacketControlFlyingCarpet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ThaumicWonders.MODID);
    
    private static int id = 0;
    
    public static void registerMessages() {
        INSTANCE.registerMessage(PacketControlFlyingCarpet.Handler.class, PacketControlFlyingCarpet.class, id++, Side.SERVER);
    }
}
