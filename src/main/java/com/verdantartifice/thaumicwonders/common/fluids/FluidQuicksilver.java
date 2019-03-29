package com.verdantartifice.thaumicwonders.common.fluids;

import java.awt.Color;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class FluidQuicksilver extends Fluid {
    public static final FluidQuicksilver INSTANCE = new FluidQuicksilver();
    
    private FluidQuicksilver() {
        super("fluid_quicksilver", new ResourceLocation(ThaumicWonders.MODID, "blocks/fluid_quicksilver"), new ResourceLocation(ThaumicWonders.MODID, "blocks/fluid_quicksilver"));
        this.setViscosity(6000);
        this.setDensity(1500);
    }
    
    @Override
    public int getColor() {
        return new Color(197, 197, 243).getRGB();
    }
}
