package com.verdantartifice.thaumicwonders.common.items.misc;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.misc.GuiIds;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemClock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemTimewinder extends ItemClock {
    public ItemTimewinder() {
        super();
        setRegistryName(ThaumicWonders.MODID, "timewinder");
        setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        setMaxStackSize(1);
        setNoRepair();
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.provider.isSurfaceWorld()) {
            playerIn.openGui(ThaumicWonders.INSTANCE, GuiIds.TIMEWINDER, worldIn, 0, 0, 0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        } else {
            if (worldIn.isRemote) {
                playerIn.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + I18n.format("event.timewinder.offworld")), true);
            }
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
    }
}
