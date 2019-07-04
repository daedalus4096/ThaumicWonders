package com.verdantartifice.thaumicwonders.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.items.ItemsTC;

public class SlotPrimordialPearl extends Slot {
    public SlotPrimordialPearl(IInventory tileEntity, int index, int xPosition, int yPosition) {
        super(tileEntity, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && !stack.isEmpty() && stack.getItem() == ItemsTC.primordialPearl && stack.getItemDamage() < 7;
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
