package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.common.init.InitResearch;
import com.verdantartifice.thaumicwonders.common.network.PacketHandler;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy implements IProxyTW {
    @Override
    public void preInit(FMLPreInitializationEvent event) {}
    
    @Override
    public void init(FMLInitializationEvent event) {
        PacketHandler.registerMessages();
        InitResearch.initResearch();
    }
}
