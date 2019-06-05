package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;

public class TileVoidBeacon extends TileTW implements ITickable, IAspectContainer, IEssentiaTransport {
    private static final int CAPACITY = 100;
    
    protected final List<TileVoidBeacon.BeamSegment> beamSegments = new ArrayList<TileVoidBeacon.BeamSegment>();
    
    protected Aspect essentiaType = null;
    protected int essentiaAmount = 0;
    protected int tickCounter = 0;
    protected boolean validPlacement = false;
    
    @SideOnly(Side.CLIENT)
    private long beamRenderCounter;
    @SideOnly(Side.CLIENT)
    private float beamRenderScale;

    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.essentiaType = Aspect.getAspect(compound.getString("essentiaType"));
        this.essentiaAmount = compound.getShort("essentiaAmount");
    }
    
    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        if (this.essentiaType != null) {
            compound.setString("essentiaType", this.essentiaType.getTag());
            compound.setShort("essentiaAmount", (short)this.essentiaAmount);
        }
        return compound;
    }
    
    @Override
    public void update() {
        this.tickCounter++;
        if (!this.world.isRemote && (this.tickCounter % 5 == 0)) {
            this.fill();
        }
        if (this.tickCounter % 80 == 0) {
            this.updateBeam();
            this.updateLevels();
        }
    }
    
    protected void updateBeam() {
        this.beamSegments.clear();
        this.validPlacement = true;
        Color beamColor = new Color(Aspect.ELDRITCH.getColor());
        TileVoidBeacon.BeamSegment segment = new TileVoidBeacon.BeamSegment(beamColor.getRGBColorComponents(null));
        this.beamSegments.add(segment);
        BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos();
        
        for (int y = this.pos.getY() + 1; y < this.world.getActualHeight(); y++) {
            mbp.setPos(this.pos.getX(), y, this.pos.getZ());
            IBlockState blockState = this.world.getBlockState(mbp);
            if (blockState.getLightOpacity(this.world, mbp) >= 15 && blockState.getBlock() != Blocks.BEDROCK) {
                this.validPlacement = false;
                this.beamSegments.clear();
                break;
            } else {
                segment.incrementHeight();
            }
        }
    }
    
    protected void updateLevels() {
        
    }
    
    protected void fill() {
        for (EnumFacing face : EnumFacing.HORIZONTALS) {
            if (!this.canInputFrom(face) || this.getEssentiaAmount(face) >= CAPACITY) {
                continue;
            }
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos, face);
            if (te != null && te instanceof IEssentiaTransport) {
                IEssentiaTransport otherTile = (IEssentiaTransport)te;
                if (!otherTile.canOutputTo(face.getOpposite())) {
                    continue;
                }
                Aspect type = otherTile.getEssentiaType(face.getOpposite());
                if ( type != null &&
                     otherTile.getEssentiaAmount(face.getOpposite()) > 0 &&
                     (this.getEssentiaType(face) == null || type == this.getEssentiaType(face)) &&
                     this.getSuctionAmount(face) > otherTile.getSuctionAmount(face.getOpposite()) &&
                     this.getSuctionAmount(face) >= otherTile.getMinimumSuction() ) {
                    int taken = otherTile.takeEssentia(type, 1, face.getOpposite());
                    int leftover = this.addToContainer(type, taken);
                    if (leftover > 0) {
                        ThaumicWonders.LOGGER.info("Void beacon spilling {} essentia on fill", leftover);
                        AuraHelper.polluteAura(this.world, this.pos, leftover, true);
                    }
                    this.syncTile(false);
                    this.markDirty();
                }
            }
        }
    }

    @Override
    public int addEssentia(Aspect aspect, int amt, EnumFacing face) {
        if (this.canInputFrom(face) && (this.getEssentiaType(face) == null || aspect == this.getEssentiaType(face))) {
            return (amt - this.addToContainer(aspect, amt));
        } else {
            return 0;
        }
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return (face != EnumFacing.DOWN && face != EnumFacing.UP);
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return false;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return this.essentiaAmount;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        return this.essentiaType;
    }

    @Override
    public int getMinimumSuction() {
        // Can't output, so no need for minimum suction
        return 0;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return (this.getEssentiaAmount(face) >= CAPACITY) ? 0 : 128;
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return this.getEssentiaType(face);
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        return this.canInputFrom(face);
    }

    @Override
    public void setSuction(Aspect aspect, int amt) {
        // Do nothing
    }

    @Override
    public int takeEssentia(Aspect aspect, int amt, EnumFacing face) {
        // Can't output
        return 0;
    }

    @Override
    public int addToContainer(Aspect aspect, int toAdd) {
        int retVal = 0;
        if (toAdd == 0) {
            return 0;
        } else if (this.essentiaAmount < CAPACITY && (this.essentiaType == null || this.essentiaType == aspect)) {
            // Add as much as possible and return the remainder
            int added = Math.min(toAdd, CAPACITY - this.essentiaAmount);
            this.essentiaAmount += added;
            this.essentiaType = aspect;
            retVal = (toAdd - added);
        } else {
            retVal = toAdd;
        }
        
        this.syncTile(false);
        this.markDirty();
        return retVal;
    }

    @Override
    public int containerContains(Aspect aspect) {
        return (this.essentiaType == aspect) ? this.essentiaAmount : 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return true;
    }

    @Override
    public boolean doesContainerContain(AspectList aspectList) {
        boolean satisfied = true;
        for (Aspect aspect : aspectList.getAspects()) {
            satisfied = satisfied && this.doesContainerContainAmount(aspect, aspectList.getAmount(aspect));
        }
        return satisfied;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect aspect, int amt) {
        return (this.essentiaType == aspect && this.essentiaAmount >= amt);
    }

    @Override
    public AspectList getAspects() {
        AspectList list = new AspectList();
        if (this.essentiaType != null) {
            list.add(this.essentiaType, this.essentiaAmount);
        }
        return list;
    }

    @Override
    public void setAspects(AspectList aspectList) {
        if (aspectList != null && aspectList.size() > 0) {
            this.essentiaType = aspectList.getAspectsSortedByAmount()[0];
            this.essentiaAmount = aspectList.getAmount(this.essentiaType);
        }
    }

    @Override
    public boolean takeFromContainer(AspectList aspectList) {
        if (!this.doesContainerContain(aspectList)) {
            return false;
        } else {
            boolean satisfied = true;
            for (Aspect aspect : aspectList.getAspects()) {
                satisfied = satisfied && this.takeFromContainer(aspect, aspectList.getAmount(aspect));
            }
            return satisfied;
        }
    }

    @Override
    public boolean takeFromContainer(Aspect aspect, int amt) {
        if (this.essentiaType == aspect && this.essentiaAmount >= amt) {
            this.essentiaAmount -= amt;
            this.syncTile(false);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)
    public List<TileVoidBeacon.BeamSegment> getBeamSegments() {
        return this.beamSegments;
    }
    
    @SideOnly(Side.CLIENT)
    public float shouldBeamRender() {
        if (!this.validPlacement) {
            return 0.0F;
        } else {
            int i = (int)(this.world.getTotalWorldTime() - this.beamRenderCounter);
            this.beamRenderCounter = this.world.getTotalWorldTime();

            if (i > 1) {
                this.beamRenderScale -= (float)i / 40.0F;
                if (this.beamRenderScale < 0.0F) {
                    this.beamRenderScale = 0.0F;
                }
            }

            this.beamRenderScale += 0.025F;

            if (this.beamRenderScale > 1.0F) {
                this.beamRenderScale = 1.0F;
            }

            return this.beamRenderScale;
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
    
    public static class BeamSegment {
        /** RGB (0 to 1.0) colors of this beam segment */
        private final float[] colors;
        private int height;

        public BeamSegment(float[] colorsIn) {
            this.colors = colorsIn;
            this.height = 1;
        }

        protected void incrementHeight() {
            ++this.height;
        }

        /** Returns RGB (0 to 1.0) colors of this beam segment */
        public float[] getColors() {
            return this.colors;
        }

        @SideOnly(Side.CLIENT)
        public int getHeight() {
            return this.height;
        }
    }
}
