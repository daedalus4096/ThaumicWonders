package com.verdantartifice.thaumicwonders.common.tiles.essentia;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.tiles.essentia.TileJarFillable;

public class TileCreativeEssentiaJar extends TileJarFillable {
    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
        // Aspect must match, but don't deduct any essentia
        return (tt == this.aspect);
    }
}
