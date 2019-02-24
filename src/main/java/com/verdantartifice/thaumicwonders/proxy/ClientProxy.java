package com.verdantartifice.thaumicwonders.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxyTW {
    private ProxyEntities proxyEntities = new ProxyEntities();
    
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.proxyEntities.setupEntityRenderers();
    }
}
