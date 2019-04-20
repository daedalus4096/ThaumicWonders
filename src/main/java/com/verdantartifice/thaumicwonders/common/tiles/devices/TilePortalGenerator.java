package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.awt.Color;
import java.util.List;

import com.verdantartifice.thaumicwonders.common.entities.EntityVoidPortal;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.lib.SoundsTC;

public class TilePortalGenerator extends TileTW implements ITickable {
    protected int linkX = 0;
    protected int linkY = 0;
    protected int linkZ = 0;
    protected int linkDim = 0;
    protected int counter = 0;
    protected boolean lastEnabled = true;
    
    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.linkX = compound.getInteger("linkX");
        this.linkY = compound.getInteger("linkY");
        this.linkZ = compound.getInteger("linkZ");
        this.linkDim = compound.getInteger("linkDim");
    }
    
    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        compound.setInteger("linkX", this.linkX);
        compound.setInteger("linkY", this.linkY);
        compound.setInteger("linkZ", this.linkZ);
        compound.setInteger("linkDim", this.linkDim);
        return super.writeToTileNBT(compound);
    }
    
    public void setLink(int linkX, int linkY, int linkZ, int linkDim) {
        this.linkX = linkX;
        this.linkY = linkY;
        this.linkZ = linkZ;
        this.linkDim = linkDim;
    }
    
    public void spawnPortal() {
        if (!this.world.isRemote) {
            double posX = this.pos.up().getX() + 0.5D;
            double posY = this.pos.up().getY();
            double posZ = this.pos.up().getZ() + 0.5D;
            EntityVoidPortal portal = new EntityVoidPortal(this.world);
            portal.setPosition(posX, posY, posZ);
            portal.setLinkX(this.linkX);
            portal.setLinkY(this.linkY);
            portal.setLinkZ(this.linkZ);
            portal.setLinkDim(this.linkDim);
            this.world.spawnEntity(portal);
        }
    }
    
    public void despawnPortal() {
        if (!this.world.isRemote) {
            EntityVoidPortal portal = this.getActivePortal();
            if (portal != null) {
                portal.setDead();
                this.world.playSound(null, this.pos, SoundsTC.shock, SoundCategory.BLOCKS, 1.0F, (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }
    
    protected EntityVoidPortal getActivePortal() {
        AxisAlignedBB bb = new AxisAlignedBB(this.pos.up());
        List<EntityVoidPortal> portalList = this.world.getEntitiesWithinAABB(EntityVoidPortal.class, bb);
        return (portalList.size() > 0) ? portalList.get(0) : null;
    }
    
    protected boolean isPortalActive() {
        return (this.getActivePortal() != null);
    }
    
    @Override
    public void onLoad() {
        super.onLoad();
        IBlockState state = this.world.getBlockState(this.pos);
        this.lastEnabled = state.getValue(IBlockEnabled.ENABLED);
    }

    @Override
    public void update() {
        this.counter++;
        if (this.world.isRemote && this.counter == 20) {
            BlockPos sourcePos = this.pos.up();
            if (this.world.rand.nextInt() % 2 == 0) {
                sourcePos = sourcePos.south();
            }
            if (this.world.rand.nextInt() % 2 == 0) {
                sourcePos = sourcePos.east();
            }
            Color color = new Color(Aspect.FLUX.getColor());
            float r = color.getRed() / 255.0F;
            float g = color.getGreen() / 255.0F;
            float b = color.getBlue() / 255.0F;
            FXDispatcher.INSTANCE.drawLightningFlash(sourcePos.getX(), sourcePos.getY(), sourcePos.getZ(), r, g, b, 1.0F, 2.5F);
            this.counter -= (20 + this.world.rand.nextInt(80));
        }
        if (!this.world.isRemote) {
            IBlockState state = this.world.getBlockState(this.pos);
            boolean enabled = state.getValue(IBlockEnabled.ENABLED);
            if (enabled != this.lastEnabled) {
                if (enabled && !this.isPortalActive()) {
                    this.spawnPortal();
                } else if (!enabled && this.isPortalActive()) {
                    this.despawnPortal();
                }
            }
            this.lastEnabled = enabled;
        }
    }
}
