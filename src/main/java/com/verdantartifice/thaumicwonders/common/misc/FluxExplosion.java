package com.verdantartifice.thaumicwonders.common.misc;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraHelper;

public class FluxExplosion extends Explosion {
    protected World world;
    
    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, affectedPositions);
        this.world = worldIn;
    }

    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean damagesTerrain) {
        super(worldIn, entityIn, x, y, z, size, flaming, damagesTerrain);
        this.world = worldIn;
    }

    public FluxExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean causesFire, boolean damagesTerrain, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, causesFire, damagesTerrain, affectedPositions);
        this.world = worldIn;
    }

    @Override
    public void doExplosionB(boolean spawnParticles) {
        // Generate flux from destroyed blocks before they're replaced by air in the super method
        for (BlockPos pos : this.getAffectedBlockPositions()) {
            IBlockState state = this.world.getBlockState(pos);
            if (state.getMaterial() != Material.AIR) {
                AuraHelper.polluteAura(this.world, pos, 1.0F, true);
            }
        }
        super.doExplosionB(spawnParticles);
    }
}
