package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderFlyingCarpet;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderHexamitePrimed;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderPrimalArrow;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderVoidPortal;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.entities.EntityHexamitePrimed;
import com.verdantartifice.thaumicwonders.common.entities.EntityPrimalArrow;
import com.verdantartifice.thaumicwonders.common.entities.EntityVoidPortal;

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
        RenderingRegistry.registerEntityRenderingHandler(EntityVoidPortal.class, new IRenderFactory<EntityVoidPortal>() {
            @Override
            public Render<? super EntityVoidPortal> createRenderFor(RenderManager manager) {
                return new RenderVoidPortal(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityHexamitePrimed.class, new IRenderFactory<EntityHexamitePrimed>() {
            @Override
            public Render<? super EntityHexamitePrimed> createRenderFor(RenderManager manager) {
                return new RenderHexamitePrimed(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityPrimalArrow.class, new IRenderFactory<EntityPrimalArrow>() {
            @Override
            public Render<? super EntityPrimalArrow> createRenderFor(RenderManager manager) {
                return new RenderPrimalArrow(manager);
            }
        });
    }
}
