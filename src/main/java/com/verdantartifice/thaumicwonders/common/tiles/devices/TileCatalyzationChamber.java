package com.verdantartifice.thaumicwonders.common.tiles.devices;

import com.verdantartifice.thaumicwonders.common.tiles.base.TileTWInventory;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftInvHelper;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.utils.BlockStateUtils;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.devices.TileBellows;

public class TileCatalyzationChamber extends TileTWInventory implements ITickable {
    protected int refineTime = 0;
    protected int maxRefineTime = 0;
    protected int speedyTime = 0;
    
    protected int facingX = -5;
    protected int facingZ = -5;
    
    public TileCatalyzationChamber() {
        super(32);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(
            this.getPos().getX() - 1.3D, this.getPos().getY() - 1.3D, this.getPos().getZ() - 1.3D,
            this.getPos().getX() + 2.3D, this.getPos().getY() + 2.3D, this.getPos().getZ() + 1.3D
        );
    }
    
    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.UP ? super.getSlotsForFace(side) : new int[0];
    }
    
    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return false;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.refineTime = compound.getShort("RefineTime");
        this.speedyTime = compound.getShort("SpeedyTime");
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setShort("RefineTime", (short)this.refineTime);
        compound.setShort("SpeedyTime", (short)this.speedyTime);
        return compound;
    }
    
    @Override
    public void update() {
        if (this.facingX == -5) {
            this.setFacing();
        }
        if (!this.world.isRemote) {
            boolean refinedFlag = false;
            if (this.refineTime > 0) {
                this.refineTime--;
                refinedFlag = true;
            }
            if (this.maxRefineTime <= 0) {
                this.maxRefineTime = this.calcRefineTime();
            }
            if (this.refineTime > this.maxRefineTime) {
                this.refineTime = this.maxRefineTime;
            }
            if (this.refineTime <= 0 && refinedFlag) {
                for (int slot = 0; slot < this.getSizeInventory(); slot++) {
                    ItemStack stack = this.getStackInSlot(slot);
                    if (stack != null && !stack.isEmpty()) {
                        ItemStack resultStack = new ItemStack(Blocks.COBBLESTONE);    // TODO get result from slotted alchemist stone interface
                        if (resultStack != null && !resultStack.isEmpty()) {
                            if (this.speedyTime > 0) {
                                this.speedyTime--;
                            }
                            this.ejectItem(resultStack.copy());
                            // TODO fire addBlockEvent
                            // TODO chance of flux?
                            this.decrStackSize(slot, 1);
                            break;
                        }
                        this.setInventorySlotContents(slot, ItemStack.EMPTY);
                    }
                }
            }
            if (this.speedyTime <= 0) {
                this.speedyTime = (int)AuraHelper.drainVis(this.getWorld(), this.getPos(), 20.0F, false);
            }
            if (this.refineTime == 0 && !refinedFlag) {
                for (int slot = 0; slot < this.getSizeInventory(); slot++) {
                    if (this.canRefine(this.getStackInSlot(slot))) {
                        this.maxRefineTime = this.calcRefineTime();
                        this.refineTime = this.maxRefineTime;
                        break;
                    }
                }
            }
        }
    }
    
    public ItemStack addItemsToInventory(ItemStack items) {
        return ThaumcraftInvHelper.insertStackAt(this.getWorld(), this.getPos(), EnumFacing.UP, items, false);
    }

    private boolean canRefine(ItemStack stack) {
        // TODO return whether the result from slotted alchemist stone interface is not empty
        return true;
    }

    private void ejectItem(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            EnumFacing facing = BlockStateUtils.getFacing(getBlockMetadata()).getOpposite();
            InventoryUtils.ejectStackAt(getWorld(), getPos(), facing, itemStack);
        }
    }

    private int calcRefineTime() {
        int count = this.getBellows();
        int bonus = (count > 0) ? (20 - (count - 1)) * count : 0;
        return Math.max(10, (this.speedyTime > 0 ? 80 : 140) - bonus);
    }

    private int getBellows() {
        int count = 0;
        for (EnumFacing dir : EnumFacing.VALUES) {
            if (dir != EnumFacing.UP) {
                BlockPos tilePos = this.pos.offset(dir, 2);
                TileEntity tile = this.world.getTileEntity(tilePos);
                if ( tile != null &&
                     tile instanceof TileBellows &&
                     BlockStateUtils.getFacing(this.world.getBlockState(tilePos)) == dir.getOpposite() &&
                     this.world.isBlockIndirectlyGettingPowered(tilePos) == 0 ) {
                    count++;
                }
            }
        }
        return Math.min(4, count);
    }

    private void setFacing() {
        EnumFacing face = this.getFacing().getOpposite();
        this.facingX = face.getFrontOffsetX();
        this.facingZ = face.getFrontOffsetZ();
    }
}
