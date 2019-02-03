package com.verdantartifice.thaumicwonders.common.blocks.devices;

import java.util.Random;

import com.verdantartifice.thaumicwonders.common.blocks.BlockDeviceTW;
import com.verdantartifice.thaumicwonders.common.tiles.devices.TileLavaJug;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLavaJug extends BlockDeviceTW<TileLavaJug> {
    public BlockLavaJug() {
        super(Material.ROCK, TileLavaJug.class, "everburning_urn");
        setSoundType(SoundType.STONE);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.0D, 0.8125D);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileLavaJug) {
                TileLavaJug tile = (TileLavaJug)tileEntity;
                if (playerIn.getHeldItem(hand).getItem() == Items.BUCKET && tile.getTank().getFluidAmount() >= 1000) {
                    ItemStack currentItemStack = playerIn.getHeldItem(hand);
                    ItemStack newItemStack = new ItemStack(Items.LAVA_BUCKET);
                    currentItemStack.shrink(1);
                    if (currentItemStack.isEmpty()) {
                        playerIn.setHeldItem(hand, newItemStack);
                    } else if (!playerIn.inventory.addItemStackToInventory(newItemStack)) {
                        playerIn.dropItem(newItemStack, false);
                    } else if (playerIn instanceof EntityPlayerMP) {
                        EntityPlayerMP playerMP = (EntityPlayerMP)playerIn;
                        playerMP.sendContainerToPlayer(playerIn.inventoryContainer);
                    }
                    tile.drain(new FluidStack(FluidRegistry.LAVA, 1000), true);
                    worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 0.33F, 
                            1.0F + (2F * worldIn.rand.nextFloat() - 1.0F) * 0.3F);
                }
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Block block = stateIn.getBlock();
        if (block.hasTileEntity(stateIn)) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof TileLavaJug) {
                TileLavaJug tile = (TileLavaJug)tileEntity;
                if (tile.getTank().getFluidAmount() >= tile.getTank().getCapacity()) {
                    double xp = (double)pos.getX() + 0.5D + (rand.nextDouble() * 0.2D - 0.1D);
                    double yp = (double)pos.getY() + 1.1D;
                    double zp = (double)pos.getZ() + 0.5D + (rand.nextDouble() * 0.2D - 0.1D);
                    if (rand.nextDouble() < 0.1D) {
                        worldIn.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, xp, yp, zp, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, xp, yp, zp, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
