package com.verdantartifice.thaumicwonders.common.items.misc;

import javax.annotation.Nullable;

import com.verdantartifice.thaumicwonders.ThaumicWonders;
import com.verdantartifice.thaumicwonders.common.items.base.ItemTW;
import com.verdantartifice.thaumicwonders.common.misc.GuiIds;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTimewinder extends ItemTW {
    public ItemTimewinder() {
        super("timewinder");
        setMaxStackSize(1);
        setNoRepair();
        
        this.addPropertyOverride(new ResourceLocation("time"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            private double rotation;
            @SideOnly(Side.CLIENT)
            private double rota;
            @SideOnly(Side.CLIENT)
            private long lastUpdateTick;
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                Entity entity = (Entity)(entityIn != null ? entityIn : stack.getItemFrame());
                if (worldIn == null && entity != null) {
                    worldIn = entity.world;
                }

                if (worldIn == null) {
                    return 0.0F;
                } else {
                    double d0;
                    if (worldIn.provider.isSurfaceWorld()) {
                        d0 = (double)worldIn.getCelestialAngle(1.0F);
                    } else {
                        d0 = Math.random();
                    }
                    d0 = this.wobble(worldIn, d0);
                    return (float)d0;
                }
            }
            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double angle) {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = angle - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.9D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }
                return this.rotation;
            }
        });
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.provider.isSurfaceWorld()) {
            playerIn.openGui(ThaumicWonders.INSTANCE, GuiIds.TIMEWINDER, worldIn, 0, 0, 0);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        } else {
            playerIn.sendStatusMessage(new TextComponentString(TextFormatting.DARK_PURPLE + I18n.format("event.timewinder.offworld")), true);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
    }
}
