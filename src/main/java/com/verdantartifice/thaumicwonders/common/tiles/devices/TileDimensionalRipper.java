package com.verdantartifice.thaumicwonders.common.tiles.devices;

import java.awt.Color;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.tiles.base.TileTW;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.IBlockEnabled;
import thaumcraft.common.blocks.IBlockFacing;
import thaumcraft.common.entities.EntityFluxRift;
import thaumcraft.common.lib.SoundsTC;
import thaumcraft.common.lib.utils.EntityUtils;

public class TileDimensionalRipper extends TileTW implements IAspectContainer, IEssentiaTransport, ITickable {
    private static final int CAPACITY = 50;
    private static final int DISTANCE = 10;
    
    protected int amount = 0;
    protected int tickCounter = 0;
    
    public int getAmount() {
        return this.amount;
    }
    
    @Override
    protected void readFromTileNBT(NBTTagCompound compound) {
        this.amount = compound.getShort("essentia");
    }

    @Override
    protected NBTTagCompound writeToTileNBT(NBTTagCompound compound) {
        compound.setShort("essentia", (short)this.amount);
        return compound;
    }

    @Override
    public int addEssentia(Aspect aspect, int amt, EnumFacing face) {
        if (this.canInputFrom(face)) {
            return (amt - this.addToContainer(aspect, amt));
        } else {
            return 0;
        }
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return this.isConnectable(face);
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return false;
    }

    @Override
    public int getEssentiaAmount(EnumFacing face) {
        return this.amount;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing face) {
        return Aspect.FLUX;
    }

    @Override
    public int getMinimumSuction() {
        // Can't output, so no need for minimum suction
        return 0;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return (this.amount >= CAPACITY) ? 0 : 128;
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return Aspect.FLUX;
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        if (this.getBlockType() instanceof IBlockFacing) {
            IBlockState blockState = this.world.getBlockState(this.pos);
            EnumFacing blockFacing = blockState.getValue(IBlockFacing.FACING);
            return (blockFacing != face);
        } else {
            return false;
        }
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
        if (toAdd == 0) {
            return 0;
        } else if (this.amount >= CAPACITY || aspect != Aspect.FLUX) {
            // Incompatible addition; return all of it
            this.syncTile(false);
            this.markDirty();
            return toAdd;
        } else {
            // Add as much as possible and return the remainder
            int added = Math.min(toAdd, CAPACITY - this.amount);
            this.amount += added;
            this.syncTile(false);
            this.markDirty();
            return (toAdd - added);
        }
    }

    @Override
    public int containerContains(Aspect aspect) {
        return (aspect == Aspect.FLUX) ? this.amount : 0;
    }

    @Override
    public boolean doesContainerAccept(Aspect aspect) {
        return (aspect == Aspect.FLUX);
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
        return (aspect == Aspect.FLUX && this.amount >= amt);
    }

    @Override
    public AspectList getAspects() {
        AspectList list = new AspectList();
        if (this.amount > 0) {
            list.add(Aspect.FLUX, this.amount);
        }
        return list;
    }

