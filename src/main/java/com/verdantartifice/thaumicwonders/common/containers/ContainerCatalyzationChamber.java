package com.verdantartifice.thaumicwonders.common.containers;

import com.verdantartifice.thaumicwonders.common.tiles.devices.TileCatalyzationChamber;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCatalyzationChamber extends Container {
    private InventoryPlayer inventoryPlayer;
    private TileCatalyzationChamber tileEntity;
    
    public ContainerCatalyzationChamber(InventoryPlayer inventoryPlayer, TileCatalyzationChamber chamberTile) {
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = chamberTile;
        
        // TODO add slot for catalyst stone
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(this.inventoryPlayer, j + (i * 9) + 9, 8 + (j * 18), 84 + (i * 18)));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(this.inventoryPlayer, i, 8 + (i * 18), 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        // TODO Auto-generated method stub
        return super.transferStackInSlot(playerIn, index);
    }
}
