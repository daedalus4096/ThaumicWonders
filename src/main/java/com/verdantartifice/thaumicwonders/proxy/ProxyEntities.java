package com.verdantartifice.thaumicwonders.proxy;

import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderFluxFireball;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderFlyingCarpet;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderHexamitePrimed;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderPrimalArrow;
import com.verdantartifice.thaumicwonders.client.renderers.entity.RenderVoidPortal;
import com.verdantartifice.thaumicwonders.client.renderers.entity.monsters.RenderCorruptionAvatar;
import com.verdantartifice.thaumicwonders.common.entities.EntityFluxFireball;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.entities.EntityHexamitePrimed;
import com.verdantartifice.thaumicwonders.common.entities.EntityPrimalArrow;
import com.verdantartifice.thaumicwonders.common.entities.EntityVoidPortal;
import com.verdantartifice.thaumicwonders.common.entities.monsters.EntityCorruptionAvatar;

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
        RenderingRegistry.registerEntityRenderingHandler(EntityCorruptionAvatar.class, new IRenderFactory<EntityCorruptionAvatar>() {
            @Override
            public Render<? super EntityCorruptionAvatar> createRenderFor(RenderManager manager) {
                return new RenderCorruptionAvatar(manager);
            }
        });
        RenderingRegistry.registerEntityRenderingHandler(EntityFluxFireball.class, new IRenderFactory<EntityFluxFireball>() {
            @Override
            public Render<? super EntityFluxFireball> createRenderFor(RenderManager manager) {
                return new RenderFluxFireball(manager);
            }
        });
    }
}
