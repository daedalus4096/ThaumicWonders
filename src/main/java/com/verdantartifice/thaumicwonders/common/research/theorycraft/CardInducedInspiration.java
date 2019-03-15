package com.verdantartifice.thaumicwonders.common.research.theorycraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.api.research.theorycraft.ResearchTableData;
import thaumcraft.api.research.theorycraft.TheorycraftCard;

public class CardInducedInspiration extends TheorycraftCard {
    private String category = null;
    private int amount;
    
    @Override
    public NBTTagCompound serialize() {
        NBTTagCompound nbt = super.serialize();
        nbt.setString("cat", this.category);
        nbt.setInteger("amt", this.amount);
        return nbt;
    }
    
    @Override
    public void deserialize(NBTTagCompound nbt) {
        super.deserialize(nbt);
        this.category = nbt.getString("cat");
        this.amount = nbt.getInteger("amt");
    }
    
    @Override
    public String getResearchCategory() {
        return this.category;
    }
    
    @Override
    public boolean isAidOnly() {
        return true;
    }
    
    @Override
    public boolean initialize(EntityPlayer player, ResearchTableData data) {
        if (data.categoryTotals.size() < 1) {
            return false;
        }
        int highestValue = 0;
        String highestKey = "";
        for (String category : data.categoryTotals.keySet()) {
            int value = data.getTotal(category);
            if (value > highestValue) {
                highestValue = value;
                highestKey = category;
            }
        }
        this.category = highestKey;
        this.amount = (5 + (highestValue / 3));
        return true;
    }

    @Override
    public boolean activate(EntityPlayer player, ResearchTableData data) {
        data.addTotal(this.category, this.amount);
        data.bonusDraws += 1;
        return true;
    }

    @Override
    public int getInspirationCost() {
        return -1;
    }

    @Override
    public String getLocalizedName() {
        return new TextComponentTranslation("card.induced_inspiration.name", new Object[0]).getUnformattedText();
    }

    @Override
    public String getLocalizedText() {
        return new TextComponentTranslation("card.induced_inspiration.text", new Object[] { Integer.valueOf(this.amount), TextFormatting.BOLD + new TextComponentTranslation(new StringBuilder().append("tc.research_category.").append(this.category).toString(), new Object[0]).getFormattedText() + TextFormatting.RESET }).getUnformattedText();
    }
}
