package com.verdantartifice.thaumicwonders.common.items.armor;

import com.verdantartifice.thaumicwonders.ThaumicWonders;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.lib.UtilsFX;

public class ItemNightVisionGoggles extends ItemArmor implements IBauble, IRenderBauble {
    protected static final ResourceLocation BAUBLE_TEXTURE = new ResourceLocation(ThaumicWonders.MODID, "textures/items/night_vision_goggles_bauble.png");
    
    public ItemNightVisionGoggles() {
        super(ThaumcraftMaterials.ARMORMAT_SPECIAL, 4, EntityEquipmentSlot.HEAD);
        this.setRegistryName(ThaumicWonders.MODID, "night_vision_goggles");
        this.setUnlocalizedName(ThaumicWonders.MODID + "." + this.getRegistryName().getResourcePath());
        this.setCreativeTab(ThaumicWonders.CREATIVE_TAB);
        this.setMaxDamage(350);
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return "thaumicwonders:textures/entities/armor/night_vision_goggles.png";
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.isItemEqual(new ItemStack(ItemsTC.ingots, 1, 2)) ? true : super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if (type == RenderType.HEAD) {
            boolean wearingHelm = (player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null);
            Minecraft.getMinecraft().renderEngine.bindTexture(BAUBLE_TEXTURE);
            IRenderBauble.Helper.translateToHeadLevel(player);
            IRenderBauble.Helper.translateToFace();
            IRenderBauble.Helper.defaultTransforms();
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5D, -0.5D, wearingHelm ? 0.12D : 0.0D);
            UtilsFX.renderTextureIn3D(0.0F, 0.0F, 1.0F, 1.0F, 16, 26, 0.1F);
        }
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.HEAD;
    }
}
