package com.verdantartifice.thaumicwonders.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxyTW {
    public void preInit(FMLPreInitializationEvent event);
    public void init(FMLInitializationEvent event);
}
