package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ProxyEntities {
    public void setupEntityRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyingCarpet.class, new IRenderFactory<EntityFlyingCarpet>() {
            @Override
            public Render<? super EntityFlyingCarpet> createRenderFor(RenderManager manager) {
                return new RenderFlyingCarpet(manager);
            }
        });
    }
}
