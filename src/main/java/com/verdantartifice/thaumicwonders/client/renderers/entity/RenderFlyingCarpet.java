package com.verdantartifice.thaumicwonders.client.renderers.entity;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.client.renderers.models.entity.ModelFlyingCarpet;
import com.verdantartifice.thaumicwonders.common.entities.EntityFlyingCarpet;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFlyingCarpet extends Render<EntityFlyingCarpet> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ThaumicWonders.MODID, "textures/entities/flying_carpet.png");

    protected ModelBase modelFlyingCarpet = new ModelFlyingCarpet();

    public RenderFlyingCarpet(RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFlyingCarpet entity) {
        return TEXTURE;
    }
    
    @Override
    public void doRender(EntityFlyingCarpet entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);
        this.setupRotation(entity, entityYaw, partialTicks);
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.modelFlyingCarpet.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void setupRotation(EntityFlyingCarpet entity, float entityYaw, float partialTicks) {
        GlStateManager.rotate(90.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
    }
}
