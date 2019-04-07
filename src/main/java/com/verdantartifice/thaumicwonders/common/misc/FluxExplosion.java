package com.verdantartifice.thaumicwonders.common.misc;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class FluxExplosion extends Explosion {
    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, affectedPositions);
    }

    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
        super(worldIn, entityIn, x, y, z, size, flaming, damagesTerrain);
    }

    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean causesFire, boolean damagesTerrain, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, causesFire, damagesTerrain, affectedPositions);
    }

    @Override
    public void doExplosionB(boolean spawnParticles) {
        // TODO Auto-generated method stub
        super.doExplosionB(spawnParticles);
    }
}