    @Override
    public void setAspects(AspectList aspects) {
        if (aspects != null && aspects.size() > 0) {
            this.amount = aspects.getAmount(Aspect.FLUX);
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
        if (aspect == Aspect.FLUX && this.amount >= amt) {
            this.amount -= amt;
            this.syncTile(false);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update() {
        if (!this.world.isRemote && (++this.tickCounter % 5 == 0)) {
            if (this.amount < CAPACITY) {
                this.fill();
            }
            this.checkForActivation();
        }
    }
    
    protected void fill() {
        for (EnumFacing face : EnumFacing.VALUES) {
            if (!this.canInputFrom(face)) {
                continue;
            }
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.world, this.pos, face);
            if (te != null && te instanceof IEssentiaTransport) {
                IEssentiaTransport otherTile = (IEssentiaTransport)te;
                if (!otherTile.canOutputTo(face.getOpposite())) {
                    continue;
                }
                if ( otherTile.getEssentiaType(face.getOpposite()) == Aspect.FLUX &&
                     otherTile.getEssentiaAmount(face.getOpposite()) > 0 &&
                     this.getSuctionAmount(face) > otherTile.getSuctionAmount(face.getOpposite()) &&
                     this.getSuctionAmount(face) >= otherTile.getMinimumSuction() ) {
                    int taken = otherTile.takeEssentia(Aspect.FLUX, 1, face.getOpposite());
                    int leftover = this.addToContainer(Aspect.FLUX, taken);
                    if (leftover > 0) {
                        ThaumicWonders.LOGGER.info("Ripper spilling {} essentia on fill", leftover);
                        AuraHelper.polluteAura(this.world, this.pos, leftover, true);
                    }
                    this.syncTile(false);
                    this.markDirty();
                    if (this.amount >= CAPACITY) {
                        break;
                    }
                }
            }
        }
    }
    
    protected void checkForActivation() {
        IBlockState state = this.world.getBlockState(this.pos);
        EnumFacing blockFacing = state.getValue(IBlockFacing.FACING);
        boolean blockEnabled = state.getValue(IBlockEnabled.ENABLED);
        BlockPos otherPos = this.pos.offset(blockFacing, DISTANCE);
        TileEntity otherTe = this.world.getTileEntity(otherPos);
        
        if (otherTe != null && otherTe instanceof TileDimensionalRipper) {
            TileDimensionalRipper otherTile = (TileDimensionalRipper)otherTe;
            IBlockState otherState = this.world.getBlockState(otherPos);
            EnumFacing otherBlockFacing = otherState.getValue(IBlockFacing.FACING);
            boolean otherBlockEnabled = otherState.getValue(IBlockEnabled.ENABLED);

            if ( otherBlockFacing == blockFacing.getOpposite() &&
                 blockEnabled &&
                 otherBlockEnabled &&
                 this.amount >= CAPACITY &&
                 otherTile.getAmount() >= CAPACITY ) {
                BlockPos targetPos = new BlockPos(
                    (this.pos.getX() + otherPos.getX()) / 2,
                    (this.pos.getY() + otherPos.getY()) / 2,
                    (this.pos.getZ() + otherPos.getZ()) / 2
                );

                // Deduct reaction fuel
                this.takeFromContainer(Aspect.FLUX, CAPACITY);
                otherTile.takeFromContainer(Aspect.FLUX, CAPACITY);

                // Play special effects
                FXDispatcher.INSTANCE.beamBore(
                    this.pos.getX() + 0.5D + (blockFacing.getFrontOffsetX() / 2.0D), 
                    this.pos.getY() + 0.5D + (blockFacing.getFrontOffsetY() / 2.0D), 
                    this.pos.getZ() + 0.5D + (blockFacing.getFrontOffsetZ() / 2.0D), 
                    targetPos.getX() + 0.5D, 
                    targetPos.getY() + 0.5D, 
                    targetPos.getZ() + 0.5D, 
                    1, 
                    Color.RED.getRGB(), 
                    false, 
                    1.0F, 
                    null, 
                    1
                );
                FXDispatcher.INSTANCE.beamBore(
                    otherPos.getX() + 0.5D + (otherBlockFacing.getFrontOffsetX() / 2.0D), 
                    otherPos.getY() + 0.5D + (otherBlockFacing.getFrontOffsetY() / 2.0D), 
                    otherPos.getZ() + 0.5D + (otherBlockFacing.getFrontOffsetZ() / 2.0D), 
                    targetPos.getX() + 0.5D, 
                    targetPos.getY() + 0.5D, 
                    targetPos.getZ() + 0.5D, 
                    1, 
                    Color.RED.getRGB(), 
                    false, 
                    1.0F, 
                    null, 
                    1
                );
                this.world.playSound(null, this.pos, SoundsTC.zap, SoundCategory.BLOCKS, 1.0F, 1.0F);
                this.world.playSound(null, otherPos, SoundsTC.zap, SoundCategory.BLOCKS, 1.0F, 1.0F);

                // Create the rift
                this.createRift(targetPos);
            }
        }
    }

    protected void createRift(BlockPos pos) {
        if (EntityUtils.getEntitiesInRange(world, pos, null, EntityFluxRift.class, 32.0D).size() > 0) {
            return;
        }
        EntityFluxRift rift = new EntityFluxRift(this.world);
        rift.setRiftSeed(this.world.rand.nextInt());
        rift.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, (float)this.world.rand.nextInt(360), 0.0F);
        double size = Math.sqrt((2 * CAPACITY) * 3.0F);
        if (this.world.spawnEntity(rift)) {
            rift.setRiftSize((int)size);
        }
    }
}
