package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.awt.Color;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;

public class TileOreDiviner extends TileTW implements ITickable {
    public static final int SCAN_RANGE = 20;
    
    protected Vec3d target = null;
    protected int counter = -1;
    
    public void setTarget(@Nullable Vec3d newTarget) {
        this.target = newTarget;
        this.counter = 0;
    }

    @Override
    public void update() {
        if (this.world.isRemote && this.target != null && this.counter % 44 == 0) {
            BlockPos targetPos = new BlockPos(target.x, target.y, target.z);
            Color color = this.getOreColor(targetPos);
            float r = color.getRed() / 255.0F;
            float g = color.getGreen() / 255.0F;
            float b = color.getBlue() / 255.0F;
            float colorAvg = (r + g + b) / 3.0F;
            
            FXGeneric particle = new FXGeneric(this.world, target.x, target.y, target.z, 0.0D, 0.0D, 0.0D);
            particle.setMaxAge(44);
            particle.setRBGColorF(r, g, b);
            particle.setAlphaF(new float[] { 0.0F, 1.0F, 0.8F, 0.0F });
            particle.setParticles(240, 15, 1);
            particle.setGridSize(16);
            particle.setLoop(true);
            particle.setScale(new float[] { 9.0F });
            particle.setLayer(colorAvg < 0.25F ? 3 : 2);
            particle.setRotationSpeed(0.0F);
            ParticleEngine.addEffect(this.world, particle);
        }
        this.counter++;
    }
    
    protected Color getOreColor(BlockPos targetPos) {
        return new Color(0xC0C0C0);
    }
}
